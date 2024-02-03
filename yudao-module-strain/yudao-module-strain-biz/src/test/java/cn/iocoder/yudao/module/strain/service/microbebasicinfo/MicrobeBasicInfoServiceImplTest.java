package cn.iocoder.yudao.module.strain.service.microbebasicinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo.MicrobeBasicInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.microbebasicinfo.MicrobeBasicInfoMapper;
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
 * {@link MicrobeBasicInfoServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(MicrobeBasicInfoServiceImpl.class)
public class MicrobeBasicInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private MicrobeBasicInfoServiceImpl microbeBasicInfoService;

    @Resource
    private MicrobeBasicInfoMapper microbeBasicInfoMapper;

    @Test
    public void testCreateMicrobeBasicInfo_success() {
        // 准备参数
        MicrobeBasicInfoSaveReqVO createReqVO = randomPojo(MicrobeBasicInfoSaveReqVO.class).setId(null);

        // 调用
        Long microbeBasicInfoId = microbeBasicInfoService.createMicrobeBasicInfo(createReqVO);
        // 断言
        assertNotNull(microbeBasicInfoId);
        // 校验记录的属性是否正确
        MicrobeBasicInfoDO microbeBasicInfo = microbeBasicInfoMapper.selectById(microbeBasicInfoId);
        assertPojoEquals(createReqVO, microbeBasicInfo, "id");
    }

    @Test
    public void testUpdateMicrobeBasicInfo_success() {
        // mock 数据
        MicrobeBasicInfoDO dbMicrobeBasicInfo = randomPojo(MicrobeBasicInfoDO.class);
        microbeBasicInfoMapper.insert(dbMicrobeBasicInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        MicrobeBasicInfoSaveReqVO updateReqVO = randomPojo(MicrobeBasicInfoSaveReqVO.class, o -> {
            o.setId(dbMicrobeBasicInfo.getId()); // 设置更新的 ID
        });

        // 调用
        microbeBasicInfoService.updateMicrobeBasicInfo(updateReqVO);
        // 校验是否更新正确
        MicrobeBasicInfoDO microbeBasicInfo = microbeBasicInfoMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, microbeBasicInfo);
    }

    @Test
    public void testUpdateMicrobeBasicInfo_notExists() {
        // 准备参数
        MicrobeBasicInfoSaveReqVO updateReqVO = randomPojo(MicrobeBasicInfoSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> microbeBasicInfoService.updateMicrobeBasicInfo(updateReqVO), MICROBE_BASIC_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteMicrobeBasicInfo_success() {
        // mock 数据
        MicrobeBasicInfoDO dbMicrobeBasicInfo = randomPojo(MicrobeBasicInfoDO.class);
        microbeBasicInfoMapper.insert(dbMicrobeBasicInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbMicrobeBasicInfo.getId();

        // 调用
        microbeBasicInfoService.deleteMicrobeBasicInfo(id);
       // 校验数据不存在了
       assertNull(microbeBasicInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteMicrobeBasicInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> microbeBasicInfoService.deleteMicrobeBasicInfo(id), MICROBE_BASIC_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetMicrobeBasicInfoPage() {
       // mock 数据
       MicrobeBasicInfoDO dbMicrobeBasicInfo = randomPojo(MicrobeBasicInfoDO.class, o -> { // 等会查询到
           o.setOriginalCode(null);
           o.setCode(null);
           o.setChineseName(null);
           o.setLatinName(null);
           o.setGeneAccessionNumber(null);
           o.setIsPathogenic(null);
           o.setIsVisiable(null);
       });
       microbeBasicInfoMapper.insert(dbMicrobeBasicInfo);
       // 测试 originalCode 不匹配
       microbeBasicInfoMapper.insert(cloneIgnoreId(dbMicrobeBasicInfo, o -> o.setOriginalCode(null)));
       // 测试 code 不匹配
       microbeBasicInfoMapper.insert(cloneIgnoreId(dbMicrobeBasicInfo, o -> o.setCode(null)));
       // 测试 chineseName 不匹配
       microbeBasicInfoMapper.insert(cloneIgnoreId(dbMicrobeBasicInfo, o -> o.setChineseName(null)));
       // 测试 latinName 不匹配
       microbeBasicInfoMapper.insert(cloneIgnoreId(dbMicrobeBasicInfo, o -> o.setLatinName(null)));
       // 测试 geneAccessionNumber 不匹配
       microbeBasicInfoMapper.insert(cloneIgnoreId(dbMicrobeBasicInfo, o -> o.setGeneAccessionNumber(null)));
       // 测试 isPathogenic 不匹配
       microbeBasicInfoMapper.insert(cloneIgnoreId(dbMicrobeBasicInfo, o -> o.setIsPathogenic(null)));
       // 测试 isVisiable 不匹配
       microbeBasicInfoMapper.insert(cloneIgnoreId(dbMicrobeBasicInfo, o -> o.setIsVisiable(null)));
       // 准备参数
       MicrobeBasicInfoPageReqVO reqVO = new MicrobeBasicInfoPageReqVO();
       reqVO.setOriginalCode(null);
       reqVO.setCode(null);
       reqVO.setChineseName(null);
       reqVO.setLatinName(null);
       reqVO.setGeneAccessionNumber(null);
       reqVO.setIsPathogenic(null);
       reqVO.setIsVisiable(null);

       // 调用
       PageResult<MicrobeBasicInfoRespVO> pageResult = microbeBasicInfoService.getMicrobeBasicInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbMicrobeBasicInfo, pageResult.getList().get(0));
    }

}