package org.jeecg.modules.online.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import org.jeecgframework.poi.excel.annotation.Excel;

@TableName("onl_auth_relation")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/entity/OnlAuthRelation.class */
public class OnlAuthRelation implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "角色id", width = 15.0d)
    private String roleId;

    @Excel(name = "权限id", width = 15.0d)
    private String authId;

    @Excel(name = "1字段 2按钮 3数据权限", width = 15.0d)
    private Integer type;
    private String cgformId;
    private String authMode;

    public OnlAuthRelation setId(String id) {
        this.id = id;
        return this;
    }

    public OnlAuthRelation setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public OnlAuthRelation setAuthId(String authId) {
        this.authId = authId;
        return this;
    }

    public OnlAuthRelation setType(Integer type) {
        this.type = type;
        return this;
    }

    public OnlAuthRelation setCgformId(String cgformId) {
        this.cgformId = cgformId;
        return this;
    }

    public OnlAuthRelation setAuthMode(String authMode) {
        this.authMode = authMode;
        return this;
    }

    public String toString() {
        return "OnlAuthRelation(id=" + getId() + ", roleId=" + getRoleId() + ", authId=" + getAuthId() + ", type=" + getType() + ", cgformId=" + getCgformId() + ", authMode=" + getAuthMode() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlAuthRelation)) {
            return false;
        }
        OnlAuthRelation onlAuthRelation = (OnlAuthRelation) o;
        if (!onlAuthRelation.canEqual(this)) {
            return false;
        }
        Integer type = getType();
        Integer type2 = onlAuthRelation.getType();
        if (type == null) {
            if (type2 != null) {
                return false;
            }
        } else if (!type.equals(type2)) {
            return false;
        }
        String id = getId();
        String id2 = onlAuthRelation.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String roleId = getRoleId();
        String roleId2 = onlAuthRelation.getRoleId();
        if (roleId == null) {
            if (roleId2 != null) {
                return false;
            }
        } else if (!roleId.equals(roleId2)) {
            return false;
        }
        String authId = getAuthId();
        String authId2 = onlAuthRelation.getAuthId();
        if (authId == null) {
            if (authId2 != null) {
                return false;
            }
        } else if (!authId.equals(authId2)) {
            return false;
        }
        String cgformId = getCgformId();
        String cgformId2 = onlAuthRelation.getCgformId();
        if (cgformId == null) {
            if (cgformId2 != null) {
                return false;
            }
        } else if (!cgformId.equals(cgformId2)) {
            return false;
        }
        String authMode = getAuthMode();
        String authMode2 = onlAuthRelation.getAuthMode();
        return authMode == null ? authMode2 == null : authMode.equals(authMode2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlAuthRelation;
    }

    public int hashCode() {
        Integer type = getType();
        int hashCode = (1 * 59) + (type == null ? 43 : type.hashCode());
        String id = getId();
        int hashCode2 = (hashCode * 59) + (id == null ? 43 : id.hashCode());
        String roleId = getRoleId();
        int hashCode3 = (hashCode2 * 59) + (roleId == null ? 43 : roleId.hashCode());
        String authId = getAuthId();
        int hashCode4 = (hashCode3 * 59) + (authId == null ? 43 : authId.hashCode());
        String cgformId = getCgformId();
        int hashCode5 = (hashCode4 * 59) + (cgformId == null ? 43 : cgformId.hashCode());
        String authMode = getAuthMode();
        return (hashCode5 * 59) + (authMode == null ? 43 : authMode.hashCode());
    }

    public String getId() {
        return this.id;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public String getAuthId() {
        return this.authId;
    }

    public Integer getType() {
        return this.type;
    }

    public String getCgformId() {
        return this.cgformId;
    }

    public String getAuthMode() {
        return this.authMode;
    }
}
