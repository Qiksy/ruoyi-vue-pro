package org.jeecg.modules.online.config.template;

import java.util.Comparator;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: FieldNumComparator.java */
/* renamed from: org.jeecg.modules.online.config.d.f */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/d/f.class */
public class FieldNumComparator implements Comparator<OnlCgformField> {
    @Override // java.util.Comparator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(OnlCgformField onlCgformField, OnlCgformField onlCgformField2) {
        if (onlCgformField == null || onlCgformField.getOrderNum() == null || onlCgformField2 == null || onlCgformField2.getOrderNum() == null) {
            return -1;
        }
        Integer orderNum = onlCgformField.getOrderNum();
        Integer orderNum2 = onlCgformField2.getOrderNum();
        if (orderNum < orderNum2) {
            return -1;
        }
        return orderNum.equals(orderNum2) ? 0 : 1;
    }
}
