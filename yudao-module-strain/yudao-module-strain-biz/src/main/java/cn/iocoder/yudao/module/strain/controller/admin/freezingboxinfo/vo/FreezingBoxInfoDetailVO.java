package cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo;


import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo.FreezingTubeStockInfoRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 冷冻盒信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FreezingBoxInfoDetailVO extends FreezingBoxInfoRespVO {


    List<List<FreezingTubeStockInfoRespVO>> tubeStockInfoList;
}
