package org.jeecg.modules.online.config.service.impl;

import java.util.List;

import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;
import org.jeecg.modules.online.config.template.ColumnMeta;
import org.jeecg.modules.online.config.template.DataBaseConst;
import org.jeecg.modules.online.config.service.DbTableHandleI;

/* compiled from: DbTableHyperSQLHandleImpl.java */
/* renamed from: org.jeecg.modules.online.config.service.a.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/service/a/c.class */
public class DbTableHyperSQLHandleImpl implements DbTableHandleI {
    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getAddColumnSql(ColumnMeta columnMeta) {
        String str = " ADD " + columnMeta.getColumnName() + " " + m508a(columnMeta);
        if (ConvertUtils.isNotEmpty(columnMeta.getFieldDefault())) {
            str = str + " DEFAULT " + columnMeta.getFieldDefault();
            if (!"Y".equals(columnMeta.getIsNullable())) {
                str = str + " NOT NULL";
            }
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str;
        String lowerCase = dataType.toLowerCase();
        if (OnlFormShowType.DATE.equals(lowerCase) || "time".equals(lowerCase)) {
            str = OnlFormShowType.DATE;
        } else if ("timestamp".equals(lowerCase)) {
            str = "datetime";
        } else if ("numeric".equals(lowerCase)) {
            str = "bigdecimal";
        } else if ("double".equals(lowerCase)) {
            str = "double";
        } else if ("integer".equals(lowerCase)) {
            str = "int";
        } else if ("clob".equals(lowerCase)) {
            str = ExtendJsonKey.TEXT;
        } else if ("blob".equals(lowerCase)) {
            str = "blob";
        } else {
            str = DataBaseConst.STRING;
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropTableSQL(String tableName) {
        return " DROP TABLE  " + tableName.toUpperCase() + " ";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName.toUpperCase();
    }

    /* renamed from: a */
    private String m508a(ColumnMeta c0100a) {
        String var3;
        String lowerCase = c0100a.getColumnType().toLowerCase();
        switch (lowerCase) {
            case "string":
                var3 = String.format("varchar(%s)", c0100a.getColumnSize());
                break;
            case "date":
                var3 = "DATE";
                break;
            case "datetime":
                var3 = "TIMESTAMP";
                break;
            case "int":
                var3 = "INTEGER";
                break;
            case "double":
                var3 = "double";
                break;
            case "bigdecimal":
                var3 = String.format("NUMERIC(%s, %s)", c0100a.getColumnSize(), c0100a.getDecimalDigits());
                break;
            case "text":
                var3 = "CLOB";
                break;
            case "blob":
                var3 = "BLOB";
                break;
            default:
                var3 = String.format("varchar(%s)", c0100a.getColumnSize());
        }
        return var3;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getReNameFieldName(ColumnMeta columnMeta) {
        return " change " + columnMeta.getOldColumnName() + " " + columnMeta.getColumnName() + " " + m508a(columnMeta);
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getCommentSql(ColumnMeta columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + "'";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return null;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getSpecialHandle(ColumnMeta newMeta, ColumnMeta oldMeta) {
        return null;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String countIndex(String indexName, String tableName) {
        return "select count(*) from user_ind_columns where index_name=upper('" + indexName + "')";
    }

    /* renamed from: a */
    private boolean m509a(String str) {
        String[] strArr = {"clob", "blob", ExtendJsonKey.TEXT, OnlFormShowType.DATE, "double", "int"};
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= strArr.length) {
                break;
            }
            if (!strArr[i].equals(str)) {
                i++;
            } else {
                z = true;
                break;
            }
        }
        return z;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public void handleUpdateMultiSql(ColumnMeta oleMeta, ColumnMeta newMeta, String tableName, List<String> updateSQL) {
        String columnName = newMeta.getColumnName();
        String oldColumnType = oleMeta.getColumnType();
        String newColumnType = newMeta.getColumnType();
        boolean z = false;
        if ((!oldColumnType.equals(newColumnType) || oleMeta.getColumnSize() != newMeta.getColumnSize() || oleMeta.getDecimalDigits() != newMeta.getDecimalDigits()) && (!oldColumnType.equals(newColumnType) || !m509a(newColumnType))) {
            z = true;
        }
        if ("Y".equals(newMeta.getIsNullable()) && !newMeta.getIsNullable().equals(oleMeta.getIsNullable())) {
            z = true;
        }
        if ("N".equals(newMeta.getIsNullable()) && !newMeta.getIsNullable().equals(oleMeta.getIsNullable())) {
            z = true;
        }
        String fieldDefault = oleMeta.getFieldDefault();
        String fieldDefault2 = newMeta.getFieldDefault();
        if ((!ConvertUtils.isEmpty(fieldDefault) || !ConvertUtils.isEmpty(fieldDefault2)) && !fieldDefault2.equals(fieldDefault)) {
            z = true;
        }
        if (z) {
            String format = String.format("alter table %s", tableName);
            updateSQL.add(format + getDropColumnSql(oleMeta.getColumnName()));
            updateSQL.add(format + getAddColumnSql(newMeta));
        }
        if (!oleMeta.isCommentEqual2(newMeta)) {
            updateSQL.add(String.format("COMMENT ON COLUMN %s.%s IS '%s'", tableName, columnName, newMeta.getComment()));
        }
    }
}
