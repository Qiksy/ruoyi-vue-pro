package org.jeecg.modules.online.cgform.converter.field;

import java.util.ArrayList;
import java.util.List;

import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SpringContextUtils;

import org.jeecg.modules.online.cgform.converter.common.ForeseeConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: DictEasyConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/c.class */
public class DictEasyConverter extends ForeseeConvert {
    public DictEasyConverter(OnlCgformField onlCgformField) {
        ISysBaseAPI iSysBaseAPI = SpringContextUtils.getBean(ISysBaseAPI.class);
        String dictTable = onlCgformField.getDictTable();
        String dictText = onlCgformField.getDictText();
        String dictField = onlCgformField.getDictField();
        List<DictModel> arrayList = new ArrayList<>();
        if (StrUtils.isNotEmpty(dictTable)) {
            arrayList = iSysBaseAPI.queryTableDictItemsByCode(dictTable, dictText, dictField);
        } else if (StrUtils.isNotEmpty(dictField)) {
            arrayList = iSysBaseAPI.queryDictItemsByCode(dictField);
        }
        this.dictlList = arrayList;
        this.field = onlCgformField.getDbFieldName();
    }
}
