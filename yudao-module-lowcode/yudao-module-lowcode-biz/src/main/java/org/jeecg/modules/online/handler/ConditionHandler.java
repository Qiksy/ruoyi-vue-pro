package org.jeecg.modules.online.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;


import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.common.util.online.DateUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgreport.constant.CgReportConstant;
import org.jeecg.modules.online.config.database.OnlineFieldConfig;
import org.jeecg.modules.online.config.template.DataBaseConst;
import org.jeecg.common.query.MatchTypeEnum;
import org.jeecg.common.query.QueryGenerator;
import org.jeecg.common.query.QueryRuleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/* compiled from: ConditionHandler.java */
/* renamed from: org.jeecg.modules.online.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/a/a.class */
public class ConditionHandler {

    /* renamed from: d */
    private static final Logger f60d = LoggerFactory.getLogger(ConditionHandler.class);

    /* renamed from: a */
    public static final String f61a = "jdbcTemplate";

    /* renamed from: b */
    public static final String f62b = "mybatis";

    /* renamed from: e */
    private String alias;

    /* renamed from: f */
    private String aliasNoPoint;

    /* renamed from: g */
    private String dataBaseType;

    /* renamed from: h */
    private boolean dateStringSearch;

    /* renamed from: i */
    private List<OnlineFieldConfig> fieldList;

    /* renamed from: j */
    private List<String> needList;

    /* renamed from: k */
    private List<SysPermissionDataRuleModel> authDatalist;

    /* renamed from: l */
    private Map<String, Object> reqParams;

    /* renamed from: m */
    private StringBuffer sql;

    /* renamed from: n */
    private StringBuffer superQuerySql;

    /* renamed from: o */
    private Map<String, Object> sqlParams;

    /* renamed from: p */
    private String daoType;

    /* renamed from: q */
    private boolean superQuery;

    /* renamed from: r */
    private String matchType;

    /* renamed from: s */
    private int usePage;

    /* renamed from: t */
    private boolean first;

    /* renamed from: u */
    private String paramPrefix;

    /* renamed from: v */
    private Map<String, String> duplicateSqlNameRecord;

    /* renamed from: w */
    private Map<String, String> duplicateParamNameRecord;

    /* renamed from: x */
    private String tableName;

