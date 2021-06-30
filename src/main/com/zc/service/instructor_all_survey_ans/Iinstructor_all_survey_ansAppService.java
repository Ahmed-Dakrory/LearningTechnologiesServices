/**
 * 
 */
package main.com.zc.service.instructor_all_survey_ans;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface Iinstructor_all_survey_ansAppService {

	public List<instructor_all_survey_ans> getAll();
	public List<instructor_all_survey_ans> getAllByCourse(int courseId);
	public List<instructor_all_survey_ans> getAllByCourseAndInstructor(int courseId,int instructorId);
	public List<instructor_all_survey_ans> getAllForAllInstructorForYearAndSemester(int semester,int year);
	public List<instructor_all_survey_ans> getAllByInstructorForYearAndSemester(int semester,int year,int instructorId);
	public List<instructor_all_survey_ans> getAllByCourseAndInstructorAndYearAndSemester(int courseId,int instructorId,int year,int semester);
	public List<instructor_all_survey_ans> getAllByCourseAndInstructorAndYearAndSemesterAndCategory(int courseId,int instructorId,int year,int semester,int category);
	public List<instructor_all_survey_ans> getAllByCourseAndInstructorAndYearAndSemesterAndType(int courseId,int instructorId,int year,int semester,int type);
	public List<instructor_all_survey_ans> getAllByInstructorForYearAndSemesterGroupbyCourseId(int instructorId,int year,int semester);
	public List<instructor_all_survey_ans> getAllByInstructorForYearAndSemesterandCategory(int semester,int year,int instructorId,int category);
	public List<instructor_all_survey_ans> getAllByCourseAndInstructorAndStudent(int courseId,int instructorId,int studentId);
	public instructor_all_survey_ans getAllByCourseAndInstructorAndStudentAndQuestion(int courseId,int instructorId,int studentId,int questionId);
	public instructor_all_survey_ans addinstructor_all_survey_ans(instructor_all_survey_ans data);
	public instructor_all_survey_ans getById(int id);
	public boolean delete(instructor_all_survey_ans data)throws Exception;
}
