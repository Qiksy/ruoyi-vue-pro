package org.jeecg.modules.online.config.service.impl;

import java.util.List;

import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;
import org.jeecg.modules.online.config.template.ColumnMeta;
import org.jeecg.modules.online.config.service.DbTableHandleI;

/* compiled from: DbTableDB2HandleImpl.java */
/* renamed from: org.jeecg.modules.online.config.service.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/service/a/a.class */
public class DbTableDB2HandleImpl implements DbTableHandleI {
    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getAddColumnSql(ColumnMeta columnMeta) {
        String str = " ADD " + columnMeta.getColumnName() + " " + m504a(columnMeta);
        if (StrUtils.isNotEmpty(columnMeta.getFieldDefault())) {
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
        str = switch (lowerCase) {
            case "varchar" -> "string";
            case "date", "time" -> "date";
            case "timestamp" -> "datetime";
            case "integer" -> "int";
            case "double" -> "double";
            case "decimal" -> "bigdecimal";
            case "long varchar" -> "text";
            case "blob" -> "blob";
            default -> "string";
        };
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropTableSQL(String tableName) {
        return " DROP TABLE  " + tableName.toLowerCase() + " ";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName.toUpperCase();
    }

    /* renamed from: a */
    private String m504a(ColumnMeta c0100a) {
        String format;
        String lowerCase = c0100a.getColumnType().toLowerCase();

        format = switch (lowerCase) {
            case "string" -> String.format("varchar(%s)", c0100a.getColumnSize());
            case "date" -> "DATE";
            case "datetime" -> "TIMESTAMP";
            case "int" -> "INTEGER";
            case "double" -> "double";
            case "bigdecimal" -> String.format("DECIMAL(%s, %s)", c0100a.getColumnSize(), c0100a.getDecimalDigits());
            case "text" -> "LONG VARCHAR";
            case "blob" -> "BLOB";
            default -> String.format("varchar(%s)", c0100a.getColumnSize());
        };
        return format;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getReNameFieldName(ColumnMeta columnMeta) {
        return "RENAME COLUMN  " + columnMeta.getOldColumnName() + " TO " + columnMeta.getColumnName();
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
    private boolean m505a(String str) {
        String[] strArr = {"blob", ExtendJsonKey.TEXT, "double", "int", OnlFormShowType.DATE};
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
        String colunmType = oleMeta.getColumnType();
        String colunmType2 = newMeta.getColumnType();
        if ((!colunmType.equals(colunmType2) || oleMeta.getColumnSize() != newMeta.getColumnSize() || oleMeta.getDecimalDigits() != newMeta.getDecimalDigits()) && (!colunmType.equals(colunmType2) || !m505a(colunmType2))) {
            updateSQL.add("alter table " + tableName + " alter column " + columnName + " set data type " + m504a(newMeta));
        }
        if ("Y".equals(newMeta.getIsNullable()) && !newMeta.getIsNullable().equals(oleMeta.getIsNullable())) {
            updateSQL.add(String.format("alter table %s alter column %s drop not null", tableName, columnName));
        }
        if ("N".equals(newMeta.getIsNullable()) && !newMeta.getIsNullable().equals(oleMeta.getIsNullable())) {
            updateSQL.add(String.format("alter table %s alter column %s set not null", tableName, columnName));
        }
        String fieldDefault = oleMeta.getFieldDefault();
        String fieldDefault2 = newMeta.getFieldDefault();
        if ((!StrUtils.isEmpty(fieldDefault) || !StrUtils.isEmpty(fieldDefault2)) && !fieldDefault2.equals(fieldDefault)) {
            updateSQL.add(String.format("alter table %s alter column %s set default %s", tableName, columnName, StrUtils.isEmpty(fieldDefault2) ? "NULL" : fieldDefault2));
        }
        if (!oleMeta.isCommentEqual2(newMeta)) {
            updateSQL.add(String.format("COMMENT ON COLUMN %s.%s IS '%s'", tableName, columnName, newMeta.getComment()));
        }
    }
}
