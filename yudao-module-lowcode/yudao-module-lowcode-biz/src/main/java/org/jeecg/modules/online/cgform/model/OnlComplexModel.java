package org.jeecg.modules.online.cgform.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;

/* compiled from: OnlComplexModel.java */
/* renamed from: org.jeecg.modules.online.cgform.model.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/b.class */
public class OnlComplexModel implements Serializable {

    /* renamed from: b */
    private static final long f353b = 1;

    /* renamed from: c */
    private String code;

    /* renamed from: d */
    private String formTemplate;

    /* renamed from: e */
    private String description;

    /* renamed from: f */
    private String currentTableName;

    /* renamed from: g */
    private Integer tableType;

    /* renamed from: h */
    private String paginationFlag;

    /* renamed from: i */
    private String checkboxFlag;

    /* renamed from: j */
    private Integer scrollFlag;

    /* renamed from: k */
    private List<OnlColumn> columns;

    /* renamed from: l */
    private List<String> hideColumns;

    /* renamed from: m */
    private Map<String, List<DictModel>> dictOptions = new HashMap();

    /* renamed from: n */
    private List<OnlCgformButton> cgButtonList;

    /* renamed from: a */
    List<HrefSlots> fieldHrefSlots;

    /* renamed from: o */
    private String enhanceJs;

    /* renamed from: p */
    private List<OnlForeignKey> foreignKeys;

    /* renamed from: q */
    private String pidField;

    /* renamed from: r */
    private String hasChildrenField;

    /* renamed from: s */
    private String textField;

    /* renamed from: t */
    private String isDesForm;

    /* renamed from: u */
    private String desFormCode;

    /* renamed from: v */
    private Integer relationType;

    public void setCode(String code) {
        this.code = code;
    }

