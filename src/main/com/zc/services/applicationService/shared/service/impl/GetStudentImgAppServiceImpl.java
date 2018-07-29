/**
 * 
 */
package main.com.zc.services.applicationService.shared.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.persons.service.IStudentProfileService;
import main.com.zc.services.applicationService.shared.service.IGetStudentImgAppService;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;

/**
 * @author omnya
 *
 */
@Service
public class GetStudentImgAppServiceImpl implements IGetStudentImgAppService{
	@Autowired
	IStudentProfileService studentProfileService;
	@Override
	public byte[] getStudentImg(Integer studentID) {
		try{
			StudentProfileDTO profile=studentProfileService.getCurrentPRofileByStudentID(studentID);
			return profile.getStudentImage();
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

}
