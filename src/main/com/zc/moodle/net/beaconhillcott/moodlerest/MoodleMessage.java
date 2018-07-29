
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * <p>Class to create an object which will hold the data required to send an instant message between users.</p>
 *
 * @author Bill Antonia
 * @see MoodleRestMessage
 */
public class MoodleMessage {
  
  private long touserid=-1;
  private String text=null;
  private String clientmsgid=null;
  private long msgid=-1;
  private String errormessage=null;

  /**
   * <p>Constructor for bean purposes.</p>
   */
  public MoodleMessage() {}

  /**
   * <p>Costructor to create a MoodleMessage object.</p>
   * <p>This is all which is required to send a message between users. The sender is the account which is used to access the webservice.</p>
   *
   * @param touserid long
   * @param text String
   */
  public MoodleMessage(long touserid, String text) {
      this.touserid=touserid;
      this.text=text;
  }

  /**
   * <p>Constructor to create a MoodleMessage object with an optional client message id.</p>
   *
   * @param touserid long
   * @param text String
   * @param clientmsgid String
   */
  public MoodleMessage(long touserid, String text, String clientmsgid) {
      this.touserid=touserid;
      this.text=text;
      this.clientmsgid=clientmsgid;
  }

  /**
   * <p>Method to set the user id to which the message is to be sent.</p>
   *
   * @param touserid long
   */
  public void setToUserId(long touserid) {
      this.touserid=touserid;
  }

  /**
   * <p>Method to set the message string.</p>
   *
   * @param text String
   */
  public void setText(String text) {
      this.text=text;
  }

  /**
   * <p>Method to set the clientmsgid attribute.</p>
   *
   * @param clientmsgid String
   */
  public void setClientMsgId(String clientmsgid) {
      this.clientmsgid=clientmsgid;
  }

  /**
   * <p>Method used to set the msgid attribute.</p>
   * <p>Used internally after a call to send a message.</p>
   *
   * @param msgid long
   */
  public void setMsgId(long msgid) {
      this.msgid=msgid;
  }

  /**
   * <p>Method to set the errormessage attribute.</p>
   * <p>Used internally, if a message cannot be sent to a user the reason will be stored using this method.</p>
   *
   * @param errormessage String
   */
  public void setErrorMessage(String errormessage) {
      this.errormessage=errormessage;
  }

  /**
   * <p>Method to get the userid of the user in the object.</p>
   *
   * @return long
   */
  public long getToUserId() {
      return touserid;
  }

  /**
   * <p>Method to return the message stored in the MoodleMessage object.</p>
   *
   * @return String
   */
  public String getText() {
      return text;
  }

  /**
   *<p>Method to return the clientmsgid attribute or null if not set.</p>
   * 
   * @return String
   */
  public String getClientMsgId() {
      return clientmsgid;
  }

  /**
   * <p>Method to return the msgid attribute. This is set after the message has been sent.</p>
   * 
   * @return long
   */
  public long getMsgId() {
      return msgid;
  }

  /**
   * <p>Method to return the errormessage atribute after a message has been attempted to be sent. If no error this will be set to null.</p>
   * 
   * @return String
   */
  public String getErrorMessage() {
      return errormessage;
  }

  /**
   * <p>Method to check if an error occured in sending a message. No error false, error true.</p>
   * 
   * @return boolean
   */
  public boolean isError() {
      return errormessage!=null;
  }

  /**
   * <p>Method to set a MoodleMessage attribute given the field name and value as strings.</p>
   * 
   * @param nodeName String
   * @param value String
   */
  public void setMoodleMesageField(String nodeName, String value) {
      if (nodeName.equals("msgid")) setMsgId(Long.parseLong(value));
      if (nodeName.equals("clientmsgid")) setClientMsgId(value);
      if (nodeName.equals("errormessage")) setErrorMessage(value);
  }
}
