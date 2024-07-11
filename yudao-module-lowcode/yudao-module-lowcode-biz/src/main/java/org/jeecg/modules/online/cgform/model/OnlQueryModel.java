package org.jeecg.modules.online.cgform.model;

import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: OnlQueryModel.java */
/* renamed from: org.jeecg.modules.online.cgform.model.e */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/e.class */
public class OnlQueryModel {

    /* renamed from: a */
    private String sql;

    /* renamed from: b */
    private Map<String, Object> params;

    /* renamed from: c */
    private Map<String, String> tableAliasMap;

    /* renamed from: d */
    private List<OnlCgformField> fieldList;

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void setTableAliasMap(Map<String, String> tableAliasMap) {
        this.tableAliasMap = tableAliasMap;
    }

    public void setFieldList(List<OnlCgformField> fieldList) {
        this.fieldList = fieldList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlQueryModel)) {
            return false;
        }
        OnlQueryModel onlQueryModel = (OnlQueryModel) o;
        if (!onlQueryModel.m291a(this)) {
            return false;
        }
        String sql = getSql();
        String sql2 = onlQueryModel.getSql();
        if (sql == null) {
            if (sql2 != null) {
                return false;
            }
        } else if (!sql.equals(sql2)) {
            return false;
        }
        Map<String, Object> params = getParams();
        Map<String, Object> params2 = onlQueryModel.getParams();
        if (params == null) {
            if (params2 != null) {
                return false;
            }
        } else if (!params.equals(params2)) {
            return false;
        }
        Map<String, String> tableAliasMap = getTableAliasMap();
        Map<String, String> tableAliasMap2 = onlQueryModel.getTableAliasMap();
        if (tableAliasMap == null) {
            if (tableAliasMap2 != null) {
                return false;
            }
        } else if (!tableAliasMap.equals(tableAliasMap2)) {
            return false;
        }
        List<OnlCgformField> fieldList = getFieldList();
        List<OnlCgformField> fieldList2 = onlQueryModel.getFieldList();
        return fieldList == null ? fieldList2 == null : fieldList.equals(fieldList2);
    }

    /* renamed from: a */
    protected boolean m291a(Object obj) {
        return obj instanceof OnlQueryModel;
    }

    public int hashCode() {
        String sql = getSql();
        int hashCode = (1 * 59) + (sql == null ? 43 : sql.hashCode());
        Map<String, Object> params = getParams();
        int hashCode2 = (hashCode * 59) + (params == null ? 43 : params.hashCode());
        Map<String, String> tableAliasMap = getTableAliasMap();
        int hashCode3 = (hashCode2 * 59) + (tableAliasMap == null ? 43 : tableAliasMap.hashCode());
        List<OnlCgformField> fieldList = getFieldList();
        return (hashCode3 * 59) + (fieldList == null ? 43 : fieldList.hashCode());
    }

    public String toString() {
        return "OnlQueryModel(sql=" + getSql() + ", params=" + getParams() + ", tableAliasMap=" + getTableAliasMap() + ", fieldList=" + getFieldList() + ")";
    }

    public String getSql() {
        return this.sql;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public Map<String, String> getTableAliasMap() {
        return this.tableAliasMap;
    }

    public List<OnlCgformField> getFieldList() {
        return this.fieldList;
    }

    public OnlQueryModel() {
    }

    public OnlQueryModel(String str, Map<String, Object> map) {
        this.sql = str;
        this.params = map;
    }
}
