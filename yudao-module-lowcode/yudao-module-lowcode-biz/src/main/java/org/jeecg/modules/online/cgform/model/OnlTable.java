package org.jeecg.modules.online.cgform.model;

import java.util.List;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* compiled from: OnlTable.java */
/* renamed from: org.jeecg.modules.online.cgform.model.f */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/f.class */
public class OnlTable {

    /* renamed from: a */
    private String tableName;

    /* renamed from: b */
    private String tableId;

    /* renamed from: c */
    private List<OnlCgformField> allFieldList;

    /* renamed from: d */
    private List<OnlCgformField> selectFieldList;

    /* renamed from: e */
    private List<SysPermissionDataRuleModel> authList;

    /* renamed from: f */
    private String mainField;

    /* renamed from: g */
    private String joinField;

    /* renamed from: h */
    private String alias;

    /* renamed from: i */
    private boolean main;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public void setAllFieldList(List<OnlCgformField> allFieldList) {
        this.allFieldList = allFieldList;
    }

    public void setSelectFieldList(List<OnlCgformField> selectFieldList) {
        this.selectFieldList = selectFieldList;
    }

    public void setAuthList(List<SysPermissionDataRuleModel> authList) {
        this.authList = authList;
    }

    public void setMainField(String mainField) {
        this.mainField = mainField;
    }

    public void setJoinField(String joinField) {
        this.joinField = joinField;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setMain(boolean isMain) {
        this.main = isMain;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlTable)) {
            return false;
        }
        OnlTable onlTable = (OnlTable) o;
        if (!onlTable.m293a(this) || m292a() != onlTable.m292a()) {
            return false;
        }
        String tableName = getTableName();
        String tableName2 = onlTable.getTableName();
        if (tableName == null) {
            if (tableName2 != null) {
                return false;
            }
        } else if (!tableName.equals(tableName2)) {
            return false;
        }
        String tableId = getTableId();
        String tableId2 = onlTable.getTableId();
        if (tableId == null) {
            if (tableId2 != null) {
                return false;
            }
        } else if (!tableId.equals(tableId2)) {
            return false;
        }
        List<OnlCgformField> allFieldList = getAllFieldList();
        List<OnlCgformField> allFieldList2 = onlTable.getAllFieldList();
        if (allFieldList == null) {
            if (allFieldList2 != null) {
                return false;
            }
        } else if (!allFieldList.equals(allFieldList2)) {
            return false;
        }
        List<OnlCgformField> selectFieldList = getSelectFieldList();
        List<OnlCgformField> selectFieldList2 = onlTable.getSelectFieldList();
        if (selectFieldList == null) {
            if (selectFieldList2 != null) {
                return false;
            }
        } else if (!selectFieldList.equals(selectFieldList2)) {
            return false;
        }
        List<SysPermissionDataRuleModel> authList = getAuthList();
        List<SysPermissionDataRuleModel> authList2 = onlTable.getAuthList();
        if (authList == null) {
            if (authList2 != null) {
                return false;
            }
        } else if (!authList.equals(authList2)) {
            return false;
        }
        String mainField = getMainField();
        String mainField2 = onlTable.getMainField();
        if (mainField == null) {
            if (mainField2 != null) {
                return false;
            }
        } else if (!mainField.equals(mainField2)) {
            return false;
        }
        String joinField = getJoinField();
        String joinField2 = onlTable.getJoinField();
        if (joinField == null) {
            if (joinField2 != null) {
                return false;
            }
        } else if (!joinField.equals(joinField2)) {
            return false;
        }
        String alias = getAlias();
        String alias2 = onlTable.getAlias();
        return alias == null ? alias2 == null : alias.equals(alias2);
    }

    /* renamed from: a */
    protected boolean m293a(Object obj) {
        return obj instanceof OnlTable;
    }

    public int hashCode() {
        int i = (1 * 59) + (m292a() ? 79 : 97);
        String tableName = getTableName();
        int hashCode = (i * 59) + (tableName == null ? 43 : tableName.hashCode());
        String tableId = getTableId();
        int hashCode2 = (hashCode * 59) + (tableId == null ? 43 : tableId.hashCode());
        List<OnlCgformField> allFieldList = getAllFieldList();
        int hashCode3 = (hashCode2 * 59) + (allFieldList == null ? 43 : allFieldList.hashCode());
        List<OnlCgformField> selectFieldList = getSelectFieldList();
        int hashCode4 = (hashCode3 * 59) + (selectFieldList == null ? 43 : selectFieldList.hashCode());
        List<SysPermissionDataRuleModel> authList = getAuthList();
        int hashCode5 = (hashCode4 * 59) + (authList == null ? 43 : authList.hashCode());
        String mainField = getMainField();
        int hashCode6 = (hashCode5 * 59) + (mainField == null ? 43 : mainField.hashCode());
        String joinField = getJoinField();
        int hashCode7 = (hashCode6 * 59) + (joinField == null ? 43 : joinField.hashCode());
        String alias = getAlias();
        return (hashCode7 * 59) + (alias == null ? 43 : alias.hashCode());
    }

    public String toString() {
        return "OnlTable(tableName=" + getTableName() + ", tableId=" + getTableId() + ", allFieldList=" + getAllFieldList() + ", selectFieldList=" + getSelectFieldList() + ", authList=" + getAuthList() + ", mainField=" + getMainField() + ", joinField=" + getJoinField() + ", alias=" + getAlias() + ", isMain=" + m292a() + ")";
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getTableId() {
        return this.tableId;
    }

    public List<OnlCgformField> getAllFieldList() {
        return this.allFieldList;
    }

    public List<OnlCgformField> getSelectFieldList() {
        return this.selectFieldList;
    }

    public List<SysPermissionDataRuleModel> getAuthList() {
        return this.authList;
    }

    public String getMainField() {
        return this.mainField;
    }

    public String getJoinField() {
        return this.joinField;
    }

    public String getAlias() {
        return this.alias;
    }

    /* renamed from: a */
    public boolean m292a() {
        return this.main;
    }

    public void setAliasByIntValue(int index) {
        this.alias = String.valueOf((char) index);
    }

    public String getTableAlias() {
        return this.alias + ".";
    }

    public OnlTable() {
    }

    public OnlTable(String str, String str2, boolean z) {
        this.tableName = str;
        this.tableId = str2;
        this.main = z;
    }
}
