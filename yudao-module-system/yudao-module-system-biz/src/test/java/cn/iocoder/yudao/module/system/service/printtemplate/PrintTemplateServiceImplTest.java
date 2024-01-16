package cn.iocoder.yudao.module.system.service.printtemplate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.system.controller.admin.printtemplate.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.printtemplate.PrintTemplateDO;
import cn.iocoder.yudao.module.system.dal.mysql.printtemplate.PrintTemplateMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link PrintTemplateServiceImpl} 的单元测试类
 *
 * @author 播恩超级管理员
 */
@Import(PrintTemplateServiceImpl.class)
public class PrintTemplateServiceImplTest extends BaseDbUnitTest {

    @Resource
    private PrintTemplateServiceImpl printTemplateService;

    @Resource
    private PrintTemplateMapper printTemplateMapper;

    @Test
    public void testCreatePrintTemplate_success() {
        // 准备参数
        PrintTemplateSaveReqVO createReqVO = randomPojo(PrintTemplateSaveReqVO.class).setId(null);

        // 调用
        Long printTemplateId = printTemplateService.createPrintTemplate(createReqVO);
        // 断言
        assertNotNull(printTemplateId);
        // 校验记录的属性是否正确
        PrintTemplateDO printTemplate = printTemplateMapper.selectById(printTemplateId);
        assertPojoEquals(createReqVO, printTemplate, "id");
    }

    @Test
    public void testUpdatePrintTemplate_success() {
        // mock 数据
        PrintTemplateDO dbPrintTemplate = randomPojo(PrintTemplateDO.class);
        printTemplateMapper.insert(dbPrintTemplate);// @Sql: 先插入出一条存在的数据
        // 准备参数
        PrintTemplateSaveReqVO updateReqVO = randomPojo(PrintTemplateSaveReqVO.class, o -> {
            o.setId(dbPrintTemplate.getId()); // 设置更新的 ID
        });

        // 调用
        printTemplateService.updatePrintTemplate(updateReqVO);
        // 校验是否更新正确
        PrintTemplateDO printTemplate = printTemplateMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, printTemplate);
    }

    @Test
    public void testUpdatePrintTemplate_notExists() {
        // 准备参数
        PrintTemplateSaveReqVO updateReqVO = randomPojo(PrintTemplateSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> printTemplateService.updatePrintTemplate(updateReqVO), PRINT_TEMPLATE_NOT_EXISTS);
    }

    @Test
    public void testDeletePrintTemplate_success() {
        // mock 数据
        PrintTemplateDO dbPrintTemplate = randomPojo(PrintTemplateDO.class);
        printTemplateMapper.insert(dbPrintTemplate);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbPrintTemplate.getId();

        // 调用
        printTemplateService.deletePrintTemplate(id);
       // 校验数据不存在了
       assertNull(printTemplateMapper.selectById(id));
    }

    @Test
    public void testDeletePrintTemplate_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> printTemplateService.deletePrintTemplate(id), PRINT_TEMPLATE_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPrintTemplatePage() {
       // mock 数据
       PrintTemplateDO dbPrintTemplate = randomPojo(PrintTemplateDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setTemplateContent(null);
           o.setIsSystemDefault(null);
           o.setCreateTime(null);
           o.setRemark(null);
       });
       printTemplateMapper.insert(dbPrintTemplate);
       // 测试 name 不匹配
       printTemplateMapper.insert(cloneIgnoreId(dbPrintTemplate, o -> o.setName(null)));
       // 测试 templateContent 不匹配
       printTemplateMapper.insert(cloneIgnoreId(dbPrintTemplate, o -> o.setTemplateContent(null)));
       // 测试 isSystemDefault 不匹配
       printTemplateMapper.insert(cloneIgnoreId(dbPrintTemplate, o -> o.setIsSystemDefault(null)));
       // 测试 createTime 不匹配
       printTemplateMapper.insert(cloneIgnoreId(dbPrintTemplate, o -> o.setCreateTime(null)));
       // 测试 remark 不匹配
       printTemplateMapper.insert(cloneIgnoreId(dbPrintTemplate, o -> o.setRemark(null)));
       // 准备参数
       PrintTemplatePageReqVO reqVO = new PrintTemplatePageReqVO();
       reqVO.setName(null);
       reqVO.setTemplateContent(null);
       reqVO.setIsSystemDefault(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setRemark(null);

       // 调用
       PageResult<PrintTemplateDO> pageResult = printTemplateService.getPrintTemplatePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbPrintTemplate, pageResult.getList().get(0));
    }

}