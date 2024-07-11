package org.jeecg.modules.online.auth.vo;

import java.io.Serializable;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/vo/AuthPageVO.class */
public class AuthPageVO implements Serializable {
    private static final long serialVersionUID = 724713901683956568L;

    /* renamed from: id */
    private String id;
    private String code;
    private String title;
    private Integer page;
    private Integer control;
    private String relId;
    private Boolean checked;

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setControl(Integer control) {
        this.control = control;
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
        if (!(o instanceof AuthPageVO)) {
            return false;
        }
        AuthPageVO authPageVO = (AuthPageVO) o;
        if (!authPageVO.canEqual(this)) {
            return false;
        }
        Integer page = getPage();
        Integer page2 = authPageVO.getPage();
        if (page == null) {
            if (page2 != null) {
                return false;
            }
        } else if (!page.equals(page2)) {
            return false;
        }
        Integer control = getControl();
        Integer control2 = authPageVO.getControl();
        if (control == null) {
            if (control2 != null) {
                return false;
            }
        } else if (!control.equals(control2)) {
            return false;
        }
        Boolean checked = getChecked();
        Boolean checked2 = authPageVO.getChecked();
        if (checked == null) {
            if (checked2 != null) {
                return false;
            }
        } else if (!checked.equals(checked2)) {
            return false;
        }
        String id = getId();
        String id2 = authPageVO.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String code = getCode();
        String code2 = authPageVO.getCode();
        if (code == null) {
            if (code2 != null) {
                return false;
            }
        } else if (!code.equals(code2)) {
            return false;
        }
        String title = getTitle();
        String title2 = authPageVO.getTitle();
        if (title == null) {
            if (title2 != null) {
                return false;
            }
        } else if (!title.equals(title2)) {
            return false;
        }
        String relId = getRelId();
        String relId2 = authPageVO.getRelId();
        return relId == null ? relId2 == null : relId.equals(relId2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof AuthPageVO;
    }

    public int hashCode() {
        Integer page = getPage();
        int hashCode = (1 * 59) + (page == null ? 43 : page.hashCode());
        Integer control = getControl();
        int hashCode2 = (hashCode * 59) + (control == null ? 43 : control.hashCode());
        Boolean checked = getChecked();
        int hashCode3 = (hashCode2 * 59) + (checked == null ? 43 : checked.hashCode());
        String id = getId();
        int hashCode4 = (hashCode3 * 59) + (id == null ? 43 : id.hashCode());
        String code = getCode();
        int hashCode5 = (hashCode4 * 59) + (code == null ? 43 : code.hashCode());
        String title = getTitle();
        int hashCode6 = (hashCode5 * 59) + (title == null ? 43 : title.hashCode());
        String relId = getRelId();
        return (hashCode6 * 59) + (relId == null ? 43 : relId.hashCode());
    }

    public String toString() {
        return "AuthPageVO(id=" + getId() + ", code=" + getCode() + ", title=" + getTitle() + ", page=" + getPage() + ", control=" + getControl() + ", relId=" + getRelId() + ", checked=" + getChecked() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getControl() {
        return this.control;
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
