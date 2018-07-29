
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * <p>Class to store the file information results once it has been uploaded</p>
 * <p>Extends MoodleFileParent</p>
 *
 * @author Bill Antonia
 * @see MoodleFileParent
 */
public class MoodleFileFile extends MoodleFileParent {

    private boolean isdir=false;
    private String url=null;

    /**
     * <p>Constructor for bean compatability</p>
     */
    public MoodleFileFile() {}

    /**
     * <p>Method to set the isdir attribute.</p>
     * <p>Only useful in construction of the object attributes by the call to the MoodleRestFile.upload webservice.</p>
     *
     * @param isdir boolean
     */
    public void setIsDir(boolean isdir) {
        this.isdir=isdir;
    }

    /**
     * <p>Method to set the url attribute.</p>
     * <p>Only useful in construction of the object attributes by the call to the MoodleRestFile.upload webservice.</p>
     *
     * @param url String
     */
    public void setURL(String url) {
        this.url=url;
    }

    /**
     * <p>Method to test the isdir attribute.</p>
     *
     * @return boolean
     */
    public boolean isDir() {
        return isdir;
    }
    
    /**
     * <p>Method to get the isdir attribute.</p>
     *
     * @return boolean
     */
    public boolean getIsDir() {
        return isdir;
    }

    /**
     * <p>Method to get the url attribute.</p>
     *
     * @return String
     */
    public String getURL() {
        return url;
    }

    /**
     * <p>Method to set a field given its name and value as strings.</p>
     * <p>Only useful in construction of the object attributes by the call to the MoodleRestFile.upload webservice.</p>
     *
     * @param field String
     * @param value String
     */
    public void setMoodleFileFileField(String field, String value) {
        setMoodleFileParentField(field, value);
        if (field.equals("isdir") && !value.isEmpty()) setIsDir(Integer.parseInt(value)!=0);
        if (field.equals("url") && !value.isEmpty()) setURL(value);
    }
}
