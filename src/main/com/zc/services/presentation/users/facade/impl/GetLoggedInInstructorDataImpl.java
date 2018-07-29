/**
 * 
 */
package main.com.zc.services.presentation.users.facade.impl;

import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.appService.IGetInstructorDataAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("GetLoggedInInstructorDataImpl")
public class GetLoggedInInstructorDataImpl implements IGetLoggedInInstructorData{
@Autowired IGetInstructorDataAppService insAppService;

	@Override
	public InstructorDTO getInsByPersonMail(String mail) {
		
		return insAppService.getInsByMailNew(mail);
	}

	@Override
	public boolean isCouurseCoordinator(String mail) {
		
		return insAppService.isCouurseCoordinator(mail);
	}
	
	@Override
	public boolean isPA(String mail) {
		
		return insAppService.isPA(mail);
	}

	@Override
	public InstructorDTO getInsByPersonID(Integer id) {
		
		return insAppService.getInsByPersonID(id);
	}
	
	

}