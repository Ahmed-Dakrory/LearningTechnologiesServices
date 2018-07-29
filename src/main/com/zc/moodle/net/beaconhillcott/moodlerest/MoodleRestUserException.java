
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 *
 * @author Bill Antonia
 */
public class MoodleRestUserException extends MoodleRestException {

    /**
     *
     */
    public static final String TOKEN_NULL="Token cannot be null";
    /**
     *
     */
    public static final String USERNAME_NULL="Username cannot be null";
    /**
     *
     */
    public static final String PASSWORD_NULL="Password cannot be null";
    /**
     *
     */
    public static final String FIRSTNAME_NULL="Firstname cannot be null";
    /**
     *
     */
    public static final String LASTNAME_NULL="Lastname cannot be null";
    /**
     *
     */
    public static final String EMAIL_NULL="Email cannot be null";
    /**
     *
     */
    public static final String URL_NULL="URL cannot be null";
    /**
     *
     */
    public static final String USER_NULL="User cannot be null";
    /**
     *
     */
    public static final String INVALID_USERID="Bad user id";
    /**
     *
     */
    public static final String INVALID_USER="Invalid user";
    /**
     *
     */
    public static final String NO_LEGACY_CALL="No legacy call";

    MoodleRestUserException() {}

    MoodleRestUserException(String msg) {
        super(msg);
    }

}
