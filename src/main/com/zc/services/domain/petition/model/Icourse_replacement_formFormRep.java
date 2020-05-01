/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;

/**
 * @author omnya.alaa
 *
 */
public interface Icourse_replacement_formFormRep {


	public course_replacement_formForm add(course_replacement_formForm form);

	public boolean remove(Integer id);

	public course_replacement_formForm update(course_replacement_formForm form);

	public List<course_replacement_formForm> getAll();

	public course_replacement_formForm getById(Integer id);
	
	public List<course_replacement_formForm> getByStudentID(Integer id);
	public List<course_replacement_formForm> getPendingByPA(Integer id);
	public List<course_replacement_formForm> getOldByPA(Integer id);
	public List<course_replacement_formForm> getOldSummer(Integer year);
	public List<course_replacement_formForm> getOldSpring(Integer year);
	public List<course_replacement_formForm> getOldFall(Integer year);
	
	public List<PendingPetitionCountObject> getInstructorPendingcourse_replacement_formPetition(boolean forDailyMAil) ;
	public List<course_replacement_formForm> getAccredEngPendingcourse_replacement_formPetition(boolean forDailyMAil) ;
	public List<course_replacement_formForm> getAccredSciPendingcourse_replacement_formPetition(boolean forDailyMAil) ;
	/**
	 * retrieve count of course_replacement_formForm  with step INSTRUCTOR(1)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil 
	 * @return List<course_replacement_formForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<course_replacement_formForm> getDeanPendingcourse_replacement_formForm(boolean forDailyMAil ) ;
	/**
	 * retrieve count of course_replacement_formForm  with step DEAN(2)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil 
	 * @return List<course_replacement_formForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<course_replacement_formForm> getAdmissionHeadPendingcourse_replacement_formForm(boolean forDailyMAil ) ;
	/**
	 * retrieve count of course_replacement_formForm  with step ADMISSION_HEAD(3)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<course_replacement_formForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<course_replacement_formForm> getAdmissionDeptPendingcourse_replacement_formForm(boolean forDailyMAil ) ;
	/**
	 * retrieve each new major head with count of pending course_replacement_formForm 
	 *	with step UNDER_REVIEW(0)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<PendingPetitionCountObject>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<PendingPetitionCountObject> getDeanOfAcadPendingcourse_replacement_formPetition(boolean forDailyMAil ) ;
	public List<course_replacement_formForm> getcourse_replacement_formFormJob( );
	 
	 /**
	  * return all performed petition if all parameter null or return petition by student name or by student id
	 * @param studentId
	 * @param studentName
	 * @return
	 * @return List<course_replacement_formForm>
	 * @author heba
	 * @since Mar 3, 2015
	 * @lastUpdated Mar 3, 2015
	 */
	public List<course_replacement_formForm> getcourse_replacement_formFormHistory( Integer studentId,String studentName);
	
}
