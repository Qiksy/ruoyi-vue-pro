package org.jeecg.modules.online.cgform.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.MatchTypeEnum;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.common.system.vo.SysUserCacheInfo;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.util.online.CommonProperty;
import org.jeecg.common.util.online.BaseColumn;
import org.jeecg.common.util.online.JsonSchemaDescrip;
import org.jeecg.common.util.online.JsonschemaUtil;
import org.jeecg.common.util.online.property.DictProperty;
import org.jeecg.common.util.online.property.HiddenProperty;
import org.jeecg.common.util.online.property.LinkDownProperty;
import org.jeecg.common.util.online.property.NumberProperty;
import org.jeecg.common.util.online.property.PopupProperty;
import org.jeecg.common.util.online.property.StringProperty;
import org.jeecg.common.util.online.property.SwitchProperty;
import org.jeecg.common.util.online.property.TreeSelectProperty;
import org.jeecg.config.mybatis.MybatisPlusSaasConfig;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.enums.CgformValidPatternEnum;
import org.jeecg.modules.online.cgform.enums.CgformConstant;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.model.TreeSelectColumn;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.constant.OnlineConst;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.database.OnlineFieldConfig;
import org.jeecg.modules.online.config.template.DataBaseConst;
import org.jeecg.modules.online.config.template.DbTableUtil;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: CgformUtil.java */
/* renamed from: org.jeecg.modules.online.cgform.d.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/d/b.class */
public class CgformUtil {

    /* renamed from: a */
    public static final String f180a = "SELECT ";

    /* renamed from: b */
    public static final String f181b = " FROM ";

    /* renamed from: c */
    public static final String f182c = " JOIN ";

    /* renamed from: d */
    public static final String f183d = " ON ";

    /* renamed from: e */
    public static final String f184e = " AND ";

    /* renamed from: f */
    public static final String f185f = " like ";

    /* renamed from: g */
    public static final String f186g = " COUNT(*) ";

    /* renamed from: h */
    public static final String f187h = " where 1=1  ";

    /* renamed from: i */
    public static final String f188i = " where  ";

    /* renamed from: j */
    public static final String f189j = " ORDER BY ";

    /* renamed from: k */
    public static final String f190k = "asc";

    /* renamed from: l */
    public static final String f191l = "desc";

    /* renamed from: m */
    public static final String f192m = "=";

    /* renamed from: n */
    public static final String f193n = "!=";

    /* renamed from: o */
    public static final String f194o = ">=";

    /* renamed from: p */
    public static final String f195p = ">";

    /* renamed from: q */
    public static final String f196q = "<=";

    /* renamed from: r */
    public static final String f197r = "<";

    /* renamed from: s */
    public static final String f198s = " or ";

    /* renamed from: t */
    public static final String f199t = "jeecg_row_key";

    /* renamed from: u */
    public static final String f200u = "Y";

    /* renamed from: v */
    public static final String f201v = "$";

    /* renamed from: w */
    public static final String f202w = "CREATE_TIME";

    /* renamed from: x */
    public static final String f203x = "CREATE_BY";

    /* renamed from: y */
    public static final String f204y = "UPDATE_TIME";

    /* renamed from: z */
    public static final String f205z = "UPDATE_BY";

    /* renamed from: A */
    public static final String f206A = "SYS_ORG_CODE";

    /* renamed from: B */
    public static final int f207B = 2;

    /* renamed from: C */
    public static final String SINGLE_QUOTE = "'";

    /* renamed from: D */
    public static final String f209D = "N";

    /* renamed from: E */
    public static final String COMMA_SEPARATOR = ",";

    /* renamed from: F */
    public static final String f211F = "single";

    /* renamed from: G */
    public static final String f212G = "id";

    /* renamed from: H */
    public static final String f213H = "bpm_status";

    /* renamed from: I */
    public static final String f214I = "1";

    /**
     * 先删除表，再创建表
     */
    /* renamed from: J */
    public static final String SYNC_TYPE_FORCE = "force";

    /**
     * 刷新表的方式。修改表
     */
    /* renamed from: K */
    public static final String SYNC_TYPE_NORMAL = "normal";

    /* renamed from: L */
    public static final String f217L = "switch";

    /* renamed from: M */
    public static final String f218M = "popup";

    /* renamed from: N */
    public static final String f219N = "sel_search";

    /* renamed from: O */
    public static final String f220O = "image";

    /* renamed from: P */
    public static final String f221P = "file";

    /* renamed from: Q */
    public static final String f222Q = "sel_tree";

    /* renamed from: R */
    public static final String f223R = "cat_tree";

    /* renamed from: S */
    public static final String f224S = "link_down";

    /* renamed from: T */
    public static final String SYS_USER = "SYS_USER";

    /* renamed from: U */
    public static final String REALNAME = "REALNAME";

    /* renamed from: V */
    public static final String USERNAME = "USERNAME";

    /* renamed from: W */
    public static final String SYS_DEPART = "SYS_DEPART";

    /* renamed from: X */
    public static final String DEPART_NAME = "DEPART_NAME";

    /* renamed from: Y */
    public static final String f230Y = "ID";

    /* renamed from: Z */
    public static final String f231Z = "SYS_CATEGORY";

    /* renamed from: aa */
    public static final String f232aa = "NAME";

    /* renamed from: ab */
    public static final String f233ab = "CODE";

    /* renamed from: ac */
    public static final String f234ac = "ID";

    /* renamed from: ad */
    public static final String f235ad = "PID";

    /* renamed from: ae */
    public static final String f236ae = "HAS_CHILD";

    /* renamed from: af */
    public static final String f237af = "sel_search";

    /* renamed from: ag */
    public static final String f238ag = "link_table";

    /* renamed from: ah */
    public static final String f239ah = "link_table_field";

    /* renamed from: ai */
    public static final String f240ai = "sub-table-design_";

    /* renamed from: aj */
    public static final String f241aj = "sub-table-one2one_";

    /* renamed from: ak */
    public static final String f242ak = "import";

    /* renamed from: al */
    public static final String f243al = "export";

    /* renamed from: am */
    public static final String f244am = "query";

    /* renamed from: an */
    public static final String FORM = "form";

    /* renamed from: ao */
    public static final String LIST = "list";

    /* renamed from: ap */
    public static final String f247ap = "1";

    /* renamed from: aq */
    public static final String f248aq = "start";

    /* renamed from: ar */
    public static final String ERP = "erp";

    /* renamed from: as */
    public static final String f250as = "innerTable";

    /* renamed from: at */
    public static final String f251at = "exportSingleOnly";

    /* renamed from: au */
    public static final String f252au = "isSingleTableImport";

    /* renamed from: av */
    public static final String f253av = "validateStatus";

    /* renamed from: aw */
    public static final String f254aw = "1";

    /* renamed from: ax */
    public static final String f255ax = "foreignKeys";

    /* renamed from: ay */
    public static final int f256ay = 1;

    /* renamed from: az */
    public static final int f257az = 2;

    /* renamed from: aA */
    public static final int f258aA = 0;

    /* renamed from: aB */
    public static final int f259aB = 1;

    /* renamed from: aC */
    public static final int f260aC = 1;

    /* renamed from: aD */
    public static final String f261aD = "1";

    /* renamed from: aF */
    public static final String f263aF = "1";

    /* renamed from: aG */
    public static final String f264aG = "id";

    /* renamed from: aH */
    public static final String CENTER = "center";

    /* renamed from: aI */
    public static final String f266aI = "modules/bpm/task/form/OnlineFormDetail";

    /* renamed from: aJ */
    public static final String f267aJ = "check/onlineForm/detail";

    /* renamed from: aK */
    public static final String f268aK = "onl_";

    /* renamed from: aL */
    public static final String f269aL = "jeecg_submit_form_and_flow";

    /* renamed from: aM */
    public static final String JOIN_QUERY = "joinQuery";

    /* renamed from: aN */
    public static final String PROPERTIES = "properties";

    /* renamed from: aO */
    public static final String TITLE = "title";

    /* renamed from: aP */
    public static final String VIEW = "view";

    /* renamed from: aQ */
    public static final String TABLE = "table";

    /* renamed from: aR */
    public static final String SEARCH_FIELD_LIST = "searchFieldList";

    /* renamed from: aT */
    private static final String f276aT = "beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created";

    /* renamed from: aU */
    private static String f277aU;

    /* renamed from: aS */
    private static final Logger f179aS = LoggerFactory.getLogger(CgformUtil.class);

    /* renamed from: aE */
    public static final Integer f262aE = 2;

