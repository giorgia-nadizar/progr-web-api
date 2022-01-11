/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.proxies;

import java.util.Set;

/**
 * @author Giorgia Nadizar
 */
public class FileProxy {

  protected String name;
  protected Set<String> hashtag;
  protected String content;
  protected String consumer;

  public FileProxy(String name, Set<String> hashtag, String content, String consumer) {
    this.name = name;
    this.hashtag = hashtag;
    this.content = content;
    this.consumer = consumer;
  }

  public FileProxy() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<String> getHashtag() {
    return hashtag;
  }

  public void setHashtag(Set<String> hashtag) {
    this.hashtag = hashtag;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getConsumer() {
    return consumer;
  }

  public void setConsumer(String consumer) {
    this.consumer = consumer;
  }

}
