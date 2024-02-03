package cn.iocoder.yudao.module.strain.service.freezingdeviceinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdeviceinfo.FreezingDeviceInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdeviceinfo.FreezingDeviceInfoMapper;
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
 * {@link FreezingDeviceInfoServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(FreezingDeviceInfoServiceImpl.class)
public class FreezingDeviceInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FreezingDeviceInfoServiceImpl freezingDeviceInfoService;

    @Resource
    private FreezingDeviceInfoMapper freezingDeviceInfoMapper;

    @Test
    public void testCreateFreezingDeviceInfo_success() {
        // 准备参数
        FreezingDeviceInfoCreateReqVO reqVO = randomPojo(FreezingDeviceInfoCreateReqVO.class);

        // 调用
        Long freezingDeviceInfoId = freezingDeviceInfoService.createFreezingDeviceInfo(reqVO);
        // 断言
        assertNotNull(freezingDeviceInfoId);
        // 校验记录的属性是否正确
        FreezingDeviceInfoDO freezingDeviceInfo = freezingDeviceInfoMapper.selectById(freezingDeviceInfoId);
        assertPojoEquals(reqVO, freezingDeviceInfo);
    }

    @Test
    public void testUpdateFreezingDeviceInfo_success() {
        // mock 数据
        FreezingDeviceInfoDO dbFreezingDeviceInfo = randomPojo(FreezingDeviceInfoDO.class);
        freezingDeviceInfoMapper.insert(dbFreezingDeviceInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FreezingDeviceInfoUpdateReqVO reqVO = randomPojo(FreezingDeviceInfoUpdateReqVO.class, o -> {
            o.setId(dbFreezingDeviceInfo.getId()); // 设置更新的 ID
        });

        // 调用
        freezingDeviceInfoService.updateFreezingDeviceInfo(reqVO);
        // 校验是否更新正确
        FreezingDeviceInfoDO freezingDeviceInfo = freezingDeviceInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, freezingDeviceInfo);
    }

    @Test
    public void testUpdateFreezingDeviceInfo_notExists() {
        // 准备参数
        FreezingDeviceInfoUpdateReqVO reqVO = randomPojo(FreezingDeviceInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> freezingDeviceInfoService.updateFreezingDeviceInfo(reqVO), FREEZING_DEVICE_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteFreezingDeviceInfo_success() {
        // mock 数据
        FreezingDeviceInfoDO dbFreezingDeviceInfo = randomPojo(FreezingDeviceInfoDO.class);
        freezingDeviceInfoMapper.insert(dbFreezingDeviceInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFreezingDeviceInfo.getId();

        // 调用
        freezingDeviceInfoService.deleteFreezingDeviceInfo(id);
       // 校验数据不存在了
       assertNull(freezingDeviceInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteFreezingDeviceInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> freezingDeviceInfoService.deleteFreezingDeviceInfo(id), FREEZING_DEVICE_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFreezingDeviceInfoPage() {
       // mock 数据
       FreezingDeviceInfoDO dbFreezingDeviceInfo = randomPojo(FreezingDeviceInfoDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setCode(null);
           o.setType(null);
           o.setTemperature(null);
           o.setCreateTime(null);
           o.setRemark(null);
       });
       freezingDeviceInfoMapper.insert(dbFreezingDeviceInfo);
       // 测试 name 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setName(null)));
       // 测试 code 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setCode(null)));
       // 测试 type 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setType(null)));
       // 测试 temperature 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setTemperature(null)));
       // 测试 createTime 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setCreateTime(null)));
       // 测试 remark 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setRemark(null)));
       // 准备参数
       FreezingDeviceInfoPageReqVO reqVO = new FreezingDeviceInfoPageReqVO();
       reqVO.setName(null);
       reqVO.setCode(null);
       reqVO.setType(null);
       reqVO.setTemperature(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);

       // 调用
       PageResult<FreezingDeviceInfoDO> pageResult = freezingDeviceInfoService.getFreezingDeviceInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbFreezingDeviceInfo, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetFreezingDeviceInfoList() {
       // mock 数据
       FreezingDeviceInfoDO dbFreezingDeviceInfo = randomPojo(FreezingDeviceInfoDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setCode(null);
           o.setType(null);
           o.setTemperature(null);
           o.setCreateTime(null);
           o.setRemark(null);
       });
       freezingDeviceInfoMapper.insert(dbFreezingDeviceInfo);
       // 测试 name 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setName(null)));
       // 测试 code 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setCode(null)));
       // 测试 type 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setType(null)));
       // 测试 temperature 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setTemperature(null)));
       // 测试 createTime 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setCreateTime(null)));
       // 测试 remark 不匹配
       freezingDeviceInfoMapper.insert(cloneIgnoreId(dbFreezingDeviceInfo, o -> o.setRemark(null)));
       // 准备参数
       FreezingDeviceInfoExportReqVO reqVO = new FreezingDeviceInfoExportReqVO();
       reqVO.setName(null);
       reqVO.setCode(null);
       reqVO.setType(null);
       reqVO.setTemperature(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);

       // 调用
       List<FreezingDeviceInfoDO> list = freezingDeviceInfoService.getFreezingDeviceInfoList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbFreezingDeviceInfo, list.get(0));
    }

}
