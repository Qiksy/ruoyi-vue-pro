package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import org.jeecg.modules.online.cgform.constant.OnlineConst;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_cgform_field")
@Data
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/entity/OnlCgformField.class */
public class OnlCgformField implements Serializable {
    private static final long serialVersionUID = 1;

    /////================基础属性=====================/////
    /**
     * 属性id
     */
    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 表id
     */
    private String cgformHeadId;
    ////========数据库属性=======////
    /**
     * 字段名
     */
    private String dbFieldName;
    /**
     * 备注
     */
    private String dbFieldTxt;
    /**
     * 原字段名
     */
    private String dbFieldNameOld;
    /**
     * 数据库字段类型
     */
    private String dbType;
    /**
     * 数据库字段长度
     */
    private Integer dbLength;
    /**
     * 小数点
     */
    private Integer dbPointLength;
    /**
     * 默认值
     */
    private String dbDefaultVal;
    /**
     * 是否主键 0否 1是
     */
    private Integer dbIsKey;
    /**
     * 是否为空 0否 1是
     */
    private Integer dbIsNull;

    /**
     * 是否需要同步数据库字段， 1是0否
     */
    private Integer dbIsPersist = OnlineConst.isPersist;


    ///==============页面属性====================///
    /**
     * 表单是否显示
     */
    private Integer isShowForm;
    /**
     * 列表是否显示
     */
    private Integer isShowList;
    /**
     * 是否只读
     */
    private Integer isReadOnly;
    /**
     * 排序字段
     */
    private String sortFlag;
    /**
     * 表单控件类型
     */
    private String fieldShowType;
    /**
     * 表单控件长度
     */
    private Integer fieldLength;

    /**
     * 是否查询条件0否 1是
     */
    private Integer isQuery;



    /**
     * 字典code
     */
    private String dictField;
    /**
     * 字典表
     */
    private String dictTable;
    /**
     * 字典文本
     */
    private String dictText;

    /**
     * 跳转链接
     */
    private String fieldHref;

    /**
     * 表单校验规则
     */
    private String fieldValidType;
    /**
     * 表单必填 0否 1是
     */
    private String fieldMustInput;
    /**
     * 扩展参数json
     */
    private String fieldExtendJson;
    /**
     * 控件默认值，不同的表达式展示不同的结果。
     * 1. 纯字符串直接赋给默认值；
     * 2. #{普通变量}；
     * 3. {{ 动态JS表达式 }}；
     * 4. ${填值规则编码}；
     * 填值规则表达式只允许存在一个，且不能和其他规则混用。
     */
    private String fieldDefaultValue;


    /**
     * 查询模式
     */
    private String queryMode;
    /**
     * 外键主表名
     */
    private String mainTable;
    /**
     * 外键主键字段
     */
    private String mainField;
    /**
     * 排序
     */
    private Integer orderNum;
    private String updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String createBy;
    /**
     * 自定义值转换器
     */
    private String converter;
    /**
     * 是否启用查询配置1是0否
     */
    private String queryConfigFlag;
    /**
     * 查询默认值
     */
    private String queryDefVal;
    /**
     * 查询配置字典text
     */
    private String queryDictText;
    /**
     * 查询配置字典code
     */
    private String queryDictField;
    /**
     * 查询配置字典table
     */
    private String queryDictTable;
    /**
     * 查询显示控件
     */
    private String queryShowType;
    /**
     * 查询字段校验类型
     */
    private String queryValidType;
    /**
     * 查询字段是否必填1是0否
     */
    private String queryMustInput;

    /**
     * 别名
     */
    private transient String alias;


    public String toString() {
        return "OnlCgformField(id=" + getId() + ", cgformHeadId=" + getCgformHeadId() + ", dbFieldName=" + getDbFieldName() + ", dbFieldTxt=" + getDbFieldTxt() + ", dbFieldNameOld=" + getDbFieldNameOld() + ", dbIsKey=" + getDbIsKey() + ", dbIsNull=" + getDbIsNull() + ", dbIsPersist=" + getDbIsPersist() + ", dbType=" + getDbType() + ", dbLength=" + getDbLength() + ", dbPointLength=" + getDbPointLength() + ", dbDefaultVal=" + getDbDefaultVal() + ", dictField=" + getDictField() + ", dictTable=" + getDictTable() + ", dictText=" + getDictText() + ", fieldShowType=" + getFieldShowType() + ", fieldHref=" + getFieldHref() + ", fieldLength=" + getFieldLength() + ", fieldValidType=" + getFieldValidType() + ", fieldMustInput=" + getFieldMustInput() + ", fieldExtendJson=" + getFieldExtendJson() + ", fieldDefaultValue=" + getFieldDefaultValue() + ", isQuery=" + getIsQuery() + ", isShowForm=" + getIsShowForm() + ", isShowList=" + getIsShowList() + ", isReadOnly=" + getIsReadOnly() + ", queryMode=" + getQueryMode() + ", mainTable=" + getMainTable() + ", mainField=" + getMainField() + ", orderNum=" + getOrderNum() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ", createTime=" + getCreateTime() + ", createBy=" + getCreateBy() + ", converter=" + getConverter() + ", queryConfigFlag=" + getQueryConfigFlag() + ", queryDefVal=" + getQueryDefVal() + ", queryDictText=" + getQueryDictText() + ", queryDictField=" + getQueryDictField() + ", queryDictTable=" + getQueryDictTable() + ", queryShowType=" + getQueryShowType() + ", queryValidType=" + getQueryValidType() + ", queryMustInput=" + getQueryMustInput() + ", sortFlag=" + getSortFlag() + ", alias=" + getAlias() + ")";
    }

}
