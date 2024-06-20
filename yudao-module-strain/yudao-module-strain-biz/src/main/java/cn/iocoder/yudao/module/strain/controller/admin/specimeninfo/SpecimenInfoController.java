package cn.iocoder.yudao.module.strain.controller.admin.specimeninfo;

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




import cn.iocoder.yudao.module.strain.controller.admin.specimeninfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.specimen.SpecimenInfoDO;
import cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry.SpecimenInfoService;

@Tag(name = "管理后台 - 样品信息录入")
@RestController
@RequestMapping("/strain/freezing-tube-stock-pre-entry")
@Validated
public class SpecimenInfoController {

    @Resource
    private SpecimenInfoService specimenInfoService;

    @PostMapping("/create")
    @Operation(summary = "新增样品")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:create')")
    public CommonResult<Collection<Long>> createFreezingTubeStockPreEntry(@Valid @RequestBody SpecimenInfoSaveReqVO createReqVO) {
        List<Long> ids = specimenInfoService.createSpecimenInfo(createReqVO);
        return success(ids);
    }

    @PutMapping("/update")
    @Operation(summary = "更新冷冻管库存预录入")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:update')")
    public CommonResult<Boolean> updateFreezingTubeStockPreEntry(@Valid @RequestBody SpecimenInfoUpdateReqVO updateReqVO) {
        specimenInfoService.updateSpecimenInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除冷冻管库存预录入")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:delete')")
    public CommonResult<Boolean> deleteFreezingTubeStockPreEntry(@RequestParam("id") Long id) {
        specimenInfoService.deleteSpecimenInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得冷冻管库存预录入")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:query')")
    public CommonResult<SpecimenInfoRespVO> getFreezingTubeStockPreEntry(@RequestParam("id") Long id) {
        SpecimenInfoDO freezingTubeStockPreEntry = specimenInfoService.getSpecimenInfo(id);
        return success(BeanUtils.toBean(freezingTubeStockPreEntry, SpecimenInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得冷冻管库存预录入分页")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:query')")
    public CommonResult<PageResult<SpecimenInfoRespVO>> getFreezingTubeStockPreEntryPage(@Valid SpecimenInfoPageReqVO pageReqVO) {
        PageResult<SpecimenInfoRespVO> pageResult = specimenInfoService.getSpecimenInfoPage2(pageReqVO);
        return success(pageResult);
    }


    //新的分页查询
    @GetMapping("/page2")
    @Operation(summary = "获得样品分页（排除正在审核中的样品）")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:query')")
    public CommonResult<PageResult<SpecimenInfoRespVO>> getFreezingTubeStockPreEntryPage2(@Valid SpecimenInfoPageReqVO pageReqVO) {
        PageResult<SpecimenInfoRespVO> pageResult = specimenInfoService.getSpecimenInfoPage3(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出冷冻管库存预录入 Excel")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-pre-entry:export')")
    
    public void exportFreezingTubeStockPreEntryExcel(@Valid SpecimenInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SpecimenInfoDO> list = specimenInfoService.getSpecimenInfo(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "冷冻管库存预录入.xls", "数据", SpecimenInfoRespVO.class,
                        BeanUtils.toBean(list, SpecimenInfoRespVO.class));
    }

}