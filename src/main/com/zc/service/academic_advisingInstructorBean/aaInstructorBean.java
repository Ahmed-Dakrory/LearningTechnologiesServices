package main.com.zc.service.academic_advisingInstructorBean;

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
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.shared.presentation.dto.BaseDTO;

@ManagedBean(name = "aaInstructorBean")
@SessionScoped
public class aaInstructorBean implements Serializable{
	
	

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
	

	@ManagedProperty(value = "#{aa_instructor_dateFacadeImpl}")
	private aa_instructor_dateAppServiceImpl aa_instructor_dateFacade;
	

	@ManagedProperty(value = "#{aa_instructorFacadeImpl}")
	private aa_instructorAppServiceImpl aa_instructorFacade;


	@ManagedProperty(value = "#{aa_instructor_studentsFacadeImpl}")
	private aa_instructor_studentsAppServiceImpl instructor_studentsFacade;
	
	
	private aa_instructor thisInstrutorAccount;
	private aa_student_profile selectedStudent;

	private List<aa_instructor_date> allinstructorDates;
	private List<BaseDTO> semesterLst;
	private List<String> yearLst;
	
	private aa_instructor_date selectedDateData;
	private aa_instructor_students selectedInstructorForThisStudent;

	

	private String selectedYear;
	private String selectedSemester;
	private String selectedAction;
	
	@PostConstruct
	public void init() {

		allinstructorDates =new ArrayList<aa_instructor_date>();
		refresh();
		
		
	}
	
	public void getAllInstructorDates() {
		FormsStatusDTO settingform = facadeSettings.getById(23);
		allinstructorDates = aa_instructor_dateFacade.getByInstructorIdAndYearAndSemester(thisInstrutorAccount.getId(),String.valueOf(settingform.getYear()), settingform.getSemester().getName());
	}
	
	

     

