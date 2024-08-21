package org.jeecg.common.util.online;

import com.alibaba.fastjson.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* compiled from: CommonProperty.java */
/* renamed from: org.jeecg.common.util.a.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/b.class */
@Data
public abstract class CommonProperty implements Serializable {

    /* renamed from: m */
    @Serial
    private static final long serialVersionUID = -426159949502493187L;

    /* renamed from: a */
    protected String key;

    /* renamed from: b */
    protected String type;

    /* renamed from: c */
    protected List<DictModel> include;

    /* renamed from: d */
    protected Object constant;

    /* renamed from: e */
    protected String view;

    /* renamed from: f */
    protected String title;

    /* renamed from: g */
    protected Integer order;

    /* renamed from: h */
    protected boolean disabled;

    /* renamed from: i */
    protected String defVal;

    /* renamed from: j */
    protected String fieldExtendJson;

    /* renamed from: k */
    protected Integer dbPointLength;

    /* renamed from: l */
    protected String mode;

    public abstract Map<String, Object> getPropertyJson();


    public JSONObject getCommonJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", this.type);
        if (this.include != null && !this.include.isEmpty()) {
            jSONObject.put("enum", this.include);
        }
        if (this.constant != null) {
            jSONObject.put("const", this.constant);
        }
        if (this.title != null) {
            jSONObject.put(CgformUtil.TITLE, this.title);
        }
        if (this.order != null) {
            jSONObject.put("order", this.order);
        }
        if (this.view == null) {
            jSONObject.put(CgformUtil.VIEW, "input");
        } else {
            jSONObject.put(CgformUtil.VIEW, this.view);
        }
        if (this.disabled) {
            jSONObject.put("ui", JSONObject.parseObject("{\"widgetattrs\":{\"disabled\":true}}"));
        }
        if (this.defVal != null && !this.defVal.isEmpty()) {
            jSONObject.put("defVal", this.defVal);
        }
        if (this.fieldExtendJson != null) {
            jSONObject.put("fieldExtendJson", this.fieldExtendJson);
        }
        if (this.dbPointLength != null) {
            jSONObject.put("dbPointLength", this.dbPointLength);
        }
        if (this.mode != null) {
            jSONObject.put("mode", this.mode);
        }
        return jSONObject;
    }
}
