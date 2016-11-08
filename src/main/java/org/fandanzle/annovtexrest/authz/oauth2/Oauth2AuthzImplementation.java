package org.fandanzle.annovtexrest.authz.oauth2;

import io.vertx.core.impl.verticle.JavaSourceContext;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.authz.AuthzHandlerInterface;
import org.fandanzle.annovtexrest.entity.Route;

import java.util.List;

/**
 * Created by alexb on 04/11/2016.
 */
public class Oauth2AuthzImplementation implements AuthzHandlerInterface {

    private RoutingContext context;

    /**
     *
     */
    private JsonObject oauth2Config = new JsonObject();

    /**
     *
     * @param context
     */
    public void init(RoutingContext context){

        this.context = context;

    }

    /**
     *
     * @param guardList
     * @return
     */
    public Boolean guardCheck(List<String> guardList){

        String bearer = context.request().getHeader("Authorization");

        System.out.println("=========================================================================================");
        System.out.println("Authz Token : " + bearer);

        String[] token = bearer.split(" ");

        System.out.println("=========================================================================================");
        System.out.println("Bearer Token : " + bearer);

        return true;

    }

}
