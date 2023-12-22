package cn.iocoder.yudao.module.sale.job;


import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.CustomerSalesDetailSyncReqVO;
import cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo.DeclineWarningGenerateReqVO;
import cn.iocoder.yudao.module.sale.service.customersalesdetail.CustomerSalesDetailService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 这里每天进行同步销售明细
 * 时间在每天的凌晨四点
 * 同步昨天的数据
 * @author linr
 * @since 2023/12/22 9:31
 */
@Component
@Slf4j
public class SaleDetailSyncJob implements JobHandler {

    @Resource
    private CustomerSalesDetailService service;


    /**
     * 执行任务
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public String execute(String param) throws Exception {


        //获取昨天的日期，yyyy-MM-dd格式
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        String yesterdayStr = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        CustomerSalesDetailSyncReqVO customerSalesDetailSyncReqVO = new CustomerSalesDetailSyncReqVO();
        customerSalesDetailSyncReqVO.setTimeRange(new String[]{yesterdayStr, yesterdayStr});

        service.syncCustomerSalesDetail(customerSalesDetailSyncReqVO);

        return null;
    }
}
