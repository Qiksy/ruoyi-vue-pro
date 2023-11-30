package cn.iocoder.yudao.module.sale.service.customersalesdetail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.customersalesdetail.CustomerSalesDetailDO;
import cn.iocoder.yudao.module.sale.dal.mysql.customersalesdetail.CustomerSalesDetailMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.springframework.context.annotation.Import;

import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link CustomerSalesDetailServiceImpl} 的单元测试类
 *
 * @author 播恩超级管理员
 */
@Import(CustomerSalesDetailServiceImpl.class)
public class CustomerSalesDetailServiceImplTest extends BaseDbUnitTest {

    @Resource
    private CustomerSalesDetailServiceImpl customerSalesDetailService;

    @Resource
    private CustomerSalesDetailMapper customerSalesDetailMapper;

    @Test
    public void testCreateCustomerSalesDetail_success() {
        // 准备参数
        CustomerSalesDetailSaveReqVO createReqVO = randomPojo(CustomerSalesDetailSaveReqVO.class).setId(null);

        // 调用
        Long customerSalesDetailId = customerSalesDetailService.createCustomerSalesDetail(createReqVO);
        // 断言
        assertNotNull(customerSalesDetailId);
        // 校验记录的属性是否正确
        CustomerSalesDetailDO customerSalesDetail = customerSalesDetailMapper.selectById(customerSalesDetailId);
        assertPojoEquals(createReqVO, customerSalesDetail, "id");
    }

    @Test
    public void testUpdateCustomerSalesDetail_success() {
        // mock 数据
        CustomerSalesDetailDO dbCustomerSalesDetail = randomPojo(CustomerSalesDetailDO.class);
        customerSalesDetailMapper.insert(dbCustomerSalesDetail);// @Sql: 先插入出一条存在的数据
        // 准备参数
        CustomerSalesDetailSaveReqVO updateReqVO = randomPojo(CustomerSalesDetailSaveReqVO.class, o -> {
            o.setId(dbCustomerSalesDetail.getId()); // 设置更新的 ID
        });

        // 调用
        customerSalesDetailService.updateCustomerSalesDetail(updateReqVO);
        // 校验是否更新正确
        CustomerSalesDetailDO customerSalesDetail = customerSalesDetailMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, customerSalesDetail);
    }

    @Test
    public void testUpdateCustomerSalesDetail_notExists() {
        // 准备参数
        CustomerSalesDetailSaveReqVO updateReqVO = randomPojo(CustomerSalesDetailSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> customerSalesDetailService.updateCustomerSalesDetail(updateReqVO), CUSTOMER_SALES_DETAIL_NOT_EXISTS);
    }

    @Test
    public void testDeleteCustomerSalesDetail_success() {
        // mock 数据
        CustomerSalesDetailDO dbCustomerSalesDetail = randomPojo(CustomerSalesDetailDO.class);
        customerSalesDetailMapper.insert(dbCustomerSalesDetail);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbCustomerSalesDetail.getId();

        // 调用
        customerSalesDetailService.deleteCustomerSalesDetail(id);
       // 校验数据不存在了
       assertNull(customerSalesDetailMapper.selectById(id));
    }

    @Test
    public void testDeleteCustomerSalesDetail_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> customerSalesDetailService.deleteCustomerSalesDetail(id), CUSTOMER_SALES_DETAIL_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetCustomerSalesDetailPage() {
       // mock 数据
       CustomerSalesDetailDO dbCustomerSalesDetail = randomPojo(CustomerSalesDetailDO.class, o -> { // 等会查询到
           o.setCustomerCode(null);
           o.setCustomerName(null);
           o.setZoneCode(null);
           o.setZoneName(null);
           o.setAreaName(null);
           o.setAreaCode(null);
           o.setDeptCode(null);
           o.setDeptName(null);
           o.setEmployeePk(null);
           o.setEmployeeCode(null);
           o.setEmployeeName(null);
           o.setSaleMonth(null);
           o.setSaleDate(null);
           o.setDailySales(null);
           o.setMonthlyCumulativeSales(null);
           o.setCreateTime(null);
       });
       customerSalesDetailMapper.insert(dbCustomerSalesDetail);
       // 测试 customerCode 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setCustomerCode(null)));
       // 测试 customerName 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setCustomerName(null)));
       // 测试 zoneCode 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setZoneCode(null)));
       // 测试 zoneName 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setZoneName(null)));
       // 测试 areaName 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setAreaName(null)));
       // 测试 areaCode 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setAreaCode(null)));
       // 测试 deptCode 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setDeptCode(null)));
       // 测试 deptName 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setDeptName(null)));
       // 测试 employeePk 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setEmployeePk(null)));
       // 测试 employeeCode 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setEmployeeCode(null)));
       // 测试 employeeName 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setEmployeeName(null)));
       // 测试 yearMonth 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setSaleMonth(null)));
       // 测试 saleDate 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setSaleDate(null)));
       // 测试 dailySales 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setDailySales(null)));
       // 测试 monthlyCumulativeSales 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setMonthlyCumulativeSales(null)));
       // 测试 createTime 不匹配
       customerSalesDetailMapper.insert(cloneIgnoreId(dbCustomerSalesDetail, o -> o.setCreateTime(null)));
       // 准备参数
       CustomerSalesDetailPageReqVO reqVO = new CustomerSalesDetailPageReqVO();
       reqVO.setCustomerCode(null);
       reqVO.setCustomerName(null);
       reqVO.setZoneCode(null);
       reqVO.setZoneName(null);
       reqVO.setAreaName(null);
       reqVO.setAreaCode(null);
       reqVO.setDeptCode(null);
       reqVO.setDeptName(null);
       reqVO.setEmployeePk(null);
       reqVO.setEmployeeCode(null);
       reqVO.setEmployeeName(null);
       reqVO.setSaleMonth(null);
       reqVO.setSaleDate(null);
       reqVO.setDailySales(null);
       reqVO.setMonthlyCumulativeSales(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<CustomerSalesDetailDO> pageResult = customerSalesDetailService.getCustomerSalesDetailPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbCustomerSalesDetail, pageResult.getList().get(0));
    }

}