package cn.iocoder.yudao.module.strain.dal.mysql.outboundsubapplication;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundsubapplication.OutboundSubApplicationDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.outboundsubapplication.vo.*;

/**
 * 出库申请子 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OutboundSubApplicationMapper extends BaseMapperX<OutboundSubApplicationDO> {

    default PageResult<OutboundSubApplicationDO> selectPage(OutboundSubApplicationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OutboundSubApplicationDO>()
                .eqIfPresent(OutboundSubApplicationDO::getParentId, reqVO.getParentId())
                .eqIfPresent(OutboundSubApplicationDO::getSpecimenId, reqVO.getTubeId())
                .betweenIfPresent(OutboundSubApplicationDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(OutboundSubApplicationDO::getRemark, reqVO.getRemark())
                .orderByDesc(OutboundSubApplicationDO::getId));
    }

}