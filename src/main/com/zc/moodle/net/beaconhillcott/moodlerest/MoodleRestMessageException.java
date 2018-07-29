
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class MoodleRestMessageException extends MoodleRestException {

    /**
     *
     */
    public static final String NO_RECIPIENT="Recipient id is required";
    /**
     *
     */
    public static final String NO_MESSAGE="Message text is required";
    
    MoodleRestMessageException() {}
    
    MoodleRestMessageException(String msg) {
        super(msg);
    }

}
