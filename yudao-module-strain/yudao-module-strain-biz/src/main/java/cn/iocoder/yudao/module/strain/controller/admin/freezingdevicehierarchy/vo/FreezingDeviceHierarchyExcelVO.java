package cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 冷冻设备层级 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class FreezingDeviceHierarchyExcelVO {

    @ExcelProperty("父级id")
    private Long parentId;

    @ExcelProperty("冷冻设备id")
    private Long freezingDeviceId;

    @ExcelProperty(value = "是否为末级", converter = DictConvert.class)
    @DictFormat("strain_yes_no") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Boolean isFinalLevel;

    @ExcelProperty("末级类型id")
    private Long freezingBoxId;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("层级类型")
    private String layerType;

}
