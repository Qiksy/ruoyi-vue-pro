package cn.iocoder.yudao.module.sale.controller.admin.declinewarningsub;

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

import cn.iocoder.yudao.module.sale.controller.admin.declinewarningsub.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarningsub.DeclineWarningSubDO;
import cn.iocoder.yudao.module.sale.service.declinewarningsub.DeclineWarningSubService;

@Tag(name = "管理后台 - 销量下降预警子表")
@RestController
@RequestMapping("/sale/decline-warning-sub")
@Validated
public class DeclineWarningSubController {

    @Resource
    private DeclineWarningSubService declineWarningSubService;

    @PostMapping("/create")
    @Operation(summary = "创建销量下降预警子表")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:create')")
    public CommonResult<Long> createDeclineWarningSub(@Valid @RequestBody DeclineWarningSubSaveReqVO createReqVO) {
        return success(declineWarningSubService.createDeclineWarningSub(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新销量下降预警子表")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:update')")
    public CommonResult<Boolean> updateDeclineWarningSub(@Valid @RequestBody DeclineWarningSubSaveReqVO updateReqVO) {
        declineWarningSubService.updateDeclineWarningSub(updateReqVO);
        return success(true);
    }


    @Operation(summary = "批量更新")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:update')")
    @PostMapping("/batch-update")
    public CommonResult<Boolean> updateBatch(@Valid @RequestBody List<DeclineWarningSubBatchSaveReqVO> updateReqVO) {
        declineWarningSubService.updateBatch(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除销量下降预警子表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:delete')")
    public CommonResult<Boolean> deleteDeclineWarningSub(@RequestParam("id") Long id) {
        declineWarningSubService.deleteDeclineWarningSub(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得销量下降预警子表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:query')")
    public CommonResult<DeclineWarningSubRespVO> getDeclineWarningSub(@RequestParam("id") Long id) {
        DeclineWarningSubDO declineWarningSub = declineWarningSubService.getDeclineWarningSub(id);
        return success(BeanUtils.toBean(declineWarningSub, DeclineWarningSubRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得销量下降预警子表分页")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:query')")
    public CommonResult<PageResult<DeclineWarningSubRespVO>> getDeclineWarningSubPage(@Valid DeclineWarningSubPageReqVO pageReqVO) {
        PageResult<DeclineWarningSubDO> pageResult = declineWarningSubService.getDeclineWarningSubPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DeclineWarningSubRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出销量下降预警子表 Excel")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:export')")
    @OperateLog(type = EXPORT)
    public void exportDeclineWarningSubExcel(@Valid DeclineWarningSubPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeclineWarningSubDO> list = declineWarningSubService.getDeclineWarningSubPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "销量下降预警子表.xls", "数据", DeclineWarningSubRespVO.class,
                        BeanUtils.toBean(list, DeclineWarningSubRespVO.class));
    }

}