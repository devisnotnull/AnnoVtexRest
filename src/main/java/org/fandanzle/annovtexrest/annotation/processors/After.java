package org.fandanzle.annovtexrest.annotation.processors;

import java.lang.annotation.*;

/**
 * Generic mapping annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE}) //can use in method only.
public @interface After {

}