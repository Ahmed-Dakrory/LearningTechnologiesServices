/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeOfConcentration.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.changeOfConcentration.assembler.ChangeOfConcentrationAssem;
import main.com.zc.services.applicationService.forms.changeOfConcentration.service.IChangeConcentrationAdHService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.ChangeConcentration;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.IChangeConcentrationRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;

/**
 * @author omnya
 *
 */
@Service
public class ChangeConcentrationAdHServiceImpl implements IChangeConcentrationAdHService{

	@Autowired
	IChangeConcentrationRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IPetitionsActionsRep actionRep;
	ChangeOfConcentrationAssem assem=new ChangeOfConcentrationAssem();
	
	
	
	@Override
	public List<ChangeConcentrationDTO> getPendingFormsOfAdmissionHead() {
		List<ChangeConcentrationDTO> filterdDTO=new ArrayList<ChangeConcentrationDTO>();
		try{
		List<ChangeConcentration> allForms=rep.getAll();
		
		for(int i=0;i<allForms.size();i++)
		{
			System.out.println("All Concentration");
			System.out.println(allForms.get(i).getStep());
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.PA))
				{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGECONCENTRATION.getValue());
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
				ChangeConcentrationDTO dto=assem.toDTO(allForms.get(i));
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
	public List<ChangeConcentrationDTO> getArchievedFormsOfAdmissionHead() {
		List<ChangeConcentrationDTO> filterdDTO=new ArrayList<ChangeConcentrationDTO>();
		try{
			
			List<ChangeConcentration> allForms=rep.getAll();
			
			for(int i=0;i<allForms.size();i++)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
					{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGECONCENTRATION.getValue());
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
					ChangeConcentrationDTO dto=assem.toDTO(allForms.get(i));
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
	public ChangeConcentrationDTO updateStatusOfForm(ChangeConcentrationDTO dto) {
		try{
			ChangeConcentration form=assem.toEntity(dto);
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JChangeConcentration",form.getId());
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
