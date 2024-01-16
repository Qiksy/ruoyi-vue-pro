package cn.iocoder.yudao.module.system.controller.admin.printtemplate;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.system.controller.admin.printtemplate.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.printtemplate.PrintTemplateDO;
import cn.iocoder.yudao.module.system.service.printtemplate.PrintTemplateService;

@Tag(name = "管理后台 - 打印模板")
@RestController
@RequestMapping("/system/print-template")
@Validated
public class PrintTemplateController {

    @Resource
    private PrintTemplateService printTemplateService;

    @PostMapping("/create")
    @Operation(summary = "创建打印模板")
    @PreAuthorize("@ss.hasPermission('system:print-template:create')")
    public CommonResult<Long> createPrintTemplate(@Valid @RequestBody PrintTemplateSaveReqVO createReqVO) {
        return success(printTemplateService.createPrintTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新打印模板")
    @PreAuthorize("@ss.hasPermission('system:print-template:update')")
    public CommonResult<Boolean> updatePrintTemplate(@Valid @RequestBody PrintTemplateSaveReqVO updateReqVO) {
        printTemplateService.updatePrintTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除打印模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:print-template:delete')")
    public CommonResult<Boolean> deletePrintTemplate(@RequestParam("id") Long id) {
        printTemplateService.deletePrintTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得打印模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:print-template:query')")
    public CommonResult<PrintTemplateRespVO> getPrintTemplate(@RequestParam("id") Long id) {
        PrintTemplateDO printTemplate = printTemplateService.getPrintTemplate(id);
        return success(BeanUtils.toBean(printTemplate, PrintTemplateRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得打印模板分页")
    @PreAuthorize("@ss.hasPermission('system:print-template:query')")
    public CommonResult<PageResult<PrintTemplateRespVO>> getPrintTemplatePage(@Valid PrintTemplatePageReqVO pageReqVO) {
        PageResult<PrintTemplateDO> pageResult = printTemplateService.getPrintTemplatePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PrintTemplateRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出打印模板 Excel")
    @PreAuthorize("@ss.hasPermission('system:print-template:export')")
    @OperateLog(type = EXPORT)
    public void exportPrintTemplateExcel(@Valid PrintTemplatePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PrintTemplateDO> list = printTemplateService.getPrintTemplatePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "打印模板.xls", "数据", PrintTemplateRespVO.class,
                        BeanUtils.toBean(list, PrintTemplateRespVO.class));
    }

}