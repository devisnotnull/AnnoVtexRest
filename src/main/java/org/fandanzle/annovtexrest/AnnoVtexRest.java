package org.fandanzle.annovtexrest;

import io.vertx.core.Vertx;

import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;
import org.fandanzle.annovtexrest.annotation.HeaderParam;
import org.fandanzle.annovtexrest.annotation.PathParam;
import org.fandanzle.annovtexrest.annotation.QueryParam;
import org.fandanzle.annovtexrest.annotation.RequestMapping;
import org.fandanzle.annovtexrest.entity.Route;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
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

    /**
     *
     * @param packageName
     * @return
     */
    public AnnoVtexRest build(String packageName) {

        System.out.println("============================================");
        System.out.println(packageName);
        System.out.println("============================================");

        Reflections reflections = new Reflections(packageName);
        // Fetch all classes that have the ProviderTypeAnnotation.class annotation
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(RequestMapping.class);
        // Iterate
        for(Class ii :annotated)
        {

            System.out.println("============================================");
            System.out.println(ii.getCanonicalName());
            System.out.println("============================================");

            // Get our Annotation and type check
            Annotation ano = ii.getAnnotation(RequestMapping.class);
            System.out.println(ano);
            // Check annotation is instance of ProviderTypeAnnotation.class
            if (ano instanceof RequestMapping) {

                Route route = new Route();

                System.out.println("============================================");
                System.out.println(((RequestMapping) ano).uri()[0]);
                System.out.println("============================================");

                Method[] methods = ii.getDeclaredMethods();
                Field[] fields = ii.getDeclaredFields();

                System.out.println("Class methods : ");

                for (Method method : methods) {

                    List<String> headerParams = new ArrayList<>();
                    List<String> queryParams = new ArrayList<>();
                    List<String> routeParams = new ArrayList<>();

                    System.out.println(method.getName());
                    RequestMapping unique = method.getAnnotation(RequestMapping.class);

                    if (unique != null) {

                        System.out.println("****************************************************************************************************************");
                        System.out.println("Declared functions");
                        System.out.println(unique.consumes());
                        System.out.println(unique.produces());
                        System.out.println(unique.description());
                        System.out.println(unique.method()[0]);
                        System.out.println(unique.noCache());
                        System.out.println( ((RequestMapping) ano).uri()[0] + unique.uri()[0] );

                        String dd =  ((RequestMapping) ano).uri()[0] + unique.uri()[0];

                        Pattern p = Pattern.compile("\\{([^}]*)\\}");
                        Matcher m = p.matcher(dd);

                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
                        System.out.println("MATCHER ");
                        while (m.find()) {
                            routeParams.add( m.group(1) );
                        }
                        System.out.println("****************************************************************************************************************");


                    }

                    Parameter[] dd = method.getParameters();

                    for(Parameter i : dd){

                        System.out.println("------------------------------------------------------");
                        System.out.println("name :" + i.getName());
                        System.out.println("To String :" + i.toString());
                        System.out.println("Is Name Present :" + i.isNamePresent());
                        System.out.println("Type name : " + i.getAnnotatedType().getType().getTypeName());
                        System.out.println("Class Can name : " + i.getClass().getCanonicalName());
                        System.out.println("Type Can Name : " + i.getType().getCanonicalName());


                        PathParam pathParam = i.getAnnotation(PathParam.class);

                        if(pathParam != null){
                            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("PATH PARAM");
                            System.out.println("Name :" + pathParam.name());
                        }

                        QueryParam queryParam = i.getAnnotation(QueryParam.class);

                        if(queryParam != null){
                            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("QUERY PARAM");
                            System.out.println("Name :" + queryParam.name());
                        }


                        HeaderParam headerParam = i.getAnnotation(HeaderParam.class);

                        if(headerParam != null){
                            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("HEADER PARAM");
                            System.out.println("Name :" + headerParam.name());
                        }

                    }
                }

            }
        }

        return this;
    }

}
