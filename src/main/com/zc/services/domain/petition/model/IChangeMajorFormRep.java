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
public interface IChangeMajorFormRep {


	public ChangeMajorForm add(ChangeMajorForm form);

	public boolean remove(Integer id);

	public ChangeMajorForm update(ChangeMajorForm form);

	public List<ChangeMajorForm> getAll();

	public ChangeMajorForm getById(Integer id);
	
	public List<ChangeMajorForm> getByStudentID(Integer id);
	public List<ChangeMajorForm> getPendingByPA(Integer id);
	public List<ChangeMajorForm> getOldByPA(Integer id);
	public List<ChangeMajorForm> getOldSummer(Integer year);
	public List<ChangeMajorForm> getOldSpring(Integer year);
	public List<ChangeMajorForm> getOldFall(Integer year);
	/**
	 * retrieve count of ChangeMajorForm  with step INSTRUCTOR(1)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil 
	 * @return List<ChangeMajorForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<ChangeMajorForm> getDeanPendingChangeMajorForm(boolean forDailyMAil ) ;
	/**
	 * retrieve count of ChangeMajorForm  with step DEAN(2)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil 
	 * @return List<ChangeMajorForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<ChangeMajorForm> getAdmissionHeadPendingChangeMajorForm(boolean forDailyMAil ) ;
	/**
	 * retrieve count of ChangeMajorForm  with step ADMISSION_HEAD(3)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<ChangeMajorForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<ChangeMajorForm> getAdmissionDeptPendingChangeMajorForm(boolean forDailyMAil ) ;
	/**
	 * retrieve each new major head with count of pending ChangeMajorForm 
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
	public List<PendingPetitionCountObject> getInstructorPendingChangMajorPetition(boolean forDailyMAil ) ;
	public List<ChangeMajorForm> getChangeMajorFormJob( );
	 
	 /**
	  * return all performed petition if all parameter null or return petition by student name or by student id
	 * @param studentId
	 * @param studentName
	 * @return
	 * @return List<ChangeMajorForm>
	 * @author heba
	 * @since Mar 3, 2015
	 * @lastUpdated Mar 3, 2015
	 */
	public List<ChangeMajorForm> getChangeMajorFormHistory( Integer studentId,String studentName);
	
}
