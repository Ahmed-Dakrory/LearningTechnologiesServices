package main.com.zc.service.courseFileInstructorBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.RowEditEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_students;
import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_studentsAppServiceImpl;
import main.com.zc.service.academic_advisingInstructorsDates.aa_instructor_date;
import main.com.zc.service.academic_advisingInstructorsDates.aa_instructor_dateAppServiceImpl;
import main.com.zc.service.academic_advising_instructor.aa_instructor;
import main.com.zc.service.academic_advising_instructor.aa_instructorAppServiceImpl;
import main.com.zc.service.academic_advising_student_profile.aa_student_profile;
import main.com.zc.service.academic_advising_student_profile.aa_student_profileAppServiceImpl;
import main.com.zc.service.filesOfLibraries.filesOfLibraries;
import main.com.zc.service.filesOfLibraries.filesOfLibrariesAppServiceImpl;
import main.com.zc.service.instructor_courses_file.instructor_courses_file;
import main.com.zc.service.instructor_courses_file.instructor_courses_fileAppServiceImpl;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.petition.model.Attachments;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

@ManagedBean(name = "courseFileInstructorBean")
@SessionScoped
public class courseFileInstructorBean implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5842440236765469L;


	/**
	 * 
	 */
	
	
 
	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade facadeSettings;



	@ManagedProperty(value = "#{instructor_courses_fileFacadeImpl}")
	private instructor_courses_fileAppServiceImpl instructor_courses_fileFacade;
	
	

	private List<instructor_courses_file> allinstructorCoursesWithLink;
	


	
	@PostConstruct
	public void init() {

		allinstructorCoursesWithLink =new ArrayList<instructor_courses_file>();

		
		refresh();
		
		
	}
	
	public void getAllInstructorCoursesFile(String InstructorMail) {
		FormsStatusDTO settingform = facadeSettings.getById(23);
		allinstructorCoursesWithLink = instructor_courses_fileFacade.getAllByInstructorEmailAndYearAndSemester(InstructorMail,String.valueOf(settingform.getYear()), settingform.getSemester().getName());
	}
	
	
	
     

	 
	


	public void refresh(){

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			
			getAllInstructorCoursesFile(mail);
		}
	}

	public IFormsStatusFacade getFacadeSettings() {
		return facadeSettings;
	}

	public void setFacadeSettings(IFormsStatusFacade facadeSettings) {
		this.facadeSettings = facadeSettings;
	}

	public instructor_courses_fileAppServiceImpl getInstructor_courses_fileFacade() {
		return instructor_courses_fileFacade;
	}

	public void setInstructor_courses_fileFacade(instructor_courses_fileAppServiceImpl instructor_courses_fileFacade) {
		this.instructor_courses_fileFacade = instructor_courses_fileFacade;
	}

	public List<instructor_courses_file> getAllinstructorCoursesWithLink() {
		return allinstructorCoursesWithLink;
	}

	public void setAllinstructorCoursesWithLink(List<instructor_courses_file> allinstructorCoursesWithLink) {
		this.allinstructorCoursesWithLink = allinstructorCoursesWithLink;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
}



