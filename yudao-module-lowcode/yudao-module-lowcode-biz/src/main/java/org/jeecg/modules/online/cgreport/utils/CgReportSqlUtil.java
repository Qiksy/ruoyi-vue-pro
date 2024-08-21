package org.jeecg.modules.online.cgreport.utils;

import cn.iocoder.yudao.module.infra.dal.dataobject.db.DataSourceConfigDO;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.SneakyThrows;
import org.jeecg.common.util.dynamic.db.DynamicDBUtil;
import org.jeecg.common.util.dynamic.db.DataSourceCachePool;
import org.jeecg.common.util.online.ReflectHelper;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.handler.ConditionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: CgReportSqlUtil.java */
/* renamed from: org.jeecg.modules.online.cgreport.c.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/c/b.class */
public class CgReportSqlUtil {

    /* renamed from: d */
    private static final Logger f468d = LoggerFactory.getLogger(CgReportSqlUtil.class);

    /* renamed from: a */
    public static final String WHERE = " where ";

    /* renamed from: b */
    public static final String AND = " and ";

    /* renamed from: c */
    public static final String OR = " or ";

    /* renamed from: a */
    public static String m429a(String str) {
        String replaceAll = str.replaceAll("(?i) where ", WHERE).replaceAll("(?i) and ", AND).replaceAll("(?i) or ", " or ");
        Matcher matcher = Pattern.compile("(,\\s*|\\s*(\\w|\\.)+\\s*[^, ]+ *\\S*)\\$\\{\\w+\\}\\S*").matcher(replaceAll);
        while (matcher.find()) {
            String group = matcher.group();
            if (group.contains(WHERE)) {
                replaceAll = replaceAll.replace(group, group.substring(0, group.indexOf(WHERE)) + " where 1=1");
            } else if (group.contains(AND)) {
                String substring = group.substring(group.indexOf("and"));
                if (substring.indexOf("(") > 0) {
                    replaceAll = replaceAll.replace(substring.substring(substring.indexOf("(") + 1), " 1=1 ");
                } else {
                    replaceAll = replaceAll.replace(substring, "and 1=1");
                }
            } else if (group.contains(OR)) {
                String substring2 = group.substring(group.indexOf("or"));
                if (substring2.indexOf("(") > 0) {
                    replaceAll = replaceAll.replace(substring2.substring(substring2.indexOf("(") + 1), " 1=1 ");
                } else {
                    replaceAll = replaceAll.replace(substring2, "or 1=1");
                }
            } else if (group.startsWith(CgformUtil.COMMA_SEPARATOR)) {
                replaceAll = replaceAll.replace(group, " ,1 ");
            } else {
                replaceAll = replaceAll.replace(group, " 1=1 ");
            }
        }
        return replaceAll.replaceAll("(?i)\\(\\s*1=1\\s*(AND|OR)", "(").replaceAll("(?i)(AND|OR)\\s*1=1", "");
    }

    /* renamed from: a */
    public static Map<String, Object> m430a(HttpServletRequest httpServletRequest) {
        String str;
        Map<String,String[]> parameterMap = httpServletRequest.getParameterMap();
        HashMap<String,Object> hashMap = new HashMap<>(5);
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

    /* renamed from: b */
    public static boolean m431b(String str) {
        return str.toLowerCase().indexOf("select") == 0;
    }

    /* renamed from: c */
    public static String m432c(String str) {
        return String.format("SELECT COUNT(1) \"total\" FROM ( %s ) temp_count", str);
    }

    /* renamed from: a */
    @SneakyThrows
    public static Map<String, Object> m433a(String str, String str2) {
        Map<String, Object> map = null;
        IDialect dialect = DialectFactory.getDialect(JdbcUtils.getDbType(DataSourceCachePool.getCacheDynamicDataSourceModel(str).getUrl()));
        if (str2.toUpperCase().contains("LIMIT") || str2.toUpperCase().contains("OFFSET")) {
            List<Map<String, Object>> findList = DynamicDBUtil.findList(str, str2, new Object[0]);
            if (!findList.isEmpty()) {
                map = findList.get(0);
            }
        } else {
            Page<?> page = new Page<>(1L, 1L);
            DialectModel buildPaginationSql = dialect.buildPaginationSql(str2, page.offset(), page.getSize());
            String dialectSql = buildPaginationSql.getDialectSql();
            if (dialectSql.contains("?")) {
                long longValue = (Long) Objects.requireNonNull(ReflectHelper.getFieldVal("firstParam", buildPaginationSql));
                map = dialectSql.length() - dialectSql.replaceAll("\\?", "").length() == 1 ?
                        ( Map<String, Object>) DynamicDBUtil.findOne(str, dialectSql, new Object[]{Long.valueOf(longValue)}) :
                        ( Map<String, Object>) DynamicDBUtil.findOne(str, dialectSql, new Object[]{Long.valueOf(longValue), Long.valueOf(((Long) ReflectHelper.getFieldVal("secondParam", buildPaginationSql)).longValue())});
            } else {
                map = ( Map<String, Object>) DynamicDBUtil.findOne(str, dialectSql, new Object[0]);
            }
        }
        return map;
    }

    /* renamed from: a */
    @SneakyThrows
    public static List<Map<String, Object>> m434a(String str, String str2, String str3, int i, int i2, Map<String, Object> map) {
        DataSourceConfigDO cacheDynamicDataSourceModel = DataSourceCachePool.getCacheDynamicDataSourceModel(str2);
        String str4 = str3;
        DialectModel dialectModel = null;
        if (!Boolean.parseBoolean(str)) {
            IDialect dialect = DialectFactory.getDialect(JdbcUtils.getDbType(cacheDynamicDataSourceModel.getUrl()));
            Page page = new Page(i, i2);
            dialectModel = dialect.buildPaginationSql(str3, page.offset(), page.getSize());
            str4 = dialectModel.getDialectSql();
        }
        if (str4.contains("?")) {
            long longValue = (Long) Objects.requireNonNull(ReflectHelper.getFieldVal("firstParam", dialectModel));
            long longValue2 = (Long) Objects.requireNonNull(ReflectHelper.getFieldVal("secondParam", dialectModel));
            if (str4.length() - str4.replaceAll("\\?", "").length() == 1) {
                str4 = ConditionHandler.m35a(str4, longValue);
            } else {
                str4 = ConditionHandler.m36a(str4, longValue, longValue2);
            }
        }
        return DynamicDBUtil.findListByNamedParam(str2, str4, map);
    }
}
