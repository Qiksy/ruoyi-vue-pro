package cn.iocoder.yudao.module.bpm.framework.flowable.core.candidate.expression;

import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.Resource;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 *
 * 多级部门审批人
 *
 * @author linr
 * @since 2024/5/6 20:15
 */
@Component
public class BpmTaskAssignMultiDeptLeaderExpression {

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;


    @Resource
    private BpmProcessInstanceService processInstanceService;


    /**
     * 按下面顺序计算审批人，如果第一个部门有负责人，则直接返回负责人，否则继续计算第二部门负责人，如果第二部门有负责人，则直接返回负责人，否则继续计算保底审批人
     * @param execution 流程执行实体
     * @param pk1 部门主键
     * @param pk2 部门主键
     * @param userCode 保底审批人
     * @return 用户编号
     */
    public Set<Long> calculateUsers(DelegateExecution execution, String pk1, String pk2, String userCode) {

        DeptRespDTO dept = deptApi.getDeptByPk(pk1);
        if (dept!=null && dept.getLeaderUserId()!=null){
            return Set.of(dept.getLeaderUserId());
        }

        dept = deptApi.getDeptByPk(pk2);
        if (dept!=null && dept.getLeaderUserId()!=null){
            return Set.of(dept.getLeaderUserId());
        }

        AdminUserRespDTO user = adminUserApi.getUserByCode(userCode);

        if (user!=null){
            return Set.of(user.getId());
        }

        throw new IllegalArgumentException("审批人计算失败");
    }
}
