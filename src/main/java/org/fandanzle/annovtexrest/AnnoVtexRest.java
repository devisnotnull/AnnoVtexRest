package org.fandanzle.annovtexrest;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.log4j.Logger;
import org.fandanzle.annovtexrest.annotation.*;
import org.fandanzle.annovtexrest.annotation.auth.Guard;
import org.fandanzle.annovtexrest.authz.AuthzHandlerInterface;
import org.fandanzle.annovtexrest.entity.Route;
import org.fandanzle.annovtexrest.handlers.InvocationInterface;
import org.reflections.Reflections;
import io.vertx.ext.web.RoutingContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;

/**
 * Created by alexb on 28/10/2016.
 */
public class AnnoVtexRest {

    private HashMap<MimeType, Class<?>> marshallerConfig = new HashMap<>();
    private HashMap<Class<?>, Class<?>> typeProcessorConfig = new HashMap<>();
    private HashMap<Annotation, Class<?>> annotationProcessorConfig = new HashMap<>();


    private static final Map<Class<?>, Function<String, Object>> adapters = new HashMap<>();

    private static Logger logger = Logger.getLogger(AnnoVtexRest.class);
    private Vertx vertx;
    private JsonObject config;
    private List<String> uriList = new ArrayList<>();
    private Router router;
    private List<Route> routes = new ArrayList<>();
    public static List<Route> routers = new ArrayList<>();

    /**
     *
     * @param vertx
     */
    public AnnoVtexRest(Vertx vertx){
        this.vertx = vertx;
    }

