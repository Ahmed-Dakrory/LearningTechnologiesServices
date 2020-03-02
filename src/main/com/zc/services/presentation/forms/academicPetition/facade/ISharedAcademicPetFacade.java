/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade;

import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;


/**
 * @author heba
 * @since Feb 12, 2015
 * @lastUpdated  Feb 12, 2015
 */
public interface ISharedAcademicPetFacade {
	void notifayNextStepOwner(CoursePetitionDTO dto);

	void notifayNextStepOwner(DropAddFormDTO dto);

	void notifayNextStepOwner(ChangeMajorDTO dto);

	void notifayNextStepOwner(OverloadRequestDTO dto);
	void notifayNextStepOwner(ReadmissionDTO dto);
	
	
	
	/**
	 * @author omnya.alaa
	 *
	 */
	
	void notifayNextStepOwner(CourseRepeatDTO dto);
	void notifayNextStepOwner(IncompleteGradeDTO dto);
	void notifayNextStepOwner(TAJuniorProgramDTO dto);
	void notifayNextStepOwner(ChangeConcentrationDTO dto);
	
	void notifyAt(CoursePetitionDTO detailedForm, String name) throws Exception;

	void notifyAt(DropAddFormDTO addFormDTO, String name) throws Exception;

	void notifyAt(ChangeMajorDTO changeMajorDTO, String name)throws Exception;;
	
	void notifyAt(ReadmissionDTO readmissionDTO, String name)throws Exception;;

	void notifyAt(CourseRepeatDTO courseRepeatDTO, String name)throws Exception;

	void notifyAt(OverloadRequestDTO overloadRequestDTO, String name)throws Exception;
	void notifyAt(IncompleteGradeDTO incompleteDto, String name)throws Exception;
	void notifyAt(TAJuniorProgramDTO juniorTADTO, String name)throws Exception;
	
	void notifyAt(ChangeConcentrationDTO changeConcenDTO, String name)throws Exception;;
}
