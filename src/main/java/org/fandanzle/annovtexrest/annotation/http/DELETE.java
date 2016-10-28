package org.fandanzle.annovtexrest.annotation.http;

import org.fandanzle.annovtexrest.annotation.RequestMethods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to map Service method to URIs in an HTTP like protocol.
 *
 * @author Rick Hightower
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface DELETE {

    String[] uri() default {};

    RequestMethods[] method() default {RequestMethods.DELETE};

    String description() default "no description";

    String contentType() default "application/json";

    boolean noCache() default false;

}