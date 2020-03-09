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
public interface IReadmissionFormRep {


	public ReadmissionForm add(ReadmissionForm form);

	public boolean remove(Integer id);

	public ReadmissionForm update(ReadmissionForm form);

	public List<ReadmissionForm> getAll();

	public ReadmissionForm getById(Integer id);
	
	public List<ReadmissionForm> getByStudentID(Integer id);
	public List<ReadmissionForm> getPendingByPA(Integer id);
	public List<ReadmissionForm> getOldByPA(Integer id);
	public List<ReadmissionForm> getOldSummer(Integer year);
	public List<ReadmissionForm> getOldSpring(Integer year);
	public List<ReadmissionForm> getOldFall(Integer year);
	
	public List<PendingPetitionCountObject> getInstructorPendingReadmissionPetition(Integer employID, boolean forDailyMAil ) ;
	/**
	 * retrieve count of ReadmissionForm  with step INSTRUCTOR(1)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil 
	 * @return List<ReadmissionForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<ReadmissionForm> getDeanPendingReadmissionForm(boolean forDailyMAil ) ;
	/**
	 * retrieve count of ReadmissionForm  with step DEAN(2)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil 
	 * @return List<ReadmissionForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<ReadmissionForm> getAdmissionHeadPendingReadmissionForm(boolean forDailyMAil ) ;
	/**
	 * retrieve count of ReadmissionForm  with step ADMISSION_HEAD(3)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<ReadmissionForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<ReadmissionForm> getAdmissionDeptPendingReadmissionForm(boolean forDailyMAil ) ;
	/**
	 * retrieve each new major head with count of pending ReadmissionForm 
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
	public List<PendingPetitionCountObject> getDeanOfAcadPendingReadMissionPetition(boolean forDailyMAil ) ;
	public List<ReadmissionForm> getReadmissionFormJob( );
	 
	 /**
	  * return all performed petition if all parameter null or return petition by student name or by student id
	 * @param studentId
	 * @param studentName
	 * @return
	 * @return List<ReadmissionForm>
	 * @author heba
	 * @since Mar 3, 2015
	 * @lastUpdated Mar 3, 2015
	 */
	public List<ReadmissionForm> getReadmissionFormHistory( Integer studentId,String studentName);
	
}
