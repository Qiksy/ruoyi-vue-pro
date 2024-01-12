package cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry.vo.*;

/**
 * 冷冻管库存预录入 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface FreezingTubeStockPreEntryMapper extends BaseMapperX<FreezingTubeStockPreEntryDO> {

    default PageResult<FreezingTubeStockPreEntryDO> selectPage(FreezingTubeStockPreEntryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FreezingTubeStockPreEntryDO>()
                .eqIfPresent(FreezingTubeStockPreEntryDO::getCode, reqVO.getCode())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubeId, reqVO.getTubeId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getBoxId, reqVO.getBoxId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubePosition, reqVO.getTubePosition())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubePositionX, reqVO.getTubePositionX())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubePositionY, reqVO.getTubePositionY())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getGenerationNumber, reqVO.getGenerationNumber())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getThawFreezeCycleCount, reqVO.getThawFreezeCycleCount())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getMicrobeId, reqVO.getMicrobeId())
                .betweenIfPresent(FreezingTubeStockPreEntryDO::getExpirationDate, reqVO.getExpirationDate())
                .betweenIfPresent(FreezingTubeStockPreEntryDO::getSaveDate, reqVO.getSaveDate())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getSaveBy, reqVO.getSaveBy())
                .betweenIfPresent(FreezingTubeStockPreEntryDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getStatus, reqVO.getStatus())
                .orderByDesc(FreezingTubeStockPreEntryDO::getId));
    }

}