    public void setFormTemplate(String formTemplate) {
        this.formTemplate = formTemplate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrentTableName(String currentTableName) {
        this.currentTableName = currentTableName;
    }

    public void setTableType(Integer tableType) {
        this.tableType = tableType;
    }

    public void setPaginationFlag(String paginationFlag) {
        this.paginationFlag = paginationFlag;
    }

    public void setCheckboxFlag(String checkboxFlag) {
        this.checkboxFlag = checkboxFlag;
    }

    public void setScrollFlag(Integer scrollFlag) {
        this.scrollFlag = scrollFlag;
    }

    public void setColumns(List<OnlColumn> columns) {
        this.columns = columns;
    }

    public void setHideColumns(List<String> hideColumns) {
        this.hideColumns = hideColumns;
    }

    public void setDictOptions(Map<String, List<DictModel>> dictOptions) {
        this.dictOptions = dictOptions;
    }

    public void setCgButtonList(List<OnlCgformButton> cgButtonList) {
        this.cgButtonList = cgButtonList;
    }

    public void setFieldHrefSlots(List<HrefSlots> fieldHrefSlots) {
        this.fieldHrefSlots = fieldHrefSlots;
    }

    public void setEnhanceJs(String enhanceJs) {
        this.enhanceJs = enhanceJs;
    }

    public void setForeignKeys(List<OnlForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public void setPidField(String pidField) {
        this.pidField = pidField;
    }

    public void setHasChildrenField(String hasChildrenField) {
        this.hasChildrenField = hasChildrenField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }


    public void setIsDesForm(String isDesForm) {
        this.isDesForm = isDesForm;
    }

    public void setDesFormCode(String desFormCode) {
        this.desFormCode = desFormCode;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlComplexModel)) {
            return false;
        }
        OnlComplexModel onlComplexModel = (OnlComplexModel) o;
        if (!onlComplexModel.m289a(this)) {
            return false;
        }
        Integer tableType = getTableType();
        Integer tableType2 = onlComplexModel.getTableType();
        if (tableType == null) {
            if (tableType2 != null) {
                return false;
            }
        } else if (!tableType.equals(tableType2)) {
            return false;
        }
        Integer scrollFlag = getScrollFlag();
        Integer scrollFlag2 = onlComplexModel.getScrollFlag();
        if (scrollFlag == null) {
            if (scrollFlag2 != null) {
                return false;
            }
        } else if (!scrollFlag.equals(scrollFlag2)) {
            return false;
        }
        Integer relationType = getRelationType();
        Integer relationType2 = onlComplexModel.getRelationType();
        if (relationType == null) {
            if (relationType2 != null) {
                return false;
            }
        } else if (!relationType.equals(relationType2)) {
            return false;
        }
        String code = getCode();
        String code2 = onlComplexModel.getCode();
        if (code == null) {
            if (code2 != null) {
                return false;
            }
        } else if (!code.equals(code2)) {
            return false;
        }
        String formTemplate = getFormTemplate();
        String formTemplate2 = onlComplexModel.getFormTemplate();
        if (formTemplate == null) {
            if (formTemplate2 != null) {
                return false;
            }
        } else if (!formTemplate.equals(formTemplate2)) {
            return false;
        }
        String description = getDescription();
        String description2 = onlComplexModel.getDescription();
        if (description == null) {
            if (description2 != null) {
                return false;
            }
        } else if (!description.equals(description2)) {
            return false;
        }
        String currentTableName = getCurrentTableName();
        String currentTableName2 = onlComplexModel.getCurrentTableName();
        if (currentTableName == null) {
            if (currentTableName2 != null) {
                return false;
            }
        } else if (!currentTableName.equals(currentTableName2)) {
            return false;
        }
        String paginationFlag = getPaginationFlag();
        String paginationFlag2 = onlComplexModel.getPaginationFlag();
        if (paginationFlag == null) {
            if (paginationFlag2 != null) {
                return false;
            }
        } else if (!paginationFlag.equals(paginationFlag2)) {
            return false;
        }
        String checkboxFlag = getCheckboxFlag();
        String checkboxFlag2 = onlComplexModel.getCheckboxFlag();
        if (checkboxFlag == null) {
            if (checkboxFlag2 != null) {
                return false;
            }
        } else if (!checkboxFlag.equals(checkboxFlag2)) {
            return false;
        }
        List<OnlColumn> columns = getColumns();
        List<OnlColumn> columns2 = onlComplexModel.getColumns();
        if (columns == null) {
            if (columns2 != null) {
                return false;
            }
        } else if (!columns.equals(columns2)) {
            return false;
        }
        List<String> hideColumns = getHideColumns();
        List<String> hideColumns2 = onlComplexModel.getHideColumns();
        if (hideColumns == null) {
            if (hideColumns2 != null) {
                return false;
            }
        } else if (!hideColumns.equals(hideColumns2)) {
            return false;
        }
        Map<String, List<DictModel>> dictOptions = getDictOptions();
        Map<String, List<DictModel>> dictOptions2 = onlComplexModel.getDictOptions();
        if (dictOptions == null) {
            if (dictOptions2 != null) {
                return false;
            }
        } else if (!dictOptions.equals(dictOptions2)) {
            return false;
        }
        List<OnlCgformButton> cgButtonList = getCgButtonList();
        List<OnlCgformButton> cgButtonList2 = onlComplexModel.getCgButtonList();
        if (cgButtonList == null) {
            if (cgButtonList2 != null) {
                return false;
            }
        } else if (!cgButtonList.equals(cgButtonList2)) {
            return false;
        }
        List<HrefSlots> fieldHrefSlots = getFieldHrefSlots();
        List<HrefSlots> fieldHrefSlots2 = onlComplexModel.getFieldHrefSlots();
        if (fieldHrefSlots == null) {
            if (fieldHrefSlots2 != null) {
                return false;
            }
        } else if (!fieldHrefSlots.equals(fieldHrefSlots2)) {
            return false;
        }
        String enhanceJs = getEnhanceJs();
        String enhanceJs2 = onlComplexModel.getEnhanceJs();
        if (enhanceJs == null) {
            if (enhanceJs2 != null) {
                return false;
            }
        } else if (!enhanceJs.equals(enhanceJs2)) {
            return false;
        }
        List<OnlForeignKey> foreignKeys = getForeignKeys();
        List<OnlForeignKey> foreignKeys2 = onlComplexModel.getForeignKeys();
        if (foreignKeys == null) {
            if (foreignKeys2 != null) {
                return false;
            }
        } else if (!foreignKeys.equals(foreignKeys2)) {
            return false;
        }
        String pidField = getPidField();
        String pidField2 = onlComplexModel.getPidField();
        if (pidField == null) {
            if (pidField2 != null) {
                return false;
            }
        } else if (!pidField.equals(pidField2)) {
            return false;
        }
        String hasChildrenField = getHasChildrenField();
        String hasChildrenField2 = onlComplexModel.getHasChildrenField();
        if (hasChildrenField == null) {
            if (hasChildrenField2 != null) {
                return false;
            }
        } else if (!hasChildrenField.equals(hasChildrenField2)) {
            return false;
        }
        String textField = getTextField();
        String textField2 = onlComplexModel.getTextField();
        if (textField == null) {
            if (textField2 != null) {
                return false;
            }
        } else if (!textField.equals(textField2)) {
            return false;
        }
        String isDesForm = getIsDesForm();
        String isDesForm2 = onlComplexModel.getIsDesForm();
        if (isDesForm == null) {
            if (isDesForm2 != null) {
                return false;
            }
        } else if (!isDesForm.equals(isDesForm2)) {
            return false;
        }
        String desFormCode = getDesFormCode();
        String desFormCode2 = onlComplexModel.getDesFormCode();
        return desFormCode == null ? desFormCode2 == null : desFormCode.equals(desFormCode2);
    }

