/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package responses;

/**
 * @author Giorgia Nadizar
 */
public class Responses {

  public static final String FORBIDDEN_ERROR_MESSAGE = "You are not allowed to perform this operation";
  public static final String MISSING_TOKEN_ERROR_MESSAGE = "Missing token";
  public static final String INVALID_TOKEN_ERROR_MESSAGE = "Token not valid or expired";
  public static final String MISSING_PARAMETER_ERROR_MESSAGE = "Compulsory parameters omitted";
  public static final String WRONG_CREDENTIALS_ERROR_MESSAGE = "Wrong credentials";
  public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Server error, please retry";
  public static final String USER_NOT_FOUND_MESSAGE = "Not existing user";
  public static final String ROLE_NOT_FOUND_MESSAGE = "Not existing role";
  public static final String FILE_NOT_FOUND_MESSAGE = "Not existing file";
  public static final String NO_USER_ERROR_MESSAGE = "Username missing";
  public static final String USER_ALREADY_EXISTING_ERROR_MESSAGE = "User already exists";
  public static final String BAD_DATE_FORMAT_ERROR_MESSAGE = "Date inserted in the wrong format";
  public static final String BAD_DATE_ERROR_MESSAGE = "Dates in the wrong order";
}
