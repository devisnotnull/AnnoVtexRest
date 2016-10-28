package org.fandanzle.annovtexrest.annotation;

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

    String contentType() default "application/json";

    boolean noCache() default false;

}