package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;

import org.jeecg.common.service.ISysBaseAPI;
import org.jeecg.common.system.query.MatchTypeEnum;
import org.jeecg.common.system.util.JeecgDataAutorUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.common.util.dynamic.db.DbTypeUtils;

import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.jeecg.modules.online.cgform.converter.ConvertUtil;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.mapper.OnlineMapper;
import org.jeecg.modules.online.cgform.model.OnlQueryModel;
import org.jeecg.modules.online.cgform.model.OnlTable;
import org.jeecg.modules.online.cgform.model.SqlOrder;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.constant.OnlineConst;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlineJoinQueryService;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.online.config.database.DataBaseConfig;
import org.jeecg.modules.online.config.database.OnlineFieldConfig;
import org.jeecg.modules.online.config.template.DbTableUtil;
import org.jeecg.modules.online.handler.ConditionHandler;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/* compiled from: OnlineJoinQueryServiceImpl.java */
@Service("onlineJoinQueryService")
/* renamed from: org.jeecg.modules.online.cgform.service.a.h */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/a/h.class */
public class OnlineJoinQueryServiceImpl implements IOnlineJoinQueryService {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(OnlineJoinQueryServiceImpl.class);

    @Autowired
    IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    IOnlCgformHeadService onlCgformHeadService;

    @Autowired
    private IOnlAuthDataService onlAuthDataService;

    @Autowired
    @Lazy
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private OnlineMapper onlineMapper;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    /* renamed from: a */

    @Override // org.jeecg.modules.online.cgform.service.IOnlineJoinQueryService
    public Map<String, Object> pageList(OnlCgformHead head, Map<String, Object> params, boolean ignoreSelectSubField) {
        OnlQueryModel queryInfo = getQueryInfo(head, params, ignoreSelectSubField);
        String sql = queryInfo.getSql();
        Map<String, Object> params2 = queryInfo.getParams();
        Map<String, String> tableAliasMap = queryInfo.getTableAliasMap();
        HashMap<String,Object> hashMap = new HashMap<>(5);
        int pageSize = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
        if (Integer.valueOf(pageSize) == -521) {
            List<Map<String, Object>> selectByCondition = this.onlineMapper.selectByCondition(sql, params2);
            if (selectByCondition == null || selectByCondition.size() == 0) {
                hashMap.put("total", 0);
            } else {
                hashMap.put("total", Integer.valueOf(selectByCondition.size()));
                if (ignoreSelectSubField) {
                    selectByCondition = m369b(selectByCondition);
                }
                hashMap.put("records", CgformUtil.m228a(selectByCondition, tableAliasMap.values()));
            }
            if (ignoreSelectSubField) {
                hashMap.put("fieldList", queryInfo.getFieldList());
            }
        } else {
            int total = params.get("pageNo") == null ? 1 :
                    Integer.parseInt(params.get("pageNo").toString());
            Page<Map<String, Object>> page = new Page<>(total, pageSize);
            page.setOptimizeCountSql(false);
            IPage<Map<String, Object>> selectPageByCondition = this.onlineMapper.selectPageByCondition(page, sql, params2);
            hashMap.put("total", selectPageByCondition.getTotal());
            List<Map<String, Object>> records = selectPageByCondition.getRecords();
            if (ignoreSelectSubField) {
                records = m369b(records);
            }
            hashMap.put("records", CgformUtil.m228a(records, tableAliasMap.values()));
        }
        return hashMap;
    }

    /* renamed from: a */
    private String m358a(OnlTable onlTable, String str, String str2, String str3) {
        String alias = onlTable.getAlias();
        String m235f = CgformUtil.m235f(onlTable.getTableName());
        String str4 = alias + ".";
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(" AND EXISTS (");
        stringBuffer.append(CgformUtil.f180a);
        stringBuffer.append(str4).append("id");
        stringBuffer.append(CgformUtil.f181b);
        stringBuffer.append(m235f);
        stringBuffer.append(" ").append(alias);
        stringBuffer.append(CgformUtil.f188i);
        stringBuffer.append(str4);
        stringBuffer.append(onlTable.getJoinField());
        stringBuffer.append(CgformUtil.f192m);
        stringBuffer.append(str);
        stringBuffer.append(onlTable.getMainField());
        if (str2 != null && !str2.isEmpty()) {
            stringBuffer.append(str2);
        }
        if (str3 != null && !str3.isEmpty()) {
            stringBuffer.append(" AND (").append(str3).append(") ");
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineJoinQueryService
    public Map<String, Object> pageList(OnlCgformHead head, Map<String, Object> params) {
        return pageList(head, params, false);
    }

    /* renamed from: a */
    private String m359a(List<String> list, Map<String, Integer> map, Map<String, String> map2) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : list) {
            String[] split = str.split("\\.");
            String str2 = split[0];
            if ("a".equals(str2)) {
                arrayList.add(str);
            } else {
                String str3 = split[1];
                if (map.get(str3) > 1) {
                    arrayList.add(str + " " + map2.get(str2) + "_" + str3);
                } else {
                    arrayList.add(str);
                }
            }
        }
        return String.join(CgformUtil.COMMA_SEPARATOR, arrayList);
    }

