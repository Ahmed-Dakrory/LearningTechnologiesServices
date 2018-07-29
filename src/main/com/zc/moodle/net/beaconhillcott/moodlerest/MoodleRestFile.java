
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.w3c.dom.NodeList;

/**
 * <p>Class to call the Moodle REST file web services.</p>
 * 
 * @author Bill Antonia
 */
public class MoodleRestFile {
  
    /**
     * <p>Method to return the details of files attached to a context.</p>
     * 
     * @param MoodleFileParent params
     * @return MoodleFileGetFiles
     * @throws MoodleRestFileException
     * @throws UnsupportedEncodingException
     * @throws MoodleRestException
     */
    public static MoodleFileGetFiles getFiles(MoodleFileParent params) throws MoodleRestFileException, UnsupportedEncodingException, MoodleRestException {
    StringBuilder data=new StringBuilder();
    String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_FILES_GET_FILES:MoodleServices.CORE_FILES_GET_FILES;
    if (MoodleCallRestWebService.getAuth()==null)
      throw new MoodleRestFileException(MoodleRestException.AUTH_NULL);
    else
      data.append(MoodleCallRestWebService.getAuth());
    data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
    if (params.contextid!=-1) data.append("&").append(URLEncoder.encode("contextid", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+params.contextid, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": contextid");
    if (params.component!=null) data.append("&").append(URLEncoder.encode("component", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(params.component, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": component");
    if (params.filearea!=null) data.append("&").append(URLEncoder.encode("filearea", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(params.filearea, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": filearea");
    if (params.itemid!=-1) data.append("&").append(URLEncoder.encode("itemid", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+params.itemid, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": itemid");
    if (params.filepath!=null) data.append("&").append(URLEncoder.encode("filepath", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(params.filepath, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": filepath");
    if (params.filename!=null) data.append("&").append(URLEncoder.encode("filename", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(params.filename, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": filename");
    NodeList elements=MoodleCallRestWebService.call(data.toString());
    MoodleFileGetFiles result=new MoodleFileGetFiles();
    MoodleFileParent fileParent=null;
    MoodleFileFile fileFile=null;
    for (int j=0;j<elements.getLength();j++) {
      String parent=elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode().getNodeName();
      if (parent.equals("KEY"))
        parent=elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("name").getNodeValue();
      String content=elements.item(j).getTextContent();
      String nodeName=elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
      if (parent.equals("parents") && nodeName.equals("contextid")) {
        if (fileParent!=null) {
          result.addParent(fileParent);
        }
        fileParent=new MoodleFileParent();
        fileParent.setMoodleFileParentField(nodeName, content);
      } else {
        if (parent.equals("parents")) {
          fileParent.setMoodleFileParentField(nodeName, content);
        } else {
          if (parent.equals("files") && nodeName.equals("contextid")) {
            if (fileFile!=null) {
              result.addFile(fileFile);
            }
            fileFile=new MoodleFileFile();
            fileFile.setMoodleFileFileField(nodeName, content);
          } else {
            fileFile.setMoodleFileFileField(nodeName, content);
          }
        }
      }
    }
    if (fileParent!=null)
      result.addParent(fileParent);
    if (fileFile!=null)
      result.addFile(fileFile);
    return result;
  }
  
  /**
   * <p>Method to upload a file and attach to a context.</p>
   * @param MoodleFileContent params
   * @return MoodleFileFile
   * @throws MoodleRestFileException
   * @throws UnsupportedEncodingException
   * @throws MoodleRestException
   */
  public static MoodleFileFile upload(MoodleFileContent params) throws MoodleRestFileException, UnsupportedEncodingException, MoodleRestException {
    StringBuilder data=new StringBuilder();
    String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_FILES_UPLOAD:MoodleServices.CORE_FILES_UPLOAD;
    if (MoodleCallRestWebService.getAuth()==null)
      throw new MoodleRestFileException(MoodleRestException.AUTH_NULL);
    else
      data.append(MoodleCallRestWebService.getAuth());
    data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
    if (params.contextid!=-1) data.append("&").append(URLEncoder.encode("contextid", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+params.contextid, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": contextid");
    if (params.component!=null) data.append("&").append(URLEncoder.encode("component", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(params.component, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": component");
    if (params.filearea!=null) data.append("&").append(URLEncoder.encode("filearea", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(params.filearea, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": filearea");
    if (params.itemid!=-1) data.append("&").append(URLEncoder.encode("itemid", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+params.itemid, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": itemid");
    if (params.filepath!=null) data.append("&").append(URLEncoder.encode("filepath", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(params.filepath, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": filepath");
    if (params.filename!=null) data.append("&").append(URLEncoder.encode("filename", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(params.filename, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": filename");
    if (params.filecontent!=null) data.append("&").append(URLEncoder.encode("filecontent", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(params.filecontent, MoodleServices.ENCODING)); else throw new MoodleRestFileException(MoodleRestException.REQUIRED_PARAMETER+": filecontent");
    NodeList elements=MoodleCallRestWebService.call(data.toString());
    MoodleFileFile fileFile=null;
    for (int j=0;j<elements.getLength();j++) {
      String parent=elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode().getNodeName();
      if (parent.equals("KEY"))
        parent=elements.item(j).getParentNode().getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem("name").getNodeValue();
      String content=elements.item(j).getTextContent();
      String nodeName=elements.item(j).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
      if (parent.equals("RESPONSE") && nodeName.equals("contextid")) {
        fileFile=new MoodleFileFile();
        fileFile.setMoodleFileFileField(nodeName, content);
      } else {
        fileFile.setMoodleFileFileField(nodeName, content);
      }
    }
    return fileFile;
  }
  
}
