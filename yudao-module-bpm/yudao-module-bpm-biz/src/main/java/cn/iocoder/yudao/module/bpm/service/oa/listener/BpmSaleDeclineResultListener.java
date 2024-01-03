package cn.iocoder.yudao.module.bpm.service.oa.listener;

import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.sale.service.declinewarning.DeclineWarningService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BpmSaleDeclineResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    DeclineWarningService declineWarningService;

    /**
     * 流程的key
     */
    public static final String PROCESS_KEY = "sale-decline";

    /**
     * @return 返回监听的流程定义 Key
     */
    @Override
    protected String getProcessDefinitionKey() {
        return PROCESS_KEY;
    }

    /**
     * 处理事件
     *
     * @param event 事件
     */
    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        Long businessKey = Long.valueOf(event.getBusinessKey());
        Integer result = event.getResult();
        declineWarningService.updateResult(businessKey, result);
    }
}