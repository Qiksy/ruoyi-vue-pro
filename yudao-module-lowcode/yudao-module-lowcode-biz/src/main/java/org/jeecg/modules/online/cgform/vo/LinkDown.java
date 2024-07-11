package org.jeecg.modules.online.cgform.vo;

import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* compiled from: LinkDown.java */
/* renamed from: org.jeecg.modules.online.cgform.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/a/a.class */
public class LinkDown {

    /* renamed from: a */
    private String table;

    /* renamed from: b */
    private String txt;

    /* renamed from: c */
    private String key;

    /* renamed from: d */
    private String linkField;

    /* renamed from: e */
    private String idField;

    /* renamed from: f */
    private String pidField;

    /* renamed from: g */
    private String pidValue;

    /* renamed from: h */
    private String condition;

    public void setTable(String table) {
        this.table = table;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLinkField(String linkField) {
        this.linkField = linkField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public void setPidField(String pidField) {
        this.pidField = pidField;
    }

    public void setPidValue(String pidValue) {
        this.pidValue = pidValue;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LinkDown)) {
            return false;
        }
        LinkDown linkDown = (LinkDown) o;
        if (!linkDown.m76a(this)) {
            return false;
        }
        String table = getTable();
        String table2 = linkDown.getTable();
        if (table == null) {
            if (table2 != null) {
                return false;
            }
        } else if (!table.equals(table2)) {
            return false;
        }
        String txt = getTxt();
        String txt2 = linkDown.getTxt();
        if (txt == null) {
            if (txt2 != null) {
                return false;
            }
        } else if (!txt.equals(txt2)) {
            return false;
        }
        String key = getKey();
        String key2 = linkDown.getKey();
        if (key == null) {
            if (key2 != null) {
                return false;
            }
        } else if (!key.equals(key2)) {
            return false;
        }
        String linkField = getLinkField();
        String linkField2 = linkDown.getLinkField();
        if (linkField == null) {
            if (linkField2 != null) {
                return false;
            }
        } else if (!linkField.equals(linkField2)) {
            return false;
        }
        String idField = getIdField();
        String idField2 = linkDown.getIdField();
        if (idField == null) {
            if (idField2 != null) {
                return false;
            }
        } else if (!idField.equals(idField2)) {
            return false;
        }
        String pidField = getPidField();
        String pidField2 = linkDown.getPidField();
        if (pidField == null) {
            if (pidField2 != null) {
                return false;
            }
        } else if (!pidField.equals(pidField2)) {
            return false;
        }
        String pidValue = getPidValue();
        String pidValue2 = linkDown.getPidValue();
        if (pidValue == null) {
            if (pidValue2 != null) {
                return false;
            }
        } else if (!pidValue.equals(pidValue2)) {
            return false;
        }
        String condition = getCondition();
        String condition2 = linkDown.getCondition();
        return condition == null ? condition2 == null : condition.equals(condition2);
    }

    /* renamed from: a */
    protected boolean m76a(Object obj) {
        return obj instanceof LinkDown;
    }

    public int hashCode() {
        String table = getTable();
        int hashCode = (1 * 59) + (table == null ? 43 : table.hashCode());
        String txt = getTxt();
        int hashCode2 = (hashCode * 59) + (txt == null ? 43 : txt.hashCode());
        String key = getKey();
        int hashCode3 = (hashCode2 * 59) + (key == null ? 43 : key.hashCode());
        String linkField = getLinkField();
        int hashCode4 = (hashCode3 * 59) + (linkField == null ? 43 : linkField.hashCode());
        String idField = getIdField();
        int hashCode5 = (hashCode4 * 59) + (idField == null ? 43 : idField.hashCode());
        String pidField = getPidField();
        int hashCode6 = (hashCode5 * 59) + (pidField == null ? 43 : pidField.hashCode());
        String pidValue = getPidValue();
        int hashCode7 = (hashCode6 * 59) + (pidValue == null ? 43 : pidValue.hashCode());
        String condition = getCondition();
        return (hashCode7 * 59) + (condition == null ? 43 : condition.hashCode());
    }

    public String toString() {
        return "LinkDown(table=" + getTable() + ", txt=" + getTxt() + ", key=" + getKey() + ", linkField=" + getLinkField() + ", idField=" + getIdField() + ", pidField=" + getPidField() + ", pidValue=" + getPidValue() + ", condition=" + getCondition() + ")";
    }

    public String getTable() {
        return this.table;
    }

    public String getTxt() {
        return this.txt;
    }

    public String getKey() {
        return this.key;
    }

    public String getLinkField() {
        return this.linkField;
    }

    public String getIdField() {
        return this.idField;
    }

    public String getPidField() {
        return this.pidField;
    }

    public String getPidValue() {
        return this.pidValue;
    }

    public String getCondition() {
        return this.condition;
    }

    private String getQuerySql() {
        new StringBuffer().append(CgformUtil.f180a);
        return null;
    }
}
