
package main.com.zc.moodle.net.beaconhillcott.moodlerest;

import java.util.ArrayList;

/**
 * <p>Class to used to create objects to enable the storage of data returned from MoodleRestFile.getFiles</p>
 *
 * @author Bill Antonia
 * @see MoodleRestFile
 */
public class MoodleFileGetFiles {
  
  private ArrayList<MoodleFileParent> parents=null;
  private ArrayList<MoodleFileFile> files=null;
  
  /**
   * <p>Constructor which initialises the array storage for the returned results of a MoodleRestFile.getFiles.</p>
   */
  public MoodleFileGetFiles() {
    parents=new ArrayList<MoodleFileParent>();
    files=new ArrayList<MoodleFileFile>();
  }
  
  /**
   * <p>Method to return a pointer to the ArrayList containing the returned MoodleFileParent objects</p>
   *
   * @return ArrayList containing MoodleFileParent objects
   * @see MoodleFileParent
   */
  public ArrayList<MoodleFileParent> getParents() {
    return parents;
  }
  
  /**
   * <p>Method to return a pointer to the ArrayList containing the returned MoodleFileFile objects</p>
   *
   * @return ArrayList containing MoodleFileFile objects
   * @see MoodleFileFile
   */
  public ArrayList<MoodleFileFile> getFiles() {
    return files;
  }
  
  /**
   * <p>Method to add a MoodleFileParent object to the ArrayList.</p>
   * 
   * @param parent MoodleFileParent
   * @see MoodleFileParent
   */
  public void addParent(MoodleFileParent parent) {
    parents.add(parent);
  }
  
  /**
   * <p>Method to add a MoodleFileFile object to the ArrayList.</p>
   *
   * @param file MoodleFileFile
   * @see MoodleFileFile
   */
  public void addFile(MoodleFileFile file) {
    files.add(file);
  }
}
