package cn.iocoder.yudao.module.system.api.user.dto;

import lombok.Data;

/**
 * 用来存储从nc查询出来的用户信息
 * @author linr
 * @since 2023/11/21 16:53
 */
@Data
public class AdminUserNcDTO {
    private String code;
    private String name;
    private String pkPsndoc;
    private String postName;
    private String deptcode;
}
