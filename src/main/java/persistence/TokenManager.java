package persistence;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import entities.storage.User;
import responses.Responses;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Giorgia Nadizar
 */
public class TokenManager {

  private static final String SECRET = "vtTRsFOUrnzDvZO22z6p6OuG5uN2LZQ3";
  private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
  private static final JWTVerifier VERIFIER = JWT.require(ALGORITHM).build();

  public static String getOneTimeToken(String username, String secret) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    Date dt = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(dt);
    c.add(Calendar.DATE, 1);
    dt = c.getTime();
    return JWT.create()
        .withSubject(username)
        .withExpiresAt(dt)
        .sign(algorithm);
  }

  public static String decodeOneTimeToken(String token) {
    if (token == null || token.length() == 0) {
      Response response = Response.status(Response.Status.UNAUTHORIZED).entity(Responses.MISSING_TOKEN_ERROR_MESSAGE).build();
      throw new NotAuthorizedException(response);
    }
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getSubject();
    } catch (Exception e) {
      Response response = Response.status(Response.Status.UNAUTHORIZED).entity(Responses.INVALID_TOKEN_ERROR_MESSAGE).build();
      throw new NotAuthorizedException(response);
    }
  }

  public static String verifyOneTimeToken(String token, String password) {
    if (token == null || token.length() == 0) {
      Response response = Response.status(Response.Status.UNAUTHORIZED).entity(Responses.MISSING_TOKEN_ERROR_MESSAGE).build();
      throw new NotAuthorizedException(response);
    }
    try {
      Algorithm algorithm = Algorithm.HMAC256(password);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      return jwt.getSubject();
    } catch (Exception e) {
      Response response = Response.status(Response.Status.UNAUTHORIZED).entity(Responses.INVALID_TOKEN_ERROR_MESSAGE).build();
      throw new NotAuthorizedException(response);
    }
  }

  public static String getJWT(User u) {
    Date dt = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(dt);
    c.add(Calendar.DATE, 1);
    dt = c.getTime();
    return JWT.create()
        .withSubject(u.getUsername())
        .withClaim("role", u.getRole())
        .withExpiresAt(dt)
        .sign(ALGORITHM);
  }

  public static HashMap<String, String> decodeJWT(String token) {
    if (token == null || token.length() == 0) {
      Response response = Response.status(Response.Status.UNAUTHORIZED).entity(Responses.MISSING_TOKEN_ERROR_MESSAGE).build();
      throw new NotAuthorizedException(response);
    }
    try {
      DecodedJWT jwt = VERIFIER.verify(token);
      HashMap<String, String> map = new HashMap<String, String>();
      map.put("username", jwt.getSubject());
      map.put("role", jwt.getClaim("role").asString());
      return map;
    } catch (Exception e) {
      Response response = Response.status(Response.Status.UNAUTHORIZED).entity(Responses.INVALID_TOKEN_ERROR_MESSAGE).build();
      throw new NotAuthorizedException(response);
    }
  }

  public static String decodeJWTRole(String token) {
    if (token == null || token.length() == 0) {
      Response response = Response.status(Response.Status.UNAUTHORIZED).entity(Responses.MISSING_TOKEN_ERROR_MESSAGE).build();
      throw new NotAuthorizedException(response);
    }
    try {
      DecodedJWT jwt = VERIFIER.verify(token);
      return jwt.getClaim("role").asString();
    } catch (Exception e) {
      Response response = Response.status(Response.Status.UNAUTHORIZED).entity(Responses.INVALID_TOKEN_ERROR_MESSAGE).build();
      throw new NotAuthorizedException(response);
    }
  }

  public static String decodeJWTUsername(String token) {
    if (token == null || token.length() == 0) {
      Response response = Response.status(Response.Status.UNAUTHORIZED).entity(Responses.MISSING_TOKEN_ERROR_MESSAGE).build();
      throw new NotAuthorizedException(response);
    }
    try {
      DecodedJWT jwt = VERIFIER.verify(token);
      return jwt.getSubject();
    } catch (Exception e) {
      Response response = Response.status(Response.Status.UNAUTHORIZED).entity(Responses.INVALID_TOKEN_ERROR_MESSAGE).build();
      throw new NotAuthorizedException(response);
    }
  }

}
