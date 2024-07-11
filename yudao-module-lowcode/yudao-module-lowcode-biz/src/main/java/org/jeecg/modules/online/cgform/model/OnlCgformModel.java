package org.jeecg.modules.online.cgform.model;

import java.util.List;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

/* compiled from: OnlCgformModel.java */
/* renamed from: org.jeecg.modules.online.cgform.model.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/a.class */
public class OnlCgformModel {

    /* renamed from: a */
    private OnlCgformHead head;

    /* renamed from: b */
    private List<OnlCgformField> fields;

    /* renamed from: c */
    private List<String> deleteFieldIds;

    /* renamed from: d */
    private List<OnlCgformIndex> indexs;

    /* renamed from: e */
    private List<String> deleteIndexIds;

    public OnlCgformHead getHead() {
        return this.head;
    }

    public void setHead(OnlCgformHead head) {
        this.head = head;
    }

    public List<OnlCgformField> getFields() {
        return this.fields;
    }

    public void setFields(List<OnlCgformField> fields) {
        this.fields = fields;
    }

    public List<OnlCgformIndex> getIndexs() {
        return this.indexs;
    }

    public void setIndexs(List<OnlCgformIndex> indexs) {
        this.indexs = indexs;
    }

    public List<String> getDeleteFieldIds() {
        return this.deleteFieldIds;
    }

    public void setDeleteFieldIds(List<String> deleteFieldIds) {
        this.deleteFieldIds = deleteFieldIds;
    }

    public List<String> getDeleteIndexIds() {
        return this.deleteIndexIds;
    }

    public void setDeleteIndexIds(List<String> deleteIndexIds) {
        this.deleteIndexIds = deleteIndexIds;
    }

    public String toString() {
        return "OnlCgformModel [head=" + this.head + ", fields=" + this.fields + ", deleteFieldIds=" + this.deleteFieldIds + ", indexs=" + this.indexs + ", deleteIndexIds=" + this.deleteIndexIds + "]";
    }
}
