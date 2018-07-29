
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * <p>Class to relate a user to a role within a particular context.</p>
 * <p>Problem here as of 2011-02-13 there is no way of obtaining the contextid or roleid programatically through the standard web services interface.</p>
 * @see MoodleRestEnrol
 * @author Bill Antonia
 */
public class MoodleEnrolUser {

    private long roleid=-1;
    private long userid=-1;
    private long contextid=-1;

    /**
     * <p>Constructor for bean requirements.</p>
     */
    public MoodleEnrolUser() {}

    /**
     * <p>Constructor to create a MoodleEnrolUser object with known roleid, userid and contextid.</p>
     *
     * @param roleid long
     * @param userid long
     * @param contextid long
     */
    public MoodleEnrolUser(long roleid, long userid, long contextid) {
        this.roleid=roleid;
        this.userid=userid;
        this.contextid=contextid;
    }

    /**
     * <p>Method to set the roleid attribute of a MoodleEnrolUser object.</p>
     * <p>The roleid currently cannot be retrieved through the standard web services interfaces.</p>
     *
     * @param roleid long
     */
    public void setRoleId(long roleid) {
        this.roleid=roleid;
    }

    /**
     * <p>Method to set the userid attribute of a MoodleEnrolUser object.</p>
     * @param userid long
     */
    public void setUserId(long userid) {
        this.userid=userid;
    }

    /**
     * <p>Method to set the contextid attribute of a MoodleEnrolUser object.</p>
     * <p>The contextid currently cannot be retrieved through the standard web services interfaces.</p>
     *
     * @param contextid long
     */
    public void setContextId(long contextid) {
        this.contextid=contextid;
    }

    /**
     * <p>Method to get the roleid attribute of a MoodleEnrolUser object.</p>
     * <p>The roleid currently cannot be retrieved through the standard web services interfaces.</p>
     * 
     * @return roleid long
     */
    public long getRoleId() {
        return roleid;
    }

    /**
     * <p>Method to get the userid attribute of a MoodleEnrolUser object.</p>
     *
     * @return userid long
     */
    public long getUserId() {
        return userid;
    }

    /**
     * <p>Method to get the contextid attribute of a MoodleEnrolUser object.</p>
     * <p>The contextid currently cannot be retrieved through the standard web services interfaces.</p>
     *
     * @return contextid long
     */
    public long getContextId() {
        return contextid;
    }
}
