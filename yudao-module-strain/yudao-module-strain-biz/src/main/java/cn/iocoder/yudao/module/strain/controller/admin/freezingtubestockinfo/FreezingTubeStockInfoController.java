package cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo;

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

import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.module.strain.service.freezingtubestockinfo.FreezingTubeStockInfoService;

@Tag(name = "管理后台 - 冷冻盒槽位")
@RestController
@RequestMapping("/strain/freezing-tube-stock-info")
@Validated
public class FreezingTubeStockInfoController {

    @Resource
    private FreezingTubeStockInfoService freezingTubeStockInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建冷冻盒槽位")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-info:create')")
    public CommonResult<Long> createFreezingTubeStockInfo(@Valid @RequestBody FreezingTubeStockInfoSaveReqVO createReqVO) {
        return success(freezingTubeStockInfoService.createFreezingTubeStockInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新冷冻盒槽位")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-info:update')")
    public CommonResult<Boolean> updateFreezingTubeStockInfo(@Valid @RequestBody FreezingTubeStockInfoSaveReqVO updateReqVO) {
        freezingTubeStockInfoService.updateFreezingTubeStockInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除冷冻盒槽位")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-info:delete')")
    public CommonResult<Boolean> deleteFreezingTubeStockInfo(@RequestParam("id") Long id) {
        freezingTubeStockInfoService.deleteFreezingTubeStockInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得冷冻盒槽位")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-info:query')")
    public CommonResult<FreezingTubeStockInfoRespVO> getFreezingTubeStockInfo(@RequestParam("id") Long id) {
        FreezingTubeStockInfoDO freezingTubeStockInfo = freezingTubeStockInfoService.getFreezingTubeStockInfo(id);
        return success(BeanUtils.toBean(freezingTubeStockInfo, FreezingTubeStockInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得冷冻盒槽位分页")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-info:query')")
    public CommonResult<PageResult<FreezingTubeStockInfoRespVO>> getFreezingTubeStockInfoPage(@Valid FreezingTubeStockInfoPageReqVO pageReqVO) {
        PageResult<FreezingTubeStockInfoDO> pageResult = freezingTubeStockInfoService.getFreezingTubeStockInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, FreezingTubeStockInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出冷冻盒槽位 Excel")
    @PreAuthorize("@ss.hasPermission('strain:freezing-tube-stock-info:export')")
    @OperateLog(type = EXPORT)
    public void exportFreezingTubeStockInfoExcel(@Valid FreezingTubeStockInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FreezingTubeStockInfoDO> list = freezingTubeStockInfoService.getFreezingTubeStockInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "冷冻盒槽位.xls", "数据", FreezingTubeStockInfoRespVO.class,
                        BeanUtils.toBean(list, FreezingTubeStockInfoRespVO.class));
    }

}