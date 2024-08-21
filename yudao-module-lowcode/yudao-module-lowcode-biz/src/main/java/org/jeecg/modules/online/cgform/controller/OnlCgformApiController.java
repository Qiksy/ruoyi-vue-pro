package org.jeecg.modules.online.cgform.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.redis.util.RedisUtil;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.common.config.LowCodeConfig;
import org.jeecg.common.constant.ModuleType;
import org.jeecg.common.service.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.online.BrowserUtils;
import org.jeecg.common.util.online.SqlInjectionUtil;
import org.jeecg.modules.codegenerate.DbReadTableUtil;
import org.jeecg.modules.online.annotation.AutoLog;
import org.jeecg.modules.online.annotation.OnlineAuth;
import org.jeecg.modules.online.annotation.PermissionData;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.PostgresSequenceMaxValueIncrementer;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.thymeleaf.spring6.context.SpringContextUtils;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

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

    @Resource
    private DictDataApi dictDataApi;

    @Autowired
    @Lazy
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private IOnlineService onlineService;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Value("${jeecg.uploadType}")
    private String uploadType;

    @Resource
    private RedisUtil redisUtil;
//
    @Resource
    private LowCodeConfig lowCodeConfig;

    /* renamed from: a */
    public OnlCgformApiController() {
    }

    @PostMapping({"/addAll"})
    @Operation(summary = "新增online在线表单")
    /* renamed from: a */
    public CommonResult<?> addAll(@RequestBody OnlCgformModel onlCgformModel) {
        try {
            String tableName = onlCgformModel.getHead().getTableName();
            if (DbTableUtil.isTableExistsInDatabase(tableName)) {
                return error(500,"数据库表[" + tableName + "]已存在,请从数据库导入表单");
            }
            if (onlCgformModel.getHead().getTableType() == 3) {
                if (onlCgformModel.getHead().getRelationType()==null) {
                    return error(500,"附表必须选择映射关系！");
                }
                if (onlCgformModel.getHead().getTabOrderNum()==null) {
                    return error(500,"附表必须填写排序序号！");
                }
            }
            return this.onlCgformHeadService.addAll(onlCgformModel);
        } catch (Exception e) {
            logger.error("OnlCgformApiController.addAll()发生异常：" + e.getMessage(), e);
            return error(500,"操作失败");
        }
    }

    /**
     * 编辑索引的时候，会调用这个接口
     *
     * @param onlCgformModel 表单模型
     * @return CommonResult<?>
     */
    @PutMapping({"/editAll"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    @Operation(summary = "编辑online在线表单")
    /* renamed from: b */
    public CommonResult<?> editAll(@RequestBody OnlCgformModel onlCgformModel) {
        try {
            if (onlCgformModel.getHead().getTableType() == 3) {
                if (onlCgformModel.getHead().getRelationType()==null) {
                    return error(500,"附表必须选择映射关系！");
                }
                if (onlCgformModel.getHead().getTabOrderNum()==null) {
                    return error(500,"附表必须填写排序序号！");
                }
            }
            return this.onlCgformHeadService.editAll(onlCgformModel);
        } catch (Exception e) {
            logger.error("OnlCgformApiController.editAll()发生异常：" + e.getMessage(), e);
            return error("操作失败");
        }
    }

    @OnlineAuth("getColumns")
    @AutoLog(operateType = 1, value = "online列表加载", module = ModuleType.ONLINE)
    @GetMapping({"/getColumns/{code}"})
    @Operation(summary = "online表单加载所有的列")
    /* renamed from: a */
    public CommonResult<OnlComplexModel> getColumns(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        CommonResult<OnlComplexModel> result = new CommonResult<>();
        try {
            OnlCgformHead table = this.onlCgformHeadService.getTable(code);
            String parameter = httpServletRequest.getParameter(OnlineConst.LINK_TABLE_SELECT_FIELDS);
            if (StringUtils.isNotEmpty(parameter)) {
                table.setSelectFieldString(parameter);
            }
            OnlComplexModel queryOnlineConfig = this.onlineService.queryOnlineConfig(table, SecurityFrameworkUtils.getUserName());
            queryOnlineConfig.setIsDesForm(table.getIsDesForm());
            queryOnlineConfig.setDesFormCode(table.getDesFormCode());
//            result.setResult(queryOnlineConfig);
//            result.setOnlTable(table.getTableName());

            return success(queryOnlineConfig);
        } catch (DBException e) {

            return error("实体不存在");
        }
    }

    @OnlineAuth("getData")
    @PermissionData
    @GetMapping({"/getData/{code}"})
    @Operation(summary = "online表单加载数据")
    /* renamed from: b */
    public CommonResult<Map<String, Object>> getData(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        Map<String, Object> queryAutolistPage;
        CommonResult<Map<String, Object>> result = new CommonResult<>();
        try {
            OnlCgformHead table = this.onlCgformHeadService.getTable(code);
            if (StringUtils.isEmpty(table.getPhysicId()) && "N".equals(table.getIsDbSynch())) {
                return error("NO_DB_SYNC");
            }
            String parameter = httpServletRequest.getParameter(OnlineConst.LINK_TABLE_SELECT_FIELDS);
            if (StringUtils.isNotEmpty(parameter)) {
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
                return success(queryAutolistPage);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return error("数据库查询失败！");
            }
//            result.setOnlTable(table.getTableName());
        } catch (DBException e2) {
            return error("实体不存在");
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
    public CommonResult<?> getFormItem(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        try {
            OnlCgformHead table = this.onlCgformHeadService.getTable(code);
            String parameter = httpServletRequest.getParameter("selectFields");
            if (StringUtils.isNotEmpty(parameter)) {
                //todo 这里分割参数之后，没有使用
                List<String> parameterList = Arrays.asList(parameter.split(CgformUtil.COMMA_SEPARATOR));
            }
            JSONObject jsonObject = CgformUtil.m247b(this.onlineService.queryOnlineFormItem(table,SecurityFrameworkUtils.getUserName()));
            return success(jsonObject);
//            result.setOnlTable(table.getTableName());
        } catch (DBException e) {
            return error("表不存在");
        }
    }

    @AutoLog(operateType = 1, value = "online根据表名加载表单", module = ModuleType.ONLINE)
    @GetMapping({"/getFormItemBytbname/{table}"})
    /* renamed from: a */
    @Operation(summary = "根据表名加载表单")
    public CommonResult<?> getFormItemBytbname(@PathVariable("table") String str, @RequestParam(name = "taskId", required = false) String taskId) {
        CommonResult<JSONObject> result = new CommonResult<>();
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<OnlCgformHead>();
        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, str);
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne(lambdaQueryWrapper);
        if (onlCgformHead == null) {
            return error("表不存在");
        }
//        result.setOnlTable(str);
        return success(CgformUtil.m247b(this.onlineService.queryFlowOnlineFormItem(onlCgformHead, SecurityFrameworkUtils.getUserName(), taskId)));
    }

    @OnlineAuth("getEnhanceJs")
    @GetMapping({"/getEnhanceJs/{code}"})
    /* renamed from: d */
    @Operation(summary = "获取增强js")
    public CommonResult<?> getEnhanceJs(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        return success(this.onlineService.queryEnahcneJsString(code, CgformUtil.FORM));
    }

    @AutoLog(operateType = 1, value = "online表单数据查询")
    @GetMapping({"/form/{code}/{id}"})
    @Operation(summary = "online表单数据查询")
    /* renamed from: b */
    public CommonResult<?> getFormInfo(@PathVariable("code") String code, @PathVariable("id") String id) {
        try {
            SqlInjectionUtil.filterContent(id, CgformUtil.SINGLE_QUOTE);
            return success(CgformUtil.m224a(this.onlCgformHeadService.queryManyFormData(code, id)));
        } catch (Exception e) {
            logger.error("Online表单查询异常：" + e.getMessage(), e);
            return error("查询失败，" + e.getMessage());
        }
    }

    @AutoLog(operateType = 1, value = "online表单数据查询")
    @GetMapping({"/detail/{code}/{id}"})
    @Operation(summary = "online表单数据查询")
    /* renamed from: c */
    public CommonResult<?> getFormDetail(@PathVariable("code") String code, @PathVariable("id") String id) {
        try {
            SqlInjectionUtil.filterContent(id, CgformUtil.SINGLE_QUOTE);
            Map<String, Object> queryManyFormData = this.onlCgformHeadService.queryManyFormData(code, id);
            ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
            arrayList.add(CgformUtil.m224a(queryManyFormData));
            this.onlCgformFieldService.handleLinkTableDictData(this.onlCgformHeadService.getTable(code).getId(), arrayList);
            return success((Map) arrayList.get(0));
        } catch (Exception e) {
            logger.error("Online表单查询异常：" + e.getMessage(), e);
            return error("查询失败，" + e.getMessage());
        }
    }

    @GetMapping({"/subform/{table}/{mainId}"})
    @Operation(summary = "online根据表名查询子表单数据")
    /* renamed from: d */
    public CommonResult<?> getSubformInfo(@PathVariable("table") String str, @PathVariable("mainId") String str2) {
        try {
            SqlInjectionUtil.filterContent(str2, CgformUtil.SINGLE_QUOTE);
            return success(CgformUtil.m224a(this.onlCgformHeadService.querySubFormData(str, str2)));
        } catch (Exception e) {
            logger.error("Online表单查询异常：" + e.getMessage(), e);
            return error("查询失败，" + e.getMessage());
        }
    }

    @GetMapping({"/subform/list/{table}/{mainId}"})
    @Operation(summary = "online根据表名查询子表单数据")
    /* renamed from: e */
    public CommonResult<?> getSubformDetail(@PathVariable("table") String table, @PathVariable("mainId") String mainId) {
        try {
            SqlInjectionUtil.filterContent(mainId, CgformUtil.SINGLE_QUOTE);
            return success(this.onlCgformHeadService.queryManySubFormData(table, mainId));
        } catch (Exception e) {
            logger.error("Online表单查询异常：" + e.getMessage(), e);
            return error("查询失败，" + e.getMessage());
        }
    }

    @AutoLog(operateType = 1, value = "online根据表名查询表单数据", module = ModuleType.ONLINE)
    @GetMapping({"/form/table_name/{tableName}/{dataId}"})
    @Operation(summary = "online根据表名查询表单数据")
    /* renamed from: f */
    public CommonResult<?> getFormDataByTableName(@PathVariable("tableName") String tableName, @PathVariable("dataId") String dataId) {
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
            CommonResult<?> result = getFormInfo(onlCgformHead.getId(), dataId);
//            result.setOnlTable(tableName);
            return result;
        } catch (Exception e) {
            logger.error("Online表单查询异常，" + e.getMessage(), e);
            return error("查询失败，" + e.getMessage());
        }
    }

    @OnlineAuth(CgformUtil.FORM)
    @PostMapping({"/form/{code}"})
    @AutoLog(operateType = 2, value = "online新增数据", module = ModuleType.ONLINE)
    @CacheEvict(value = {OnlineConst.CACHE_ONLINE_LINK_TABLE}, allEntries = true)
    @Operation(summary = "online表单新增数据新增数据")
    /* renamed from: a */
    public CommonResult<String> createDataByCode(@PathVariable("code") String code, @RequestBody JSONObject jSONObject, HttpServletRequest httpServletRequest) {
        CommonResult<String> result = new CommonResult<>();
        try {
            String id = CgformUtil.nextId();
            jSONObject.put("id", id);
            //TokenUtils.getTokenByRequest(httpServletRequest)
            String saveManyFormData = this.onlCgformHeadService.saveManyFormData(code, jSONObject, "这里需要获取token放进去的");
            return success(id);
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formAdd()发生异常：", e);
            return error("保存失败，" + CgformUtil.exceptionToMessage(e));
        }
    }

    @OnlineAuth(CgformUtil.FORM)
    @AutoLog(operateType = 3, value = "online修改数据", module = ModuleType.ONLINE)
    @PutMapping({"/form/{code}"})
    @CacheEvict(value = {OnlineConst.CACHE_ONLINE_LINK_TABLE}, allEntries = true)
    @Operation(summary = "online表单修改数据")
    /* renamed from: a */
    public CommonResult<?> m90a(@PathVariable("code") String str, @RequestBody JSONObject jSONObject) {
        try {
            String editManyFormData = this.onlCgformHeadService.editManyFormData(str, jSONObject);
//            ok.setOnlTable(editManyFormData);
            return success(true);
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formEdit()发生异常：" + e.getMessage(), e);
            return error("修改失败，" + CgformUtil.exceptionToMessage(e));
        }
    }

    @OnlineAuth(CgformUtil.FORM)
    @DeleteMapping({"/form/{code}/{id}"})
    @AutoLog(operateType = 4, value = "online删除数据", module = ModuleType.ONLINE)
    @Operation(summary = "online表单删除数据")
    /* renamed from: g */
    public CommonResult<?> m91g(@PathVariable("code") String str, @PathVariable("id") String str2) {
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(str);
        if (onlCgformHead == null) {
            return error("实体不存在");
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
//            CommonResult<?> ok = Result.ok("删除成功!");
//            ok.setOnlTable(onlCgformHead.getTableName());
            return success(true);
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formEdit()发生异常：" + e.getMessage(), e);
            return error("删除失败," + e.getMessage());
        }
    }

    @DeleteMapping({"/formByCode/{code}/{id}"})
    @AutoLog(operateType = 4, value = "online删除数据", module = ModuleType.ONLINE)
    @Operation(summary = "online表单删除数据")
    /* renamed from: h */
    public CommonResult<?> m92h(@PathVariable("code") String str, @PathVariable("id") String str2) {
        try {
            String deleteDataByCode = this.onlCgformHeadService.deleteDataByCode(str, str2);
//            CommonResult<?> OK = Result.OK("删除成功!", deleteDataByCode);
//            OK.setOnlTable(deleteDataByCode);
            return success("删除成功！"+deleteDataByCode);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @OnlineAuth("getQueryInfo")
    @GetMapping({"/getQueryInfo/{code}"})
    @Operation(summary = "获取查询信息")
    /* renamed from: a */
    public CommonResult<?> m93a(@PathVariable("code") String str) {
        try {
            return success(this.onlCgformFieldService.getAutoListQueryInfo(str));
        } catch (Exception e) {
            logger.error("OnlCgformApiController.getQueryInfo()发生异常：" + e.getMessage(), e);
            return error("查询失败");
        }
    }

    @GetMapping({"/getQueryInfoVue3/{code}"})
    @Operation(summary = "获取查询信息")
    /* renamed from: b */
    public CommonResult<?> getQueryInfoVue3(@PathVariable("code") String str) {
        try {
            return success(this.onlineService.getOnlineVue3QueryInfo(str));
        } catch (Exception e) {
            logger.error("OnlCgformApiController.getQueryInfoVue3()发生异常：" + e.getMessage(), e);
            return error("查询失败");
        }
    }

    @PostMapping({"/doDbSynch/{code}/{synMethod}"})
    @PreAuthorize("@ss.hasPermission('online:form:syncDb')")
    @Operation(summary = "同步数据库")
    /* renamed from: i */
    public CommonResult<?> m95i(@PathVariable("code") String code, @PathVariable("synMethod") String synMethod) {
        try {
            System.currentTimeMillis();
            this.onlCgformHeadService.doDbSynch(code, synMethod);
            return success("同步数据库成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("同步数据库失败，" + CgformUtil.exceptionToMessage(e));
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
        if (StringUtils.isNotEmpty(parameter)) {
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

//    @OnlineAuth("exportXlsOld")
//    @PermissionData
//    @GetMapping({"/exportXlsOld/{code}"})
//    @Operation(summary = "导出excel")
//    /* renamed from: b */
//    public void exportXlsOld(@PathVariable("code") String str, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(str);
//        if (onlCgformHead == null) {
//            return;
//        }
//        String tableTxt = onlCgformHead.getTableTxt();
//        String parameter = httpServletRequest.getParameter("paramsStr");
//        HashMap<String, Object> hashMap = new HashMap<>(5);
//        if (StringUtils.isNotEmpty(parameter)) {
//            TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {
//            };
//            hashMap = JSONObject.parseObject(parameter, typeReference);
//        }
//        hashMap.put("pageSize", -521);
//        Map<String, Object> pageList = CgformUtil.m181a(onlCgformHead) ? this.onlineJoinQueryService.pageList(onlCgformHead, hashMap, true) : this.onlCgformFieldService.queryAutolistPage(onlCgformHead, hashMap, null);
//        List<OnlCgformField> list = (List<OnlCgformField>) pageList.get("fieldList");
//        List<Map<String, Object>> list2 = (List<Map<String, Object>>) pageList.get("records");
//        List<Map<String, Object>> arrayList;
//        String obj = hashMap.get("selections") == null ? null : hashMap.get("selections").toString();
//        if (StringUtils.isNotEmpty(obj)) {
//            List<String> m255h = CgformUtil.m255h(obj);
//            arrayList = list2.stream().filter(map -> m255h.contains(map.get("id"))).collect(Collectors.toList());
//        } else {
//            if (list2 == null) {
//                list2 = new ArrayList<>();
//            }
//            arrayList = new ArrayList<>(list2);
//        }
//        ConvertUtil.m176a(1, arrayList, list);
//        try {
//            this.onlCgformHeadService.executeEnhanceExport(onlCgformHead, arrayList);
//        } catch (BusinessException e) {
//            logger.error("导出java增强处理出错{}", e.getMessage());
//        }
//        List<ExcelExportEntity> m257b = CgformUtil.m257b( list, "id", this.upLoadPath);
//        if (onlCgformHead.getTableType() == 2 && StringUtils.isEmpty(hashMap.get(CgformUtil.f251at))) {
//            String subTableStr = onlCgformHead.getSubTableStr();
//            if (StringUtils.isNotEmpty(subTableStr)) {
//                for (String str2 : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
//                    this.onlineJoinQueryService.addAllSubTableDate(str2, hashMap, arrayList, m257b, false);
//                }
//            }
//        }
//        ExportParams exportParams = new ExportParams((String) null, tableTxt);
//        exportParams.setType(ExcelType.XSSF);
//        Workbook exportExcel = ExcelExportUtil.exportExcel(exportParams, m257b, arrayList);
//        OutputStream outputStream = null;
//        try {
//            try {
//                httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//                String checkBrowse = BrowserUtils.checkBrowse(httpServletRequest);
//                String str3 = onlCgformHead.getTableTxt() + "-v" + onlCgformHead.getTableVersion();
//                if ("MSIE".equalsIgnoreCase(checkBrowse.substring(0, 4))) {
//                    httpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(str3, StandardCharsets.UTF_8) + ".xlsx");
//                } else {
//                    httpServletResponse.setHeader("content-disposition", "attachment;filename=" + new String(str3.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + ".xlsx");
//                }
//                outputStream = httpServletResponse.getOutputStream();
//                exportExcel.write(outputStream);
//                httpServletResponse.flushBuffer();
//                if (outputStream != null) {
//                    try {
//                        outputStream.close();
//                    } catch (IOException e2) {
//                        logger.error(e2.getMessage(), e2);
//                    }
//                }
//            } catch (Exception e3) {
//                logger.error("--通过流的方式获取文件异常--" + e3.getMessage(), e3);
//                if (outputStream != null) {
//                    try {
//                        outputStream.close();
//                    } catch (IOException e4) {
//                        logger.error(e4.getMessage(), e4);
//                    }
//                }
//            }
//        } catch (Throwable th) {
//            if (outputStream != null) {
//                try {
//                    outputStream.close();
//                } catch (IOException e5) {
//                    logger.error(e5.getMessage(), e5);
//                }
//            }
//            throw th;
//        }
//    }

//    @OnlineAuth("importXls")
//    @PostMapping({"/importXls/{code}"})
//    @Operation(summary = "导入excel")
//    /* renamed from: c */
//    public CommonResult<?> importXls(@PathVariable("code") String str, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//        OnlCgformHead onlCgformHead = null;
//        System.currentTimeMillis();
//        CommonResult<String> result = new CommonResult<>();
//        String str2 = "";
//        String parameter = httpServletRequest.getParameter(CgformUtil.f253av);
//        StringBuffer stringBuffer = new StringBuffer();
//        try {
//            onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(str);
//        } catch (Exception e) {
////            result.setSuccess(false);
////            result.setMessage(e.getMessage());
//            logger.error(e.getMessage(), e);
//            return error(e.getMessage());
//        }
//        if (onlCgformHead == null) {
//            return error("数据库不存在该表记录");
//        }
//        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str);
//        List<OnlCgformField> list = this.onlCgformFieldService.list(lambdaQueryWrapper);
//        String parameter2 = httpServletRequest.getParameter(CgformUtil.f252au);
//        List<String> m238e = CgformUtil.m238e(list);
//        if (StringUtils.isEmpty(parameter2) && onlCgformHead.getTableType().intValue() == 2 && StringUtils.isNotEmpty(onlCgformHead.getSubTableStr())) {
//            for (String str3 : onlCgformHead.getSubTableStr().split(CgformUtil.COMMA_SEPARATOR)) {
//                OnlCgformHead onlCgformHead2 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str3));
//                if (onlCgformHead2 != null) {
//                    List<String> m239c = CgformUtil.m239c(this.onlCgformFieldService.list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, onlCgformHead2.getId())), onlCgformHead2.getTableTxt());
//                    if (!m239c.isEmpty()) {
//                        m238e.addAll(m239c);
//                    }
//                }
//            }
//        }
//        JSONObject jSONObject = null;
//        String parameter3 = httpServletRequest.getParameter(CgformUtil.f255ax);
//        if (StringUtils.isNotEmpty(parameter3)) {
//            jSONObject = JSONObject.parseObject(parameter3);
//        }
//        Map<String, MultipartFile> fileMap = ((MultipartHttpServletRequest) httpServletRequest).getFileMap();
//        DataSource dataSource = (DataSource) SpringContextUtils.getApplicationContext().getBean(DataSource.class);
//        String m490a = DbTableUtil.m490a(dataSource);
//        Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
//        while (it.hasNext()) {
//            MultipartFile multipartFile = it.next().getValue();
//            ImportParams importParams = new ImportParams();
//            importParams.setImageList(m238e);
//            importParams.setDataHanlder(new CgFormExcelHandler(list, this.upLoadPath, this.uploadType));
//            List<Map<String, Object>> importExcel = ExcelImportUtil.importExcel(multipartFile.getInputStream(), Map.class, importParams);
//            if (importExcel == null) {
//                str2 = "识别模版数据错误";
//                logger.error(str2);
//            } else {
//                if (CgformConstant.f333a.equals(onlCgformHead.getTableType()) && onlCgformHead.getRelationType().intValue() == 1 && importExcel.size() > 1) {
//                    return error("一对一的表只能导入一条数据!");
//                }
//                Object obj = "";
//                ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
//                for (Map<String, Object> map : importExcel) {
//                    boolean z = false;
//                    Set<String> keySet = map.keySet();
//                    HashMap<String, Object> hashMap = new HashMap<>(5);
//                    for (String str4 : keySet) {
//                        if (!str4.contains("$subTable$")) {
//                            if (str4.contains("$mainTable$") && StringUtils.isNotEmpty(map.get(str4).toString())) {
//                                z = true;
//                                obj = m100a(onlCgformHead, dataSource, m490a);
//                            }
//                            hashMap.put(str4.replace("$mainTable$", ""), map.get(str4));
//                        }
//                    }
//                    if ("Y".equals(onlCgformHead.getIsTree())) {
//                        if (StringUtils.isEmpty(hashMap.get(onlCgformHead.getTreeParentIdField()))) {
//                            hashMap.put(onlCgformHead.getTreeParentIdField(), "0");
//                        }
//                        if (StringUtils.isEmpty(hashMap.get(onlCgformHead.getTreeIdField()))) {
//                            hashMap.put(onlCgformHead.getTreeIdField(), "0");
//                        }
//                    }
//                    if (z) {
//                        hashMap.put("id", obj);
//                        arrayList.add(hashMap);
//                        obj = hashMap.get("id");
//                    }
//                    if (jSONObject != null) {
//                        for (String str5 : jSONObject.keySet()) {
//                            System.out.println(str5 + "=" + jSONObject.getString(str5));
//                            hashMap.put(str5, jSONObject.getString(str5));
//                        }
//                    }
//                    map.put("$mainTable$id", obj);
//                }
//                if (arrayList.isEmpty()) {
//                    return error("导入失败，匹配的数据条数为零!");
//                }
//                if ("1".equals(parameter)) {
//                    Map<String, String> saveOnlineImportDataWithValidate = this.onlCgformSqlService.saveOnlineImportDataWithValidate(onlCgformHead, list, arrayList);
//                    String str6 = saveOnlineImportDataWithValidate.get(OnlineImportValidator.ERROR);
//                    str2 = saveOnlineImportDataWithValidate.get(OnlineImportValidator.TIP);
//                    if (str6 != null && !str6.isEmpty()) {
//                        stringBuffer.append(onlCgformHead.getTableTxt()).append("导入校验,").append(str2).append(",详情如下:\r\n").append(str6);
//                    }
//                } else {
//                    this.onlCgformSqlService.saveBatchOnlineTable(onlCgformHead, list, arrayList);
//                }
//                if (StringUtils.isEmpty(parameter2) && onlCgformHead.getTableType() == 2 && StringUtils.isNotEmpty(onlCgformHead.getSubTableStr())) {
//                    for (String str7 : onlCgformHead.getSubTableStr().split(CgformUtil.COMMA_SEPARATOR)) {
//                        OnlCgformHead onlCgformHead3 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str7));
//                        if (onlCgformHead3 != null) {
//                            LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
//                            lambdaQueryWrapper2.eq(OnlCgformField::getCgformHeadId, onlCgformHead3.getId());
//                            List<OnlCgformField> list2 = this.onlCgformFieldService.list(lambdaQueryWrapper2);
//                            ArrayList<Map<String, Object>> arrayList2 = new ArrayList<>();
//                            String tableTxt = onlCgformHead3.getTableTxt();
//                            for (Map map2 : importExcel) {
//                                boolean z2 = false;
//                                HashMap<String, Object> hashMap2 = new HashMap<>();
//                                for (OnlCgformField onlCgformField : list2) {
//                                    String mainTable = onlCgformField.getMainTable();
//                                    String mainField = onlCgformField.getMainField();
//                                    boolean z3 = onlCgformHead.getTableName().equals(mainTable) && StringUtils.isNotEmpty(mainField);
//                                    String str8 = tableTxt + "_" + onlCgformField.getDbFieldTxt();
//                                    if (z3) {
//                                        hashMap2.put(onlCgformField.getDbFieldName(), map2.get("$mainTable$" + mainField));
//                                    }
//                                    Object obj2 = map2.get("$subTable$" + str8);
//                                    if (null != obj2 && StringUtils.isNotEmpty(obj2.toString())) {
//                                        z2 = true;
//                                        hashMap2.put(onlCgformField.getDbFieldName(), obj2);
//                                    }
//                                }
//                                if (z2) {
//                                    hashMap2.put("id", m100a(onlCgformHead3, dataSource, m490a));
//                                    arrayList2.add(hashMap2);
//                                }
//                            }
//                            if (!arrayList2.isEmpty()) {
//                                if ("1".equals(parameter)) {
//                                    Map<String, String> saveOnlineImportDataWithValidate2 = this.onlCgformSqlService.saveOnlineImportDataWithValidate(onlCgformHead3, list2, arrayList2);
//                                    String str9 = saveOnlineImportDataWithValidate2.get(OnlineImportValidator.ERROR);
//                                    String str10 = saveOnlineImportDataWithValidate2.get(OnlineImportValidator.TIP);
//                                    if (str9 != null && !str9.isEmpty()) {
//                                        stringBuffer.append(onlCgformHead3.getTableTxt()).append("导入校验,").append(str10).append(",详情如下:\r\n").append(str9);
//                                    }
//                                } else {
//                                    this.onlCgformSqlService.saveBatchOnlineTable(onlCgformHead3, list2, arrayList2);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
////        result.setSuccess(true);
//        if ("1".equals(parameter) && !stringBuffer.isEmpty()) {
//            String logPath = CgformUtil.generateLogFile(this.upLoadPath, onlCgformHead.getTableTxt(), stringBuffer);
//            result.setCode(201);
////            导入失败，生成日志
//            String message = String.format("导入失败：{}，详情请查看日志文件：{}", str2, logPath);
//            return error(201,message);
//        } else {
//            return success("导入成功!");
////            result.setMessage("导入成功!");
//        }
//        return result;
//    }

    @PostMapping({"/doButton"})
    @Operation(summary = "执行按钮")
    /* renamed from: a */
    public CommonResult<?> createDoButton(@RequestBody JSONObject jSONObject) {
        String string = jSONObject.getString("formId");
        String string2 = jSONObject.getString("dataId");
        String string3 = jSONObject.getString("buttonCode");
        jSONObject.getJSONObject("uiFormData");
        try {
            this.onlCgformHeadService.executeCustomerButton(string3, string, string2);
            return success("执行成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("执行失败," + e.getMessage());
        }
    }

    /* renamed from: a */
    public Object m100a(OnlCgformHead onlCgformHead, DataSource dataSource, String str) throws SQLException, DBException {
        Object obj = null;
        String idType = onlCgformHead.getIdType();
        String idSequence = onlCgformHead.getIdSequence();
        if (StringUtils.isNotEmpty(idType) && "UUID".equalsIgnoreCase(idType)) {
            obj = CgformUtil.nextId();
        } else if (StringUtils.isNotEmpty(idType) && "NATIVE".equalsIgnoreCase(idType)) {
            if (StringUtils.isNotEmpty(str) && "oracle".equalsIgnoreCase(str)) {
                try {
                    obj = new OracleSequenceMaxValueIncrementer(dataSource, "HIBERNATE_SEQUENCE").nextLongValue();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            } else if (StringUtils.isNotEmpty(str) && "postgres".equalsIgnoreCase(str)) {
                try {
                    obj = new PostgresSequenceMaxValueIncrementer(dataSource, "HIBERNATE_SEQUENCE").nextLongValue();
                } catch (Exception e2) {
                    logger.error(e2.getMessage(), e2);
                }
            } else {
                obj = null;
            }
        } else if (StringUtils.isNotEmpty(idType) && "SEQUENCE".equalsIgnoreCase(idType)) {
            if (StringUtils.isNotEmpty(str) && "oracle".equalsIgnoreCase(str)) {
                try {
                    obj = new OracleSequenceMaxValueIncrementer(dataSource, idSequence).nextLongValue();
                } catch (Exception e3) {
                    logger.error(e3.getMessage(), e3);
                }
            } else if (StringUtils.isNotEmpty(str) && "postgres".equalsIgnoreCase(str)) {
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

    /**
     * 填充字典数据
     * 暂时弃用
     * @param map
     * @param list
     */
    private void fillDictData(Map<String, Object> map, List<OnlCgformField> list) {
        List<DictModel> queryTableDictItemsByCode = new ArrayList<>();
        for (OnlCgformField onlCgformField : list) {
            String dictTable = onlCgformField.getDictTable();
            String dictField = onlCgformField.getDictField();
            String dictText = onlCgformField.getDictText();
            if (!StringUtils.isEmpty(dictTable) || !StringUtils.isEmpty(dictField)) {
                if (!CgformUtil.f218M.equals(onlCgformField.getFieldShowType())) {
                    String valueOf = String.valueOf(map.get(onlCgformField.getDbFieldName()));
                    if (StringUtils.isEmpty(dictTable)) {
//                        queryTableDictItemsByCode = this.sysBaseAPI.queryDictItemsByCode(dictField);
                    } else {
//                        queryTableDictItemsByCode = this.sysBaseAPI.queryTableDictItemsByCode(dictTable, dictText, dictField);
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
    public CommonResult<?> checkOnlyTable(@RequestParam("tbname") String str, @RequestParam("id") String str2) {
        if (StringUtils.isEmpty(str2)) {
            if (DbTableUtil.isTableExistsInDatabase(str).booleanValue()) {
                return success(-1);
            }
            if (this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str))!=null) {
                return success(-1);
            }
        } else if (!str.equals(((OnlCgformHead) this.onlCgformHeadService.getById(str2)).getTableName()) && DbTableUtil.isTableExistsInDatabase(str).booleanValue()) {
            return success(-1);
        }
        return success(1);
    }

    @PostMapping({"/codeGenerate"})
    @Operation(summary = "代码生成")
    /* renamed from: b */
    public CommonResult<?> codeGenerate(@RequestBody JSONObject jSONObject) {
        List<String> generateOneToMany;
        OnlGenerateModel onlGenerateModel = (OnlGenerateModel) JSONObject.parseObject(jSONObject.toJSONString(), OnlGenerateModel.class);
        if ((this.lowCodeConfig.getFirewall() != null ? this.lowCodeConfig.getFirewall().getDataSourceSafe() : false)
                && !DbReadTableUtil.getProjectPath().equals(onlGenerateModel.getProjectPath())) {
            onlGenerateModel.setProjectPath(DbReadTableUtil.getProjectPath());
            logger.warn("数据源安全模式下，自定义代码生成路径无效，使用全局配置的路径 ::{}", DbReadTableUtil.getProjectPath());
        }
        //todo 数据源安全模式下，自定义代码生成路径无效，使用全局配置的路径
        try {
            if ("1".equals(onlGenerateModel.getJformType())) {
                generateOneToMany = this.onlCgformHeadService.generateCode(onlGenerateModel);
            } else {
                generateOneToMany = this.onlCgformHeadService.generateOneToMany(onlGenerateModel);
            }
            String str = (SecurityFrameworkUtils.getUserName() + onlGenerateModel.getTableName() + RandomUtil.randomString(16));
            this.redisUtil.set(str, onlGenerateModel.getProjectPath().replaceAll("\\\\", "/"), 1800L);
            return success(str);
        } catch (Exception e) {
            e.printStackTrace();
            throw exception(e.getMessage());
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
            throw exception("路径失效，请重新操作!");
        }
        if (!str3.replaceAll("\\\\", "/").contains(obj.toString())) {
            logger.error("非法的请求路径，请重新操作!");
            throw exception("非法的请求路径，请重新操作!");
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
        String pathKey = jSONObject.getString("pathKey");
        string = URLDecoder.decode(string, StandardCharsets.UTF_8);
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(string.split(CgformUtil.COMMA_SEPARATOR)));


        List<String> list = arrayList.stream().filter(str2 -> !str2.contains("src/main/java") && !str2.contains("src%5Cmain%5Cjava") && !str2.contains("src\\main\\java")).toList();

        if (!list.isEmpty()) {
            logger.error(" fileList 不合法！！！{}", arrayList);
            return;
        }

        Object obj = this.redisUtil.get(pathKey);
        if (obj == null) {
            logger.error("路径失效，请重新操作!");
            throw exception("路径失效，请重新操作!");
        }
        String obj2 = obj.toString();
        for (String s : arrayList) {
            if (!s.replaceAll("\\\\", "/").contains(obj2)) {
                logger.error("非法的请求路径，请重新操作!");
                throw exception("非法的请求路径，请重新操作!");
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
    public CommonResult<Map<String, Object>> getTreeDatatByCode(@PathVariable("code") String code, HttpServletRequest httpServletRequest) {
        CommonResult<Map<String, Object>> result = new CommonResult<>();
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(code);
        if (onlCgformHead == null) {

            return error("实体不存在");
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
            return success(queryAutoTreeNoPage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("数据库查询失败" + e.getMessage());
        }
    }

    /* renamed from: a */
    private void m107a(OnlCgformHead onlCgformHead, Map<String, Object> map) throws BusinessException {
        this.onlCgformHeadService.executeEnhanceList(onlCgformHead, "query", (List<Map<String, Object>>) map.get("records"));
    }

    @PostMapping({"/crazyForm/{name}"})
    @Operation(summary = "保存表单数据")
    /* renamed from: b */
    public CommonResult<String> createCrazyForm(@PathVariable("name") String str, @RequestBody JSONObject jSONObject) {
        CommonResult<String> result = new CommonResult<>();
        try {
            String nextId = CgformUtil.nextId();
            jSONObject.put("id", nextId);
            this.onlCgformHeadService.addCrazyFormData(str, jSONObject);
//            result.setMessage("保存成功");
            return success(nextId);
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formAddForDesigner()发生异常：" + e.getMessage(), e);
            return error("保存失败");
        }
    }

    @PutMapping({"/crazyForm/{name}"})
    @Operation(summary = "更新表单数据crazyForm")
    /* renamed from: c */
    public CommonResult<?> updateCrazyForm(@PathVariable("name") String str, @RequestBody JSONObject jSONObject) {
        try {
            jSONObject.remove("create_by");
            jSONObject.remove("create_time");
            jSONObject.remove("update_by");
            jSONObject.remove("update_time");
            this.onlCgformHeadService.editCrazyFormData(str, jSONObject);
            return success(true);
        } catch (Exception e) {
            logger.error("OnlCgformApiController.formEditForDesigner()发生异常：" + e.getMessage(), e);
            return error("保存失败");
        }
    }

    @AutoLog(operateType = 1, value = "online列表加载", module = ModuleType.ONLINE)
    @GetMapping({"/getErpColumns/{code}"})
    @Operation(summary = "获取表单字段getErpColumns")
    /* renamed from: c */
    public CommonResult<Map<String, Object>> getErpColumns(@PathVariable("code") String str) {
        CommonResult<Map<String, Object>> result = new CommonResult<>();
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(str);
        if (onlCgformHead == null) {
            return error("实体不存在");
        }
        HashMap<String, Object> hashMap = new HashMap<>(5);
        LoginUser loginUser = (LoginUser) SecurityFrameworkUtils.getLoginUser();
        hashMap.put(CgReportConstant.MAIN, this.onlineService.queryOnlineConfig(onlCgformHead, String.valueOf(loginUser.getId())));
        if (CgformUtil.ERP.equals(onlCgformHead.getThemeTemplate()) && onlCgformHead.getTableType() == 2) {
            String subTableStr = onlCgformHead.getSubTableStr();
            if (StringUtils.isNotEmpty(subTableStr)) {
                ArrayList<OnlComplexModel> arrayList = new ArrayList<>();
                for (String str2 : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    OnlCgformHead onlCgformHead2 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str2));
                    if (onlCgformHead2 != null) {
                        arrayList.add(this.onlineService.queryOnlineConfig(onlCgformHead2, String.valueOf(loginUser.getId())));
                    }
                }
                if (!arrayList.isEmpty()) {
                    hashMap.put("subList", arrayList);
                }
            }
        }
//        result.setOnlTable(onlCgformHead.getTableName());
//        result.setResult(hashMap);
//        result.setSuccess(true);

     return success(hashMap);
    }

    @AutoLog(operateType = 1, value = "online表单加载", module = ModuleType.ONLINE)
    @GetMapping({"/getErpFormItem/{code}"})
    @Operation(summary = "获取表单getErpFormItem")
    /* renamed from: f */
    public CommonResult<JSONObject> getErpFormItem(@PathVariable("code") String str, HttpServletRequest httpServletRequest) {
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(str);
        if (onlCgformHead == null) {
            return error("表不存在");
        }
        CommonResult<JSONObject> result = new CommonResult<>();

        return success(CgformUtil.m247b(this.onlineService.queryOnlineFormObj(onlCgformHead,SecurityFrameworkUtils.getUserName())));
    }

    @GetMapping({"/querySelectOptions"})
    @Operation(summary = "级联下拉数据加载")
    /* renamed from: a */
    public CommonResult<List<TreeModel>> querySelectOptions(@ModelAttribute LinkDown linkDown) {
        CommonResult<List<TreeModel>> result = new CommonResult<>();
        try {
            return success(this.onlCgformFieldService.queryDataListByLinkDown(linkDown));
        } catch (Exception e) {
            logger.warn("online级联下拉数据加载失败：{}", e.getMessage());
            e.printStackTrace();
            return error("online级联下拉数据加载失败");
        }
    }

    @GetMapping({"/data/{tableName}/queryById"})
    @Operation(summary = "根据ID查询数据")
    /* renamed from: a */
    public JSONObject queryDataById(@PathVariable("tableName") String tableName, @RequestParam(name = "mock", required = false) Boolean bool, HttpServletRequest httpServletRequest) {
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, tableName);
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne(lambdaQueryWrapper);
        if (onlCgformHead == null) {
            throw exception("Online表单 {} 不存在",tableName);
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
            throw exception("数据库查询失败，{}",e.getMessage());
        }
    }
}
