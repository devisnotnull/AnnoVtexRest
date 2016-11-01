package org.fandanzle.annovtexrest.entity;

import java.lang.reflect.Type;

/**
 * Created by alexb on 30/10/2016.
 */
public class HeaderParam {

    public String name;

    public String value;

    public String description;

    public Class clazz;

    public String getName() {
        return name;
    }

    public HeaderParam setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public HeaderParam setValue(String value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HeaderParam setDescription(String description) {
        this.description = description;
        return this;
    }

    public Class getClazz() {
        return clazz;
    }

    public HeaderParam setClazz(Class clazz) {
        this.clazz = clazz;
        return this;
    }
}
