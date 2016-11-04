package org.fandanzle.annovtexrest.authz.oauth2;

import io.vertx.core.impl.verticle.JavaSourceContext;
import io.vertx.core.json.JsonObject;
import org.fandanzle.annovtexrest.authz.AuthzHandlerInterface;

import java.util.List;

/**
 * Created by alexb on 04/11/2016.
 */
public class Oauth2AuthzImplementation implements AuthzHandlerInterface {

    /**
     *
     */
    private JsonObject oauth2Config = new JsonObject();

    /**
     *
     * @param guardList
     * @return
     */
    public Boolean guardCheck(List<String> guardList){

        return true;

    }

}
