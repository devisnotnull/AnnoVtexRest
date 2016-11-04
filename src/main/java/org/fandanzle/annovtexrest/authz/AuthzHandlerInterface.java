package org.fandanzle.annovtexrest.authz;

import java.util.List;

/**
 * Created by alexb on 04/11/2016.
 */
public interface AuthzHandlerInterface {

    public Boolean guardCheck(List<String> guardList);

}
