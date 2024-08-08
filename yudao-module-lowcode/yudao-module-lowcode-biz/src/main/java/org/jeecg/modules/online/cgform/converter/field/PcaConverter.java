package org.jeecg.modules.online.cgform.converter.field;

import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;

import org.jeecg.modules.online.cgform.converter.common.ForeseeConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: PcaConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.h */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/h.class */
public class PcaConverter extends ForeseeConvert {

    /**
     * 省份区域
     */
    /* renamed from: c */
    ProvinceCityArea cityArea;

    public PcaConverter(OnlCgformField onlCgformField) {
        this.field = onlCgformField.getDbFieldName();
        this.cityArea = SpringContextUtils.getBean(ProvinceCityArea.class);
    }

    @Override // org.jeecg.modules.online.cgform.converter.p010a.C0030b, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        if (StrUtils.isEmpty(txt)) {
            return null;
        }
        return this.cityArea.getCode(txt);
    }

    @Override // org.jeecg.modules.online.cgform.converter.p010a.C0030b, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToTxt(String val) {
        if (StrUtils.isEmpty(val)) {
            return null;
        }
        return this.cityArea.getText(val);
    }
}
