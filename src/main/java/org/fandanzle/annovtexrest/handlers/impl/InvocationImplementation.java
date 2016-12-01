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

        // Stream our route
        Route e = router.stream()
                .filter(
                        f -> f.getUri().equals(context.currentRoute().getPath()) && f.getMethod().name().equals(context.request().method().name())
                ).findFirst().get();

        // Find our route in the stream
        if(e != null){

            // Set input arrays
            Parameter[] params = e.getParams();
            Object[] objInv = new Object[e.getParams().length];

            for(int i=0; i < (e.getParams().length); i++) {

                //
                // If we have no annotations then we already know to return an error
                if(params[i].getAnnotations().length == 0){
                    error("Invalid controller declaration, A function variable has not been declared properly with an annotation");
                    return;
                }

                //
                // Fetch our PathParam
                // TODO implement class injectors to delegate to Interface enforced classed
                PathParam pathParam = params[i].getAnnotation(PathParam.class);
                if (processPathParam(pathParam, params[i].getClass()) != null) {

                    try {
                        if (params[i].getType() == String.class) {
                            objInv[i] = context.request().getParam(pathParam.name());
                        }
                        else if (params[i].getType() == Integer.class) {
                            objInv[i] = Integer.valueOf(context.request().getParam(pathParam.name()));
                        }
                        else{
                            error("Invalid Path Type : Variable " + pathParam.name() + " is of type " + params[i].getType() + ", It must be either a String or Integer");
                            return;
                        }
                    }catch (Exception exe){
                        exe.printStackTrace();
                        error("Invalid Path Type : Variable " + pathParam.name() + " is of type " + params[i].getType() + ", It must be either a String or Integer");
                        return;
                    }

                }

                //
                // Fetch our QueryParam
                // TODO implement class injectors to delegate to Interface enforced classed
                QueryParam queryParam = params[i].getAnnotation(QueryParam.class);
                if (processQueryParam(queryParam, params[i].getClass()) != null) {

                    try {
                        //
                        if (params[i].getType() == String.class) {
                            objInv[i] = context.request().params().get(queryParam.name());
                        }
                        else if (params[i].getType() == Integer.class) {
                            objInv[i] = Integer.valueOf(context.request().params().get(pathParam.name()));
                        }
                        else{
                            error("Invalid Query Type : Variable " + queryParam.name() + " is of type " + params[i].getType() + ", It must be either a String or Integer");
                            return;
                        }
                    }catch (Exception exe){
                        exe.printStackTrace();
                        error("Invalid Path Type : Variable " + pathParam.name() + " is of type " + params[i].getType() + ", It must be either a String or Integer");
                        return;
                    }

                }


                //
                // Fetch our HeaderParam
                // TODO implement class injectors to delegate to Interface enforced classed
                HeaderParam headerParam = params[i].getAnnotation(HeaderParam.class);
                if (processHeaderParam(headerParam, params[i].getClass()) != null) {

                    try {
                        //
                        if (params[i].getType() == String.class) {
                            objInv[i] = context.request().params().get(headerParam.name());
                        }
                        else{
                            error("Invalid Header Type : Variable " + headerParam.name() + " is of type " + params[i].getType() + ", It must be either a String or Integer");
                            return;
                        }
                    }catch (Exception exe){
                        exe.printStackTrace();
                        error("Invalid Path Type : Variable " + pathParam.name() + " is of type " + params[i].getType() + ", It must be either a String or Integer");
                        return;
                    }

                }

                //
                // Fetch our HeaderParam
                // TODO implement class injectors to delegate to Interface enforced classed
                Form formParam = params[i].getAnnotation(Form.class);
                if (headerParam != null) {

                    try {

                        //
                        if (params[i].getType() == String.class) {
                            objInv[i] = Json.encodePrettily(context.request().formAttributes().entries());
                        }
                        else{
                            error("Invalid Header Type : Variable " + headerParam.name() + " is of type " + params[i].getType() + ", It must be either a String or Integer");
                            return;
                        }
                    }catch (Exception exe){
                        exe.printStackTrace();
                        error("Invalid Path Type : Variable " + pathParam.name() + " is of type " + params[i].getType() + ", It must be either a String or Integer");
                        return;
                    }

                }

                //
                // Fetch our HeaderParam
                // TODO implement class injectors to delegate to Interface enforced classed
                Body bodyParam = params[i].getAnnotation(Body.class);
                if (bodyParam != null) {

                    try {

                        //
                        if (params[i].getType() == String.class) {
                            objInv[i] = context.getBodyAsJson().encodePrettily();
                        }
                        else{
                            error("Invalid Header Type : Variable " + headerParam.name() + " is of type " + params[i].getType() + ", It must be either a String or Integer");
                            return;
                        }
                    }catch (Exception exe){
                        exe.printStackTrace();
                        error("Invalid Path Type : Variable " + pathParam.name() + " is of type " + params[i].getType() + ", It must be either a String or Integer");
                        return;
                    }

                }


                //
                // Fetch our HeaderParam
                // TODO implement class injectors to delegate to Interface enforced classed
                Context contextParam = params[i].getAnnotation(Context.class);
                if (contextParam != null) {

                    if (params[i].getType() == RoutingContext.class) {
                        objInv[i] = (RoutingContext) context;
                    }

                    else if (params[i].getType() == Vertx.class) {
                        objInv[i] = (Vertx) vertx;
                    }

                    else if (params[i].getType() == HttpServerRequest.class) {
                        objInv[i] = (HttpServerRequest) context.request();
                    }

                    else if (params[i].getType() == HttpServerResponse.class) {
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
                // TODO implement injectable serializers
                context.response().putHeader("Content-Type", e.getProduces().name());
                // If is void this will ignore
                // Void checker
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
     * @param controllerClazz
     */
    private void handleController(Class<?> controllerClazz){

        // Get our Annotation and type check
        Annotation ano = controllerClazz.getAnnotation(RequestMapping.class);

        // Check annotation is instance of ProviderTypeAnnotation.class
        if (ano instanceof RequestMapping) {

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
     * @return
     */
    private Parameter[] handleMethod(Method controllerMethod){

        RequestMapping unique = controllerMethod.getAnnotation(RequestMapping.class);

        if (unique != null) {
            String dd = ((RequestMapping) unique).uri()[0] + unique.uri()[0];
            return controllerMethod.getParameters();

        }
        return null;

    }


    /**
     *
     * @param anno
     * @param clazz
     * @return
     */
    private org.fandanzle.annovtexrest.entity.HeaderParam processHeaderParam(HeaderParam anno, Class clazz){

        if(anno != null){
            org.fandanzle.annovtexrest.entity.HeaderParam param = new org.fandanzle.annovtexrest.entity.HeaderParam();
            param.setName(anno.name());
            param.setDescription(anno.description());
            param.setClazz(clazz);
            return param;

        }

        return null;
    }

    /**
     *
     * @param anno
     * @param clazz
     * @return
     */
    private org.fandanzle.annovtexrest.entity.QueryParam processQueryParam(QueryParam anno, Class clazz){

        if(anno != null){
            org.fandanzle.annovtexrest.entity.QueryParam param = new org.fandanzle.annovtexrest.entity.QueryParam();
            param.setName(anno.name());
            param.setDescription(anno.description());
            param.setClazz(clazz);
            return param;

        }

        return null;
    }


    /**
     *
     * @param anno
     * @param clazz
     * @return
     */
    private org.fandanzle.annovtexrest.entity.PathParam processPathParam(PathParam anno, Class clazz){

        List<org.fandanzle.annovtexrest.entity.PathParam> list = new ArrayList<>();

        if(anno != null){
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
     * @param anno
     * @param clazz
     * @return
     */
    private org.fandanzle.annovtexrest.entity.PathParam processCookieParam(PathParam anno, Class clazz){

        List<org.fandanzle.annovtexrest.entity.PathParam> list = new ArrayList<>();

        if(anno != null){
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
     * @param anno
     * @param clazz
     * @return
     */
    private org.fandanzle.annovtexrest.entity.PathParam processFormParam(PathParam anno, Class clazz){

        List<org.fandanzle.annovtexrest.entity.PathParam> list = new ArrayList<>();

        if(anno != null){
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
