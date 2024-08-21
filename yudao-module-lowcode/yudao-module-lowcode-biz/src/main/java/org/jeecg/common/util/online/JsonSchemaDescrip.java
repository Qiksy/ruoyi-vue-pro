package org.jeecg.common.util.online;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/* compiled from: JsonSchemaDescrip.java */
/* renamed from: org.jeecg.common.util.a.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/common/util/a/c.class */
@Data
public class JsonSchemaDescrip implements Serializable {

    /* renamed from: a */
    private static final long f48a = 7682073117441544718L;

    /* renamed from: b */
    private String schema;

    /* renamed from: c */
    private String title;

    /* renamed from: d */
    private String description;

    /* renamed from: e */
    private String type;

    /* renamed from: f */
    private List<String> required;

    public JsonSchemaDescrip() {
        this.schema = "http://json-schema.org/draft-07/schema#";
    }

    public JsonSchemaDescrip(List<String> list) {
        this.schema = "http://json-schema.org/draft-07/schema#";
        this.description = "我是一个jsonschema description";
        this.title = "我是一个jsonschema title";
        this.type = "object";
        this.required = list;
    }
}
