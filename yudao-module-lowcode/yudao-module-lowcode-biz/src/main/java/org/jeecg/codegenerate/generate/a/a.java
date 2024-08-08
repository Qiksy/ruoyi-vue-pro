//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.codegenerate.generate.a;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class a {
    private static final Logger a = LoggerFactory.getLogger(a.class);
    private String b;
    private List<File> c = new ArrayList();
    private String d;
    private String e;

    public a(String var1) {
        this.b = var1;
    }

    private void a(File var1) {
        this.a(var1);
    }

    private void a(File... var1) {
        this.c = Arrays.asList(var1);
    }

    public String a() {
        return this.e;
    }

    public void a(String var1) {
        this.e = var1;
    }

    public String b() {
        return this.d;
    }

    public void b(String var1) {
        this.d = var1;
    }

    public List<File> c() {
        URL var1 = this.getClass().getResource(this.b);
        if (var1 == null) {
            a.error(" >> 模板加载失败，请重新编译jeecg-system-biz项目，templatePath = " + this.b);
        }

        String var2 = this.getClass().getResource(this.b).getFile();

        try {
            var2 = URLDecoder.decode(var2, "utf-8");
        } catch (UnsupportedEncodingException var4) {
        }

        var2 = var2.replaceAll("%20", " ");
        if (var2.indexOf("/BOOT-INF/classes!") != -1 || var2.indexOf("/BOOT-INF/lib/") != -1 || var2.indexOf(".jar!") != -1) {
            var2 = System.getProperty("user.dir") + File.separator + "config/jeecg/code-template-online/".replace("/", File.separator);
        }

        this.a(new File(var2));
        return this.c;
    }

    public void a(List<File> var1) {
        this.c = var1;
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder();
        var1.append("{\"templateRootDirs\":\"");
        var1.append(this.c);
        var1.append("\",\"stylePath\":\"");
        var1.append(this.d);
        var1.append("\",\"vueStyle\":\"");
        var1.append(this.e);
        var1.append("\"} ");
        return var1.toString();
    }
}
