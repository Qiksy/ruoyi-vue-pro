package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_cgform_index")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/entity/OnlCgformIndex.class */
public class OnlCgformIndex implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String cgformHeadId;
    private String indexName;
    private String indexField;
    private String isDbSynch;
    private Integer delFlag;
    private String indexType;
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public void setId(String id) {
        this.id = id;
    }

    public void setCgformHeadId(String cgformHeadId) {
        this.cgformHeadId = cgformHeadId;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public void setIndexField(String indexField) {
        this.indexField = indexField;
    }

    public void setIsDbSynch(String isDbSynch) {
        this.isDbSynch = isDbSynch;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlCgformIndex)) {
            return false;
        }
        OnlCgformIndex onlCgformIndex = (OnlCgformIndex) o;
        if (!onlCgformIndex.canEqual(this)) {
            return false;
        }
        Integer delFlag = getDelFlag();
        Integer delFlag2 = onlCgformIndex.getDelFlag();
        if (delFlag == null) {
            if (delFlag2 != null) {
                return false;
            }
        } else if (!delFlag.equals(delFlag2)) {
            return false;
        }
        String id = getId();
        String id2 = onlCgformIndex.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String cgformHeadId = getCgformHeadId();
        String cgformHeadId2 = onlCgformIndex.getCgformHeadId();
        if (cgformHeadId == null) {
            if (cgformHeadId2 != null) {
                return false;
            }
        } else if (!cgformHeadId.equals(cgformHeadId2)) {
            return false;
        }
        String indexName = getIndexName();
        String indexName2 = onlCgformIndex.getIndexName();
        if (indexName == null) {
            if (indexName2 != null) {
                return false;
            }
        } else if (!indexName.equals(indexName2)) {
            return false;
        }
        String indexField = getIndexField();
        String indexField2 = onlCgformIndex.getIndexField();
        if (indexField == null) {
            if (indexField2 != null) {
                return false;
            }
        } else if (!indexField.equals(indexField2)) {
            return false;
        }
        String isDbSynch = getIsDbSynch();
        String isDbSynch2 = onlCgformIndex.getIsDbSynch();
        if (isDbSynch == null) {
            if (isDbSynch2 != null) {
                return false;
            }
        } else if (!isDbSynch.equals(isDbSynch2)) {
            return false;
        }
        String indexType = getIndexType();
        String indexType2 = onlCgformIndex.getIndexType();
        if (indexType == null) {
            if (indexType2 != null) {
                return false;
            }
        } else if (!indexType.equals(indexType2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlCgformIndex.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlCgformIndex.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlCgformIndex.getUpdateBy();
        if (updateBy == null) {
            if (updateBy2 != null) {
                return false;
            }
        } else if (!updateBy.equals(updateBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlCgformIndex.getUpdateTime();
        return updateTime == null ? updateTime2 == null : updateTime.equals(updateTime2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlCgformIndex;
    }

    public int hashCode() {
        Integer delFlag = getDelFlag();
        int hashCode = (1 * 59) + (delFlag == null ? 43 : delFlag.hashCode());
        String id = getId();
        int hashCode2 = (hashCode * 59) + (id == null ? 43 : id.hashCode());
        String cgformHeadId = getCgformHeadId();
        int hashCode3 = (hashCode2 * 59) + (cgformHeadId == null ? 43 : cgformHeadId.hashCode());
        String indexName = getIndexName();
        int hashCode4 = (hashCode3 * 59) + (indexName == null ? 43 : indexName.hashCode());
        String indexField = getIndexField();
        int hashCode5 = (hashCode4 * 59) + (indexField == null ? 43 : indexField.hashCode());
        String isDbSynch = getIsDbSynch();
        int hashCode6 = (hashCode5 * 59) + (isDbSynch == null ? 43 : isDbSynch.hashCode());
        String indexType = getIndexType();
        int hashCode7 = (hashCode6 * 59) + (indexType == null ? 43 : indexType.hashCode());
        String createBy = getCreateBy();
        int hashCode8 = (hashCode7 * 59) + (createBy == null ? 43 : createBy.hashCode());
        Date createTime = getCreateTime();
        int hashCode9 = (hashCode8 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String updateBy = getUpdateBy();
        int hashCode10 = (hashCode9 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
        Date updateTime = getUpdateTime();
        return (hashCode10 * 59) + (updateTime == null ? 43 : updateTime.hashCode());
    }

    public String toString() {
        return "OnlCgformIndex(id=" + getId() + ", cgformHeadId=" + getCgformHeadId() + ", indexName=" + getIndexName() + ", indexField=" + getIndexField() + ", isDbSynch=" + getIsDbSynch() + ", delFlag=" + getDelFlag() + ", indexType=" + getIndexType() + ", createBy=" + getCreateBy() + ", createTime=" + getCreateTime() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getCgformHeadId() {
        return this.cgformHeadId;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public String getIndexField() {
        return this.indexField;
    }

    public String getIsDbSynch() {
        return this.isDbSynch;
    }

    public Integer getDelFlag() {
        return this.delFlag;
    }

    public String getIndexType() {
        return this.indexType;
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
}
