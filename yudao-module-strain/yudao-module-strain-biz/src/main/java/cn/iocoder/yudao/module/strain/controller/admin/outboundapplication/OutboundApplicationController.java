package cn.iocoder.yudao.module.strain.controller.admin.outboundapplication;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
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




import cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundapplication.OutboundApplicationDO;
import cn.iocoder.yudao.module.strain.service.outboundapplication.OutboundApplicationService;

@Tag(name = "管理后台 - 出库申请")
@RestController
@RequestMapping("/strain/outbound-application")
@Validated
public class OutboundApplicationController {

    @Resource
    private OutboundApplicationService outboundApplicationService;

    @PostMapping("/create")
    @Operation(summary = "保存出库申请")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:create')")
    public CommonResult<Long> createOutboundApplication(@Valid @RequestBody OutboundApplicationCreateReqVO createReqVO) {
        return success(outboundApplicationService.createOutboundApplication(createReqVO));
    }


    @PostMapping("/save-and-submit")
    @Operation(summary = "保存和申请出库单")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:create')")
    public CommonResult<Long> createAndApplyOutboundApplication(@Valid @RequestBody OutboundApplicationCreateReqVO createReqVO) {
        return success(outboundApplicationService.createAndApplyOutboundApplication(createReqVO));
    }


    @PutMapping("/submit")
    @Operation(summary = "提交审批")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:update')")
    public CommonResult<Boolean> submit(@NotNull @RequestParam("id") Long id){

        return success(outboundApplicationService.submit(id));
    }


    @PutMapping("/update")
    @Operation(summary = "更新出库申请")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:update')")
    public CommonResult<Boolean> updateOutboundApplication(@Valid @RequestBody OutboundApplicationCreateReqVO updateReqVO) {
        outboundApplicationService.updateOutboundApplication(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-and-submit")
    @Operation(summary = "更新出库并提交申请")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:update')")
    public CommonResult<Boolean> updateAndSubmitOutboundApplication(@Valid @RequestBody OutboundApplicationCreateReqVO updateReqVO) {
        outboundApplicationService.updateAndSubmitOutboundApplication(updateReqVO);
        return success(true);
    }

    @PostMapping("/approve")
    @Operation(summary = "审批出库")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:update')")
    public CommonResult<Boolean> approveOutboundApplication(@Valid @RequestBody OutboundApplicationCreateReqVO updateReqVO) {
        outboundApplicationService.approveOutboundApplication(updateReqVO);
        return success(true);
    }


    @DeleteMapping("/delete")
    @Operation(summary = "删除出库申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:delete')")
    public CommonResult<Boolean> deleteOutboundApplication(@RequestParam("id") Long id) {
        outboundApplicationService.deleteOutboundApplication(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得出库申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:query')")
    public CommonResult<OutboundApplicationRespVO> getOutboundApplication(@RequestParam("id") Long id) {
//        OutboundApplicationDO outboundApplication = outboundApplicationService.getOutboundApplication(id);
        OutboundApplicationRespVO outboundApplication = outboundApplicationService.getOutboundApplicationVO(id);
        return success(outboundApplication);
    }

    @GetMapping("/page")
    @Operation(summary = "获得出库申请分页")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:query')")
    public CommonResult<PageResult<OutboundApplicationRespVO>> getOutboundApplicationPage(@Valid OutboundApplicationPageReqVO pageReqVO) {
        PageResult<OutboundApplicationDO> pageResult = outboundApplicationService.getOutboundApplicationPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OutboundApplicationRespVO.class));
    }

    @GetMapping("/self-page")
    @Operation(summary = "获得自己申请的出库申请分页")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:query')")
    public CommonResult<PageResult<OutboundApplicationRespVO>> getOutboundApplicationPageSelf(@Valid OutboundApplicationPageReqVO pageReqVO) {
        return success(outboundApplicationService.getOutboundApplicationPageSelf(pageReqVO));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出出库申请 Excel")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:export')")
    
    public void exportOutboundApplicationExcel(@Valid OutboundApplicationPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OutboundApplicationDO> list = outboundApplicationService.getOutboundApplicationPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "出库申请.xls", "数据", OutboundApplicationRespVO.class,
                        BeanUtils.toBean(list, OutboundApplicationRespVO.class));
    }


    @PutMapping("/add-subList")
    @Operation(summary = "新增申请单的菌种数据")
    @PreAuthorize("@ss.hasPermission('strain:outbound-application:update')")
    public CommonResult<Boolean> addSubList(@Valid @RequestBody OutboundApplicationSubInfoUpdateReqVO updateReqVO) {
        outboundApplicationService.addSubList(updateReqVO);
        return success(true);
    }
}