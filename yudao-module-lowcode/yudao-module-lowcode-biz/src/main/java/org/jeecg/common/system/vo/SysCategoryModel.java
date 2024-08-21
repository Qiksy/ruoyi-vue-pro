package org.jeecg.common.system.vo;

import lombok.Data;

/**
 * 系统类别
 * @author opv2
 * @since 2024/8/21 下午1:02
 */
@Data
public class SysCategoryModel {
    /**主键*/
    private java.lang.String id;
    /**父级节点*/
    private java.lang.String pid;
    /**类型名称*/
    private java.lang.String name;
    /**类型编码*/
    private java.lang.String code;

}