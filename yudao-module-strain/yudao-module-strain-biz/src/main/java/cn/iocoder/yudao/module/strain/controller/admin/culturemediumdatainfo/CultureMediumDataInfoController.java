package cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo;

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

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.culturemediumdatainfo.CultureMediumDataInfoDO;
import cn.iocoder.yudao.module.strain.convert.culturemediumdatainfo.CultureMediumDataInfoConvert;
import cn.iocoder.yudao.module.strain.service.culturemediumdatainfo.CultureMediumDataInfoService;

@Tag(name = "管理后台 - 培养基数据信息")
@RestController
@RequestMapping("/strain/culture-medium-data-info")
@Validated
public class CultureMediumDataInfoController {

    @Resource
    private CultureMediumDataInfoService cultureMediumDataInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建培养基数据信息")
    @PreAuthorize("@ss.hasPermission('strain:culture-medium-data-info:create')")
    public CommonResult<Long> createCultureMediumDataInfo(@Valid @RequestBody CultureMediumDataInfoCreateReqVO createReqVO) {
        return success(cultureMediumDataInfoService.createCultureMediumDataInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新培养基数据信息")
    @PreAuthorize("@ss.hasPermission('strain:culture-medium-data-info:update')")
    public CommonResult<Boolean> updateCultureMediumDataInfo(@Valid @RequestBody CultureMediumDataInfoUpdateReqVO updateReqVO) {
        cultureMediumDataInfoService.updateCultureMediumDataInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除培养基数据信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:culture-medium-data-info:delete')")
    public CommonResult<Boolean> deleteCultureMediumDataInfo(@RequestParam("id") Long id) {
        cultureMediumDataInfoService.deleteCultureMediumDataInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得培养基数据信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:culture-medium-data-info:query')")
    public CommonResult<CultureMediumDataInfoRespVO> getCultureMediumDataInfo(@RequestParam("id") Long id) {
        CultureMediumDataInfoDO cultureMediumDataInfo = cultureMediumDataInfoService.getCultureMediumDataInfo(id);
        return success(CultureMediumDataInfoConvert.INSTANCE.convert(cultureMediumDataInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得培养基数据信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('strain:culture-medium-data-info:query')")
    public CommonResult<List<CultureMediumDataInfoRespVO>> getCultureMediumDataInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<CultureMediumDataInfoDO> list = cultureMediumDataInfoService.getCultureMediumDataInfoList(ids);
        return success(CultureMediumDataInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得培养基数据信息分页")
    @PreAuthorize("@ss.hasPermission('strain:culture-medium-data-info:query')")
    public CommonResult<PageResult<CultureMediumDataInfoRespVO>> getCultureMediumDataInfoPage(@Valid CultureMediumDataInfoPageReqVO pageVO) {
        PageResult<CultureMediumDataInfoDO> pageResult = cultureMediumDataInfoService.getCultureMediumDataInfoPage(pageVO);
        return success(CultureMediumDataInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出培养基数据信息 Excel")
    @PreAuthorize("@ss.hasPermission('strain:culture-medium-data-info:export')")
    @OperateLog(type = EXPORT)
    public void exportCultureMediumDataInfoExcel(@Valid CultureMediumDataInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<CultureMediumDataInfoDO> list = cultureMediumDataInfoService.getCultureMediumDataInfoList(exportReqVO);
        // 导出 Excel
        List<CultureMediumDataInfoExcelVO> datas = CultureMediumDataInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "培养基数据信息.xls", "数据", CultureMediumDataInfoExcelVO.class, datas);
    }

}
