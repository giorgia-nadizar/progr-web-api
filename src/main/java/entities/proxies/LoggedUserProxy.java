/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.proxies;

import entities.storage.User;

/*
 * @author Giorgia Nadizar
 */
public class LoggedUserProxy {

  protected String token;
  protected String username;
  protected String role;

  public LoggedUserProxy(String token, String username, String role) {
    this.token = token;
    this.username = username;
    this.role = role;
  }

  public LoggedUserProxy() {
  }

  public LoggedUserProxy(User u, String token) {
    this.token = token;
    username = u.getUsername();
    role = u.getRole();
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
