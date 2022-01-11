package entities.proxies;

import entities.storage.File;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*
 * @author Giorgia Nadizar
 */
public class FileProxyOutputUploader {

  protected Long id;
  protected String name;
  protected Date visualizationDate;
  protected String visualizationIp;
  protected Set<String> hashtag;

  public FileProxyOutputUploader() {
  }

  public FileProxyOutputUploader(File f) {
    id = f.getId();
    name = f.getNome();
    visualizationDate = f.getVisualizationDate();
    visualizationIp = f.getVisualizationIp();
    hashtag = f.getHashtag();
  }

  public String getVisualizationIp() {
    return visualizationIp;
  }

  public void setVisualizationIp(String visualizationIp) {
    this.visualizationIp = visualizationIp;
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

  public Date getVisualizationDate() {
    return visualizationDate;
  }

  public void setVisualizationDate(Date visualizationDate) {
    this.visualizationDate = visualizationDate;
  }

  public static List<FileProxyOutputUploader> listFileProxyOutput(List<File> files) {
    List<FileProxyOutputUploader> result = new ArrayList<FileProxyOutputUploader>();
    for (File file : files) {
      FileProxyOutputUploader f = new FileProxyOutputUploader(file);
      result.add(f);
    }
    return result;
  }

}