    /* renamed from: a */
    private void m360a(String str, boolean z, List<OnlCgformField> list, List<String> list2, Map<String, Integer> map) {
        if (list == null || list.isEmpty()) {
            if (z) {
                list2.add(str + "id");
                return;
            }
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            OnlCgformField onlCgformField = list.get(i);
            String dbFieldName = onlCgformField.getDbFieldName();
            if (!"id".equals(dbFieldName) && 1 == onlCgformField.getIsShowList()) {
                if (CgformUtil.f223R.equals(onlCgformField.getFieldShowType()) && StrUtils.isNotEmpty(onlCgformField.getDictText())) {
                    list2.add(str + onlCgformField.getDictText());
                }
                list2.add(str + dbFieldName);
                map.merge(dbFieldName, 1, Integer::sum);
            }
        }
        list2.add(str + "id");
        map.put("id", 2);
    }

    /* renamed from: a */
    private OnlTable m361a(OnlCgformHead onlCgformHead, int i, boolean z) {
        String id = onlCgformHead.getId();
        String tableName = onlCgformHead.getTableName();
        OnlTable onlTable = new OnlTable(tableName, id, z);
        List<OnlCgformField> m364a = m364a(id);
        List<OnlCgformField> queryAvailableFields = this.onlCgformFieldService.queryAvailableFields(id, tableName, true, m364a, null);
        onlTable.setAllFieldList(m364a);
        onlTable.setSelectFieldList(queryAvailableFields);
        onlTable.setAliasByIntValue(i);
        if (!z) {
            Iterator<OnlCgformField> it = m364a.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                OnlCgformField next = it.next();
                if (StrUtils.isNotEmpty(next.getMainField()) && StrUtils.isNotEmpty(next.getMainTable())) {
                    onlTable.setMainField(next.getMainField());
                    onlTable.setJoinField(next.getDbFieldName());
                    break;
                }
            }
        }
        return onlTable;
    }

    /* renamed from: a */
    private List<OnlTable> m362a(OnlCgformHead onlCgformHead, String str) {
        String subTableStr;
        ArrayList<OnlTable> arrayList = new ArrayList<>();
        int i = 97 + 1;
        OnlTable m361a = m361a(onlCgformHead, 97, true);
        m361a.setAuthList(this.onlAuthDataService.queryUserOnlineAuthData(str, onlCgformHead.getId()));
        arrayList.add(m361a);
        Integer tableType = onlCgformHead.getTableType();
        if (tableType != null && tableType == 2 && (subTableStr = onlCgformHead.getSubTableStr()) != null && !subTableStr.isEmpty()) {
            for (String str2 : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                OnlCgformHead onlCgformHead2 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str2));
                if (onlCgformHead2 != null) {
                    int i2 = i;
                    i++;
                    OnlTable m361a2 = m361a(onlCgformHead2, i2, false);
                    m361a2.setAuthList(this.onlAuthDataService.queryUserOnlineAuthData(str, onlCgformHead2.getId()));
                    arrayList.add(m361a2);
                }
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    private Map<String, List<OnlCgformField>> m363a(OnlCgformHead onlCgformHead, Map<String, String> map) {
        String subTableStr;
        Map<String,List<OnlCgformField>> hashMap = new HashMap<>(5);
        map.put(onlCgformHead.getTableName(), onlCgformHead.getId());
        hashMap.put(onlCgformHead.getTableName(), m364a(onlCgformHead.getId()));
        Integer tableType = onlCgformHead.getTableType();
        if (tableType != null && tableType == 2 && (subTableStr = onlCgformHead.getSubTableStr()) != null && !subTableStr.isEmpty()) {
            for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                OnlCgformHead onlCgformHead2 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                if (onlCgformHead2 != null) {
                    map.put(onlCgformHead2.getTableName(), onlCgformHead2.getId());
                    hashMap.put(onlCgformHead2.getTableName(), m364a(onlCgformHead2.getId()));
                }
            }
        }
        return hashMap;
    }

    /* renamed from: a */
    private List<OnlCgformField> m364a(String str) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str);
        lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, OnlineConst.isPersist);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        return this.onlCgformFieldService.list(lambdaQueryWrapper);
    }

    /* renamed from: a */
    private boolean m365a(Map<String, Object> map, boolean z, String str, String str2, List<OnlCgformField> list, List<SqlOrder> list2) {
        String string;
        boolean z2 = z;
        Object obj = map.get("column");
        if (obj != null && !"id".equals(obj.toString())) {
            String obj2 = obj.toString();
            Object obj3 = map.get("order");
            String str3 = "desc";
            if (obj3 != null) {
                str3 = obj3.toString();
            }
            if (z) {
                if (CgformUtil.m207c(obj2, list)) {
                    SqlOrder sqlOrder = new SqlOrder(obj2, str3);
                    sqlOrder.setAlias(str2);
                    list2.add(sqlOrder);
                }
            } else if (obj2.startsWith(str)) {
                String replaceFirst = obj2.replaceFirst(str + "_", "");
                if (CgformUtil.m207c(replaceFirst, list)) {
                    SqlOrder sqlOrder2 = new SqlOrder(replaceFirst, str3);
                    sqlOrder2.setAlias(str2);
                    list2.add(sqlOrder2);
                    z2 = true;
                }
            }
        } else {
            for (OnlCgformField onlCgformField : list) {
                if ("1".equals(onlCgformField.getSortFlag())) {
                    String fieldExtendJson = onlCgformField.getFieldExtendJson();
                    SqlOrder sqlOrder3 = new SqlOrder(onlCgformField.getDbFieldName());
                    sqlOrder3.setAlias(str2);
                    if (fieldExtendJson != null && !fieldExtendJson.isEmpty() && (string = JSON.parseObject(fieldExtendJson).getString(ExtendJsonKey.ORDER_RULE)) != null && !string.isEmpty()) {
                        sqlOrder3.setRule(string);
                        list2.add(sqlOrder3);
                        z2 = true;
                    }
                }
            }
        }
        return z2;
    }

    /* renamed from: a */
    private String m366a(List<SqlOrder> list) {
        if (list.isEmpty()) {
            list.add(SqlOrder.m296a("a."));
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (SqlOrder sqlOrder : list) {
            arrayList.add(sqlOrder.getRealSql());
        }
        return " ORDER BY " + String.join(CgformUtil.COMMA_SEPARATOR, arrayList);
    }

    /* renamed from: a */
    private String m367a(StringBuilder sb) {
        String sb2 = sb.toString();
        if (sb2.isEmpty()) {
            return "";
        }
        return " AND (" + sb2 + ") ";
    }

    /* renamed from: a */
    private boolean m368a(StringBuilder sb, JSONArray jSONArray, MatchTypeEnum matchTypeEnum, String str, String str2, List<OnlCgformField> list, boolean z, boolean z2) {
        boolean z3 = z2;
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.size(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("field");
                String[] split = string.split(CgformUtil.COMMA_SEPARATOR);
                if (split.length == 1) {
                    if (z && CgformUtil.m207c(string, list)) {
                        CgformUtil.m187a(sb, str + string, jSONObject, matchTypeEnum, null, z3);
                        z3 = false;
                    }
                } else {
                    String str3 = split[1];
                    if (str2.equalsIgnoreCase(split[0]) && CgformUtil.m207c(str3, list)) {
                        CgformUtil.m187a(sb, str + str3, jSONObject, matchTypeEnum, null, z3);
                        z3 = false;
                    }
                }
            }
        }
        return z3;
    }

    /* renamed from: b */
    private List<Map<String, Object>> m369b(List<Map<String, Object>> list) {
        Map<String,Map<String, Object>> hashMap = new HashMap<>(5);
        for (Map<String, Object> map : list) {
            String obj = map.get("id").toString();
            hashMap.putIfAbsent(obj, map);
        }
        return new ArrayList<>(hashMap.values());
    }

    /* renamed from: a */
    private boolean m370a(OnlTable onlTable, JSONArray jSONArray) {
        if (onlTable.m292a()) {
            return true;
        }
        String tableName = onlTable.getTableName();
        if (jSONArray != null && !jSONArray.isEmpty()) {
            for (int i = 0; i < jSONArray.size(); i++) {
                String[] split = jSONArray.getJSONObject(i).getString("field").split(CgformUtil.COMMA_SEPARATOR);
                if (split.length == 2 && split[0] != null && split[0].equals(tableName)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /* renamed from: a */
    private boolean m371a(OnlTable onlTable) {
        if (onlTable.m292a()) {
            return true;
        }
        List<OnlCgformField> selectFieldList = onlTable.getSelectFieldList();
        if (selectFieldList != null && !selectFieldList.isEmpty()) {
            for (OnlCgformField onlCgformField : selectFieldList) {
                String mainTable = onlCgformField.getMainTable();
                if (mainTable == null || mainTable.isEmpty()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /* renamed from: a */
    private ConditionHandler m372a(OnlTable onlTable, JSONArray jSONArray, String str, ConditionHandler conditionHandler) {
        String tableName = onlTable.getTableName();
        boolean m292a = onlTable.m292a();
        List<OnlCgformField> allFieldList = onlTable.getAllFieldList();
        ArrayList<OnlineFieldConfig> arrayList = new ArrayList<>();
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.size(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("field");
                String[] split = string.split(CgformUtil.COMMA_SEPARATOR);
                if (split.length == 1) {
                    if (m292a && CgformUtil.m207c(string, allFieldList)) {
                        arrayList.add(new OnlineFieldConfig(jSONObject));
                    }
                } else {
                    String str2 = split[1];
                    if (tableName.equalsIgnoreCase(split[0]) && CgformUtil.m207c(str2, allFieldList)) {
                        arrayList.add(new OnlineFieldConfig(jSONObject));
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                ConditionHandler c0013a2 = new ConditionHandler(onlTable.getAlias() + ".", true, str);
                c0013a2.setDuplicateSqlNameRecord(conditionHandler.getDuplicateSqlNameRecord());
                c0013a2.setDuplicateParamNameRecord(conditionHandler.getDuplicateParamNameRecord());
                c0013a2.m10a((List<OnlineFieldConfig>) arrayList);
                return c0013a2;
            }
            return null;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineJoinQueryService
    public OnlQueryModel getQueryInfo(OnlCgformHead head, Map<String, Object> params, boolean ignoreSelectSubField) {
        return getQueryInfo(head, params, ignoreSelectSubField, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jeecg.modules.online.cgform.service.IOnlineJoinQueryService
    public OnlQueryModel getQueryInfo(OnlCgformHead head, Map<String, Object> params, boolean ignoreSelectSubField, boolean isNewExport) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<OnlTable> m362a = m362a(head, loginUser.getId());
        JSONArray m249b = CgformUtil.m249b(params);
        MatchTypeEnum m250c = CgformUtil.m250c(params);
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        String str = "";
        boolean z2 = false;
        StringBuilder stringBuffer = new StringBuilder();
        StringBuilder stringBuffer2 = new StringBuilder();
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<SqlOrder> arrayList2 = new ArrayList<>();
        HashMap<String,Integer> hashMap = new HashMap<>(5);
        HashMap<String,String> hashMap2 = new HashMap<>(5);
        List<OnlCgformField> arrayList3 = new ArrayList<>();
        Map<String,Object> hashMap3 = new HashMap<>(5);
        for (OnlTable onlTable : m362a) {
            List<OnlCgformField> selectFieldList = onlTable.getSelectFieldList();
            String alias = onlTable.getAlias();
            String str2 = alias + ".";
            String str3 = " " + alias + " ";
            String tableName = onlTable.getTableName();
            List<OnlCgformField> allFieldList = onlTable.getAllFieldList();
            List<SysPermissionDataRuleModel> authList = onlTable.getAuthList();
            if (!z2 && authList != null && !authList.isEmpty()) {
                JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
                z2 = true;
            }
            ConditionHandler conditionHandler = new ConditionHandler(str2);
            conditionHandler.setTableName(tableName);
            conditionHandler.setNeedList(null);
            conditionHandler.setFirst(false);
            String m8a = conditionHandler.m8a(CgformUtil.m252g(allFieldList), params, authList, tableName + "@");
            hashMap3.putAll(conditionHandler.getSqlParams());
            boolean m365a = m365a(params, onlTable.m292a(), tableName, str2, allFieldList, arrayList2);
            boolean m371a = m371a(onlTable);
            boolean m370a = m370a(onlTable, m249b);
            boolean z3 = !m8a.isEmpty();
            if (m371a || m370a || z3 || m365a) {
                boolean z4 = !m371a && (m370a || z3);
                if (m365a) {
                    z4 = false;
                }
                if ((ignoreSelectSubField && onlTable.m292a()) || (!ignoreSelectSubField && m371a)) {
                    m360a(str2, onlTable.m292a(), selectFieldList, arrayList, hashMap);
                }
                String str4 = "";
                ConditionHandler m372a = m372a(onlTable, m249b, m250c.getValue(), conditionHandler);
                if (m372a != null) {
                    str4 = m372a.getSql().toString();
                    if (!str4.isEmpty()) {
                        hashMap3.putAll(m372a.getSqlParams());
                    }
                }
                if (onlTable.m292a()) {
                    stringBuffer.append(" FROM ").append(CgformUtil.m235f(tableName)).append(str3);
                    str = str2;
                } else {
                    hashMap2.put(alias, tableName);
                    if (z4) {
                        stringBuffer2.append(m358a(onlTable, str, m8a, str4));
                    } else {
                        stringBuffer.append(" LEFT JOIN ");
                        stringBuffer.append(CgformUtil.m235f(tableName));
                        stringBuffer.append(str3);
                        stringBuffer.append(CgformUtil.f183d);
                        stringBuffer.append(str2);
                        stringBuffer.append(onlTable.getJoinField());
                        stringBuffer.append(CgformUtil.f192m);
                        stringBuffer.append(str);
                        stringBuffer.append(onlTable.getMainField());
                    }
                }
                if (!z4) {
                    stringBuffer2.append(m8a);
                    if (!str4.isEmpty()) {
                        if (z) {
                            sb.append(str4);
                            z = false;
                        } else {
                            sb.append(" ").append(m250c.getValue()).append(" ").append(str4);
                        }
                    }
                }
            }
        }
        String m359a = m359a(arrayList, hashMap, hashMap2);
        String m367a = m367a(sb);
        String m366a = m366a(arrayList2);
        String str5 = "SELECT " + m359a + stringBuffer.toString() + " where 1=1  " + stringBuffer2.toString() + m367a;
        if (!DbTypeUtils.dbTypeIsSqlServer(DbTableUtil.getDbTypeByonfig((DataBaseConfig) null))) {
            str5 = str5 + m366a;
        }
        OnlQueryModel onlQueryModel = new OnlQueryModel(str5, hashMap3);
        onlQueryModel.setTableAliasMap(hashMap2);
        for (OnlTable c0067f2 : m362a) {
            List<OnlCgformField> selectFieldList2 = c0067f2.getSelectFieldList();
            if (isNewExport) {
                for (OnlCgformField onlCgformField : selectFieldList2) {
                    String dbFieldName = onlCgformField.getDbFieldName();
                    Integer num = hashMap.get(dbFieldName);
                    if (num != null && num.intValue() > 1 && !c0067f2.m292a()) {
                        onlCgformField.setDbFieldName(c0067f2.getTableName() + "_" + dbFieldName);
                    }
                    arrayList3.add(onlCgformField);
                }
            } else if (ignoreSelectSubField && c0067f2.m292a()) {
                arrayList3 = selectFieldList2;
            }
        }
        onlQueryModel.setFieldList(arrayList3);
        return onlQueryModel;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineJoinQueryService
    public XSSFWorkbook handleOnlineExport(OnlCgformHead head, Map<String, Object> params) {
        OnlQueryModel queryInfo;
        XSSFWorkbook xSSFWorkbook = new XSSFWorkbook();
        boolean m181a = CgformUtil.m181a(head);
        if (m181a) {
            queryInfo = getQueryInfo(head, params, false, true);
        } else {
            queryInfo = this.onlCgformFieldService.getQueryInfo(head, params, null);
        }
        boolean z = true;
        int num = 50000;
        int num2 = 1;
        String sql = queryInfo.getSql();
        Map<String, Object> params2 = queryInfo.getParams();
        List<OnlCgformField> fieldList = queryInfo.getFieldList();
        List<ExcelExportEntity> m257b = CgformUtil.m257b(fieldList, "id", this.upLoadPath);
        boolean z2 = false;
        while (z) {
            Page<Map<String, Object>> page = new Page<>(num2, num);
            page.setOptimizeCountSql(false);
            page.setSearchCount(false);
            Integer num3 = num2;
            num2 = num2 + 1;
            params.put("pageNo", num3);
            List<Map<String, Object>> m227d = CgformUtil.m227d(this.onlineMapper.selectPageByCondition(page, sql, params2).getRecords());
            if (m227d.isEmpty()) {
                z = false;
            } else {
                List<Map<String, Object>> arrayList = new ArrayList<>();
                String obj = params.get("selections") == null ? null : params.get("selections").toString();
                if (StrUtils.isNotEmpty(obj)) {
                    z = false;
                    if (m181a) {
                        Map<String, List<String>> m256f = CgformUtil.m256f(obj, new ArrayList<>(queryInfo.getTableAliasMap().values()));
                        arrayList =  m227d.stream().filter(map -> m373a( map,  m256f)).collect(Collectors.toList());
                    } else {
                        List<String> m255h = CgformUtil.m255h(obj);
                        arrayList =  m227d.stream().filter(map2 -> m255h.contains((String) map2.get("id"))).collect(Collectors.toList());
                    }
                } else {
                    if (m227d == null) {
                        m227d = new ArrayList<>();
                    }
                    arrayList.addAll(m227d);
                }
                ConvertUtil.m176a(1, arrayList, fieldList);
                try {
                    this.onlCgformHeadService.executeEnhanceExport(head, arrayList);
                } catch (BusinessException e) {
                    logger.error("导出java增强处理出错", e.getMessage());
                }
                if (head.getTableType().intValue() == 2 && !m181a && StrUtils.isEmpty(params.get(CgformUtil.f251at))) {
                    String subTableStr = head.getSubTableStr();
                    if (StrUtils.isNotEmpty(subTableStr)) {
                        for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                            addAllSubTableDate(str, params, arrayList, m257b, z2);
                        }
                        z2 = true;
                    }
                }
                ExcelExportServer excelExportServer = new ExcelExportServer();
                ExportParams exportParams = new ExportParams();
                exportParams.setType(ExcelType.XSSF);
                excelExportServer.createSheetForMap(xSSFWorkbook, exportParams, m257b, arrayList);
            }
        }
        return xSSFWorkbook;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineJoinQueryService
    public void addAllSubTableDate(String subTable, Map<String, Object> params, List<Map<String, Object>> result, List<ExcelExportEntity> entityList, boolean subEntityExist) {
        if (StrUtils.isEmpty(subTable)) {
            return;
        }
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, subTable));
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<OnlCgformField>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId());
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = this.onlCgformFieldService.list(lambdaQueryWrapper);
        String str = "";
        String str2 = "";
        Iterator<OnlCgformField> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            OnlCgformField onlCgformField = (OnlCgformField) it.next();
            if (StrUtils.isNotEmpty(onlCgformField.getMainField())) {
                str = onlCgformField.getMainField();
                str2 = onlCgformField.getDbFieldName();
                break;
            }
        }
        if (!subEntityExist) {
            ExcelExportEntity excelExportEntity = new ExcelExportEntity(onlCgformHead.getTableTxt(), subTable);
            excelExportEntity.setList(CgformUtil.m257b(list, "id", this.upLoadPath));
            entityList.add(excelExportEntity);
        }
        for (int i = 0; i < result.size(); i++) {
            params.put(str2, result.get(i).get(str));
            List<Map<String, Object>> queryListData = this.onlCgformHeadService.queryListData(CgformUtil.m205a(onlCgformHead.getTableName(), list, params));
            ConvertUtil.m176a(1, queryListData, list);
            result.get(i).put(subTable, CgformUtil.m227d(queryListData));
        }
    }

    /* renamed from: a */
    private boolean m373a(Map<String, Object> map, Map<String, List<String>> map2) {
        boolean z = true;
        for (String str : map2.keySet()) {
            z = z && map2.get(str).contains((String) map.get(str));
        }
        return z;
    }
}
