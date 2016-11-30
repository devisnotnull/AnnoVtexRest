package org.fandanzle.annovtexrest.example.controller.simple;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.MimeType;
import org.fandanzle.annovtexrest.annotation.Context;
import org.fandanzle.annovtexrest.annotation.Controller;
import org.fandanzle.annovtexrest.annotation.RequestMapping;
import org.fandanzle.annovtexrest.annotation.RequestMethods;
import org.fandanzle.annovtexrest.annotation.processors.After;
import org.fandanzle.annovtexrest.annotation.processors.Before;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alexb on 28/10/2016.
 */

@Controller(uri = "/simple")
public class MockSimpleController {

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
     * USERS GENERIC CLASS
     * Param types must be of type primitive is there is a primitive
     *
     * @return
     */
    @RequestMapping(
            uri = "/all",
            method = RequestMethods.POST,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public JsonObject post(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context
    ){

        System.out.println("-------------------------------------------------");
        System.out.println("POST");


        return context.getBodyAsJson();

    }

    /**
     * USERS GENERIC CLASS
     * Param types must be of type primitive is there is a primitive
     *
     * @return
     */
    @RequestMapping(
            uri = "/test",
            method = RequestMethods.POST,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public void postTest(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context
    ){

        System.out.println("-------------------------------------------------");
        System.out.println("POST");
        System.out.println(context.request().method().name());
        System.out.println(context.getBodyAsString());
        System.out.println(context.getBody());


    }

    /**
     * USERS GENERIC CLASS
     * Param types must be of type primitive is there is a primitive
     *
     * @return
     */
    @RequestMapping(
            uri = "/all",
            method = RequestMethods.GET,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public List<String> get(
            @Context  Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context
    ){

        List<String> arr = Arrays.asList("111111","222222","3333","44444444","efoihwefouwehf","wpifnrwiofn");
        System.out.println("-------------------------------------------------");
        System.out.println("GET");
        System.out.println(context.request().method().name());
        return arr;

    }



    /**
     * USERS GENERIC CLASS
     * Param types must be of type primitive is there is a primitive
     *
     * @return
     */
    @RequestMapping(
            uri = "/all",
            method = RequestMethods.DELETE,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public String delete(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context
    ){

        System.out.println("-------------------------------------------------");
        System.out.println("-------------------------------------------------");
        System.out.println("-------------------------------------------------");
        System.out.println("-------------------------------------------------");
        System.out.println("POST");
        System.out.println(context.request().method().name());
        System.out.println(context.getBodyAsString());
        return context.getBodyAsString();

    }


}
