package cn.iocoder.yudao.module.strain.convert.freezingtubeinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubeinfo.FreezingTubeInfoDO;

/**
 * 冷冻管基本信息 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface FreezingTubeInfoConvert {

    FreezingTubeInfoConvert INSTANCE = Mappers.getMapper(FreezingTubeInfoConvert.class);

    FreezingTubeInfoDO convert(FreezingTubeInfoCreateReqVO bean);

    FreezingTubeInfoDO convert(FreezingTubeInfoUpdateReqVO bean);

    FreezingTubeInfoRespVO convert(FreezingTubeInfoDO bean);

    List<FreezingTubeInfoRespVO> convertList(List<FreezingTubeInfoDO> list);

    PageResult<FreezingTubeInfoRespVO> convertPage(PageResult<FreezingTubeInfoDO> page);

    List<FreezingTubeInfoExcelVO> convertList02(List<FreezingTubeInfoDO> list);

}
