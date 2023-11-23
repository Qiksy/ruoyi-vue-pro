package cn.iocoder.yudao.module.sale.service.productioncompeteinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.sale.controller.admin.productioncompeteinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioncompeteinfo.ProductionCompeteInfoDO;
import cn.iocoder.yudao.module.sale.dal.mysql.productioncompeteinfo.ProductionCompeteInfoMapper;
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
 * {@link ProductionCompeteInfoServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(ProductionCompeteInfoServiceImpl.class)
public class ProductionCompeteInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ProductionCompeteInfoServiceImpl productionCompeteInfoService;

    @Resource
    private ProductionCompeteInfoMapper productionCompeteInfoMapper;

    @Test
    public void testCreateProductionCompeteInfo_success() {
        // 准备参数
        ProductionCompeteInfoSaveReqVO createReqVO = randomPojo(ProductionCompeteInfoSaveReqVO.class).setId(null);

        // 调用
        Long productionCompeteInfoId = productionCompeteInfoService.createProductionCompeteInfo(createReqVO);
        // 断言
        assertNotNull(productionCompeteInfoId);
        // 校验记录的属性是否正确
        ProductionCompeteInfoDO productionCompeteInfo = productionCompeteInfoMapper.selectById(productionCompeteInfoId);
        assertPojoEquals(createReqVO, productionCompeteInfo, "id");
    }

    @Test
    public void testUpdateProductionCompeteInfo_success() {
        // mock 数据
        ProductionCompeteInfoDO dbProductionCompeteInfo = randomPojo(ProductionCompeteInfoDO.class);
        productionCompeteInfoMapper.insert(dbProductionCompeteInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ProductionCompeteInfoSaveReqVO updateReqVO = randomPojo(ProductionCompeteInfoSaveReqVO.class, o -> {
            o.setId(dbProductionCompeteInfo.getId()); // 设置更新的 ID
        });

        // 调用
        productionCompeteInfoService.updateProductionCompeteInfo(updateReqVO);
        // 校验是否更新正确
        ProductionCompeteInfoDO productionCompeteInfo = productionCompeteInfoMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, productionCompeteInfo);
    }

    @Test
    public void testUpdateProductionCompeteInfo_notExists() {
        // 准备参数
        ProductionCompeteInfoSaveReqVO updateReqVO = randomPojo(ProductionCompeteInfoSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> productionCompeteInfoService.updateProductionCompeteInfo(updateReqVO), PRODUCTION_COMPETE_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteProductionCompeteInfo_success() {
        // mock 数据
        ProductionCompeteInfoDO dbProductionCompeteInfo = randomPojo(ProductionCompeteInfoDO.class);
        productionCompeteInfoMapper.insert(dbProductionCompeteInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProductionCompeteInfo.getId();

        // 调用
        productionCompeteInfoService.deleteProductionCompeteInfo(id);
       // 校验数据不存在了
       assertNull(productionCompeteInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteProductionCompeteInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> productionCompeteInfoService.deleteProductionCompeteInfo(id), PRODUCTION_COMPETE_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetProductionCompeteInfoPage() {
       // mock 数据
       ProductionCompeteInfoDO dbProductionCompeteInfo = randomPojo(ProductionCompeteInfoDO.class, o -> { // 等会查询到
           o.setProductionId(null);
           o.setDeptId(null);
           o.setPrice(null);
           o.setCreateTime(null);
       });
       productionCompeteInfoMapper.insert(dbProductionCompeteInfo);
       // 测试 productionId 不匹配
       productionCompeteInfoMapper.insert(cloneIgnoreId(dbProductionCompeteInfo, o -> o.setProductionId(null)));
       // 测试 deptId 不匹配
       productionCompeteInfoMapper.insert(cloneIgnoreId(dbProductionCompeteInfo, o -> o.setDeptId(null)));
       // 测试 price 不匹配
       productionCompeteInfoMapper.insert(cloneIgnoreId(dbProductionCompeteInfo, o -> o.setPrice(null)));
       // 测试 createTime 不匹配
       productionCompeteInfoMapper.insert(cloneIgnoreId(dbProductionCompeteInfo, o -> o.setCreateTime(null)));
       // 准备参数
       ProductionCompeteInfoPageReqVO reqVO = new ProductionCompeteInfoPageReqVO();
       reqVO.setProductionId(null);
       reqVO.setDeptId(null);
       reqVO.setPrice(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ProductionCompeteInfoDO> pageResult = productionCompeteInfoService.getProductionCompeteInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbProductionCompeteInfo, pageResult.getList().get(0));
    }

}