package org.jeecg.modules.online.config.service;

import java.util.List;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.template.ColumnMeta;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/service/DbTableHandleI.class */
public interface DbTableHandleI {
    String getAddColumnSql(ColumnMeta c0100a);

    String getReNameFieldName(ColumnMeta c0100a);

    String getUpdateColumnSql(ColumnMeta c0100a, ColumnMeta c0100a2) throws DBException;

    String getMatchClassTypeByDataType(String str, int i);

    String dropTableSQL(String str);

    String getDropColumnSql(String str);

    String getCommentSql(ColumnMeta c0100a);

    String getSpecialHandle(ColumnMeta newMeta, ColumnMeta oldMeta);

    String dropIndexs(String str, String str2);

    String countIndex(String str, String str2);

    /**
     * @param oleMeta 旧的元数据
     * @param newMeta 新的元数据
     * @param tableName 表名
     * @param updateSQL 更新语句
     */
    default void handleUpdateMultiSql(ColumnMeta oleMeta, ColumnMeta newMeta, String tableName, List<String> updateSQL) {
    }
}
