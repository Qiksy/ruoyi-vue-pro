package org.jeecg.common.util.online.property;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import org.jeecg.common.util.online.CommonProperty;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.config.template.DataBaseConst;

/* compiled from: PopupProperty.java */
/* renamed from: org.jeecg.common.util.a.a.e */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a/e.class */
@Data
public class PopupProperty extends CommonProperty {

    /* renamed from: m */
    private static final long f17m = -3200493311633999539L;

    /* renamed from: n */
    private String code;

    /* renamed from: o */
    private String destFields;

    /* renamed from: p */
    private String orgFields;

    /* renamed from: q */
    private Boolean popupMulti;


    public PopupProperty(String str, String str2, String str3, String str4, String str5) {
        this.view = CgformUtil.f218M;
        this.type = DataBaseConst.STRING;
        this.key = str;
        this.title = str2;
        this.code = str3;
        this.destFields = str4;
        this.orgFields = str5;
        this.popupMulti = true;
    }

    @Override // org.jeecg.common.util.p000a.AbstractC0009b
    public Map<String, Object> getPropertyJson() {
        HashMap<String, Object> hashMap = new HashMap<>(5);
        hashMap.put("key", getKey());
        JSONObject commonJson = getCommonJson();
        if (this.code != null) {
            commonJson.put("code", this.code);
        }
        if (this.destFields != null) {
            commonJson.put("destFields", this.destFields);
        }
        if (this.orgFields != null) {
            commonJson.put("orgFields", this.orgFields);
        }
        commonJson.put(ExtendJsonKey.POPUP_MULTI, this.popupMulti);
        hashMap.put("prop", commonJson);
        return hashMap;
    }
}
