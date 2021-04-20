/**
 * 
 */
package main.com.zc.service.academic_advisingInstructorsDates;

import java.util.List;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface aa_instructor_dateRepository {

	public List<aa_instructor_date> getAll();
	public List<aa_instructor_date> getByInstructorId(int id);
	public List<aa_instructor_date> getByStudentId(int id);
	public List<aa_instructor_date> getAllAvailableByInstructorId(int id);
	public aa_instructor_date getByInstructorIdAndStudentId(int idInstructor, int idStudent);
	public List<aa_instructor_date> getByAction(String state);
	public List<aa_instructor_date> getByActionAndInstructor(String state,int idInstructor);
	public aa_instructor_date addaa_instructor_date(aa_instructor_date data);
	public aa_instructor_date getById(int id);
	public boolean delete(aa_instructor_date data)throws Exception;
}
