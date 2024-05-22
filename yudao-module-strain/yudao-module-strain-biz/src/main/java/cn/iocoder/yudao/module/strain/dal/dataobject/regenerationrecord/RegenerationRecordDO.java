package cn.iocoder.yudao.module.strain.dal.dataobject.regenerationrecord;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 样品复壮传代记录 DO
 *
 * @author 超级管理员
 */
@TableName("strain_regeneration_record")
@KeySequence("strain_regeneration_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegenerationRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 样品id
     */
    private Long specimenId;
    /**
     * 更改内容
     */
    private String content;
    /**
     * 备注
     */
    private String remark;

}