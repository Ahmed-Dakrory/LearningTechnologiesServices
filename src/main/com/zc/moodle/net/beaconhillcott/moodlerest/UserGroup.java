
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class UserGroup {

  long id=-1;
  String name=null;
  String decription=null;
  
  /**
   *
   */
  public UserGroup() {}
  
  /**
   * 
   * @param id
   * @param name
   * @param description
   */
  public UserGroup(long id, String name, String description) {
    this.id=id;
    this.name=name;
    this.decription=description;
  }
  
  /**
   *
   * @return
   */
  public long getId() {
    return id;
  }
  
  /**
   *
   * @return
   */
  public String getName() {
    return name;
  }
  
  /**
   *
   * @return
   */
  public String getDescription() {
    return decription;
  }
  
  /**
   *
   * @param id
   */
  public void setId(long id) {
    this.id=id;
  }
  
  /**
   *
   * @param name
   */
  public void setName(String name) {
    this.name=name;
  }
  
  /**
   *
   * @param description
   */
  public void setDescription(String description) {
    this.decription=description;
  }
  
  /**
   *
   * @param nodeName
   * @param content
   */
  public void setUserGroupField(String nodeName,String content) {
    if (nodeName.equals("id")) setId(Long.parseLong(content));
    if (nodeName.equals("name")) setName(content);
    if (nodeName.equals("description")) setDescription(content);
  }
}
