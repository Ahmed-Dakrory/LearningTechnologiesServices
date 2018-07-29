package main.com.zc.services.applicationService.survey.courseEval.assembler;

import main.com.zc.services.applicationService.survey.config.assembler.SectionAssembler;
import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;

public class CourseEvalInsQuestionsAssembler {

	public CourseEvalInsQuestionsDTO toDTO(CourseEvalQuestions ques)
	{
		CourseEvalInsQuestionsDTO dto=new CourseEvalInsQuestionsDTO();
		dto.setId(ques.getId());
		///dto.setCategory(ques.getCategory());
		SectionAssembler assem=new SectionAssembler();
		dto.setSection(assem.toDTO(ques.getCategory()));
		dto.setText(ques.getText());
		return dto;
	}
	
	public CourseEvalQuestions toEntity(CourseEvalInsQuestionsDTO dto)
	{
		CourseEvalQuestions ques=new CourseEvalQuestions();
		ques.setId(dto.getId());
		//ques.setCategory(dto.getCategory());
		SectionAssembler assem=new SectionAssembler();
		ques.setCategory(assem.toEntity(dto.getSection()));
	
		ques.setText(dto.getText());
		return ques;
	}
}
