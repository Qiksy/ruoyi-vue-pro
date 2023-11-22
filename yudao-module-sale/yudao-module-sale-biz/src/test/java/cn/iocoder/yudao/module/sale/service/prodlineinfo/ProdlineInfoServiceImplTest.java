package cn.iocoder.yudao.module.sale.service.prodlineinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.sale.controller.admin.prodlineinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.prodlineinfo.ProdlineInfoDO;
import cn.iocoder.yudao.module.sale.dal.mysql.prodlineinfo.ProdlineInfoMapper;
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
 * {@link ProdlineInfoServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(ProdlineInfoServiceImpl.class)
public class ProdlineInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ProdlineInfoServiceImpl prodlineInfoService;

    @Resource
    private ProdlineInfoMapper prodlineInfoMapper;

    @Test
    public void testCreateProdlineInfo_success() {
        // 准备参数
        ProdlineInfoSaveReqVO createReqVO = randomPojo(ProdlineInfoSaveReqVO.class).setId(null);

        // 调用
        Long prodlineInfoId = prodlineInfoService.createProdlineInfo(createReqVO);
        // 断言
        assertNotNull(prodlineInfoId);
        // 校验记录的属性是否正确
        ProdlineInfoDO prodlineInfo = prodlineInfoMapper.selectById(prodlineInfoId);
        assertPojoEquals(createReqVO, prodlineInfo, "id");
    }

    @Test
    public void testUpdateProdlineInfo_success() {
        // mock 数据
        ProdlineInfoDO dbProdlineInfo = randomPojo(ProdlineInfoDO.class);
        prodlineInfoMapper.insert(dbProdlineInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ProdlineInfoSaveReqVO updateReqVO = randomPojo(ProdlineInfoSaveReqVO.class, o -> {
            o.setId(dbProdlineInfo.getId()); // 设置更新的 ID
        });

        // 调用
        prodlineInfoService.updateProdlineInfo(updateReqVO);
        // 校验是否更新正确
        ProdlineInfoDO prodlineInfo = prodlineInfoMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, prodlineInfo);
    }

    @Test
    public void testUpdateProdlineInfo_notExists() {
        // 准备参数
        ProdlineInfoSaveReqVO updateReqVO = randomPojo(ProdlineInfoSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> prodlineInfoService.updateProdlineInfo(updateReqVO), PRODLINE_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteProdlineInfo_success() {
        // mock 数据
        ProdlineInfoDO dbProdlineInfo = randomPojo(ProdlineInfoDO.class);
        prodlineInfoMapper.insert(dbProdlineInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProdlineInfo.getId();

        // 调用
        prodlineInfoService.deleteProdlineInfo(id);
       // 校验数据不存在了
       assertNull(prodlineInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteProdlineInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> prodlineInfoService.deleteProdlineInfo(id), PRODLINE_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetProdlineInfoList() {
       // mock 数据
       ProdlineInfoDO dbProdlineInfo = randomPojo(ProdlineInfoDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setParentId(null);
           o.setCreateTime(null);
       });
       prodlineInfoMapper.insert(dbProdlineInfo);
       // 测试 name 不匹配
       prodlineInfoMapper.insert(cloneIgnoreId(dbProdlineInfo, o -> o.setName(null)));
       // 测试 parentId 不匹配
       prodlineInfoMapper.insert(cloneIgnoreId(dbProdlineInfo, o -> o.setParentId(null)));
       // 测试 createTime 不匹配
       prodlineInfoMapper.insert(cloneIgnoreId(dbProdlineInfo, o -> o.setCreateTime(null)));
       // 准备参数
       ProdlineInfoListReqVO reqVO = new ProdlineInfoListReqVO();
       reqVO.setName(null);
       reqVO.setParentId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<ProdlineInfoDO> list = prodlineInfoService.getProdlineInfoList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbProdlineInfo, list.get(0));
    }

}