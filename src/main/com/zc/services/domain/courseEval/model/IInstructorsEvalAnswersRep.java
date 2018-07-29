/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IInstructorsEvalAnswersRep {

	public List<InstructorsEvalAnswers> getAll();
	public List<InstructorsEvalAnswers> getByCategoryID(Integer id);
	public List<InstructorsEvalAnswers> getByQuestID(Integer id);
	public List<InstructorsEvalAnswers> getByInsFromID(Integer id);
	public List<InstructorsEvalAnswers> getByInsTOID(Integer id);
	public List<InstructorsEvalAnswers> getByFromInsAndTOIns(Integer from,Integer to);
	InstructorsEvalAnswers getByFromInsAndTOInsAndQuesID(Integer from, Integer to, Integer quesID);
	public List<InstructorsEvalAnswers> getByQuestIDAndInsFromID(Integer ques, Integer from);
	public List<InstructorsEvalAnswers> getByQuestIDAndInsTOID(Integer ques, Integer to);
	public InstructorsEvalAnswers getById(Integer id);
	public InstructorsEvalAnswers add(InstructorsEvalAnswers form) ;
	public InstructorsEvalAnswers update(InstructorsEvalAnswers form) ;
	public boolean delete(InstructorsEvalAnswers form) ;

}
