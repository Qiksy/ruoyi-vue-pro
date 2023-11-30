package cn.iocoder.yudao.module.sale.dal.mysql.customersalesdetail;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.sale.dal.dataobject.customersalesdetail.CustomerSalesDetailDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.*;

/**
 * 客户销售明细 Mapper
 *
 * @author 播恩超级管理员
 */
@Mapper
public interface CustomerSalesDetailMapper extends BaseMapperX<CustomerSalesDetailDO> {

    default PageResult<CustomerSalesDetailDO> selectPage(CustomerSalesDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CustomerSalesDetailDO>()
                .eqIfPresent(CustomerSalesDetailDO::getCustomerCode, reqVO.getCustomerCode())
                .likeIfPresent(CustomerSalesDetailDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(CustomerSalesDetailDO::getZoneCode, reqVO.getZoneCode())
                .likeIfPresent(CustomerSalesDetailDO::getZoneName, reqVO.getZoneName())
                .eqIfPresent(CustomerSalesDetailDO::getAreaName, reqVO.getAreaName())
                .likeIfPresent(CustomerSalesDetailDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(CustomerSalesDetailDO::getDeptCode, reqVO.getDeptCode())
                .likeIfPresent(CustomerSalesDetailDO::getDeptName, reqVO.getDeptName())
                .eqIfPresent(CustomerSalesDetailDO::getEmployeePk, reqVO.getEmployeePk())
                .eqIfPresent(CustomerSalesDetailDO::getEmployeeCode, reqVO.getEmployeeCode())
                .likeIfPresent(CustomerSalesDetailDO::getEmployeeName, reqVO.getEmployeeName())
                .eqIfPresent(CustomerSalesDetailDO::getSaleMonth, reqVO.getSaleMonth())
                .eqIfPresent(CustomerSalesDetailDO::getSaleDate, reqVO.getSaleDate())
                .eqIfPresent(CustomerSalesDetailDO::getDailySales, reqVO.getDailySales())
                .eqIfPresent(CustomerSalesDetailDO::getMonthlyCumulativeSales, reqVO.getMonthlyCumulativeSales())
                .betweenIfPresent(CustomerSalesDetailDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CustomerSalesDetailDO::getId));
    }

    List<CustomerSalesDetailDO> getDoFromNc(CustomerSalesDetailSyncReqVO syncReqVO);
}