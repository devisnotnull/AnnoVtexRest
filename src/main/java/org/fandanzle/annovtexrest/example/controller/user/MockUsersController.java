package org.fandanzle.annovtexrest.example.controller.user;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.MimeType;
import org.fandanzle.annovtexrest.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alexb on 28/10/2016.
 */

@Controller(uri = "/users")
public class MockUsersController {

    /**
     * Returns a type list
     * @return
     */
    @RequestMapping(
            uri = "/all",
            method = RequestMethods.GET,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public List<String> getAllUsers(
            Vertx vertx,
            HttpServerRequest request,
            HttpServerResponse response,
            RoutingContext context
    ){
        List<String> arr = Arrays.asList("wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg","wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg");
        return arr;
    }

    /**
     * Returns a type String
     * @return
     */
    @RequestMapping(
            uri = "/users/:param1/:param2/:param3",
            method = RequestMethods.GET,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to get a specific user with another paramter for refinement"
    )
    public JsonObject getSingleUser(
            Vertx vertx,
            HttpServerRequest request,
            HttpServerResponse response,
            RoutingContext context,
            @PathParam(name="param1") Integer param1,
            @PathParam(name="param2") Integer param2,
            @PathParam(name="param3") String param3,
            @QueryParam(name = "q1") String q1

    ){

        JsonObject dd = new JsonObject()
                .put("item", "fefewf")
                .put("wefwefwefwef","efefwefwef")
                .put("item", "fefewf")
                .put("wefwefwefwef","efefwefwef")
                .put("item", "fefewf")
                .put("wefwefwefwef","efefwefwef")
                .put("item", "fefewf")
                .put("wefwefwefwef","efefwefwef")
                .put("item", "fefewf")
                .put("wefwefwefwef","efefwefwef")
                .put("item", "fefewf")
                .put("wefwefwefwef","efefwefwef");

        return dd;

    }

}
