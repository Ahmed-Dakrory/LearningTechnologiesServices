package main.com.zc.services.presentation.survey.CourseEvalNew.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalAnswersAppService;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.IGeneratePDFFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

@Service("IExportPDFFacade")
public class GeneratePDFFacade implements IGeneratePDFFacade {

	@Autowired
	ICourseEvalAnswersAppService answersAppService;
	
	@Override
	public void exportPDF(CoursesDTO courseDto) {
		
		answersAppService.exportResultsToPDF(courseDto);
	}
	
}
