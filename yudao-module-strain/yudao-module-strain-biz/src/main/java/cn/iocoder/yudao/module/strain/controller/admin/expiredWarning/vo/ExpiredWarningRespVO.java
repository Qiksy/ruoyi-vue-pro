package cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpiredWarningRespVO {

    private Long id;

    private String specimenCode;

    private String chineseName;

    private String latinName;

    private String microbeType;

    private LocalDateTime saveDate;

    private LocalDateTime expirationDate;

    private String useage;

    private String mediumName;

    private String source;

    private String temperature;

    private Long stockId;

    private String positionStr;

    private String boxCode;

}
