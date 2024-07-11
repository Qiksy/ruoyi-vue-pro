package org.jeecg.modules.online.cgform.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* compiled from: ConvertUtil.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b.class */
public class ConvertUtil {

    /* renamed from: a */
    public static final int f166a = 2;

    /* renamed from: b */
    public static final int f167b = 1;

    /* renamed from: a */
    public static void m176a(int i, List<Map<String, Object>> list, List<OnlCgformField> list2) {
        Map<String, FieldCommentConverter> m174a = ConvertFactory.m174a(list2);
        for (Map<String, Object> map : list) {
            HashMap<String,Object> hashMap = new HashMap<>(5);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    String key = entry.getKey();
                    FieldCommentConverter fieldCommentConverter = m174a.get(key.toLowerCase());
                    if (fieldCommentConverter != null) {
                        String obj = value.toString();
                        String converterToTxt = i == 1 ? fieldCommentConverter.converterToTxt(obj) : fieldCommentConverter.converterToVal(obj);
                        if (converterToTxt == null) {
                            converterToTxt = obj;
                        }
                        m177a(fieldCommentConverter, map, i);
                        m178a(fieldCommentConverter, hashMap, obj);
                        map.put(key, converterToTxt);
                    }
                }
            }
            for (String str : hashMap.keySet()) {
                map.put(str, hashMap.get(str));
            }
        }
    }

    /* renamed from: a */
    private static void m177a(FieldCommentConverter fieldCommentConverter, Map<String, Object> map, int i) {
        Map<String, String> config = fieldCommentConverter.getConfig();
        if (config != null) {
            String str = config.get("linkField");
            if (oConvertUtils.isNotEmpty(str)) {
                for (String str2 : str.split(CgformUtil.COMMA_SEPARATOR)) {
                    Object obj = map.get(str2);
                    if (obj != null) {
                        String obj2 = obj.toString();
                        map.put(str2, i == 1 ? fieldCommentConverter.converterToTxt(obj2) : fieldCommentConverter.converterToVal(obj2));
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private static void m178a(FieldCommentConverter fieldCommentConverter, Map<String, Object> map, String str) {
        Map<String, String> config = fieldCommentConverter.getConfig();
        if (config != null) {
            String str2 = config.get("treeText");
            if (oConvertUtils.isNotEmpty(str2)) {
                map.put(str2, str);
            }
        }
    }
}
