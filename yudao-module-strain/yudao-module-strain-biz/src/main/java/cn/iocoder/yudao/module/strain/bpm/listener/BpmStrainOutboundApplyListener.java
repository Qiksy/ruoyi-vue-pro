package cn.iocoder.yudao.module.strain.bpm.listener;

import cn.iocoder.yudao.module.bpm.enums.task.BpmTaskStatusEnum;
import cn.iocoder.yudao.module.bpm.event.BpmProcessInstanceStatusEvent;
import cn.iocoder.yudao.module.bpm.event.BpmProcessInstanceStatusEventListener;
import cn.iocoder.yudao.module.strain.api.OutboundApplicationApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 菌种出库申请流程监听器
 * @author linr
 * @since 2024/5/22 下午4:33
 */
@Component
public class BpmStrainOutboundApplyListener extends BpmProcessInstanceStatusEventListener {

    @Resource
    private OutboundApplicationApi outboundApplicationApi;

    public static final String OUTBOUND_PROCESS_KEY = "strain-outbound";

    /**
     * @return 返回监听的流程定义 Key
     */
    @Override
    protected String getProcessDefinitionKey() {
        return OUTBOUND_PROCESS_KEY;
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
        outboundApplicationApi.updateResult(businessKey, result);

        if(BpmTaskStatusEnum.APPROVE.getStatus().equals(result)){
            //如果是审批通过了，需要更新菌种库的相关数据
            outboundApplicationApi.updateStrainStock(businessKey);
        }
    }
}
