package org.jeecg.common.util.online.property;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jeecg.common.util.online.CommonProperty;
import org.jeecg.common.util.online.BaseColumn;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.config.template.DataBaseConst;

/* compiled from: LinkDownProperty.java */
/* renamed from: org.jeecg.common.util.a.a.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a/c.class */
public class LinkDownProperty extends CommonProperty {

    /* renamed from: m */
    String dictTable;

    /* renamed from: n */
    List<BaseColumn> otherColumns;

    public String getDictTable() {
        return this.dictTable;
    }

    public void setDictTable(String dictTable) {
        this.dictTable = dictTable;
    }

    public List<BaseColumn> getOtherColumns() {
        return this.otherColumns;
    }

    public void setOtherColumns(List<BaseColumn> otherColumns) {
        this.otherColumns = otherColumns;
    }

    public LinkDownProperty() {
    }

    public LinkDownProperty(String str, String str2, String str3) {
        this.type = DataBaseConst.STRING;
        this.view = CgformUtil.f224S;
        this.key = str;
        this.title = str2;
        this.dictTable = str3;
    }

    @Override // org.jeecg.common.util.p000a.AbstractC0009b
    public Map<String, Object> getPropertyJson() {
        HashMap hashMap = new HashMap(5);
        hashMap.put("key", getKey());
        JSONObject commonJson = getCommonJson();
        commonJson.put("config", JSONObject.parseObject(this.dictTable));
        commonJson.put("others", this.otherColumns);
        hashMap.put("prop", commonJson);
        return hashMap;
    }
}
