
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.util.ArrayList;

/**
 *
 * @author Bill Antonia
 */
public class MoodleWebService {
  private String sitename=null;
  private String username=null;
  private String firstname=null;
  private String lastname=null;
  private String fullname=null;
  private long userid=-1;
  private String siteurl=null;
  private String userpictureurl=null;
  private ArrayList<Function> functions=null;
  private boolean downloadfiles=false;

  /**
   *
   */
  public MoodleWebService() {}

  /**
   *
   * @return
   */
  public String getSiteName() {
      return sitename;
  }

  /**
   *
   * @return String
   */
  public String getUserName() {
      return username;
  }

  /**
   *
   * @return String
   */
  public String getFirstName() {
      return firstname;
  }

  /**
   *
   * @return String
   */
  public String getLastName() {
      return lastname;
  }

  /**
   *
   * @return String
   */
  public String getFullName() {
      return fullname;
  }

  /**
   *
   * @return long
   */
  public long getUserId() {
      return userid;
  }

  /**
   *
   * @return String
   */
  public String getSiteURL() {
      return siteurl;
  }

  /**
   *
   * @return String
   */
  public String getUserPictureURL() {
      return userpictureurl;
  }

  /**
   *
   * @return ArrayList Function
   */
  public ArrayList<Function> getFunctions() {
      return functions;
  }

  /**
   *
   * @return boolean
   */
  public boolean canDownloadFiles() {
      return downloadfiles;
  }

  /**
   *
   * @param name
   * @param version
   */
  public void addFunction(String name, double version) {
      if (functions==null) functions=new ArrayList<Function>();
      functions.add(new Function(name, version));
  }
  /**
   *
   * @param function
   */
  public void addFunction(Function function) {
      if (functions==null) functions=new ArrayList<Function>();
      functions.add(function);
  }

  /**
   *
   * @param sitename
   */
  public void setSiteName(String sitename) {
      this.sitename=sitename;
  }

  /**
   *
   * @param username
   */
  public void setUserName(String username) {
      this.username=username;
  }

  /**
   *
   * @param firstname
   */
  public void setFirstName(String firstname) {
      this.firstname=firstname;
  }

  /**
   * 
   * @param lastname
   */
  public void setLastName(String lastname) {
      this.lastname=lastname;
  }

  /**
   *
   * @param fullname
   */
  public void setFullName(String fullname) {
      this.fullname=fullname;
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
   * @param siteurl
   */
  public void setSiteURL(String siteurl) {
      this.siteurl=siteurl;
  }

  /**
   *
   * @param userpictureurl
   */
  public void setUserPictureURL(String userpictureurl) {
      this.userpictureurl=userpictureurl;
  }

  /**
   *
   * @param downloadfiles
   */
  public void setDownloadFiles(boolean downloadfiles) {
      this.downloadfiles=downloadfiles;
  }

  /**
   *
   * @param field
   * @param value
   */
  public void setFunctionField(String field, String value) {
      if (field.equals("sitename") && !value.equals("")) setSiteName(value);
      if (field.equals("username") && !value.equals("")) setUserName(value);
      if (field.equals("firstname") && !value.equals("")) setFirstName(value);
      if (field.equals("lastname") && !value.equals("")) setLastName(value);
      if (field.equals("fullname") && !value.equals("")) setFullName(value);
      if (field.equals("userid") && !value.equals("")) setUserId(Integer.parseInt(value));
      if (field.equals("siteurl") && !value.equals("")) setSiteURL(value);
      if (field.equals("userpictureurl") && !value.equals("")) setUserPictureURL(value);
      if (field.equals("downloadfiles") && !value.equals("")) setDownloadFiles(value.equals("1")?true:false);
  }
}
