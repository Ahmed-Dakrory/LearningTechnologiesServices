/**
 * 
 */
package main.com.zc.services.presentation.generalFeedback.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.generalFeedback.service.IFeedbackAppService;
import main.com.zc.services.domain.shared.exception.DuplicatedRowException;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;
import main.com.zc.services.presentation.generalFeedback.facade.IFeedbacksFacade;

/**
 * @author Omnya Alaa
 *
 */
@Service(value="feedbacksFacadeImpl")
public class FeedbacksFacadeImpl implements IFeedbacksFacade{

	@Autowired
	IFeedbackAppService feedbackAppService;
	@Override
	public List<FeedbackDTO> getAll() {
		
		
		return feedbackAppService.getAll();
	}
	@Override
	public FeedbackDTO add(FeedbackDTO feedback) throws DuplicatedRowException {
		return feedbackAppService.add(feedback);
	}
	@Override
	public List<FeedbackDTO> getFeedbackPersonID(Integer personId) {
		return feedbackAppService.getFeedbackPersonID(personId);
	}
	@Override
	public FeedbackDTO getById(Integer id) {
		return feedbackAppService.getById(id);
	}
	@Override
	public List<FeedbackDTO> getByCategId(Integer id) {
		return feedbackAppService.getByCategId(id);
	}
	@Override
	public List<FeedbackDTO> getByPersonIdAndStep(Integer id, Integer step) {
		return feedbackAppService.getByPersonIdAndStep(id,step);
	}
	@Override
	public List<FeedbackDTO> getByCategoryAndStep(Integer id, Integer step) {
		return feedbackAppService.getByCategoryAndStep(id,step);
	}
	@Override
	public List<FeedbackDTO> getByMajorAndStep(Integer id, Integer step) {
		return feedbackAppService.getByMajorAndStep(id,step);
	}
	@Override
	public List<FeedbackDTO> getByMajorHeadAndStep(Integer id, Integer step) {
		return feedbackAppService.getByMajorHeadAndStep(id,step);
		
	}
	@Override
	public List<FeedbackDTO> getByStep(Integer step) {
		return feedbackAppService.getByStep(step);
	}
	@Override
	public List<FeedbackDTO> getPendingFeedbacks(Integer personID) {
	
		return feedbackAppService.getPendingFeedbacks(personID);
	}
	@Override
	public List<FeedbackDTO> getOldFeedbacks(Integer personID) {
		return feedbackAppService.getOldFeedbacks(personID);
	}
	@Override
	public FeedbackDTO getFeedbackByID(Integer id) {
		return feedbackAppService.getFeedbackByID(id);
	}
	@Override
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) {
		 feedbackAppService.addComment( dto, instructorID);
		
	}


	@Override
	public List<FeedbackDTO> getPendingByMajorHead(Integer insID) {
		return  feedbackAppService.getPendingByMajorHead(insID);
	}
	@Override
	public List<FeedbackDTO> getOldByMajorHead(Integer insID) {
		return  feedbackAppService.getOldByMajorHead(insID);
	}
	@Override
	public List<FeedbackDTO> getPendingHandler() {
		
		return feedbackAppService.getPendingHandler();
	}
	@Override
	public List<FeedbackDTO> getOldHandler() {
		return feedbackAppService.getOldHandler();
	}
	@Override
	public FeedbackDTO updateStatusOfForm(FeedbackDTO dto) {
		return feedbackAppService.updateStatusOfForm(dto);
	}
	@Override
	public List<FeedbackDTO> getPendingByCategoryHead(Integer id) {
		return feedbackAppService.getPendingByCategoryHead(id);
	}
	@Override
	public List<FeedbackDTO> getOldByCategoryHead(Integer id) {
		return feedbackAppService.getOldByCategoryHead(id);
	}

}
