package org.jeecg.modules.online.cgform.converter.field;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.jeecg.modules.online.cgform.converter.common.ConfigConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.vo.LinkDown;

/* compiled from: LinkDownConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.e */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/e.class */
public class LinkDownConverter extends ConfigConvert {

    /* renamed from: f */
    private String linkField;

    public String getLinkField() {
        return this.linkField;
    }

    public void setLinkField(String linkField) {
        this.linkField = linkField;
    }

    public LinkDownConverter(OnlCgformField onlCgformField) {
        LinkDown linkDown = (LinkDown) JSONObject.parseObject(onlCgformField.getDictTable(), LinkDown.class);
        setTable(linkDown.getTable());
        setCode(linkDown.getKey());
        setText(linkDown.getTxt());
        this.linkField = linkDown.getLinkField();
    }

    @Override // org.jeecg.modules.online.cgform.converter.p010a.C0029a, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public Map<String, String> getConfig() {
        HashMap hashMap = new HashMap(5);
        hashMap.put("linkField", this.linkField);
        return hashMap;
    }
}
