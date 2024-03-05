package cn.iocoder.yudao.module.strain.service.freezingboxinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo.FreezingBoxInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingboxinfo.FreezingBoxInfoMapper;
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
 * {@link FreezingBoxInfoServiceImpl} 的单元测试类
 * 冻藏盒 基础信息测试
 *
 * @author qiksy
 */
@Import(FreezingBoxInfoServiceImpl.class)
public class FreezingBoxInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FreezingBoxInfoServiceImpl freezingBoxInfoService;

    @Resource
    private FreezingBoxInfoMapper freezingBoxInfoMapper;

    @Test
    public void testCreateFreezingBoxInfo_success() {
        // 准备参数
        FreezingBoxInfoCreateReqVO reqVO = randomPojo(FreezingBoxInfoCreateReqVO.class);

        // 调用
        Long freezingBoxInfoId = freezingBoxInfoService.createFreezingBoxInfo(reqVO);
        // 断言
        assertNotNull(freezingBoxInfoId);
        // 校验记录的属性是否正确
        FreezingBoxInfoDO freezingBoxInfo = freezingBoxInfoMapper.selectById(freezingBoxInfoId);
        assertPojoEquals(reqVO, freezingBoxInfo, "createTime", "updateTime");
    }

    @Test
    public void testUpdateFreezingBoxInfo_success() {
        // mock 数据
        FreezingBoxInfoDO dbFreezingBoxInfo = randomPojo(FreezingBoxInfoDO.class);
        freezingBoxInfoMapper.insert(dbFreezingBoxInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FreezingBoxInfoUpdateReqVO reqVO = randomPojo(FreezingBoxInfoUpdateReqVO.class, o -> {
            o.setId(dbFreezingBoxInfo.getId()); // 设置更新的 ID
        });

        // 调用
        freezingBoxInfoService.updateFreezingBoxInfo(reqVO);
        // 校验是否更新正确
        FreezingBoxInfoDO freezingBoxInfo = freezingBoxInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, freezingBoxInfo);
    }

    @Test
    public void testUpdateFreezingBoxInfo_notExists() {
        // 准备参数
        FreezingBoxInfoUpdateReqVO reqVO = randomPojo(FreezingBoxInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> freezingBoxInfoService.updateFreezingBoxInfo(reqVO), FREEZING_BOX_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteFreezingBoxInfo_success() {
        // mock 数据
        FreezingBoxInfoDO dbFreezingBoxInfo = randomPojo(FreezingBoxInfoDO.class);
        freezingBoxInfoMapper.insert(dbFreezingBoxInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFreezingBoxInfo.getId();

        // 调用
        freezingBoxInfoService.deleteFreezingBoxInfo(id);
        // 校验数据不存在了
        assertNull(freezingBoxInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteFreezingBoxInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> freezingBoxInfoService.deleteFreezingBoxInfo(id), FREEZING_BOX_INFO_NOT_EXISTS);
    }

    @Test
    public void testGetFreezingBoxInfoPage() {
        // mock 数据
        FreezingBoxInfoDO dbFreezingBoxInfo = randomPojo(FreezingBoxInfoDO.class, o -> { // 等会查询到
            o.setCode("001");
            o.setName("基础冻藏盒");
            o.setRemark("备注");
        });
        freezingBoxInfoMapper.insert(dbFreezingBoxInfo);
        // 测试 code 不匹配
        freezingBoxInfoMapper.insert(cloneIgnoreId(dbFreezingBoxInfo, o -> o.setCode("002")));
        // 测试 name 不匹配
        freezingBoxInfoMapper.insert(cloneIgnoreId(dbFreezingBoxInfo, o -> o.setName("测试冻藏盒")));
        // 测试 remark 不匹配
        freezingBoxInfoMapper.insert(cloneIgnoreId(dbFreezingBoxInfo, o -> o.setRemark("测试啊啊啊")));
        // 准备参数
        FreezingBoxInfoPageReqVO reqVO = new FreezingBoxInfoPageReqVO();
        reqVO.setCode("001");
        reqVO.setName("基础冻藏盒");
        reqVO.setRemark("备注");

        // 调用
        PageResult<FreezingBoxInfoDO> pageResult = freezingBoxInfoService.getFreezingBoxInfoPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbFreezingBoxInfo, pageResult.getList().getFirst());
    }

    @Test
    public void testGetFreezingBoxInfoList() {
        // mock 数据
        FreezingBoxInfoDO dbFreezingBoxInfo = randomPojo(FreezingBoxInfoDO.class, o -> { // 等会查询到
            o.setCode("001");
            o.setName("基础冻藏盒");
            o.setRemark("备注");
        });
        freezingBoxInfoMapper.insert(dbFreezingBoxInfo);
        // 测试 code 不匹配
        freezingBoxInfoMapper.insert(cloneIgnoreId(dbFreezingBoxInfo, o -> o.setCode("002")));
        // 测试 name 不匹配
        freezingBoxInfoMapper.insert(cloneIgnoreId(dbFreezingBoxInfo, o -> o.setName("测试冻藏盒")));
        // 测试 remark 不匹配
        freezingBoxInfoMapper.insert(cloneIgnoreId(dbFreezingBoxInfo, o -> o.setRemark("测试啊啊啊")));
        // 准备参数
        FreezingBoxInfoExportReqVO reqVO = new FreezingBoxInfoExportReqVO();
        reqVO.setCode("001");
        reqVO.setName("基础冻藏盒");
        reqVO.setRemark("备注");

        // 调用
        List<FreezingBoxInfoDO> list = freezingBoxInfoService.getFreezingBoxInfoList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbFreezingBoxInfo, list.getFirst());
    }

}
