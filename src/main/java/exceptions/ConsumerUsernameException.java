package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class ConsumerUsernameException extends InvalidUserException {

  public ConsumerUsernameException() {
    super("The username of a consumer must be a valid fiscal code.");
  }

}
