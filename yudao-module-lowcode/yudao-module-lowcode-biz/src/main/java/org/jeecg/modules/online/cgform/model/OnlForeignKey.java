package org.jeecg.modules.online.cgform.model;

/* compiled from: OnlForeignKey.java */
/* renamed from: org.jeecg.modules.online.cgform.model.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/c.class */
public class OnlForeignKey {

    /* renamed from: a */
    private String field;

    /* renamed from: b */
    private String table;

    /* renamed from: c */
    private String key;

    public void setField(String field) {
        this.field = field;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlForeignKey)) {
            return false;
        }
        OnlForeignKey onlForeignKey = (OnlForeignKey) o;
        if (!onlForeignKey.m290a(this)) {
            return false;
        }
        String field = getField();
        String field2 = onlForeignKey.getField();
        if (field == null) {
            if (field2 != null) {
                return false;
            }
        } else if (!field.equals(field2)) {
            return false;
        }
        String table = getTable();
        String table2 = onlForeignKey.getTable();
        if (table == null) {
            if (table2 != null) {
                return false;
            }
        } else if (!table.equals(table2)) {
            return false;
        }
        String key = getKey();
        String key2 = onlForeignKey.getKey();
        return key == null ? key2 == null : key.equals(key2);
    }

    /* renamed from: a */
    protected boolean m290a(Object obj) {
        return obj instanceof OnlForeignKey;
    }

    public int hashCode() {
        String field = getField();
        int hashCode = (1 * 59) + (field == null ? 43 : field.hashCode());
        String table = getTable();
        int hashCode2 = (hashCode * 59) + (table == null ? 43 : table.hashCode());
        String key = getKey();
        return (hashCode2 * 59) + (key == null ? 43 : key.hashCode());
    }

    public String toString() {
        return "OnlForeignKey(field=" + getField() + ", table=" + getTable() + ", key=" + getKey() + ")";
    }

    public String getField() {
        return this.field;
    }

    public String getTable() {
        return this.table;
    }

    public String getKey() {
        return this.key;
    }

    public OnlForeignKey() {
    }

    public OnlForeignKey(String str, String str2) {
        this.key = str2;
        this.field = str;
    }
}
