/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface ICourseEvalOtherQuesAnswersRep {

	public List<CourseEvalOtherQuesAnswers> getAll();
	public List<CourseEvalOtherQuesAnswers> getByCategoryID(Integer id);
	public List<CourseEvalOtherQuesAnswers> getByQuestID(Integer id);
	public List<CourseEvalOtherQuesAnswers> getByStudentID(Integer id);
	public CourseEvalOtherQuesAnswers getById(Integer id);
	public CourseEvalOtherQuesAnswers add(CourseEvalOtherQuesAnswers form) ;
	public CourseEvalOtherQuesAnswers update(CourseEvalOtherQuesAnswers form) ;
	public boolean delete(CourseEvalOtherQuesAnswers form) ;
	public CourseEvalOtherQuesAnswers getByQuestIDAndStudentID(Integer id, Integer stID);
	
	
	
}
