package cn.iocoder.yudao.module.bpm.framework.flowable.core.candidate.expression;

import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.Resource;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * 从变量中获取审批人
 * @author linr
 * @since 2024/5/6 21:22
 */
@Component
public class BpmTaskAssignMultiUserCodeExpression {


    @Resource
    private AdminUserApi adminUserApi;


    /**
     * 从变量中获取审批人
     * @param execution 流程执行实体
     * @param userCodes 用户编号
     * @return
     */
    public Set<Long> calculateUsers(DelegateExecution execution, List<String> userCodes) {
        List<AdminUserRespDTO> userListByCodes = adminUserApi.getUserListByCodes(userCodes);

        return userListByCodes.stream().map(AdminUserRespDTO::getId).collect(Collectors.toSet());
    }
}
