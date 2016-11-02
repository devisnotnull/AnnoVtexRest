package org.fandanzle.annovtexrest.annotation;

import org.fandanzle.annovtexrest.MimeTypes;

import java.lang.annotation.*;

/**
 * Generic mapping annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE}) //can use in method only.
public @interface Controller {

    String[] uri() default {};

    RequestMethods[] method() default {RequestMethods.DELETE};

    String description() default "no description";

    MimeTypes consumes() default MimeTypes.APPLICATION_HTML;

    MimeTypes produces() default MimeTypes.APPLICATION_JSON;

    String charset() default "UTF-8";

    boolean noCache() default false;

}