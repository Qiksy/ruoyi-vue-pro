package cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo;

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

import cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo.FreezingBoxInfoDO;
import cn.iocoder.yudao.module.strain.convert.freezingboxinfo.FreezingBoxInfoConvert;
import cn.iocoder.yudao.module.strain.service.freezingboxinfo.FreezingBoxInfoService;

@Tag(name = "管理后台 - 冷冻盒信息")
@RestController
@RequestMapping("/strain/freezing-box-info")
@Validated
public class FreezingBoxInfoController {

    @Resource
    private FreezingBoxInfoService freezingBoxInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建冷冻盒信息")
    @PreAuthorize("@ss.hasPermission('strain:freezing-box-info:create')")
    public CommonResult<Long> createFreezingBoxInfo(@RequestBody FreezingBoxInfoCreateReqVO createReqVO) {
        return success(freezingBoxInfoService.createFreezingBoxInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新冷冻盒信息")
    @PreAuthorize("@ss.hasPermission('strain:freezing-box-info:update')")
    public CommonResult<Boolean> updateFreezingBoxInfo(@RequestBody FreezingBoxInfoUpdateReqVO updateReqVO) {
        freezingBoxInfoService.updateFreezingBoxInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除冷冻盒信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:freezing-box-info:delete')")
    public CommonResult<Boolean> deleteFreezingBoxInfo(@RequestParam("id") Long id) {
        freezingBoxInfoService.deleteFreezingBoxInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得冷冻盒信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:freezing-box-info:query')")
    public CommonResult<FreezingBoxInfoRespVO> getFreezingBoxInfo(@RequestParam("id") Long id) {
        FreezingBoxInfoDO freezingBoxInfo = freezingBoxInfoService.getFreezingBoxInfo(id);
        return success(FreezingBoxInfoConvert.INSTANCE.convert(freezingBoxInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得冷冻盒信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('strain:freezing-box-info:query')")
    public CommonResult<List<FreezingBoxInfoRespVO>> getFreezingBoxInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<FreezingBoxInfoDO> list = freezingBoxInfoService.getFreezingBoxInfoList(ids);
        return success(FreezingBoxInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得冷冻盒信息分页")
    @PreAuthorize("@ss.hasPermission('strain:freezing-box-info:query')")
    public CommonResult<PageResult<FreezingBoxInfoRespVO>> getFreezingBoxInfoPage(@Valid FreezingBoxInfoPageReqVO pageVO) {
        PageResult<FreezingBoxInfoDO> pageResult = freezingBoxInfoService.getFreezingBoxInfoPage(pageVO);
        return success(FreezingBoxInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出冷冻盒信息 Excel")
    @PreAuthorize("@ss.hasPermission('strain:freezing-box-info:export')")
    @OperateLog(type = EXPORT)
    public void exportFreezingBoxInfoExcel(@Valid FreezingBoxInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FreezingBoxInfoDO> list = freezingBoxInfoService.getFreezingBoxInfoList(exportReqVO);
        // 导出 Excel
        List<FreezingBoxInfoExcelVO> datas = FreezingBoxInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "冷冻盒信息.xls", "数据", FreezingBoxInfoExcelVO.class, datas);
    }

}
