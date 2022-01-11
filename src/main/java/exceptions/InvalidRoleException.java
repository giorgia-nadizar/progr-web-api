/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class InvalidRoleException extends InvalidUserException {

  public InvalidRoleException() {
    super("The inserted role is not valid.");
  }

}
