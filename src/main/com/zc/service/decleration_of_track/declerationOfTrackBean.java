package main.com.zc.service.decleration_of_track;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_students;
import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_studentsAppServiceImpl;
import main.com.zc.service.academic_advisingInstructorsDates.aa_instructor_date;
import main.com.zc.service.academic_advisingInstructorsDates.aa_instructor_dateAppServiceImpl;
import main.com.zc.service.academic_advising_instructor.aa_instructorAppServiceImpl;
import main.com.zc.service.academic_advising_student_profile.aa_student_profile;
import main.com.zc.service.academic_advising_student_profile.aa_student_profileAppServiceImpl;
import main.com.zc.service.student.IStudentGetDataAppService;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;

@ManagedBean(name = "declerationOfTrackBean")
@SessionScoped
public class declerationOfTrackBean implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5842440231516516419L;


	/**
	 * 
	 */
	
	
 


	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade facadeSettings;


	@ManagedProperty(value = "#{decleration_of_trackFacadeImpl}")
	private decleration_of_trackAppServiceImpl decleration_of_trackFacade;

	private Student selectedStudent;
	private aa_instructor_students selectedInstructorForThisStudent;

	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	

	@ManagedProperty("#{IStudentGetDataAppService}")
	private IStudentGetDataAppService studentFacade;
	


	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;


	private decleration_of_track decleration_of_trackObject;
	private Integer year=0;
	private Integer semester = 0;

	private boolean trackSelected = false;
	private decleration_of_track current_decleration_of_track;
	private Integer selectedTrack = -1;
	
	
	@PostConstruct
	public void init() {

		
		refresh();
		
		
	}
	
	public void refresh(){
		trackSelected = false;
		FormsStatusDTO settingform = facadeSettings.getById(26);
		year = settingform.getYear();
		semester = settingform.getSemester().getId();
		
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4))) // student case
			{
//				studentDataFacade.getPersonByPersonMail(mail).
				
				selectedStudent = studentFacade.getStudentByPersonMail(mail);
				List<decleration_of_track> current_decleration_of_trackAll = decleration_of_trackFacade.getAllByYearAndSemestar(year, semester);
				if(current_decleration_of_trackAll!=null) {
					if(current_decleration_of_trackAll.size()>0) {
						current_decleration_of_track = current_decleration_of_trackAll.get(current_decleration_of_trackAll.size()-1);
						trackSelected = true;
					}
				}
				
				
				}
		}
		
	}
	

	
	public void addTrack() {
		decleration_of_trackObject = new decleration_of_track();
		decleration_of_trackObject.setDate(Calendar.getInstance());
		decleration_of_trackObject.setStudentId(selectedStudent);
		decleration_of_trackObject.setTrack(selectedTrack);
		decleration_of_trackObject.setSemester(semester);
		decleration_of_trackObject.setYear(year);
		decleration_of_trackFacade.adddecleration_of_track(decleration_of_trackObject);
		FacesMessage msg = new FacesMessage("Declared", "Your Track has been declared");
		trackSelected = true;
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public aa_instructor_students getSelectedInstructorForThisStudent() {
		return selectedInstructorForThisStudent;
	}



	public void setSelectedInstructorForThisStudent(aa_instructor_students selectedInstructorForThisStudent) {
		this.selectedInstructorForThisStudent = selectedInstructorForThisStudent;
	}





	public IFormsStatusFacade getFacadeSettings() {
		return facadeSettings;
	}



	public void setFacadeSettings(IFormsStatusFacade facadeSettings) {
		this.facadeSettings = facadeSettings;
	}







	public Integer getSelectedTrack() {
		return selectedTrack;
	}

	public void setSelectedTrack(Integer selectedTrack) {
		this.selectedTrack = selectedTrack;
	}

	public decleration_of_trackAppServiceImpl getDecleration_of_trackFacade() {
		return decleration_of_trackFacade;
	}

	public void setDecleration_of_trackFacade(decleration_of_trackAppServiceImpl decleration_of_trackFacade) {
		this.decleration_of_trackFacade = decleration_of_trackFacade;
	}

	public Student getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(Student selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}

	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}

	public IStudentGetDataAppService getStudentFacade() {
		return studentFacade;
	}

	public void setStudentFacade(IStudentGetDataAppService studentFacade) {
		this.studentFacade = studentFacade;
	}

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public boolean isTrackSelected() {
		return trackSelected;
	}

	public void setTrackSelected(boolean trackSelected) {
		this.trackSelected = trackSelected;
	}

	
	public decleration_of_track getCurrent_decleration_of_track() {
		return current_decleration_of_track;
	}

	public void setCurrent_decleration_of_track(decleration_of_track current_decleration_of_track) {
		this.current_decleration_of_track = current_decleration_of_track;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}



