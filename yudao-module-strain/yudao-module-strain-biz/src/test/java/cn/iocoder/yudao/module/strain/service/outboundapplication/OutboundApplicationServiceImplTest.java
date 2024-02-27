package cn.iocoder.yudao.module.strain.service.outboundapplication;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundapplication.OutboundApplicationDO;
import cn.iocoder.yudao.module.strain.dal.mysql.outboundapplication.OutboundApplicationMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link OutboundApplicationServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(OutboundApplicationServiceImpl.class)
public class OutboundApplicationServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OutboundApplicationServiceImpl outboundApplicationService;

    @Resource
    private OutboundApplicationMapper outboundApplicationMapper;

    @Test
    public void testCreateOutboundApplication_success() {
        // 准备参数
        OutboundApplicationCreateReqVO createReqVO = randomPojo(OutboundApplicationCreateReqVO.class);

        // 调用
        Long outboundApplicationId = outboundApplicationService.createOutboundApplication(createReqVO);
        // 断言
        assertNotNull(outboundApplicationId);
        // 校验记录的属性是否正确
        OutboundApplicationDO outboundApplication = outboundApplicationMapper.selectById(outboundApplicationId);
        assertPojoEquals(createReqVO, outboundApplication, "id");
    }

    @Test
    public void testUpdateOutboundApplication_success() {
        // mock 数据
        OutboundApplicationDO dbOutboundApplication = randomPojo(OutboundApplicationDO.class);
        outboundApplicationMapper.insert(dbOutboundApplication);// @Sql: 先插入出一条存在的数据
        // 准备参数
        OutboundApplicationSaveReqVO updateReqVO = randomPojo(OutboundApplicationSaveReqVO.class, o -> {
            o.setId(dbOutboundApplication.getId()); // 设置更新的 ID
        });

        // 调用
        outboundApplicationService.updateOutboundApplication(updateReqVO);
        // 校验是否更新正确
        OutboundApplicationDO outboundApplication = outboundApplicationMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, outboundApplication);
    }

    @Test
    public void testUpdateOutboundApplication_notExists() {
        // 准备参数
        OutboundApplicationSaveReqVO updateReqVO = randomPojo(OutboundApplicationSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> outboundApplicationService.updateOutboundApplication(updateReqVO), OUTBOUND_APPLICATION_NOT_EXISTS);
    }

    @Test
    public void testDeleteOutboundApplication_success() {
        // mock 数据
        OutboundApplicationDO dbOutboundApplication = randomPojo(OutboundApplicationDO.class);
        outboundApplicationMapper.insert(dbOutboundApplication);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOutboundApplication.getId();

        // 调用
        outboundApplicationService.deleteOutboundApplication(id);
       // 校验数据不存在了
       assertNull(outboundApplicationMapper.selectById(id));
    }

    @Test
    public void testDeleteOutboundApplication_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> outboundApplicationService.deleteOutboundApplication(id), OUTBOUND_APPLICATION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOutboundApplicationPage() {
       // mock 数据
       OutboundApplicationDO dbOutboundApplication = randomPojo(OutboundApplicationDO.class, o -> { // 等会查询到
           o.setCode(null);
           o.setApplicant(null);
           o.setUseage(null);
           o.setIsRestocked(null);
           o.setType(null);
           o.setProcessInstanceId(null);
           o.setApproResult(null);
       });
       outboundApplicationMapper.insert(dbOutboundApplication);
       // 测试 code 不匹配
       outboundApplicationMapper.insert(cloneIgnoreId(dbOutboundApplication, o -> o.setCode(null)));
       // 测试 applicant 不匹配
       outboundApplicationMapper.insert(cloneIgnoreId(dbOutboundApplication, o -> o.setApplicant(null)));
       // 测试 useage 不匹配
       outboundApplicationMapper.insert(cloneIgnoreId(dbOutboundApplication, o -> o.setUseage(null)));
       // 测试 isRestocked 不匹配
       outboundApplicationMapper.insert(cloneIgnoreId(dbOutboundApplication, o -> o.setIsRestocked(null)));
       // 测试 type 不匹配
       outboundApplicationMapper.insert(cloneIgnoreId(dbOutboundApplication, o -> o.setType(null)));
       // 测试 processInstanceId 不匹配
       outboundApplicationMapper.insert(cloneIgnoreId(dbOutboundApplication, o -> o.setProcessInstanceId(null)));
       // 测试 approResult 不匹配
       outboundApplicationMapper.insert(cloneIgnoreId(dbOutboundApplication, o -> o.setApproResult(null)));
       // 准备参数
       OutboundApplicationPageReqVO reqVO = new OutboundApplicationPageReqVO();
       reqVO.setCode(null);
       reqVO.setApplicant(null);
       reqVO.setUseage(null);
       reqVO.setIsRestocked(null);
       reqVO.setType(null);
       reqVO.setProcessInstanceId(null);
       reqVO.setApproResult(null);

       // 调用
       PageResult<OutboundApplicationDO> pageResult = outboundApplicationService.getOutboundApplicationPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbOutboundApplication, pageResult.getList().get(0));
    }

}