package cn.iocoder.yudao.module.sale.controller.admin.productioninfo;

import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
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




import cn.iocoder.yudao.module.sale.controller.admin.productioninfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioninfo.ProductionInfoDO;
import cn.iocoder.yudao.module.sale.service.productioninfo.ProductionInfoService;

@Tag(name = "管理后台 - 物料信息")
@RestController
@RequestMapping("/sale/production-info")
@Validated
public class ProductionInfoController {

    @Resource
    private ProductionInfoService productionInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建物料信息")
    @PreAuthorize("@ss.hasPermission('sale:production-info:create')")
    public CommonResult<Long> createProductionInfo(@Valid @RequestBody ProductionInfoSaveReqVO createReqVO) {
        return success(productionInfoService.createProductionInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新物料信息")
    @PreAuthorize("@ss.hasPermission('sale:production-info:update')")
    public CommonResult<Boolean> updateProductionInfo(@Valid @RequestBody ProductionInfoSaveReqVO updateReqVO) {
        productionInfoService.updateProductionInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除物料信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:production-info:delete')")
    public CommonResult<Boolean> deleteProductionInfo(@RequestParam("id") Long id) {
        productionInfoService.deleteProductionInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得物料信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:production-info:query')")
    public CommonResult<ProductionInfoRespVO> getProductionInfo(@RequestParam("id") Long id) {
        ProductionInfoDO productionInfo = productionInfoService.getProductionInfo(id);
        return success(BeanUtils.toBean(productionInfo, ProductionInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得物料信息分页")
    @PreAuthorize("@ss.hasPermission('sale:production-info:query')")
    public CommonResult<PageResult<ProductionInfoRespVO>> getProductionInfoPage(@Valid ProductionInfoPageReqVO pageReqVO) {
        PageResult<ProductionInfoDO> pageResult = productionInfoService.getProductionInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProductionInfoRespVO.class));
    }


    //新增一个接口，用来显示和搜索产成品物料
    @GetMapping("/simple-list")
    @Operation(summary = "获得物料信息列表")
    //允许匿名访问
    @PreAuthenticated
    public CommonResult<List<ProductionInfoSimpleRespVO>> getProductionInfoList(@Valid ProductionInfoPageReqVO reqVO) {
        List<ProductionInfoDO> list = productionInfoService.getProductionInfoList(reqVO);

        List<ProductionInfoSimpleRespVO> result = new ArrayList<>();
        for (ProductionInfoDO productionInfoDO : list) {
            ProductionInfoSimpleRespVO temp = new ProductionInfoSimpleRespVO();
            temp.setText(productionInfoDO.getName());
            temp.setValue(productionInfoDO.getId());
            temp.setSpec(productionInfoDO.getSpec());
            result.add(temp);
        }
        return success(result);
    }



    @GetMapping("/export-excel")
    @Operation(summary = "导出物料信息 Excel")
    @PreAuthorize("@ss.hasPermission('sale:production-info:export')")
    
    public void exportProductionInfoExcel(@Valid ProductionInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProductionInfoDO> list = productionInfoService.getProductionInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "物料信息.xls", "数据", ProductionInfoRespVO.class,
                        BeanUtils.toBean(list, ProductionInfoRespVO.class));
    }

}