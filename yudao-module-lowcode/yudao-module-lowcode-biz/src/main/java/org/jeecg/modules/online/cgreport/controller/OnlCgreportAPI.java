package org.jeecg.modules.online.cgreport.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.exception.JeecgBootException;

import org.jeecg.common.service.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.DynamicDataSourceModel;
import org.jeecg.common.util.BrowserUtils;
import org.jeecg.common.util.SqlInjectionUtil;

import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.common.util.security.JdbcSecurityUtil;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.constant.CgReportConstant;
import org.jeecg.modules.online.cgreport.utils.CgReportSqlUtil;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.jeecg.modules.online.cgreport.service.impl.OnlCgreportHeadServiceImpl;
import org.jeecg.modules.online.config.blackList.OnlReportQueryBlackListHandler;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* compiled from: OnlCgreportAPI.java */
@RequestMapping({"/online/cgreport/api"})
@RestController("onlCgreportAPI")
/* renamed from: org.jeecg.modules.online.cgreport.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/a/a.class */
@Tag(name = "online报表API")
public class OnlCgreportAPI {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(OnlCgreportAPI.class);

    @Autowired
    private org.jeecg.modules.online.cgreport.service.impl.OnlCgreportAPIService cgreportAPIService;

    @Autowired
    private OnlCgreportHeadServiceImpl onlCgreportHeadService;

    @Autowired
    private IOnlCgreportItemService onlCgreportItemService;

    @Autowired
    @Lazy
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;

    @Autowired
    private OnlReportQueryBlackListHandler onlReportQueryBlackListHandler;

    /* renamed from: a */

