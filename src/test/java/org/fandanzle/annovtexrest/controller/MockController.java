package org.fandanzle.annovtexrest.controller;

import org.fandanzle.annovtexrest.annotation.PathParam;
import org.fandanzle.annovtexrest.annotation.RequestMapping;
import org.fandanzle.annovtexrest.annotation.RequestMethods;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alexb on 28/10/2016.
 */

@RequestMapping(uri = "/api")
public class MockController {

    /**
     *
     * @return
     */
    @RequestMapping(
            uri = "/users",
            method = RequestMethods.GET,
            description = "An endpoint to get all users"
    )
    public List<String> getAllUsers(@PathParam int a){

        List<String> arr = Arrays.asList("wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg","wfwrgrwgerg","efoihwefouwehf","82uf0824ufb04c8fg");

        return arr;

    }

}
