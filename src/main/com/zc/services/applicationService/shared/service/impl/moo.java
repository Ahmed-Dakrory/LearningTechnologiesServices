package main.com.zc.services.applicationService.shared.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.com.zc.moodle.net.beaconhillcott.moodlerest.*;

/**
 *
 * @author bill
 */
public class moo {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    try {
  //    MoodleCallRestWebService.init("https://moodle.zclt.info/webservice/rest/server.php", "astringwhichrepresentsatoken");
    	System.setProperty("jsse.enableSNIExtension", "false");
    	MoodleCallRestWebService.init("https://moodle.zclt.info/moodle/webservice/rest/server.php","e564040928c7e33843f28c18c8f52d6a");
      // The minimum required parameters to create a user. Modify the object "user" to add extra details.
    	
   	MoodleUser user=MoodleRestUser.getUserByUserName("s-afnan.sultan@zewailcity.edu.eg");
	//user.setPassword("AS-1234");
	//MoodleRestUser.updateUser(user);
	
     System.out.println("finish");
    } catch (MoodleRestUserException ex) {
      Logger.getLogger(moo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (MoodleRestException ex) {
      Logger.getLogger(moo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
    
  }

  private static void printCreatedUserDetails(MoodleUser createdUser) {
    System.out.println("id        ="+createdUser.getId());
    System.out.println("username  ="+createdUser.getUsername());
  }
}