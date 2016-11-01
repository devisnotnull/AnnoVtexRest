package org.fandanzle.annovtexrest.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.handlers.impl.InvocationImplementation;

public interface InvocationInterface extends Handler<RoutingContext> {

    static InvocationInterface create() {
        return new InvocationImplementation();
    }

}
