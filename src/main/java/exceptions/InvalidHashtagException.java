/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class InvalidHashtagException extends InvalidFileException {

  public InvalidHashtagException() {
    super("An inserted hashtag is not valid as it contains invalid characters.");
  }

}
