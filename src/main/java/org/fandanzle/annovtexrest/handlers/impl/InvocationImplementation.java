package org.fandanzle.annovtexrest.handlers.impl;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.AnnoVtexRest;
import org.fandanzle.annovtexrest.annotation.*;
import org.fandanzle.annovtexrest.entity.PathParam;
import org.fandanzle.annovtexrest.entity.Route;
import org.fandanzle.annovtexrest.handlers.InvocationInterface;
import org.mozilla.javascript.tools.shell.JSConsole;
import org.omg.CORBA.SystemException;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by alexb on 31/10/2016.
 */
public class InvocationImplementation implements InvocationInterface{

    Vertx vertx;
    RoutingContext context;

    /**
     *
     * @param context
     */
    @Override
    public void handle(RoutingContext context) {

        this.vertx = context.vertx();
        this.context = context;

        List<Route> router = AnnoVtexRest.routers;

        if (router == null) return;

        Route e = router.stream()
                .filter(
                        f -> f.getUri().equals(context.currentRoute().getPath()) && f.getMethod().name().equals(context.request().method().name())
                ).findFirst().get();

        // Find our route in the stream
        if(e != null){

            Parameter[] params = e.getParams();
            Object[] objInv = new Object[e.getParams().length];

            for(int i=0; i < (e.getParams().length); i++) {

                if(params[i].getAnnotations().length == 0){
                    error("Invalid controller declaration, A function variable has not been declared properly with an annotation");
                    return;
                }
                for(int ii = 0; ii < ( params[i].getAnnotations().length ); ii++){
                    System.out.println("ANNONTATION !!!!");
                    System.out.println("Class : " + params[i].getAnnotations()[ii].annotationType().getClass());
                    System.out.println("TYPE : " + params[i].getAnnotations()[ii].annotationType());
                    System.out.println("RAW CLAZZ : " + params[i].getAnnotations()[ii].getClass());
                    System.out.println("CAN Name : " + params[i].getAnnotations()[ii].annotationType().getCanonicalName());
                }
                //
                // Fetch our PathParam
                org.fandanzle.annovtexrest.annotation.PathParam pathParam = params[i].getAnnotation(org.fandanzle.annovtexrest.annotation.PathParam.class);
                if (processPathParam(pathParam, params[i].getClass()) != null) {

                    System.out.println("================================================================================");
                    System.out.println("PATH PARAM =-=--------------------------------------------------------------");
                    System.out.println("PATH PARAM VALUE : "  );
                    try {
                        if (params[i].getType() == String.class) {
                            objInv[i] = context.request().getParam(pathParam.name());
                        }
                        else if (params[i].getType() == Integer.class) {
                            objInv[i] = Integer.valueOf(context.request().getParam(pathParam.name()));
                        }
                        else{
                            error("Invalid PATH Param Type : " + pathParam.name() + " : " + params[i].getType());
                            return;
                        }
                    }catch (Exception exe){
                        exe.printStackTrace();
                        error(exe.getMessage());
                        return;
                    }

                }

                //
                // Fetch our QueryParam
                QueryParam queryParam = params[i].getAnnotation(QueryParam.class);
                if (processQueryParam(queryParam, params[i].getClass()) != null) {

                    System.out.println("================================================================================");
                    System.out.println("QUERY PARAM =-=--------------------------------------------------------------");

                    try {
                        //
                        if (params[i].getType() == String.class) {
                            objInv[i] = context.request().params().get(queryParam.name());
                        }
                        else if (params[i].getType() == Integer.class) {
                            objInv[i] = Integer.valueOf(context.request().params().get(pathParam.name()));
                        }
                        else{
                            error("Invalid Query Param Type : " + queryParam.name() + " : " + params[i].getType());
                            return;
                        }
                    }catch (Exception exe){
                        exe.printStackTrace();
                        error(exe.getMessage());
                        return;
                    }

                }

                //
                // Fetch our HeaderParam
                HeaderParam headerParam = params[i].getAnnotation(HeaderParam.class);
                if (processHeaderParam(headerParam, params[i].getClass()) != null) {

                    System.out.println("================================================================================");
                    System.out.println("HEADER PARAM =-=--------------------------------------------------------------");
                    try {
                        //
                        if (params[i].getType() == String.class) {
                            objInv[i] = context.request().params().get(headerParam.name());
                        }
                        else{
                            error("Invalid HEADER Param Type : " + headerParam.name() + " : " + params[i].getType());
                            return;
                        }
                    }catch (Exception exe){
                        exe.printStackTrace();
                        error(exe.getMessage());
                        return;
                    }

                }

                //
                // Fetch our HeaderParam
                Context contextParam = params[i].getAnnotation(Context.class);
                if (contextParam != null) {
                    System.out.println("================================================================================");
                    System.out.println("CONTEXT PARAM =-=--------------------------------------------------------------");

                    if (params[i].getType() == RoutingContext.class) {
                        System.out.println("------------ ROUTING CONTEXT --------------------");
                        objInv[i] = (RoutingContext) context;
                    }

                    else if (params[i].getType() == Vertx.class) {
                        System.out.println("------------ VERTX CONTEXT --------------------");

                        objInv[i] = (Vertx) vertx;
                    }

                    else if (params[i].getType() == HttpServerRequest.class) {
                        System.out.println("------------ REQUEST CONTEXT --------------------");

                        objInv[i] = (HttpServerRequest) context.request();
                    }

                    else if (params[i].getType() == HttpServerResponse.class) {
                        System.out.println("------------ RESPONSE CONTEXT --------------------");

                        objInv[i] = (HttpServerResponse) context.response();
                    }
                    else{
                        error("Requested Clazz is not a valid context request");
                        return;
                    }
                }

            }

            // Handle reflections based call from Route.class object
            //
            try {

                System.out.println("Length or angument srray : " +  objInv.length);
                for(int i = 0; i < objInv.length; i++){
                    System.out.println("Class iterator : " + i + " _ " + objInv[i]);
                }

                // Proccess each class for each value
                Class<?> paramsInv[] = new Class[objInv.length];
                for (int uu = 0; uu < objInv.length; uu++) {
                    paramsInv[uu] = params[uu].getType();
                }
                // Set names from annotation to invoke
                String methodName = e.getInvokeMethod().getName(); // methodname to be invoked
                String className = e.getInvokeClazz().getName();// Class name
                Class<?> cls = Class.forName(className);
                Object _instance = cls.newInstance();
                Method myMethod = cls.getDeclaredMethod(methodName, paramsInv);
                // Set content type from annotation
                context.response().putHeader("Content-Type", e.getProduces().name());
                // If is void this will ignore
                // Void checker
                System.out.println("Here is our instance of the Reflections class");
                System.out.println(Json.encode(_instance));
                System.out.println("Here is the OBVS instance");
                System.out.println("Invoking the class with variables scaffold");
                Object isVoid = myMethod.invoke(_instance, objInv);
                // If return type of invoked function is null we assume that return logic is handeled via the invoked function
                if(isVoid != null){

                    try{
                        Json.encodePrettily(myMethod.invoke(_instance, objInv));
                    }catch (Exception ex){
                        error("Unable to encode payload : " + ex.getMessage());
                    }

                    context.response().end(
                            Json.encodePrettily(myMethod.invoke(_instance, objInv))
                    );

                }
            }catch (Exception er){
                er.printStackTrace();
                context.response().setStatusCode(500).end(er.getMessage());
            }

        }

    }

