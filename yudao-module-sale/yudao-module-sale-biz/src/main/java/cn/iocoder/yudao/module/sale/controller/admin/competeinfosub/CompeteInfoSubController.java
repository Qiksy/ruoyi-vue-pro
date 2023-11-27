package cn.iocoder.yudao.module.sale.controller.admin.competeinfosub;

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

import cn.iocoder.yudao.module.sale.controller.admin.competeinfosub.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfosub.CompeteInfoSubDO;
import cn.iocoder.yudao.module.sale.service.competeinfosub.CompeteInfoSubService;

@Tag(name = "管理后台 - 竞品信息子")
@RestController
@RequestMapping("/sale/compete-info-sub")
@Validated
public class CompeteInfoSubController {

    @Resource
    private CompeteInfoSubService competeInfoSubService;

    @PostMapping("/create")
    @Operation(summary = "创建竞品信息子")
    @PreAuthorize("@ss.hasPermission('sale:compete-info:create')")
    public CommonResult<Long> createCompeteInfoSub(@Valid @RequestBody CompeteInfoSubSaveReqVO createReqVO) {
        return success(competeInfoSubService.createCompeteInfoSub(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新竞品信息子")
    @PreAuthorize("@ss.hasPermission('sale:compete-info:update')")
    public CommonResult<Boolean> updateCompeteInfoSub(@Valid @RequestBody CompeteInfoSubSaveReqVO updateReqVO) {
        competeInfoSubService.updateCompeteInfoSub(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除竞品信息子")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:compete-info:delete')")
    public CommonResult<Boolean> deleteCompeteInfoSub(@RequestParam("id") Long id) {
        competeInfoSubService.deleteCompeteInfoSub(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得竞品信息子")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:compete-info:query')")
    public CommonResult<CompeteInfoSubRespVO> getCompeteInfoSub(@RequestParam("id") Long id) {
        CompeteInfoSubRespVO competeInfoSub = competeInfoSubService.getCompeteInfoSub(id);
        return success(competeInfoSub);
    }

    @GetMapping("/page")
    @Operation(summary = "获得竞品信息子分页")
    @PreAuthorize("@ss.hasPermission('sale:compete-info:query')")
    public CommonResult<PageResult<CompeteInfoSubRespVO>> getCompeteInfoSubPage(@Valid CompeteInfoSubPageReqVO pageReqVO) {
        PageResult<CompeteInfoSubDO> pageResult = competeInfoSubService.getCompeteInfoSubPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CompeteInfoSubRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出竞品信息子 Excel")
    @PreAuthorize("@ss.hasPermission('sale:compete-info:export')")
    @OperateLog(type = EXPORT)
    public void exportCompeteInfoSubExcel(@Valid CompeteInfoSubPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CompeteInfoSubDO> list = competeInfoSubService.getCompeteInfoSubPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "竞品信息子.xls", "数据", CompeteInfoSubRespVO.class,
                        BeanUtils.toBean(list, CompeteInfoSubRespVO.class));
    }

}