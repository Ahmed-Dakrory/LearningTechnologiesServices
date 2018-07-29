/**
 * 
 */
package main.com.zc.services.applicationService.generalFeedback.service;

import java.util.List;

import main.com.zc.services.domain.feedback.model.FeedbackCategory;
import main.com.zc.services.presentation.generalFeedback.dto.CategoryDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface ICategoriesAppService {

	public List<CategoryDTO> getAll();
}
