package org.jeecg.common.util.online.property;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.jeecg.common.util.online.CommonProperty;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.config.template.DataBaseConst;

/* compiled from: SwitchProperty.java */
/* renamed from: org.jeecg.common.util.a.a.g */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a/g.class */
public class SwitchProperty extends CommonProperty {

    /* renamed from: m */
    private String fieldExtendJson2;

    public SwitchProperty() {
    }

    public SwitchProperty(String str, String str2, String str3) {
        this.type = DataBaseConst.STRING;
        this.view = CgformUtil.f217L;
        this.key = str;
        this.title = str2;
        this.fieldExtendJson2 = str3;
    }

    @Override // org.jeecg.common.util.p000a.AbstractC0009b
    public Map<String, Object> getPropertyJson() {
        HashMap hashMap = new HashMap(5);
        hashMap.put("key", getKey());
        JSONObject commonJson = getCommonJson();
        new JSONArray();
        if (this.fieldExtendJson2 != null) {
            commonJson.put("extendOption", JSONArray.parseArray(this.fieldExtendJson2));
        }
        hashMap.put("prop", commonJson);
        return hashMap;
    }
}
