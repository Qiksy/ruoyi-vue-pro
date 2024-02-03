package cn.iocoder.yudao.module.system.dal.dataobject.printtemplate;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 打印模板 DO
 *
 * @author 播恩超级管理员
 */
@TableName("system_print_template")
@KeySequence("system_print_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrintTemplateDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;


    /**
     * 编码
     */
    private String code;
    /**
     * 模板内容
     */
    private String templateContent;
    /**
     * 系统默认
     */
    private Boolean isSystemDefault;
    /**
     * 备注
     */
    private String remark;

}