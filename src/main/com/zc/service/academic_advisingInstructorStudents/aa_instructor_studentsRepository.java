/**
 * 
 */
package main.com.zc.service.academic_advisingInstructorStudents;

import java.util.List;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface aa_instructor_studentsRepository {

	public List<aa_instructor_students> getAll();
	public List<aa_instructor_students> getAllByYearAndSemester(String year,String semester);
	public List<aa_instructor_students> getByInstructorIdAndYearAndSemester(int id,String year,String semester);
	public aa_instructor_students getByStudentIdAndYearAndSemester(int id,String year,String semester);
	public aa_instructor_students addaa_instructor_students(aa_instructor_students data);
	public aa_instructor_students getById(int id);
	public boolean delete(aa_instructor_students data)throws Exception;
}
