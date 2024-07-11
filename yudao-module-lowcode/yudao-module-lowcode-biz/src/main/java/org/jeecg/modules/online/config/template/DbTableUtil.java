package org.jeecg.modules.online.config.template;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.dynamic.db.DbTypeUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.database.DataBaseConfig;
import org.jeecg.modules.online.config.service.DbTableHandleI;
import org.jeecg.modules.online.config.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/* compiled from: DbTableUtil.java */
/* renamed from: org.jeecg.modules.online.config.d.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/d/d.class */
public class DbTableUtil {

    /* renamed from: b */
    private static final Logger f554b = LoggerFactory.getLogger(DbTableUtil.class);

    /* renamed from: a */
    public static String f555a = "";

    public static DbTableHandleI getTableHandle() throws SQLException, DBException {
        return getDbHandle((DataBaseConfig) null);
    }

    /**
     * 获取数据库处理器
     * @param dataBaseConfig
     * @return
     * @throws SQLException
     * @throws DBException
     */
    /* renamed from: a */
    public static DbTableHandleI getDbHandle(DataBaseConfig dataBaseConfig) throws SQLException, DBException {
        DbTableHandleI c0111d;
        DbType m499c = getDbTypeByonfig(dataBaseConfig);
        String dbTypeString = DbTypeUtils.getDbTypeString(m499c);
        if (DbType.DM.equals(m499c)) {
            return new DbTableDmHandleImpl();
        }


        switch (dbTypeString) {
            case "MYSQL":
                c0111d = new DbTableMysqlHandleImpl();
                break;
            case "MARIADB":
                c0111d = new DbTableMysqlHandleImpl();
                break;
            case "ORACLE":
                c0111d = new DbTableOracleHandleImpl();
                break;
            case "DM":
                c0111d = new DbTableDmHandleImpl();
                break;
            case "SQLSERVER":
                c0111d = new DbTableSQLServerHandleImpl();
                break;
            case "POSTGRESQL":
                c0111d = new DbTablePostgresHandleImpl();
                break;
            case "DB2":
                c0111d = new DbTableDB2HandleImpl();
                break;
            case "HSQL":
                c0111d = new DbTableHyperSQLHandleImpl();
                break;
            default:
                c0111d = new DbTableMysqlHandleImpl();
        }

        return c0111d;
    }

    public static Connection getConnection() throws SQLException {
        return ((DataSource) SpringContextUtils.getApplicationContext().getBean(DataSource.class)).getConnection();
    }

    public static String getDatabaseType() throws SQLException, DBException {
        if (oConvertUtils.isNotEmpty(f555a)) {
            return f555a;
        }
        return m490a((DataSource) SpringContextUtils.getApplicationContext().getBean(DataSource.class));
    }

