package cn.iocoder.yudao.module.sale.service.competeinfosub;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.sale.controller.admin.competeinfosub.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfosub.CompeteInfoSubDO;
import cn.iocoder.yudao.module.sale.dal.mysql.competeinfosub.CompeteInfoSubMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.springframework.context.annotation.Import;

import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link CompeteInfoSubServiceImpl} 的单元测试类
 *
 * @author 播恩超级管理员
 */
@Import(CompeteInfoSubServiceImpl.class)
public class CompeteInfoSubServiceImplTest extends BaseDbUnitTest {

    @Resource
    private CompeteInfoSubServiceImpl competeInfoSubService;

    @Resource
    private CompeteInfoSubMapper competeInfoSubMapper;

    @Test
    public void testCreateCompeteInfoSub_success() {
        // 准备参数
        CompeteInfoSubSaveReqVO createReqVO = randomPojo(CompeteInfoSubSaveReqVO.class).setId(null);

        // 调用
        Long competeInfoSubId = competeInfoSubService.createCompeteInfoSub(createReqVO);
        // 断言
        assertNotNull(competeInfoSubId);
        // 校验记录的属性是否正确
        CompeteInfoSubDO competeInfoSub = competeInfoSubMapper.selectById(competeInfoSubId);
        assertPojoEquals(createReqVO, competeInfoSub, "id");
    }

    @Test
    public void testUpdateCompeteInfoSub_success() {
        // mock 数据
        CompeteInfoSubDO dbCompeteInfoSub = randomPojo(CompeteInfoSubDO.class);
        competeInfoSubMapper.insert(dbCompeteInfoSub);// @Sql: 先插入出一条存在的数据
        // 准备参数
        CompeteInfoSubSaveReqVO updateReqVO = randomPojo(CompeteInfoSubSaveReqVO.class, o -> {
            o.setId(dbCompeteInfoSub.getId()); // 设置更新的 ID
        });

        // 调用
        competeInfoSubService.updateCompeteInfoSub(updateReqVO);
        // 校验是否更新正确
        CompeteInfoSubDO competeInfoSub = competeInfoSubMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, competeInfoSub);
    }

    @Test
    public void testUpdateCompeteInfoSub_notExists() {
        // 准备参数
        CompeteInfoSubSaveReqVO updateReqVO = randomPojo(CompeteInfoSubSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> competeInfoSubService.updateCompeteInfoSub(updateReqVO), COMPETE_INFO_SUB_NOT_EXISTS);
    }

    @Test
    public void testDeleteCompeteInfoSub_success() {
        // mock 数据
        CompeteInfoSubDO dbCompeteInfoSub = randomPojo(CompeteInfoSubDO.class);
        competeInfoSubMapper.insert(dbCompeteInfoSub);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbCompeteInfoSub.getId();

        // 调用
        competeInfoSubService.deleteCompeteInfoSub(id);
       // 校验数据不存在了
       assertNull(competeInfoSubMapper.selectById(id));
    }

    @Test
    public void testDeleteCompeteInfoSub_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> competeInfoSubService.deleteCompeteInfoSub(id), COMPETE_INFO_SUB_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetCompeteInfoSubPage() {
       // mock 数据
       CompeteInfoSubDO dbCompeteInfoSub = randomPojo(CompeteInfoSubDO.class, o -> { // 等会查询到
           o.setParentId(null);
           o.setChangeDate(null);
           o.setPriceChanges(null);
           o.setFileId(null);
           o.setCreateTime(null);
       });
       competeInfoSubMapper.insert(dbCompeteInfoSub);
       // 测试 parentId 不匹配
       competeInfoSubMapper.insert(cloneIgnoreId(dbCompeteInfoSub, o -> o.setParentId(null)));
       // 测试 changeDate 不匹配
       competeInfoSubMapper.insert(cloneIgnoreId(dbCompeteInfoSub, o -> o.setChangeDate(null)));
       // 测试 priceChanges 不匹配
       competeInfoSubMapper.insert(cloneIgnoreId(dbCompeteInfoSub, o -> o.setPriceChanges(null)));
       // 测试 fileUrl 不匹配
       competeInfoSubMapper.insert(cloneIgnoreId(dbCompeteInfoSub, o -> o.setFileId(null)));
       // 测试 createTime 不匹配
       competeInfoSubMapper.insert(cloneIgnoreId(dbCompeteInfoSub, o -> o.setCreateTime(null)));
       // 准备参数
       CompeteInfoSubPageReqVO reqVO = new CompeteInfoSubPageReqVO();
       reqVO.setParentId(null);
       reqVO.setChangeDate(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setPriceChanges(null);
       reqVO.setFileId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<CompeteInfoSubDO> pageResult = competeInfoSubService.getCompeteInfoSubPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbCompeteInfoSub, pageResult.getList().get(0));
    }

}