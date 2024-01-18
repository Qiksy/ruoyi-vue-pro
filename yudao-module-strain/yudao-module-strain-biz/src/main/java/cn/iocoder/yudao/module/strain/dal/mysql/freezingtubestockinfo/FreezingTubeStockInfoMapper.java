package cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo.*;

/**
 * 冷冻盒槽位 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface FreezingTubeStockInfoMapper extends BaseMapperX<FreezingTubeStockInfoDO> {

    default PageResult<FreezingTubeStockInfoDO> selectPage(FreezingTubeStockInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FreezingTubeStockInfoDO>()
                .eqIfPresent(FreezingTubeStockInfoDO::getCode, reqVO.getCode())
                .eqIfPresent(FreezingTubeStockInfoDO::getTubeId, reqVO.getTubeId())
                .eqIfPresent(FreezingTubeStockInfoDO::getBoxId, reqVO.getBoxId())
                .eqIfPresent(FreezingTubeStockInfoDO::getTubePosition, reqVO.getTubePosition())
                .eqIfPresent(FreezingTubeStockInfoDO::getTubePositionX, reqVO.getTubePositionX())
                .eqIfPresent(FreezingTubeStockInfoDO::getTubePositionY, reqVO.getTubePositionY())
                .eqIfPresent(FreezingTubeStockInfoDO::getGenerationNumber, reqVO.getGenerationNumber())
                .eqIfPresent(FreezingTubeStockInfoDO::getThawFreezeCycleCount, reqVO.getThawFreezeCycleCount())
                .eqIfPresent(FreezingTubeStockInfoDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(FreezingTubeStockInfoDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(FreezingTubeStockInfoDO::getMicrobeId, reqVO.getMicrobeId())
                .betweenIfPresent(FreezingTubeStockInfoDO::getExpirationDate, reqVO.getExpirationDate())
                .betweenIfPresent(FreezingTubeStockInfoDO::getSaveDate, reqVO.getSaveDate())
                .eqIfPresent(FreezingTubeStockInfoDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FreezingTubeStockInfoDO::getSaveBy, reqVO.getSaveBy())
                .eqIfPresent(FreezingTubeStockInfoDO::getStockPreEntryId, reqVO.getStockPreEntryId())
                .betweenIfPresent(FreezingTubeStockInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FreezingTubeStockInfoDO::getRemark, reqVO.getRemark())
                .orderByDesc(FreezingTubeStockInfoDO::getId));
    }

}