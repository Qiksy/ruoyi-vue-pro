package cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 存放区域信息 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class StorageAreaInfoExcelVO {

    @ExcelProperty("编号")
    private String code;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("位置信息")
    private String locationInfo;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("备注")
    private String remark;

}
