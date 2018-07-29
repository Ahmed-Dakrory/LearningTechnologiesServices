/**
 * 
 */
package main.com.zc.services.applicationService.generalFeedback.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.generalFeedback.assempler.FeedBackAssempler;
import main.com.zc.services.applicationService.generalFeedback.service.IAddFeedbackFormAppService;
import main.com.zc.services.domain.feedback.model.Feedback;
import main.com.zc.services.domain.feedback.model.FeedbackCategory;
import main.com.zc.services.domain.feedback.model.ICategoryRepository;
import main.com.zc.services.domain.feedback.model.IFeedbackRepository;
import main.com.zc.services.domain.person.model.ILoginDataRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.LoginData;
import main.com.zc.services.domain.shared.exception.DuplicatedRowException;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service
public class AddFeedbackFormAppServiceImpl implements IAddFeedbackFormAppService{

	@Autowired
	IFeedbackRepository feedbackRep;
	
	@Autowired
	IStudentRepository personRep;
	@Autowired
	ICategoryRepository catRep;
	@Autowired
	ILoginDataRepository loginDataRep;
	FeedBackAssempler assem=new FeedBackAssempler();
	@Override
	public FeedbackDTO add(FeedbackDTO dto) throws DuplicatedRowException{   // return 0 if failed to add , and id of inserted row if added successfully
		
		try
		{
			Feedback feedback=assem.toEntity(dto);
			feedback= feedbackRep.add(feedback);
			return assem.toDTO(feedback);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error in adding new feedback");
			return null;
		}
			
		
		
	}
	@Override
	public FeedbackDTO update(FeedbackDTO dto) {// return 0 if failed to update , and id of updated row if updated successfully
		try
		{
			/*if(personRep.getPersonById(dto.getFileNo())!=null)
			{*/
			Feedback feedback=assem.toEntity(dto);
			feedback=feedbackRep.update(feedback);
			 return assem.toDTO(feedback);
			/*}
			else
			{
				return null;
			}*/
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error in updating feedback new feedback");
			return null;
		}
		
		
	}

	@Override
	public boolean removeFeedback(Integer id) {
		
		try{
		return feedbackRep.remove(id);
		}catch(Exception ex)
		{
			return false;
		}
	}
	
	
	@Override
	public FeedbackDTO getFeedbackByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<FeedbackDTO> getfeedbacksByEmail(String email) {
		List<FeedbackDTO> dtos=new ArrayList<FeedbackDTO>();
		try
		{
			LoginData log=loginDataRep.getByMail(email);
			
			List<Feedback> feedback=feedbackRep.getFeedbackPersonID(log.getId());
			for(int i=0;i<feedback.size();i++)
			{
				dtos.add(assem.toDTO(feedback.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		return dtos;
	}

}
