package cn.iocoder.yudao.module.strain.service.freezingtubeinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubeinfo.FreezingTubeInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubeinfo.FreezingTubeInfoMapper;
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
 * {@link FreezingTubeInfoServiceImpl} 的单元测试类
 * <p>
 * 冻藏管信息的单元测试
 *
 * @author 芋道源码
 */
@Import(FreezingTubeInfoServiceImpl.class)
public class FreezingTubeInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FreezingTubeInfoServiceImpl freezingTubeInfoService;

    @Resource
    private FreezingTubeInfoMapper freezingTubeInfoMapper;

    @Test
    public void testCreateFreezingTubeInfo_success() {
        // 准备参数
        FreezingTubeInfoCreateReqVO reqVO = randomPojo(FreezingTubeInfoCreateReqVO.class);

        // 调用
        Long freezingTubeInfoId = freezingTubeInfoService.createFreezingTubeInfo(reqVO);
        // 断言
        assertNotNull(freezingTubeInfoId);
        // 校验记录的属性是否正确
        FreezingTubeInfoDO freezingTubeInfo = freezingTubeInfoMapper.selectById(freezingTubeInfoId);
        assertPojoEquals(reqVO, freezingTubeInfo, "createTime", "updateTime");
    }

    @Test
    public void testUpdateFreezingTubeInfo_success() {
        // mock 数据
        FreezingTubeInfoDO dbFreezingTubeInfo = randomPojo(FreezingTubeInfoDO.class);
        freezingTubeInfoMapper.insert(dbFreezingTubeInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FreezingTubeInfoUpdateReqVO reqVO = randomPojo(FreezingTubeInfoUpdateReqVO.class, o -> {
            o.setId(dbFreezingTubeInfo.getId()); // 设置更新的 ID
        });

        // 调用
        freezingTubeInfoService.updateFreezingTubeInfo(reqVO);
        // 校验是否更新正确
        FreezingTubeInfoDO freezingTubeInfo = freezingTubeInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, freezingTubeInfo);
    }

    @Test
    public void testUpdateFreezingTubeInfo_notExists() {
        // 准备参数
        FreezingTubeInfoUpdateReqVO reqVO = randomPojo(FreezingTubeInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> freezingTubeInfoService.updateFreezingTubeInfo(reqVO), FREEZING_TUBE_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteFreezingTubeInfo_success() {
        // mock 数据
        FreezingTubeInfoDO dbFreezingTubeInfo = randomPojo(FreezingTubeInfoDO.class);
        freezingTubeInfoMapper.insert(dbFreezingTubeInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFreezingTubeInfo.getId();

        // 调用
        freezingTubeInfoService.deleteFreezingTubeInfo(id);
        // 校验数据不存在了
        assertNull(freezingTubeInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteFreezingTubeInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> freezingTubeInfoService.deleteFreezingTubeInfo(id), FREEZING_TUBE_INFO_NOT_EXISTS);
    }

    @Test
    public void testGetFreezingTubeInfoPage() {
        // mock 数据
        FreezingTubeInfoDO dbFreezingTubeInfo = randomPojo(FreezingTubeInfoDO.class, o -> { // 等会查询到
            o.setCode("code");
            o.setName("name");
            o.setCapacity(10);
            o.setVolumeUnit("ml");
            o.setRemark("备注测试");
        });
        freezingTubeInfoMapper.insert(dbFreezingTubeInfo);
        // 测试 code 不匹配
        freezingTubeInfoMapper.insert(cloneIgnoreId(dbFreezingTubeInfo, o -> o.setCode("123456")));
        // 测试 name 不匹配
        freezingTubeInfoMapper.insert(cloneIgnoreId(dbFreezingTubeInfo, o -> o.setName("123456")));
        // 测试 capacity 不匹配
        freezingTubeInfoMapper.insert(cloneIgnoreId(dbFreezingTubeInfo, o -> o.setCapacity(50)));
        // 测试 volumeUnit 不匹配
        freezingTubeInfoMapper.insert(cloneIgnoreId(dbFreezingTubeInfo, o -> o.setVolumeUnit("L")));
        // 测试 remark 不匹配
        freezingTubeInfoMapper.insert(cloneIgnoreId(dbFreezingTubeInfo, o -> o.setRemark("45644")));
        // 准备参数
        FreezingTubeInfoPageReqVO reqVO = new FreezingTubeInfoPageReqVO();
        reqVO.setCode("code");
        reqVO.setName("name");
        reqVO.setCapacity(10);
        reqVO.setVolumeUnit("ml");
//        reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setRemark("备注测试");

        // 调用
        PageResult<FreezingTubeInfoDO> pageResult = freezingTubeInfoService.getFreezingTubeInfoPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbFreezingTubeInfo, pageResult.getList().getFirst());
    }

    @Test
    public void testGetFreezingTubeInfoList() {
        // mock 数据
        FreezingTubeInfoDO dbFreezingTubeInfo = randomPojo(FreezingTubeInfoDO.class, o -> { // 等会查询到
            o.setCode("code");
            o.setName("name");
            o.setCapacity(10);
            o.setVolumeUnit("ml");
            o.setRemark("备注测试");
        });
        freezingTubeInfoMapper.insert(dbFreezingTubeInfo);
        // 测试 code 不匹配
        freezingTubeInfoMapper.insert(cloneIgnoreId(dbFreezingTubeInfo, o -> o.setCode("123456")));
        // 测试 name 不匹配
        freezingTubeInfoMapper.insert(cloneIgnoreId(dbFreezingTubeInfo, o -> o.setName("123456")));
        // 测试 capacity 不匹配
        freezingTubeInfoMapper.insert(cloneIgnoreId(dbFreezingTubeInfo, o -> o.setCapacity(50)));
        // 测试 volumeUnit 不匹配
        freezingTubeInfoMapper.insert(cloneIgnoreId(dbFreezingTubeInfo, o -> o.setVolumeUnit("L")));
        // 测试 remark 不匹配
        freezingTubeInfoMapper.insert(cloneIgnoreId(dbFreezingTubeInfo, o -> o.setRemark("45644")));
        // 准备参数
        FreezingTubeInfoExportReqVO reqVO = new FreezingTubeInfoExportReqVO();
        reqVO.setCode("code");
        reqVO.setName("name");
        reqVO.setCapacity(10);
        reqVO.setVolumeUnit("ml");
//        reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setRemark("备注测试");

        // 调用
        List<FreezingTubeInfoDO> list = freezingTubeInfoService.getFreezingTubeInfoList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbFreezingTubeInfo, list.getFirst());
    }

}
