package org.jeecg.modules.online.cgform.enhance.impl.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgform.enhance.impl.http.base.CgformEnhanceHttpInter;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* compiled from: CgformEnhanceHttpListImpl.java */
@Component("cgformEnhanceJavaListHttpImpl")
/* renamed from: org.jeecg.modules.online.cgform.enhance.impl.http.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/impl/http/b.class */
public class CgformEnhanceHttpListImpl implements CgformEnhanceHttpInter {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(CgformEnhanceHttpListImpl.class);

    @Override // org.jeecg.modules.online.cgform.enhance.impl.http.base.CgformEnhanceHttpInter
    public void execute(String tableName, List<Map<String, Object>> dataList, OnlCgformEnhanceJava enhance) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("tableName", tableName);
        jSONObject.put("dataList", dataList);
        Object sendPost = sendPost(jSONObject, enhance);
        if (sendPost instanceof JSONArray jSONArray) {
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).putAll(jSONArray.getJSONObject(i));
            }
        }
    }
}
