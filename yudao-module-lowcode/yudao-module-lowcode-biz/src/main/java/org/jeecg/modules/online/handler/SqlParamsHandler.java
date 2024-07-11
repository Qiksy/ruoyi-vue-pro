package org.jeecg.modules.online.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;

/* compiled from: SqlParamsHandler.java */
/* renamed from: org.jeecg.modules.online.a.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/a/b.class */
public class SqlParamsHandler<T> {

    /* renamed from: b */
    private final Map<String, Object> f86b;

    /* renamed from: c */
    private final List<T> f87c;

    /* renamed from: a */
    private final String URL = "url";

    /* renamed from: d */
    private final Map<String, Object> selfSqlParams = new HashMap<>();

    /* renamed from: e */
    private final Map<String, Object> otherParams = new HashMap<>();

    public SqlParamsHandler(Map<String, Object> map, List<T> list) {
        this.f86b = map;
        this.f87c = list;
    }

    /* renamed from: a */
    public String m47a(String str) {
        return m50a(str, ConditionHandler.f62b);
    }

    /* renamed from: b */
    public String m48b(String str) {
        return m50a(str, ConditionHandler.f61a);
    }

    /* renamed from: c */
    public String m49c(String str) {
        Objects.requireNonNull(this);
        return m50a(str, URL);
    }

    /* renamed from: a */
    private String m50a(String str, String str2) {
        String str3;
        String replaceAll;
        if (this.f87c == null || this.f87c.isEmpty()) {
            return str;
        }
        for (T t : this.f87c) {
            String m51a = m51a(t);
            String m52b = m52b(t);
            Object obj = this.f86b.get("self_" + m51a);
            Object obj2 = this.f86b.get(m51a);
            String str4 = "";
            if (oConvertUtils.isNotEmpty(obj)) {
                str4 = obj.toString();
            } else if (oConvertUtils.isNotEmpty(obj2)) {
                str4 = obj2.toString();
            } else if (oConvertUtils.isNotEmpty(m52b)) {
                str4 = m52b;
            }
            String str5 = "${" + m51a + "}";
            if (str.indexOf(str5) > 0) {
                if (str4.startsWith(CgformUtil.SINGLE_QUOTE) && str4.endsWith(CgformUtil.SINGLE_QUOTE)) {
                    str4 = str4.substring(1, str4.length() - 1);
                }
                Objects.requireNonNull(this);
                if ("url".equals(str2)) {
                    str = str.replace(str5, str4);
                } else {
                    String str6 = "_sql_param_" + m51a;
                    if (ConditionHandler.f61a.equals(str2)) {
                        str3 = ":" + str6;
                    } else {
                        str3 = "#{param." + str6 + "}";
                    }
                    Matcher matcher = Pattern.compile("'([^']*)\\$\\{" + m51a + "}([^']*)'").matcher(str);
                    if (matcher.find()) {
                        str4 = matcher.group(1) + str4 + matcher.group(2);
                        replaceAll = str.replace(matcher.group(0), str3);
                    } else {
                        replaceAll = str.replaceAll("'?\\$\\{" + m51a + "}'?", str3);
                    }
                    str = replaceAll;
                    this.selfSqlParams.put(str6, str4);
                }
            } else if (oConvertUtils.isNotEmpty(str4) && (t instanceof OnlCgreportParam)) {
                this.selfSqlParams.put(m51a, obj);
                this.f86b.put("popup_param_pre__" + m51a, str4);
            }
        }
        return str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a */
    private String m51a(T t) {
        if (t instanceof OnlCgreportParam) {
            return ((OnlCgreportParam) t).getParamName();
        }
        throw new JeecgBootException("不支持的类型：" + t.getClass().getName());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: b */
    private String m52b(T t) {
        if (t instanceof OnlCgreportParam) {
            return ((OnlCgreportParam) t).getParamValue();
        }
        throw new JeecgBootException("不支持的类型：" + t.getClass().getName());
    }

    public Map<String, Object> getSelfSqlParams() {
        return this.selfSqlParams;
    }

    public Map<String, Object> getOtherParams() {
        return this.otherParams;
    }
}
