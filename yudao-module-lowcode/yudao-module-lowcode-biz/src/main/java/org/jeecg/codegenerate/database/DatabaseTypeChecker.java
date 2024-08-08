package org.jeecg.codegenerate.database;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseTypeChecker {

    /**
     * @param url 数据库连接
     * @return
     */
    public static boolean isMySQL(String url) {
        return a(url, "mysql") || a(url, "mariadb") || a(url, "sqlite") || a(url, "clickhouse") || a(url, "polardb");
    }

    public static boolean isOracle(String var0) {
        return a(var0, "oracle9i") || a(var0, "oracle") || a(var0, "dm") || a(var0, "edb");
    }

    public static boolean isSQLServer(String var0) {
        return a(var0, "sqlserver") || a(var0, "sqlserver2012") || a(var0, "derby");
    }

    public static boolean isPostgreSQL(String var0) {
        return a(var0, "postgresql") || a(var0, "kingbase") || a(var0, "zenith");
    }

    private static boolean a(String var0, String var1) {
        String var2 = "jdbc:" + var1;
        return var0.toLowerCase().contains(var2);
    }
}