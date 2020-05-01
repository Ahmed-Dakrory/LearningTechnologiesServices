/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.course_replacement_form.assembler.course_replacement_formAssembler;
import main.com.zc.services.applicationService.forms.course_replacement_form.services.IAdmissionHeadcourse_replacement_formService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.domain.petition.model.Icourse_replacement_formFormRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class AdmissionHeadcourse_replacement_formServiceImpl implements IAdmissionHeadcourse_replacement_formService{

	@Autowired
	Icourse_replacement_formFormRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IPetitionsActionsRep actionRep;
	course_replacement_formAssembler assem=new course_replacement_formAssembler();
	
	@Override
	public List<course_replacement_formDTO> getPendingFormsOfAdmissionHead() {
		List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
		try{
		List<course_replacement_formForm> allForms=rep.getAll();
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
				{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
				List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
				if(actions!=null)
				{
					for(int a=0;a<actions.size();a++)
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setId(actions.get(a).getId());
						actionDTO.setComment(actions.get(a).getComment());
						actionDTO.setDate(actions.get(a).getDate());
						actionDTO.setFormType(actions.get(a).getFormType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
						actionDTO.setPetitionID(actions.get(a).getPetitionID());
						actionDTO.setActionType(actions.get(a).getActionType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
						actionsDTO.add(actionDTO);
					}
				}
				course_replacement_formDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
				}
		}
		return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting pending petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	}

	@Override
	public List<course_replacement_formDTO> getArchievedFormsOfAdmissionHead() {
		List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
		try{
			
			List<course_replacement_formForm> allForms=rep.getAll();
			
			for(int i=0;i<allForms.size();i++)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
					{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
					List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
					if(actions!=null)
					{
						for(int a=0;a<actions.size();a++)
						{
							PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
							actionDTO.setId(actions.get(a).getId());
							actionDTO.setComment(actions.get(a).getComment());
							actionDTO.setDate(actions.get(a).getDate());
							actionDTO.setFormType(actions.get(a).getFormType());
							if(actions.get(a).getInstructor()!=null)
							actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
							actionDTO.setPetitionID(actions.get(a).getPetitionID());
							actionDTO.setActionType(actions.get(a).getActionType());
							if(actions.get(a).getInstructor()!=null)
							actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
							actionsDTO.add(actionDTO);
						}
					}
					course_replacement_formDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
					}
			}
		
			}
			catch(Exception ex)
			{
				System.out.println("-------Error in getting old petition");
				ex.printStackTrace();
				
			}
		return filterdDTO;
	}

	
	
	@Override
	public void addComment(Integer id, String comment) {
		try{
		course_replacement_formForm form = rep.getById(id);		
		form.setComment(comment); rep.update(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public course_replacement_formDTO updateStatusOfForm(course_replacement_formDTO dto) {
		try{
			course_replacement_formForm form=assem.toEntity(dto);
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("Jcourse_replacement_formForm",form.getId());
			}	
			return assem.toDTO(form);
			}
			catch(Exception ex)
			{
				System.out.println("<<<<<<<<<< Form can't be updated >>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

}
