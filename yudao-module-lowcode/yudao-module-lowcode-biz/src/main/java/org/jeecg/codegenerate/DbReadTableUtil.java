package org.jeecg.codegenerate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.codegenerate.database.DatabaseTypeChecker;
import org.jeecg.codegenerate.database.util.DatabaseStrUtil;
import org.jeecg.codegenerate.generate.pojo.ColumnVo;
import org.jeecg.codegenerate.generate.util.DatabaseTableReaderUtil;
import org.jeecg.common.config.LowCodeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbReadTableUtil {
    private static final Logger log = LoggerFactory.getLogger(DbReadTableUtil.class);
    private static Connection connection;
    private static Statement statement;

    public DbReadTableUtil() {
    }

    public static List<String> a() throws SQLException {
        return readAllTableNames();
    }

    public static List<String> readAllTableNames() throws SQLException {
        String sql = null;
        ArrayList<String> tableNames = new ArrayList<>(0);

        try {
            Class.forName(LowCodeProperties.getDriverName());
            connection = DriverManager.getConnection(LowCodeProperties.getUrl(), LowCodeProperties.getUserName(), LowCodeProperties.getPassword());
            statement = connection.createStatement(1005, 1007);
            String catalog = connection.getCatalog();
            log.info(" connect databaseName : " + catalog);
            if (DatabaseTypeChecker.isMySQL(LowCodeProperties.url)) {
                //如果这里是mysql类型的
                sql = MessageFormat.format("select distinct table_name from information_schema.columns where table_schema = {0}", DatabaseTableReaderUtil.wrapSingleQuotation(catalog));
            }

            if (DatabaseTypeChecker.isOracle(LowCodeProperties.url)) {
                sql = " select distinct colstable.table_name as  table_name from user_tab_cols colstable order by colstable.table_name";
            }

            if (DatabaseTypeChecker.isPostgreSQL(LowCodeProperties.url)) {
                if (!LowCodeProperties.schemaName.contains(",")) {
                    sql = MessageFormat.format("select tablename from pg_tables where schemaname in( {0} )",
                            DatabaseTableReaderUtil.wrapSingleQuotation(LowCodeProperties.schemaName));
                } else {
                    StringBuffer buffer = new StringBuffer();
                    String[] schemaNames = LowCodeProperties.schemaName.split(",");
                    String[] tempSchemaNames = schemaNames;
                    int len = schemaNames.length;

                    for(int i = 0; i < len; ++i) {
                        String schemaName = tempSchemaNames[i];
                        buffer.append(DatabaseTableReaderUtil.wrapSingleQuotation(schemaName) + ",");
                    }

                    sql = MessageFormat.format("select tablename from pg_tables where schemaname in( {0} )", buffer.toString().substring(0, buffer.toString().length() - 1));
                }
            }

            if (DatabaseTypeChecker.isSQLServer(LowCodeProperties.url)) {
                sql = "select distinct c.name as  table_name from sys.objects c where c.type = 'U' ";
            }

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                String tableName = resultSet.getString(1);
                tableNames.add(tableName);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    statement = null;
                    System.gc();
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                    System.gc();
                }
            } catch (SQLException exception) {
                throw exception;
            }

        }

        return tableNames;
    }

    public static List<ColumnVo> readColumnByTableName(String tableName) throws Exception {
        String sql = null;
        ArrayList<ColumnVo> columns = new ArrayList<>();

        int rowCount;
        try {
            Class.forName(LowCodeProperties.driverName);
            connection = DriverManager.getConnection(LowCodeProperties.getUrl(), LowCodeProperties.getUserName(), LowCodeProperties.getPassword());
            statement = connection.createStatement(1005, 1007);
            String catalog = connection.getCatalog();
            log.info(" connect databaseName : " + catalog);
            if (DatabaseTypeChecker.isMySQL(LowCodeProperties.url)) {
                sql = MessageFormat.format("select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ORDINAL_POSITION", DatabaseTableReaderUtil.wrapSingleQuotation(tableName), DatabaseTableReaderUtil.wrapSingleQuotation(catalog));
            }

            if (DatabaseTypeChecker.isOracle(LowCodeProperties.url)) {
                sql = MessageFormat.format(" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}", DatabaseTableReaderUtil.wrapSingleQuotation(tableName.toUpperCase()));
            }

            if (DatabaseTypeChecker.isPostgreSQL(LowCodeProperties.url)) {
                sql = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull  from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname \tFROM pg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", DatabaseTableReaderUtil.wrapSingleQuotation(tableName), DatabaseTableReaderUtil.wrapSingleQuotation(tableName));
            }

            if (DatabaseTypeChecker.isSQLServer(LowCodeProperties.url)) {
                sql = MessageFormat.format("select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as NVARCHAR(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id", DatabaseTableReaderUtil.wrapSingleQuotation(tableName));
            }

            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
            rowCount = resultSet.getRow();
            if (rowCount <= 0) {
                throw new Exception("该表不存在或者表中没有字段");
            }

            ColumnVo vo = new ColumnVo();
            if (LowCodeProperties.dbFiledConvert) {
                vo.setFieldName(DatabaseTableReaderUtil.underlineToCamel(resultSet.getString(1).toLowerCase()));
            } else {
                vo.setFieldName(resultSet.getString(1).toLowerCase());
            }

            vo.setFieldDbName(resultSet.getString(1).toUpperCase());
            vo.setFieldType(DatabaseTableReaderUtil.underlineToCamel(resultSet.getString(2).toLowerCase()));
            vo.setFieldDbType(DatabaseTableReaderUtil.underlineToCamel(resultSet.getString(2).toLowerCase()));
            vo.setPrecision(resultSet.getString(4));
            vo.setScale(resultSet.getString(5));
            vo.setCharmaxLength(resultSet.getString(6));
            vo.setNullable(DatabaseTableReaderUtil.converTrueFalse(resultSet.getString(7)));
            DatabaseTableReaderUtil.setClassType(vo);
            vo.setFiledComment(StringUtils.isBlank(resultSet.getString(3)) ? vo.getFieldName() : resultSet.getString(3));
            //忽略
            String[] ignoreFields = new String[0];
            if (LowCodeProperties.pageFilterFields != null) {
                ignoreFields = LowCodeProperties.pageFilterFields.toLowerCase().split(",");
            }

            if (!LowCodeProperties.tableId.equals(vo.getFieldName()) && !DatabaseStrUtil.isContains(vo.getFieldDbName().toLowerCase(), ignoreFields)) {
                columns.add(vo);
            }

            while(resultSet.previous()) {
                ColumnVo columnVo = new ColumnVo();
                if (LowCodeProperties.dbFiledConvert) {
                    columnVo.setFieldName(DatabaseTableReaderUtil.underlineToCamel(resultSet.getString(1).toLowerCase()));
                } else {
                    columnVo.setFieldName(resultSet.getString(1).toLowerCase());
                }

                columnVo.setFieldDbName(resultSet.getString(1).toUpperCase());
                if (!LowCodeProperties.tableId.equals(columnVo.getFieldName()) && !DatabaseStrUtil.isContains(columnVo.getFieldDbName().toLowerCase(), ignoreFields)) {
                    columnVo.setFieldType(DatabaseTableReaderUtil.underlineToCamel(resultSet.getString(2).toLowerCase()));
                    columnVo.setFieldDbType(DatabaseTableReaderUtil.underlineToCamel(resultSet.getString(2).toLowerCase()));
                    columnVo.setPrecision(resultSet.getString(4));
                    columnVo.setScale(resultSet.getString(5));
                    columnVo.setCharmaxLength(resultSet.getString(6));
                    columnVo.setNullable(DatabaseTableReaderUtil.converTrueFalse(resultSet.getString(7)));
                    DatabaseTableReaderUtil.setClassType(columnVo);
                    columnVo.setFiledComment(StringUtils.isBlank(resultSet.getString(3)) ? columnVo.getFieldName() : resultSet.getString(3));
                    columns.add(columnVo);
                }
            }
        } catch (ClassNotFoundException var18) {
            throw var18;
        } catch (SQLException var19) {
            throw var19;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    statement = null;
                    System.gc();
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                    System.gc();
                }
            } catch (SQLException var17) {
                throw var17;
            }

        }

        ArrayList var21 = new ArrayList();

        for(rowCount = columns.size() - 1; rowCount >= 0; --rowCount) {
            ColumnVo var6 = (ColumnVo)columns.get(rowCount);
            var21.add(var6);
        }

        return var21;
    }

    public static String getProjectPath() {
        return LowCodeProperties.projectPath;
    }

    public static List<ColumnVo> b(String tableName) throws Exception {
        return readOriginalTableColumn(tableName);
    }

    public static List<ColumnVo> readOriginalTableColumn(String tableName) throws Exception {
        ResultSet var1 = null;
        String var2 = null;
        ArrayList var3 = new ArrayList();

        int var5;
        try {
            Class.forName(LowCodeProperties.driverName);
            connection = DriverManager.getConnection(LowCodeProperties.getUrl(), LowCodeProperties.getUserName(), LowCodeProperties.getPassword());
            statement = connection.createStatement(1005, 1007);
            String var4 = connection.getCatalog();
            log.info(" connect databaseName : " + var4);
            if (DatabaseTypeChecker.isMySQL(LowCodeProperties.url)) {
                var2 = MessageFormat.format("select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ORDINAL_POSITION", DatabaseTableReaderUtil.wrapSingleQuotation(tableName), DatabaseTableReaderUtil.wrapSingleQuotation(var4));
            }

            if (DatabaseTypeChecker.isOracle(LowCodeProperties.url)) {
                var2 = MessageFormat.format(" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}", DatabaseTableReaderUtil.wrapSingleQuotation(tableName.toUpperCase()));
            }

            if (DatabaseTypeChecker.isPostgreSQL(LowCodeProperties.url)) {
                var2 = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull  from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname \tFROM pg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", DatabaseTableReaderUtil.wrapSingleQuotation(tableName), DatabaseTableReaderUtil.wrapSingleQuotation(tableName));
            }

            if (DatabaseTypeChecker.isSQLServer(LowCodeProperties.url)) {
                var2 = MessageFormat.format("select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as NVARCHAR(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id", DatabaseTableReaderUtil.wrapSingleQuotation(tableName));
            }

            var1 = statement.executeQuery(var2);
            var1.last();
            var5 = var1.getRow();
            if (var5 <= 0) {
                throw new Exception("该表不存在或者表中没有字段");
            }

            ColumnVo columnVo = new ColumnVo();
            if (LowCodeProperties.dbFiledConvert) {
                columnVo.setFieldName(DatabaseTableReaderUtil.underlineToCamel(var1.getString(1).toLowerCase()));
            } else {
                columnVo.setFieldName(var1.getString(1).toLowerCase());
            }

            columnVo.setFieldDbName(var1.getString(1).toUpperCase());
            columnVo.setPrecision(DatabaseTableReaderUtil.converStr(var1.getString(4)));
            columnVo.setScale(DatabaseTableReaderUtil.converStr(var1.getString(5)));
            columnVo.setCharmaxLength(DatabaseTableReaderUtil.converStr(var1.getString(6)));
            columnVo.setNullable(DatabaseTableReaderUtil.converTrueFalse(var1.getString(7)));
            columnVo.setFieldType(DatabaseTableReaderUtil.converType(var1.getString(2).toLowerCase(), columnVo.getPrecision(), columnVo.getScale()));
            columnVo.setFieldDbType(DatabaseTableReaderUtil.underlineToCamel(var1.getString(2).toLowerCase()));
            DatabaseTableReaderUtil.setClassType(columnVo);
            columnVo.setFiledComment(StringUtils.isBlank(var1.getString(3)) ? columnVo.getFieldName() : var1.getString(3));
            var3.add(columnVo);

            while(var1.previous()) {
                ColumnVo vo = new ColumnVo();
                if (LowCodeProperties.dbFiledConvert) {
                    vo.setFieldName(DatabaseTableReaderUtil.underlineToCamel(var1.getString(1).toLowerCase()));
                } else {
                    vo.setFieldName(var1.getString(1).toLowerCase());
                }

                vo.setFieldDbName(var1.getString(1).toUpperCase());
                vo.setPrecision(DatabaseTableReaderUtil.converStr(var1.getString(4)));
                vo.setScale(DatabaseTableReaderUtil.converStr(var1.getString(5)));
                vo.setCharmaxLength(DatabaseTableReaderUtil.converStr(var1.getString(6)));
                vo.setNullable(DatabaseTableReaderUtil.converTrueFalse(var1.getString(7)));
                vo.setFieldType(DatabaseTableReaderUtil.converType(var1.getString(2).toLowerCase(), vo.getPrecision(), vo.getScale()));
                vo.setFieldDbType(DatabaseTableReaderUtil.underlineToCamel(var1.getString(2).toLowerCase()));
                DatabaseTableReaderUtil.setClassType(vo);
                vo.setFiledComment(StringUtils.isBlank(var1.getString(3)) ? vo.getFieldName() : var1.getString(3));
                var3.add(vo);
            }
        } catch (ClassNotFoundException var17) {
            throw var17;
        } catch (SQLException var18) {
            throw var18;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    statement = null;
                    System.gc();
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                    System.gc();
                }
            } catch (SQLException var16) {
                throw var16;
            }

        }

        ArrayList var20 = new ArrayList();

        for(var5 = var3.size() - 1; var5 >= 0; --var5) {
            ColumnVo var6 = (ColumnVo)var3.get(var5);
            var20.add(var6);
        }

        return var20;
    }

    public static boolean c(String var0) {
        String var2 = null;

        try {
            Class.forName(LowCodeProperties.driverName);
            connection = DriverManager.getConnection(LowCodeProperties.getUrl(), LowCodeProperties.getUserName(), LowCodeProperties.getPassword());
            statement = connection.createStatement(1005, 1007);
            String var3 = connection.getCatalog();
            log.info(" connect databaseName : " + var3);
            if (DatabaseTypeChecker.isMySQL(LowCodeProperties.url)) {
                var2 = "select column_name,data_type,column_comment,0,0 from information_schema.columns where table_name = '" + var0 + "' and table_schema = '" + var3 + "'";
            }

            if (DatabaseTypeChecker.isOracle(LowCodeProperties.url)) {
                var2 = "select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = '" + var0.toUpperCase() + "'";
            }

            if (DatabaseTypeChecker.isPostgreSQL(LowCodeProperties.url)) {
                var2 = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull  from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname \tFROM pg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", DatabaseTableReaderUtil.wrapSingleQuotation(var0), DatabaseTableReaderUtil.wrapSingleQuotation(var0));
            }

            if (DatabaseTypeChecker.isSQLServer(LowCodeProperties.url)) {
                var2 = MessageFormat.format("select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as NVARCHAR(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id", DatabaseTableReaderUtil.wrapSingleQuotation(var0));
            }

            ResultSet var1 = statement.executeQuery(var2);
            var1.last();
            int var4 = var1.getRow();
            return var4 > 0;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public static String d(String var0) {
        String[] var1 = var0.split("_");
        var0 = "";
        int var2 = 0;

        for(int var3 = var1.length; var2 < var3; ++var2) {
            if (var2 > 0) {
                String var4 = var1[var2].toLowerCase();
                var4 = var4.substring(0, 1).toUpperCase() + var4.substring(1, var4.length());
                var0 = var0 + var4;
            } else {
                var0 = var0 + var1[var2].toLowerCase();
            }
        }

        var0 = var0.substring(0, 1).toUpperCase() + var0.substring(1);
        return var0;
    }
}
