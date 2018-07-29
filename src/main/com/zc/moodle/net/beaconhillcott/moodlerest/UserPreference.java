
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class UserPreference {
  private String type=null;
  private String value=null;
  
  public UserPreference() {}
  
  public UserPreference(String type, String value) {
    this.type=type;
    this.value=value;
  }
  
  public void setType(String type) {
    this.type=type;
  }
  
  public void setValue(String value) {
    this.value=value;
  }
  
  public String getType() {
    return type;
  }
  
  public String getValue() {
    return value;
  }
  
  public void setUserPreference(String type, String value) {
    this.type=type;
    this.value=value;
  }
}
