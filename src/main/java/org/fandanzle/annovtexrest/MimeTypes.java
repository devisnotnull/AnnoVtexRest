package org.fandanzle.annovtexrest;

/**
 * Created by alexb on 28/10/2016.
 */
public enum MimeTypes {

    APPLICATION_HTML("text/html"),

    APPLICATION_XML("application/xml"),

    APPLICATION_JSON("application/json"),

    X_FORM_ENCODED("application/x-www-form-urlencoded");

    private String mime;

    MimeTypes(String grantType) {
        this.mime = grantType;
    }

    @Override
    public String toString() {
        return mime;
    }

}
