package org.fandanzle.annovtexrest.controller;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.MimeType;
import org.fandanzle.annovtexrest.annotation.*;
import org.fandanzle.annovtexrest.annotation.processors.After;
import org.fandanzle.annovtexrest.annotation.processors.Before;
import org.fandanzle.annovtexrest.helper.MultiMapForm;

import java.util.Arrays;
import java.util.List;

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
            MultiMapForm formValues,
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
