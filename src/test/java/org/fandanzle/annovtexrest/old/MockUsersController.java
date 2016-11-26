package org.fandanzle.annovtexrest.old;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
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
            uri = "/users/:param1/:param2",
            method = RequestMethods.GET,
            produces = MimeType.APPLICATION_JSON,
            description = "An endpoint to get a specific user with another paramter for refinement"
    )
    public String getSingleUser(
            Vertx vertx,
            HttpServerRequest request,
            HttpServerResponse response,
            RoutingContext context,
            @PathParam(name="param1") Integer param1,
            @PathParam(name="param2") Integer param2,
            @QueryParam(name = "perPage", required = false) Integer perPage,
            @QueryParam(name = "pageNumber", required = false) Integer pageNumber,
            @HeaderParam(name = "user-agent") String userAgent,
            @HeaderParam(name = "req-type") String requestType
    ){

        return "qefefwefwef";

    }

}
