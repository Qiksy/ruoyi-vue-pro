package org.jeecg.modules.online.cgform.enhance.impl.http;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.enhance.impl.http.base.CgformEnhanceHttpInter;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* compiled from: CgformEnhanceHttpFormImpl.java */
@Component("cgformEnhanceJavaHttpImpl")
/* renamed from: org.jeecg.modules.online.cgform.enhance.impl.http.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/impl/http/a.class */
public class CgformEnhanceHttpFormImpl implements CgformEnhanceHttpInter {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(CgformEnhanceHttpFormImpl.class);

    @Override // org.jeecg.modules.online.cgform.enhance.impl.http.base.CgformEnhanceHttpInter
    public void execute(String tableName, JSONObject record, OnlCgformEnhanceJava enhance) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("tableName", tableName);
        jSONObject.put("record", record);
        Object sendPost = sendPost(jSONObject, enhance);
        if (sendPost != null && oConvertUtils.getInt(sendPost) == null && (sendPost instanceof JSONObject)) {
            JSONObject jSONObject2 = (JSONObject) sendPost;
            oConvertUtils.getInt(jSONObject2.get("code"));
            JSONObject jSONObject3 = jSONObject2.getJSONObject("record");
            if (jSONObject3 != null) {
                record.putAll(jSONObject3);
            }
        }
    }
}
