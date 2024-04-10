package cn.iocoder.yudao.module.bpm.framework.flowable.core.behavior.script.impl.sale;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.module.bpm.dal.dataobject.task.BpmProcessInstanceExtDO;
import cn.iocoder.yudao.module.bpm.dal.mysql.task.BpmProcessInstanceExtMapper;
import cn.iocoder.yudao.module.bpm.enums.definition.BpmTaskRuleScriptEnum;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.behavior.script.BpmTaskAssignScript;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;

import java.util.Map;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.SetUtils.asSet;

@Slf4j
public abstract class BpmTaskAssignDeptLeaderAbstractScript implements BpmTaskAssignScript {

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;
    @Resource
    @Lazy // 解决循环依赖
    private BpmProcessInstanceService bpmProcessInstanceService;

    @Resource
    private BpmProcessInstanceExtMapper processInstanceExtMapper;

    /**
     * @param execution
     * @param enumValue 参见 {@link BpmTaskRuleScriptEnum}
     * @return
     */
    protected Set<Long> calculateTaskCandidateUsers(DelegateExecution execution, Long enumValue) {
        // 获得部门id
        Long deptId;
        ProcessInstance processInstance = bpmProcessInstanceService.getProcessInstance(execution.getProcessInstanceId());

        // todo 获取扩展表
//        BpmProcessInstanceExtDO bpmProcessInstanceExtDO = processInstanceExtMapper.selectByProcessInstanceId(execution.getProcessInstanceId());
//        Map<String, Object> processVariables = bpmProcessInstanceExtDO.getFormVariables();
        Map<String, Object> processVariables = processInstance.getProcessVariables();

        String deptPk;
        if (enumValue.equals(BpmTaskRuleScriptEnum.AREA_LEADER.getId())){
            //大区负责人
            deptId = Long.valueOf(processVariables.get("areaCode").toString());
            deptPk = processVariables.get("areaPk").toString();
            log.debug("审批角色：大区负责人，获取的变量为：{}",processVariables);
        }else{
            log.debug("审批角色：部门负责人，获取的变量为：{}",processVariables);
            BpmProcessInstanceExtDO bpmProcessInstanceExtDO = processInstanceExtMapper.selectByProcessInstanceId(execution.getProcessInstanceId());
            processVariables = bpmProcessInstanceExtDO.getFormVariables();
            //部门负责人
            deptId = Long.valueOf(processVariables.get("zoneCode").toString());
            deptPk = processVariables.get("zonePk").toString();
        }

        // 获得部门负责人

        DeptRespDTO dept = deptApi.getDeptByPk(deptPk);
        if (dept==null){
            dept = deptApi.getDept(deptId);
        }
        if (dept == null) { // 找不到发起人的部门，所以无法使用该规则
            throw exception(new ErrorCode(888,"部门无法找到"));
        }

        Long leaderUserId = dept.getLeaderUserId();
        if (leaderUserId == null) { // 找不到部门负责人，所以无法使用该规则
            throw exception(new ErrorCode(888,"部门负责人无法找到"));
        }

        return asSet(dept.getLeaderUserId());
    }
}
