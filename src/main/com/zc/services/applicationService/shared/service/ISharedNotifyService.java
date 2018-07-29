/**
 * 
 */
package main.com.zc.services.applicationService.shared.service;

import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.ChangeConcentration;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IncompleteGrade;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.domain.petition.model.TAJuniorProgram;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;



/**
 * description
 * @author heba
 * @since Feb 12, 2015
 * @lastUpdated  Feb 12, 2015
 */
public interface ISharedNotifyService {

	void notifayNextStepOwner(CoursePetitionDTO dto);

	void notifayNextStepOwner(DropAddFormDTO dto);

	void notifayNextStepOwner(ChangeMajorDTO dto);

	void notifayNextStepOwner(OverloadRequestDTO dto);

	/**
	 * @author omnya.alaa
	 *
	 */
	void notifayNextStepOwner(CourseRepeatDTO dto);
	
	void notifayNextStepOwner(TAJuniorProgramDTO dto);
	
	void notifayNextStepOwner(ChangeConcentrationDTO dto);
	
	void notifayAtDate(CoursePetition coursePetition, Employee instructor) throws Exception;
	
	void notifayAtDate(TAJuniorProgram coursePetition, Employee instructor) throws Exception;
	void notifayAtDate(ChangeConcentration petition, Employee instructor) throws Exception;
	void notifyAt(CoursePetitionDTO detailedForm, String name) throws Exception;
	void notifyAt(TAJuniorProgramDTO detailedForm, String name) throws Exception;
	void notifayAtDate(ChangeConcentrationDTO petition, String name) throws Exception;
	
	void updateStatus(String formType, Integer formId, boolean status);

	void notifayAtDate(CoursePetition coursePetition);

	void notifayAtDate(TAJuniorProgram coursePetition);
	
	void notifayAtDate(DropAddForm addForm);

	void notifayAtDate(ChangeMajorForm changeMajorForm);

	void notifayAtDate(OverloadRequest overloadRequest);

	void notifayAtDate(RepeatCourseForm repeatCourseForm);
	void notifayAtDate(IncompleteGrade incompleteGrade);
	void notifayAtDate(ChangeConcentration changeOfConcen);
	

//	void notifayAtDate(DropAddForm dropAddForm, Instructor instructor)
//			throws Exception;
//
//	void notifayAtDate(ChangeMajorForm changeMajorForm, Instructor instructor)
//			throws Exception;
//
//	void notifayAtDate(OverloadRequest overloadRequest, Instructor instructor)
//			throws Exception;
//
//	void notifayAtDate(RepeatCourseForm repeatCourseForm, Instructor instructor)
//			throws Exception;

	void notifyAt(DropAddFormDTO detailedForm, String name) throws Exception;

	void notifyAt(ChangeMajorDTO detailedForm, String name) throws Exception;

	void notifyAt(OverloadRequestDTO detailedForm, String name)
			throws Exception;

	void notifyAt(ChangeConcentrationDTO detailedForm, String name)
			throws Exception;

	void notifyAt(CourseRepeatDTO detailedForm, String name) throws Exception;

	void removeJobFromScheduler(String jobName, Integer Id);
	/**
	 * @author omnya.alaa
	 *
	 */
	void notifayNextStepOwner(IncompleteGradeDTO dto);

	/**
	 * @author omnya.alaa
	 *
	 */
	
	
	void notifyAt(IncompleteGradeDTO incompleteGrade, String name) throws Exception;


}
