package cn.iocoder.yudao.module.sale.controller.admin.declinewarning;

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

import cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarning.DeclineWarningDO;
import cn.iocoder.yudao.module.sale.service.declinewarning.DeclineWarningService;

@Tag(name = "管理后台 - 销量预警")
@RestController
@RequestMapping("/sale/decline-warning")
@Validated
public class DeclineWarningController {

    @Resource
    private DeclineWarningService declineWarningService;

    @PostMapping("/create")
    @Operation(summary = "创建销量预警")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:create')")
    public CommonResult<Long> createDeclineWarning(@Valid @RequestBody DeclineWarningSaveReqVO createReqVO) {
        return success(declineWarningService.createDeclineWarning(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新销量预警")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:update')")
    public CommonResult<Boolean> updateDeclineWarning(@Valid @RequestBody DeclineWarningSaveReqVO updateReqVO) {
        declineWarningService.updateDeclineWarning(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除销量预警")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:delete')")
    public CommonResult<Boolean> deleteDeclineWarning(@RequestParam("id") Long id) {
        declineWarningService.deleteDeclineWarning(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得销量预警")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:query')")
    public CommonResult<DeclineWarningRespVO> getDeclineWarning(@RequestParam("id") Long id) {
        DeclineWarningDO declineWarning = declineWarningService.getDeclineWarning(id);
        return success(BeanUtils.toBean(declineWarning, DeclineWarningRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得销量预警分页")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:query')")
    public CommonResult<PageResult<DeclineWarningRespVO>> getDeclineWarningPage(@Valid DeclineWarningPageReqVO pageReqVO) {
        PageResult<DeclineWarningDO> pageResult = declineWarningService.getDeclineWarningPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DeclineWarningRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出销量预警 Excel")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:export')")
    @OperateLog(type = EXPORT)
    public void exportDeclineWarningExcel(@Valid DeclineWarningPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeclineWarningDO> list = declineWarningService.getDeclineWarningPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "销量预警.xls", "数据", DeclineWarningRespVO.class,
                        BeanUtils.toBean(list, DeclineWarningRespVO.class));
    }

}