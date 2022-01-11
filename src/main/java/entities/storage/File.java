package entities.storage;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.condition.IfNotNull;
import com.googlecode.objectify.condition.IfNull;
import entities.proxies.FileProxy;
import exceptions.*;
import persistence.ConsumerHelper;
import persistence.UploaderHelper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.decodeBase64;

/**
 * @author Giorgia Nadizar
 */
@Entity
public class File {

  private static final String REGEX_IP_ADDRESS = "((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
  private static final String REGEX_HASHTAG = "\\w+";
  private static final String REGEX_BASE64_FILE = "data:image/(.*);base64,(.*)|data:application/pdf;base64,(.*)";
  private static final int MAX_FILE_SIZE = 1110000;

  @Id
  protected Long id;
  protected String nome;
  @Index
  protected Date uploadDate;
  @Index(IfNull.class)
  protected Date visualizationDate;
  @Index(IfNotNull.class)
  protected String visualizationIp;
  protected Set<String> hashtag;
  protected byte[] content;
  protected String type;
  @Index
  @Parent
  protected Key<Uploader> uploader;
  @Index
  protected Key<Consumer> consumer;

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) throws InvalidFilenameException {
    if (nome == null || nome.length() == 0) {
      throw new InvalidFilenameException();
    }
    this.nome = nome;
  }

  public Date getUploadDate() {
    return uploadDate;
  }

  public Date getVisualizationDate() {
    return visualizationDate;
  }

  public Long getId() {
    return id;
  }

  public void setVisualizationDate(Date visualizationDate) throws InvalidVisualizationDateException {
    Date now = new Date();
    if (visualizationDate == null || visualizationDate.after(now)) {
      throw new InvalidVisualizationDateException();
    }
    this.visualizationDate = visualizationDate;
  }

  public String getVisualizationIp() {
    return visualizationIp;
  }

  public void setVisualizationIp(String visualizationIp) throws InvalidIPAddressException {
    if (visualizationIp == null || !Pattern.matches(REGEX_IP_ADDRESS, visualizationIp)) {
      throw new InvalidIPAddressException();
    }
    this.visualizationIp = visualizationIp;
  }

  public Set<String> getHashtag() {
    return hashtag;
  }

  public void setHashtag(Set<String> hashtag) throws InvalidHashtagException {
    for (String h : hashtag) {
      if (h.indexOf("#") == 0) {
        h = h.substring(1);
      }
      if (!Pattern.matches(REGEX_HASHTAG, h)) {
        throw new InvalidHashtagException();
      }
    }
    this.hashtag = hashtag;
  }

  public Key<Uploader> getUploader() {
    return uploader;
  }

  public String getUploaderUsername() {
    return uploader.getName();
  }

  public void setUploader(Key<Uploader> uploader) throws InvalidFileUploaderException {
    if (uploader == null || !UploaderHelper.exists(uploader)) {
      throw new InvalidFileUploaderException();
    }
    this.uploader = uploader;
  }

  public void setUploader(String uploader) throws InvalidFileUploaderException {
    if (uploader == null) {
      throw new InvalidFileUploaderException();
    }
    Key<Uploader> k = Key.create(Uploader.class, uploader);
    if (!UploaderHelper.exists(k)) {
      throw new InvalidFileUploaderException();
    }
    this.uploader = k;
  }

  public Key<Consumer> getConsumer() {
    return consumer;
  }

  public String getConsumerUsername() {
    return consumer.getName();
  }

  public void setConsumer(Key<Consumer> consumer) throws InvalidFileConsumerException {
    if (consumer == null || !ConsumerHelper.exists(consumer)) {
      throw new InvalidFileConsumerException();
    }
    this.consumer = consumer;
  }

  public void setConsumer(String consumer) throws InvalidFileConsumerException {
    if (consumer == null) {
      throw new InvalidFileConsumerException();
    }
    Key<Consumer> k = Key.create(Consumer.class, consumer);
    if (!ConsumerHelper.exists(k)) {
      throw new InvalidFileConsumerException();
    }
    this.consumer = k;
  }

  public String getType() {
    return type;
  }

  public File(FileProxy f, String uploader) throws InvalidFileException {
    if (f.getName() == null || f.getName().length() == 0) {
      throw new InvalidFilenameException();
    }
    Set<String> list = new HashSet<String>();
    for (String h : f.getHashtag()) {
      if (h.indexOf("#") == 0) {
        h = h.substring(1);
      }
      if (!Pattern.matches(REGEX_HASHTAG, h.substring(1))) {
        throw new InvalidHashtagException();
      }
      list.add(h);
    }
    if (f.getConsumer() == null) {
      throw new InvalidFileConsumerException();
    }
    Key<Consumer> consumerKey = Key.create(Consumer.class, f.getConsumer());
    if (!ConsumerHelper.exists(consumerKey)) {
      throw new InvalidFileConsumerException();
    }
    if (uploader == null) {
      throw new InvalidFileUploaderException();
    }
    Key<Uploader> uploaderKey = Key.create(Uploader.class, uploader);
    if (!UploaderHelper.exists(uploaderKey)) {
      throw new InvalidFileUploaderException();
    }
    this.nome = f.getName();
    this.hashtag = list;
    this.uploadDate = new Date();
    this.consumer = consumerKey;
    this.uploader = uploaderKey;
    if (!Pattern.matches(REGEX_BASE64_FILE, f.getContent())) {
      throw new InvalidFileTypeException();
    }
    if (f.getContent().length() > MAX_FILE_SIZE) {
      throw new FileTooBigException();
    }
    String plainBase64 = f.getContent().substring(f.getContent().indexOf(";base64,") + 8);
    this.type = f.getContent().substring(5, f.getContent().indexOf(";base64,"));
    this.content = decodeBase64(plainBase64);
  }

  public File() {
  }

  public File(String nome, Set<String> hashtag, String content, String uploader, String consumer) throws InvalidFileException {
    if (nome == null || nome.length() == 0) {
      throw new InvalidFilenameException();
    }
    Set<String> list = new HashSet<String>();
    for (String h : hashtag) {
      if (h.indexOf("#") == 0) {
        h = h.substring(1);
      }
      if (!Pattern.matches(REGEX_HASHTAG, h.substring(1))) {
        throw new InvalidHashtagException();
      }
      list.add(h);
    }
    if (consumer == null) {
      throw new InvalidFileConsumerException();
    }
    Key<Consumer> consumerKey = Key.create(Consumer.class, consumer);
    if (!ConsumerHelper.exists(consumerKey)) {
      throw new InvalidFileConsumerException();
    }
    if (uploader == null) {
      throw new InvalidFileUploaderException();
    }
    Key<Uploader> uploaderKey = Key.create(Uploader.class, uploader);
    if (!UploaderHelper.exists(uploaderKey)) {
      throw new InvalidFileUploaderException();
    }
    this.nome = nome;
    this.hashtag = list;
    this.uploader = uploaderKey;
    this.consumer = consumerKey;
    this.uploadDate = new Date();
    if (content.length() > MAX_FILE_SIZE) {
      throw new FileTooBigException();
    }
    if (!Pattern.matches(REGEX_BASE64_FILE, content)) {
      throw new InvalidFileTypeException();
    }
    String plainBase64 = content.substring(content.indexOf(";base64,") + 8);
    this.type = content.substring(5, content.indexOf(";base64,"));
    this.content = decodeBase64(plainBase64);
  }

}
