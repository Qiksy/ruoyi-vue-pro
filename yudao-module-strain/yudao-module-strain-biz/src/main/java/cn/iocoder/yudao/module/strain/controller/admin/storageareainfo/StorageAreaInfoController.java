package cn.iocoder.yudao.module.strain.controller.admin.storageareainfo;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
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

import cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.storageareainfo.StorageAreaInfoDO;
import cn.iocoder.yudao.module.strain.convert.storageareainfo.StorageAreaInfoConvert;
import cn.iocoder.yudao.module.strain.service.storageareainfo.StorageAreaInfoService;

@Tag(name = "管理后台 - 存放区域信息")
@RestController
@RequestMapping("/strain/storage-area-info")
@Validated
public class StorageAreaInfoController {

    @Resource
    private StorageAreaInfoService storageAreaInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建存放区域信息")
    @PreAuthorize("@ss.hasPermission('strain:storage-area-info:create')")
    public CommonResult<Long> createStorageAreaInfo(@Valid @RequestBody StorageAreaInfoCreateReqVO createReqVO) {
        return success(storageAreaInfoService.createStorageAreaInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新存放区域信息")
    @PreAuthorize("@ss.hasPermission('strain:storage-area-info:update')")
    public CommonResult<Boolean> updateStorageAreaInfo(@Valid @RequestBody StorageAreaInfoUpdateReqVO updateReqVO) {
        storageAreaInfoService.updateStorageAreaInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除存放区域信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('strain:storage-area-info:delete')")
    public CommonResult<Boolean> deleteStorageAreaInfo(@RequestParam("id") Long id) {
        storageAreaInfoService.deleteStorageAreaInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得存放区域信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('strain:storage-area-info:query')")
    public CommonResult<StorageAreaInfoRespVO> getStorageAreaInfo(@RequestParam("id") Long id) {
        StorageAreaInfoDO storageAreaInfo = storageAreaInfoService.getStorageAreaInfo(id);
        return success(StorageAreaInfoConvert.INSTANCE.convert(storageAreaInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得存放区域信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('strain:storage-area-info:query')")
    public CommonResult<List<StorageAreaInfoRespVO>> getStorageAreaInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<StorageAreaInfoDO> list = storageAreaInfoService.getStorageAreaInfoList(ids);
        return success(StorageAreaInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得存放区域信息分页")
    @PreAuthorize("@ss.hasPermission('strain:storage-area-info:query')")
    public CommonResult<PageResult<StorageAreaInfoRespVO>> getStorageAreaInfoPage(@Valid StorageAreaInfoPageReqVO pageVO) {
        PageResult<StorageAreaInfoDO> pageResult = storageAreaInfoService.getStorageAreaInfoPage(pageVO);
        return success(StorageAreaInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出存放区域信息 Excel")
    @PreAuthorize("@ss.hasPermission('strain:storage-area-info:export')")
    @OperateLog(type = EXPORT)
    public void exportStorageAreaInfoExcel(@Valid StorageAreaInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<StorageAreaInfoDO> list = storageAreaInfoService.getStorageAreaInfoList(exportReqVO);
        // 导出 Excel
        List<StorageAreaInfoExcelVO> datas = StorageAreaInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "存放区域信息.xls", "数据", StorageAreaInfoExcelVO.class, datas);
    }


    @GetMapping("/all-list")
    @Operation(summary = "获得所有的区域信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('strain:storage-area-info:query')")
    public CommonResult<List<StorageAreaInfoRespVO>> getAllStorageAreaInfoList() {
        List<StorageAreaInfoDO> list = storageAreaInfoService.getAllStorageAreaInfoList();
        return success(BeanUtils.toBean(list, StorageAreaInfoRespVO.class));
    }

}
