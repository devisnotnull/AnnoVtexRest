package org.fandanzle.annovtexrest.entity;

/**
 * Created by alexb on 30/10/2016.
 */
public class QueryParam {

    public String name;

    public String value;

    public String description;

    public boolean required;

    public Class clazz;

    public boolean isRequired() {
        return required;
    }

    public QueryParam setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public String getName() {
        return name;
    }

    public QueryParam setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public QueryParam setValue(String value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public QueryParam setDescription(String description) {
        this.description = description;
        return this;
    }

    public Class getClazz() {
        return clazz;
    }

    public QueryParam setClazz(Class clazz) {
        this.clazz = clazz;
        return this;
    }
}
