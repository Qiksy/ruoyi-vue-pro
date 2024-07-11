package org.jeecg.modules.online.cgform.model;

/* compiled from: ScopedSlots.java */
/* renamed from: org.jeecg.modules.online.cgform.model.g */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/g.class */
public class ScopedSlots {

    /* renamed from: a */
    private String customRender;

    public void setCustomRender(String customRender) {
        this.customRender = customRender;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ScopedSlots)) {
            return false;
        }
        ScopedSlots scopedSlots = (ScopedSlots) o;
        if (!scopedSlots.m294a(this)) {
            return false;
        }
        String customRender = getCustomRender();
        String customRender2 = scopedSlots.getCustomRender();
        return customRender == null ? customRender2 == null : customRender.equals(customRender2);
    }

    /* renamed from: a */
    protected boolean m294a(Object obj) {
        return obj instanceof ScopedSlots;
    }

    public int hashCode() {
        String customRender = getCustomRender();
        return (1 * 59) + (customRender == null ? 43 : customRender.hashCode());
    }

    public String toString() {
        return "ScopedSlots(customRender=" + getCustomRender() + ")";
    }

    public String getCustomRender() {
        return this.customRender;
    }

    public ScopedSlots() {
    }

    public ScopedSlots(String str) {
        this.customRender = str;
    }
}
