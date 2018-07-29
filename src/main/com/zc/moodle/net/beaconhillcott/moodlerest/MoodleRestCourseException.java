
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class MoodleRestCourseException extends MoodleRestException {

    /**
     *
     */
    public static final String NO_LEGACY_CALL="No legacy call";
    
    MoodleRestCourseException() {}

    MoodleRestCourseException(String msg) {
        super(msg);
    }
}
