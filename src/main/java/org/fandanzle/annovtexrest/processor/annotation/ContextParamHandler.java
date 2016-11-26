package org.fandanzle.annovtexrest.processor.annotation;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.annotation.Context;
import org.fandanzle.annovtexrest.annotation.HeaderParam;
import org.fandanzle.annovtexrest.processor.IProcessor;

import java.lang.reflect.Type;

/**
 * Created by alexb on 21/11/2016.
 */
public class ContextParamHandler <T> implements IProcessor<Context, RoutingContext>{


    /**
     * 
     * @param context
     * @param annotation
     * @param paramName
     * @param resultClass
     * @return
     * @throws Exception
     */
    public RoutingContext resolve(RoutingContext context, Context annotation, String paramName, Class<?> resultClass) throws Exception {

        /**
        Class<?> clazzOfVar =
        Vertx vertx,
        HttpServerRequest request,
        HttpServerResponse response,
        RoutingContext context,
         **/

        return context;
    }


}
