package cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 冷冻设备信息 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class FreezingDeviceInfoExcelVO {

    @ExcelProperty("编号")
    private String name;

    @ExcelProperty("名称")
    private String code;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("摄氏度")
    private Long temperature;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("备注")
    private String remark;

}
