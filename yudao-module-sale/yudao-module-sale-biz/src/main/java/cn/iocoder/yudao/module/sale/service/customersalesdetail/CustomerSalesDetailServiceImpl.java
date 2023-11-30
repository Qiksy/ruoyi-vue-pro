package cn.iocoder.yudao.module.sale.service.customersalesdetail;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.customersalesdetail.CustomerSalesDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
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
@Slf4j
public class CustomerSalesDetailServiceImpl implements CustomerSalesDetailService {

    @Autowired
    @Lazy
    private CustomerSalesDetailService detailService;

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

    /**
     * 同步客户销售明细
     *
     * @param syncReqVO
     */
    @Override
    public void syncCustomerSalesDetail(CustomerSalesDetailSyncReqVO syncReqVO) {
        // TODO 同步数据
        List<CustomerSalesDetailDO> list =  detailService.getDoFromNc(syncReqVO);
        log.info("同步客户销售明细数据大小：{}", list.size());
        // 插入或者更新
        for (CustomerSalesDetailDO detailDO : list) {
            LambdaQueryWrapper<CustomerSalesDetailDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CustomerSalesDetailDO::getCustomerCode, detailDO.getCustomerCode()) // 客户编码
                    .eq(CustomerSalesDetailDO::getZoneCode, detailDO.getZoneCode()) // 战区编码
                    .eq(CustomerSalesDetailDO::getAreaCode, detailDO.getAreaCode()) // 大区编码
                    .eq(CustomerSalesDetailDO::getSaleDate, detailDO.getSaleDate()); // 年月日
            //todo 等会验证这里是否会自动校验删除字段
            CustomerSalesDetailDO customerSalesDetailDO = customerSalesDetailMapper.selectOne(queryWrapper);
            if (customerSalesDetailDO == null) {
                // 插入
                customerSalesDetailMapper.insert(detailDO);
            } else {
                // 更新
                detailDO.setId(customerSalesDetailDO.getId());
                customerSalesDetailMapper.updateById(detailDO);
            }
        }

    }

    @Override
    @DS("nc65")
    public List<CustomerSalesDetailDO> getDoFromNc(CustomerSalesDetailSyncReqVO syncReqVO) {
        // 从NC获取数据
        return customerSalesDetailMapper.getDoFromNc(syncReqVO);
    }
}