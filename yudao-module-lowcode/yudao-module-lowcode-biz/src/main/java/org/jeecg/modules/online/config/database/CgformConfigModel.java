package org.jeecg.modules.online.config.database;

import java.util.List;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

/* compiled from: CgformConfigModel.java */
/* renamed from: org.jeecg.modules.online.config.b.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/b/a.class */
public class CgformConfigModel {

    /* renamed from: a */
    private String tableName;

    /* renamed from: b */
    private String isDbSynch;

    /* renamed from: c */
    private String content;

    /* renamed from: d */
    private String jformVersion;

    /* renamed from: e */
    private Integer jformType;

    /* renamed from: f */
    private String jformPkType;

    /* renamed from: g */
    private String jformPkSequence;

    /* renamed from: h */
    private Integer relationType;

    /* renamed from: i */
    private String subTableStr;

    /* renamed from: j */
    private Integer tabOrder;

    /* renamed from: k */
    private List<OnlCgformField> columns;

    /* renamed from: l */
    private List<OnlCgformIndex> indexes;

    /* renamed from: m */
    private String treeParentIdFieldName;

    /* renamed from: n */
    private String treeIdFieldname;

    /* renamed from: o */
    private String treeFieldname;

    /* renamed from: p */
    private DataBaseConfig dbConfig;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIsDbSynch() {
        return this.isDbSynch;
    }

    public void setIsDbSynch(String isDbSynch) {
        this.isDbSynch = isDbSynch;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getJformVersion() {
        return this.jformVersion;
    }

    public void setJformVersion(String jformVersion) {
        this.jformVersion = jformVersion;
    }

    public Integer getJformType() {
        return this.jformType;
    }

    public void setJformType(Integer jformType) {
        this.jformType = jformType;
    }

    public String getJformPkType() {
        return this.jformPkType;
    }

    public void setJformPkType(String jformPkType) {
        this.jformPkType = jformPkType;
    }

    public String getJformPkSequence() {
        return this.jformPkSequence;
    }

    public void setJformPkSequence(String jformPkSequence) {
        this.jformPkSequence = jformPkSequence;
    }

    public Integer getRelationType() {
        return this.relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public String getSubTableStr() {
        return this.subTableStr;
    }

    public void setSubTableStr(String subTableStr) {
        this.subTableStr = subTableStr;
    }

    public Integer getTabOrder() {
        return this.tabOrder;
    }

    public void setTabOrder(Integer tabOrder) {
        this.tabOrder = tabOrder;
    }

    public List<OnlCgformField> getColumns() {
        return this.columns;
    }

    public void setColumns(List<OnlCgformField> columns) {
        this.columns = columns;
    }

    public List<OnlCgformIndex> getIndexes() {
        return this.indexes;
    }

    public void setIndexes(List<OnlCgformIndex> indexes) {
        this.indexes = indexes;
    }

    public String getTreeParentIdFieldName() {
        return this.treeParentIdFieldName;
    }

    public void setTreeParentIdFieldName(String treeParentIdFieldName) {
        this.treeParentIdFieldName = treeParentIdFieldName;
    }

    public String getTreeIdFieldname() {
        return this.treeIdFieldname;
    }

    public void setTreeIdFieldname(String treeIdFieldname) {
        this.treeIdFieldname = treeIdFieldname;
    }

    public String getTreeFieldname() {
        return this.treeFieldname;
    }

    public void setTreeFieldname(String treeFieldname) {
        this.treeFieldname = treeFieldname;
    }

    public DataBaseConfig getDbConfig() {
        return this.dbConfig;
    }

    public void setDbConfig(DataBaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }
}
