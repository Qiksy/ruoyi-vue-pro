package cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * 同步客户销售明细 Request VO
 * @author linr
 * @since 2023/11/30 16:36
 */
@Data
public class CustomerSalesDetailSyncReqVO {

    @Schema(description = "时间范围")
//    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private String[] timeRange;
}
