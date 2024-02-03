package cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo;

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

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;

import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo.MicrobeBasicInfoDO;
import cn.iocoder.yudao.module.strain.service.microbebasicinfo.MicrobeBasicInfoService;

@Tag(name = "管理后台 - 菌种信息")
@RestController
@RequestMapping("/strain/microbe-basic-info")
@Validated
public class MicrobeBasicInfoController {

    @Resource
    private MicrobeBasicInfoService microbeBasicInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建菌种信息")
    @PreAuthorize("@ss.hasPermission('strain:microbe-basic-info:create')")
    public CommonResult<Long> createMicrobeBasicInfo(@Valid @RequestBody MicrobeBasicInfoSaveReqVO createReqVO) {
        return success(microbeBasicInfoService.createMicrobeBasicInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菌种信息")
    @PreAuthorize("@ss.hasPermission('strain:microbe-basic-info:update')")
    public CommonResult<Boolean> updateMicrobeBasicInfo(@Valid @RequestBody MicrobeBasicInfoSaveReqVO updateReqVO) {
        microbeBasicInfoService.updateMicrobeBasicInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菌种信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:microbe-basic-info:delete')")
    public CommonResult<Boolean> deleteMicrobeBasicInfo(@RequestParam("id") Long id) {
        microbeBasicInfoService.deleteMicrobeBasicInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菌种信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:microbe-basic-info:query')")
    public CommonResult<MicrobeBasicInfoRespVO> getMicrobeBasicInfo(@RequestParam("id") Long id) {
        MicrobeBasicInfoDO microbeBasicInfo = microbeBasicInfoService.getMicrobeBasicInfo(id);
        return success(BeanUtils.toBean(microbeBasicInfo, MicrobeBasicInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得菌种信息分页")
    @PreAuthorize("@ss.hasPermission('strain:microbe-basic-info:query')")
    public CommonResult<PageResult<MicrobeBasicInfoRespVO>> getMicrobeBasicInfoPage(@Valid MicrobeBasicInfoPageReqVO pageReqVO) {
        PageResult<MicrobeBasicInfoRespVO> pageResult = microbeBasicInfoService.getMicrobeBasicInfoPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出菌种信息 Excel")
    @PreAuthorize("@ss.hasPermission('strain:microbe-basic-info:export')")
    @OperateLog(type = EXPORT)
    public void exportMicrobeBasicInfoExcel(@Valid MicrobeBasicInfoPageReqVO pageReqVO,
                                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MicrobeBasicInfoRespVO> list = microbeBasicInfoService.getMicrobeBasicInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "菌种信息.xls", "数据", MicrobeBasicInfoRespVO.class, list);
    }

}