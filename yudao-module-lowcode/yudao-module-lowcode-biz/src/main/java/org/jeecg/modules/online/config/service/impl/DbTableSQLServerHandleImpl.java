package org.jeecg.modules.online.config.service.impl;


import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;
import org.jeecg.modules.online.config.template.ColumnMeta;
import org.jeecg.modules.online.config.template.DataBaseConst;
import org.jeecg.modules.online.config.service.DbTableHandleI;

/* compiled from: DbTableSQLServerHandleImpl.java */
/* renamed from: org.jeecg.modules.online.config.service.a.g */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/service/a/g.class */
public class DbTableSQLServerHandleImpl implements DbTableHandleI {
    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getAddColumnSql(ColumnMeta columnMeta) {
        return " ADD  " + m524a(columnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getReNameFieldName(ColumnMeta columnMeta) {
        return "  sp_rename '" + columnMeta.getTableName() + "." + columnMeta.getOldColumnName() + "', '" + columnMeta.getColumnName() + "', 'COLUMN';";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return " ALTER COLUMN  " + m523a(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str = "";
        if (DataBaseConst.VARCHAR.equalsIgnoreCase(dataType) || "nvarchar".equalsIgnoreCase(dataType)) {
            str = DataBaseConst.STRING;
        } else if ("float".equalsIgnoreCase(dataType)) {
            str = "double";
        } else if ("int".equalsIgnoreCase(dataType)) {
            str = "int";
        } else if ("Datetime".equalsIgnoreCase(dataType)) {
            str = "datetime";
        } else if ("numeric".equalsIgnoreCase(dataType)) {
            str = "bigdecimal";
        } else if ("varbinary".equalsIgnoreCase(dataType) || "image".equalsIgnoreCase(dataType)) {
            str = "blob";
        } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(dataType) || "ntext".equalsIgnoreCase(dataType)) {
            str = ExtendJsonKey.TEXT;
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropTableSQL(String tableName) {
        return " DROP TABLE " + tableName + " ;";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    /* renamed from: a */
    private String m523a(ColumnMeta c0100a, ColumnMeta c0100a2) {
        String str = "";
        if (DataBaseConst.STRING.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " nvarchar(" + c0100a.getColumnSize() + ") " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (OnlFormShowType.DATE.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " datetime " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("datetime".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " datetime " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("int".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " int " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("double".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " float " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("bigdecimal".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " numeric(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " ntext " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("blob".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " image";
        }
        return str;
    }

    /* renamed from: a */
    private String m524a(ColumnMeta c0100a) {
        String str = "";
        if (DataBaseConst.STRING.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " nvarchar(" + c0100a.getColumnSize() + ") " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (OnlFormShowType.DATE.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " datetime " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("datetime".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " datetime " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("int".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " int " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("double".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " float " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("bigdecimal".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " numeric(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " ntext " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("blob".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " image";
        }
        return str;
    }

    /* renamed from: b */
    private String m525b(ColumnMeta c0100a) {
        String str = "";
        if (DataBaseConst.STRING.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " nvarchar(" + c0100a.getColumnSize() + ") " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (OnlFormShowType.DATE.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " datetime " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("datetime".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " datetime " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("int".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " int " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if ("double".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " float " + ("Y".equals(c0100a.getIsNullable()) ? "NULL" : "NOT NULL");
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getCommentSql(ColumnMeta columnMeta) {
        StringBuilder stringBuffer = new StringBuilder("EXECUTE ");
        if (ConvertUtils.isEmpty(columnMeta.getOldColumnName())) {
            stringBuffer.append("sp_addextendedproperty");
        } else {
            stringBuffer.append("sp_updateextendedproperty");
        }
        stringBuffer.append(" N'MS_Description', '");
        stringBuffer.append(columnMeta.getComment());
        stringBuffer.append("', N'SCHEMA', N'dbo', N'TABLE', N'");
        stringBuffer.append(columnMeta.getTableName());
        stringBuffer.append("', N'COLUMN', N'");
        stringBuffer.append(columnMeta.getColumnName() + "'");
        return stringBuffer.toString();
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
        return "SELECT count(*) FROM sys.indexes WHERE object_id=OBJECT_ID('" + tableName + "') and NAME= '" + indexName + "'";
    }
}
