/**
 * 
 */
package main.com.zc.services.presentation.survey.CourseEvalNew.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.CourseEvalNew.dto.InstructorsEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;

/**
 * @author omnya
 *
 */
public interface IInstructorsEvalAnswersFacade {

	public List<InstructorsEvalAnswersDTO> getAll();
	public List<InstructorsEvalAnswersDTO> getByCategoryID(Integer id);
	public List<InstructorsEvalAnswersDTO> getByQuestID(Integer id);
	public List<InstructorsEvalAnswersDTO> getByInsFromID(Integer id);
	public List<InstructorsEvalAnswersDTO> getAnswersByTOAndQues(Integer toInsID, Integer questionID);
	public InstructorsEvalAnswersDTO getById(Integer id);
	public InstructorsEvalAnswersDTO add(InstructorsEvalAnswersDTO form) ;
	public InstructorsEvalAnswersDTO update(InstructorsEvalAnswersDTO form) ;
	public boolean delete(InstructorsEvalAnswersDTO form) ;
	public List<InstructorsEvalAnswersDTO> getByFromINSAndTOINS(Integer from, Integer to);
	/*
	public List<InstructorsEvalAnswersDTO> getByStudentIDAndCourseID(Integer id, Integer courseID);
	public List<CourseEvalAnswersDTO> getByStudentIDAndCourseIDAndInstructor(Integer id, Integer courseID, Integer insID);
	public List<CourseEvalAnswersDTO> getByCourseID(Integer courseID);
	*/
}