    /* renamed from: c */
    public String subTableStr;

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setAliasNoPoint(String aliasNoPoint) {
        this.aliasNoPoint = aliasNoPoint;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public void setDateStringSearch(boolean dateStringSearch) {
        this.dateStringSearch = dateStringSearch;
    }

    public void setFieldList(List<OnlineFieldConfig> fieldList) {
        this.fieldList = fieldList;
    }

    public void setNeedList(List<String> needList) {
        this.needList = needList;
    }

    public void setAuthDatalist(List<SysPermissionDataRuleModel> authDatalist) {
        this.authDatalist = authDatalist;
    }

    public void setReqParams(Map<String, Object> reqParams) {
        this.reqParams = reqParams;
    }

    public void setSql(StringBuffer sql) {
        this.sql = sql;
    }

    public void setSuperQuerySql(StringBuffer superQuerySql) {
        this.superQuerySql = superQuerySql;
    }

    public void setSqlParams(Map<String, Object> sqlParams) {
        this.sqlParams = sqlParams;
    }

    public void setDaoType(String daoType) {
        this.daoType = daoType;
    }

    public void setSuperQuery(boolean superQuery) {
        this.superQuery = superQuery;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public void setUsePage(int usePage) {
        this.usePage = usePage;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void setParamPrefix(String paramPrefix) {
        this.paramPrefix = paramPrefix;
    }

    public void setDuplicateSqlNameRecord(Map<String, String> duplicateSqlNameRecord) {
        this.duplicateSqlNameRecord = duplicateSqlNameRecord;
    }

    public void setDuplicateParamNameRecord(Map<String, String> duplicateParamNameRecord) {
        this.duplicateParamNameRecord = duplicateParamNameRecord;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setSubTableStr(String subTableStr) {
        this.subTableStr = subTableStr;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConditionHandler)) {
            return false;
        }
        ConditionHandler conditionHandler = (ConditionHandler) o;
        if (!conditionHandler.m46a(this) || m43a() != conditionHandler.m43a() || m44b() != conditionHandler.m44b() || getUsePage() != conditionHandler.getUsePage() || m45c() != conditionHandler.m45c()) {
            return false;
        }
        String alias = getAlias();
        String alias2 = conditionHandler.getAlias();
        if (alias == null) {
            if (alias2 != null) {
                return false;
            }
        } else if (!alias.equals(alias2)) {
            return false;
        }
        String aliasNoPoint = getAliasNoPoint();
        String aliasNoPoint2 = conditionHandler.getAliasNoPoint();
        if (aliasNoPoint == null) {
            if (aliasNoPoint2 != null) {
                return false;
            }
        } else if (!aliasNoPoint.equals(aliasNoPoint2)) {
            return false;
        }
        String dataBaseType = getDataBaseType();
        String dataBaseType2 = conditionHandler.getDataBaseType();
        if (dataBaseType == null) {
            if (dataBaseType2 != null) {
                return false;
            }
        } else if (!dataBaseType.equals(dataBaseType2)) {
            return false;
        }
        List<OnlineFieldConfig> fieldList = getFieldList();
        List<OnlineFieldConfig> fieldList2 = conditionHandler.getFieldList();
        if (fieldList == null) {
            if (fieldList2 != null) {
                return false;
            }
        } else if (!fieldList.equals(fieldList2)) {
            return false;
        }
        List<String> needList = getNeedList();
        List<String> needList2 = conditionHandler.getNeedList();
        if (needList == null) {
            if (needList2 != null) {
                return false;
            }
        } else if (!needList.equals(needList2)) {
            return false;
        }
        List<SysPermissionDataRuleModel> authDatalist = getAuthDatalist();
        List<SysPermissionDataRuleModel> authDatalist2 = conditionHandler.getAuthDatalist();
        if (authDatalist == null) {
            if (authDatalist2 != null) {
                return false;
            }
        } else if (!authDatalist.equals(authDatalist2)) {
            return false;
        }
        Map<String, Object> reqParams = getReqParams();
        Map<String, Object> reqParams2 = conditionHandler.getReqParams();
        if (reqParams == null) {
            if (reqParams2 != null) {
                return false;
            }
        } else if (!reqParams.equals(reqParams2)) {
            return false;
        }
        StringBuffer sql = getSql();
        StringBuffer sql2 = conditionHandler.getSql();
        if (sql == null) {
            if (sql2 != null) {
                return false;
            }
        } else if (!sql.equals(sql2)) {
            return false;
        }
        StringBuffer superQuerySql = getSuperQuerySql();
        StringBuffer superQuerySql2 = conditionHandler.getSuperQuerySql();
        if (superQuerySql == null) {
            if (superQuerySql2 != null) {
                return false;
            }
        } else if (!superQuerySql.equals(superQuerySql2)) {
            return false;
        }
        Map<String, Object> sqlParams = getSqlParams();
        Map<String, Object> sqlParams2 = conditionHandler.getSqlParams();
        if (sqlParams == null) {
            if (sqlParams2 != null) {
                return false;
            }
        } else if (!sqlParams.equals(sqlParams2)) {
            return false;
        }
        String daoType = getDaoType();
        String daoType2 = conditionHandler.getDaoType();
        if (daoType == null) {
            if (daoType2 != null) {
                return false;
            }
        } else if (!daoType.equals(daoType2)) {
            return false;
        }
        String matchType = getMatchType();
        String matchType2 = conditionHandler.getMatchType();
        if (matchType == null) {
            if (matchType2 != null) {
                return false;
            }
        } else if (!matchType.equals(matchType2)) {
            return false;
        }
        String paramPrefix = getParamPrefix();
        String paramPrefix2 = conditionHandler.getParamPrefix();
        if (paramPrefix == null) {
            if (paramPrefix2 != null) {
                return false;
            }
        } else if (!paramPrefix.equals(paramPrefix2)) {
            return false;
        }
        Map<String, String> duplicateSqlNameRecord = getDuplicateSqlNameRecord();
        Map<String, String> duplicateSqlNameRecord2 = conditionHandler.getDuplicateSqlNameRecord();
        if (duplicateSqlNameRecord == null) {
            if (duplicateSqlNameRecord2 != null) {
                return false;
            }
        } else if (!duplicateSqlNameRecord.equals(duplicateSqlNameRecord2)) {
            return false;
        }
        Map<String, String> duplicateParamNameRecord = getDuplicateParamNameRecord();
        Map<String, String> duplicateParamNameRecord2 = conditionHandler.getDuplicateParamNameRecord();
        if (duplicateParamNameRecord == null) {
            if (duplicateParamNameRecord2 != null) {
                return false;
            }
        } else if (!duplicateParamNameRecord.equals(duplicateParamNameRecord2)) {
            return false;
        }
        String tableName = getTableName();
        String tableName2 = conditionHandler.getTableName();
        if (tableName == null) {
            if (tableName2 != null) {
                return false;
            }
        } else if (!tableName.equals(tableName2)) {
            return false;
        }
        String subTableStr = getSubTableStr();
        String subTableStr2 = conditionHandler.getSubTableStr();
        return subTableStr == null ? subTableStr2 == null : subTableStr.equals(subTableStr2);
    }

    /* renamed from: a */
    protected boolean m46a(Object obj) {
        return obj instanceof ConditionHandler;
    }

    public int hashCode() {
        int usePage = (((((((1 * 59) + (m43a() ? 79 : 97)) * 59) + (m44b() ? 79 : 97)) * 59) + getUsePage()) * 59) + (m45c() ? 79 : 97);
        String alias = getAlias();
        int hashCode = (usePage * 59) + (alias == null ? 43 : alias.hashCode());
        String aliasNoPoint = getAliasNoPoint();
        int hashCode2 = (hashCode * 59) + (aliasNoPoint == null ? 43 : aliasNoPoint.hashCode());
        String dataBaseType = getDataBaseType();
        int hashCode3 = (hashCode2 * 59) + (dataBaseType == null ? 43 : dataBaseType.hashCode());
        List<OnlineFieldConfig> fieldList = getFieldList();
        int hashCode4 = (hashCode3 * 59) + (fieldList == null ? 43 : fieldList.hashCode());
        List<String> needList = getNeedList();
        int hashCode5 = (hashCode4 * 59) + (needList == null ? 43 : needList.hashCode());
        List<SysPermissionDataRuleModel> authDatalist = getAuthDatalist();
        int hashCode6 = (hashCode5 * 59) + (authDatalist == null ? 43 : authDatalist.hashCode());
        Map<String, Object> reqParams = getReqParams();
        int hashCode7 = (hashCode6 * 59) + (reqParams == null ? 43 : reqParams.hashCode());
        StringBuffer sql = getSql();
        int hashCode8 = (hashCode7 * 59) + (sql == null ? 43 : sql.hashCode());
        StringBuffer superQuerySql = getSuperQuerySql();
        int hashCode9 = (hashCode8 * 59) + (superQuerySql == null ? 43 : superQuerySql.hashCode());
        Map<String, Object> sqlParams = getSqlParams();
        int hashCode10 = (hashCode9 * 59) + (sqlParams == null ? 43 : sqlParams.hashCode());
        String daoType = getDaoType();
        int hashCode11 = (hashCode10 * 59) + (daoType == null ? 43 : daoType.hashCode());
        String matchType = getMatchType();
        int hashCode12 = (hashCode11 * 59) + (matchType == null ? 43 : matchType.hashCode());
        String paramPrefix = getParamPrefix();
        int hashCode13 = (hashCode12 * 59) + (paramPrefix == null ? 43 : paramPrefix.hashCode());
        Map<String, String> duplicateSqlNameRecord = getDuplicateSqlNameRecord();
        int hashCode14 = (hashCode13 * 59) + (duplicateSqlNameRecord == null ? 43 : duplicateSqlNameRecord.hashCode());
        Map<String, String> duplicateParamNameRecord = getDuplicateParamNameRecord();
        int hashCode15 = (hashCode14 * 59) + (duplicateParamNameRecord == null ? 43 : duplicateParamNameRecord.hashCode());
        String tableName = getTableName();
        int hashCode16 = (hashCode15 * 59) + (tableName == null ? 43 : tableName.hashCode());
        String subTableStr = getSubTableStr();
        return (hashCode16 * 59) + (subTableStr == null ? 43 : subTableStr.hashCode());
    }

    public String toString() {
        return "ConditionHandler(alias=" + getAlias() + ", aliasNoPoint=" + getAliasNoPoint() + ", dataBaseType=" + getDataBaseType() + ", dateStringSearch=" + m43a() + ", fieldList=" + getFieldList() + ", needList=" + getNeedList() + ", authDatalist=" + getAuthDatalist() + ", reqParams=" + getReqParams() + ", sql=" + getSql() + ", superQuerySql=" + getSuperQuerySql() + ", sqlParams=" + getSqlParams() + ", daoType=" + getDaoType() + ", superQuery=" + m44b() + ", matchType=" + getMatchType() + ", usePage=" + getUsePage() + ", first=" + m45c() + ", paramPrefix=" + getParamPrefix() + ", duplicateSqlNameRecord=" + getDuplicateSqlNameRecord() + ", duplicateParamNameRecord=" + getDuplicateParamNameRecord() + ", tableName=" + getTableName() + ", subTableStr=" + getSubTableStr() + ")";
    }

    public String getAlias() {
        return this.alias;
    }

    public String getAliasNoPoint() {
        return this.aliasNoPoint;
    }

    public String getDataBaseType() {
        return this.dataBaseType;
    }

    /* renamed from: a */
    public boolean m43a() {
        return this.dateStringSearch;
    }

    public List<OnlineFieldConfig> getFieldList() {
        return this.fieldList;
    }

    public List<String> getNeedList() {
        return this.needList;
    }

    public List<SysPermissionDataRuleModel> getAuthDatalist() {
        return this.authDatalist;
    }

    public Map<String, Object> getReqParams() {
        return this.reqParams;
    }

    public StringBuffer getSql() {
        return this.sql;
    }

    public StringBuffer getSuperQuerySql() {
        return this.superQuerySql;
    }

    public Map<String, Object> getSqlParams() {
        return this.sqlParams;
    }

    public String getDaoType() {
        return this.daoType;
    }

    /* renamed from: b */
    public boolean m44b() {
        return this.superQuery;
    }

    public String getMatchType() {
        return this.matchType;
    }

    public int getUsePage() {
        return this.usePage;
    }

    /* renamed from: c */
    public boolean m45c() {
        return this.first;
    }

    public String getParamPrefix() {
        return this.paramPrefix;
    }

    public Map<String, String> getDuplicateSqlNameRecord() {
        return this.duplicateSqlNameRecord;
    }

    public Map<String, String> getDuplicateParamNameRecord() {
        return this.duplicateParamNameRecord;
    }

    public String getTableName() {
        return this.tableName;
    }

    public ConditionHandler() {
    }

    public ConditionHandler(String str, String str2) {
        this.alias = str;
        this.aliasNoPoint = str.replace(".", "");
        this.dataBaseType = str2;
        this.dateStringSearch = m34e(str2);
        this.sql = new StringBuffer();
        this.sqlParams = new HashMap(5);
        this.authDatalist = null;
        this.needList = null;
        this.matchType = CgformUtil.f184e;
        this.usePage = 1;
        this.first = true;
        this.paramPrefix = "";
        this.duplicateSqlNameRecord = new HashMap(5);
        this.duplicateParamNameRecord = new HashMap(5);
    }

    public ConditionHandler(String str) {
        this(str, null);
        this.usePage = 2;
    }

    public ConditionHandler(String str, boolean z, String str2) {
        this(str, null);
        this.superQuery = z;
        this.matchType = " " + str2 + " ";
        this.usePage = 2;
    }

    /* renamed from: a */
    public String m6a(List<OnlineFieldConfig> list, Map<String, Object> map) {
        m11c(list, map);
        m12d();
        return this.sql.toString();
    }

    /* renamed from: a */
    public String m7a(List<OnlineFieldConfig> list, Map<String, Object> map, List<SysPermissionDataRuleModel> list2) {
        setAuthDatalist(list2);
        m11c(list, map);
        m14b(list);
        m39a(map);
        m15e();
        return this.sql.toString();
    }

    /* renamed from: a */
    public String m8a(List<OnlineFieldConfig> list, Map<String, Object> map, List<SysPermissionDataRuleModel> list2, String str) {
        setAuthDatalist(list2);
        this.paramPrefix = str;
        m11c(list, map);
        m14b(list);
        m15e();
        return this.sql.toString();
    }

    /* renamed from: b */
    public String m9b(List<OnlineFieldConfig> list, Map<String, Object> map) {
        m11c(list, map);
        return this.sql.toString();
    }

    /* renamed from: a */
    public String m10a(List<OnlineFieldConfig> list) {
        if (this.superQuery) {
            for (OnlineFieldConfig onlineFieldConfig : list) {
                String name = onlineFieldConfig.getName();
                String val = onlineFieldConfig.getVal();
                if (val != null) {
                    QueryRuleEnum byValue = QueryRuleEnum.getByValue(onlineFieldConfig.getRule());
                    if (byValue == null) {
                        byValue = QueryRuleEnum.EQ;
                    }
                    m17a(name, onlineFieldConfig.getType(), val, byValue);
                }
            }
        }
        return this.sql.toString();
    }

    /* renamed from: c */
    public void m11c(List<OnlineFieldConfig> list, Map<String, Object> map) {
        Object obj;
        for (OnlineFieldConfig onlineFieldConfig : list) {
            String name = onlineFieldConfig.getName();
            String type = onlineFieldConfig.getType();
            if (this.needList != null && this.needList.contains(name)) {
                onlineFieldConfig.setIsSearch(1);
                onlineFieldConfig.setMode("single");
            }
            if (ConvertUtils.isNotEmpty(onlineFieldConfig.getMainField()) && ConvertUtils.isNotEmpty(onlineFieldConfig.getMainTable())) {
                onlineFieldConfig.setIsSearch(1);
                onlineFieldConfig.setMode("single");
            }
            if (1 == onlineFieldConfig.getIsSearch().intValue()) {
                if ("time".equals(onlineFieldConfig.getView()) && CgReportConstant.f467K.equals(onlineFieldConfig.getMode())) {
                    onlineFieldConfig.setMode("single");
                }
                if (CgReportConstant.f467K.equals(onlineFieldConfig.getMode())) {
                    String str = name + "_begin";
                    Object obj2 = map.get(this.paramPrefix + str);
                    if (null != obj2) {
                        m23b(name, CgReportConstant.GE);
                        m21b(str, type, obj2);
                    }
                    String str2 = name + "_end";
                    Object obj3 = map.get(this.paramPrefix + str2);
                    if (null != obj3) {
                        m23b(name, CgReportConstant.LE);
                        m22a(str2, type, obj3, "end");
                    }
                } else {
                    Object obj4 = map.get(this.paramPrefix + name);
                    if (obj4 != null) {
                        String view = onlineFieldConfig.getView();
                        if ("list_multi".equals(view)) {
                            m37e(name, obj4);
                        } else if (CgformUtil.f218M.equals(view)) {
                            m38f(name, obj4);
                        } else {
                            m16a(name, type, obj4);
                        }
                    }
                }
            }
        }
        for (String str3 : map.keySet()) {
            if (str3.startsWith(CgReportConstant.POPUP_PARAM_PRE) && (obj = map.get(str3)) != null) {
                m16a(str3.replace(CgReportConstant.POPUP_PARAM_PRE, ""), "", obj);
            }
        }
    }

    public void setAuthList(List<SysPermissionDataRuleModel> authDatalist) {
        this.authDatalist = authDatalist;
    }

    /* renamed from: d */
    private void m12d() {
        SysPermissionDataRuleModel sysPermissionDataRuleModel;
//        List<SysPermissionDataRuleModel> loadDataSearchConditon = JeecgDataAutorUtils.loadDataSearchConditon();
        List<SysPermissionDataRuleModel> loadDataSearchConditon = Collections.emptyList();
        if (loadDataSearchConditon != null && loadDataSearchConditon.size() > 0) {
            for (int i = 0; i < loadDataSearchConditon.size() && (sysPermissionDataRuleModel = (SysPermissionDataRuleModel) loadDataSearchConditon.get(i)) != null; i++) {
                String ruleValue = sysPermissionDataRuleModel.getRuleValue();
                if (!ConvertUtils.isEmpty(ruleValue)) {
                    if (QueryRuleEnum.SQL_RULES.getValue().equals(sysPermissionDataRuleModel.getRuleConditions())) {
                        m23b("", QueryGenerator.getSqlRuleValue(ruleValue));
                    } else {
                        QueryRuleEnum byValue = QueryRuleEnum.getByValue(sysPermissionDataRuleModel.getRuleConditions());
                        String str = "Integer";
                        String trim = ruleValue.trim();
                        if (trim.startsWith(CgformUtil.SINGLE_QUOTE) && trim.endsWith(CgformUtil.SINGLE_QUOTE)) {
                            str = DataBaseConst.STRING;
                            trim = trim.substring(1, trim.length() - 1);
                        } else if (trim.startsWith("#{") && trim.endsWith("}")) {
                            str = DataBaseConst.STRING;
                        }
                        m17a(sysPermissionDataRuleModel.getRuleColumn(), str, QueryGenerator.converRuleValue(trim), byValue);
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private OnlineFieldConfig m13a(String str, List<OnlineFieldConfig> list) {
        if (list != null && str != null) {
            String camelToUnderline = ConvertUtils.camelToUnderline(str);
            for (int i = 0; i < list.size(); i++) {
                OnlineFieldConfig onlineFieldConfig = list.get(i);
                String name = onlineFieldConfig.getName();
                if (str.equals(name) || camelToUnderline.equals(name)) {
                    return onlineFieldConfig;
                }
            }
            return null;
        }
        return null;
    }

    /* renamed from: b */
    private void m14b(List<OnlineFieldConfig> list) {
        SysPermissionDataRuleModel sysPermissionDataRuleModel;
        List<SysPermissionDataRuleModel> list2 = this.authDatalist;
        if (list2 == null) {
            //todo 这里会报错噢
//            list2 = JeecgDataAutorUtils.loadDataSearchConditon();
        }
        if (list2 != null && list2.size() > 0) {
            for (int i = 0; i < list2.size() && (sysPermissionDataRuleModel = list2.get(i)) != null; i++) {
                String ruleValue = sysPermissionDataRuleModel.getRuleValue();
                if (!ConvertUtils.isEmpty(ruleValue)) {
                    if (QueryRuleEnum.SQL_RULES.getValue().equals(sysPermissionDataRuleModel.getRuleConditions())) {
                        m23b("", QueryGenerator.getSqlRuleValue(ruleValue));
                    } else {
                        OnlineFieldConfig m13a = m13a(sysPermissionDataRuleModel.getRuleColumn(), list);
                        if (m13a != null) {
                            m17a(m13a.getName(), m13a.getType(), QueryGenerator.converRuleValue(ruleValue), QueryRuleEnum.getByValue(sysPermissionDataRuleModel.getRuleConditions()));
                        }
                    }
                }
            }
        }
    }

    /* renamed from: e */
    private void m15e() {
        if (CgformUtil.m260j(CgformUtil.sanitizeTableName(this.tableName))) {
            m17a("tenant_id", "int", ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("X-Tenant-Id"), QueryRuleEnum.EQ);
        }
    }

    /* renamed from: a */
    private void m16a(String str, String str2, Object obj) {
        m17a(str, str2, obj, (QueryRuleEnum) null);
    }

    /* renamed from: a */
    private void m17a(String str, String str2, Object obj, QueryRuleEnum queryRuleEnum) {
        if (obj != null) {
            String obj2 = obj.toString();
            boolean z = false;
            if (queryRuleEnum == null) {
                z = true;
                queryRuleEnum = QueryGenerator.convert2Rule(obj);
            }
            if (z) {
                obj2 = obj2.trim();
            }
            switch (AnonymousClass1.f84a[queryRuleEnum.ordinal()]) {
                case 1:
                case 2:
                    m23b(str, queryRuleEnum.getValue());
                    if (z) {
                        obj2 = obj2.substring(1);
                    }
                    m21b(str, str2, (Object) obj2);
                    return;
                case 3:
                case 4:
                    m23b(str, queryRuleEnum.getValue());
                    if (z) {
                        obj2 = obj2.substring(2);
                    }
                    m21b(str, str2, (Object) obj2);
                    return;
                case 5:
                    m23b(str, queryRuleEnum.getValue());
                    m21b(str, str2, (Object) obj2);
                    return;
                case 6:
                    m23b(str, queryRuleEnum.getValue());
                    if (z) {
                        obj2 = obj2.replaceAll("\\+\\+", CgformUtil.COMMA_SEPARATOR);
                    }
                    m21b(str, str2, (Object) obj2);
                    return;
                case 7:
                    m23b(str, " <> ");
                    if (z) {
                        obj2 = obj2.substring(1);
                    }
                    m21b(str, str2, (Object) obj2);
                    return;
                case 8:
                    m23b(str, " in ");
                    m18a(str, str2, obj2);
                    return;
                case 9:
                case 10:
                case 11:
                    m23b(str, CgformUtil.f185f);
                    if (z) {
                        m19a(str, obj2);
                        return;
                    } else {
                        m20a(str, obj2, queryRuleEnum);
                        return;
                    }
                default:
                    m23b(str, CgReportConstant.EQUAL);
                    m21b(str, str2, (Object) obj2);
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ConditionHandler.java */
    /* renamed from: org.jeecg.modules.online.a.a$1, reason: invalid class name */
    /* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/a/a$1.class */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a */
        static final /* synthetic */ int[] f84a = new int[QueryRuleEnum.values().length];

        static {
            try {
                f84a[QueryRuleEnum.GT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f84a[QueryRuleEnum.LT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f84a[QueryRuleEnum.GE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f84a[QueryRuleEnum.LE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f84a[QueryRuleEnum.EQ.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f84a[QueryRuleEnum.EQ_WITH_ADD.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f84a[QueryRuleEnum.NE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f84a[QueryRuleEnum.IN.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f84a[QueryRuleEnum.LIKE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f84a[QueryRuleEnum.RIGHT_LIKE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f84a[QueryRuleEnum.LEFT_LIKE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    /* renamed from: a */
    private void m18a(String str, String str2, String str3) {
        String[] split = str3.split(CgformUtil.COMMA_SEPARATOR);
        if (split.length == 0) {
            m25a("('')");
            return;
        }
        String str4 = "";
        for (int i = 0; i < split.length; i++) {
            String trim = split[i].trim();
            String format = String.format("foreach_%s_%s", str, Integer.valueOf(i));
            if (i > 0) {
                str4 = str4 + ",";
            }
            String m40f = m40f(format);
            if (f61a.equals(this.daoType)) {
                str4 = str4 + ":" + m40f;
            } else {
                str4 = str4 + "#{" + m27b(m40f) + "}";
            }
            if ("Long".equals(str2) || "Integer".equals(str2)) {
                m28a(format, Integer.valueOf(Integer.parseInt(trim)));
            } else {
                m28a(format, (Object) trim);
            }
        }
        m25a("(" + str4 + ")");
    }

    /* renamed from: a */
    private void m19a(String str, String str2) {
        String str3;
        m25a(m26c(str, "VARCHAR"));
        if ((str2.startsWith("*") && str2.endsWith("*")) || (str2.startsWith("%") && str2.endsWith("%"))) {
            str3 = "%" + str2.substring(1, str2.length() - 1) + "%";
        } else if (str2.startsWith("*") || str2.startsWith("%")) {
            str3 = "%" + str2.substring(1);
        } else if (str2.endsWith("*") || str2.endsWith("%")) {
            str3 = str2.substring(0, str2.length() - 1) + "%";
        } else {
            str3 = "%" + str2 + "%";
        }
        m28a(str, (Object) str3);
    }

    /* renamed from: a */
    private void m20a(String str, String str2, QueryRuleEnum queryRuleEnum) {
        m25a(m26c(str, "VARCHAR"));
        if (queryRuleEnum == QueryRuleEnum.LEFT_LIKE) {
            m28a(str, (Object) ("%" + str2));
        } else if (queryRuleEnum == QueryRuleEnum.RIGHT_LIKE) {
            m28a(str, (Object) (str2 + "%"));
        } else {
            m28a(str, (Object) ("%" + str2 + "%"));
        }
    }

    /* renamed from: b */
    private void m21b(String str, String str2, Object obj) {
        m22a(str, str2, obj, (String) null);
    }

    /* renamed from: a */
    private void m22a(String str, String str2, Object obj, String str3) {
        String lowerCase = str2.toLowerCase();
        if (m33d(str2)) {
            if (CgformUtil.m251g(obj.toString())) {
                m25a(obj.toString());
                return;
            } else {
                m25a("''");
                f60d.info("请注意，查询条件值" + obj.toString() + "非数字！");
                return;
            }
        }
        if ("datetime".equals(lowerCase)) {
            String trim = obj.toString().trim();
            if (trim.length() <= 10) {
                if ("end".equals(str3)) {
                    trim = trim + " 23:59:59";
                } else {
                    trim = trim + " 00:00:00";
                }
            }
            m30b(str, DateUtils.str2Date(trim, (SimpleDateFormat) DateUtils.datetimeFormat.get()));
            return;
        }
        if (OnlFormShowType.DATE.equals(lowerCase)) {
            String trim2 = obj.toString().trim();
            if (trim2.length() > 10) {
                trim2 = trim2.substring(0, 10);
            }
            m31c(str, DateUtils.str2Date(trim2, (SimpleDateFormat) DateUtils.date_sdf.get()));
            return;
        }
        String trim3 = obj.toString().trim();
        if (trim3.startsWith(CgformUtil.SINGLE_QUOTE) && trim3.endsWith(CgformUtil.SINGLE_QUOTE) && this.usePage == 1) {
            m25a(trim3);
        } else {
            m32d(str, trim3);
        }
    }

    /* renamed from: b */
    private void m23b(String str, String str2) {
        m24b(str, str2, this.matchType);
    }

    /* renamed from: b */
    private void m24b(String str, String str2, String str3) {
        if (this.first) {
            this.first = false;
        } else {
            this.sql.append(str3);
        }
        if (str.length() > 0) {
            this.sql.append(this.alias).append(str).append(str2);
        } else {
            this.sql.append(" ").append(str2).append(" ");
        }
    }

    /* renamed from: a */
    private void m25a(String str) {
        this.sql.append(str);
    }

    /* renamed from: c */
    private String m26c(String str, String str2) {
        String m40f = m40f(str);
        if (f61a.equals(this.daoType)) {
            return ":" + m40f;
        }
        String m27b = m27b(m40f);
        if (str2 == null) {
            return String.format("#{%s}", m27b);
        }
        return String.format("#{%s, jdbcType=%s}", m27b, str2);
    }

    /* renamed from: b */
    private String m27b(String str) {
        return "param." + m29c(str);
    }

    /* renamed from: a */
    private void m28a(String str, Object obj) {
        this.sqlParams.put(m29c(m41g(str)), obj);
    }

    /* renamed from: c */
    private String m29c(String str) {
        if (this.usePage == 1) {
            return str;
        }
        return this.aliasNoPoint + "_" + str;
    }

    /* renamed from: b */
    private void m30b(String str, Object obj) {
        if (obj != null) {
            m25a(m26c(str, "TIMESTAMP"));
            m28a(str, obj);
        }
    }

    /* renamed from: c */
    private void m31c(String str, Object obj) {
        if (obj != null) {
            m25a(m26c(str, "DATE"));
            m28a(str, obj);
        }
    }

    /* renamed from: d */
    private void m32d(String str, Object obj) {
        if (obj != null) {
            m25a(m26c(str, (String) null));
            m28a(str, obj);
        }
    }

    /* renamed from: d */
    private boolean m33d(String str) {
        return "Long".equals(str) || "Integer".equals(str) || "int".equals(str) || "double".equals(str) || "BigDecimal".equals(str) || DataBaseConst.NUMBER.equals(str);
    }

    /* renamed from: e */
    private boolean m34e(String str) {
        return !"ORACLE".equals(str);
    }

    /* renamed from: a */
    public static String m35a(String str, long j) {
        return str.replaceFirst("\\?", String.valueOf(j));
    }

    /* renamed from: a */
    public static String m36a(String str, long j, long j2) {
        return str.replaceFirst("\\?", String.valueOf(j)).replaceFirst("\\?", String.valueOf(j2));
    }

    /* renamed from: e */
    private void m37e(String str, Object obj) {
        String str2;
        if (obj != null) {
            String[] split = obj.toString().split(CgformUtil.COMMA_SEPARATOR);
            String str3 = "";
            String str4 = this.alias + str;
            for (int i = 0; i < split.length; i++) {
                String str5 = str4 + " like '%" + split[i] + ",%' or " + str4 + " like '%," + split[i] + "%' or " + str4 + " = '" + split[i] + "'";
                if (str3.length() == 0) {
                    str2 = str5;
                } else {
                    str2 = str3 + " or " + str5;
                }
                str3 = str2;
            }
            if (str3.length() > 0) {
                m23b("", "(" + str3 + ")");
            }
        }
    }

    /* renamed from: f */
    private void m38f(String str, Object obj) {
        String str2;
        if (obj != null) {
            String str3 = this.alias + str;
            String str4 = "";
            String[] split = obj.toString().split(CgformUtil.COMMA_SEPARATOR);
            for (int i = 0; i < split.length; i++) {
                String format = String.format("popup_%s_%s", str, Integer.valueOf(i));
                String m26c = m26c(format, "VARCHAR");
                m28a(format, (Object) ("%" + split[i] + "%"));
                String str5 = str3 + " like " + m26c;
                if (str4.length() == 0) {
                    str2 = str5;
                } else {
                    str2 = str4 + " and " + str5;
                }
                str4 = str2;
            }
            if (str4.length() > 0) {
                m23b("", "(" + str4 + ")");
            }
        }
    }

    public String getSubTableStr() {
        return this.subTableStr;
    }

    /* renamed from: a */
    private void m39a(Map<String, Object> map) {
        MatchTypeEnum byValue = MatchTypeEnum.getByValue(map.get("superQueryMatchType"));
        if (byValue == null) {
            byValue = MatchTypeEnum.AND;
        }
        Object obj = map.get("superQueryParams");
        if (obj == null || StringUtils.isBlank(obj.toString())) {
            return;
        }
        try {
            JSONArray parseArray = JSONArray.parseArray(URLDecoder.decode(obj.toString(), "UTF-8"));
            IOnlCgformFieldService iOnlCgformFieldService = (IOnlCgformFieldService) SpringUtil.getBean(IOnlCgformFieldService.class);
            ArrayList arrayList = new ArrayList();
            arrayList.add("JEECG_SUPER_QUERY_MAIN_TABLE");
            if (this.subTableStr != null && !"".equals(this.subTableStr)) {
                for (String str : this.subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    arrayList.add(str);
                }
            }
            HashMap hashMap = new HashMap(5);
            StringBuffer stringBuffer = new StringBuffer();
            int i = 0;
            while (i < arrayList.size()) {
                String str2 = (String) arrayList.get(i);
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < parseArray.size(); i2++) {
                    JSONObject jSONObject = parseArray.getJSONObject(i2);
                    String string = jSONObject.getString("field");
                    if (!ConvertUtils.isEmpty(string)) {
                        String[] split = string.split(CgformUtil.COMMA_SEPARATOR);
                        OnlineFieldConfig onlineFieldConfig = new OnlineFieldConfig(jSONObject);
                        if ("JEECG_SUPER_QUERY_MAIN_TABLE".equals(str2) && split.length == 1) {
                            arrayList2.add(onlineFieldConfig);
                        } else if (split.length == 2 && split[0].equals(str2)) {
                            arrayList2.add(onlineFieldConfig);
                            if (((JSONObject) hashMap.get(str2)) == null) {
                                List<OnlCgformField> queryFormFieldsByTableName = iOnlCgformFieldService.queryFormFieldsByTableName(str2);
                                JSONObject jSONObject2 = new JSONObject();
                                for (OnlCgformField onlCgformField : queryFormFieldsByTableName) {
                                    if (StringUtils.isNotBlank(onlCgformField.getMainTable())) {
                                        jSONObject2.put("subTableName", str2);
                                        jSONObject2.put("subField", onlCgformField.getDbFieldName());
                                        jSONObject2.put("mainTable", onlCgformField.getMainTable());
                                        jSONObject2.put("mainField", onlCgformField.getMainField());
                                    }
                                }
                                hashMap.put(str2, jSONObject2);
                            }
                        }
                    }
                }
                if (arrayList2.size() > 0) {
                    ConditionHandler conditionHandler = new ConditionHandler(i == 0 ? this.alias : this.aliasNoPoint + i + ".", true, byValue.getValue());
                    conditionHandler.setDuplicateParamNameRecord(getDuplicateParamNameRecord());
                    conditionHandler.setDuplicateSqlNameRecord(getDuplicateSqlNameRecord());
                    String m10a = conditionHandler.m10a((List<OnlineFieldConfig>) arrayList2);
                    Map<String, Object> sqlParams = conditionHandler.getSqlParams();
                    if (m10a != null && m10a.length() > 0) {
                        if (i == 0) {
                            stringBuffer.append(" ").append(m10a).append(" ");
                            this.sqlParams.putAll(sqlParams);
                        } else {
                            JSONObject jSONObject3 = (JSONObject) hashMap.get(str2);
                            String format = String.format(" %s in (select %s from %s %s where ", jSONObject3.getString("mainField"), jSONObject3.getString("subField"), jSONObject3.getString("subTableName"), this.aliasNoPoint + i);
                            this.sqlParams.putAll(sqlParams);
                            stringBuffer.append(byValue.getValue()).append(format).append(m10a).append(") ");
                        }
                    }
                }
                i++;
            }
            String stringBuffer2 = stringBuffer.toString();
            if (stringBuffer2.length() > 0) {
                if (stringBuffer2.startsWith("AND ")) {
                    stringBuffer2 = stringBuffer2.substring(3);
                } else if (stringBuffer2.startsWith("OR ")) {
                    stringBuffer2 = stringBuffer2.substring(2);
                }
                m23b("", "(" + stringBuffer2 + ")");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: f */
    private String m40f(String str) {
        return m42a(str, this.duplicateSqlNameRecord);
    }

    /* renamed from: g */
    private String m41g(String str) {
        return m42a(str, this.duplicateParamNameRecord);
    }

    /* renamed from: a */
    private String m42a(String str, Map<String, String> map) {
        String str2 = map.get(str);
        if (str2 == null) {
            str2 = str;
            map.put(str, str + "_1");
        } else {
            map.put(str, str + "_" + (Integer.parseInt(str2.substring(str2.lastIndexOf("_") + 1)) + 1));
        }
        return str2;
    }
}
