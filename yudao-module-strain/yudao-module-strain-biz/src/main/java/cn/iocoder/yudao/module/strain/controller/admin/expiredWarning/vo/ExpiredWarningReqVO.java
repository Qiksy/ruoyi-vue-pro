package cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo;


import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class ExpiredWarningReqVO extends PageParam {

//    code: '',
//    chineseName: '',
//    latinName: '',
//    microbeType: ''

    private String specimenCode;

    private String chineseName;

    private String latinName;

    private String microbeType;

    private String saveByName;

}
