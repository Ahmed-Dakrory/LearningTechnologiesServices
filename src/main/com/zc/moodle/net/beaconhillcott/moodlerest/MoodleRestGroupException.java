
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class MoodleRestGroupException extends MoodleRestException {

    /**
     *
     */
    public static final String NO_LEGACY_CALL="No legacy call";
    
    MoodleRestGroupException() {}

    MoodleRestGroupException(String msg) {
        super(msg);
    }

}
