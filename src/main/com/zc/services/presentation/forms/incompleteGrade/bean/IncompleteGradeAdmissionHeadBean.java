/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeAdmissionHeadFacade;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IReportsFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean(name="IncompleteGradeAdmissionHeadBean")
@ViewScoped
public class IncompleteGradeAdmissionHeadBean {

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
   	
	@ManagedProperty("#{IIncompleteGradeAdmissionHeadFacade}")
	private IIncompleteGradeAdmissionHeadFacade facade;

    @ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
    
    private List<IncompleteGradeDTO> pendingForms;
	private List<IncompleteGradeDTO> archievedForms;
	private IncompleteGradeDTO selectedPendingForms;
	private List<IncompleteGradeDTO> filteredPendingForms;
	private IncompleteGradeDTO selectedArchievedForms;
	private List<IncompleteGradeDTO> filteredArchievedForms;
    private IncompleteGradeDTO detailedForm;
    private String newComment;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;	
	
	@ManagedProperty("#{ReportsFacadeIncompleteImpl}")
	private IReportsFacade reportsFacade;
    @PostConstruct
	public void init()
	{
    	try{
    		fillPendingAdmissionHead();
    		//fillArchsAdmissionHead();
    		try{
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			
			
			setSelectedPendingForms(facade.getById(dtoID));
			
    		}
    		catch(Exception ex)
    		{
    			ex.toString();
    		}
    	}
    	catch(Exception ex)
    	{
    		
    	}
    	semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		yearLst=new ArrayList<Integer>();
		for(int i=0;i<20;i++){
			yearLst.add(2013+i);
		}
	}
    public void showDetails(IncompleteGradeDTO form) 
    {
    	try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("admissionH1"))
    		{
    			FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=AdmissionH&oldMood=1");
    		}
    		else 
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("formDetails.xhtml?id="+form.getId()+"&cases=AdmissionH");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
    public void onRowSelect(SelectEvent event) {  
	  	try {
	  		IncompleteGradeDTO selectedDTO=(IncompleteGradeDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
    public void fillPendingAdmissionHead()
    {
    	pendingForms=new ArrayList<IncompleteGradeDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.REGISTRAR_HEAD_EMAIL)){
				pendingForms=facade.getPendingFormsOfAdmissionHead();
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
    }
/*    public void fillArchsAdmissionHead()
    {
    	archievedForms=new ArrayList<IncompleteGradeDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.ADMISSION_HEAD)){
				archievedForms=facade.getArchievedFormsOfAdmissionHead();
				
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
    }*/
    public void ininttialzieYear(){
		selectedYear=null;
		archievedForms=new ArrayList<IncompleteGradeDTO>();
	}
  public void getPetitionsBySemester(){
		if(getSelectedYear()!=null){
		if(getSelectedSemester()==0)
		archievedForms=reportsFacade.getOldFall(getSelectedYear());
		else if(getSelectedSemester()==1)
			archievedForms=reportsFacade.getOldSpring(getSelectedYear());
		else if(getSelectedSemester()==2)
			archievedForms=reportsFacade.getOldSummer(getSelectedYear());
		}
	}
  public void exportExcelReport(){
		if(getArchievedForms()!=null)
			if(getArchievedForms().size()>0)
				reportsFacade.generateExcelByList(getArchievedForms());
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public IIncompleteGradeAdmissionHeadFacade getFacade() {
		return facade;
	}

	public void setFacade(IIncompleteGradeAdmissionHeadFacade facade) {
		this.facade = facade;
	}

	public IFormsHistoryFacade getFormsHistoryFacade() {
		return formsHistoryFacade;
	}

	public void setFormsHistoryFacade(IFormsHistoryFacade formsHistoryFacade) {
		this.formsHistoryFacade = formsHistoryFacade;
	}

	public List<IncompleteGradeDTO> getPendingForms() {
		return pendingForms;
	}

	public void setPendingForms(List<IncompleteGradeDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}

	public List<IncompleteGradeDTO> getArchievedForms() {
		return archievedForms;
	}

	public void setArchievedForms(List<IncompleteGradeDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}

	public IncompleteGradeDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}

	public void setSelectedPendingForms(IncompleteGradeDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}

	public List<IncompleteGradeDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}

	public void setFilteredPendingForms(
			List<IncompleteGradeDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}


	public IncompleteGradeDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(IncompleteGradeDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<IncompleteGradeDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}

	public void setFilteredArchievedForms(
			List<IncompleteGradeDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}

	public IncompleteGradeDTO getDetailedForm() {
		return detailedForm;
	}

	public void setDetailedForm(IncompleteGradeDTO detailedForm) {
		this.detailedForm = detailedForm;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
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
	public IReportsFacade getReportsFacade() {
		return reportsFacade;
	}
	public void setReportsFacade(IReportsFacade reportsFacade) {
		this.reportsFacade = reportsFacade;
	}
    
	
	
	
	
}
