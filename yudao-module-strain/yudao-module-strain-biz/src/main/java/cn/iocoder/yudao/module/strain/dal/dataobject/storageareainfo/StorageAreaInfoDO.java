package cn.iocoder.yudao.module.strain.dal.dataobject.storageareainfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 存放区域信息 DO
 *
 * @author 芋道源码
 */
@TableName("strain_storage_area_info")
@KeySequence("strain_storage_area_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageAreaInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 编号
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 位置信息
     */
    private String locationInfo;
    /**
     * 备注
     */
    private String remark;

}
