package org.jeecg.modules.online.cgform.controller;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;


import org.jeecg.common.constant.CgformEnum;
import org.jeecg.modules.codegenerate.DbReadTableUtil;
import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.modules.online.annotation.PermissionData;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgreport.constant.CgReportConstant;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.query.QueryGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
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

/* compiled from: OnlCgformHeadController.java */
@RequestMapping({"/online/cgform/head"})
@RestController("onlCgformHeadController")
/* renamed from: org.jeecg.modules.online.cgform.c.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/c/d.class */
@Tag(name="online表单head")
public class OnlCgformHeadController {

    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    private IOnlCgformEnhanceService onlCgformEnhanceService;

    @Autowired
    ResourceLoader resourceLoader;

    /* renamed from: c */
    private static String f142c;

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(OnlCgformHeadController.class);

    /* renamed from: b */
    private static List<String> excludeTables = null;

    /* renamed from: a */
    public OnlCgformHeadController(){

    }

    /**
     *
     * 获取在线表单表头
     * @param onlCgformHead 请求参数
     * @param pageNo
     * @param pageSize
     * @param httpServletRequest
     * @return
     */
    @GetMapping({"/list"})
    @PermissionData
    /* renamed from: a */
    @Operation(summary="online表单-查询")
    public CommonResult<IPage<OnlCgformHead>> getList(OnlCgformHead onlCgformHead,
                                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                      HttpServletRequest httpServletRequest) {
        CommonResult<IPage<OnlCgformHead>> result = new CommonResult<>();
        IPage<OnlCgformHead> page = this.onlCgformHeadService.page(new Page<>(pageNo, pageSize), QueryGenerator.initQueryWrapper(onlCgformHead, httpServletRequest.getParameterMap()));
        if (onlCgformHead.getCopyType() != null && onlCgformHead.getCopyType() == 0) {
            this.onlCgformHeadService.initCopyState(page.getRecords());
        }
//        result.setSuccess(true);
//        result.setResult(page);
        
        return success(page);
    }

