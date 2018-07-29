/**
 * 
 */
package main.com.zc.services.applicationService.generalFeedback.service;

import java.util.List;

import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IAddFeedbackFormAppService {
public FeedbackDTO add(FeedbackDTO dto);
public FeedbackDTO update(FeedbackDTO dto);
public boolean removeFeedback(Integer id);
public FeedbackDTO getFeedbackByID(Integer id);
public List<FeedbackDTO> getfeedbacksByEmail(String email);
}
