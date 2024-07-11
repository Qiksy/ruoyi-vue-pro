package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 在线表单的表头
 * @author linr
 * @since 2024/5/14 上午8:51
 */
@TableName("onl_cgform_head")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/entity/OnlCgformHead.class */
public class OnlCgformHead implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String tableName;
    /**
     * 表类型 0单表、1主表、2附表
     */
    private Integer tableType;
    private Integer tableVersion;
    private String tableTxt;
    private String isCheckbox;
    private String isDbSynch;
    private String isPage;
    private String isTree;
    private String idSequence;
    private String idType;
    private String queryMode;
    private Integer relationType;
    private String subTableStr;
    private Integer tabOrderNum;
    private String treeParentIdField;
    private String treeIdField;
    private String treeFieldname;
    private String formCategory;
    private String formTemplate;
    private String themeTemplate;
    private String formTemplateMobile;
    private String extConfigJson;
    private String updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer copyType;
    private Integer copyVersion;
    private String physicId;
    private transient Integer hascopy;
    private Integer scroll;
    private transient String taskId;
    private String isDesForm;
    private String desFormCode;
    private String lowAppId;
    private transient String selectFieldString;

    public void setId(String id) {
        this.id = id;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableType(Integer tableType) {
        this.tableType = tableType;
    }

    public void setTableVersion(Integer tableVersion) {
        this.tableVersion = tableVersion;
    }

    public void setTableTxt(String tableTxt) {
        this.tableTxt = tableTxt;
    }

    public void setIsCheckbox(String isCheckbox) {
        this.isCheckbox = isCheckbox;
    }

    public void setIsDbSynch(String isDbSynch) {
        this.isDbSynch = isDbSynch;
    }

    public void setIsPage(String isPage) {
        this.isPage = isPage;
    }

    public void setIsTree(String isTree) {
        this.isTree = isTree;
    }

    public void setIdSequence(String idSequence) {
        this.idSequence = idSequence;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public void setQueryMode(String queryMode) {
        this.queryMode = queryMode;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public void setSubTableStr(String subTableStr) {
        this.subTableStr = subTableStr;
    }

    public void setTabOrderNum(Integer tabOrderNum) {
        this.tabOrderNum = tabOrderNum;
    }

    public void setTreeParentIdField(String treeParentIdField) {
        this.treeParentIdField = treeParentIdField;
    }

    public void setTreeIdField(String treeIdField) {
        this.treeIdField = treeIdField;
    }

    public void setTreeFieldname(String treeFieldname) {
        this.treeFieldname = treeFieldname;
    }

    public void setFormCategory(String formCategory) {
        this.formCategory = formCategory;
    }

    public void setFormTemplate(String formTemplate) {
        this.formTemplate = formTemplate;
    }

    public void setThemeTemplate(String themeTemplate) {
        this.themeTemplate = themeTemplate;
    }

    public void setFormTemplateMobile(String formTemplateMobile) {
        this.formTemplateMobile = formTemplateMobile;
    }

    public void setExtConfigJson(String extConfigJson) {
        this.extConfigJson = extConfigJson;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCopyType(Integer copyType) {
        this.copyType = copyType;
    }

    public void setCopyVersion(Integer copyVersion) {
        this.copyVersion = copyVersion;
    }

    public void setPhysicId(String physicId) {
        this.physicId = physicId;
    }

    public void setHascopy(Integer hascopy) {
        this.hascopy = hascopy;
    }

    public void setScroll(Integer scroll) {
        this.scroll = scroll;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setIsDesForm(String isDesForm) {
        this.isDesForm = isDesForm;
    }

    public void setDesFormCode(String desFormCode) {
        this.desFormCode = desFormCode;
    }

    public void setLowAppId(String lowAppId) {
        this.lowAppId = lowAppId;
    }

    public void setSelectFieldString(String selectFieldString) {
        this.selectFieldString = selectFieldString;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlCgformHead)) {
            return false;
        }
        OnlCgformHead onlCgformHead = (OnlCgformHead) o;
        if (!onlCgformHead.canEqual(this)) {
            return false;
        }
        Integer tableType = getTableType();
        Integer tableType2 = onlCgformHead.getTableType();
        if (tableType == null) {
            if (tableType2 != null) {
                return false;
            }
        } else if (!tableType.equals(tableType2)) {
            return false;
        }
        Integer tableVersion = getTableVersion();
        Integer tableVersion2 = onlCgformHead.getTableVersion();
        if (tableVersion == null) {
            if (tableVersion2 != null) {
                return false;
            }
        } else if (!tableVersion.equals(tableVersion2)) {
            return false;
        }
        Integer relationType = getRelationType();
        Integer relationType2 = onlCgformHead.getRelationType();
        if (relationType == null) {
            if (relationType2 != null) {
                return false;
            }
        } else if (!relationType.equals(relationType2)) {
            return false;
        }
        Integer tabOrderNum = getTabOrderNum();
        Integer tabOrderNum2 = onlCgformHead.getTabOrderNum();
        if (tabOrderNum == null) {
            if (tabOrderNum2 != null) {
                return false;
            }
        } else if (!tabOrderNum.equals(tabOrderNum2)) {
            return false;
        }
        Integer copyType = getCopyType();
        Integer copyType2 = onlCgformHead.getCopyType();
        if (copyType == null) {
            if (copyType2 != null) {
                return false;
            }
        } else if (!copyType.equals(copyType2)) {
            return false;
        }
        Integer copyVersion = getCopyVersion();
        Integer copyVersion2 = onlCgformHead.getCopyVersion();
        if (copyVersion == null) {
            if (copyVersion2 != null) {
                return false;
            }
        } else if (!copyVersion.equals(copyVersion2)) {
            return false;
        }
        Integer scroll = getScroll();
        Integer scroll2 = onlCgformHead.getScroll();
        if (scroll == null) {
            if (scroll2 != null) {
                return false;
            }
        } else if (!scroll.equals(scroll2)) {
            return false;
        }
        String id = getId();
        String id2 = onlCgformHead.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String tableName = getTableName();
        String tableName2 = onlCgformHead.getTableName();
        if (tableName == null) {
            if (tableName2 != null) {
                return false;
            }
        } else if (!tableName.equals(tableName2)) {
            return false;
        }
        String tableTxt = getTableTxt();
        String tableTxt2 = onlCgformHead.getTableTxt();
        if (tableTxt == null) {
            if (tableTxt2 != null) {
                return false;
            }
        } else if (!tableTxt.equals(tableTxt2)) {
            return false;
        }
        String isCheckbox = getIsCheckbox();
        String isCheckbox2 = onlCgformHead.getIsCheckbox();
        if (isCheckbox == null) {
            if (isCheckbox2 != null) {
                return false;
            }
        } else if (!isCheckbox.equals(isCheckbox2)) {
            return false;
        }
        String isDbSynch = getIsDbSynch();
        String isDbSynch2 = onlCgformHead.getIsDbSynch();
        if (isDbSynch == null) {
            if (isDbSynch2 != null) {
                return false;
            }
        } else if (!isDbSynch.equals(isDbSynch2)) {
            return false;
        }
        String isPage = getIsPage();
        String isPage2 = onlCgformHead.getIsPage();
        if (isPage == null) {
            if (isPage2 != null) {
                return false;
            }
        } else if (!isPage.equals(isPage2)) {
            return false;
        }
        String isTree = getIsTree();
        String isTree2 = onlCgformHead.getIsTree();
        if (isTree == null) {
            if (isTree2 != null) {
                return false;
            }
        } else if (!isTree.equals(isTree2)) {
            return false;
        }
        String idSequence = getIdSequence();
        String idSequence2 = onlCgformHead.getIdSequence();
        if (idSequence == null) {
            if (idSequence2 != null) {
                return false;
            }
        } else if (!idSequence.equals(idSequence2)) {
            return false;
        }
        String idType = getIdType();
        String idType2 = onlCgformHead.getIdType();
        if (idType == null) {
            if (idType2 != null) {
                return false;
            }
        } else if (!idType.equals(idType2)) {
            return false;
        }
        String queryMode = getQueryMode();
        String queryMode2 = onlCgformHead.getQueryMode();
        if (queryMode == null) {
            if (queryMode2 != null) {
                return false;
            }
        } else if (!queryMode.equals(queryMode2)) {
            return false;
        }
        String subTableStr = getSubTableStr();
        String subTableStr2 = onlCgformHead.getSubTableStr();
        if (subTableStr == null) {
            if (subTableStr2 != null) {
                return false;
            }
        } else if (!subTableStr.equals(subTableStr2)) {
            return false;
        }
        String treeParentIdField = getTreeParentIdField();
        String treeParentIdField2 = onlCgformHead.getTreeParentIdField();
        if (treeParentIdField == null) {
            if (treeParentIdField2 != null) {
                return false;
            }
        } else if (!treeParentIdField.equals(treeParentIdField2)) {
            return false;
        }
        String treeIdField = getTreeIdField();
        String treeIdField2 = onlCgformHead.getTreeIdField();
        if (treeIdField == null) {
            if (treeIdField2 != null) {
                return false;
            }
        } else if (!treeIdField.equals(treeIdField2)) {
            return false;
        }
        String treeFieldname = getTreeFieldname();
        String treeFieldname2 = onlCgformHead.getTreeFieldname();
        if (treeFieldname == null) {
            if (treeFieldname2 != null) {
                return false;
            }
        } else if (!treeFieldname.equals(treeFieldname2)) {
            return false;
        }
        String formCategory = getFormCategory();
        String formCategory2 = onlCgformHead.getFormCategory();
        if (formCategory == null) {
            if (formCategory2 != null) {
                return false;
            }
        } else if (!formCategory.equals(formCategory2)) {
            return false;
        }
        String formTemplate = getFormTemplate();
        String formTemplate2 = onlCgformHead.getFormTemplate();
        if (formTemplate == null) {
            if (formTemplate2 != null) {
                return false;
            }
        } else if (!formTemplate.equals(formTemplate2)) {
            return false;
        }
        String themeTemplate = getThemeTemplate();
        String themeTemplate2 = onlCgformHead.getThemeTemplate();
        if (themeTemplate == null) {
            if (themeTemplate2 != null) {
                return false;
            }
        } else if (!themeTemplate.equals(themeTemplate2)) {
            return false;
        }
        String formTemplateMobile = getFormTemplateMobile();
        String formTemplateMobile2 = onlCgformHead.getFormTemplateMobile();
        if (formTemplateMobile == null) {
            if (formTemplateMobile2 != null) {
                return false;
            }
        } else if (!formTemplateMobile.equals(formTemplateMobile2)) {
            return false;
        }
        String extConfigJson = getExtConfigJson();
        String extConfigJson2 = onlCgformHead.getExtConfigJson();
        if (extConfigJson == null) {
            if (extConfigJson2 != null) {
                return false;
            }
        } else if (!extConfigJson.equals(extConfigJson2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlCgformHead.getUpdateBy();
        if (updateBy == null) {
            if (updateBy2 != null) {
                return false;
            }
        } else if (!updateBy.equals(updateBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlCgformHead.getUpdateTime();
        if (updateTime == null) {
            if (updateTime2 != null) {
                return false;
            }
        } else if (!updateTime.equals(updateTime2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlCgformHead.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlCgformHead.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String physicId = getPhysicId();
        String physicId2 = onlCgformHead.getPhysicId();
        if (physicId == null) {
            if (physicId2 != null) {
                return false;
            }
        } else if (!physicId.equals(physicId2)) {
            return false;
        }
        String isDesForm = getIsDesForm();
        String isDesForm2 = onlCgformHead.getIsDesForm();
        if (isDesForm == null) {
            if (isDesForm2 != null) {
                return false;
            }
        } else if (!isDesForm.equals(isDesForm2)) {
            return false;
        }
        String desFormCode = getDesFormCode();
        String desFormCode2 = onlCgformHead.getDesFormCode();
        if (desFormCode == null) {
            if (desFormCode2 != null) {
                return false;
            }
        } else if (!desFormCode.equals(desFormCode2)) {
            return false;
        }
        String lowAppId = getLowAppId();
        String lowAppId2 = onlCgformHead.getLowAppId();
        return lowAppId == null ? lowAppId2 == null : lowAppId.equals(lowAppId2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlCgformHead;
    }

    public int hashCode() {
        Integer tableType = getTableType();
        int hashCode = (1 * 59) + (tableType == null ? 43 : tableType.hashCode());
        Integer tableVersion = getTableVersion();
        int hashCode2 = (hashCode * 59) + (tableVersion == null ? 43 : tableVersion.hashCode());
        Integer relationType = getRelationType();
        int hashCode3 = (hashCode2 * 59) + (relationType == null ? 43 : relationType.hashCode());
        Integer tabOrderNum = getTabOrderNum();
        int hashCode4 = (hashCode3 * 59) + (tabOrderNum == null ? 43 : tabOrderNum.hashCode());
        Integer copyType = getCopyType();
        int hashCode5 = (hashCode4 * 59) + (copyType == null ? 43 : copyType.hashCode());
        Integer copyVersion = getCopyVersion();
        int hashCode6 = (hashCode5 * 59) + (copyVersion == null ? 43 : copyVersion.hashCode());
        Integer scroll = getScroll();
        int hashCode7 = (hashCode6 * 59) + (scroll == null ? 43 : scroll.hashCode());
        String id = getId();
        int hashCode8 = (hashCode7 * 59) + (id == null ? 43 : id.hashCode());
        String tableName = getTableName();
        int hashCode9 = (hashCode8 * 59) + (tableName == null ? 43 : tableName.hashCode());
        String tableTxt = getTableTxt();
        int hashCode10 = (hashCode9 * 59) + (tableTxt == null ? 43 : tableTxt.hashCode());
        String isCheckbox = getIsCheckbox();
        int hashCode11 = (hashCode10 * 59) + (isCheckbox == null ? 43 : isCheckbox.hashCode());
        String isDbSynch = getIsDbSynch();
        int hashCode12 = (hashCode11 * 59) + (isDbSynch == null ? 43 : isDbSynch.hashCode());
        String isPage = getIsPage();
        int hashCode13 = (hashCode12 * 59) + (isPage == null ? 43 : isPage.hashCode());
        String isTree = getIsTree();
        int hashCode14 = (hashCode13 * 59) + (isTree == null ? 43 : isTree.hashCode());
        String idSequence = getIdSequence();
        int hashCode15 = (hashCode14 * 59) + (idSequence == null ? 43 : idSequence.hashCode());
        String idType = getIdType();
        int hashCode16 = (hashCode15 * 59) + (idType == null ? 43 : idType.hashCode());
        String queryMode = getQueryMode();
        int hashCode17 = (hashCode16 * 59) + (queryMode == null ? 43 : queryMode.hashCode());
        String subTableStr = getSubTableStr();
        int hashCode18 = (hashCode17 * 59) + (subTableStr == null ? 43 : subTableStr.hashCode());
        String treeParentIdField = getTreeParentIdField();
        int hashCode19 = (hashCode18 * 59) + (treeParentIdField == null ? 43 : treeParentIdField.hashCode());
        String treeIdField = getTreeIdField();
        int hashCode20 = (hashCode19 * 59) + (treeIdField == null ? 43 : treeIdField.hashCode());
        String treeFieldname = getTreeFieldname();
        int hashCode21 = (hashCode20 * 59) + (treeFieldname == null ? 43 : treeFieldname.hashCode());
        String formCategory = getFormCategory();
        int hashCode22 = (hashCode21 * 59) + (formCategory == null ? 43 : formCategory.hashCode());
        String formTemplate = getFormTemplate();
        int hashCode23 = (hashCode22 * 59) + (formTemplate == null ? 43 : formTemplate.hashCode());
        String themeTemplate = getThemeTemplate();
        int hashCode24 = (hashCode23 * 59) + (themeTemplate == null ? 43 : themeTemplate.hashCode());
        String formTemplateMobile = getFormTemplateMobile();
        int hashCode25 = (hashCode24 * 59) + (formTemplateMobile == null ? 43 : formTemplateMobile.hashCode());
        String extConfigJson = getExtConfigJson();
        int hashCode26 = (hashCode25 * 59) + (extConfigJson == null ? 43 : extConfigJson.hashCode());
        String updateBy = getUpdateBy();
        int hashCode27 = (hashCode26 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
        Date updateTime = getUpdateTime();
        int hashCode28 = (hashCode27 * 59) + (updateTime == null ? 43 : updateTime.hashCode());
        String createBy = getCreateBy();
        int hashCode29 = (hashCode28 * 59) + (createBy == null ? 43 : createBy.hashCode());
        Date createTime = getCreateTime();
        int hashCode30 = (hashCode29 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String physicId = getPhysicId();
        int hashCode31 = (hashCode30 * 59) + (physicId == null ? 43 : physicId.hashCode());
        String isDesForm = getIsDesForm();
        int hashCode32 = (hashCode31 * 59) + (isDesForm == null ? 43 : isDesForm.hashCode());
        String desFormCode = getDesFormCode();
        int hashCode33 = (hashCode32 * 59) + (desFormCode == null ? 43 : desFormCode.hashCode());
        String lowAppId = getLowAppId();
        return (hashCode33 * 59) + (lowAppId == null ? 43 : lowAppId.hashCode());
    }

    public String toString() {
        return "OnlCgformHead(id=" + getId() + ", tableName=" + getTableName() + ", tableType=" + getTableType() + ", tableVersion=" + getTableVersion() + ", tableTxt=" + getTableTxt() + ", isCheckbox=" + getIsCheckbox() + ", isDbSynch=" + getIsDbSynch() + ", isPage=" + getIsPage() + ", isTree=" + getIsTree() + ", idSequence=" + getIdSequence() + ", idType=" + getIdType() + ", queryMode=" + getQueryMode() + ", relationType=" + getRelationType() + ", subTableStr=" + getSubTableStr() + ", tabOrderNum=" + getTabOrderNum() + ", treeParentIdField=" + getTreeParentIdField() + ", treeIdField=" + getTreeIdField() + ", treeFieldname=" + getTreeFieldname() + ", formCategory=" + getFormCategory() + ", formTemplate=" + getFormTemplate() + ", themeTemplate=" + getThemeTemplate() + ", formTemplateMobile=" + getFormTemplateMobile() + ", extConfigJson=" + getExtConfigJson() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ", createBy=" + getCreateBy() + ", createTime=" + getCreateTime() + ", copyType=" + getCopyType() + ", copyVersion=" + getCopyVersion() + ", physicId=" + getPhysicId() + ", hascopy=" + getHascopy() + ", scroll=" + getScroll() + ", taskId=" + getTaskId() + ", isDesForm=" + getIsDesForm() + ", desFormCode=" + getDesFormCode() + ", lowAppId=" + getLowAppId() + ", selectFieldString=" + getSelectFieldString() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getTableName() {
        return this.tableName;
    }

    public Integer getTableType() {
        return this.tableType;
    }

    public Integer getTableVersion() {
        return this.tableVersion;
    }

    public String getTableTxt() {
        return this.tableTxt;
    }

    public String getIsCheckbox() {
        return this.isCheckbox;
    }

    public String getIsDbSynch() {
        return this.isDbSynch;
    }

    public String getIsPage() {
        return this.isPage;
    }

    public String getIsTree() {
        return this.isTree;
    }

    public String getIdSequence() {
        return this.idSequence;
    }

    public String getIdType() {
        return this.idType;
    }

    public String getQueryMode() {
        return this.queryMode;
    }

    public Integer getRelationType() {
        return this.relationType;
    }

    public String getSubTableStr() {
        return this.subTableStr;
    }

    public Integer getTabOrderNum() {
        return this.tabOrderNum;
    }

    public String getTreeParentIdField() {
        return this.treeParentIdField;
    }

    public String getTreeIdField() {
        return this.treeIdField;
    }

    public String getTreeFieldname() {
        return this.treeFieldname;
    }

    public String getFormCategory() {
        return this.formCategory;
    }

    public String getFormTemplate() {
        return this.formTemplate;
    }

    public String getThemeTemplate() {
        return this.themeTemplate;
    }

    public String getFormTemplateMobile() {
        return this.formTemplateMobile;
    }

    public String getExtConfigJson() {
        return this.extConfigJson;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Integer getCopyType() {
        return this.copyType;
    }

    public Integer getCopyVersion() {
        return this.copyVersion;
    }

    public String getPhysicId() {
        return this.physicId;
    }

    public Integer getHascopy() {
        return this.hascopy;
    }

    public Integer getScroll() {
        return this.scroll;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getIsDesForm() {
        return this.isDesForm;
    }

    public String getDesFormCode() {
        return this.desFormCode;
    }

    public String getLowAppId() {
        return this.lowAppId;
    }

    public String getSelectFieldString() {
        return this.selectFieldString;
    }

    public List<String> getSelectFieldList() {
        if (this.selectFieldString == null || "".equals(this.selectFieldString)) {
            return null;
        }
        return Arrays.asList(this.selectFieldString.split(CgformUtil.COMMA_SEPARATOR));
    }
}
