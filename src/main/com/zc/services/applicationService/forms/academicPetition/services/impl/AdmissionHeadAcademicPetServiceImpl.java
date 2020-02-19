/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.com.zc.services.applicationService.forms.academicPetition.assembler.AcademicPetitionAssembler;
import main.com.zc.services.applicationService.forms.academicPetition.services.IAdmissionHeadAcademicPetService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class AdmissionHeadAcademicPetServiceImpl implements IAdmissionHeadAcademicPetService {

	@Autowired
	ICoursePetitionRep rep;
	
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	AcademicPetitionAssembler assem=new AcademicPetitionAssembler();
	@Autowired
	IPetitionsActionsRep actionRep;
	@Override
	public List<CoursePetitionDTO> getPendingPet() {
		List<CoursePetitionDTO> filterdDTO=new ArrayList<CoursePetitionDTO>();
		try{
		List<CoursePetition> allForms=rep.getAll();
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
		
				{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.ACADEMICPETITION.getValue());
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
				CoursePetitionDTO dto=assem.toDTO(allForms.get(i));
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
	public List<CoursePetitionDTO> getOldPet() {
		List<CoursePetitionDTO> filterdDTO=new ArrayList<CoursePetitionDTO>();
		try{
			
			List<CoursePetition> allForms=rep.getAll();
			
			for(int i=0;i<allForms.size();i++)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
					{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.ACADEMICPETITION.getValue());
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
					CoursePetitionDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
					}
			}
			return filterdDTO;
			}
			catch(Exception ex)
			{
				System.out.println("-------Error in getting old petition");
				ex.printStackTrace();
				return filterdDTO;
			}
	}

	@Override
	public CoursePetitionDTO update(CoursePetitionDTO dto) {
		try{
			CoursePetition form=assem.toEntity(dto);
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JCoursePetition",form.getId());
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
	
	@Override
	public void addComment(CoursePetitionDTO dto,Integer instructorID) {
		
		try{
			if(dto.getId()!=null)
			{
				PetitionsActions action= actionRep.getById(dto.getId());		
				action.setComment(dto.getComment());
				actionRep.update(action);
			}
			else 
			{
				PetitionsActions action=new PetitionsActions();
				action.setComment(dto.getComment());
				Employee instructor=new  Employee();
				instructor.setId(instructorID);
				action.setInstructor(instructor);
				action.setDate(Calendar.getInstance());
				action.setFormType(FormTypesEnum.INCOMPLETEGRADE);
				action.setPetitionID(dto.getId());
				actionRep.add(action);
			}
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
	}

}
