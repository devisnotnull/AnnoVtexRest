package org.fandanzle.annovtexrest.controller;

import org.fandanzle.annovtexrest.MimeTypes;
import org.fandanzle.annovtexrest.annotation.*;
import org.fandanzle.annovtexrest.annotation.http.GET;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alexb on 28/10/2016.
 */

//@RequestMapping(uri = "/users")
public class MockUsersController {

    /**
     * USERES GENERIC CLASS
     * @return
     */
    @RequestMapping(
            uri = "/users",
            method = RequestMethods.GET,
            consumes = MimeTypes.APPLICATION_HTML,
            produces = MimeTypes.APPLICATION_JSON,
            description = "An endpoint to get all users"
    )
    public List<String> getAllUsers(

    ){

        List<String> arr = Arrays.asList("wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg","wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg");

        return arr;

    }

    /**
     * USERES GENERIC CLASS
     * @return
     */
    @RequestMapping(
            uri = "/users/{param1}/{param2}",
            method = RequestMethods.GET,
            consumes = MimeTypes.APPLICATION_HTML,
            produces = MimeTypes.APPLICATION_JSON,
            description = "An endpoint to get a specific usser with another paramter for refinement"
    )
    public String getSingleUser(
            @PathParam(name="param1") int param1,
            @PathParam(name="param2") int param2,
            @QueryParam(name = "perPage", required = false) int perPage,
            @QueryParam(name = "pageNumber", required = false) int pageNumber,
            @HeaderParam(name = "user-agent") String userAgent,
            @HeaderParam(name = "req-type") String requestType
            //@CookieParam(name="x-ses-id") String dfdf
    ){

        return "qefefwefwef";

    }

}
