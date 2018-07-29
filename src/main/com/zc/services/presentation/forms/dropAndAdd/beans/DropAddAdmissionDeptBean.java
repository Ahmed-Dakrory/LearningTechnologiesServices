/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.beans;

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
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IAddDropActionsFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IAdmissionDeptAddDropFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IReportsFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya.alaa
 *
 */
@ManagedBean
@ViewScoped
public class DropAddAdmissionDeptBean {

	@ManagedProperty("#{AdmissionDeptAddDropFacadeImpl}")
	private IAdmissionDeptAddDropFacade facade;
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;

	private List<DropAddFormDTO> pendingForms;
	private List<DropAddFormDTO> archievedForms;
	private DropAddFormDTO selectedPendingForms;
	private List<DropAddFormDTO> filteredPendingForms;
	private DropAddFormDTO selectedArchievedForms;
	private List<DropAddFormDTO> filteredArchievedForms;
	private DropAddFormDTO detailedForm;
	private String newComment;
    private boolean renderAddAction;
    private boolean renderDropAction;
	
    @ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
    
    @ManagedProperty("#{IAddDropActionsFacade}")
	private IAddDropActionsFacade actionFacade;
	private boolean detailView;
	private FormDTO formDetail;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;	
	
	@ManagedProperty("#{ReportsFacadeAddDropImpl}")
	private IReportsFacade reportsFacade;
	@PostConstruct
	public void init()
	{
		detailView = false;
		
		fillPendingFormLst();
		//fillArchievedPetitionsLst();
		detailedForm=new DropAddFormDTO();
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try
		{
			
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			DropAddFormDTO dto=new DropAddFormDTO();
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
	public void fillPendingFormLst()
	{
		pendingForms=new ArrayList<DropAddFormDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase())){
				pendingForms=facade.getPendingFormsOfAdmissionDept();
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
		
	}

	public void onRowSelect(SelectEvent event) {  
      	try {
          DropAddFormDTO selectedDTO=(DropAddFormDTO) event.getObject();
		showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  

	public void ininttialzieYear(){
		selectedYear=null;
		archievedForms=new ArrayList<DropAddFormDTO>();
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
	public IAdmissionDeptAddDropFacade getFacade() {
		return facade;
	}
	public void setFacade(IAdmissionDeptAddDropFacade facade) {
		this.facade = facade;
	}
	public List<DropAddFormDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<DropAddFormDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<DropAddFormDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<DropAddFormDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	
	public DropAddFormDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(DropAddFormDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<DropAddFormDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<DropAddFormDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public DropAddFormDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(DropAddFormDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<DropAddFormDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<DropAddFormDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public DropAddFormDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(DropAddFormDTO detailedForm) {
		this.detailedForm = detailedForm;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public boolean isRenderAddAction() {
		return renderAddAction;
	}

	public void setRenderAddAction(boolean renderAddAction) {
		this.renderAddAction = renderAddAction;
	}

	public boolean isRenderDropAction() {
		return renderDropAction;
	}

	public void setRenderDropAction(boolean renderDropAction) {
		this.renderDropAction = renderDropAction;
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
	public void showDetails(DropAddFormDTO form) {
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("addDropAdmissionDept1"))
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
	public IAddDropActionsFacade getActionFacade() {
		return actionFacade;
	}
	public void setActionFacade(IAddDropActionsFacade actionFacade) {
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

