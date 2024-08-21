package org.jeecg.common.util.online;

import lombok.Data;

/* compiled from: BaseColumn.java */
/* renamed from: org.jeecg.common.util.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a.class */
@Data
public class BaseColumn {

    /* renamed from: a */
    private String title;

    /* renamed from: b */
    private String field;

    /* renamed from: c */
    private Integer order;


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseColumn baseColumn)) {
            return false;
        }
        if (!baseColumn.isBaseColum(this)) {
            return false;
        }
        Integer order = getOrder();
        Integer order2 = baseColumn.getOrder();
        if (order == null) {
            if (order2 != null) {
                return false;
            }
        } else if (!order.equals(order2)) {
            return false;
        }
        String title = getTitle();
        String title2 = baseColumn.getTitle();
        if (title == null) {
            if (title2 != null) {
                return false;
            }
        } else if (!title.equals(title2)) {
            return false;
        }
        String field = getField();
        String field2 = baseColumn.getField();
        return field == null ? field2 == null : field.equals(field2);
    }

    /* renamed from: a */
    protected boolean isBaseColum(Object obj) {
        return obj instanceof BaseColumn;
    }

    public int hashCode() {
        Integer order = getOrder();
        int hashCode = (1 * 59) + (order == null ? 43 : order.hashCode());
        String title = getTitle();
        int hashCode2 = (hashCode * 59) + (title == null ? 43 : title.hashCode());
        String field = getField();
        return (hashCode2 * 59) + (field == null ? 43 : field.hashCode());
    }

    public String toString() {
        return "BaseColumn(title=" + getTitle() + ", field=" + getField() + ", order=" + getOrder() + ")";
    }


    public BaseColumn() {
    }

    public BaseColumn(String title, String field, Integer num) {
        this.title = title;
        this.field = field;
        this.order = num;
    }
}
