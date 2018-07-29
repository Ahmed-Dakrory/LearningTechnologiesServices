/**
 * 
 */
package main.com.zc.services.presentation.generalFeedback.facade;

import java.util.List;

import main.com.zc.services.domain.shared.exception.DuplicatedRowException;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IAddFeedbackFormFacade {
public FeedbackDTO addFeedbackForm(FeedbackDTO dto) throws DuplicatedRowException;
public FeedbackDTO updateFeedbackForm(FeedbackDTO dto);

public boolean removeFeedback(Integer id);
public FeedbackDTO getFeedbackByID(Integer id);
public List<FeedbackDTO> getfeedbacksByEmail(String mail);
}
