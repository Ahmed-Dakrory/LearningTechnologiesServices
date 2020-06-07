/**
 * 
 */
package main.com.zc.service.instructor_survey_ans;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface Iinstructor_survey_ansAppService {

	public List<instructor_survey_ans> getAll();
	public List<instructor_survey_ans> getAllByCourse(int courseId);
	public List<instructor_survey_ans> getAllByCourseAndInstructor(int courseId,int instructorId);
	public List<instructor_survey_ans> getAllByCourseAndInstructorAndStudent(int courseId,int instructorId,int studentId);
	public instructor_survey_ans getAllByCourseAndInstructorAndStudentAndQuestion(int courseId,int instructorId,int studentId,int questionId);
	public instructor_survey_ans addinstructor_survey_ans(instructor_survey_ans data);
	public instructor_survey_ans getById(int id);
	public boolean delete(instructor_survey_ans data)throws Exception;
}
