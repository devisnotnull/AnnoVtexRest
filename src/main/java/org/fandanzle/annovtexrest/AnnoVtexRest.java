package org.fandanzle.annovtexrest;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.apache.log4j.Logger;
import org.fandanzle.annovtexrest.annotation.HeaderParam;
import org.fandanzle.annovtexrest.annotation.PathParam;
import org.fandanzle.annovtexrest.annotation.QueryParam;
import org.fandanzle.annovtexrest.annotation.RequestMapping;
import org.fandanzle.annovtexrest.entity.Route;
import org.fandanzle.annovtexrest.handlers.InvocationInterface;
import org.fandanzle.annovtexrest.handlers.impl.DefaultMethodInvocationHandler;
import org.reflections.Reflections;

import io.vertx.ext.web.RoutingContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by alexb on 28/10/2016.
 */
public class AnnoVtexRest {

    private static Logger logger = Logger.getLogger(AnnoVtexRest.class);
    private Vertx vertx;
    private JsonObject config;
    private List<String> uriList = new ArrayList<>();
    private Router router;

    /**
     *
     * @param vertx
     */
    public AnnoVtexRest(Vertx vertx){
        this.vertx = vertx;
    }

    /**
     *
     * @param vertx
     * @param config
     */
    public AnnoVtexRest(Vertx vertx, JsonObject config){
        this.vertx = vertx;
    }

    public static List<Route> routers = new ArrayList<>();

    /**
     *
     * @param packageName
     * @return
     */
    public AnnoVtexRest build(String packageName) throws Exception {

        System.out.println("============================================");
        System.out.println("Package Name");
        System.out.println(packageName);
        System.out.println("============================================");

        List<Route> routeList = new ArrayList<>();

        Reflections reflections = new Reflections(packageName);
        // Fetch all classes that have the ProviderTypeAnnotation.class annotation
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(RequestMapping.class);
        // Iterate
        for(Class<?> ii :annotated)
        {

            Class<?> controllerClazz = ii;

            System.out.println("============================================");
            System.out.println("Caniconial Name");
            System.out.println(ii.getCanonicalName());
            System.out.println("============================================");

            router = Router.router(vertx);

            // Get our Annotation and type check
            Annotation ano = ii.getAnnotation(RequestMapping.class);
            System.out.println(ano);
            // Check annotation is instance of ProviderTypeAnnotation.class
            if (ano instanceof RequestMapping) {

                System.out.println("============================================");
                System.out.println("Request Mapping URI");
                System.out.println(((RequestMapping) ano).uri()[0]);
                System.out.println("============================================");

                Method[] methods = ii.getDeclaredMethods();

                System.out.println("Class methods : ");

                for (Method method : methods) {

                    List<String> headerParams = new ArrayList<>();
                    List<String> queryParams = new ArrayList<>();
                    List<String> routeParams = new ArrayList<>();

                    System.out.println(method.getName());
                    RequestMapping unique = method.getAnnotation(RequestMapping.class);

                    if (unique != null) {

                        System.out.println("------------------------------------------------------");
                        System.out.println("Method name : " + method.getName());

                        Route route = new Route();

                        route.setInvokeClazz(controllerClazz);
                        route.setInvokeMethod(method);
                        route.setUri(((RequestMapping) ano).uri()[0] + unique.uri()[0]);
                        route.setProduces(unique.consumes());
                        route.setProduces(unique.produces());
                        route.setDescription(unique.description());
                        route.setMethod(unique.method()[0]);

                        String dd =  ((RequestMapping) ano).uri()[0] + unique.uri()[0];

                        router.route(((RequestMapping) ano).uri()[0] + unique.uri()[0]).handler(InvocationInterface.create());

                        Pattern p = Pattern.compile("\\{([^}]*)\\}");
                        Matcher m = p.matcher(dd);

                        while (m.find()) {
                            routeParams.add( m.group(1) );
                        }

                        Parameter[] pp = method.getParameters();

                        route.setParams(pp);

                        for(int i=0; i < pp.length; i++){

                            if(pp[i].getType() == Integer.class || pp[i].getType() == String.class ) {

                                PathParam pathParam = pp[i].getAnnotation(PathParam.class);

                                if (processPathParam(pathParam, pp[i].getClass()) != null) {
                                    org.fandanzle.annovtexrest.entity.PathParam pathParam1 = new org.fandanzle.annovtexrest.entity.PathParam();
                                    pathParam1.setClazz(pp[i].getType().getClass());
                                    pathParam1.setName(pathParam.name());
                                    System.out.println(route.getRequiredPathParams());
                                    route.getRequiredPathParams().add(pathParam1);
                                }

                                QueryParam queryParam = pp[i].getAnnotation(QueryParam.class);

                                if (processQueryParam(queryParam, pp[i].getClass()) != null) {
                                    org.fandanzle.annovtexrest.entity.QueryParam queryParam1 = new org.fandanzle.annovtexrest.entity.QueryParam();
                                    queryParam1.setClazz(pp[i].getType().getClass());
                                    queryParam1.setName(queryParam.name());
                                    queryParam1.setRequired(queryParam.required());
                                    route.getRequiredQueryParams().add(queryParam1);
                                }

                                HeaderParam headerParam = pp[i].getAnnotation(HeaderParam.class);

                                if (processHeaderParam(headerParam, pp[i].getClass()) != null) {
                                    org.fandanzle.annovtexrest.entity.HeaderParam headerParam1 = new org.fandanzle.annovtexrest.entity.HeaderParam();
                                    headerParam1.setClazz(pp[i].getType().getClass());
                                    headerParam1.setName(headerParam.name());
                                    route.getRequiredHeaders().add(headerParam1);

                                }
                            }
                            else throw new Exception("Invalid parameter type, You can only use String and Interger types");
                        }

                        routers.add(route);

                    }

                }

            }

        }

        return this;
    }

    /**
     *
     * @return
     */
    public Router getRouter(){
        return this.router;
    }


    /**
     *
     * @param packageName
     * @return
     */
    public AnnoVtexRest build1(String packageName) {

        System.out.println("============================================");
        System.out.println("Package Name");
        System.out.println(packageName);
        System.out.println("============================================");

        Reflections reflections = new Reflections(packageName);
        // Fetch all classes that have the ProviderTypeAnnotation.class annotation
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(RequestMapping.class);
        // Iterate
        for(Class<?> ii :annotated) {
            this.handleController(ii);
        }

        return this;
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

        PathParam pathParam = methodParameter.getAnnotation(PathParam.class);

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
    private org.fandanzle.annovtexrest.entity.PathParam processPathParam(PathParam anno, Class clazz){

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

    private void handleGetAllClients(RoutingContext context) {

    }

}
