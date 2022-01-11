/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class InvalidLogoException extends InvalidUserException {

  public InvalidLogoException() {
    super("The logo must be a base64 uri format image, or it can be omitted.");
  }

}
