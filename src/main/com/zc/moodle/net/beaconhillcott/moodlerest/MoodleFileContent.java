
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

/**
 * <p>Class to store the file content as a Base64 uuencoded string.</p>
 * <p>Note no encoding takes place, this is left up to the developer as it may be implementation dependent.</p>
 * <p>Extends MoodleFileFile.</p>
 *
 * @author Bill Antonia
 * @see MoodleFileFile
 */
public class MoodleFileContent extends MoodleFileFile {

    /**
     *
     */
    protected String filecontent=null;

    /**
     * <p>Constructor for bean compatability</p>
     */
    public MoodleFileContent() {}

    /**
     * <p>Method to set the file content as a Base64 uuencoded string.</p>
     * <p>Encoding is left to the developer as it may be implementation dependent. So must be encoded before being stored into the object.</p>
     *
     * @param filecontent String
     */
    public void setFileContent(String filecontent) {
        this.filecontent=filecontent;
    }

    /**
     * <p>Method to get the Base64 uuencoded string held in the object.</p>
     *
     * @return filecontent String
     */
    public String getFileContent() {
        return filecontent;
    }

    /**
     * <p>Method to set a field given its name and value as strings.</p>
     * 
     * @param field String
     * @param value String
     */
    public void setMoodleFileContentField(String field, String value) {
        setMoodleFileFileField(field, value);
        if (field.equals("filecontent") && !value.isEmpty()) filecontent=value;
    }
}
