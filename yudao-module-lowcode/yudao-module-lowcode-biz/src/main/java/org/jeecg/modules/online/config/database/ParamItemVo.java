package org.jeecg.modules.online.config.database;

import com.alibaba.fastjson.JSONArray;
import java.util.Map;
import org.jeecg.modules.online.cgreport.constant.CgReportConstant;

/* compiled from: ParamItemVo.java */
/* renamed from: org.jeecg.modules.online.config.b.f */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/b/f.class */
public class ParamItemVo {

    /* renamed from: a */
    private String fieldTxt;

    /* renamed from: b */
    private String fieldName;

    /* renamed from: c */
    private String fieldType;

    /* renamed from: d */
    private String searchMode;

    /* renamed from: e */
    private Object value;

    public void setFieldTxt(String fieldTxt) {
        this.fieldTxt = fieldTxt;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ParamItemVo)) {
            return false;
        }
        ParamItemVo paramItemVo = (ParamItemVo) o;
        if (!paramItemVo.m453a(this)) {
            return false;
        }
        String fieldTxt = getFieldTxt();
        String fieldTxt2 = paramItemVo.getFieldTxt();
        if (fieldTxt == null) {
            if (fieldTxt2 != null) {
                return false;
            }
        } else if (!fieldTxt.equals(fieldTxt2)) {
            return false;
        }
        String fieldName = getFieldName();
        String fieldName2 = paramItemVo.getFieldName();
        if (fieldName == null) {
            if (fieldName2 != null) {
                return false;
            }
        } else if (!fieldName.equals(fieldName2)) {
            return false;
        }
        String fieldType = getFieldType();
        String fieldType2 = paramItemVo.getFieldType();
        if (fieldType == null) {
            if (fieldType2 != null) {
                return false;
            }
        } else if (!fieldType.equals(fieldType2)) {
            return false;
        }
        String searchMode = getSearchMode();
        String searchMode2 = paramItemVo.getSearchMode();
        if (searchMode == null) {
            if (searchMode2 != null) {
                return false;
            }
        } else if (!searchMode.equals(searchMode2)) {
            return false;
        }
        Object value = getValue();
        Object value2 = paramItemVo.getValue();
        return value == null ? value2 == null : value.equals(value2);
    }

    /* renamed from: a */
    protected boolean m453a(Object obj) {
        return obj instanceof ParamItemVo;
    }

    public int hashCode() {
        String fieldTxt = getFieldTxt();
        int hashCode = (1 * 59) + (fieldTxt == null ? 43 : fieldTxt.hashCode());
        String fieldName = getFieldName();
        int hashCode2 = (hashCode * 59) + (fieldName == null ? 43 : fieldName.hashCode());
        String fieldType = getFieldType();
        int hashCode3 = (hashCode2 * 59) + (fieldType == null ? 43 : fieldType.hashCode());
        String searchMode = getSearchMode();
        int hashCode4 = (hashCode3 * 59) + (searchMode == null ? 43 : searchMode.hashCode());
        Object value = getValue();
        return (hashCode4 * 59) + (value == null ? 43 : value.hashCode());
    }

    public String toString() {
        return "ParamItemVo(fieldTxt=" + getFieldTxt() + ", fieldName=" + getFieldName() + ", fieldType=" + getFieldType() + ", searchMode=" + getSearchMode() + ", value=" + getValue() + ")";
    }

    public String getFieldTxt() {
        return this.fieldTxt;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public String getSearchMode() {
        return this.searchMode;
    }

    public Object getValue() {
        return this.value;
    }

    /* renamed from: a */
    public void m452a(Map<String, Object> map) {
        if (this.value == null) {
            return;
        }
        map.put(this.fieldName, this.value);
        if (CgReportConstant.f467K.equals(this.searchMode)) {
            JSONArray jSONArray = (JSONArray) this.value;
            map.put(this.fieldName + "_begin", jSONArray.get(0));
            map.put(this.fieldName + "_end", jSONArray.get(1));
        }
    }
}
