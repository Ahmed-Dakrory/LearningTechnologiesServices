/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.Calendar;
import java.util.List;

import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;

/**
 * @author Omnya Alaa
 *
 */
public interface ICoursePetitionRep {
	public CoursePetition add(CoursePetition coursePet);

	public boolean remove(Integer id);

	public CoursePetition update(CoursePetition CoursePetition);

	public List<CoursePetition> getAll();

	public CoursePetition getById(Integer id);
	
	public List<CoursePetition> getByPersonID(Integer id);
	
	public List<CoursePetition> getByStatus(String status);
	
	public List<CoursePetition> getByDate(Calendar submittedDate);

	public List<CoursePetition> getByCourseID(Integer id);
	
	public List<CoursePetition>getByCourseCoordinatorIDPending(Integer id);
	
	public List<CoursePetition> getByCourseCoordinatorIDOld(Integer id);
	public List<CoursePetition> getOldSummer(Integer year);
	public List<CoursePetition> getOldSpring(Integer year);
	public List<CoursePetition> getOldFall(Integer year);
	
	/**
	 * retrieve count of course petition  with step INSTRUCTOR(1)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<CoursePetition>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<CoursePetition> getDeanPendingCoursePetition(boolean forDailyMAil );
	/**
	 * retrieve count of course petition  with step DEAN(2)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<CoursePetition>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<CoursePetition> getAdmissionHeadPendingCoursePetition(boolean forDailyMAil );
	
	/**
	 * retrieve count of course petition  with step ADMISSION_HEAD(3)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<CoursePetition>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<CoursePetition> getAdmissionDeptPendingCoursePetition(boolean forDailyMAil );
	
	/**
	 * retrieve each  course Coordinator  with count of pending CoursePetition 
	 *	with step UNDER_REVIEW(0)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<PendingPetitionCountObject>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */public List<PendingPetitionCountObject> getInstructorPendingCoursePetition(boolean forDailyMAil );
	
	 public List<CoursePetition> getCoursePetitionJob( );
	 
	 /**
	  * return all performed petition if all parameter null or return petition by student name or by student id
	 * @param studentId
	 * @param studentName
	 * @return
	 * @return List<CoursePetition>
	 * @author heba
	 * @since Mar 3, 2015
	 * @lastUpdated Mar 3, 2015
	 */
	public List<CoursePetition> getCoursePetitionHistory( Integer studentId,String studentName);

	List<CoursePetition> getAllAuditing();

	public List<CoursePetition> getPendingAndAuditingAcademicPetitionsAdmissionDept();

}
