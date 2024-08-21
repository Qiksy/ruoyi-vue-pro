package org.jeecg.common.util.online;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* compiled from: JsonschemaUtil.java */
/* renamed from: org.jeecg.common.util.a.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/d.class */
@Slf4j
public class JsonschemaUtil {


    public static JSONObject createJsonschema(JsonSchemaDescrip jsonSchemaDescrip, List<CommonProperty> list) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("$schema", jsonSchemaDescrip.getSchema());
        jSONObject.put("type", jsonSchemaDescrip.getType());
        jSONObject.put(CgformUtil.TITLE, jsonSchemaDescrip.getTitle());
        jSONObject.put("required", jsonSchemaDescrip.getRequired());
        JSONObject properties = new JSONObject();
        for (CommonProperty commonProperty : list) {
            Map<String, Object> propertyJson = commonProperty.getPropertyJson();
            properties.put(propertyJson.get("key").toString(), propertyJson.get("prop"));
        }
        jSONObject.put(CgformUtil.PROPERTIES, properties);
        return jSONObject;
    }

    /* renamed from: a */
    public static JSONObject m3a(String str, List<String> required, List<CommonProperty> list2) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", "object");
        jSONObject.put(CgformUtil.VIEW, "tab");
        jSONObject.put(CgformUtil.TITLE, str);
        if (required == null) {
            required = new ArrayList<>();
        }
        jSONObject.put("required", required);
        JSONObject properties = new JSONObject();
        for (CommonProperty commonProperty : list2) {
            Map<String, Object> propertyJson = commonProperty.getPropertyJson();
            properties.put(propertyJson.get("key").toString(), propertyJson.get("prop"));
        }
        jSONObject.put(CgformUtil.PROPERTIES, properties);
        return jSONObject;
    }
}
