package org.fandanzle.annovtexrest;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;
import org.fandanzle.annovtexrest.annotation.RequestMapping;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;


/**
 * Created by alexb on 28/10/2016.
 */
public class AnnoVtexRest {

    private static Logger logger = Logger.getLogger(AnnoVtexRest.class);

    private Vertx vertx;
    private JsonObject config;

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

        Reflections reflections = new Reflections(packageName);
        // Fetch all classes that have the ProviderTypeAnnotation.class annotation
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(RequestMapping.class);
        // Iterate
        for(Class ii :annotated)
        {
            logger.info(ii.getCanonicalName());
            // Get our Annotation and type check
            Annotation ano = ii.getAnnotation(RequestMapping.class);
            // Check annotation is instance of ProviderTypeAnnotation.class
            if (ano instanceof RequestMapping) {

                /**
                HashMap<String, String> collectIndex = new HashMap<String, String>();
                CollectionDefinition myAnnotation = (CollectionDefinition) ano;
                Method[] methods = ii.getDeclaredMethods();
                Field[] fields = ii.getDeclaredFields();

                logger.info("Class fields : ");
                for (Field field : fields) {
                    logger.info(field.getName());
                    UniqueIndex unique = field.getAnnotation(UniqueIndex.class);
                    if (unique != null) {
                        logger.info("Index to process");
                        logger.info(field.getName());
                        logger.info(unique.indexName());
                        collectIndex.put(field.getName(), unique.indexName());
                    }
                    collectionIndex.put(myAnnotation.collectionName(), collectIndex);
                }
                 **/
            }
        }

        return this;
    }

}
