package cn.iocoder.yudao.module.oa.dal.mysql.signinuser;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.oa.dal.dataobject.signinuser.SignInUserDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.oa.controller.admin.signinuser.vo.*;

/**
 * 会议参与成员 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface SignInUserMapper extends BaseMapperX<SignInUserDO> {

    default PageResult<SignInUserDO> selectPage(SignInUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SignInUserDO>()
                .eqIfPresent(SignInUserDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SignInUserDO::getMeetingId, reqVO.getMeetingId())
                .betweenIfPresent(SignInUserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SignInUserDO::getId));
    }

}