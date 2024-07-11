package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("onl_cgform_enhance_sql")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/entity/OnlCgformEnhanceSql.class */
public class OnlCgformEnhanceSql implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String cgformHeadId;
    private String buttonCode;
    private String cgbSql;
    private String cgbSqlName;
    private String content;

    public void setId(String id) {
        this.id = id;
    }

    public void setCgformHeadId(String cgformHeadId) {
        this.cgformHeadId = cgformHeadId;
    }

    public void setButtonCode(String buttonCode) {
        this.buttonCode = buttonCode;
    }

    public void setCgbSql(String cgbSql) {
        this.cgbSql = cgbSql;
    }

    public void setCgbSqlName(String cgbSqlName) {
        this.cgbSqlName = cgbSqlName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnlCgformEnhanceSql onlCgformEnhanceSql)) {
            return false;
        }
        if (!onlCgformEnhanceSql.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = onlCgformEnhanceSql.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String cgformHeadId = getCgformHeadId();
        String cgformHeadId2 = onlCgformEnhanceSql.getCgformHeadId();
        if (cgformHeadId == null) {
            if (cgformHeadId2 != null) {
                return false;
            }
        } else if (!cgformHeadId.equals(cgformHeadId2)) {
            return false;
        }
        String buttonCode = getButtonCode();
        String buttonCode2 = onlCgformEnhanceSql.getButtonCode();
        if (buttonCode == null) {
            if (buttonCode2 != null) {
                return false;
            }
        } else if (!buttonCode.equals(buttonCode2)) {
            return false;
        }
        String cgbSql = getCgbSql();
        String cgbSql2 = onlCgformEnhanceSql.getCgbSql();
        if (cgbSql == null) {
            if (cgbSql2 != null) {
                return false;
            }
        } else if (!cgbSql.equals(cgbSql2)) {
            return false;
        }
        String cgbSqlName = getCgbSqlName();
        String cgbSqlName2 = onlCgformEnhanceSql.getCgbSqlName();
        if (cgbSqlName == null) {
            if (cgbSqlName2 != null) {
                return false;
            }
        } else if (!cgbSqlName.equals(cgbSqlName2)) {
            return false;
        }
        String content = getContent();
        String content2 = onlCgformEnhanceSql.getContent();
        return content == null ? content2 == null : content.equals(content2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof OnlCgformEnhanceSql;
    }

    public int hashCode() {
        String id = getId();
        int hashCode = (1 * 59) + (id == null ? 43 : id.hashCode());
        String cgformHeadId = getCgformHeadId();
        int hashCode2 = (hashCode * 59) + (cgformHeadId == null ? 43 : cgformHeadId.hashCode());
        String buttonCode = getButtonCode();
        int hashCode3 = (hashCode2 * 59) + (buttonCode == null ? 43 : buttonCode.hashCode());
        String cgbSql = getCgbSql();
        int hashCode4 = (hashCode3 * 59) + (cgbSql == null ? 43 : cgbSql.hashCode());
        String cgbSqlName = getCgbSqlName();
        int hashCode5 = (hashCode4 * 59) + (cgbSqlName == null ? 43 : cgbSqlName.hashCode());
        String content = getContent();
        return (hashCode5 * 59) + (content == null ? 43 : content.hashCode());
    }

    public String toString() {
        return "OnlCgformEnhanceSql(id=" + getId() + ", cgformHeadId=" + getCgformHeadId() + ", buttonCode=" + getButtonCode() + ", cgbSql=" + getCgbSql() + ", cgbSqlName=" + getCgbSqlName() + ", content=" + getContent() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getCgformHeadId() {
        return this.cgformHeadId;
    }

    public String getButtonCode() {
        return this.buttonCode;
    }

    public String getCgbSql() {
        return this.cgbSql;
    }

    public String getCgbSqlName() {
        return this.cgbSqlName;
    }

    public String getContent() {
        return this.content;
    }
}
