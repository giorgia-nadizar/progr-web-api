/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class InvalidEmailException extends InvalidUserException {

  public InvalidEmailException() {
    super("The inserted email address is not valid.");
  }

}
