package cn.iocoder.yudao.module.report.framework.jmreport.core.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.framework.security.config.SecurityProperties;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.system.api.oauth2.OAuth2TokenApi;
import cn.iocoder.yudao.module.system.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import lombok.RequiredArgsConstructor;
import org.jeecg.modules.jmreport.api.JmReportTokenServiceI;
import org.springframework.http.HttpHeaders;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@link JmReportTokenServiceI} 实现类，提供积木报表的 Token 校验、用户信息的查询等功能
 *
 * @author 随心
 */
@RequiredArgsConstructor
public class JmReportTokenServiceImpl implements JmReportTokenServiceI {

    /**
     * 积木 token head 头
     */
    private static final String JM_TOKEN_HEADER = "X-Access-Token";
    /**
     * auth 相关格式
     */
    private static final String AUTHORIZATION_FORMAT = SecurityFrameworkUtils.AUTHORIZATION_BEARER + " %s";

    private final OAuth2TokenApi oauth2TokenApi;

    private final SecurityProperties securityProperties;

    /**
     * 自定义 API 数据集appian自定义 Header，解决 Token 传递。
     * 参考 <a href="http://report.jeecg.com/2222224">api数据集token机制详解</a> 文档
     *
     * @return 新 head
     */
    @Override
    public HttpHeaders customApiHeader() {
        // 读取积木标标系统的 token
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader(JM_TOKEN_HEADER);

        // 设置到 yudao 系统的 token
        HttpHeaders headers = new HttpHeaders();
        headers.add(securityProperties.getTokenHeader(), String.format(AUTHORIZATION_FORMAT, token));
        return headers;
    }

    /**
     * 校验 Token 是否有效，即验证通过
     *
     * @param token JmReport 前端传递的 token
     * @return 是否认证通过
     */
    @Override
    public Boolean verifyToken(String token) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (!Objects.isNull(userId)) {
            return true;
        }
        return buildLoginUserByToken(token) != null;
    }

    /**
     * 获得用户编号
     * <p>
     * 虽然方法名获得的是 username，实际对应到项目中是用户编号
     *
     * @param token JmReport 前端传递的 token
     * @return 用户编号
     */
    @Override
    public String getUsername(String token) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (ObjectUtil.isNotNull(userId)) {
            return String.valueOf(userId);
        }
        LoginUser user = buildLoginUserByToken(token);
        return user == null ? null : String.valueOf(user.getId());
    }

    /**
     * 基于 token 构建登录用户
     *
     * @param token token
     * @return 返回 token 对应的用户信息
     */
    private LoginUser buildLoginUserByToken(String token) {
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        // TODO 如下的实现不算特别优雅，主要咱是不想搞的太复杂，所以参考对应的 Filter 先实现了

        // ① 参考 TokenAuthenticationFilter 的认证逻辑（Security 的上下文清理，交给 Spring Security 完成）
        // 目的：实现基于 JmReport 前端传递的 token，实现认证
        TenantContextHolder.setIgnore(true); // 忽略租户，保证可查询到 token 信息
        LoginUser user = null;
        try {
            OAuth2AccessTokenCheckRespDTO accessToken = oauth2TokenApi.checkAccessToken(token);
            if (accessToken == null) {
                return null;
            }
            user = new LoginUser().setId(accessToken.getUserId()).setUserType(accessToken.getUserType())
                    .setTenantId(accessToken.getTenantId()).setScopes(accessToken.getScopes());
        } catch (ServiceException ignored) {
            // do nothing：如果报错，说明认证失败，则返回 false 即可
        }
        if (user == null) {
            return null;
        }
        SecurityFrameworkUtils.setLoginUser(user, WebFrameworkUtils.getRequest());

        // ② 参考 TenantContextWebFilter 实现（Tenant 的上下文清理，交给 TenantContextWebFilter 完成）
        // 目的：基于 LoginUser 获得到的租户编号，设置到 Tenant 上下文，避免查询数据库时的报错
        TenantContextHolder.setIgnore(false);
        TenantContextHolder.setTenantId(user.getTenantId());
        return user;
    }

    /**
     * 获得用户角色
     * 这个应该是新版本的接口，老版本的接口没有这个方法
     *
     * @param var1
     * @return
     */
    @Override
    public String[] getRoles(String var1) {
        return new String[0];
    }


    /**
     * 这里的作用是给每个用户带上一个系统变量
     *
     * @param token
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String token) {

        HashMap<String, Object> variables = new HashMap<>(5);
        variables.put("sysUserCode", this.getUsername(token));
        variables.put("sysOrgCode", "");
        variables.put("nearestDate",getNearestDate());
        return variables;
    }


    /**
     * 获取距离今天最近的并满足条件的日期
     * 1. 需要为每个月的10、20号和月末
     * 2. 日期不能是未来的
     * @return
     */
    private String getNearestDate() {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today;

        // 判断今天是不是10号、20号或月末
        if (isTargetDate(today)) {
            targetDate = today.minusDays(1);
        }

        // 从昨天开始往前找，找到满足条件的日期
        while (!isTargetDate(targetDate)) {
            targetDate = targetDate.minusDays(1);
        }

        return targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private boolean isTargetDate(LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        // 判断日期是不是10号、20号或月末
        return dayOfMonth == 10 || dayOfMonth == 20 || date.isEqual(endOfMonth);
    }
}
