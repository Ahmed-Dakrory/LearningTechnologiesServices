/**
 * 
 */
package main.com.zc.services.presentation.survey.DeclarationOfMajor.facade.impl;

import java.util.List;
import main.com.zc.services.applicationService.survey.intendedMajor.service.IStudentDeclarationOfMajorService;
import main.com.zc.services.presentation.survey.DeclarationOfMajor.facade.IStudentDeclarationOfMajorFacade;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("IStudentDeclarationOfMajorFacade")
public class StudentDeclarationOfMajorFacadeImpl implements IStudentDeclarationOfMajorFacade{

	@Autowired
	IStudentDeclarationOfMajorService service; 
	@Override
	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto) {
		
		return service.submit(dto);
	}

	@Override
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto) {
		
		return service.update(dto);
	}

	@Override
	public IntendedMajorSurveyDTO getByStudentID(Integer studentID) {
		
		return service.getByStudentID(studentID);
	}


	@Override
	public IntendedMajorSurveyDTO getByStudentIDAndYearAndSemester(
			Integer studentID) {
		
		return service.getByStudentIDAndYearAndSemester(studentID);
	}

}
