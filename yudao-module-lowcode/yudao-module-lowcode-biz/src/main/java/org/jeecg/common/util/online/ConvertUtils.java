package org.jeecg.common.util.online;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.SymbolConstant;

import java.io.IOException;
import java.io.InputStream;


@Slf4j
public class ConvertUtils extends StrUtil {

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

    /**
     * 读取静态文本内容
     * @param url
     * @return
     */
    public static String readStatic(String url) {
        String json = "";
        try {
            //换个写法，解决springboot读取jar包中文件的问题
            InputStream stream = ConvertUtils.class.getClassLoader().getResourceAsStream(url.replace("classpath:", ""));
            json = IOUtils.toString(stream,"UTF-8");
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return json;
    }


    public static int getInt(String s, int defval) {
        if (s == null || "".equals(s)) {
            return (defval);
        }
        try {
            return (Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return (defval);
        }
    }

    public static int getInt(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        try {
            return (Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int getInt(String s, Integer df) {
        if (s == null || "".equals(s)) {
            return df;
        }
        try {
            return (Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Integer getInt(Object object) {
        if (isEmpty(object)) {
            return null;
        }
        try {
            return (Integer.parseInt(object.toString()));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public static int getInt(Object object, int defval) {
        if (isEmpty(object)) {
            return (defval);
        }
        try {
            return (Integer.parseInt(object.toString()));
        } catch (NumberFormatException e) {
            return (defval);
        }
    }

    public static String getString(Object object) {
        if (isEmpty(object)) {
            return "";
        }
        return (object.toString().trim());
    }

    public static String getString(int i) {
        return (String.valueOf(i));
    }

    public static String getString(float i) {
        return (String.valueOf(i));
    }

    public static String getString(String s, String defval) {
        if (isEmpty(s)) {
            return (defval);
        }
        return (s.trim());
    }

    public static String getString(Object s, String defval) {
        if (isEmpty(s)) {
            return (defval);
        }
        return (s.toString().trim());
    }


    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。
     * 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world->helloWorld
     *
     * @param name
     *            转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains(SymbolConstant.UNDERLINE)) {
            // 不含下划线，仅将首字母小写
            //update-begin--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
            //update-begin--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
            return name.substring(0, 1).toLowerCase() + name.substring(1).toLowerCase();
            //update-end--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    public static boolean isNotEmpty(Object object) {
        if (object != null && !"".equals(object) && !object.equals(CommonConstant.STRING_NULL)) {
            return (true);
        }
        return (false);
    }

}
