package cn.iocoder.yudao.module.sale.controller.admin.productioncompeteinfo;

import cn.iocoder.yudao.module.sale.dal.dataobject.productioninfo.ProductionInfoDO;
import cn.iocoder.yudao.module.sale.service.productioninfo.ProductionInfoService;
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
import java.util.stream.Collectors;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.sale.controller.admin.productioncompeteinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioncompeteinfo.ProductionCompeteInfoDO;
import cn.iocoder.yudao.module.sale.service.productioncompeteinfo.ProductionCompeteInfoService;

@Tag(name = "管理后台 - 工厂竞品管理")
@RestController
@RequestMapping("/sale/production-compete-info")
@Validated
public class ProductionCompeteInfoController {

    @Resource
    private ProductionCompeteInfoService productionCompeteInfoService;

    @Resource
    private ProductionInfoService productionInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建工厂竞品管理")
    @PreAuthorize("@ss.hasPermission('sale:production-compete-info:create')")
    public CommonResult<Long> createProductionCompeteInfo(@Valid @RequestBody ProductionCompeteInfoSaveReqVO createReqVO) {
        return success(productionCompeteInfoService.createProductionCompeteInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工厂竞品管理")
    @PreAuthorize("@ss.hasPermission('sale:production-compete-info:update')")
    public CommonResult<Boolean> updateProductionCompeteInfo(@Valid @RequestBody ProductionCompeteInfoSaveReqVO updateReqVO) {
        productionCompeteInfoService.updateProductionCompeteInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除工厂竞品管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:production-compete-info:delete')")
    public CommonResult<Boolean> deleteProductionCompeteInfo(@RequestParam("id") Long id) {
        productionCompeteInfoService.deleteProductionCompeteInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得工厂竞品管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:production-compete-info:query')")
    public CommonResult<ProductionCompeteInfoRespVO> getProductionCompeteInfo(@RequestParam("id") Long id) {
        ProductionCompeteInfoDO productionCompeteInfo = productionCompeteInfoService.getProductionCompeteInfo(id);
        return success(BeanUtils.toBean(productionCompeteInfo, ProductionCompeteInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得工厂竞品管理分页")
    @PreAuthorize("@ss.hasPermission('sale:production-compete-info:query')")
    public CommonResult<PageResult<ProductionCompeteInfoRespVO>> getProductionCompeteInfoPage(@Valid ProductionCompeteInfoPageReqVO pageReqVO) {
        PageResult<ProductionCompeteInfoDO> pageResult = productionCompeteInfoService.getProductionCompeteInfoPage(pageReqVO);
        // 顺便吧物料名称也查询出来
        List<Long> productionIds = pageResult.getList().stream().map(ProductionCompeteInfoDO::getProductionId).distinct().collect(Collectors.toList());
        Map<Long, ProductionInfoDO> productionMap = productionInfoService.getProductionMap(productionIds);
        List<ProductionCompeteInfoRespVO> list = new ArrayList<>(pageResult.getList().size());
        for (ProductionCompeteInfoDO productionCompeteInfo : pageResult.getList()) {
            ProductionCompeteInfoRespVO respVO = BeanUtils.toBean(productionCompeteInfo, ProductionCompeteInfoRespVO.class);
            respVO.setProductionName(productionMap.get(productionCompeteInfo.getProductionId()).getName());
            list.add(respVO);
        }

        return success(new PageResult<>(list, pageResult.getTotal()));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工厂竞品管理 Excel")
    @PreAuthorize("@ss.hasPermission('sale:production-compete-info:export')")
    @OperateLog(type = EXPORT)
    public void exportProductionCompeteInfoExcel(@Valid ProductionCompeteInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProductionCompeteInfoDO> list = productionCompeteInfoService.getProductionCompeteInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "工厂竞品管理.xls", "数据", ProductionCompeteInfoRespVO.class,
                        BeanUtils.toBean(list, ProductionCompeteInfoRespVO.class));
    }

}