    /* renamed from: a */
    protected boolean m289a(Object obj) {
        return obj instanceof OnlComplexModel;
    }

    public int hashCode() {
        Integer tableType = getTableType();
        int hashCode = (1 * 59) + (tableType == null ? 43 : tableType.hashCode());
        Integer scrollFlag = getScrollFlag();
        int hashCode2 = (hashCode * 59) + (scrollFlag == null ? 43 : scrollFlag.hashCode());
        Integer relationType = getRelationType();
        int hashCode3 = (hashCode2 * 59) + (relationType == null ? 43 : relationType.hashCode());
        String code = getCode();
        int hashCode4 = (hashCode3 * 59) + (code == null ? 43 : code.hashCode());
        String formTemplate = getFormTemplate();
        int hashCode5 = (hashCode4 * 59) + (formTemplate == null ? 43 : formTemplate.hashCode());
        String description = getDescription();
        int hashCode6 = (hashCode5 * 59) + (description == null ? 43 : description.hashCode());
        String currentTableName = getCurrentTableName();
        int hashCode7 = (hashCode6 * 59) + (currentTableName == null ? 43 : currentTableName.hashCode());
        String paginationFlag = getPaginationFlag();
        int hashCode8 = (hashCode7 * 59) + (paginationFlag == null ? 43 : paginationFlag.hashCode());
        String checkboxFlag = getCheckboxFlag();
        int hashCode9 = (hashCode8 * 59) + (checkboxFlag == null ? 43 : checkboxFlag.hashCode());
        List<OnlColumn> columns = getColumns();
        int hashCode10 = (hashCode9 * 59) + (columns == null ? 43 : columns.hashCode());
        List<String> hideColumns = getHideColumns();
        int hashCode11 = (hashCode10 * 59) + (hideColumns == null ? 43 : hideColumns.hashCode());
        Map<String, List<DictModel>> dictOptions = getDictOptions();
        int hashCode12 = (hashCode11 * 59) + (dictOptions == null ? 43 : dictOptions.hashCode());
        List<OnlCgformButton> cgButtonList = getCgButtonList();
        int hashCode13 = (hashCode12 * 59) + (cgButtonList == null ? 43 : cgButtonList.hashCode());
        List<HrefSlots> fieldHrefSlots = getFieldHrefSlots();
        int hashCode14 = (hashCode13 * 59) + (fieldHrefSlots == null ? 43 : fieldHrefSlots.hashCode());
        String enhanceJs = getEnhanceJs();
        int hashCode15 = (hashCode14 * 59) + (enhanceJs == null ? 43 : enhanceJs.hashCode());
        List<OnlForeignKey> foreignKeys = getForeignKeys();
        int hashCode16 = (hashCode15 * 59) + (foreignKeys == null ? 43 : foreignKeys.hashCode());
        String pidField = getPidField();
        int hashCode17 = (hashCode16 * 59) + (pidField == null ? 43 : pidField.hashCode());
        String hasChildrenField = getHasChildrenField();
        int hashCode18 = (hashCode17 * 59) + (hasChildrenField == null ? 43 : hasChildrenField.hashCode());
        String textField = getTextField();
        int hashCode19 = (hashCode18 * 59) + (textField == null ? 43 : textField.hashCode());
        String isDesForm = getIsDesForm();
        int hashCode20 = (hashCode19 * 59) + (isDesForm == null ? 43 : isDesForm.hashCode());
        String desFormCode = getDesFormCode();
        return (hashCode20 * 59) + (desFormCode == null ? 43 : desFormCode.hashCode());
    }

