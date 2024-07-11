package org.jeecg.modules.online.auth.vo;

import java.io.Serializable;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/vo/AuthColumnVO.class */
public class AuthColumnVO implements Serializable {
    private static final long serialVersionUID = 5445993027926933917L;

    /* renamed from: id */
    private String id;
    private String cgformId;
    private Integer type;
    private String code;
    private String title;
    private Integer status;
    private boolean listShow;
    private boolean formShow;
    private boolean formEditable;
    private Integer isShowForm;
    private Integer isShowList;
    private String tableName;
    private String tableNameTxt;
    private int switchFlag;
    private Integer dbIsPersist;
    private Boolean isMain;

    public void setId(String id) {
        this.id = id;
    }

    public void setCgformId(String cgformId) {
        this.cgformId = cgformId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setListShow(boolean listShow) {
        this.listShow = listShow;
    }

    public void setFormShow(boolean formShow) {
        this.formShow = formShow;
    }

    public void setFormEditable(boolean formEditable) {
        this.formEditable = formEditable;
    }

    public void setIsShowForm(Integer isShowForm) {
        this.isShowForm = isShowForm;
    }

    public void setIsShowList(Integer isShowList) {
        this.isShowList = isShowList;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableNameTxt(String tableNameTxt) {
        this.tableNameTxt = tableNameTxt;
    }

    public void setSwitchFlag(int switchFlag) {
        this.switchFlag = switchFlag;
    }

    public void setDbIsPersist(Integer dbIsPersist) {
        this.dbIsPersist = dbIsPersist;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuthColumnVO)) {
            return false;
        }
        AuthColumnVO authColumnVO = (AuthColumnVO) o;
        if (!authColumnVO.canEqual(this) || isListShow() != authColumnVO.isListShow() || isFormShow() != authColumnVO.isFormShow() || isFormEditable() != authColumnVO.isFormEditable() || getSwitchFlag() != authColumnVO.getSwitchFlag()) {
            return false;
        }
        Integer type = getType();
        Integer type2 = authColumnVO.getType();
        if (type == null) {
            if (type2 != null) {
                return false;
            }
        } else if (!type.equals(type2)) {
            return false;
        }
        Integer status = getStatus();
        Integer status2 = authColumnVO.getStatus();
        if (status == null) {
            if (status2 != null) {
                return false;
            }
        } else if (!status.equals(status2)) {
            return false;
        }
        Integer isShowForm = getIsShowForm();
        Integer isShowForm2 = authColumnVO.getIsShowForm();
        if (isShowForm == null) {
            if (isShowForm2 != null) {
                return false;
            }
        } else if (!isShowForm.equals(isShowForm2)) {
            return false;
        }
        Integer isShowList = getIsShowList();
        Integer isShowList2 = authColumnVO.getIsShowList();
        if (isShowList == null) {
            if (isShowList2 != null) {
                return false;
            }
        } else if (!isShowList.equals(isShowList2)) {
            return false;
        }
        Integer dbIsPersist = getDbIsPersist();
        Integer dbIsPersist2 = authColumnVO.getDbIsPersist();
        if (dbIsPersist == null) {
            if (dbIsPersist2 != null) {
                return false;
            }
        } else if (!dbIsPersist.equals(dbIsPersist2)) {
            return false;
        }
        Boolean isMain = getIsMain();
        Boolean isMain2 = authColumnVO.getIsMain();
        if (isMain == null) {
            if (isMain2 != null) {
                return false;
            }
        } else if (!isMain.equals(isMain2)) {
            return false;
        }
        String id = getId();
        String id2 = authColumnVO.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String cgformId = getCgformId();
        String cgformId2 = authColumnVO.getCgformId();
        if (cgformId == null) {
            if (cgformId2 != null) {
                return false;
            }
        } else if (!cgformId.equals(cgformId2)) {
            return false;
        }
        String code = getCode();
        String code2 = authColumnVO.getCode();
        if (code == null) {
            if (code2 != null) {
                return false;
            }
        } else if (!code.equals(code2)) {
            return false;
        }
        String title = getTitle();
        String title2 = authColumnVO.getTitle();
        if (title == null) {
            if (title2 != null) {
                return false;
            }
        } else if (!title.equals(title2)) {
            return false;
        }
        String tableName = getTableName();
        String tableName2 = authColumnVO.getTableName();
        if (tableName == null) {
            if (tableName2 != null) {
                return false;
            }
        } else if (!tableName.equals(tableName2)) {
            return false;
        }
        String tableNameTxt = getTableNameTxt();
        String tableNameTxt2 = authColumnVO.getTableNameTxt();
        return tableNameTxt == null ? tableNameTxt2 == null : tableNameTxt.equals(tableNameTxt2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof AuthColumnVO;
    }

    public int hashCode() {
        int switchFlag = (((((((1 * 59) + (isListShow() ? 79 : 97)) * 59) + (isFormShow() ? 79 : 97)) * 59) + (isFormEditable() ? 79 : 97)) * 59) + getSwitchFlag();
        Integer type = getType();
        int hashCode = (switchFlag * 59) + (type == null ? 43 : type.hashCode());
        Integer status = getStatus();
        int hashCode2 = (hashCode * 59) + (status == null ? 43 : status.hashCode());
        Integer isShowForm = getIsShowForm();
        int hashCode3 = (hashCode2 * 59) + (isShowForm == null ? 43 : isShowForm.hashCode());
        Integer isShowList = getIsShowList();
        int hashCode4 = (hashCode3 * 59) + (isShowList == null ? 43 : isShowList.hashCode());
        Integer dbIsPersist = getDbIsPersist();
        int hashCode5 = (hashCode4 * 59) + (dbIsPersist == null ? 43 : dbIsPersist.hashCode());
        Boolean isMain = getIsMain();
        int hashCode6 = (hashCode5 * 59) + (isMain == null ? 43 : isMain.hashCode());
        String id = getId();
        int hashCode7 = (hashCode6 * 59) + (id == null ? 43 : id.hashCode());
        String cgformId = getCgformId();
        int hashCode8 = (hashCode7 * 59) + (cgformId == null ? 43 : cgformId.hashCode());
        String code = getCode();
        int hashCode9 = (hashCode8 * 59) + (code == null ? 43 : code.hashCode());
        String title = getTitle();
        int hashCode10 = (hashCode9 * 59) + (title == null ? 43 : title.hashCode());
        String tableName = getTableName();
        int hashCode11 = (hashCode10 * 59) + (tableName == null ? 43 : tableName.hashCode());
        String tableNameTxt = getTableNameTxt();
        return (hashCode11 * 59) + (tableNameTxt == null ? 43 : tableNameTxt.hashCode());
    }

    public String toString() {
        return "AuthColumnVO(id=" + getId() + ", cgformId=" + getCgformId() + ", type=" + getType() + ", code=" + getCode() + ", title=" + getTitle() + ", status=" + getStatus() + ", listShow=" + isListShow() + ", formShow=" + isFormShow() + ", formEditable=" + isFormEditable() + ", isShowForm=" + getIsShowForm() + ", isShowList=" + getIsShowList() + ", tableName=" + getTableName() + ", tableNameTxt=" + getTableNameTxt() + ", switchFlag=" + getSwitchFlag() + ", dbIsPersist=" + getDbIsPersist() + ", isMain=" + getIsMain() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getCgformId() {
        return this.cgformId;
    }

    public Integer getType() {
        return this.type;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getStatus() {
        return this.status;
    }

    public boolean isListShow() {
        return this.listShow;
    }

    public boolean isFormShow() {
        return this.formShow;
    }

    public boolean isFormEditable() {
        return this.formEditable;
    }

    public Integer getIsShowForm() {
        return this.isShowForm;
    }

    public Integer getIsShowList() {
        return this.isShowList;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getTableNameTxt() {
        return this.tableNameTxt;
    }

    public int getSwitchFlag() {
        return this.switchFlag;
    }

    public Integer getDbIsPersist() {
        return this.dbIsPersist;
    }

    public Boolean getIsMain() {
        return this.isMain;
    }

    public AuthColumnVO() {
        this.type = 1;
    }

    public AuthColumnVO(OnlCgformField field) {
        this.type = 1;
        this.id = field.getId();
        this.cgformId = field.getCgformHeadId();
        this.code = field.getDbFieldName();
        this.title = field.getDbFieldTxt();
        this.type = 1;
        this.isShowForm = field.getIsShowForm();
        this.isShowList = field.getIsShowList();
        this.dbIsPersist = field.getDbIsPersist();
    }
}
