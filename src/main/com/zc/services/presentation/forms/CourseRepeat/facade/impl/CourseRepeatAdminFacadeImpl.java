/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.CourseRepeat.assembler.CourseRepeatAssembler;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatAdminService;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatAdmissionHeadService;
import main.com.zc.services.applicationService.forms.academicPetition.assembler.AcademicPetitionAssembler;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.IRepeatCourseFormRep;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatAdminFacade;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatAdmissionHeadFacade;
import main.com.zc.services.presentation.forms.shared.facade.ISendEmailFacade;

/**
 * @author momen
 *
 */
@Service("ICourseRepeatAdminFacade")
public class CourseRepeatAdminFacadeImpl implements ICourseRepeatAdminFacade {

	@Autowired
	ICourseRepeatAdminService service;
	

	@Autowired
	ISharedNotifyService notifySevice;
	
	CourseRepeatAssembler assembler = new CourseRepeatAssembler();
	
	@Autowired
	IRepeatCourseFormRep rep;
	@Autowired
	ISendEmailFacade sendEmailFacade;
	@Override
	public List<CourseRepeatDTO> getPendingFormsOfAdmissionHead() {
		
		return service.getPendingFormsOfAdmissionHead();
	}

	@Override
	public List<CourseRepeatDTO> getArchievedFormsOfAdmissionHead() {
		
		return service.getArchievedFormsOfAdmissionHead();
	}

	@Override
	public CourseRepeatDTO updateStatusOfForm(CourseRepeatDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public CourseRepeatDTO getById(Integer id) {
		
		return service.getById(id);
	}

	@Override
	public void addComment(Integer id, String comment) {
		service.addComment(id, comment);
		
	}

	@Override
	public void notifyNextUser(CourseRepeatDTO dto) {
		RepeatCourseForm form = rep.getById(dto.getId());
		String content="We would like to inform you that you have repeat course petition of id "+form.getId()+" needs an action" +
				"<br/> To access the petitions please visit : http://lts.zewailcity.edu.eg/LearningTechnologiesServices/pages/public/login.xhtml";
	
		String title="New Repeat Course Form with ID("+form.getId()+")";
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			sendEmailFacade.sendEmail(form.getMajor().getHeadOfMajorId().getName()
		
				, form.getMajor().getHeadOfMajorId().getMail(), content,title);
		}
		else if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			sendEmailFacade.sendEmail(Constants.DEAN_OF_ACADEMIC_NAME
		
				,Constants.DEAN_OF_ACADEMIC, content,title);
		}
		else if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			sendEmailFacade.sendEmail(Constants.ADMISSION_HEAD_NAME
		
				,Constants.ADMISSION_HEAD, content,title);
		}
		else if(form.getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
		{
			sendEmailFacade.sendEmail("registrar"
		
				,Constants.ADMISSION_DEPT, content,title);
		}
		/*form.setInsNotifyDate(new Date()); //set notification time to now
		notifySevice.notifayAtDate(form);*/
	}

}
