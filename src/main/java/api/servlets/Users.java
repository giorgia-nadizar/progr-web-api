package api.servlets;

import annotations.TokenSecured;
import entities.proxies.LoggedUserProxy;
import entities.proxies.PasswordProxy;
import entities.proxies.UserProxy;
import entities.proxies.UserProxyOutput;
import entities.storage.Administrator;
import entities.storage.Consumer;
import entities.storage.Uploader;
import entities.storage.User;
import exceptions.InvalidPasswordException;
import exceptions.InvalidUserException;
import org.apache.commons.text.RandomStringGenerator;
import persistence.*;
import responses.Responses;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import static entities.storage.User.isAuthorized;

/*
 * @author Giorgia Nadizar
 */
@Path("/user")
public class Users {

  @GET
  @Path("/isUnique/{username}")
  public Boolean isUnique(@PathParam("username") String username) {
    return (username == null || !UserHelper.exists(User.class, username));
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/login/{username}/{password}")
  public LoggedUserProxy login(@PathParam("username") String username, @PathParam("password") String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
    if (username == null || password == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    User u = UserHelper.getByUsername(User.class, username);
    if (u == null || !u.equals(password)) {
      throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).entity(Responses.WRONG_CREDENTIALS_ERROR_MESSAGE).build());
    }
    return new LoggedUserProxy(u, TokenManager.getJWT(u));
  }

