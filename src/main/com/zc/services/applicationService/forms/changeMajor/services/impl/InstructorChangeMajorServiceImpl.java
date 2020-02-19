/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeMajor.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.changeMajor.assembler.ChangeMajorAssembler;
import main.com.zc.services.applicationService.forms.changeMajor.services.IInstructorChangeMajorService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.applicationService.shared.service.impl.SharedNotifyServiceImpl;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IChangeMajorFormRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class InstructorChangeMajorServiceImpl implements IInstructorChangeMajorService{

	@Autowired
	IChangeMajorFormRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	IPetitionsActionsRep actionRep;
    ChangeMajorAssembler assem=new ChangeMajorAssembler();
	@Override
	public List<ChangeMajorDTO> getPendingFormsOfInstructor(Integer insID) {
		List<ChangeMajorDTO> filterdDTO=new ArrayList<ChangeMajorDTO>();
		try{
			
			List<ChangeMajorForm> allForms=rep.getPendingByPA(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
					ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				}
				
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
					ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				}
			}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
	}

	@Override
	public List<ChangeMajorDTO> getArchievedFormsOfInstructor(Integer insID) {
		List<ChangeMajorDTO> filterdDTO=new ArrayList<ChangeMajorDTO>();
		try{
			//List<DropAddForm> allForms=addDropRepository.getByCourseCoordinatorID(insID);
			List<ChangeMajorForm> allForms=rep.getOldByPA(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
				ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
				
			
			}
			}
			else if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getForwardTOIns()!=null)
				{
					if(allForms.get(i).getForwardTOIns().getId().equals(insID))
					{
						if(
								allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
								allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
						
						    {
							// first add list of actions to this petition 
							List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
							ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
							dto.setActionDTO(actionsDTO);
							
						     filterdDTO.add(dto);
							}
						
					}
					else  
					{// first add list of actions to this petition 
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
						ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
						dto.setActionDTO(actionsDTO);
						
					     filterdDTO.add(dto);}
					
				
				}
				else
				{
				if(
						allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
				{	// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
					ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				     }
				}
				
				
				
				
				
			}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
	}

	@Override
	public ChangeMajorDTO updateStatusOfForm(ChangeMajorDTO dto) {
		try{
			ChangeMajorForm form=assem.toEntity(dto);
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JChangeMajorForm",form.getId());
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
	public List<ChangeMajorDTO> getPendingFormsOfDean() {
		
		List<ChangeMajorDTO> filterdDTO=new ArrayList<ChangeMajorDTO>();
		try{
			List<ChangeMajorForm> allForms=rep.getAll();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
					ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				}
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
					ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				}
			}
		}
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
	}

	@Override
	public List<ChangeMajorDTO> getArchievedFormsOfDean() {
		
		List<ChangeMajorDTO> filterdDTO=new ArrayList<ChangeMajorDTO>();
		try
		{
			List<ChangeMajorForm> allForms=rep.getAll();	
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
				ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
			}
			else
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
				{// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
					ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);}
			}
			}
			else if(allForms.get(i).getPerformed()==null) 
			{if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
			{// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
				ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);}}
		}}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
	}

	@Override
	public void addComment(Integer id, String comment) {
		try{
		ChangeMajorForm form = rep.getById(id);		
		form.setComment(comment); rep.update(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public ChangeMajorDTO getByID(Integer id) {
		ChangeMajorDTO dto=new ChangeMajorDTO();
		try
		{
			ChangeMajorForm form=rep.getById(id);
			dto=assem.toDTO(form);
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		try{
		List<Employee> ins=insRep.getAll();
		for(int i=0;i<ins.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			dto.setId(ins.get(i).getId());
			dto.setName(ins.get(i).getName());
			dto.setMail(ins.get(i).getMail());
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			
		}
		return dtos;
	}

	@Override
	public ChangeMajorDTO forwardPetition(ChangeMajorDTO dto) {
		try{
			ChangeMajorForm form=assem.toEntity(dto);
			form=rep.update(form);
				
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
