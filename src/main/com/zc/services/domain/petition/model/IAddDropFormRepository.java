/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;

/**
 * @author omnya
 *
 */
public interface IAddDropFormRepository {

	public DropAddForm add(DropAddForm form);

	public boolean remove(Integer id);

	public DropAddForm update(DropAddForm form);

	public List<DropAddForm> getAll();

	public DropAddForm getById(Integer id);
	
	public List<DropAddForm>getByStudentID(Integer id);
	
	/*public List<DropAddForm>getByCourseCoordinatorID(Integer id);
	
	public List<DropAddForm>getByCourseID(Integer id);*/
	public List<DropAddForm> getPendingByPA(Integer id);
	public List<DropAddForm> getOldByPA(Integer id);
	public List<DropAddForm> getByDropppedCourseIns(Integer id);
	public List<DropAddForm> getPendingDean();
	public List<DropAddForm> getOldSummer(Integer year);
	public List<DropAddForm> getOldSpring(Integer year);
	public List<DropAddForm> getOldFall(Integer year);
	
	/**
	 * retrieve count of DropAddForm  with step INSTRUCTOR(1)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<DropAddForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<DropAddForm> getDeanPendingDropAddForm(boolean forDailyMAil ) ;
	/**
	 * retrieve count of DropAddForm  with step DEAN(2)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<DropAddForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<DropAddForm> getAdmissionHeadPendingDropAddForm(boolean forDailyMAil ) ;
	/**
	 * retrieve count of DropAddForm  with step ADMISSION_HEAD(3)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<DropAddForm>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	
	public List<DropAddForm> getAdmissionDeptPendingDropAddForm(boolean forDailyMAil ) ;

	/**
	 * retrieve each  major head with count of pending DropAddForm 
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
	public List<PendingPetitionCountObject> getInstructorPendingDropAddPetition(boolean forDailyMAil ) ;
	
	public List<DropAddForm> getDropAddFormJob( );

	 /**
	  * return all performed petition if all parameter null or return petition by student name or by student id
	 * @param studentId
	 * @param studentName
	 * @return
	 * @return List<DropAddForm>
	 * @author heba
	 * @since Mar 3, 2015
	 * @lastUpdated Mar 3, 2015
	 */
	public List<DropAddForm> getDropAddFormHistory( Integer studentId,String studentName);
	public List<DropAddForm> getInstructorCountPending(Integer instructorID);
}
