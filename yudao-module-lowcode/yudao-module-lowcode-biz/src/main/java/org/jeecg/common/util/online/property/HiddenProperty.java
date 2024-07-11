package org.jeecg.common.util.online.property;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.jeecg.common.util.online.CommonProperty;
import org.jeecg.modules.online.config.template.DataBaseConst;

/* compiled from: HiddenProperty.java */
/* renamed from: org.jeecg.common.util.a.a.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a/b.class */
public class HiddenProperty extends CommonProperty {

    /* renamed from: m */
    private static final long f7m = -8939298551502162479L;

    public HiddenProperty() {
    }

    public HiddenProperty(String str, String str2) {
        this.type = DataBaseConst.STRING;
        this.view = "hidden";
        this.key = str;
        this.title = str2;
    }

    @Override // org.jeecg.common.util.p000a.AbstractC0009b
    public Map<String, Object> getPropertyJson() {
        HashMap hashMap = new HashMap(5);
        hashMap.put("key", getKey());
        JSONObject commonJson = getCommonJson();
        commonJson.put("hidden", true);
        hashMap.put("prop", commonJson);
        return hashMap;
    }
}
