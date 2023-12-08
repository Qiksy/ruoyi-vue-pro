package cn.iocoder.yudao.module.sale.service.productioninfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.sale.controller.admin.productioninfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioninfo.ProductionInfoDO;
import cn.iocoder.yudao.module.sale.dal.mysql.productioninfo.ProductionInfoMapper;
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
 * {@link ProductionInfoServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(ProductionInfoServiceImpl.class)
public class ProductionInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ProductionInfoServiceImpl productionInfoService;

    @Resource
    private ProductionInfoMapper productionInfoMapper;

    @Test
    public void testCreateProductionInfo_success() {
        // 准备参数
        ProductionInfoSaveReqVO createReqVO = randomPojo(ProductionInfoSaveReqVO.class).setId(null);

        // 调用
        Long productionInfoId = productionInfoService.createProductionInfo(createReqVO);
        // 断言
        assertNotNull(productionInfoId);
        // 校验记录的属性是否正确
        ProductionInfoDO productionInfo = productionInfoMapper.selectById(productionInfoId);
        assertPojoEquals(createReqVO, productionInfo, "id");
    }

    @Test
    public void testUpdateProductionInfo_success() {
        // mock 数据
        ProductionInfoDO dbProductionInfo = randomPojo(ProductionInfoDO.class);
        productionInfoMapper.insert(dbProductionInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ProductionInfoSaveReqVO updateReqVO = randomPojo(ProductionInfoSaveReqVO.class, o -> {
            o.setId(dbProductionInfo.getId()); // 设置更新的 ID
        });

        // 调用
        productionInfoService.updateProductionInfo(updateReqVO);
        // 校验是否更新正确
        ProductionInfoDO productionInfo = productionInfoMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, productionInfo);
    }

    @Test
    public void testUpdateProductionInfo_notExists() {
        // 准备参数
        ProductionInfoSaveReqVO updateReqVO = randomPojo(ProductionInfoSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> productionInfoService.updateProductionInfo(updateReqVO), PRODUCTION_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteProductionInfo_success() {
        // mock 数据
        ProductionInfoDO dbProductionInfo = randomPojo(ProductionInfoDO.class);
        productionInfoMapper.insert(dbProductionInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProductionInfo.getId();

        // 调用
        productionInfoService.deleteProductionInfo(id);
       // 校验数据不存在了
       assertNull(productionInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteProductionInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> productionInfoService.deleteProductionInfo(id), PRODUCTION_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetProductionInfoPage() {
       // mock 数据
       ProductionInfoDO dbProductionInfo = randomPojo(ProductionInfoDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setMarsaleclassId(null);
           o.setMarbasclassId(null);
           o.setProdlineId(null);
           o.setSpec(null);
           o.setPrice(null);
           o.setProtein(null);
           o.setCreateTime(null);
       });
       productionInfoMapper.insert(dbProductionInfo);
       // 测试 name 不匹配
       productionInfoMapper.insert(cloneIgnoreId(dbProductionInfo, o -> o.setName(null)));
       // 测试 marsaleclassId 不匹配
       productionInfoMapper.insert(cloneIgnoreId(dbProductionInfo, o -> o.setMarsaleclassId(null)));
       // 测试 marbasclassId 不匹配
       productionInfoMapper.insert(cloneIgnoreId(dbProductionInfo, o -> o.setMarbasclassId(null)));
       // 测试 prodlineId 不匹配
       productionInfoMapper.insert(cloneIgnoreId(dbProductionInfo, o -> o.setProdlineId(null)));
       // 测试 spec 不匹配
       productionInfoMapper.insert(cloneIgnoreId(dbProductionInfo, o -> o.setSpec(null)));
       // 测试 price 不匹配
       productionInfoMapper.insert(cloneIgnoreId(dbProductionInfo, o -> o.setPrice(null)));
       // 测试 protein 不匹配
       productionInfoMapper.insert(cloneIgnoreId(dbProductionInfo, o -> o.setProtein(null)));
       // 测试 createTime 不匹配
       productionInfoMapper.insert(cloneIgnoreId(dbProductionInfo, o -> o.setCreateTime(null)));
       // 准备参数
       ProductionInfoPageReqVO reqVO = new ProductionInfoPageReqVO();
       reqVO.setName(null);
       reqVO.setMarsaleclassId(null);
       reqVO.setMarbasclassId(null);
       reqVO.setProdlineId(null);
       reqVO.setSpec(null);
       reqVO.setPrice(null);
       reqVO.setProtein(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ProductionInfoDO> pageResult = productionInfoService.getProductionInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbProductionInfo, pageResult.getList().get(0));
    }

}