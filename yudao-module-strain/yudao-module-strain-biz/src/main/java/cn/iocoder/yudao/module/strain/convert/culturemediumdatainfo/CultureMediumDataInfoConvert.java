package cn.iocoder.yudao.module.strain.convert.culturemediumdatainfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.culturemediumdatainfo.CultureMediumDataInfoDO;

/**
 * 培养基数据信息 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface CultureMediumDataInfoConvert {

    CultureMediumDataInfoConvert INSTANCE = Mappers.getMapper(CultureMediumDataInfoConvert.class);

    CultureMediumDataInfoDO convert(CultureMediumDataInfoCreateReqVO bean);

    CultureMediumDataInfoDO convert(CultureMediumDataInfoUpdateReqVO bean);

    CultureMediumDataInfoRespVO convert(CultureMediumDataInfoDO bean);

    List<CultureMediumDataInfoRespVO> convertList(List<CultureMediumDataInfoDO> list);

    PageResult<CultureMediumDataInfoRespVO> convertPage(PageResult<CultureMediumDataInfoDO> page);

    List<CultureMediumDataInfoExcelVO> convertList02(List<CultureMediumDataInfoDO> list);

}
