package org.jeecg.common.util.online;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;

public class StrUtils extends StrUtil {

    /**
     * 字符串驼峰转下划线格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String camelToUnderline(String param) {
        if (isBlank(param)) {
            return StringPool.EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    public static boolean isEmpty(Object object){
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            return ((String) object).trim().isEmpty();
        }
        return false;
    }
}
