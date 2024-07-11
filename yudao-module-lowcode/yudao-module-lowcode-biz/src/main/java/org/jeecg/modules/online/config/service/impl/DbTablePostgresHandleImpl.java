package org.jeecg.modules.online.config.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.template.ColumnMeta;
import org.jeecg.modules.online.config.template.DataBaseConst;
import org.jeecg.modules.online.config.service.DbTableHandleI;

/* compiled from: DbTablePostgresHandleImpl.java */
/* renamed from: org.jeecg.modules.online.config.service.a.f */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/service/a/f.class */
public class DbTablePostgresHandleImpl implements DbTableHandleI {
    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getAddColumnSql(ColumnMeta columnMeta) {
        return " ADD COLUMN " + m521a(columnMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getReNameFieldName(ColumnMeta columnMeta) {
        return " RENAME  COLUMN  " + columnMeta.getOldColumnName() + " to " + columnMeta.getColumnName() + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) throws DBException {
        return m519c(cgformcolumnMeta, datacolumnMeta);
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getSpecialHandle(ColumnMeta newMeta, ColumnMeta oldMeta) {
        return "  ALTER  COLUMN   " + m520d(newMeta, oldMeta) + ";";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str = "";
        if (DataBaseConst.VARCHAR.equalsIgnoreCase(dataType)) {
            str = DataBaseConst.STRING;
        } else if ("double".equalsIgnoreCase(dataType)) {
            str = "double";
        } else if (dataType.contains("int")) {
            str = "int";
        } else if ("Date".equalsIgnoreCase(dataType)) {
            str = OnlFormShowType.DATE;
        } else if ("timestamp".equalsIgnoreCase(dataType)) {
            str = "datetime";
        } else if ("bytea".equalsIgnoreCase(dataType)) {
            str = "blob";
        } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(dataType)) {
            str = ExtendJsonKey.TEXT;
        } else if (DataBaseConst.DECIMAL.equalsIgnoreCase(dataType)) {
            str = "bigdecimal";
        } else if ("numeric".equalsIgnoreCase(dataType)) {
            str = "bigdecimal";
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropTableSQL(String tableName) {
        return " DROP TABLE  " + tableName + " ;";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    /* renamed from: a */
    private boolean m516a(String str, String str2) {
        return "int,double,bigdecimal".contains(str == null ? "" : str.toLowerCase()) && "int,double,bigdecimal".contains(str2 == null ? "" : str2.toLowerCase());
    }

    /* renamed from: a */
    private String m517a(ColumnMeta c0100a, ColumnMeta c0100a2) {
        return getDropColumnSql(c0100a2.getColumnName()) + (String.format("alter table %s", c0100a.getTableName()) + getAddColumnSql(c0100a));
    }

    /* renamed from: b */
    private String m518b(ColumnMeta c0100a, ColumnMeta c0100a2) {
        String isNullable = c0100a.getIsNullable();
        String isNullable2 = c0100a2.getIsNullable();
        String str = isNullable == null ? "Y" : isNullable;
        if (!str.equals(isNullable2 == null ? "Y" : isNullable2)) {
            String tableName = c0100a.getTableName();
            String columnName = c0100a.getColumnName();
            if ("Y".equals(str)) {
                return String.format("ALTER table %s ALTER COLUMN %s %s not null;", tableName, columnName, "drop");
            }
            if ("N".equals(str)) {
                return String.format("ALTER table %s ALTER COLUMN %s %s not null;", tableName, columnName, "set");
            }
            return "";
        }
        return "";
    }

    /* renamed from: c */
    private String m519c(ColumnMeta c0100a, ColumnMeta c0100a2) throws DBException {
        String str = "  ALTER  COLUMN   ";
        if (DataBaseConst.STRING.equalsIgnoreCase(c0100a.getColumnType())) {
            str = str + c0100a.getColumnName() + "  type character varying(" + c0100a.getColumnSize() + ") ";
        } else if (OnlFormShowType.DATE.equalsIgnoreCase(c0100a.getColumnType())) {
            if (c0100a2.getColumnType().toLowerCase().contains(OnlFormShowType.DATE)) {
                str = str + c0100a.getColumnName() + "  type date ";
            } else {
                str = m517a(c0100a, c0100a2);
            }
        } else if ("datetime".equalsIgnoreCase(c0100a.getColumnType())) {
            if (c0100a2.getColumnType().toLowerCase().contains(OnlFormShowType.DATE)) {
                str = str + c0100a.getColumnName() + "  type timestamp ";
            } else {
                str = m517a(c0100a, c0100a2);
            }
        } else if ("int".equalsIgnoreCase(c0100a.getColumnType())) {
            if (m516a(c0100a.getColumnType(), c0100a2.getColumnType())) {
                str = str + c0100a.getColumnName() + " type int4";
            } else {
                str = m517a(c0100a, c0100a2);
            }
        } else if ("double".equalsIgnoreCase(c0100a.getColumnType())) {
            if (m516a(c0100a.getColumnType(), c0100a2.getColumnType())) {
                str = str + c0100a.getColumnName() + " type  numeric(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") ";
            } else {
                str = m517a(c0100a, c0100a2);
            }
        } else if ("BigDecimal".equalsIgnoreCase(c0100a.getColumnType())) {
            if (m516a(c0100a.getColumnType(), c0100a2.getColumnType())) {
                str = str + c0100a.getColumnName() + " type  decimal(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") ";
            } else {
                str = m517a(c0100a, c0100a2);
            }
        } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(c0100a.getColumnType())) {
            str = str + c0100a.getColumnName() + " text ";
        } else if ("blob".equalsIgnoreCase(c0100a.getColumnType())) {
            throw new DBException("blob类型不可修改");
        }
        if (!str.endsWith(";")) {
            str = str + ";";
        }
        return str + m518b(c0100a, c0100a2);
    }

    /* renamed from: d */
    private String m520d(ColumnMeta newMeta, ColumnMeta oldMeta) {
        String str = "";
        if (!newMeta.isCommentEqual(oldMeta)) {
            if (DataBaseConst.STRING.equalsIgnoreCase(newMeta.getColumnType())) {
                str = newMeta.getColumnName() + (StringUtils.isNotEmpty(newMeta.getFieldDefault()) ? " SET DEFAULT " + newMeta.getFieldDefault() : " DROP DEFAULT");
            } else if (OnlFormShowType.DATE.equalsIgnoreCase(newMeta.getColumnType()) || "datetime".equalsIgnoreCase(newMeta.getColumnType())) {
                str = newMeta.getColumnName() + (StringUtils.isNotEmpty(newMeta.getFieldDefault()) ? " SET DEFAULT " + newMeta.getFieldDefault() : " DROP DEFAULT");
            } else if ("int".equalsIgnoreCase(newMeta.getColumnType())) {
                str = newMeta.getColumnName() + (StringUtils.isNotEmpty(newMeta.getFieldDefault()) ? " SET DEFAULT " + newMeta.getFieldDefault() : " DROP DEFAULT");
            } else if ("double".equalsIgnoreCase(newMeta.getColumnType())) {
                str = newMeta.getColumnName() + (StringUtils.isNotEmpty(newMeta.getFieldDefault()) ? " SET DEFAULT " + newMeta.getFieldDefault() : " DROP DEFAULT");
            } else if ("bigdecimal".equalsIgnoreCase(newMeta.getColumnType())) {
                str = newMeta.getColumnName() + (StringUtils.isNotEmpty(newMeta.getFieldDefault()) ? " SET DEFAULT " + newMeta.getFieldDefault() : " DROP DEFAULT");
            } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(newMeta.getColumnType())) {
                str = newMeta.getColumnName() + (StringUtils.isNotEmpty(newMeta.getFieldDefault()) ? " SET DEFAULT " + newMeta.getFieldDefault() : " DROP DEFAULT");
            }
        }
        return str;
    }

    /* renamed from: a */
    private String m521a(ColumnMeta c0100a) {
        String str = "";
        if (DataBaseConst.STRING.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " character varying(" + c0100a.getColumnSize() + ") ";
        } else if (OnlFormShowType.DATE.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " date ";
        } else if ("datetime".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " timestamp ";
        } else if ("int".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " int4";
        } else if ("double".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " numeric(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") ";
        } else if ("bigdecimal".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " decimal(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") ";
        } else if ("blob".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " bytea ";
        } else if (ExtendJsonKey.TEXT.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " text ";
        }
        String str2 = str + (StringUtils.isNotEmpty(c0100a.getFieldDefault()) ? " DEFAULT " + c0100a.getFieldDefault() : " ");
        if ("N".equals(c0100a.getIsNullable())) {
            str2 = str2 + " NOT NULL ";
        }
        return str2;
    }

    /* renamed from: b */
    private String m522b(ColumnMeta c0100a) {
        String str = "";
        if (DataBaseConst.STRING.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " character varying(" + c0100a.getColumnSize() + ") ";
        } else if (OnlFormShowType.DATE.equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " date ";
        } else if ("datetime".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " timestamp ";
        } else if ("int".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " int(" + c0100a.getColumnSize() + ") ";
        } else if ("double".equalsIgnoreCase(c0100a.getColumnType())) {
            str = c0100a.getColumnName() + " numeric(" + c0100a.getColumnSize() + "," + c0100a.getDecimalDigits() + ") ";
        }
        return str;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String getCommentSql(ColumnMeta columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + "'";
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName;
    }

    @Override // org.jeecg.modules.online.config.service.DbTableHandleI
    public String countIndex(String indexName, String tableName) {
        return "SELECT count(*) FROM pg_indexes WHERE indexname = '" + indexName + "' and tablename = '" + tableName + "'";
    }
}
