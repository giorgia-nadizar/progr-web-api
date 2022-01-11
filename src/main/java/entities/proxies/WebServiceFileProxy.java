package entities.proxies;

import java.util.Set;

/*
 * @author Giorgia Nadizar
 */
public class WebServiceFileProxy {

  protected String fiscalCode;
  protected String email;
  protected String fullName;
  protected String nome;
  protected Set<String> hashtag;
  protected String content;

  public WebServiceFileProxy() {
  }

  public WebServiceFileProxy(String fiscalCode, String email, String fullName, String nome, Set<String> hashtag, String content) {
    this.fiscalCode = fiscalCode;
    this.email = email;
    this.fullName = fullName;
    this.nome = nome;
    this.hashtag = hashtag;
    this.content = content;
  }

  public String getFiscalCode() {
    return fiscalCode;
  }

  public void setFiscalCode(String fiscalCode) {
    this.fiscalCode = fiscalCode;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
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

}
