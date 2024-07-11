package org.jeecg.common.util.online;

/* compiled from: BaseColumn.java */
/* renamed from: org.jeecg.common.util.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/a.class */
public class BaseColumn {

    /* renamed from: a */
    private String f0a;

    /* renamed from: b */
    private String f1b;

    /* renamed from: c */
    private Integer f2c;

    public void setTitle(String title) {
        this.f0a = title;
    }

    public void setField(String field) {
        this.f1b = field;
    }

    public void setOrder(Integer order) {
        this.f2c = order;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseColumn)) {
            return false;
        }
        BaseColumn baseColumn = (BaseColumn) o;
        if (!baseColumn.m0a(this)) {
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
    protected boolean m0a(Object obj) {
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

    public String getTitle() {
        return this.f0a;
    }

    public String getField() {
        return this.f1b;
    }

    public Integer getOrder() {
        return this.f2c;
    }

    public BaseColumn() {
    }

    public BaseColumn(String str, String str2, Integer num) {
        this.f0a = str;
        this.f1b = str2;
        this.f2c = num;
    }
}
