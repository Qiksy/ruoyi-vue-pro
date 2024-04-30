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
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;
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
            //todo 获取负责人的逻辑改变 ： 如果没有战区总，就大区总兼任。如果没有大区总，劳诗晓兼任

            //大区负责人
            deptId = Long.valueOf(processVariables.get("areaCode").toString());
            deptPk = processVariables.get("areaPk").toString();
            log.debug("审批角色：大区负责人，获取的变量为：{}",processVariables);


            //获取部门

            DeptRespDTO dept = deptApi.getDeptByPk(deptPk);
            if (dept==null){
                dept = deptApi.getDept(deptId);
            }

            if (dept == null) { // 找不到发起人的部门，所以无法使用该规则
                throw exception(new ErrorCode(888,"部门无法找到"));
            }

            Long leaderUserId = dept.getLeaderUserId();
            if (leaderUserId == null) { // 找不到部门负责人的话，就找上级部门的负责人
                DeptRespDTO parentDept = deptApi.getDept(dept.getParentId());

                if (parentDept != null) { //  上级部门不为空的话，就找部门负责人
                    leaderUserId =  parentDept.getLeaderUserId();

                    if (leaderUserId == null) { // 上级部门负责人为空的话，就找上级部门的上级部门的负责人
                        ArrayList<String> codes = new ArrayList<>();
                        codes.add("000130");

                        List<AdminUserRespDTO> userListByCodes = adminUserApi.getUserListByCodes(codes);
                        AdminUserRespDTO adminUserRespDTO = userListByCodes.getFirst();
                        leaderUserId = adminUserRespDTO.getId();
                    }

                    return asSet(leaderUserId);

                }else {
                    // 上级部门为空的话，抛出异常
                    throw exception(new ErrorCode(888,"部门无法找到"));
                }


            }else {
                return asSet(dept.getLeaderUserId());
            }

        }else{
            log.debug("审批角色：战区负责人，获取的变量为：{}",processVariables);
            BpmProcessInstanceExtDO bpmProcessInstanceExtDO = processInstanceExtMapper.selectByProcessInstanceId(execution.getProcessInstanceId());
            processVariables = bpmProcessInstanceExtDO.getFormVariables();
            //部门负责人
            deptId = Long.valueOf(processVariables.get("zoneCode").toString());
            deptPk = processVariables.get("zonePk").toString();


            //如果战区为空的话，那就不到战区层级，大区自己审批了


            DeptRespDTO dept = deptApi.getDeptByPk(deptPk);
            if (dept==null){
                dept = deptApi.getDept(deptId);
            }

            if (dept == null) { // 找不到发起人的部门，所以无法使用该规则
                throw exception(new ErrorCode(888,"部门无法找到"));
            }

            Long leaderUserId = dept.getLeaderUserId();
            if (leaderUserId == null) { // 找不到部门负责人的话，就直接找大区负责人审批
                deptId = Long.valueOf(processVariables.get("areaCode").toString());
                deptPk = processVariables.get("areaPk").toString();

                DeptRespDTO dept2 = deptApi.getDeptByPk(deptPk);
                if (dept2==null){
                    dept2 = deptApi.getDept(deptId);
                }

                if (dept2 == null) { // 找不到发起人的部门，所以无法使用该规则
                    throw exception(new ErrorCode(888,"部门无法找到"));
                }

                Long leaderUserId2 = dept.getLeaderUserId();

                if (leaderUserId2==null){
                    //大区也是空的，直接给劳诗晓
                    ArrayList<String> codes = new ArrayList<>();
                    codes.add("000130");

                    List<AdminUserRespDTO> userListByCodes = adminUserApi.getUserListByCodes(codes);
                    AdminUserRespDTO adminUserRespDTO = userListByCodes.getFirst();

                    return asSet(adminUserRespDTO.getId());
                }else {
                    return asSet(leaderUserId2);
                }
            }else {
                return asSet(dept.getLeaderUserId());
            }

        }
    }
}
