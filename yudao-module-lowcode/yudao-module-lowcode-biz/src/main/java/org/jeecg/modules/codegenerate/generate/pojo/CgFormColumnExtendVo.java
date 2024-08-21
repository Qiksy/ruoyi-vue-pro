package org.jeecg.modules.codegenerate.generate.pojo;

import lombok.Data;

import java.util.Map;


@Data
public class CgFormColumnExtendVo {
    protected Integer fieldLength;
    protected String fieldHref;
    protected String fieldValidType;
    protected String fieldDefault;
    protected String fieldShowType;
    protected Integer fieldOrderNum;
    protected String isKey;
    protected String isShow;
    protected String isShowList;
    protected String isQuery;
    protected String queryMode;
    protected String dictField;
    protected String dictTable;
    protected String dictText;
    protected String sort = "N";
    protected String readonly = "N";
    protected String defaultVal;
    protected String uploadnum;
    protected Map<?, ?> extendParams;


    public String toString() {
        return "{}";
    }
}
