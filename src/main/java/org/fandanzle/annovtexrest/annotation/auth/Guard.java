package org.fandanzle.annovtexrest.annotation.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by alexb on 01/11/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER})
public @interface Guard {

    String[] scopes() default {};

}
