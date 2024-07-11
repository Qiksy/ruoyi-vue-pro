package org.jeecg.modules.online.cgform.converter.field;

import com.alibaba.fastjson.JSONArray;
import java.util.ArrayList;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.converter.common.ForeseeConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: SwitchConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.i */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/i.class */
public class SwitchConverter extends ForeseeConvert {
    public SwitchConverter(OnlCgformField onlCgformField) {
        JSONArray parseArray;
        String fieldExtendJson = onlCgformField.getFieldExtendJson();
        String trueStr = "Y";
        String falseStr = "N";
        if (fieldExtendJson != null && !fieldExtendJson.isEmpty() && (parseArray = JSONArray.parseArray(fieldExtendJson)) != null && parseArray.size() == 2) {
            trueStr = parseArray.get(0).toString();
            falseStr = parseArray.get(1).toString();
        }
        ArrayList<DictModel> arrayList = new ArrayList<>();
        DictModel dictModel = new DictModel(trueStr, "是");
        DictModel dictModel2 = new DictModel(falseStr, "否");
        arrayList.add(dictModel);
        arrayList.add(dictModel2);
        this.dictlList = arrayList;
        this.field = onlCgformField.getDbFieldName();
    }
}
