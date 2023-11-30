package cn.iocoder.yudao.module.sale.service.customersalesdetail;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.customersalesdetail.CustomerSalesDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.customersalesdetail.CustomerSalesDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 客户销售明细 Service 实现类
 *
 * @author 播恩超级管理员
 */
@Service
@Validated
public class CustomerSalesDetailServiceImpl implements CustomerSalesDetailService {

    @Resource
    private CustomerSalesDetailMapper customerSalesDetailMapper;

    @Override
    public Long createCustomerSalesDetail(CustomerSalesDetailSaveReqVO createReqVO) {
        // 插入
        CustomerSalesDetailDO customerSalesDetail = BeanUtils.toBean(createReqVO, CustomerSalesDetailDO.class);
        customerSalesDetailMapper.insert(customerSalesDetail);
        // 返回
        return customerSalesDetail.getId();
    }

    @Override
    public void updateCustomerSalesDetail(CustomerSalesDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerSalesDetailExists(updateReqVO.getId());
        // 更新
        CustomerSalesDetailDO updateObj = BeanUtils.toBean(updateReqVO, CustomerSalesDetailDO.class);
        customerSalesDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomerSalesDetail(Long id) {
        // 校验存在
        validateCustomerSalesDetailExists(id);
        // 删除
        customerSalesDetailMapper.deleteById(id);
    }

    private void validateCustomerSalesDetailExists(Long id) {
        if (customerSalesDetailMapper.selectById(id) == null) {
            throw exception(CUSTOMER_SALES_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public CustomerSalesDetailDO getCustomerSalesDetail(Long id) {
        return customerSalesDetailMapper.selectById(id);
    }

    @Override
    public PageResult<CustomerSalesDetailDO> getCustomerSalesDetailPage(CustomerSalesDetailPageReqVO pageReqVO) {
        return customerSalesDetailMapper.selectPage(pageReqVO);
    }

}