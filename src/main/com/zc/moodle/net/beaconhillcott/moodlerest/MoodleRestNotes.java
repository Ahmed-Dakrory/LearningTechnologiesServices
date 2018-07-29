
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import org.w3c.dom.NodeList;

/**
 * <p>Class containing static Methods to call Moodle REST notes web services.</p>
 * 
 * @author Bill Antonia
 */
public class MoodleRestNotes {
  
  //private static final int BUFFER_MAX=4000;
  
  /**
   * <p>Method to attach a single note to a user.</p>
   * 
   * @param note
   * @return MoodleNote
   * @throws MoodleRestNotesException
   * @throws MoodleRestException
   */
  public static MoodleNote createNote(MoodleNote note) throws MoodleRestNotesException, MoodleRestException {
    MoodleNote[] a=new MoodleNote[1];
    a[0]=note;
    MoodleNote[] n=createNotes(a);
    return n[0];
  }
  
  //core_notes_create_notes

  /**
   * <p>Method to attach notes to users.</p>
   * 
   * @param MoodleNote[] notes
   * @return MoodleNote[]
   * @throws MoodleRestNotesException
   * @throws MoodleRestException
   */
  public static MoodleNote[] createNotes(MoodleNote[] notes) throws MoodleRestNotesException, MoodleRestException {
    int processedCount=0;
    String functionCall=MoodleCallRestWebService.isLegacy()?MoodleServices.MOODLE_NOTES_CREATE_NOTES:MoodleServices.CORE_NOTES_CREATE_NOTES;
    try {
      StringBuilder data=new StringBuilder();
      if (MoodleCallRestWebService.getAuth()==null)
        throw new MoodleRestNotesException();
      else
        data.append(MoodleCallRestWebService.getAuth());
      data.append("&").append(URLEncoder.encode("wsfunction", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(functionCall, MoodleServices.ENCODING));
      for (int i=0;i<notes.length;i++) {
        if (notes[i]==null) throw new MoodleRestNotesException(MoodleRestNotesException.NOTES_NULL);
        if (notes[i].getUserId()==-1) throw new MoodleRestNotesException(MoodleRestNotesException.USERID_NOT_SET); else data.append("&").append(URLEncoder.encode("notes["+i+"][userid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+notes[i].getUserId(), MoodleServices.ENCODING));
        if (notes[i].getPublishState()==null) throw new MoodleRestNotesException(MoodleRestNotesException.PUBLISHSTATE_NULL); else data.append("&").append(URLEncoder.encode("notes["+i+"][publishstate]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(notes[i].getPublishState(), MoodleServices.ENCODING));
        if (notes[i].getCourseId()==-1) throw new MoodleRestNotesException(MoodleRestNotesException.COURSEID_NOT_SET); else data.append("&").append(URLEncoder.encode("notes["+i+"][courseid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(""+notes[i].getCourseId(), MoodleServices.ENCODING));
        if (notes[i].getText()==null) throw new MoodleRestNotesException(MoodleRestNotesException.TEXT_NULL); else data.append("&").append(URLEncoder.encode("notes["+i+"][text]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(notes[i].getText(), MoodleServices.ENCODING));
        if (notes[i].getFormat()==null) { notes[i].setFormat("text"); }
        if (notes[i].getFormat().equals("text") || notes[i].getFormat().equals("html"))
          data.append("&").append(URLEncoder.encode("notes["+i+"][format]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(notes[i].getFormat(), MoodleServices.ENCODING));
        else
          throw new MoodleRestNotesException(MoodleRestNotesException.FORMAT_INCORRECT);
        if (notes[i].getClientNoteId()!=null) data.append("&").append(URLEncoder.encode("notes["+i+"][clientnoteid]", MoodleServices.ENCODING)).append("=").append(URLEncoder.encode(notes[i].getClientNoteId(), MoodleServices.ENCODING));
      }
      data.trimToSize();
      NodeList elements=MoodleCallRestWebService.call(data.toString());
      for (int j=0;j<elements.getLength();j+=3,processedCount++) {
        for (int k=0; k<3; k++) {
          String content=elements.item(j+k).getTextContent();
          String nodeName=elements.item(j+k).getParentNode().getAttributes().getNamedItem("name").getNodeValue();
          notes[processedCount].setMoodleNoteField(nodeName, content);
        }
      }
    }  catch (IOException ex) {
      Logger.getLogger(MoodleRestNotes.class.getName()).log(Level.SEVERE, null, ex);
    }
    return notes;
  }
}
