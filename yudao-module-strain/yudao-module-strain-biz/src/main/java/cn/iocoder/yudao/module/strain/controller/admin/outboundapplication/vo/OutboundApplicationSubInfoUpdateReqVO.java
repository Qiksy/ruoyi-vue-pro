package cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo;


import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 出库申请新增样品请求VO，用来传代/复壮的时候添加新的样品")
@Data
public class OutboundApplicationSubInfoUpdateReqVO {

    /**
     * 主表id
     */
    private Long id;


    /**
     * 样品id
     */
    private List<ExpiredWarningRespVO> specimenInfo;
}
