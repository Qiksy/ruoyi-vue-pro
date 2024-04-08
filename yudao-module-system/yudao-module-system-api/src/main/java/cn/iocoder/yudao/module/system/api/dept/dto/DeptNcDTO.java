package cn.iocoder.yudao.module.system.api.dept.dto;

import lombok.Data;

import java.util.List;

/**
 * 从
 * @author linr
 * @since 2023/12/19 14:36
 */
@Data
public class DeptNcDTO {


    private Long id;
    /**
     * 部门名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;
    /**
     * 父部门ID
     *
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 负责人
     *
     */
    private Long leaderUserId;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 部门状态
     *
     */
    private Integer status;


    /**
     * 负责人电话，这个用来确定负责人是谁
     */
    private String leaderUserPhone;

    /**
     * NC系统中的PK值
     */
    private String pkDept;

    /**
     * 父级的PK值
     */
    private String parentPkDept;


    List<DeptNcDTO> children;
}
