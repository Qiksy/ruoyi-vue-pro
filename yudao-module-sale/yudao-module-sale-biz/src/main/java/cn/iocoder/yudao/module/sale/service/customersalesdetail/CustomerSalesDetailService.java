package cn.iocoder.yudao.module.sale.service.customersalesdetail;

import java.util.*;

import com.baomidou.dynamic.datasource.annotation.DS;
import jakarta.validation.*;
import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.customersalesdetail.CustomerSalesDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 客户销售明细 Service 接口
 *
 * @author 播恩超级管理员
 */
public interface CustomerSalesDetailService {

    /**
     * 创建客户销售明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomerSalesDetail(@Valid CustomerSalesDetailSaveReqVO createReqVO);

    /**
     * 更新客户销售明细
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomerSalesDetail(@Valid CustomerSalesDetailSaveReqVO updateReqVO);

    /**
     * 删除客户销售明细
     *
     * @param id 编号
     */
    void deleteCustomerSalesDetail(Long id);

    /**
     * 获得客户销售明细
     *
     * @param id 编号
     * @return 客户销售明细
     */
    CustomerSalesDetailDO getCustomerSalesDetail(Long id);

    /**
     * 获得客户销售明细分页
     *
     * @param pageReqVO 分页查询
     * @return 客户销售明细分页
     */
    PageResult<CustomerSalesDetailDO> getCustomerSalesDetailPage(CustomerSalesDetailPageReqVO pageReqVO);

    /**
     * 同步客户销售明细
     * @param syncReqVO
     */
    void syncCustomerSalesDetail(CustomerSalesDetailSyncReqVO syncReqVO);


    List<CustomerSalesDetailDO> getDoFromNc(CustomerSalesDetailSyncReqVO syncReqVO);

    PageResult<CustomerSalesDetailAnalysisRespVO> getCustomerSalesDetailAnalysisPage(CustomerSalesDetailPageReqVO pageReqVO);

    List<CustomerSalesDetailAnalysisRespVO> getCustomerSalesDetailAnalysisList(CustomerSalesDetailPageReqVO pageReqVO);
}