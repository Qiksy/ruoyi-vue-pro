package cn.iocoder.yudao.module.strain.convert.freezingdeviceinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdeviceinfo.FreezingDeviceInfoDO;

/**
 * 冷冻设备信息 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface FreezingDeviceInfoConvert {

    FreezingDeviceInfoConvert INSTANCE = Mappers.getMapper(FreezingDeviceInfoConvert.class);

    FreezingDeviceInfoDO convert(FreezingDeviceInfoCreateReqVO bean);

    FreezingDeviceInfoDO convert(FreezingDeviceInfoUpdateReqVO bean);

    FreezingDeviceInfoRespVO convert(FreezingDeviceInfoDO bean);

    List<FreezingDeviceInfoRespVO> convertList(List<FreezingDeviceInfoDO> list);

    PageResult<FreezingDeviceInfoRespVO> convertPage(PageResult<FreezingDeviceInfoDO> page);

    List<FreezingDeviceInfoExcelVO> convertList02(List<FreezingDeviceInfoDO> list);

}
