package cn.iocoder.yudao.module.strain.controller.admin.resave.vo;


import lombok.Data;

@Data
public class ResaveRespVO {


    String applyCode;

    String specimenCode;

    String microbeCode;

    String chineseName;

    String latinName;

    String positionStr;

    /**
     * 插槽位置
     */
    Long stockId;

}
