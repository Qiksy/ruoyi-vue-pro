package org.jeecg.modules.online.config.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;
import org.jeecg.modules.online.config.template.ColumnMeta;
import org.jeecg.modules.online.config.template.DataBaseConst;
import org.jeecg.modules.online.config.service.DbTableHandleI;

/* compiled from: DbTableMysqlHandleImpl.java */
/* renamed from: org.jeecg.modules.online.config.service.a.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/service/a/d.class */
public class DbTableMysqlHandleImpl implements DbTableHandleI {
    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getAddColumnSql(ColumnMeta columnMeta) {
        return " ADD COLUMN " + m512a(columnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getReNameFieldName(ColumnMeta columnMeta) {
        return "CHANGE COLUMN " + columnMeta.getOldColumnName() + " " + m513b(columnMeta) + " ;";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return " MODIFY COLUMN " + m511b(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str = "";
        if (DataBaseConst.VARCHAR.equalsIgnoreCase(dataType)) {
            str = DataBaseConst.STRING;
        } else if ("double".equalsIgnoreCase(dataType)) {
            str = "double";
        } else if ("int".equalsIgnoreCase(dataType)) {
            str = "int";
        } else if ("Date".equalsIgnoreCase(dataType)) {
            str = OnlFormShowType.DATE;
        } else if ("Datetime".equalsIgnoreCase(dataType)) {
            str = "datetime";
        } else if (DataBaseConst.DECIMAL.equalsIgnoreCase(dataType)) {
            str = "bigdecimal";
        } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(dataType)) {
            str = ExtendJsonKey.TEXT;
        } else if ("blob".equalsIgnoreCase(dataType)) {
            str = "blob";
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropTableSQL(String tableName) {
        return " DROP TABLE IF EXISTS " + tableName + " ;";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    /* renamed from: a */
    private String m510a(ColumnMeta c0100a, ColumnMeta c0100a2) {
        String str = "";
        if (DataBaseConst.STRING.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " varchar(" + c0100a.getColumnSize() + ") " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (OnlFormShowType.DATE.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " date " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("datetime".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " datetime " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("int".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " int(" + c0100a.getColumnSize() + ") " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("double".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " double(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("bigdecimal".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " decimal(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " text " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("blob".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " blob " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        }
        String str2 = (str + (StringUtils.isNotEmpty(c0100a.getComment()) ? " COMMENT '" + c0100a.getComment() + "'" : " ")) + (StringUtils.isNotEmpty(c0100a.getFieldDefault()) ? " DEFAULT " + c0100a.getFieldDefault() : " ");
        String pkType = c0100a.getPkType();
        if ("id".equalsIgnoreCase(c0100a.getColumnName()) && pkType != null && ("SEQUENCE".equalsIgnoreCase(pkType) || "NATIVE".equalsIgnoreCase(pkType))) {
            str2 = str2 + " AUTO_INCREMENT ";
        }
        return str2;
    }

    /* renamed from: b */
    private String m511b(ColumnMeta c0100a, ColumnMeta c0100a2) {
        return m510a(c0100a, c0100a2);
    }

    /* renamed from: a */
    private String m512a(ColumnMeta c0100a) {
        return m510a(c0100a, null);
    }

    /* renamed from: b */
    private String m513b(ColumnMeta c0100a) {
        return m510a(c0100a, null);
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getCommentSql(ColumnMeta columnMeta) {
        return "";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getSpecialHandle(ColumnMeta newMeta, ColumnMeta oldMeta) {
        return null;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName + " ON " + tableName;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String countIndex(String indexName, String tableName) {
        return "select COUNT(*) from information_schema.statistics where table_name = '" + tableName + "'  AND index_name = '" + indexName + "'";
    }
}
