
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * <p>Class to store the details of the connection between a user and a Moodle group.</p>
 *
 * @see MoodleRestGroup
 * @author Bill Antonia
 */
public class MoodleGroupUser {

    private long groupid=-1;
    private long userid=-1;

    /**
     * <p>Constructor for bean requirements</p>
     */
    public MoodleGroupUser() {}

    /**
     * <p>Constructor to create a MoodleGroupUser object given the group and user ids.</p>
     *
     * @param groupid long
     * @param userid long
     */
    public MoodleGroupUser(long groupid, long userid) {
        this.groupid=groupid;
        this.userid=userid;
    }

    /**
     * <p>Method to set the groupid attribute of a MoodleGroupUser object.</p>
     *
     * @param groupid long
     */
    public void setGroupId(long groupid) {
        this.groupid=groupid;
    }

    /**
     * <p>Method to set the userid attribute of a MoodleGroupUser object.</p>
     *
     * @param userid long
     */
    public void setUserId(long userid) {
        this.userid=userid;
    }

    /**
     * <p>Method to get the groupid attribute of a MoodleGroupUser object.</p>
     *
     * @return groupid long
     */
    public long getGroupId() {
        return groupid;
    }

    /**
     * <p>Method to get the userid attribute of a MoodleGroupUser object.</p>
     * 
     * @return userid long
     */
    public long getUserId() {
        return userid;
    }
}
