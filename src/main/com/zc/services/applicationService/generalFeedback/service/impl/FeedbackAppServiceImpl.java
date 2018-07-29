/**
 * 
 */
package main.com.zc.services.applicationService.generalFeedback.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.generalFeedback.assempler.FeedBackAssempler;
import main.com.zc.services.applicationService.generalFeedback.service.IFeedbackAppService;
import main.com.zc.services.applicationService.persons.service.IStudentProfileService;
import main.com.zc.services.domain.feedback.model.Feedback;
import main.com.zc.services.domain.feedback.model.IFeedbackRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.domain.shared.exception.DuplicatedRowException;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;
import main.com.zc.shared.appService.IGetInstructorDataAppService;
import main.com.zc.shared.appService.IPersonGetDataAppService;

/**
 * @author Omnya Alaa
 *
 */
@Service
public class FeedbackAppServiceImpl implements IFeedbackAppService {

	@Autowired
	IFeedbackRepository feedbackRep;
	@Autowired
	IGetInstructorDataAppService insService;
	@Autowired
	IPersonGetDataAppService studentService;
	@Autowired
	IStudentProfileService studentProfileService;
	@Autowired
	IPetitionsActionsRep actionRep;
	FeedBackAssempler assem=new FeedBackAssempler();
	@Override
	public List<FeedbackDTO> getAll() {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getAll();
		
		FeedBackAssempler assem=new FeedBackAssempler();
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public FeedbackDTO add(FeedbackDTO feedback) throws DuplicatedRowException {
		try
		{
			Feedback entity=assem.toEntity(feedback);
			entity= feedbackRep.add(entity);
			if(entity.getId()!=null)
			{entity=feedbackRep.getById(entity.getId());
			}
			
			return assem.toDTO(entity);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error in adding new feedback");
			return null;
		}
	}
	
	@Override
	public List<FeedbackDTO> getFeedbackPersonID(Integer personId) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getFeedbackPersonID(personId);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public FeedbackDTO getById(Integer id) {
		FeedbackDTO dto=new FeedbackDTO();
		try{
		Feedback feedback=feedbackRep.getById(id);
		
	
			dto= assem.toDTO(feedback);
		
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}
	@Override
	public List<FeedbackDTO> getByCategId(Integer id) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getByCategId(id);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getByPersonIdAndStep(Integer id, Integer step) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getByPersonIdAndStep(id,step);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getByCategoryAndStep(Integer id, Integer step) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getByCategoryAndStep(id,step);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getByMajorAndStep(Integer id, Integer step) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getByMajorAndStep(id,step);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getByMajorHeadAndStep(Integer id, Integer step) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getByMajorHeadAndStep(id,step);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
					dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getByStep(Integer step) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getByStep(step);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));

			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getPendingFeedbacks(Integer personID) {
		try{
		//Pending: 0 , 8 and 9
		
		List<FeedbackDTO> dtos1=getByPersonIdAndStep(personID,PetitionStepsEnum.UNDER_REVIEW.getValue());
		List<FeedbackDTO> dtos2=getByPersonIdAndStep(personID,PetitionStepsEnum.UNDER_PROCESSING.getValue());
		
		for(int i=0;i<dtos2.size();i++)
		{
			dtos1.add(dtos2.get(i));
		}
		dtos2=new ArrayList<FeedbackDTO>();
		dtos2=getByPersonIdAndStep(personID,PetitionStepsEnum.CHECKED.getValue());
		for(int i=0;i<dtos2.size();i++)
		{
			dtos1.add(dtos2.get(i));
		}
		return dtos1;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public List<FeedbackDTO> getOldFeedbacks(Integer personID) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
			//Old :10
			List<Feedback> feedbacks=feedbackRep.getoldByStudentID(personID);
			
			
			FeedBackAssempler assem=new FeedBackAssempler();
			for(int i=0;i<feedbacks.size();i++)
			{
				FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			dtos.add(dto);
			}
			return dtos;
			}
			catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
	}
	@Override
	public FeedbackDTO getFeedbackByID(Integer id) {
		
		
		FeedbackDTO dto=new FeedbackDTO();
		try{
			Feedback form=feedbackRep.getById(id);
			List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(form.getId(),FormTypesEnum.FEEDBACK.getValue());
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
			dto=assem.toDTO(form);
			dto.getStudent().setStudentProfileDTO(studentProfileService.getCurrentPRofileByStudentID(form.getStudent().getId()));
			dto.setActionDTO(actionsDTO);
			if(dto.getCategoryID()!=1)
			{
				dto.setCategoryHeadName(insService.getInsByPersonID(form.getCategory().getCategoryHeadID()).getName());
			}
			
			
			
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
				action.setFormType(FormTypesEnum.FEEDBACK);
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
				action.setFormType(FormTypesEnum.FEEDBACK);
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
	public List<FeedbackDTO> getByCategoryHeadAndStep(Integer id, Integer step) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getByCategoryHeadAndStep(id,step);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
					dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getPendingByMajorHead(Integer insID) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getPendingByMajorHead(insID);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			if(dto.getPerformed()!=null)
			{
				if(!dto.getPerformed())
			dtos.add(dto);
			}
			else 
				dtos.add(dto);
					
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getOldByMajorHead(Integer insID) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getOldByMajorHead(insID);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			if(dto.getPerformed()!=null)
			{
				if(!(dto.getStep().equals(PetitionStepsEnum.CHECKED) && dto.getPerformed()))
			
					dtos.add(dto);
			}
			else 
					dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getPendingHandler() {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
			dtos=getByStep(0);
			List<FeedbackDTO> dtos2=new ArrayList<FeedbackDTO>();
			dtos2=getByStep(8);
			for(int i=0;i<dtos2.size();i++)
			{
				if(dtos2.get(i).getPerformed()!=null)
				{
					if(!dtos2.get(i).getPerformed())
				dtos.add(dtos2.get(i));
				}
				else 
					dtos.add(dtos2.get(i));
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getOldHandler() {
	
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getHandlerOld();
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
					dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public FeedbackDTO updateStatusOfForm(FeedbackDTO dto) {
		try
		{
			
			
			Feedback form=assem.toEntity(dto);
			if(dto.getMajor()!=null)
			if(dto.getMajor().getId()==null){
				dto.setMajor(null);
				form.setMajor(null);
			}
			form.setId(feedbackRep.getById(dto.getId()).getId());
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
          
			form=feedbackRep.update(form);
		/*	  //TODO notification setting
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JCoursePetition",form.getId());
			}	*/
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
	public List<FeedbackDTO> getPendingByCategoryHead(Integer id) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getPendingByCategoryHead(id);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			if(dto.getPerformed()!=null)
			{
				if(!dto.getPerformed())
			dtos.add(dto);
			}
			else 
				dtos.add(dto);
					
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	@Override
	public List<FeedbackDTO> getOldByCategoryHead(Integer id) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try{
		List<Feedback> feedbacks=feedbackRep.getOldByCategoryHead(id);
		
		for(int i=0;i<feedbacks.size();i++)
		{
			FeedbackDTO dto= assem.toDTO(feedbacks.get(i));
			if(dto.getPerformed()!=null)
			{
				if(!(dto.getStep().equals(PetitionStepsEnum.CHECKED) && dto.getPerformed()))
			
					dtos.add(dto);
			}
			else 
					dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}

}
