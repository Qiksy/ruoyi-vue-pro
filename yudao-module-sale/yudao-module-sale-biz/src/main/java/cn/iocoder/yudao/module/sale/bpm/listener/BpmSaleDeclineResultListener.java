package cn.iocoder.yudao.module.sale.bpm.listener;

import cn.iocoder.yudao.module.bpm.event.BpmProcessInstanceStatusEvent;
import cn.iocoder.yudao.module.bpm.event.BpmProcessInstanceStatusEventListener;
import cn.iocoder.yudao.module.sale.service.declinewarning.DeclineWarningService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BpmSaleDeclineResultListener extends BpmProcessInstanceStatusEventListener {

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
    protected void onEvent(BpmProcessInstanceStatusEvent event) {
        Long businessKey = Long.valueOf(event.getBusinessKey());
        Integer result = event.getStatus();
        declineWarningService.updateResult(businessKey, result);
    }
}