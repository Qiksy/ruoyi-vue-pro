package org.jeecg.modules.online.cgreport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_cgreport_item")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/entity/OnlCgreportItem.class */
public class OnlCgreportItem implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String cgrheadId;
    private String fieldName;
    private String fieldTxt;
    private Integer fieldWidth;
    private String fieldType;
    private String searchMode;
    private Integer isOrder;
    private Integer isSearch;
    private String dictCode;
    private String fieldHref;
    private Integer isShow;
    private Integer orderNum;
    private String replaceVal;
    private String isTotal;
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String groupTitle;

    public void setId(String id) {
        this.id = id;
    }

    public void setCgrheadId(String cgrheadId) {
        this.cgrheadId = cgrheadId;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldTxt(String fieldTxt) {
        this.fieldTxt = fieldTxt;
    }

    public void setFieldWidth(Integer fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public void setIsOrder(Integer isOrder) {
        this.isOrder = isOrder;
    }

    public void setIsSearch(Integer isSearch) {
        this.isSearch = isSearch;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public void setFieldHref(String fieldHref) {
        this.fieldHref = fieldHref;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public void setReplaceVal(String replaceVal) {
        this.replaceVal = replaceVal;
    }

    public void setIsTotal(String isTotal) {
        this.isTotal = isTotal;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlCgreportItem)) {
            return false;
        }
        OnlCgreportItem onlCgreportItem = (OnlCgreportItem) o;
        if (!onlCgreportItem.canEqual(this)) {
            return false;
        }
        Integer fieldWidth = getFieldWidth();
        Integer fieldWidth2 = onlCgreportItem.getFieldWidth();
        if (fieldWidth == null) {
            if (fieldWidth2 != null) {
                return false;
            }
        } else if (!fieldWidth.equals(fieldWidth2)) {
            return false;
        }
        Integer isOrder = getIsOrder();
        Integer isOrder2 = onlCgreportItem.getIsOrder();
        if (isOrder == null) {
            if (isOrder2 != null) {
                return false;
            }
        } else if (!isOrder.equals(isOrder2)) {
            return false;
        }
        Integer isSearch = getIsSearch();
        Integer isSearch2 = onlCgreportItem.getIsSearch();
        if (isSearch == null) {
            if (isSearch2 != null) {
                return false;
            }
        } else if (!isSearch.equals(isSearch2)) {
            return false;
        }
        Integer isShow = getIsShow();
        Integer isShow2 = onlCgreportItem.getIsShow();
        if (isShow == null) {
            if (isShow2 != null) {
                return false;
            }
        } else if (!isShow.equals(isShow2)) {
            return false;
        }
        Integer orderNum = getOrderNum();
        Integer orderNum2 = onlCgreportItem.getOrderNum();
        if (orderNum == null) {
            if (orderNum2 != null) {
                return false;
            }
        } else if (!orderNum.equals(orderNum2)) {
            return false;
        }
        String id = getId();
        String id2 = onlCgreportItem.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String cgrheadId = getCgrheadId();
        String cgrheadId2 = onlCgreportItem.getCgrheadId();
        if (cgrheadId == null) {
            if (cgrheadId2 != null) {
                return false;
            }
        } else if (!cgrheadId.equals(cgrheadId2)) {
            return false;
        }
        String fieldName = getFieldName();
        String fieldName2 = onlCgreportItem.getFieldName();
        if (fieldName == null) {
            if (fieldName2 != null) {
                return false;
            }
        } else if (!fieldName.equals(fieldName2)) {
            return false;
        }
        String fieldTxt = getFieldTxt();
        String fieldTxt2 = onlCgreportItem.getFieldTxt();
        if (fieldTxt == null) {
            if (fieldTxt2 != null) {
                return false;
            }
        } else if (!fieldTxt.equals(fieldTxt2)) {
            return false;
        }
        String fieldType = getFieldType();
        String fieldType2 = onlCgreportItem.getFieldType();
        if (fieldType == null) {
            if (fieldType2 != null) {
                return false;
            }
        } else if (!fieldType.equals(fieldType2)) {
            return false;
        }
        String searchMode = getSearchMode();
        String searchMode2 = onlCgreportItem.getSearchMode();
        if (searchMode == null) {
            if (searchMode2 != null) {
                return false;
            }
        } else if (!searchMode.equals(searchMode2)) {
            return false;
        }
        String dictCode = getDictCode();
        String dictCode2 = onlCgreportItem.getDictCode();
        if (dictCode == null) {
            if (dictCode2 != null) {
                return false;
            }
        } else if (!dictCode.equals(dictCode2)) {
            return false;
        }
        String fieldHref = getFieldHref();
        String fieldHref2 = onlCgreportItem.getFieldHref();
        if (fieldHref == null) {
            if (fieldHref2 != null) {
                return false;
            }
        } else if (!fieldHref.equals(fieldHref2)) {
            return false;
        }
        String replaceVal = getReplaceVal();
        String replaceVal2 = onlCgreportItem.getReplaceVal();
        if (replaceVal == null) {
            if (replaceVal2 != null) {
                return false;
            }
        } else if (!replaceVal.equals(replaceVal2)) {
            return false;
        }
        String isTotal = getIsTotal();
        String isTotal2 = onlCgreportItem.getIsTotal();
        if (isTotal == null) {
            if (isTotal2 != null) {
                return false;
            }
        } else if (!isTotal.equals(isTotal2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlCgreportItem.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlCgreportItem.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlCgreportItem.getUpdateBy();
        if (updateBy == null) {
            if (updateBy2 != null) {
                return false;
            }
        } else if (!updateBy.equals(updateBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlCgreportItem.getUpdateTime();
        if (updateTime == null) {
            if (updateTime2 != null) {
                return false;
            }
        } else if (!updateTime.equals(updateTime2)) {
            return false;
        }
        String groupTitle = getGroupTitle();
        String groupTitle2 = onlCgreportItem.getGroupTitle();
        return groupTitle == null ? groupTitle2 == null : groupTitle.equals(groupTitle2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlCgreportItem;
    }

    public int hashCode() {
        Integer fieldWidth = getFieldWidth();
        int hashCode = (1 * 59) + (fieldWidth == null ? 43 : fieldWidth.hashCode());
        Integer isOrder = getIsOrder();
        int hashCode2 = (hashCode * 59) + (isOrder == null ? 43 : isOrder.hashCode());
        Integer isSearch = getIsSearch();
        int hashCode3 = (hashCode2 * 59) + (isSearch == null ? 43 : isSearch.hashCode());
        Integer isShow = getIsShow();
        int hashCode4 = (hashCode3 * 59) + (isShow == null ? 43 : isShow.hashCode());
        Integer orderNum = getOrderNum();
        int hashCode5 = (hashCode4 * 59) + (orderNum == null ? 43 : orderNum.hashCode());
        String id = getId();
        int hashCode6 = (hashCode5 * 59) + (id == null ? 43 : id.hashCode());
        String cgrheadId = getCgrheadId();
        int hashCode7 = (hashCode6 * 59) + (cgrheadId == null ? 43 : cgrheadId.hashCode());
        String fieldName = getFieldName();
        int hashCode8 = (hashCode7 * 59) + (fieldName == null ? 43 : fieldName.hashCode());
        String fieldTxt = getFieldTxt();
        int hashCode9 = (hashCode8 * 59) + (fieldTxt == null ? 43 : fieldTxt.hashCode());
        String fieldType = getFieldType();
        int hashCode10 = (hashCode9 * 59) + (fieldType == null ? 43 : fieldType.hashCode());
        String searchMode = getSearchMode();
        int hashCode11 = (hashCode10 * 59) + (searchMode == null ? 43 : searchMode.hashCode());
        String dictCode = getDictCode();
        int hashCode12 = (hashCode11 * 59) + (dictCode == null ? 43 : dictCode.hashCode());
        String fieldHref = getFieldHref();
        int hashCode13 = (hashCode12 * 59) + (fieldHref == null ? 43 : fieldHref.hashCode());
        String replaceVal = getReplaceVal();
        int hashCode14 = (hashCode13 * 59) + (replaceVal == null ? 43 : replaceVal.hashCode());
        String isTotal = getIsTotal();
        int hashCode15 = (hashCode14 * 59) + (isTotal == null ? 43 : isTotal.hashCode());
        String createBy = getCreateBy();
        int hashCode16 = (hashCode15 * 59) + (createBy == null ? 43 : createBy.hashCode());
        Date createTime = getCreateTime();
        int hashCode17 = (hashCode16 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String updateBy = getUpdateBy();
        int hashCode18 = (hashCode17 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
        Date updateTime = getUpdateTime();
        int hashCode19 = (hashCode18 * 59) + (updateTime == null ? 43 : updateTime.hashCode());
        String groupTitle = getGroupTitle();
        return (hashCode19 * 59) + (groupTitle == null ? 43 : groupTitle.hashCode());
    }

    public String toString() {
        return "OnlCgreportItem(id=" + getId() + ", cgrheadId=" + getCgrheadId() + ", fieldName=" + getFieldName() + ", fieldTxt=" + getFieldTxt() + ", fieldWidth=" + getFieldWidth() + ", fieldType=" + getFieldType() + ", searchMode=" + getSearchMode() + ", isOrder=" + getIsOrder() + ", isSearch=" + getIsSearch() + ", dictCode=" + getDictCode() + ", fieldHref=" + getFieldHref() + ", isShow=" + getIsShow() + ", orderNum=" + getOrderNum() + ", replaceVal=" + getReplaceVal() + ", isTotal=" + getIsTotal() + ", createBy=" + getCreateBy() + ", createTime=" + getCreateTime() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ", groupTitle=" + getGroupTitle() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getCgrheadId() {
        return this.cgrheadId;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getFieldTxt() {
        return this.fieldTxt;
    }

    public Integer getFieldWidth() {
        return this.fieldWidth;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public String getSearchMode() {
        return this.searchMode;
    }

    public Integer getIsOrder() {
        return this.isOrder;
    }

    public Integer getIsSearch() {
        return this.isSearch;
    }

    public String getDictCode() {
        return this.dictCode;
    }

    public String getFieldHref() {
        return this.fieldHref;
    }

    public Integer getIsShow() {
        return this.isShow;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public String getReplaceVal() {
        return this.replaceVal;
    }

    public String getIsTotal() {
        return this.isTotal;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getGroupTitle() {
        return this.groupTitle;
    }
}
