/**
 * 
 */
package main.com.zc.services.applicationService.dashboard;

import java.util.List;

import main.com.zc.services.domain.petition.model.ChangeConcentration;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.domain.petition.model.course_replacement_formForm;

/**
 * @author Momen
 *
 */
public interface IDashboardAppService 
{
	public List<CoursePetition> getDeanAcademicPetitionsPending();
	public List<DropAddForm> getDeanAddDropPending();
	public List<ChangeMajorForm> getDeanChangeMajorPending();
	public List<ChangeConcentration> getDeanChangeConcentrationPending();
	public List<ReadmissionForm> getDeanReadmissionPending();
	public List<course_replacement_formForm> getDeancourse_replacement_formPending();
	public Integer getDeanCourseReplacementFormsPending();
	public List<OverloadRequest> getDeanOverloadRequestPending();
	public Integer getDeanCourseRepeatPending();
	public Integer getDeanIncompleteGradePending();/** @author Omnya */
	public Integer getDeanJuniorTAPending();/** @author Omnya */
	
	public List<CoursePetition> getAdmissionHeadAcademicPetitionsPending();
	public List<DropAddForm> getAdmissionHeadAddDropPending();
	public List<ChangeMajorForm> getAdmissionHeadChangeMajorPending();
	public List<ChangeConcentration> getAdmissionHeadChangeConcentrationPending();
	public List<OverloadRequest> getAdmissionHeadOverloadRequestPending();
	public List<ReadmissionForm> getAdmissionHeadReadmissionPending();
	public Integer getAdmissionHeadCourseRepeatPending();
	public Integer getAdmissionHeadIncompleteGradePending();/** @author Omnya */
	
	public List<CoursePetition> getAdmissionDepartmentAcademicPetitionsPending();
	public List<DropAddForm> getAdmissionDepartmentAddDropPending();
	public List<ChangeMajorForm> getAdmissionDepartmentChangeMajorPending();
	public List<ChangeConcentration> getAdmissionDepartmentChangeConcentrationPending();
	public List<OverloadRequest> getAdmissionDepartmentOverloadRequestPending();
	public Integer getAdmissionDepartmentCourseRepeatPending();
	public Integer getAdmissionDepartmentIncompleteGradePending(); /** @author Omnya */
	public List<ReadmissionForm> getAdmissionDepartmentReadmissionPending();
	public List<course_replacement_formForm> getAdmissionDepartmentcourse_replacement_formPending();
	public Integer getAdmissionDepartmentCourseReplacementFormsPending();
	public Integer getAdmissionDepartmentCourseReplacementFormsAuditing();
	
	
	public List<OverloadRequest> getProvostOverloadRequestPending();
	
	public Integer getAdminAcademicPetitionsPending();
	public Integer getAdminAcademicPetitionsOld();
	public Integer getAdminAddDropPending();
	public Integer getAdminAddDropOld();
	public Integer getAdminChangeMajorPending();
	public Integer getAdminChangeMajorOld();
	public Integer getAdminOverloadRequestPending();
	public Integer getAdminOverloadRequestOld();
	public Integer getAdminCourseRepeatPending();
	public Integer getAdminCourseRepeatOld();
	public Integer getAdminIncompleteGradePending();/** @author Omnya */
	public Integer getAdminIncompleteGradeOld();/** @author Omnya */
	public Integer getAdminReadmissionPending();/** @author Omnya */
	public Integer getAdminReadmissionOld();/** @author Dakrory */
	public Integer getAdmincourse_replacement_formPending();/** @author Omnya */
	public Integer getAdmincourse_replacement_formOld();/** @author Dakrory */
	
	public Integer getInstructorAcademicPetitionsPending(String mail);
	public Integer getInstructorAddDropPending(String mail);
	public Integer getInstructorChangeMajorPending(String mail);
	public Integer getInstructorChangeConcentrationPending(String mail);
	public Integer getInstructorOverloadRequestPending(String mail);
	public Integer getInstructorCourseRepeatPending(String mail);
	public Integer getInstructorIncompleteGradePending(String mail);/** @author Omnya */
	public Integer getInstructorJuniorTAPending(String mail);/** @author Omnya */
	public Integer getInstructorReadmissionPending(Integer employId,String mail);/** @author Dakrory */
	public Integer getInstructorcourse_replacement_formPending(Integer employId,String mail);/** @author Dakrory */
	public Integer getInstructorCourseReplacementForms(String mail);
	
	public Integer getStudentAcademicPetitionsPending(Integer studentId);
	public Integer getStudentAddDropPending(Integer studentId);
	public Integer getStudentChangeMajorPending(Integer studentId);
	public Integer getStudentChangeConcentrationPending(Integer studentId);
	public Integer getStudentOverloadRequestPending(Integer studentId);
	public Integer getStudentCourseRepeatPending(Integer studentId);
	public Integer getStudentIncompleteGradePending(Integer studentId);/** @author Omnya */
	public Integer getStudentJuniorTAPending(Integer studentId);/** @author Omnya */
	public Integer getStudentReadmissionPending(Integer studentId);/** @author Dakrory */
	public Integer getStudentcourse_replacement_formPending(Integer studentId);/** @author Dakrory */
	public Integer getStudentCourseReplacementForms(Integer studentId);
	
	
	

	public Integer getInstructorHeadcourse_replacement_formPending(String mail);
	public Integer getAccreditionEngHeadCourseReplacementPending();
	public Integer getAccreditionSciHeadCourseReplacementPending();
	
}
