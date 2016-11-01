package org.fandanzle.annovtexrest.handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;


public abstract class AbstractMethodInvocationHandler<T> implements Handler<RoutingContext> {

    protected final Method method;
    protected final Object instance;
    protected final boolean hasNext;
    protected final BiConsumer<RoutingContext, T> returnHandler;
    protected final boolean returnsSomething;
    private final Parameter[] parameters;
    protected boolean usesRoutingContext;
    protected boolean usesHttpResponse;

    protected AbstractMethodInvocationHandler(Object instance, Method method, boolean hasNext, BiConsumer<RoutingContext, T> returnHandler) {
        this.method = method;
        returnsSomething = !method.getReturnType().equals(Void.TYPE);
        this.hasNext = hasNext;
        parameters = method.getParameters();
        for (Parameter param : parameters) {
            Class<?> paramType = param.getType();
            if (paramType.equals(RoutingContext.class)) {
                usesRoutingContext = true;
            }
            if (paramType.equals(HttpServerResponse.class)) {
                usesHttpResponse = true;
            }
        }
        this.instance = instance;
        this.returnHandler = returnHandler;
    }

    @Override
    abstract public void handle(RoutingContext routingContext);

    protected Object[] getParameters(RoutingContext routingContext) throws Exception {
        List<Object> params = new ArrayList<>();
        for (Parameter param : parameters) {
            //Object paramInstance = getParameterInstance(routingContext, param.getAnnotations(), param.getType(), param.getName());
            //params.add(paramInstance);
        }
        return params.toArray();
    }

}