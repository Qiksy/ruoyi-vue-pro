package org.jeecg.modules.online.config.template;

import com.baomidou.mybatisplus.annotation.DbType;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.util.dynamic.db.DbTypeUtils;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;

/* compiled from: ColumnMeta.java */
/* renamed from: org.jeecg.modules.online.config.d.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/d/a.class */
public class ColumnMeta {

    /* renamed from: a */
    private String tableName;

    /* renamed from: b */
    private String columnId;

    /* renamed from: c */
    private String columnName;

    /* renamed from: d */
    private int columnSize;

    /* renamed from: e */
    private String columnType;

    /* renamed from: f */
    private String comment;

    /* renamed from: g */
    private String fieldDefault;

    /* renamed from: h */
    private int decimalDigits;

    /* renamed from: i */
    private String isNullable;

    /* renamed from: j */
    private String pkType;

    /* renamed from: k */
    private String oldColumnName;

    /* renamed from: l */
    private String realDbType;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ColumnMeta columnMeta)) {
            return false;
        }
        return (this.columnType.contains(OnlFormShowType.DATE) || this.columnType.contains("blob") || this.columnType.contains(ExtendJsonKey.TEXT)) ? this.columnName.equals(columnMeta.getColumnName()) && this.isNullable.equals(columnMeta.isNullable) && isCommentEqual(this.comment, columnMeta.getComment()) && isCommentEqual(this.fieldDefault, columnMeta.getFieldDefault()) : this.columnType.equals(columnMeta.getColumnType()) && this.isNullable.equals(columnMeta.isNullable) && this.columnSize == columnMeta.getColumnSize() && isCommentEqual(this.comment, columnMeta.getComment()) && isCommentEqual(this.fieldDefault, columnMeta.getFieldDefault());
    }

    /**
     * 判断两个的类型是否一致
     * @param dbType
     * @param meta
     * @return
     */
    /* renamed from: a */
    public boolean isColumnTypeEqual(DbType dbType, ColumnMeta meta) {
        String colunmType = meta.getColumnType();
        if (DbTypeUtils.dbTypeIf(dbType, DbType.ORACLE, DbType.ORACLE_12C)) {
            // 如果是Oracle数据库
            if ("datetime".equalsIgnoreCase(colunmType) && OnlFormShowType.DATE.equalsIgnoreCase(this.columnType)) {
                // 数据库类型 是时间， 本对象的控件展示类型是日期，则返回rue
                return true;
            }
        } else if (DbTypeUtils.dbTypeIsSqlServer(dbType) && OnlFormShowType.DATE.equalsIgnoreCase(colunmType) && "datetime".equalsIgnoreCase(this.columnType)) {
            return true;
        }

        return this.columnType.equalsIgnoreCase(colunmType);
    }

    /**
     * 根据传进来的对象和数据库类型，判断他们是否相等
     * @param newMeta
     * @param dbType
     * @return
     */
    /* renamed from: a */
    public boolean isEqualColumnMeta(Object newMeta, DbType dbType) {
        if (newMeta == this) {
            return true;
        }
        if (!(newMeta instanceof ColumnMeta meta)) {
            return false;
        }
        if (isColumnTypeEqual(dbType, meta)) {
            return DbTypeUtils.dbTypeIsSqlServer(dbType) ?
                    (this.columnType.contains(OnlFormShowType.DATE) || this.columnType.contains("blob") || this.columnType.contains(ExtendJsonKey.TEXT))
                            ? this.columnName.equals(meta.getColumnName()) && this.isNullable.equals(meta.isNullable)
                            : this.columnType.equals(meta.getColumnType()) && this.isNullable.equals(meta.isNullable)
                            && this.columnSize == meta.getColumnSize() && this.decimalDigits == meta.getDecimalDigits()
                            && isCommentEqual(this.fieldDefault, meta.getFieldDefault())
                    : DbTypeUtils.dbTypeIsPostgre(dbType) ?
                    (this.columnType.contains(OnlFormShowType.DATE) || this.columnType.contains("blob") || this.columnType.contains(ExtendJsonKey.TEXT))
                            ?this.columnName.equals(meta.getColumnName()) && this.isNullable.equals(meta.isNullable)
                            :this.columnType.equals(meta.getColumnType()) && this.isNullable.equals(meta.isNullable)
                            && this.columnSize == meta.getColumnSize() && this.decimalDigits == meta.getDecimalDigits()
                            && isCommentEqual(this.fieldDefault, meta.getFieldDefault()) :
                    DbTypeUtils.dbTypeIsOracle(dbType)
                    ? (this.columnType.contains(OnlFormShowType.DATE) || this.columnType.contains("blob") || this.columnType.contains(ExtendJsonKey.TEXT))
                    ? isColumnTypeEqual(dbType, meta) && this.columnName.equals(meta.getColumnName()) && this.isNullable.equals(meta.isNullable)
                    : this.columnType.equals(meta.getColumnType()) && this.isNullable.equals(meta.isNullable) &&
                    this.columnSize == meta.getColumnSize() &&
                    this.decimalDigits == meta.getDecimalDigits()
                    && isCommentEqual(this.fieldDefault, meta.getFieldDefault())
                    : (this.columnType.contains(OnlFormShowType.DATE) || this.columnType.contains("blob") || this.columnType.contains(ExtendJsonKey.TEXT))
                    ? isColumnTypeEqual(dbType, meta) && this.columnName.equals(meta.getColumnName()) && this.isNullable.equals(meta.isNullable)
                    && isCommentEqual(this.comment, meta.getComment()) && isCommentEqual(this.fieldDefault, meta.getFieldDefault())
                    : this.columnType.equals(meta.getColumnType()) && this.isNullable.equals(meta.isNullable) && this.columnSize == meta.getColumnSize()
                    && this.decimalDigits == meta.getDecimalDigits() && isCommentEqual(this.comment, meta.getComment())
                    && isCommentEqual(this.fieldDefault, meta.getFieldDefault());
        }
        return false;
    }

    /* renamed from: a */
    public boolean isCommentEqual(ColumnMeta otherMeta) {
        if (otherMeta == this) {
            return true;
        }
        return isCommentEqual(this.comment, otherMeta.getComment());
    }

    /* renamed from: b */
    public boolean isCommentEqual2(ColumnMeta otherMeta) {
        if (otherMeta == this) {
            return true;
        }
        return isCommentEqual(this.comment, otherMeta.getComment());
    }

    /* renamed from: a */
    private boolean isCommentEqual(String comment1, String comment2) {
        boolean isNotEmpty = StringUtils.isNotEmpty(comment1);
        boolean isNotEmpty2 = StringUtils.isNotEmpty(comment2);
        if ("".equals(comment2)) {
            if (!isNotEmpty || comment1.toLowerCase().toString().indexOf("null") >= 0) {
                return true;
            }
            return false;
        }
        if (isNotEmpty != isNotEmpty2) {
            return false;
        }
        if (isNotEmpty) {
            return comment1.equals(comment2);
        }
        return true;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public int getColumnSize() {
        return this.columnSize;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public String getComment() {
        return this.comment;
    }

    public int getDecimalDigits() {
        return this.decimalDigits;
    }

    public String getIsNullable() {
        return this.isNullable;
    }

    public String getOldColumnName() {
        return this.oldColumnName;
    }

    public int hashCode() {
        return this.columnSize + (this.columnType.hashCode() * this.columnName.hashCode());
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }

    public String toString() {
        return this.columnName + "," + this.columnType + "," + this.isNullable + "," + this.columnSize;
    }

    public String getColumnId() {
        return this.columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldDefault() {
        return this.fieldDefault;
    }

    public void setFieldDefault(String fieldDefault) {
        this.fieldDefault = fieldDefault;
    }

    public String getPkType() {
        return this.pkType;
    }

    public void setPkType(String pkType) {
        this.pkType = pkType;
    }

    public String getRealDbType() {
        return this.realDbType;
    }

    public void setRealDbType(String realDbType) {
        this.realDbType = realDbType;
    }
}
