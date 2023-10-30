package cn.iocoder.yudao.module.crm.convert.business;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.crm.controller.admin.business.vo.*;
import cn.iocoder.yudao.module.crm.dal.dataobject.business.CrmBusinessDO;
import cn.iocoder.yudao.module.crm.service.permission.bo.TransferCrmPermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商机 Convert
 *
 * @author ljlleo
 */
@Mapper
public interface CrmBusinessConvert {

    CrmBusinessConvert INSTANCE = Mappers.getMapper(CrmBusinessConvert.class);

    CrmBusinessDO convert(CrmBusinessCreateReqVO bean);

    CrmBusinessDO convert(CrmBusinessUpdateReqVO bean);

    CrmBusinessRespVO convert(CrmBusinessDO bean);

    PageResult<CrmBusinessRespVO> convertPage(PageResult<CrmBusinessDO> page);

    List<CrmBusinessExcelVO> convertList02(List<CrmBusinessDO> list);

    @Mappings({
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "crmDataId", source = "reqVO.id")
    })
    TransferCrmPermissionBO convert(CrmTransferBusinessReqVO reqVO, Long userId);

}