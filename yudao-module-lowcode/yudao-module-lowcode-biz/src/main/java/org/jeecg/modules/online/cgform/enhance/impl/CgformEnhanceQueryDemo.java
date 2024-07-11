package org.jeecg.modules.online.cgform.enhance.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* compiled from: CgformEnhanceQueryDemo.java */
@Component("cgformEnhanceQueryDemo")
/* renamed from: org.jeecg.modules.online.cgform.enhance.impl.f */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/impl/f.class */
public class CgformEnhanceQueryDemo implements CgformEnhanceJavaListInter {

    /* renamed from: a */
    private static final Logger loger = LoggerFactory.getLogger(CgformEnhanceQueryDemo.class);

    @Override // org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter
    public void execute(String tableName, List<Map<String, Object>> data) throws BusinessException {
        List<a> m285a = m285a();
        if (data == null) {
            return;
        }
        for (Map<String, Object> map : data) {
            Object obj = map.get("province");
            if (obj != null) {
                map.put("province", (String) m285a.stream().filter(aVar -> {
                    return obj.toString().equals(aVar.m287a());
                }).map((v0) -> {
                    return v0.m288b();
                }).findAny().orElse(""));
            }
        }
    }

    /* renamed from: a */
    private List<a> m285a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new a("bj", "北京"));
        arrayList.add(new a("sd", "山东"));
        arrayList.add(new a("ah", "安徽"));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CgformEnhanceQueryDemo.java */
    /* renamed from: org.jeecg.modules.online.cgform.enhance.impl.f$a */
    /* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/impl/f$a.class */
    public class a {

        /* renamed from: a */
        String f321a;

        /* renamed from: b */
        String f322b;

        public a(String str, String str2) {
            this.f321a = str;
            this.f322b = str2;
        }

        /* renamed from: a */
        public String m287a() {
            return this.f321a;
        }

        /* renamed from: b */
        public String m288b() {
            return this.f322b;
        }
    }
}
