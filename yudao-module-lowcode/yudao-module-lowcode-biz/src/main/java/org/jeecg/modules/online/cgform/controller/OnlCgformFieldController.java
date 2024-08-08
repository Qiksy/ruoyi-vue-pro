package org.jeecg.modules.online.cgform.controller;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.query.QueryGenerator;
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

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/* compiled from: OnlCgformFieldController.java */
@RequestMapping({"/online/cgform/field"})
@RestController("onlCgformFieldController")
/* renamed from: org.jeecg.modules.online.cgform.c.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/c/c.class */
@Tag(name="online表单field")
public class OnlCgformFieldController {

    /* renamed from: a */
    private static final Logger f139a = LoggerFactory.getLogger(OnlCgformFieldController.class);

    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;

    /* renamed from: a */

    @GetMapping({"/listByHeadCode"})
    @Operation(summary="online表单明细-通过表头编码查询")
    /* renamed from: a */
    public CommonResult<?> geiListByHeadCode(@RequestParam("headCode") String str) {
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, str);
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getOne(lambdaQueryWrapper);
        if (onlCgformHead == null) {
            return error("表名[" + str + "]不存在！");
        }
        return getListByHeadId(onlCgformHead.getId());
    }

    @GetMapping({"/listByHeadId"})
    @Operation(summary="online表单明细-通过表头主键查询")
    /* renamed from: b */
    public CommonResult<?> getListByHeadId(@RequestParam("headId") String headId) {
        QueryWrapper<OnlCgformField> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cgform_head_id", headId);
        queryWrapper.orderByAsc("order_num");
        return success(this.onlCgformFieldService.list(queryWrapper));
    }

    @GetMapping({"/list"})
    /* renamed from: a */
    @Operation(summary="online表单明细-通过参数查询")
    public CommonResult<IPage<OnlCgformField>> getList(OnlCgformField onlCgformField, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest httpServletRequest) {
        CommonResult<IPage<OnlCgformField>> result = new CommonResult<>();
        IPage<OnlCgformField> page = this.onlCgformFieldService.page(new Page<>(pageNo, pageSize), QueryGenerator.initQueryWrapper(onlCgformField, httpServletRequest.getParameterMap()));
//        result.setSuccess(true);
//        result.setResult(page);
        return success(page);
    }

    @PostMapping({"/add"})
    @Operation(summary="online表单明细-增加")
    /* renamed from: a */
    public CommonResult<OnlCgformField> add(@RequestBody OnlCgformField onlCgformField) {
        CommonResult<OnlCgformField> result = new CommonResult<>();
        try {
            this.onlCgformFieldService.save(onlCgformField);
            success("添加成功！");
        } catch (Exception e) {
            f139a.error(e.getMessage(), e);
            return error("操作失败");
        }
        return result;
    }

    @PutMapping({"/edit"})
    @Operation(summary="online表单明细-修改")
    /* renamed from: b */
    public CommonResult<OnlCgformField> edit(@RequestBody OnlCgformField onlCgformField) {
        CommonResult<OnlCgformField> result = new CommonResult<>();
        if (this.onlCgformFieldService.getById(onlCgformField.getId()) == null) {
            return error("未找到对应实体");
        } else if (this.onlCgformFieldService.updateById(onlCgformField)) {
            success("修改成功!");
        }
        return result;
    }

    @DeleteMapping({"/delete"})
    @Operation(summary="online表单明细-删除")
    /* renamed from: c */
    public CommonResult<OnlCgformField> delete(@RequestParam(name = "id", required = true) String str) {
        CommonResult<OnlCgformField> result = new CommonResult<>();
        if (this.onlCgformFieldService.getById(str) == null) {
            return error("未找到对应实体");
        } else if (this.onlCgformFieldService.removeById(str)) {
            success("删除成功!");
        }
        return result;
    }

    @DeleteMapping({"/deleteBatch"})
    @Operation(summary="online表单明细-批量删除")
    /* renamed from: d */
    public CommonResult<OnlCgformField> deleteBatch(@RequestParam(name = "ids", required = true) String str) {
        CommonResult<OnlCgformField> result = new CommonResult<>();
        if (str == null || str.trim().isEmpty()) {
            return error("参数不识别！");
        } else {
            this.onlCgformFieldService.removeByIds(Arrays.asList(str.split(CgformUtil.COMMA_SEPARATOR)));
            success("删除成功!");
        }
        return result;
    }

    @GetMapping({"/queryById"})
    @Operation(summary="online表单明细-通过id查询")
    /* renamed from: e */
    public CommonResult<OnlCgformField> queryById(@RequestParam(name = "id", required = true) String str) {
        CommonResult<OnlCgformField> result = new CommonResult<>();
        OnlCgformField onlCgformField = this.onlCgformFieldService.getById(str);
        if (onlCgformField == null) {
            return error("未找到对应实体");
        } else {
//            result.setResult(onlCgformField);
//            result.setSuccess(true);
            return success(onlCgformField);
        }
    }
}
