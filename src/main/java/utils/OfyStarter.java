package utils;

import com.googlecode.objectify.ObjectifyService;
import entities.storage.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Giorgia Nadizar
 */
public class OfyStarter implements ServletContextListener {

  public void contextInitialized(ServletContextEvent sce) {

    ObjectifyService.register(User.class);
    ObjectifyService.register(File.class);
    ObjectifyService.register(Consumer.class);
    ObjectifyService.register(Uploader.class);
    ObjectifyService.register(Administrator.class);

  }

  public void contextDestroyed(ServletContextEvent sce) {

  }

}
