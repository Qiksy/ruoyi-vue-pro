package cn.iocoder.yudao.module.sale.service.declinewarningsub;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.sale.controller.admin.declinewarningsub.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarningsub.DeclineWarningSubDO;
import cn.iocoder.yudao.module.sale.dal.mysql.declinewarningsub.DeclineWarningSubMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link DeclineWarningSubServiceImpl} 的单元测试类
 *
 * @author 播恩超级管理员
 */
@Import(DeclineWarningSubServiceImpl.class)
public class DeclineWarningSubServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DeclineWarningSubServiceImpl declineWarningSubService;

    @Resource
    private DeclineWarningSubMapper declineWarningSubMapper;

    @Test
    public void testCreateDeclineWarningSub_success() {
        // 准备参数
        DeclineWarningSubSaveReqVO createReqVO = randomPojo(DeclineWarningSubSaveReqVO.class).setId(null);

        // 调用
        Long declineWarningSubId = declineWarningSubService.createDeclineWarningSub(createReqVO);
        // 断言
        assertNotNull(declineWarningSubId);
        // 校验记录的属性是否正确
        DeclineWarningSubDO declineWarningSub = declineWarningSubMapper.selectById(declineWarningSubId);
        assertPojoEquals(createReqVO, declineWarningSub, "id");
    }

    @Test
    public void testUpdateDeclineWarningSub_success() {
        // mock 数据
        DeclineWarningSubDO dbDeclineWarningSub = randomPojo(DeclineWarningSubDO.class);
        declineWarningSubMapper.insert(dbDeclineWarningSub);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DeclineWarningSubSaveReqVO updateReqVO = randomPojo(DeclineWarningSubSaveReqVO.class, o -> {
            o.setId(dbDeclineWarningSub.getId()); // 设置更新的 ID
        });

        // 调用
        declineWarningSubService.updateDeclineWarningSub(updateReqVO);
        // 校验是否更新正确
        DeclineWarningSubDO declineWarningSub = declineWarningSubMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, declineWarningSub);
    }

    @Test
    public void testUpdateDeclineWarningSub_notExists() {
        // 准备参数
        DeclineWarningSubSaveReqVO updateReqVO = randomPojo(DeclineWarningSubSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> declineWarningSubService.updateDeclineWarningSub(updateReqVO), DECLINE_WARNING_SUB_NOT_EXISTS);
    }

    @Test
    public void testDeleteDeclineWarningSub_success() {
        // mock 数据
        DeclineWarningSubDO dbDeclineWarningSub = randomPojo(DeclineWarningSubDO.class);
        declineWarningSubMapper.insert(dbDeclineWarningSub);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDeclineWarningSub.getId();

        // 调用
        declineWarningSubService.deleteDeclineWarningSub(id);
       // 校验数据不存在了
       assertNull(declineWarningSubMapper.selectById(id));
    }

    @Test
    public void testDeleteDeclineWarningSub_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> declineWarningSubService.deleteDeclineWarningSub(id), DECLINE_WARNING_SUB_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDeclineWarningSubPage() {
       // mock 数据
       DeclineWarningSubDO dbDeclineWarningSub = randomPojo(DeclineWarningSubDO.class, o -> { // 等会查询到
           o.setCustomerName(null);
           o.setCustomerCode(null);
           o.setEmployeeName(null);
           o.setDeptName(null);
           o.setPreMonthSales(null);
           o.setCurrMonthSales(null);
           o.setDeclineRatio(null);
           o.setDeclineNum(null);
           o.setParentId(null);
           o.setCreateTime(null);
       });
       declineWarningSubMapper.insert(dbDeclineWarningSub);
       // 测试 customerName 不匹配
       declineWarningSubMapper.insert(cloneIgnoreId(dbDeclineWarningSub, o -> o.setCustomerName(null)));
       // 测试 customerCode 不匹配
       declineWarningSubMapper.insert(cloneIgnoreId(dbDeclineWarningSub, o -> o.setCustomerCode(null)));
       // 测试 employeeName 不匹配
       declineWarningSubMapper.insert(cloneIgnoreId(dbDeclineWarningSub, o -> o.setEmployeeName(null)));
       // 测试 deptName 不匹配
       declineWarningSubMapper.insert(cloneIgnoreId(dbDeclineWarningSub, o -> o.setDeptName(null)));
       // 测试 preMonthSales 不匹配
       declineWarningSubMapper.insert(cloneIgnoreId(dbDeclineWarningSub, o -> o.setPreMonthSales(null)));
       // 测试 currMonthSales 不匹配
       declineWarningSubMapper.insert(cloneIgnoreId(dbDeclineWarningSub, o -> o.setCurrMonthSales(null)));
       // 测试 declineRatio 不匹配
       declineWarningSubMapper.insert(cloneIgnoreId(dbDeclineWarningSub, o -> o.setDeclineRatio(null)));
       // 测试 declineNum 不匹配
       declineWarningSubMapper.insert(cloneIgnoreId(dbDeclineWarningSub, o -> o.setDeclineNum(null)));
       // 测试 parentId 不匹配
       declineWarningSubMapper.insert(cloneIgnoreId(dbDeclineWarningSub, o -> o.setParentId(null)));
       // 测试 createTime 不匹配
       declineWarningSubMapper.insert(cloneIgnoreId(dbDeclineWarningSub, o -> o.setCreateTime(null)));
       // 准备参数
       DeclineWarningSubPageReqVO reqVO = new DeclineWarningSubPageReqVO();
       reqVO.setCustomerName(null);
       reqVO.setCustomerCode(null);
       reqVO.setEmployeeName(null);
       reqVO.setDeptName(null);
       reqVO.setPreMonthSales(null);
       reqVO.setCurrMonthSales(null);
       reqVO.setDeclineRatio(null);
       reqVO.setDeclineNum(null);
       reqVO.setParentId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<DeclineWarningSubDO> pageResult = declineWarningSubService.getDeclineWarningSubPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbDeclineWarningSub, pageResult.getList().get(0));
    }

}