/**
 * 
 */
package main.com.zc.services.applicationService.moodleService.impl;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import main.com.zc.moodle.net.beaconhillcott.moodlerest.MoodleCallRestWebService;
import main.com.zc.moodle.net.beaconhillcott.moodlerest.MoodleRestException;
import main.com.zc.moodle.net.beaconhillcott.moodlerest.MoodleRestUser;
import main.com.zc.moodle.net.beaconhillcott.moodlerest.MoodleRestUserException;
import main.com.zc.moodle.net.beaconhillcott.moodlerest.MoodleUser;
import main.com.zc.services.applicationService.moodleService.IMoodleIntegrationAppService;
import main.com.zc.services.applicationService.shared.service.impl.moo;

@Service
public class MoodleIntegrationAppServiceImpl implements IMoodleIntegrationAppService{
	
	@Override
	public void updateMoodleUserPassword(String username, String newPass) {
		 try {
		 System.setProperty("jsse.enableSNIExtension", "false");
		 
    	MoodleCallRestWebService.init("https://moodle.zclt.info/moodle/webservice/rest/server.php","e564040928c7e33843f28c18c8f52d6a");
    	MoodleUser user=MoodleRestUser.getUserByUserName(username);
    	user.setPassword(newPass);
    	MoodleRestUser.updateUser(user);
		 } catch (MoodleRestUserException ex) {
			 ex.printStackTrace();
		      Logger.getLogger(moo.class.getName()).log(Level.SEVERE, null, ex);
		    } catch (MoodleRestException ex) {
		    	ex.printStackTrace();
		      Logger.getLogger(moo.class.getName()).log(Level.SEVERE, null, ex);
		    } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	
	
