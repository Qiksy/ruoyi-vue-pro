package cn.iocoder.yudao.module.strain.controller.admin.resave;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.strain.controller.admin.resave.vo.ResaveReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.resave.vo.ResaveRespVO;
import cn.iocoder.yudao.module.strain.service.resave.ResaveInfoService;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 查询已经审批但是等待回库的样品信息
 * @author linr
 * @since 2024/6/18 上午10:36
 */

@RestController
@RequestMapping("/strain/resave")
public class ResaveInfoController {


    @Resource
    private ResaveInfoService resaveInfoService;


    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('strain:resave:list')")
    public CommonResult<PageResult<ResaveRespVO>> list( ResaveReqVO param) {
        return CommonResult.success(resaveInfoService.list(param));
    }

}
