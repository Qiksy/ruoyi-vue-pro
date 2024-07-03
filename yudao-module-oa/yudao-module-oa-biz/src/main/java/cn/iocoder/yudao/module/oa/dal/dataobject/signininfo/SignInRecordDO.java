package cn.iocoder.yudao.module.oa.dal.dataobject.signininfo;

import cn.iocoder.yudao.framework.jackson.core.databind.TimestampLocalDateTimeDeserializer;
import cn.iocoder.yudao.framework.jackson.core.databind.TimestampLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

import java.time.LocalDate;

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

    private String userName;
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
     * 扫码信息
     */
    private String scannerInfo;
    /**
     * IP地址
     */
    private String ipInfo;

    /**
     * 签到日期
     */
    //配置序列化所使用的Serializer Deserializer
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate signDate;
    /**
     * 时间段ID
     */
    private Long rangeId;
    /**
     * 主表id
     */
    private Long parentId;

}