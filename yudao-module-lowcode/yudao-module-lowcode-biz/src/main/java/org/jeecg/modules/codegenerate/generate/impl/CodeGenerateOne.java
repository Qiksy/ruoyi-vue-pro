//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.codegenerate.generate.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.codegenerate.DbReadTableUtil;
import org.jeecg.modules.codegenerate.generate.IGenerate;
import org.jeecg.modules.codegenerate.generate.a.TemplateReaderUtil;
import org.jeecg.modules.codegenerate.generate.impl.defaultGenerate.DefaultGenerate;
import org.jeecg.modules.codegenerate.generate.pojo.ColumnVo;
import org.jeecg.modules.codegenerate.generate.pojo.TableVo;
import org.jeecg.modules.codegenerate.generate.util.NonceUtils;
import org.jeecg.modules.codegenerate.generate.util.g;
import org.jeecg.common.config.LowCodeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeGenerateOne extends DefaultGenerate implements IGenerate {
    private static final Logger a = LoggerFactory.getLogger(CodeGenerateOne.class);
    private TableVo tableVo;
    private List<ColumnVo> e;
    private List<ColumnVo> f;

    public CodeGenerateOne(TableVo tableVo) {
        this.tableVo = tableVo;
    }

    public CodeGenerateOne(TableVo tableVo, List<ColumnVo> columns, List<ColumnVo> originalColumns) {
        this.tableVo = tableVo;
        this.e = columns;
        this.f = originalColumns;
    }

    public Map<String, Object> a() throws Exception {
        HashMap var1 = new HashMap();
        var1.put("bussiPackage", LowCodeProperties.bussiPackageName);
        var1.put("entityPackage", this.tableVo.getEntityPackage());
        var1.put("entityName", this.tableVo.getEntityName());
        var1.put("tableName", this.tableVo.getTableName());
        var1.put("primaryKeyField", LowCodeProperties.tableId);
        if (this.tableVo.getFieldRequiredNum() == null) {
            this.tableVo.setFieldRequiredNum(StringUtils.isNotEmpty(LowCodeProperties.fieldRequiredNum) ? Integer.parseInt(LowCodeProperties.fieldRequiredNum) : -1);
        }

        if (this.tableVo.getSearchFieldNum() == null) {
            this.tableVo.setSearchFieldNum(StringUtils.isNotEmpty(LowCodeProperties.pageSearchFiledNum) ? Integer.parseInt(LowCodeProperties.pageSearchFiledNum) : -1);
        }

        if (this.tableVo.getFieldRowNum() == null) {
            this.tableVo.setFieldRowNum(Integer.parseInt(LowCodeProperties.fieldRowNum));
        }

        var1.put("tableVo", this.tableVo);

        try {
            if (this.e == null || this.e.size() == 0) {
                this.e = DbReadTableUtil.readColumnByTableName(this.tableVo.getTableName());
            }

            var1.put("columns", this.e);
            if (this.f == null || this.f.size() == 0) {
                this.f = DbReadTableUtil.readOriginalTableColumn(this.tableVo.getTableName());
            }

            var1.put("originalColumns", this.f);
            Iterator var2 = this.f.iterator();

            while(var2.hasNext()) {
                ColumnVo var3 = (ColumnVo)var2.next();
                if (var3.getFieldName().toLowerCase().equals(LowCodeProperties.tableId.toLowerCase())) {
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
        String var2 = LowCodeProperties.projectPath;
        Map var3 = this.a();
        String var4 = LowCodeProperties.templatePath;
        if (a(var4, "/").equals("jeecg/code-template")) {
            var4 = "/" + a(var4, "/") + "/one";
            LowCodeProperties.setTemplatePath(var4);
        }

        TemplateReaderUtil var5 = new TemplateReaderUtil(var4);
        var5.setStylePath(stylePath);
        if (this.tableVo != null && this.tableVo.getExtendParams() != null) {
            var5.setVueStyle(g.trimWithDefault(this.tableVo.getExtendParams().get("vueStyle"), "vue"));
        }

        this.a(var5, var2, var3);
        a.info(" ----- jeecg-boot ---- generate  code  success =======> 表名：" + this.tableVo.getTableName() + " ");
        return this.d;
    }

    public List<String> generateCodeFile(String projectPath, String templatePath, String stylePath) throws Exception {
        if (projectPath != null && !"".equals(projectPath)) {
            LowCodeProperties.setProjectPath(projectPath);
        }

        if (templatePath != null && !"".equals(templatePath)) {
            LowCodeProperties.setTemplatePath(templatePath);
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
