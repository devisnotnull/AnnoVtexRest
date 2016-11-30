package org.fandanzle.annovtexrest.example.controller.api;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.CookieImpl;
import org.fandanzle.annovtexrest.MimeType;
import org.fandanzle.annovtexrest.annotation.*;
import org.fandanzle.annovtexrest.annotation.auth.Guard;
import org.fandanzle.annovtexrest.annotation.processors.After;
import org.fandanzle.annovtexrest.annotation.processors.Before;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by alexb on 28/10/2016.
 */
@Controller(uri = "/api")
public class MockApiController {

    /**
     *
     * @return
     */
    @RequestMapping(
            uri = "/users",
            method = RequestMethods.GET,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public HashMap<String,Object> getAllUsers(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context
    ){


        HashMap<String,Object> params = new HashMap<>();

        params.put("path", context.currentRoute().getPath());
        params.put("http-method", context.request().method().name());

        return params;


    }


    /**
     *
     * @return
     */

    @RequestMapping(
            uri = "/users/:id1",
            method = RequestMethods.GET,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to a specific user by there name"
    )
    public HashMap<String,Object> getUserByName(
            @Context RoutingContext context,
            @PathParam(name = "id1") Integer id1
    ){

        HashMap<String,Object> params = new HashMap<>();

        params.put("path", context.currentRoute().getPath());
        params.put("http-method", context.request().method().name());
        params.put("id1", id1);

        return params;

    }

    /**
     *
     * @return
     */
    @Guard(
            scopes= {
                    "support_account.READ"
            }
    )
    @RequestMapping(
            uri = "/users/:id1/:id2",
            method = RequestMethods.GET,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to a specific user by there name"
    )
    public List<String> getUserByName(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context,
            @CookieParam(name = "x-session-id") String cookie,
            @PathParam(name = "id1") Integer id1,
            @PathParam(name = "id2") Integer id2,
            @HeaderParam(name = "h1") Integer h1 ,
            @HeaderParam(name = "h2") String h2,
            @QueryParam(name = "q1") String q1
    ){

            List<String> arr = Arrays.asList("wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg","wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg");

            return arr;

    }


    @RequestMapping(
            uri = "/users/:id1/:id2/:id3",
            method = RequestMethods.GET,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to a specific user by there name"
    )
    public List<String> getList(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context,
            @CookieParam(name = "x-session-id") String cookie,
            @PathParam(name = "id1") Integer id1,
            @PathParam(name = "id2") Integer id2,
            @PathParam(name = "id3") Integer id3,
            @HeaderParam(name = "h1") Integer h1 ,
            @HeaderParam(name = "h2") String h2,
            @QueryParam(name = "q1") String q1
    ){

        List<String> arr = Arrays.asList("wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg","wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg");

        return arr;

    }

}
