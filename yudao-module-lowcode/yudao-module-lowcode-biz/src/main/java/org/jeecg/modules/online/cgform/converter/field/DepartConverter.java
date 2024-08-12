package org.jeecg.modules.online.cgform.converter.field;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;

import org.jeecg.common.service.ISysBaseAPI;
import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.modules.online.cgform.converter.common.ForeseeConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* compiled from: DepartConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/b.class */
public class DepartConverter extends ForeseeConvert {
    public DepartConverter(OnlCgformField onlCgformField) {
        String str;
        ISysBaseAPI iSysBaseAPI = (ISysBaseAPI) SpringUtil.getBean(ISysBaseAPI.class);
        String str2 = CgformUtil.DEPART_NAME;
        str = "ID";
        String fieldExtendJson = onlCgformField.getFieldExtendJson();
        if (ConvertUtils.isNotEmpty(fieldExtendJson)) {
            JSONObject parseObject = JSON.parseObject(fieldExtendJson);
            str = parseObject.containsKey(ExtendJsonKey.STORE) ? ConvertUtils.camelToUnderline(parseObject.getString(ExtendJsonKey.STORE)) : "ID";
            if (parseObject.containsKey(ExtendJsonKey.TEXT)) {
                str2 = ConvertUtils.camelToUnderline(parseObject.getString(ExtendJsonKey.TEXT));
            }
        }
        this.dictlList = iSysBaseAPI.queryTableDictItemsByCode(CgformUtil.SYS_DEPART, str2, str);
        this.field = onlCgformField.getDbFieldName();
    }

    @Override // org.jeecg.modules.online.cgform.converter.p010a.C0030b, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        if (ConvertUtils.isEmpty(txt)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
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
        ArrayList arrayList = new ArrayList();
        for (String str : val.split(CgformUtil.COMMA_SEPARATOR)) {
            String converterToTxt = super.converterToTxt(str);
            if (converterToTxt != null) {
                arrayList.add(converterToTxt);
            }
        }
        return String.join(CgformUtil.COMMA_SEPARATOR, arrayList);
    }
}
