package org.jeecg.modules.online.cgform.converter.field;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.extra.spring.SpringUtil;

import org.jeecg.common.system.vo.DictModel;


import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.modules.online.cgform.converter.common.ForeseeConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* compiled from: MultiSelectConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.g */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/g.class */
public class MultiSelectConverter extends ForeseeConvert {
    public MultiSelectConverter(OnlCgformField onlCgformField) {
        ISysBaseAPI iSysBaseAPI = SpringUtil.getBean(ISysBaseAPI.class);
        String dictTable = onlCgformField.getDictTable();
        String dictText = onlCgformField.getDictText();
        String dictField = onlCgformField.getDictField();
        List<DictModel> dictModelList = new ArrayList<>();
        if (ConvertUtils.isNotEmpty(dictTable)) {
            dictModelList =  iSysBaseAPI.queryTableDictItemsByCode(dictTable, dictText, dictField);
        } else if (ConvertUtils.isNotEmpty(dictField)) {
            dictModelList =  iSysBaseAPI.queryDictItemsByCode(dictField);
        }
        this.dictlList = dictModelList;
        this.field = onlCgformField.getDbFieldName();
    }

    @Override // org.jeecg.modules.online.cgform.converter.p010a.C0030b, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        if (ConvertUtils.isEmpty(txt)) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : txt.split(CgformUtil.COMMA_SEPARATOR)) {
            String converterToVal = super.converterToVal(str);
            if (converterToVal != null) {
                arrayList.add(converterToVal);
            }
        }
        return String.join(CgformUtil.COMMA_SEPARATOR, arrayList);
    }

    @Override // org.jeecg.modules.online.cgform.converter.p010a.C0030b, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToTxt(String val) {
        if (ConvertUtils.isEmpty(val)) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : val.split(CgformUtil.COMMA_SEPARATOR)) {
            String converterToTxt = super.converterToTxt(str);
            if (converterToTxt != null) {
                arrayList.add(converterToTxt);
            }
        }
        return String.join(CgformUtil.COMMA_SEPARATOR, arrayList);
    }
}
