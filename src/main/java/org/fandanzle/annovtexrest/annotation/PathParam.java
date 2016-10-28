package org.fandanzle.annovtexrest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by alexb on 28/10/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER})
public @interface PathParam {

    String name() default "";

    String description() default "no description";

}
