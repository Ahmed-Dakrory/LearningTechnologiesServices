
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class UserRole {

  long roleid=-1;
  String name=null;
  String shortname=null;
  int sortorder=-1;
  
  /**
   *
   */
  public UserRole() {}
  
  /**
   *
   * @param roleid
   * @param name
   * @param shortname
   * @param sortorder
   */
  public UserRole(long roleid, String name, String shortname, int sortorder) {
    this.roleid=roleid;
    this.name=name;
    this.shortname=shortname;
    this.sortorder=sortorder;
  }
  
  /**
   *
   * @return long
   */
  public long getRoleId() {
    return roleid;
  }
  
  /**
   * 
   * @return String
   */
  public String getName() {
    return name;
  }
  
  /**
   *
   * @return String
   */
  public String getShortName() {
    return shortname;
  }
  
  /**
   *
   * @return int
   */
  public int getSortOrder() {
    return sortorder;
  }
  
  /**
   *
   * @param roleid
   */
  public void setRoleId(long roleid) {
    this.roleid=roleid;
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
   * @param shortname
   */
  public void setShortName(String shortname) {
    this.shortname=shortname;
  }
  
  /**
   *
   * @param sortorder
   */
  public void setSortOrder(int sortorder) {
    this.sortorder=sortorder;
  }
  
  /**
   *
   * @param nodeName
   * @param content
   */
  public void setUserRoleField(String nodeName,String content) {
    if (nodeName.equals("roleid")) setRoleId(Long.parseLong(content));
    if (nodeName.equals("name")) setName(content);
    if (nodeName.equals("shortname")) setShortName(content);
    if (nodeName.equals("sortorder")) setSortOrder(Integer.parseInt(content));
  }
  
}
