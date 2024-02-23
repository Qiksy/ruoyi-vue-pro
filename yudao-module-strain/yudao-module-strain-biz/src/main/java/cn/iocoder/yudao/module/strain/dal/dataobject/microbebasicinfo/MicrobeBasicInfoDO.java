package cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import org.glassfish.jaxb.core.v2.TODO;

/**
 * 菌种信息 DO
 *
 * @author 芋道源码
 */
@TableName("strain_microbe_basic_info")
@KeySequence("strain_microbe_basic_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MicrobeBasicInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 初始编码
     */
    private String originalCode;
    /**
     * 编码
     */
    private String code;
    /**
     * 保存摄氏度
     */
    private Long temperature;
    /**
     * 用途
     */
    private String useage;
    /**
     * 中文名
     */
    private String chineseName;
    /**
     * 拉丁名
     */
    private String latinName;
    /**
     * 来源
     */
    private String source;
    /**
     * 文献
     */
    private String literatrue;
    /**
     * 基因登录号
     */
    private String geneAccessionNumber;
    /**
     * 菌种类型（使用字典类型）
     */
    private String microbeType;
    /**
     * 菌落形态
     */
    private String colonyMorphology;
    /**
     * 培养基id
     */
    private Long mediumId;
    /**
     * 有效期至
     */
    private LocalDateTime expirationDate;
    /**
     * 保存日期
     */
    private LocalDateTime saveDate;
    /**
     * 有效期天数
     */
    private Integer validityPeriodDays;
    /**
     * 是否致病（0否1是）
     *
     * 枚举 {@link TODO strain_yes_no 对应的类}
     */
    private Boolean isPathogenic;
    /**
     * 是否公开浏览 （0否1是）
     *
     * 枚举 {@link TODO strain_yes_no 对应的类}
     */
    private Boolean isVisiable;
    /**
     * 备注
     */
    private String remark;


    //保存方式
    private String storageMode;


    //菌种图片列表
    //形如 [1354985,165765465]
    private String microbeImages;

    //菌种附件说明
    // 形如 [1354985,165765465]
    private String  microbeAttachment;

    //菌体形态
    private String microbialMorphology;
}