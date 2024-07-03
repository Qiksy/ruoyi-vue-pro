package cn.iocoder.yudao.module.oa.dal.mysql.signininfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInTimeRangeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 签到时间范围 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface SignInTimeRangeMapper extends BaseMapperX<SignInTimeRangeDO> {

    default List<SignInTimeRangeDO> selectListByParentId(Long parentId) {
        return selectList(SignInTimeRangeDO::getParentId, parentId);
    }

    default int deleteByParentId(Long parentId) {
        return delete(SignInTimeRangeDO::getParentId, parentId);
    }

}