    /* renamed from: a */
    public static boolean m489a() {
        try {
            return "ORACLE".equals(getDatabaseType());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (DBException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* renamed from: a */
    public static String m490a(DataSource dataSource) throws SQLException, DBException {
        if ("".equals(f555a)) {
            Connection connection = dataSource.getConnection();
            try {
                try {
                    String lowerCase = connection.getMetaData().getDatabaseProductName().toLowerCase();
                    if (lowerCase.indexOf("mysql") >= 0) {
                        f555a = "MYSQL";
                    } else if (lowerCase.indexOf("oracle") >= 0) {
                        f555a = "ORACLE";
                    } else if (lowerCase.indexOf("dm") >= 0) {
                        f555a = "DM";
                    } else if (lowerCase.indexOf("sqlserver") >= 0 || lowerCase.indexOf("sql server") >= 0) {
                        f555a = "SQLSERVER";
                    } else if (lowerCase.indexOf("postgresql") >= 0 || lowerCase.indexOf("kingbasees") >= 0) {
                        f555a = "POSTGRESQL";
                    } else if (lowerCase.indexOf("mariadb") >= 0) {
                        f555a = "MARIADB";
                    } else {
                        f554b.error("数据库类型:[" + lowerCase + "]不识别!");
                    }
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (Exception e) {
                    f554b.error(e.getMessage(), e);
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                }
            } catch (Throwable th) {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                throw th;
            }
        }
        return f555a;
    }

    /* renamed from: a */
    public static String m491a(Connection connection) throws SQLException, DBException {
        if ("".equals(f555a)) {
            String lowerCase = connection.getMetaData().getDatabaseProductName().toLowerCase();
            if (lowerCase.indexOf("mysql") >= 0) {
                f555a = "MYSQL";
            } else if (lowerCase.indexOf("oracle") >= 0) {
                f555a = "ORACLE";
            } else if (lowerCase.indexOf("sqlserver") >= 0 || lowerCase.indexOf("sql server") >= 0) {
                f555a = "SQLSERVER";
            } else if (lowerCase.indexOf("postgresql") >= 0) {
                f555a = "POSTGRESQL";
            } else if (lowerCase.indexOf("mariadb") >= 0) {
                f555a = "MARIADB";
            } else {
                f554b.error("数据库类型:[" + lowerCase + "]不识别!");
            }
        }
        return f555a;
    }

    /**
     * 根据数据库类型适配表名
     * @param tableName
     * @param dbType
     * @return
     */
    /* renamed from: a */
    public static String adaptTableNameByDbType(String tableName, String dbType) {
        switch (dbType) {
            case "ORACLE":
            case "DB2":
                return tableName.toUpperCase();
            case "POSTGRESQL":
                return tableName.toLowerCase();
            default:
                return tableName;
        }
    }

    /**
     * 校验表是否存在数据库当中
     * @param tableName 表名
     * @return 是否存在
     */
    /* renamed from: a */
    public static Boolean isTableExistsInDatabase(String tableName) {
        try {
            return isTableExistsInDatabase(tableName, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param tableName 表名
     * @param dataBaseConfig 数据库配置
     * @return  是否存在
     * @throws SQLException SQL异常
     */
    /* renamed from: a */
    public static Boolean isTableExistsInDatabase(String tableName, DataBaseConfig dataBaseConfig) throws SQLException {
        Connection connection1 = null;
        String username;
        ResultSet tables;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            try {
                String[] strArr = {"TABLE"};
                if (dataBaseConfig == null) {
                    connection1 = getConnection();
                } else {
                    connection1 = getConnection(dataBaseConfig);
                }
                DatabaseMetaData metaData = connection1.getMetaData();
                DbType m499c = getDbTypeByonfig(dataBaseConfig);
                String m492a = adaptTableNameByDbType(tableName, DbTypeUtils.getDbTypeString(m499c));
                if (dataBaseConfig != null) {
                    username = dataBaseConfig.getUsername();
                } else {
                    username = ((DataBaseConfig) SpringContextUtils.getBean(DataBaseConfig.class)).getUsername();
                }
                if (DbTypeUtils.dbTypeIsOracle(m499c) || DbType.DB2.equals(m499c)) {
                    username = username != null ? username.toUpperCase() : null;
                }
                if (DbTypeUtils.dbTypeIsSqlServer(m499c)) {
                    tables = metaData.getTables(connection1.getCatalog(), null, m492a, strArr);
                } else if (DbTypeUtils.dbTypeIsPostgre(m499c)) {
                    tables = metaData.getTables(connection1.getCatalog(), "public", m492a, strArr);
                } else if (DbType.HSQL.equals(m499c)) {
                    tables = metaData.getTables(connection1.getCatalog(), "PUBLIC", m492a.toUpperCase(), strArr);
                } else {
                    tables = metaData.getTables(connection1.getCatalog(), username, m492a, strArr);
                }
                if (tables.next()) {
                    if (tables != null) {
                        try {
                            tables.close();
                        } catch (SQLException e) {
                            f554b.error(e.getMessage(), e);
                        }
                    }
                    if (connection1 != null) {
                        connection1.close();
                    }
                    return true;
                }
                if (tables != null) {
                    try {
                        tables.close();
                    } catch (SQLException e2) {
                        f554b.error(e2.getMessage(), e2);
                    }
                }
                if (connection1 != null) {
                    connection1.close();
                }
                return false;
            } catch (SQLException e3) {
                throw new RuntimeException();
            }
        } catch (Throwable th) {
            if (resultSet!=null) {
                try {
                    resultSet.close();
                } catch (SQLException e4) {
                    f554b.error(e4.getMessage(), e4);
                    throw th;
                }
            }
            if (connection1!=null) {
                connection.close();
            }
            throw th;
        }
    }

    /* renamed from: a */
    public static Map<String, Object> m495a(List<Map<String, Object>> list) {
        HashMap hashMap = new HashMap(5);
        for (int i = 0; i < list.size(); i++) {
            hashMap.put(list.get(i).get("column_name").toString(), list.get(i));
        }
        return hashMap;
    }

    public static String getDialect() throws SQLException, DBException {
        return m496b(getDatabaseType());
    }

    /* renamed from: b */
    public static String m496b(String str) throws SQLException, DBException {
        String str2 = "org.hibernate.dialect.MySQL5InnoDBDialect";
        switch (str) {
            case "SQLSERVER":
                str2 = "org.hibernate.dialect.SQLServerDialect";
                break;
            case "POSTGRESQL":
            case "KINGBASEES":
                str2 = "org.hibernate.dialect.PostgreSQLDialect";
                break;
            case "ORACLE":
                str2 = "org.hibernate.dialect.OracleDialect";
                break;
            case "DM":
                str2 = "org.hibernate.dialect.DmDialect";
        }

        return str2;
    }

    /* renamed from: c */
    public static String m497c(String str) {
        return str;
    }

    /**
     * 获取数据库链接
     * @param dataBaseConfig
     * @return
     * @throws SQLException
     */
    /* renamed from: b */
    public static Connection getConnection(DataBaseConfig dataBaseConfig) throws SQLException {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dataBaseConfig.getDriverClassName());
        driverManagerDataSource.setUrl(dataBaseConfig.getUrl());
        driverManagerDataSource.setUsername(dataBaseConfig.getUsername());
        driverManagerDataSource.setPassword(dataBaseConfig.getPassword());
        return driverManagerDataSource.getConnection();
    }

    /* renamed from: c */
    public static DbType getDbTypeByonfig(DataBaseConfig dataBaseConfig) {
        if (dataBaseConfig == null) {
            return CommonUtils.getDatabaseTypeEnum();
        }
        return JdbcUtils.getDbType(dataBaseConfig.getUrl());
    }
}
