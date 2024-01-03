package cn.iocoder.yudao.module.bpm.framework.flowable.core.listener;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 测试用的执行监听器
 * 可能会出现无法注入的问题
 * https://www.jianshu.com/p/aced758ad935
 * https://blog.csdn.net/qq_38374397/article/details/120286414
 * @author linr
 * @since 2023/12/26 12:12
 */
@Component
@Slf4j
public class DeclineStartListener implements TaskListener {

    @Resource
    private BpmProcessInstanceService processInstanceService;


    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private DeptApi deptApi;

    private static DeclineStartListener myListener;

    /**
     * 监听器中无法获取spring bean配置
     */
    @PostConstruct
    public void init() {
        myListener = this;
        myListener.processInstanceService = this.processInstanceService;
        myListener.adminUserApi = this.adminUserApi;
        myListener.deptApi = this.deptApi;
    }


    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance processInstance = myListener.processInstanceService.getProcessInstance(processInstanceId);
        Map<String, Object> variables = processInstance.getProcessVariables();
        //todo 这里准备通知各个战区总、大区总、科普员
        String kpyIds = (String) variables.get("kpyIds");

        String[] idArr = kpyIds.split(",");//其实是用户code

        //先通知对应的科普员，您有几个客户需要跟进，请及时与大区总沟通
        List<AdminUserRespDTO> userListByCodes = myListener.adminUserApi.getUserListByCodes(CollUtil.newArrayList(idArr));



        //其次通知对应的大区总，您有待处理的销售掉量预警，请及时处理
        Long areaCode = (Long) variables.get("areaCode");
        DeptRespDTO dept = myListener.deptApi.getDept(areaCode);
        Long leaderUserId = dept.getLeaderUserId();


        // 插入抄送表

    }

    // 审批界面的地址 https://saletool.bo-en.com/sale/decline-warning
}
