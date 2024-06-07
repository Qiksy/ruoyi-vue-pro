package cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo;

import lombok.Data;

@Data
public class OutboundApplicationSubRespVO extends OutboundApplicationSubCreateReqVO{


    private Long id;


    /**
     * 位置信息，需要实时获取
     */
    private String positionStr;

    private String saveByName;

    /**
     * 是否重新入库
     */
    private boolean resave;
}
