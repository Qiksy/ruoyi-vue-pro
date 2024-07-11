package org.jeecg.modules.online.cgform.converter.field;

import org.jeecg.modules.online.cgform.converter.common.ConfigConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: DictTableConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/d.class */
public class DictTableConverter extends ConfigConvert {
    public DictTableConverter(OnlCgformField onlCgformField) {
        super(onlCgformField.getDictTable(), onlCgformField.getDictField(), onlCgformField.getDictText());
    }
}
