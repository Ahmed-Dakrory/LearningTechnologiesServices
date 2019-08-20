
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.io.*;
import java.net.*;
import java.net.URL;
import java.util.logging.*;
import javax.xml.xpath.*;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * <p>Contains static routines to initialise the REST web service for authentication and to call the Moodle web service.</p>
 * <p>Before a call to any Moodle webservice, the static authentication fields have to be set up by calling one of the init methods of this class.</p>
 * <p>Choose either token or username/password as an authentication method. If both are set, token authentication takes precedence.</p>
 * 
 * <p>Moodle versions up to 2.2, at the time of writing this library, can call the depreciated webservices. If this is the case, set the legacy flag to true.
 * However, if using Moodle version 2.2 or above leave the legacy setting to its default as false so to use the new calls introduced in Moodle 2.2.</p>
 * 
 * <p>All webservice calls use the call method in this class passing parameters by HTML POST protocol.</p>
 * 
 * @author Bill Antonia
 */
public class MoodleCallRestWebService {

    private static final String MOODLE_REST_VERSION="Version  0.1.5";
    private static String token=null;
    private static String username=null;
    private static String password=null;
    private static String url=null;
    private static boolean legacy=false;
    private static boolean debug=false;

    /**
     * Constructor for bean usage.
     */
    public MoodleCallRestWebService() {
    }

    /**
     * <p>One init method MUST be called before any other call to any other MoodleRestXXXXXXX Classes to initialise the Moodle URL web server service and authentication.</p>
     * <p>url contains the URL of the REST server service within the Moodle site.<br />
     * token Contains the token provided by the Moodle installation for access to the configured REST web services.</p>
     * 
     * @param url String
     * @param token String
     */
    public static void init(String url, String token) {
        MoodleCallRestWebService.token=token;
        MoodleCallRestWebService.url=url;
    }
    
    /**
     * <p>One init method MUST be called before any other call to any other MoodleRestXXXXXXX Classes to initialise the Moodle URL web server service and authentication.</p>
     * <p>url contains the URL of the REST server service within the Moodle site.<br />
     * token Contains the token provided by the Moodle installation for access to the configured REST web services.<br />
     * Legacy if set to true will use the pre Moodle 2.2 function calls.</p>
     * 
     * @param url String
     * @param token String
     * @param legacy boolean
     */
    public static void init(String url, String token, boolean legacy) {
        MoodleCallRestWebService.token=token;
        MoodleCallRestWebService.url=url;
        MoodleCallRestWebService.legacy=legacy;
    }

    /**
     * <p>One init method MUST be called before any other call to any other MoodleRestXXXXXXX Classes to initialise the Moodle URL web server service and authentication.</p>
     * <p>url contains the URL of the REST server service within the Moodle site.<br/>
     * username password pair Contains the username and password for authentication to the Moodle configured REST web services.</p>
     * 
     * @param url String
     * @param username String
     * @param password String
     */
    public static void init(String url, String username, String password) {
        MoodleCallRestWebService.username=username;
        MoodleCallRestWebService.password=password;
        MoodleCallRestWebService.url=url;
    }
    
    /**
     * <p>One init method MUST be called before any other call to any other MoodleRestXXXXXXX Classes to initialise the Moodle URL web server service and authentication.</p>
     * <p>url contains the URL of the REST server service within the Moodle site.<br />
     * username password pair Contains the username and password for authentication to the Moodle configured REST web services.<br />
     * Legacy if set to true will use the pre Moodle 2.2 function calls.</p>
     * 
     * @param url String
     * @param username String
     * @param password String
     * @param legacy boolean
     */
    public static void init(String url, String username, String password, boolean legacy) {
        MoodleCallRestWebService.username=username;
        MoodleCallRestWebService.password=password;
        MoodleCallRestWebService.url=url;
        MoodleCallRestWebService.legacy=legacy;
    }

    /**
     * <p>Method to return the version of this library.</p>
     * 
     * @return
     */
    public static String getVersion() {
        return MoodleCallRestWebService.MOODLE_REST_VERSION;
    }

    /**
     * Returns the currently configured token or null if not set.
     * 
     * @return token String
     */
    public static String getToken() {
        return token;
    }

    /**
     * Returns the currently configured username or null if not set.
     * 
     * @return username String
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Returns the currently configured password or null if not set.
     * 
     * @return password String
     */
    public static String getPassword() {
        return password;
    }

    /**
     * Returns the currently configured URL or null if not set.
     * 
     * @return url String
     */
    public static String getURL() {
        return url;
    }
    
    /**
     * Checks the legacy attribute to see if the legacy/depreciated calls should be used.
     * 
     * @return boolean
     */
    public static boolean isLegacy() {
        return legacy;
    }

    /**
     * Sets the authentication token.
     * 
     * @param token String
     */
    public static void setAuth(String token) {
        MoodleCallRestWebService.token=token;
    }

