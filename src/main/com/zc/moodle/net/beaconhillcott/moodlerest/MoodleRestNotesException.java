
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class MoodleRestNotesException extends MoodleRestException {
  MoodleRestNotesException() {}

  /**
   *
   */
  public static final String  NOTES_NULL="Note cannot be null";
  /**
   *
   */
  public static final String  USERID_NOT_SET="User id not set";
  /**
   *
   */
  public static final String  PUBLISHSTATE_NULL="Publish state is null";
  /**
   *
   */
  public static final String  COURSEID_NOT_SET="Course id not set";
  /**
   *
   */
  public static final String  TEXT_NULL="Text is null";
  /**
   *
   */
  public static final String  FORMAT_INCORRECT="Format is incorrect, \"text\" or \"html\" only";
  /**
   *
   */
  public static final String  CLIENTNOTEID_NOT_SET="Client note id not set";
  /**
   * 
   */
  public static final String  NO_LEGACY_CALL="No legacy call";
  
    MoodleRestNotesException(String msg) {
        super(msg);
    }
}
