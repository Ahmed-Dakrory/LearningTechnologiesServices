package main.com.zc.service.academic_advisingRegistrarBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.SelectEvent;

import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_students;
import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_studentsAppServiceImpl;
import main.com.zc.service.academic_advisingInstructorsDates.aa_instructor_date;
import main.com.zc.service.academic_advisingInstructorsDates.aa_instructor_dateAppServiceImpl;
import main.com.zc.service.academic_advising_student_profile.aa_student_profile;
import main.com.zc.service.academic_advising_student_profile.aa_student_profileAppServiceImpl;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.shared.presentation.dto.BaseDTO;

@ManagedBean(name = "aaRegistrarBean")
@SessionScoped
public class aaRegistrarBean implements Serializable{
	
	

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


	@ManagedProperty(value = "#{aa_instructor_studentsFacadeImpl}")
	private aa_instructor_studentsAppServiceImpl instructor_studentsFacade;
	
	private aa_student_profile selectedStudent;
	
	private List<aa_student_profile> allStudentSelected;
	

	@ManagedProperty(value = "#{aa_instructor_dateFacadeImpl}")
	private aa_instructor_dateAppServiceImpl aa_instructor_dateFacade;
	
	private String selectedYear;
	private String selectedSemester;
	private String selectedAction;
	

	private List<aa_instructor_date> allinstructorDates;
	private List<BaseDTO> semesterLst;
	private List<String> yearLst;
	

	private aa_instructor_date selectedDateData;
	private aa_instructor_students selectedInstructorForThisStudent;
	

	private boolean meetingSelected=false;
	
	
	@PostConstruct
	public void init() {
		allinstructorDates=new ArrayList<aa_instructor_date>();
		allStudentSelected =new ArrayList<aa_student_profile>();
		refresh();
		
		
	}
	
	public void goToStudentDates(int idOfDate) {
		System.out.print(idOfDate);
		meetingSelected = false;
		FormsStatusDTO settingform = facadeSettings.getById(23);
		
		selectedStudent = aa_student_profileFacade.getById(idOfDate);

		
		selectedInstructorForThisStudent = instructor_studentsFacade.getByStudentIdAndYearAndSemester(selectedStudent.getId(), String.valueOf(settingform.getYear()), settingform.getSemester().getName());
		
			/**
			 * THIS CASE WHEN STUDENT NOT RESERVE A SLOT
			 * 
			 * Get all Available Dates for the student
			 */
			
			// This is the last dates reserved
			allinstructorDates = aa_instructor_dateFacade.getByStudentIdAndYearAndSemester(selectedInstructorForThisStudent.getStudent().getId() , String.valueOf(settingform.getYear()), settingform.getSemester().getName());
			
			
		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect(ec.getRequestContextPath()
		            + "/pages/secured/academic_advising/studentDates.xhtml");
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}
	
	public void goToStudentProfileMeeting(int idOfDate) {
		System.out.print(idOfDate);
		selectedDateData = aa_instructor_dateFacade.getById(idOfDate);
		selectedStudent = aa_student_profileFacade.getById(selectedDateData.getStudent().getId());

		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect(ec.getRequestContextPath()
		            + "/pages/secured/academic_advising/studentProfile_Reg.xhtml");
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}
	
	public void getAllListOfDates() {
		
		if(selectedAction.equalsIgnoreCase("All")) {
			allinstructorDates = aa_instructor_dateFacade.getAllByYearAndSemester(selectedYear, selectedSemester);
		}else {
			allinstructorDates = aa_instructor_dateFacade.getByActionAndYearAndSemester(selectedAction, selectedYear, selectedSemester);
		}
		
	}
	
	public void getAllListOfStudents() {
		allStudentSelected = aa_student_profileFacade.getAllByYearAndSemester(selectedYear, selectedSemester);
	}
	
	public void onRowSelect(SelectEvent event) {
		selectedStudent= (aa_student_profile) event.getObject();

		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect(ec.getRequestContextPath()
		            + "/pages/secured/academic_advising/studentRegistrar.xhtml");
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
    }
	
	
	public void onRowEdit(RowEditEvent event) {
		
		aa_student_profile profileStudent = (aa_student_profile) event.getObject();
		aa_student_profileFacade.addaa_student_profile(profileStudent);
        FacesMessage msg = new FacesMessage("Student with Id Edited", String.valueOf(profileStudent.getZewailcity_id()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
	
	
	public void refresh(){

		fillSemesterLst();
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

	public List<aa_student_profile> getAllStudentSelected() {
		return allStudentSelected;
	}

	public void setAllStudentSelected(List<aa_student_profile> allStudentSelected) {
		this.allStudentSelected = allStudentSelected;
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


	public aa_instructor_dateAppServiceImpl getAa_instructor_dateFacade() {
		return aa_instructor_dateFacade;
	}


	public void setAa_instructor_dateFacade(aa_instructor_dateAppServiceImpl aa_instructor_dateFacade) {
		this.aa_instructor_dateFacade = aa_instructor_dateFacade;
	}


	public String getSelectedAction() {
		return selectedAction;
	}


	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}


	public List<aa_instructor_date> getAllinstructorDates() {
		return allinstructorDates;
	}


	public void setAllinstructorDates(List<aa_instructor_date> allinstructorDates) {
		this.allinstructorDates = allinstructorDates;
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

	public boolean isMeetingSelected() {
		return meetingSelected;
	}

	public void setMeetingSelected(boolean meetingSelected) {
		this.meetingSelected = meetingSelected;
	}
	
	
	
}



