package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("onl_cgform_button")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/entity/OnlCgformButton.class */
@Data
public class OnlCgformButton implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: id */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String cgformHeadId;
    private String buttonCode;
    private String buttonName;
    private String buttonStyle;
    private String optType;
    private String exp;
    private String buttonStatus;
    private Integer orderNum;
    private String buttonIcon;
    private String optPosition;


    public String toString() {
        return "OnlCgformButton(id=" + getId() + ", cgformHeadId=" + getCgformHeadId() + ", buttonCode=" + getButtonCode() + ", buttonName=" + getButtonName() + ", buttonStyle=" + getButtonStyle() + ", optType=" + getOptType() + ", exp=" + getExp() + ", buttonStatus=" + getButtonStatus() + ", orderNum=" + getOrderNum() + ", buttonIcon=" + getButtonIcon() + ", optPosition=" + getOptPosition() + ")";
    }
}
