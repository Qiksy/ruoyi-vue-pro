package org.jeecg.modules.online.cgform.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.aspect.annotation.OnlineAuth;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.constant.enums.ModuleType;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.BrowserUtils;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.cgform.converter.ConvertUtil;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.enums.CgformConstant;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
import org.jeecg.modules.online.cgform.model.OnlComplexModel;
import org.jeecg.modules.online.cgform.model.OnlGenerateModel;
import org.jeecg.modules.online.cgform.model.TreeModel;
import org.jeecg.modules.online.cgform.vo.LinkDown;
import org.jeecg.modules.online.cgform.constant.OnlineConst;
import org.jeecg.modules.online.cgform.utils.CgFormExcelHandler;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.utils.GenerateCodeFileToZip;
import org.jeecg.modules.online.cgform.utils.OnlineImportValidator;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformSqlService;
import org.jeecg.modules.online.cgform.service.IOnlineJoinQueryService;
import org.jeecg.modules.online.cgform.service.IOnlineService;
import org.jeecg.modules.online.cgreport.constant.CgReportConstant;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.template.DbTableUtil;
import org.jeecgframework.codegenerate.database.DbReadTableUtil;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.PostgresSequenceMaxValueIncrementer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/* compiled from: OnlCgformApiController.java */
@RequestMapping({"/online/cgform/api"})
@RestController("onlCgformApiController")
/* renamed from: org.jeecg.modules.online.cgform.c.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/c/a.class */
@Tag(name = "online表单api")
public class OnlCgformApiController {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(OnlCgformApiController.class);

    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    @Autowired
    IOnlineJoinQueryService onlineJoinQueryService;

    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    private IOnlCgformSqlService onlCgformSqlService;

    @Autowired
    private IOnlAuthPageService onlAuthPageService;

    @Autowired
    @Lazy
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private IOnlineService onlineService;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Value("${jeecg.uploadType}")
    private String uploadType;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JeecgBaseConfig jeecgBaseConfig;

    /* renamed from: a */
    public OnlCgformApiController() {
    }

    @PostMapping({"/addAll"})
    @Operation(summary = "新增online在线表单")
    /* renamed from: a */
    public Result<?> addAll(@RequestBody OnlCgformModel onlCgformModel) {
        try {
            String tableName = onlCgformModel.getHead().getTableName();
            if (DbTableUtil.isTableExistsInDatabase(tableName)) {
                return Result.error("数据库表[" + tableName + "]已存在,请从数据库导入表单");
            }
            if (onlCgformModel.getHead().getTableType() == 3) {
                if (oConvertUtils.isEmpty(onlCgformModel.getHead().getRelationType())) {
                    return Result.error("附表必须选择映射关系！");
                }
                if (oConvertUtils.isEmpty(onlCgformModel.getHead().getTabOrderNum())) {
                    return Result.error("附表必须填写排序序号！");
                }
            }
            return this.onlCgformHeadService.addAll(onlCgformModel);
        } catch (Exception e) {
            logger.error("OnlCgformApiController.addAll()发生异常：" + e.getMessage(), e);
            return Result.error("操作失败");
        }
    }

    /**
     * 编辑索引的时候，会调用这个接口
     *
     * @param onlCgformModel 表单模型
     * @return Result<?>
     */
    @PutMapping({"/editAll"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "编辑online在线表单")
    /* renamed from: b */
    public Result<?> editAll(@RequestBody OnlCgformModel onlCgformModel) {
        try {
            if (onlCgformModel.getHead().getTableType() == 3) {
                if (oConvertUtils.isEmpty(onlCgformModel.getHead().getRelationType())) {
                    return Result.error("附表必须选择映射关系！");
                }
                if (oConvertUtils.isEmpty(onlCgformModel.getHead().getTabOrderNum())) {
                    return Result.error("附表必须填写排序序号！");
                }
            }
            return this.onlCgformHeadService.editAll(onlCgformModel);
        } catch (Exception e) {
            logger.error("OnlCgformApiController.editAll()发生异常：" + e.getMessage(), e);
            return Result.error("操作失败");
        }
    }

    @OnlineAuth("getColumns")
    @AutoLog(operateType = 1, value = "online列表加载", module = ModuleType.ONLINE)
    @GetMapping({"/getColumns/{code}"})
    @Operation(summary = "online表单加载所有的列")
    /* renamed from: a */
    public Result<OnlComplexModel> getColumns(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        Result<OnlComplexModel> result = new Result<>();
        try {
            OnlCgformHead table = this.onlCgformHeadService.getTable(code);
            String parameter = httpServletRequest.getParameter(OnlineConst.LINK_TABLE_SELECT_FIELDS);
            if (oConvertUtils.isNotEmpty(parameter)) {
                table.setSelectFieldString(parameter);
            }
            OnlComplexModel queryOnlineConfig = this.onlineService.queryOnlineConfig(table, ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getUsername());
            queryOnlineConfig.setIsDesForm(table.getIsDesForm());
            queryOnlineConfig.setDesFormCode(table.getDesFormCode());
            result.setResult(queryOnlineConfig);
            result.setOnlTable(table.getTableName());
            return result;
        } catch (DBException e) {
            result.error500("实体不存在");
            return result;
        }
    }

    @OnlineAuth("getData")
    @PermissionData
    @GetMapping({"/getData/{code}"})
    @Operation(summary = "online表单加载数据")
    /* renamed from: b */
    public Result<Map<String, Object>> getData(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        Map<String, Object> queryAutolistPage;
        Result<Map<String, Object>> result = new Result<>();
        try {
            OnlCgformHead table = this.onlCgformHeadService.getTable(code);
            if (oConvertUtils.isEmpty(table.getPhysicId()) && "N".equals(table.getIsDbSynch())) {
                result.error500("NO_DB_SYNC");
                return result;
            }
            String parameter = httpServletRequest.getParameter(OnlineConst.LINK_TABLE_SELECT_FIELDS);
            if (oConvertUtils.isNotEmpty(parameter)) {
                table.setSelectFieldString(parameter);
            }
            try {
                Map<String, Object> m190a = CgformUtil.m190a(httpServletRequest);
                if (CgformUtil.m181a(table)) {
                    queryAutolistPage = this.onlineJoinQueryService.pageList(table, m190a);
                } else {
                    queryAutolistPage = this.onlCgformFieldService.queryAutolistPage(table, m190a, null);
                }
                m107a(table, queryAutolistPage);
                result.setResult(queryAutolistPage);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                result.error500("数据库查询失败，" + e.getMessage());
            }
            result.setOnlTable(table.getTableName());
            return result;
        } catch (DBException e2) {
            result.error500("实体不存在");
            return result;
        }
    }

    @OnlineAuth("getFormItem")
    @AutoLog(operateType = 1, value = "online表单加载", module = ModuleType.ONLINE)
    @GetMapping({"/getFormItem/{code}"})
    @Operation(summary = "获取表单控件", description = """
            这里实际上获取了主表的信息 head
            也获取了新增时候的那个form的schema
            """)
    /* renamed from: c */
    public Result<?> getFormItem(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        try {
            OnlCgformHead table = this.onlCgformHeadService.getTable(code);
            Result<JSONObject> result = new Result<>();
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            String parameter = httpServletRequest.getParameter("selectFields");
            if (oConvertUtils.isNotEmpty(parameter)) {
                //todo 这里分割参数之后，没有使用
                List<String> parameterList = Arrays.asList(parameter.split(CgformUtil.COMMA_SEPARATOR));
            }
            JSONObject jsonObject = CgformUtil.m247b(this.onlineService.queryOnlineFormItem(table, loginUser.getUsername()));
            result.setResult(jsonObject);
            result.setOnlTable(table.getTableName());
            return result;
        } catch (DBException e) {
            return Result.error("表不存在");
        }
    }

    @AutoLog(operateType = 1, value = "online根据表名加载表单", module = ModuleType.ONLINE)
    @GetMapping({"/getFormItemBytbname/{table}"})
    /* renamed from: a */
    @Operation(summary = "根据表名加载表单")
    public Result<?> getFormItemBytbname(@PathVariable("table") String str, @RequestParam(name = "taskId", required = false) String str2) {
        Result<JSONObject> result = new Result<>();
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<OnlCgformHead>();
        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, str);
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne(lambdaQueryWrapper);
        if (onlCgformHead == null) {
            Result.error("表不存在");
        }
        result.setResult(CgformUtil.m247b(this.onlineService.queryFlowOnlineFormItem(onlCgformHead, ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getUsername(), str2)));
        result.setOnlTable(str);
        return result;
    }

