package org.fandanzle.annovtexrest.controller;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.apache.log4j.Logger;
import org.fandanzle.annovtexrest.AnnoVtexRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 *
 * Created by alexb on 06/07/15.
 *
 */
@RunWith(VertxUnitRunner.class)
public class SimpleControllerTest {

    private Logger logger = Logger.getLogger(SimpleControllerTest.class);

    private Vertx vertx;

    /**
     *
     * Setup for the test
     * @throws Exception
     */
    @Before
    public void setup() throws Exception {


    }

    /**
     *
     * @param context
     */
    @Test
    public void testMongoIndexCreation(TestContext context){

        vertx = Vertx.vertx();

        final Async async = context.async();

        AnnoVtexRest annoVtexRest = new AnnoVtexRest(vertx);

        annoVtexRest.build("org.fandanzle.annovtexrest.controller");

        assertTrue(true);

        async.complete();

    }

}