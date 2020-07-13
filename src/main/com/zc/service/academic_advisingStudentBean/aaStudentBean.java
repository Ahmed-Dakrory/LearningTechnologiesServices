package main.com.zc.service.academic_advisingStudentBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;

@ManagedBean(name = "aaStudentBean")
@SessionScoped
public class aaStudentBean implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5842440236769354419L;


	/**
	 * 
	 */
	
	
 


	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade facadeSettings;


	@ManagedProperty(value = "#{aa_student_profileFacadeImpl}")
	private aa_student_profileAppServiceImpl aa_student_profileFacade;

	private aa_student_profile selectedStudent;
	private aa_instructor_students selectedInstructorForThisStudent;

	@ManagedProperty(value = "#{aa_instructor_dateFacadeImpl}")
	private aa_instructor_dateAppServiceImpl aa_instructor_dateFacade;
	


	@ManagedProperty(value = "#{aa_instructor_studentsFacadeImpl}")
	private aa_instructor_studentsAppServiceImpl instructor_studentsFacade;
	
	@ManagedProperty(value = "#{aa_instructorFacadeImpl}")
	private aa_instructorAppServiceImpl aa_instructorFacade;

	private List<aa_instructor_date> allinstructorDates;
	
	private boolean meetingSelected=false;
	@PostConstruct
	public void init() {

		
		refresh();
		
		
	}
	
	
	
	public void refresh(){
		FormsStatusDTO settingform = facadeSettings.getById(23);
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4))) // student case
			{
				selectedStudent =  aa_student_profileFacade.getByMailAndYearAndSemester(mail, String.valueOf(settingform.getYear()), settingform.getSemester().getName());

				selectedInstructorForThisStudent = instructor_studentsFacade.getByStudentIdAndYearAndSemester(selectedStudent.getId(), String.valueOf(settingform.getYear()), settingform.getSemester().getName());
				if(selectedInstructorForThisStudent.getInstructor_date()==null) {
				allinstructorDates = aa_instructor_dateFacade.getAllAvailableByInstructorIdAndYearAndSemester(selectedInstructorForThisStudent.getInstructor().getId() , String.valueOf(settingform.getYear()), settingform.getSemester().getName());
				meetingSelected = true;
				}else {
					allinstructorDates = new ArrayList<aa_instructor_date>();
					meetingSelected=false;
					
				}
				}
		}
		
	}



	
	
	public boolean isMeetingSelected() {
		return meetingSelected;
	}



	public void setMeetingSelected(boolean meetingSelected) {
		this.meetingSelected = meetingSelected;
	}



	public aa_instructor_students getSelectedInstructorForThisStudent() {
		return selectedInstructorForThisStudent;
	}



	public void setSelectedInstructorForThisStudent(aa_instructor_students selectedInstructorForThisStudent) {
		this.selectedInstructorForThisStudent = selectedInstructorForThisStudent;
	}



	public aa_instructor_dateAppServiceImpl getAa_instructor_dateFacade() {
		return aa_instructor_dateFacade;
	}



	public void setAa_instructor_dateFacade(aa_instructor_dateAppServiceImpl aa_instructor_dateFacade) {
		this.aa_instructor_dateFacade = aa_instructor_dateFacade;
	}



	public aa_instructor_studentsAppServiceImpl getInstructor_studentsFacade() {
		return instructor_studentsFacade;
	}



	public void setInstructor_studentsFacade(aa_instructor_studentsAppServiceImpl instructor_studentsFacade) {
		this.instructor_studentsFacade = instructor_studentsFacade;
	}



	public aa_instructorAppServiceImpl getAa_instructorFacade() {
		return aa_instructorFacade;
	}



	public void setAa_instructorFacade(aa_instructorAppServiceImpl aa_instructorFacade) {
		this.aa_instructorFacade = aa_instructorFacade;
	}



	public List<aa_instructor_date> getAllinstructorDates() {
		return allinstructorDates;
	}



	public void setAllinstructorDates(List<aa_instructor_date> allinstructorDates) {
		this.allinstructorDates = allinstructorDates;
	}



	public IFormsStatusFacade getFacadeSettings() {
		return facadeSettings;
	}



	public void setFacadeSettings(IFormsStatusFacade facadeSettings) {
		this.facadeSettings = facadeSettings;
	}



	public aa_student_profileAppServiceImpl getAa_student_profileFacade() {
		return aa_student_profileFacade;
	}



	public void setAa_student_profileFacade(aa_student_profileAppServiceImpl aa_student_profileFacade) {
		this.aa_student_profileFacade = aa_student_profileFacade;
	}



	public aa_student_profile getSelectedStudent() {
		return selectedStudent;
	}



	public void setSelectedStudent(aa_student_profile selectedStudent) {
		this.selectedStudent = selectedStudent;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}



