/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade.impl;

import java.util.Date;
import java.util.List;

import main.com.zc.services.applicationService.forms.academicPetition.assembler.AcademicPetitionAssembler;
import main.com.zc.services.applicationService.forms.academicPetition.services.IAdmissionAdminAcademicPetService;
import main.com.zc.services.applicationService.forms.academicPetition.services.IAdmissionHeadAcademicPetService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAdmissionAdminAcademicPetFacade;
import main.com.zc.services.presentation.forms.shared.facade.ISendEmailFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service("AdmissionAdminAcademicPetFacadeImpl")
public class AdmissionAdminAcademicPetFacadeImpl implements IAdmissionAdminAcademicPetFacade {

	@Autowired
	IAdmissionAdminAcademicPetService service;
	
	@Autowired
	ISharedNotifyService notifySevice;
	
	@Autowired
	ISendEmailFacade sendEmailFacade;
	@Autowired
	ICoursePetitionRep rep;
	AcademicPetitionAssembler assembler = new AcademicPetitionAssembler();
	
	@Override
	public List<CoursePetitionDTO> getPendingPet() {
		
		return service.getPendingPet();
	}

	@Override
	public List<CoursePetitionDTO> getOldPet() {
		
		return service.getOldPet();
	}

	@Override
	public CoursePetitionDTO update(CoursePetitionDTO dto) {
		
		return service.update(dto);
	}

	@Override
	public void notifyNextUser(CoursePetitionDTO dto) {
		CoursePetition form =rep.getById(dto.getId());
		String content="We would like to inform you that you have academic petition of id "+form.getId()+" needs an action" +
				"<br/> To access the petitions please visit : http://lts.zewailcity.edu.eg/LearningTechnologiesServices/pages/public/login.xhtml";
        String title="New Academic petition With ID ("+form.getId()+")";
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			
			sendEmailFacade.sendEmail(form.getCourse().getCourseCoordinator().getName()
					
					, form.getCourse().getCourseCoordinator().getMail(), content,title);
		}
		
		else if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			sendEmailFacade.sendEmail(Constants.DEAN_OF_STRATEGIC_NAME
		
				,Constants.DEAN_OF_STRATEGIC, content,title);
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
	/*	form.setInsNotifyDate(new Date()); //set notification time to now
		notifySevice.notifayAtDate(form);*/
	}
}
