package api.servlets;

import annotations.AdministratorOnly;
import annotations.ConsumerOnly;
import annotations.TokenSecured;
import annotations.UploaderOnly;
import entities.proxies.*;
import entities.storage.Consumer;
import entities.storage.File;
import entities.storage.Uploader;
import exceptions.InvalidFileException;
import exceptions.InvalidUserException;
import org.apache.commons.text.RandomStringGenerator;
import persistence.*;
import responses.Responses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * @author Giorgia Nadizar
 */
@Path("/file")
public class Files {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

  @GET
  @TokenSecured
  @ConsumerOnly
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/getMyUploaders")
  public List<UserProxyOutput> getMyUploaders(@Context HttpServletRequest request) {
    String username = (String) request.getAttribute("username");
    return FileHelper.uploaderForConsumer(username);
  }

  @GET
  @TokenSecured
  @ConsumerOnly
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/getFilesForConsumer/{uploader}")
  public List<FileProxyOutputConsumer> getFilesForConsumer(@Context HttpServletRequest request, @PathParam("uploader") String uploader) {
    if (uploader == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String username = (String) request.getAttribute("username");
    return FileHelper.sortedFilesUploaderConsumer(uploader, username);
  }

  @GET
  @TokenSecured
  @UploaderOnly
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/getFilesForUploader/{consumer}")
  public List<FileProxyOutputUploader> getFilesForUploader(@Context HttpServletRequest request, @PathParam("consumer") String consumer) {
    if (consumer == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String username = (String) request.getAttribute("username");
    return FileHelper.filterByConsumerUploader(consumer, username);
  }

  @POST
  @TokenSecured
  @UploaderOnly
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/")
  public String uploadFile(@Context HttpServletRequest request, FileProxy f) {
    if (f == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String username = (String) request.getAttribute("username");
    try {
      File file = new File(f, username);
      FileHelper.saveNow(file);
      Uploader u = UploaderHelper.getByUsername(Uploader.class, username);
      Consumer c = ConsumerHelper.getByUsername(Consumer.class, f.getConsumer());
      try {
        EmailSender.notifyFileUpload(file.getNome(), u.getFullName(), c.getEmail(), u.getUsername() + "/" + file.getId() + "/" + TokenManager.getJWT(c));
      } catch (Exception e) {
        FileHelper.delete(file.getId(), username);
        throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
      }
      return file.getId().toString();
    } catch (InvalidFileException ex) {
      Response response = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
      throw new BadRequestException(response);
    }

  }

  @DELETE
  @TokenSecured
  @UploaderOnly
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  public String deleteFile(@Context HttpServletRequest request, @PathParam("id") Long id) {
    if (id == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String username = (String) request.getAttribute("username");
    File f = FileHelper.get(id, username);
    if (f == null) {
      throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).entity(Responses.FILE_NOT_FOUND_MESSAGE).build());
    }
    if (!f.getUploader().getName().equals(username)) {
      throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity(Responses.FORBIDDEN_ERROR_MESSAGE).build());
    }
    FileHelper.delete(id, username);
    return "{}";
  }

  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/{uploader}/{id}/{token}")
  public byte[] getFile(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("uploader") String uploader, @PathParam("id") Long id, @PathParam("token") String token) {
    if (token == null || token.length() == 0) {
      throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).entity(Responses.MISSING_TOKEN_ERROR_MESSAGE).build());
    }
    Map<String, String> map = TokenManager.decodeJWT(token);
    request.setAttribute("username", map.get("username"));
    request.setAttribute("role", map.get("role"));
    return getFileAuthHeader(request, response, uploader, id);
  }

  @GET
  @TokenSecured
  @Path("/{uploader}/{id}")
  public byte[] getFileAuthHeader(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("uploader") String uploader, @PathParam("id") Long id) {
    if (uploader == null || id == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String role = (String) request.getAttribute("role");
    String username = (String) request.getAttribute("username");
    File f = FileHelper.get(id, uploader);
    if (f == null) {
      throw new NotFoundException(Response.status(Response.Status.NOT_FOUND).entity(Responses.FILE_NOT_FOUND_MESSAGE).build());
    }
    if (!((role.equals("consumer") && username.equals(f.getConsumerUsername()))
        || (role.equals("uploader") && username.equals(f.getUploaderUsername())))) {
      throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity(Responses.FORBIDDEN_ERROR_MESSAGE).build());
    }
    response.setContentType(f.getType());
    response.setHeader("Content-Disposition", "attachment; filename=" + f.getNome() + "." + f.getType().substring(f.getType().indexOf('/') + 1));
    try {
      f.setVisualizationDate(new Date());
      f.setVisualizationIp(request.getRemoteAddr());
      FileHelper.saveNow(f);
      return f.getContent();
    } catch (InvalidFileException ex) {
      throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
    }

  }

  @GET
  @TokenSecured
  @AdministratorOnly
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/uploadersStats/{from}/{to}")
  public List<StatsProxy> getUploadersStats(@PathParam("from") String from, @PathParam("to") String to, @Context HttpServletRequest request) {
    if (from == null || to == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    try {
      DATE_FORMAT.setLenient(false);
      Date startDate = DATE_FORMAT.parse(from);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(DATE_FORMAT.parse(to));
      calendar.add(Calendar.DATE, 1);
      Date endDate = calendar.getTime();
      if (!endDate.after(startDate)) {
        throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.BAD_DATE_ERROR_MESSAGE).build());
      }
      return FileHelper.numFileConsumersForUploader(startDate, endDate);
    } catch (ParseException ex) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.BAD_DATE_FORMAT_ERROR_MESSAGE).build());
    }
  }

  @POST
  @TokenSecured
  @UploaderOnly
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/uploadWS")
  public String uploadWS(@Context HttpServletRequest request, WebServiceFileProxy w) {
    if (w == null || w.getFiscalCode() == null) {
      throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(Responses.MISSING_PARAMETER_ERROR_MESSAGE).build());
    }
    String username = (String) request.getAttribute("username");
    try {
      RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
      String password = pwdGenerator.generate(8);
      Consumer consumer = ConsumerHelper.getByUsername(Consumer.class, w.getFiscalCode());
      if (consumer == null) {
        consumer = new Consumer(w.getFiscalCode(), password, w.getEmail(), w.getFullName());
        ConsumerHelper.saveNow(consumer);
        try {
          EmailSender.notifyProfileCreation(consumer.getEmail(), password);
        } catch (Exception e) {
          ConsumerHelper.deleteByUsername(Consumer.class, consumer.getUsername());
          throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
        }
      }
      File file = new File(w.getNome(), w.getHashtag(), w.getContent(), username, consumer.getUsername());
      FileHelper.saveNow(file);
      Uploader u = UploaderHelper.getByUsername(Uploader.class, username);
      try {
        EmailSender.notifyFileUpload(file.getNome(), u.getFullName(), consumer.getEmail(), u.getUsername() + "/" + file.getId() + "/" + TokenManager.getJWT(consumer));
      } catch (Exception e) {
        FileHelper.delete(file.getId(), username);
        throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
      }
      return file.getId().toString();
    } catch (NoSuchAlgorithmException ex) {
      throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
    } catch (InvalidKeySpecException ex) {
      throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Responses.INTERNAL_SERVER_ERROR_MESSAGE).build());
    } catch (InvalidUserException ex) {
      Response response = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
      throw new BadRequestException(response);
    } catch (InvalidFileException ex) {
      Response response = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
      throw new BadRequestException(response);
    }

  }
}
