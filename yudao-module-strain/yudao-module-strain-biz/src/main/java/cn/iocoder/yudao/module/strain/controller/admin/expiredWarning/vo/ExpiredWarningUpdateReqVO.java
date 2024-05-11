package cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ExpiredWarningUpdateReqVO {

    private LocalDateTime expiredDate;

    private Long[] ids;

    private String type;

}
