package org.jeecg.modules.online.config.template;

import com.alibaba.druid.filter.config.ConfigTools;
import com.baomidou.mybatisplus.annotation.DbType;
import freemarker.template.TemplateException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.common.util.dynamic.db.DbTypeUtils;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.database.CgformConfigModel;
import org.jeecg.modules.online.config.database.DataBaseConfig;
import org.jeecg.modules.online.config.service.DbTableHandleI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: DbTableProcess.java */
/* renamed from: org.jeecg.modules.online.config.d.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/d/c.class */
public class DbTableProcess {

    /* renamed from: b */
    private static final String template_path = "org/jeecg/modules/online/config/engine/tableTemplate.ftl";

    /* renamed from: c */
    private static DbTableHandleI dbTableHandleI;

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(DbTableProcess.class);

    /* renamed from: d */
    private static ServiceRegistry serviceRegistry = null;

    public DbTableProcess(DataBaseConfig dataBaseConfig) throws SQLException, DBException {
        dbTableHandleI = DbTableUtil.getDbHandle(dataBaseConfig);
    }

    public DbTableProcess() throws SQLException, DBException {
        dbTableHandleI = DbTableUtil.getDbHandle(null);
    }

    /**
     * 表不存在的情况下直接更新
     * @param cgformConfigModel
     * @throws IOException
     * @throws TemplateException
     * @throws HibernateException
     * @throws SQLException
     * @throws DBException
     */
    /* renamed from: a */
    public static void handleCreateNewTableByFormModel(CgformConfigModel cgformConfigModel) throws IOException, TemplateException, HibernateException, SQLException, DBException {
        String message;
        DbType m499c = DbTableUtil.getDbTypeByonfig(cgformConfigModel.getDbConfig());
        if (DbTypeUtils.dbTypeIsOracle(m499c)) {
            ArrayList arrayList = new ArrayList();
            for (OnlCgformField onlCgformField : cgformConfigModel.getColumns()) {
                if ("int".equals(onlCgformField.getDbType())) {
                    onlCgformField.setDbType("double");
                    onlCgformField.setDbPointLength(0);
                }
                arrayList.add(onlCgformField);
            }
            cgformConfigModel.setColumns(arrayList);
        }
        String m503a = FreemarkerHelper.m503a(template_path, m471a(cgformConfigModel, m499c));
        HashMap<String,Object> hashMap = new HashMap<>(5);
        DataBaseConfig dbConfig = cgformConfigModel.getDbConfig();
        if (serviceRegistry == null) {
            hashMap.put("hibernate.connection.driver_class", dbConfig.getDriverClassName());
            hashMap.put("hibernate.connection.url", dbConfig.getUrl());
            hashMap.put("hibernate.connection.username", dbConfig.getUsername());
            String password = dbConfig.getPassword();
            if (password != null) {
                if (dbConfig.getDruid() != null && StrUtils.isNotEmpty(dbConfig.getDruid().getPublicKey())) {
                    try {
                        hashMap.put("hibernate.connection.password", ConfigTools.decrypt(dbConfig.getDruid().getPublicKey(), password));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    hashMap.put("hibernate.connection.password", password);
                }
            }
            hashMap.put("hibernate.show_sql", true);
            hashMap.put("hibernate.format_sql", true);
            hashMap.put("hibernate.temp.use_jdbc_metadata_defaults", false);
            hashMap.put("hibernate.dialect", DbTypeUtils.getDbDialect(m499c));
            hashMap.put("hibernate.hbm2ddl.auto", "create");
            hashMap.put("hibernate.connection.autocommit", false);
            hashMap.put("hibernate.current_session_context_class", "thread");
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(hashMap).build();
        }
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(m503a.getBytes("utf-8"));
        metadataSources.addInputStream(byteArrayInputStream);
        Metadata buildMetadata = metadataSources.buildMetadata();
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.create(EnumSet.of(TargetType.DATABASE), buildMetadata);
        byteArrayInputStream.close();
        List<Exception> exceptions = schemaExport.getExceptions();
        for (Exception exc : exceptions) {
            if ("java.sql.SQLSyntaxErrorException".equals(exc.getCause().getClass().getName())) {
                SQLSyntaxErrorException sQLSyntaxErrorException = (SQLSyntaxErrorException) exc.getCause();
                if ("42000".equals(sQLSyntaxErrorException.getSQLState())) {
                    if (1064 == sQLSyntaxErrorException.getErrorCode() || 903 == sQLSyntaxErrorException.getErrorCode()) {
                        logger.error(sQLSyntaxErrorException.getMessage());
                        throw new DBException("请确认表名是否为关键字。");
                    }
                } else {
                    throw new DBException(exc.getMessage());
                }
            } else if ("com.microsoft.sqlserver.jdbc.SQLServerException".equals(exc.getCause().getClass().getName())) {
                if (exc.getCause().toString().indexOf("Incorrect syntax near the keyword") != -1) {
                    exc.printStackTrace();
                    throw new DBException(exc.getCause().getMessage());
                }
                logger.error(exc.getMessage());
            } else {
                if ((DbType.DM.equals(m499c) || DbType.DB2.equals(m499c)) && (message = exc.getMessage()) != null && message.indexOf("Error executing DDL \"drop table") >= 0) {
                    logger.error(message);
                }
                throw new DBException(exc.getMessage());
            }
        }
    }

    /**
     * 正常同步状态下
     * 这里应该是获取修改表字段的DDL;
     * @param cgformConfigModel
     * @return
     * @throws DBException
     * @throws SQLException
     */
    /* renamed from: b */
    public List<String> getUpdateSQL(CgformConfigModel cgformConfigModel) throws DBException, SQLException {
        //数据库类型
        DbType dbType = DbTableUtil.getDbTypeByonfig(cgformConfigModel.getDbConfig());

        String tableName = DbTableUtil.adaptTableNameByDbType(cgformConfigModel.getTableName(), DbTypeUtils.getDbTypeString(dbType));
        String baseUpdateSQL = "alter table  " + tableName + " ";
        List<String> updateSqlList = new ArrayList<>();  //修改语句
        try {
            //查询这个表已经存在数据库中的列表元数据
            Map<String, ColumnMeta> oldColumnMetaMap = getColumnMeta(null, tableName, cgformConfigModel.getDbConfig());
            //根据模型生成出来的列表元数据
            Map<String, ColumnMeta> formColumnMeta = getColumnMeta(cgformConfigModel);

            Map<String, String> colNameChangeRelMap = getColumnNameChangeRelationMap(cgformConfigModel.getColumns());
            for (String fieldName : formColumnMeta.keySet()) {
                if (!"id".equalsIgnoreCase(fieldName)) {
                    //如果不是id这么特殊的字段
                    if (!oldColumnMetaMap.containsKey(fieldName)) {  //旧数据没有这个字段名
                        //获取新的列元数据，进行对比差异
                        ColumnMeta newMeta = formColumnMeta.get(fieldName);
                        //旧的列名
                        String oldFieldName = colNameChangeRelMap.get(fieldName);
                        if (colNameChangeRelMap.containsKey(fieldName) && oldColumnMetaMap.containsKey(oldFieldName)) {
                            // 这个情况说明是改名了，
                            ColumnMeta oldMeta = oldColumnMetaMap.get(oldFieldName);
                            if (DbType.HSQL.equals(dbType)) {
                                //处理hsql的特殊情况
                                handleUpdateMultiSql(oldMeta, newMeta, tableName, updateSqlList);
                            } else {
                                //获取重命名后的fieldName
                                String reNameFieldName = dbTableHandleI.getReNameFieldName(newMeta);
                                if (DbTypeUtils.dbTypeIsSqlServer(dbType)) {
                                    updateSqlList.add(reNameFieldName);
                                } else {
                                    updateSqlList.add(baseUpdateSQL + reNameFieldName);
                                }

                                if (DbType.DB2.equals(dbType)) {
                                    handleUpdateMultiSql(oldMeta, newMeta, tableName, updateSqlList);
                                } else {
                                    if (!oldMeta.equals(newMeta)) {
                                        //如果相等，则拼接
                                        updateSqlList.add(baseUpdateSQL + getUpdateColumnSql(newMeta, oldMeta));
                                        if (DbTypeUtils.dbTypeIsPostgre(dbType)) {
                                            updateSqlList.add(baseUpdateSQL + getSpecialHandle(newMeta, oldMeta));
                                        }
                                    }
                                    if (!DbTypeUtils.dbTypeIsSqlServer(dbType) && !oldMeta.isCommentEqual2(newMeta)) {
                                        // 不是sqlserver，并且注释不相等
                                        updateSqlList.add(getCommentSql(newMeta));
                                    }
                                }
                            }
                            
                            //更新表数据中的old字段名
                            updateSqlList.add(getUpdateOldFieldSQL(fieldName, newMeta.getColumnId()));
                        } else {
                            // 这个情况就说明，删除了原本的字段，又新增了一个字段
                            updateSqlList.add(baseUpdateSQL + getAddColumnSql(newMeta));
                            if (!DbTypeUtils.dbTypeIsSqlServer(dbType) && StringUtils.isNotEmpty(newMeta.getComment())) {
                                updateSqlList.add(getCommentSql(newMeta));
                            }
                        }
                    } else {

                        // 这里就是直接新增字段
                        ColumnMeta oldMeta = oldColumnMetaMap.get(fieldName);
                        ColumnMeta newMeta = formColumnMeta.get(fieldName);
                        if (DbType.DB2.equals(dbType) || DbType.HSQL.equals(dbType)) {
                            handleUpdateMultiSql(oldMeta, newMeta, tableName, updateSqlList);
                        } else {
                            if (!oldMeta.isEqualColumnMeta(newMeta, dbType)) {
                                updateSqlList.add(baseUpdateSQL + getUpdateColumnSql(newMeta, oldMeta));
                            }
                            if (!DbTypeUtils.dbTypeIsSqlServer(dbType) && !DbTypeUtils.dbTypeIsOracle(dbType) && !oldMeta.isCommentEqual2(newMeta)) {
                                updateSqlList.add(getCommentSql(newMeta));
                            }
                        }
                    }
                }
            }
            for (String str4 : oldColumnMetaMap.keySet()) {
                if (!formColumnMeta.containsKey(str4.toLowerCase()) && !colNameChangeRelMap.containsValue(str4.toLowerCase())) {
                    updateSqlList.add(baseUpdateSQL + m475b(str4));
                }
            }
            if (DbType.DB2.equals(dbType)) {
                updateSqlList.add("CALL SYSPROC.ADMIN_CMD('reorg table " + tableName + "')");
            }
            return updateSqlList;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /* renamed from: a */
    private static Map<String, Object> m471a(CgformConfigModel cgformConfigModel, DbType dbType) {
        String dbTypeString = DbTypeUtils.getDbTypeString(dbType);
        HashMap hashMap = new HashMap(5);
        for (OnlCgformField onlCgformField : cgformConfigModel.getColumns()) {
            onlCgformField.setDbDefaultVal(processColumnDefaultValue(onlCgformField.getDbDefaultVal()));
        }
        hashMap.put("entity", cgformConfigModel);
        hashMap.put("dataType", dbTypeString);
        hashMap.put("db", dbType.getDb());
        return hashMap;
    }

    /**
     * @param str
     * @param tableName 表名
     * @param dataBaseConfig 数据库配置
     * @return 返回列的元数据
     * @throws SQLException
     */
    /* renamed from: a */
    private Map<String, ColumnMeta> getColumnMeta(String str, String tableName, DataBaseConfig dataBaseConfig) throws SQLException {
        ResultSet columns;
        HashMap<String,ColumnMeta> hashMap = new HashMap<>(5);
        Connection connection = null;
        try {
            connection = DbTableUtil.getConnection(dataBaseConfig);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        //获取数据库元数据
        DatabaseMetaData metaData = connection.getMetaData();
        String username = dataBaseConfig.getUsername();
        DbType dbType = DbTableUtil.getDbTypeByonfig(dataBaseConfig);
        //用户名转换
        if (DbTypeUtils.dbTypeIsOracle(dbType) || DbType.DB2.equals(dbType)) {
            username = username.toUpperCase();
        }
        //获取列的元数据
        if (DbTypeUtils.dbTypeIsSqlServer(dbType)) {
            columns = metaData.getColumns(connection.getCatalog(), null, tableName, "%");
        } else if (DbTypeUtils.dbTypeIsPostgre(dbType)) {
            columns = metaData.getColumns(connection.getCatalog(), "public", tableName, "%");
        } else if (DbType.HSQL.equals(dbType)) {
            columns = metaData.getColumns(connection.getCatalog(), "PUBLIC", tableName.toUpperCase(), "%");
        } else {
            columns = metaData.getColumns(connection.getCatalog(), username, tableName, "%");
        }
        while (columns.next()) {
            ColumnMeta columnMeta = new ColumnMeta();
            columnMeta.setTableName(tableName);
            String lowerCase = columns.getString("COLUMN_NAME").toLowerCase();
            columnMeta.setColumnName(lowerCase);
            String string = columns.getString("TYPE_NAME");
            int i = columns.getInt("DECIMAL_DIGITS");
            columnMeta.setColumnType(dbTableHandleI.getMatchClassTypeByDataType(string, i));
            columnMeta.setRealDbType(string);
            columnMeta.setColumnSize(columns.getInt("COLUMN_SIZE"));
            columnMeta.setDecimalDigits(i);
            columnMeta.setIsNullable(columns.getInt("NULLABLE") == 1 ? "Y" : "N");
            columnMeta.setComment(columns.getString("REMARKS"));
            String columnDef = columns.getString("COLUMN_DEF");
            columnMeta.setFieldDefault(processColumnDefaultValue(columnDef) == null ? "" : processColumnDefaultValue(columnDef));
            hashMap.put(lowerCase, columnMeta);
        }
        return hashMap;
    }

    /**
     * @param cgformConfigModel 从表单模型中，获取列的元数据
     * @return
     */
    /* renamed from: c */
    private Map<String, ColumnMeta> getColumnMeta(CgformConfigModel cgformConfigModel) {
        HashMap<String, ColumnMeta> hashMap = new HashMap<>(5);
        for (OnlCgformField onlCgformField : cgformConfigModel.getColumns()) {
            ColumnMeta c0100a = new ColumnMeta();
            c0100a.setTableName(cgformConfigModel.getTableName().toLowerCase());
            c0100a.setColumnId(onlCgformField.getId());
            c0100a.setColumnName(onlCgformField.getDbFieldName().toLowerCase());
            c0100a.setColumnSize(onlCgformField.getDbLength().intValue());
            c0100a.setColumnType(onlCgformField.getDbType().toLowerCase());
            c0100a.setIsNullable(onlCgformField.getDbIsNull().intValue() == 1 ? "Y" : "N");
            c0100a.setComment(onlCgformField.getDbFieldTxt());
            c0100a.setDecimalDigits(onlCgformField.getDbPointLength().intValue());
            c0100a.setFieldDefault(processColumnDefaultValue(onlCgformField.getDbDefaultVal()));
            c0100a.setPkType(cgformConfigModel.getJformPkType() == null ? "UUID" : cgformConfigModel.getJformPkType());
            c0100a.setOldColumnName(onlCgformField.getDbFieldNameOld() != null ? onlCgformField.getDbFieldNameOld().toLowerCase() : null);
            hashMap.put(onlCgformField.getDbFieldName().toLowerCase(), c0100a);
        }
        return hashMap;
    }

    /**
     * @param list 从表单模型中获取的列名的变更记录
     * @return 返回列名的变更记录 key 新列名 value 旧列名
     */
    /* renamed from: a */
    private Map<String, String> getColumnNameChangeRelationMap(List<OnlCgformField> list) {
        HashMap<String,String> hashMap = new HashMap<>(5);
        for (OnlCgformField onlCgformField : list) {
            hashMap.put(onlCgformField.getDbFieldName(), onlCgformField.getDbFieldNameOld());
        }
        return hashMap;
    }

    /* renamed from: b */
    private String m475b(String str) {
        return dbTableHandleI.getDropColumnSql(str);
    }

    /* renamed from: a */
    private String getUpdateColumnSql(ColumnMeta newMeta, ColumnMeta oldMeta) throws DBException {
        return dbTableHandleI.getUpdateColumnSql(newMeta, oldMeta);
    }

    /* renamed from: b */
    private String getSpecialHandle(ColumnMeta newMeta, ColumnMeta oldMeta) {
        return dbTableHandleI.getSpecialHandle(newMeta, oldMeta);
    }

    /**
     * 处理update Sql
     * @param oldMeta
     * @param newMeta
     * @param tableName
     * @param list
     */
    /* renamed from: a */
    private void handleUpdateMultiSql(ColumnMeta oldMeta, ColumnMeta newMeta, String tableName, List<String> list) {
        dbTableHandleI.handleUpdateMultiSql(oldMeta, newMeta, tableName, list);
    }

    /* renamed from: a */
    private String m479a(ColumnMeta c0100a) {
        return dbTableHandleI.getReNameFieldName(c0100a);
    }

    /* renamed from: b */
    private String getAddColumnSql(ColumnMeta meta) {
        return dbTableHandleI.getAddColumnSql(meta);
    }

    /* renamed from: c */
    private String getCommentSql(ColumnMeta meta) {
        return dbTableHandleI.getCommentSql(meta);
    }

    /**
     * 更新在线表单的旧字段名的数据
     * @param oldFieldName
     * @param id
     * @return
     */
    /* renamed from: c */
    private String getUpdateOldFieldSQL(String oldFieldName, String id) {
        return "update onl_cgform_field set DB_FIELD_NAME_OLD = '" + oldFieldName + "' where ID ='" + id + "'";
    }

    /* renamed from: a */
    private int m483a(String str, String str2, Session session) {
        return session.createSQLQuery("update onl_cgform_field set DB_FIELD_NAME_OLD= '" + str + "' where ID ='" + str2 + "'").executeUpdate();
    }

    /**
     * 处理字段默认值
     * @param columnDef
     * @return
     */
    /* renamed from: c */
    private static String processColumnDefaultValue(String columnDef) {
        if (StringUtils.isNotEmpty(columnDef)) {
            try {
                Double.valueOf(columnDef);
            } catch (Exception e) {
                if (!columnDef.startsWith(CgformUtil.SINGLE_QUOTE) || !columnDef.endsWith(CgformUtil.SINGLE_QUOTE)) {
                    columnDef = "'" + columnDef + "'";
                }
            }
        }
        return columnDef;
    }

    /* renamed from: a */
    public String m485a(String str, String str2) {
        return dbTableHandleI.dropIndexs(SqlInjectionUtil.getSqlInjectField(str), SqlInjectionUtil.getSqlInjectTableName(str2));
    }

    /* renamed from: b */
    public String m486b(String str, String str2) {
        return dbTableHandleI.countIndex(SqlInjectionUtil.getSqlInjectField(str), SqlInjectionUtil.getSqlInjectTableName(str2));
    }

    /* renamed from: a */
    public static List<String> m487a(String str) throws SQLException {
        Connection connection = null;
        ArrayList arrayList = new ArrayList();
        try {
            try {
                connection = DbTableUtil.getConnection();
                ResultSet indexInfo = connection.getMetaData().getIndexInfo(null, null, str, false, false);
                indexInfo.getMetaData();
                while (indexInfo.next()) {
                    String string = indexInfo.getString("INDEX_NAME");
                    if (StrUtils.isEmpty(string)) {
                        string = indexInfo.getString("index_name");
                    }
                    if (StrUtils.isNotEmpty(string)) {
                        arrayList.add(string);
                    }
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                if (connection != null) {
                    connection.close();
                }
            }
            return arrayList;
        } catch (Throwable th) {
            if (connection != null) {
                connection.close();
            }
            throw th;
        }
    }
}
