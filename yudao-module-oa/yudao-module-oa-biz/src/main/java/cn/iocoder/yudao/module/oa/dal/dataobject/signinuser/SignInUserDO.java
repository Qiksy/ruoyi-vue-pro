package cn.iocoder.yudao.module.oa.dal.dataobject.signinuser;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 会议参与成员 DO
 *
 * @author 超级管理员
 */
@TableName("oa_sign_in_user")
@KeySequence("oa_sign_in_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInUserDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 参与成员id
     */
    private Long userId;
    /**
     * 会议id
     */
    private Long meetingId;

}