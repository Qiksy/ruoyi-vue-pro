package org.jeecg.modules.online.cgform.model;

import org.jeecg.modules.online.cgform.utils.CgformUtil;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/OnlColumn.class */
public class OnlColumn {
    private String title;
    private String dataIndex;
    private String align;
    private String fieldExtendJson;
    private String customRender;
    private ScopedSlots scopedSlots;
    private String hrefSlotName;
    private int showLength;
    private boolean sorter;
    private String linkField;
    private String tableName;
    private String dbType;
    private String fieldType;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setFieldExtendJson(String fieldExtendJson) {
        this.fieldExtendJson = fieldExtendJson;
    }

    public void setCustomRender(String customRender) {
        this.customRender = customRender;
    }

    public void setScopedSlots(ScopedSlots scopedSlots) {
        this.scopedSlots = scopedSlots;
    }

    public void setHrefSlotName(String hrefSlotName) {
        this.hrefSlotName = hrefSlotName;
    }

    public void setShowLength(int showLength) {
        this.showLength = showLength;
    }

    public void setSorter(boolean sorter) {
        this.sorter = sorter;
    }

    public void setLinkField(String linkField) {
        this.linkField = linkField;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlColumn onlColumn)) {
            return false;
        }
        if (!onlColumn.canEqual(this) || getShowLength() != onlColumn.getShowLength() || isSorter() != onlColumn.isSorter()) {
            return false;
        }
        String title = getTitle();
        String title2 = onlColumn.getTitle();
        if (title == null) {
            if (title2 != null) {
                return false;
            }
        } else if (!title.equals(title2)) {
            return false;
        }
        String dataIndex = getDataIndex();
        String dataIndex2 = onlColumn.getDataIndex();
        if (dataIndex == null) {
            if (dataIndex2 != null) {
                return false;
            }
        } else if (!dataIndex.equals(dataIndex2)) {
            return false;
        }
        String align = getAlign();
        String align2 = onlColumn.getAlign();
        if (align == null) {
            if (align2 != null) {
                return false;
            }
        } else if (!align.equals(align2)) {
            return false;
        }
        String fieldExtendJson = getFieldExtendJson();
        String fieldExtendJson2 = onlColumn.getFieldExtendJson();
        if (fieldExtendJson == null) {
            if (fieldExtendJson2 != null) {
                return false;
            }
        } else if (!fieldExtendJson.equals(fieldExtendJson2)) {
            return false;
        }
        String customRender = getCustomRender();
        String customRender2 = onlColumn.getCustomRender();
        if (customRender == null) {
            if (customRender2 != null) {
                return false;
            }
        } else if (!customRender.equals(customRender2)) {
            return false;
        }
        ScopedSlots scopedSlots = getScopedSlots();
        ScopedSlots scopedSlots2 = onlColumn.getScopedSlots();
        if (scopedSlots == null) {
            if (scopedSlots2 != null) {
                return false;
            }
        } else if (!scopedSlots.equals(scopedSlots2)) {
            return false;
        }
        String hrefSlotName = getHrefSlotName();
        String hrefSlotName2 = onlColumn.getHrefSlotName();
        if (hrefSlotName == null) {
            if (hrefSlotName2 != null) {
                return false;
            }
        } else if (!hrefSlotName.equals(hrefSlotName2)) {
            return false;
        }
        String linkField = getLinkField();
        String linkField2 = onlColumn.getLinkField();
        if (linkField == null) {
            if (linkField2 != null) {
                return false;
            }
        } else if (!linkField.equals(linkField2)) {
            return false;
        }
        String tableName = getTableName();
        String tableName2 = onlColumn.getTableName();
        if (tableName == null) {
            if (tableName2 != null) {
                return false;
            }
        } else if (!tableName.equals(tableName2)) {
            return false;
        }
        String dbType = getDbType();
        String dbType2 = onlColumn.getDbType();
        if (dbType == null) {
            if (dbType2 != null) {
                return false;
            }
        } else if (!dbType.equals(dbType2)) {
            return false;
        }
        String fieldType = getFieldType();
        String fieldType2 = onlColumn.getFieldType();
        return fieldType == null ? fieldType2 == null : fieldType.equals(fieldType2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlColumn;
    }

    public int hashCode() {
        int showLength = (((1 * 59) + getShowLength()) * 59) + (isSorter() ? 79 : 97);
        String title = getTitle();
        int hashCode = (showLength * 59) + (title == null ? 43 : title.hashCode());
        String dataIndex = getDataIndex();
        int hashCode2 = (hashCode * 59) + (dataIndex == null ? 43 : dataIndex.hashCode());
        String align = getAlign();
        int hashCode3 = (hashCode2 * 59) + (align == null ? 43 : align.hashCode());
        String fieldExtendJson = getFieldExtendJson();
        int hashCode4 = (hashCode3 * 59) + (fieldExtendJson == null ? 43 : fieldExtendJson.hashCode());
        String customRender = getCustomRender();
        int hashCode5 = (hashCode4 * 59) + (customRender == null ? 43 : customRender.hashCode());
        ScopedSlots scopedSlots = getScopedSlots();
        int hashCode6 = (hashCode5 * 59) + (scopedSlots == null ? 43 : scopedSlots.hashCode());
        String hrefSlotName = getHrefSlotName();
        int hashCode7 = (hashCode6 * 59) + (hrefSlotName == null ? 43 : hrefSlotName.hashCode());
        String linkField = getLinkField();
        int hashCode8 = (hashCode7 * 59) + (linkField == null ? 43 : linkField.hashCode());
        String tableName = getTableName();
        int hashCode9 = (hashCode8 * 59) + (tableName == null ? 43 : tableName.hashCode());
        String dbType = getDbType();
        int hashCode10 = (hashCode9 * 59) + (dbType == null ? 43 : dbType.hashCode());
        String fieldType = getFieldType();
        return (hashCode10 * 59) + (fieldType == null ? 43 : fieldType.hashCode());
    }

    public String toString() {
        return "OnlColumn(title=" + getTitle() + ", dataIndex=" + getDataIndex() + ", align=" + getAlign() + ", fieldExtendJson=" + getFieldExtendJson() + ", customRender=" + getCustomRender() + ", scopedSlots=" + getScopedSlots() + ", hrefSlotName=" + getHrefSlotName() + ", showLength=" + getShowLength() + ", sorter=" + isSorter() + ", linkField=" + getLinkField() + ", tableName=" + getTableName() + ", dbType=" + getDbType() + ", fieldType=" + getFieldType() + ")";
    }

    public String getTitle() {
        return this.title;
    }

    public String getDataIndex() {
        return this.dataIndex;
    }

    public String getAlign() {
        return this.align;
    }

    public String getFieldExtendJson() {
        return this.fieldExtendJson;
    }

    public String getCustomRender() {
        return this.customRender;
    }

    public ScopedSlots getScopedSlots() {
        return this.scopedSlots;
    }

    public String getHrefSlotName() {
        return this.hrefSlotName;
    }

    public int getShowLength() {
        return this.showLength;
    }

    public boolean isSorter() {
        return this.sorter;
    }

    public String getLinkField() {
        return this.linkField;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getDbType() {
        return this.dbType;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public OnlColumn() {
        this.sorter = false;
    }

    public OnlColumn(String title, String dataIndex) {
        this.sorter = false;
        this.align = CgformUtil.CENTER;
        this.title = title;
        this.dataIndex = dataIndex;
    }
}
