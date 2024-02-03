package cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 存放区域信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StorageAreaInfoRespVO extends StorageAreaInfoBaseVO {

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 冻藏设备数量
     */
    private long deviceNum;


    //在库数量
    private long inStockNum;

    //待回库数量
    private long waitInStockNum;

    //空闲数量
    private long freeNum;

    //总数量
    private long totalNum;


}
