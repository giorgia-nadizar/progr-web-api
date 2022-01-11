package persistence;

import com.googlecode.objectify.Key;
import entities.proxies.UserProxyOutput;
import entities.storage.Administrator;
import entities.storage.Consumer;
import entities.storage.Uploader;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * @author Giorgia Nadizar
 */
public class UserHelper extends AbstractHelper {

  public static List<UserProxyOutput> getAllUsers(String role) {
    if (role.equals("consumer")) {
      List<Consumer> c = ofy().load().type(Consumer.class).list();
      return UserProxyOutput.listUserProxyOutput(c);
    } else if (role.equals("uploader")) {
      List<Uploader> u = ofy().load().type(Uploader.class).list();
      return UserProxyOutput.listUserProxyOutput(u);
    } else if (role.equals("administrator")) {
      List<Administrator> a = ofy().load().type(Administrator.class).list();
      return UserProxyOutput.listUserProxyOutput(a);
    } else {
      return null;
    }
  }

  public static List<String> getAllUsernames(String role) {
    if (role.equals("consumer")) {
      Iterable<Key<Consumer>> keys = ofy().load().type(Consumer.class).keys();
      List<String> usernames = new ArrayList<String>();
      for (Key<Consumer> k : keys) {
        usernames.add(k.getName());
      }
      return usernames;
    } else if (role.equals("uploader")) {
      Iterable<Key<Uploader>> keys = ofy().load().type(Uploader.class).keys();
      List<String> usernames = new ArrayList<String>();
      for (Key<Uploader> k : keys) {
        usernames.add(k.getName());
      }
      return usernames;
    } else if (role.equals("administrator")) {
      Iterable<Key<Administrator>> keys = ofy().load().type(Administrator.class).keys();
      List<String> usernames = new ArrayList<String>();
      for (Key<Administrator> k : keys) {
        usernames.add(k.getName());
      }
      return usernames;
    } else {
      return null;
    }
  }

}
