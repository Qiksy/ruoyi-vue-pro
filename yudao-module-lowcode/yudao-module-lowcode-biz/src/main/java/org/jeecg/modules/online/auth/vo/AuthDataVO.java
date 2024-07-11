package org.jeecg.modules.online.auth.vo;

import java.io.Serializable;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/vo/AuthDataVO.class */
public class AuthDataVO implements Serializable {
    private static final long serialVersionUID = 1057819436991228603L;

    /* renamed from: id */
    private String id;
    private String title;
    private String relId;
    private Boolean checked;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuthDataVO)) {
            return false;
        }
        AuthDataVO authDataVO = (AuthDataVO) o;
        if (!authDataVO.canEqual(this)) {
            return false;
        }
        Boolean checked = getChecked();
        Boolean checked2 = authDataVO.getChecked();
        if (checked == null) {
            if (checked2 != null) {
                return false;
            }
        } else if (!checked.equals(checked2)) {
            return false;
        }
        String id = getId();
        String id2 = authDataVO.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String title = getTitle();
        String title2 = authDataVO.getTitle();
        if (title == null) {
            if (title2 != null) {
                return false;
            }
        } else if (!title.equals(title2)) {
            return false;
        }
        String relId = getRelId();
        String relId2 = authDataVO.getRelId();
        return relId == null ? relId2 == null : relId.equals(relId2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof AuthDataVO;
    }

    public int hashCode() {
        Boolean checked = getChecked();
        int hashCode = (1 * 59) + (checked == null ? 43 : checked.hashCode());
        String id = getId();
        int hashCode2 = (hashCode * 59) + (id == null ? 43 : id.hashCode());
        String title = getTitle();
        int hashCode3 = (hashCode2 * 59) + (title == null ? 43 : title.hashCode());
        String relId = getRelId();
        return (hashCode3 * 59) + (relId == null ? 43 : relId.hashCode());
    }

    public String toString() {
        return "AuthDataVO(id=" + getId() + ", title=" + getTitle() + ", relId=" + getRelId() + ", checked=" + getChecked() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getRelId() {
        return this.relId;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public Boolean isChecked() {
        return Boolean.valueOf(this.relId != null && this.relId.length() > 0);
    }
}
