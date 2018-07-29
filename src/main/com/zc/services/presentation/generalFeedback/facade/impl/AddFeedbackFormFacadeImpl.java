/**
 * 
 */
package main.com.zc.services.presentation.generalFeedback.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.generalFeedback.service.IAddFeedbackFormAppService;
import main.com.zc.services.domain.shared.exception.DuplicatedRowException;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;
import main.com.zc.services.presentation.generalFeedback.facade.IAddFeedbackFormFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service("addFeedbackFormFacadeImpl")
public class AddFeedbackFormFacadeImpl implements IAddFeedbackFormFacade{

	@Autowired
	IAddFeedbackFormAppService feedbackAppService;
	@Override
	public FeedbackDTO addFeedbackForm(FeedbackDTO dao) throws DuplicatedRowException {
	
		return feedbackAppService.add(dao);
		
	}
	@Override
	public FeedbackDTO updateFeedbackForm(FeedbackDTO dao) {
		
		return feedbackAppService.update(dao);
	}
	
	@Override
	public boolean removeFeedback(Integer id) {
		
		return feedbackAppService.removeFeedback(id);
	}
	@Override
	public FeedbackDTO getFeedbackByID(Integer id) {
		
		return feedbackAppService.getFeedbackByID(id);
	}
	@Override
	public List<FeedbackDTO> getfeedbacksByEmail(String mail) {
		
		return feedbackAppService.getfeedbacksByEmail(mail);
	}

}
