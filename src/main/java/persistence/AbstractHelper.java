package persistence;

import com.googlecode.objectify.Key;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * @author Giorgia Nadizar
 */
public abstract class AbstractHelper {

  public static <T> void saveDelayed(T t) {
    ofy().save().entity(t);
  }

  public static <T> void saveNow(T t) {
    ofy().save().entity(t).now();
  }

  public static <T> boolean exists(Key<T> k) {
    return (ofy().load().key(k).now() != null);
  }

  public static <T> boolean exists(Class<T> c, String username) {
    return (exists(Key.create(c, username.toLowerCase())) || exists(Key.create(c, username.toUpperCase())));
  }

  public static <T> T getByKey(Key<T> k) {
    return ofy().load().key(k).now();
  }

  public static <T> T getById(Class<T> c, Long id) {
    Key<T> k = Key.create(c, id);
    return ofy().load().key(k).now();
  }

  public static <T> T getByUsername(Class<T> c, String username) {
    Key<T> k = Key.create(c, username);
    return ofy().load().key(k).now();
  }

  public static <T> void deleteByUsername(Class<T> c, String username) {
    Key<T> k = Key.create(c, username);
    ofy().delete().key(k);
  }

  public static <T> void deleteByUsernameNow(Class<T> c, String username) {
    Key<T> k = Key.create(c, username);
    ofy().delete().key(k).now();
  }

}
