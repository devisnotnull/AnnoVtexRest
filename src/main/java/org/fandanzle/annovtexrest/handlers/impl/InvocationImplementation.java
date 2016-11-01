package org.fandanzle.annovtexrest.handlers.impl;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.fandanzle.annovtexrest.AnnoVtexRest;
import org.fandanzle.annovtexrest.annotation.HeaderParam;
import org.fandanzle.annovtexrest.annotation.QueryParam;
import org.fandanzle.annovtexrest.annotation.RequestMapping;
import org.fandanzle.annovtexrest.entity.PathParam;
import org.fandanzle.annovtexrest.entity.Route;
import org.fandanzle.annovtexrest.handlers.InvocationInterface;
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
        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("ROUTER : " + router);

        Stream<Route> dd = router.stream()
                .filter(
                    f -> f.getUri().equals(context.currentRoute().getPath())
                );

        if(dd.findFirst().isPresent()){

            Stream<Route> dde = router.stream()
                    .filter(
                            f -> f.getUri().equals(context.currentRoute().getPath())
                    );

            dde.forEach(e->{

                System.out.println("__________________________________________________________________________________-");

                for(PathParam pathParam : e.getRequiredPathParams()){
                    String id = context.request().getParam(pathParam.getName());
                    System.out.println("HERE IS OUR ID ");
                    System.out.println(pathParam.getName() + "  : " + id);
                }

                Parameter[] params = e.getParams();
                Object[] objInv = new Object[e.getParams().length];

                for(int i=0; i < e.getParams().length; i++) {

                    System.out.println("PARAM : " + e.getParams()[i]);
                    System.out.println("------------------------------------------------------");
                    System.out.println("name :" + params[i].getName());
                    System.out.println("To String :" + params[i].toString());
                    System.out.println("Is Name Present :" + params[i].isNamePresent());
                    System.out.println("Type name : " + params[i].getAnnotatedType().getType().getTypeName());
                    System.out.println("Class Can name : " + params[i].getClass().getCanonicalName());
                    System.out.println("Type Can Name : " + params[i].getType().getCanonicalName());

                    org.fandanzle.annovtexrest.annotation.PathParam pathParam = params[i].getAnnotation(org.fandanzle.annovtexrest.annotation.PathParam.class);
                    if(processPathParam( pathParam,params[i].getClass() ) != null){

                        if(params[i].getType() == String.class){
                            objInv[i] = "pp";
                        }
                        if(params[i].getType() == Integer.class){
                            objInv[i] = 1;
                        }

                    }

                    QueryParam queryParam = params[i].getAnnotation(QueryParam.class);
                    if(processQueryParam( queryParam , params[i].getClass() ) != null){

                        if(params[i].getType() == String.class){
                            objInv[i] = "qp";
                        }
                        if(params[i].getType() == Integer.class){
                            objInv[i] = 1;
                        }


                    }

                    HeaderParam headerParam = params[i].getAnnotation(HeaderParam.class);
                    if(processHeaderParam( headerParam,params[i].getClass() ) != null){

                        if(params[i].getType() == String.class){
                            objInv[i] = "ip";
                        }
                        if(params[i].getType() == Integer.class){
                            objInv[i] = 1;
                        }
                    }

                    try {

                        // Step 1) Make an object array and store the parameters that you wish
                        // to pass it.
                        // for method1()
                        // Object[] obj={"hello"}; for method1(String str)
                        // Object[] obj={"hello",1}; for method1(String str,int number)
                        // Step 2) Create a class array which will hold the signature of the
                        // method being called.
                        Class<?> paramsInv[] = new Class[objInv.length];
                        for (int uu = 0; uu < objInv.length; uu++) {
                            if (objInv[uu] instanceof Integer) {
                                paramsInv[uu] = Integer.class;
                            } else if (objInv[uu] instanceof String) {
                                paramsInv[uu] = String.class;
                            }
                            // you can do additional checks for other data types if you want.
                        }

                        String methodName = e.getInvokeMethod().getName(); // methodname to be invoked
                        String className = e.getInvokeClazz().getName();// Class name
                        Class<?> cls = Class.forName(className);
                        Object _instance = cls.newInstance();
                        Method myMethod = cls.getDeclaredMethod(methodName, paramsInv);

                        context.response().end(Json.encode(myMethod.invoke(_instance, objInv)));


                    }catch (Exception er){
                        er.printStackTrace();
                    }


                }

            });

        }

        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("URI  : " + context.request().uri() );
        System.out.println("ROUTE PATH  : " + context.currentRoute().getPath() );
        System.out.println("###########################################");
        System.out.println("###########################################");
        System.out.println("###########################################");

        context.response().end("qofrhqu yew9hye orhewo8 ehwohewf oewhfohewf owehfoiwehf owuehi ");

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

        List<String> headerParams = new ArrayList<>();
        List<String> queryParams = new ArrayList<>();
        List<String> routeParams = new ArrayList<>();

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

            Pattern p = Pattern.compile("\\{([^}]*)\\}");
            Matcher m = p.matcher(dd);

            while (m.find()) {
                routeParams.add(m.group(1));
            }

            for(Parameter param : controllerMethod.getParameters()){
                //this.handleParamater(param);
            }

            return controllerMethod.getParameters();

        }
        return null;

    }

    /**
     *
     * @param methodParameter
     */
    private void handleParamater(Parameter methodParameter){

        System.out.println("------------------------------------------------------");
        System.out.println("name :" + methodParameter.getName());
        System.out.println("To String :" + methodParameter.toString());
        System.out.println("Is Name Present :" + methodParameter.isNamePresent());
        System.out.println("Type name : " + methodParameter.getAnnotatedType().getType().getTypeName());
        System.out.println("Class Can name : " + methodParameter.getClass().getCanonicalName());
        System.out.println("Type Can Name : " + methodParameter.getType().getCanonicalName());

        org.fandanzle.annovtexrest.annotation.PathParam pathParam = methodParameter.getAnnotation(org.fandanzle.annovtexrest.annotation.PathParam.class);

        if(processPathParam( pathParam, methodParameter.getClass() ) != null){

        }

        QueryParam queryParam = methodParameter.getAnnotation(QueryParam.class);

        if(processQueryParam( queryParam, methodParameter.getClass() ) != null){

        }

        HeaderParam headerParam = methodParameter.getAnnotation(HeaderParam.class);

        if(processQueryParam( queryParam, methodParameter.getClass() ) != null){

        }

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
}
