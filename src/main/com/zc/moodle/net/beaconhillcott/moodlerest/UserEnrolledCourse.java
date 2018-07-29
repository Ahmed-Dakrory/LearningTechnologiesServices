
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class UserEnrolledCourse {
  private long id=-1;
  private String fullname=null;
  private String shortname=null;
  
  public UserEnrolledCourse() {}
  
  public UserEnrolledCourse(long id) {
    this.id=id;
  }
  
  public UserEnrolledCourse(long id, String fullname, String shortname) {
    this.id=id;
    this.fullname=fullname;
    this.shortname=shortname;
  }
  
  public long getId() {
    return id;
  }
  
  public String getFullName() {
    return fullname;
  }
  
  public String getShortName() {
    return shortname;
  }
  
  public void setId(long id) {
    this.id=id;
  }
  
  public void setFullName(String fullname) {
    this.fullname=fullname;
  }
  
  public void setShortName(String shortname) {
    this.shortname=shortname;
  }
  
  public void setUserEnrolledCourseField(String field, String value) {
    if (field.equals("id")) setId(Long.parseLong(value));
    if (field.equals("fullname") && !value.equals("")) setFullName(value);
    if (field.equals("shortname") && !value.equals("")) setShortName(value);
  }
}
