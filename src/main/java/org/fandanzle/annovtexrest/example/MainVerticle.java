package org.fandanzle.annovtexrest.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;
import org.apache.log4j.pattern.CachedDateFormat;
import org.fandanzle.annovtexrest.AnnoVtexRest;

import java.util.*;

/**
 *
 * Created by alexb on 05/07/15.
 *
*/

public class MainVerticle extends AbstractVerticle {

    private static final Logger logger = Logger.getLogger(MainVerticle.class);

    private Router router = null;

    protected AnnoVtexRest annoVtexRest;

    /**
     * Start verticle
     */
    @Override
    public void start() {

        try{
            annoVtexRest = new AnnoVtexRest(vertx);

            annoVtexRest.build("org.fandanzle.annovtexrest.example.controller");

            System.out.println("////////>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("DONE WITH BUILD");

            router = annoVtexRest.getRouter();

            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println("=================================");
            System.out.println("=================================");

            System.out.println(Json.encode(annoVtexRest.getRouter().getRoutes()));

        }catch (Exception e){

        }
        // Pass router referernce into router
        // Build all routes for this verticle.
        vertx.createHttpServer().requestHandler(router::accept).listen(8000);

    }



    //
    // Generic errors and responses
    private void sendError(RoutingContext context ,Integer errorCode, Object message){
        context.response().setStatusCode(errorCode).end(Json.encodePrettily(message));
    }

    //
    private void sendResult(RoutingContext context, JsonObject message){
        context.response().setStatusCode(200).end(Json.encodePrettily(message));
    }

    //
    private void sendResult(RoutingContext context, List<JsonObject> message){
        context.response().setStatusCode(200).end(Json.encodePrettily(message));
    }

    //
    private void sendResult(RoutingContext context, String message){
        context.response().setStatusCode(200).end(message);
    }

}
