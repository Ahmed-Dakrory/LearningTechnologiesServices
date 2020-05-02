/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.course_replacement_form.assembler.course_replacement_formAssembler;
import main.com.zc.services.applicationService.forms.course_replacement_form.services.IAdmincourse_replacement_formService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.Icourse_replacement_formFormRep;
import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formAdminFacade;
import main.com.zc.services.presentation.forms.shared.facade.ISendEmailFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service("course_replacement_formAdminFacadeImpl")
public class course_replacement_formAdminFacadeImpl implements Icourse_replacement_formAdminFacade {

	@Autowired
	IAdmincourse_replacement_formService service;
	
	@Autowired
	ISharedNotifyService notifySevice;
	
	@Autowired
	Icourse_replacement_formFormRep rep;
	
	@Autowired
	ISendEmailFacade sendEmailFacade;
	
	course_replacement_formAssembler assembler = new course_replacement_formAssembler();
	
	@Override
	public course_replacement_formDTO updateRequest(course_replacement_formDTO dto) {
	
		return service.updateRequest(dto);
	}

	@Override
	public List<course_replacement_formDTO> getPendingPetitionsOfstuent() {
	
		return service.getPendingPetitionsOfstuent();
	}

	@Override
	public List<course_replacement_formDTO> getArchievedPetitionsOfstuent() {
	
		return service.getArchievedPetitionsOfstuent();
	}

	@Override
	public void notifyNextUser(course_replacement_formDTO dto) {
		course_replacement_formForm form = rep.getById(dto.getId());
		String content="We would like to inform you that you have change of major form of id "+form.getId()+" needs an action" +
				"<br/> To access the petitions please visit : https://lts.zewailcity.edu.eg/LearningTechnologiesServices/pages/public/login.xhtml";
		String title="New Course Replacement form with ID("+form.getId()+")";
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			sendEmailFacade.sendEmail(Constants.DEAN_OF_ACADEMIC_NAME
					
					,Constants.DEAN_OF_ACADEMIC, content,title);
		}
		else if(form.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
		{
		sendEmailFacade.sendEmail(Constants.DEAN_OF_STRATEGIC_NAME
		
				,Constants.DEAN_OF_STRATEGIC, content,title);
		}
		
		/*form.setInsNotifyDate(new Date()); //set notification time to now
		notifySevice.notifayAtDate(form);*/
	}

}
