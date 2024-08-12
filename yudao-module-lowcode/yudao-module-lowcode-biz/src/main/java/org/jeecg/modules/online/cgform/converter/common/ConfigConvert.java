package org.jeecg.modules.online.cgform.converter.common;

import java.util.List;
import java.util.Map;

import cn.hutool.extra.spring.SpringUtil;

import org.jeecg.common.service.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;


import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.modules.online.cgform.converter.FieldCommentConverter;

/* compiled from: ConfigConvert.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/a/a.class */
public class ConfigConvert implements FieldCommentConverter {

    /* renamed from: a */
    protected ISysBaseAPI baseApi;

    /* renamed from: b */
    protected String dbFieldName;

    /* renamed from: c */
    protected String f161c;

    /* renamed from: d */
    protected String f162d;

    /* renamed from: e */
    protected String f163e;

    public ConfigConvert() {
        this.baseApi = SpringUtil.getBean(ISysBaseAPI.class);
    }

    public ConfigConvert(String str, String str2, String str3) {
        this();
        this.f161c = str;
        this.f162d = str2;
        this.f163e = str3;
    }

    public String getField() {
        return this.dbFieldName;
    }

    public void setField(String field) {
        this.dbFieldName = field;
    }

    public String getTable() {
        return this.f161c;
    }

    public void setTable(String table) {
        this.f161c = table;
    }

    public String getCode() {
        return this.f162d;
    }

    public void setCode(String code) {
        this.f162d = code;
    }

    public String getText() {
        return this.f163e;
    }

    public void setText(String text) {
        this.f163e = text;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToVal(String txt) {
        String str;
        if (ConvertUtils.isNotEmpty(txt)) {
            String str2 = this.f163e + "= '" + txt + "'";
            int indexOf = this.f161c.indexOf("where");
            if (indexOf > 0) {
                str = this.f161c.substring(0, indexOf).trim();
                str2 = str2 + " and " + this.f161c.substring(indexOf + 5);
            } else {
                str = this.f161c;
            }
            List queryFilterTableDictInfo = this.baseApi.queryFilterTableDictInfo(str, this.f163e, this.f162d, str2);
            if (queryFilterTableDictInfo != null && !queryFilterTableDictInfo.isEmpty()) {
                return ((DictModel) queryFilterTableDictInfo.get(0)).getValue();
            }
            return null;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.converter.FieldCommentConverter
    public String converterToTxt(String val) {
        String str;
        if (ConvertUtils.isNotEmpty(val)) {
            String str2 = this.f162d + "= '" + val + "'";
            int indexOf = this.f161c.indexOf("where");
            if (indexOf > 0) {
                str = this.f161c.substring(0, indexOf).trim();
                str2 = str2 + " and " + this.f161c.substring(indexOf + 5);
            } else {
                str = this.f161c;
            }
            List queryFilterTableDictInfo = this.baseApi.queryFilterTableDictInfo(str, this.f163e, this.f162d, str2);
            if (queryFilterTableDictInfo != null && !queryFilterTableDictInfo.isEmpty()) {
                return ((DictModel) queryFilterTableDictInfo.get(0)).getText();
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
