package cn.iocoder.yudao.module.strain.dal.mysql.freezingdeviceinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdeviceinfo.FreezingDeviceInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo.*;

/**
 * 冷冻设备信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface FreezingDeviceInfoMapper extends BaseMapperX<FreezingDeviceInfoDO> {

    default PageResult<FreezingDeviceInfoDO> selectPage(FreezingDeviceInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FreezingDeviceInfoDO>()
                .likeIfPresent(FreezingDeviceInfoDO::getName, reqVO.getName())
                .eqIfPresent(FreezingDeviceInfoDO::getCode, reqVO.getCode())
                .eqIfPresent(FreezingDeviceInfoDO::getType, reqVO.getType())
                .eqIfPresent(FreezingDeviceInfoDO::getTemperature, reqVO.getTemperature())
                .betweenIfPresent(FreezingDeviceInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FreezingDeviceInfoDO::getRemark, reqVO.getRemark())
                .orderByDesc(FreezingDeviceInfoDO::getId));
    }

    default List<FreezingDeviceInfoDO> selectList(FreezingDeviceInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FreezingDeviceInfoDO>()
                .likeIfPresent(FreezingDeviceInfoDO::getName, reqVO.getName())
                .eqIfPresent(FreezingDeviceInfoDO::getCode, reqVO.getCode())
                .eqIfPresent(FreezingDeviceInfoDO::getType, reqVO.getType())
                .eqIfPresent(FreezingDeviceInfoDO::getTemperature, reqVO.getTemperature())
                .betweenIfPresent(FreezingDeviceInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FreezingDeviceInfoDO::getRemark, reqVO.getRemark())
                .orderByDesc(FreezingDeviceInfoDO::getId));
    }

}
