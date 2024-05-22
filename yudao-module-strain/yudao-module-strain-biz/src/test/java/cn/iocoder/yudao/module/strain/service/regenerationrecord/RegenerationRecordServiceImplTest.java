package cn.iocoder.yudao.module.strain.service.regenerationrecord;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.strain.controller.admin.regenerationrecord.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.regenerationrecord.RegenerationRecordDO;
import cn.iocoder.yudao.module.strain.dal.mysql.regenerationrecord.RegenerationRecordMapper;
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
 * {@link RegenerationRecordServiceImpl} 的单元测试类
 *
 * @author 超级管理员
 */
@Import(RegenerationRecordServiceImpl.class)
public class RegenerationRecordServiceImplTest extends BaseDbUnitTest {

    @Resource
    private RegenerationRecordServiceImpl regenerationRecordService;

    @Resource
    private RegenerationRecordMapper regenerationRecordMapper;

    @Test
    public void testCreateRegenerationRecord_success() {
        // 准备参数
        RegenerationRecordSaveReqVO createReqVO = randomPojo(RegenerationRecordSaveReqVO.class).setId(null);

        // 调用
        Long regenerationRecordId = regenerationRecordService.createRegenerationRecord(createReqVO);
        // 断言
        assertNotNull(regenerationRecordId);
        // 校验记录的属性是否正确
        RegenerationRecordDO regenerationRecord = regenerationRecordMapper.selectById(regenerationRecordId);
        assertPojoEquals(createReqVO, regenerationRecord, "id");
    }

    @Test
    public void testUpdateRegenerationRecord_success() {
        // mock 数据
        RegenerationRecordDO dbRegenerationRecord = randomPojo(RegenerationRecordDO.class);
        regenerationRecordMapper.insert(dbRegenerationRecord);// @Sql: 先插入出一条存在的数据
        // 准备参数
        RegenerationRecordSaveReqVO updateReqVO = randomPojo(RegenerationRecordSaveReqVO.class, o -> {
            o.setId(dbRegenerationRecord.getId()); // 设置更新的 ID
        });

        // 调用
        regenerationRecordService.updateRegenerationRecord(updateReqVO);
        // 校验是否更新正确
        RegenerationRecordDO regenerationRecord = regenerationRecordMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, regenerationRecord);
    }

    @Test
    public void testUpdateRegenerationRecord_notExists() {
        // 准备参数
        RegenerationRecordSaveReqVO updateReqVO = randomPojo(RegenerationRecordSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> regenerationRecordService.updateRegenerationRecord(updateReqVO), REGENERATION_RECORD_NOT_EXISTS);
    }

    @Test
    public void testDeleteRegenerationRecord_success() {
        // mock 数据
        RegenerationRecordDO dbRegenerationRecord = randomPojo(RegenerationRecordDO.class);
        regenerationRecordMapper.insert(dbRegenerationRecord);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbRegenerationRecord.getId();

        // 调用
        regenerationRecordService.deleteRegenerationRecord(id);
       // 校验数据不存在了
       assertNull(regenerationRecordMapper.selectById(id));
    }

    @Test
    public void testDeleteRegenerationRecord_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> regenerationRecordService.deleteRegenerationRecord(id), REGENERATION_RECORD_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetRegenerationRecordPage() {
       // mock 数据
       RegenerationRecordDO dbRegenerationRecord = randomPojo(RegenerationRecordDO.class, o -> { // 等会查询到
           o.setSpecimenId(null);
           o.setContent(null);
           o.setCreateTime(null);
           o.setRemark(null);
       });
       regenerationRecordMapper.insert(dbRegenerationRecord);
       // 测试 specimenId 不匹配
       regenerationRecordMapper.insert(cloneIgnoreId(dbRegenerationRecord, o -> o.setSpecimenId(null)));
       // 测试 content 不匹配
       regenerationRecordMapper.insert(cloneIgnoreId(dbRegenerationRecord, o -> o.setContent(null)));
       // 测试 createTime 不匹配
       regenerationRecordMapper.insert(cloneIgnoreId(dbRegenerationRecord, o -> o.setCreateTime(null)));
       // 测试 remark 不匹配
       regenerationRecordMapper.insert(cloneIgnoreId(dbRegenerationRecord, o -> o.setRemark(null)));
       // 准备参数
       RegenerationRecordPageReqVO reqVO = new RegenerationRecordPageReqVO();
       reqVO.setSpecimenId(null);
       reqVO.setContent(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);

       // 调用
       PageResult<RegenerationRecordDO> pageResult = regenerationRecordService.getRegenerationRecordPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbRegenerationRecord, pageResult.getList().get(0));
    }

}