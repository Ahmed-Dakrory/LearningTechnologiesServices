/**
 * 
 */
package main.com.zc.services.applicationService.forms.overloadRequest.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.overloadRequest.assembler.OverloadRquestAssembler;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IInsOverloadRequestService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.applicationService.shared.service.impl.SharedNotifyServiceImpl;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class InsOverloadRequestServiceImpl implements IInsOverloadRequestService{

	@Autowired
	IOverloadRequestRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	IPetitionsActionsRep actionRep;
	OverloadRquestAssembler assem=new OverloadRquestAssembler();
	@Override
	public List<OverloadRequestDTO> getPendingFormsOfInstructor(Integer insID) {
		List<OverloadRequestDTO> filterdDTO=new ArrayList<OverloadRequestDTO>();
		try{
			//List<OverloadRequest> allForms=rep.getByCourseCoordniator(insID);
			List<OverloadRequest> allForms=rep.getPendingByPA(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR))
				{
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
					OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
				}
				
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.UNDER_REVIEW)||allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR))
				{
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
					OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
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
	public List<OverloadRequestDTO> getArchievedFormsOfInstructor(Integer insID) {
		List<OverloadRequestDTO> filterdDTO=new ArrayList<OverloadRequestDTO>();
		try{
			//List<DropAddForm> allForms=addDropRepository.getByCourseCoordinatorID(insID);
			List<OverloadRequest> allForms=rep.getOldByPA(insID);
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
				OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
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
						if(		allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
								allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)||
								allForms.get(i).getStep().equals(PetitionStepsEnum.PROVOST)||
								allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
						
						    {
							List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
							OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
							dto.setActionDTO(actionsDTO);
							filterdDTO.add(dto);
							}
						
					}
					else  
						{
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
						OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
						dto.setActionDTO(actionsDTO);
						filterdDTO.add(dto);
						}
					
				
				}
				else
				{
				if(
						allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.PROVOST)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
				{	List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
				OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);}
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
	public OverloadRequestDTO updateStatusOfForm(OverloadRequestDTO dto) {
		try{
			OverloadRequest form=assem.toEntity(dto);
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JOverloadRequest",form.getId());
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
	public List<OverloadRequestDTO> getPendingFormsOfDean() {
		List<OverloadRequestDTO> filterdDTO=new ArrayList<OverloadRequestDTO>();
		try{
			List<OverloadRequest> allForms=rep.getAll();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
					OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
				}
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.INSTRUCTOR)||allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
					OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
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
	public List<OverloadRequestDTO> getArchievedFormsOfDean() {
		List<OverloadRequestDTO> filterdDTO=new ArrayList<OverloadRequestDTO>();
		try{
			List<OverloadRequest> allForms=rep.getAll();
			
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
				OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);
			}
			else
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.PROVOST)||allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)
						||allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
				{	List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
				OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				filterdDTO.add(dto);}
			}
			}
			else if(allForms.get(i).getPerformed()==null) 
			{if(allForms.get(i).getStep().equals(PetitionStepsEnum.PROVOST)||allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)||allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
			{	List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
			OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
			dto.setActionDTO(actionsDTO);
			filterdDTO.add(dto);}}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return filterdDTO;
	}

	@Override
	public List<OverloadRequestDTO> getPendingFormsOfProvost() {
		List<OverloadRequestDTO> filterdDTO=new ArrayList<OverloadRequestDTO>();
		try{
			List<OverloadRequest> allForms=rep.getAll();
			
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.PROVOST)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
				{
					if(allForms.get(i).getProvostRequired()!=null)
					{
					if(allForms.get(i).getProvostRequired()==true)
					{
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
						OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
						dto.setActionDTO(actionsDTO);
						filterdDTO.add(dto);
					}
					}
				}
			}
			else if(allForms.get(i).getPerformed()!=null)
			{
				if(allForms.get(i).getPerformed()==false)
				{
					if(allForms.get(i).getStep().equals(PetitionStepsEnum.PROVOST)||
							allForms.get(i).getStep().equals(PetitionStepsEnum.DEAN))
					{
						List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
						OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
						dto.setActionDTO(actionsDTO);
						filterdDTO.add(dto);
					}
				}
			}
		}}
	catch(Exception ex)
		{
		ex.printStackTrace();
		}
		return filterdDTO;
	}

	@Override
	public List<OverloadRequestDTO> getArchievedFormsOfProvost() {
		List<OverloadRequestDTO> filterdDTO=new ArrayList<OverloadRequestDTO>();
		try{
			List<OverloadRequest> allForms=rep.getAll();
			
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
				if(allForms.get(i).getPerformed()==true)
					
			
					{
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
					OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
					}
				
			}
			else if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)||
						allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
				{
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
					OverloadRequestDTO	dto=assem.toDTO(allForms.get(i));
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
	public void addComment(Integer id, String comment) {
		
		OverloadRequest request = rep.getById(id);			
		request.setComment(comment); rep.update(request);
	}

	@Override
	public OverloadRequestDTO gteById(Integer id) {
		OverloadRequestDTO dto=new OverloadRequestDTO();
		try
	{
			OverloadRequest form=rep.getById(id);
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
	public OverloadRequestDTO forwardPetition(OverloadRequestDTO dto) {
		try{
			OverloadRequest form=assem.toEntity(dto);
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
