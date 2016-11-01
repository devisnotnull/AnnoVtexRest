package org.fandanzle.annovtexrest.controller;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.apache.log4j.Logger;
import org.fandanzle.annovtexrest.AnnoVtexRest;
import org.fandanzle.annovtexrest.reflection.MethodSpy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

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

        System.out.println("_______________________________________________________________________________________________________________");
        System.out.println("_______________________________________________________________________________________________________________");
        System.out.println("_______________________________________________________________________________________________________________");

        // MethodSpy.printClassMethods(MockApiController.class);

        System.out.println("_______________________________________________________________________________________________________________");
        System.out.println("_______________________________________________________________________________________________________________");
        System.out.println("_______________________________________________________________________________________________________________");

        // MethodSpy.printClassMethods(MockUsersController.class);

        Object[] obj = {  1 , "wwoifhweoif" , true }; // for method1()

        System.out.println(Json.encode(obj));

        // Object[] obj={"hello"}; for method1(String str)
        // Object[] obj={"hello",1}; for method1(String str,int number)
        // Step 2) Create a class array which will hold the signature of the
        // method being called.
        Class<?> params[] = new Class[obj.length];

        for (int i = 0; i < obj.length; i++) {

            System.out.println("VALUE : " + obj[i]);
            System.out.println("CLAZZ : " + obj[i].getClass().getCanonicalName());
            System.out.println("IS PRIMITIVE : " + obj[i].getClass().isPrimitive());

            if (obj[i] instanceof Integer) {
                System.out.println("INTERGER TYPE ===========");
                if (obj[i].getClass().isPrimitive()){
                    System.out.println("-------------------");
                    System.out.println("WE ARE A PRIMITIVE");
                }
                params[i] = Integer.class;
            }

            if (obj[i] instanceof Boolean) {
                System.out.println("BOOLEAN TYPE ===========");
                params[i] = Boolean.class;
            }else if (obj[i] instanceof String) {
                System.out.println("STRINF TYPE ===========");

                params[i] = String.class;
            }
            // you can do additional checks for other data types if you want.
        }

        try {
            String methoName = "test"; // methodname to be invoked
            String className = MockSimpleController.class.getName();// Class name
            Class<?> cls = Class.forName(className);
            Object _instance = cls.newInstance();
            Method myMethod = cls.getDeclaredMethod(methoName, params);
            System.out.println( myMethod.invoke(_instance, obj) );
        }catch (Exception e){
            e.printStackTrace();
        }


        async.complete();

    }

}