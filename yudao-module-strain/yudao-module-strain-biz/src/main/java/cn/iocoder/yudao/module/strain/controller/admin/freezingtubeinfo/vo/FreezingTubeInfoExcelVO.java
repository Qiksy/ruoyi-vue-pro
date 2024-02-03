package cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 冷冻管基本信息 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class FreezingTubeInfoExcelVO {

    @ExcelProperty("编号")
    private String code;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("容量")
    private Integer capacity;

    @ExcelProperty(value = "容量单位（1ml/cm3 2 L/dm3 3 m3）", converter = DictConvert.class)
    @DictFormat("strain_volume_unit") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String volumeUnit;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("备注")
    private String remark;

}
