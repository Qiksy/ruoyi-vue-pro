//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.codegenerate.generate.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.codegenerate.DbReadTableUtil;
import org.jeecg.codegenerate.generate.IGenerate;
import org.jeecg.codegenerate.generate.pojo.ColumnVo;
import org.jeecg.codegenerate.generate.pojo.TableVo;
import org.jeecg.codegenerate.generate.util.NonceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeGenerateOne extends a implements IGenerate {
    private static final Logger a = LoggerFactory.getLogger(CodeGenerateOne.class);
    private TableVo b;
    private List<ColumnVo> e;
    private List<ColumnVo> f;

    public CodeGenerateOne(TableVo tableVo) {
        this.b = tableVo;
    }

    public CodeGenerateOne(TableVo tableVo, List<ColumnVo> columns, List<ColumnVo> originalColumns) {
        this.b = tableVo;
        this.e = columns;
        this.f = originalColumns;
    }

    public Map<String, Object> a() throws Exception {
        HashMap var1 = new HashMap();
        var1.put("bussiPackage", org.jeecgframework.codegenerate.a.a.g);
        var1.put("entityPackage", this.b.getEntityPackage());
        var1.put("entityName", this.b.getEntityName());
        var1.put("tableName", this.b.getTableName());
        var1.put("primaryKeyField", org.jeecgframework.codegenerate.a.a.l);
        if (this.b.getFieldRequiredNum() == null) {
            this.b.setFieldRequiredNum(StringUtils.isNotEmpty(org.jeecgframework.codegenerate.a.a.m) ? Integer.parseInt(org.jeecgframework.codegenerate.a.a.m) : -1);
        }

        if (this.b.getSearchFieldNum() == null) {
            this.b.setSearchFieldNum(StringUtils.isNotEmpty(org.jeecgframework.codegenerate.a.a.n) ? Integer.parseInt(org.jeecgframework.codegenerate.a.a.n) : -1);
        }

        if (this.b.getFieldRowNum() == null) {
            this.b.setFieldRowNum(Integer.parseInt(org.jeecgframework.codegenerate.a.a.p));
        }

        var1.put("tableVo", this.b);

        try {
            if (this.e == null || this.e.size() == 0) {
                this.e = DbReadTableUtil.a(this.b.getTableName());
            }

            var1.put("columns", this.e);
            if (this.f == null || this.f.size() == 0) {
                this.f = DbReadTableUtil.readOriginalTableColumn(this.b.getTableName());
            }

            var1.put("originalColumns", this.f);
            Iterator var2 = this.f.iterator();

            while(var2.hasNext()) {
                ColumnVo var3 = (ColumnVo)var2.next();
                if (var3.getFieldName().toLowerCase().equals(org.jeecgframework.codegenerate.a.a.l.toLowerCase())) {
                    var1.put("primaryKeyPolicy", var3.getFieldType());
                }
            }
        } catch (Exception var4) {
            throw var4;
        }

        long var5 = NonceUtils.c() + NonceUtils.h();
        var1.put("serialVersionUID", String.valueOf(var5));
        a.info("load template data: " + var1.toString());
        return var1;
    }

    public List<String> generateCodeFile(String stylePath) throws Exception {
        String var2 = org.jeecgframework.codegenerate.a.a.f;
        Map var3 = this.a();
        String var4 = org.jeecgframework.codegenerate.a.a.j;
        if (a(var4, "/").equals("jeecg/code-template")) {
            var4 = "/" + a(var4, "/") + "/one";
            org.jeecgframework.codegenerate.a.a.b(var4);
        }

        org.jeecgframework.codegenerate.generate.a.a var5 = new org.jeecgframework.codegenerate.generate.a.a(var4);
        var5.b(stylePath);
        if (this.b != null && this.b.getExtendParams() != null) {
            var5.a(g.a(this.b.getExtendParams().get("vueStyle"), "vue"));
        }

        this.a(var5, var2, var3);
        a.info(" ----- jeecg-boot ---- generate  code  success =======> 表名：" + this.b.getTableName() + " ");
        return this.d;
    }

    public List<String> generateCodeFile(String projectPath, String templatePath, String stylePath) throws Exception {
        if (projectPath != null && !"".equals(projectPath)) {
            org.jeecgframework.codegenerate.a.a.a(projectPath);
        }

        if (templatePath != null && !"".equals(templatePath)) {
            org.jeecgframework.codegenerate.a.a.b(templatePath);
        }

        this.generateCodeFile(stylePath);
        return this.d;
    }

    public static void main(String[] args) {
        TableVo var1 = new TableVo();
        var1.setTableName("demo");
        var1.setPrimaryKeyPolicy("uuid");
        var1.setEntityPackage("test");
        var1.setEntityName("JeecgDemo");
        var1.setFtlDescription("jeecg 测试demo");

        try {
            (new CodeGenerateOne(var1)).generateCodeFile((String)null);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
}
