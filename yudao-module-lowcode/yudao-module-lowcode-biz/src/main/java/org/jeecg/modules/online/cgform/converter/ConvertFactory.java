package org.jeecg.modules.online.cgform.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jeecg.common.util.MyClassLoader;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.field.CatTreeConverter;
import org.jeecg.modules.online.cgform.converter.field.DepartConverter;
import org.jeecg.modules.online.cgform.converter.field.DictEasyConverter;
import org.jeecg.modules.online.cgform.converter.field.DictTableConverter;
import org.jeecg.modules.online.cgform.converter.field.LinkDownConverter;
import org.jeecg.modules.online.cgform.converter.field.MultiSelectConverter;
import org.jeecg.modules.online.cgform.converter.field.PcaConverter;
import org.jeecg.modules.online.cgform.converter.field.SwitchConverter;
import org.jeecg.modules.online.cgform.converter.field.TreeSelectConverter;
import org.jeecg.modules.online.cgform.converter.field.UserSelectConverter;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: ConvertFactory.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/a.class */
public class ConvertFactory {

    /* renamed from: a */
    private static final Logger f145a = LoggerFactory.getLogger(ConvertFactory.class);

    /* renamed from: b */
    private static final String f146b = "list";

    /* renamed from: c */
    private static final String f147c = "radio";

    /* renamed from: d */
    private static final String f148d = "checkbox";

    /* renamed from: e */
    private static final String f149e = "list_multi";

    /* renamed from: f */
    private static final String f150f = "sel_search";

    /* renamed from: g */
    private static final String f151g = "sel_tree";

    /* renamed from: h */
    private static final String f152h = "cat_tree";

    /* renamed from: i */
    private static final String f153i = "link_down";

    /* renamed from: j */
    private static final String f154j = "sel_depart";

    /* renamed from: k */
    private static final String f155k = "sel_user";

    /* renamed from: l */
    private static final String f156l = "pca";

    /* renamed from: m */
    private static final String f157m = "switch";

    /* renamed from: n */
    private static final String f158n = "input";

    /* renamed from: a */
    public static FieldCommentConverter m173a(OnlCgformField onlCgformField) {
        FieldCommentConverter fieldCommentConverter;
        String fieldShowType = onlCgformField.getFieldShowType();

        switch (fieldShowType) {
            case "list":
            case "radio":
                fieldCommentConverter = new DictEasyConverter(onlCgformField);
                break;
            case "list_multi":
            case "checkbox":
                fieldCommentConverter = new MultiSelectConverter(onlCgformField);
                break;
            case "sel_search":
                fieldCommentConverter = new DictTableConverter(onlCgformField);
                break;
            case "sel_tree":
                fieldCommentConverter = new TreeSelectConverter(onlCgformField);
                break;
            case "cat_tree":
                fieldCommentConverter = new CatTreeConverter(onlCgformField);
                break;
            case "link_down":
                fieldCommentConverter = new LinkDownConverter(onlCgformField);
                break;
            case "sel_depart":
                fieldCommentConverter = new DepartConverter(onlCgformField);
                break;
            case "sel_user":
                fieldCommentConverter = new UserSelectConverter(onlCgformField);
                break;
            case "pca":
                fieldCommentConverter = new PcaConverter(onlCgformField);
                break;
            case "switch":
                fieldCommentConverter = new SwitchConverter(onlCgformField);
                break;
            case "input":
                String dictField = onlCgformField.getDictField();
                if (dictField == null || "".equals(dictField)) {
                    fieldCommentConverter = null;
                } else {
                    fieldCommentConverter = new DictEasyConverter(onlCgformField);
                }
                break;
            default:
                fieldCommentConverter = null;
                break;
        }
        return fieldCommentConverter;
    }

    /* renamed from: a */
    public static Map<String, FieldCommentConverter> m174a(List<OnlCgformField> list) {
        FieldCommentConverter m173a;
        HashMap<String,FieldCommentConverter> hashMap = new HashMap<>(5);
        for (OnlCgformField onlCgformField : list) {
            if (oConvertUtils.isNotEmpty(onlCgformField.getConverter())) {
                m173a = m175a(onlCgformField.getConverter().trim());
            } else {
                m173a = m173a(onlCgformField);
            }
            if (m173a != null) {
                hashMap.put(onlCgformField.getDbFieldName().toLowerCase(), m173a);
            }
        }
        return hashMap;
    }

    /* renamed from: a */
    private static FieldCommentConverter m175a(String str) {
        Object obj = null;
        if (str.indexOf(".") > 0) {
            try {
                obj = MyClassLoader.getClassByScn(str).getDeclaredConstructor().newInstance();
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                f145a.error(e.getMessage(), e);
            }
        } else {
            obj = SpringContextUtils.getBean(str);
        }
        if (obj != null && (obj instanceof FieldCommentConverter)) {
            return (FieldCommentConverter) obj;
        }
        return null;
    }
}
