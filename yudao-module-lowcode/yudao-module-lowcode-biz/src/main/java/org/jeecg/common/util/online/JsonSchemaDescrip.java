package org.jeecg.common.util.online;

import java.io.Serializable;
import java.util.List;

/* compiled from: JsonSchemaDescrip.java */
/* renamed from: org.jeecg.common.util.a.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/c.class */
public class JsonSchemaDescrip implements Serializable {

    /* renamed from: a */
    private static final long f48a = 7682073117441544718L;

    /* renamed from: b */
    private String f49b;

    /* renamed from: c */
    private String f50c;

    /* renamed from: d */
    private String f51d;

    /* renamed from: e */
    private String f52e;

    /* renamed from: f */
    private List<String> f53f;

    public List<String> getRequired() {
        return this.f53f;
    }

    public void setRequired(List<String> required) {
        this.f53f = required;
    }

    public String get$schema() {
        return this.f49b;
    }

    public void set$schema(String $schema) {
        this.f49b = $schema;
    }

    public String getTitle() {
        return this.f50c;
    }

    public void setTitle(String title) {
        this.f50c = title;
    }

    public String getDescription() {
        return this.f51d;
    }

    public void setDescription(String description) {
        this.f51d = description;
    }

    public String getType() {
        return this.f52e;
    }

    public void setType(String type) {
        this.f52e = type;
    }

    public JsonSchemaDescrip() {
        this.f49b = "http://json-schema.org/draft-07/schema#";
    }

    public JsonSchemaDescrip(List<String> list) {
        this.f49b = "http://json-schema.org/draft-07/schema#";
        this.f51d = "我是一个jsonschema description";
        this.f50c = "我是一个jsonschema title";
        this.f52e = "object";
        this.f53f = list;
    }
}
