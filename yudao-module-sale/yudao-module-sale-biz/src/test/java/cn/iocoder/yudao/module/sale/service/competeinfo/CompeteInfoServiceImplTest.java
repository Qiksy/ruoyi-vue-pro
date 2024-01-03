package cn.iocoder.yudao.module.sale.service.competeinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.sale.controller.admin.competeinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfo.CompeteInfoDO;
import cn.iocoder.yudao.module.sale.dal.mysql.competeinfo.CompeteInfoMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link CompeteInfoServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(CompeteInfoServiceImpl.class)
public class CompeteInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private CompeteInfoServiceImpl competeInfoService;

    @Resource
    private CompeteInfoMapper competeInfoMapper;

    @Test
    public void testCreateCompeteInfo_success() {
        // 准备参数
        CompeteInfoSaveReqVO createReqVO = randomPojo(CompeteInfoSaveReqVO.class).setId(null);

        // 调用
        Long competeInfoId = competeInfoService.createCompeteInfo(createReqVO);
        // 断言
        assertNotNull(competeInfoId);
        // 校验记录的属性是否正确
        CompeteInfoDO competeInfo = competeInfoMapper.selectById(competeInfoId);
        assertPojoEquals(createReqVO, competeInfo, "id");
    }

    @Test
    public void testUpdateCompeteInfo_success() {
        // mock 数据
        CompeteInfoDO dbCompeteInfo = randomPojo(CompeteInfoDO.class);
        competeInfoMapper.insert(dbCompeteInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        CompeteInfoSaveReqVO updateReqVO = randomPojo(CompeteInfoSaveReqVO.class, o -> {
            o.setId(dbCompeteInfo.getId()); // 设置更新的 ID
        });

        // 调用
        competeInfoService.updateCompeteInfo(updateReqVO);
        // 校验是否更新正确
        CompeteInfoDO competeInfo = competeInfoMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, competeInfo);
    }

    @Test
    public void testUpdateCompeteInfo_notExists() {
        // 准备参数
        CompeteInfoSaveReqVO updateReqVO = randomPojo(CompeteInfoSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> competeInfoService.updateCompeteInfo(updateReqVO), COMPETE_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteCompeteInfo_success() {
        // mock 数据
        CompeteInfoDO dbCompeteInfo = randomPojo(CompeteInfoDO.class);
        competeInfoMapper.insert(dbCompeteInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbCompeteInfo.getId();

        // 调用
        competeInfoService.deleteCompeteInfo(id);
       // 校验数据不存在了
       assertNull(competeInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteCompeteInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> competeInfoService.deleteCompeteInfo(id), COMPETE_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetCompeteInfoPage() {
       // mock 数据
       CompeteInfoDO dbCompeteInfo = randomPojo(CompeteInfoDO.class, o -> { // 等会查询到
           o.setBrand(null);
           o.setProdName(null);
           o.setSpec(null);
           o.setCompeteId(null);
           o.setPrice(null);
           o.setCreateTime(null);
       });
       competeInfoMapper.insert(dbCompeteInfo);
       // 测试 brand 不匹配
       competeInfoMapper.insert(cloneIgnoreId(dbCompeteInfo, o -> o.setBrand(null)));
       // 测试 prodName 不匹配
       competeInfoMapper.insert(cloneIgnoreId(dbCompeteInfo, o -> o.setProdName(null)));
       // 测试 spec 不匹配
       competeInfoMapper.insert(cloneIgnoreId(dbCompeteInfo, o -> o.setSpec(null)));
       // 测试 competeId 不匹配
       competeInfoMapper.insert(cloneIgnoreId(dbCompeteInfo, o -> o.setCompeteId(null)));
       // 测试 price 不匹配
       competeInfoMapper.insert(cloneIgnoreId(dbCompeteInfo, o -> o.setPrice(null)));
       // 测试 createTime 不匹配
       competeInfoMapper.insert(cloneIgnoreId(dbCompeteInfo, o -> o.setCreateTime(null)));
       // 准备参数
       CompeteInfoPageReqVO reqVO = new CompeteInfoPageReqVO();
       reqVO.setBrand(null);
       reqVO.setProdName(null);
       reqVO.setSpec(null);
       reqVO.setCompeteId(null);
       reqVO.setPrice(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<CompeteInfoDO> pageResult = competeInfoService.getCompeteInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbCompeteInfo, pageResult.getList().get(0));
    }

}