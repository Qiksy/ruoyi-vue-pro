package org.jeecg.modules.online.cgform.converter.field;

import java.util.HashMap;
import java.util.Map;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.common.ConfigConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* compiled from: CatTreeConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/a.class */
public class CatTreeConverter extends ConfigConvert {

    /* renamed from: f */
    private String treeText;

    public String getTreeText() {
        return this.treeText;
    }

    public void setTreeText(String treeText) {
        this.treeText = treeText;
    }

    public CatTreeConverter(OnlCgformField onlCgformField) {
        super(CgformUtil.f231Z, "ID", CgformUtil.f232aa);
        this.treeText = onlCgformField.getDictText();
        this.dbFieldName = onlCgformField.getDbFieldName();
    }

    @Override // org.jeecg.modules.online.cgform.converter.p010a.C0029a, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public Map<String, String> getConfig() {
        if (oConvertUtils.isEmpty(this.treeText)) {
            return null;
        }
        HashMap<String,String> hashMap = new HashMap<>(5);
        hashMap.put("treeText", this.treeText);
        hashMap.put("field", this.dbFieldName);
        return hashMap;
    }
}
