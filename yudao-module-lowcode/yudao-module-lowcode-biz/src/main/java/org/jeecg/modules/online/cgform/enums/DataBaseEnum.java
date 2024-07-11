package org.jeecg.modules.online.cgform.enums;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enums/DataBaseEnum.class */
public enum DataBaseEnum {
    MYSQL("MYSQL", "1"),
    ORACLE("ORACLE", "2"),
    SQLSERVER("SQLSERVER", "3"),
    POSTGRESQL("POSTGRESQL", "4");

    private String name;
    private String value;

    DataBaseEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static String getDataBaseNameByValue(String value) {
        for (DataBaseEnum dataBaseEnum : values()) {
            if (dataBaseEnum.value.equals(value)) {
                return dataBaseEnum.name;
            }
        }
        return MYSQL.name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
