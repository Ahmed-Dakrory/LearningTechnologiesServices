/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.facade.impl;

import java.util.Date;
import java.util.List;

import main.com.zc.services.applicationService.forms.academicPetition.assembler.AcademicPetitionAssembler;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.overloadRequest.assembler.OverloadRquestAssembler;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IAdminOverloadRequestService;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IAdmissionHOverloadRequestService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IAdminOverloadRequestFacade;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IAdmissionHOverloadRequestFacade;
import main.com.zc.services.presentation.forms.shared.facade.ISendEmailFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service("AdminOverloadRequestFacadeImpl")
public class AdminOverloadRequestFacadeImpl implements IAdminOverloadRequestFacade{

	@Autowired
	IAdminOverloadRequestService service;
	
	@Autowired
	ISharedNotifyService notifySevice;
	
	@Autowired
	IOverloadRequestRep overloadRequestRep;
	@Autowired
	ISendEmailFacade sendEmailFacade;
	OverloadRquestAssembler assembler = new OverloadRquestAssembler();
	
	@Override
	public List<OverloadRequestDTO> getPendingForms() {
		
		return service.getPendingForms();
	}

	@Override
	public List<OverloadRequestDTO> getArchievedForms() {
		
		return service.getArchievedForms();
	}

	@Override
	public OverloadRequestDTO updateStatus(OverloadRequestDTO dto) {
		
		return service.updateStatus(dto);
	}

	@Override
	public void notifyNextUser(OverloadRequestDTO dto) {
		OverloadRequest form = overloadRequestRep.getById(dto.getId());
		String content="We would like to inform you that you have Overload request petition of id "+form.getId()+" needs an action" +
				"<br/> To access the petitions please visit : https://lts.zewailcity.edu.eg/LearningTechnologiesServices/pages/public/login.xhtml";

	    String title="New Overload request form with ID("+form.getId()+")";
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			sendEmailFacade.sendEmail(form.getMajor().getHeadOfMajorId().getName()
					
					, form.getMajor().getHeadOfMajorId().getMail(), content,title);
		}
		
		else if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			sendEmailFacade.sendEmail(Constants.DEAN_OF_STRATEGIC_NAME
		
				,Constants.DEAN_OF_STRATEGIC, content,title);
		}
		else if(form.getStep().equals(PetitionStepsEnum.DEAN)&&form.getProvostRequired()==true)
		{
			sendEmailFacade.sendEmail(Constants.PROVOST_NAME
		
				,Constants.PROVOST, content,title);
		}
		else if(form.getStep().equals(PetitionStepsEnum.DEAN)&&form.getProvostRequired()!=true)
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
