package cn.iocoder.yudao.module.strain.service.outboundsubapplication;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.outboundsubapplication.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundsubapplication.OutboundSubApplicationDO;
import cn.iocoder.yudao.module.strain.dal.mysql.outboundsubapplication.OutboundSubApplicationMapper;
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
 * {@link OutboundSubApplicationServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(OutboundSubApplicationServiceImpl.class)
public class OutboundSubApplicationServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OutboundSubApplicationServiceImpl outboundSubApplicationService;

    @Resource
    private OutboundSubApplicationMapper outboundSubApplicationMapper;

    @Test
    public void testCreateOutboundSubApplication_success() {
        // 准备参数
        OutboundSubApplicationSaveReqVO createReqVO = randomPojo(OutboundSubApplicationSaveReqVO.class).setId(null);

        // 调用
        Long outboundSubApplicationId = outboundSubApplicationService.createOutboundSubApplication(createReqVO);
        // 断言
        assertNotNull(outboundSubApplicationId);
        // 校验记录的属性是否正确
        OutboundSubApplicationDO outboundSubApplication = outboundSubApplicationMapper.selectById(outboundSubApplicationId);
        assertPojoEquals(createReqVO, outboundSubApplication, "id");
    }

    @Test
    public void testUpdateOutboundSubApplication_success() {
        // mock 数据
        OutboundSubApplicationDO dbOutboundSubApplication = randomPojo(OutboundSubApplicationDO.class);
        outboundSubApplicationMapper.insert(dbOutboundSubApplication);// @Sql: 先插入出一条存在的数据
        // 准备参数
        OutboundSubApplicationSaveReqVO updateReqVO = randomPojo(OutboundSubApplicationSaveReqVO.class, o -> {
            o.setId(dbOutboundSubApplication.getId()); // 设置更新的 ID
        });

        // 调用
        outboundSubApplicationService.updateOutboundSubApplication(updateReqVO);
        // 校验是否更新正确
        OutboundSubApplicationDO outboundSubApplication = outboundSubApplicationMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, outboundSubApplication);
    }

    @Test
    public void testUpdateOutboundSubApplication_notExists() {
        // 准备参数
        OutboundSubApplicationSaveReqVO updateReqVO = randomPojo(OutboundSubApplicationSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> outboundSubApplicationService.updateOutboundSubApplication(updateReqVO), OUTBOUND_SUB_APPLICATION_NOT_EXISTS);
    }

    @Test
    public void testDeleteOutboundSubApplication_success() {
        // mock 数据
        OutboundSubApplicationDO dbOutboundSubApplication = randomPojo(OutboundSubApplicationDO.class);
        outboundSubApplicationMapper.insert(dbOutboundSubApplication);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOutboundSubApplication.getId();

        // 调用
        outboundSubApplicationService.deleteOutboundSubApplication(id);
       // 校验数据不存在了
       assertNull(outboundSubApplicationMapper.selectById(id));
    }

    @Test
    public void testDeleteOutboundSubApplication_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> outboundSubApplicationService.deleteOutboundSubApplication(id), OUTBOUND_SUB_APPLICATION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOutboundSubApplicationPage() {
       // mock 数据
       OutboundSubApplicationDO dbOutboundSubApplication = randomPojo(OutboundSubApplicationDO.class, o -> { // 等会查询到
           o.setParentId(null);
           o.setTubeId(null);
           o.setCreateTime(null);
           o.setRemark(null);
       });
       outboundSubApplicationMapper.insert(dbOutboundSubApplication);
       // 测试 parentId 不匹配
       outboundSubApplicationMapper.insert(cloneIgnoreId(dbOutboundSubApplication, o -> o.setParentId(null)));
       // 测试 tubeId 不匹配
       outboundSubApplicationMapper.insert(cloneIgnoreId(dbOutboundSubApplication, o -> o.setTubeId(null)));
       // 测试 createTime 不匹配
       outboundSubApplicationMapper.insert(cloneIgnoreId(dbOutboundSubApplication, o -> o.setCreateTime(null)));
       // 测试 remark 不匹配
       outboundSubApplicationMapper.insert(cloneIgnoreId(dbOutboundSubApplication, o -> o.setRemark(null)));
       // 准备参数
       OutboundSubApplicationPageReqVO reqVO = new OutboundSubApplicationPageReqVO();
       reqVO.setParentId(null);
       reqVO.setTubeId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);

       // 调用
       PageResult<OutboundSubApplicationDO> pageResult = outboundSubApplicationService.getOutboundSubApplicationPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbOutboundSubApplication, pageResult.getList().get(0));
    }

}