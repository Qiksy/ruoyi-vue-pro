//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.codegenerate.generate.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
    private static final Logger c = LoggerFactory.getLogger(FileUtils.class);
    public static List<String> staticFileList = new ArrayList<>();
    public static List<String> tempalteList = new ArrayList<>();


    public static List<File> getFileList(File file) throws IOException {
        ArrayList<File> fileList = new ArrayList<>();
        getFileList(file, fileList);
        fileList.sort(Comparator.comparing(File::getAbsolutePath));
        return fileList;
    }

    public static void getFileList(File file, List<File> fileList) throws IOException {
        if (!file.isHidden() && file.isDirectory() && !isIgnoreFile(file)) {
            File[] list = file.listFiles();

            if (list != null) {
                for (File value : list) {
                    getFileList(value, fileList);
                }
            }
        } else if (!isTemplateFile(file) && !isIgnoreFile(file)) {
            fileList.add(file);
        }

    }

    public static String a(File var0, File var1) {
        if (var0.equals(var1)) {
            return "";
        } else {
            return var0.getParentFile() == null ? var1.getAbsolutePath().substring(var0.getAbsolutePath().length()) : var1.getAbsolutePath().substring(var0.getAbsolutePath().length() + 1);
        }
    }

    public static boolean b(File var0) {
        return var0.isDirectory() ? false : a(var0.getName());
    }

    public static boolean a(String var0) {
        return !StringUtils.isBlank(b(var0));
    }

    public static String b(String var0) {
        if (var0 == null) {
            return null;
        } else {
            int var1 = var0.indexOf(".");
            return var1 == -1 ? "" : var0.substring(var1 + 1);
        }
    }

    public static File c(String var0) {
        if (var0 == null) {
            throw new IllegalArgumentException("file must be not null");
        } else {
            File var1 = new File(var0);
            c(var1);
            return var1;
        }
    }

    public static void c(File file) {
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

    }

    private static boolean isIgnoreFile(File file) {
        for (String string : staticFileList) {
            if (file.getName().equals(string)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isTemplateFile(File file) {
        for (String string : tempalteList) {
            if (file.getName().endsWith(string)) {
                return true;
            }
        }

        return false;
    }

    static {
        staticFileList.add(".svn");
        staticFileList.add("CVS");
        staticFileList.add(".cvsignore");
        staticFileList.add(".copyarea.db");
        staticFileList.add("SCCS");
        staticFileList.add("vssver.scc");
        staticFileList.add(".DS_Store");
        staticFileList.add(".git");
        staticFileList.add(".gitignore");
        tempalteList.add(".ftl");
    }
}
