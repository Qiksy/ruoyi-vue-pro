package cn.iocoder.yudao.module.bpm.framework.flowable.core.behavior.script.impl.sale;

import cn.iocoder.yudao.module.bpm.enums.definition.BpmTaskRuleScriptEnum;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 大区总
 * @author linr
 * @since 2023/12/2 23:01
 */
@Component
@Slf4j
public class BpmTaskAssignDeptLeaderX1tScript extends BpmTaskAssignDeptLeaderAbstractScript {


    /**
     * 基于执行任务，获得任务的候选用户们
     *
     * @param execution 执行任务
     * @return 候选人用户的编号数组
     */
    @Override
    public Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {

        return calculateTaskCandidateUsers(execution, BpmTaskRuleScriptEnum.AREA_LEADER.getId());
    }

    /**
     * 获得枚举值
     *
     * @return 枚举值
     */
    @Override
    public BpmTaskRuleScriptEnum getEnum() {
        return BpmTaskRuleScriptEnum.AREA_LEADER;
    }
}
