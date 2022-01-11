package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class InvalidPasswordException extends InvalidUserException {

  public InvalidPasswordException() {
    super("The password must be at least 8 characters long for security reasons.");
  }

}
