
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * Class to create objects to store an option of a course content.
 *
 * @author Bill Antonia
 */
public class MoodleCourseContentOption {
  private String name=null;
  private String value=null;
  
  /**
   * Constructor for bean compatability
   */
  public MoodleCourseContentOption() {}
  
  /**
   * Method to set a field given its name and value as strings.
   * @param name String
   * @param value String
   */
  protected MoodleCourseContentOption(String name, String value) {
    this.name=name;
    this.value=value;
  }
  
  /**
   * Method to read the name of a course option in the object.
   *
   * @return name String
   */
  public String getName() {
    return name;
  }
  
  /**
   * Method to read the value of a course option in the object.
   *
   * @return value String
   */
  public String getValue() {
    return value;
  }
  
  /**
   * Method to set the option name field in the object. Only useful in construction of data from the webservice.
   *
   * @param name String
   */
  protected void setName(String name) {
    this.name=name;
  }
  
  /**
   * Method to set the option value field in the object. Only useful in construction of data from the webservice.
   *
   * @param value String
   */
  protected void setValue(String value) {
    this.value=value;
  }
  
  /**
   * Method to set the option name and value fields in the object. Only useful in construction of data from the webservice.
   * 
   * @param name String
   * @param value String
   */
  protected void setMoodleCourseContentOptionField(String name, String value) {
    this.name=name;
    this.value=value;
  }
  
}
