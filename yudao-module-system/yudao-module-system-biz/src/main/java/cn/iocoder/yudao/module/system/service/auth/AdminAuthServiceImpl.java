package cn.iocoder.yudao.module.system.service.auth;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.util.monitor.TracerUtils;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import cn.iocoder.yudao.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.yudao.module.system.api.sms.SmsCodeApi;
import cn.iocoder.yudao.module.system.api.social.dto.SocialUserBindReqDTO;
import cn.iocoder.yudao.module.system.api.social.dto.SocialUserRespDTO;
import cn.iocoder.yudao.module.system.api.tencent.TencentMiniProgramAuthApi;
import cn.iocoder.yudao.module.system.api.tencent.dto.WechatSessionRespDTO;
import cn.iocoder.yudao.module.system.api.tencent.dto.WxworkSessionRespDTO;
import cn.iocoder.yudao.module.system.controller.admin.auth.vo.*;
import cn.iocoder.yudao.module.system.convert.auth.AuthConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.yudao.module.system.enums.logger.LoginResultEnum;
import cn.iocoder.yudao.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.iocoder.yudao.module.system.enums.sms.SmsSceneEnum;
import cn.iocoder.yudao.module.system.service.logger.LoginLogService;
import cn.iocoder.yudao.module.system.service.member.MemberService;
import cn.iocoder.yudao.module.system.service.oauth2.OAuth2TokenService;
import cn.iocoder.yudao.module.system.service.social.SocialUserService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.validation.Validator;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * Auth Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private AdminUserService userService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private SocialUserService socialUserService;
    @Resource
    private MemberService memberService;
    @Resource
    private Validator validator;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private SmsCodeApi smsCodeApi;

    @Resource
    private TencentMiniProgramAuthApi tencentMiniProgramAuthApi;

    /**
     * 验证码的开关，默认为 true
     */
    @Value("${yudao.captcha.enable:true}")
    private Boolean captchaEnable;

    @Override
    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (CommonStatusEnum.isDisable(user.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 校验验证码
        validateCaptcha(reqVO);

        // 使用账号密码，进行登录
        AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // 如果 socialType 非空，说明需要绑定社交用户
        if (reqVO.getSocialType() != null) {
            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }
        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @Override
    public void sendSmsCode(AuthSmsSendReqVO reqVO) {
        // 登录场景，验证是否存在
        if (userService.getUserByMobile(reqVO.getMobile()) == null) {
            throw exception(AUTH_MOBILE_NOT_EXISTS);
        }
        // 发送验证码
        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
    }

    @Override
    public AuthLoginRespVO smsLogin(AuthSmsLoginReqVO reqVO) {
        // 校验验证码
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_MEMBER_LOGIN.getScene(), getClientIP()));

        // 获得用户信息
        AdminUserDO user = userService.getUserByMobile(reqVO.getMobile());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE);
    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    @Override
    public AuthLoginRespVO socialLogin(AuthSocialLoginReqVO reqVO) {
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
        SocialUserRespDTO socialUser = socialUserService.getSocialUserByCode(UserTypeEnum.ADMIN.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (socialUser == null || socialUser.getUserId() == null) {
            throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
        }

        // 获得用户
        AdminUserDO user = userService.getUser(socialUser.getUserId());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_SOCIAL);
    }


    /**
     * 使用小程序快速登录
     *
     * @param reqVO 请求登录信息
     * @return 登录结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthLoginRespVO mpLogin(AuthMpWeixinLoginReqVO reqVO) {
        if ("wxwork".equals(reqVO.getMpType())){
            //企业微信平台

            // 1. 通过code去获取 userId，corpid，session_key
            WxworkSessionRespDTO sessionRespDTO = tencentMiniProgramAuthApi.wxworkCode2Session(reqVO.getCode());
            log.info("在企业微信中的小程序登录，用户的userId为：{}",sessionRespDTO.getUserid());

            // 2. 记录三方用户，有就更新，没有就插入
            socialUserService.insertOrUpdateSocialUser(sessionRespDTO.getUserid(), UserTypeEnum.ADMIN.getValue());

            // 3. 此处的userId对应的是企业微信通讯录中的明文id。这个时候可以去获取对应的系统用户信息
            AdminUserDO userByPkPsndoc = userService.getUserByPkPsndoc(sessionRespDTO.getUserid());
            if (userByPkPsndoc==null){
                userByPkPsndoc = userService.getUserByWecomeId(sessionRespDTO.getUserid());
            }

            if (userByPkPsndoc==null){
                //没有绑定的用户，报错
                throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
            }

            // 4. 创建 Token 令牌，记录登录日志
            return createTokenAfterLoginSuccess(userByPkPsndoc.getId(), userByPkPsndoc.getUsername(), LoginLogTypeEnum.LOGIN_WORK_WECHAT_MP);

        } else {
            // 此处是微信的openId
            // 1.根据code去获取openId
            WechatSessionRespDTO respDTO =  tencentMiniProgramAuthApi.wechatCode2Session(reqVO.getCode());

            // 2.调用 企业应用 播恩助手的openId转换userId的接口
            String userId = tencentMiniProgramAuthApi.openId2userId(respDTO.getOpenid());

            // 3. 获取真正的用户信息
            // 3. 此处的userId对应的是企业微信通讯录中的明文id。这个时候可以去获取对应的系统用户信息
            AdminUserDO userByPkPsndoc = userService.getUserByPkPsndoc(userId);

            if (userByPkPsndoc==null){
                userByPkPsndoc = userService.getUserByWecomeId(userId);
            }

            log.info("在微信中的小程序登录，用户的userId为：{}",userId);
            log.info("在微信中的小程序登录，用户的userByPkPsndoc为：{}",userByPkPsndoc);
            if (userByPkPsndoc==null){
                //没有绑定的用户，报错
                throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
            }
            // 4. 创建Token令牌，记录登录日志
            return  createTokenAfterLoginSuccess(userByPkPsndoc.getId(), userByPkPsndoc.getUsername(), LoginLogTypeEnum.LOGIN_WECHAT_MP);

        }
    }

    @VisibleForTesting
    void validateCaptcha(AuthLoginReqVO reqVO) {
        // 如果验证码关闭，则不进行校验
        if (!captchaEnable) {
            return;
        }
        // 校验验证码
        ValidationUtils.validate(validator, reqVO, AuthLoginReqVO.CodeEnableGroup.class);
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(reqVO.getCaptchaVerification());
        ResponseModel response = captchaService.verification(captchaVO);
        // 验证不通过
        if (!response.isSuccess()) {
            // 创建登录失败日志（验证码不正确)
            createLoginLog(null, reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.CAPTCHA_CODE_ERROR);
            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR, response.getRepMsg());
        }
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        // 插入登陆日志
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // 创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(token);
        if (accessTokenDO == null) {
            return;
        }
        // 删除成功，则记录登出日志
        createLogoutLog(accessTokenDO.getUserId(), accessTokenDO.getUserType(), logType);
    }


    @Override
    public AuthLoginRespVO workWechatLogin(String code, String state) throws IOException {
        String tencentAccessToken;
        //获取accesstoken
        tencentAccessToken = tencentMiniProgramAuthApi.getSaleAccessToken();
        JsonNode jsonNode = getWeChatUserInfo(code, tencentAccessToken);

        if (jsonNode.get("errcode").asInt()!=0){
            //如果失败了，则重试几次
            //需要清除旧的access_token
            for (int i = 0; i < 3; i++) {
                tencentMiniProgramAuthApi.clearSaleAccessToken();
                tencentAccessToken = tencentMiniProgramAuthApi.getSaleAccessToken();
                jsonNode = getWeChatUserInfo(code, tencentAccessToken);
                if (jsonNode.get("errcode").asInt()==0){
                    //如果成功了，跳出循环
                    break;
                }
            }
        }
        String pkPsndoc = jsonNode.get("").asText();

        //后续的登录按照原本的逻辑
        AdminUserDO user = userService.getUserByPkPsndoc(pkPsndoc);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_WORK_WECHAT);
    }

    private static JsonNode getWeChatUserInfo(String code, String tencentAccessToken) throws IOException {
        //获取用户id
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo?access_token=" + tencentAccessToken + "&code="+ code;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        String content = EntityUtils.toString(response.getEntity(), "utf-8");

        ObjectMapper op = new ObjectMapper();
        JsonNode jsonNode = op.readTree(content);
        return jsonNode;
    }

    private void createLogoutLog(Long userId, Integer userType, Integer logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(userType);
        if (ObjectUtil.equal(getUserType().getValue(), userType)) {
            reqDTO.setUsername(getUsername(userId));
        } else {
            reqDTO.setUsername(memberService.getMemberUserMobile(userId));
        }
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        AdminUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

}
