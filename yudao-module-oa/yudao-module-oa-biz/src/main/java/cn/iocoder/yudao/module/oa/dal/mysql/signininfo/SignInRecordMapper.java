package cn.iocoder.yudao.module.oa.dal.mysql.signinrecord;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.oa.dal.dataobject.signinrecord.SignInRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 签到记录 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface SignInRecordMapper extends BaseMapperX<SignInRecordDO> {

    default List<SignInRecordDO> selectListByParentId(Long parentId) {
        return selectList(SignInRecordDO::getParentId, parentId);
    }

    default int deleteByParentId(Long parentId) {
        return delete(SignInRecordDO::getParentId, parentId);
    }

}