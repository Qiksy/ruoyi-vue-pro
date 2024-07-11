package org.jeecg.modules.online.cgform.model;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/model/TreeModel.class */
public class TreeModel {
    private String label;
    private String store;

    /* renamed from: id */
    private String id;
    private String pid;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TreeModel)) {
            return false;
        }
        TreeModel treeModel = (TreeModel) o;
        if (!treeModel.canEqual(this)) {
            return false;
        }
        String label = getLabel();
        String label2 = treeModel.getLabel();
        if (label == null) {
            if (label2 != null) {
                return false;
            }
        } else if (!label.equals(label2)) {
            return false;
        }
        String store = getStore();
        String store2 = treeModel.getStore();
        if (store == null) {
            if (store2 != null) {
                return false;
            }
        } else if (!store.equals(store2)) {
            return false;
        }
        String id = getId();
        String id2 = treeModel.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String pid = getPid();
        String pid2 = treeModel.getPid();
        return pid == null ? pid2 == null : pid.equals(pid2);
    }

    protected boolean canEqual(Object other) {
        return other instanceof TreeModel;
    }

    public int hashCode() {
        String label = getLabel();
        int hashCode = (1 * 59) + (label == null ? 43 : label.hashCode());
        String store = getStore();
        int hashCode2 = (hashCode * 59) + (store == null ? 43 : store.hashCode());
        String id = getId();
        int hashCode3 = (hashCode2 * 59) + (id == null ? 43 : id.hashCode());
        String pid = getPid();
        return (hashCode3 * 59) + (pid == null ? 43 : pid.hashCode());
    }

    public String toString() {
        return "TreeModel(label=" + getLabel() + ", store=" + getStore() + ", id=" + getId() + ", pid=" + getPid() + ")";
    }

    public String getLabel() {
        return this.label;
    }

    public String getStore() {
        return this.store;
    }

    public String getId() {
        return this.id;
    }

    public String getPid() {
        return this.pid;
    }
}
