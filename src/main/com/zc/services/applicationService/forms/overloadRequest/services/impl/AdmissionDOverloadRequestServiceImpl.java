/**
 * 
 */
package main.com.zc.services.applicationService.forms.overloadRequest.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.overloadRequest.assembler.OverloadRquestAssembler;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IAdmissionDOverloadRequestService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.applicationService.shared.service.impl.SharedNotifyServiceImpl;
import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya.alaa
 *
 */
@Service
public class AdmissionDOverloadRequestServiceImpl implements IAdmissionDOverloadRequestService{

	@Autowired
	IOverloadRequestRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IPetitionsActionsRep actionRep;
	OverloadRquestAssembler assem=new OverloadRquestAssembler(); 
	
	@Override
	public List<OverloadRequestDTO> getPendingForms() {
		List<OverloadRequestDTO> dtos=new ArrayList<OverloadRequestDTO>();
		try
		{
			List<OverloadRequest> request=rep.getAll();
			for(int i=0;i<request.size();i++)
			{
				if(request.get(i).getPerformed()==null)
				{
				if(request.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_HEAD)||request.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_HEAD))
					{
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(request.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
					OverloadRequestDTO	dto=assem.toDTO(request.get(i));
					dto.setActionDTO(actionsDTO);
					dtos.add(dto);
					}
				}
				
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error in getting petitions");
		}
		return dtos;
	}

	@Override
	public List<OverloadRequestDTO> getArchievedForms() {
		List<OverloadRequestDTO> dtos=new ArrayList<OverloadRequestDTO>();
		try
		{
			List<OverloadRequest> request=rep.getAll();
			for(int i=0;i<request.size();i++)
			{
				if(request.get(i).getPerformed()!=null)
				{
				if(request.get(i).getPerformed()==true)
					{
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(request.get(i).getId(),FormTypesEnum.OVERLOADREQUEST.getValue());
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
					OverloadRequestDTO	dto=assem.toDTO(request.get(i));
					dto.setActionDTO(actionsDTO);
					dtos.add(dto);
					}
				
				}
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error in getting petitions");
		}
		return dtos;
	}

	@Override
	public OverloadRequestDTO updateStatus(OverloadRequestDTO dto) {
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
	public void addComment(Integer id, String comment) {
		
		OverloadRequest request = rep.getById(id);			
		request.setComment(comment); rep.update(request);
	}
	@Override
	public List<OverloadRequestDTO> getAuditingPet() {
		List<OverloadRequestDTO> filterdDTO=new ArrayList<OverloadRequestDTO>();
		try{
		List<OverloadRequest> allForms=rep.getAllAuditing();
		
		for(OverloadRequest petition:allForms){
			filterdDTO.add(assem.toDTO(petition));
		}
				
		return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting Auditing petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	}
}