    @OnlineAuth("getEnhanceJs")
    @GetMapping({"/getEnhanceJs/{code}"})
    /* renamed from: d */
    @Operation(summary = "获取增强js")
    public Result<?> getEnhanceJs(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        return Result.ok(this.onlineService.queryEnahcneJsString(code, CgformUtil.FORM));
    }

    @AutoLog(operateType = 1, value = "online表单数据查询")
    @GetMapping({"/form/{code}/{id}"})
    @Operation(summary = "online表单数据查询")
    /* renamed from: b */
    public Result<?> getFormInfo(@PathVariable("code") String code, @PathVariable("id") String id) {
        try {
            SqlInjectionUtil.filterContent(id, CgformUtil.SINGLE_QUOTE);
            return Result.ok(CgformUtil.m224a(this.onlCgformHeadService.queryManyFormData(code, id)));
        } catch (Exception e) {
            logger.error("Online表单查询异常：" + e.getMessage(), e);
            return Result.error("查询失败，" + e.getMessage());
        }
    }

    @AutoLog(operateType = 1, value = "online表单数据查询")
    @GetMapping({"/detail/{code}/{id}"})
    @Operation(summary = "online表单数据查询")
    /* renamed from: c */
    public Result<?> getFormDetail(@PathVariable("code") String code, @PathVariable("id") String id) {
        try {
            SqlInjectionUtil.filterContent(id, CgformUtil.SINGLE_QUOTE);
            Map<String, Object> queryManyFormData = this.onlCgformHeadService.queryManyFormData(code, id);
            ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
            arrayList.add(CgformUtil.m224a(queryManyFormData));
            this.onlCgformFieldService.handleLinkTableDictData(this.onlCgformHeadService.getTable(code).getId(), arrayList);
            return Result.ok((Map) arrayList.get(0));
        } catch (Exception e) {
            logger.error("Online表单查询异常：" + e.getMessage(), e);
            return Result.error("查询失败，" + e.getMessage());
        }
    }

    @GetMapping({"/subform/{table}/{mainId}"})
    @Operation(summary = "online根据表名查询子表单数据")
    /* renamed from: d */
    public Result<?> getSubformInfo(@PathVariable("table") String str, @PathVariable("mainId") String str2) {
        try {
            SqlInjectionUtil.filterContent(str2, CgformUtil.SINGLE_QUOTE);
            return Result.ok(CgformUtil.m224a(this.onlCgformHeadService.querySubFormData(str, str2)));
        } catch (Exception e) {
            logger.error("Online表单查询异常：" + e.getMessage(), e);
            return Result.error("查询失败，" + e.getMessage());
        }
    }

    @GetMapping({"/subform/list/{table}/{mainId}"})
    @Operation(summary = "online根据表名查询子表单数据")
    /* renamed from: e */
    public Result<?> getSubformDetail(@PathVariable("table") String table, @PathVariable("mainId") String mainId) {
        try {
            SqlInjectionUtil.filterContent(mainId, CgformUtil.SINGLE_QUOTE);
            return Result.ok(this.onlCgformHeadService.queryManySubFormData(table, mainId));
        } catch (Exception e) {
            logger.error("Online表单查询异常：" + e.getMessage(), e);
            return Result.error("查询失败，" + e.getMessage());
        }
    }

    @AutoLog(operateType = 1, value = "online根据表名查询表单数据", module = ModuleType.ONLINE)
    @GetMapping({"/form/table_name/{tableName}/{dataId}"})
    @Operation(summary = "online根据表名查询表单数据")
    /* renamed from: f */
    public Result<?> getFormDataByTableName(@PathVariable("tableName") String tableName, @PathVariable("dataId") String dataId) {
        try {
            LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq((v0) -> {
                return v0.getTableName();
            }, tableName);
            OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getOne(lambdaQueryWrapper);
            if (onlCgformHead == null) {
                throw new Exception("OnlCgform tableName: " + tableName + " 不存在！");
            }
            SqlInjectionUtil.filterContent(dataId, CgformUtil.SINGLE_QUOTE);
            Result<?> m84b = getFormInfo(onlCgformHead.getId(), dataId);
            m84b.setOnlTable(tableName);
            return m84b;
        } catch (Exception e) {
            logger.error("Online表单查询异常，" + e.getMessage(), e);
            return Result.error("查询失败，" + e.getMessage());
        }
    }

