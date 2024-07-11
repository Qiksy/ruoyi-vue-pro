package org.jeecg.modules.online.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_auth_page")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/entity/OnlAuthPage.class */
public class OnlAuthPage implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "online表id", width = 15.0d)
    private String cgformId;

    @Excel(name = "字段名/按钮编码", width = 15.0d)
    private String code;

    @Excel(name = "1字段 2按钮", width = 15.0d)
    private Integer type;

    @Excel(name = "3可编辑 5可见", width = 15.0d)
    private Integer control;

    @Excel(name = "3列表 5表单", width = 15.0d)
    private Integer page;

    @Excel(name = "1有效 0无效", width = 15.0d)
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonIgnore
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonIgnore
    private String createBy;

    @JsonIgnore
    private String updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonIgnore
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date updateTime;

    public OnlAuthPage setId(String id) {
        this.id = id;
        return this;
    }

    public OnlAuthPage setCgformId(String cgformId) {
        this.cgformId = cgformId;
        return this;
    }

    public OnlAuthPage setCode(String code) {
        this.code = code;
        return this;
    }

    public OnlAuthPage setType(Integer type) {
        this.type = type;
        return this;
    }

    public OnlAuthPage setControl(Integer control) {
        this.control = control;
        return this;
    }

    public OnlAuthPage setPage(Integer page) {
        this.page = page;
        return this;
    }

    public OnlAuthPage setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @JsonIgnore
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public OnlAuthPage setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    @JsonIgnore
    public OnlAuthPage setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    @JsonIgnore
    public OnlAuthPage setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    @JsonIgnore
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public OnlAuthPage setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String toString() {
        return "OnlAuthPage(id=" + getId() + ", cgformId=" + getCgformId() + ", code=" + getCode() + ", type=" + getType() + ", control=" + getControl() + ", page=" + getPage() + ", status=" + getStatus() + ", createTime=" + getCreateTime() + ", createBy=" + getCreateBy() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlAuthPage)) {
            return false;
        }
        OnlAuthPage onlAuthPage = (OnlAuthPage) o;
        if (!onlAuthPage.canEqual(this)) {
            return false;
        }
        Integer type = getType();
        Integer type2 = onlAuthPage.getType();
        if (type == null) {
            if (type2 != null) {
                return false;
            }
        } else if (!type.equals(type2)) {
            return false;
        }
        Integer control = getControl();
        Integer control2 = onlAuthPage.getControl();
        if (control == null) {
            if (control2 != null) {
                return false;
            }
        } else if (!control.equals(control2)) {
            return false;
        }
        Integer page = getPage();
        Integer page2 = onlAuthPage.getPage();
        if (page == null) {
            if (page2 != null) {
                return false;
            }
        } else if (!page.equals(page2)) {
            return false;
        }
        Integer status = getStatus();
        Integer status2 = onlAuthPage.getStatus();
        if (status == null) {
            if (status2 != null) {
                return false;
            }
        } else if (!status.equals(status2)) {
            return false;
        }
        String id = getId();
        String id2 = onlAuthPage.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String cgformId = getCgformId();
        String cgformId2 = onlAuthPage.getCgformId();
        if (cgformId == null) {
            if (cgformId2 != null) {
                return false;
            }
        } else if (!cgformId.equals(cgformId2)) {
            return false;
        }
        String code = getCode();
        String code2 = onlAuthPage.getCode();
        if (code == null) {
            if (code2 != null) {
                return false;
            }
        } else if (!code.equals(code2)) {
            return false;
        }
        Date createTime = getCreateTime();
        Date createTime2 = onlAuthPage.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String createBy = getCreateBy();
        String createBy2 = onlAuthPage.getCreateBy();
        if (createBy == null) {
            if (createBy2 != null) {
                return false;
            }
        } else if (!createBy.equals(createBy2)) {
            return false;
        }
        String updateBy = getUpdateBy();
        String updateBy2 = onlAuthPage.getUpdateBy();
        if (updateBy == null) {
            if (updateBy2 != null) {
                return false;
            }
        } else if (!updateBy.equals(updateBy2)) {
            return false;
        }
        Date updateTime = getUpdateTime();
        Date updateTime2 = onlAuthPage.getUpdateTime();
        return updateTime == null ? updateTime2 == null : updateTime.equals(updateTime2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlAuthPage;
    }

    public int hashCode() {
        Integer type = getType();
        int hashCode = (1 * 59) + (type == null ? 43 : type.hashCode());
        Integer control = getControl();
        int hashCode2 = (hashCode * 59) + (control == null ? 43 : control.hashCode());
        Integer page = getPage();
        int hashCode3 = (hashCode2 * 59) + (page == null ? 43 : page.hashCode());
        Integer status = getStatus();
        int hashCode4 = (hashCode3 * 59) + (status == null ? 43 : status.hashCode());
        String id = getId();
        int hashCode5 = (hashCode4 * 59) + (id == null ? 43 : id.hashCode());
        String cgformId = getCgformId();
        int hashCode6 = (hashCode5 * 59) + (cgformId == null ? 43 : cgformId.hashCode());
        String code = getCode();
        int hashCode7 = (hashCode6 * 59) + (code == null ? 43 : code.hashCode());
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

    public String getCode() {
        return this.code;
    }

    public Integer getType() {
        return this.type;
    }

    public Integer getControl() {
        return this.control;
    }

    public Integer getPage() {
        return this.page;
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

    public OnlAuthPage() {
    }

    public OnlAuthPage(String cgformId, String code, int page, int control) {
        this.type = 1;
        this.cgformId = cgformId;
        this.code = code;
        this.control = Integer.valueOf(control);
        this.page = Integer.valueOf(page);
        this.status = 1;
    }
}
