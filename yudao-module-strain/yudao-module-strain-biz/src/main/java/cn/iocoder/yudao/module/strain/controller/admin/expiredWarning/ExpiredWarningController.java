package cn.iocoder.yudao.module.strain.controller.admin.expiredWarning;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningRespVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningUpdateReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.RejuvenateReqVO;
import cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry.FreezingTubeStockPreEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 过期预警")
@RestController
@RequestMapping("/strain/expired-warning")
@Validated
public class ExpiredWarningController {


    @Resource
    private FreezingTubeStockPreEntryService freezingTubeStockPreEntryService;

    @GetMapping("/page")
    @Operation(summary = "获取即将过期")
    @PreAuthorize("@ss.hasPermission('strain:expired-waring:query')")
    public CommonResult<PageResult<ExpiredWarningRespVO>> getExpiredWarningPage( ExpiredWarningReqVO queryVO){
        PageResult<ExpiredWarningRespVO> pageResult =  freezingTubeStockPreEntryService.getExpiredWaringPage(queryVO);
        return success(pageResult);
    }


    @PutMapping("/expired-date")
    @Operation(summary = "更新过期日期")
    @PreAuthorize("@ss.hasPermission('strain:expired-waring:update')")
    public CommonResult<Boolean> updateExpiredDate(@RequestBody ExpiredWarningUpdateReqVO reqVO){
        freezingTubeStockPreEntryService.updateExpiredDate(reqVO);
        return success(true);
    }


    @PutMapping("/rejuvenate")
    @Operation(summary = "传代 / 复壮")
    @PreAuthorize("@ss.hasPermission('strain:expired-waring:rejuvenate')")
    public CommonResult<Boolean> rejuvenate(@RequestBody @Validated RejuvenateReqVO reqVO){
        freezingTubeStockPreEntryService.rejuvenate(reqVO);
        return success(true);
    }

}
