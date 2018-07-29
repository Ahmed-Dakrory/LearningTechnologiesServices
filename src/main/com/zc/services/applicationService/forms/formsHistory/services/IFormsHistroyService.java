/**
 * 
 */
package main.com.zc.services.applicationService.forms.formsHistory.services;

import java.io.Serializable;
import java.util.List;

import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;


public interface IFormsHistroyService extends Serializable{
	public List<FormDTO> getFormsHistory(String studentInfo);

	public FormDTO getFormDetail(CoursePetitionDTO coursePetitionDTO);
	public FormDTO getFormDetail(DropAddFormDTO addFormDTO);
	public FormDTO getFormDetail(ChangeMajorDTO changeMajorDTO);
	public	FormDTO getFormDetail(OverloadRequestDTO overloadRequestDTO);
	public FormDTO getFormDetail(CourseRepeatDTO courseRepeatDTO);

}
