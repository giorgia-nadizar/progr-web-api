/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.proxies;

import entities.storage.Uploader;
import entities.storage.User;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Giorgia Nadizar
 */
public class UserProxyOutput {

  protected String username;
  protected String role;
  protected String fullName;
  protected String email;
  protected String logo;

  public UserProxyOutput() {
  }

  public UserProxyOutput(String username, String role, String fullName, String email, String logo) {
    this.username = username;
    this.role = role;
    this.fullName = fullName;
    this.email = email;
    this.logo = logo;
  }

  public UserProxyOutput(User u) {
    this.username = u.getUsername();
    this.role = u.getRole();
    this.fullName = u.getFullName();
    this.email = u.getEmail();
    if (role.equals("uploader")) {
      this.logo = ((Uploader) u).getLogo();
    }
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public static List<UserProxyOutput> listUserProxyOutput(List<? extends User> users) {
    List<UserProxyOutput> result = new ArrayList<UserProxyOutput>();
    for (User user : users) {
      UserProxyOutput u = new UserProxyOutput(user);
      result.add(u);
    }
    return result;
  }

}