    @GetMapping({"/getColumnsAndData/{code}"})
//    @PermissionData
    @Operation(summary = "获取报表列和数据")
    /* renamed from: a */
    public Result<?> m389a(@PathVariable("code") String str, HttpServletRequest httpServletRequest) {
        OnlCgreportHead onlCgreportHead = this.onlCgreportHeadService.getById(str);
        if (onlCgreportHead == null) {
            return Result.error("实体不存在");
        }
        Result<?> m391b = m391b(str, httpServletRequest);
        if (m391b.getCode().equals(200)) {
            List<Map<String, Object>> list =  (List<Map<String, Object>>)((Map<String,Object>)m391b.getResult()).get("records");
            Map<String, Object> queryColumnInfo = this.onlCgreportHeadService.queryColumnInfo(str, false);
            JSONArray jSONArray = (JSONArray) queryColumnInfo.get("columns");
            HashMap<String,List<DictModel>> hashMap = new HashMap<>(5);
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.size(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    Object obj = jSONObject.get("dictCode");
                    if (obj != null) {
                        String obj2 = obj.toString();
                        String string = jSONArray.getJSONObject(i).getString("dataIndex");
                        List<DictModel> queryColumnDictList = this.onlCgreportHeadService.queryColumnDictList(obj2, list, string);
                        if (queryColumnDictList != null) {
                            hashMap.put(string, queryColumnDictList);
                            jSONObject.put("customRender", string);
                        }
                    }
                }
            }
            queryColumnInfo.put("cgreportHeadName", onlCgreportHead.getName());
            queryColumnInfo.put("data", m391b.getResult());
            queryColumnInfo.put("dictOptions", hashMap);
            return Result.ok(queryColumnInfo);
        }
        return m391b;
    }

    @GetMapping({"/getColumns/{code}"})
    @Deprecated
    @Operation(summary = "获取报表列")
    /* renamed from: a */
    public Result<?> m390a(@PathVariable("code") String str) {
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) this.onlCgreportHeadService.getById(str);
        if (onlCgreportHead == null) {
            return Result.error("实体不存在");
        }
        QueryWrapper<OnlCgreportItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cgrhead_id", str);
        queryWrapper.eq("is_show", 1);
        queryWrapper.orderByAsc("order_num");
        List<OnlCgreportItem> list = this.onlCgreportItemService.list(queryWrapper);
        ArrayList<Map<String,String>> arrayList = new ArrayList<>();
        HashMap<String,Object> hashMap = new HashMap<>(5);
        for (OnlCgreportItem onlCgreportItem : list) {
            HashMap<String,String> hashMap2 = new HashMap(5);
            hashMap2.put(CgformUtil.TITLE, onlCgreportItem.getFieldTxt());
            hashMap2.put("dataIndex", onlCgreportItem.getFieldName());
            hashMap2.put("align", CgformUtil.CENTER);
            hashMap2.put("sorter", "true");
            arrayList.add(hashMap2);
            String dictCode = onlCgreportItem.getDictCode();
            if (StrUtils.isNotEmpty(dictCode)) {
                List<DictModel> list2 = null;
                if (dictCode.toLowerCase().indexOf("select ") == 0) {
                    this.sysBaseAPI.dictTableWhiteListCheckByDict(dictCode);
                    SqlInjectionUtil.specialFilterContentForOnlineReport(dictCode);
                    List<Map<String, Object>> executeSqlDict = this.onlCgreportHeadService.getBaseMapper().executeSqlDict(dictCode);
                    if (executeSqlDict != null && !executeSqlDict.isEmpty()) {
                        list2 = JSON.parseArray(JSON.toJSONString(executeSqlDict), DictModel.class);
                    }
                } else {
                    list2 = this.sysBaseAPI.queryDictItemsByCode(dictCode);
                }
                if (list2 != null) {
                    hashMap.put(onlCgreportItem.getFieldName(), list2);
                    hashMap2.put("customRender", onlCgreportItem.getFieldName());
                }
            }
        }
        HashMap<String,Object> hashMap3 = new HashMap<>(1);
        hashMap3.put("columns", arrayList);
        hashMap3.put("dictOptions", hashMap);
        hashMap3.put("cgreportHeadName", onlCgreportHead.getName());
        return Result.ok(hashMap3);
    }

    @GetMapping({"/getData/{code}"})
    @PermissionData
    @Operation(summary = "获取报表数据")
    /* renamed from: b */
    public Result<?> m391b(@PathVariable("code") String str, HttpServletRequest httpServletRequest) {
        Map<String, Object> m430a = CgReportSqlUtil.m430a(httpServletRequest);
        m430a.put("getAll", httpServletRequest.getAttribute("getAll"));
        try {
            return Result.OK(this.cgreportAPIService.getDataById(str, m430a));
        } catch (JeecgBootException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping({"/getDataOrderByValue/{code}"})
    @PermissionData
    @Operation(summary = "获取报表数据并排序")
    /* renamed from: c */
    public Result<?> m392c(@PathVariable("code") String str, HttpServletRequest httpServletRequest) {
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) this.onlCgreportHeadService.getById(str);
        if (onlCgreportHead == null) {
            return Result.error("实体不存在");
        }
        String trim = onlCgreportHead.getCgrSql().trim();
        String dbSource = onlCgreportHead.getDbSource();
        try {
            Map<String, Object> m430a = CgReportSqlUtil.m430a(httpServletRequest);
            Object obj = m430a.get(CgReportConstant.ORDER_FIELD);
            Object obj2 = m430a.get(CgReportConstant.ORDER_VALUE);
            if (StrUtils.isEmpty(obj) || StrUtils.isEmpty(obj2)) {
                return Result.error("order_field 和 order_value 参数不能为空！");
            }
            String str2 = "force_" + obj;
            m430a.put(str2, obj2);
            m430a.put("getAll", true);
            JSONArray parseArray = JSON.parseArray(JSON.toJSONString(this.cgreportAPIService.executeSelectSqlRoute(dbSource, trim, m430a, onlCgreportHead.getId()).get("records")));
            m430a.remove(obj.toString());
            m430a.remove(str2);
            m430a.remove(CgReportConstant.ORDER_FIELD);
            m430a.remove(CgReportConstant.ORDER_VALUE);
            m430a.put("getAll", httpServletRequest.getAttribute("getAll"));
            Map<String, Object> executeSelectSqlRoute = this.cgreportAPIService.executeSelectSqlRoute(dbSource, trim, m430a, onlCgreportHead.getId());
            JSONArray parseArray2 = JSON.parseArray(JSON.toJSONString(executeSelectSqlRoute.get("records")));
            m393a(parseArray, parseArray2);
            executeSelectSqlRoute.put("records", parseArray2);
            return Result.ok(executeSelectSqlRoute);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.error("SQL执行失败：" + e.getMessage());
        }
    }

    /* renamed from: a */
    private void m393a(JSONArray jSONArray, JSONArray jSONArray2) {
        for (int i = 0; i < jSONArray.size(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            String string = jSONObject.getString("id");
            if (((int) jSONArray2.stream().filter(obj -> {
                return string.equals(((JSONObject) obj).getString("id"));
            }).count()) == 0) {
                jSONArray2.add(0, jSONObject);
            }
        }
    }

    @GetMapping({"/getQueryInfo/{code}"})
    @Operation(summary = "获取报表查询信息")
    /* renamed from: b */
    public Result<?> m394b(@PathVariable("code") String str) {
        try {
            return Result.ok(this.onlCgreportItemService.getAutoListQueryInfo(str));
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    @GetMapping({"/getParamsInfo/{code}"})
    @Operation(summary = "获取报表参数信息")
    /* renamed from: c */
    public Result<?> m395c(@PathVariable("code") String str) {
        try {
            LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OnlCgreportParam::getCgrheadId, str);
            lambdaQueryWrapper.orderByAsc(OnlCgreportParam::getOrderNum);
            return Result.ok(this.onlCgreportParamService.list(lambdaQueryWrapper));
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    @RequestMapping({"/exportManySheetXls/{reportId}"})
    @PermissionData
    @Operation(summary = "导出多sheet报表")
    /* renamed from: a */
    public void m396a(@PathVariable("reportId") String str, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (StrUtils.isEmpty(str)) {
            throw exception("参数错误");
        }
        Workbook reportWorkbook = this.cgreportAPIService.getReportWorkbook(str, CgReportSqlUtil.m430a(httpServletRequest));
        httpServletResponse.setContentType("application/vnd.ms-excel");
        OutputStream outputStream = null;
        try {
            try {
                if ("MSIE".equalsIgnoreCase(BrowserUtils.checkBrowse(httpServletRequest).substring(0, 4))) {
                    httpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("报表", StandardCharsets.UTF_8) + ".xls");
                } else {
                    httpServletResponse.setHeader("content-disposition", "attachment;filename=" + new String("报表".getBytes(StandardCharsets.UTF_8), "ISO8859-1") + ".xls");
                }
                outputStream = httpServletResponse.getOutputStream();
                reportWorkbook.write(outputStream);
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                logger.warn("导出失败{}", e2.getMessage());
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e3) {
                }
            }
        } catch (Throwable th) {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (Exception e4) {
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v171, types: [java.util.List] */
    @RequestMapping({"/exportXls/{reportId}"})
    @PermissionData
    @Deprecated
    @Operation(summary = "导出报表")
    /* renamed from: b */
    public void m397b(@PathVariable("reportId") String str, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (StrUtils.isNotEmpty(str)) {
            try {
                List<Map<String,Object>> list = (List<Map<String,Object>>) this.onlCgreportHeadService.queryCgReportConfig(str).get(CgReportConstant.ITEMS);
                httpServletRequest.setAttribute("getAll", true);
                Result<?> m391b = m391b(str, httpServletRequest);
                List<Map<String, Object>> list2 = null;
                if (m391b.getCode().equals(200)) {
                    list2 = (List) ((Map) m391b.getResult()).get("records");
                }
                ArrayList<String> arrayList = new ArrayList<>();
                HashMap<String,Object> hashMap = new HashMap<>(5);
                HashMap<String,List<String>> hashMap2 = new HashMap<>(5);
                ArrayList<ExcelExportEntity> arrayList2 = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    String str2 = (String) ( list.get(i)).get(CgReportConstant.FIELD_TYPE);
                    if ("1".equals(ConvertUtils.getString(( list.get(i)).get("is_show")))) {
                        String obj = ( list.get(i)).get(CgReportConstant.FIELD_NAME).toString();
                        ExcelExportEntity excelExportEntity = new ExcelExportEntity(( list.get(i)).get("field_txt").toString(), obj, 15);
                        Object obj2 = ( list.get(i)).get(CgReportConstant.DICT_CODE);
                        List<DictModel> queryColumnDictList = this.onlCgreportHeadService.queryColumnDictList(ConvertUtils.getString(obj2), list2, obj);
                        if (queryColumnDictList != null && !queryColumnDictList.isEmpty()) {
                            ArrayList<String> arrayList3 = new ArrayList<>();
                            for (DictModel dictModel : queryColumnDictList) {
                                if (dictModel.getValue().contains("_")) {
                                    arrayList3.add(dictModel.getText() + "_" + dictModel.getValue().replace("_", "---"));
                                } else {
                                    arrayList3.add(dictModel.getText() + "_" + dictModel.getValue());
                                }
                            }
                            excelExportEntity.setReplace(arrayList3.toArray(new String[arrayList3.size()]));
                        }
                        Object obj3 = ((Map<?, ?>) list.get(i)).get("replace_val");
                        if (StrUtils.isNotEmpty(obj3)) {
                            excelExportEntity.setReplace(obj3.toString().split(CgformUtil.COMMA_SEPARATOR));
                        }
                        if (StrUtils.isNotEmpty(( list.get(i)).get("group_title"))) {
                            String obj4 = ( list.get(i)).get("group_title").toString();
                            List<String> arrayList4 = new ArrayList<>();
                            if (hashMap2.containsKey(obj4)) {
                                arrayList4 =  hashMap2.get(obj4);
                                arrayList4.add(obj);
                            } else {
                                arrayList2.add(new ExcelExportEntity(obj4, obj4, true));
                                arrayList4.add(obj);
                            }
                            hashMap2.put(obj4, arrayList4);
                            excelExportEntity.setColspan(true);
                        }
                        if (StrUtils.isNotEmpty(str2) && StrUtils.isEmpty(obj2) && ("Integer".equals(str2) || "Long".equals(str2))) {
                            excelExportEntity.setType(4);
                        }
                        arrayList2.add(excelExportEntity);
                    }
                    if ("1".equals(ConvertUtils.getString(( list.get(i)).get("is_total")))) {
                        arrayList.add(( list.get(i)).get(CgReportConstant.FIELD_NAME).toString());
                    }
                }
                for (Map.Entry<String, List<String>> entry : hashMap2.entrySet()) {
                    String str3 = entry.getKey();
                    List<String> list3 = entry.getValue();
                    for (ExcelExportEntity excelExportEntity2 : arrayList2) {
                        if (str3.equals(excelExportEntity2.getName()) && excelExportEntity2.isColspan()) {
                            excelExportEntity2.setSubColumnList(list3);
                        }
                    }
                }
                if (!arrayList.isEmpty()) {
                    for (String str4 : arrayList) {
                        BigDecimal bigDecimal = new BigDecimal("0.0");
                        if (list2 != null) {
                            for (Map<String, Object> stringObjectMap : list2) {
                                String obj5 = stringObjectMap.get(str4).toString();
                                if (obj5.matches("\\d+(.\\d+)?")) {
                                    bigDecimal = bigDecimal.add(new BigDecimal(obj5));
                                }
                            }
                        }
                        hashMap.put(str4, bigDecimal);
                    }
                    if (list2 != null) {
                        list2.add(hashMap);
                    }
                }
                httpServletResponse.setContentType("application/vnd.ms-excel");
                OutputStream outputStream = null;
                try {
                    if ("MSIE".equalsIgnoreCase(BrowserUtils.checkBrowse(httpServletRequest).substring(0, 4))) {
                        httpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("报表", StandardCharsets.UTF_8) + ".xls");
                    } else {
                        httpServletResponse.setHeader("content-disposition", "attachment;filename=" + new String("报表".getBytes(StandardCharsets.UTF_8), "ISO8859-1") + ".xls");
                    }
                    Workbook exportExcel = ExcelExportUtil.exportExcel(new ExportParams((String) null, "导出信息"), arrayList2, list2);
                    outputStream = httpServletResponse.getOutputStream();
                    exportExcel.write(outputStream);
                    try {
                        outputStream.flush();
                        outputStream.close();
                        return;
                    } catch (Exception e) {
                        return;
                    }
                } catch (Exception e2) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                        return;
                    } catch (Exception e3) {
                        return;
                    }
                } catch (Throwable th) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (Exception e4) {
                    }
                    throw th;
                }
            } catch (Exception e5) {
                throw exception("动态报表配置不存在!");
            }
        }
        throw exception("参数错误");
    }

    @GetMapping({"/getRpColumns/{code}"})
    @Operation(summary = "获取报表列")
    /* renamed from: d */
    public Result<?> m398d(@PathVariable("code") String str) {
        LambdaQueryWrapper<OnlCgreportHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgreportHead::getCode, str);
        OnlCgreportHead onlCgreportHead = this.onlCgreportHeadService.getOne(lambdaQueryWrapper);
        if (onlCgreportHead == null) {
            return Result.error("实体不存在");
        }
        Map<String, Object> queryColumnInfo = this.onlCgreportHeadService.queryColumnInfo(onlCgreportHead.getId(), true);
        queryColumnInfo.put("cgRpConfigId", onlCgreportHead.getId());
        queryColumnInfo.put("cgRpConfigName", onlCgreportHead.getName());
        return Result.ok(queryColumnInfo);
    }

    @PostMapping({"/testConnection"})
    @Operation(summary = "测试数据库连接")
    /* renamed from: a */
    public Result<?> m399a(@RequestBody DynamicDataSourceModel dynamicDataSourceModel) {
        Connection connection = null;
        try {
            try {
                JdbcSecurityUtil.validate(dynamicDataSourceModel.getDbUrl());
                Class.forName(dynamicDataSourceModel.getDbDriver());
                Connection connection2 = DriverManager.getConnection(dynamicDataSourceModel.getDbUrl(), dynamicDataSourceModel.getDbUsername(), dynamicDataSourceModel.getDbPassword());
                if (connection2 != null) {
                    Result ok = Result.ok("数据库连接成功");
                    if (connection2 != null) {
                        try {
                            if (!connection2.isClosed()) {
                                connection2.close();
                            }
                        } catch (SQLException e) {
                            logger.error(e.toString());
                        }
                    }
                    return ok;
                }
                Result<?> ok2 = Result.ok("数据库连接失败：错误未知");
                if (connection2 != null) {
                    try {
                        if (!connection2.isClosed()) {
                            connection2.close();
                        }
                    } catch (SQLException e2) {
                        logger.error(e2.toString());
                    }
                }
                return ok2;
            } catch (ClassNotFoundException e3) {
                logger.error(e3.toString());
                Result<?> error = Result.error("数据库连接失败：驱动类不存在");
                if (0 != 0) {
                    try {
                        if (!connection.isClosed()) {
                            connection.close();
                        }
                    } catch (SQLException e4) {
                        logger.error(e4.toString());
                        return error;
                    }
                }
                return error;
            } catch (Exception e5) {
                logger.error(e5.toString());
                Result<?> error2 = Result.error("数据库连接失败：" + e5.getMessage());
                if (0 != 0) {
                    try {
                        if (!connection.isClosed()) {
                            connection.close();
                        }
                    } catch (SQLException e6) {
                        logger.error(e6.toString());
                        return error2;
                    }
                }
                return error2;
            }
        } catch (Throwable th) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e7) {
                logger.error(e7.toString());
                throw th;
            }
            throw th;
        }
    }

    @GetMapping({"/getReportDictList"})
    @Operation(summary = "获取报表字典数据")
    /* renamed from: a */
    public Result<?> m400a(@RequestParam("fieldId") String str, @RequestParam(name = "keyword", required = false) String str2) {
        OnlCgreportItem onlCgreportItem = (OnlCgreportItem) this.onlCgreportItemService.getById(str);
        if (onlCgreportItem == null) {
            throw exception("指定字段不存在");
        }
        return Result.ok(this.onlCgreportHeadService.queryDictSelectData(onlCgreportItem.getDictCode(), str2));
    }
}
