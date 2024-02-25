package cn.iocoder.yudao.module.strain.dal.mysql.outboundapplication;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundapplication.OutboundApplicationDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo.*;

/**
 * 出库申请 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OutboundApplicationMapper extends BaseMapperX<OutboundApplicationDO> {

    default PageResult<OutboundApplicationDO> selectPage(OutboundApplicationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OutboundApplicationDO>()
                .eqIfPresent(OutboundApplicationDO::getCode, reqVO.getCode())
                .eqIfPresent(OutboundApplicationDO::getApplicant, reqVO.getApplicant())
                .eqIfPresent(OutboundApplicationDO::getUseage, reqVO.getUseage())
                .eqIfPresent(OutboundApplicationDO::getIsRestocked, reqVO.getIsRestocked())
                .eqIfPresent(OutboundApplicationDO::getType, reqVO.getType())
                .eqIfPresent(OutboundApplicationDO::getProcessInstanceId, reqVO.getProcessInstanceId())
                .eqIfPresent(OutboundApplicationDO::getApproResult, reqVO.getApproResult())
                .orderByDesc(OutboundApplicationDO::getId));
    }

}