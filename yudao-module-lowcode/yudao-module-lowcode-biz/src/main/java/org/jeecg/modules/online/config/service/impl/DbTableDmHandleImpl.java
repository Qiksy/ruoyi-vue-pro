package org.jeecg.modules.online.config.service.impl;


import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;
import org.jeecg.modules.online.config.template.ColumnMeta;
import org.jeecg.modules.online.config.template.DataBaseConst;
import org.jeecg.modules.online.config.service.DbTableHandleI;

/* compiled from: DbTableDmHandleImpl.java */
/* renamed from: org.jeecg.modules.online.config.service.a.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/service/a/b.class */
public class DbTableDmHandleImpl implements DbTableHandleI {
    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getAddColumnSql(ColumnMeta columnMeta) {
        return " ADD COLUMN " + m506a(columnMeta);
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getReNameFieldName(ColumnMeta columnMeta) {
        return "RENAME COLUMN " + columnMeta.getOldColumnName() + " TO " + columnMeta.getColumnName();
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return " MODIFY " + m507a(cgformcolumnMeta, datacolumnMeta);
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str = "";
        if ("varchar2".equalsIgnoreCase(dataType)) {
            str = DataBaseConst.STRING;
        } else if (DataBaseConst.VARCHAR.equalsIgnoreCase(dataType)) {
            str = DataBaseConst.STRING;
        } else if ("nvarchar2".equalsIgnoreCase(dataType)) {
            str = DataBaseConst.STRING;
        } else if ("double".equalsIgnoreCase(dataType)) {
            str = "double";
        } else if (DataBaseConst.NUMBER.equalsIgnoreCase(dataType) && digits == 0) {
            str = "int";
        } else if (DataBaseConst.NUMBER.equalsIgnoreCase(dataType) && digits != 0) {
            str = "double";
        } else if ("int".equalsIgnoreCase(dataType)) {
            str = "int";
        } else if ("Date".equalsIgnoreCase(dataType)) {
            str = OnlFormShowType.DATE;
        } else if ("timestamp".equalsIgnoreCase(dataType)) {
            str = "datetime";
        } else if ("datetime".equalsIgnoreCase(dataType)) {
            str = "datetime";
        } else if ("blob".equalsIgnoreCase(dataType)) {
            str = "blob";
        } else if ("clob".equalsIgnoreCase(dataType)) {
            str = ExtendJsonKey.TEXT;
        }
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
    private String m506a(ColumnMeta c0100a) {
        String str = "(\"" + c0100a.getColumnName() + "\"";
        if (DataBaseConst.STRING.equalsIgnoreCase(c0100a.getColumnType())) {
            str = str + " varchar2(" + c0100a.getColumnSize() + ")";
        } else if (OnlFormShowType.DATE.equalsIgnoreCase(c0100a.getColumnType())) {
            str = str + " date";
        } else if ("datetime".equalsIgnoreCase(c0100a.getColumnType())) {
            str = str + " datetime";
        } else if ("int".equalsIgnoreCase(c0100a.getColumnType())) {
            str = str + " INT";
        } else if ("double".equalsIgnoreCase(c0100a.getColumnType())) {
            str = str + " NUMBER(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ")";
        } else if ("bigdecimal".equalsIgnoreCase(c0100a.getColumnType())) {
            str = str + " DECIMAL(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ")";
        } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(c0100a.getColumnType())) {
            str = str + " CLOB ";
        } else if ("blob".equalsIgnoreCase(c0100a.getColumnType())) {
            str = str + " BLOB ";
        }
        return ((str + (ConvertUtils.isNotEmpty(c0100a.getFieldDefault()) ? " DEFAULT " + c0100a.getFieldDefault() : " ")) + ("Y".equals(c0100a.getIsNullable()) ? " NULL" : " NOT NULL")) + ")";
    }

    /* renamed from: a */
    private String m507a(ColumnMeta c0100a, ColumnMeta c0100a2) {
        String str = "";
        String str2 = "";
        if (!c0100a2.getIsNullable().equals(c0100a.getIsNullable())) {
            str2 = "Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL";
        }
        if (DataBaseConst.STRING.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " varchar2(" + c0100a.getColumnSize() + ")";
        } else if (OnlFormShowType.DATE.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " date ";
        } else if ("datetime".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " datetime ";
        } else if ("int".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " INT ";
        } else if ("double".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " NUMBER(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") ";
        } else if ("bigdecimal".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " DECIMAL(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") ";
        } else if ("blob".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " BLOB ";
        } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " CLOB ";
        }
        return (str + (ConvertUtils.isNotEmpty(c0100a.getFieldDefault()) ? " DEFAULT " + c0100a.getFieldDefault() : " ")) + str2;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getCommentSql(ColumnMeta columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + "'";
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
}