    /**
     *
     *
     */
    private void handleController(Class<?> controllerClazz){

        // Get our Annotation and type check
        Annotation ano = controllerClazz.getAnnotation(RequestMapping.class);

        System.out.println("------------------------------------------------------");
        System.out.println("Caniconial Name   : " + controllerClazz.getCanonicalName());
        System.out.println("Our Annotation : " +  ano);

        // Check annotation is instance of ProviderTypeAnnotation.class
        if (ano instanceof RequestMapping) {

            System.out.println("============================================");
            System.out.println("Request Mapping URI   : " + ((RequestMapping) ano).uri()[0]);

            Method[] methods = controllerClazz.getDeclaredMethods();

            for(Method method : methods){
                //this.handleMethod(method);
            }

        }
        else{

        }

    }

    /**
     *
     * @param controllerMethod
     */
    private Parameter[] handleMethod(Method controllerMethod){

        System.out.println(controllerMethod.getName());
        RequestMapping unique = controllerMethod.getAnnotation(RequestMapping.class);

        if (unique != null) {

            System.out.println("------------------------------------------------------");
            System.out.println("Method name : " + controllerMethod.getName());
            System.out.println(unique.consumes());
            System.out.println(unique.produces());
            System.out.println(unique.description());
            System.out.println(unique.method()[0]);
            System.out.println(unique.noCache());
            System.out.println(((RequestMapping) unique).uri()[0] + unique.uri()[0]);

            String dd = ((RequestMapping) unique).uri()[0] + unique.uri()[0];


            return controllerMethod.getParameters();

        }
        return null;

    }


