package cn.iocoder.yudao.module.bpm.framework.flowable.core.behavior.script.impl.sale;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.module.bpm.enums.definition.BpmTaskRuleScriptEnum;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.behavior.script.BpmTaskAssignScript;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarningsub.DeclineWarningSubDO;
import cn.iocoder.yudao.module.sale.dal.mysql.declinewarningsub.DeclineWarningSubMapper;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用来获取科普员
 *
 * @author linr
 * @since 2023/12/26 10:58
 */
@Component
@Slf4j
public class BpmTaskAssignKPYScript implements BpmTaskAssignScript {

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private DeclineWarningSubMapper declineWarningSubMapper;


    /**
     * 基于执行任务，获得任务的候选用户们
     *
     * @param execution 执行任务
     * @return 候选人用户的编号数组
     */
    @Override
    public Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {
        //获取业务主键
        String processInstanceBusinessKey = execution.getProcessInstanceBusinessKey();
        //获取子表中的code
        LambdaQueryWrapperX<DeclineWarningSubDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(DeclineWarningSubDO::getParentId, processInstanceBusinessKey);


        List<DeclineWarningSubDO> subList = declineWarningSubMapper.selectList(queryWrapperX);

        List<String> codes = subList.stream().map(DeclineWarningSubDO::getEmployeeCode).toList();

        List<AdminUserRespDTO> userList = adminUserApi.getUserListByCodes(codes);

        return userList.stream().map(AdminUserRespDTO::getId).collect(Collectors.toSet());
    }

    /**
     * 获得枚举值
     *
     * @return 枚举值
     */
    @Override
    public BpmTaskRuleScriptEnum getEnum() {
        return BpmTaskRuleScriptEnum.KPY;
    }
}
