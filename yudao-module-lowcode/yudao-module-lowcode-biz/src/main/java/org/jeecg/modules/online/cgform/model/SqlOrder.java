package org.jeecg.modules.online.cgform.model;

import org.jeecg.common.util.oConvertUtils;

/* compiled from: SqlOrder.java */
/* renamed from: org.jeecg.modules.online.cgform.model.h */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/h.class */
public class SqlOrder {

    /* renamed from: a */
    public static final String f404a = "asc";

    /* renamed from: b */
    public static final String f405b = "desc";

    /* renamed from: c */
    public static final String f406c = " ORDER BY ";

    /* renamed from: d */
    public static final String f407d = "ID";

    /* renamed from: e */
    private String column;

    /* renamed from: f */
    private String rule;

    /* renamed from: g */
    private String alias;

    /* renamed from: a */
    public static SqlOrder m295a() {
        return m296a("");
    }

    /* renamed from: a */
    public static SqlOrder m296a(String str) {
        SqlOrder sqlOrder = new SqlOrder("ID");
        sqlOrder.setAlias(str);
        return sqlOrder;
    }

    public String getRealSql() {
        String str;
        String str2 = this.alias + oConvertUtils.camelToUnderline(this.column);
        if ("asc".equals(this.rule)) {
            str = str2 + " asc";
        } else {
            str = str2 + " desc";
        }
        return str;
    }

    public SqlOrder() {
    }

    public SqlOrder(String str, String str2) {
        this.column = str;
        this.rule = str2;
        this.alias = "";
    }

    public SqlOrder(String str) {
        this.rule = "desc";
        this.column = str;
        this.alias = "";
    }

    public String getColumn() {
        return this.column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getRule() {
        return this.rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
