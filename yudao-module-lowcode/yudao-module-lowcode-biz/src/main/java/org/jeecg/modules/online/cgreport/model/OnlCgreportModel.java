package org.jeecg.modules.online.cgreport.model;

import java.util.Arrays;
import java.util.List;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/model/OnlCgreportModel.class */
public class OnlCgreportModel {
    private OnlCgreportHead head;
    private List<OnlCgreportParam> params;
    private String deleteParamIds;
    private List<OnlCgreportItem> items;
    private String deleteItemIds;

    public OnlCgreportHead getHead() {
        return this.head;
    }

    public void setHead(OnlCgreportHead head) {
        this.head = head;
    }

    public List<OnlCgreportParam> getParams() {
        return this.params;
    }

    public void setParams(List<OnlCgreportParam> params) {
        this.params = params;
    }

    public List<OnlCgreportItem> getItems() {
        return this.items;
    }

    public void setItems(List<OnlCgreportItem> items) {
        this.items = items;
    }

    public String getDeleteParamIds() {
        return this.deleteParamIds;
    }

    public List<String> getDeleteParamIdList() {
        return Arrays.asList(this.deleteParamIds.split(CgformUtil.COMMA_SEPARATOR));
    }

    public void setDeleteParamIds(String deleteParamIds) {
        this.deleteParamIds = deleteParamIds;
    }

    public String getDeleteItemIds() {
        return this.deleteItemIds;
    }

    public List<String> getDeleteItemIdList() {
        return Arrays.asList(this.deleteItemIds.split(CgformUtil.COMMA_SEPARATOR));
    }

    public void setDeleteItemIds(String deleteItemIds) {
        this.deleteItemIds = deleteItemIds;
    }

    public String toString() {
        return "OnlCgreportModel [head=" + this.head + ", params=" + this.params + ", deleteParamIds=" + this.deleteParamIds + ", items=" + this.items + ", deleteItemIds=" + this.deleteItemIds + "]";
    }
}
