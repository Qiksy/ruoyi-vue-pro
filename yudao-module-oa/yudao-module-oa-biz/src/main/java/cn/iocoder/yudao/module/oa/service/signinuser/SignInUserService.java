package cn.iocoder.yudao.module.oa.service.signinuser;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.oa.controller.admin.signinuser.vo.*;
import cn.iocoder.yudao.module.oa.dal.dataobject.signinuser.SignInUserDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 会议参与成员 Service 接口
 *
 * @author 超级管理员
 */
public interface SignInUserService {

    /**
     * 创建会议参与成员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSignInUser(@Valid SignInUserSaveReqVO createReqVO);

    /**
     * 更新会议参与成员
     *
     * @param updateReqVO 更新信息
     */
    void updateSignInUser(@Valid SignInUserSaveReqVO updateReqVO);

    /**
     * 删除会议参与成员
     *
     * @param id 编号
     */
    void deleteSignInUser(Long id);

    /**
     * 获得会议参与成员
     *
     * @param id 编号
     * @return 会议参与成员
     */
    SignInUserDO getSignInUser(Long id);

    /**
     * 获得会议参与成员分页
     *
     * @param pageReqVO 分页查询
     * @return 会议参与成员分页
     */
    PageResult<SignInUserDO> getSignInUserPage(SignInUserPageReqVO pageReqVO);

}