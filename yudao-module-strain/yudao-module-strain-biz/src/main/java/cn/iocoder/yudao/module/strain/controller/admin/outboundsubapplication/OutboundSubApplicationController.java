package cn.iocoder.yudao.module.strain.controller.admin.outboundsubapplication;

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

import cn.iocoder.yudao.module.strain.controller.admin.outboundsubapplication.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundsubapplication.OutboundSubApplicationDO;
import cn.iocoder.yudao.module.strain.service.outboundsubapplication.OutboundSubApplicationService;

@Tag(name = "管理后台 - 出库申请子")
@RestController
@RequestMapping("/strain/outbound-sub-application")
@Validated
public class OutboundSubApplicationController {

    @Resource
    private OutboundSubApplicationService outboundSubApplicationService;

    @PostMapping("/create")
    @Operation(summary = "创建出库申请子表")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:create')")
    public CommonResult<Long> createOutboundSubApplication(@Valid @RequestBody OutboundSubApplicationSaveReqVO createReqVO) {
        return success(outboundSubApplicationService.createOutboundSubApplication(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新出库申请子表")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:update')")
    public CommonResult<Boolean> updateOutboundSubApplication(@Valid @RequestBody OutboundSubApplicationSaveReqVO updateReqVO) {
        outboundSubApplicationService.updateOutboundSubApplication(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除出库申请子表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:delete')")
    public CommonResult<Boolean> deleteOutboundSubApplication(@RequestParam("id") Long id) {
        outboundSubApplicationService.deleteOutboundSubApplication(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得出库申请子表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:query')")
    public CommonResult<OutboundSubApplicationRespVO> getOutboundSubApplication(@RequestParam("id") Long id) {
        OutboundSubApplicationDO outboundSubApplication = outboundSubApplicationService.getOutboundSubApplication(id);
        return success(BeanUtils.toBean(outboundSubApplication, OutboundSubApplicationRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得出库申请子分页")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:query')")
    public CommonResult<PageResult<OutboundSubApplicationRespVO>> getOutboundSubApplicationPage(@Valid OutboundSubApplicationPageReqVO pageReqVO) {
        PageResult<OutboundSubApplicationDO> pageResult = outboundSubApplicationService.getOutboundSubApplicationPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OutboundSubApplicationRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出出库申请子 Excel")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:export')")
    @OperateLog(type = EXPORT)
    public void exportOutboundSubApplicationExcel(@Valid OutboundSubApplicationPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OutboundSubApplicationDO> list = outboundSubApplicationService.getOutboundSubApplicationPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "出库申请子.xls", "数据", OutboundSubApplicationRespVO.class,
                        BeanUtils.toBean(list, OutboundSubApplicationRespVO.class));
    }

}