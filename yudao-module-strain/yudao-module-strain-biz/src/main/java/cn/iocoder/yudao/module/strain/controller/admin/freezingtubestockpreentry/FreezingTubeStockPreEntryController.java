package cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry;

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

import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry.FreezingTubeStockPreEntryService;

@Tag(name = "管理后台 - 冷冻管库存预录入")
@RestController
@RequestMapping("/strain/freezing-tube-stock-pre-entry")
@Validated
public class FreezingTubeStockPreEntryController {

    @Resource
    private FreezingTubeStockPreEntryService freezingTubeStockPreEntryService;

    @PostMapping("/create")
    @Operation(summary = "创建冷冻管库存预录入")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:create')")
    public CommonResult<Collection<Long>> createFreezingTubeStockPreEntry(@Valid @RequestBody FreezingTubeStockPreEntrySaveReqVO createReqVO) {
        List<Long> ids = freezingTubeStockPreEntryService.createFreezingTubeStockPreEntry(createReqVO);
        return success(ids);
    }

    @PutMapping("/update")
    @Operation(summary = "更新冷冻管库存预录入")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:update')")
    public CommonResult<Boolean> updateFreezingTubeStockPreEntry(@Valid @RequestBody FreezingTubeStockPreEntryUpdateReqVO updateReqVO) {
        freezingTubeStockPreEntryService.updateFreezingTubeStockPreEntry(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除冷冻管库存预录入")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:delete')")
    public CommonResult<Boolean> deleteFreezingTubeStockPreEntry(@RequestParam("id") Long id) {
        freezingTubeStockPreEntryService.deleteFreezingTubeStockPreEntry(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得冷冻管库存预录入")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:query')")
    public CommonResult<FreezingTubeStockPreEntryRespVO> getFreezingTubeStockPreEntry(@RequestParam("id") Long id) {
        FreezingTubeStockPreEntryDO freezingTubeStockPreEntry = freezingTubeStockPreEntryService.getFreezingTubeStockPreEntry(id);
        return success(BeanUtils.toBean(freezingTubeStockPreEntry, FreezingTubeStockPreEntryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得冷冻管库存预录入分页")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:query')")
    public CommonResult<PageResult<FreezingTubeStockPreEntryRespVO>> getFreezingTubeStockPreEntryPage(@Valid FreezingTubeStockPreEntryPageReqVO pageReqVO) {
        PageResult<FreezingTubeStockPreEntryRespVO> pageResult = freezingTubeStockPreEntryService.getFreezingTubeStockPreEntryPage2(pageReqVO);
        return success(pageResult);
    }


    //新的分页查询
    @GetMapping("/page2")
    @Operation(summary = "获得样品分页（排除正在审核中的样品）")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:query')")
    public CommonResult<PageResult<FreezingTubeStockPreEntryRespVO>> getFreezingTubeStockPreEntryPage2(@Valid FreezingTubeStockPreEntryPageReqVO pageReqVO) {
        PageResult<FreezingTubeStockPreEntryRespVO> pageResult = freezingTubeStockPreEntryService.getFreezingTubeStockPreEntryPage3(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出冷冻管库存预录入 Excel")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:export')")
    @OperateLog(type = EXPORT)
    public void exportFreezingTubeStockPreEntryExcel(@Valid FreezingTubeStockPreEntryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FreezingTubeStockPreEntryDO> list = freezingTubeStockPreEntryService.getFreezingTubeStockPreEntryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "冷冻管库存预录入.xls", "数据", FreezingTubeStockPreEntryRespVO.class,
                        BeanUtils.toBean(list, FreezingTubeStockPreEntryRespVO.class));
    }

}