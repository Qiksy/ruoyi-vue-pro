package cn.iocoder.yudao.module.strain.dal.dataobject.outboundapplication;

import cn.iocoder.yudao.module.bpm.enums.task.BpmTaskStatusEnum;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 出库申请 DO
 *
 * @author 芋道源码
 */
@TableName("strain_outbound_application")
@KeySequence("strain_outbound_application_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboundApplicationDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 编码
     */
    private String code;
    /**
     * 申请人
     */
    private String applicant;
    /**
     * 用途说明
     */
    private String useage;
    /**
     * 是否会重新入库 0否 1是
     */
//    private Boolean isRestocked;
    /**
     * {@link cn.iocoder.yudao.module.strain.enums.OutboundTypeConstants}
     * 出库类型：1正常出库 2销毁出库
     */
    private String type;
    /**
     * 结果反馈
     */
    private String result;
    /**
     * 备注
     */
    private String remark;
    /**
     * 审批流程实例id
     */
    private String processInstanceId;
    /**
     * 审批结果
     * 枚举 {@link BpmTaskStatusEnum}
     */
    private String approResult;

}