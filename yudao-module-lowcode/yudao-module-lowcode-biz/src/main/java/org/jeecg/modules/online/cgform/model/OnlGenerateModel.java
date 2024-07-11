package org.jeecg.modules.online.cgform.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: OnlGenerateModel.java */
/* renamed from: org.jeecg.modules.online.cgform.model.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/d.class */
public class OnlGenerateModel implements Serializable {

    /* renamed from: b */
    private static final long f378b = 684098897071177558L;

    /* renamed from: c */
    private String code;

    /* renamed from: d */
    private String projectPath;

    /* renamed from: e */
    private String packageStyle;

    /* renamed from: f */
    private String ftlDescription;

    /* renamed from: g */
    private String jformType;

    /* renamed from: h */
    private String tableName;

    /* renamed from: i */
    private String entityPackage;

    /* renamed from: j */
    private String entityName;

    /* renamed from: k */
    private String jspMode;

    /* renamed from: l */
    private String valueStyle;

    /* renamed from: a */
    List<OnlGenerateModel> subList = new ArrayList();

    public String getVueStyle() {
        return this.valueStyle;
    }

    public void setVueStyle(String vueStyle) {
        this.valueStyle = vueStyle;
    }

    public String getProjectPath() {
        return this.projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getPackageStyle() {
        return this.packageStyle;
    }

    public void setPackageStyle(String packageStyle) {
        this.packageStyle = packageStyle;
    }

    public String getFtlDescription() {
        return this.ftlDescription;
    }

    public void setFtlDescription(String ftlDescription) {
        this.ftlDescription = ftlDescription;
    }

    public String getJformType() {
        return this.jformType;
    }

    public void setJformType(String jformType) {
        this.jformType = jformType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntityPackage() {
        return this.entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getJspMode() {
        return this.jspMode;
    }

    public void setJspMode(String jspMode) {
        this.jspMode = jspMode;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<OnlGenerateModel> getSubList() {
        return this.subList;
    }

    public void setSubList(List<OnlGenerateModel> subList) {
        this.subList = subList;
    }
}
