package cn.iocoder.yudao.module.strain.convert.freezingboxinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo.FreezingBoxInfoDO;

/**
 * 冷冻盒信息 Convert
 *
 * @author qiksy
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface FreezingBoxInfoConvert {

    FreezingBoxInfoConvert INSTANCE = Mappers.getMapper(FreezingBoxInfoConvert.class);


    FreezingBoxInfoDO convert(FreezingBoxInfoCreateReqVO bean);

    FreezingBoxInfoDO convert(FreezingBoxInfoUpdateReqVO bean);

    FreezingBoxInfoRespVO convert(FreezingBoxInfoDO bean);

    List<FreezingBoxInfoRespVO> convertList(List<FreezingBoxInfoDO> list);

    PageResult<FreezingBoxInfoRespVO> convertPage(PageResult<FreezingBoxInfoDO> page);

    List<FreezingBoxInfoExcelVO> convertList02(List<FreezingBoxInfoDO> list);

}