    @OnlineAuth(CgformUtil.FORM)
    @PostMapping({"/form/{code}"})
    @AutoLog(operateType = 2, value = "online新增数据", module = ModuleType.ONLINE)
    @CacheEvict(value = {OnlineConst.CACHE_ONLINE_LINK_TABLE}, allEntries = true)
    @Operation(summary = "online表单新增数据新增数据")
    /* renamed from: a */
    public Result<String> createDataByCode(@PathVariable("code") String code, @RequestBody JSONObject jSONObject, HttpServletRequest httpServletRequest) {
        Result<String> result = new Result<>();
        try {
            String id = CgformUtil.nextId();
            jSONObject.put("id", id);
            String saveManyFormData = this.onlCgformHeadService.saveManyFormData(code, jSONObject, TokenUtils.getTokenByRequest(httpServletRequest));
            result.setSuccess(true);
            result.setResult(id);
            result.setOnlTable(saveManyFormData);
            result.setMessage("添加成功!");
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formAdd()发生异常：", e);
            result.setSuccess(false);
            result.setMessage("保存失败，" + CgformUtil.m241a(e));
        }
        return result;
    }

    @OnlineAuth(CgformUtil.FORM)
    @AutoLog(operateType = 3, value = "online修改数据", module = ModuleType.ONLINE)
    @PutMapping({"/form/{code}"})
    @CacheEvict(value = {OnlineConst.CACHE_ONLINE_LINK_TABLE}, allEntries = true)
    @Operation(summary = "online表单修改数据")
    /* renamed from: a */
    public Result<?> m90a(@PathVariable("code") String str, @RequestBody JSONObject jSONObject) {
        try {
            String editManyFormData = this.onlCgformHeadService.editManyFormData(str, jSONObject);
            Result<?> ok = Result.ok("修改成功！");
            ok.setOnlTable(editManyFormData);
            return ok;
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formEdit()发生异常：" + e.getMessage(), e);
            return Result.error("修改失败，" + CgformUtil.m241a(e));
        }
    }

    @OnlineAuth(CgformUtil.FORM)
    @DeleteMapping({"/form/{code}/{id}"})
    @AutoLog(operateType = 4, value = "online删除数据", module = ModuleType.ONLINE)
    @Operation(summary = "online表单删除数据")
    /* renamed from: g */
    public Result<?> m91g(@PathVariable("code") String str, @PathVariable("id") String str2) {
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(str);
        if (onlCgformHead == null) {
            return Result.error("实体不存在");
        }
        try {
            String str3 = "";
            if ("Y".equals(onlCgformHead.getIsTree())) {
                str2 = this.onlCgformFieldService.queryTreeChildIds(onlCgformHead, str2);
                str3 = this.onlCgformFieldService.queryTreePids(onlCgformHead, str2);
            }
            if (str2.indexOf(CgformUtil.COMMA_SEPARATOR) > 0) {
                if (onlCgformHead.getTableType() == 2) {
                    this.onlCgformFieldService.deleteAutoListMainAndSub(onlCgformHead, str2);
                } else {
                    this.onlCgformFieldService.deleteAutoListById(onlCgformHead.getTableName(), str2);
                }
                if ("Y".equals(onlCgformHead.getIsTree())) {
                    String tableName = onlCgformHead.getTableName();
                    String treeIdField = onlCgformHead.getTreeIdField();
                    for (String str4 : str3.split(CgformUtil.COMMA_SEPARATOR)) {
                        this.onlCgformFieldService.updateTreeNodeNoChild(tableName, treeIdField, str4);
                    }
                }
            } else {
                this.onlCgformHeadService.deleteOneTableInfo(str, str2);
            }
            Result<?> ok = Result.ok("删除成功!");
            ok.setOnlTable(onlCgformHead.getTableName());
            return ok;
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formEdit()发生异常：" + e.getMessage(), e);
            return Result.error("删除失败," + e.getMessage());
        }
    }

    @DeleteMapping({"/formByCode/{code}/{id}"})
    @AutoLog(operateType = 4, value = "online删除数据", module = ModuleType.ONLINE)
    @Operation(summary = "online表单删除数据")
    /* renamed from: h */
    public Result<?> m92h(@PathVariable("code") String str, @PathVariable("id") String str2) {
        try {
            String deleteDataByCode = this.onlCgformHeadService.deleteDataByCode(str, str2);
            Result<?> OK = Result.OK("删除成功!", deleteDataByCode);
            OK.setOnlTable(deleteDataByCode);
            return OK;
        } catch (JeecgBootException e) {
            return Result.error(e.getMessage());
        }
    }

    @OnlineAuth("getQueryInfo")
    @GetMapping({"/getQueryInfo/{code}"})
    @Operation(summary = "获取查询信息")
    /* renamed from: a */
    public Result<?> m93a(@PathVariable("code") String str) {
        try {
            return Result.ok(this.onlCgformFieldService.getAutoListQueryInfo(str));
        } catch (Exception e) {
            logger.error("OnlCgformApiController.getQueryInfo()发生异常：" + e.getMessage(), e);
            return Result.error("查询失败");
        }
    }

    @GetMapping({"/getQueryInfoVue3/{code}"})
    @Operation(summary = "获取查询信息")
    /* renamed from: b */
    public Result<?> getQueryInfoVue3(@PathVariable("code") String str) {
        try {
            return Result.ok(this.onlineService.getOnlineVue3QueryInfo(str));
        } catch (Exception e) {
            logger.error("OnlCgformApiController.getQueryInfoVue3()发生异常：" + e.getMessage(), e);
            return Result.error("查询失败");
        }
    }

