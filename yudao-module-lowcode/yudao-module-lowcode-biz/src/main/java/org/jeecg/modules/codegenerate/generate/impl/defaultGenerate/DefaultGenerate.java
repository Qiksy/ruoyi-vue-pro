//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.codegenerate.generate.impl.defaultGenerate;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.codegenerate.generate.a.TemplateReaderUtil;
import org.jeecg.modules.codegenerate.generate.util.FileUtils;
import org.jeecg.modules.codegenerate.generate.util.NonceUtils;
import org.jeecg.modules.codegenerate.generate.util.b;
import org.jeecg.modules.codegenerate.generate.util.g;
import org.jeecg.common.config.LowCodeProperties;

@Slf4j
public class DefaultGenerate {
    protected static String c = "UTF-8";
    private static final String underLine = "__";
    protected List<String> d = new ArrayList();

    public DefaultGenerate() {
    }

    protected void a(TemplateReaderUtil templateReaderUtil, String var2, Map<String, Object> var3) throws Exception {
        for(int i = 0; i < templateReaderUtil.loadTemplate().size(); ++i) {
            File template = templateReaderUtil.loadTemplate().get(i);
            this.a(var2, template, var3, templateReaderUtil);
        }

    }

    protected void a(String var1, File file, Map<String, Object> var3, TemplateReaderUtil readerUtil) throws Exception {
        if (file == null) {
            throw new IllegalStateException("'templateRootDir' must be not null");
        } else {
            log.info("  load template from templateRootDir = '" + file.getAbsolutePath() + "',stylePath ='" + readerUtil.getStylePath() + "',  out GenerateRootDir:" + LowCodeProperties.projectPath);
            List<File> fileList = FileUtils.getFileList(file);

            for (File tempFile : fileList) {
                this.a(var1, file, var3, tempFile, readerUtil);
            }

        }
    }

    protected void a(String var1, File var2, Map<String, Object> var3, File var4, TemplateReaderUtil templateReaderUtil) throws Exception {
        String relativePath = FileUtils.getRelativePath(var2, var4);

        try {
            if (templateReaderUtil.getStylePath() != null && !"".equals(templateReaderUtil.getStylePath()) && !relativePath.replace(File.separator, ".").startsWith(templateReaderUtil.getStylePath())) {
                return;
            }

            String var7 = a(var3, relativePath, templateReaderUtil);
            String var8;
            if (var7.startsWith("java")) {
                var8 = var1 + File.separator + LowCodeProperties.sourceRootPackage.replace(".", File.separator);
                var7 = var7.substring("java".length());
                var7 = var8 + var7;
                this.a(relativePath, var7, var3, templateReaderUtil);
            } else if (var7.startsWith("webapp")) {
                var8 = var1 + File.separator + LowCodeProperties.webRootPackage.replace(".", File.separator);
                var7 = var7.substring("webapp".length());
                var7 = var8 + var7;
                this.a(relativePath, var7, var3, templateReaderUtil);
            }
        } catch (Exception var10) {
            log.error(var10.toString(), var10);
        }

    }

    protected void a(String var1, String fileName, Map<String, Object> var3, TemplateReaderUtil var4) throws Exception {
        if (fileName.endsWith("i")) {
            fileName = fileName.substring(0, fileName.length() - 1);
        }

        boolean var5 = NonceUtils.a(fileName);
        if (fileName.contains(underLine) && !var5) {
            fileName = fileName.replace(underLine, ".");
        }

        String var6 = fileName;
        if (fileName.endsWith(".vue")) {
            var6 = fileName.substring(0, fileName.length() - 4);
        }

        if (!var6.contains("vue") || var4 == null || !g.tempC(var4.getVueStyle()) || fileName.contains(var4.getVueStyle() + File.separator)) {
            Template var7 = this.a(var1, var4);
            var7.setOutputEncoding(c);
            File var8 = FileUtils.c(fileName);
            log.info("[generate]\t template:" + var1 + " ==> " + fileName);
            b.a(var7, var3, var8, c);
            if (!this.a(var8)) {
                this.d.add("生成成功：" + fileName);
            }

            if (this.a(var8)) {
                this.a(var8, "#segment#");
            }

        }
    }

    protected Template a(String var1, TemplateReaderUtil var2) throws IOException {
        return b.a(var2.loadTemplate(), c, var1).getTemplate(var1);
    }

    protected boolean a(File var1) {
        return var1.getName().startsWith("[1-n]");
    }

