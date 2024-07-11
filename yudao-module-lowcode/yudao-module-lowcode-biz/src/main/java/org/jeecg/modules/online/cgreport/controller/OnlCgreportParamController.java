package org.jeecg.modules.online.cgreport.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* compiled from: OnlCgreportParamController.java */
@RequestMapping({"/online/cgreport/param"})
@RestController("onlCgreportParamController")
/* renamed from: org.jeecg.modules.online.cgreport.a.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/a/d.class */
@Tag(name = "online报表参数")
public class OnlCgreportParamController {

    /* renamed from: a */
    private static final Logger f430a = LoggerFactory.getLogger(OnlCgreportParamController.class);

    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;

    @GetMapping({"/listByHeadId"})
    @Operation(summary = "通过表头ID查询参数列表")
    /* renamed from: a */
    public Result<List<OnlCgreportParam> > m419a(@RequestParam("headId") String str) {
        QueryWrapper<OnlCgreportParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cgrhead_id", str);
        queryWrapper.orderByAsc("order_num");
        List<OnlCgreportParam> list = this.onlCgreportParamService.list(queryWrapper);
        Result<List<OnlCgreportParam> > result = new Result<>();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    @GetMapping({"/list"})
    @Operation(summary = "获取参数列表")
    /* renamed from: a */
    public Result<IPage<OnlCgreportParam>> m420a(OnlCgreportParam onlCgreportParam,
                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer num,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer num2,
                                                 HttpServletRequest httpServletRequest) {
        Result<IPage<OnlCgreportParam>> result = new Result<>();
        IPage<OnlCgreportParam> page = this.onlCgreportParamService.page(new Page<>(num, num2), QueryGenerator.initQueryWrapper(onlCgreportParam, httpServletRequest.getParameterMap()));
        result.setSuccess(true);
        result.setResult(page);
        return result;
    }

    @PostMapping({"/add"})
    @Operation(summary = "添加参数")
    /* renamed from: a */
    public Result<?> m421a(@RequestBody OnlCgreportParam onlCgreportParam) {
        this.onlCgreportParamService.save(onlCgreportParam);
        return Result.ok("添加成功!");
    }

    @PutMapping({"/edit"})
    @Operation(summary = "编辑参数")
    /* renamed from: b */
    public Result<?> m422b(@RequestBody OnlCgreportParam onlCgreportParam) {
        this.onlCgreportParamService.updateById(onlCgreportParam);
        return Result.ok("编辑成功!");
    }

    @DeleteMapping({"/delete"})
    @Operation(summary = "删除参数")
    /* renamed from: b */
    public Result<?> m423b(@RequestParam(name = "id", required = true) String str) {
        this.onlCgreportParamService.removeById(str);
        return Result.ok("删除成功!");
    }

    @DeleteMapping({"/deleteBatch"})
    @Operation(summary = "批量删除参数")
    /* renamed from: c */
    public Result<?> m424c(@RequestParam(name = "ids", required = true) String str) {
        this.onlCgreportParamService.removeByIds(Arrays.asList(str.split(CgformUtil.COMMA_SEPARATOR)));
        return Result.ok("批量删除成功!");
    }

    @GetMapping({"/queryById"})
    @Operation(summary = "通过id查询参数")
    /* renamed from: d */
    public Result<OnlCgreportParam> m425d(@RequestParam(name = "id", required = true) String str) {
        Result<OnlCgreportParam> result = new Result<>();
        result.setResult(this.onlCgreportParamService.getById(str));
        return result;
    }
}
