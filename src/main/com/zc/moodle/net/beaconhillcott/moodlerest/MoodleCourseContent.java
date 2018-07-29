
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <p>Class to hold information about the content of a course.</p>
 *
 * @author Bill Antonia
 */
public class MoodleCourseContent {
  
  private long id=-1;
  private String name=null;
  private boolean visible=false;
  private String summary=null;
  private Hashtable<Long, MoodleModule> modules=null;
  
  /**
   * <p>Constructor for bean compatability.</p>
   */
  public MoodleCourseContent() {}
  
  /**
   * <p>Constructor to build an object to store information about course content initially setting the course id.<br />
   * Only useful in construction of data from the webservice.</p>
   *
   * @param id long
   */
  public MoodleCourseContent(long id) {
    this.id=id;
    modules=new Hashtable();
  }
  
  /**
   * <p>Constructor to build an object to store information about course content initially setting the course id, name and the summary.<br />
   * Only useful in construction of data from the webservice.</p>
   *
   * @param id long
   * @param name String
   * @param summary String
   */
  public MoodleCourseContent(long id, String name, String summary) {
    this.id=id;
    this.name=name;
    this.summary=summary;
    modules=new Hashtable();
  }
  
  /**
   * <p>Constructor to build an object to store information about course content initially setting the course id, name, visibility and the summary.<br />
   * Only useful in construction of data from the webservice.</p>
   *
   * @param id long
   * @param name String
   * @param visible 
   * @param summary String
   */
  public MoodleCourseContent(long  id, String name, boolean visible, String summary) {
    this.id=id;
    this.name=name;
    this.visible=visible;
    this.summary=summary;
    modules=new Hashtable();
  }
  
  /**
   * <p>Method to set the course id in the object.<br />
   * Only useful in construction of data from the webservice.</p>
   *
   * @param id long
   */
  public void setId(long id) {
    this.id=id;
  }
  
  /**
   * <p>Method to set the course name in the object.<br />
   * Only useful in construction of data from the webservice.</p>
   *
   * @param name String
   */
  public void setName(String name) {
    this.name=name;
  }
  
  /**
   * <p>Method to set the course visibility in the object.<br />
   * Only useful in construction of data from the webservice.</p>
   *
   * @param visible boolean
   */
  public void setVisible(boolean visible) {
    this.visible=visible;
  }
  
  /**
   * <p>Method to set the course summary in the object.<br />
   * Only useful in construction of data from the webservice.</p>
   *
   * @param summary String
   */
  public void setSummary(String summary) {
    this.summary=summary;
  }
  
  /**
   * <p>Method to get the id of course in the object.</p>
   *
   * @return id long
   */
  public long getId() {
    return id;
  }
  
  /**
   * <p>Method to get the name of the course in the object.</p>
   *
   * @return name String
   */
  public String getName() {
    return name;
  }
  
  /**
   * <p>Method to get the visibility of the course in the object.</p>
   *
   * @return visible boolean
   */
  public boolean getVisible() {
    return visible;
  }
  
  /**
   * <p>Method to test the visibility of the course in the object.</p>
   *
   * @return visible boolean
   */
  public boolean isVisible() {
    return visible;
  }
  
  /**
   * <p>Method to get the summary of the course in the object.</p>
   *
   * @return summary
   */
  public String getSummary() {
    return summary;
  }
  
  /**
   * <p>Method to add module object to a course content object.<br />
   * Only useful in construction of data from the webservice.</p>
   *
   * @param module MoodleModule
   */
  public void addMoodleModule(MoodleModule module) {
    modules.put(module.getId(), module);
  }
  
  /**
   * <p>Method to set add a MoodleModule object to the course content object.<br />
   * Only useful in construction of data from the webservice.</p>
   *
   * @return MoodleModule[]
   */
  public MoodleModule[] getMoodleModules() {
    if (modules==null)
      return null;
    if (modules.isEmpty())
      return null;
    MoodleModule[] results=new MoodleModule[modules.size()];
    Enumeration<MoodleModule> elements = modules.elements();
    for (int i=0; elements.hasMoreElements(); i++) {
      results[i]=elements.nextElement();
    }
    return results;
  }
  
  /**
   * <p>Method to set field in the object given the field name and value as strings.<br />
   * Only useful in construction of data from the webservice.</p>
   * 
   * @param name String
   * @param value String
   */
  public void setMoodleCourseContentField(String name, String value) {
    if (name.equals("id")) setId(Long.parseLong(value));
    if (name.equals("name")) setName(value);
    if (name.equals("visible")) setVisible(!value.equals("0"));
    if (name.equals("summary")) setSummary(value);
  }
}
