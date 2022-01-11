/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * @author Giorgia Nadizar
 */
public class InvalidVisualizationDateException extends InvalidFileException {

  public InvalidVisualizationDateException() {
    super("The visualization date inserted is in the future.");
  }

}
