/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.academicPetition.assembler.AcademicPetitionAssembler;
import main.com.zc.services.applicationService.forms.addAndDrop.assembler.AddDropAssembler;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IAdminAddDropFormService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IAdmissionHAddDropFormService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IAddDropFormRepository;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IAdminAddDropFormFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IAdmissionHAddDropFormFacade;
import main.com.zc.services.presentation.forms.shared.facade.ISendEmailFacade;

/**
 * @author momen
 *
 */
@Service("AdminAddDropFormFacadeImpl")
public class AdminAddDropFormFacadeImpl implements IAdminAddDropFormFacade{

	@Autowired
	IAdminAddDropFormService appService;
	
	@Autowired
	ISharedNotifyService notifySevice;
	
	@Autowired
	IAddDropFormRepository dropAddRep;
	
	@Autowired
	ISendEmailFacade sendEmailFacade;
	AddDropAssembler assembler = new AddDropAssembler();
	
	@Override
	public List<DropAddFormDTO> getPendingFormsOfAdmissionHead() {
		
		return appService.getPendingFormsOfAdmissionHead();
	}

	@Override
	public List<DropAddFormDTO> getArchievedFormsOfAdmissionHead() {
		return appService.getArchievedFormsOfAdmissionHead();
	}

	@Override
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto) {
		return appService.updateStatusOfForm(dto);
	}

	@Override
	public void notifyNextUser(DropAddFormDTO dto) {
		DropAddForm form = dropAddRep.getById(dto.getId());
		String content="We would like to inform you that you have add/drop petition of id "+form.getId()+" needs an action" +
				"<br/> To access the petitions please visit : https://lts.zewailcity.edu.eg/LearningTechnologiesServices/pages/public/login.xhtml";

	    String title="New Drop/Add Form with ID("+form.getId()+")";
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			//TODO in phase 1/2
			/*		sendEmailFacade.sendEmail(form.getMajor().getHeadOfMajorId().getName()
		
				, form.getMajor().getHeadOfMajorId().getMail(), content);*/
			sendEmailFacade.sendEmail(form.getDroppedCourseIns().getName()
					
					, form.getDroppedCourseIns().getMail(), content,title);
		}
		else if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			sendEmailFacade.sendEmail(Constants.DEAN_OF_STRATEGIC_NAME
		
				,Constants.DEAN_OF_STRATEGIC, content,title);
		}
		else if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			sendEmailFacade.sendEmail(Constants.REGISTRAR_HEAD_NAME
		
				,Constants.REGISTRAR_HEAD_EMAIL, content,title);
		}
		else if(form.getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
		{
			sendEmailFacade.sendEmail("registrar"
		
				,Constants.ADMISSION_DEPT, content,title);
		}
		/*form.setInsNotifyDate(new Date()); 
		notifySevice.notifayAtDate(form);*/
	}

}
