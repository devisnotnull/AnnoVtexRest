package org.fandanzle.annovtexrest.controller;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.MimeTypes;
import org.fandanzle.annovtexrest.annotation.Controller;
import org.fandanzle.annovtexrest.annotation.RequestMapping;
import org.fandanzle.annovtexrest.annotation.RequestMethods;
import java.util.Date;
import java.util.Set;

/**
 * Created by alexb on 28/10/2016.
 */

@Controller(uri = "/stats")
public class MockVertxController {

    /**
     * USERS GENERIC CLASS
     * Param types must be of type primitive is there is a primitive
     *
     * @return
     */
    @RequestMapping(
            uri = "/all",
            method = RequestMethods.GET,
            consumes = MimeTypes.APPLICATION_HTML,
            produces = MimeTypes.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public Set<String> tesjiot(
            Vertx vertx,
            HttpServerRequest request,
            HttpServerResponse response,
            RoutingContext context,
            Date wfwefwef
    ){

        return vertx.deploymentIDs();

    }

    /**
     * USERS GENERIC CLASS
     * Param types must be of type primitive is there is a primitive
     *
     * @return
     */
    @RequestMapping(
            uri = "/all",
            method = RequestMethods.POST,
            consumes = MimeTypes.APPLICATION_HTML,
            produces = MimeTypes.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public Set<String> createStat(
            Vertx vertx,
            HttpServerRequest request,
            HttpServerResponse response,
            RoutingContext context,
            Date wfwefwef
    ){

        return vertx.deploymentIDs();

    }

}
