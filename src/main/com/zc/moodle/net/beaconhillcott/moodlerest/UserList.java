
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class UserList {
  private long userid=-1;
  private long courseid=-1;
  
  /**
   *
   */
  public UserList() {}
  
  /**
   *
   * @param userid
   * @param courseid
   */
  public UserList(long userid, long courseid) {
    this.userid=userid;
    this.courseid=courseid;
  }
  
  /**
   *
   * @return
   */
  public long getUserId() {
    return userid;
  }
  
  /**
   *
   * @return
   */
  public long getCourseId() {
    return courseid;
  }
  
  /**
   * 
   * @param userid
   */
  public void setUserId(long userid) {
    this.userid=userid;
  }
  
  /**
   *
   * @param courseid
   */
  public void setCourseId(long courseid) {
    this.courseid=courseid;
  }
}
