/**
 * 
 */
package main.com.zc.services.presentation.survey.midTermEvaluation.bean.facade.impl;

import java.util.List;

import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.domain.configurations.model.ICourseStudentRep;
import main.com.zc.services.domain.courseEval.model.ICourseEvalAnswersRep;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.survey.midTermEvaluation.bean.facade.ICourseStudentFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service("ICourseStudentFacade")
public class CourseStudentFacadeImpl implements ICourseStudentFacade{

	@Autowired
	ICourseStudentRep courseStudentRep;
	
	@Autowired
	ICourseEvalAnswersRep rep;
	@Override
	public List<CourseStudent> getByCourseID(Integer id) {
		// TODO Auto-generated method stub
		return courseStudentRep.getByCourseID(id);
	}
	@Override
	public List<Student> getTotalStudentsOfCourse(Integer courseID,Integer surveyID) {
		// TODO Auto-generated method stub
		return rep.getTotalStudentsOfCourse( courseID,surveyID);
	}

}
