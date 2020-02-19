/**
 * 
 */
package main.com.zc.services.applicationService.forms.addAndDrop.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.assembler.AddDropAssembler;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IAdmissionHAddDropFormService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.applicationService.shared.service.impl.SharedNotifyServiceImpl;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IAddDropFormRepository;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya.alaa
 *
 */
@Service("AdmissionHAddDropFormServiceImpl")
public class AdmissionHAddDropFormServiceImpl implements IAdmissionHAddDropFormService {

	@Autowired
	IAddDropFormRepository addDropRepository;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IPetitionsActionsRep actionRep;
	AddDropAssembler assem=new AddDropAssembler();
	@Override
	public List<DropAddFormDTO> getPendingFormsOfAdmissionHead() {
	
		List<DropAddFormDTO> filterdDTO=new ArrayList<DropAddFormDTO>();
		try{
		List<DropAddForm> allForms=addDropRepository.getAll();
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.DROPADD.getValue());
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
				DropAddFormDTO dto=assem.toDTO(allForms.get(i));
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
	public List<DropAddFormDTO> getArchievedFormsOfAdmissionHead() {
		List<DropAddFormDTO> filterdDTO=new ArrayList<DropAddFormDTO>();
		try{
			
			List<DropAddForm> allForms=addDropRepository.getAll();
			
			for(int i=0;i<allForms.size();i++)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
					{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.DROPADD.getValue());
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
					DropAddFormDTO dto=assem.toDTO(allForms.get(i));
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
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto) {
		try{
			DropAddForm form=assem.toEntity(dto);
			form=addDropRepository.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JDropAddForm",form.getId());
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
	public void addComment(Integer id, String comment) {
		
		DropAddForm dropAdd = addDropRepository.getById(id);		
		dropAdd.setComment(comment); addDropRepository.update(dropAdd);
	}

}
