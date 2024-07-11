package org.jeecg.modules.online.config.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/* compiled from: DmDataBaseConfig.java */
@ConfigurationProperties(prefix = "spring.datasource.druid")
@Component("dmDataBaseConfig")
/* renamed from: org.jeecg.modules.online.config.b.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/config/b/c.class */
public class dmDataBaseConfig {

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
        return this.druid;
    }

    public void setDruid(Druid druid) {
        this.druid = druid;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
