package cn.iocoder.yudao.module.strain.convert.storageareainfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.storageareainfo.StorageAreaInfoDO;

/**
 * 存放区域信息 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface StorageAreaInfoConvert {

    StorageAreaInfoConvert INSTANCE = Mappers.getMapper(StorageAreaInfoConvert.class);

    StorageAreaInfoDO convert(StorageAreaInfoCreateReqVO bean);

    StorageAreaInfoDO convert(StorageAreaInfoUpdateReqVO bean);

    StorageAreaInfoRespVO convert(StorageAreaInfoDO bean);

    List<StorageAreaInfoRespVO> convertList(List<StorageAreaInfoDO> list);

    PageResult<StorageAreaInfoRespVO> convertPage(PageResult<StorageAreaInfoDO> page);

    List<StorageAreaInfoExcelVO> convertList02(List<StorageAreaInfoDO> list);

}
