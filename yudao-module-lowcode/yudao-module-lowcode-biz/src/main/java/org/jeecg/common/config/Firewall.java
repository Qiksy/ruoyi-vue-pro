package org.jeecg.common.config;

public class Firewall {
    /**
     * 数据源安全 (开启后，Online报表和图表的数据源为必填)
     */
    private Boolean dataSourceSafe = false;
    /**
     * 低代码模式（dev:开发模式，prod:发布模式——关闭所有在线开发配置能力）
     */
    private String lowCodeMode;


    public Boolean getDataSourceSafe() {
        return dataSourceSafe;
    }

    public void setDataSourceSafe(Boolean dataSourceSafe) {
        this.dataSourceSafe = dataSourceSafe;
    }

    public String getLowCodeMode() {
        return lowCodeMode;
    }

    public void setLowCodeMode(String lowCodeMode) {
        this.lowCodeMode = lowCodeMode;
    }

}