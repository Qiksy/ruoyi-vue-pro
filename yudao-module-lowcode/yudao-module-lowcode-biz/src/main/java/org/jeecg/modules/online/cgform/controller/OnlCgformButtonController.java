package org.jeecg.modules.online.cgform.controller;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.service.IOnlCgformButtonService;
import org.jeecg.query.QueryGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/* compiled from: OnlCgformButtonController.java */
@RequestMapping({"/online/cgform/button"})
@RestController("onlCgformButtonController")
@Tag(name="online表单自定义按钮")
/* renamed from: org.jeecg.modules.online.cgform.c.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/c/b.class */
public class OnlCgformButtonController {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(OnlCgformButtonController.class);

    @Autowired
    private IOnlCgformButtonService onlCgformButtonService;

    @GetMapping({"/list/{code}"})
    @Operation(summary = "获取按钮列表")
    /* renamed from: a */
    public CommonResult<IPage<OnlCgformButton>> getList(OnlCgformButton onlCgformButton, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest httpServletRequest,
                                                        @PathVariable("code") String code) {
        CommonResult<IPage<OnlCgformButton>> result = new CommonResult<>();
        onlCgformButton.setCgformHeadId(code);
        IPage<OnlCgformButton> page = this.onlCgformButtonService.page(new Page<>(pageNo, pageSize), QueryGenerator.initQueryWrapper(onlCgformButton, httpServletRequest.getParameterMap()));
        
        return success(page);
    }

    @PostMapping({"/add"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "添加按钮")
    /* renamed from: a */
    public CommonResult<OnlCgformButton> m118a(@RequestBody OnlCgformButton onlCgformButton) {
        CommonResult<OnlCgformButton> result = new CommonResult<>();
        try {
            this.onlCgformButtonService.save(onlCgformButton);
            success("添加成功！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("操作失败");
        }
        return result;
    }

    @PostMapping({"/aitest"})
    @Operation(summary = "ai测试按钮")
    /* renamed from: a */
    public CommonResult<OnlCgformButton> aitest(@RequestBody JSONArray jSONArray) {
        CommonResult<OnlCgformButton> result = new CommonResult<>();
        for (int i = 0; i < jSONArray.size(); i++) {
            try {
                this.onlCgformButtonService.saveButton(JSONObject.parseObject(jSONArray.getJSONObject(i).toJSONString(), OnlCgformButton.class));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return error("操作失败");
            }
        }
        success("添加成功！");
        return result;
    }

    @PutMapping({"/edit"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "编辑按钮")
    /* renamed from: b */
    public CommonResult<OnlCgformButton> edit(@RequestBody OnlCgformButton onlCgformButton) {
        CommonResult<OnlCgformButton> result = new CommonResult<>();
        if (this.onlCgformButtonService.getById(onlCgformButton.getId()) == null) {
            return error("未找到对应实体");
        } else if (this.onlCgformButtonService.updateById(onlCgformButton)) {
            success("修改成功!");
        }
        return result;
    }

    @DeleteMapping({"/delete"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "删除按钮")
    /* renamed from: a */
public CommonResult<OnlCgformButton> delete(@RequestParam(name = "id", required = true) String id) {
        CommonResult<OnlCgformButton> result = new CommonResult<>();
        if (this.onlCgformButtonService.getById(id) == null) {
            return error("未找到对应实体");
        } else if (this.onlCgformButtonService.removeById(id)) {
            success("删除成功!");
        }
        return result;
    }

    @DeleteMapping({"/deleteBatch"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "批量删除按钮")
    /* renamed from: b */
    public CommonResult<OnlCgformButton> deleteBatch(@RequestParam(name = "ids", required = true) String str) {
        CommonResult<OnlCgformButton> result = new CommonResult<>();
        if (str == null || str.trim().isEmpty()) {
            return error("参数不识别！");
        } else {
            this.onlCgformButtonService.removeByIds(Arrays.asList(str.split(CgformUtil.COMMA_SEPARATOR)));
            success("删除成功!");
        }
        return result;
    }

    @GetMapping({"/queryById"})
    @Operation(summary = "通过id查询按钮")
    /* renamed from: c */
    public CommonResult<OnlCgformButton> queryById(@RequestParam(name = "id", required = true) String str) {
        CommonResult<OnlCgformButton> result = new CommonResult<>();
        OnlCgformButton onlCgformButton = this.onlCgformButtonService.getById(str);
        if (onlCgformButton == null) {
            return error("未找到对应实体");
        } else {
//            result.setResult(onlCgformButton);
//            result.setSuccess(true);
            return success(onlCgformButton);
        }
    }
}
