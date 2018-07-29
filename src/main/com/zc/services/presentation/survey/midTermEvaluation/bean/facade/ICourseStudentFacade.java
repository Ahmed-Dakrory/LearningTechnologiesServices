/**
 * 
 */
package main.com.zc.services.presentation.survey.midTermEvaluation.bean.facade;

import java.util.List;

import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.domain.person.model.Student;

/**
 * @author Omnya Alaa
 *
 */
public interface ICourseStudentFacade {
	public List<CourseStudent> getByCourseID(Integer id);
	public List<Student> getTotalStudentsOfCourse(Integer courseID,Integer surveyID);
}
