package org.jeecg.modules.online.cgform.converter.field;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.common.ForeseeConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* compiled from: UserSelectConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.k */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/k.class */
public class UserSelectConverter extends ForeseeConvert {
    public UserSelectConverter(OnlCgformField onlCgformField) {
        ISysBaseAPI iSysBaseAPI = SpringContextUtils.getBean(ISysBaseAPI.class);
        String realname = CgformUtil.REALNAME;
        String username = CgformUtil.USERNAME;
        String fieldExtendJson = onlCgformField.getFieldExtendJson();
        if (oConvertUtils.isNotEmpty(fieldExtendJson)) {
            JSONObject parseObject = JSON.parseObject(fieldExtendJson);
            if (parseObject.containsKey(ExtendJsonKey.STORE)) {
                String string = parseObject.getString(ExtendJsonKey.STORE);
                SqlInjectionUtil.filterContent(new String[]{string});
                username = oConvertUtils.camelToUnderline(string);
            }
            if (parseObject.containsKey(ExtendJsonKey.TEXT)) {
                String string2 = parseObject.getString(ExtendJsonKey.TEXT);
                SqlInjectionUtil.filterContent(string2);
                realname = oConvertUtils.camelToUnderline(string2);
            }
        }
        this.dictlList = iSysBaseAPI.queryTableDictItemsByCode(CgformUtil.SYS_USER, realname, username);
        this.field = onlCgformField.getDbFieldName();
    }

    @Override // org.jeecg.modules.online.cgform.converter.p010a.C0030b, org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        if (oConvertUtils.isEmpty(txt)) {
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
        if (oConvertUtils.isEmpty(val)) {
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