    public String toString() {
        return "OnlComplexModel(code=" + getCode() + ", formTemplate=" + getFormTemplate() + ", description=" + getDescription() + ", currentTableName=" + getCurrentTableName() + ", tableType=" + getTableType() + ", paginationFlag=" + getPaginationFlag() + ", checkboxFlag=" + getCheckboxFlag() + ", scrollFlag=" + getScrollFlag() + ", columns=" + getColumns() + ", hideColumns=" + getHideColumns() + ", dictOptions=" + getDictOptions() + ", cgButtonList=" + getCgButtonList() + ", fieldHrefSlots=" + getFieldHrefSlots() + ", enhanceJs=" + getEnhanceJs() + ", foreignKeys=" + getForeignKeys() + ", pidField=" + getPidField() + ", hasChildrenField=" + getHasChildrenField() + ", textField=" + getTextField() + ", isDesForm=" + getIsDesForm() + ", desFormCode=" + getDesFormCode() + ", relationType=" + getRelationType() + ")";
    }

    public String getCode() {
        return this.code;
    }

    public String getFormTemplate() {
        return this.formTemplate;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCurrentTableName() {
        return this.currentTableName;
    }

    public Integer getTableType() {
        return this.tableType;
    }

    public String getPaginationFlag() {
        return this.paginationFlag;
    }

    public String getCheckboxFlag() {
        return this.checkboxFlag;
    }

    public Integer getScrollFlag() {
        return this.scrollFlag;
    }

    public List<OnlColumn> getColumns() {
        return this.columns;
    }

    public List<String> getHideColumns() {
        return this.hideColumns;
    }

    public Map<String, List<DictModel>> getDictOptions() {
        return this.dictOptions;
    }

    public List<OnlCgformButton> getCgButtonList() {
        return this.cgButtonList;
    }

    public List<HrefSlots> getFieldHrefSlots() {
        return this.fieldHrefSlots;
    }

    public String getEnhanceJs() {
        return this.enhanceJs;
    }

    public List<OnlForeignKey> getForeignKeys() {
        return this.foreignKeys;
    }

    public String getPidField() {
        return this.pidField;
    }

    public String getHasChildrenField() {
        return this.hasChildrenField;
    }

    public String getTextField() {
        return this.textField;
    }

    public String getIsDesForm() {
        return this.isDesForm;
    }

    public String getDesFormCode() {
        return this.desFormCode;
    }

    public Integer getRelationType() {
        return this.relationType;
    }
}