    /**
     * Set the authentication handler for Annotex instance
     *
     * @param handleAuthz
     * @return
     */
    public AnnoVtexRest setAuthzHandler(AuthzHandlerInterface handleAuthz){

        return this;
    }

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
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        // Fetch the Vert.x router
        router = Router.router(vertx);
        // Iterate all classes with controller
        // TODO Seperate out all this shit into seperate functions !!!!
        // TODO This is messy as shit
        for(Class<?> ii :annotated)
        {

            Class<?> controllerClazz = ii;

            // Get our Annotation and type check
            Annotation ano = ii.getAnnotation(Controller.class);

            // Check annotation is instance of ProviderTypeAnnotation.class
            if (ano instanceof Controller) {

                Method[] methods = ii.getDeclaredMethods();

                for (Method method : methods) {

                    /**
                     * Process @RequestMapping annotation for class functions
                     */
                    Parameter[] pp = method.getParameters();
                    RequestMapping unique = method.getAnnotation(RequestMapping.class);

                    // Handle our guards only if that method has a @RequestMapping annotation
                    //
                    List<String> guardsList = new ArrayList<>();
                    Guard guard = method.getAnnotation(Guard.class);

                    // Iterate Guard scopes
                    if (guard != null){
                        String[] guards = guard.scopes();
                        for(int i=0; i < guards.length; i++){
                            String gg = guards[i];
                            guardsList.add(gg);
                        }
                    }

                    if (unique != null) {

                        String uri =  ((Controller) ano).uri()[0] + unique.uri()[0];
                        // TODO delegate to seperate function
                        // We use an entity to store information on a route,
                        // this info will be used with swagger/raml for self documentation
                        Route route = new Route();
                        route.setInvokeClazz(controllerClazz);
                        route.setInvokeMethod(method);
                        route.setUri(uri);
                        route.setProduces(unique.consumes());
                        route.setProduces(unique.produces());
                        route.setDescription(unique.description());
                        route.setMethod(unique.method()[0]);
                        route.setParams(pp);

                        // Handle different HTTP request types
                        // TODO implement all HTTP request types
                        // TODO delegate to seperate function
                        if(unique.method()[0] == RequestMethods.GET){
                            router.get(uri).handler(InvocationInterface.create());
                        }else if(unique.method()[0] == RequestMethods.POST) {
                            // Required for Json uploads
                            router.route().handler(BodyHandler.create());
                            router.post(uri).handler(e->{
                                e.next();
                            });
                            router.post(uri).handler(InvocationInterface.create());
                            router.options(uri).handler(InvocationInterface.create());
                        }else if(unique.method()[0] == RequestMethods.DELETE) {
                            router.delete(uri).handler(InvocationInterface.create());
                        }else if(unique.method()[0] == RequestMethods.OPTIONS) {
                            router.options(uri).handler(InvocationInterface.create());
                        }

                        // Iterate all parameters of method, We need to evaulate the params
                        // to work out the objects to inject into functions
                        for(int i=0; i < pp.length; i++){

                            if(pp[i].getAnnotations().length > 0) {

                                if (pp[i].getType() == Integer.class || pp[i].getType() == String.class) {

                                    // Path params to be pulled for URI
                                    PathParam pathParam = pp[i].getAnnotation(PathParam.class);
                                    if (processPathParam(pathParam, pp[i].getClass()) != null) {
                                        org.fandanzle.annovtexrest.entity.PathParam pathParam1 = new org.fandanzle.annovtexrest.entity.PathParam();
                                        pathParam1.setClazz(pp[i].getType().getClass());
                                        pathParam1.setName(pathParam.name());
                                        System.out.println(route.getRequiredPathParams());
                                        route.getRequiredPathParams().add(pathParam1);
                                    }

                                    // Query params to be pulled from the URI
                                    QueryParam queryParam = pp[i].getAnnotation(QueryParam.class);
                                    if (processQueryParam(queryParam, pp[i].getClass()) != null) {
                                        org.fandanzle.annovtexrest.entity.QueryParam queryParam1 = new org.fandanzle.annovtexrest.entity.QueryParam();
                                        queryParam1.setClazz(pp[i].getType().getClass());
                                        queryParam1.setName(queryParam.name());
                                        queryParam1.setRequired(queryParam.required());
                                        route.getRequiredQueryParams().add(queryParam1);
                                    }

                                    // Header params to be pulled from URI
                                    HeaderParam headerParam = pp[i].getAnnotation(HeaderParam.class);
                                    if (processHeaderParam(headerParam, pp[i].getClass()) != null) {
                                        org.fandanzle.annovtexrest.entity.HeaderParam headerParam1 = new org.fandanzle.annovtexrest.entity.HeaderParam();
                                        headerParam1.setClazz(pp[i].getType().getClass());
                                        headerParam1.setName(headerParam.name());
                                        route.getRequiredHeaders().add(headerParam1);
                                    }

                                    // TODO implement cookie annotation, I know cookies are pulled from the header,
                                    // But having seperate logic keeps it cleaner

                                } else
                                    throw new Exception("Invalid parameter type, You can only use String and Interger types");
                            }
                        }
                        // Add to class scoped var
                        routes.add(route);
                        routers.add(route);

                    }
                }
            }
        }

        router.route().handler(ctx -> {
            ctx.response().putHeader(HttpHeaders.CONTENT_TYPE,"application/json; charset=utf-8").end("{error:'404 not found'}");
        });

        return this;
    }

    /**
     * Get the Vert.x router used in context of this instance
     * @return
     */
    public Router getRouter(){
        return this.router;
    }

    /**
     * Return list of Route entities
     * @return
     */
    public List<Route> getRoutes(){
        return this.routes;
    }

    /**
     * Handle all HeaderParam annotations
     * @return
     */
    private org.fandanzle.annovtexrest.entity.HeaderParam processHeaderParam(HeaderParam anno, Class clazz){

        if(anno != null){
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
     * Turn Vert.x native @{MultiMap} into a HashMap
     * @param queryParameters MultiMap from vert.x
     * @return HashMap
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
     *
     * @param mime
     * @param marshaller
     * @param handler
     */
    public void registerMarshaller(MimeType mime, Class<?> marshaller) {
        marshallerConfig.put(mime, marshaller);
    }

    /**
     *
     * @param type
     */
    public void registerTypeProcessor(Class<?> type, Class<?> processor) {
        typeProcessorConfig.put(type,processor);
    }

    /**
     *
     * @param annotation
     * @param handler
     * @param <T>
     */
    public void registerAnnotationProcessor(Annotation annotation,Class<?> processor) {
        annotationProcessorConfig.put(annotation, processor);
    }

}