	public void fillSemesterLst()
	{
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		//semesterLst.add(new BaseDTO(3,"Winter"));
		
		yearLst=new ArrayList<String>();
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(String.valueOf(i));
		}
	}
	
	public void fillYearLst(AjaxBehaviorEvent event)
	{
		 yearLst=new ArrayList<String>();
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(String.valueOf(i));
		}
	}

	
	public void refresh(){

		fillSemesterLst();
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			
				thisInstrutorAccount =  aa_instructorFacade.getByMail(mail);	
			
		}
		getAllInstructorDates();
	}

	public void deleteDateSchedule(int index) {
		try {
			aa_instructor_dateFacade.delete(allinstructorDates.get(index));
			allinstructorDates.remove(index);
			FacesMessage msg = new FacesMessage("Deleted", "Date With "+String.valueOf(allinstructorDates.get(index).getId())+" deleted");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesMessage msg = new FacesMessage("Problem", "Date With "+String.valueOf(allinstructorDates.get(index).getId())+" not deleted");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
		
	}
	
	
	public void cancelthisdate(int dateId) {
		aa_instructor_date dateForStudentAndInstructor = aa_instructor_dateFacade.getById(dateId);
		dateForStudentAndInstructor.setState(aa_instructor_date.State_Cancelled_by_Instructor);
		dateForStudentAndInstructor.setDatelastComment(new Date());

		aa_student_profileFacade.addaa_student_profile(selectedStudent);
		aa_instructor_dateFacade.addaa_instructor_date(dateForStudentAndInstructor);
		FormsStatusDTO settingform = facadeSettings.getById(23);
		selectedInstructorForThisStudent = instructor_studentsFacade.getByStudentIdAndYearAndSemester(dateForStudentAndInstructor.getStudent().getId(), String.valueOf(settingform.getYear()), settingform.getSemester().getName());
		selectedInstructorForThisStudent.setInstructor_date(null);
		instructor_studentsFacade.addaa_instructor_students(selectedInstructorForThisStudent);
		Constants.sendEmailNotificationForThisEmailWithMessage(selectedInstructorForThisStudent.getStudent().getName(), "Academic Advising Cancelation", "Your academic advising Meeting has Been Cancelled", selectedInstructorForThisStudent.getStudent().getMail());
		refresh();
		FacesMessage msg = new FacesMessage("Cancelled", "The Meeting has been cancelled");
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
	
	public void goToStudentProfileMeeting(int idOfDate) {
		selectedDateData = aa_instructor_dateFacade.getById(idOfDate);
		selectedStudent = aa_student_profileFacade.getById(selectedDateData.getStudent().getId());

		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect(ec.getRequestContextPath()
		            + "/pages/secured/academic_advising/studentProfile_Ins.xhtml");
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}
	
	public void saveDataOfthisMeeting() {
		selectedDateData.setDatelastComment(new Date());
		aa_instructor_dateFacade.addaa_instructor_date(selectedDateData);
		aa_student_profileFacade.addaa_student_profile(selectedStudent);
		Constants.sendEmailNotificationForThisEmailWithMessage(selectedDateData.getStudent().getName(), "Academic Advising Change", "Your academic advising Meeting has Been Modified", selectedDateData.getStudent().getMail());
		
		FacesMessage msg = new FacesMessage("Saved", "Meeting Data with "+String.valueOf(selectedDateData.getId())+" Saved");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
	
public void getAllListOfDates() {
		
		if(selectedAction.equalsIgnoreCase("All")) {
			allinstructorDates = aa_instructor_dateFacade.getByInstructorIdAndYearAndSemester(thisInstrutorAccount.getId(),selectedYear, selectedSemester);
		}else {

			allinstructorDates = aa_instructor_dateFacade.getByActionAndInstructorAndYearAndSemester(selectedAction,thisInstrutorAccount.getId(), selectedYear, selectedSemester);
		}
		
	}


	public void finishThisMeeting() {
//		selectedDateData.setDatelastComment(new Date());
		selectedDateData.setState(aa_instructor_date.State_Finished);
		aa_instructor_dateFacade.addaa_instructor_date(selectedDateData);
		Constants.sendEmailNotificationForThisEmailWithMessage(selectedDateData.getStudent().getName(), "Academic Advising Finishing", "Your academic advising Meeting has Been Finished You can see your meeting results", selectedDateData.getStudent().getMail());
		
		FacesMessage msg = new FacesMessage("Finished", "Meeting Data with "+String.valueOf(selectedDateData.getId())+" has been Finished");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public void addNewSchedule() {

		if(allinstructorDates==null) {
			allinstructorDates =new ArrayList<aa_instructor_date>();
		}
		FormsStatusDTO settingform = facadeSettings.getById(23);
		aa_instructor_date dateSchedule = new aa_instructor_date();
		dateSchedule.setDate(new Date());
		dateSchedule.setYear(String.valueOf(settingform.getYear()));
		dateSchedule.setSemester(settingform.getSemester().getName());
		dateSchedule.setInstructor(thisInstrutorAccount);
		allinstructorDates.add(dateSchedule);
		aa_instructor_dateFacade.addaa_instructor_date(allinstructorDates.get(allinstructorDates.size()-1));
		FacesMessage msg = new FacesMessage("Added", "Date With "+String.valueOf(allinstructorDates.get(allinstructorDates.size()-1).getId())+" Added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public void saveDateSchedule(int index) {
		aa_instructor_dateFacade.addaa_instructor_date(allinstructorDates.get(index));
		
		FacesMessage msg = new FacesMessage("Saved", "Date With "+String.valueOf(allinstructorDates.get(index).getId())+" saved");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public aa_instructor_dateAppServiceImpl getAa_instructor_dateFacade() {
		return aa_instructor_dateFacade;
	}

	public void setAa_instructor_dateFacade(aa_instructor_dateAppServiceImpl aa_instructor_dateFacade) {
		this.aa_instructor_dateFacade = aa_instructor_dateFacade;
	}

	public aa_instructorAppServiceImpl getAa_instructorFacade() {
		return aa_instructorFacade;
	}

	public void setAa_instructorFacade(aa_instructorAppServiceImpl aa_instructorFacade) {
		this.aa_instructorFacade = aa_instructorFacade;
	}

	public aa_instructor getThisInstrutorAccount() {
		return thisInstrutorAccount;
	}

	public void setThisInstrutorAccount(aa_instructor thisInstrutorAccount) {
		this.thisInstrutorAccount = thisInstrutorAccount;
	}

	public aa_student_profile getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(aa_student_profile selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public List<aa_instructor_date> getAllinstructorDates() {
		return allinstructorDates;
	}

	public void setAllinstructorDates(List<aa_instructor_date> allinstructorDates) {
		this.allinstructorDates = allinstructorDates;
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

	public aa_instructor_studentsAppServiceImpl getInstructor_studentsFacade() {
		return instructor_studentsFacade;
	}

	public void setInstructor_studentsFacade(aa_instructor_studentsAppServiceImpl instructor_studentsFacade) {
		this.instructor_studentsFacade = instructor_studentsFacade;
	}

	public aa_instructor_students getSelectedInstructorForThisStudent() {
		return selectedInstructorForThisStudent;
	}

	public void setSelectedInstructorForThisStudent(aa_instructor_students selectedInstructorForThisStudent) {
		this.selectedInstructorForThisStudent = selectedInstructorForThisStudent;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public String getSelectedSemester() {
		return selectedSemester;
	}

	public void setSelectedSemester(String selectedSemester) {
		this.selectedSemester = selectedSemester;
	}

	public String getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}

	public List<BaseDTO> getSemesterLst() {
		return semesterLst;
	}

	public void setSemesterLst(List<BaseDTO> semesterLst) {
		this.semesterLst = semesterLst;
	}

	public List<String> getYearLst() {
		return yearLst;
	}

	public void setYearLst(List<String> yearLst) {
		this.yearLst = yearLst;
	}


	
	
	
}



