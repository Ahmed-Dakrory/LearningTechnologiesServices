package main.com.zc.services.presentation.survey.CourseEvalNew.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import main.com.zc.services.presentation.survey.CourseEvalNew.facade.IGeneratePDFFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;


@ManagedBean(name="ExportPDFBean")
@ViewScoped
public class GeneratePDFBean {

	@ManagedProperty("#{IExportPDFFacade}")
	private IGeneratePDFFacade facade;
	
	@ManagedProperty("#{ICourseEvalAnswersFacade}")
	private ICourseEvalAnswersFacade ansFacade;
	
	private List<CoursesDTO> courses = new ArrayList<CoursesDTO>();
	
	@PostConstruct
	public void init() {
		
		setCourses(ansFacade.getCoursesByYearAndSemester(0, 2016));
		
		for (CoursesDTO coursesDTO : courses) {
			
			//if(coursesDTO.getId() == 55)
			
				facade.exportPDF(coursesDTO);			
		}
		
	}
	
	public IGeneratePDFFacade getFacade() {
		return facade;
	}

	public void setFacade(IGeneratePDFFacade facade) {
		this.facade = facade;
	}
	
	public ICourseEvalAnswersFacade getAnsFacade() {
		return ansFacade;
	}

	public void setAnsFacade(ICourseEvalAnswersFacade ansFacade) {
		this.ansFacade = ansFacade;
	}

	public List<CoursesDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CoursesDTO> courses) {
		this.courses = courses;
	}
}
