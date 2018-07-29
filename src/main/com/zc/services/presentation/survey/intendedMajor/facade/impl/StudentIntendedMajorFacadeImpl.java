/**
 * 
 */
package main.com.zc.services.presentation.survey.intendedMajor.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.intendedMajor.service.IStudentIntendedMajorService;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.survey.intendedMajor.facade.IStudentIntendedMajorFacade;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
@Service("IStudentIntendedMajorFacade")
public class StudentIntendedMajorFacadeImpl implements IStudentIntendedMajorFacade{

	@Autowired
	IStudentIntendedMajorService service;
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
	public List<MajorDTO> getMajors() {
		
		return service.getMajors();
	}

}
