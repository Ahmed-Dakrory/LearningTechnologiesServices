/**
 * 
 */
package main.com.zc.services.presentation.generalFeedback.facade;

import java.util.List;

import main.com.zc.services.domain.shared.exception.DuplicatedRowException;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IFeedbacksFacade {

	public List<FeedbackDTO> getAll();
	public FeedbackDTO add(FeedbackDTO feedback) throws DuplicatedRowException;
	public List<FeedbackDTO> getFeedbackPersonID(Integer personId);
	public FeedbackDTO getById(Integer id);
	
	//New version
	public List<FeedbackDTO>  getByCategId(Integer id);
	public List<FeedbackDTO>  getByPersonIdAndStep(Integer id,Integer step);
	public List<FeedbackDTO>  getByCategoryAndStep(Integer id,Integer step);
	public List<FeedbackDTO>  getByMajorAndStep(Integer id,Integer step);
	public List<FeedbackDTO>  getByMajorHeadAndStep(Integer id,Integer step);
	public List<FeedbackDTO>  getByStep(Integer step);
	public List<FeedbackDTO> getPendingFeedbacks(Integer personID);
	public List<FeedbackDTO> getOldFeedbacks(Integer personID);
	public FeedbackDTO getFeedbackByID(Integer id);
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) ;
	//public List<FeedbackDTO>  getByCategoryHeadAndStep(String mail, Integer step);
	public List<FeedbackDTO> getPendingByMajorHead(Integer insID);
	public List<FeedbackDTO> getPendingHandler();
	public List<FeedbackDTO> getOldByMajorHead(Integer insID);
	public List<FeedbackDTO> getOldHandler();
	public FeedbackDTO updateStatusOfForm(FeedbackDTO dto);
	public List<FeedbackDTO> getPendingByCategoryHead(Integer id);
	public List<FeedbackDTO> getOldByCategoryHead(Integer id);
}
