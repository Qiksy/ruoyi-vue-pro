package org.jeecg.modules.online.cgform.utils;

import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;
import java.util.Map;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: OnlineDbHandler.java */
/* renamed from: org.jeecg.modules.online.cgform.d.g */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/d/g.class */
public class OnlineDbHandler {

    /* renamed from: a */
    public static final String INT = "int";

    /* renamed from: b */
    public static final String INTEGER = "Integer";

    /* renamed from: c */
    public static final String DOUBLE = "double";

    /* renamed from: d */
    public static final String BIG_DECIMAL = "BigDecimal";

    /* renamed from: e */
    public static final String BLOB = "Blob";

    /* renamed from: f */
    public static final String DATE = "Date";

    /* renamed from: g */
    public static final String DATETIME = "datetime";

    /* renamed from: h */
    public static final String f305h = "Timestamp";

    /* renamed from: i */
    public static final String LONG = "Long";

    /* renamed from: a */
    public static boolean m278a(String str) {
        return "int".equals(str) || "double".equals(str) || "BigDecimal".equals(str) || "Integer".equals(str) || "Long".equals(str);
    }

    /* renamed from: b */
    public static boolean m279b(String str) {
        return "Date".equalsIgnoreCase(str) || "datetime".equalsIgnoreCase(str) || f305h.equalsIgnoreCase(str);
    }

    /* renamed from: a */
    public static String m280a(String str, OnlCgformField onlCgformField, JSONObject jSONObject, Map<String, Object> map) {
        String dbType = onlCgformField.getDbType();
        String dbFieldName = onlCgformField.getDbFieldName();
        String fieldShowType = onlCgformField.getFieldShowType();
        if (jSONObject.get(dbFieldName) == null) {
            return "null";
        }
        if ("int".equals(dbType)) {
            map.put(dbFieldName, Integer.valueOf(jSONObject.getIntValue(dbFieldName)));
            return "#{" + dbFieldName + ",jdbcType=INTEGER}";
        }
        if ("double".equals(dbType)) {
            map.put(dbFieldName, Double.valueOf(jSONObject.getDoubleValue(dbFieldName)));
            return "#{" + dbFieldName + ",jdbcType=DOUBLE}";
        }
        if ("BigDecimal".equals(dbType)) {
            map.put(dbFieldName, new BigDecimal(jSONObject.getString(dbFieldName)));
            return "#{" + dbFieldName + ",jdbcType=DECIMAL}";
        }
        if ("Blob".equals(dbType)) {
            map.put(dbFieldName, jSONObject.getString(dbFieldName) != null ? jSONObject.getString(dbFieldName).getBytes() : null);
            return "#{" + dbFieldName + ",jdbcType=BLOB}";
        }
        if ("Date".equals(dbType) || "datetime".equalsIgnoreCase(dbType)) {
            String string = jSONObject.getString(dbFieldName);
            if ("ORACLE".equals(str)) {
                if (OnlFormShowType.DATE.equals(fieldShowType)) {
                    map.put(dbFieldName, string.length() > 10 ? string.substring(0, 10) : string);
                    return "to_date(#{" + dbFieldName + "},'yyyy-MM-dd')";
                }
                map.put(dbFieldName, string.length() == 10 ? jSONObject.getString(dbFieldName) + " 00:00:00" : string);
                return "to_date(#{" + dbFieldName + "},'yyyy-MM-dd HH24:mi:ss')";
            }
            if ("POSTGRESQL".equals(str)) {
                if (OnlFormShowType.DATE.equals(fieldShowType)) {
                    map.put(dbFieldName, string.length() > 10 ? string.substring(0, 10) : string);
                    return "CAST(#{" + dbFieldName + "} as DATE)";
                }
                map.put(dbFieldName, string.length() == 10 ? jSONObject.getString(dbFieldName) + " 00:00:00" : string);
                return "CAST(#{" + dbFieldName + "} as TIMESTAMP)";
            }
            map.put(dbFieldName, jSONObject.getString(dbFieldName));
            return "#{" + dbFieldName + "}";
        }
        map.put(dbFieldName, jSONObject.getString(dbFieldName));
        return "#{" + dbFieldName + ",jdbcType=VARCHAR}";
    }
}
