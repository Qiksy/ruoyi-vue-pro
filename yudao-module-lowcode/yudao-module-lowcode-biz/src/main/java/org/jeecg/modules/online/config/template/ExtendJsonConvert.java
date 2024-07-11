package org.jeecg.modules.online.config.template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: ExtendJsonConvert.java */
/* renamed from: org.jeecg.modules.online.config.d.e */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/d/e.class */
public class ExtendJsonConvert {

    /* renamed from: a */
    protected static Map<String, String> f556a = new HashMap(5);

    static {
        f556a.put("class", "clazz");
    }

    /* renamed from: a */
    private static String m500a(String str, int i) {
        String str2 = str;
        Iterator<String> it = f556a.keySet().iterator();
        while (it.hasNext()) {
            String valueOf = String.valueOf(it.next());
            String valueOf2 = String.valueOf(f556a.get(valueOf));
            if (i == 1) {
                str2 = str.replaceAll(valueOf, valueOf2);
            } else if (i == 2) {
                str2 = str.replaceAll(valueOf2, valueOf);
            }
        }
        return str2;
    }
}
