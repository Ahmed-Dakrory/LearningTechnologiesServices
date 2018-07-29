
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class MoodleRestException extends Exception {

    /**
     *
     */
    public static final String NO_LEGACY="There is no legacy call for this webservice";
    /**
     *
     */
    public static final String AUTH_NULL="No credential provided";
    /**
     *
     */
    public static final String REQUIRED_PARAMETER="Parameter is required";

    MoodleRestException() {}

    MoodleRestException(String msg) {
        super(msg);
    }
}
