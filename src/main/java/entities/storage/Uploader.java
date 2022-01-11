package entities.storage;

import com.googlecode.objectify.annotation.Subclass;
import entities.proxies.UserProxy;
import exceptions.InvalidLogoException;
import exceptions.InvalidUserException;
import exceptions.UploaderUsernameException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

/**
 * @author Giorgia Nadizar
 */
@Subclass(index = true)
public class Uploader extends User {

  private static final String REGEX_BASE64_IMAGE = "data:image/(.*);base64,(.*)";

  protected String logo;

  public Uploader() {
  }

  public Uploader(String username, String password, String email, String fullName, String logo) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidUserException {
    super(validateUsername(username), password, email, fullName, "uploader");
    if (logo != null && logo.length() > 0 && !Pattern.matches(REGEX_BASE64_IMAGE, logo)) {
      throw new InvalidLogoException();
    }
    this.logo = logo;
  }

  public Uploader(UserProxy u) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidUserException {
    super(validateUserProxyUsername(u));
    if (u.getLogo() != null && u.getLogo().length() > 0 && !Pattern.matches(REGEX_BASE64_IMAGE, u.getLogo())) {
      throw new InvalidLogoException();
    }
    this.logo = u.getLogo();
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) throws InvalidLogoException {
    if (!(logo == null || logo.length() == 0 || Pattern.matches(REGEX_BASE64_IMAGE, logo))) {
      throw new InvalidLogoException();
    }
    this.logo = logo;
  }

  public void update(String email, String fullName, String logo) throws InvalidUserException {
    if (logo != null && logo.length() > 0 && !Pattern.matches(REGEX_BASE64_IMAGE, logo)) {
      throw new InvalidLogoException();
    }
    this.logo = logo;
    this.update(email, fullName);
    this.logo = logo;

  }

  private static String validateUsername(String username) throws UploaderUsernameException {
    username = username.toLowerCase();
    if (username.length() != 4) {
      throw new UploaderUsernameException();
    } else {
      return username;
    }
  }

  private static UserProxy validateUserProxyUsername(UserProxy u) throws UploaderUsernameException {
    u.setUsername(u.getUsername().toLowerCase());
    if (u.getUsername() == null || u.getUsername().length() != 4) {
      throw new UploaderUsernameException();
    }
    return u;
  }

  public void setUsername(String username) throws UploaderUsernameException {
    this.username = validateUsername(username);
  }

}
