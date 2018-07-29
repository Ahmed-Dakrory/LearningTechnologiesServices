
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.NodeList;

/**
 * <p>Class to call the Moodle REST WebServices.</p>
 * @author Bill Antonia
 */
public class MoodleRestWebService {
    
  //core_webservice_get_site_info
    /**
     * <p>Method to fetch information about the Moodle site.</p>
     * 
     * @return MoodleWebService
     * @throws MoodleRestWebServiceException
     * @throws MoodleRestException
     */
    public static MoodleWebService getSiteInfo() throws MoodleRestWebServiceException, MoodleRestException {
        MoodleWebService service=null;
        String functionCall = MoodleCallRestWebService.isLegacy() ? MoodleServices.MOODLE_WEBSERVICE_GET_SITE_INFO : MoodleServices.CORE_WEBSERVICE_GET_SITE_INFO;
        StringBuilder data = new StringBuilder();
        try {
            if (MoodleCallRestWebService.getAuth() == null) {
                throw new MoodleRestWebServiceException();
            } else {
                data.append(MoodleCallRestWebService.getAuth());
            }
            data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
            NodeList elements=MoodleCallRestWebService.call(data.toString());
            Function function=null;
            for (int j=0;j<elements.getLength();j++) {
                String parent=elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode().getNodeName();
                if (parent.equals("KEY"))
                    parent=elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("name").getNodeValue();
                String content=elements.item(j).getTextContent();
                String nodeName=elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
                if (parent.equals("#document") && nodeName.equals("sitename")) {
                    service=new MoodleWebService();
                    service.setSiteName(content);
                } else {
                    if (parent.equals("#document")) {
                        service.setFunctionField(nodeName, content);
                    } else {
                        if (parent.equals("functions") && nodeName.equals("name")) {
                            if (function!=null) {
                                service.addFunction(function);
                                function=new Function();
                                function.setName(content);
                            } else {
                                function=new Function();
                                function.setName(content);
                            }
                        } else {
                            if (parent.equals("functions")) {
                                function.setFunctionField(nodeName, content);
                            }
                        }
                    }
                }
            }
            if (function!=null) {
                service.addFunction(function);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MoodleRestWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return service;
    }
}
