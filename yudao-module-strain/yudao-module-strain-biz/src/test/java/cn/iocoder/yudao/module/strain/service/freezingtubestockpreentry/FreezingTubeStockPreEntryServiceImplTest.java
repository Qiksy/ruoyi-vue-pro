package cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.FreezingTubeStockPreEntryMapper;
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
 * {@link FreezingTubeStockPreEntryServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(FreezingTubeStockPreEntryServiceImpl.class)
public class FreezingTubeStockPreEntryServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FreezingTubeStockPreEntryServiceImpl freezingTubeStockPreEntryService;

    @Resource
    private FreezingTubeStockPreEntryMapper freezingTubeStockPreEntryMapper;

    @Test
    public void testCreateFreezingTubeStockPreEntry_success() {
        // 准备参数
        FreezingTubeStockPreEntrySaveReqVO createReqVO = randomPojo(FreezingTubeStockPreEntrySaveReqVO.class).setId(null);

        // 调用
        freezingTubeStockPreEntryService.createFreezingTubeStockPreEntry(createReqVO);
        // 断言
//        assertNotNull(freezingTubeStockPreEntryId);
//        // 校验记录的属性是否正确
//        FreezingTubeStockPreEntryDO freezingTubeStockPreEntry = freezingTubeStockPreEntryMapper.selectById(freezingTubeStockPreEntryId);
//        assertPojoEquals(createReqVO, freezingTubeStockPreEntry, "id");
    }

    @Test
    public void testUpdateFreezingTubeStockPreEntry_success() {
        // mock 数据
        FreezingTubeStockPreEntryDO dbFreezingTubeStockPreEntry = randomPojo(FreezingTubeStockPreEntryDO.class);
        freezingTubeStockPreEntryMapper.insert(dbFreezingTubeStockPreEntry);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FreezingTubeStockPreEntryUpdateReqVO updateReqVO = randomPojo(FreezingTubeStockPreEntryUpdateReqVO.class, o -> {
            o.setId(dbFreezingTubeStockPreEntry.getId()); // 设置更新的 ID
        });

        // 调用
        freezingTubeStockPreEntryService.updateFreezingTubeStockPreEntry(updateReqVO);
        // 校验是否更新正确
        FreezingTubeStockPreEntryDO freezingTubeStockPreEntry = freezingTubeStockPreEntryMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, freezingTubeStockPreEntry);
    }

    @Test
    public void testUpdateFreezingTubeStockPreEntry_notExists() {
        // 准备参数
        FreezingTubeStockPreEntryUpdateReqVO updateReqVO = randomPojo(FreezingTubeStockPreEntryUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> freezingTubeStockPreEntryService.updateFreezingTubeStockPreEntry(updateReqVO), FREEZING_TUBE_STOCK_PRE_ENTRY_NOT_EXISTS);
    }

    @Test
    public void testDeleteFreezingTubeStockPreEntry_success() {
        // mock 数据
        FreezingTubeStockPreEntryDO dbFreezingTubeStockPreEntry = randomPojo(FreezingTubeStockPreEntryDO.class);
        freezingTubeStockPreEntryMapper.insert(dbFreezingTubeStockPreEntry);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFreezingTubeStockPreEntry.getId();

        // 调用
        freezingTubeStockPreEntryService.deleteFreezingTubeStockPreEntry(id);
       // 校验数据不存在了
       assertNull(freezingTubeStockPreEntryMapper.selectById(id));
    }

    @Test
    public void testDeleteFreezingTubeStockPreEntry_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> freezingTubeStockPreEntryService.deleteFreezingTubeStockPreEntry(id), FREEZING_TUBE_STOCK_PRE_ENTRY_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFreezingTubeStockPreEntryPage() {
       // mock 数据
       FreezingTubeStockPreEntryDO dbFreezingTubeStockPreEntry = randomPojo(FreezingTubeStockPreEntryDO.class, o -> { // 等会查询到
           o.setCode(null);
           o.setTubeId(null);
           o.setBoxId(null);
           o.setTubePosition(null);
           o.setTubePositionX(null);
           o.setTubePositionY(null);
           o.setGenerationNumber(null);
           o.setThawFreezeCycleCount(null);
           o.setDeptId(null);
           o.setProjectId(null);
           o.setMicrobeId(null);
           o.setExpirationDate(null);
           o.setSaveDate(null);
           o.setSaveBy(null);
           o.setCreateTime(null);
           o.setRemark(null);
           o.setStatus(null);
       });
       freezingTubeStockPreEntryMapper.insert(dbFreezingTubeStockPreEntry);
       // 测试 code 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setCode(null)));
       // 测试 tubeId 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setTubeId(null)));
       // 测试 boxId 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setBoxId(null)));
       // 测试 tubePosition 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setTubePosition(null)));
       // 测试 tubePositionX 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setTubePositionX(null)));
       // 测试 tubePositionY 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setTubePositionY(null)));
       // 测试 generationNumber 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setGenerationNumber(null)));
       // 测试 thawFreezeCycleCount 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setThawFreezeCycleCount(null)));
       // 测试 deptId 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setDeptId(null)));
       // 测试 projectId 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setProjectId(null)));
       // 测试 microbeId 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setMicrobeId(null)));
       // 测试 expirationDate 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setExpirationDate(null)));
       // 测试 saveDate 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setSaveDate(null)));
       // 测试 saveBy 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setSaveBy(null)));
       // 测试 createTime 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setCreateTime(null)));
       // 测试 remark 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setRemark(null)));
       // 测试 status 不匹配
       freezingTubeStockPreEntryMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setStatus(null)));
       // 准备参数
       FreezingTubeStockPreEntryPageReqVO reqVO = new FreezingTubeStockPreEntryPageReqVO();
       reqVO.setCode(null);
       reqVO.setTubeId(null);
       reqVO.setBoxId(null);
       reqVO.setTubePosition(null);
       reqVO.setTubePositionX(null);
       reqVO.setTubePositionY(null);
       reqVO.setGenerationNumber(null);
       reqVO.setThawFreezeCycleCount(null);
       reqVO.setDeptId(null);
       reqVO.setProjectId(null);
       reqVO.setMicrobeId(null);
       reqVO.setExpirationDate(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setSaveDate(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setSaveBy(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);
       reqVO.setStatus(null);

       // 调用
       PageResult<FreezingTubeStockPreEntryDO> pageResult = freezingTubeStockPreEntryService.getFreezingTubeStockPreEntryPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbFreezingTubeStockPreEntry, pageResult.getList().get(0));
    }

}