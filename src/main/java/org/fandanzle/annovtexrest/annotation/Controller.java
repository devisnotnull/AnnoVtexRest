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

    String description() default "no description";

    String charset() default "UTF-8";

    boolean noCache() default false;

}