package api.filters;

/*
 * @author Giorgia Nadizar
 */

import annotations.AdministratorOnly;
import responses.Responses;

import javax.annotation.Priority;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@AdministratorOnly
@Priority(Priorities.AUTHORIZATION)
public class AdministratorFilter implements ContainerRequestFilter {

  public void filter(ContainerRequestContext request) throws IOException {
    if (!request.getProperty("role").equals("administrator")) {
      throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity(Responses.FORBIDDEN_ERROR_MESSAGE).build());
    }
  }
}
