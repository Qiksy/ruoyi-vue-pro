package cn.iocoder.yudao.module.sale.controller.admin.prodlineinfo;

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




import cn.iocoder.yudao.module.sale.controller.admin.prodlineinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.prodlineinfo.ProdlineInfoDO;
import cn.iocoder.yudao.module.sale.service.prodlineinfo.ProdlineInfoService;

@Tag(name = "管理后台 - 产品线")
@RestController
@RequestMapping("/sale/prodline-info")
@Validated
public class ProdlineInfoController {

    @Resource
    private ProdlineInfoService prodlineInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建产品线")
    @PreAuthorize("@ss.hasPermission('sale:prodline-info:create')")
    public CommonResult<Long> createProdlineInfo(@Valid @RequestBody ProdlineInfoSaveReqVO createReqVO) {
        return success(prodlineInfoService.createProdlineInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品线")
    @PreAuthorize("@ss.hasPermission('sale:prodline-info:update')")
    public CommonResult<Boolean> updateProdlineInfo(@Valid @RequestBody ProdlineInfoSaveReqVO updateReqVO) {
        prodlineInfoService.updateProdlineInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品线")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:prodline-info:delete')")
    public CommonResult<Boolean> deleteProdlineInfo(@RequestParam("id") Long id) {
        prodlineInfoService.deleteProdlineInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品线")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:prodline-info:query')")
    public CommonResult<ProdlineInfoRespVO> getProdlineInfo(@RequestParam("id") Long id) {
        ProdlineInfoDO prodlineInfo = prodlineInfoService.getProdlineInfo(id);
        return success(BeanUtils.toBean(prodlineInfo, ProdlineInfoRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得产品线列表")
    @PreAuthorize("@ss.hasPermission('sale:prodline-info:query')")
    public CommonResult<List<ProdlineInfoRespVO>> getProdlineInfoList(@Valid ProdlineInfoListReqVO listReqVO) {
        List<ProdlineInfoDO> list = prodlineInfoService.getProdlineInfoList(listReqVO);
        return success(BeanUtils.toBean(list, ProdlineInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品线 Excel")
    @PreAuthorize("@ss.hasPermission('sale:prodline-info:export')")
    
    public void exportProdlineInfoExcel(@Valid ProdlineInfoListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<ProdlineInfoDO> list = prodlineInfoService.getProdlineInfoList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "产品线.xls", "数据", ProdlineInfoRespVO.class,
                        BeanUtils.toBean(list, ProdlineInfoRespVO.class));
    }

}