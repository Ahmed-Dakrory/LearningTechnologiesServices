/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.services;

import java.util.List;

import main.com.zc.services.domain.courseEval.model.InstructorsEvalAnswers;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.InstructorsEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;

/**
 * @author omnya
 *
 */
public interface IInstructorsEvalAnswersAppService {

	public List<InstructorsEvalAnswersDTO> getAll();
	public List<InstructorsEvalAnswersDTO> getByCategoryID(Integer id);
	public List<InstructorsEvalAnswersDTO> getByQuestID(Integer id);
	public List<InstructorsEvalAnswersDTO> getByInsFromID(Integer id);
	public List<InstructorsEvalAnswersDTO> getByInsTOID(Integer id);
	public List<InstructorsEvalAnswersDTO> getByFromInsAndTOIns(Integer from,Integer to);
	InstructorsEvalAnswersDTO getByFromInsAndTOInsAndQuesID(Integer from, Integer to, Integer quesID);
	public List<InstructorsEvalAnswersDTO> getByQuestIDAndInsFromID(Integer ques, Integer from);
	public List<InstructorsEvalAnswersDTO> getByQuestIDAndInsTOID(Integer ques, Integer to);
	public InstructorsEvalAnswersDTO getById(Integer id);
	public InstructorsEvalAnswersDTO add(InstructorsEvalAnswersDTO form) ;
	public InstructorsEvalAnswersDTO update(InstructorsEvalAnswersDTO form) ;
	public boolean delete(InstructorsEvalAnswersDTO form) ;
	
}