    protected void a(File var1, String var2) {
        InputStreamReader var3 = null;
        BufferedReader var4 = null;
        ArrayList var5 = new ArrayList();
        boolean var20 = false;

        int var28;
        label341: {
            label342: {
                try {
                    var20 = true;
                    var3 = new InputStreamReader(new FileInputStream(var1), "UTF-8");
                    var4 = new BufferedReader(var3);
                    boolean var7 = false;
                    OutputStreamWriter var8 = null;

                    while(true) {
                        String var6;
                        while((var6 = var4.readLine()) != null) {
                            if (var6.trim().length() > 0 && var6.startsWith(var2)) {
                                String var9 = var6.substring(var2.length());
                                String var10 = var1.getParentFile().getAbsolutePath();
                                var9 = var10 + File.separator + var9;
                                log.info("[generate]\t split file:" + var1.getAbsolutePath() + " ==> " + var9);
                                var8 = new OutputStreamWriter(new FileOutputStream(var9), "UTF-8");
                                var5.add(var8);
                                this.d.add("生成成功：" + var9);
                                var7 = true;
                            } else if (var7) {
                                var8.append(var6 + "\r\n");
                            }
                        }

                        for(int var29 = 0; var29 < var5.size(); ++var29) {
                            ((Writer)var5.get(var29)).close();
                        }

                        var4.close();
                        var3.close();
                        b(var1);
                        var20 = false;
                        break label341;
                    }
                } catch (FileNotFoundException var25) {
                    var25.printStackTrace();
                    var20 = false;
                    break label342;
                } catch (IOException var26) {
                    var26.printStackTrace();
                    var20 = false;
                } finally {
                    if (var20) {
                        try {
                            if (var4 != null) {
                                var4.close();
                            }

                            if (var3 != null) {
                                var3.close();
                            }

                            if (var5.size() > 0) {
                                for(int var12 = 0; var12 < var5.size(); ++var12) {
                                    if (var5.get(var12) != null) {
                                        ((Writer)var5.get(var12)).close();
                                    }
                                }
                            }
                        } catch (IOException var21) {
                            var21.printStackTrace();
                        }

                    }
                }

                try {
                    if (var4 != null) {
                        var4.close();
                    }

                    if (var3 != null) {
                        var3.close();
                    }

                    if (var5.size() > 0) {
                        for(var28 = 0; var28 < var5.size(); ++var28) {
                            if (var5.get(var28) != null) {
                                ((Writer)var5.get(var28)).close();
                            }
                        }
                    }
                } catch (IOException var22) {
                    var22.printStackTrace();
                }

                return;
            }

            try {
                if (var4 != null) {
                    var4.close();
                }

                if (var3 != null) {
                    var3.close();
                }

                if (var5.size() > 0) {
                    for(var28 = 0; var28 < var5.size(); ++var28) {
                        if (var5.get(var28) != null) {
                            ((Writer)var5.get(var28)).close();
                        }
                    }
                }
            } catch (IOException var23) {
                var23.printStackTrace();
            }

            return;
        }

        try {
            if (var4 != null) {
                var4.close();
            }

            if (var3 != null) {
                var3.close();
            }

            if (var5.size() > 0) {
                for(var28 = 0; var28 < var5.size(); ++var28) {
                    if (var5.get(var28) != null) {
                        ((Writer)var5.get(var28)).close();
                    }
                }
            }
        } catch (IOException var24) {
            var24.printStackTrace();
        }

    }

    protected static String a(Map<String, Object> var0, String var1, TemplateReaderUtil readerUtil) throws Exception {
        String var3 = var1;
        boolean var4 = true;
        int var9;
        if ((var9 = var1.indexOf(64)) != -1) {
            var3 = var1.substring(0, var9);
            String var5 = var1.substring(var9 + 1);
            Object var6 = var0.get(var5);
            if (var6 == null) {
                System.err.println("[not-generate] WARN: test expression is null by key:[" + var5 + "] on template:[" + var1 + "]");
                return null;
            }

            if (!"true".equals(String.valueOf(var6))) {
                log.error("[not-generate]\t test expression '@" + var5 + "' is false,template:" + var1);
                return null;
            }
        }

        Configuration var10 = b.a(readerUtil.loadTemplate(), c, "/");
        var3 = b.a(var3, var0, var10);
        String var11 = readerUtil.getStylePath();
        if (var11 != null && var11 != "") {
            var3 = var3.substring(var11.length() + 1);
        }

        String var7 = var3.substring(var3.lastIndexOf("."));
        String var8 = var3.substring(0, var3.lastIndexOf(".")).replace(".", File.separator);
        var3 = var8 + var7;
        return var3;
    }

    protected static boolean b(File var0) {
        boolean var1 = false;

        for(int var2 = 0; !var1 && var2++ < 10; var1 = var0.delete()) {
            System.gc();
        }

        return var1;
    }

    protected static String a(String var0, String var1) {
        boolean var2 = true;
        boolean var3 = true;

        do {
            int var4 = var0.indexOf(var1) == 0 ? 1 : 0;
            int var5 = var0.lastIndexOf(var1) + 1 == var0.length() ? var0.lastIndexOf(var1) : var0.length();
            var0 = var0.substring(var4, var5);
            var2 = var0.indexOf(var1) == 0;
            var3 = var0.lastIndexOf(var1) + 1 == var0.length();
        } while(var2 || var3);

        return var0;
    }
}
