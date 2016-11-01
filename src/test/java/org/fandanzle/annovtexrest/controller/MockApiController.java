package org.fandanzle.annovtexrest.controller;

import org.fandanzle.annovtexrest.MimeTypes;
import org.fandanzle.annovtexrest.annotation.*;

import javax.annotation.concurrent.GuardedBy;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alexb on 28/10/2016.
 */
//@RequestMapping(uri = "/api")
public class MockApiController {

    /**
     *
     * @return
     */
    @RequestMapping(
            uri = "/users/{flhwgfo}/{weofhweoufhowef}/{wejfhweufweif}",
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
     *
     * @return
     */
    @RequestMapping(
            uri = "/users/{id}/{id1}/{id2}",
            method = RequestMethods.GET,
            consumes = MimeTypes.APPLICATION_HTML,
            produces = MimeTypes.APPLICATION_JSON,
            description = "An endpoint to a specific user by there name"
    )
    public List<String> getUserByName(
            @PathParam(name = "id1") int id1,
            @PathParam(name = "id2") int id2,
            @PathParam(name = "id3") int id3,
            @HeaderParam(name = "h1") int h1 ,
            @HeaderParam(name = "h2") String h2,
            @QueryParam(name = "q1") String q1
    ){

        List<String> arr = Arrays.asList("wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg","wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg");

        return arr;

    }
}
