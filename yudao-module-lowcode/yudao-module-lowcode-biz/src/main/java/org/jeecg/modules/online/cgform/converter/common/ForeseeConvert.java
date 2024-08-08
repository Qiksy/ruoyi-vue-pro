package org.jeecg.modules.online.cgform.converter.common;

import java.util.List;
import java.util.Map;
import org.jeecg.common.system.vo.DictModel;

import org.jeecg.modules.online.cgform.converter.FieldCommentConverter;

/* compiled from: ForeseeConvert.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.a.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/a/b.class */
public class ForeseeConvert implements FieldCommentConverter {

    /* renamed from: a */
    protected String field;

    /* renamed from: b */
    protected List<DictModel> dictlList;

    public String getFiled() {
        return this.field;
    }

    public void setFiled(String filed) {
        this.field = filed;
    }

    public List<DictModel> getDictList() {
        return this.dictlList;
    }

    public void setDictList(List<DictModel> dictList) {
        this.dictlList = dictList;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        if (StrUtils.isNotEmpty(txt)) {
            for (DictModel dictModel : this.dictlList) {
                if (dictModel.getText().equals(txt)) {
                    return dictModel.getValue();
                }
            }
            return null;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToTxt(String val) {
        if (StrUtils.isNotEmpty(val)) {
            for (DictModel dictModel : this.dictlList) {
                if (dictModel.getValue() != null && dictModel.getValue().equals(val)) {
                    return dictModel.getText();
                }
            }
            return null;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public Map<String, String> getConfig() {
        return null;
    }
}