    /**
     * Handle all HeaderParam annotations
     * @return
     */
    private org.fandanzle.annovtexrest.entity.HeaderParam processHeaderParam(HeaderParam anno, Class clazz){

        if(anno != null){
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("PATH PARAM");
            System.out.println("Name :" + anno.name());

            org.fandanzle.annovtexrest.entity.HeaderParam param = new org.fandanzle.annovtexrest.entity.HeaderParam();
            param.setName(anno.name());
            param.setDescription(anno.description());
            param.setClazz(clazz);
            return param;

        }

        return null;
    }

    /**
     * Handle all QueryParam annotations
     * @return
     */
    private org.fandanzle.annovtexrest.entity.QueryParam processQueryParam(QueryParam anno, Class clazz){

        if(anno != null){
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("QUERY PARAM");
            System.out.println("Name :" + anno.name());

            org.fandanzle.annovtexrest.entity.QueryParam param = new org.fandanzle.annovtexrest.entity.QueryParam();
            param.setName(anno.name());
            param.setDescription(anno.description());
            param.setClazz(clazz);
            return param;

        }

        return null;
    }

    /**
     * Handle all QueryParam annotations
     * @return
     */
    private org.fandanzle.annovtexrest.entity.PathParam processPathParam(org.fandanzle.annovtexrest.annotation.PathParam anno, Class clazz){

        List<org.fandanzle.annovtexrest.entity.PathParam> list = new ArrayList<>();

        if(anno != null){
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("PATH PARAM");
            System.out.println("Name :" + anno.name());

            org.fandanzle.annovtexrest.entity.PathParam param = new org.fandanzle.annovtexrest.entity.PathParam();
            param.setName(anno.name());
            param.setDescription(anno.description());
            param.setClazz(clazz);
            return param;

        }

        return null;
    }

    /**
     *
     * @param queryParameters
     * @return
     */
    private HashMap<String,List<String>> prepareParameters(MultiMap queryParameters) {

        HashMap<String, List<String>> processedHash = new HashMap<>();
        Set<String> queryNames = queryParameters.names();

        for (String item : queryNames){
            processedHash.put(item, queryParameters.getAll(item));
        }

        return processedHash;

    }

    /**
     * Get all values from vertx
     * @param vertx
     */
    private void handleRequestParams(RoutingContext vertx){
        prepareParameters(vertx.request().formAttributes());
        prepareParameters(vertx.request().headers());
        prepareParameters(vertx.request().params());
        vertx.request().absoluteURI();
        vertx.request().method().toString();
        vertx.getAcceptableContentType();
    }

    /**
     *
     * @param name
     */
    private List<String> getQueryParam(String name){
        List<String> param = prepareParameters(context.request().params()).get(name);
        return param;
    }

    /**
     *
     * @param message
     */
    private void error(String message){

        JsonObject errorCode = new JsonObject().put("error", "There was an error processing your request").put("message", message);
        context.response().setStatusCode(500).end(Json.encodePrettily( errorCode ));

    }
}