    @PostMapping({"/doDbSynch/{code}/{synMethod}"})
    @RequiresPermissions({"online:form:syncDb"})
    @Operation(summary = "同步数据库")
    /* renamed from: i */
    public Result<?> m95i(@PathVariable("code") String code, @PathVariable("synMethod") String synMethod) {
        try {
            System.currentTimeMillis();
            this.onlCgformHeadService.doDbSynch(code, synMethod);
            return Result.ok("同步数据库成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.error("同步数据库失败，" + CgformUtil.m241a(e));
        }
    }

    @OnlineAuth("exportXls")
    @PermissionData
    @GetMapping({"/exportXls/{code}"})
    @Operation(summary = "导出excel")
    /* renamed from: a */
    public void exportXls(@PathVariable("code") String str, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(str);
        if (onlCgformHead == null) {
            return;
        }
        String tableTxt = onlCgformHead.getTableTxt();
        String parameter = httpServletRequest.getParameter("paramsStr");
        HashMap<String, Object> hashMap = new HashMap<>(5);
        if (oConvertUtils.isNotEmpty(parameter)) {
            hashMap = (HashMap<String, Object>) JSONObject.parseObject(parameter, Map.class);
        }
        XSSFWorkbook handleOnlineExport = this.onlineJoinQueryService.handleOnlineExport(onlCgformHead, hashMap);
        OutputStream outputStream = null;
        try {
            try {
                httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                String checkBrowse = BrowserUtils.checkBrowse(httpServletRequest);
                String str2 = onlCgformHead.getTableTxt() + "-v" + onlCgformHead.getTableVersion();
                if ("MSIE".equalsIgnoreCase(checkBrowse.substring(0, 4))) {
                    httpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(str2, StandardCharsets.UTF_8) + ".xlsx");
                } else {
                    httpServletResponse.setHeader("content-disposition", "attachment;filename=" + new String(str2.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + ".xlsx");
                }
                outputStream = httpServletResponse.getOutputStream();
                handleOnlineExport.write(outputStream);
                httpServletResponse.flushBuffer();
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            } catch (Exception e2) {
                logger.error("--通过流的方式获取文件异常--" + e2.getMessage(), e2);
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e3) {
                        logger.error(e3.getMessage(), e3);
                    }
                }
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e4) {
                    logger.error(e4.getMessage(), e4);
                }
            }
            throw th;
        }
    }

    @OnlineAuth("exportXlsOld")
    @PermissionData
    @GetMapping({"/exportXlsOld/{code}"})
    @Operation(summary = "导出excel")
    /* renamed from: b */
    public void exportXlsOld(@PathVariable("code") String str, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(str);
        if (onlCgformHead == null) {
            return;
        }
        String tableTxt = onlCgformHead.getTableTxt();
        String parameter = httpServletRequest.getParameter("paramsStr");
        HashMap<String, Object> hashMap = new HashMap<>(5);
        if (oConvertUtils.isNotEmpty(parameter)) {
            TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {
            };
            hashMap = JSONObject.parseObject(parameter, typeReference);
        }
        hashMap.put("pageSize", -521);
        Map<String, Object> pageList = CgformUtil.m181a(onlCgformHead) ? this.onlineJoinQueryService.pageList(onlCgformHead, hashMap, true) : this.onlCgformFieldService.queryAutolistPage(onlCgformHead, hashMap, null);
        List<OnlCgformField> list = (List<OnlCgformField>) pageList.get("fieldList");
        List<Map<String, Object>> list2 = (List<Map<String, Object>>) pageList.get("records");
        List<Map<String, Object>> arrayList;
        String obj = hashMap.get("selections") == null ? null : hashMap.get("selections").toString();
        if (oConvertUtils.isNotEmpty(obj)) {
            List<String> m255h = CgformUtil.m255h(obj);
            arrayList = list2.stream().filter(map -> m255h.contains(map.get("id"))).collect(Collectors.toList());
        } else {
            if (list2 == null) {
                list2 = new ArrayList<>();
            }
            arrayList = new ArrayList<>(list2);
        }
        ConvertUtil.m176a(1, arrayList, list);
        try {
            this.onlCgformHeadService.executeEnhanceExport(onlCgformHead, arrayList);
        } catch (BusinessException e) {
            logger.error("导出java增强处理出错{}", e.getMessage());
        }
        List<ExcelExportEntity> m257b = CgformUtil.m257b((List<OnlCgformField>) list, "id", this.upLoadPath);
        if (onlCgformHead.getTableType() == 2 && oConvertUtils.isEmpty(hashMap.get(CgformUtil.f251at))) {
            String subTableStr = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                for (String str2 : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    this.onlineJoinQueryService.addAllSubTableDate(str2, hashMap, arrayList, m257b, false);
                }
            }
        }
        ExportParams exportParams = new ExportParams((String) null, tableTxt);
        exportParams.setType(ExcelType.XSSF);
        Workbook exportExcel = ExcelExportUtil.exportExcel(exportParams, m257b, arrayList);
        OutputStream outputStream = null;
        try {
            try {
                httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                String checkBrowse = BrowserUtils.checkBrowse(httpServletRequest);
                String str3 = onlCgformHead.getTableTxt() + "-v" + onlCgformHead.getTableVersion();
                if ("MSIE".equalsIgnoreCase(checkBrowse.substring(0, 4))) {
                    httpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(str3, StandardCharsets.UTF_8) + ".xlsx");
                } else {
                    httpServletResponse.setHeader("content-disposition", "attachment;filename=" + new String(str3.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + ".xlsx");
                }
                outputStream = httpServletResponse.getOutputStream();
                exportExcel.write(outputStream);
                httpServletResponse.flushBuffer();
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e2) {
                        logger.error(e2.getMessage(), e2);
                    }
                }
            } catch (Exception e3) {
                logger.error("--通过流的方式获取文件异常--" + e3.getMessage(), e3);
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e4) {
                        logger.error(e4.getMessage(), e4);
                    }
                }
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e5) {
                    logger.error(e5.getMessage(), e5);
                }
            }
            throw th;
        }
    }

    @OnlineAuth("importXls")
    @PostMapping({"/importXls/{code}"})
    @Operation(summary = "导入excel")
    /* renamed from: c */
    public Result<?> importXls(@PathVariable("code") String str, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        OnlCgformHead onlCgformHead = null;
        System.currentTimeMillis();
        Result<String> result = new Result<>();
        String str2 = "";
        String parameter = httpServletRequest.getParameter(CgformUtil.f253av);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(str);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        if (onlCgformHead == null) {
            return Result.error("数据库不存在该表记录");
        }
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str);
        List<OnlCgformField> list = this.onlCgformFieldService.list(lambdaQueryWrapper);
        String parameter2 = httpServletRequest.getParameter(CgformUtil.f252au);
        List<String> m238e = CgformUtil.m238e(list);
        if (oConvertUtils.isEmpty(parameter2) && onlCgformHead.getTableType().intValue() == 2 && oConvertUtils.isNotEmpty(onlCgformHead.getSubTableStr())) {
            for (String str3 : onlCgformHead.getSubTableStr().split(CgformUtil.COMMA_SEPARATOR)) {
                OnlCgformHead onlCgformHead2 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str3));
                if (onlCgformHead2 != null) {
                    List<String> m239c = CgformUtil.m239c(this.onlCgformFieldService.list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, onlCgformHead2.getId())), onlCgformHead2.getTableTxt());
                    if (!m239c.isEmpty()) {
                        m238e.addAll(m239c);
                    }
                }
            }
        }
        JSONObject jSONObject = null;
        String parameter3 = httpServletRequest.getParameter(CgformUtil.f255ax);
        if (oConvertUtils.isNotEmpty(parameter3)) {
            jSONObject = JSONObject.parseObject(parameter3);
        }
        Map<String, MultipartFile> fileMap = ((MultipartHttpServletRequest) httpServletRequest).getFileMap();
        DataSource dataSource = (DataSource) SpringContextUtils.getApplicationContext().getBean(DataSource.class);
        String m490a = DbTableUtil.m490a(dataSource);
        Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
        while (it.hasNext()) {
            MultipartFile multipartFile = it.next().getValue();
            ImportParams importParams = new ImportParams();
            importParams.setImageList(m238e);
            importParams.setDataHanlder(new CgFormExcelHandler(list, this.upLoadPath, this.uploadType));
            List<Map<String, Object>> importExcel = ExcelImportUtil.importExcel(multipartFile.getInputStream(), Map.class, importParams);
            if (importExcel == null) {
                str2 = "识别模版数据错误";
                logger.error(str2);
            } else {
                if (CgformConstant.f333a.equals(onlCgformHead.getTableType()) && onlCgformHead.getRelationType().intValue() == 1 && importExcel.size() > 1) {
                    return Result.error("一对一的表只能导入一条数据!");
                }
                Object obj = "";
                ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                for (Map<String, Object> map : importExcel) {
                    boolean z = false;
                    Set<String> keySet = map.keySet();
                    HashMap<String, Object> hashMap = new HashMap<>(5);
                    for (String str4 : keySet) {
                        if (!str4.contains("$subTable$")) {
                            if (str4.contains("$mainTable$") && oConvertUtils.isNotEmpty(map.get(str4).toString())) {
                                z = true;
                                obj = m100a(onlCgformHead, dataSource, m490a);
                            }
                            hashMap.put(str4.replace("$mainTable$", ""), map.get(str4));
                        }
                    }
                    if ("Y".equals(onlCgformHead.getIsTree())) {
                        if (oConvertUtils.isEmpty(hashMap.get(onlCgformHead.getTreeParentIdField()))) {
                            hashMap.put(onlCgformHead.getTreeParentIdField(), "0");
                        }
                        if (oConvertUtils.isEmpty(hashMap.get(onlCgformHead.getTreeIdField()))) {
                            hashMap.put(onlCgformHead.getTreeIdField(), "0");
                        }
                    }
                    if (z) {
                        hashMap.put("id", obj);
                        arrayList.add(hashMap);
                        obj = hashMap.get("id");
                    }
                    if (jSONObject != null) {
                        for (String str5 : jSONObject.keySet()) {
                            System.out.println(str5 + "=" + jSONObject.getString(str5));
                            hashMap.put(str5, jSONObject.getString(str5));
                        }
                    }
                    map.put("$mainTable$id", obj);
                }
                if (arrayList.isEmpty()) {
                    result.setSuccess(false);
                    result.setMessage("导入失败，匹配的数据条数为零!");
                    return result;
                }
                if ("1".equals(parameter)) {
                    Map<String, String> saveOnlineImportDataWithValidate = this.onlCgformSqlService.saveOnlineImportDataWithValidate(onlCgformHead, list, arrayList);
                    String str6 = saveOnlineImportDataWithValidate.get(OnlineImportValidator.ERROR);
                    str2 = saveOnlineImportDataWithValidate.get(OnlineImportValidator.TIP);
                    if (str6 != null && !str6.isEmpty()) {
                        stringBuffer.append(onlCgformHead.getTableTxt()).append("导入校验,").append(str2).append(",详情如下:\r\n").append(str6);
                    }
                } else {
                    this.onlCgformSqlService.saveBatchOnlineTable(onlCgformHead, list, arrayList);
                }
                if (oConvertUtils.isEmpty(parameter2) && onlCgformHead.getTableType() == 2 && oConvertUtils.isNotEmpty(onlCgformHead.getSubTableStr())) {
                    for (String str7 : onlCgformHead.getSubTableStr().split(CgformUtil.COMMA_SEPARATOR)) {
                        OnlCgformHead onlCgformHead3 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str7));
                        if (onlCgformHead3 != null) {
                            LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
                            lambdaQueryWrapper2.eq(OnlCgformField::getCgformHeadId, onlCgformHead3.getId());
                            List<OnlCgformField> list2 = this.onlCgformFieldService.list(lambdaQueryWrapper2);
                            ArrayList<Map<String, Object>> arrayList2 = new ArrayList<>();
                            String tableTxt = onlCgformHead3.getTableTxt();
                            for (Map map2 : importExcel) {
                                boolean z2 = false;
                                HashMap<String, Object> hashMap2 = new HashMap<>();
                                for (OnlCgformField onlCgformField : list2) {
                                    String mainTable = onlCgformField.getMainTable();
                                    String mainField = onlCgformField.getMainField();
                                    boolean z3 = onlCgformHead.getTableName().equals(mainTable) && oConvertUtils.isNotEmpty(mainField);
                                    String str8 = tableTxt + "_" + onlCgformField.getDbFieldTxt();
                                    if (z3) {
                                        hashMap2.put(onlCgformField.getDbFieldName(), map2.get("$mainTable$" + mainField));
                                    }
                                    Object obj2 = map2.get("$subTable$" + str8);
                                    if (null != obj2 && oConvertUtils.isNotEmpty(obj2.toString())) {
                                        z2 = true;
                                        hashMap2.put(onlCgformField.getDbFieldName(), obj2);
                                    }
                                }
                                if (z2) {
                                    hashMap2.put("id", m100a(onlCgformHead3, dataSource, m490a));
                                    arrayList2.add(hashMap2);
                                }
                            }
                            if (!arrayList2.isEmpty()) {
                                if ("1".equals(parameter)) {
                                    Map<String, String> saveOnlineImportDataWithValidate2 = this.onlCgformSqlService.saveOnlineImportDataWithValidate(onlCgformHead3, list2, arrayList2);
                                    String str9 = saveOnlineImportDataWithValidate2.get(OnlineImportValidator.ERROR);
                                    String str10 = saveOnlineImportDataWithValidate2.get(OnlineImportValidator.TIP);
                                    if (str9 != null && !str9.isEmpty()) {
                                        stringBuffer.append(onlCgformHead3.getTableTxt()).append("导入校验,").append(str10).append(",详情如下:\r\n").append(str9);
                                    }
                                } else {
                                    this.onlCgformSqlService.saveBatchOnlineTable(onlCgformHead3, list2, arrayList2);
                                }
                            }
                        }
                    }
                }
            }
        }
        result.setSuccess(true);
        if ("1".equals(parameter) && !stringBuffer.isEmpty()) {
            result.setResult(CgformUtil.m246a(this.upLoadPath, onlCgformHead.getTableTxt(), stringBuffer));
            result.setMessage(str2);
            result.setCode(201);
        } else {
            result.setMessage("导入成功!");
        }
        return result;
    }

    @PostMapping({"/doButton"})
    @Operation(summary = "执行按钮")
    /* renamed from: a */
    public Result<?> createDoButton(@RequestBody JSONObject jSONObject) {
        String string = jSONObject.getString("formId");
        String string2 = jSONObject.getString("dataId");
        String string3 = jSONObject.getString("buttonCode");
        jSONObject.getJSONObject("uiFormData");
        try {
            this.onlCgformHeadService.executeCustomerButton(string3, string, string2);
            return Result.ok("执行成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.error("执行失败," + e.getMessage());
        }
    }

    /* renamed from: a */
    public Object m100a(OnlCgformHead onlCgformHead, DataSource dataSource, String str) throws SQLException, DBException {
        Object obj = null;
        String idType = onlCgformHead.getIdType();
        String idSequence = onlCgformHead.getIdSequence();
        if (oConvertUtils.isNotEmpty(idType) && "UUID".equalsIgnoreCase(idType)) {
            obj = CgformUtil.nextId();
        } else if (oConvertUtils.isNotEmpty(idType) && "NATIVE".equalsIgnoreCase(idType)) {
            if (oConvertUtils.isNotEmpty(str) && "oracle".equalsIgnoreCase(str)) {
                try {
                    obj = new OracleSequenceMaxValueIncrementer(dataSource, "HIBERNATE_SEQUENCE").nextLongValue();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            } else if (oConvertUtils.isNotEmpty(str) && "postgres".equalsIgnoreCase(str)) {
                try {
                    obj = new PostgresSequenceMaxValueIncrementer(dataSource, "HIBERNATE_SEQUENCE").nextLongValue();
                } catch (Exception e2) {
                    logger.error(e2.getMessage(), e2);
                }
            } else {
                obj = null;
            }
        } else if (oConvertUtils.isNotEmpty(idType) && "SEQUENCE".equalsIgnoreCase(idType)) {
            if (oConvertUtils.isNotEmpty(str) && "oracle".equalsIgnoreCase(str)) {
                try {
                    obj = new OracleSequenceMaxValueIncrementer(dataSource, idSequence).nextLongValue();
                } catch (Exception e3) {
                    logger.error(e3.getMessage(), e3);
                }
            } else if (oConvertUtils.isNotEmpty(str) && "postgres".equalsIgnoreCase(str)) {
                try {
                    obj = new PostgresSequenceMaxValueIncrementer(dataSource, idSequence).nextLongValue();
                } catch (Exception e4) {
                    logger.error(e4.getMessage(), e4);
                }
            } else {
                obj = null;
            }
        } else {
            obj = CgformUtil.nextId();
        }
        return obj;
    }

    /* renamed from: a */
    private void m101a(Map<String, Object> map, List<OnlCgformField> list) {
        List<DictModel> queryTableDictItemsByCode;
        for (OnlCgformField onlCgformField : list) {
            String dictTable = onlCgformField.getDictTable();
            String dictField = onlCgformField.getDictField();
            String dictText = onlCgformField.getDictText();
            if (!oConvertUtils.isEmpty(dictTable) || !oConvertUtils.isEmpty(dictField)) {
                if (!CgformUtil.f218M.equals(onlCgformField.getFieldShowType())) {
                    String valueOf = String.valueOf(map.get(onlCgformField.getDbFieldName()));
                    if (oConvertUtils.isEmpty(dictTable)) {
                        queryTableDictItemsByCode = this.sysBaseAPI.queryDictItemsByCode(dictField);
                    } else {
                        queryTableDictItemsByCode = this.sysBaseAPI.queryTableDictItemsByCode(dictTable, dictText, dictField);
                    }
                    for (DictModel dictModel : queryTableDictItemsByCode) {
                        if (valueOf.equals(dictModel.getText())) {
                            map.put(onlCgformField.getDbFieldName(), dictModel.getValue());
                        }
                    }
                }
            }
        }
    }

    @GetMapping({"/checkOnlyTable"})
    @Operation(summary = "校验表是否存在")
    /* renamed from: j */
    public Result<?> checkOnlyTable(@RequestParam("tbname") String str, @RequestParam("id") String str2) {
        if (oConvertUtils.isEmpty(str2)) {
            if (DbTableUtil.isTableExistsInDatabase(str).booleanValue()) {
                return Result.ok(-1);
            }
            if (oConvertUtils.isNotEmpty((OnlCgformHead) this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str)))) {
                return Result.ok(-1);
            }
        } else if (!str.equals(((OnlCgformHead) this.onlCgformHeadService.getById(str2)).getTableName()) && DbTableUtil.isTableExistsInDatabase(str).booleanValue()) {
            return Result.ok(-1);
        }
        return Result.ok(1);
    }

    @PostMapping({"/codeGenerate"})
    @Operation(summary = "代码生成")
    /* renamed from: b */
    public Result<?> codeGenerate(@RequestBody JSONObject jSONObject) {
        List<String> generateOneToMany;
        OnlGenerateModel onlGenerateModel = (OnlGenerateModel) JSONObject.parseObject(jSONObject.toJSONString(), OnlGenerateModel.class);
        if ((this.jeecgBaseConfig.getFirewall() != null ? this.jeecgBaseConfig.getFirewall().getDataSourceSafe() : false) && !DbReadTableUtil.getProjectPath().equals(onlGenerateModel.getProjectPath())) {
            onlGenerateModel.setProjectPath(DbReadTableUtil.getProjectPath());
            logger.warn("数据源安全模式下，自定义代码生成路径无效，使用全局配置的路径 ::{}", DbReadTableUtil.getProjectPath());
        }
        try {
            if ("1".equals(onlGenerateModel.getJformType())) {
                generateOneToMany = this.onlCgformHeadService.generateCode(onlGenerateModel);
            } else {
                generateOneToMany = this.onlCgformHeadService.generateOneToMany(onlGenerateModel);
            }
            String str = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getUsername() + onlGenerateModel.getTableName() + oConvertUtils.randomGen(16);
            Result<?> ok = Result.ok(generateOneToMany);
            this.redisUtil.set(str, onlGenerateModel.getProjectPath().replaceAll("\\\\", "/"), 1800L);
            ok.setMessage(str);
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    @GetMapping({"/codeView"})
    @Operation(summary = "代码预览")
    /* renamed from: a */
    public void codeView(@RequestParam(name = "path") String path, @RequestParam(name = "pathKey") String pathKey, HttpServletResponse httpServletResponse) {
        String str3 = "";
        str3 = URLDecoder.decode(path, StandardCharsets.UTF_8);
        if (!str3.contains("src/main/java") && !str3.contains("src%5Cmain%5Cjava") && !str3.contains("src\\main\\java")) {
            logger.error(" path 不合法！！！");
            return;
        }
        Object obj = this.redisUtil.get(pathKey);
        if (obj == null) {
            logger.error("路径失效，请重新操作!");
            JwtUtil.responseError(httpServletResponse, 500, "路径失效，请重新操作!");
            return;
        }
        if (!str3.replaceAll("\\\\", "/").contains(obj.toString())) {
            logger.error("非法的请求路径，请重新操作!");
            JwtUtil.responseError(httpServletResponse, 500, "非法的请求路径，请重新操作!");
            return;
        }
        String substring = str3.substring(str3.lastIndexOf("/") + 1);
        File file = new File(str3);
        if (file.exists()) {
            httpServletResponse.setContentType("application/force-download");
            httpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + substring);
            byte[] bArr = new byte[1024];
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            try {
                try {
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    ServletOutputStream outputStream = httpServletResponse.getOutputStream();
                    for (int read = bufferedInputStream.read(bArr); read != -1; read = bufferedInputStream.read(bArr)) {
                        outputStream.write(bArr, 0, read);
                    }
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                }
            } catch (Throwable th) {
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }

    @PostMapping({"/downGenerateCode"})
    @Operation(summary = "下载生成代码")
    /* renamed from: a */
    public void downGenerateCode(@RequestBody JSONObject jSONObject, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String string = jSONObject.getString("fileList");
        String string2 = jSONObject.getString("pathKey");
        string = URLDecoder.decode(string, StandardCharsets.UTF_8);
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(string.split(CgformUtil.COMMA_SEPARATOR)));


        List<String> list = arrayList.stream().filter(str2 -> !str2.contains("src/main/java") && !str2.contains("src%5Cmain%5Cjava") && !str2.contains("src\\main\\java")).toList();

        if (!list.isEmpty()) {
            logger.error(" fileList 不合法！！！{}", arrayList);
            return;
        }

        Object obj = this.redisUtil.get(string2);
        if (obj == null) {
            logger.error("路径失效，请重新操作!");
            JwtUtil.responseError(httpServletResponse, 500, "路径失效，请重新操作!");
            return;
        }
        String obj2 = obj.toString();
        for (String s : arrayList) {
            if (!s.replaceAll("\\\\", "/").contains(obj2)) {
                logger.error("非法的请求路径，请重新操作!");
                JwtUtil.responseError(httpServletResponse, 500, "非法的请求路径，请重新操作!");
                return;
            }
        }
        String str3 = "生成代码_" + System.currentTimeMillis() + ".zip";
        str3 = URLEncoder.encode(str3, StandardCharsets.UTF_8);
        final String str4 = "/opt/temp/codegenerate/" + str3;
        File m277a = GenerateCodeFileToZip.m277a(arrayList, str4);

        if (m277a != null && m277a.exists()) {
            httpServletResponse.setContentType("application/force-download");
            httpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + str3);
            byte[] bArr = new byte[1024];
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            try {
                try {
                    fileInputStream = new FileInputStream(m277a);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    ServletOutputStream outputStream = httpServletResponse.getOutputStream();
                    for (int read = bufferedInputStream.read(bArr); read != -1; read = bufferedInputStream.read(bArr)) {
                        outputStream.write(bArr, 0, read);
                    }
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    new Thread() { // from class: org.jeecg.modules.online.cgform.c.a.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            try {
                                Thread.sleep(10000L);
                                FileUtil.del(str4);
                            } catch (InterruptedException e5) {
                                e5.printStackTrace();
                            }
                        }
                    }.start();
                } catch (Throwable th) {
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    new Thread() { // from class: org.jeecg.modules.online.cgform.c.a.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            try {
                                Thread.sleep(10000L);
                                FileUtil.del(str4);
                            } catch (InterruptedException e52) {
                                e52.printStackTrace();
                            }
                        }
                    }.start();
                    throw th;
                }
            } catch (Exception e7) {
                e7.printStackTrace();
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                }
                new Thread() { // from class: org.jeecg.modules.online.cgform.c.a.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            Thread.sleep(10000L);
                            FileUtil.del(str4);
                        } catch (InterruptedException e52) {
                            e52.printStackTrace();
                        }
                    }
                }.start();
            }
        }
    }

    @GetMapping({"/getTreeData/{code}"})
    @PermissionData
    @Operation(summary = "树表数据加载")
    /* renamed from: e */
    public Result<Map<String, Object>> getTreeDatatByCode(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        Result<Map<String, Object>> result = new Result<>();
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(code);
        if (onlCgformHead == null) {
            result.error500("实体不存在");
            return result;
        }
        try {
            String tableName = onlCgformHead.getTableName();
            String treeIdField = onlCgformHead.getTreeIdField();
            String treeParentIdField = onlCgformHead.getTreeParentIdField();
            ArrayList<String> newArrayList = Lists.newArrayList(treeIdField, treeParentIdField);
            Map<String, Object> m190a = CgformUtil.m190a(httpServletRequest);
            if (m190a.get(treeIdField) != null) {
                m190a.get(treeIdField).toString();
            }
            if (m190a.get("hasQuery") != null && "false".equals(m190a.get("hasQuery")) && m190a.get(treeParentIdField) == null) {
                m190a.put(treeParentIdField, "0");
            } else {
                m190a.put("pageSize", -521);
                m190a.put(treeParentIdField, m190a.get(treeParentIdField));
            }
            m190a.put(treeIdField, null);
            Map<String, Object> queryAutoTreeNoPage = this.onlCgformFieldService.queryAutoTreeNoPage(tableName, code, m190a, newArrayList, treeParentIdField);
            m107a(onlCgformHead, queryAutoTreeNoPage);
            result.setResult(queryAutoTreeNoPage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.error500("数据库查询失败" + e.getMessage());
        }
        result.setOnlTable(onlCgformHead.getTableName());
        return result;
    }

    /* renamed from: a */
    private void m107a(OnlCgformHead onlCgformHead, Map<String, Object> map) throws BusinessException {
        this.onlCgformHeadService.executeEnhanceList(onlCgformHead, "query", (List<Map<String, Object>>) map.get("records"));
    }

    @PostMapping({"/crazyForm/{name}"})
    @Operation(summary = "保存表单数据")
    /* renamed from: b */
    public Result<String> createCrazyForm(@PathVariable("name") String str, @RequestBody JSONObject jSONObject) {
        Result<String> result = new Result<>();
        try {
            String m240a = CgformUtil.nextId();
            jSONObject.put("id", m240a);
            this.onlCgformHeadService.addCrazyFormData(str, jSONObject);
            result.setResult(m240a);
            result.setMessage("保存成功");
            return result;
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formAddForDesigner()发生异常：" + e.getMessage(), e);
            return Result.error("保存失败");
        }
    }

    @PutMapping({"/crazyForm/{name}"})
    @Operation(summary = "更新表单数据crazyForm")
    /* renamed from: c */
    public Result<?> updateCrazyForm(@PathVariable("name") String str, @RequestBody JSONObject jSONObject) {
        try {
            jSONObject.remove("create_by");
            jSONObject.remove("create_time");
            jSONObject.remove("update_by");
            jSONObject.remove("update_time");
            this.onlCgformHeadService.editCrazyFormData(str, jSONObject);
            return Result.ok("保存成功!");
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formEditForDesigner()发生异常：" + e.getMessage(), e);
            return Result.error("保存失败");
        }
    }

    @AutoLog(operateType = 1, value = "online列表加载", module = ModuleType.ONLINE)
    @GetMapping({"/getErpColumns/{code}"})
    @Operation(summary = "获取表单字段getErpColumns")
    /* renamed from: c */
    public Result<Map<String, Object>> getErpColumns(@PathVariable("code") String str) {
        Result<Map<String, Object>> result = new Result<>();
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(str);
        if (onlCgformHead == null) {
            result.error500("实体不存在");
            return result;
        }
        HashMap<String, Object> hashMap = new HashMap<>(5);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        hashMap.put(CgReportConstant.MAIN, this.onlineService.queryOnlineConfig(onlCgformHead, loginUser.getUsername()));
        if (CgformUtil.ERP.equals(onlCgformHead.getThemeTemplate()) && onlCgformHead.getTableType() == 2) {
            String subTableStr = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(subTableStr)) {
                ArrayList<OnlComplexModel> arrayList = new ArrayList<>();
                for (String str2 : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    OnlCgformHead onlCgformHead2 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str2));
                    if (onlCgformHead2 != null) {
                        arrayList.add(this.onlineService.queryOnlineConfig(onlCgformHead2, loginUser.getUsername()));
                    }
                }
                if (!arrayList.isEmpty()) {
                    hashMap.put("subList", arrayList);
                }
            }
        }
        result.setOnlTable(onlCgformHead.getTableName());
        result.setResult(hashMap);
        result.setSuccess(true);
        return result;
    }

    @AutoLog(operateType = 1, value = "online表单加载", module = ModuleType.ONLINE)
    @GetMapping({"/getErpFormItem/{code}"})
    @Operation(summary = "获取表单getErpFormItem")
    /* renamed from: f */
    public Result<JSONObject> getErpFormItem(@PathVariable("code") String str, HttpServletRequest httpServletRequest) {
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(str);
        if (onlCgformHead == null) {
            return Result.error("表不存在");
        }
        Result<JSONObject> result = new Result<>();
        result.setResult(CgformUtil.m247b(this.onlineService.queryOnlineFormObj(onlCgformHead, ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getUsername())));
        result.setOnlTable(onlCgformHead.getTableName());
        return result;
    }

    @GetMapping({"/querySelectOptions"})
    @Operation(summary = "级联下拉数据加载")
    /* renamed from: a */
    public Result<List<TreeModel>> querySelectOptions(@ModelAttribute LinkDown linkDown) {
        Result<List<TreeModel>> result = new Result<>();
        try {
            result.setResult(this.onlCgformFieldService.queryDataListByLinkDown(linkDown));
            result.setSuccess(true);
        } catch (Exception e) {
            logger.warn("online级联下拉数据加载失败：{}", e.getMessage());
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @GetMapping({"/data/{tableName}/queryById"})
    @Operation(summary = "根据ID查询数据")
    /* renamed from: a */
    public JSONObject queryDataById(@PathVariable("tableName") String tableName, @RequestParam(name = "mock", required = false) Boolean bool, HttpServletRequest httpServletRequest) {
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, tableName);
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne(lambdaQueryWrapper);
        if (onlCgformHead == null) {
            throw new JeecgBootException("Online表单 " + tableName + " 不存在");
        }
        try {
            Map<String, Object> m190a = CgformUtil.m190a(httpServletRequest);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("id");
            Map<String, Object> queryAutolistPage = this.onlCgformFieldService.queryAutolistPage(onlCgformHead, m190a, arrayList);
            m107a(onlCgformHead, queryAutolistPage);
            List<Object> records = CgformUtil.converterRecordsToList(queryAutolistPage.get("records"), Object.class);
            if (Boolean.TRUE.equals(bool) && (records == null || records.isEmpty())) {
                Map<String, Object> generateMockData = this.onlCgformFieldService.generateMockData(onlCgformHead.getTableName());
                ArrayList<Map<String, Object>> arrayList2 = new ArrayList<>();
                arrayList2.add(generateMockData);
                queryAutolistPage.put("records", arrayList2);
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("data", queryAutolistPage.get("records"));
            return jSONObject;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JeecgBootException("数据库查询失败，" + e.getMessage());
        }
    }
}