  @PATCH
  @TokenSecured
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/changePassword")
  public UserProxyOutput changePassword(@Context HttpServletRequest request, PasswordProxy p) throws NoSuchAlgorithmException, InvalidKeySpecException {
    if (p == null || p.getOldPassword() == null || p.getNewPassword() == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String username = (String) request.getAttribute("username");
    User u = UserHelper.getByUsername(User.class, username);
    if (!u.equals(p.getOldPassword())) {
      throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).entity(Responses.WRONG_CREDENTIALS_ERROR_MESSAGE).build());
    }
    try {
      u.changePassword(p.getNewPassword());
      UserHelper.saveNow(u);
      return new UserProxyOutput(u);
    } catch (InvalidPasswordException e) {
      Response response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
      throw new BadRequestException(response);
    }

  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/")
  public UserProxyOutput create(UserProxy newUser, @Context HttpServletRequest request) {
    if (newUser == null || newUser.getUsername() == null || newUser.getUsername().length() == 0) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.NO_USER_ERROR_MESSAGE).build());
    }
    if (UserHelper.exists(User.class, newUser.getUsername())) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.USER_ALREADY_EXISTING_ERROR_MESSAGE).build());
    }
    if (newUser.getRole().equals("consumer")) {
      try {
        Consumer c = new Consumer(newUser);
        ConsumerHelper.saveNow(c);
        return new UserProxyOutput(c);
      } catch (InvalidUserException ex) {
        Response response = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        throw new BadRequestException(response);
      } catch (NoSuchAlgorithmException ex) {
        throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
      } catch (InvalidKeySpecException ex) {
        throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
      }
    } else {
      String authHeader = request.getHeader("Authorization");
      if (authHeader == null || !authHeader.startsWith("Bearer")) {
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).entity(Responses.MISSING_TOKEN_ERROR_MESSAGE).build());
      }
      String token = authHeader.substring(7);
      String role = TokenManager.decodeJWTRole(token);
      if (!isAuthorized(role, newUser.getRole())) {
        throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity(Responses.FORBIDDEN_ERROR_MESSAGE).build());
      }
      if (newUser.getRole().equals("uploader")) {
        try {
          Uploader u = new Uploader(newUser);
          UploaderHelper.saveNow(u);
          return new UserProxyOutput(u);
        } catch (InvalidUserException ex) {
          Response response = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
          throw new BadRequestException(response);
        } catch (NoSuchAlgorithmException ex) {
          throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
        } catch (InvalidKeySpecException ex) {
          throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
        }
      } else if (newUser.getRole().equals("administrator")) {
        try {
          Administrator a = new Administrator(newUser);
          AdministratorHelper.saveNow(a);
          return new UserProxyOutput(a);
        } catch (InvalidUserException ex) {
          Response response = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
          throw new BadRequestException(response);
        } catch (NoSuchAlgorithmException ex) {
          throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
        } catch (InvalidKeySpecException ex) {
          throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
        }
      } else {
        throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.ROLE_NOT_FOUND_MESSAGE).build());
      }
    }

  }

  @GET
  @TokenSecured
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{username}")
  public UserProxyOutput getUser(@PathParam("username") String username, @Context HttpServletRequest request) {
    if (username == null) {
      throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).entity(Responses.USER_NOT_FOUND_MESSAGE).build());
    }
    String user = (String) request.getAttribute("username");
    String role = (String) request.getAttribute("role");
    User u = UserHelper.getByUsername(User.class, username);
    if (u == null) {
      throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).entity(Responses.USER_NOT_FOUND_MESSAGE).build());
    }
    if (!user.equals(username) && !isAuthorized(role, u.getRole())) {
      throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity(Responses.FORBIDDEN_ERROR_MESSAGE).build());
    }
    return new UserProxyOutput(u);
  }

  @PATCH
  @TokenSecured
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/")
  public UserProxyOutput edit(UserProxy user, @Context HttpServletRequest request) {
    if (user == null || user.getUsername() == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String username = (String) request.getAttribute("username");
    String role = (String) request.getAttribute("role");
    if (!user.getUsername().equals(username) && !isAuthorized(role, user.getRole())) {
      throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity(Responses.FORBIDDEN_ERROR_MESSAGE).build());
    }
    User us = UserHelper.getByUsername(User.class, user.getUsername());
    if (us == null || !us.getRole().equals(user.getRole())) {
      throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).entity(Responses.USER_NOT_FOUND_MESSAGE).build());
    }
    if (user.getRole().equals("consumer")) {
      try {
        Consumer c = (Consumer) us;
        c.update(user.getEmail(), user.getFullName());
        ConsumerHelper.saveNow(c);
        return new UserProxyOutput(c);
      } catch (InvalidUserException e) {
        Response response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        throw new BadRequestException(response);
      }
    } else if (user.getRole().equals("uploader")) {
      Uploader u = (Uploader) us;
      try {
        u.update(user.getEmail(), user.getFullName(), user.getLogo());
        UploaderHelper.saveNow(u);
        return new UserProxyOutput(u);
      } catch (InvalidUserException e) {
        Response response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        throw new BadRequestException(response);
      }
    } else if (user.getRole().equals("administrator")) {
      Administrator a = (Administrator) us;
      try {
        a.update(user.getEmail(), user.getFullName());
        AdministratorHelper.saveNow(a);
        return new UserProxyOutput(a);
      } catch (InvalidUserException e) {
        Response response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        throw new BadRequestException(response);
      }
    } else {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.ROLE_NOT_FOUND_MESSAGE).build());
    }
  }

  @DELETE
  @TokenSecured
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{username}")
  public String delete(@PathParam("username") String username, @Context HttpServletRequest request) {
    if (username == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String user = (String) request.getAttribute("username");
    String role = (String) request.getAttribute("role");
    User u = UserHelper.getByUsername(User.class, username);
    if (u == null) {
      throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).entity(Responses.USER_NOT_FOUND_MESSAGE).build());
    }
    if (u.getUsername().equals(user) || !isAuthorized(role, u.getRole())) {
      throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity(Responses.FORBIDDEN_ERROR_MESSAGE).build());
    }
    if (u.getRole().equals("consumer")) {
      FileHelper.deleteByConsumer(u.getUsername());
    } else if (u.getRole().equals("uploader")) {
      FileHelper.deleteByUploader(u.getUsername());
    }
    UserHelper.deleteByUsername(User.class, username);
    return "{}";
  }

  @GET
  @TokenSecured
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/getUsers/{role}")
  public List<UserProxyOutput> getUsers(@Context HttpServletRequest request, @PathParam("role") String role) {
    if (role == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String userRole = (String) request.getAttribute("role");
    if (!role.equals("consumer") && !role.equals("uploader") && !role.equals("administrator")) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.ROLE_NOT_FOUND_MESSAGE).build());
    }
    if (!isAuthorized(userRole, role)) {
      throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity(Responses.FORBIDDEN_ERROR_MESSAGE).build());
    }
    return UserHelper.getAllUsers(role);

  }

  @GET
  @TokenSecured
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/getUsernames/{role}")
  public List<String> getUsernames(@Context HttpServletRequest request, @PathParam("role") String role) {
    if (role == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String userRole = (String) request.getAttribute("role");
    if (!role.equals("consumer") && !role.equals("uploader") && !role.equals("administrator")) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.ROLE_NOT_FOUND_MESSAGE).build());
    }
    if (!isAuthorized(userRole, role)) {
      throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity(Responses.FORBIDDEN_ERROR_MESSAGE).build());
    }
    return UserHelper.getAllUsernames(role);
  }

  @GET
  @Path("passwordRecovery/{username}")
  public String passwordRecovery(@PathParam("username") String username) {
    if (username == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    User u = UserHelper.getByUsername(User.class, username);
    if (u == null) {
      throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).entity(Responses.USER_NOT_FOUND_MESSAGE).build());
    }
    try {
      EmailSender.passwordRecovery(u.getEmail(), TokenManager.getOneTimeToken(u.getUsername(), new String(u.getPassword())));
    } catch (Exception e) {
      throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
    }
    return "{}";
  }

  @GET
  @Path("passwordReset/{token}")
  public String passwordReset(@PathParam("token") String token) {
    if (token == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String username = TokenManager.decodeOneTimeToken(token);
    User u = UserHelper.getByUsername(User.class, username);
    if (u == null) {
      throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).entity(Responses.USER_NOT_FOUND_MESSAGE).build());
    }
    if (!TokenManager.verifyOneTimeToken(token, new String(u.getPassword())).equals(username)) {
      throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).entity(Responses.USER_NOT_FOUND_MESSAGE).build());
    }
    RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
    String password = pwdGenerator.generate(8);
    try {
      u.changePassword(password);
      UserHelper.saveNow(u);
      EmailSender.passwordReset(u.getEmail(), password);
      return "Password successfully reset, the new password was sent to your email address.";
    } catch (Exception ex) {
      throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
    }
  }
}
