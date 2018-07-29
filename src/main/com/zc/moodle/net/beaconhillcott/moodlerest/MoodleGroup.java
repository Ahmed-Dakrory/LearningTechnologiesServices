
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * Class to create and hold the status of a MoodleGroup object. Used in the process of group creation and manipulation for courses.
 * 
 * @author Bill Antonia
 * @see MoodleRestGroup
 */
public class MoodleGroup {

    private long id=-1;
    private long courseid=-1;
    private String name=null;
    private String description=null;
    private String enrolmentkey=null;

    /**
     * Constructor for bean requirements
     */
    public MoodleGroup() {}

    /**
     * <p>Constructor to create a MoodleGroup object.</p>
     * <p>Setter method calls are needed to set other attributes of the object.</p>
     * @param id long
     */
    public MoodleGroup(long id) {
        this.id=id;
    }

    /**
     * <p>Constructor to build a basic MoodleGroup object ready to create a Moodle group.</p>
     *
     * @param courseid long
     * @param name String
     */
    public MoodleGroup(long courseid, String name) {
        this.courseid=courseid;
        this.name=name;
        this.description="";
        this.enrolmentkey="";
    }

    /**
     * <p>Constructor to build a MoodleGroup object ready to create a Moodle group.</p>
     *
     * @param courseid long
     * @param name String
     * @param description String
     */
    public MoodleGroup(long courseid, String name, String description) {
        this.courseid=courseid;
        this.name=name;
        this.description=description;
        this.enrolmentkey="";
    }

    /**
     * <p>Constructor to build a MoodleGroup object ready to create a Moodle group.</p>
     *
     * @param courseid long
     * @param name String
     * @param description String
     * @param enrolmentkey String
     */
    public MoodleGroup(long courseid, String name, String description, String enrolmentkey) {
        this.courseid=courseid;
        this.name=name;
        this.description=description;
        this.enrolmentkey=enrolmentkey;
    }

    /**
     * <p>Constructor to build a MoodleGroup object ready to create a Moodle group.<br />
     * Probably will never get used as it needs the id of the Moodle group.</p>
     *
     * @param id long
     * @param courseid long
     * @param name String
     * @param description String
     * @param enrolmentkey String
     */
    public MoodleGroup(long id, long courseid, String name, String description, String enrolmentkey) {
        this.id=id;
        this.courseid=courseid;
        this.name=name;
        this.description=description;
        this.enrolmentkey=enrolmentkey;
    }

    /**
     * <p>Method to set the id attribute of a MoodleGroup object.</p>
     * <p>Probably will never get used but present due to bean requirements.</p>
     *
     * @param id long
     */
    public void setId(long id) {
        this.id=id;
    }

    /**
     * <p>Method to set the courseid attribute of a MoodleGroup object.</
     * p>
     * @param courseid long
     */
    public void setCourseId(long courseid) {
        this.courseid=courseid;
    }

    /**
     * <p>Method to set the name attribute of a MoodleGroup object.</p>
     *
     * @param name String
     */
    public void setName(String name) {
        this.name=name;
    }

    /**
     * <p>Method to set the description attribute of a MoodleGroup object.</p>
     *
     * @param description String
     */
    public void setDescription(String description) {
        this.description=description;
    }

    /**
     * <p>Method to set the enrolmentkey attribute of a MoodleGroup object.</p>
     *
     * @param enrolmentkey String
     */
    public void setEnrolmentKey(String enrolmentkey) {
        this.enrolmentkey=enrolmentkey;
    }

    /**
     * <p>Method to get the id attribute of a MoodleGroup object.</p>
     *
     * @return id long
     */
    public long getId() {
        return id;
    }

    /**
     * <p>Method to get the courseid attribute of a MoodleGroup object.</p>
     *
     * @return courseid long
     */
    public long getCourseId() {
        return courseid;
    }

    /**
     * <p>Method to get the name attribute of a MoodleGroup object.</p>
     *
     * @return name String
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Method to get the description attribute of a MoodleGroup object.</p>
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Method to get the enrolmentkey attribute of a MoodleGroup object.</p>
     *
     * @return enrolmentkey String
     */
    public String getEnrolmentKey() {
        return enrolmentkey;
    }

    /**
     * <p>Method to set a MoodleGroup attribute given the name of the attribute and its value, both passed as String. Automatic conversion to the internal type takes place.</p>
     * 
     * @param nodeName String
     * @param content String
     */
    public void setMoodleGroupField(String nodeName, String content) {
        if (nodeName.equals("id")) setId(Long.parseLong(content.trim()));
        if (nodeName.equals("name")) setName(content);
        if (nodeName.equals("courseid")) setCourseId(Long.parseLong(content.trim()));
        if (nodeName.equals("description")) setDescription(content);
        if (nodeName.equals("enrolmentkey")) setEnrolmentKey(content);
    }
}
