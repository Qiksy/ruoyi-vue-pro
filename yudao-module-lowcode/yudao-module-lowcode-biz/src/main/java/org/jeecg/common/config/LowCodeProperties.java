package org.jeecg.common.config;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


@Slf4j
public class LowCodeProperties {

    private static final String DATABASE_PROPERTIES_PATH = "lowcode/lowcode_database";
    private static final String LOWCODE_CONFIG = "lowcode/lowcode_config";
    private static ResourceBundle dataBaseProperties = getPropertiesBundle(DATABASE_PROPERTIES_PATH);
    private static ResourceBundle configProperties;
    public static String schemaName;
    public static String driverName;
    public static String url;
    public static String username;
    public static String password;
    public static String projectPath;
    public static String bussiPackageName;
    public static String sourceRootPackage;
    public static String webRootPackage;
    public static String templatePath;
    public static boolean dbFiledConvert;
    public static String tableId;
    public static String fieldRequiredNum;
    public static String pageSearchFiledNum;
    public static String pageFilterFields;
    public static String fieldRowNum;
    private static int classCount;
    private static boolean isIndexJspPresent;
    private static boolean x;
    private static boolean y;
    private static boolean z;
    private static boolean A;
    private static boolean B;


    private static ResourceBundle getPropertiesBundle(String path) {
        PropertyResourceBundle bundle = null;
        BufferedInputStream inputStream = null;
        String fullPath = System.getProperty("user.dir") + File.separator + "config" + File.separator + path + ".properties";

        try {
            inputStream = new BufferedInputStream(new FileInputStream(fullPath));
            bundle = new PropertyResourceBundle(inputStream);
            inputStream.close();
            if (bundle != null) {
                log.info(" JAR方式部署，通过config目录读取配置：" + fullPath);
            }
        } catch (IOException var13) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }

        }

        return bundle;
    }



    public static final String getDriverName() {
        return dataBaseProperties.getString("diver_name");
    }

    public static final String getUrl() {
        return dataBaseProperties.getString("url");
    }

    public static final String getUserName() {
        return dataBaseProperties.getString("username");
    }

    public static final String getSchemaName() {
        return dataBaseProperties.containsKey("schemaName") ? dataBaseProperties.getString("schemaName") : null;
    }

    public static final String getPassword() {
        return dataBaseProperties.getString("password");
    }

    public static final String getDatabaseName() {
        return dataBaseProperties.getString("database_name");
    }

    public static final boolean getDbFiledConvert() {
        String dbFiledConvert1 = configProperties.getString("db_filed_convert");
        return !dbFiledConvert1.equals("false");
    }

    private static String getBussiPackage() {
        return configProperties.getString("bussi_package");
    }

    private static String getTemplatePath() {
        return configProperties.getString("templatepath");
    }

    public static final String getSourceRootPackage() {
        return configProperties.getString("source_root_package");
    }

    public static final String getWebrootPackage() {
        return configProperties.getString("webroot_package");
    }

    public static final String getDbTableId() {
        return configProperties.getString("db_table_id");
    }

    public static final String getPageFilterFields() {
        return configProperties.getString("page_filter_fields");
    }

    public static final String getPageSearchFiledNum() {
        return configProperties.getString("page_search_filed_num");
    }

    public static String getProjectPath() {
        String var0 = configProperties.getString("project_path");
        if (var0 != null && !"".equals(var0)) {
            projectPath = var0;
        }

        return projectPath;
    }

    public static final String getPageFieldRequiredNum() {
        return configProperties.getString("page_field_required_num");
    }

    public static void setProjectPath(String var0) {
        projectPath = var0;
    }

    public static void setTemplatePath(String var0) {
        templatePath = var0;
    }

    static {
        if (dataBaseProperties == null) {
            dataBaseProperties = ResourceBundle.getBundle(DATABASE_PROPERTIES_PATH);
        }

        configProperties = getPropertiesBundle(LOWCODE_CONFIG);
        if (configProperties == null) {
            configProperties = ResourceBundle.getBundle(LOWCODE_CONFIG);
        }

        schemaName = "public";
        driverName = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/jeecg-boot?useUnicode=true&characterEncoding=UTF-8";
        username = "root";
        password = "root";
        projectPath = "c:/workspace/jeecg";
        bussiPackageName = "com.jeecg";
        sourceRootPackage = "src";
        webRootPackage = "WebRoot";
        templatePath = "/jeecg/code-template/";
        dbFiledConvert = true;
        fieldRequiredNum = "4";
        pageSearchFiledNum = "3";
        fieldRowNum = "1";
        driverName = getDriverName();
        url = getUrl();
        String schemaName = getSchemaName();
        if (schemaName != null && !"".equals(schemaName)) {
            LowCodeProperties.schemaName = schemaName;
        }

        username = getUserName();
        password = getPassword();
        sourceRootPackage = getSourceRootPackage();
        webRootPackage = getWebrootPackage();
        bussiPackageName = getBussiPackage();
        templatePath = getTemplatePath();
        projectPath = getProjectPath();
        tableId = getDbTableId();
        dbFiledConvert = getDbFiledConvert();
        pageFilterFields = getPageFilterFields();
        pageSearchFiledNum = getPageSearchFiledNum();
        sourceRootPackage = sourceRootPackage.replace(".", "/");
        webRootPackage = webRootPackage.replace(".", "/");

    }
}
