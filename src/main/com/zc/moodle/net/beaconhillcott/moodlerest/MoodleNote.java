
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * <p>Class to create objects which contain the details of a Moodle note.</p>
 * 
 * @author Bill Antonia
 */
public class MoodleNote {
  
    /**
     * <p></p>
     */
    public static final String FORMAT_TEXT="text";
    /**
     * <p></p>
     */
    public static final String FORMAT_HTML="html";
  /**
   * <p></p>
   */
  public static final String PUBLISH_STATE_PERSONAL="personal";
  /**
   * <p></p>
   */
  public static final String PUBLISH_STATE_COURSE="course";
  /**
   * <p></p>
   */
  public static final String PUBLISH_STATE_SITE="site";
  
  private long userId=-1;
  private String publishState=null;
  private long courseId=-1;
  private String text=null;
  private String format=null;
  private String clientNoteId=null;
  private long noteId=-1;
  private String errorMessage=null;
  
  /**
   *
   */
  public MoodleNote() {}
  
  /**
   * <p>Constructor for creating a Moodle note error object.<br />Used internally.</p>
   * 
   * @param String clientNoteId String containing a user defined value to identify the note.
   * @param noteId
   * @param errorMessage
   * @throws MoodleRestNotesException
   */
  public MoodleNote(String clientNoteId, long noteId, String errorMessage) throws MoodleRestNotesException {
    this.clientNoteId=clientNoteId;
    this.noteId=noteId;
    this.errorMessage=errorMessage;
    if (noteId==-1) {
      throw new MoodleRestNotesException(this.errorMessage);
    }
  }
  
  /**
   * <p>Constructor to create a note entry.</p>
   * 
   * @param long userId Id of the user to which the note is about.
   * @param String publishState The state of the note, "site", "personal" or "course".
   * @param long courseId The ID of the course to which the note is to be attached.
   * @param String text The text of the note to be stored.
   * @param String format "html" or "text".
   * @param String clientNoteId String containing a user defined value to identify the note.
   */
  public MoodleNote(long userId, String publishState, long courseId, String text, String format, String clientNoteId) {
    this.userId=userId;
    this.publishState=publishState;
    this.courseId=courseId;
    this.text=text;
    this.format=format;
    this.clientNoteId=clientNoteId;
  }
  
  /**
   * <p>Method to return the userid the note is attached to.</p>
   * 
   * @return long userid
   */
  public long getUserId() { return userId; }
  
  /**
   * <p>Method to return the publish state of the note.</p>
   * 
   * @return String publishState
   */
  public String getPublishState() { return publishState; }
  
  /**
   * <p>Method to return the course id the note is attached to.</p>
   * 
   * @return long courseId
   */
  public long getCourseId() { return courseId; }
  
  /**
   * <p>Method to return the text of the note.</p>
   * 
   * @return String text
   */
  public String getText() { return text; }
  
  /**
   * <p>Method to return the format of the note.</p>
   * 
   * @return String format
   */
  public String getFormat() { return format; }
  
  /**
   * <p>Method to return the user defined client id of the note.</p>
   * 
   * @return String clientNoteId
   */
  public String getClientNoteId() { return clientNoteId; }
  
  /**
   * <p>Method to return the note id. Populated after the note has been sent to Moodle if no errors occur.</p>
   * 
   * @return long noteId
   */
  public long getNoteId() { return noteId; }
  
  /**
   * <p>Method to set the user id the note is to be attached to.</p>
   * 
   * @param long userId
   */
  public void setUserId(long userId) { this.userId=userId; }
  
  /**
   * <p>Method to set the publish state of the note.</p>
   * 
   * @param String publishState
   */
  public void setPublishState(String publishState) { this.publishState=publishState; }
  
  /**
   * <p>Method to set the course id to which the note is to be attached.</p>
   * 
   * @param long courseId
   */
  public void setCourseId(long courseId) { this.courseId=courseId; }
  
  /**
   * <p>Method to set the contents of the note.</p>
   * 
   * @param String text
   */
  public void setText(String text) { this.text=text; }
  
  /**
   * <p>Method to set the format of the note.</p>
   * 
   * @param String format
   */
  public void setFormat(String format) { this.format=format; }
  
  /**
   * <p>Method to set the user defined client note id.</p>
   * 
   * @param String clientNoteId
   */
  public void setClientNoteId(String clientNoteId) { this.clientNoteId=clientNoteId; }
  
  /**
   * <p>Method to set the note id.<br />
   * Used internally after a call to send the note to Moodle, the noteid attribute is then updated</p>
   * 
   * @param long noteId
   */
  public void setNoteId(long noteId) { this.noteId=noteId; }
  
  /**
   * <p>Method to set the errormessage attribute<br />
   * Used internally after a call to send the note when an error occurs.</p>
   * 
   * @param String errorMessage
   */
  public void setErrorMessage(String errorMessage) { this.errorMessage=errorMessage; }
  
  /**
   * <p>Method to set a MoodleNote objects attribute given its name and value as strings.</p>
   * 
   * @param String nodeName
   * @param String content
   */
  public void setMoodleNoteField(String nodeName,String content) {
    if (nodeName.equals("clientnoteid"))
      if (!content.isEmpty() && content!=null)
        setClientNoteId(content);
    if (nodeName.equals("noteid")) setNoteId(Integer.parseInt(content));
    if (getNoteId()==-1)
      if (nodeName.equals("errorMessage")) setErrorMessage(content);
  }
}
