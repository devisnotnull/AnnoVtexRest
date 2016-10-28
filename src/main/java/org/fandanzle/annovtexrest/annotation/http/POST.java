package org.fandanzle.annovtexrest.annotation.http;

import org.fandanzle.annovtexrest.annotation.RequestMethods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to map classes to Vert.x HttpEndpoints
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface POST {

    String[] uri() default {};

    RequestMethods[] method() default {RequestMethods.POST};

    String description() default "no description";

    String contentType() default "application/json";

    boolean noCache() default false;
}