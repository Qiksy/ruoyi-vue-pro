package cn.iocoder.yudao.module.strain.service.freezingtubestockinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockinfo.FreezingTubeStockInfoMapper;
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
 * {@link FreezingTubeStockInfoServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(FreezingTubeStockInfoServiceImpl.class)
public class FreezingTubeStockInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FreezingTubeStockInfoServiceImpl freezingTubeStockInfoService;

    @Resource
    private FreezingTubeStockInfoMapper freezingTubeStockInfoMapper;

    @Test
    public void testCreateFreezingTubeStockInfo_success() {
        // 准备参数
        FreezingTubeStockInfoSaveReqVO createReqVO = randomPojo(FreezingTubeStockInfoSaveReqVO.class).setId(null);

        // 调用
        Long freezingTubeStockInfoId = freezingTubeStockInfoService.createFreezingTubeStockInfo(createReqVO);
        // 断言
        assertNotNull(freezingTubeStockInfoId);
        // 校验记录的属性是否正确
        FreezingTubeStockInfoDO freezingTubeStockInfo = freezingTubeStockInfoMapper.selectById(freezingTubeStockInfoId);
        assertPojoEquals(createReqVO, freezingTubeStockInfo, "id");
    }

    @Test
    public void testUpdateFreezingTubeStockInfo_success() {
        // mock 数据
        FreezingTubeStockInfoDO dbFreezingTubeStockInfo = randomPojo(FreezingTubeStockInfoDO.class);
        freezingTubeStockInfoMapper.insert(dbFreezingTubeStockInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FreezingTubeStockInfoSaveReqVO updateReqVO = randomPojo(FreezingTubeStockInfoSaveReqVO.class, o -> {
            o.setId(dbFreezingTubeStockInfo.getId()); // 设置更新的 ID
        });

        // 调用
        freezingTubeStockInfoService.updateFreezingTubeStockInfo(updateReqVO);
        // 校验是否更新正确
        FreezingTubeStockInfoDO freezingTubeStockInfo = freezingTubeStockInfoMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, freezingTubeStockInfo);
    }

    @Test
    public void testUpdateFreezingTubeStockInfo_notExists() {
        // 准备参数
        FreezingTubeStockInfoSaveReqVO updateReqVO = randomPojo(FreezingTubeStockInfoSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> freezingTubeStockInfoService.updateFreezingTubeStockInfo(updateReqVO), FREEZING_TUBE_STOCK_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteFreezingTubeStockInfo_success() {
        // mock 数据
        FreezingTubeStockInfoDO dbFreezingTubeStockInfo = randomPojo(FreezingTubeStockInfoDO.class);
        freezingTubeStockInfoMapper.insert(dbFreezingTubeStockInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFreezingTubeStockInfo.getId();

        // 调用
        freezingTubeStockInfoService.deleteFreezingTubeStockInfo(id);
       // 校验数据不存在了
       assertNull(freezingTubeStockInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteFreezingTubeStockInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> freezingTubeStockInfoService.deleteFreezingTubeStockInfo(id), FREEZING_TUBE_STOCK_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFreezingTubeStockInfoPage() {
       // mock 数据
       FreezingTubeStockInfoDO dbFreezingTubeStockInfo = randomPojo(FreezingTubeStockInfoDO.class, o -> { // 等会查询到
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
           o.setStatus(null);
           o.setSaveBy(null);
           o.setStockPreEntryId(null);
           o.setCreateTime(null);
           o.setRemark(null);
       });
       freezingTubeStockInfoMapper.insert(dbFreezingTubeStockInfo);
       // 测试 code 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setCode(null)));
       // 测试 tubeId 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setTubeId(null)));
       // 测试 boxId 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setBoxId(null)));
       // 测试 tubePosition 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setTubePosition(null)));
       // 测试 tubePositionX 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setTubePositionX(null)));
       // 测试 tubePositionY 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setTubePositionY(null)));
       // 测试 generationNumber 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setGenerationNumber(null)));
       // 测试 thawFreezeCycleCount 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setThawFreezeCycleCount(null)));
       // 测试 deptId 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setDeptId(null)));
       // 测试 projectId 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setProjectId(null)));
       // 测试 microbeId 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setMicrobeId(null)));
       // 测试 expirationDate 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setExpirationDate(null)));
       // 测试 saveDate 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setSaveDate(null)));
       // 测试 status 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setStatus(null)));
       // 测试 saveBy 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setSaveBy(null)));
       // 测试 stockPreEntryId 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setStockPreEntryId(null)));
       // 测试 createTime 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setCreateTime(null)));
       // 测试 remark 不匹配
       freezingTubeStockInfoMapper.insert(cloneIgnoreId(dbFreezingTubeStockInfo, o -> o.setRemark(null)));
       // 准备参数
       FreezingTubeStockInfoPageReqVO reqVO = new FreezingTubeStockInfoPageReqVO();
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
       reqVO.setStatus(null);
       reqVO.setSaveBy(null);
       reqVO.setStockPreEntryId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);

       // 调用
       PageResult<FreezingTubeStockInfoDO> pageResult = freezingTubeStockInfoService.getFreezingTubeStockInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbFreezingTubeStockInfo, pageResult.getList().get(0));
    }

}