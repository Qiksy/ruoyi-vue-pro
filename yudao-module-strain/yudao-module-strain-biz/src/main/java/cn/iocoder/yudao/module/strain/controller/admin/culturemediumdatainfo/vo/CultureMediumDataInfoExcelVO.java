package cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 培养基数据信息 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class CultureMediumDataInfoExcelVO {

    @ExcelProperty("编码")
    private String code;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("灭菌条件")
    private String sterilizationConditions;

    @ExcelProperty("用途")
    private String purpose;

    @ExcelProperty("分类")
    private String category;

    @ExcelProperty("配方")
    private String formula;

    @ExcelProperty("创建者")
    private String createBy;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("备注")
    private String remark;

}
