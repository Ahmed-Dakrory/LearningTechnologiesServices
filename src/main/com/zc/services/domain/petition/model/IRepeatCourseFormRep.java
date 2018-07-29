/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IRepeatCourseFormRep {

	public RepeatCourseForm add(RepeatCourseForm form);

	public boolean remove(Integer id);

	public RepeatCourseForm update(RepeatCourseForm form);

	public List<RepeatCourseForm> getAll();

	public RepeatCourseForm getById(Integer id);
	
	public List<RepeatCourseForm> getByStudentID(Integer id);
	
	public List<RepeatCourseForm> getPendinByPA(Integer id);
	public List<RepeatCourseForm> getOldByPA(Integer id);
	
	public List<RepeatCourseForm> getOldSummer(Integer year);
	public List<RepeatCourseForm> getOldSpring(Integer year);
	public List<RepeatCourseForm> getOldFall(Integer year);
	
	public List<RepeatCourseForm> getRepeatCourseFormJob( );
	
	 /**
	  * return all performed petition if all parameter null or return petition by student name or by student id
	 * @param studentId
	 * @param studentName
	 * @return
	 * @return List<RepeatCourseForm>
	 * @author heba
	 * @since Mar 3, 2015
	 * @lastUpdated Mar 3, 2015
	 */
	public List<RepeatCourseForm> getRepeatCourseFormHistory( Integer studentId,String studentName);
	
	List<RepeatCourseForm> getAllAuditing();

	List<RepeatCourseForm> getPendingAdmissionDept();

	List<RepeatCourseForm> getPendingAuditingAdmissionDept();

}
