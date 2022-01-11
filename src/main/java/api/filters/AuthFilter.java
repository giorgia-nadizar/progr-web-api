package api.filters;

import annotations.TokenSecured;
import persistence.TokenManager;
import responses.Responses;

import javax.annotation.Priority;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Map;

@Provider
@TokenSecured
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

  public void filter(ContainerRequestContext request) throws IOException {
    String authHeader = request.getHeaderString(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer")) {
      throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).entity(Responses.MISSING_TOKEN_ERROR_MESSAGE).build());
    }
    String token = authHeader.substring(7);
    Map<String, String> map = TokenManager.decodeJWT(token);
    String username = map.get("username");
    String role = map.get("role");
    if (!role.equals("administrator") && !role.equals("uploader") && !role.equals("consumer")) {
      throw new BadRequestException();
    }
    request.setProperty("username", username);
    request.setProperty("role", role);

  }
}
