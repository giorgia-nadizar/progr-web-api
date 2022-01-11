package persistence;

import com.googlecode.objectify.Key;
import entities.proxies.FileProxyOutputConsumer;
import entities.proxies.FileProxyOutputUploader;
import entities.proxies.StatsProxy;
import entities.proxies.UserProxyOutput;
import entities.storage.Consumer;
import entities.storage.File;
import entities.storage.Uploader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/*
 * @author Giorgia Nadizar
 */
public class FileHelper extends AbstractHelper {

  public static File get(long id, String uploader) {
    Key<File> k = Key.create(Key.create(Uploader.class, uploader), File.class, id);
    return ofy().load().key(k).now();
  }

  public static void delete(long id, String uploader) {
    Key<File> k = Key.create(Key.create(Uploader.class, uploader), File.class, id);
    ofy().delete().key(k);
  }

  public static void deleteByUploader(String uploader) {
    Key<Uploader> k = Key.create(Uploader.class, uploader);
    Iterable<Key<File>> fileKeys = ofy().load().type(File.class).ancestor(k).keys();
    ofy().delete().keys(fileKeys);
  }

  public static void deleteByConsumer(String consumer) {
    Key<Consumer> k = Key.create(Consumer.class, consumer);
    Iterable<Key<File>> fileKeys = ofy().load().type(File.class).filter("consumer", k).keys();
    ofy().delete().keys(fileKeys);
  }

  public static List<File> filterByConsumer(String consumer) {
    Key<Consumer> k = Key.create(Consumer.class, consumer);
    return ofy().load().type(File.class).filter("consumer", k).list();
  }

  public static List<File> filterByUploader(String uploader) {
    Key<Uploader> k = Key.create(Uploader.class, uploader);
    return ofy().load().type(File.class).ancestor(k).list();
  }

  public static List<File> filterByDate(Date from, Date to) {
    return ofy().load().type(File.class).filter("uploadDate > ", from).filter("uploadDate < ", to).list();
  }

  public static int numFilesForUploader(Date from, Date to, String uploader) {
    Key<Uploader> u = Key.create(Uploader.class, uploader);
    return ofy().load().type(File.class).filter("uploadDate > ", from).filter("uploadDate < ", to).ancestor(u).count();
  }

  public static List<StatsProxy> numFileConsumersForUploader(Date da, Date a) {
    List<StatsProxy> list = new ArrayList<StatsProxy>();
    int counter;
    Iterable<Key<Consumer>> consumerKeys = ofy().load().type(Consumer.class).keys();
    Iterable<Key<Uploader>> uploaderKeys = ofy().load().type(Uploader.class).keys();
    for (Key<Uploader> u : uploaderKeys) {
      counter = 0;
      for (Key<Consumer> c : consumerKeys) {
        int f = ofy().load().type(File.class).filter("uploadDate > ", da).filter("uploadDate < ", a).filter("consumer", c).ancestor(u).count();
        if (f >= 1) {
          counter++;
        }
      }
      int numFile = numFilesForUploader(da, a, u.getName());
      StatsProxy listElement = new StatsProxy(u.getName(), numFile, counter);
      list.add(listElement);
    }
    return list;
  }

  public static List<FileProxyOutputConsumer> sortedFilesUploaderConsumer(String uploader, String consumer) {
    Key<Consumer> c = Key.create(Consumer.class, consumer);
    Key<Uploader> u = Key.create(Uploader.class, uploader);
    List<File> notSeenFiles = ofy().load().type(File.class).filter("consumer", c).ancestor(u).filter("visualizationDate", null).order("-uploadDate").list();
    List<File> seenFiles = ofy().load().type(File.class).filter("consumer", c).ancestor(u).order("-uploadDate").order("visualizationIp").list();
    List<File> allFiles = new ArrayList<File>();
    allFiles.addAll(notSeenFiles);
    allFiles.addAll(seenFiles);
    return FileProxyOutputConsumer.listFileProxyOutput(allFiles);
  }

  public static List<UserProxyOutput> uploaderForConsumer(String consumer) {
    Key<Consumer> c = Key.create(Consumer.class, consumer);
    Iterable<Key<Uploader>> uploaderKeys = ofy().load().type(Uploader.class).keys();
    List<UserProxyOutput> uploaders = new ArrayList<UserProxyOutput>();
    for (Key<Uploader> u : uploaderKeys) {
      int f = ofy().load().type(File.class).ancestor(u).filter("consumer", c).count();
      if (f >= 1) {
        uploaders.add(new UserProxyOutput(UploaderHelper.getByKey(u)));
      }
    }
    return uploaders;
  }

  public static List<UserProxyOutput> consumerPerUploader(String uploader) {
    Key<Uploader> u = Key.create(Uploader.class, uploader);
    Iterable<Key<Consumer>> consumerKeys = ofy().load().type(Consumer.class).keys();
    List<UserProxyOutput> consumers = new ArrayList<UserProxyOutput>();
    for (Key<Consumer> c : consumerKeys) {
      int f = ofy().load().type(File.class).ancestor(u).filter("consumer", c).count();
      if (f >= 1) {
        consumers.add(new UserProxyOutput(ConsumerHelper.getByKey(c)));
      }
    }
    return consumers;
  }

  public static List<FileProxyOutputUploader> filterByConsumerUploader(String consumer, String uploader) {
    Key<Consumer> c = Key.create(Consumer.class, consumer);
    Key<Uploader> u = Key.create(Uploader.class, uploader);
    List<File> files = ofy().load().type(File.class).ancestor(u).filter("consumer", c).list();
    return FileProxyOutputUploader.listFileProxyOutput(files);
  }

}
