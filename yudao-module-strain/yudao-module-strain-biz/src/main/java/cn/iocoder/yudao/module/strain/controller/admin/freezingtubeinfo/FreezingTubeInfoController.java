package cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo;

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

import cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubeinfo.FreezingTubeInfoDO;
import cn.iocoder.yudao.module.strain.convert.freezingtubeinfo.FreezingTubeInfoConvert;
import cn.iocoder.yudao.module.strain.service.freezingtubeinfo.FreezingTubeInfoService;

@Tag(name = "管理后台 - 冷冻管基本信息")
@RestController
@RequestMapping("/strain/freezing-tube-info")
@Validated
public class FreezingTubeInfoController {

    @Resource
    private FreezingTubeInfoService freezingTubeInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建冷冻管基本信息")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-info:create')")
    public CommonResult<Long> createFreezingTubeInfo(@Valid @RequestBody FreezingTubeInfoCreateReqVO createReqVO) {
        return success(freezingTubeInfoService.createFreezingTubeInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新冷冻管基本信息")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-info:update')")
    public CommonResult<Boolean> updateFreezingTubeInfo(@Valid @RequestBody FreezingTubeInfoUpdateReqVO updateReqVO) {
        freezingTubeInfoService.updateFreezingTubeInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除冷冻管基本信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-info:delete')")
    public CommonResult<Boolean> deleteFreezingTubeInfo(@RequestParam("id") Long id) {
        freezingTubeInfoService.deleteFreezingTubeInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得冷冻管基本信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-info:query')")
    public CommonResult<FreezingTubeInfoRespVO> getFreezingTubeInfo(@RequestParam("id") Long id) {
        FreezingTubeInfoDO freezingTubeInfo = freezingTubeInfoService.getFreezingTubeInfo(id);
        return success(FreezingTubeInfoConvert.INSTANCE.convert(freezingTubeInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得冷冻管基本信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-info:query')")
    public CommonResult<List<FreezingTubeInfoRespVO>> getFreezingTubeInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<FreezingTubeInfoDO> list = freezingTubeInfoService.getFreezingTubeInfoList(ids);
        return success(FreezingTubeInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得冷冻管基本信息分页")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-info:query')")
    public CommonResult<PageResult<FreezingTubeInfoRespVO>> getFreezingTubeInfoPage(@Valid FreezingTubeInfoPageReqVO pageVO) {
        PageResult<FreezingTubeInfoDO> pageResult = freezingTubeInfoService.getFreezingTubeInfoPage(pageVO);
        return success(FreezingTubeInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出冷冻管基本信息 Excel")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-info:export')")
    @OperateLog(type = EXPORT)
    public void exportFreezingTubeInfoExcel(@Valid FreezingTubeInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FreezingTubeInfoDO> list = freezingTubeInfoService.getFreezingTubeInfoList(exportReqVO);
        // 导出 Excel
        List<FreezingTubeInfoExcelVO> datas = FreezingTubeInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "冷冻管基本信息.xls", "数据", FreezingTubeInfoExcelVO.class, datas);
    }

}
