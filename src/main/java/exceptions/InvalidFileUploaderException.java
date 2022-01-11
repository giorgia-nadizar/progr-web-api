/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class InvalidFileUploaderException extends InvalidFileException {

  public InvalidFileUploaderException() {
    super("The file uploader does not exist.");
  }

}
