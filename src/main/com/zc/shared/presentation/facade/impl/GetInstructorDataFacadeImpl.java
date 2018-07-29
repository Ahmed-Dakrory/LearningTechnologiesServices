/**
 * 
 */
package main.com.zc.shared.presentation.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.presentation.survey.courseFeedback.dto.InstructorDTO;
import main.com.zc.shared.appService.IGetInstructorDataAppService;
import main.com.zc.shared.presentation.facade.IGetInstructorDataFacade;

/**
 * @author Omnya Alaa
 *
 */
@Service("getInstructorDataFacadeImpl")
public class GetInstructorDataFacadeImpl implements IGetInstructorDataFacade {

	@Autowired
	IGetInstructorDataAppService getInsAppService;
	@Override
	public InstructorDTO getInsByMail(String mail) {
		// TODO Auto-generated method stub
		return getInsAppService.getInsByMail(mail);
	}

}
