/**
 * 
 */
package main.com.zc.services.applicationService.forms.tAJuniorProgram.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.tAJuniorProgram.assembler.TAJuniorProgramAssembler;
import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.IJuniorTAServiceInstructor;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.ITAJuniorProgramRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.petition.model.TAJuniorProgram;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class JuniorTAServiceInstructorImpl implements IJuniorTAServiceInstructor{

	@Autowired
	ITAJuniorProgramRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	IPetitionsActionsRep actionRep;
	
	TAJuniorProgramAssembler assem=new TAJuniorProgramAssembler();
	
	
	@Override
	public List<TAJuniorProgramDTO> getPendingFormsOfInstructor(Integer insID) {
		List<TAJuniorProgramDTO> filterdDTO=new ArrayList<TAJuniorProgramDTO>();
		try{
			
			List<TAJuniorProgram> allForms=rep.getByCourseCoordniatorPending(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR))
				{
				// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);

				}
				
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
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
	public List<TAJuniorProgramDTO> getArchievedFormsOfInstructor(Integer insID) {
		List<TAJuniorProgramDTO> filterdDTO=new ArrayList<TAJuniorProgramDTO>();
		try{
			List<TAJuniorProgram> allForms=rep.getByCourseCoordniatorOld(insID);
		/*	List<IncompleteGrade> forwardHistor=rep.getAll();
			for(int i=0;i<forwardHistor.size();i++)
			{
				String[] firstParts = forwardHistor.get(i).getForwardHistory().split(",");
				for(int j=0;j<firstParts.length;j++)
				{
					
				}
			}*/
			
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				filterdDTO.add(assem.toDTO(allForms.get(i)));
			}
			else
			{
				//TODO Updating scenario and removing program advisor step
				if(//allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||
				allForms.get(i).getStep().equals(PetitionStepsEnum.PA)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
				
				}
			}
			}
			else if(allForms.get(i).getPerformed()==null) 
			{
				
				
		
			
				//TODO updating scenario by removing program advisor step
				//if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||
						if(allForms.get(i).getStep().equals(PetitionStepsEnum.PA)
						||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
			{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
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
	public List<TAJuniorProgramDTO> getPendingFormsOfPA(Integer insID) {
		List<TAJuniorProgramDTO> filterdDTO=new ArrayList<TAJuniorProgramDTO>();
		try{
			
			List<TAJuniorProgram> allForms=rep.getPendingByPA(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||allForms.get(i).getStep().equals(PetitionStepsEnum.PA))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);
				}
				
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||allForms.get(i).getStep().equals(PetitionStepsEnum.PA))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
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
	public List<TAJuniorProgramDTO> getArchievedFormsOfPA(Integer insID) {
		List<TAJuniorProgramDTO> filterdDTO=new ArrayList<TAJuniorProgramDTO>();
		try{
			List<TAJuniorProgram> allForms=rep.getOldByPA(insID);
			
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
				TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);
			}
			else
			{
				
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
				
				}
			}
			}
			else if(allForms.get(i).getPerformed()==null||allForms.get(i).getPerformed()==false)
			{
				
		
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
			{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
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
	public TAJuniorProgramDTO updateStatusOfForm(TAJuniorProgramDTO dto) {
		
		try
		{
			TAJuniorProgram form=assem.toEntity(dto);
			// First update actions list
			if(dto.getActionDTO()!=null)
			{
				for(int i=0;i<dto.getActionDTO().size();i++)
				{
					if(dto.getActionDTO().get(i).getId()!=null)
					{
						//update
						PetitionsActions action=actionRep.getById(dto.getActionDTO().get(i).getId());
						action.setActionType(dto.getActionDTO().get(i).getActionType());
						action.setComment(dto.getActionDTO().get(i).getComment());
                        action.setDate(dto.getActionDTO().get(i).getDate());
                        action.setFormType(dto.getActionDTO().get(i).getFormType());
                        
                        actionRep.update(action);
                        
                        
						
					}
					else
						//add
						{
						PetitionsActions action=new  PetitionsActions();
						action.setActionType(dto.getActionDTO().get(i).getActionType());
						action.setComment(dto.getActionDTO().get(i).getComment());
                        action.setDate(dto.getActionDTO().get(i).getDate());
                        action.setFormType(dto.getActionDTO().get(i).getFormType());
                        Employee actionIns=new Employee();
                        actionIns.setId(dto.getActionDTO().get(i).getInstructorID());
                        action.setInstructor(actionIns);
                        action.setPetitionID(dto.getId());
                        
                        actionRep.add(action);
						}
						
				}
			}
			// Second update the petition itself
            
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JJuniorTA",form.getId());
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
	public List<TAJuniorProgramDTO> getPendingFormsOfDean() {
		List<TAJuniorProgramDTO> filterdDTO=new ArrayList<TAJuniorProgramDTO>();
		try
		{
			
			List<TAJuniorProgram> allForms=rep.getAll();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
					if(allForms.get(i).getStep().equals(PetitionStepsEnum.PA)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);
				}
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
			if(allForms.get(i).getStep().equals(PetitionStepsEnum.PA)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
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
	public List<TAJuniorProgramDTO> getArchievedFormsOfDean() {
		List<TAJuniorProgramDTO> filterdDTO=new ArrayList<TAJuniorProgramDTO>();
		try
		{
			List<TAJuniorProgram> allForms=rep.getAll();	
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
				TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);
			}
			else
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_HEAD))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
					TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);}
			}
			}
			else if(allForms.get(i).getPerformed()==null) 
			{if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_HEAD))
			{// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
				TAJuniorProgramDTO dto=assem.toDTO(allForms.get(i));
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
	public TAJuniorProgramDTO getByID(Integer id) {
		TAJuniorProgramDTO dto=new TAJuniorProgramDTO();
		try
		{
			TAJuniorProgram form=rep.getById(id);
			
			dto=assem.toDTO(form);
			// get the list of actions of this petitions 
			List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(form.getId(),FormTypesEnum.JUNIORTAPROGRAM.getValue());
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
			
			dto.setActionDTO(actionsDTO);
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) {
		try
		{
			// there is no old action
			if(dto.getId()==null)
			{
				PetitionsActions action=new PetitionsActions();
				action.setActionType(dto.getActionType());
				action.setComment(dto.getComment());
				action.setDate(dto.getDate());
				action.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
				Employee ins=new Employee();
				ins.setId(instructorID);
				action.setInstructor(ins);
				action.setPetitionID(dto.getPetitionID());
				actionRep.add(action);
			}
			else // there is no old action
			{
				PetitionsActions action=actionRep.getById(dto.getId());
				action.setActionType(dto.getActionType());
				action.setComment(dto.getComment());
				action.setDate(dto.getDate());
				action.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
				Employee ins=new Employee();
				ins.setId(instructorID);
				action.setInstructor(ins);
				action.setPetitionID(dto.getPetitionID());
				actionRep.update(action);
			}
		}
		catch(Exception ex)
		{
			
		}
		
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
	public List<TAJuniorProgramDTO> getPendingByDeanDashboard() {
		List<TAJuniorProgram> forms=new ArrayList<TAJuniorProgram>();
		List<TAJuniorProgramDTO> dtos=new ArrayList<TAJuniorProgramDTO>();
		try{
		forms=rep.getPendingByDeanDashboard();
		for(int i=0;i<forms.size();i++)
		{
			TAJuniorProgramDTO dto=new TAJuniorProgramDTO();
			dto=assem.toDTO(forms.get(i));
			dtos.add(dto);
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<TAJuniorProgramDTO> getPendingByCoordDashboard(Integer id) {
		List<TAJuniorProgram> forms=new ArrayList<TAJuniorProgram>();
		List<TAJuniorProgramDTO> dtos=new ArrayList<TAJuniorProgramDTO>();
		try{
		forms=rep.getPendingByCoordDashboard(id);
		for(int i=0;i<forms.size();i++)
		{
			TAJuniorProgramDTO dto=new TAJuniorProgramDTO();
			dto=assem.toDTO(forms.get(i));
			dtos.add(dto);
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<TAJuniorProgramDTO> getPendingByPADashboard(Integer id) {
		List<TAJuniorProgram> forms=new ArrayList<TAJuniorProgram>();
		List<TAJuniorProgramDTO> dtos=new ArrayList<TAJuniorProgramDTO>();
		try{
		forms=rep.getPendingByPADashboard(id);
		for(int i=0;i<forms.size();i++)
		{
			TAJuniorProgramDTO dto=new TAJuniorProgramDTO();
			dto=assem.toDTO(forms.get(i));
			dtos.add(dto);
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<TAJuniorProgramDTO> getByCourseID(Integer id) {
		List<TAJuniorProgram> forms=new ArrayList<TAJuniorProgram>();
		List<TAJuniorProgramDTO> dtos=new ArrayList<TAJuniorProgramDTO>();
		try{
		forms=rep.getByCourseID(id);
		for(int i=0;i<forms.size();i++)
		{
			TAJuniorProgramDTO dto=new TAJuniorProgramDTO();
			dto=assem.toDTO(forms.get(i));
			dtos.add(dto);
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return dtos;
	}



}