    /* renamed from: a */
    public static boolean m181a(OnlCgformHead onlCgformHead) {
        String extConfigJson;
        if (onlCgformHead != null && f262aE.equals(onlCgformHead.getTableType())) {
            String themeTemplate = onlCgformHead.getThemeTemplate();
            if (!ERP.equals(themeTemplate) && !f250as.equals(themeTemplate) && !"Y".equals(onlCgformHead.getIsTree()) && (extConfigJson = onlCgformHead.getExtConfigJson()) != null && !"".equals(extConfigJson)) {
                JSONObject parseObject = JSON.parseObject(extConfigJson);
                if (parseObject.containsKey(JOIN_QUERY) && 1 == parseObject.getInteger(JOIN_QUERY).intValue()) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    /* renamed from: a */
    public static void m182a(String str, List<OnlCgformField> list, StringBuffer stringBuffer) {
        if (list == null || list.size() == 0) {
            stringBuffer.append("SELECT id");
        } else {
            stringBuffer.append(f180a);
            int size = list.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                OnlCgformField onlCgformField = list.get(i);
                onlCgformField.setDbFieldName(SqlInjectionUtil.getSqlInjectField(onlCgformField.getDbFieldName()));
                if (OnlineConst.isPersist.equals(onlCgformField.getDbIsPersist())) {
                    if ("id".equals(onlCgformField.getDbFieldName())) {
                        z = true;
                    }
                    if (f223R.equals(onlCgformField.getFieldShowType()) && oConvertUtils.isNotEmpty(onlCgformField.getDictText())) {
                        stringBuffer.append(onlCgformField.getDictText() + ",");
                    }
                    if (i == size - 1) {
                        stringBuffer.append(onlCgformField.getDbFieldName() + " ");
                    } else {
                        stringBuffer.append(onlCgformField.getDbFieldName() + ",");
                    }
                }
            }
            if (COMMA_SEPARATOR.equals(stringBuffer.substring(stringBuffer.length() - 1))) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            if (!z) {
                stringBuffer.append(",id");
            }
        }
        stringBuffer.append(" FROM " + m235f(str));
    }

    /* renamed from: a */
    public static String m183a(String str) {
        return " to_date('" + str + "','yyyy-MM-dd HH24:mi:ss')";
    }

    /* renamed from: b */
    public static String m184b(String str) {
        return " to_date('" + str + "','yyyy-MM-dd')";
    }

    /**
     * 如果是列表，或者是选择框之类的，返回true
     * @param str
     * @return
     */
    /* renamed from: c */
    public static boolean m185c(String str) {
        if (LIST.equals(str) || "radio".equals(str) || "checkbox".equals(str) || "list_multi".equals(str)) {
            return true;
        }
        return false;
    }

    /* renamed from: a */
    public static boolean m186a(OnlCgformField onlCgformField) {
        if (oConvertUtils.isNotEmpty(onlCgformField.getMainField()) && oConvertUtils.isNotEmpty(onlCgformField.getMainTable())) {
            String fieldExtendJson = onlCgformField.getFieldExtendJson();
            if (oConvertUtils.isNotEmpty(fieldExtendJson) && fieldExtendJson.indexOf(ExtendJsonKey.TEXT_FIELD) > 0) {
                onlCgformField.setDictTable(onlCgformField.getMainTable());
                onlCgformField.setDictField(onlCgformField.getMainField());
                onlCgformField.setFieldShowType("sel_search");
                onlCgformField.setDictText(JSON.parseObject(fieldExtendJson).getString(ExtendJsonKey.TEXT_FIELD));
                return true;
            }
            return false;
        }
        return false;
    }

    /* renamed from: a */
    public static void m187a(StringBuilder sb, String str, JSONObject jSONObject, MatchTypeEnum matchTypeEnum, JSONObject jSONObject2, boolean z) {
        if (!z) {
            sb.append(" ").append(matchTypeEnum.getValue()).append(" ");
        }
        String string = jSONObject.getString("type");
        String string2 = jSONObject.getString("val");
        String m189b = m189b(string, string2);
        QueryRuleEnum byValue = QueryRuleEnum.getByValue(jSONObject.getString("rule"));
        if (byValue == null) {
            byValue = QueryRuleEnum.EQ;
        }
        if (jSONObject2 != null) {
            String string3 = jSONObject2.getString("subTableName");
            String string4 = jSONObject2.getString("subField");
            jSONObject2.getString("mainTable");
            sb.append("(").append(jSONObject2.getString("mainField")).append(" IN (SELECT ").append(string4).append(f181b).append(string3).append(" WHERE ");
            if (f218M.equals(string)) {
                sb.append(m245c(str, string2));
            } else {
                sb.append(str);
                m188a(sb, byValue, string2, m189b, string);
            }
            sb.append("))");
            return;
        }
        if (f218M.equals(string)) {
            sb.append(m245c(str, string2));
        } else {
            sb.append(str);
            m188a(sb, byValue, string2, m189b, string);
        }
    }

    /* renamed from: a */
    private static void m188a(StringBuilder sb, QueryRuleEnum queryRuleEnum, String str, String str2, String str3) {
        if (OnlFormShowType.DATE.equals(str3) && "ORACLE".equalsIgnoreCase(getDatabseType())) {
            String replace = str2.replace(SINGLE_QUOTE, "");
            if (replace.length() == 10) {
                str2 = m184b(replace);
            } else {
                str2 = m183a(replace);
            }
        }
        switch (AnonymousClass3.f278a[queryRuleEnum.ordinal()]) {
            case 1:
                sb.append(f195p).append(str2);
                return;
            case 2:
                sb.append(f194o).append(str2);
                return;
            case 3:
                sb.append(f197r).append(str2);
                return;
            case 4:
                sb.append(f196q).append(str2);
                return;
            case 5:
                sb.append(f193n).append(str2);
                return;
            case 6:
                sb.append(" IN (");
                String[] split = str.split(COMMA_SEPARATOR);
                for (int i = 0; i < split.length; i++) {
                    String str4 = split[i];
                    if (StringUtils.isNotBlank(str4)) {
                        sb.append(m189b(str3, str4));
                        if (i < split.length - 1) {
                            sb.append(COMMA_SEPARATOR);
                        }
                    }
                }
                sb.append(")");
                return;
            case 7:
                sb.append(f185f).append("N").append(SINGLE_QUOTE).append("%").append(str).append("%").append(SINGLE_QUOTE);
                return;
            case 8:
                sb.append(f185f).append("N").append(SINGLE_QUOTE).append("%").append(str).append(SINGLE_QUOTE);
                return;
            case 9:
                sb.append(f185f).append("N").append(SINGLE_QUOTE).append(str).append("%").append(SINGLE_QUOTE);
                return;
            case 10:
            default:
                sb.append(f192m).append(str2);
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CgformUtil.java */
    /* renamed from: org.jeecg.modules.online.cgform.d.b$3, reason: invalid class name */
    /* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/d/b$3.class */
    public static /* synthetic */ class AnonymousClass3 {

        /* renamed from: a */
        static final /* synthetic */ int[] f278a = new int[QueryRuleEnum.values().length];

        static {
            try {
                f278a[QueryRuleEnum.GT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f278a[QueryRuleEnum.GE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f278a[QueryRuleEnum.LT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f278a[QueryRuleEnum.LE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f278a[QueryRuleEnum.NE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f278a[QueryRuleEnum.IN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f278a[QueryRuleEnum.LIKE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f278a[QueryRuleEnum.LEFT_LIKE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f278a[QueryRuleEnum.RIGHT_LIKE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f278a[QueryRuleEnum.EQ.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    /* renamed from: b */
    private static String m189b(String str, String str2) {
        if ("int".equals(str) || DataBaseConst.NUMBER.equals(str)) {
            return str2;
        }
        if (OnlFormShowType.DATE.equals(str)) {
            return "'" + str2 + "'";
        }
        if ("SQLSERVER".equals(getDatabseType())) {
            return "N'" + str2 + "'";
        }
        return "'" + str2 + "'";
    }

    /* renamed from: a */
    public static Map<String, Object> m190a(HttpServletRequest httpServletRequest) {
        String str;
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        HashMap hashMap = new HashMap(5);
        String str2 = "";
        for (Map.Entry entry : parameterMap.entrySet()) {
            String str3 = (String) entry.getKey();
            Object value = entry.getValue();
            if ("_t".equals(str3) || null == value) {
                str = "";
            } else if (value instanceof String[]) {
                for (String str4 : (String[]) value) {
                    str2 = str4 + ",";
                }
                str = str2.substring(0, str2.length() - 1);
            } else {
                str = value.toString();
            }
            str2 = str;
            hashMap.put(str3, str2);
        }
        return hashMap;
    }

    /* renamed from: a */
    public static boolean m191a(String str, List<OnlCgformField> list) {
        Iterator<OnlCgformField> it = list.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().getDbFieldName())) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    public static JSONObject m192a(List<OnlCgformField> list, List<String> list2, TreeSelectColumn treeSelectColumn) {
        JSONObject m2a;
//        C0001a c0001a;
        CommonProperty c0001a;
        new JSONObject();
        ArrayList arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList<>();
        OnlCgformHeadMapper onlCgformHeadMapper = SpringContextUtils.getBean(OnlCgformHeadMapper.class);
        ArrayList arrayList3 = new ArrayList<>();
        for (OnlCgformField onlCgformField : list) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (!"id".equals(dbFieldName) && !arrayList3.contains(dbFieldName)) {
                String dbFieldTxt = onlCgformField.getDbFieldTxt();
                if ("1".equals(onlCgformField.getFieldMustInput())) {
                    arrayList.add(dbFieldName);
                }
                String fieldShowType = onlCgformField.getFieldShowType();
                if (f217L.equals(fieldShowType)) {
                    c0001a = new SwitchProperty(dbFieldName, dbFieldTxt, onlCgformField.getFieldExtendJson());
                } else if (m185c(fieldShowType) || f239ah.equals(fieldShowType)) {
                    c0001a = new DictProperty(dbFieldName, fieldShowType, dbFieldTxt, onlCgformField.getDictTable(), onlCgformField.getDictField(), onlCgformField.getDictText());
                    if (OnlineDbHandler.m278a(onlCgformField.getDbType())) {
                        c0001a.setType(DataBaseConst.NUMBER);
                    }
                } else if ("sel_search".equals(fieldShowType) || m186a(onlCgformField)) {
                    c0001a = new DictProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable(), onlCgformField.getDictField(), onlCgformField.getDictText());
                } else if (f238ag.equals(fieldShowType)) {
                    c0001a = new DictProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable(), onlCgformField.getDictField(), onlCgformField.getDictText());
                    c0001a.setView(f238ag);
                } else if (OnlineDbHandler.m278a(onlCgformField.getDbType())) {
                    var c0004d = new NumberProperty(dbFieldName, dbFieldTxt, DataBaseConst.NUMBER);
                    if (CgformValidPatternEnum.INTEGER.getType().equals(onlCgformField.getFieldValidType())) {
                        c0004d.setPattern(CgformValidPatternEnum.INTEGER.getPattern());
                    }
                    c0001a = c0004d;
                } else if (f218M.equals(fieldShowType)) {
                    var c0005e = new PopupProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable(), onlCgformField.getDictText(), onlCgformField.getDictField());
                    String dictText = onlCgformField.getDictText();
                    if (dictText != null && !"".equals(dictText)) {
                        for (String str : dictText.split(COMMA_SEPARATOR)) {
                            if (!m191a(str, list)) {
                                HiddenProperty hiddenProperty = new HiddenProperty(str, str);
                                hiddenProperty.setOrder(onlCgformField.getOrderNum());
                                arrayList2.add(hiddenProperty);
                            }
                        }
                    }
                    String fieldExtendJson = onlCgformField.getFieldExtendJson();
                    if (fieldExtendJson != null && !"".equals(fieldExtendJson)) {
                        JSONObject parseObject = JSONObject.parseObject(fieldExtendJson);
                        if (parseObject.containsKey(ExtendJsonKey.POPUP_MULTI)) {
                            c0005e.setPopupMulti(parseObject.getBoolean(ExtendJsonKey.POPUP_MULTI));
                        }
                    }
                    c0001a = c0005e;
                } else if (f224S.equals(fieldShowType)) {
                    var c0003c = new LinkDownProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable());
                    m236a((LinkDownProperty) c0003c, list, arrayList3);
                    c0001a = c0003c;
                } else if (f222Q.equals(fieldShowType)) {
                    String[] split = onlCgformField.getDictText().split(COMMA_SEPARATOR);
                    var c0008h = new TreeSelectProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable() + "," + split[2] + "," + split[0], split[1], onlCgformField.getDictField());
                    if (split.length > 3) {
                        c0008h.setHasChildField(split[3]);
                    }
                    c0001a = c0008h;
                } else if (f223R.equals(fieldShowType)) {
                    String dictText2 = onlCgformField.getDictText();
                    String dictField = onlCgformField.getDictField();
                    String str2 = "0";
                    if (oConvertUtils.isNotEmpty(dictField) && !"0".equals(dictField)) {
                        str2 = onlCgformHeadMapper.queryCategoryIdByCode(dictField);
                    }
                    if (oConvertUtils.isEmpty(dictText2)) {
                        c0001a = new TreeSelectProperty(dbFieldName, dbFieldTxt, str2);
                    } else {
                        c0001a = new TreeSelectProperty(dbFieldName, dbFieldTxt, str2, dictText2);
                        arrayList2.add(new HiddenProperty(dictText2, dictText2));
                    }
                } else if (treeSelectColumn != null && dbFieldName.equals(treeSelectColumn.getFieldName())) {
                    var c0008h2 = new TreeSelectProperty(dbFieldName, dbFieldTxt, treeSelectColumn.getTableName() + "," + treeSelectColumn.getTextField() + "," + treeSelectColumn.getCodeField(), treeSelectColumn.getPidField(), treeSelectColumn.getPidValue());
                    c0008h2.setHasChildField(treeSelectColumn.getHsaChildField());
                    c0008h2.setPidComponent(1);
                    c0001a = c0008h2;
                } else {
                    var c0006f = new StringProperty(dbFieldName, dbFieldTxt, fieldShowType, onlCgformField.getDbLength());
                    if (oConvertUtils.isNotEmpty(onlCgformField.getFieldValidType())) {
                        CgformValidPatternEnum patternInfoByType = CgformValidPatternEnum.getPatternInfoByType(onlCgformField.getFieldValidType());
                        String m193a = m193a(ExtendJsonKey.VALIDATE_ERROR, onlCgformField.getFieldExtendJson());
                        if (patternInfoByType != null) {
                            if (CgformValidPatternEnum.NOTNULL == patternInfoByType) {
                                arrayList.add(dbFieldName);
                            } else {
                                c0006f.setPattern(patternInfoByType.getPattern());
                                if (oConvertUtils.isEmpty(m193a)) {
                                    c0006f.setErrorInfo(patternInfoByType.getMsg());
                                } else {
                                    c0006f.setErrorInfo(m193a);
                                }
                            }
                        } else {
                            c0006f.setPattern(onlCgformField.getFieldValidType());
                            if (oConvertUtils.isEmpty(m193a)) {
                                c0006f.setErrorInfo("输入的值不合法");
                            } else {
                                c0006f.setErrorInfo(m193a);
                            }
                        }
                    }
                    c0001a = c0006f;
                }
                if (onlCgformField.getIsReadOnly().intValue() == 1 || (list2 != null && list2.indexOf(dbFieldName) >= 0)) {
                    (c0001a).setDisabled(true);
                }
                (c0001a).setOrder(onlCgformField.getOrderNum());
                (c0001a).setDefVal(onlCgformField.getFieldDefaultValue());
                (c0001a).setFieldExtendJson(onlCgformField.getFieldExtendJson());
                (c0001a).setDbPointLength(onlCgformField.getDbPointLength());
                (c0001a).setMode(onlCgformField.getQueryMode());
                arrayList2.add(c0001a);
            }
        }
        if (arrayList.size() > 0) {
            m2a = JsonschemaUtil.m2a(new JsonSchemaDescrip(arrayList), arrayList2);
        } else {
            m2a = JsonschemaUtil.m2a(new JsonSchemaDescrip(), arrayList2);
        }
        return m2a;
    }

    /* renamed from: a */
    public static String m193a(String str, String str2) {
        String str3 = "";
        if (str2 != null && !"".equals(str2)) {
            JSONObject parseObject = JSONObject.parseObject(str2);
            if (parseObject.containsKey(str)) {
                str3 = parseObject.getString(str);
            }
        }
        return str3;
    }

    /* renamed from: b */
    public static JSONObject m194b(String str, List<OnlCgformField> list) {
        CommonProperty c0006f;
        new JSONObject();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ISysBaseAPI iSysBaseAPI = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
        for (OnlCgformField onlCgformField : list) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (!"id".equals(dbFieldName)) {
                String dbFieldTxt = onlCgformField.getDbFieldTxt();
                if ("1".equals(onlCgformField.getFieldMustInput())) {
                    arrayList.add(dbFieldName);
                }
                String fieldShowType = onlCgformField.getFieldShowType();
                String dictField = onlCgformField.getDictField();
                if (OnlineDbHandler.m278a(onlCgformField.getDbType())) {
                    c0006f = new NumberProperty(dbFieldName, dbFieldTxt, DataBaseConst.NUMBER);
                } else if (m185c(fieldShowType)) {
                    c0006f = new StringProperty(dbFieldName, dbFieldTxt, fieldShowType, onlCgformField.getDbLength(), iSysBaseAPI.queryDictItemsByCode(dictField));
                } else {
                    c0006f = new StringProperty(dbFieldName, dbFieldTxt, fieldShowType, onlCgformField.getDbLength());
                }
                CommonProperty commonProperty = c0006f;
                commonProperty.setOrder(onlCgformField.getOrderNum());
                arrayList2.add(commonProperty);
            }
        }
        return JsonschemaUtil.m3a(str, arrayList, arrayList2);
    }

    /* renamed from: a */
    public static Set<String> m195a(List<OnlCgformField> list) {
        String dictText;
        HashSet hashSet = new HashSet();
        for (OnlCgformField onlCgformField : list) {
            if (f218M.equals(onlCgformField.getFieldShowType()) && (dictText = onlCgformField.getDictText()) != null && !"".equals(dictText)) {
                hashSet.addAll((Collection) Arrays.stream(dictText.split(COMMA_SEPARATOR)).collect(Collectors.toSet()));
            }
            if (f223R.equals(onlCgformField.getFieldShowType())) {
                String dictText2 = onlCgformField.getDictText();
                if (oConvertUtils.isNotEmpty(dictText2)) {
                    hashSet.add(dictText2);
                }
            }
        }
        for (OnlCgformField onlCgformField2 : list) {
            String dbFieldName = onlCgformField2.getDbFieldName();
            if (onlCgformField2.getIsShowForm().intValue() == 1 && hashSet.contains(dbFieldName)) {
                hashSet.remove(dbFieldName);
            }
        }
        return hashSet;
    }

    /* renamed from: a */
    public static Map<String, Object> m196a(String str, List<OnlCgformField> list, JSONObject jSONObject) {
        String dbFieldName;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        String str2 = "";
        try {
            str2 = DbTableUtil.getDatabaseType();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DBException e2) {
            e2.printStackTrace();
        }
        HashMap hashMap = new HashMap(5);
        boolean z = false;
        String str3 = null;
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (loginUser == null) {
            throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
        }
        Set<String> m195a = m195a(list);
        String m235f = m235f(str);
        boolean m260j = m260j(m235f);
        for (OnlCgformField onlCgformField : list) {
            if (OnlineConst.isPersist.equals(onlCgformField.getDbIsPersist()) && null != (dbFieldName = onlCgformField.getDbFieldName())) {
                if ("id".equals(dbFieldName.toLowerCase())) {
                    z = true;
                    str3 = jSONObject.getString(dbFieldName);
                } else if (!m260j || !"tenant_id".equalsIgnoreCase(dbFieldName)) {
                    m200a(onlCgformField, loginUser, jSONObject, f203x, f202w, f206A);
                    if (f213H.equals(dbFieldName.toLowerCase())) {
                        stringBuffer.append("," + dbFieldName);
                        stringBuffer2.append(",'1'");
                    } else if (m195a.contains(dbFieldName)) {
                        stringBuffer.append("," + dbFieldName);
                        stringBuffer2.append("," + OnlineDbHandler.m280a(str2, onlCgformField, jSONObject, hashMap));
                    } else if (onlCgformField.getIsShowForm().intValue() == 1 || !oConvertUtils.isEmpty(onlCgformField.getMainField()) || !oConvertUtils.isEmpty(onlCgformField.getDbDefaultVal())) {
                        if (oConvertUtils.isEmpty(jSONObject.get(dbFieldName))) {
                            if (!oConvertUtils.isEmpty(onlCgformField.getDbDefaultVal())) {
                                jSONObject.put(dbFieldName, onlCgformField.getDbDefaultVal());
                            }
                        }
                        if ("".equals(jSONObject.get(dbFieldName))) {
                            String dbType = onlCgformField.getDbType();
                            if (!OnlineDbHandler.m278a(dbType) && !OnlineDbHandler.m279b(dbType)) {
                            }
                        }
                        stringBuffer.append("," + dbFieldName);
                        stringBuffer2.append("," + OnlineDbHandler.m280a(str2, onlCgformField, jSONObject, hashMap));
                    }
                }
            }
        }
        if (z) {
            if (oConvertUtils.isEmpty(str3)) {
                str3 = nextId();
            }
        } else {
            str3 = nextId();
        }
        if (m260j) {
            stringBuffer.append("," + "tenant_id");
            stringBuffer2.append(",#{" + "tenant_id" + "}");
            hashMap.put("tenant_id", SpringContextUtils.getHttpServletRequest().getHeader("X-Tenant-Id"));
        }
        hashMap.put("execute_sql_string", "insert into " + m235f + "(id" + stringBuffer.toString() + ") values(#{id,jdbcType=VARCHAR}" + stringBuffer2.toString() + ")");
        hashMap.put("id", str3);
        return hashMap;
    }

    /* renamed from: b */
    public static Map<String, Object> m197b(String str, List<OnlCgformField> list, JSONObject jSONObject) {
        String dbFieldName;
        StringBuffer stringBuffer = new StringBuffer();
        HashMap hashMap = new HashMap(5);
        String str2 = "";
        try {
            str2 = DbTableUtil.getDatabaseType();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DBException e2) {
            e2.printStackTrace();
        }
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (loginUser == null) {
            throw new JeecgBootException("online修改表单数据异常:系统未找到当前登陆用户信息");
        }
        Set<String> m195a = m195a(list);
        for (OnlCgformField onlCgformField : list) {
            if (OnlineConst.isPersist.equals(onlCgformField.getDbIsPersist()) && null != (dbFieldName = onlCgformField.getDbFieldName())) {
                m200a(onlCgformField, loginUser, jSONObject, f205z, f204y);
                if (m195a.contains(dbFieldName) && jSONObject.get(dbFieldName) != null && !"".equals(jSONObject.getString(dbFieldName))) {
                    stringBuffer.append(dbFieldName + "=" + OnlineDbHandler.m280a(str2, onlCgformField, jSONObject, hashMap) + ",");
                } else if (onlCgformField.getIsShowForm().intValue() == 1 && !"id".equals(dbFieldName)) {
                    if ("".equals(jSONObject.get(dbFieldName))) {
                        String dbType = onlCgformField.getDbType();
                        if (!OnlineDbHandler.m278a(dbType) && !OnlineDbHandler.m279b(dbType)) {
                        }
                    }
                    if (!oConvertUtils.isNotEmpty(onlCgformField.getMainTable()) || !oConvertUtils.isNotEmpty(onlCgformField.getMainField()) || !oConvertUtils.isEmpty(jSONObject.get(dbFieldName))) {
                        stringBuffer.append(dbFieldName + "=" + OnlineDbHandler.m280a(str2, onlCgformField, jSONObject, hashMap) + ",");
                    }
                }
            }
        }
        String stringBuffer2 = stringBuffer.toString();
        if (stringBuffer2.endsWith(COMMA_SEPARATOR)) {
            stringBuffer2 = stringBuffer2.substring(0, stringBuffer2.length() - 1);
        }
        hashMap.put("execute_sql_string", "update " + m235f(str) + " set " + stringBuffer2 + " where  id='" + jSONObject.getString("id") + "'");
        hashMap.put("id", jSONObject.getString("id"));
        return hashMap;
    }

    /* renamed from: a */
    public static QueryWrapper<?> m198a(List<OnlCgformField> list, String str) {
        return m199a(list, "id", str);
    }

    /* renamed from: a */
    public static QueryWrapper<?> m199a(List<OnlCgformField> list, String str, String str2) {
        String sqlInjectField = SqlInjectionUtil.getSqlInjectField(str);
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (OnlCgformField onlCgformField : list) {
            if (OnlineConst.isPersist.equals(onlCgformField.getDbIsPersist())) {
                String dbFieldName = onlCgformField.getDbFieldName();
                if ("id".equals(dbFieldName)) {
                    z = true;
                }
                arrayList.add(SqlInjectionUtil.getSqlInjectField(dbFieldName));
            }
        }
        if (!z) {
            arrayList.add("id");
        }
        if (!arrayList.isEmpty()) {
            queryWrapper.select((String[]) arrayList.toArray(new String[0]));
        }
        queryWrapper.eq(sqlInjectField, str2);
        return queryWrapper;
    }

    /* renamed from: a */
    public static void m200a(OnlCgformField var0, LoginUser var1, JSONObject var2, String... var3) {
        String var4 = var0.getDbFieldName();
        boolean var5 = false;
        String[] var6 = var3;
        int var7 = var3.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String var9 = var6[var8];
            if (var4.toUpperCase().equals(var9)) {
                if (var0.getIsShowForm() == 1) {
                    if (var2.get(var4) == null) {
                        var5 = true;
                    }
                } else {
                    var0.setIsShowForm(1);
                    var5 = true;
                }

                if (var5) {
                    switch (var9) {
                        case "CREATE_BY":
                            var2.put(var4, var1.getUsername());
                            return;
                        case "CREATE_TIME":
                            var0.setFieldShowType("datetime");
                            var2.put(var4, DateUtils.formatDateTime());
                            return;
                        case "UPDATE_BY":
                            var2.put(var4, var1.getUsername());
                            return;
                        case "UPDATE_TIME":
                            var0.setFieldShowType("datetime");
                            var2.put(var4, DateUtils.formatDateTime());
                            return;
                        case "SYS_ORG_CODE":
                            var2.put(var4, var1.getOrgCode());
                    }
                }
                break;
            }
        }
    }

    /* renamed from: a */
    public static boolean m201a(Object obj, Object obj2) {
        if (oConvertUtils.isEmpty(obj) && oConvertUtils.isEmpty(obj2)) {
            return true;
        }
        if (oConvertUtils.isNotEmpty(obj) && obj.equals(obj2)) {
            return true;
        }
        return false;
    }

    /* renamed from: a */
    public static boolean m202a(OnlCgformField onlCgformField, OnlCgformField onlCgformField2) {
        if (!OnlineConst.isPersist.equals(onlCgformField2.getDbIsPersist()) && !OnlineConst.isPersist.equals(onlCgformField.getDbIsPersist())) {
            return false;
        }
        if (!m201a((Object) onlCgformField.getDbFieldName(), (Object) onlCgformField2.getDbFieldName()) || !m201a((Object) onlCgformField.getDbFieldTxt(), (Object) onlCgformField2.getDbFieldTxt()) || !m201a(onlCgformField.getDbLength(), onlCgformField2.getDbLength()) || !m201a(onlCgformField.getDbPointLength(), onlCgformField2.getDbPointLength()) || !m201a((Object) onlCgformField.getDbType(), (Object) onlCgformField2.getDbType()) || !m201a(onlCgformField.getDbIsNull(), onlCgformField2.getDbIsNull()) || !m201a(onlCgformField.getDbIsPersist(), onlCgformField2.getDbIsPersist()) || !m201a(onlCgformField.getDbIsKey(), onlCgformField2.getDbIsKey()) || !m201a((Object) onlCgformField.getDbDefaultVal(), (Object) onlCgformField2.getDbDefaultVal())) {
            return true;
        }
        return false;
    }

    /* renamed from: a */
    public static boolean m203a(OnlCgformIndex onlCgformIndex, OnlCgformIndex onlCgformIndex2) {
        if (!m201a((Object) onlCgformIndex.getIndexName(), (Object) onlCgformIndex2.getIndexName()) || !m201a((Object) onlCgformIndex.getIndexField(), (Object) onlCgformIndex2.getIndexField()) || !m201a((Object) onlCgformIndex.getIndexType(), (Object) onlCgformIndex2.getIndexType())) {
            return true;
        }
        return false;
    }

    /* renamed from: a */
    public static boolean m204a(OnlCgformHead onlCgformHead, OnlCgformHead onlCgformHead2) {
        if (!m201a((Object) onlCgformHead.getTableName(), (Object) onlCgformHead2.getTableName()) || !m201a((Object) onlCgformHead.getTableTxt(), (Object) onlCgformHead2.getTableTxt())) {
            return true;
        }
        return false;
    }

    /* renamed from: a */
    public static String m205a(String str, List<OnlCgformField> list, Map<String, Object> map) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        String str2 = str + "@";
        HashMap hashMap = new HashMap(5);
        for (String str3 : map.keySet()) {
            if (str3.startsWith(str2)) {
                hashMap.put(str3.replace(str2, ""), map.get(str3));
            } else {
                hashMap.put(str3, map.get(str3));
            }
        }
        for (OnlCgformField onlCgformField : list) {
            String dbFieldName = onlCgformField.getDbFieldName();
            String dbType = onlCgformField.getDbType();
            if (onlCgformField.getIsShowList().intValue() == 1) {
                stringBuffer2.append("," + dbFieldName);
            }
            if (oConvertUtils.isNotEmpty(onlCgformField.getMainField())) {
                String singleQueryConditionSql = QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", hashMap.get(dbFieldName), !OnlineDbHandler.m278a(dbType));
                if (!"".equals(singleQueryConditionSql)) {
                    stringBuffer.append(" AND " + singleQueryConditionSql);
                }
            }
            if (onlCgformField.getIsQuery().intValue() == 1) {
                if ("single".equals(onlCgformField.getQueryMode())) {
                    if (hashMap.get(dbFieldName) != null) {
                        String singleQueryConditionSql2 = QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", hashMap.get(dbFieldName), !OnlineDbHandler.m278a(dbType));
                        if (!"".equals(singleQueryConditionSql2)) {
                            stringBuffer.append(" AND " + singleQueryConditionSql2);
                        }
                    }
                } else {
                    Object obj = hashMap.get(dbFieldName + "_begin");
                    if (obj != null) {
                        stringBuffer.append(" AND " + dbFieldName + ">=");
                        if (OnlineDbHandler.m278a(dbType)) {
                            stringBuffer.append(obj.toString());
                        } else {
                            stringBuffer.append("'" + obj.toString() + "'");
                        }
                    }
                    Object obj2 = hashMap.get(dbFieldName + "_end");
                    if (obj2 != null) {
                        stringBuffer.append(" AND " + dbFieldName + "<=");
                        if (OnlineDbHandler.m278a(dbType)) {
                            stringBuffer.append(obj2.toString());
                        } else {
                            stringBuffer.append("'" + obj2.toString() + "'");
                        }
                    }
                }
            }
        }
        return "SELECT id" + stringBuffer2.toString() + " FROM " + m235f(str) + " where 1=1  " + stringBuffer.toString() + m206b(str, list, hashMap);
    }

    /* renamed from: b */
    public static String m206b(String str, List<OnlCgformField> list, Map<String, Object> map) {
        boolean z = true;
        JSONArray m249b = m249b(map);
        MatchTypeEnum m250c = m250c(map);
        StringBuilder sb = new StringBuilder();
        if (m249b != null) {
            for (int i = 0; i < m249b.size(); i++) {
                JSONObject jSONObject = m249b.getJSONObject(i);
                String[] split = jSONObject.getString("field").split(COMMA_SEPARATOR);
                if (split.length != 1) {
                    String str2 = split[1];
                    if (str.equalsIgnoreCase(split[0]) && m207c(str2, list)) {
                        m187a(sb, str2, jSONObject, m250c, null, z);
                        z = false;
                    }
                }
            }
        }
        String sb2 = sb.toString();
        if (sb2 == null || "".equals(sb2)) {
            return "";
        }
        return " AND (" + sb2 + ") ";
    }

    /* renamed from: c */
    public static boolean m207c(String str, List<OnlCgformField> list) {
        boolean z = false;
        Iterator<OnlCgformField> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (oConvertUtils.camelToUnderline(str).equalsIgnoreCase(it.next().getDbFieldName())) {
                z = true;
                break;
            }
        }
        return z;
    }

    @Deprecated
    /* renamed from: b */
    public static List<ExcelExportEntity> m208b(List<OnlCgformField> list, String str) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if ((null == str || !str.equals(list.get(i).getDbFieldName())) && list.get(i).getIsShowList().intValue() == 1) {
                ExcelExportEntity excelExportEntity = new ExcelExportEntity(list.get(i).getDbFieldTxt(), list.get(i).getDbFieldName());
                int intValue = list.get(i).getDbLength().intValue() == 0 ? 12 : list.get(i).getDbLength().intValue() > 30 ? 30 : list.get(i).getDbLength().intValue();
                if (OnlFormShowType.DATE.equals(list.get(i).getFieldShowType())) {
                    excelExportEntity.setFormat("yyyy-MM-dd");
                } else if ("datetime".equals(list.get(i).getFieldShowType())) {
                    excelExportEntity.setFormat("yyyy-MM-dd HH:mm:ss");
                }
                if (intValue < 10) {
                    intValue = 10;
                }
                excelExportEntity.setWidth(intValue);
                arrayList.add(excelExportEntity);
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public static boolean m209a(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        Class<?> cls;
        String cgJavaType = onlCgformEnhanceJava.getCgJavaType();
        String cgJavaValue = onlCgformEnhanceJava.getCgJavaValue();
        if (oConvertUtils.isNotEmpty(cgJavaValue)) {
            try {
                if ("class".equals(cgJavaType) && ((cls = Class.forName(cgJavaValue)) == null || cls.newInstance() == null)) {
                    return false;
                }
                if ("spring".equals(cgJavaType)) {
                    if (SpringContextUtils.getBean(cgJavaValue) == null) {
                        return false;
                    }
                    return true;
                }
                return true;
            } catch (Exception e) {
                f179aS.error(e.getMessage(), e);
                return false;
            }
        }
        return true;
    }

    /* renamed from: b */
    public static void m210b(List<String> list) {
        Collections.sort(list, new Comparator<String>() { // from class: org.jeecg.modules.online.cgform.d.b.1
            @Override // java.util.Comparator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(String str, String str2) {
                if (str == null || str2 == null) {
                    return -1;
                }
                if (str.compareTo(str2) > 0) {
                    return 1;
                }
                if (str.compareTo(str2) < 0) {
                    return -1;
                }
                if (str.compareTo(str2) == 0) {
                    return 0;
                }
                return 0;
            }
        });
    }

    /* renamed from: c */
    public static void m211c(List<String> list) {
        Collections.sort(list, new Comparator<String>() { // from class: org.jeecg.modules.online.cgform.d.b.2
            @Override // java.util.Comparator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(String str, String str2) {
                if (str == null || str2 == null) {
                    return -1;
                }
                if (str.length() > str2.length()) {
                    return 1;
                }
                if (str.length() < str2.length()) {
                    return -1;
                }
                if (str.compareTo(str2) > 0) {
                    return 1;
                }
                if (str.compareTo(str2) < 0) {
                    return -1;
                }
                if (str.compareTo(str2) == 0) {
                    return 0;
                }
                return 0;
            }
        });
    }

    /* renamed from: a */
    private static String m212a(String str, boolean z, QueryRuleEnum queryRuleEnum) {
        if (queryRuleEnum == QueryRuleEnum.IN) {
            return m213a(str, z);
        }
        if (z) {
            return "'" + QueryGenerator.converRuleValue(str) + "'";
        }
        return QueryGenerator.converRuleValue(str);
    }

    /* renamed from: a */
    private static String m213a(String str, boolean z) {
        if (str == null || str.length() == 0) {
            return "()";
        }
        String[] split = QueryGenerator.converRuleValue(str).split(COMMA_SEPARATOR);
        ArrayList arrayList = new ArrayList();
        for (String str2 : split) {
            if (str2 != null && str2.length() != 0) {
                if (z) {
                    arrayList.add("'" + str2 + "'");
                } else {
                    arrayList.add(str2);
                }
            }
        }
        return "(" + StringUtils.join(arrayList, COMMA_SEPARATOR) + ")";
    }

    /* renamed from: a */
    public static void m214a(String str, SysPermissionDataRuleModel sysPermissionDataRuleModel, String str2, String str3, StringBuffer stringBuffer) {
        QueryRuleEnum byValue = QueryRuleEnum.getByValue(sysPermissionDataRuleModel.getRuleConditions());
        String m212a = m212a(sysPermissionDataRuleModel.getRuleValue(), !OnlineDbHandler.m278a(str3), byValue);
        if (m212a == null || byValue == null) {
            return;
        }
        if ("ORACLE".equalsIgnoreCase(str) && "Date".equals(str3)) {
            String replace = m212a.replace(SINGLE_QUOTE, "");
            m212a = replace.length() == 10 ? m184b(replace) : m183a(replace);
        }
        switch (AnonymousClass3.f278a[byValue.ordinal()]) {
            case 1:
                stringBuffer.append(" AND " + str2 + ">" + m212a);
                return;
            case 2:
                stringBuffer.append(" AND " + str2 + ">=" + m212a);
                return;
            case 3:
                stringBuffer.append(" AND " + str2 + "<" + m212a);
                return;
            case 4:
                stringBuffer.append(" AND " + str2 + "<=" + m212a);
                return;
            case 5:
                stringBuffer.append(" AND " + str2 + " <> " + m212a);
                return;
            case 6:
                stringBuffer.append(" AND " + str2 + " IN " + m212a);
                return;
            case 7:
                stringBuffer.append(" AND " + str2 + " LIKE '%" + QueryGenerator.trimSingleQuote(m212a) + "%'");
                return;
            case 8:
                stringBuffer.append(" AND " + str2 + " LIKE '%" + QueryGenerator.trimSingleQuote(m212a) + "'");
                return;
            case 9:
                stringBuffer.append(" AND " + str2 + " LIKE '" + QueryGenerator.trimSingleQuote(m212a) + "%'");
                return;
            case 10:
                stringBuffer.append(" AND " + str2 + "=" + m212a);
                return;
            default:
                return;
        }
    }

    /* renamed from: a */
    public static String m215a(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            return str;
        }
        String replace = str.replace("#{UUID}", UUIDGenerator.generate());
        for (String str2 : QueryGenerator.getSqlRuleParams(replace)) {
            if (jSONObject.get(str2.toUpperCase()) == null && jSONObject.get(str2.toLowerCase()) == null) {
                String userSystemData = JwtUtil.getUserSystemData(str2, (SysUserCacheInfo) null);
                if (userSystemData == null) {
                    replace = replace.replace("'#{" + str2 + "}'", "NULL").replace("#{" + str2 + "}", "NULL");
                } else {
                    replace = replace.replace("#{" + str2 + "}", userSystemData);
                }
            } else {
                String str3 = null;
                if (jSONObject.containsKey(str2.toLowerCase())) {
                    str3 = jSONObject.getString(str2.toLowerCase());
                } else if (jSONObject.containsKey(str2.toUpperCase())) {
                    str3 = jSONObject.getString(str2.toUpperCase());
                }
                replace = replace.replace("#{" + str2 + "}", str3);
            }
        }
        return replace;
    }

    /* renamed from: d */
    public static String m216d(String str, List<OnlCgformButton> list) {
        String m221e = m221e(str, list);
        for (String str2 : f276aT.split(COMMA_SEPARATOR)) {
            if ("beforeAdd,afterAdd,mounted,created".indexOf(str2) >= 0) {
                Matcher matcher = Pattern.compile("(" + str2 + "\\s*\\(\\)\\s*\\{)").matcher(m221e);
                if (matcher.find()) {
                    m221e = m221e.replace(matcher.group(0), str2 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                }
            } else {
                Matcher matcher2 = Pattern.compile("(" + str2 + "\\s*\\(row\\)\\s*\\{)").matcher(m221e);
                if (matcher2.find()) {
                    m221e = m221e.replace(matcher2.group(0), str2 + "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                } else {
                    Matcher matcher3 = Pattern.compile("(" + str2 + "\\s*\\(\\)\\s*\\{)").matcher(m221e);
                    if (matcher3.find()) {
                        m221e = m221e.replace(matcher3.group(0), str2 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                    }
                }
            }
        }
        return m220d(m221e);
    }

    /* renamed from: a */
    public static void m217a(OnlCgformEnhanceJs onlCgformEnhanceJs, String str, List<OnlCgformField> list) {
        if (onlCgformEnhanceJs == null || oConvertUtils.isEmpty(onlCgformEnhanceJs.getCgJs())) {
            return;
        }
        String cgJs = onlCgformEnhanceJs.getCgJs();
        Matcher matcher = Pattern.compile("(" + str + "_" + CgformConstant.ONL_CHANGE + "\\s*\\(\\)\\s*\\{)").matcher(cgJs);
        if (matcher.find()) {
            cgJs = cgJs.replace(matcher.group(0), str + "_" + CgformConstant.ONL_CHANGE + "(){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
            for (OnlCgformField onlCgformField : list) {
                Matcher matcher2 = Pattern.compile("(" + onlCgformField.getDbFieldName() + "\\s*\\(\\))").matcher(cgJs);
                if (matcher2.find()) {
                    cgJs = cgJs.replace(matcher2.group(0), onlCgformField.getDbFieldName() + "(that,event)");
                }
            }
        }
        onlCgformEnhanceJs.setCgJs(cgJs);
    }

    /* renamed from: a */
    public static void m218a(OnlCgformEnhanceJs onlCgformEnhanceJs, String str, List<OnlCgformField> list, boolean z) {
        if (onlCgformEnhanceJs == null || oConvertUtils.isEmpty(onlCgformEnhanceJs.getCgJs())) {
            return;
        }
        String cgJs = onlCgformEnhanceJs.getCgJs();
        Matcher matcher = Pattern.compile("([^_]" + CgformConstant.ONL_CHANGE + "\\s*\\(\\)\\s*\\{)").matcher(cgJs);
        if (matcher.find()) {
            cgJs = cgJs.replace(matcher.group(0), CgformConstant.ONL_CHANGE + "(){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
            for (OnlCgformField onlCgformField : list) {
                Matcher matcher2 = Pattern.compile("(" + onlCgformField.getDbFieldName() + "\\s*\\(\\))").matcher(cgJs);
                if (matcher2.find()) {
                    cgJs = cgJs.replace(matcher2.group(0), onlCgformField.getDbFieldName() + "(that,event)");
                }
            }
        }
        onlCgformEnhanceJs.setCgJs(cgJs);
        m219a(onlCgformEnhanceJs);
        m217a(onlCgformEnhanceJs, str, list);
    }

    /* renamed from: a */
    public static void m219a(OnlCgformEnhanceJs onlCgformEnhanceJs) {
        String cgJs = onlCgformEnhanceJs.getCgJs();
        Matcher matcher = Pattern.compile("(" + "show" + "\\s*\\(\\)\\s*\\{)").matcher(cgJs);
        if (matcher.find()) {
            cgJs = cgJs.replace(matcher.group(0), "show" + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
        }
        onlCgformEnhanceJs.setCgJs(cgJs);
    }

    /* renamed from: d */
    public static String m220d(String str) {
        return "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}" + str + "}";
    }

    /* renamed from: e */
    public static String m221e(String str, List<OnlCgformButton> list) {
        if (list != null) {
            for (OnlCgformButton onlCgformButton : list) {
                String buttonCode = onlCgformButton.getButtonCode();
                if ("link".equals(onlCgformButton.getButtonStyle())) {
                    Matcher matcher = Pattern.compile("(" + buttonCode + "\\s*\\(row\\)\\s*\\{)").matcher(str);
                    if (matcher.find()) {
                        str = str.replace(matcher.group(0), buttonCode + "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                    } else {
                        Matcher matcher2 = Pattern.compile("(" + buttonCode + "\\s*\\(\\)\\s*\\{)").matcher(str);
                        if (matcher2.find()) {
                            str = str.replace(matcher2.group(0), buttonCode + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                        }
                    }
                } else if ("button".equals(onlCgformButton.getButtonStyle()) || FORM.equals(onlCgformButton.getButtonStyle())) {
                    Matcher matcher3 = Pattern.compile("(" + buttonCode + "\\s*\\(\\)\\s*\\{)").matcher(str);
                    if (matcher3.find()) {
                        str = str.replace(matcher3.group(0), buttonCode + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                    }
                }
            }
        }
        return str;
    }

    /* renamed from: a */
    public static JSONArray m222a(List<OnlCgformField> list, List<String> list2) {
        JSONArray jSONArray = new JSONArray();
        for (OnlCgformField onlCgformField : list) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (!"id".equals(dbFieldName)) {
                JSONObject jSONObject = new JSONObject();
                if (list2 != null && list2.indexOf(dbFieldName) >= 0) {
                    jSONObject.put("disabled", true);
                }
                if (onlCgformField.getIsReadOnly() != null && 1 == onlCgformField.getIsReadOnly().intValue()) {
                    jSONObject.put("disabled", true);
                }
                jSONObject.put(TITLE, onlCgformField.getDbFieldTxt());
                jSONObject.put("key", dbFieldName);
                String m243d = m243d(onlCgformField);
                jSONObject.put("type", m243d);
                if (onlCgformField.getFieldLength() == null) {
                    onlCgformField.setFieldLength(186);
                }
                if (("sel_depart".equals(m243d) || "sel_user".equals(m243d)) && onlCgformField.getFieldLength().intValue() < 170) {
                    jSONObject.put("width", "170px");
                } else if (OnlFormShowType.DATE.equals(m243d) && onlCgformField.getFieldLength().intValue() < 140) {
                    jSONObject.put("width", "140px");
                } else if ("datetime".equals(m243d) && onlCgformField.getFieldLength().intValue() < 190) {
                    jSONObject.put("width", "190px");
                } else {
                    jSONObject.put("width", onlCgformField.getFieldLength() + "px");
                }
                if ("file".equals(m243d) || "image".equals(m243d)) {
                    jSONObject.put("responseName", "message");
                    jSONObject.put("token", true);
                }
                if (f217L.equals(m243d)) {
                    jSONObject.put("type", "checkbox");
                    JSONArray jSONArray2 = new JSONArray();
                    if (oConvertUtils.isEmpty(onlCgformField.getFieldExtendJson())) {
                        jSONArray2.add("Y");
                        jSONArray2.add("N");
                    } else {
                        jSONArray2 = JSONArray.parseArray(onlCgformField.getFieldExtendJson());
                    }
                    jSONObject.put("customValue", jSONArray2);
                }
                if (f218M.equals(m243d)) {
                    jSONObject.put("popupCode", onlCgformField.getDictTable());
                    jSONObject.put("orgFields", onlCgformField.getDictField());
                    jSONObject.put("destFields", onlCgformField.getDictText());
                    String dictText = onlCgformField.getDictText();
                    if (dictText != null && !"".equals(dictText)) {
                        ArrayList arrayList = new ArrayList();
                        for (String str : dictText.split(COMMA_SEPARATOR)) {
                            if (!m191a(str, list)) {
                                arrayList.add(str);
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put(TITLE, str);
                                jSONObject2.put("key", str);
                                jSONObject2.put("type", "hidden");
                                jSONArray.add(jSONObject2);
                            }
                        }
                    }
                }
                jSONObject.put("defaultValue", onlCgformField.getDbDefaultVal());
                jSONObject.put("fieldDefaultValue", onlCgformField.getFieldDefaultValue());
                jSONObject.put("placeholder", "请输入" + onlCgformField.getDbFieldTxt());
                jSONObject.put("validateRules", m223c(onlCgformField));
                if (LIST.equals(onlCgformField.getFieldShowType()) || "radio".equals(onlCgformField.getFieldShowType()) || "checkbox_meta".equals(onlCgformField.getFieldShowType()) || "list_multi".equals(onlCgformField.getFieldShowType()) || "sel_search".equals(onlCgformField.getFieldShowType())) {
                    jSONObject.put(VIEW, onlCgformField.getFieldShowType());
                    jSONObject.put("dictTable", onlCgformField.getDictTable());
                    jSONObject.put("dictText", onlCgformField.getDictText());
                    jSONObject.put("dictCode", onlCgformField.getDictField());
                    if ("list_multi".equals(onlCgformField.getFieldShowType())) {
                        jSONObject.put("width", "230px");
                    }
                }
                jSONObject.put("fieldExtendJson", onlCgformField.getFieldExtendJson());
                jSONArray.add(jSONObject);
            }
        }
        return jSONArray;
    }

    /* renamed from: c */
    private static JSONArray m223c(OnlCgformField onlCgformField) {
        JSONArray jSONArray = new JSONArray();
        if (onlCgformField.getDbIsNull().intValue() == 0 || "1".equals(onlCgformField.getFieldMustInput())) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("required", true);
            jSONObject.put("message", onlCgformField.getDbFieldTxt() + "不能为空!");
            jSONArray.add(jSONObject);
        }
        if (oConvertUtils.isNotEmpty(onlCgformField.getFieldValidType())) {
            JSONObject jSONObject2 = new JSONObject();
            if ("only".equals(onlCgformField.getFieldValidType())) {
                jSONObject2.put("unique", true);
                jSONObject2.put("message", onlCgformField.getDbFieldTxt() + "不能重复");
            } else {
                jSONObject2.put("pattern", onlCgformField.getFieldValidType());
                String m193a = m193a(ExtendJsonKey.VALIDATE_ERROR, onlCgformField.getFieldExtendJson());
                if (oConvertUtils.isEmpty(m193a)) {
                    jSONObject2.put("message", onlCgformField.getDbFieldTxt() + "格式不正确");
                } else {
                    jSONObject2.put("message", m193a);
                }
            }
            jSONArray.add(jSONObject2);
        }
        return jSONArray;
    }

    /* renamed from: a */
    public static Map<String, Object> m224a(Map<String, Object> map) {
        HashMap<String,Object> hashMap = new HashMap<>(5);
        if (map == null || map.isEmpty()) {
            return hashMap;
        }
        for (String key : map.keySet()) {
            Object obj = map.get(key);
            if (obj instanceof Clob) {
                obj = m230a((Clob) obj);
            } else if (obj instanceof byte[]) {
                obj = new String((byte[]) obj);
            } else if (obj instanceof Blob) {
                if (obj != null) {
                    try {
                        Blob blob = (Blob) obj;
                        obj = new String(blob.getBytes(1L, (int) blob.length()), "UTF-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            hashMap.put(key.toLowerCase(), obj == null ? "" : obj);
        }
        return hashMap;
    }

    /* renamed from: a */
    public static JSONObject m225a(JSONObject jSONObject) {
        if (DbTableUtil.m489a()) {
            JSONObject jSONObject2 = new JSONObject();
            if (jSONObject == null || jSONObject.isEmpty()) {
                return jSONObject2;
            }
            for (String str : jSONObject.keySet()) {
                jSONObject2.put(str.toLowerCase(), jSONObject.get(str));
            }
            return jSONObject2;
        }
        return jSONObject;
    }

    /* renamed from: a */
    public static List<Map<String, Object>> m226a(JSONArray jSONArray) {
        return m228a( jSONArray.stream().map(obj -> {
            return (JSONObject) obj;
        }).collect(Collectors.toList()), (Collection<String>) null);
    }

    /* renamed from: d */
    public static List<Map<String, Object>> m227d(List<Map<String, Object>> list) {
        return m228a(list, (Collection<String>) null);
    }

    /* renamed from: a */
    public static List<Map<String, Object>> m228a(List<Map<String, Object>> list, Collection<String> collection) {
        ArrayList arrayList = new ArrayList();
        for (Map<String, Object> map : list) {
            if (map != null) {
                HashMap hashMap = new HashMap(5);
                for (String str : map.keySet()) {
                    Object obj = map.get(str);
                    if (obj instanceof Clob) {
                        obj = m230a((Clob) obj);
                    } else if (obj instanceof byte[]) {
                        obj = new String((byte[]) obj);
                    } else if (obj instanceof Long) {
                        if (obj != null) {
                            obj = String.valueOf(obj);
                        }
                    } else if (obj instanceof Blob) {
                        if (obj != null) {
                            try {
                                Blob blob = (Blob) obj;
                                obj = new String(blob.getBytes(1L, (int) blob.length()), "UTF-8");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    hashMap.put(str.toLowerCase(), obj == null ? "" : obj);
                }
                hashMap.put(f199t, m229a((Map<String, Object>) hashMap, collection));
                arrayList.add(hashMap);
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    private static String m229a(Map<String, Object> map, Collection<String> collection) {
        String obj = map.containsKey("id") ? map.get("id").toString() : null;
        if (oConvertUtils.isNotEmpty(obj) && collection != null) {
            Iterator<String> it = collection.iterator();
            while (it.hasNext()) {
                Object obj2 = map.get(it.next().toLowerCase() + "_id");
                if (obj2 == null) {
                    obj = obj + "@";
                } else {
                    obj = obj + "@" + obj2.toString();
                }
            }
        }
        return obj;
    }

    /* renamed from: a */
    public static String m230a(Clob clob) {
        String str = "";
        try {
            Reader characterStream = clob.getCharacterStream();
            char[] cArr = new char[(int) clob.length()];
            characterStream.read(cArr);
            str = new String(cArr);
            characterStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return str;
    }

    /* renamed from: c */
    public static Map<String, Object> m231c(String str, List<OnlCgformField> list, JSONObject jSONObject) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        String str2 = "";
        try {
            str2 = DbTableUtil.getDatabaseType();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DBException e2) {
            e2.printStackTrace();
        }
        HashMap hashMap = new HashMap(5);
        boolean z = false;
        String str3 = null;
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (loginUser == null) {
            String userNameByToken = JwtUtil.getUserNameByToken(SpringContextUtils.getHttpServletRequest());
            if (oConvertUtils.isNotEmpty(userNameByToken)) {
                loginUser = new LoginUser();
                loginUser.setUsername(userNameByToken);
            } else {
                throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
            }
        }
        String sqlInjectTableName = SqlInjectionUtil.getSqlInjectTableName(m235f(str));
        boolean m260j = m260j(sqlInjectTableName);
        for (OnlCgformField onlCgformField : list) {
            String sqlInjectField = SqlInjectionUtil.getSqlInjectField(onlCgformField.getDbFieldName());
            if (null != sqlInjectField && (jSONObject.get(sqlInjectField) != null || f203x.equalsIgnoreCase(sqlInjectField) || f202w.equalsIgnoreCase(sqlInjectField) || f206A.equalsIgnoreCase(sqlInjectField))) {
                if (!m260j || !"tenant_id".equalsIgnoreCase(sqlInjectField)) {
                    m200a(onlCgformField, loginUser, jSONObject, f203x, f202w, f206A);
                    if ("".equals(jSONObject.get(sqlInjectField))) {
                        String dbType = onlCgformField.getDbType();
                        if (!OnlineDbHandler.m278a(dbType) && !OnlineDbHandler.m279b(dbType)) {
                        }
                    }
                    if ("id".equals(sqlInjectField.toLowerCase())) {
                        z = true;
                        str3 = jSONObject.getString(sqlInjectField);
                    } else if (!f239ah.equals(onlCgformField.getFieldShowType()) || OnlineConst.isPersist.equals(onlCgformField.getDbIsPersist())) {
                        stringBuffer.append("," + sqlInjectField);
                        stringBuffer2.append("," + OnlineDbHandler.m280a(str2, onlCgformField, jSONObject, hashMap));
                    }
                }
            }
        }
        if (!z || oConvertUtils.isEmpty(str3)) {
            str3 = nextId();
        }
        if (m260j) {
            stringBuffer.append("," + "tenant_id");
            stringBuffer2.append(",#{" + "tenant_id" + "}");
            hashMap.put("tenant_id", SpringContextUtils.getHttpServletRequest().getHeader("X-Tenant-Id"));
        }
        hashMap.put("execute_sql_string", "insert into " + sqlInjectTableName + "(id" + stringBuffer.toString() + ") values('" + str3 + "'" + stringBuffer2.toString() + ")");
        hashMap.put("id", str3);
        return hashMap;
    }

    /* renamed from: d */
    public static Map<String, Object> m232d(String str, List<OnlCgformField> list, JSONObject jSONObject) {
        String dbFieldName;
        StringBuffer stringBuffer = new StringBuffer();
        HashMap hashMap = new HashMap(5);
        String str2 = "";
        try {
            str2 = DbTableUtil.getDatabaseType();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DBException e2) {
            e2.printStackTrace();
        }
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (loginUser == null) {
            String userNameByToken = JwtUtil.getUserNameByToken(SpringContextUtils.getHttpServletRequest());
            if (oConvertUtils.isNotEmpty(userNameByToken)) {
                loginUser = new LoginUser();
                loginUser.setUsername(userNameByToken);
            } else {
                throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
            }
        }
        for (OnlCgformField onlCgformField : list) {
            if (OnlineConst.isPersist.equals(onlCgformField.getDbIsPersist()) && null != (dbFieldName = onlCgformField.getDbFieldName()) && !"id".equals(dbFieldName) && (jSONObject.get(dbFieldName) != null || f205z.equalsIgnoreCase(dbFieldName) || f204y.equalsIgnoreCase(dbFieldName) || f206A.equalsIgnoreCase(dbFieldName))) {
                m200a(onlCgformField, loginUser, jSONObject, f205z, f204y, f206A);
                if ("".equals(jSONObject.get(dbFieldName))) {
                    String dbType = onlCgformField.getDbType();
                    if (!OnlineDbHandler.m278a(dbType) && !OnlineDbHandler.m279b(dbType)) {
                    }
                }
                stringBuffer.append(dbFieldName + "=" + OnlineDbHandler.m280a(str2, onlCgformField, jSONObject, hashMap) + ",");
            }
        }
        String stringBuffer2 = stringBuffer.toString();
        if (stringBuffer2.endsWith(COMMA_SEPARATOR)) {
            stringBuffer2 = stringBuffer2.substring(0, stringBuffer2.length() - 1);
        }
        hashMap.put("execute_sql_string", "update " + m235f(str) + " set " + stringBuffer2 + " where  id='" + jSONObject.getString("id") + "'");
        hashMap.put("id", jSONObject.getString("id"));
        return hashMap;
    }

    /* renamed from: a */
    public static Map<String, Object> m233a(String str, String str2, String str3) {
        HashMap hashMap = new HashMap(5);
        hashMap.put("execute_sql_string", "update " + m235f(str) + " set " + str2 + "='0' where  id='" + str3 + "'");
        return hashMap;
    }

    /* renamed from: e */
    public static String m234e(String str) {
        if (str == null || "".equals(str) || "0".equals(str)) {
            return "";
        }
        return "CODE like '" + str + "%'";
    }

    /* renamed from: f */
    public static String m235f(String str) {
        String str2;
        if (Pattern.matches("^[a-zA-z].*\\$\\d+$", str)) {
            str2 = str.substring(0, str.lastIndexOf(f201v));
        } else {
            str2 = str;
        }
        return SqlInjectionUtil.getSqlInjectTableName(str2);
    }

    /* renamed from: a */
    public static void m236a(LinkDownProperty linkDownProperty, List<OnlCgformField> list, List<String> list2) {
        String string = JSONObject.parseObject(linkDownProperty.getDictTable()).getString("linkField");
        ArrayList arrayList = new ArrayList();
        if (oConvertUtils.isNotEmpty(string)) {
            String[] split = string.split(COMMA_SEPARATOR);
            for (OnlCgformField onlCgformField : list) {
                String dbFieldName = onlCgformField.getDbFieldName();
                int length = split.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    if (!split[i].equals(dbFieldName)) {
                        i++;
                    } else {
                        list2.add(dbFieldName);
                        arrayList.add(new BaseColumn(onlCgformField.getDbFieldTxt(), dbFieldName, onlCgformField.getOrderNum()));
                        break;
                    }
                }
            }
        }
        linkDownProperty.setOtherColumns(arrayList);
    }

    /* renamed from: a */
    public static String m237a(byte[] bArr, String str, String str2, String str3) {
        return CommonUtils.uploadOnlineImage(bArr, str, str2, str3);
    }

    /* renamed from: e */
    public static List<String> m238e(List<OnlCgformField> list) {
        ArrayList arrayList = new ArrayList();
        for (OnlCgformField onlCgformField : list) {
            if ("image".equals(onlCgformField.getFieldShowType())) {
                arrayList.add(onlCgformField.getDbFieldTxt());
            }
        }
        return arrayList;
    }

    /* renamed from: c */
    public static List<String> m239c(List<OnlCgformField> list, String str) {
        ArrayList arrayList = new ArrayList();
        for (OnlCgformField onlCgformField : list) {
            if ("image".equals(onlCgformField.getFieldShowType())) {
                arrayList.add(str + "_" + onlCgformField.getDbFieldTxt());
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public static String nextId() {
        return String.valueOf(IdWorker.getId());
    }

    /* renamed from: a */
    public static String exceptionToMessage(Exception exc) {
        String message = exc.getCause() != null ? exc.getCause().getMessage() : exc.getMessage();
        if (message.indexOf("ORA-01452") != -1) {
            message = "ORA-01452: 无法 CREATE UNIQUE INDEX; 找到重复的关键字";
        } else if (message.indexOf("duplicate key") != -1) {
            message = "无法 CREATE UNIQUE INDEX; 找到重复的关键字";
        }
        return message;
    }

    /* renamed from: b */
    public static List<DictModel> m242b(OnlCgformField onlCgformField) {
        ArrayList arrayList = new ArrayList();
        String fieldExtendJson = onlCgformField.getFieldExtendJson();
        JSONArray parseArray = JSONArray.parseArray("[\"Y\",\"N\"]");
        if (oConvertUtils.isNotEmpty(fieldExtendJson)) {
            parseArray = JSONArray.parseArray(fieldExtendJson);
        }
        DictModel dictModel = new DictModel(parseArray.getString(0), "是");
        DictModel dictModel2 = new DictModel(parseArray.getString(1), "否");
        arrayList.add(dictModel);
        arrayList.add(dictModel2);
        return arrayList;
    }

    /* renamed from: d */
    private static String m243d(OnlCgformField onlCgformField) {
        if ("checkbox".equals(onlCgformField.getFieldShowType())) {
            return "checkbox";
        }
        if (OnlFormShowType.PCA.equals(onlCgformField.getFieldShowType())) {
            return OnlFormShowType.PCA;
        }
        if (LIST.equals(onlCgformField.getFieldShowType())) {
            return "select";
        }
        if (f217L.equals(onlCgformField.getFieldShowType())) {
            return f217L;
        }
        if ("sel_user".equals(onlCgformField.getFieldShowType())) {
            return "sel_user";
        }
        if ("sel_depart".equals(onlCgformField.getFieldShowType())) {
            return "sel_depart";
        }
        if (DataBaseConst.TEXTAREA.equals(onlCgformField.getFieldShowType())) {
            return DataBaseConst.TEXTAREA;
        }
        if ("image".equals(onlCgformField.getFieldShowType()) || "file".equals(onlCgformField.getFieldShowType()) || "radio".equals(onlCgformField.getFieldShowType()) || f218M.equals(onlCgformField.getFieldShowType()) || "list_multi".equals(onlCgformField.getFieldShowType()) || "sel_search".equals(onlCgformField.getFieldShowType())) {
            return onlCgformField.getFieldShowType();
        }
        if ("datetime".equals(onlCgformField.getFieldShowType())) {
            return "datetime";
        }
        if (OnlFormShowType.DATE.equals(onlCgformField.getFieldShowType())) {
            return OnlFormShowType.DATE;
        }
        if ("time".equals(onlCgformField.getFieldShowType())) {
            return "time";
        }
        if ("int".equals(onlCgformField.getDbType()) || "double".equals(onlCgformField.getDbType()) || "BigDecimal".equals(onlCgformField.getDbType())) {
            return "inputNumber";
        }
        return "input";
    }

    private static String getDatabseType() {
        if (oConvertUtils.isNotEmpty(f277aU)) {
            return f277aU;
        }
        try {
            f277aU = DbTableUtil.getDatabaseType();
            return f277aU;
        } catch (Exception e) {
            e.printStackTrace();
            return f277aU;
        }
    }

    /* renamed from: f */
    public static List<String> m244f(List<String> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().toLowerCase());
        }
        return arrayList;
    }

    /* renamed from: c */
    private static String m245c(String str, String str2) {
        String str3 = "";
        if (str2 == null || "".equals(str2)) {
            return str3;
        }
        String[] split = str2.split(COMMA_SEPARATOR);
        for (int i = 0; i < split.length; i++) {
            if (i > 0) {
                str3 = str3 + " AND ";
            }
            String str4 = str3 + str + " like ";
            if ("SQLSERVER".equals(getDatabseType())) {
                str4 = str4 + "N";
            }
            str3 = str4 + "'%" + split[i] + "%'";
        }
        return str3;
    }

    /* renamed from: a */
    public static String generateLogFile(String str, String str2, StringBuffer stringBuffer) {
        LocalDate now = LocalDate.now();
        //now转为String
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String logPath = "logs" + File.separator + nowStr + File.separator;
        String str4 = str + File.separator + logPath;
        File file = new File(str4);
        if (!file.exists()) {
            file.mkdirs();
        }
        String str5 = str2 + Math.round(Math.random() * 10000.0d);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(str4 + str5 + ".txt"));
            bufferedWriter.write(stringBuffer.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
        }
        return "/sys/common/static/" + logPath + str5 + ".txt";
    }

    /**
     * 这里是将jsonObject 进行一个转换，转为formItem
     * @param jSONObject
     * @return
     */
    /* renamed from: b */
    public static JSONObject m247b(JSONObject jSONObject) {
        JSONObject jSONObject2;
        if (jSONObject.containsKey(PROPERTIES)) {
            jSONObject2 = jSONObject.getJSONObject(PROPERTIES);
        } else {
            jSONObject2 = jSONObject.getJSONObject("schema").getJSONObject(PROPERTIES);
        }
        ISysBaseAPI iSysBaseAPI = SpringContextUtils.getBean(ISysBaseAPI.class);
        for (String key : jSONObject2.keySet()) {
            JSONObject jSONObject3 = jSONObject2.getJSONObject(key);
            String string = jSONObject3.getString(VIEW);
            if (m185c(string)) {
                String string2 = jSONObject3.getString("dictCode");
                String string3 = jSONObject3.getString("dictText");
                String string4 = jSONObject3.getString("dictTable");
                ArrayList arrayList = new ArrayList();
                if (oConvertUtils.isNotEmpty(string4)) {
                    arrayList = (ArrayList) iSysBaseAPI.queryTableDictItemsByCode(string4, string3, string2);
                } else if (oConvertUtils.isNotEmpty(string2)) {
                    arrayList = (ArrayList) iSysBaseAPI.queryEnableDictItemsByCode(string2);
                }
                if (arrayList != null && arrayList.size() > 0) {
                    jSONObject3.put("enum", arrayList);
                }
            } else if ("tab".equals(string)) {
                if ("1".equals(jSONObject3.getString("relationType"))) {
                    m247b(jSONObject3);
                } else {
                    JSONArray jSONArray = jSONObject3.getJSONArray("columns");
                    for (int i = 0; i < jSONArray.size(); i++) {
                        JSONObject jSONObject4 = jSONArray.getJSONObject(i);
                        if (m248c(jSONObject4)) {
                            String string5 = jSONObject4.getString("dictCode");
                            String string6 = jSONObject4.getString("dictText");
                            String string7 = jSONObject4.getString("dictTable");
                            ArrayList arrayList2 = new ArrayList();
                            if (oConvertUtils.isNotEmpty(string7)) {
                                arrayList2 = (ArrayList) iSysBaseAPI.queryTableDictItemsByCode(string7, string6, string5);
                            } else if (oConvertUtils.isNotEmpty(string5)) {
                                arrayList2 = (ArrayList) iSysBaseAPI.queryEnableDictItemsByCode(string5);
                            }
                            if (arrayList2 != null && arrayList2.size() > 0) {
                                jSONObject4.put("options", arrayList2);
                            }
                        }
                    }
                }
            }
        }
        return jSONObject;
    }

    /* renamed from: c */
    private static boolean m248c(JSONObject jSONObject) {
        Object obj = jSONObject.get(VIEW);
        if (obj != null) {
            String obj2 = obj.toString();
            if (LIST.equals(obj2) || "radio".equals(obj2) || "checkbox_meta".equals(obj2) || "list_multi".equals(obj2) || "sel_search".equals(obj2)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /* renamed from: b */
    public static JSONArray m249b(Map<String, Object> map) {
        Object obj = map.get("superQueryParams");
        if (obj != null) {
            try {
                return JSONArray.parseArray(URLDecoder.decode(obj.toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                f179aS.error("高级查询json参数转换失败" + e.getMessage());
                return null;
            }
        }
        return null;
    }

    /* renamed from: c */
    public static MatchTypeEnum m250c(Map<String, Object> map) {
        MatchTypeEnum byValue = MatchTypeEnum.getByValue(map.get("superQueryMatchType"));
        if (byValue == null) {
            byValue = MatchTypeEnum.AND;
        }
        return byValue;
    }

    /* renamed from: g */
    public static boolean m251g(String str) {
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt != '.' && charAt != '-' && charAt != '+' && !Character.isDigit(charAt)) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: g */
    public static List<OnlineFieldConfig> m252g(List<OnlCgformField> list) {
        ArrayList arrayList = new ArrayList();
        for (OnlCgformField onlCgformField : list) {
            if (OnlineConst.isPersist.equals(onlCgformField.getDbIsPersist())) {
                arrayList.add(new OnlineFieldConfig(onlCgformField));
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public static String m253a(Map<String, Object> map, String str) {
        Object m254b = m254b(map, str);
        if (m254b != null) {
            return m254b.toString();
        }
        return null;
    }

    /* renamed from: b */
    public static Object m254b(Map<String, Object> map, String str) {
        if (map == null || oConvertUtils.isEmpty(str)) {
            return null;
        }
        Object obj = map.get(str);
        if (obj != null) {
            return obj;
        }
        Object obj2 = map.get(str.toUpperCase());
        if (obj2 != null) {
            return obj2;
        }
        Object obj3 = map.get(str.toLowerCase());
        if (obj3 != null) {
            return obj3;
        }
        return null;
    }

    /* renamed from: h */
    public static List<String> m255h(String str) {
        ArrayList arrayList = new ArrayList();
        if (oConvertUtils.isNotEmpty(str)) {
            for (String str2 : str.split(COMMA_SEPARATOR)) {
                int indexOf = str2.indexOf("@");
                if (indexOf > 0) {
                    arrayList.add(str2.substring(0, indexOf));
                } else {
                    arrayList.add(str2);
                }
            }
        }
        return arrayList;
    }

    /* renamed from: f */
    public static Map<String, List<String>> m256f(String str, List<String> list) {
        HashMap hashMap = new HashMap(5);
        hashMap.put("id", new ArrayList());
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                hashMap.put(list.get(i) + "_id", new ArrayList());
            }
        }
        if (oConvertUtils.isNotEmpty(str)) {
            for (String str2 : str.split(COMMA_SEPARATOR)) {
                String[] split = str2.split("@");
                if (split.length > 0) {
                    ((List) hashMap.get("id")).add(split[0]);
                }
                if (split.length > 1 && list != null && list.size() > 0) {
                    for (int i2 = 1; i2 < split.length; i2++) {
                        ((List) hashMap.get(list.get(i2 - 1) + "_id")).add(split[i2]);
                    }
                }
            }
        }
        return hashMap;
    }

    /* renamed from: b */
    public static List<ExcelExportEntity> m257b(List<OnlCgformField> list, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if ((null == str || !str.equals(list.get(i).getDbFieldName())) && list.get(i).getIsShowList().intValue() == 1) {
                ExcelExportEntity excelExportEntity = new ExcelExportEntity(list.get(i).getDbFieldTxt(), list.get(i).getDbFieldName());
                if ("image".equals(list.get(i).getFieldShowType())) {
                    excelExportEntity.setType(2);
                    excelExportEntity.setExportImageType(3);
                    excelExportEntity.setImageBasePath(str2);
                    excelExportEntity.setHeight(50.0d);
                    excelExportEntity.setWidth(60.0d);
                } else {
                    int intValue = list.get(i).getDbLength().intValue() == 0 ? 12 : list.get(i).getDbLength().intValue() > 30 ? 30 : list.get(i).getDbLength().intValue();
                    if (OnlFormShowType.DATE.equals(list.get(i).getFieldShowType())) {
                        excelExportEntity.setFormat("yyyy-MM-dd");
                    } else if ("datetime".equals(list.get(i).getFieldShowType())) {
                        excelExportEntity.setFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    if (intValue < 10) {
                        intValue = 10;
                    }
                    excelExportEntity.setWidth(intValue);
                }
                arrayList.add(excelExportEntity);
            }
        }
        return arrayList;
    }

    /**
     * @param obj 对象
     * @param cls 类
     * @param <T> 泛型
     * @return 返回一个list
     */
    /* renamed from: a */
    public static <T> List<T> converterRecordsToList(Object obj, Class<T> cls) {
        ArrayList<T> arrayList = new ArrayList<>();
        if (obj instanceof List) {
            for (T t : (List<T>) obj) {
                arrayList.add(cls.cast(t));
            }
            return arrayList;
        }
        return null;
    }

    /* renamed from: i */
    public static boolean m259i(String str) {
        return f202w.equalsIgnoreCase(str) || f203x.equalsIgnoreCase(str) || f204y.equalsIgnoreCase(str) || f205z.equalsIgnoreCase(str) || f206A.equalsIgnoreCase(str) || "id".equalsIgnoreCase(str);
    }

    /* renamed from: j */
    public static boolean m260j(String str) {
        boolean z = false;
        Iterator it = MybatisPlusSaasConfig.TENANT_TABLE.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (((String) it.next()).equalsIgnoreCase(str)) {
                z = true;
                break;
            }
        }
        return z;
    }

    /* renamed from: k */
    public static String m261k(String str) {
        if (str.indexOf("@") > 0) {
            str = str.substring(0, str.indexOf("@"));
        }
        return str;
    }
}
