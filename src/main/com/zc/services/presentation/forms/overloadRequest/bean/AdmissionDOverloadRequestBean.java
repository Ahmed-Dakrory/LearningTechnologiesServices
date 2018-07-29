/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IAdmissionDOverloadRequestFacade;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IOverloadRequestActionsSharedFacade;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IReportsFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya.alaa
 *
 */
@ManagedBean(name="AdmissionDOverloadRequestBean")
@ViewScoped
public class AdmissionDOverloadRequestBean {

	@ManagedProperty("#{AdmissionDOverloadRequestFacadeImpl}")
	private IAdmissionDOverloadRequestFacade facade;
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    @ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
		
	private List<OverloadRequestDTO> pendingForms;
	private List<OverloadRequestDTO> archievedForms;
	private OverloadRequestDTO selectedPendingForms;
	private List<OverloadRequestDTO> filteredPendingForms;
	private OverloadRequestDTO selectedArchievedForms;
	private List<OverloadRequestDTO> filteredArchievedForms;
	private OverloadRequestDTO detailedForm;
	private String newComment;
	@ManagedProperty("#{IOverloadRequestActionsSharedFacade}")
	private IOverloadRequestActionsSharedFacade actionFacade;	
	
	@ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
	private boolean detailView;
	private FormDTO formDetail;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;	
	
	@ManagedProperty("#{ReportsFacadeOverloadImpl}")
	private IReportsFacade reportsFacade;	
	@PostConstruct
	public void init()
	{
		detailView = false;
		
		fillPendingForms();
		//fillArchievedForms();
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try
		{
			
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			OverloadRequestDTO dto=new OverloadRequestDTO();
			dto=actionFacade.getByID(dtoID);
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form of student : "+dto.getStudent().getName()+"is Finalized successfully !" );
			setSelectedPendingForms(dto);
			
		}
		catch(Exception ex){
			System.out.println("No redirect");
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
	public void fillPendingForms()
	{
		pendingForms=new ArrayList<OverloadRequestDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			if(authentication.getName().equals(Constants.ADMISSION_DEPT))
			{
			pendingForms=  facade.getPendingForms();
		    if(pendingForms==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only for admission department members ");
			}
		}
	
	}
	/*public void fillArchievedForms()
	{
		archievedForms=new ArrayList<OverloadRequestDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			if(authentication.getName().equals(Constants.ADMISSION_DEPT))
			{
			archievedForms=  facade.getArchievedForms();
		    if(archievedForms==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null,"Error In getting Petitions" );
		    }
			}
            else {
            	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only for admission department members ");
			}
		}
	}*/
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		OverloadRequestDTO selectedDTO=(OverloadRequestDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	 public void ininttialzieYear(){
			selectedYear=null;
			archievedForms=new ArrayList<OverloadRequestDTO>();
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
	public IAdmissionDOverloadRequestFacade getFacade() {
		return facade;
	}
	public void setFacade(IAdmissionDOverloadRequestFacade facade) {
		this.facade = facade;
	}
	public List<OverloadRequestDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<OverloadRequestDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<OverloadRequestDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<OverloadRequestDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	public OverloadRequestDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(
			OverloadRequestDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<OverloadRequestDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(
			List<OverloadRequestDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public OverloadRequestDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(
			OverloadRequestDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<OverloadRequestDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<OverloadRequestDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public OverloadRequestDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(OverloadRequestDTO detailedForm) {
		this.detailedForm = detailedForm;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}
	
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}
	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public void showDetails(OverloadRequestDTO form) {
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("overloeadRequestAdmissionD1"))
    		{
    			FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=AdmissionD&oldMood=1");
    		}
    		else 
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("formDetails.xhtml?id="+form.getId()+"&cases=AdmissionD");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	public IFormsHistoryFacade getFormsHistoryFacade() {
		return formsHistoryFacade;
	}
	public void setFormsHistoryFacade(IFormsHistoryFacade formsHistoryFacade) {
		this.formsHistoryFacade = formsHistoryFacade;
	}
	public boolean isDetailView() {
		return detailView;
	}
	public void setDetailView(boolean detailView) {
		this.detailView = detailView;
	}
	public FormDTO getFormDetail() {
		return formDetail;
	}
	public void setFormDetail(FormDTO formDetail) {
		this.formDetail = formDetail;
	}
	public IOverloadRequestActionsSharedFacade getActionFacade() {
		return actionFacade;
	}
	public void setActionFacade(IOverloadRequestActionsSharedFacade actionFacade) {
		this.actionFacade = actionFacade;
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

