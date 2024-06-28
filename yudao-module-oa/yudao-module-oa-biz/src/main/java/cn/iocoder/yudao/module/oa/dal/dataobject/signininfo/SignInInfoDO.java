package cn.iocoder.yudao.module.oa.dal.dataobject.signininfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 会议签到 DO
 *
 * @author 超级管理员
 */
@TableName("oa_sign_in_info")
@KeySequence("oa_sign_in_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 封面图片id
     */
    private Long coverPicId;
    /**
     * 封面图片URL
     */
    private String coverPicUrl;
    /**
     * 标题
     */
    private String title;
    /**
     * 说明
     */
    private String description;
    /**
     * 开始时间
     */
    private LocalDateTime startDate;
    /**
     * 结束时间
     */
    private LocalDateTime endDate;
    /**
     * 签到时间类型 0全天 1自定义
     */
    private String signInTimeType;
    /**
     * 首次签到是否需要填写个人信息 0否1是
     */
    private Boolean personInfoNeed;
    /**
     * 是否需要连接指定位置才可以 0否1是
     */
    private Boolean positionNeed;
    /**
     * 位置信息 实际json数据
     */
    private String positionInfo;
    /**
     * 是否必须扫码签到
     */
    private Boolean scannerNeed;
    /**
     * 签到大屏背景图
     */
    private Long bannerId;
    /**
     * 签到大屏背景图 url
     */
    private String bannerUrl;
    /**
     * logo_id
     */
    private Long logoId;
    /**
     * logo 地址
     */
    private String logoUrl;
    /**
     * 标题图片
     */
    private String titlePicUrl;
    /**
     * 标题图片id
     */
    private Long titlePicId;
    /**
     * 需要签到多少次(天数*时间范围)
     */
    private Integer signTaskCount;

}