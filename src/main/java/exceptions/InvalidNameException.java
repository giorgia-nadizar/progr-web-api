/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class InvalidNameException extends InvalidUserException {

  public InvalidNameException() {
    super("The inserted name is not valid.");
  }

}
