
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.util.ArrayList;

/**
 * <p>Class for which to create object in which to store module information that has been attached to a course.</p>
 * 
 * @author Bill Antonia
 * @see MoodleRestCourse
 * @see MoodleModuleContent
 */
public class MoodleModule {
  
    /**
     * <p></p>
     */
    public static final boolean MODULE_VISIBLE_TO_STUDENTS_NO=false;
    /**
     * <p></p>
     */
    public static final boolean  MODULE_VISIBLE_TO_STUDENTS_YES=true;
          
  private long id=-1;
  private String url=null;
  private String name=null;
  private String description=null;
  private boolean visible=false;
  private String modicon=null;
  private String modname=null;
  private String modplural=null;
  private long availablefrom=-1;
  private long availableuntil=-1;
  private int indent=-1;
  private ArrayList<MoodleModuleContent> content=null;
  
  /**
   * <p>Constructor for bean purposes</p>
   */
  public MoodleModule() {}
  
  /**
   * <p>Constructor used to set up initial module information which will be completed later from data returned.</p>
   * 
   * @param id long
   */
  public MoodleModule(long id) {
    this.id=id;
    content=new ArrayList();
  }
  
  /**
   * <p>Costructor using complete module information.</p>
   * 
   * @param id long
   * @param name String
   * @param modicon String
   * @param modname String
   * @param modplural String
   * @param indent int
   */
  public MoodleModule(long id, String name, String modicon, String modname, String modplural, int indent) {
    this.id=id;
    this.name=name;
    this.modicon=modicon;
    this.modname=modname;
    this.modplural=modplural;
    this.indent=indent;
    content=new ArrayList();
  }
  
  /**
   * <p>Method to set the id attribute of a course module object.<br />
   * This call is used internally.</p>
   * 
   * @param id long
   */
  public void setId(long id) {
    this.id=id;
  }
  
  /**
   * <p>Method to set the url attribute of a course module object.<br />
   * This call is used internally.</p>
   * 
   * @param url String
   */
  public void setUrl(String url) {
    this.url=url;
  }
  
  /**
   * <p>Method to set the name attribute of a course module object.<br />
   * This call is used internally.</p>
   * 
   * @param name String
   */
  public void setName(String name) {
    this.name=name;
  }
  
  /**
   * <p>Method to set the description attribute of a course module object.<br />
   * This call is used internally.</p>
   * 
   * @param description String
   */
  public void setDescription(String description) {
    this.description=description;
  }
  
  /**
   * <p>Method to set the visible attribute of a course module object.<br />
   * This call is used internally.</p>
   * 
   * @param visible boolean
   */
  public void setVisible(boolean visible) {
    this.visible=visible;
  }
  
  /**
   * <p>Method to set the modicon attribute of a course module object.<br />
   * This call is used internally.</p>
   * 
   * @param modicon String
   */
  public void setModIcon(String modicon) {
    this.modicon=modicon;
  }
  
  /**
   * <p>Method to set the modname attribute of a course module object.<br />
   * This call is used internally.</p>
   * 
   * @param modname String
   */
  public void setModName(String modname) {
    this.modname=modname;
  }
  
  /**
   * <p>Method to set the modplural attribute of a course module object.<br />
   * This call is used internally.</p>
   * @param modplural String
   */
  public void setModPlural(String modplural) {
    this.modplural=modplural;
  }
  
  /**
   * <p>Method to set the availablefrom attribute of a course module object.<br />
   * This call is used internally.</p>
   * 
   * @param availablefrom long
   */
  public void setAvailableFrom(long availablefrom) {
    this.availablefrom=availablefrom;
  }
  
  /**
   * <p>Method to set the availableuntil attribute of a course module object.<br />
   * This call is used internally.</p>
   * 
   * @param availableuntil long
   */
  public void setAvailableUntil(long availableuntil) {
    this.availableuntil=availableuntil;
  }
  
  /**
   * <p>Method to set the indent attribute of a course module object.<br />
   * This call is used internally.</p>
   * 
   * @param indent int
   */
  public void setIndent(int indent) {
    this.indent=indent;
  }
  
  /**
   * <p>Method to get the id attribute of a course module object.</p>
   * 
   * @return id long
   */
  public long getId() {
    return id;
  }
  
  /**
   * <p>Method to get the url attribute of a course module object.</p>
   * 
   * @return url String
   */
  public String getUrl() {
    return url;
  }
  
  /**
   * <p>Method to get the name attribute of a course module object.</p>
   * 
   * @return name String
   */
  public String getName() {
    return name;
  }
  
  /**
   * <p>Method to get the description attribute of a course module object.</p>
   * 
   * @return description String
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * <p>Method to get the visible attribute of a course module object.</p>
   * 
   * @return visible boolean
   */
  public boolean getVisible() {
    return visible;
  }
  
  /**
   * <p>Method to get the modicon attribute of a course module object.</p>
   * 
   * @return modicon String
   */
  public String getModIcon() {
    return modicon;
  }
  
  /**
   * <p>Method to get the modname attribute of a course module object.</p>
   * 
   * @return modname String
   */
  public String getModName() {
    return modname;
  }
  
  /**
   * <p>Method to get the modplural attribute of a course module object.</p>
   * 
   * @return modplural String
   */
  public String getModPlural() {
    return modplural;
  }
  
  /**
   * <p>Method to get the availablefrom attribute of a course module object.</p>
   * 
   * @return availablefrom long
   */
  public long getAvailableFrom() {
    return availablefrom;
  }
  
  /**
   * <p>Method to get the availableuntil attribute of a course module object.</p>
   * 
   * @return availableuntil long
   */
  public long getAvailableUntil() {
    return availableuntil;
  }
  
  /**
   * <p>Method to get the indent attribute of a course module object.</p>
   * 
   * @return indent int
   */
  public int getIndent() {
    return indent;
  }
  
  /**
   * <p>Method to set attribute field of a course module object given the name of the field and value as Strings.</p>
   * 
   * @param name String
   * @param content String
   */
  public void setMoodleModuleField(String name, String content) {
    if (name.equals("id")) setId(Long.parseLong(content.trim()));
    if (name.equals("name")) setName(content);
    if (name.equals("description")) setDescription(content);
    if (name.equals("visible")) setVisible(Integer.parseInt(content)==0?MODULE_VISIBLE_TO_STUDENTS_NO:MODULE_VISIBLE_TO_STUDENTS_YES);
    if (name.equals("modicon")) setModIcon(content);
    if (name.equals("modname")) setModName(content);
    if (name.equals("modplural")) setModPlural(content);
    if (name.equals("availablefrom")) setAvailableFrom(Long.parseLong(content.trim()));
    if (name.equals("availableuntil")) setAvailableUntil(Long.parseLong(content.trim()));
    if (name.equals("indent")) setIndent(Integer.parseInt(content));
  }
  
  /**
   * <p>Method to add a MoodleModuleContent object to the ArrayList.</p>
   * 
   * @param content
   */
  public void addContent(MoodleModuleContent content) {
    this.content.add(content);
  }
  
  /**
   * <p>Method to get an array of MoodleModuleContent objects.</p>
   * 
   * @return MoodleModuleContent[]
   */
  public ArrayList<MoodleModuleContent> getContent() {
    if (content.isEmpty())
      return null;
    return content;
  }
}
