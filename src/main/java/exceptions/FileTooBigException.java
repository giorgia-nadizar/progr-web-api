/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class FileTooBigException extends InvalidFileException {

  public FileTooBigException() {
    super("The uploaded file exceeds the maximum allowed dimension.");
  }

}
