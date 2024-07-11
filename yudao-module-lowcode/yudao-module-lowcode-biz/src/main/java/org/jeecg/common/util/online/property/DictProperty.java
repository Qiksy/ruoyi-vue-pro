package org.jeecg.common.util.online.property;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.jeecg.common.util.online.CommonProperty;
import org.jeecg.modules.online.config.template.DataBaseConst;

/* compiled from: DictProperty.java */
/* renamed from: org.jeecg.common.util.a.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a/a.class */
public class DictProperty extends CommonProperty {

    /* renamed from: m */
    private static final long f3m = 3786503639885610767L;

    /* renamed from: n */
    private String dictCode;

    /* renamed from: o */
    private String dictTable;

    /* renamed from: p */
    private String dictText;

    public String getDictCode() {
        return this.dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictTable() {
        return this.dictTable;
    }

    public void setDictTable(String dictTable) {
        this.dictTable = dictTable;
    }

    public String getDictText() {
        return this.dictText;
    }

    public void setDictText(String dictText) {
        this.dictText = dictText;
    }

    public DictProperty() {
    }

    public DictProperty(String str, String str2, String str3, String str4, String str5) {
        this.type = DataBaseConst.STRING;
        this.view = "sel_search";
        this.key = str;
        this.title = str2;
        this.dictCode = str4;
        this.dictTable = str3;
        this.dictText = str5;
    }

    public DictProperty(String str, String str2, String str3, String str4, String str5, String str6) {
        this.type = DataBaseConst.STRING;
        this.view = str2;
        this.key = str;
        this.title = str3;
        this.dictCode = str5;
        this.dictTable = str4;
        this.dictText = str6;
    }

    @Override // org.jeecg.common.util.p000a.AbstractC0009b
    public Map<String, Object> getPropertyJson() {
        HashMap hashMap = new HashMap(5);
        hashMap.put("key", getKey());
        JSONObject commonJson = getCommonJson();
        if (this.dictCode != null) {
            commonJson.put("dictCode", this.dictCode);
        }
        if (this.dictTable != null) {
            commonJson.put("dictTable", this.dictTable);
        }
        if (this.dictText != null) {
            commonJson.put("dictText", this.dictText);
        }
        hashMap.put("prop", commonJson);
        return hashMap;
    }
}
