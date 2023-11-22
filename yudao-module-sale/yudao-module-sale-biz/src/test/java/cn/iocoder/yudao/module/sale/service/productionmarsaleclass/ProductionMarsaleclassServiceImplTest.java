package cn.iocoder.yudao.module.sale.service.productionmarsaleclass;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.sale.controller.admin.productionmarsaleclass.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarsaleclass.ProductionMarsaleclassDO;
import cn.iocoder.yudao.module.sale.dal.mysql.productionmarsaleclass.ProductionMarsaleclassMapper;
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
 * {@link ProductionMarsaleclassServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(ProductionMarsaleclassServiceImpl.class)
public class ProductionMarsaleclassServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ProductionMarsaleclassServiceImpl productionMarsaleclassService;

    @Resource
    private ProductionMarsaleclassMapper productionMarsaleclassMapper;

    @Test
    public void testCreateProductionMarsaleclass_success() {
        // 准备参数
        ProductionMarsaleclassSaveReqVO createReqVO = randomPojo(ProductionMarsaleclassSaveReqVO.class).setId(null);

        // 调用
        Long productionMarsaleclassId = productionMarsaleclassService.createProductionMarsaleclass(createReqVO);
        // 断言
        assertNotNull(productionMarsaleclassId);
        // 校验记录的属性是否正确
        ProductionMarsaleclassDO productionMarsaleclass = productionMarsaleclassMapper.selectById(productionMarsaleclassId);
        assertPojoEquals(createReqVO, productionMarsaleclass, "id");
    }

    @Test
    public void testUpdateProductionMarsaleclass_success() {
        // mock 数据
        ProductionMarsaleclassDO dbProductionMarsaleclass = randomPojo(ProductionMarsaleclassDO.class);
        productionMarsaleclassMapper.insert(dbProductionMarsaleclass);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ProductionMarsaleclassSaveReqVO updateReqVO = randomPojo(ProductionMarsaleclassSaveReqVO.class, o -> {
            o.setId(dbProductionMarsaleclass.getId()); // 设置更新的 ID
        });

        // 调用
        productionMarsaleclassService.updateProductionMarsaleclass(updateReqVO);
        // 校验是否更新正确
        ProductionMarsaleclassDO productionMarsaleclass = productionMarsaleclassMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, productionMarsaleclass);
    }

    @Test
    public void testUpdateProductionMarsaleclass_notExists() {
        // 准备参数
        ProductionMarsaleclassSaveReqVO updateReqVO = randomPojo(ProductionMarsaleclassSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> productionMarsaleclassService.updateProductionMarsaleclass(updateReqVO), PRODUCTION_MARSALECLASS_NOT_EXISTS);
    }

    @Test
    public void testDeleteProductionMarsaleclass_success() {
        // mock 数据
        ProductionMarsaleclassDO dbProductionMarsaleclass = randomPojo(ProductionMarsaleclassDO.class);
        productionMarsaleclassMapper.insert(dbProductionMarsaleclass);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProductionMarsaleclass.getId();

        // 调用
        productionMarsaleclassService.deleteProductionMarsaleclass(id);
       // 校验数据不存在了
       assertNull(productionMarsaleclassMapper.selectById(id));
    }

    @Test
    public void testDeleteProductionMarsaleclass_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> productionMarsaleclassService.deleteProductionMarsaleclass(id), PRODUCTION_MARSALECLASS_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetProductionMarsaleclassList() {
       // mock 数据
       ProductionMarsaleclassDO dbProductionMarsaleclass = randomPojo(ProductionMarsaleclassDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setParentId(null);
           o.setCreateTime(null);
       });
       productionMarsaleclassMapper.insert(dbProductionMarsaleclass);
       // 测试 name 不匹配
       productionMarsaleclassMapper.insert(cloneIgnoreId(dbProductionMarsaleclass, o -> o.setName(null)));
       // 测试 parentId 不匹配
       productionMarsaleclassMapper.insert(cloneIgnoreId(dbProductionMarsaleclass, o -> o.setParentId(null)));
       // 测试 createTime 不匹配
       productionMarsaleclassMapper.insert(cloneIgnoreId(dbProductionMarsaleclass, o -> o.setCreateTime(null)));
       // 准备参数
       ProductionMarsaleclassListReqVO reqVO = new ProductionMarsaleclassListReqVO();
       reqVO.setName(null);
       reqVO.setParentId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<ProductionMarsaleclassDO> list = productionMarsaleclassService.getProductionMarsaleclassList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbProductionMarsaleclass, list.get(0));
    }

}