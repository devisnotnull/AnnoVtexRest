package org.fandanzle.annovtexrest.annotation;

import org.fandanzle.annovtexrest.MimeType;

import java.lang.annotation.*;

/**
 * Generic mapping annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE}) //can use in method only.
public @interface RequestMapping {

    String[] uri() default {};

    RequestMethods[] method() default {RequestMethods.DELETE};

    String description() default "no description";

    MimeType consumes() default MimeType.APPLICATION_HTML;

    MimeType produces() default MimeType.APPLICATION_JSON;

    String charset() default "UTF-8";

    boolean noCache() default false;

}