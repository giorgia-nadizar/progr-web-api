package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class AdministratorUsernameException extends InvalidUserException {

  public AdministratorUsernameException() {
    super("The username of an administrator must be a valid email address.");
  }

}
