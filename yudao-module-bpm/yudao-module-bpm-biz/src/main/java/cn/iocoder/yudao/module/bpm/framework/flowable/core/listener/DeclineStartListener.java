package cn.iocoder.yudao.module.bpm.framework.flowable.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * 测试用的执行监听器
 * @author linr
 * @since 2023/12/26 12:12
 */
@Component
@Slf4j
public class DeclineStartListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        log.info("DeclineStartListener，这里已经被调用了");
        String processInstanceBusinessKey = execution.getProcessInstanceBusinessKey();
        //todo 这里准备插入一个抄送表，实现这个表的所有人，都可以进行查看对应的流程
    }
}
