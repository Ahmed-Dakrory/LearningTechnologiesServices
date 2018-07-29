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
public interface IOverloadRequestRep {

	public OverloadRequest add(OverloadRequest form);

	public boolean remove(Integer id);

	public OverloadRequest update(OverloadRequest form);

	public List<OverloadRequest> getAll();

	public OverloadRequest getById(Integer id);
	
	public List<OverloadRequest> getByStudentID(Integer id);
	
	public List<OverloadRequest> getByCourseCoordniator(Integer id);

	public List<OverloadRequest> getPendingByPA(Integer id);
	public List<OverloadRequest> getOldByPA(Integer id);
	public List<OverloadRequest> getOldSummer(Integer year);
	public List<OverloadRequest> getOldSpring(Integer year);
	public List<OverloadRequest> getOldFall(Integer year);
	
	/**
	 * retrieve count of OverloadRequest  with step INSTRUCTOR(1)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<OverloadRequest>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<OverloadRequest> getDeanPendingOverloadRequest(boolean forDailyMAil ) ;
	/**
	 * retrieve count of OverloadRequest  with 
	 * step DEAN(2) And next not provost 
	 * Or step PROVOST(5) 
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<OverloadRequest>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<OverloadRequest> getAdmissionHeadPendingOverloadRequest(boolean forDailyMAil ) ;
	/**
	 * retrieve count of OverloadRequestn  with step ADMISSION_HEAD(3)
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<OverloadRequest>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<OverloadRequest> getAdmissionDeptPendingOverloadRequest(boolean forDailyMAil ) ;
	/**
	 * retrieve count of OverloadRequest  with 
	 * step DEAN(2) And next is provost 
	 * and performed =0 ---running on flow
	 * and insNotifyDate = null -- not has specific notification date
	 * forDailyMAil false if need for sending mail
	 * @param forDailyMAil
	 * @return List<OverloadRequest>
	 * @author heba
	 * @since Feb 11, 2015
	 * @lastUpdated Feb 11, 2015
	 */
	public List<OverloadRequest> getProvostPendingOverloadRequest(boolean forDailyMAil ) ;
	
	/**
	 * retrieve each  major head with count of pending OverloadRequest 
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
	public List<PendingPetitionCountObject> getInstructorPendingOverLoadPetition(boolean forDailyMAil ) ;
	 
	public List<OverloadRequest> getOverloadRequestJob( );
	 /**
	  * return all performed petition if all parameter null orDropAddForm return petition by student name or by student id
	 * @param studentId
	 * @param studentName
	 * @return
	 * @return List<OverloadRequest>
	 * @author heba
	 * @since Mar 3, 2015
	 * @lastUpdated Mar 3, 2015
	 */
	public List<OverloadRequest> getOverloadRequestHistory( Integer studentId,String studentName);

	List<OverloadRequest> getAllAuditing();


	public List<OverloadRequest> getPendingAndAuditingOverloadRequestAdmissionDept();
	
}
