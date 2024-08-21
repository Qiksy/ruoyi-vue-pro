//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.codegenerate.database.util;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class DatabaseStrUtil {


    /**
     * 输入：["apple", "banana", "cherry"]
     * 输出："'apple','banana','cherry'"
     * @param array
     * @return
     */
    public static String joinStrList(String[] array) {
        StringBuffer sb = new StringBuffer();
        String[] strings = array;
        int var3 = array.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = strings[var4];
            if (StringUtils.isNotBlank(var5)) {
                sb.append(",");
                sb.append("'");
                sb.append(var5.trim());
                sb.append("'");
            }
        }

        return sb.substring(1);
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String firstLetterLowercase(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.substring(0, 1).toLowerCase() + str.substring(1);
        }

        return str;
    }

    public static Integer defaultIfNull(Integer integer) {
        return integer == null ? 0 : integer;
    }

    public static boolean isContains(String keyWord, String[] array) {
        if (array != null && array.length != 0) {
            for (String var3 : array) {
                if (var3.equals(keyWord)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isContains(String keyWord, List<String> list) {
        String[] var2 = new String[0];
        if (list != null) {
            var2 = (String[]) list.toArray();
        }

        if (var2 != null && var2.length != 0) {
            for (String var4 : var2) {
                if (var4.equals(keyWord)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }
}
