package fr.mappy.resource;

import fr.mappy.model.User;
import fr.mappy.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collection;

/**
 * Chercher dans la liste d'un utilisateur le follower, qui a le repo le mieux noté (stars) et avec le plus d'issues ouvertes.
 */
@Path("/")
public class RootResource {

    public static final Logger LOG = LoggerFactory.getLogger(RootResource.class);

    @Inject
    private UserService userService;

    @GET
    @Path("/users")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<User> users(@Context HttpHeaders httpHeaders) throws IOException {
        LOG.debug("Searching for ulrich user...");

        return userService.searchUsersByName("ulrich");
    }

    @Path("/remote")
    @GET
    public String getRemoteAddress(@Context HttpServletRequest request) {
        return request.getRemoteHost();
    }
}
