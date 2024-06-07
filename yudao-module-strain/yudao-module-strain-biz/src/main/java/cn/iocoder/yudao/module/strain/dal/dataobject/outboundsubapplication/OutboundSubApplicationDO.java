package cn.iocoder.yudao.module.strain.dal.dataobject.outboundsubapplication;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 出库申请子 DO
 *
 * @author 芋道源码
 */
@TableName("strain_outbound_sub_application")
@KeySequence("strain_outbound_sub_application_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboundSubApplicationDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 主表id
     */
    private Long parentId;
    /**
     * 样品id
     */
    private Long specimenId;

    /**
     * 槽位id
     */
    private Long stockId;

    /**
     * 菌种名称
     */
    private String specimenCode;
    /**
     * 中文名称
     */
    private String chineseName;

    private String latinName;


    /**
     * 是否已经重新存储了 0否1是
     *
     * 如果是销毁、消耗出库 则不需要重新存储，这个时候设置成为1
     */
    private boolean resave;





    /**
     * 备注
     */
    private String remark;

}