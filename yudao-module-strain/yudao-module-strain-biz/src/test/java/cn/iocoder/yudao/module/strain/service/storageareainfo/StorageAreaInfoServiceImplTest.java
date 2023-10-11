package cn.iocoder.yudao.module.strain.service.storageareainfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.storageareainfo.StorageAreaInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.storageareainfo.StorageAreaInfoMapper;
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
 * {@link StorageAreaInfoServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(StorageAreaInfoServiceImpl.class)
public class StorageAreaInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private StorageAreaInfoServiceImpl storageAreaInfoService;

    @Resource
    private StorageAreaInfoMapper storageAreaInfoMapper;

    @Test
    public void testCreateStorageAreaInfo_success() {
        // 准备参数
        StorageAreaInfoCreateReqVO reqVO = randomPojo(StorageAreaInfoCreateReqVO.class);

        // 调用
        Long storageAreaInfoId = storageAreaInfoService.createStorageAreaInfo(reqVO);
        // 断言
        assertNotNull(storageAreaInfoId);
        // 校验记录的属性是否正确
        StorageAreaInfoDO storageAreaInfo = storageAreaInfoMapper.selectById(storageAreaInfoId);
        assertPojoEquals(reqVO, storageAreaInfo);
    }

    @Test
    public void testUpdateStorageAreaInfo_success() {
        // mock 数据
        StorageAreaInfoDO dbStorageAreaInfo = randomPojo(StorageAreaInfoDO.class);
        storageAreaInfoMapper.insert(dbStorageAreaInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        StorageAreaInfoUpdateReqVO reqVO = randomPojo(StorageAreaInfoUpdateReqVO.class, o -> {
            o.setId(dbStorageAreaInfo.getId()); // 设置更新的 ID
        });

        // 调用
        storageAreaInfoService.updateStorageAreaInfo(reqVO);
        // 校验是否更新正确
        StorageAreaInfoDO storageAreaInfo = storageAreaInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, storageAreaInfo);
    }

    @Test
    public void testUpdateStorageAreaInfo_notExists() {
        // 准备参数
        StorageAreaInfoUpdateReqVO reqVO = randomPojo(StorageAreaInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> storageAreaInfoService.updateStorageAreaInfo(reqVO), STORAGE_AREA_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteStorageAreaInfo_success() {
        // mock 数据
        StorageAreaInfoDO dbStorageAreaInfo = randomPojo(StorageAreaInfoDO.class);
        storageAreaInfoMapper.insert(dbStorageAreaInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbStorageAreaInfo.getId();

        // 调用
        storageAreaInfoService.deleteStorageAreaInfo(id);
       // 校验数据不存在了
       assertNull(storageAreaInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteStorageAreaInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> storageAreaInfoService.deleteStorageAreaInfo(id), STORAGE_AREA_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetStorageAreaInfoPage() {
       // mock 数据
       StorageAreaInfoDO dbStorageAreaInfo = randomPojo(StorageAreaInfoDO.class, o -> { // 等会查询到
           o.setCode(null);
           o.setName(null);
           o.setLocationInfo(null);
           o.setCreateTime(null);
           o.setRemark(null);
       });
       storageAreaInfoMapper.insert(dbStorageAreaInfo);
       // 测试 code 不匹配
       storageAreaInfoMapper.insert(cloneIgnoreId(dbStorageAreaInfo, o -> o.setCode(null)));
       // 测试 name 不匹配
       storageAreaInfoMapper.insert(cloneIgnoreId(dbStorageAreaInfo, o -> o.setName(null)));
       // 测试 locationInfo 不匹配
       storageAreaInfoMapper.insert(cloneIgnoreId(dbStorageAreaInfo, o -> o.setLocationInfo(null)));
       // 测试 createTime 不匹配
       storageAreaInfoMapper.insert(cloneIgnoreId(dbStorageAreaInfo, o -> o.setCreateTime(null)));
       // 测试 remark 不匹配
       storageAreaInfoMapper.insert(cloneIgnoreId(dbStorageAreaInfo, o -> o.setRemark(null)));
       // 准备参数
       StorageAreaInfoPageReqVO reqVO = new StorageAreaInfoPageReqVO();
       reqVO.setCode(null);
       reqVO.setName(null);
       reqVO.setLocationInfo(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);

       // 调用
       PageResult<StorageAreaInfoDO> pageResult = storageAreaInfoService.getStorageAreaInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbStorageAreaInfo, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetStorageAreaInfoList() {
       // mock 数据
       StorageAreaInfoDO dbStorageAreaInfo = randomPojo(StorageAreaInfoDO.class, o -> { // 等会查询到
           o.setCode(null);
           o.setName(null);
           o.setLocationInfo(null);
           o.setCreateTime(null);
           o.setRemark(null);
       });
       storageAreaInfoMapper.insert(dbStorageAreaInfo);
       // 测试 code 不匹配
       storageAreaInfoMapper.insert(cloneIgnoreId(dbStorageAreaInfo, o -> o.setCode(null)));
       // 测试 name 不匹配
       storageAreaInfoMapper.insert(cloneIgnoreId(dbStorageAreaInfo, o -> o.setName(null)));
       // 测试 locationInfo 不匹配
       storageAreaInfoMapper.insert(cloneIgnoreId(dbStorageAreaInfo, o -> o.setLocationInfo(null)));
       // 测试 createTime 不匹配
       storageAreaInfoMapper.insert(cloneIgnoreId(dbStorageAreaInfo, o -> o.setCreateTime(null)));
       // 测试 remark 不匹配
       storageAreaInfoMapper.insert(cloneIgnoreId(dbStorageAreaInfo, o -> o.setRemark(null)));
       // 准备参数
       StorageAreaInfoExportReqVO reqVO = new StorageAreaInfoExportReqVO();
       reqVO.setCode(null);
       reqVO.setName(null);
       reqVO.setLocationInfo(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);

       // 调用
       List<StorageAreaInfoDO> list = storageAreaInfoService.getStorageAreaInfoList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbStorageAreaInfo, list.get(0));
    }

}
