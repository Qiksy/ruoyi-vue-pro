package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("onl_cgform_enhance_js")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/entity/OnlCgformEnhanceJs.class */
public class OnlCgformEnhanceJs implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String cgformHeadId;
    private String cgJsType;
    private String cgJs;
    private String content;

    public void setId(String id) {
        this.id = id;
    }

    public void setCgformHeadId(String cgformHeadId) {
        this.cgformHeadId = cgformHeadId;
    }

    public void setCgJsType(String cgJsType) {
        this.cgJsType = cgJsType;
    }

    public void setCgJs(String cgJs) {
        this.cgJs = cgJs;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlCgformEnhanceJs onlCgformEnhanceJs)) {
            return false;
        }
        if (!onlCgformEnhanceJs.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = onlCgformEnhanceJs.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String cgformHeadId = getCgformHeadId();
        String cgformHeadId2 = onlCgformEnhanceJs.getCgformHeadId();
        if (cgformHeadId == null) {
            if (cgformHeadId2 != null) {
                return false;
            }
        } else if (!cgformHeadId.equals(cgformHeadId2)) {
            return false;
        }
        String cgJsType = getCgJsType();
        String cgJsType2 = onlCgformEnhanceJs.getCgJsType();
        if (cgJsType == null) {
            if (cgJsType2 != null) {
                return false;
            }
        } else if (!cgJsType.equals(cgJsType2)) {
            return false;
        }
        String cgJs = getCgJs();
        String cgJs2 = onlCgformEnhanceJs.getCgJs();
        if (cgJs == null) {
            if (cgJs2 != null) {
                return false;
            }
        } else if (!cgJs.equals(cgJs2)) {
            return false;
        }
        String content = getContent();
        String content2 = onlCgformEnhanceJs.getContent();
        return content == null ? content2 == null : content.equals(content2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlCgformEnhanceJs;
    }

    public int hashCode() {
        String id = getId();
        int hashCode = (1 * 59) + (id == null ? 43 : id.hashCode());
        String cgformHeadId = getCgformHeadId();
        int hashCode2 = (hashCode * 59) + (cgformHeadId == null ? 43 : cgformHeadId.hashCode());
        String cgJsType = getCgJsType();
        int hashCode3 = (hashCode2 * 59) + (cgJsType == null ? 43 : cgJsType.hashCode());
        String cgJs = getCgJs();
        int hashCode4 = (hashCode3 * 59) + (cgJs == null ? 43 : cgJs.hashCode());
        String content = getContent();
        return (hashCode4 * 59) + (content == null ? 43 : content.hashCode());
    }

    public String toString() {
        return "OnlCgformEnhanceJs(id=" + getId() + ", cgformHeadId=" + getCgformHeadId() + ", cgJsType=" + getCgJsType() + ", cgJs=" + getCgJs() + ", content=" + getContent() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getCgformHeadId() {
        return this.cgformHeadId;
    }

    public String getCgJsType() {
        return this.cgJsType;
    }

    public String getCgJs() {
        return this.cgJs;
    }

    public String getContent() {
        return this.content;
    }
}
