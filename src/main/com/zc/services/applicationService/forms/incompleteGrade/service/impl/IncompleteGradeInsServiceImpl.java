/**
 * 
 */
package main.com.zc.services.applicationService.forms.incompleteGrade.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.incompleteGrade.assembler.IncompleteGradeAssembler;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeInsService;
import main.com.zc.services.applicationService.persons.service.IStudentProfileService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.IIncompleteGradeRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.IncompleteGrade;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class IncompleteGradeInsServiceImpl implements IIncompleteGradeInsService{

	@Autowired
	IIncompleteGradeRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	IPetitionsActionsRep actionRep;
	@Autowired
	IStudentProfileService studentProfileService;
	
	IncompleteGradeAssembler assem=new IncompleteGradeAssembler();
	
	@Override
	public List<IncompleteGradeDTO> getPendingFormsOfInstructor(Integer insID) {
		List<IncompleteGradeDTO> filterdDTO=new ArrayList<IncompleteGradeDTO>();
		try{
			
			List<IncompleteGrade> allForms=rep.getPendingByInstructor(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
				{
				// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.INCOMPLETEGRADE.getValue());
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);

				}
				
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
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
	public List<IncompleteGradeDTO> getArchievedFormsOfInstructor(Integer insID) {
		List<IncompleteGradeDTO> filterdDTO=new ArrayList<IncompleteGradeDTO>();
		try{
			List<IncompleteGrade> allForms=rep.getOldByInstructor(insID);
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
				//(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE)||
						
						allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)
						||allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT)
						||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
				
				}
			}
			}
			else if(allForms.get(i).getPerformed()==null) 
			{
				
				if(allForms.get(i).getForwardTOIns()!=null)
				{
				if(allForms.get(i).getForwardTOIns().getId().equals(insID))
				{
					//TODO Updating scenario and removing program advisor step
					//if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||
					if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE)||
							allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)
							||allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT)
							||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
						// first add list of actions to this petition 
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
						IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
						dto.setActionDTO(actionsDTO);
						
						filterdDTO.add(dto);
				}
				}
				else 
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
				}
				
				}
		
			else 
			{
				//TODO updating scenario by removing program advisor step
				//if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||
						if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)
						||allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT)
						||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
			{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
			}
			}
		}
		}	}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
	}

	@Override
	public List<IncompleteGradeDTO> getPendingFormsOfPA(Integer insID) {
		List<IncompleteGradeDTO> filterdDTO=new ArrayList<IncompleteGradeDTO>();
		try{
			
			List<IncompleteGrade> allForms=rep.getPendinByPA(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);
				}
				
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
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
	public List<IncompleteGradeDTO> getArchievedFormsOfPA(Integer insID) {
		List<IncompleteGradeDTO> filterdDTO=new ArrayList<IncompleteGradeDTO>();
		try{
			List<IncompleteGrade> allForms=rep.getOldByPA(insID);
			
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
				IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);
			}
			else
			{
				
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)
						||allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
				
				}
			}
			}
			else if(allForms.get(i).getPerformed()==null) 
			{
				
				if(allForms.get(i).getForwardTOIns()!=null)
				{
				if(allForms.get(i).getForwardTOIns().getId().equals(insID))
				{
					if(		allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
							allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)||
							allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
				{
						// first add list of actions to this petition 
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
						IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
						dto.setActionDTO(actionsDTO);
						filterdDTO.add(dto);
				}
				}
				else 
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
				}
				
				}
			}
			else 
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
			{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
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
	public IncompleteGradeDTO updateStatusOfForm(IncompleteGradeDTO dto) {
		/*try{
			IncompleteGrade form=assem.toEntity(dto);
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JIncompleteGrade",form.getId());
			}	
			return assem.toDTO(form);
			}
			catch(Exception ex)
			{
				System.out.println("<<<<<<<<<< Form can't be updated >>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}*/
	
			
			
			try
			{
				IncompleteGrade form=assem.toEntity(dto);
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
					sharedNotifyService.removeJobFromScheduler("JIncompleteGrade",form.getId());
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
	public List<IncompleteGradeDTO> getPendingFormsOfDean() {
		List<IncompleteGradeDTO> filterdDTO=new ArrayList<IncompleteGradeDTO>();
		try
		{
			
			List<IncompleteGrade> allForms=rep.getAll();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				//TODO updating scenario and removing program advisor step
				//if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);
				}
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				//TODO Updating form scenario and removing program advisor step
				//if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
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
	public List<IncompleteGradeDTO> getArchievedFormsOfDean() {
		List<IncompleteGradeDTO> filterdDTO=new ArrayList<IncompleteGradeDTO>();
		try
		{
			List<IncompleteGrade> allForms=rep.getAll();	
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
				IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);
			}
			else
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
					IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);}
			}
			}
			else if(allForms.get(i).getPerformed()==null) 
			{if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
			{// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),5);
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
				IncompleteGradeDTO dto=assem.toDTO(allForms.get(i));
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
	public IncompleteGradeDTO getByID(Integer id) {
		IncompleteGradeDTO dto=new IncompleteGradeDTO();
		try
		{
			IncompleteGrade form=rep.getById(id);
			
			dto=assem.toDTO(form);
			// get the list of actions of this petitions 
			List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(form.getId(),5);
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
			dto.getStudent().setStudentProfileDTO(studentProfileService.getCurrentPRofileByStudentID(form.getStudent().getId()));
			
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
				action.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
				action.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
	public IncompleteGradeDTO forwardPetition(IncompleteGradeDTO dto) {
		try{
			
			IncompleteGrade form=assem.toEntity(dto);
			IncompleteGrade oldForm=rep.getById(dto.getId());
			String history=oldForm.getForwardHistory();
			if(history!=null)
			{
			history+=","+dto.getForwardFromIns().getId()+"-"+dto.getForwardTOIns().getId();
			}
			else 
			{	history=dto.getForwardFromIns().getId()+"-"+dto.getForwardTOIns().getId();
			}
			
			form.setForwardHistory(history);
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
	public IncompleteGradeDTO update(IncompleteGradeDTO dto) {
		// TODO Auto-generated method stub
		try{
			IncompleteGrade form=assem.toEntity(dto);
			form=rep.update(form);
			return assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
}
