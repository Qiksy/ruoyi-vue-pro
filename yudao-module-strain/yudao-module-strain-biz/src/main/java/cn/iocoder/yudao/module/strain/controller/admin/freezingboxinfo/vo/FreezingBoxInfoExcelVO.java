package cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 冷冻盒信息 Excel VO
 *
 * @author qiksy
 */
@Data
public class FreezingBoxInfoExcelVO {

    @ExcelProperty("编码")
    private String code;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("x轴容量")
    private Integer xAxisCapacity;

    @ExcelProperty("y轴容量")
    private Integer yAxisCapacity;

    @ExcelProperty("x轴编号类型(0 数字 1 字母)")
    private String xAxisCodeType;

    @ExcelProperty("y轴编号类型(0 数字 1 字母)")
    private String yAxisCodeType;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("备注")
    private String remark;

}
