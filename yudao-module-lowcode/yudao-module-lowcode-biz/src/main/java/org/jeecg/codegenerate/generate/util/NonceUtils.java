//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.codegenerate.generate.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;

public class NonceUtils {
    private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final String[] b = new String[]{"0", "00", "0000", "00000000"};
    private static Date c;
    private static int d = 0;

    public NonceUtils() {
    }

    public static String a(int var0) {
        return RandomStringUtils.randomAlphanumeric(var0);
    }

    public static int a() {
        return (new SecureRandom()).nextInt();
    }

    public static String b() {
        return Integer.toHexString(a());
    }

    public static long c() {
        return (new SecureRandom()).nextLong();
    }

    public static String d() {
        return Long.toHexString(c());
    }

    public static String e() {
        Date var0 = new Date();
        SimpleDateFormat var1 = new SimpleDateFormat("yyyyMMdd");
        String var2 = var1.format(var0);
        return var2;
    }

    public static String f() {
        return UUID.randomUUID().toString();
    }

    public static String g() {
        Date var0 = new Date();
        return a.format(var0);
    }

    public static long h() {
        return System.currentTimeMillis();
    }

    public static String i() {
        return Long.toHexString(h());
    }

    public static synchronized String j() {
        Date var0 = new Date();
        if (var0.equals(c)) {
            ++d;
        } else {
            c = var0;
            d = 0;
        }

        return Integer.toHexString(d);
    }

    public static String a(String var0, int var1) {
        int var2 = var1 - var0.length();

        StringBuilder var3;
        for(var3 = new StringBuilder(); var2 >= 8; var2 -= 8) {
            var3.append(b[3]);
        }

        for(int var4 = 2; var4 >= 0; --var4) {
            if ((var2 & 1 << var4) != 0) {
                var3.append(b[var4]);
            }
        }

        var3.append(var0);
        return var3.toString();
    }

    public static boolean a(String var0) {
        int var1 = var0.lastIndexOf("/");
        int var2 = var0.lastIndexOf("\\");
        if (var1 < var2) {
            var1 = var2;
        }

        String var3 = var0.substring(var1 + 1);
        String var4 = "V\\d{8}_\\d{1,2}__.*\\.sql";
        boolean var5 = var3.matches(var4);
        return var5;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(c() + h());
    }
}
