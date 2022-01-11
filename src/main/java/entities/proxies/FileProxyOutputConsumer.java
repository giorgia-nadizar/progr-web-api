package entities.proxies;

import entities.storage.File;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*
 * @author Giorgia Nadizar
 */
public class FileProxyOutputConsumer {

  protected Long id;
  protected String name;
  protected Date uploadDate;
  protected Date visualizationDate;
  protected Set<String> hashtag;

  public FileProxyOutputConsumer() {
  }

  public FileProxyOutputConsumer(File f) {
    id = f.getId();
    name = f.getNome();
    uploadDate = f.getUploadDate();
    visualizationDate = f.getVisualizationDate();
    hashtag = f.getHashtag();
  }

  public Set<String> getHashtag() {
    return hashtag;
  }

  public void setHashtag(Set<String> hashtag) {
    this.hashtag = hashtag;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getUploadDate() {
    return uploadDate;
  }

  public void setUploadDate(Date uploadDate) {
    this.uploadDate = uploadDate;
  }

  public Date getVisualizationDate() {
    return visualizationDate;
  }

  public void setVisualizationDate(Date visualizationDate) {
    this.visualizationDate = visualizationDate;
  }

  public static List<FileProxyOutputConsumer> listFileProxyOutput(List<File> files) {
    List<FileProxyOutputConsumer> result = new ArrayList<FileProxyOutputConsumer>();
    for (File file : files) {
      FileProxyOutputConsumer f = new FileProxyOutputConsumer(file);
      result.add(f);
    }
    return result;
  }

}
