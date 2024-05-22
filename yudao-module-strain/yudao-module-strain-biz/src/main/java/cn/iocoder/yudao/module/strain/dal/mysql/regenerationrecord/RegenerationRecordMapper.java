package cn.iocoder.yudao.module.strain.dal.mysql.regenerationrecord;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.regenerationrecord.RegenerationRecordDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.regenerationrecord.vo.*;

/**
 * 样品复壮传代记录 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface RegenerationRecordMapper extends BaseMapperX<RegenerationRecordDO> {

    default PageResult<RegenerationRecordDO> selectPage(RegenerationRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RegenerationRecordDO>()
                .eqIfPresent(RegenerationRecordDO::getSpecimenId, reqVO.getSpecimenId())
                .eqIfPresent(RegenerationRecordDO::getContent, reqVO.getContent())
                .betweenIfPresent(RegenerationRecordDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RegenerationRecordDO::getRemark, reqVO.getRemark())
                .orderByDesc(RegenerationRecordDO::getId));
    }

}