    @PostMapping({"/add"})
    @Operation(summary="online表单-新增")
    /* renamed from: a */
    public CommonResult<OnlCgformHead> addOnlCgFormHead(@RequestBody OnlCgformHead onlCgformHead) {
        CommonResult<OnlCgformHead> result = new CommonResult<>();
        try {
            this.onlCgformHeadService.save(onlCgformHead);
            success("添加成功！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("操作失败");
        }
        return result;
    }

    @PutMapping({"/edit"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary="online表单-编辑")
    /* renamed from: b */
    public CommonResult<OnlCgformHead> edit(@RequestBody OnlCgformHead onlCgformHead) {
        CommonResult<OnlCgformHead> result = new CommonResult<>();
        if (this.onlCgformHeadService.getById(onlCgformHead.getId()) == null) {
            return error("未找到对应实体");
        } else if (this.onlCgformHeadService.updateById(onlCgformHead)) {
            success("修改成功!");
        }
        return result;
    }

    @DeleteMapping({"/delete"})
    @Operation(summary="online表单-删除")
    /* renamed from: a */
    public CommonResult<?> delete(@RequestParam(name = "id", required = true) String str) {
        try {
            this.onlCgformHeadService.deleteRecordAndTable(str);
            return  success("删除成功!");
        } catch (SQLException e) {
            return error("删除失败" + e.getMessage());
        } catch (DBException e2) {
            return error("删除失败" + e2.getMessage());
        }
    }

    @DeleteMapping({"/removeRecord"})
    @Operation(summary="online表单-删除记录")
    /* renamed from: b */
    public CommonResult<?> removeRecord(@RequestParam(name = "id", required = true) String str) {
        try {
            this.onlCgformHeadService.deleteRecord(str);
            return  success("移除成功!");
        } catch (SQLException e) {
            return error("移除失败" + e.getMessage());
        } catch (DBException e2) {
            return error("移除失败" + e2.getMessage());
        }
    }

    @DeleteMapping({"/deleteBatch"})
    @Operation(summary="online表单-批量删除")
    /* renamed from: a */
    public CommonResult<OnlCgformHead> deleteBatch(@RequestParam(name = "ids", required = true) String str, @RequestParam(name = "flag") String str2) {
        CommonResult<OnlCgformHead> result = new CommonResult<>();
        if (str == null || "".equals(str.trim())) {
            return error("参数不识别！");
        } else {
            this.onlCgformHeadService.deleteBatch(str, str2);
            if ("1".equals(str2)) {
                success("删除成功!");
            } else {
                success("移除成功!");
            }
        }
        return result;
    }

    @GetMapping({"/queryById"})
    @Operation(summary="online表单-查询byid")
    /* renamed from: c */
    public CommonResult<OnlCgformHead> queryById(@RequestParam(name = "id", required = true) String str) {
        CommonResult<OnlCgformHead> result = new CommonResult<>();
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(str);
        if (onlCgformHead == null) {
            return error("未找到对应实体");
        } else {
//            result.setResult(onlCgformHead);
//            result.setSuccess(true);
            return success(onlCgformHead);
        }
    }

    @GetMapping({"/queryByTableNames"})
    @Operation(summary="online表单-通过表名查询")
    /* renamed from: d */
    public CommonResult<?> queryByTableNames(@RequestParam(name = "tableNames", required = true) String str) {
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(OnlCgformHead::getTableName, Arrays.asList(str.split(CgformUtil.COMMA_SEPARATOR)));
        List<OnlCgformHead> list = this.onlCgformHeadService.list(lambdaQueryWrapper);
        if (list == null) {
            return error("未找到对应实体");
        }
        return  success(list);
    }

    @PostMapping({"/enhanceJs/{code}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    /* renamed from: a */
    @Operation(summary="online表单-增强js保存")
    public CommonResult<?> createEnhanceJs(@PathVariable("code") String str, @RequestBody OnlCgformEnhanceJs onlCgformEnhanceJs) {
        try {
            onlCgformEnhanceJs.setCgformHeadId(str);
            this.onlCgformHeadService.saveEnhance(onlCgformEnhanceJs);
            return  success("保存成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("保存失败!");
        }
    }

    @GetMapping({"/enhanceJs/{code}"})
    /* renamed from: a */
    @Operation(summary="online表单-增强js查询")
    public CommonResult<?> getEnhanceJs(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        try {
            OnlCgformEnhanceJs queryEnhance = this.onlCgformHeadService.queryEnhance(code, httpServletRequest.getParameter("type"));
            if (queryEnhance == null) {
                return error("查询为空");
            }
            return  success(queryEnhance);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("查询失败!");
        }
    }

    @PutMapping({"/enhanceJs/{code}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary="online表单-增强js编辑")
    /* renamed from: b */
    public CommonResult<?> updateEnhanceJs(@PathVariable("code") String str, @RequestBody OnlCgformEnhanceJs onlCgformEnhanceJs) {
        try {
            onlCgformEnhanceJs.setCgformHeadId(str);
            this.onlCgformHeadService.editEnhance(onlCgformEnhanceJs);
            return  success("保存成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("保存失败!");
        }
    }

    @GetMapping({"/enhanceButton/{formId}"})
    /* renamed from: b */
    @Operation(summary="online表单-增强按钮查询")
    public CommonResult<?> getEnhanceButton(@PathVariable("formId") String str, HttpServletRequest httpServletRequest) {
        try {
            List<OnlCgformButton> queryButtonList = this.onlCgformHeadService.queryButtonList(str);
            if (queryButtonList == null || queryButtonList.isEmpty()) {
                return error("查询为空");
            }
            return  success(queryButtonList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("查询失败!");
        }
    }

    @GetMapping({"/enhanceSql/{formId}"})
    @Operation(summary="online表单-增强sql查询")
    /* renamed from: c */
    public CommonResult<?> getEnhanceSql(@PathVariable("formId") String str, HttpServletRequest httpServletRequest) {
        return  success(this.onlCgformEnhanceService.queryEnhanceSqlList(str));
    }

    @PostMapping({"/enhanceSql/{formId}"})
//    @RequiresPermissions({"online:form:enhanceSql:save"})
    @PreAuthorize("@ss.hasPermission('online:form:enhanceSql:save')")
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary="online表单-增强sql保存")
    /* renamed from: a */
    public CommonResult<?> createEnhanceSql(@PathVariable("formId") String str, @RequestBody OnlCgformEnhanceSql onlCgformEnhanceSql) {
        try {
            onlCgformEnhanceSql.setCgformHeadId(str);
            if (this.onlCgformEnhanceService.checkOnlyEnhance(onlCgformEnhanceSql)) {
                this.onlCgformEnhanceService.saveEnhanceSql(onlCgformEnhanceSql);
                return  success("保存成功!");
            }
            return error("保存失败,该按钮已存在增强配置!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("保存失败!");
        }
    }

//    @RequiresPermissions({"online:form:enhanceSql:edit"})
    @PreAuthorize("@ss.hasPermission('online:form:enhanceSql:edit')")
    @PutMapping({"/enhanceSql/{formId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary="online表单-增强sql编辑")
    /* renamed from: b */
    public CommonResult<?> updateEnhanceSql(@PathVariable("formId") String str, @RequestBody OnlCgformEnhanceSql onlCgformEnhanceSql) {
        try {
            onlCgformEnhanceSql.setCgformHeadId(str);
            if (this.onlCgformEnhanceService.checkOnlyEnhance(onlCgformEnhanceSql)) {
                this.onlCgformEnhanceService.updateEnhanceSql(onlCgformEnhanceSql);
                return  success("保存成功!");
            }
            return error("保存失败,该按钮已存在增强配置!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("保存失败!");
        }
    }

    @DeleteMapping({"/enhanceSql"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary="online表单-增强sql删除")
    /* renamed from: e */
    public CommonResult<?> deleteEnhanceSql(@RequestParam(name = "id", required = true) String str) {
        try {
            this.onlCgformEnhanceService.deleteEnhanceSql(str);
            return  success("删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("删除失败!");
        }
    }

    @DeleteMapping({"/deletebatchEnhanceSql"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary="online表单-增强sql批量删除")
    /* renamed from: f */
    public CommonResult<?> deleteBatchEnhanceSql(@RequestParam(name = "ids", required = true) String str) {
        try {
            this.onlCgformEnhanceService.deleteBatchEnhanceSql(Arrays.asList(str.split(CgformUtil.COMMA_SEPARATOR)));
            return  success("删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("删除失败!");
        }
    }

    @GetMapping({"/enhanceJava/{formId}"})
    @Operation(summary="online表单-增强java查询")
    /* renamed from: a */
    public CommonResult<?> getEnhanceJava(@PathVariable("formId") String str, OnlCgformEnhanceJava onlCgformEnhanceJava) {
        return  success(this.onlCgformEnhanceService.queryEnhanceJavaList(str));
    }

    @PostMapping({"/enhanceJava/{formId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary="online表单-增强java保存")
    /* renamed from: b */
    public CommonResult<?> createEnhanceJava(@PathVariable("formId") String str, @RequestBody OnlCgformEnhanceJava onlCgformEnhanceJava) {
        try {
            if ("1".equals(onlCgformEnhanceJava.getActiveStatus()) && !CgformUtil.m209a(onlCgformEnhanceJava)) {
                return error("类实例化失败，请检查!");
            }
            onlCgformEnhanceJava.setCgformHeadId(str);
            String buttonCode = onlCgformEnhanceJava.getButtonCode();
            if ("import".equals(buttonCode) || "export".equals(buttonCode) || "query".equals(buttonCode)) {
                onlCgformEnhanceJava.setEvent(CgformUtil.f248aq);
            }
            if (this.onlCgformEnhanceService.checkOnlyEnhance(onlCgformEnhanceJava)) {
                this.onlCgformEnhanceService.saveEnhanceJava(onlCgformEnhanceJava);
                return  success("保存成功!");
            }
            return error("保存失败：一个按钮、事件只能有一个增强！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("保存失败!");
        }
    }

    @PutMapping({"/enhanceJava/{formId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary="online表单-增强java编辑")
    /* renamed from: c */
    public CommonResult<?> updateEnhanceJava(@PathVariable("formId") String str, @RequestBody OnlCgformEnhanceJava onlCgformEnhanceJava) {
        try {
            if ("1".equals(onlCgformEnhanceJava.getActiveStatus()) && !CgformUtil.m209a(onlCgformEnhanceJava)) {
                return error("类实例化失败，请检查!");
            }
            onlCgformEnhanceJava.setCgformHeadId(str);
            String buttonCode = onlCgformEnhanceJava.getButtonCode();
            if ("import".equals(buttonCode) || "export".equals(buttonCode) || "query".equals(buttonCode)) {
                onlCgformEnhanceJava.setEvent(CgformUtil.f248aq);
            }
            if (this.onlCgformEnhanceService.checkOnlyEnhance(onlCgformEnhanceJava)) {
                this.onlCgformEnhanceService.updateEnhanceJava(onlCgformEnhanceJava);
                return  success("保存成功!");
            }
            return error("保存失败：一个按钮、事件只能有一个增强！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("保存失败!");
        }
    }

    @DeleteMapping({"/enhanceJava"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary="online表单-增强java删除")
    /* renamed from: g */
    public CommonResult<?> deleteEnhanceJava(@RequestParam(name = "id") String str) {
        try {
            this.onlCgformEnhanceService.deleteEnhanceJava(str);
            return  success("删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("删除失败!");
        }
    }

    @DeleteMapping({"/deleteBatchEnhanceJava"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary="online表单-增强java批量删除")
    /* renamed from: h */
    public CommonResult<?> deleteBatchEnhanceJava(@RequestParam(name = "ids") String str) {
        try {
            this.onlCgformEnhanceService.deleteBatchEnhanceJava(Arrays.asList(str.split(CgformUtil.COMMA_SEPARATOR)));
            return  success("删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("删除失败!");
        }
    }

//    @RequiresPermissions({"online:form:queryTables"})
    @PreAuthorize("@ss.hasPermission('online:form:queryTables')")
    @GetMapping({"/queryTables"})
    @Operation(summary="online表单-查询数据库表")
    /* renamed from: a */
    public CommonResult<?> queryTables(@RequestParam(name = "tableName", required = false) String tableName, @RequestParam(name = "pageNo", defaultValue = "1") Integer num, @RequestParam(name = "pageSize", defaultValue = "10") Integer num2, HttpServletRequest httpServletRequest) {
        if (!"admin".equals(SecurityFrameworkUtils.getUserName())) {
            return error("noadminauth");
        }
        try {
            List<String> readAllTableNames = DbReadTableUtil.readAllTableNames();
            CgformUtil.m210b(readAllTableNames);
            List<String> m244f = CgformUtil.m244f(readAllTableNames);
            List<String> queryOnlinetables = this.onlCgformHeadService.queryOnlinetables();
            loadExcludeTables();
            m244f.removeAll(queryOnlinetables);
            ArrayList<Map<String,String>> arrayList = new ArrayList<>();
            for (String str2 : m244f) {
                if (!isExclude(str2)) {
                    HashMap<String,String> hashMap = new HashMap<>(5);
                    hashMap.put("id", str2);
                    arrayList.add(hashMap);
                }
            }
            return  success(arrayList);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return error("同步失败，未获取数据库表信息");
        }
    }

    @PostMapping({"/transTables/{tbnames}"})
    @Operation(summary="online表单-同步数据库表")
    /* renamed from: d */
    public CommonResult<?> createTransTables(@PathVariable("tbnames") String str, HttpServletRequest httpServletRequest) {
        if (!"admin".equals(SecurityFrameworkUtils.getUserName())) {
            return error("noadminauth");
        }
        if (ConvertUtils.isEmpty(str)) {
            return error("未识别的表名信息");
        }
        if (f142c != null && f142c.equals(str)) {
            return error("不允许重复生成!");
        }
        f142c = str;
        String[] split = str.split(CgformUtil.COMMA_SEPARATOR);
        for (int i = 0; i < split.length; i++) {
            if (ConvertUtils.isNotEmpty(split[i]) && this.onlCgformHeadService.count(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, split[i])) <= 0) {
                this.onlCgformHeadService.saveDbTable2Online(split[i]);
            }
        }
        f142c = null;
        return  success("同步完成!");
    }

//    @RequiresPermissions({"online:codeGenerate:projectPath"})
    @GetMapping({"/rootFile"})
    @Operation(summary="online表单-获取项目在服务器的根目录")
    /* renamed from: a */
    @PreAuthorize("@ss.hasPermission('online:codeGenerate:projectPath') && @ss.hasRole('admin')")
    public CommonResult<?> rootFile() {
//        if (!SecurityUtils.getSubject().hasRole("admin")) {
//            throw exception("权限不足，只有admin角色才有权限，获取服务器目录！");
//        }
        JSONArray jSONArray = new JSONArray();
        for (File file : File.listRoots()) {
            JSONObject jSONObject = new JSONObject();
            if (file.isDirectory()) {
                jSONObject.put("key", file.getAbsolutePath());
                jSONObject.put(CgformUtil.TITLE, file.getPath());
                jSONObject.put("opened", false);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("icon", "custom");
                jSONObject.put("scopedSlots", jSONObject2);
                jSONObject.put("isLeaf", file.listFiles() == null || Objects.requireNonNull(file.listFiles()).length == 0);
            }
            jSONArray.add(jSONObject);
        }
        return  success(jSONArray);
    }

//    @RequiresPermissions({"online:codeGenerate:projectPath"})
    @GetMapping({"/fileTree"})
    @Operation(summary="online表单-获取服务器目录的文件树")
    @PreAuthorize("@ss.hasPermission('online:codeGenerate:projectPath') && @ss.hasRole('admin')")
    /* renamed from: i */
    public CommonResult<?> m158i(@RequestParam(name = "parentPath", required = true) String parentPath) {
//        if (!SecurityUtils.getSubject().hasRole("admin")) {
//            throw exception("权限不足，只有admin角色才有权限，获取服务器目录！");
//        }
        JSONArray jSONArray = new JSONArray();
        for (File file : Objects.requireNonNull(new File(parentPath).listFiles())) {
            if (file.isDirectory() && ConvertUtils.isNotEmpty(file.getPath())) {
                JSONObject jSONObject = new JSONObject();
                System.out.println(file.getPath());
                jSONObject.put("key", file.getAbsolutePath());
                jSONObject.put(CgformUtil.TITLE, file.getPath().substring(file.getPath().lastIndexOf(File.separator) + 1));
                jSONObject.put("isLeaf", file.listFiles() == null || Objects.requireNonNull(file.listFiles()).length == 0);
                jSONObject.put("opened", false);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("icon", "custom");
                jSONObject.put("scopedSlots", jSONObject2);
                jSONArray.add(jSONObject);
            }
        }
        return  success(jSONArray);
    }

    @GetMapping({"/tableInfo"})
    @Operation(summary="online表单-表信息")
    /* renamed from: j */
    public CommonResult<?> tableInfo(@RequestParam(name = "code", required = true) String str) {
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(str);
        if (onlCgformHead == null) {
            return error("未找到对应实体");
        }
        HashMap<String,Object> hashMap = new HashMap<>(5);
        hashMap.put(CgReportConstant.MAIN, onlCgformHead);
        if (onlCgformHead.getTableType() == 2) {
            String subTableStr = onlCgformHead.getSubTableStr();
            if (ConvertUtils.isNotEmpty(subTableStr)) {
                ArrayList<OnlCgformHead> arrayList = new ArrayList<>();
                for (String str2 : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.eq(OnlCgformHead::getTableName, str2);
                    arrayList.add(this.onlCgformHeadService.getOne(lambdaQueryWrapper));
                }
                // from class: org.jeecg.modules.online.cgform.c.d.1
// java.util.Comparator
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                Collections.sort(arrayList, (onlCgformHead2, onlCgformHead3) -> {
                    Integer tabOrderNum = onlCgformHead2.getTabOrderNum();
                    if (tabOrderNum == null) {
                        tabOrderNum = 0;
                    }
                    Integer tabOrderNum2 = onlCgformHead3.getTabOrderNum();
                    if (tabOrderNum2 == null) {
                        tabOrderNum2 = 0;
                    }
                    return tabOrderNum.compareTo(tabOrderNum2);
                });
                hashMap.put("sub", arrayList);
            }
        }
        Integer tableType = onlCgformHead.getTableType();
        if ("Y".equals(onlCgformHead.getIsTree())) {
            tableType = 3;
        }
        hashMap.put("jspModeList", CgformEnum.getJspModelList(tableType));
        hashMap.put("projectPath", DbReadTableUtil.getProjectPath());
        return  success(hashMap);
    }

    @PostMapping({"/copyOnline"})
    @Operation(summary="online表单-复制表")
    /* renamed from: k */
    public CommonResult<?> m160k(@RequestParam(name = "code", required = true) String str) throws Exception {
        OnlCgformHead onlCgformHead = null;
        try {
            onlCgformHead = this.onlCgformHeadService.getById(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (onlCgformHead == null) {
            return error("未找到对应实体");
        }
        this.onlCgformHeadService.copyOnlineTableConfig(onlCgformHead);
        return  success(true);
    }

    @GetMapping({"/copyOnlineTable/{id}"})
    @Operation(summary="online表单-复制表")
    /* renamed from: b */
    public CommonResult<?> copyOnlineTable(@PathVariable("id") String str, @RequestParam(name = "tableName") String str2) {
        try {
            this.onlCgformHeadService.copyOnlineTable(str, str2);
            return  success(true);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 查看是否被排除
     * @param table 表名
     * @return 是否被排除
     */
    /* renamed from: l */
    private boolean isExclude(String table) {
        for (String str2 : excludeTables) {
            if (table.startsWith(str2) || table.startsWith(str2.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从配置文件中加载排除的表
     */
    /* renamed from: b */
    private void loadExcludeTables() {
        if (excludeTables == null) {
            InputStream inputStream = null;
            try {
                try {
                    //获取目录下的配置文件并加载
                    inputStream = this.resourceLoader.getResource("classpath:jeecg" + File.separator + "jeecg_config.properties").getInputStream();
                    Properties properties = new Properties();
                    properties.load(inputStream);
                    //获取排除的table
                    String property = properties.getProperty("exclude_table");
                    if (property != null) {
                        excludeTables = Arrays.asList(property.split(CgformUtil.COMMA_SEPARATOR));
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                }
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }
}
