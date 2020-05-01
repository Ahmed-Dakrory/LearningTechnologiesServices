/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.course_replacement_form.assembler.course_replacement_formAssembler;
import main.com.zc.services.applicationService.forms.course_replacement_form.services.IInstructorcourse_replacement_formService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.domain.petition.model.Icourse_replacement_formFormRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class Instructorcourse_replacement_formServiceImpl implements IInstructorcourse_replacement_formService{

	@Autowired
	Icourse_replacement_formFormRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	IPetitionsActionsRep actionRep;
    course_replacement_formAssembler assem=new course_replacement_formAssembler();
	@Override
	public List<course_replacement_formDTO> getPendingFormsOfInstructor(Integer insID) {
		List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
		try{
			
			List<course_replacement_formForm> allForms=rep.getPendingByPA(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
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
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
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
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
	}

	@Override
	public List<course_replacement_formDTO> getArchievedFormsOfInstructor(Integer insID) {
		List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
		try{
			//List<DropAddForm> allForms=addDropRepository.getByCourseCoordinatorID(insID);
			List<course_replacement_formForm> allForms=rep.getOldByPA(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
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
					else  
					{// first add list of actions to this petition 
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
						
					     filterdDTO.add(dto);}
					
				
				}
				else
				{
				if(
						allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
				{	// first add list of actions to this petition 
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
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
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

	@Override
	public List<course_replacement_formDTO> getPendingFormsOfDean() {
		
		List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
		try{
			List<course_replacement_formForm> allForms=rep.getAll();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
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
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
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
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
	}

	@Override
	public List<course_replacement_formDTO> getArchievedFormsOfDean() {
		
		List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
		try
		{
			List<course_replacement_formForm> allForms=rep.getAll();	
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
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
			else
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
				{// first add list of actions to this petition 
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
					
				     filterdDTO.add(dto);}
			}
			}
			else if(allForms.get(i).getPerformed()==null) 
			{if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
			{// first add list of actions to this petition 
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
		course_replacement_formForm form = rep.getById(id);		
		form.setComment(comment); rep.update(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public course_replacement_formDTO getByID(Integer id) {
		course_replacement_formDTO dto=new course_replacement_formDTO();
		try
		{
			course_replacement_formForm form=rep.getById(id);
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
	public course_replacement_formDTO forwardPetition(course_replacement_formDTO dto) {
		try{
			course_replacement_formForm form=assem.toEntity(dto);
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
	public List<course_replacement_formDTO> getPendingFormsOfDeanOfAcademic() {
			
			List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
			try{
				List<course_replacement_formForm> allForms=rep.getAll();
			for(int i=0;i<allForms.size();i++)
			{
				if(allForms.get(i).getPerformed()==null)
				{
					if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
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
				else if(allForms.get(i).getPerformed()!=true)
				{
					if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
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
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return filterdDTO;
		}



}
