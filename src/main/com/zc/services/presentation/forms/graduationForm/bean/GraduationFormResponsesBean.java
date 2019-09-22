/**
 * 
 */
package main.com.zc.services.presentation.forms.graduationForm.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import main.com.zc.services.presentation.accountSetting.facade.IStudentProfileFacade;
import main.com.zc.services.presentation.forms.graduationForm.dto.GraduationInformationDTO;
import main.com.zc.services.presentation.forms.graduationForm.facade.IExportReportsFacade;
import main.com.zc.services.presentation.forms.graduationForm.facade.IGraduationInformationFacade;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class GraduationFormResponsesBean {

	private List<GraduationInformationDTO> responses;
	private List<GraduationInformationDTO> filteredResponses;
	private GraduationInformationDTO selectedReponse;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;
	private Integer selectedSemester;
	private Integer selectedYear;
	
	StudentProfileDTO studentProfile;
	private GraduationInformationDTO detailedForm;
	@ManagedProperty("#{IGraduationInformationFacade}")
	private IGraduationInformationFacade facade;
	
	@ManagedProperty("#{IStudentProfileFacade}")
	private IStudentProfileFacade studentProfileFacade;
	
	@ManagedProperty("#{IExportReportsFacade}")
	IExportReportsFacade reportsFacade;
	
	@PostConstruct
	public void init()
	{ 
		Authentication 	authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!authentication.getName().startsWith("S-")&&!authentication.getName().startsWith("s-")&&!StringUtils.isNumeric(authentication.getName().substring(0, 4))){
	
			 try{
		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		try{
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			if(dtoID!=null){
				detailedForm=facade.getById(dtoID);
				setStudentProfile(studentProfileFacade.getCurrentPRofileByStudentID(detailedForm.getStudent().getId()));
				}
			}
		catch(Exception ex){
			 responses=new ArrayList<>();
			 fillSemesterLst();
			 fillYearLst();
		}
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		 
	}
	}
	public void fillSemesterLst()
	{
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		//semesterLst.add(new BaseDTO(3,"Winter"));
	}
	public void fillYearLst()
	{
		 yearLst=new ArrayList<Integer>();
			for(int i=2013;i<2031;i++)
		{
			yearLst.add(i);
		}
	}
	public void fillForms(){
		if(getSelectedSemester()!=null&&getSelectedYear()!=null)
		{
		responses=new ArrayList<>();
		responses=facade.getFormBySemesterAndYear(getSelectedYear(), getSelectedSemester());
		}
	}
	 public void onRowSelect(SelectEvent event) {  
	      	
		 GraduationInformationDTO selectedDTO=(GraduationInformationDTO) event.getObject();
         try {
	    		FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+selectedDTO.getId());
	    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     	
   }  
	  public void exportExcelReport(){
			 if(getResponses()!=null)
				if(getResponses().size()>0)
					reportsFacade.export(getResponses());
			
		}
	public List<GraduationInformationDTO> getResponses() {
		return responses;
	}

	public void setResponses(List<GraduationInformationDTO> responses) {
		this.responses = responses;
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

	public Integer getSelectedSemester() {
		return selectedSemester;
	}

	public void setSelectedSemester(Integer selectedSemester) {
		this.selectedSemester = selectedSemester;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public IGraduationInformationFacade getFacade() {
		return facade;
	}

	public void setFacade(IGraduationInformationFacade facade) {
		this.facade = facade;
	}

	public List<GraduationInformationDTO> getFilteredResponses() {
		return filteredResponses;
	}

	public void setFilteredResponses(List<GraduationInformationDTO> filteredResponses) {
		this.filteredResponses = filteredResponses;
	}

	public GraduationInformationDTO getSelectedReponse() {
		return selectedReponse;
	}

	public void setSelectedReponse(GraduationInformationDTO selectedReponse) {
		this.selectedReponse = selectedReponse;
	}

	public GraduationInformationDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(GraduationInformationDTO detailedForm) {
		this.detailedForm = detailedForm;
	}

	public StudentProfileDTO getStudentProfile() {
		return studentProfile;
	}
	public void setStudentProfile(StudentProfileDTO studentProfile) {
		this.studentProfile = studentProfile;
	}
	public IStudentProfileFacade getStudentProfileFacade() {
		return studentProfileFacade;
	}
	public void setStudentProfileFacade(IStudentProfileFacade studentProfileFacade) {
		this.studentProfileFacade = studentProfileFacade;
	}
	public IExportReportsFacade getReportsFacade() {
		return reportsFacade;
	}
	public void setReportsFacade(IExportReportsFacade reportsFacade) {
		this.reportsFacade = reportsFacade;
	}


}
