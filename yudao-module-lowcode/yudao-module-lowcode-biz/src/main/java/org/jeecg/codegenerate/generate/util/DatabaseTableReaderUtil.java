
//

package org.jeecg.codegenerate.generate.util;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.codegenerate.generate.pojo.ColumnVo;

public class DatabaseTableReaderUtil {


    /**
     * 转换为true或false
     * @param str
     * @return
     */
    public static String converTrueFalse(String str) {
        if (!"YES".equals(str) && !"yes".equals(str) && !"y".equals(str) && !"Y".equals(str) && !"f".equals(str)) {
            return !"NO".equals(str) && !"N".equals(str) && !"no".equals(str) && !"n".equals(str) && !"t".equals(str) ? null : "N";
        } else {
            return "Y";
        }
    }

    public static String converStr(String var0) {
        return StringUtils.isBlank(var0) ? "" : var0;
    }


    /**
     * 包裹单引号 Wrap single quotation
     * @param str
     * @return
     */
    public static String wrapSingleQuotation(String str) {
        return "'" + str + "'";
    }

    /**
     * 输入一个tableName，返回一个类名，如输入：jeecg_demo，返回JeecgDemo
     * @param tableName
     * @return
     */
    public static String underlineToCamel(String tableName) {
        String[] words = tableName.split("_");
        tableName = "";
        int var2 = 0;

        for(int var3 = words.length; var2 < var3; ++var2) {
            if (var2 > 0) {
                String tempWord = words[var2].toLowerCase();
                tempWord = tempWord.substring(0, 1).toUpperCase() + tempWord.substring(1, tempWord.length());
                tableName = tableName + tempWord;
            } else {
                tableName = tableName + words[var2].toLowerCase();
            }
        }

        return tableName;
    }

    public static void setClassType(ColumnVo columnVo) {
        String fieldType = columnVo.getFieldType();
        String scale = columnVo.getScale();
        columnVo.setClassType("inputxt");
        if ("N".equals(columnVo.getNullable())) {
            //不允许为空
            columnVo.setOptionType("*");
        }

        if (!"datetime".equals(fieldType) && !fieldType.contains("time")) {
            if ("date".equals(fieldType)) {
                columnVo.setClassType("easyui-datebox");
            } else if (fieldType.contains("int")) {
                columnVo.setOptionType("n");
            } else if ("number".equals(fieldType)) {
                if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0) {
                    columnVo.setOptionType("d");
                }
            } else if (!"float".equals(fieldType) && !"double".equals(fieldType) && !"decimal".equals(fieldType)) {
                if ("numeric".equals(fieldType)) {
                    columnVo.setOptionType("d");
                }
            } else {
                columnVo.setOptionType("d");
            }
        } else {
            columnVo.setClassType("easyui-datetimebox");
        }

    }

    public static String converType(String type, String var1, String var2) {
        if (type.contains("char")) {
            type = "java.lang.String";
        } else if (type.contains("int")) {
            type = "java.lang.Integer";
        } else if (type.contains("float")) {
            type = "java.lang.Float";
        } else if (type.contains("double")) {
            type = "java.lang.Double";
        } else if (type.contains("number")) {
            if (StringUtils.isNotBlank(var2) && Integer.parseInt(var2) > 0) {
                type = "java.math.BigDecimal";
            } else if (StringUtils.isNotBlank(var1) && Integer.parseInt(var1) > 10) {
                type = "java.lang.Long";
            } else {
                type = "java.lang.Integer";
            }
        } else if (type.contains("decimal")) {
            type = "java.math.BigDecimal";
        } else if (type.contains("date")) {
            type = "java.util.Date";
        } else if (type.contains("time")) {
            type = "java.util.Date";
        } else if (type.contains("blob")) {
            type = "byte[]";
        } else if (type.contains("clob")) {
            type = "java.sql.Clob";
        } else if (type.contains("numeric")) {
            type = "java.math.BigDecimal";
        } else {
            type = "java.lang.Object";
        }

        return type;
    }
}
