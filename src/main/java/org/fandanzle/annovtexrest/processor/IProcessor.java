package org.fandanzle.annovtexrest.processor;

import io.vertx.ext.web.RoutingContext;

import java.lang.annotation.Annotation;

@FunctionalInterface
public interface IProcessor<T extends Annotation, U> {
    public U resolve(RoutingContext context, T annotation, String paramName, Class<?> resultClass) throws Exception;
}
