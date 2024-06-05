package cn.iocoder.yudao.module.strain.controller.admin.regenerationrecord;

import cn.iocoder.yudao.module.strain.dal.dataobject.specimen.SpecimenInfoDO;
import cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry.FreezingTubeStockPreEntryService;
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

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.strain.controller.admin.regenerationrecord.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.regenerationrecord.RegenerationRecordDO;
import cn.iocoder.yudao.module.strain.service.regenerationrecord.RegenerationRecordService;

@Tag(name = "管理后台 - 样品复壮传代记录")
@RestController
@RequestMapping("/strain/regeneration-record")
@Validated
public class RegenerationRecordController {

    @Resource
    private RegenerationRecordService regenerationRecordService;

    @Resource
    private FreezingTubeStockPreEntryService freezingTubeStockPreEntryService;

    @PostMapping("/create")
    @Operation(summary = "创建样品复壮传代记录")
    @PreAuthorize("@ss.hasPermission('strain:regeneration-record:create')")
    public CommonResult<Long> createRegenerationRecord(@Valid @RequestBody RegenerationRecordSaveReqVO createReqVO) {
        return success(regenerationRecordService.createRegenerationRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新样品复壮传代记录")
    @PreAuthorize("@ss.hasPermission('strain:regeneration-record:update')")
    public CommonResult<Boolean> updateRegenerationRecord(@Valid @RequestBody RegenerationRecordSaveReqVO updateReqVO) {
        regenerationRecordService.updateRegenerationRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除样品复壮传代记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:regeneration-record:delete')")
    public CommonResult<Boolean> deleteRegenerationRecord(@RequestParam("id") Long id) {
        regenerationRecordService.deleteRegenerationRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得样品复壮传代记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:regeneration-record:query')")
    public CommonResult<RegenerationRecordRespVO> getRegenerationRecord(@RequestParam("id") Long id) {
        RegenerationRecordDO regenerationRecord = regenerationRecordService.getRegenerationRecord(id);
        return success(BeanUtils.toBean(regenerationRecord, RegenerationRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得样品复壮传代记录分页")
    @PreAuthorize("@ss.hasPermission('strain:regeneration-record:query')")
    public CommonResult<PageResult<RegenerationRecordRespVO>> getRegenerationRecordPage(@Valid RegenerationRecordPageReqVO pageReqVO) {
        PageResult<RegenerationRecordDO> pageResult = regenerationRecordService.getRegenerationRecordPage(pageReqVO);

        List<RegenerationRecordRespVO> list = BeanUtils.toBean(pageResult.getList(), RegenerationRecordRespVO.class);

        for (RegenerationRecordRespVO vo : list) {
            SpecimenInfoDO entry = freezingTubeStockPreEntryService.getFreezingTubeStockPreEntry(vo.getSpecimenId());
            //设置编号
            if (entry==null){
                continue;
            }
            // todo 以后再优化 查询
            vo.setSpecimenCode(entry.getCode());
        }
        return success(new PageResult<>(list, pageResult.getTotal()));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出样品复壮传代记录 Excel")
    @PreAuthorize("@ss.hasPermission('strain:regeneration-record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRegenerationRecordExcel(@Valid RegenerationRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RegenerationRecordDO> list = regenerationRecordService.getRegenerationRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "样品复壮传代记录.xls", "数据", RegenerationRecordRespVO.class,
                        BeanUtils.toBean(list, RegenerationRecordRespVO.class));
    }

}