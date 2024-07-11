package org.jeecg.modules.online.cgform.converter;

import java.util.Map;
import org.springframework.stereotype.Component;

/* compiled from: CustomDemoConverter.java */
@Component("customDemoConverter")
/* renamed from: org.jeecg.modules.online.cgform.converter.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/c.class */
public class CustomDemoConverter implements FieldCommentConverter {
    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        if ("管理员1".equals(txt)) {
            return "admin";
        }
        return txt;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToTxt(String val) {
        if (val != null) {
            if ("admin".equals(val)) {
                return "管理员1";
            }
            if ("scott".equals(val)) {
                return "管理员2";
            }
        }
        return val;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public Map<String, String> getConfig() {
        return null;
    }
}
