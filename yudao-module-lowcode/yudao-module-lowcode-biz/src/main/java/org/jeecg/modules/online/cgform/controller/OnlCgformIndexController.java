package org.jeecg.modules.online.cgform.controller;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
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

/* compiled from: OnlCgformIndexController.java */
@RequestMapping({"/online/cgform/index"})
@RestController("onlCgformIndexController")
/* renamed from: org.jeecg.modules.online.cgform.c.e */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/c/e.class */
@Tag(name = "online表单索引")
public class OnlCgformIndexController {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(OnlCgformIndexController.class);

    @Autowired
    private IOnlCgformIndexService onlCgformIndexService;

    @GetMapping({"/listByHeadId"})
    @Operation(summary = "通过表头ID查询索引列表")
    /* renamed from: a */
    public CommonResult<?> m166a(@RequestParam("headId") String str) {
        QueryWrapper<OnlCgformIndex> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cgform_head_id", str);
        queryWrapper.eq("del_flag", CommonConstant.DEL_FLAG_0);
        queryWrapper.orderByDesc("create_time");
        return success(this.onlCgformIndexService.list(queryWrapper));
    }

    @GetMapping({"/list"})
    @Operation(summary = "获取索引列表")
    /* renamed from: a */
    public CommonResult<IPage<OnlCgformIndex>> m167a(OnlCgformIndex onlCgformIndex, @RequestParam(name = "pageNo", defaultValue = "1") Integer num, @RequestParam(name = "pageSize", defaultValue = "10") Integer num2, HttpServletRequest httpServletRequest) {
        CommonResult<IPage<OnlCgformIndex>> result = new CommonResult<>();
        IPage<OnlCgformIndex> page = this.onlCgformIndexService.page(new Page<>(num, num2), QueryGenerator.initQueryWrapper(onlCgformIndex, httpServletRequest.getParameterMap()));
//        result.setSuccess(true);
//        result.setResult(page);
        return success(page);
    }

    @PostMapping({"/add"})
    @Operation(summary = "添加索引")
    /* renamed from: a */
    public CommonResult<OnlCgformIndex> add(@RequestBody OnlCgformIndex onlCgformIndex) {
        CommonResult<OnlCgformIndex> result = new CommonResult<>();
        try {
            this.onlCgformIndexService.save(onlCgformIndex);
            success("添加成功！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("操作失败");
        }
        return result;
    }

    @PutMapping({"/edit"})
    @Operation(summary = "编辑索引")
    /* renamed from: b */
    public CommonResult<OnlCgformIndex> edit(@RequestBody OnlCgformIndex onlCgformIndex) {
        CommonResult<OnlCgformIndex> result = new CommonResult<>();
        if (this.onlCgformIndexService.getById(onlCgformIndex.getId()) == null) {
            return error("未找到对应实体");
        } else if (this.onlCgformIndexService.updateById(onlCgformIndex)) {
            success("修改成功!");
        }
        return result;
    }

    @DeleteMapping({"/delete"})
    @Operation(summary = "删除索引")
    /* renamed from: b */
    public CommonResult<OnlCgformIndex> delete(@RequestParam(name = "id", required = true) String str) {
        CommonResult<OnlCgformIndex> result = new CommonResult<>();
        if (this.onlCgformIndexService.getById(str) == null) {
            return error("未找到对应实体");
        } else if (this.onlCgformIndexService.removeById(str)) {
            success("删除成功!");
        }
        return result;
    }

    @DeleteMapping({"/deleteBatch"})
    @Operation(summary = "批量删除索引")
    /* renamed from: c */
    public CommonResult<OnlCgformIndex> deleteBatch(@RequestParam(name = "ids", required = true) String str) {
        CommonResult<OnlCgformIndex> result = new CommonResult<>();
        if (str == null || str.trim().isEmpty()) {
            return error("参数不识别！");
        } else {
            this.onlCgformIndexService.removeByIds(Arrays.asList(str.split(CgformUtil.COMMA_SEPARATOR)));
            success("删除成功!");
        }
        return result;
    }

    @GetMapping({"/queryById"})
    @Operation(summary = "通过id查询索引")
    /* renamed from: d */
    public CommonResult<OnlCgformIndex> queryById(@RequestParam(name = "id", required = true) String str) {
        CommonResult<OnlCgformIndex> result = new CommonResult<>();
        OnlCgformIndex onlCgformIndex = (OnlCgformIndex) this.onlCgformIndexService.getById(str);
        if (onlCgformIndex == null) {
            return error("未找到对应实体");
        } else {
//            result.setResult(onlCgformIndex);
//            result.setSuccess(true);
            return success(onlCgformIndex);
        }
    }
}
