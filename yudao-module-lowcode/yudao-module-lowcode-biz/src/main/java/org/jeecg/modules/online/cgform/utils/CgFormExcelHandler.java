package org.jeecg.modules.online.cgform.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.hutool.extra.spring.SpringUtil;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.jeecgframework.poi.util.PoiPublicUtil;

/* compiled from: CgFormExcelHandler.java */
/* renamed from: org.jeecg.modules.online.cgform.d.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/d/a.class */
public class CgFormExcelHandler extends ExcelDataHandlerDefaultImpl {

    /* renamed from: a */
    Map<String, OnlCgformField> f174a;

    /* renamed from: c */
    String f176c;

    /* renamed from: e */
    String f178e;

    /* renamed from: d */
    String f177d = "online";

    /* renamed from: b */
    ISysBaseAPI f175b = (ISysBaseAPI) SpringUtil.getBean(ISysBaseAPI.class);

    public CgFormExcelHandler(List<OnlCgformField> list, String str, String str2) {
        this.f174a = m179a(list);
        this.f176c = str;
        this.f178e = str2;
    }

    /* renamed from: a */
    private Map<String, OnlCgformField> m179a(List<OnlCgformField> list) {
        HashMap<String,OnlCgformField> hashMap = new HashMap<>();
        for (OnlCgformField onlCgformField : list) {
            hashMap.put(onlCgformField.getDbFieldTxt(), onlCgformField);
        }
        return hashMap;
    }

    public void setMapValue(Map<String, Object> map, String originKey, Object value) {
        String m180a = m180a(originKey);
        if (value instanceof Double) {
            map.put(m180a, PoiPublicUtil.doubleToString((Double) value));
            return;
        }
        if (value instanceof byte[]) {
            String m237a = CgformUtil.m237a((byte[]) value, this.f176c, this.f177d, this.f178e);
            if (m237a != null) {
                map.put(m180a, m237a);
                return;
            }
            return;
        }
        map.put(m180a, value == null ? "" : value.toString());
    }

    /* renamed from: a */
    private String m180a(String str) {
        if (this.f174a.containsKey(str)) {
            return "$mainTable$" + this.f174a.get(str).getDbFieldName();
        }
        return "$subTable$" + str;
    }
}
