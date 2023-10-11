package cn.iocoder.yudao.module.strain.dal.mysql.freezingtubeinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubeinfo.FreezingTubeInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo.vo.*;

/**
 * 冷冻管基本信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface FreezingTubeInfoMapper extends BaseMapperX<FreezingTubeInfoDO> {

    default PageResult<FreezingTubeInfoDO> selectPage(FreezingTubeInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FreezingTubeInfoDO>()
                .eqIfPresent(FreezingTubeInfoDO::getCode, reqVO.getCode())
                .likeIfPresent(FreezingTubeInfoDO::getName, reqVO.getName())
                .eqIfPresent(FreezingTubeInfoDO::getCapacity, reqVO.getCapacity())
                .eqIfPresent(FreezingTubeInfoDO::getVolumeUnit, reqVO.getVolumeUnit())
                .betweenIfPresent(FreezingTubeInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FreezingTubeInfoDO::getRemark, reqVO.getRemark())
                .orderByDesc(FreezingTubeInfoDO::getId));
    }

    default List<FreezingTubeInfoDO> selectList(FreezingTubeInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FreezingTubeInfoDO>()
                .eqIfPresent(FreezingTubeInfoDO::getCode, reqVO.getCode())
                .likeIfPresent(FreezingTubeInfoDO::getName, reqVO.getName())
                .eqIfPresent(FreezingTubeInfoDO::getCapacity, reqVO.getCapacity())
                .eqIfPresent(FreezingTubeInfoDO::getVolumeUnit, reqVO.getVolumeUnit())
                .betweenIfPresent(FreezingTubeInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FreezingTubeInfoDO::getRemark, reqVO.getRemark())
                .orderByDesc(FreezingTubeInfoDO::getId));
    }

}
