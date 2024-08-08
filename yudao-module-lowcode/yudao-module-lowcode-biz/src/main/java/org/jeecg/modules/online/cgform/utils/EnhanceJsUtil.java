package org.jeecg.modules.online.cgform.utils;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.enums.CgformConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: EnhanceJsUtil.java */
/* renamed from: org.jeecg.modules.online.cgform.d.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/d/c.class */
public class EnhanceJsUtil {

    /* renamed from: f */
    private static final String f280f = "setup,beforeSubmit,beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created,show,loaded";

    /* renamed from: g */
    private static final String f281g = "\\}\\s*\r*\n*\\s*";

    /* renamed from: h */
    private static final String f282h = ",";

    /* renamed from: b */
    public static final String f284b = "import";

    /* renamed from: c */
    public static final String f285c = "customImport";

    /* renamed from: d */
    public static final String f286d = "_hook";

    /* renamed from: e */
    private static final Logger f279e = LoggerFactory.getLogger(EnhanceJsUtil.class);

    /* renamed from: a */
    public static final Pattern f283a = Pattern.compile("^import\\s+(.*)\\s+from\\s+(['\"].*['\"])[;]?$");

    /* renamed from: a */
    public static String m265a(String str, String str2) {
        String str3;
        String str4 = "(" + str2 + "\\s*\\(row\\)\\s*\\{)";
        String str5 = str2 + ":function(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;";
        String m267b = m267b(str, "\\}\\s*\r*\n*\\s*" + str4, "}," + str5);
        if (m267b == null) {
            str3 = m268c(str, str4, str5);
        } else {
            str3 = m267b;
        }
        return m266a(str3, str2, (String) null);
    }

    /* renamed from: a */
    public static String m266a(String str, String str2, String str3) {
        String str4;
        String str5 = "(" + ConvertUtils.getString(str3) + str2 + "\\s*\\(\\)\\s*\\{)";
        String str6 = str2 + ":function(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;";
        String m267b = m267b(str, "\\}\\s*\r*\n*\\s*" + str5, "}," + str6);
        if (m267b == null) {
            str4 = m268c(str, str5, str6);
        } else {
            str4 = m267b;
        }
        return str4;
    }

    /* renamed from: b */
    public static String m267b(String str, String str2, String str3) {
        Matcher matcher = Pattern.compile(str2).matcher(str);
        if (matcher.find()) {
            return str.replace(matcher.group(0), str3);
        }
        return null;
    }

    /* renamed from: c */
    public static String m268c(String str, String str2, String str3) {
        String m267b = m267b(str, str2, str3);
        if (m267b != null) {
            return m267b;
        }
        return str;
    }

    /* renamed from: a */
    public static String m269a(String str, List<OnlCgformButton> list) {
        return "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}" + str + "}";
    }

    /* renamed from: b */
    public static String m270b(String str, String str2) {
        String str3;
        String str4 = "(\\s+" + str2 + "\\s*\\(\\)\\s*\\{)";
        String str5 = str2 + ":function(that,event){";
        String m267b = m267b(str, "\\}\\s*\r*\n*\\s*" + str4, "}," + str5);
        if (m267b == null) {
            str3 = m268c(str, str4, str5);
        } else {
            str3 = m267b;
        }
        return str3;
    }

    /* renamed from: a */
    public static String m271a(String str) {
        return "function OnlineEnhanceJs(getAction,postAction,deleteAction){return {_getAction:getAction,_postAction:postAction,_deleteAction:deleteAction," + str + "}}";
    }

    /* renamed from: b */
    public static String m272b(String str, List<OnlCgformButton> list) {
        return "function OnlineEnhanceJs(getAction,postAction,deleteAction){return {_getAction:getAction,_postAction:postAction,_deleteAction:deleteAction," + m273c(str, list) + "}}";
    }

    /* renamed from: c */
    public static String m273c(String str, List<OnlCgformButton> list) {
        String m265a;
        String m276b = m276b(str);
        if (list != null) {
            for (OnlCgformButton onlCgformButton : list) {
                String buttonCode = onlCgformButton.getButtonCode();
                if ("link".equals(onlCgformButton.getButtonStyle())) {
                    m276b = m265a(m265a(m276b, buttonCode), buttonCode + "_hook");
                } else if ("button".equals(onlCgformButton.getButtonStyle()) || CgformUtil.FORM.equals(onlCgformButton.getButtonStyle())) {
                    m276b = m266a(m266a(m276b, buttonCode, (String) null), buttonCode + "_hook", (String) null);
                }
            }
        }
        for (String str2 : f280f.split(",")) {
            if ("setup,beforeAdd,afterAdd,mounted,created,show,loaded".contains(str2)) {
                m265a = m266a(m276b, str2, (String) null);
            } else {
                m265a = m265a(m276b, str2);
            }
            m276b = m265a;
        }
        return m276b;
    }

    /* renamed from: a */
    public static void m274a(OnlCgformEnhanceJs onlCgformEnhanceJs, String str, List<OnlCgformField> list) {
        if (onlCgformEnhanceJs == null || StrUtils.isEmpty(onlCgformEnhanceJs.getCgJs())) {
            return;
        }
        String str2 = " " + onlCgformEnhanceJs.getCgJs();
        if (Pattern.compile("(\\s{1}onlChange\\s*\\(\\)\\s*\\{)").matcher(str2).find()) {
            str2 = m266a(str2, CgformConstant.ONL_CHANGE, "\\s{1}");
            Iterator<OnlCgformField> it = list.iterator();
            while (it.hasNext()) {
                str2 = m270b(str2, it.next().getDbFieldName());
            }
        }
        onlCgformEnhanceJs.setCgJs(str2);
    }

    /* renamed from: b */
    public static void m275b(OnlCgformEnhanceJs onlCgformEnhanceJs, String str, List<OnlCgformField> list) {
        if (onlCgformEnhanceJs == null || StrUtils.isEmpty(onlCgformEnhanceJs.getCgJs())) {
            return;
        }
        String cgJs = onlCgformEnhanceJs.getCgJs();
        String str2 = str + "_onlChange";
        if (Pattern.compile("(" + str2 + "\\s*\\(\\)\\s*\\{)").matcher(cgJs).find()) {
            cgJs = m266a(cgJs, str2, (String) null);
            for (OnlCgformField onlCgformField : list) {
                cgJs = m270b(cgJs, onlCgformField.getDbFieldName());
            }
        }
        onlCgformEnhanceJs.setCgJs(cgJs);
    }

    /* renamed from: b */
    private static String m276b(String str) {
        String[] split = str.split("\n");
        for (int i = 0; i < split.length; i++) {
            String trim = split[i].trim();
            if (trim.startsWith("import")) {
                Matcher matcher = f283a.matcher(trim);
                if (matcher.find()) {
                    split[i] = split[i].replace(trim, String.format("const %s = %s(%s)", matcher.group(1), f285c, matcher.group(2)));
                }
            }
        }
        return String.join("\n", split);
    }
}
