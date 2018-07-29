
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * Class used to hold user/course memberships. Returned when fetching a list of users enrolled in a course.
 * @see MoodleRestCourse
 * @author Bill Antonia
 */
public class MoodleCourseUser {

    private long courseid=-1;
    private long userid=-1;

    /**
     * Constructor for bean requirements
     */
    public MoodleCourseUser(){}

    /**
     * Constructor setting user to course membership
     * @param courseid long
     * @param userid long
     */
    public MoodleCourseUser(long courseid, long userid) {
        this.courseid=courseid;
        this.userid=userid;
    }

    /**
     * Method to set the courseid attribute of a MoodleCourseUser object.
     * @param courseid long
     */
    public void setCourseId(long courseid) {
        this.courseid=courseid;
    }

    /**
     * Method to set the userid attribute of a MoodleCourseUser object.
     * @param userid long
     */
    public void setUserId(long userid) {
        this.userid=userid;
    }

    /**
     * Method to get the courseid attribute of a MoodleCourseUser object.
     * @return courseid long
     */
    public long getCourseId() {
        return courseid;
    }

    /**
     * Method to get the userid attribute of a MoodleCourseUser object.
     * @return userid long
     */
    public long getUserId() {
        return userid;
    }
}
