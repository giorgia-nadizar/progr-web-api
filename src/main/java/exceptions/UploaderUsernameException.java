package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class UploaderUsernameException extends InvalidUserException {

  public UploaderUsernameException() {
    super("The username of an uploader must be made of exactly for characters.");
  }

}
