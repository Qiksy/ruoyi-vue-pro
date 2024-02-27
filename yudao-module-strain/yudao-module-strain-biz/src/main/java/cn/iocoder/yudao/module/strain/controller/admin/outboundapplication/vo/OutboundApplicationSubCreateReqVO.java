package cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo;


import lombok.Data;

@Data
public class OutboundApplicationSubCreateReqVO {

    /**
     * 样品id
     */
    private Long specimenId;


    /**
     * 样品保藏编号
     */
    private String specimenCode;

    /**
     * 中文名称
     */
    private String chineseName;


    /**
     * 拉丁名
     */
    private String latinName;


    /**
     * 槽位id
     */
    private Long stockId;
}
