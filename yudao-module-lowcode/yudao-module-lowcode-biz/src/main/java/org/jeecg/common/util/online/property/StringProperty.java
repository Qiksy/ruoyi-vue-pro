package org.jeecg.common.util.online.property;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.online.CommonProperty;
import org.jeecg.modules.online.config.template.DataBaseConst;

/* compiled from: StringProperty.java */
/* renamed from: org.jeecg.common.util.a.a.f */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a/f.class */
@Data
public class StringProperty extends CommonProperty {

    /* renamed from: m */
    private static final long f22m = -3200493311633999539L;

    /* renamed from: n */
    private Integer maxLength;

    /* renamed from: o */
    private Integer minLength;

    /* renamed from: p */
    private String pattern;

    /* renamed from: q */
    private String errorInfo;


    public StringProperty(String str, String str2, String str3, Integer num) {
        this.maxLength = num;
        this.key = str;
        this.view = str3;
        this.title = str2;
        this.type = DataBaseConst.STRING;
    }

    public StringProperty(String str, String str2, String str3, Integer num, List<DictModel> list) {
        this.maxLength = num;
        this.key = str;
        this.view = str3;
        this.title = str2;
        this.type = DataBaseConst.STRING;
        this.include = list;
    }

    @Override // org.jeecg.common.util.p000a.AbstractC0009b
    public Map<String, Object> getPropertyJson() {
        HashMap hashMap = new HashMap(5);
        hashMap.put("key", getKey());
        JSONObject commonJson = getCommonJson();
        if (this.maxLength != null) {
            commonJson.put("maxLength", this.maxLength);
        }
        if (this.minLength != null) {
            commonJson.put("minLength", this.minLength);
        }
        if (this.pattern != null) {
            commonJson.put("pattern", this.pattern);
        }
        if (this.errorInfo != null) {
            commonJson.put("errorInfo", this.errorInfo);
        }
        hashMap.put("prop", commonJson);
        return hashMap;
    }
}
