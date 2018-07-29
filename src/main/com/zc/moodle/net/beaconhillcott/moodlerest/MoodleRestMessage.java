
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.NodeList;

/**
 * <p>Class to call Moodle REST message web services.</p>
 * 
 * @author Bill Antonia
 */
public class MoodleRestMessage {
  
  /**
   * <p>Method to send a single message between users.</p>
   * 
   * @param MoodleMessage message
   * @return MoodleMessage 
   * @throws MoodleRestMessageException
   * @throws MoodleRestException 
   */
  public static MoodleMessage sendInstantMessage(MoodleMessage message) throws MoodleRestMessageException, MoodleRestException {
    MoodleMessage[] messages=new MoodleMessage[1];
    messages[0]=message;
    messages=sendInstantMessages(messages);
    return messages[0];
  }
  
  //core_message_send_instant_messages
    /**
     * <p>Method to send messages between the caller and other users.</p>
   * 
     * @param messages
     * @return MoodleMessage[]
     * @throws MoodleRestMessageException
     * @throws MoodleRestException
     */
    public static MoodleMessage[] sendInstantMessages(MoodleMessage[] messages) throws MoodleRestMessageException, MoodleRestException {
        StringBuilder data = new StringBuilder();
        if (MoodleCallRestWebService.isLegacy()) throw new MoodleRestMessageException(MoodleRestException.NO_LEGACY);
        String functionCall=MoodleServices.CORE_MESSAGE_SEND_INSTANT_MESSAGES;
        try {
            if (MoodleCallRestWebService.getAuth() == null) {
                throw new MoodleRestMessageException();
            } else {
                data.append(MoodleCallRestWebService.getAuth());
            }
            data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
            for (int i=0; i<messages.length; i++) {
                if (messages[i].getToUserId()==-1) throw new MoodleRestMessageException(MoodleRestMessageException.NO_RECIPIENT);
                if (messages[i].getText()==null) throw new MoodleRestMessageException(MoodleRestMessageException.NO_MESSAGE);
                data.append("&").append(URLEncoder.encode("messages["+i+"][touserid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(Long.toString(messages[i].getToUserId()), MoodleServices.ENCODING));
                data.append("&").append(URLEncoder.encode("messages["+i+"][text]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(messages[i].getText(), MoodleServices.ENCODING));
                if (messages[i].getClientMsgId()!=null) data.append("&").append(URLEncoder.encode("messages["+i+"][clientmsgid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(messages[i].getClientMsgId(), MoodleServices.ENCODING));
            }
            data.trimToSize();
            NodeList elements=MoodleCallRestWebService.call(data.toString());
            for (int j=0, i=-1;j<elements.getLength();j++) {
                String parent=elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode().getNodeName();
                if (parent.equals("KEY"))
                    parent=elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("name").getNodeValue();
                String content=elements.item(j).getTextContent();
                String nodeName=elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
                if (parent.equals("RESPONSE") && nodeName.equals("msgid")) {
                    i++;
                    messages[i].setMsgId(Long.parseLong(content));
                } else {
                    messages[i].setMoodleMesageField(nodeName, content);
                    if (nodeName.equals("errormessage") && content.length()!=0)
                        throw new MoodleRestMessageException(content);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MoodleRestMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return messages;
    }
}
