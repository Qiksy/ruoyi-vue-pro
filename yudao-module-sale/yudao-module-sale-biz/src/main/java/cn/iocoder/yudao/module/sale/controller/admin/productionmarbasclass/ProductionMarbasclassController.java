package cn.iocoder.yudao.module.sale.controller.admin.productionmarbasclass;

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




import cn.iocoder.yudao.module.sale.controller.admin.productionmarbasclass.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarbasclass.ProductionMarbasclassDO;
import cn.iocoder.yudao.module.sale.service.productionmarbasclass.ProductionMarbasclassService;

@Tag(name = "管理后台 - 物料分类")
@RestController
@RequestMapping("/sale/production-marbasclass")
@Validated
public class ProductionMarbasclassController {

    @Resource
    private ProductionMarbasclassService productionMarbasclassService;

    @PostMapping("/create")
    @Operation(summary = "创建物料分类")
    @PreAuthorize("@ss.hasPermission('sale:production-marbasclass:create')")
    public CommonResult<Long> createProductionMarbasclass(@Valid @RequestBody ProductionMarbasclassSaveReqVO createReqVO) {
        return success(productionMarbasclassService.createProductionMarbasclass(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新物料分类")
    @PreAuthorize("@ss.hasPermission('sale:production-marbasclass:update')")
    public CommonResult<Boolean> updateProductionMarbasclass(@Valid @RequestBody ProductionMarbasclassSaveReqVO updateReqVO) {
        productionMarbasclassService.updateProductionMarbasclass(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除物料分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:production-marbasclass:delete')")
    public CommonResult<Boolean> deleteProductionMarbasclass(@RequestParam("id") Long id) {
        productionMarbasclassService.deleteProductionMarbasclass(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得物料分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:production-marbasclass:query')")
    public CommonResult<ProductionMarbasclassRespVO> getProductionMarbasclass(@RequestParam("id") Long id) {
        ProductionMarbasclassDO productionMarbasclass = productionMarbasclassService.getProductionMarbasclass(id);
        return success(BeanUtils.toBean(productionMarbasclass, ProductionMarbasclassRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得物料分类列表")
    @PreAuthorize("@ss.hasPermission('sale:production-marbasclass:query')")
    public CommonResult<List<ProductionMarbasclassRespVO>> getProductionMarbasclassList(@Valid ProductionMarbasclassListReqVO listReqVO) {
        List<ProductionMarbasclassDO> list = productionMarbasclassService.getProductionMarbasclassList(listReqVO);
        return success(BeanUtils.toBean(list, ProductionMarbasclassRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出物料分类 Excel")
    @PreAuthorize("@ss.hasPermission('sale:production-marbasclass:export')")
    
    public void exportProductionMarbasclassExcel(@Valid ProductionMarbasclassListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<ProductionMarbasclassDO> list = productionMarbasclassService.getProductionMarbasclassList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "物料分类.xls", "数据", ProductionMarbasclassRespVO.class,
                        BeanUtils.toBean(list, ProductionMarbasclassRespVO.class));
    }

}