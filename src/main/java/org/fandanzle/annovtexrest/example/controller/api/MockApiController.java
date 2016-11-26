package org.fandanzle.annovtexrest.example.controller.api;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.MimeType;
import org.fandanzle.annovtexrest.annotation.*;
import org.fandanzle.annovtexrest.annotation.auth.Guard;
import org.fandanzle.annovtexrest.annotation.processors.After;
import org.fandanzle.annovtexrest.annotation.processors.Before;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alexb on 28/10/2016.
 */
@Controller(uri = "/api")
public class MockApiController {

    /**
     * Before any route is run,
     * Must return a handler with type routing context
     * MUST BE VOID
     * @param vertx
     * @param request
     * @param response
     * @param context
     */
    @Before
    public RoutingContext before(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context
    ){


        return context;
    }

    /**
     * After a route has been run,
     * Must return a handler with type routing context
     * MUST BE VOID
     * @param vertx
     * @param request
     * @param response
     * @param context
     */
    @After
    public RoutingContext after(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context
    ){
        return context;
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
            uri = "/users",
            method = RequestMethods.GET,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public List<String> getAllUsers(

    ){

        List<String> arr = Arrays.asList("wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg","wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg");

        return arr;

    }


    /**
     *
     * @return
     */

    @RequestMapping(
            uri = "/users/:id",
            method = RequestMethods.GET,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to a specific user by there name"
    )
    public List<String> getUserByName(
            @PathParam(name = "id") Integer id1
    ){

        List<String> arr = Arrays.asList("wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg","wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg");

        return arr;

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
            uri = "/users/:id/:id1/:id2",
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
            @PathParam(name = "id3") Integer id3,
            @HeaderParam(name = "h1") Integer h1 ,
            @HeaderParam(name = "h2") String h2,
            @QueryParam(name = "q1") String q1
    ){

            List<String> arr = Arrays.asList("wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg","wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg");

            return arr;

    }


    @RequestMapping(
            uri = "/users/:id/:id1/:id2",
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
