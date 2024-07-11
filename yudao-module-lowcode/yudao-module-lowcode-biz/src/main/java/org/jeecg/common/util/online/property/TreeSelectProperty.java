package org.jeecg.common.util.online.property;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.jeecg.common.util.online.CommonProperty;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.config.template.DataBaseConst;

/* compiled from: TreeSelectProperty.java */
/* renamed from: org.jeecg.common.util.a.a.h */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a/h.class */
public class TreeSelectProperty extends CommonProperty {

    /* renamed from: m */
    private static final long f28m = 3786503639885610767L;

    /* renamed from: n */
    private String dict;

    /* renamed from: o */
    private String pidField;

    /* renamed from: p */
    private String pidValue;

    /* renamed from: q */
    private String hasChildField;

    /* renamed from: r */
    private String textField;

    /* renamed from: s */
    private Integer pidComponent;

    public String getDict() {
        return this.dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public String getPidField() {
        return this.pidField;
    }

    public void setPidField(String pidField) {
        this.pidField = pidField;
    }

    public String getPidValue() {
        return this.pidValue;
    }

    public void setPidValue(String pidValue) {
        this.pidValue = pidValue;
    }

    public String getHasChildField() {
        return this.hasChildField;
    }

    public void setHasChildField(String hasChildField) {
        this.hasChildField = hasChildField;
    }

    public TreeSelectProperty() {
        this.pidComponent = 0;
    }

    public String getTextField() {
        return this.textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }

    public Integer getPidComponent() {
        return this.pidComponent;
    }

    public void setPidComponent(Integer pidComponent) {
        this.pidComponent = pidComponent;
    }

    public TreeSelectProperty(String str, String str2, String str3, String str4, String str5) {
        this.pidComponent = 0;
        this.type = DataBaseConst.STRING;
        this.view = CgformUtil.f222Q;
        this.key = str;
        this.title = str2;
        this.dict = str3;
        this.pidField = str4;
        this.pidValue = str5;
    }

    public TreeSelectProperty(String str, String str2, String str3) {
        this.pidComponent = 0;
        this.type = DataBaseConst.STRING;
        this.view = CgformUtil.f223R;
        this.key = str;
        this.title = str2;
        this.pidValue = str3;
    }

    public TreeSelectProperty(String str, String str2, String str3, String str4) {
        this(str, str2, str3);
        this.textField = str4;
    }

    @Override // org.jeecg.common.util.p000a.AbstractC0009b
    public Map<String, Object> getPropertyJson() {
        HashMap<String,Object> hashMap = new HashMap<>(5);
        hashMap.put("key", getKey());
        JSONObject commonJson = getCommonJson();
        if (this.dict != null) {
            commonJson.put("dict", this.dict);
        }
        if (this.pidField != null) {
            commonJson.put("pidField", this.pidField);
        }
        if (this.pidValue != null) {
            commonJson.put("pidValue", this.pidValue);
        }
        if (this.textField != null) {
            commonJson.put(ExtendJsonKey.f130j, this.textField);
        }
        if (this.hasChildField != null) {
            commonJson.put("hasChildField", this.hasChildField);
        }
        if (this.pidComponent != null) {
            commonJson.put("pidComponent", this.pidComponent);
        }
        hashMap.put("prop", commonJson);
        return hashMap;
    }
}
