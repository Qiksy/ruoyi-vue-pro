package org.jeecg.modules.online.cgform.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.enums.CgformValidPatternEnum;

/* compiled from: OnlineImportValidator.java */
/* renamed from: org.jeecg.modules.online.cgform.d.h */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/d/h.class */
public class OnlineImportValidator {

    /* renamed from: d */
    private Map<String, OnlCgformField> fieldList;

    /**
     * 不允许为空的字段
     */
    /* renamed from: e */
    private Map<String, OnlCgformField> notNullFieldList;

    /* renamed from: f */
    private static final String f309f = ",";

    /* renamed from: g */
    private static final String rowMessage = "第%s行校验信息:";

    /* renamed from: h */
    private static final String totalMessage = "总上传行数:%s,已导入行数:%s,错误行数:%s";


    /* renamed from: a */
    public static final String ERROR = "error";

    /* renamed from: b */
    public static final String TIP = "tip";

    /* renamed from: c */
    public static final String FILE_PATH = "filePath";

    public OnlineImportValidator() {
    }

    public OnlineImportValidator(List<OnlCgformField> list) {
        this.fieldList = new HashMap<>(5);
        this.notNullFieldList = new HashMap<>(5);
        for (OnlCgformField onlCgformField : list) {
            String fieldValidType = onlCgformField.getFieldValidType();
            if (fieldValidType != null && !fieldValidType.isEmpty() && !CgformValidPatternEnum.ONLY.getType().equals(fieldValidType)) {
                if (CgformValidPatternEnum.NOTNULL.getType().equals(fieldValidType)) {
                    this.notNullFieldList.put(onlCgformField.getDbFieldName(), onlCgformField);
                } else {
                    this.fieldList.put(onlCgformField.getDbFieldName(), onlCgformField);
                }
            }
            if (onlCgformField.getDbIsNull() == 0 || "1".equals(onlCgformField.getFieldMustInput())) {
                if (oConvertUtils.isEmpty(onlCgformField.getDbDefaultVal())) {
                    this.notNullFieldList.put(onlCgformField.getDbFieldName(), onlCgformField);
                }
            }
        }
    }

    /* renamed from: a */
    public String m281a(String str, int i) {
        String pattern;
        String msg;
        StringBuilder stringBuffer = new StringBuilder();
        JSONObject parseObject = JSON.parseObject(str);
        for (String str2 : this.notNullFieldList.keySet()) {
            String string = parseObject.getString(str2);
            OnlCgformField onlCgformField = this.notNullFieldList.get(str2);
            if (string == null || string.isEmpty()) {
                stringBuffer.append(onlCgformField.getDbFieldTxt()).append(CgformValidPatternEnum.NOTNULL.getMsg()).append(",");
            }
        }
        for (String str3 : this.fieldList.keySet()) {
            String string2 = parseObject.getString(str3);
            OnlCgformField onlCgformField2 = this.fieldList.get(str3);
            String fieldValidType = onlCgformField2.getFieldValidType();
            if (string2 != null && !string2.isEmpty()) {
                if (CgformValidPatternEnum.INTEGER.getType().equals(fieldValidType)) {
                    pattern = "^-?[1-9]\\d*$";
                    msg = "请输入整数";
                } else {
                    CgformValidPatternEnum patternInfoByType = CgformValidPatternEnum.getPatternInfoByType(fieldValidType);
                    if (patternInfoByType == null) {
                        pattern = fieldValidType;
                        msg = "校验【" + pattern + "】未通过";
                    } else {
                        pattern = patternInfoByType.getPattern();
                        msg = patternInfoByType.getMsg();
                    }
                }
                if (!Pattern.compile(pattern).matcher(string2).find()) {
                    stringBuffer.append(onlCgformField2.getDbFieldTxt() + msg + ",");
                }
            }
        }
        if (!stringBuffer.isEmpty()) {
            return m282b(stringBuffer.toString(), i);
        }
        return null;
    }

    /* renamed from: b */
    public static String m282b(String str, int i) {
        return String.format(rowMessage, i) + str + "\r\n";
    }

    /* renamed from: a */
    public static String m283a(int i, int i2) {
        return String.format(totalMessage, i, i - i2, i2);
    }
}
