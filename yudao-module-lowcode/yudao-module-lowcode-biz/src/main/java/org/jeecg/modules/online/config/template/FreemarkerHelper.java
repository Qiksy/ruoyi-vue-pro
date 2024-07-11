package org.jeecg.modules.online.config.template;

import freemarker.core.TemplateClassResolver;
import freemarker.template.Configuration;
import java.io.StringWriter;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: FreemarkerHelper.java */
/* renamed from: org.jeecg.modules.online.config.d.g */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/d/g.class */
public class FreemarkerHelper {

    /* renamed from: a */
    private static final Logger f557a = LoggerFactory.getLogger(FreemarkerHelper.class);

    /* renamed from: b */
    private static Configuration f558b = new Configuration(Configuration.VERSION_2_3_28);

    static {
        f558b.setNumberFormat("0.#####################");
        f558b.setClassForTemplateLoading(FreemarkerHelper.class, "/");
        f558b.setNewBuiltinClassResolver(TemplateClassResolver.SAFER_RESOLVER);
    }

    /* renamed from: a */
    public static String m502a(String str, String str2, Map<String, Object> map) {
        try {
            StringWriter stringWriter = new StringWriter();
            f558b.getTemplate(str, str2).process(map, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            f557a.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /* renamed from: a */
    public static String m503a(String str, Map<String, Object> map) {
        return m502a(str, "utf-8", map);
    }
}
