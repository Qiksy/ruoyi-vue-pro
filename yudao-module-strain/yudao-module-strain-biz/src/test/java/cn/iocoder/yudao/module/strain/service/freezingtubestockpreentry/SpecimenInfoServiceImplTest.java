package cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.specimeninfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.specimen.SpecimenInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.SpecimenInfoMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.springframework.context.annotation.Import;

import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link SpecimenInfoServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(SpecimenInfoServiceImpl.class)
public class SpecimenInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private SpecimenInfoServiceImpl freezingTubeStockPreEntryService;

    @Resource
    private SpecimenInfoMapper specimenInfoMapper;

    @Test
    public void testCreateSpecimenInfo_success() {
        // 准备参数
        SpecimenInfoSaveReqVO createReqVO = randomPojo(SpecimenInfoSaveReqVO.class).setId(null);

        // 调用
        freezingTubeStockPreEntryService.createSpecimenInfo(createReqVO);
        // 断言
//        assertNotNull(freezingTubeStockPreEntryId);
//        // 校验记录的属性是否正确
//        FreezingTubeStockPreEntryDO freezingTubeStockPreEntry = freezingTubeStockPreEntryMapper.selectById(freezingTubeStockPreEntryId);
//        assertPojoEquals(createReqVO, freezingTubeStockPreEntry, "id");
    }

    @Test
    public void testUpdateSpecimenInfo_success() {
        // mock 数据
        SpecimenInfoDO dbFreezingTubeStockPreEntry = randomPojo(SpecimenInfoDO.class);
        specimenInfoMapper.insert(dbFreezingTubeStockPreEntry);// @Sql: 先插入出一条存在的数据
        // 准备参数
        SpecimenInfoUpdateReqVO updateReqVO = randomPojo(SpecimenInfoUpdateReqVO.class, o -> {
            o.setId(dbFreezingTubeStockPreEntry.getId()); // 设置更新的 ID
        });

        // 调用
        freezingTubeStockPreEntryService.updateSpecimenInfo(updateReqVO);
        // 校验是否更新正确
        SpecimenInfoDO freezingTubeStockPreEntry = specimenInfoMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, freezingTubeStockPreEntry);
    }

    @Test
    public void testUpdateSpecimenInfo_notExists() {
        // 准备参数
        SpecimenInfoUpdateReqVO updateReqVO = randomPojo(SpecimenInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> freezingTubeStockPreEntryService.updateSpecimenInfo(updateReqVO), FREEZING_TUBE_STOCK_PRE_ENTRY_NOT_EXISTS);
    }

    @Test
    public void testDeleteSpecimenInfo_success() {
        // mock 数据
        SpecimenInfoDO dbFreezingTubeStockPreEntry = randomPojo(SpecimenInfoDO.class);
        specimenInfoMapper.insert(dbFreezingTubeStockPreEntry);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFreezingTubeStockPreEntry.getId();

        // 调用
        freezingTubeStockPreEntryService.deleteSpecimenInfo(id);
       // 校验数据不存在了
       assertNull(specimenInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteSpecimenInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> freezingTubeStockPreEntryService.deleteSpecimenInfo(id), FREEZING_TUBE_STOCK_PRE_ENTRY_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetSpecimenInfoPage() {
       // mock 数据
       SpecimenInfoDO dbFreezingTubeStockPreEntry = randomPojo(SpecimenInfoDO.class, o -> { // 等会查询到
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
       specimenInfoMapper.insert(dbFreezingTubeStockPreEntry);
       // 测试 code 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setCode(null)));
       // 测试 tubeId 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setTubeId(null)));
       // 测试 boxId 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setBoxId(null)));
       // 测试 tubePosition 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setTubePosition(null)));
       // 测试 tubePositionX 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setTubePositionX(null)));
       // 测试 tubePositionY 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setTubePositionY(null)));
       // 测试 generationNumber 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setGenerationNumber(null)));
       // 测试 thawFreezeCycleCount 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setThawFreezeCycleCount(null)));
       // 测试 deptId 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setDeptId(null)));
       // 测试 projectId 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setProjectId(null)));
       // 测试 microbeId 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setMicrobeId(null)));
       // 测试 expirationDate 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setExpirationDate(null)));
       // 测试 saveDate 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setSaveDate(null)));
       // 测试 saveBy 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setSaveBy(null)));
       // 测试 createTime 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setCreateTime(null)));
       // 测试 remark 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setRemark(null)));
       // 测试 status 不匹配
       specimenInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockPreEntry, o -> o.setStatus(null)));
       // 准备参数
       SpecimenInfoPageReqVO reqVO = new SpecimenInfoPageReqVO();
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
       PageResult<SpecimenInfoDO> pageResult = freezingTubeStockPreEntryService.getSpecimenInfo(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbFreezingTubeStockPreEntry, pageResult.getList().get(0));
    }

}