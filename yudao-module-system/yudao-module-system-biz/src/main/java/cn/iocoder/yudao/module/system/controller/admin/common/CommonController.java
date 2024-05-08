package cn.iocoder.yudao.module.system.controller.admin.common;


import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Tag(name = "公共模块")
@RestController
@RequestMapping("/system/common")
public class CommonController {


    @GetMapping("/businessId")
    @PermitAll
    @Operation(summary = "获得业务编号")
    public CommonResult<Long> getBusinessId() {
        Long id = DefaultIdentifierGenerator.getInstance().nextId(new Date());
        return CommonResult.success(id);
    }

}
