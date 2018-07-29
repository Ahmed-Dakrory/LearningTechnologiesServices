
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.io.InputStream;

/**
 * Path to the Entity file to be injected into the XML stream returned from a webservice.
 * Required as Moodle does not provide these and they are needed for the process of parsing the XML stream.
 *
 * @author Bill Antonia (Class only), file downloaded from the Internet.
 */
public class FindPath {

    /**
     * Stream resource
     */
    protected InputStream resource;

    /**
     * Method to get the XML Entity resource file as a stream
     */
    protected FindPath() {
        resource = this.getClass().getClassLoader().getResourceAsStream("main/com/zc/moodle/net/beaconhillcott/moodlerest/EntityInjection.xml");
    }
}
