package org.jeecg.modules.online.cgreport.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_cgreport_head")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/entity/OnlCgreportHead.class */
public class OnlCgreportHead implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String code;
    private String name;
    private String cgrSql;
    private String returnValField;
    private String returnTxtField;
    private String returnType;

//    @Dict(dicCode = "code", dicText = "name", dictTable = "sys_data_source")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String dbSource;
    private String content;
    private String lowAppId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String createBy;

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCgrSql(String cgrSql) {
        this.cgrSql = cgrSql;
    }

    public void setReturnValField(String returnValField) {
        this.returnValField = returnValField;
    }

    public void setReturnTxtField(String returnTxtField) {
        this.returnTxtField = returnTxtField;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLowAppId(String lowAppId) {
        this.lowAppId = lowAppId;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlCgreportHead)) {
            return false;
        }
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) o;
        if (!onlCgreportHead.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = onlCgreportHead.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String code = getCode();
        String code2 = onlCgreportHead.getCode();
        if (code == null) {
            if (code2 != null) {
                return false;
            }
        } else if (!code.equals(code2)) {
            return false;
        }
        String name = getName();
        String name2 = onlCgreportHead.getName();
        if (name == null) {
            if (name2 != null) {
                return false;
            }
        } else if (!name.equals(name2)) {
            return false;
        }
        String cgrSql = getCgrSql();
        String cgrSql2 = onlCgreportHead.getCgrSql();
        if (cgrSql == null) {
            if (cgrSql2 != null) {
                return false;
            }
        } else if (!cgrSql.equals(cgrSql2)) {
            return false;
        }
        String returnValField = getReturnValField();
        String returnValField2 = onlCgreportHead.getReturnValField();
        if (returnValField == null) {
            if (returnValField2 != null) {
                return false;
            }
        } else if (!returnValField.equals(returnValField2)) {
            return false;
        }
        String returnTxtField = getReturnTxtField();
        String returnTxtField2 = onlCgreportHead.getReturnTxtField();
        if (returnTxtField == null) {
            if (returnTxtField2 != null) {
                return false;
            }
        } else if (!returnTxtField.equals(returnTxtField2)) {
            return false;
        }
        String returnType = getReturnType();
        String returnType2 = onlCgreportHead.getReturnType();
        if (returnType == null) {
            if (returnType2 != null) {
                return false;
            }
        } else if (!returnType.equals(returnType2)) {
            return false;
        }
        String dbSource = getDbSource();
        String dbSource2 = onlCgreportHead.getDbSource();
        if (dbSource == null) {
            if (dbSource2 != null) {
                return false;
            }
        } else if (!dbSource.equals(dbSource2)) {
            return false;
        }
        String content = getContent();
        String content2 = onlCgreportHead.getContent();
        if (content == null) {
            if (content2 != null) {
                return false;
            }
        } else if (!content.equals(content2)) {
            return false;
        }
        String lowAppId = getLowAppId();
        String lowAppId2 = onlCgreportHead.getLowAppId();
        if (lowAppId == null) {
            if (lowAppId2 != null) {
                return false;
            }
        } else if (!lowAppId.equals(lowAppId2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlCgreportHead.getUpdateTime();
        if (updateTime == null) {
            if (updateTime2 != null) {
                return false;
            }
        } else if (!updateTime.equals(updateTime2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlCgreportHead.getUpdateBy();
        if (updateBy == null) {
            if (updateBy2 != null) {
                return false;
            }
        } else if (!updateBy.equals(updateBy2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlCgreportHead.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlCgreportHead.getCreateBy();
        return createBy == null ? createBy2 == null : createBy.equals(createBy2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlCgreportHead;
    }

    public int hashCode() {
        String id = getId();
        int hashCode = (1 * 59) + (id == null ? 43 : id.hashCode());
        String code = getCode();
        int hashCode2 = (hashCode * 59) + (code == null ? 43 : code.hashCode());
        String name = getName();
        int hashCode3 = (hashCode2 * 59) + (name == null ? 43 : name.hashCode());
        String cgrSql = getCgrSql();
        int hashCode4 = (hashCode3 * 59) + (cgrSql == null ? 43 : cgrSql.hashCode());
        String returnValField = getReturnValField();
        int hashCode5 = (hashCode4 * 59) + (returnValField == null ? 43 : returnValField.hashCode());
        String returnTxtField = getReturnTxtField();
        int hashCode6 = (hashCode5 * 59) + (returnTxtField == null ? 43 : returnTxtField.hashCode());
        String returnType = getReturnType();
        int hashCode7 = (hashCode6 * 59) + (returnType == null ? 43 : returnType.hashCode());
        String dbSource = getDbSource();
        int hashCode8 = (hashCode7 * 59) + (dbSource == null ? 43 : dbSource.hashCode());
        String content = getContent();
        int hashCode9 = (hashCode8 * 59) + (content == null ? 43 : content.hashCode());
        String lowAppId = getLowAppId();
        int hashCode10 = (hashCode9 * 59) + (lowAppId == null ? 43 : lowAppId.hashCode());
        Date updateTime = getUpdateTime();
        int hashCode11 = (hashCode10 * 59) + (updateTime == null ? 43 : updateTime.hashCode());
        String updateBy = getUpdateBy();
        int hashCode12 = (hashCode11 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
        Date createTime = getCreateTime();
        int hashCode13 = (hashCode12 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String createBy = getCreateBy();
        return (hashCode13 * 59) + (createBy == null ? 43 : createBy.hashCode());
    }

    public String toString() {
        return "OnlCgreportHead(id=" + getId() + ", code=" + getCode() + ", name=" + getName() + ", cgrSql=" + getCgrSql() + ", returnValField=" + getReturnValField() + ", returnTxtField=" + getReturnTxtField() + ", returnType=" + getReturnType() + ", dbSource=" + getDbSource() + ", content=" + getContent() + ", lowAppId=" + getLowAppId() + ", updateTime=" + getUpdateTime() + ", updateBy=" + getUpdateBy() + ", createTime=" + getCreateTime() + ", createBy=" + getCreateBy() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getCgrSql() {
        return this.cgrSql;
    }

    public String getReturnValField() {
        return this.returnValField;
    }

    public String getReturnTxtField() {
        return this.returnTxtField;
    }

    public String getReturnType() {
        return this.returnType;
    }

    public String getDbSource() {
        return this.dbSource;
    }

    public String getContent() {
        return this.content;
    }

    public String getLowAppId() {
        return this.lowAppId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getCreateBy() {
        return this.createBy;
    }
}
