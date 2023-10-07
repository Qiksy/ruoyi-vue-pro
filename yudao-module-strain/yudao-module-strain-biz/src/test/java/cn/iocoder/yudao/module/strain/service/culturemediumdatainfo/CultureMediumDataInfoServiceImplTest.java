package cn.iocoder.yudao.module.strain.service.culturemediumdatainfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.culturemediumdatainfo.CultureMediumDataInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.culturemediumdatainfo.CultureMediumDataInfoMapper;
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
 * {@link CultureMediumDataInfoServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(CultureMediumDataInfoServiceImpl.class)
public class CultureMediumDataInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private CultureMediumDataInfoServiceImpl cultureMediumDataInfoService;

    @Resource
    private CultureMediumDataInfoMapper cultureMediumDataInfoMapper;

    @Test
    public void testCreateCultureMediumDataInfo_success() {
        // 准备参数
        CultureMediumDataInfoCreateReqVO reqVO = randomPojo(CultureMediumDataInfoCreateReqVO.class);

        // 调用
        Long cultureMediumDataInfoId = cultureMediumDataInfoService.createCultureMediumDataInfo(reqVO);
        // 断言
        assertNotNull(cultureMediumDataInfoId);
        // 校验记录的属性是否正确
        CultureMediumDataInfoDO cultureMediumDataInfo = cultureMediumDataInfoMapper.selectById(cultureMediumDataInfoId);
        assertPojoEquals(reqVO, cultureMediumDataInfo);
    }

    @Test
    public void testUpdateCultureMediumDataInfo_success() {
        // mock 数据
        CultureMediumDataInfoDO dbCultureMediumDataInfo = randomPojo(CultureMediumDataInfoDO.class);
        cultureMediumDataInfoMapper.insert(dbCultureMediumDataInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        CultureMediumDataInfoUpdateReqVO reqVO = randomPojo(CultureMediumDataInfoUpdateReqVO.class, o -> {
            o.setId(dbCultureMediumDataInfo.getId()); // 设置更新的 ID
        });

        // 调用
        cultureMediumDataInfoService.updateCultureMediumDataInfo(reqVO);
        // 校验是否更新正确
        CultureMediumDataInfoDO cultureMediumDataInfo = cultureMediumDataInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, cultureMediumDataInfo);
    }

    @Test
    public void testUpdateCultureMediumDataInfo_notExists() {
        // 准备参数
        CultureMediumDataInfoUpdateReqVO reqVO = randomPojo(CultureMediumDataInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> cultureMediumDataInfoService.updateCultureMediumDataInfo(reqVO), CULTURE_MEDIUM_DATA_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteCultureMediumDataInfo_success() {
        // mock 数据
        CultureMediumDataInfoDO dbCultureMediumDataInfo = randomPojo(CultureMediumDataInfoDO.class);
        cultureMediumDataInfoMapper.insert(dbCultureMediumDataInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbCultureMediumDataInfo.getId();

        // 调用
        cultureMediumDataInfoService.deleteCultureMediumDataInfo(id);
       // 校验数据不存在了
       assertNull(cultureMediumDataInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteCultureMediumDataInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> cultureMediumDataInfoService.deleteCultureMediumDataInfo(id), CULTURE_MEDIUM_DATA_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetCultureMediumDataInfoPage() {
       // mock 数据
       CultureMediumDataInfoDO dbCultureMediumDataInfo = randomPojo(CultureMediumDataInfoDO.class, o -> { // 等会查询到
           o.setCode(null);
           o.setName(null);
       });
       cultureMediumDataInfoMapper.insert(dbCultureMediumDataInfo);
       // 测试 code 不匹配
       cultureMediumDataInfoMapper.insert(cloneIgnoreId(dbCultureMediumDataInfo, o -> o.setCode(null)));
       // 测试 name 不匹配
       cultureMediumDataInfoMapper.insert(cloneIgnoreId(dbCultureMediumDataInfo, o -> o.setName(null)));
       // 准备参数
       CultureMediumDataInfoPageReqVO reqVO = new CultureMediumDataInfoPageReqVO();
       reqVO.setCode(null);
       reqVO.setName(null);

       // 调用
       PageResult<CultureMediumDataInfoDO> pageResult = cultureMediumDataInfoService.getCultureMediumDataInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbCultureMediumDataInfo, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetCultureMediumDataInfoList() {
       // mock 数据
       CultureMediumDataInfoDO dbCultureMediumDataInfo = randomPojo(CultureMediumDataInfoDO.class, o -> { // 等会查询到
           o.setCode(null);
           o.setName(null);
       });
       cultureMediumDataInfoMapper.insert(dbCultureMediumDataInfo);
       // 测试 code 不匹配
       cultureMediumDataInfoMapper.insert(cloneIgnoreId(dbCultureMediumDataInfo, o -> o.setCode(null)));
       // 测试 name 不匹配
       cultureMediumDataInfoMapper.insert(cloneIgnoreId(dbCultureMediumDataInfo, o -> o.setName(null)));
       // 准备参数
       CultureMediumDataInfoExportReqVO reqVO = new CultureMediumDataInfoExportReqVO();
       reqVO.setCode(null);
       reqVO.setName(null);

       // 调用
       List<CultureMediumDataInfoDO> list = cultureMediumDataInfoService.getCultureMediumDataInfoList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbCultureMediumDataInfo, list.get(0));
    }

}
