
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * <p>Class to create an object which will hold the parent information of a file from a call to MoodleRestFile.getFiles.</p>
 *
 * @author Bill Antonia
 * @see MoodleRestFile
 */
public class MoodleFileParent {

    /**
     *
     */
    protected long contextid=-1;
    /**
     *
     */
    protected String component=null;
    /**
     *
     */
    protected String filearea=null;
    /**
     *
     */
    protected long itemid=-1;
    /**
     *
     */
    protected String filepath=null;
    /**
     *
     */
    protected String filename=null;

    /**
     *
     */
    public MoodleFileParent() {}

    /**
     * <p>Method to set the contextid attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @param contextid long
     * @see MoodleRestFile
     */
    public void setContextId(long contextid) {
        this.contextid=contextid;
    }

    /**
     * <p>Method to set the component attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @param component String
     * @see MoodleRestFile
     */
    public void setComponent(String component) {
        this.component=component;
    }

    /**
     * <p>Method to set the filearea attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @param filearea String
     * @see MoodleRestFile
     */
    public void setFileArea(String filearea) {
        this.filearea=filearea;
    }

    /**
     * <p>Method to set the itemid attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @param itemid long
     * @see MoodleRestFile
     */
    public void setItemId(long itemid) {
        this.itemid=itemid;
    }

    /**
     * <p>Method to set the filepath attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @param filepath String
     * @see MoodleRestFile
     */
    public void setFilePath(String filepath) {
        this.filepath=filepath;
    }

    /**
     * <p>Method to set the filename attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @param filename String
     * @see MoodleRestFile
     */
    public void setFileName(String filename) {
        this.filename=filename;
    }

    /**
     * <p>Method to get the contextid attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @return long
     * @see MoodleRestFile
     */
    public long getContextId() {
        return contextid;
    }

    /**
     * <p>Method to get the component attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @return String
     * @see MoodleRestFile
     */
    public String getComponent() {
        return component;
    }

    /**
     * <p>Method to get the filearea attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @return String
     * @see MoodleRestFile
     */
    public String getFileArea() {
        return filearea;
    }

    /**
     * <p>Method to get the itemid attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @return long
     * @see MoodleRestFile
     */
    public long getItemId() {
        return itemid;
    }

    /**
     * <p>Method to get the filepath attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @return String
     * @see MoodleRestFile
     */
    public String getFilePath() {
        return filepath;
    }

    /**
     * <p>Method to get the filename attribute of a MoodleFileParent object from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @return String
     * @see MoodleRestFile
     */
    public String getFileName() {
        return filename;
    }

    /**
     * <p>Method to set an attribute of a MoodleFileParent object given its field name and value as strings from data returned by a call to MoodleRestFile.getfiles.</p>
     *
     * @param field String
     * @param value String
     * @see MoodleRestFile
     */
    public void setMoodleFileParentField(String field, String value) {
        if (field.equals("contextid") && !value.isEmpty()) setContextId(Long.parseLong(value));
        if (field.equals("component") && !value.isEmpty()) setComponent(value);
        if (field.equals("filearea") && !value.isEmpty()) setFileArea(value);
        if (field.equals("itemid") && !value.isEmpty()) setItemId(Long.parseLong(value));
        if (field.equals("filepath") && !value.isEmpty()) setFilePath(value);
        if (field.equals("filename") && !value.isEmpty()) setFileName(value);
    }
    
}
