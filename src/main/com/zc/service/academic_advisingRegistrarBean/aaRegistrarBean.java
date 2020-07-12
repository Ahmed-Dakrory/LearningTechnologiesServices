package main.com.zc.service.academic_advisingRegistrarBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import main.com.zc.service.academic_advising_student_profile.aa_student_profile;
import main.com.zc.service.academic_advising_student_profile.aa_student_profileAppServiceImpl;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.shared.presentation.dto.BaseDTO;

@ManagedBean(name = "aaRegistrarBean")
@ViewScoped
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

	private aa_student_profile selectedStudent;
	
	private List<aa_student_profile> allStudentSelected;
	
	private int selectedYear;
	private String selectedSemester;
	

	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;
	@PostConstruct
	public void init() {

		allStudentSelected =new ArrayList<aa_student_profile>();
		refresh();
		
		
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
		
		yearLst=new ArrayList<Integer>();
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(i);
		}
	}
	
	public void fillYearLst(AjaxBehaviorEvent event)
	{
		 yearLst=new ArrayList<Integer>();
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(i);
		}
	}

	
	
	public List<BaseDTO> getSemesterLst() {
		return semesterLst;
	}

	public void setSemesterLst(List<BaseDTO> semesterLst) {
		this.semesterLst = semesterLst;
	}

	public List<Integer> getYearLst() {
		return yearLst;
	}

	public void setYearLst(List<Integer> yearLst) {
		this.yearLst = yearLst;
	}

	public List<aa_student_profile> getAllStudentSelected() {
		return allStudentSelected;
	}

	public void setAllStudentSelected(List<aa_student_profile> allStudentSelected) {
		this.allStudentSelected = allStudentSelected;
	}

	public int getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(int selectedYear) {
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
	
	
}



