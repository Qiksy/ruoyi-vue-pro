package cn.iocoder.yudao.module.sale.controller.admin.productionmarsaleclass;

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




import cn.iocoder.yudao.module.sale.controller.admin.productionmarsaleclass.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarsaleclass.ProductionMarsaleclassDO;
import cn.iocoder.yudao.module.sale.service.productionmarsaleclass.ProductionMarsaleclassService;

@Tag(name = "管理后台 - 销售分类")
@RestController
@RequestMapping("/sale/production-marsaleclass")
@Validated
public class ProductionMarsaleclassController {

    @Resource
    private ProductionMarsaleclassService productionMarsaleclassService;

    @PostMapping("/create")
    @Operation(summary = "创建销售分类")
    @PreAuthorize("@ss.hasPermission('sale:production-marsaleclass:create')")
    public CommonResult<Long> createProductionMarsaleclass(@Valid @RequestBody ProductionMarsaleclassSaveReqVO createReqVO) {
        return success(productionMarsaleclassService.createProductionMarsaleclass(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新销售分类")
    @PreAuthorize("@ss.hasPermission('sale:production-marsaleclass:update')")
    public CommonResult<Boolean> updateProductionMarsaleclass(@Valid @RequestBody ProductionMarsaleclassSaveReqVO updateReqVO) {
        productionMarsaleclassService.updateProductionMarsaleclass(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除销售分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:production-marsaleclass:delete')")
    public CommonResult<Boolean> deleteProductionMarsaleclass(@RequestParam("id") Long id) {
        productionMarsaleclassService.deleteProductionMarsaleclass(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得销售分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:production-marsaleclass:query')")
    public CommonResult<ProductionMarsaleclassRespVO> getProductionMarsaleclass(@RequestParam("id") Long id) {
        ProductionMarsaleclassDO productionMarsaleclass = productionMarsaleclassService.getProductionMarsaleclass(id);
        return success(BeanUtils.toBean(productionMarsaleclass, ProductionMarsaleclassRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得销售分类列表")
    @PreAuthorize("@ss.hasPermission('sale:production-marsaleclass:query')")
    public CommonResult<List<ProductionMarsaleclassRespVO>> getProductionMarsaleclassList(@Valid ProductionMarsaleclassListReqVO listReqVO) {
        List<ProductionMarsaleclassDO> list = productionMarsaleclassService.getProductionMarsaleclassList(listReqVO);
        return success(BeanUtils.toBean(list, ProductionMarsaleclassRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出销售分类 Excel")
    @PreAuthorize("@ss.hasPermission('sale:production-marsaleclass:export')")
    
    public void exportProductionMarsaleclassExcel(@Valid ProductionMarsaleclassListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<ProductionMarsaleclassDO> list = productionMarsaleclassService.getProductionMarsaleclassList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "销售分类.xls", "数据", ProductionMarsaleclassRespVO.class,
                        BeanUtils.toBean(list, ProductionMarsaleclassRespVO.class));
    }

}