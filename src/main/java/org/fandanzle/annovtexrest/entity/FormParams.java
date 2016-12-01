package org.fandanzle.annovtexrest.entity;

/**
 * Created by alexb on 30/10/2016.
 */
public class FormParams {

    public String name;

    public String value;

    public String description;

    public Class clazz;

    public String getName() {
        return name;
    }

    public FormParams setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public FormParams setValue(String value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FormParams setDescription(String description) {
        this.description = description;
        return this;
    }

    public Class getClazz() {
        return clazz;
    }

    public FormParams setClazz(Class clazz) {
        this.clazz = clazz;
        return this;
    }
}
