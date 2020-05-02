/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.facade.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.incompleteGrade.assembler.IncompleteGradeAssembler;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdminService;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdmissionDeptService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IIncompleteGradeRep;
import main.com.zc.services.domain.petition.model.IncompleteGrade;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeAdminFacade;
import main.com.zc.services.presentation.forms.shared.facade.ISendEmailFacade;
import main.com.zc.shared.appService.IPersonGetDataAppService;

/**
 * @author omnya
 *
 */
@Service("IIncompleteGradeAdminFacade")
public class IncompleteGradeAdminFacadeImpl implements IIncompleteGradeAdminFacade{

	@Autowired
	IIncompleteGradeAdminService serice;
	@Autowired
	ISharedNotifyService notifySevice;
	@Autowired
	IIncompleteGradeRep rep;
	@Autowired
	ISendEmailFacade sendEmailFacade;
	IncompleteGradeAssembler assem=new IncompleteGradeAssembler();
	@Override
	public List<IncompleteGradeDTO> getPendingForms() {
		
		return serice.getPendingForms();
	}

	@Override
	public List<IncompleteGradeDTO> getArchievedForms() {
		// TODO Auto-generated method stub
		return serice.getArchievedForms();
	}

	@Override
	public IncompleteGradeDTO updateStatusOfForm(IncompleteGradeDTO dto) {
		
		return serice.updateStatusOfForm(dto);
	}

	@Override
	public void notifyNextUser(IncompleteGradeDTO dto) {
		IncompleteGrade form =rep.getById(dto.getId());
		String content="We would like to inform you that you have incomplete grade petition of id "+form.getId()+" needs an action" +
				"<br/> To access the petitions please visit : https://lts.zewailcity.edu.eg/LearningTechnologiesServices/pages/public/login.xhtml";

		String title="New Incomplete Grade Form with ID("+form.getId()+")";
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
		
			sendEmailFacade.sendEmail(form.getInstructor().getName()
					
					, form.getInstructor().getMail(), content,title);
		}
		else if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
		{
			sendEmailFacade.sendEmail(form.getMajor().getHeadOfMajorId().getName()
					
					, form.getMajor().getHeadOfMajorId().getMail(), content,title);
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
		
		/*form.setInsNotifyDate(new Date()); //set notification time to now
		notifySevice.notifayAtDate(form);*/
		
	}

}
