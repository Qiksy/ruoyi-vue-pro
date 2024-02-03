package cn.iocoder.yudao.module.strain.service.freezingdevicehierarchy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy.FreezingDeviceHierarchyMapper;
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
 * {@link FreezingDeviceHierarchyServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(FreezingDeviceHierarchyServiceImpl.class)
public class FreezingDeviceHierarchyServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FreezingDeviceHierarchyServiceImpl freezingDeviceHierarchyService;

    @Resource
    private FreezingDeviceHierarchyMapper freezingDeviceHierarchyMapper;

    @Test
    public void testCreateFreezingDeviceHierarchy_success() {
        // 准备参数
        FreezingDeviceHierarchyCreateReqVO reqVO = randomPojo(FreezingDeviceHierarchyCreateReqVO.class);

        // 调用
        Long freezingDeviceHierarchyId = freezingDeviceHierarchyService.createFreezingDeviceHierarchy(reqVO);
        // 断言
        assertNotNull(freezingDeviceHierarchyId);
        // 校验记录的属性是否正确
        FreezingDeviceHierarchyDO freezingDeviceHierarchy = freezingDeviceHierarchyMapper.selectById(freezingDeviceHierarchyId);
        assertPojoEquals(reqVO, freezingDeviceHierarchy);
    }

    @Test
    public void testUpdateFreezingDeviceHierarchy_success() {
        // mock 数据
        FreezingDeviceHierarchyDO dbFreezingDeviceHierarchy = randomPojo(FreezingDeviceHierarchyDO.class);
        freezingDeviceHierarchyMapper.insert(dbFreezingDeviceHierarchy);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FreezingDeviceHierarchyUpdateReqVO reqVO = randomPojo(FreezingDeviceHierarchyUpdateReqVO.class, o -> {
            o.setId(dbFreezingDeviceHierarchy.getId()); // 设置更新的 ID
        });

        // 调用
        freezingDeviceHierarchyService.updateFreezingDeviceHierarchy(reqVO);
        // 校验是否更新正确
        FreezingDeviceHierarchyDO freezingDeviceHierarchy = freezingDeviceHierarchyMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, freezingDeviceHierarchy);
    }

    @Test
    public void testUpdateFreezingDeviceHierarchy_notExists() {
        // 准备参数
        FreezingDeviceHierarchyUpdateReqVO reqVO = randomPojo(FreezingDeviceHierarchyUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> freezingDeviceHierarchyService.updateFreezingDeviceHierarchy(reqVO), FREEZING_DEVICE_HIERARCHY_NOT_EXISTS);
    }

    @Test
    public void testDeleteFreezingDeviceHierarchy_success() {
        // mock 数据
        FreezingDeviceHierarchyDO dbFreezingDeviceHierarchy = randomPojo(FreezingDeviceHierarchyDO.class);
        freezingDeviceHierarchyMapper.insert(dbFreezingDeviceHierarchy);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFreezingDeviceHierarchy.getId();

        // 调用
        freezingDeviceHierarchyService.deleteFreezingDeviceHierarchy(id);
       // 校验数据不存在了
       assertNull(freezingDeviceHierarchyMapper.selectById(id));
    }

    @Test
    public void testDeleteFreezingDeviceHierarchy_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> freezingDeviceHierarchyService.deleteFreezingDeviceHierarchy(id), FREEZING_DEVICE_HIERARCHY_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFreezingDeviceHierarchyPage() {
       // mock 数据
       FreezingDeviceHierarchyDO dbFreezingDeviceHierarchy = randomPojo(FreezingDeviceHierarchyDO.class, o -> { // 等会查询到
           o.setParentId(null);
           o.setFreezingDeviceId(null);
           o.setIsFinalLevel(null);
           o.setFreezingBoxId(null);
           o.setRemark(null);
           o.setLayerType(null);
       });
       freezingDeviceHierarchyMapper.insert(dbFreezingDeviceHierarchy);
       // 测试 parentId 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setParentId(null)));
       // 测试 freezingDeviceId 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setFreezingDeviceId(null)));
       // 测试 isFinalLevel 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setIsFinalLevel(null)));
       // 测试 freezingBoxId 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setFreezingBoxId(null)));
       // 测试 remark 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setRemark(null)));
       // 测试 layerType 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setLayerType(null)));
       // 准备参数
       FreezingDeviceHierarchyPageReqVO reqVO = new FreezingDeviceHierarchyPageReqVO();
       reqVO.setParentId(null);
       reqVO.setFreezingDeviceId(null);
       reqVO.setIsFinalLevel(null);
       reqVO.setFreezingBoxId(null);
       reqVO.setRemark(null);
       reqVO.setLayerType(null);

       // 调用
       PageResult<FreezingDeviceHierarchyDO> pageResult = freezingDeviceHierarchyService.getFreezingDeviceHierarchyPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbFreezingDeviceHierarchy, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFreezingDeviceHierarchyList() {
       // mock 数据
       FreezingDeviceHierarchyDO dbFreezingDeviceHierarchy = randomPojo(FreezingDeviceHierarchyDO.class, o -> { // 等会查询到
           o.setParentId(null);
           o.setFreezingDeviceId(null);
           o.setIsFinalLevel(null);
           o.setFreezingBoxId(null);
           o.setRemark(null);
           o.setLayerType(null);
       });
       freezingDeviceHierarchyMapper.insert(dbFreezingDeviceHierarchy);
       // 测试 parentId 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setParentId(null)));
       // 测试 freezingDeviceId 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setFreezingDeviceId(null)));
       // 测试 isFinalLevel 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setIsFinalLevel(null)));
       // 测试 freezingBoxId 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setFreezingBoxId(null)));
       // 测试 remark 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setRemark(null)));
       // 测试 layerType 不匹配
       freezingDeviceHierarchyMapper.insert(cloneIgnoreId(dbFreezingDeviceHierarchy, o -> o.setLayerType(null)));
       // 准备参数
       FreezingDeviceHierarchyExportReqVO reqVO = new FreezingDeviceHierarchyExportReqVO();
       reqVO.setParentId(null);
       reqVO.setFreezingDeviceId(null);
       reqVO.setIsFinalLevel(null);
       reqVO.setFreezingBoxId(null);
       reqVO.setRemark(null);
       reqVO.setLayerType(null);

       // 调用
       List<FreezingDeviceHierarchyDO> list = freezingDeviceHierarchyService.getFreezingDeviceHierarchyList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbFreezingDeviceHierarchy, list.get(0));
    }

}
