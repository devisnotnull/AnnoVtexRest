package org.fandanzle.annovtexrest.authz;

import io.vertx.ext.web.RoutingContext;

import java.util.List;

/**
 * Created by alexb on 04/11/2016.
 */
public interface AuthzHandlerInterface {

    public void init(RoutingContext context);

    public Boolean guardCheck(List<String> guardList);

}
