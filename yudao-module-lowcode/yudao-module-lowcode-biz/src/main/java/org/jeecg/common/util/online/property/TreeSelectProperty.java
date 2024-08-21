package org.jeecg.common.util.online.property;

import com.alibaba.fastjson.JSONObject;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import org.jeecg.common.util.online.CommonProperty;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.config.template.DataBaseConst;

/* compiled from: TreeSelectProperty.java */
/* renamed from: org.jeecg.common.util.a.a.h */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a/h.class */
@Data
public class TreeSelectProperty extends CommonProperty {

    /* renamed from: m */
    @Serial
    private static final long serialVersionUID = 3786503639885610767L;

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
            commonJson.put(ExtendJsonKey.TEXT_FIELD, this.textField);
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
