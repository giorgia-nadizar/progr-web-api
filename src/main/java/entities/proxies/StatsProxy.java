/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.proxies;

/*
 * @author Giorgia Nadizar
 */
public class StatsProxy {

  protected String uploader;
  protected int nFiles;
  protected int nConsumers;

  public StatsProxy() {
  }

  public StatsProxy(String uploader, int nFiles, int nConsumers) {
    this.uploader = uploader;
    this.nFiles = nFiles;
    this.nConsumers = nConsumers;
  }

  public String getUploader() {
    return uploader;
  }

  public void setUploader(String uploader) {
    this.uploader = uploader;
  }

  public int getnFiles() {
    return nFiles;
  }

  public void setnFiles(int nFiles) {
    this.nFiles = nFiles;
  }

  public int getnConsumers() {
    return nConsumers;
  }

  public void setnConsumers(int nConsumers) {
    this.nConsumers = nConsumers;
  }

}
