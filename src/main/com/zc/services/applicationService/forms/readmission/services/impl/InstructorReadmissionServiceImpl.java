/**
 * 
 */
package main.com.zc.services.applicationService.forms.readmission.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.readmission.assembler.ReadmissionAssembler;
import main.com.zc.services.applicationService.forms.readmission.services.IInstructorReadmissionService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.domain.petition.model.IReadmissionFormRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class InstructorReadmissionServiceImpl implements IInstructorReadmissionService{

	@Autowired
	IReadmissionFormRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	IPetitionsActionsRep actionRep;
    ReadmissionAssembler assem=new ReadmissionAssembler();
	@Override
	public List<ReadmissionDTO> getPendingFormsOfInstructor(Integer insID) {
		List<ReadmissionDTO> filterdDTO=new ArrayList<ReadmissionDTO>();
		try{
			
			List<ReadmissionForm> allForms=rep.getPendingByPA(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
					ReadmissionDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				}
				
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
					ReadmissionDTO dto=assem.toDTO(allForms.get(i));
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
	public List<ReadmissionDTO> getArchievedFormsOfInstructor(Integer insID) {
		List<ReadmissionDTO> filterdDTO=new ArrayList<ReadmissionDTO>();
		try{
			//List<DropAddForm> allForms=addDropRepository.getByCourseCoordinatorID(insID);
			List<ReadmissionForm> allForms=rep.getOldByPA(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
				ReadmissionDTO dto=assem.toDTO(allForms.get(i));
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
								allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
						
						    {
							// first add list of actions to this petition 
							List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
							ReadmissionDTO dto=assem.toDTO(allForms.get(i));
							dto.setActionDTO(actionsDTO);
							
						     filterdDTO.add(dto);
							}
						
					}
					else  
					{// first add list of actions to this petition 
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
						ReadmissionDTO dto=assem.toDTO(allForms.get(i));
						dto.setActionDTO(actionsDTO);
						
					     filterdDTO.add(dto);}
					
				
				}
				else
				{
				if(
						allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
				{	// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
					ReadmissionDTO dto=assem.toDTO(allForms.get(i));
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
	public ReadmissionDTO updateStatusOfForm(ReadmissionDTO dto) {
		try{
			ReadmissionForm form=assem.toEntity(dto);
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JReadmissionForm",form.getId());
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
	public List<ReadmissionDTO> getPendingFormsOfDean() {
		
		List<ReadmissionDTO> filterdDTO=new ArrayList<ReadmissionDTO>();
		try{
			List<ReadmissionForm> allForms=rep.getAll();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
					ReadmissionDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);
				}
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
					ReadmissionDTO dto=assem.toDTO(allForms.get(i));
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
	public List<ReadmissionDTO> getArchievedFormsOfDean() {
		
		List<ReadmissionDTO> filterdDTO=new ArrayList<ReadmissionDTO>();
		try
		{
			List<ReadmissionForm> allForms=rep.getAll();	
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
				ReadmissionDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
			}
			else
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
				{// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
					ReadmissionDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					
				     filterdDTO.add(dto);}
			}
			}
			else if(allForms.get(i).getPerformed()==null) 
			{if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
			{// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
				ReadmissionDTO dto=assem.toDTO(allForms.get(i));
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
		ReadmissionForm form = rep.getById(id);		
		form.setComment(comment); rep.update(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public ReadmissionDTO getByID(Integer id) {
		ReadmissionDTO dto=new ReadmissionDTO();
		try
		{
			ReadmissionForm form=rep.getById(id);
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
	public ReadmissionDTO forwardPetition(ReadmissionDTO dto) {
		try{
			ReadmissionForm form=assem.toEntity(dto);
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

	@Override
	public List<ReadmissionDTO> getPendingFormsOfDeanOfAcademic() {
			
			List<ReadmissionDTO> filterdDTO=new ArrayList<ReadmissionDTO>();
			try{
				List<ReadmissionForm> allForms=rep.getAll();
			for(int i=0;i<allForms.size();i++)
			{
				if(allForms.get(i).getPerformed()==null)
				{
					if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
					{
						// first add list of actions to this petition 
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
						ReadmissionDTO dto=assem.toDTO(allForms.get(i));
						dto.setActionDTO(actionsDTO);
						
					     filterdDTO.add(dto);
					}
				}
				else if(allForms.get(i).getPerformed()!=true)
				{
					if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
					{
						// first add list of actions to this petition 
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.READMISSION.getValue());
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
						ReadmissionDTO dto=assem.toDTO(allForms.get(i));
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



}
