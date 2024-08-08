package org.jeecg.modules.online.config.database;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/* compiled from: DataBaseConfig.java */
@ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
@Component("dataBaseConfig")
/* renamed from: org.jeecg.modules.online.config.b.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/b/b.class */
public class DataBaseConfig {

    @Autowired
    private org.jeecg.modules.online.config.database.dmDataBaseConfig dmDataBaseConfig;

    /* renamed from: a */
    private String url;

    /* renamed from: b */
    private String username;

    /* renamed from: c */
    private String password;

    /* renamed from: d */
    private String driverClassName;

    /* renamed from: e */
    private Druid druid;

    public Druid getDruid() {
        if (this.druid == null) {
            return this.dmDataBaseConfig.getDruid();
        }
        return this.druid;
    }

    public void setDruid(Druid druid) {
        this.druid = druid;
    }

    public String getUrl() {
        return ConvertUtils.getString(this.url, this.dmDataBaseConfig.getUrl());
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return ConvertUtils.getString(this.username, this.dmDataBaseConfig.getUsername());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return ConvertUtils.getString(this.password, this.dmDataBaseConfig.getPassword());
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return ConvertUtils.getString(this.driverClassName, this.dmDataBaseConfig.getDriverClassName());
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setDmDataBaseConfig(org.jeecg.modules.online.config.database.dmDataBaseConfig dmDataBaseConfig) {
        this.dmDataBaseConfig = dmDataBaseConfig;
    }
}
