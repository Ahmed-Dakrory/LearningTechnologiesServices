/**
 * 
 */
package main.com.zc.services.applicationService.forms.formsHistory.services;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.formsHistory.assembler.FormDTOAssembler;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IAddDropFormRepository;
import main.com.zc.services.domain.petition.model.IChangeMajorFormRep;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.IRepeatCourseFormRep;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormsHistroyServiceImpl implements IFormsHistroyService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	ICoursePetitionRep coursePetitionRep;
	@Autowired
	IRepeatCourseFormRep repeatCourseFormRep;
	@Autowired
	IOverloadRequestRep overloadRequestRep;
	@Autowired
	IChangeMajorFormRep changeMajorFormRep;
	@Autowired
	IAddDropFormRepository addDropFormRepository;
	FormDTOAssembler formDTOAssembler=new FormDTOAssembler();
	
	@Override
	public List<FormDTO> getFormsHistory(String studentInfo) {
		List<FormDTO> formDTOs=new ArrayList<FormDTO>();
		
		Integer studentId = null;
		String studentName = null;
		if (studentInfo != null) {
			try {
				studentId = Integer.parseInt(studentInfo);
			} catch (Exception e) {
				e.printStackTrace();
				studentName = studentInfo;
			}
		}
		
		List<CoursePetition> coursePetitions = coursePetitionRep
				.getCoursePetitionHistory(studentId, studentName);

			for(CoursePetition coursePetition:coursePetitions)
		{
			formDTOs.add(formDTOAssembler.toDTO(coursePetition));
		}
		
		List<DropAddForm> dropAddForms = addDropFormRepository
				.getDropAddFormHistory(studentId, studentName);

		for(DropAddForm dropAddForm:dropAddForms)
		{
			formDTOs.add(formDTOAssembler.toDTO(dropAddForm));
		}
		
		List<RepeatCourseForm> repeatCourseForms = repeatCourseFormRep
				.getRepeatCourseFormHistory(studentId, studentName);

		for(RepeatCourseForm repeatCourseForm:repeatCourseForms)
		{
			formDTOs.add(formDTOAssembler.toDTO(repeatCourseForm));
		}
		
		List<ChangeMajorForm> changeMajorForms = changeMajorFormRep
				.getChangeMajorFormHistory(studentId, studentName);

		for(ChangeMajorForm changeMajorForm:changeMajorForms)
		{
			formDTOs.add(formDTOAssembler.toDTO(changeMajorForm));
		}
		
		List<OverloadRequest> overloadRequests = overloadRequestRep
				.getOverloadRequestHistory(studentId, studentName);

		for(OverloadRequest overloadRequest:overloadRequests)
		{
			formDTOs.add(formDTOAssembler.toDTO(overloadRequest));
		}
		
		return formDTOs;
	}

	@Override
	public FormDTO getFormDetail(CoursePetitionDTO coursePetitionDTO) {
	return	formDTOAssembler.toDTO(coursePetitionRep
		.getById(coursePetitionDTO.getId()));
	}

	@Override
	public FormDTO getFormDetail(DropAddFormDTO addFormDTO) {
		return	formDTOAssembler.toDTO(addDropFormRepository
				.getById(addFormDTO.getId()));
}

	@Override
	public FormDTO getFormDetail(ChangeMajorDTO changeMajorDTO) {
		return	formDTOAssembler.toDTO(changeMajorFormRep
				.getById(changeMajorDTO.getId()));
}

	@Override
	public FormDTO getFormDetail(OverloadRequestDTO overloadRequestDTO) {
		return	formDTOAssembler.toDTO(overloadRequestRep
				.getById(overloadRequestDTO.getId()));
}

	@Override
	public FormDTO getFormDetail(CourseRepeatDTO courseRepeatDTO) {
		return	formDTOAssembler.toDTO(repeatCourseFormRep
				.getById(courseRepeatDTO.getId()));
}

}