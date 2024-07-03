package cn.iocoder.yudao.module.oa.controller.admin.signininfo;

import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInRecordDO;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInTimeRangeDO;
import io.swagger.v3.oas.annotations.media.Schema;
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

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.oa.controller.admin.signininfo.vo.*;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInInfoDO;
import cn.iocoder.yudao.module.oa.service.signininfo.SignInInfoService;

@Tag(name = "管理后台 - 会议签到")
@RestController
@RequestMapping("/oa/meeting")
@Validated
public class SignInInfoController {

    @Resource
    private SignInInfoService signInInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建会议签到")
    @PreAuthorize("@ss.hasRole('common')")
    public CommonResult<Long> createSignInInfo(@Valid @RequestBody SignInInfoSaveReqVO createReqVO) {
        return success(signInInfoService.createSignInInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新会议签到")
    @PreAuthorize("@ss.hasRole('common')")
    public CommonResult<Boolean> updateSignInInfo(@Valid @RequestBody SignInInfoSaveReqVO updateReqVO) {
        signInInfoService.updateSignInInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除会议签到")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasRole('common')")
    public CommonResult<Boolean> deleteSignInInfo(@RequestParam("id") Long id) {
        signInInfoService.deleteSignInInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得会议签到")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasRole('common')")
    public CommonResult<SignInInfoRespVO> getSignInInfo(@RequestParam("id") Long id) {
        SignInInfoRespVO signInInfo = signInInfoService.getSignInInfo(id);
        return success(signInInfo);
    }

    @GetMapping("/page")
    @Operation(summary = "获得会议签到分页")
    @PreAuthorize("@ss.hasRole('common')")
    public CommonResult<PageResult<SignInInfoRespVO>> getSignInInfoPage(@Valid SignInInfoPageReqVO pageReqVO) {
        PageResult<SignInInfoRespVO> pageResult = signInInfoService.getSignInInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SignInInfoRespVO.class));
    }
//
//    @GetMapping("/export-excel")
//    @Operation(summary = "导出会议签到 Excel")
//    @PreAuthorize("@ss.hasRole('common')")
//    @ApiAccessLog(operateType = EXPORT)
//    public void exportSignInInfoExcel(@Valid SignInInfoPageReqVO pageReqVO,
//              HttpServletResponse response) throws IOException {
//        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
//        List<SignInInfoDO> list = signInInfoService.getSignInInfoPage(pageReqVO).getList();
//        // 导出 Excel
//        ExcelUtils.write(response, "会议签到.xls", "数据", SignInInfoRespVO.class,
//                        BeanUtils.toBean(list, SignInInfoRespVO.class));
//    }

    // ==================== 子表（签到记录） ====================

    @GetMapping("/sign-in-record/list-by-parent-id")
    @Operation(summary = "获得签到记录列表")
    @Parameter(name = "parentId", description = "主表id")
    @PreAuthorize("@ss.hasRole('common')")
    public CommonResult<List<SignInRecordDO>> getSignInRecordListByParentId(@RequestParam("parentId") Long parentId) {
        return success(signInInfoService.getSignInRecordListByParentId(parentId));
    }


//    用户点击参与签到
    @PostMapping("/signIn")
    @Operation(summary = "用户签到")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasRole('common')")
    public CommonResult<Boolean> signIn(@RequestParam("id") Long id) {
        signInInfoService.signIn(id);
        return success(true);
    }


    // ==================== 子表（签到时间范围） ====================

    @GetMapping("/sign-in-time-range/list-by-parent-id")
    @Operation(summary = "获得签到时间范围列表")
    @Parameter(name = "parentId", description = "主表id")
    @PreAuthorize("@ss.hasRole('common')")
    public CommonResult<List<SignInTimeRangeDO>> getSignInTimeRangeListByParentId(@RequestParam("parentId") Long parentId) {
        return success(signInInfoService.getSignInTimeRangeListByParentId(parentId));
    }

}