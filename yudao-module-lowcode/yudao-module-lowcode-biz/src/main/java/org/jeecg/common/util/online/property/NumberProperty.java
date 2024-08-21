package org.jeecg.common.util.online.property;

import com.alibaba.fastjson.JSONObject;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.online.CommonProperty;
import org.jeecg.modules.online.config.template.DataBaseConst;

/* compiled from: NumberProperty.java */
/* renamed from: org.jeecg.common.util.a.a.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a/d.class */
@Data
public class NumberProperty extends CommonProperty {

    /* renamed from: m */
    @Serial
    private static final long serialVersionUID = -558615331436437200L;

    /* renamed from: n */
    private Integer multipleOf;

    /* renamed from: o */
    private Integer maxinum;

    /* renamed from: p */
    private Integer exclusiveMaximum;

    /* renamed from: q */
    private Integer minimum;

    /* renamed from: r */
    private Integer exclusiveMinimum;

    /* renamed from: s */
    private String pattern;

    public NumberProperty(String str, String str2, String str3) {
        this.key = str;
        this.type = str3;
        this.title = str2;
        this.view = DataBaseConst.NUMBER;
    }

    public NumberProperty(String str, String str2, String str3, List<DictModel> list) {
        this.type = "integer";
        this.key = str;
        this.view = str3;
        this.title = str2;
        this.include = list;
    }

    @Override // org.jeecg.common.util.p000a.AbstractC0009b
    public Map<String, Object> getPropertyJson() {
        HashMap<String, Object> hashMap = new HashMap<>(5);
        hashMap.put("key", getKey());
        JSONObject commonJson = getCommonJson();
        if (this.multipleOf != null) {
            commonJson.put("multipleOf", this.multipleOf);
        }
        if (this.maxinum != null) {
            commonJson.put("maxinum", this.maxinum);
        }
        if (this.exclusiveMaximum != null) {
            commonJson.put("exclusiveMaximum", this.exclusiveMaximum);
        }
        if (this.minimum != null) {
            commonJson.put("minimum", this.minimum);
        }
        if (this.exclusiveMinimum != null) {
            commonJson.put("exclusiveMinimum", this.exclusiveMinimum);
        }
        if (this.pattern != null) {
            commonJson.put("pattern", this.pattern);
        }
        hashMap.put("prop", commonJson);
        return hashMap;
    }
}
