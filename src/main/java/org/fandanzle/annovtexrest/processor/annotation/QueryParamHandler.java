package org.fandanzle.annovtexrest.processor.annotation;

import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.annotation.HeaderParam;
import org.fandanzle.annovtexrest.annotation.QueryParam;
import org.fandanzle.annovtexrest.processor.IProcessor;

/**
 * Created by alexb on 21/11/2016.
 */
public class QueryParamHandler implements IProcessor<QueryParam, String>{

    /**
     *
     * @param context
     * @param annotation
     * @param paramName
     * @param resultClass
     * @return
     * @throws Exception
     */
    public String resolve(RoutingContext context, QueryParam annotation, String paramName, Class<?> resultClass) throws Exception {

        return "";
    }


}
