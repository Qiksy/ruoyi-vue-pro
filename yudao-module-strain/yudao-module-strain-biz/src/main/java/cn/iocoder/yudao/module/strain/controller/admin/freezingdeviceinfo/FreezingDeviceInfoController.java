package cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo;

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




import cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdeviceinfo.FreezingDeviceInfoDO;
import cn.iocoder.yudao.module.strain.convert.freezingdeviceinfo.FreezingDeviceInfoConvert;
import cn.iocoder.yudao.module.strain.service.freezingdeviceinfo.FreezingDeviceInfoService;

@Tag(name = "管理后台 - 冷冻设备信息")
@RestController
@RequestMapping("/strain/freezing-device-info")
@Validated
public class FreezingDeviceInfoController {

    @Resource
    private FreezingDeviceInfoService freezingDeviceInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建冷冻设备信息")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:create')")
    public CommonResult<Long> createFreezingDeviceInfo(@Valid @RequestBody FreezingDeviceInfoCreateReqVO createReqVO) {
        return success(freezingDeviceInfoService.createFreezingDeviceInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新冷冻设备信息")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:update')")
    public CommonResult<Boolean> updateFreezingDeviceInfo(@Valid @RequestBody FreezingDeviceInfoUpdateReqVO updateReqVO) {
        freezingDeviceInfoService.updateFreezingDeviceInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除冷冻设备信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:delete')")
    public CommonResult<Boolean> deleteFreezingDeviceInfo(@RequestParam("id") Long id) {
        freezingDeviceInfoService.deleteFreezingDeviceInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得冷冻设备信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:query')")
    public CommonResult<FreezingDeviceInfoRespVO> getFreezingDeviceInfo(@RequestParam("id") Long id) {
        FreezingDeviceInfoRespVO freezingDeviceInfo = freezingDeviceInfoService.getFreezingDeviceInfo(id);
        return success(freezingDeviceInfo);
    }


    @Operation(summary = "获取冷冻设备的层级信息")
    @GetMapping("/get-level")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:query')")
    public CommonResult<FreezingDeviceInfoLevelRespVO> getFreezingDeviceInfoLevel(@RequestParam("id") Long id) {
        FreezingDeviceInfoLevelRespVO freezingDeviceInfo = freezingDeviceInfoService.getFreezingDeviceInfoLevel(id);
        return success(freezingDeviceInfo);
    }

    @GetMapping("/list")
    @Operation(summary = "获得冷冻设备信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:query')")
    public CommonResult<List<FreezingDeviceInfoRespVO>> getFreezingDeviceInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<FreezingDeviceInfoDO> list = freezingDeviceInfoService.getFreezingDeviceInfoList(ids);
        return success(FreezingDeviceInfoConvert.INSTANCE.convertList(list));
    }


    @GetMapping("/list-by-area-code")
    @Operation(summary = "获得冷冻设备信息列表")
    @Parameter(name = "areaCode", description = "区域编码", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:query')")
    public CommonResult<List<FreezingDeviceInfoLevelRespVO> > getFreezingDeviceInfoListByAreaCode(@RequestParam("code") String code){
        List<FreezingDeviceInfoLevelRespVO>  list = freezingDeviceInfoService.getFreezingDeviceInfoListByAreaCode(code);
        return success(list);
    }



    @GetMapping("/page")
    @Operation(summary = "获得冷冻设备信息分页")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:query')")
    public CommonResult<PageResult<FreezingDeviceInfoRespVO>> getFreezingDeviceInfoPage(@Valid FreezingDeviceInfoPageReqVO pageVO) {
        PageResult<FreezingDeviceInfoDO> pageResult = freezingDeviceInfoService.getFreezingDeviceInfoPage(pageVO);
        return success(FreezingDeviceInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出冷冻设备信息 Excel")
    @PreAuthorize("@ss.hasPermission('strain:freezing-device-info:export')")
    
    public void exportFreezingDeviceInfoExcel(@Valid FreezingDeviceInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FreezingDeviceInfoDO> list = freezingDeviceInfoService.getFreezingDeviceInfoList(exportReqVO);
        // 导出 Excel
        List<FreezingDeviceInfoExcelVO> datas = FreezingDeviceInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "冷冻设备信息.xls", "数据", FreezingDeviceInfoExcelVO.class, datas);
    }

}
