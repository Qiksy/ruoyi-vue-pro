package org.jeecg.modules.online.cgform.converter;

import java.util.Map;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/FieldCommentConverter.class */
public interface FieldCommentConverter {
    String converterToVal(String str);

    String converterToTxt(String str);

    Map<String, String> getConfig();
}
