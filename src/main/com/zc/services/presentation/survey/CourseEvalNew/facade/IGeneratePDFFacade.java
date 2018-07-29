package main.com.zc.services.presentation.survey.CourseEvalNew.facade;

import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

public interface IGeneratePDFFacade {

	public void exportPDF (CoursesDTO courseDto);
}
