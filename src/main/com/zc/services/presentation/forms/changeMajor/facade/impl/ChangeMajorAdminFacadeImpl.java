/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade.impl;

import java.util.Date;
import java.util.List;

import main.com.zc.services.applicationService.forms.academicPetition.assembler.AcademicPetitionAssembler;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.changeMajor.assembler.ChangeMajorAssembler;
import main.com.zc.services.applicationService.forms.changeMajor.services.IAdminChangeMajorService;
import main.com.zc.services.applicationService.forms.changeMajor.services.IAdmissionDeptChangeMajorService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.IChangeMajorFormRep;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeMajor.facade.IChangeMajorAdminFacade;
import main.com.zc.services.presentation.forms.shared.facade.ISendEmailFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.corba.se.impl.orbutil.closure.Constant;

/**
 * @author momen
 *
 */
@Service("ChangeMajorAdminFacadeImpl")
public class ChangeMajorAdminFacadeImpl implements IChangeMajorAdminFacade {

	@Autowired
	IAdminChangeMajorService service;
	
	@Autowired
	ISharedNotifyService notifySevice;
	
	@Autowired
	IChangeMajorFormRep rep;
	
	@Autowired
	ISendEmailFacade sendEmailFacade;
	
	ChangeMajorAssembler assembler = new ChangeMajorAssembler();
	
	@Override
	public ChangeMajorDTO updateRequest(ChangeMajorDTO dto) {
	
		return service.updateRequest(dto);
	}

	@Override
	public List<ChangeMajorDTO> getPendingPetitionsOfstuent() {
	
		return service.getPendingPetitionsOfstuent();
	}

	@Override
	public List<ChangeMajorDTO> getArchievedPetitionsOfstuent() {
	
		return service.getArchievedPetitionsOfstuent();
	}

	@Override
	public void notifyNextUser(ChangeMajorDTO dto) {
		ChangeMajorForm form = rep.getById(dto.getId());
		String content="We would like to inform you that you have change of major form of id "+form.getId()+" needs an action" +
				"<br/> To access the petitions please visit : http://lts.zclt.info/LearningTechnologiesServices/pages/public/login.xhtml";
		String title="New Change of major form with ID("+form.getId()+")";
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			sendEmailFacade.sendEmail(form.getNewMajor().getHeadOfMajorId().getName()
		
				, form.getNewMajor().getHeadOfMajorId().getMail(), content,title);
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
		else if(form.getStep().equals(PetitionStepsEnum.ADMISSION_HEAD))
		{
			sendEmailFacade.sendEmail("registrar"
		
				,Constants.ADMISSION_DEPT, content,title);
		}
		/*form.setInsNotifyDate(new Date()); //set notification time to now
		notifySevice.notifayAtDate(form);*/
	}

}
