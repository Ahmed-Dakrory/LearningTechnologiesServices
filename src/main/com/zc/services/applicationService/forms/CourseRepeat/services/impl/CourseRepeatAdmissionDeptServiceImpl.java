/**
 * 
 */
package main.com.zc.services.applicationService.forms.CourseRepeat.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.CourseRepeat.assembler.CourseRepeatAssembler;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatAdmissionDeptService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.IRepeatCourseFormRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;

/**
 * @author omnya
 *
 */
@Service
public class CourseRepeatAdmissionDeptServiceImpl implements ICourseRepeatAdmissionDeptService {

	@Autowired
	IRepeatCourseFormRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;
	@Autowired
	IPetitionsActionsRep actionRep;
    CourseRepeatAssembler assem=new CourseRepeatAssembler();
	@Override
	public CourseRepeatDTO updateRequest(CourseRepeatDTO dto) {
		try{
			RepeatCourseForm form=assem.toEntity(dto);
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JRepeatCourseForm",form.getId());
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
	public CourseRepeatDTO getByID(Integer id) {
		CourseRepeatDTO dto=new CourseRepeatDTO();
		try
	{
			RepeatCourseForm form=rep.getById(id);
			dto=assem.toDTO(form);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return dto;
	}

	@Override
	public List<CourseRepeatDTO> getPendingPetitionsOfstuent() {
		List<CourseRepeatDTO> filterdDTO=new ArrayList<CourseRepeatDTO>();
		try{
		List<RepeatCourseForm> allForms=rep.getAll();
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				if(allForms.get(i).getStep().equals(PetitionStepsEnum.ADMISSION_HEAD))
					{

					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.REPEATECOURSE.getValue());
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
					CourseRepeatDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
					}
			}
			else if(allForms.get(i).getPerformed()!=null)
				{
				if(allForms.get(i).getPerformed()!=true)
				{
					// first add list of actions to this petition 
					List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.REPEATECOURSE.getValue());
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
					CourseRepeatDTO dto=assem.toDTO(allForms.get(i));
					dto.setActionDTO(actionsDTO);
					filterdDTO.add(dto);
					}
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
	public List<CourseRepeatDTO> getArchievedPetitionsOfstuent() {
		List<CourseRepeatDTO> filterdDTO=new ArrayList<CourseRepeatDTO>();
		try{
		List<RepeatCourseForm> allForms=rep.getAll();
		
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			if(allForms.get(i).getPerformed()==true)
				{
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.REPEATECOURSE.getValue());
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
				CourseRepeatDTO dto=assem.toDTO(allForms.get(i));
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
	public void addComment(Integer id, String comment) {
	try
	{
		
		RepeatCourseForm form = rep.getById(id);		
		form.setComment(comment); rep.update(form);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		
	}
	@Override
	public List<CourseRepeatDTO> getAuditingPet() {
		List<CourseRepeatDTO> filterdDTO=new ArrayList<CourseRepeatDTO>();
		try{
		List<RepeatCourseForm> allForms=rep.getAllAuditing();
		
		for(RepeatCourseForm petition:allForms){
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
	@Override
	public int getPendingAndAuditingCourseRepeateAdmissionDept() {
		try{
		return rep.getPendingAuditingAdmissionDept().size();
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting pending petition");
			ex.printStackTrace();
			return 0;
		}
	}
}
