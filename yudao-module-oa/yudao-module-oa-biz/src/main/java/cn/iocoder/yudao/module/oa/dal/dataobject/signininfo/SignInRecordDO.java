package cn.iocoder.yudao.module.oa.dal.dataobject.signinrecord;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 签到记录 DO
 *
 * @author 超级管理员
 */
@TableName("oa_sign_in_record")
@KeySequence("oa_sign_in_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 位置信息
     */
    private String positionInfo;
    /**
     * WiFi信息
     */
    private String wifiInfo;
    /**
     * 行号
     */
    private Integer rw;
    /**
     * 活动id
     */
    private Long signInInfoId;
    /**
     * 扫码信息
     */
    private String scannerInfo;
    /**
     * IP地址
     */
    private String ipInfo;
    /**
     * 时间段ID
     */
    private Long rangeId;
    /**
     * 主表id
     */
    private Long parentId;

}