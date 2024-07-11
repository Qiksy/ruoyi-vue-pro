package org.jeecg.modules.online.cgform.converter.field;

import org.jeecg.modules.online.cgform.converter.common.ConfigConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* compiled from: TreeSelectConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.j */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/j.class */
public class TreeSelectConverter extends ConfigConvert {
    public TreeSelectConverter(OnlCgformField onlCgformField) {
        String[] split = onlCgformField.getDictText().split(CgformUtil.COMMA_SEPARATOR);
        setTable(onlCgformField.getDictTable());
        setCode(split[0]);
        setText(split[2]);
    }
}
