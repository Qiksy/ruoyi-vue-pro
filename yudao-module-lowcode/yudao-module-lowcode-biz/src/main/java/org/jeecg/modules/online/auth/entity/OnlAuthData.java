package org.jeecg.modules.online.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_auth_data")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/entity/OnlAuthData.class */
public class OnlAuthData implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "online表ID", width = 15.0d)
    private String cgformId;

    @Excel(name = "规则名", width = 15.0d)
    private String ruleName;

    @Excel(name = "规则列", width = 15.0d)
    private String ruleColumn;

    @Excel(name = "规则条件 大于小于like", width = 15.0d)
    private String ruleOperator;

    @Excel(name = "规则值", width = 15.0d)
    private String ruleValue;

    @Excel(name = "1有效 0无效", width = 15.0d)
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;
    private String createBy;
    private String updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date updateTime;

    public OnlAuthData setId(String id) {
        this.id = id;
        return this;
    }

    public OnlAuthData setCgformId(String cgformId) {
        this.cgformId = cgformId;
        return this;
    }

    public OnlAuthData setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public OnlAuthData setRuleColumn(String ruleColumn) {
        this.ruleColumn = ruleColumn;
        return this;
    }

    public OnlAuthData setRuleOperator(String ruleOperator) {
        this.ruleOperator = ruleOperator;
        return this;
    }

    public OnlAuthData setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
        return this;
    }

    public OnlAuthData setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public OnlAuthData setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public OnlAuthData setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public OnlAuthData setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public OnlAuthData setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String toString() {
        return "OnlAuthData(id=" + getId() + ", cgformId=" + getCgformId() + ", ruleName=" + getRuleName() + ", ruleColumn=" + getRuleColumn() + ", ruleOperator=" + getRuleOperator() + ", ruleValue=" + getRuleValue() + ", status=" + getStatus() + ", createTime=" + getCreateTime() + ", createBy=" + getCreateBy() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlAuthData)) {
            return false;
        }
        OnlAuthData onlAuthData = (OnlAuthData) o;
        if (!onlAuthData.canEqual(this)) {
            return false;
        }
        Integer status = getStatus();
        Integer status2 = onlAuthData.getStatus();
        if (status == null) {
            if (status2 != null) {
                return false;
            }
        } else if (!status.equals(status2)) {
            return false;
        }
        String id = getId();
        String id2 = onlAuthData.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String cgformId = getCgformId();
        String cgformId2 = onlAuthData.getCgformId();
        if (cgformId == null) {
            if (cgformId2 != null) {
                return false;
            }
        } else if (!cgformId.equals(cgformId2)) {
            return false;
        }
        String ruleName = getRuleName();
        String ruleName2 = onlAuthData.getRuleName();
        if (ruleName == null) {
            if (ruleName2 != null) {
                return false;
            }
        } else if (!ruleName.equals(ruleName2)) {
            return false;
        }
        String ruleColumn = getRuleColumn();
        String ruleColumn2 = onlAuthData.getRuleColumn();
        if (ruleColumn == null) {
            if (ruleColumn2 != null) {
                return false;
            }
        } else if (!ruleColumn.equals(ruleColumn2)) {
            return false;
        }
        String ruleOperator = getRuleOperator();
        String ruleOperator2 = onlAuthData.getRuleOperator();
        if (ruleOperator == null) {
            if (ruleOperator2 != null) {
                return false;
            }
        } else if (!ruleOperator.equals(ruleOperator2)) {
            return false;
        }
        String ruleValue = getRuleValue();
        String ruleValue2 = onlAuthData.getRuleValue();
        if (ruleValue == null) {
            if (ruleValue2 != null) {
                return false;
            }
        } else if (!ruleValue.equals(ruleValue2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlAuthData.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlAuthData.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlAuthData.getUpdateBy();
        if (updateBy == null) {
            if (updateBy2 != null) {
                return false;
            }
        } else if (!updateBy.equals(updateBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlAuthData.getUpdateTime();
        return updateTime == null ? updateTime2 == null : updateTime.equals(updateTime2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlAuthData;
    }

    public int hashCode() {
        Integer status = getStatus();
        int hashCode = (1 * 59) + (status == null ? 43 : status.hashCode());
        String id = getId();
        int hashCode2 = (hashCode * 59) + (id == null ? 43 : id.hashCode());
        String cgformId = getCgformId();
        int hashCode3 = (hashCode2 * 59) + (cgformId == null ? 43 : cgformId.hashCode());
        String ruleName = getRuleName();
        int hashCode4 = (hashCode3 * 59) + (ruleName == null ? 43 : ruleName.hashCode());
        String ruleColumn = getRuleColumn();
        int hashCode5 = (hashCode4 * 59) + (ruleColumn == null ? 43 : ruleColumn.hashCode());
        String ruleOperator = getRuleOperator();
        int hashCode6 = (hashCode5 * 59) + (ruleOperator == null ? 43 : ruleOperator.hashCode());
        String ruleValue = getRuleValue();
        int hashCode7 = (hashCode6 * 59) + (ruleValue == null ? 43 : ruleValue.hashCode());
        Date createTime = getCreateTime();
        int hashCode8 = (hashCode7 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String createBy = getCreateBy();
        int hashCode9 = (hashCode8 * 59) + (createBy == null ? 43 : createBy.hashCode());
        String updateBy = getUpdateBy();
        int hashCode10 = (hashCode9 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
        Date updateTime = getUpdateTime();
        return (hashCode10 * 59) + (updateTime == null ? 43 : updateTime.hashCode());
    }

    public String getId() {
        return this.id;
    }

    public String getCgformId() {
        return this.cgformId;
    }

    public String getRuleName() {
        return this.ruleName;
    }

    public String getRuleColumn() {
        return this.ruleColumn;
    }

    public String getRuleOperator() {
        return this.ruleOperator;
    }

    public String getRuleValue() {
        return this.ruleValue;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }
}
