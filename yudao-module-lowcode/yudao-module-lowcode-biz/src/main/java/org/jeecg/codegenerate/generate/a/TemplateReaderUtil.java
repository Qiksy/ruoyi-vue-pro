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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateReaderUtil {
    private String templatePath;
    private List<File> templateRootDirs = new ArrayList<>();
    private String stylePath;
    private String vueStyle;

    public TemplateReaderUtil(String templatePath) {
        this.templatePath = templatePath;
    }

    private void setTemplateRootDirs(File var1) {
        this.setTemplateRootDirsByList(var1);
    }

    private void setTemplateRootDirsByList(File... var1) {
        this.templateRootDirs = Arrays.asList(var1);
    }

    public String getVueStyle() {
        return this.vueStyle;
    }

    public void setVueStyle(String vueStyle) {
        this.vueStyle = vueStyle;
    }

    public String getStylePath() {
        return this.stylePath;
    }

    public void setStylePath(String stylePath) {
        this.stylePath = stylePath;
    }

    public List<File> loadTemplate() {
        URL var1 = this.getClass().getResource(this.templatePath);
        if (var1 == null) {
            log.error(" >> 模板加载失败，请重新编译jeecg-system-biz项目，templatePath = " + this.templatePath);
        }

        String var2 = this.getClass().getResource(this.templatePath).getFile();

        try {
            var2 = URLDecoder.decode(var2, "utf-8");
        } catch (UnsupportedEncodingException var4) {
        }

        var2 = var2.replaceAll("%20", " ");
        if (var2.indexOf("/BOOT-INF/classes!") != -1 || var2.indexOf("/BOOT-INF/lib/") != -1 || var2.indexOf(".jar!") != -1) {
            var2 = System.getProperty("user.dir") + File.separator + "config/jeecg/code-template-online/".replace("/", File.separator);
        }

        this.setTemplateRootDirs(new File(var2));
        return this.templateRootDirs;
    }

    public void setTemplateRootDirsByList(List<File> var1) {
        this.templateRootDirs = var1;
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder();
        var1.append("{\"templateRootDirs\":\"");
        var1.append(this.templateRootDirs);
        var1.append("\",\"stylePath\":\"");
        var1.append(this.stylePath);
        var1.append("\",\"vueStyle\":\"");
        var1.append(this.vueStyle);
        var1.append("\"} ");
        return var1.toString();
    }
}
