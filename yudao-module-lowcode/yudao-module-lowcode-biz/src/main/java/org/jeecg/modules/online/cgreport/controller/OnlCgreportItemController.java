package org.jeecg.modules.online.cgreport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
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

/* compiled from: OnlCgreportItemController.java */
@RequestMapping({"/online/cgreport/item"})
@RestController("onlCgreportItemController")
@Tag(name = "online报表明细")
/* renamed from: org.jeecg.modules.online.cgreport.a.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/a/c.class */
public class OnlCgreportItemController {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(OnlCgreportItemController.class);

    @Autowired
    private IOnlCgreportItemService onlCgreportItemService;

    @Autowired
    private IOnlCgreportHeadService onlCgreportHeadService;

    /* renamed from: a */


    @GetMapping({"/listByHeadId"})
    @Operation(summary = "通过表头ID查询明细列表")
    /* renamed from: a */
    public Result<List<OnlCgreportItem>> m410a(@RequestParam("headId") String str) {
        QueryWrapper<OnlCgreportItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cgrhead_id", str);
        queryWrapper.orderByAsc("order_num");
        List<OnlCgreportItem> list = this.onlCgreportItemService.list(queryWrapper);
        Result<List<OnlCgreportItem>> result = new Result<>();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    @GetMapping({"/listByHeadCode"})
    @Operation(summary = "通过表头编码查询明细列表")
    /* renamed from: b */
    public Result<List<OnlCgreportItem>> m411b(@RequestParam("headCode") String str) {
        LambdaQueryWrapper<OnlCgreportHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgreportHead::getCode, str);
        OnlCgreportHead onlCgreportHead = this.onlCgreportHeadService.getOne(lambdaQueryWrapper);
        if (onlCgreportHead == null) {
            throw exception("该报表不存在");
        }
        QueryWrapper<OnlCgreportItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cgrhead_id", onlCgreportHead.getId());
        queryWrapper.orderByAsc("order_num");
        List<OnlCgreportItem> list = this.onlCgreportItemService.list(queryWrapper);
        Result<List<OnlCgreportItem>> result = new Result<>();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    @GetMapping({"/list"})
    @Operation(summary = "获取明细列表")
    /* renamed from: a */
    public Result<IPage<OnlCgreportItem>> m412a(OnlCgreportItem onlCgreportItem, @RequestParam(name = "pageNo", defaultValue = "1") Integer num, @RequestParam(name = "pageSize", defaultValue = "10") Integer num2, HttpServletRequest httpServletRequest) {
        Result<IPage<OnlCgreportItem>> result = new Result<>();
        IPage<OnlCgreportItem> page = this.onlCgreportItemService.page(new Page<>(num, num2), QueryGenerator.initQueryWrapper(onlCgreportItem, httpServletRequest.getParameterMap()));
        result.setSuccess(true);
        result.setResult(page);
        return result;
    }

    @PostMapping({"/add"})
    @Operation(summary = "添加明细")
    /* renamed from: a */
    public Result<?> m413a(@RequestBody OnlCgreportItem onlCgreportItem) {
        this.onlCgreportItemService.save(onlCgreportItem);
        return Result.ok("添加成功!");
    }

    @PutMapping({"/edit"})
    @Operation(summary = "编辑明细")
    /* renamed from: b */
    public Result<?> m414b(@RequestBody OnlCgreportItem onlCgreportItem) {
        this.onlCgreportItemService.updateById(onlCgreportItem);
        return Result.ok("编辑成功!");
    }

    @DeleteMapping({"/delete"})
    @Operation(summary = "删除明细")
    /* renamed from: c */
    public Result<?> m415c(@RequestParam(name = "id", required = true) String str) {
        this.onlCgreportItemService.removeById(str);
        return Result.ok("删除成功!");
    }

    @DeleteMapping({"/deleteBatch"})
    @Operation(summary = "批量删除明细")
    /* renamed from: d */
    public Result<?> m416d(@RequestParam(name = "ids", required = true) String str) {
        this.onlCgreportItemService.removeByIds(Arrays.asList(str.split(CgformUtil.COMMA_SEPARATOR)));
        return Result.ok("批量删除成功!");
    }

    @GetMapping({"/queryById"})
    @Operation(summary = "通过id查询明细")
    /* renamed from: e */
    public Result<OnlCgreportItem> m417e(@RequestParam(name = "id", required = true) String str) {
        Result<OnlCgreportItem> result = new Result<>();
        result.setResult((OnlCgreportItem) this.onlCgreportItemService.getById(str));
        result.setSuccess(true);
        return result;
    }
}
