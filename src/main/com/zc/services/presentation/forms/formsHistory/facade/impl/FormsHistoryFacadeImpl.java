
package main.com.zc.services.presentation.forms.formsHistory.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.forms.formsHistory.services.IFormsHistroyService;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description
 * @author heba
 * @since Mar 3, 2015
 * @lastUpdated  Mar 3, 2015
 */
@Service("FormsHistoryFacadeImpl")
public class FormsHistoryFacadeImpl implements IFormsHistoryFacade {

	@Autowired
	IFormsHistroyService formsHistroyService;
	@Override
	public List<FormDTO> getAllFormsHistory(String studentInfo) {
		return formsHistroyService.getFormsHistory(studentInfo);
	}
	
	@Override
	public FormDTO getFormDetail(CoursePetitionDTO coursePetitionDTO) {
		return formsHistroyService.getFormDetail(coursePetitionDTO);
	}

	@Override
	public FormDTO getFormDetail(DropAddFormDTO addFormDTO) {
		return formsHistroyService.getFormDetail(addFormDTO);
		}

	@Override
	public FormDTO getFormDetail(ChangeMajorDTO changeMajorDTO) {
		return formsHistroyService.getFormDetail(changeMajorDTO);
		}

	@Override
	public FormDTO getFormDetail(OverloadRequestDTO overloadRequestDTO) {
		return formsHistroyService.getFormDetail(overloadRequestDTO);
		}

	@Override
	public FormDTO getFormDetail(CourseRepeatDTO courseRepeatDTO) {
		return formsHistroyService.getFormDetail(courseRepeatDTO);
		}
	

}
