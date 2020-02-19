/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.com.zc.services.applicationService.forms.academicPetition.assembler.AcademicPetitionAssembler;
import main.com.zc.services.applicationService.forms.academicPetition.services.IInstructorAcademicPetitionService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class InstructorAcademicPetitionServiceImpl implements IInstructorAcademicPetitionService{

	@Autowired
	ICoursePetitionRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	IPetitionsActionsRep actionRep;
	
	AcademicPetitionAssembler assem=new AcademicPetitionAssembler();
	@Override
	public List<CoursePetitionDTO> getPendingPetOfInstructor(Integer insId) {
		List<CoursePetition> allForms=rep.getByCourseCoordinatorIDPending(insId);
		List<CoursePetitionDTO> filterdDTO=new ArrayList<CoursePetitionDTO>();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR))
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
				//filterdDTO.add(assem.toDTO(allForms.get(i)));
				}
				
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR))
				{
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
				//filterdDTO.add(assem.toDTO(allForms.get(i)));
				}
			}
		}
		return filterdDTO;
	}

	@Override
	public List<CoursePetitionDTO> getOldPetOfInstructor(Integer insId) {
		List<CoursePetition> allForms=rep.getByCourseCoordinatorIDOld(insId);
		List<CoursePetitionDTO> filterdDTO=new ArrayList<CoursePetitionDTO>();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
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
				//filterdDTO.add(assem.toDTO(allForms.get(i)));
				
			
			}
			}
			else if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getForwardTOIns()!=null)
				{
					if(allForms.get(i).getForwardTOIns().getId().equals(insId))
					{
						if(
								allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
								allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
						
						    {
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
							//filterdDTO.add(assem.toDTO(allForms.get(i)));
							}
						
					}
					else  
					{
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
						//filterdDTO.add(assem.toDTO(allForms.get(i)));
					
				
				}
				else
				{
				if(
						allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
				{//filterdDTO.add(assem.toDTO(allForms.get(i)));}
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
			}
		}
		return filterdDTO;
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
	public List<CoursePetitionDTO> getPendingPetOfDean() {
		List<CoursePetition> allForms=rep.getAll();
		List<CoursePetitionDTO> filterdDTO=new ArrayList<CoursePetitionDTO>();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
				//filterdDTO.add(assem.toDTO(allForms.get(i)));
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
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
				//filterdDTO.add(assem.toDTO(allForms.get(i)));
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
		}
		return filterdDTO;
	}

	@Override
	public List<CoursePetitionDTO> getOldPetOfDean() {
		List<CoursePetition> allForms=rep.getAll();
		List<CoursePetitionDTO> filterdDTO=new ArrayList<CoursePetitionDTO>();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				//filterdDTO.add(assem.toDTO(allForms.get(i)));
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
			else
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
				{
					//filterdDTO.add(assem.toDTO(allForms.get(i)));
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
			}
			else if(allForms.get(i).getPerformed()==null) 
			{if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
			{
				//filterdDTO.add(assem.toDTO(allForms.get(i)));
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
				}}
		}
		return filterdDTO;
	}

	@Override
	public void addComment(CoursePetitionDTO dto , Integer personID) {
		
		/*CoursePetition petition = rep.getById(id);		
		petition.setComment(comment); rep.update(petition);*/
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
				instructor.setId(personID);
				action.setInstructor(instructor);
				action.setDate(Calendar.getInstance());
				action.setFormType(FormTypesEnum.ACADEMICPETITION);
				action.setPetitionID(dto.getId());
				actionRep.add(action);
			}
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		
	}

	@Override
	public CoursePetitionDTO getByID(Integer id) {
		CoursePetitionDTO dto=new CoursePetitionDTO();
		
		try{
			CoursePetition form=rep.getById(id);
			List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(form.getId(),FormTypesEnum.ACADEMICPETITION.getValue());
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
	public CoursePetitionDTO forwardPetition(CoursePetitionDTO dto) {
		try{
		CoursePetition form=assem.toEntity(dto);
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
