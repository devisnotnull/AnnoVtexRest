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
import org.fandanzle.annovtexrest.example.entity.User;

import java.time.DateTimeException;
import java.util.*;

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
            @Context RoutingContext context,
            String wefohwfouwhg
    ){


        HashMap<String,Object> params = new HashMap<>();
        Date dateTime = new Date();
        params.put("path", context.currentRoute().getPath());
        params.put("http-method", context.request().method().name());
        params.put("generated", dateTime.toString());


        return params;


    }


    /**
     *
     * @return
     */
    @RequestMapping(
            uri = "/users/createjson",
            method = RequestMethods.POST,
            consumes = MimeType.APPLICATION_JSON,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public String createUserJson(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context,
            @Body String user
    ){

        HashMap<String,Object> params = new HashMap<>();
        Date dateTime = new Date();
        params.put("path", context.currentRoute().getPath());
        params.put("http-method", context.request().method().name());
        params.put("generated", dateTime.toString());
        params.put("json:input", user);


        return "created user";

    }

    /**
     *
     * @return
     */
    @RequestMapping(
            uri = "/users/createform",
            method = RequestMethods.POST,
            consumes = MimeType.APPLICATION_JSON,
            produces = MimeType.X_FORM_ENCODED,
            description = "An endpoint to get all users"
    )
    public HashMap<String,Object> createUserForm(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context,
            @Form String user
    ){

        HashMap<String,Object> params = new HashMap<>();
        Date dateTime = new Date();
        params.put("path", context.currentRoute().getPath());
        params.put("http-method", context.request().method().name());
        params.put("generated", dateTime.toString());
        params.put("form:input", user);


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
            @PathParam(name = "id1") Integer id1,
            @QueryParam(name = "q1", defaultValue = "45") Integer q1,
            @QueryParam(name = "q2", defaultValue = "45") Integer q2


    ){

        HashMap<String,Object> params = new HashMap<>();
        Date dateTime = new Date();
        params.put("path", context.currentRoute().getPath());
        params.put("http-method", context.request().method().name());
        params.put("id1", id1);
        params.put("generated", dateTime.toString());


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
    public HashMap<String,Object> getUserByName(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context,
            @PathParam(name = "id1") Integer id1,
            @PathParam(name = "id2") Integer id2
    ){

        HashMap<String,Object> params = new HashMap<>();

        Date dateTime = new Date();
        params.put("path", context.currentRoute().getPath());
        params.put("http-method", context.request().method().name());
        params.put("id1", id1);
        params.put("id2", id1);
        params.put("generated", dateTime.toString());

        return params;

    }


    @RequestMapping(
            uri = "/users/:id1/:id2/:id3",
            method = RequestMethods.GET,
            consumes = MimeType.APPLICATION_HTML,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to a specific user by there name"
    )
    public HashMap<String,Object> getList(
            @Context Vertx vertx,
            @Context HttpServerRequest request,
            @Context HttpServerResponse response,
            @Context RoutingContext context,
            @PathParam(name = "id1") Integer id1,
            @PathParam(name = "id2") Integer id2,
            @PathParam(name = "id3") Integer id3
    ){

        HashMap<String,Object> params = new HashMap<>();
        Date dateTime = new Date();
        params.put("path", context.currentRoute().getPath());
        params.put("http-method", context.request().method().name());
        params.put("id1", id1);
        params.put("id2", id2);
        params.put("id3", id3);
        params.put("generated", dateTime.toString());


        return params;

    }

}
