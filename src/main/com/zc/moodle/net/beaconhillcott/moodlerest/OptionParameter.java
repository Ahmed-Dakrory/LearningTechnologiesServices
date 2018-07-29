
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class OptionParameter {
  private String name=null;
  private String value=null;
  
  /**
   *
   */
  public OptionParameter() {}
  
  /**
   *
   * @param name
   * @param value
   */
  public OptionParameter(String name, String value) {
    this.name=name;
    this.value=value;
  }
  
  /**
   *
   * @return  String
   */
  public String getName() {
    return name;
  }
  
  /**
   *
   * @return String
   */
  public String getValue() {
    return value;
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
   * @param value
   */
  public void setValue(String value) {
    this.value=value;
  }
}
