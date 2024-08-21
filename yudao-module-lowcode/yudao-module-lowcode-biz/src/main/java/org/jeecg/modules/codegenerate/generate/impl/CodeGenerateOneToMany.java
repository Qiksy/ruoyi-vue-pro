//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.codegenerate.generate.impl;

import java.util.ArrayList;
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
import org.jeecg.modules.codegenerate.generate.pojo.onetomany.MainTableVo;
import org.jeecg.modules.codegenerate.generate.pojo.onetomany.SubTableVo;
import org.jeecg.modules.codegenerate.generate.util.NonceUtils;
import org.jeecg.modules.codegenerate.generate.util.g;
import org.jeecg.common.config.LowCodeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeGenerateOneToMany extends DefaultGenerate implements IGenerate {
    private static final Logger e = LoggerFactory.getLogger(CodeGenerateOneToMany.class);
    private static String f;
    public static String a = "A";
    public static String b = "B";
    private MainTableVo mainTableVo;
    private List<ColumnVo> mainColums;
    private List<ColumnVo> originalMainColumns;
    private List<SubTableVo> subTableVos;
    private static DbReadTableUtil k = new DbReadTableUtil();

    public CodeGenerateOneToMany(MainTableVo mainTableVo, List<SubTableVo> subTables) {
        this.subTableVos = subTables;
        this.mainTableVo = mainTableVo;
    }

    public CodeGenerateOneToMany(MainTableVo mainTableVo, List<ColumnVo> mainColums, List<ColumnVo> originalMainColumns, List<SubTableVo> subTables) {
        this.mainTableVo = mainTableVo;
        this.mainColums = mainColums;
        this.originalMainColumns = originalMainColumns;
        this.subTableVos = subTables;
    }

    public Map<String, Object> a() throws Exception {
        HashMap var1 = new HashMap();
        var1.put("bussiPackage", LowCodeProperties.bussiPackageName);
        var1.put("entityPackage", this.mainTableVo.getEntityPackage());
        var1.put("entityName", this.mainTableVo.getEntityName());
        var1.put("tableName", this.mainTableVo.getTableName());
        var1.put("ftl_description", this.mainTableVo.getFtlDescription());
        var1.put("primaryKeyField", LowCodeProperties.tableId);
        if (this.mainTableVo.getFieldRequiredNum() == null) {
            this.mainTableVo.setFieldRequiredNum(StringUtils.isNotEmpty(LowCodeProperties.fieldRequiredNum) ? Integer.parseInt(LowCodeProperties.fieldRequiredNum) : -1);
        }

        if (this.mainTableVo.getSearchFieldNum() == null) {
            this.mainTableVo.setSearchFieldNum(StringUtils.isNotEmpty(LowCodeProperties.pageSearchFiledNum) ? Integer.parseInt(LowCodeProperties.pageSearchFiledNum) : -1);
        }

        if (this.mainTableVo.getFieldRowNum() == null) {
            this.mainTableVo.setFieldRowNum(Integer.parseInt(LowCodeProperties.fieldRowNum));
        }

        var1.put("tableVo", this.mainTableVo);

        try {
            if (this.mainColums == null || this.mainColums.size() == 0) {
                this.mainColums = DbReadTableUtil.readColumnByTableName(this.mainTableVo.getTableName());
            }

            if (this.originalMainColumns == null || this.originalMainColumns.size() == 0) {
                this.originalMainColumns = DbReadTableUtil.readOriginalTableColumn(this.mainTableVo.getTableName());
            }

            var1.put("columns", this.mainColums);
            var1.put("originalColumns", this.originalMainColumns);
            Iterator var2 = this.originalMainColumns.iterator();

            while(var2.hasNext()) {
                ColumnVo var3 = (ColumnVo)var2.next();
                if (var3.getFieldName().toLowerCase().equals(LowCodeProperties.tableId.toLowerCase())) {
                    var1.put("primaryKeyPolicy", var3.getFieldType());
                }
            }

            var2 = this.subTableVos.iterator();

            while(var2.hasNext()) {
                SubTableVo var12 = (SubTableVo)var2.next();
                List<ColumnVo> var4;
                if (var12.getColums() == null || var12.getColums().isEmpty()) {
                    var4 = DbReadTableUtil.readColumnByTableName(var12.getTableName());
                    var12.setColums(var4);
                }

                if (var12.getOriginalColumns() == null || var12.getOriginalColumns().isEmpty()) {
                    var4 = DbReadTableUtil.readOriginalTableColumn(var12.getTableName());
                    var12.setOriginalColumns(var4);
                }

                String[] var13 = var12.getForeignKeys();
                ArrayList var5 = new ArrayList();
                String[] var6 = var13;
                int var7 = var13.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    String var9 = var6[var8];
                    var5.add(DbReadTableUtil.d(var9));
                }

                var12.setForeignKeys((String[])var5.toArray(new String[0]));
                var12.setOriginalForeignKeys(var13);
            }

            var1.put("subTables", this.subTableVos);
        } catch (Exception var10) {
            throw var10;
        }

        long var11 = NonceUtils.c() + NonceUtils.h();
        var1.put("serialVersionUID", String.valueOf(var11));
        e.info("code template data: " + var1.toString());
        return var1;
    }

    public List<String> generateCodeFile(String stylePath) throws Exception {
        String var2 = LowCodeProperties.projectPath;
        Map var3 = this.a();
        String var4 = LowCodeProperties.templatePath;
        if (a(var4, "/").equals("jeecg/code-template")) {
            var4 = "/" + a(var4, "/") + "/onetomany";
            LowCodeProperties.setTemplatePath(var4);
        }

        TemplateReaderUtil var5 = new TemplateReaderUtil(var4);
        var5.setStylePath(stylePath);
        if (this.mainTableVo != null && this.mainTableVo.getExtendParams() != null) {
            var5.setVueStyle(g.trimWithDefault(this.mainTableVo.getExtendParams().get("vueStyle"), "vue"));
        }

        this.a(var5, var2, var3);
        e.info("----- jeecg-boot ---- generate  code  success =======> 主表名：" + this.mainTableVo.getTableName());
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
}
