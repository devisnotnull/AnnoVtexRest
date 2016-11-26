package org.fandanzle.annovtexrest.processor.annotation;

import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.annotation.PathParam;
import org.fandanzle.annovtexrest.processor.IProcessor;

/**
 * Created by alexb on 21/11/2016.
 */
public class PathParamHandler implements IProcessor<PathParam, String> {

    public String resolve(RoutingContext context, PathParam annotation, String paramName, Class<?> resultClass) throws Exception {

        return "";
    }

}
