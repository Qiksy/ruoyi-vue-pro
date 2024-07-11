package org.jeecg.modules.online.cgreport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_cgreport_param")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/entity/OnlCgreportParam.class */
public class OnlCgreportParam implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String cgrheadId;
    private String paramName;
    private String paramTxt;
    private String paramValue;
    private Integer orderNum;
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

    public void setCgrheadId(String cgrheadId) {
        this.cgrheadId = cgrheadId;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public void setParamTxt(String paramTxt) {
        this.paramTxt = paramTxt;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
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
        if (!(o instanceof OnlCgreportParam)) {
            return false;
        }
        OnlCgreportParam onlCgreportParam = (OnlCgreportParam) o;
        if (!onlCgreportParam.canEqual(this)) {
            return false;
        }
        Integer orderNum = getOrderNum();
        Integer orderNum2 = onlCgreportParam.getOrderNum();
        if (orderNum == null) {
            if (orderNum2 != null) {
                return false;
            }
        } else if (!orderNum.equals(orderNum2)) {
            return false;
        }
        String id = getId();
        String id2 = onlCgreportParam.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String cgrheadId = getCgrheadId();
        String cgrheadId2 = onlCgreportParam.getCgrheadId();
        if (cgrheadId == null) {
            if (cgrheadId2 != null) {
                return false;
            }
        } else if (!cgrheadId.equals(cgrheadId2)) {
            return false;
        }
        String paramName = getParamName();
        String paramName2 = onlCgreportParam.getParamName();
        if (paramName == null) {
            if (paramName2 != null) {
                return false;
            }
        } else if (!paramName.equals(paramName2)) {
            return false;
        }
        String paramTxt = getParamTxt();
        String paramTxt2 = onlCgreportParam.getParamTxt();
        if (paramTxt == null) {
            if (paramTxt2 != null) {
                return false;
            }
        } else if (!paramTxt.equals(paramTxt2)) {
            return false;
        }
        String paramValue = getParamValue();
        String paramValue2 = onlCgreportParam.getParamValue();
        if (paramValue == null) {
            if (paramValue2 != null) {
                return false;
            }
        } else if (!paramValue.equals(paramValue2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlCgreportParam.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlCgreportParam.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlCgreportParam.getUpdateBy();
        if (updateBy == null) {
            if (updateBy2 != null) {
                return false;
            }
        } else if (!updateBy.equals(updateBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlCgreportParam.getUpdateTime();
        return updateTime == null ? updateTime2 == null : updateTime.equals(updateTime2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlCgreportParam;
    }

    public int hashCode() {
        Integer orderNum = getOrderNum();
        int hashCode = (1 * 59) + (orderNum == null ? 43 : orderNum.hashCode());
        String id = getId();
        int hashCode2 = (hashCode * 59) + (id == null ? 43 : id.hashCode());
        String cgrheadId = getCgrheadId();
        int hashCode3 = (hashCode2 * 59) + (cgrheadId == null ? 43 : cgrheadId.hashCode());
        String paramName = getParamName();
        int hashCode4 = (hashCode3 * 59) + (paramName == null ? 43 : paramName.hashCode());
        String paramTxt = getParamTxt();
        int hashCode5 = (hashCode4 * 59) + (paramTxt == null ? 43 : paramTxt.hashCode());
        String paramValue = getParamValue();
        int hashCode6 = (hashCode5 * 59) + (paramValue == null ? 43 : paramValue.hashCode());
        String createBy = getCreateBy();
        int hashCode7 = (hashCode6 * 59) + (createBy == null ? 43 : createBy.hashCode());
        Date createTime = getCreateTime();
        int hashCode8 = (hashCode7 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String updateBy = getUpdateBy();
        int hashCode9 = (hashCode8 * 59) + (updateBy == null ? 43 : updateBy.hashCode());
        Date updateTime = getUpdateTime();
        return (hashCode9 * 59) + (updateTime == null ? 43 : updateTime.hashCode());
    }

    public String toString() {
        return "OnlCgreportParam(id=" + getId() + ", cgrheadId=" + getCgrheadId() + ", paramName=" + getParamName() + ", paramTxt=" + getParamTxt() + ", paramValue=" + getParamValue() + ", orderNum=" + getOrderNum() + ", createBy=" + getCreateBy() + ", createTime=" + getCreateTime() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getCgrheadId() {
        return this.cgrheadId;
    }

    public String getParamName() {
        return this.paramName;
    }

    public String getParamTxt() {
        return this.paramTxt;
    }

    public String getParamValue() {
        return this.paramValue;
    }

    public Integer getOrderNum() {
        return this.orderNum;
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
