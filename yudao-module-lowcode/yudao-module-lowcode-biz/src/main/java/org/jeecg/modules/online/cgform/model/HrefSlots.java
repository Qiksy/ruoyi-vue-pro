package org.jeecg.modules.online.cgform.model;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/HrefSlots.class */
public class HrefSlots {
    private String slotName;
    private String href;

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HrefSlots hrefSlots)) {
            return false;
        }
        if (!hrefSlots.canEqual(this)) {
            return false;
        }
        String slotName = getSlotName();
        String slotName2 = hrefSlots.getSlotName();
        if (slotName == null) {
            if (slotName2 != null) {
                return false;
            }
        } else if (!slotName.equals(slotName2)) {
            return false;
        }
        String href = getHref();
        String href2 = hrefSlots.getHref();
        return href == null ? href2 == null : href.equals(href2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof HrefSlots;
    }

    public int hashCode() {
        String slotName = getSlotName();
        int hashCode = (1 * 59) + (slotName == null ? 43 : slotName.hashCode());
        String href = getHref();
        return (hashCode * 59) + (href == null ? 43 : href.hashCode());
    }

    public String toString() {
        return "HrefSlots(slotName=" + getSlotName() + ", href=" + getHref() + ")";
    }

    public String getSlotName() {
        return this.slotName;
    }

    public String getHref() {
        return this.href;
    }

    public HrefSlots() {
    }

    public HrefSlots(String slotName, String href) {
        this.slotName = slotName;
        this.href = href;
    }
}
