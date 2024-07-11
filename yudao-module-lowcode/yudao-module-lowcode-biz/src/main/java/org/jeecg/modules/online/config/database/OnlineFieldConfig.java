package org.jeecg.modules.online.config.database;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.utils.OnlineDbHandler;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;

/* compiled from: OnlineFieldConfig.java */
/* renamed from: org.jeecg.modules.online.config.b.e */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/b/e.class */
public class OnlineFieldConfig {

    /* renamed from: a */
    private String name;

    /* renamed from: b */
    private String type;

    /* renamed from: c */
    private String view;

    /* renamed from: d */
    private String mode;

    /* renamed from: e */
    private String val;

    /* renamed from: f */
    private String rule;

    /* renamed from: g */
    private Integer isSearch;

    /* renamed from: h */
    private String mainField;

    /* renamed from: i */
    private String mainTable;

    /* renamed from: j */
    private String table;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public void setIsSearch(Integer isSearch) {
        this.isSearch = isSearch;
    }

    public void setMainField(String mainField) {
        this.mainField = mainField;
    }

    public void setMainTable(String mainTable) {
        this.mainTable = mainTable;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlineFieldConfig)) {
            return false;
        }
        OnlineFieldConfig onlineFieldConfig = (OnlineFieldConfig) o;
        if (!onlineFieldConfig.m451a(this)) {
            return false;
        }
        Integer isSearch = getIsSearch();
        Integer isSearch2 = onlineFieldConfig.getIsSearch();
        if (isSearch == null) {
            if (isSearch2 != null) {
                return false;
            }
        } else if (!isSearch.equals(isSearch2)) {
            return false;
        }
        String name = getName();
        String name2 = onlineFieldConfig.getName();
        if (name == null) {
            if (name2 != null) {
                return false;
            }
        } else if (!name.equals(name2)) {
            return false;
        }
        String type = getType();
        String type2 = onlineFieldConfig.getType();
        if (type == null) {
            if (type2 != null) {
                return false;
            }
        } else if (!type.equals(type2)) {
            return false;
        }
        String view = getView();
        String view2 = onlineFieldConfig.getView();
        if (view == null) {
            if (view2 != null) {
                return false;
            }
        } else if (!view.equals(view2)) {
            return false;
        }
        String mode = getMode();
        String mode2 = onlineFieldConfig.getMode();
        if (mode == null) {
            if (mode2 != null) {
                return false;
            }
        } else if (!mode.equals(mode2)) {
            return false;
        }
        String val = getVal();
        String val2 = onlineFieldConfig.getVal();
        if (val == null) {
            if (val2 != null) {
                return false;
            }
        } else if (!val.equals(val2)) {
            return false;
        }
        String rule = getRule();
        String rule2 = onlineFieldConfig.getRule();
        if (rule == null) {
            if (rule2 != null) {
                return false;
            }
        } else if (!rule.equals(rule2)) {
            return false;
        }
        String mainField = getMainField();
        String mainField2 = onlineFieldConfig.getMainField();
        if (mainField == null) {
            if (mainField2 != null) {
                return false;
            }
        } else if (!mainField.equals(mainField2)) {
            return false;
        }
        String mainTable = getMainTable();
        String mainTable2 = onlineFieldConfig.getMainTable();
        if (mainTable == null) {
            if (mainTable2 != null) {
                return false;
            }
        } else if (!mainTable.equals(mainTable2)) {
            return false;
        }
        String table = getTable();
        String table2 = onlineFieldConfig.getTable();
        return table == null ? table2 == null : table.equals(table2);
    }

    /* renamed from: a */
    protected boolean m451a(Object obj) {
        return obj instanceof OnlineFieldConfig;
    }

    public int hashCode() {
        Integer isSearch = getIsSearch();
        int hashCode = (1 * 59) + (isSearch == null ? 43 : isSearch.hashCode());
        String name = getName();
        int hashCode2 = (hashCode * 59) + (name == null ? 43 : name.hashCode());
        String type = getType();
        int hashCode3 = (hashCode2 * 59) + (type == null ? 43 : type.hashCode());
        String view = getView();
        int hashCode4 = (hashCode3 * 59) + (view == null ? 43 : view.hashCode());
        String mode = getMode();
        int hashCode5 = (hashCode4 * 59) + (mode == null ? 43 : mode.hashCode());
        String val = getVal();
        int hashCode6 = (hashCode5 * 59) + (val == null ? 43 : val.hashCode());
        String rule = getRule();
        int hashCode7 = (hashCode6 * 59) + (rule == null ? 43 : rule.hashCode());
        String mainField = getMainField();
        int hashCode8 = (hashCode7 * 59) + (mainField == null ? 43 : mainField.hashCode());
        String mainTable = getMainTable();
        int hashCode9 = (hashCode8 * 59) + (mainTable == null ? 43 : mainTable.hashCode());
        String table = getTable();
        return (hashCode9 * 59) + (table == null ? 43 : table.hashCode());
    }

    public String toString() {
        return "OnlineFieldConfig(name=" + getName() + ", type=" + getType() + ", view=" + getView() + ", mode=" + getMode() + ", val=" + getVal() + ", rule=" + getRule() + ", isSearch=" + getIsSearch() + ", mainField=" + getMainField() + ", mainTable=" + getMainTable() + ", table=" + getTable() + ")";
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getView() {
        return this.view;
    }

    public String getMode() {
        return this.mode;
    }

    public String getVal() {
        return this.val;
    }

    public String getRule() {
        return this.rule;
    }

    public Integer getIsSearch() {
        return this.isSearch;
    }

    public String getMainField() {
        return this.mainField;
    }

    public String getMainTable() {
        return this.mainTable;
    }

    public String getTable() {
        return this.table;
    }

    public OnlineFieldConfig() {
    }

    public OnlineFieldConfig(JSONObject jSONObject) {
        String string = jSONObject.getString("field");
        String[] split = string.split(CgformUtil.COMMA_SEPARATOR);
        if (split.length == 1) {
            this.name = string;
        } else if (split.length == 2) {
            this.name = split[1];
            this.table = split[0];
        }
        String string2 = jSONObject.getString("type");
        String string3 = jSONObject.getString("dbType");
        if (StringUtils.isNotEmpty(string3) && OnlineDbHandler.m278a(string3)) {
            this.type = string3;
        } else {
            this.type = string2;
        }
        if ("list_multi".equals(string2) || CgformUtil.f218M.equals(string2)) {
            this.view = string2;
        }
        this.rule = jSONObject.getString("rule");
        this.val = jSONObject.getString("val");
        this.mode = "single";
        this.isSearch = 1;
    }

    public OnlineFieldConfig(OnlCgreportItem onlCgreportItem) {
        this.name = onlCgreportItem.getFieldName();
        this.type = onlCgreportItem.getFieldType();
        this.mode = onlCgreportItem.getSearchMode();
        this.isSearch = onlCgreportItem.getIsSearch();
    }

    public OnlineFieldConfig(ParamItemVo paramItemVo) {
        this.name = paramItemVo.getFieldName();
        this.type = paramItemVo.getFieldType();
        this.mode = paramItemVo.getSearchMode();
        this.isSearch = 1;
    }

    public OnlineFieldConfig(OnlCgformField onlCgformField) {
        this.name = onlCgformField.getDbFieldName();
        this.type = onlCgformField.getDbType();
        this.mode = onlCgformField.getQueryMode();
        this.isSearch = onlCgformField.getIsQuery();
        this.mainField = onlCgformField.getMainField();
        this.mainTable = onlCgformField.getMainTable();
        if ("1".equals(onlCgformField.getQueryConfigFlag())) {
            this.view = onlCgformField.getQueryShowType();
        } else {
            this.view = onlCgformField.getFieldShowType();
        }
    }
}
