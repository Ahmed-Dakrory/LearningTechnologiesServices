/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.readmission.assembler.ReadmissionAssembler;
import main.com.zc.services.applicationService.forms.readmission.services.IAdminReadmissionService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.domain.petition.model.IReadmissionFormRep;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.Readmission.facade.IReadmissionAdminFacade;
import main.com.zc.services.presentation.forms.shared.facade.ISendEmailFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service("ReadmissionAdminFacadeImpl")
public class ReadmissionAdminFacadeImpl implements IReadmissionAdminFacade {

	@Autowired
	IAdminReadmissionService service;
	
	@Autowired
	ISharedNotifyService notifySevice;
	
	@Autowired
	IReadmissionFormRep rep;
	
	@Autowired
	ISendEmailFacade sendEmailFacade;
	
	ReadmissionAssembler assembler = new ReadmissionAssembler();
	
	@Override
	public ReadmissionDTO updateRequest(ReadmissionDTO dto) {
	
		return service.updateRequest(dto);
	}

	@Override
	public List<ReadmissionDTO> getPendingPetitionsOfstuent() {
	
		return service.getPendingPetitionsOfstuent();
	}

	@Override
	public List<ReadmissionDTO> getArchievedPetitionsOfstuent() {
	
		return service.getArchievedPetitionsOfstuent();
	}

	@Override
	public void notifyNextUser(ReadmissionDTO dto) {
		ReadmissionForm form = rep.getById(dto.getId());
		String content="We would like to inform you that you have change of major form of id "+form.getId()+" needs an action" +
				"<br/> To access the petitions please visit : https://lts.zewailcity.edu.eg/LearningTechnologiesServices/pages/public/login.xhtml";
		String title="New Readmission form with ID("+form.getId()+")";
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
