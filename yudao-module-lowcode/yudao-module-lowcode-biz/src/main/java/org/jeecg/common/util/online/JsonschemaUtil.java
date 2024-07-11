package org.jeecg.common.util.online;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: JsonschemaUtil.java */
/* renamed from: org.jeecg.common.util.a.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/d.class */
public class JsonschemaUtil {

    /* renamed from: a */
    private static final Logger f54a = LoggerFactory.getLogger(JsonschemaUtil.class);

    /* renamed from: a */
    public static JSONObject m2a(JsonSchemaDescrip jsonSchemaDescrip, List<CommonProperty> list) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("$schema", jsonSchemaDescrip.get$schema());
        jSONObject.put("type", jsonSchemaDescrip.getType());
        jSONObject.put(CgformUtil.TITLE, jsonSchemaDescrip.getTitle());
        jSONObject.put("required", jsonSchemaDescrip.getRequired());
        JSONObject jSONObject2 = new JSONObject();
        Iterator<CommonProperty> it = list.iterator();
        while (it.hasNext()) {
            Map<String, Object> propertyJson = it.next().getPropertyJson();
            jSONObject2.put(propertyJson.get("key").toString(), propertyJson.get("prop"));
        }
        jSONObject.put(CgformUtil.PROPERTIES, jSONObject2);
        return jSONObject;
    }

    /* renamed from: a */
    public static JSONObject m3a(String str, List<String> list, List<CommonProperty> list2) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", "object");
        jSONObject.put(CgformUtil.VIEW, "tab");
        jSONObject.put(CgformUtil.TITLE, str);
        if (list == null) {
            list = new ArrayList();
        }
        jSONObject.put("required", list);
        JSONObject jSONObject2 = new JSONObject();
        Iterator<CommonProperty> it = list2.iterator();
        while (it.hasNext()) {
            Map<String, Object> propertyJson = it.next().getPropertyJson();
            jSONObject2.put(propertyJson.get("key").toString(), propertyJson.get("prop"));
        }
        jSONObject.put(CgformUtil.PROPERTIES, jSONObject2);
        return jSONObject;
    }
}
