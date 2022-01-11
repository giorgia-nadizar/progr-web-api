package entities.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import entities.proxies.UserProxy;
import exceptions.*;
import org.apache.commons.validator.routines.EmailValidator;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * @author Giorgia Nadizar
 */
@Entity
public class User {

  @Id
  protected String username;
  protected byte[] salt;
  protected byte[] password;
  protected String email;
  protected String fullName;
  protected String role;

  public User() {
  }

  public User(String username, String password, String email, String fullName, String role) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidUserException {
    EmailValidator validator = EmailValidator.getInstance();
    if (email == null || !validator.isValid(email)) {
      throw new InvalidEmailException();
    }
    if (password == null || password.length() < 8) {
      throw new InvalidPasswordException();
    }
    if (role == null || !role.equals("consumer") && !role.equals("administrator") && !role.equals("uploader")) {
      throw new InvalidRoleException();
    }
    if (fullName == null || fullName.length() == 0) {
      throw new InvalidNameException();
    }
    SecureRandom random = new SecureRandom();
    byte[] s = new byte[16];
    random.nextBytes(s);
    this.salt = s;
    KeySpec spec = new PBEKeySpec(password.toCharArray(), s, 65536, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = factory.generateSecret(spec).getEncoded();
    this.username = username;
    this.password = hash;
    this.email = email;
    this.fullName = fullName;
    this.role = role;
  }

  public User(UserProxy u) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidUserException {
    EmailValidator validator = EmailValidator.getInstance();
    if (u.getEmail() == null || !validator.isValid(u.getEmail())) {
      throw new InvalidEmailException();
    }
    if (u.getPassword() == null || u.getPassword().length() < 8) {
      throw new InvalidPasswordException();
    }
    if (u.getRole() == null || (!u.getRole().equals("consumer") && !u.getRole().equals("administrator") && !u.getRole().equals("uploader"))) {
      throw new InvalidRoleException();
    }
    if (u.getFullName() == null || u.getFullName().length() == 0) {
      throw new InvalidNameException();
    }
    SecureRandom random = new SecureRandom();
    byte[] s = new byte[16];
    random.nextBytes(s);
    this.salt = s;
    KeySpec spec = new PBEKeySpec(u.getPassword().toCharArray(), s, 65536, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = factory.generateSecret(spec).getEncoded();
    this.username = u.getUsername();
    this.password = hash;
    this.email = u.getEmail();
    this.fullName = u.getFullName();
    this.role = u.getRole();
  }

  public boolean equals(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] s = this.salt;
    KeySpec spec = new PBEKeySpec(password.toCharArray(), s, 65536, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = factory.generateSecret(spec).getEncoded();
    return Arrays.equals(this.password, hash);
  }

  public String getUsername() {
    return username;
  }

  public byte[] getSalt() {
    return salt;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) throws InvalidEmailException {
    EmailValidator validator = EmailValidator.getInstance();
    if (email == null || !validator.isValid(email)) {
      throw new InvalidEmailException();
    }
    this.email = email;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) throws InvalidNameException {
    if (fullName == null || fullName.length() == 0) {
      throw new InvalidNameException();
    }
    this.fullName = fullName;
  }

  public String getRole() {
    return role;
  }

  public void changePassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidPasswordException {
    if (password == null || password.length() < 8) {
      throw new InvalidPasswordException();
    }
    byte[] s = this.salt;
    KeySpec spec = new PBEKeySpec(password.toCharArray(), s, 65536, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    this.password = factory.generateSecret(spec).getEncoded();
  }

  public void setPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidPasswordException {
    if (password == null || password.length() < 8) {
      throw new InvalidPasswordException();
    }
    if (this.salt == null) {
      SecureRandom random = new SecureRandom();
      byte[] s = new byte[16];
      random.nextBytes(s);
      this.salt = s;
    }
    KeySpec spec = new PBEKeySpec(password.toCharArray(), this.salt, 65536, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    this.password = factory.generateSecret(spec).getEncoded();
  }

  public byte[] getPassword() {
    return password;
  }

  public static boolean isAuthorized(String actorRole, String targetRole) {
    return ((actorRole != null && targetRole != null)
        && ((actorRole.equals("administrator") && (targetRole.equals("administrator") || targetRole.equals("uploader")))
        || (actorRole.equals("uploader") && targetRole.equals("consumer"))));
  }

  public void update(String email, String fullName) throws InvalidUserException {
    EmailValidator validator = EmailValidator.getInstance();
    if (email == null || !validator.isValid(email)) {
      throw new InvalidEmailException();
    }
    if (fullName == null || fullName.length() == 0) {
      throw new InvalidNameException();
    }
    this.email = email;
    this.fullName = fullName;
  }

}
