package org.jeecg.modules.codegenerate.generate.pojo;

import lombok.Data;

import java.util.Map;


@Data
public class TableVo {
    private String tableName;
    private String ftlDescription;
    private String primaryKeyPolicy;
    private String sequenceCode;
    private String entityPackage;
    private String entityName;
    private String smallEntityName;
    private Integer fieldRowNum;
    private Integer searchFieldNum;
    private Integer fieldRequiredNum;
    private Map<?, ?> extendParams;


    public String toString() {
        return "{\"tableName\":\"" + this.tableName + "\",\"ftlDescription\":\"" + this.ftlDescription + "\",\"primaryKeyPolicy\":\"" + this.primaryKeyPolicy + "\",\"sequenceCode\":\"" + this.sequenceCode + "\",\"entityPackage\":\"" + this.entityPackage + "\",\"entityName\":\"" + this.entityName + "\",\"fieldRowNum\":\"" + this.fieldRowNum + "\",\"searchFieldNum\":\"" + this.searchFieldNum + "\",\"fieldRequiredNum\":\"" + this.fieldRequiredNum + "\"}";
    }
}
