package cn.iocoder.yudao.module.sale.service.productionmarbasclass;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.sale.controller.admin.productionmarbasclass.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarbasclass.ProductionMarbasclassDO;
import cn.iocoder.yudao.module.sale.dal.mysql.productionmarbasclass.ProductionMarbasclassMapper;
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
 * {@link ProductionMarbasclassServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(ProductionMarbasclassServiceImpl.class)
public class ProductionMarbasclassServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ProductionMarbasclassServiceImpl productionMarbasclassService;

    @Resource
    private ProductionMarbasclassMapper productionMarbasclassMapper;

    @Test
    public void testCreateProductionMarbasclass_success() {
        // 准备参数
        ProductionMarbasclassSaveReqVO createReqVO = randomPojo(ProductionMarbasclassSaveReqVO.class).setId(null);

        // 调用
        Long productionMarbasclassId = productionMarbasclassService.createProductionMarbasclass(createReqVO);
        // 断言
        assertNotNull(productionMarbasclassId);
        // 校验记录的属性是否正确
        ProductionMarbasclassDO productionMarbasclass = productionMarbasclassMapper.selectById(productionMarbasclassId);
        assertPojoEquals(createReqVO, productionMarbasclass, "id");
    }

    @Test
    public void testUpdateProductionMarbasclass_success() {
        // mock 数据
        ProductionMarbasclassDO dbProductionMarbasclass = randomPojo(ProductionMarbasclassDO.class);
        productionMarbasclassMapper.insert(dbProductionMarbasclass);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ProductionMarbasclassSaveReqVO updateReqVO = randomPojo(ProductionMarbasclassSaveReqVO.class, o -> {
            o.setId(dbProductionMarbasclass.getId()); // 设置更新的 ID
        });

        // 调用
        productionMarbasclassService.updateProductionMarbasclass(updateReqVO);
        // 校验是否更新正确
        ProductionMarbasclassDO productionMarbasclass = productionMarbasclassMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, productionMarbasclass);
    }

    @Test
    public void testUpdateProductionMarbasclass_notExists() {
        // 准备参数
        ProductionMarbasclassSaveReqVO updateReqVO = randomPojo(ProductionMarbasclassSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> productionMarbasclassService.updateProductionMarbasclass(updateReqVO), PRODUCTION_MARBASCLASS_NOT_EXISTS);
    }

    @Test
    public void testDeleteProductionMarbasclass_success() {
        // mock 数据
        ProductionMarbasclassDO dbProductionMarbasclass = randomPojo(ProductionMarbasclassDO.class);
        productionMarbasclassMapper.insert(dbProductionMarbasclass);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProductionMarbasclass.getId();

        // 调用
        productionMarbasclassService.deleteProductionMarbasclass(id);
       // 校验数据不存在了
       assertNull(productionMarbasclassMapper.selectById(id));
    }

    @Test
    public void testDeleteProductionMarbasclass_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> productionMarbasclassService.deleteProductionMarbasclass(id), PRODUCTION_MARBASCLASS_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetProductionMarbasclassList() {
       // mock 数据
       ProductionMarbasclassDO dbProductionMarbasclass = randomPojo(ProductionMarbasclassDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setParentId(null);
           o.setCreateTime(null);
       });
       productionMarbasclassMapper.insert(dbProductionMarbasclass);
       // 测试 name 不匹配
       productionMarbasclassMapper.insert(cloneIgnoreId(dbProductionMarbasclass, o -> o.setName(null)));
       // 测试 parentId 不匹配
       productionMarbasclassMapper.insert(cloneIgnoreId(dbProductionMarbasclass, o -> o.setParentId(null)));
       // 测试 createTime 不匹配
       productionMarbasclassMapper.insert(cloneIgnoreId(dbProductionMarbasclass, o -> o.setCreateTime(null)));
       // 准备参数
       ProductionMarbasclassListReqVO reqVO = new ProductionMarbasclassListReqVO();
       reqVO.setName(null);
       reqVO.setParentId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<ProductionMarbasclassDO> list = productionMarbasclassService.getProductionMarbasclassList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbProductionMarbasclass, list.get(0));
    }

}