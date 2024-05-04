package cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail;

import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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




import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.customersalesdetail.CustomerSalesDetailDO;
import cn.iocoder.yudao.module.sale.service.customersalesdetail.CustomerSalesDetailService;

@Tag(name = "管理后台 - 客户销售明细")
@RestController
@RequestMapping("/sale/customer-sales-detail")
@Validated
public class CustomerSalesDetailController {

    @Resource
    private CustomerSalesDetailService customerSalesDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建客户销售明细")
    @PreAuthorize("@ss.hasPermission('sale:customer-sales-detail:create')")
    public CommonResult<Long> createCustomerSalesDetail(@Valid @RequestBody CustomerSalesDetailSaveReqVO createReqVO) {
        return success(customerSalesDetailService.createCustomerSalesDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户销售明细")
    @PreAuthorize("@ss.hasPermission('sale:customer-sales-detail:update')")
    public CommonResult<Boolean> updateCustomerSalesDetail(@Valid @RequestBody CustomerSalesDetailSaveReqVO updateReqVO) {
        customerSalesDetailService.updateCustomerSalesDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客户销售明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:customer-sales-detail:delete')")
    public CommonResult<Boolean> deleteCustomerSalesDetail(@RequestParam("id") Long id) {
        customerSalesDetailService.deleteCustomerSalesDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客户销售明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:customer-sales-detail:query')")
    public CommonResult<CustomerSalesDetailRespVO> getCustomerSalesDetail(@RequestParam("id") Long id) {
        CustomerSalesDetailDO customerSalesDetail = customerSalesDetailService.getCustomerSalesDetail(id);
        return success(BeanUtils.toBean(customerSalesDetail, CustomerSalesDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得客户销售明细分页")
    @PreAuthorize("@ss.hasPermission('sale:customer-sales-detail:query')")
    public CommonResult<PageResult<CustomerSalesDetailRespVO>> getCustomerSalesDetailPage(@Valid CustomerSalesDetailPageReqVO pageReqVO) {
        PageResult<CustomerSalesDetailDO> pageResult = customerSalesDetailService.getCustomerSalesDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CustomerSalesDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户销售明细 Excel")
    @PreAuthorize("@ss.hasPermission('sale:customer-sales-detail:export')")
    
    public void exportCustomerSalesDetailExcel(@Valid CustomerSalesDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CustomerSalesDetailDO> list = customerSalesDetailService.getCustomerSalesDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "客户销售明细.xls", "数据", CustomerSalesDetailRespVO.class,
                        BeanUtils.toBean(list, CustomerSalesDetailRespVO.class));
    }


    @PostMapping("/sync")
    @Operation(summary = "同步客户销售明细")
//    @PreAuthorize("@ss.hasPermission('sale:customer-sales-detail:create')")
    @PermitAll
    public CommonResult<Boolean> syncCustomerSalesDetail(@Valid @RequestBody CustomerSalesDetailSyncReqVO syncReqVO) {
        customerSalesDetailService.syncCustomerSalesDetail(syncReqVO);
        return success(true);
    }


    @GetMapping("/analysis/page")
    @Operation(summary = "获得客户下降分页")
//    @PreAuthorize("@ss.hasPermission('sale:customer-sales-detail:query')")
    @PermitAll
    public CommonResult<PageResult<CustomerSalesDetailAnalysisRespVO>> getCustomerSalesDetailAnalysisPage(@Valid CustomerSalesDetailPageReqVO pageReqVO) {
        PageResult<CustomerSalesDetailAnalysisRespVO> pageResult = customerSalesDetailService.getCustomerSalesDetailAnalysisPage(pageReqVO);
        return success(pageResult);
    }

}