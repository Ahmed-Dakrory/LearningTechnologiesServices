/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface ICourseEvalQuestionsRep {

	public List<CourseEvalQuestions> getAll();
	public List<CourseEvalQuestions> getByCategoryID(Integer id);
	public CourseEvalQuestions getById(Integer id);
	public CourseEvalQuestions add(CourseEvalQuestions form) ;
	public CourseEvalQuestions update(CourseEvalQuestions form) ;
	public boolean delete(CourseEvalQuestions form) ;
	
}
