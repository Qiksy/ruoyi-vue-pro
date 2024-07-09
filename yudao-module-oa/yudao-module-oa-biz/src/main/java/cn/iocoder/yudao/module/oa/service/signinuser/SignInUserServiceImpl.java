package cn.iocoder.yudao.module.oa.service.signinuser;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.oa.controller.admin.signinuser.vo.*;
import cn.iocoder.yudao.module.oa.dal.dataobject.signinuser.SignInUserDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.oa.dal.mysql.signinuser.SignInUserMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.oa.enums.ErrorCodeConstants.*;

/**
 * 会议参与成员 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class SignInUserServiceImpl implements SignInUserService {

    @Resource
    private SignInUserMapper signInUserMapper;

    @Override
    public Long createSignInUser(SignInUserSaveReqVO createReqVO) {
        // 插入
        SignInUserDO signInUser = BeanUtils.toBean(createReqVO, SignInUserDO.class);
        signInUserMapper.insert(signInUser);
        // 返回
        return signInUser.getId();
    }

    @Override
    public void updateSignInUser(SignInUserSaveReqVO updateReqVO) {
        // 校验存在
        validateSignInUserExists(updateReqVO.getId());
        // 更新
        SignInUserDO updateObj = BeanUtils.toBean(updateReqVO, SignInUserDO.class);
        signInUserMapper.updateById(updateObj);
    }

    @Override
    public void deleteSignInUser(Long id) {
        // 校验存在
        validateSignInUserExists(id);
        // 删除
        signInUserMapper.deleteById(id);
    }

    private void validateSignInUserExists(Long id) {
        if (signInUserMapper.selectById(id) == null) {
            throw exception(SIGN_IN_USER_NOT_EXISTS);
        }
    }

    @Override
    public SignInUserDO getSignInUser(Long id) {
        return signInUserMapper.selectById(id);
    }

    @Override
    public PageResult<SignInUserDO> getSignInUserPage(SignInUserPageReqVO pageReqVO) {
        return signInUserMapper.selectPage(pageReqVO);
    }

}