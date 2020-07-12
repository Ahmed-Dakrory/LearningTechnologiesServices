package main.com.zc.service.academic_advisingInstructorBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.service.academic_advisingInstructorsDates.copy.aa_instructor_date;
import main.com.zc.service.academic_advisingInstructorsDates.copy.aa_instructor_dateAppServiceImpl;
import main.com.zc.service.academic_advising_instructor.aa_instructor;
import main.com.zc.service.academic_advising_instructor.aa_instructorAppServiceImpl;
import main.com.zc.service.academic_advising_student_profile.aa_student_profile;
import main.com.zc.service.academic_advising_student_profile.aa_student_profileAppServiceImpl;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;

@ManagedBean(name = "aaInstructorBean")
@ViewScoped
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

	private aa_instructor thisInstrutorAccount;
	private aa_student_profile selectedStudent;

	private List<aa_instructor_date> allinstructorDates;
	
	

	@PostConstruct
	public void init() {

		allinstructorDates =new ArrayList<aa_instructor_date>();
		refresh();
		
		
	}
	
	public void getAllInstructorDates() {
		FormsStatusDTO settingform = facadeSettings.getById(23);
		allinstructorDates = aa_instructor_dateFacade.getAllByYearAndSemester( settingform.getYear(), settingform.getSemester().getName());
	}
	
	

     
	
	
	public void refresh(){
		FormsStatusDTO settingform = facadeSettings.getById(23);
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			
				thisInstrutorAccount =  aa_instructorFacade.getByMailAndYearAndSemester(mail, settingform.getYear(), settingform.getSemester().getName());	
			
		}
		getAllInstructorDates();
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


	
	
	
}



