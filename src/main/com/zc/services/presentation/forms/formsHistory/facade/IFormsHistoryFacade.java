/**
 * 
 */
package main.com.zc.services.presentation.forms.formsHistory.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;

/**
 * description
 * @author heba
 * @since Mar 3, 2015
 * @lastUpdated  Mar 3, 2015
 */
public interface IFormsHistoryFacade {

	public List<FormDTO> getAllFormsHistory(String studentInfo);

	FormDTO getFormDetail(CoursePetitionDTO coursePetitionDTO);
	FormDTO getFormDetail(DropAddFormDTO addFormDTO);
	FormDTO getFormDetail(ChangeMajorDTO changeMajorDTO);
	FormDTO getFormDetail(OverloadRequestDTO overloadRequestDTO);
	FormDTO getFormDetail(CourseRepeatDTO courseRepeatDTO);
}
