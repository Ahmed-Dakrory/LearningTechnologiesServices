
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class UserCustomField {
  
  private String type=null;
  private String value=null;
  private String name=null;
  private String shortname=null;
  
  /**
   *
   */
  public UserCustomField() {}
  
  /**
   *
   * @param type
   * @param value
   * @param name
   * @param shortname
   */
  public UserCustomField(String type, String value, String name, String shortname) {
    this.type=type;
    this.value=value;
    this.name=name;
    this.shortname=shortname;
  }
  
  /**
   *
   * @return
   */
  public String getType() {
    return type;
  }
  
  /**
   *
   * @return
   */
  public String getValue() {
    return value;
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
  public String getShortname() {
    return shortname;
  }
  
  /**
   * 
   * @param type
   */
  public void setType(String type) {
    this.type=type;
  }
  
  /**
   *
   * @param value
   */
  public void setValue(String value) {
    this.value=value;
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
  public void setShortname(String shortname) {
    this.shortname=shortname;
  }
  
  /**
   *
   * @param nodeName
   * @param content
   */
  public void setCustomFieldField(String nodeName, String content) {
    if (nodeName.equals("name")) setName(content);
    if (nodeName.equals("type")) setType(content);
    if (nodeName.equals("value")) setValue(content);
    if (nodeName.equals("shortname")) setShortname(content);
  }
}
