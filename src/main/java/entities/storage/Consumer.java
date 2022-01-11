package entities.storage;

import com.googlecode.objectify.annotation.Subclass;
import entities.proxies.UserProxy;
import exceptions.ConsumerUsernameException;
import exceptions.InvalidUserException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

/*
 * @author Giorgia Nadizar
 */
@Subclass(index = true)
public class Consumer extends User {

  private static final String REGEX_COD_FISCALE = "[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]";

  public Consumer() {
  }

  public Consumer(String username, String password, String email, String nomeCognome) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidUserException {
    super(validateUsername(username), password, email, nomeCognome, "consumer");
  }

  public Consumer(UserProxy c) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidUserException {
    super(validateUserProxyUsername(c));
  }

  private static String validateUsername(String username) throws ConsumerUsernameException {
    username = username.toUpperCase();
    if (!Pattern.matches(REGEX_COD_FISCALE, username)) {
      throw new ConsumerUsernameException();
    } else {
      return username;
    }
  }

  private static UserProxy validateUserProxyUsername(UserProxy c) throws ConsumerUsernameException {
    c.setUsername(c.getUsername().toUpperCase());
    if (c.getUsername() == null || !Pattern.matches(REGEX_COD_FISCALE, c.getUsername())) {
      throw new ConsumerUsernameException();
    } else {
      return c;
    }
  }

  public void setUsername(String username) throws ConsumerUsernameException {
    this.username = validateUsername(username);
  }

}
