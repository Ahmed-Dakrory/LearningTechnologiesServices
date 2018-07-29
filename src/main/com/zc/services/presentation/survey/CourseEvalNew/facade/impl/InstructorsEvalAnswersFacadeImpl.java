/**
 * 
 */
package main.com.zc.services.presentation.survey.CourseEvalNew.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.survey.courseEval.services.IInstructorsEvalAnswersAppService;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.InstructorsEvalAnswersDTO;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.IInstructorsEvalAnswersFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("IInstructorsEvalAnswersFacade")
public class InstructorsEvalAnswersFacadeImpl implements IInstructorsEvalAnswersFacade{

	@Autowired
	IInstructorsEvalAnswersAppService service;
	@Override
	public List<InstructorsEvalAnswersDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByCategoryID(Integer id) {
		
		return service.getByCategoryID(id);
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByQuestID(Integer id) {
		
		return service.getByQuestID(id);
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByInsFromID(Integer id) {
	
		return service.getByInsFromID(id);
	}

	@Override
	public InstructorsEvalAnswersDTO getById(Integer id) {
		
		return service.getById(id);
	}

	@Override
	public InstructorsEvalAnswersDTO add(InstructorsEvalAnswersDTO form) {
		
		return service.add(form);
	}

	@Override
	public InstructorsEvalAnswersDTO update(InstructorsEvalAnswersDTO form) {
		
		return service.update(form);
	}

	@Override
	public boolean delete(InstructorsEvalAnswersDTO form) {
		
		return service.delete(form);
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getByFromINSAndTOINS(Integer from,
			Integer to) {
		return service.getByFromInsAndTOIns(from, to);
	}

	@Override
	public List<InstructorsEvalAnswersDTO> getAnswersByTOAndQues(
			Integer toInsID, Integer questionID) {

		return service.getByQuestIDAndInsTOID(questionID, toInsID);
	}

}
