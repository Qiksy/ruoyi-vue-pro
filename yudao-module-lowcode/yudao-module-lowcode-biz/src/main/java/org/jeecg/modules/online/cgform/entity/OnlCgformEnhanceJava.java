package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("onl_cgform_enhance_java")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/entity/OnlCgformEnhanceJava.class */
public class OnlCgformEnhanceJava implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String cgformHeadId;
    private String buttonCode;
    private String cgJavaType;
    private String cgJavaValue;
    private String activeStatus;
    private String event;

    public void setId(String id) {
        this.id = id;
    }

    public void setCgformHeadId(String cgformHeadId) {
        this.cgformHeadId = cgformHeadId;
    }

    public void setButtonCode(String buttonCode) {
        this.buttonCode = buttonCode;
    }

    public void setCgJavaType(String cgJavaType) {
        this.cgJavaType = cgJavaType;
    }

    public void setCgJavaValue(String cgJavaValue) {
        this.cgJavaValue = cgJavaValue;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlCgformEnhanceJava onlCgformEnhanceJava)) {
            return false;
        }
        if (!onlCgformEnhanceJava.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = onlCgformEnhanceJava.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String cgformHeadId = getCgformHeadId();
        String cgformHeadId2 = onlCgformEnhanceJava.getCgformHeadId();
        if (cgformHeadId == null) {
            if (cgformHeadId2 != null) {
                return false;
            }
        } else if (!cgformHeadId.equals(cgformHeadId2)) {
            return false;
        }
        String buttonCode = getButtonCode();
        String buttonCode2 = onlCgformEnhanceJava.getButtonCode();
        if (buttonCode == null) {
            if (buttonCode2 != null) {
                return false;
            }
        } else if (!buttonCode.equals(buttonCode2)) {
            return false;
        }
        String cgJavaType = getCgJavaType();
        String cgJavaType2 = onlCgformEnhanceJava.getCgJavaType();
        if (cgJavaType == null) {
            if (cgJavaType2 != null) {
                return false;
            }
        } else if (!cgJavaType.equals(cgJavaType2)) {
            return false;
        }
        String cgJavaValue = getCgJavaValue();
        String cgJavaValue2 = onlCgformEnhanceJava.getCgJavaValue();
        if (cgJavaValue == null) {
            if (cgJavaValue2 != null) {
                return false;
            }
        } else if (!cgJavaValue.equals(cgJavaValue2)) {
            return false;
        }
        String activeStatus = getActiveStatus();
        String activeStatus2 = onlCgformEnhanceJava.getActiveStatus();
        if (activeStatus == null) {
            if (activeStatus2 != null) {
                return false;
            }
        } else if (!activeStatus.equals(activeStatus2)) {
            return false;
        }
        String event = getEvent();
        String event2 = onlCgformEnhanceJava.getEvent();
        return event == null ? event2 == null : event.equals(event2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlCgformEnhanceJava;
    }

    public int hashCode() {
        String id = getId();
        int hashCode = (1 * 59) + (id == null ? 43 : id.hashCode());
        String cgformHeadId = getCgformHeadId();
        int hashCode2 = (hashCode * 59) + (cgformHeadId == null ? 43 : cgformHeadId.hashCode());
        String buttonCode = getButtonCode();
        int hashCode3 = (hashCode2 * 59) + (buttonCode == null ? 43 : buttonCode.hashCode());
        String cgJavaType = getCgJavaType();
        int hashCode4 = (hashCode3 * 59) + (cgJavaType == null ? 43 : cgJavaType.hashCode());
        String cgJavaValue = getCgJavaValue();
        int hashCode5 = (hashCode4 * 59) + (cgJavaValue == null ? 43 : cgJavaValue.hashCode());
        String activeStatus = getActiveStatus();
        int hashCode6 = (hashCode5 * 59) + (activeStatus == null ? 43 : activeStatus.hashCode());
        String event = getEvent();
        return (hashCode6 * 59) + (event == null ? 43 : event.hashCode());
    }

    public String toString() {
        return "OnlCgformEnhanceJava(id=" + getId() + ", cgformHeadId=" + getCgformHeadId() + ", buttonCode=" + getButtonCode() + ", cgJavaType=" + getCgJavaType() + ", cgJavaValue=" + getCgJavaValue() + ", activeStatus=" + getActiveStatus() + ", event=" + getEvent() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getCgformHeadId() {
        return this.cgformHeadId;
    }

    public String getButtonCode() {
        return this.buttonCode;
    }

    public String getCgJavaType() {
        return this.cgJavaType;
    }

    public String getCgJavaValue() {
        return this.cgJavaValue;
    }

    public String getActiveStatus() {
        return this.activeStatus;
    }

    public String getEvent() {
        return this.event;
    }
}
