package entities.storage;

import com.googlecode.objectify.annotation.Subclass;
import entities.proxies.UserProxy;
import exceptions.AdministratorUsernameException;
import exceptions.InvalidUserException;
import org.apache.commons.validator.routines.EmailValidator;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Giorgia Nadizar
 */
@Subclass(index = true)
public class Administrator extends User {

  public Administrator() {
  }

  public Administrator(String username, String password, String email, String nomeCognome) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidUserException {
    super(validateUsername(username), password, email, nomeCognome, "administrator");
  }

  public Administrator(UserProxy a) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidUserException {
    super(validateUserProxyUsername(a));
  }

  private static String validateUsername(String username) throws AdministratorUsernameException {
    username = username.toLowerCase();
    EmailValidator validator = EmailValidator.getInstance();
    if (!validator.isValid(username)) {
      throw new AdministratorUsernameException();
    } else {
      return username;
    }
  }

  private static UserProxy validateUserProxyUsername(UserProxy a) throws AdministratorUsernameException {
    a.setUsername(a.getUsername().toLowerCase());
    EmailValidator validator = EmailValidator.getInstance();
    if (a.getUsername() == null || !validator.isValid(a.getUsername())) {
      throw new AdministratorUsernameException();
    } else {
      return a;
    }
  }

  public void setUsername(String username) throws AdministratorUsernameException {
    this.username = validateUsername(username);
  }
}
