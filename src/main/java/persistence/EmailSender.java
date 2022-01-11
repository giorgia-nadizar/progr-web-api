package persistence;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Giorgia Nadizar
 */
public class EmailSender {

  private static final String URL = "https://progrweb2020-273020.appspot.com/";
  private static final String DOWNLOAD_FILE = URL + "api/file";
  private static final String PASSWORD_RESET = URL + "api/user/passwordReset/";

  public static void sendEmail(String subject, String emailBody, String consumerEmail) throws Exception {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress("giorgia.nadizar@gmail.com", "ProgrWeb2020"));
    msg.addRecipient(Message.RecipientType.TO,
        new InternetAddress(consumerEmail));
    msg.setSubject(subject);
    msg.setContent(emailBody, "text/html; charset=utf-8");
    Transport.send(msg);
  }

  public static void notifyFileUpload(String filename, String uploaderName, String consumerEmail, String fileSelector) throws Exception {
    String emailBody = "<p>Gentile consumer,</p>"
        + "<p>We inform you that a new file was uploaded for you. It is file " + filename + ", uploaded by " + uploaderName + ".</p>"
        + "<p><a href=\"" + URL + "\">Go to website.</a></p>"
        + "<p><a href=\"" + DOWNLOAD_FILE + "/" + fileSelector + "\">Download directly.</a></p>"
        + "<p>Please pay attention to the download link: as it enables anyone to download the file in the next 24 hours," +
        " it is advisable to keep it secret.</p>"
        + "<p>Giorgia Nadizar, Progr Web 2020</p>";
    String subject = "News: new file";
    sendEmail(subject, emailBody, consumerEmail);
  }

  public static void notifyProfileCreation(String consumerEmail, String password) throws Exception {
    String emailBody = "<p>Dear consumer,</p>"
        + "<p>We inform you that an account was created for you.</p>"
        + "<p>The login credentials are your fiscal code and the temporary password <strong>" + password + "</strong></p>"
        + "<p><a href=\"" + URL + "\">Go to website.</a></p>"
        + "<p>Giorgia Nadizar, Progr Web 2020</p>";
    String subject = "News: account creation";
    sendEmail(subject, emailBody, consumerEmail);
  }

  public static void passwordRecovery(String email, String token) throws Exception {
    String emailBody = "<p>Dear user,</p>"
        + "<p>We inform you that a password reset was requested for your account.</p>"
        + "<p><a href=\"" + PASSWORD_RESET + token + "\">Reset password.</a></p>"
        + "<p>If you did not ask for a reset just ignore this email; the link will become inactive in 24 hours.</p>"
        + "<p>Giorgia Nadizar, Progr Web 2020</p>";
    String subject = "Password recovery";
    sendEmail(subject, emailBody, email);
  }

  public static void passwordReset(String email, String password) throws Exception {
    String emailBody = "<p>Dear user,</p>"
        + "<p>Your new password is <strong>" + password + "</strong></p>"
        + "<p>Giorgia Nadizar, Progr Web 2020</p>";
    String subject = "Password changed";
    sendEmail(subject, emailBody, email);
  }

}
