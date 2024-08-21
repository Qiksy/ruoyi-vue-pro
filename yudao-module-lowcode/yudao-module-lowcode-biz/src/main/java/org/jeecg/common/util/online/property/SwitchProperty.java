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


    public SwitchProperty(String key, String title, String fieldExtendJson2) {
        this.type = DataBaseConst.STRING;
        this.view = CgformUtil.f217L;
        this.key = key;
        this.title = title;
        this.fieldExtendJson2 = fieldExtendJson2;
    }

    @Override // org.jeecg.common.util.p000a.AbstractC0009b
    public Map<String, Object> getPropertyJson() {
        HashMap<String, Object> hashMap = new HashMap<>(5);
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
