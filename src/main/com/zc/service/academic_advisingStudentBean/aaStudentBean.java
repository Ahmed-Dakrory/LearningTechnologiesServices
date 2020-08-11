package main.com.zc.service.academic_advisingStudentBean;

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
import main.com.zc.services.domain.shared.Constants;
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
	private aa_instructor_date selectedDateData;
	private boolean meetingSelected=false;
	
	@PostConstruct
	public void init() {

		
		refresh();
		
		
	}
	
	
	public void cancelthisdate(int dateId) {
		aa_instructor_date dateForStudentAndInstructor = aa_instructor_dateFacade.getById(dateId);
		dateForStudentAndInstructor.setState(aa_instructor_date.State_Cancelled_by_Student);
		selectedStudent.setDateStudentLastAction(new Date());
		aa_student_profileFacade.addaa_student_profile(selectedStudent);
		aa_instructor_dateFacade.addaa_instructor_date(dateForStudentAndInstructor);
		FormsStatusDTO settingform = facadeSettings.getById(23);
		selectedInstructorForThisStudent = instructor_studentsFacade.getByStudentIdAndYearAndSemester(selectedStudent.getId(), String.valueOf(settingform.getYear()), settingform.getSemester().getName());
		selectedInstructorForThisStudent.setInstructor_date(null);
		instructor_studentsFacade.addaa_instructor_students(selectedInstructorForThisStudent);
		Constants.sendEmailNotificationForThisEmailWithMessage(dateForStudentAndInstructor.getInstructor().getName(), "Academic Advising Meeting Cancelled", "A Student has cancelled the meeting with date "+String.valueOf(dateForStudentAndInstructor.getDate()), dateForStudentAndInstructor.getInstructor().getMail());
		
		refresh();
		FacesMessage msg = new FacesMessage("Cancelled", "Your Meeting has been cancelled");
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
	public void selectThisdate(int dateId) {
		aa_instructor_date dateForStudentAndInstructor = aa_instructor_dateFacade.getById(dateId);
		dateForStudentAndInstructor.setState(aa_instructor_date.State_Reserved);
		selectedStudent.setDateStudentLastAction(new Date());

		aa_student_profileFacade.addaa_student_profile(selectedStudent);
		dateForStudentAndInstructor.setStudent(selectedStudent);
		aa_instructor_dateFacade.addaa_instructor_date(dateForStudentAndInstructor);
		FormsStatusDTO settingform = facadeSettings.getById(23);
		selectedInstructorForThisStudent = instructor_studentsFacade.getByStudentIdAndYearAndSemester(selectedStudent.getId(), String.valueOf(settingform.getYear()), settingform.getSemester().getName());
		selectedInstructorForThisStudent.setInstructor_date(dateForStudentAndInstructor);
		instructor_studentsFacade.addaa_instructor_students(selectedInstructorForThisStudent);
		Constants.sendEmailNotificationForThisEmailWithMessage(dateForStudentAndInstructor.getInstructor().getName(), "Academic Advising Meeting Selected", "A Student has select meeting with date "+String.valueOf(dateForStudentAndInstructor.getDate()), dateForStudentAndInstructor.getInstructor().getMail());
		
		refresh();
		FacesMessage msg = new FacesMessage("Reserved", "Your Meeting has been reserved");
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public void refresh(){
		meetingSelected = false;
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
					allinstructorDates = aa_instructor_dateFacade.getByStudentIdAndYearAndSemester(selectedInstructorForThisStudent.getStudent().getId() , String.valueOf(settingform.getYear()), settingform.getSemester().getName());
					List<aa_instructor_date> allNullInstructorDates = aa_instructor_dateFacade.getAllAvailableByInstructorIdAndYearAndSemester(selectedInstructorForThisStudent.getInstructor().getId() , String.valueOf(settingform.getYear()), settingform.getSemester().getName());
					if(allNullInstructorDates!=null) {
						if(allNullInstructorDates.size()>0) {
							if(allinstructorDates==null) {
								allinstructorDates =new ArrayList<aa_instructor_date>();
							}
							allinstructorDates.addAll(allNullInstructorDates);
						}
					}
				 
				}else {
					allinstructorDates = new ArrayList<aa_instructor_date>();
					
					allinstructorDates = aa_instructor_dateFacade.getByStudentIdAndYearAndSemester(selectedInstructorForThisStudent.getStudent().getId() , String.valueOf(settingform.getYear()), settingform.getSemester().getName());
					
					meetingSelected=true;
					
				}
				}
		}
		
	}
	public void goToStudentProfileMeeting(int idOfDate) {
//		System.out.print(idOfDate);
		selectedDateData = aa_instructor_dateFacade.getById(idOfDate);
		selectedStudent = aa_student_profileFacade.getById(selectedDateData.getStudent().getId());

		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect(ec.getRequestContextPath()
		            + "/pages/secured/academic_advising/studentProfile.xhtml");
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}

	//1 minute = 60 seconds
	//1 hour = 60 x 60 = 3600
	//1 day = 3600 x 24 = 86400
	public boolean ableToCancel(Date dateMeeting){
		
		
		Date endDate =new Date();
	    //milliseconds
	    long different = dateMeeting.getTime() - endDate.getTime();


	    long secondsInMilli = 1000;
	    long minutesInMilli = secondsInMilli * 60;
	    long hoursInMilli = minutesInMilli * 60;

	    //long elapsedDays = different / daysInMilli;
	    //different = different % daysInMilli;

	    long elapsedHours = different / hoursInMilli;

//	    System.out.println(elapsedHours);
	    if((elapsedHours)>24) {
			return true;
		}else {
			return false;
		}
	    

	}
	
	
	public void saveDataOfthisMeeting() {
		selectedStudent.setDateStudentLastAction(new Date());

		aa_student_profileFacade.addaa_student_profile(selectedStudent);
		aa_instructor_dateFacade.addaa_instructor_date(selectedDateData);
		Constants.sendEmailNotificationForThisEmailWithMessage(selectedDateData.getInstructor().getName(), "Academic Advising Change", "Your academic advising Meeting has Been Modified", selectedDateData.getInstructor().getMail());
		
		FacesMessage msg = new FacesMessage("Saved", "Meeting Data with "+String.valueOf(selectedDateData.getId())+" Saved");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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


	public aa_instructor_date getSelectedDateData() {
		return selectedDateData;
	}


	public void setSelectedDateData(aa_instructor_date selectedDateData) {
		this.selectedDateData = selectedDateData;
	}
	
	
}



