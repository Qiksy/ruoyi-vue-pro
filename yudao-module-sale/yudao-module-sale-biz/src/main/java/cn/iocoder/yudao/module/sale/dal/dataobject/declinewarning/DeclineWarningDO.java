package cn.iocoder.yudao.module.sale.dal.dataobject.declinewarning;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 销量预警 DO
 *
 * @author 播恩超级管理员
 */
@TableName("sale_decline_warning")
@KeySequence("sale_decline_warning_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclineWarningDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 战区编码
     */
    private String zoneCode;
    /**
     * 战区名称
     */
    private String zoneName;
    /**
     * 大区编码
     */
    private String areaName;
    /**
     * 大区名称
     */
    private String areaCode;
    /**
     * 总结
     */
    private String summarize;
    /**
     * 原因分析
     */
    private String reasonAnalysis;
    /**
     * 改进措施
     */
    private String improvementMeasure;
    /**
     * 附件id
     */
    private String fileId;
    /**
     * 对比时间
     */
    private String competeTime;
    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 客户数量
     */
    int custCount;
    /**
     * 总下降量
     */
    Double totalDeclineNum;
    /**
     * 总上月销量
     */
    Double totalPreMonthSales;
    /**
     * 总当前月销量
     */
    Double totalCurrMonthSales;
    /**
     * 总下降比例
     */
    Double totalDeclineRatio;

    /**
     * 审批结果
     */
    int result;//审批结果

}