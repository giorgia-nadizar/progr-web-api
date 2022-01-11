package api.filters;

/*
 * @author Giorgia Nadizar
 */

import annotations.ConsumerOnly;
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
@ConsumerOnly
@Priority(Priorities.AUTHORIZATION)
public class ConsumerFilter implements ContainerRequestFilter {

  public void filter(ContainerRequestContext request) throws IOException {
    if (!request.getProperty("role").equals("consumer")) {
      throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity(Responses.FORBIDDEN_ERROR_MESSAGE).build());
    }
  }
}
