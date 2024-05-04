package cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy;

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




import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import cn.iocoder.yudao.module.strain.convert.freezingdevicehierarchy.FreezingDeviceHierarchyConvert;
import cn.iocoder.yudao.module.strain.service.freezingdevicehierarchy.FreezingDeviceHierarchyService;

@Tag(name = "管理后台 - 冷冻设备层级")
@RestController
@RequestMapping("/strain/freezing-device-hierarchy")
@Validated
public class FreezingDeviceHierarchyController {

    @Resource
    private FreezingDeviceHierarchyService freezingDeviceHierarchyService;

    @PostMapping("/create")
    @Operation(summary = "创建冷冻设备层级")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:create')")
    public CommonResult<Long> createFreezingDeviceHierarchy(@Valid @RequestBody FreezingDeviceHierarchyCreateReqVO createReqVO) {
        return success(freezingDeviceHierarchyService.createFreezingDeviceHierarchy(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新冷冻设备层级")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:update')")
    public CommonResult<Boolean> updateFreezingDeviceHierarchy(@Valid @RequestBody FreezingDeviceHierarchyUpdateReqVO updateReqVO) {
        freezingDeviceHierarchyService.updateFreezingDeviceHierarchy(updateReqVO);
        return success(true);
    }


    @PutMapping("/add-new")
    @Operation(summary = "添加新的冷冻设备层级")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:update')")
    public CommonResult<Boolean> addNewFreezingDeviceHierarchy(@Valid @RequestBody FreezingDeviceHierarchyUpdateReqVO updateReqVO) {
        freezingDeviceHierarchyService.addNewFreezingDeviceHierarchy(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除冷冻设备层级")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:delete')")
    public CommonResult<Boolean> deleteFreezingDeviceHierarchy(@RequestParam("id") Long id) {
        freezingDeviceHierarchyService.deleteFreezingDeviceHierarchy(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得冷冻设备层级")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:query')")
    public CommonResult<FreezingDeviceHierarchyRespVO> getFreezingDeviceHierarchy(@RequestParam("id") Long id) {
        FreezingDeviceHierarchyDO freezingDeviceHierarchy = freezingDeviceHierarchyService.getFreezingDeviceHierarchy(id);
        return success(FreezingDeviceHierarchyConvert.INSTANCE.convert(freezingDeviceHierarchy));
    }

    @GetMapping("/list")
    @Operation(summary = "获得冷冻设备层级列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:query')")
    public CommonResult<List<FreezingDeviceHierarchyRespVO>> getFreezingDeviceHierarchyList(@RequestParam("ids") Collection<Long> ids) {
        List<FreezingDeviceHierarchyDO> list = freezingDeviceHierarchyService.getFreezingDeviceHierarchyList(ids);
        return success(FreezingDeviceHierarchyConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得冷冻设备层级分页")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:query')")
    public CommonResult<PageResult<FreezingDeviceHierarchyRespVO>> getFreezingDeviceHierarchyPage(@Valid FreezingDeviceHierarchyPageReqVO pageVO) {
        PageResult<FreezingDeviceHierarchyDO> pageResult = freezingDeviceHierarchyService.getFreezingDeviceHierarchyPage(pageVO);
        return success(FreezingDeviceHierarchyConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出冷冻设备层级 Excel")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:export')")
    
    public void exportFreezingDeviceHierarchyExcel(@Valid FreezingDeviceHierarchyExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FreezingDeviceHierarchyDO> list = freezingDeviceHierarchyService.getFreezingDeviceHierarchyList(exportReqVO);
        // 导出 Excel
        List<FreezingDeviceHierarchyExcelVO> datas = FreezingDeviceHierarchyConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "冷冻设备层级.xls", "数据", FreezingDeviceHierarchyExcelVO.class, datas);
    }

}