    /**
     * Sets the username/password pair for authentication.
     * 
     * @param username String
     * @param password String
     */
    public static void setAuth(String username, String password) {
        MoodleCallRestWebService.username=username;
        MoodleCallRestWebService.password=password;
    }
    
    /**
     * Modifies the legacy flag.
     * 
     * @param legacy boolean
     */
    public static void setLegacy(boolean legacy) {
        MoodleCallRestWebService.legacy=legacy;
    }

    /**
     * <p>Returns the HTTP encoded string required for web service authentication.</p>
     * <p>Order of authentication methods: token then username/password, if token not initialised or null if both methods not initialised.</p>
     * 
     * @return String containing HTTP encoded string or null.
     * @throws UnsupportedEncodingException
     */
    public static String getAuth() throws UnsupportedEncodingException {
        StringBuilder data=new StringBuilder();
        if (MoodleCallRestWebService.getToken()!=null) {
            data.append(URLEncoder.encode("wstoken", "UTF-8")).append("=").append(URLEncoder.encode(MoodleCallRestWebService.getToken(), "UTF-8"));
        } else {
            if (MoodleCallRestWebService.getUsername()!=null && MoodleCallRestWebService.getPassword()!=null) {
                data.append(URLEncoder.encode("wsusername", "UTF-8")).append("=").append(URLEncoder.encode(MoodleCallRestWebService.getUsername(), "UTF-8"));
                data.append("&"); // Fix by César Martínez
                data.append(URLEncoder.encode("wspassword", "UTF-8")).append("=").append(URLEncoder.encode(MoodleCallRestWebService.getPassword(), "UTF-8"));
            } else
                return null;
        }
        return data.toString();
    }

    /**
     * Sets the URL to the REST server service within the Moodle site.
     * 
     * @param url String
     */
    public static void setURL(String url) {
        MoodleCallRestWebService.url=url;
    }

    /**
     * <p>Set debug mode. Enables/Disables viewing of the raw output from webservices calls on a console.<br />
     * Defaults to false.</p>
     * 
     * @param debug boolean true enable debug, false disable debug.
     */
    public static void setDebug(boolean debug) {
      MoodleCallRestWebService.debug=debug;
    }
    
    /**
     * <p>This calls the external Moodle web service.</p>
     * <p>params String containing the parameters of the call.<br />
     * elements NodeList containing name/value pairs of returned XML data.</p>
     * @param params String
     * @return elements NodeList
     * @throws MoodleRestException
     */
    public static NodeList call(String params) throws MoodleRestException {
        NodeList elements = null;
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)getUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(params);
            writer.flush();
            writer.close();
            // Used for testing
            StringBuilder buffer=new StringBuilder();
            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line=reader.readLine();
            buffer.append(line).append('\n');
            //FindPath fp=new FindPath();
          
            InputStream resource =  Thread.currentThread().getContextClassLoader().getResourceAsStream("main/com/zc/moodle/net/beaconhillcott/moodlerest/EntityInjection.xml");
            BufferedReader entities=new BufferedReader(new InputStreamReader(/*fp.*/resource));
            String entitiesLine=null;
            while ((entitiesLine=entities.readLine())!=null) {
                //System.out.println(entitiesLine);
                buffer.append(entitiesLine).append('\n');
            }
            entities.close();
            boolean error=false;
            while ((line=reader.readLine())!=null) {
                //System.out.println(line);
                if (error)
                  throw new MoodleRestException(line.substring(line.indexOf('>')+1, line.indexOf('<', line.indexOf('>')+1)));
                if (line.contains("<EXCEPTION"))
                  error=true;
                buffer.append(line).append('\n');
            }
            reader.close();
            if (debug) {
              System.out.println(buffer.toString());
            }
            XPath xpath=XPathFactory.newInstance().newXPath();
            //InputSource source=new InputSource(connection.getInputStream());
            InputSource source=new InputSource( new ByteArrayInputStream(buffer.toString().getBytes()));
            elements = (NodeList)xpath.evaluate("//VALUE", source, XPathConstants.NODESET);
            // Used for testing
            if (debug) {
              for (int i=0;i<elements.getLength();i++) {
                String parent=elements.item(i).getParentNode().getParentNode().getParentNode().getParentNode().getNodeName();
                if (parent.equals("KEY"))
                  parent=elements.item(i).getParentNode().getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("name").getNodeValue();
                String content=elements.item(i).getLocalName();
                String nodeName=elements.item(i).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
                System.out.println("parent="+parent+" nodeName="+nodeName+" content="+content);
              }
            }
            connection.disconnect();
        } catch (XPathExpressionException ex) {
          Logger.getLogger(MoodleCallRestWebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MoodleCallRestWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return elements;
    }
}
