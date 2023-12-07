package cn.iocoder.yudao.module.sale.service.remote;

import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.CustomerSalesDetailSyncReqVO;
import cn.iocoder.yudao.module.sale.dal.dataobject.customersalesdetail.CustomerSalesDetailDO;


import java.util.List;

/**
 * 远程接口
 * @author linr
 * @since 2023/12/7 21:39
 */
public interface RemoteService {

    /**
     * 获取客户销售明细
     */
    List<CustomerSalesDetailDO> getCustomerSalesDetail(CustomerSalesDetailSyncReqVO reqVO);


}
