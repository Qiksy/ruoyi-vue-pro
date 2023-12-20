package cn.iocoder.yudao.module.sale.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import org.springframework.stereotype.Component;

/**
 * 同步产品线、销售分类、物料分类、物料基本信息的定时任务
 * @author linr
 * @since 2023/12/20 10:54
 */
@Component
public class MaterialSyncJob  implements JobHandler {
    /**
     * 执行任务
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public String execute(String param) throws Exception {
        return null;
    }
}
