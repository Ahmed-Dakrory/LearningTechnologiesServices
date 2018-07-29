/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.bean;

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

import main.com.zc.services.applicationService.shared.service.IGetSemesterFromDateService;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeConcentrationActionsFacade;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeConcentrationRegistrarFacade;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IReportsFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class ChangeOfConcentrationRegistrarBean {

	@ManagedProperty("#{IChangeConcentrationRegistrarFacade}")
	private IChangeConcentrationRegistrarFacade facade;
	
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
	private List<ChangeConcentrationDTO> pendingForms;
	private List<ChangeConcentrationDTO> archievedForms;
	private ChangeConcentrationDTO selectedPendingForms;
	private List<ChangeConcentrationDTO> filteredPendingForms;
	private ChangeConcentrationDTO selectedArchievedForms;
	private List<ChangeConcentrationDTO> filteredArchievedForms;
	private ChangeConcentrationDTO detailedForm;
	private String newComment;
	@ManagedProperty("#{IChangeConcentrationActionsFacade}")
	private IChangeConcentrationActionsFacade actionFacade;
	
	
	@ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
	
	@ManagedProperty("#{IReportsFacade}")
	private IReportsFacade reprotsFacade;;
	
	
	
	private boolean detailView;
	private FormDTO formDetail;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;
	@PostConstruct
	public void init()
	{
		detailView = false;
		selectedSemester=null;
		selectedYear=null;
		fillPendingFormLst();
		archievedForms=new ArrayList<ChangeConcentrationDTO>();
		//fillArchievedPetitionsLst();
		detailedForm=new ChangeConcentrationDTO();
		try{
      		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
              Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
              ChangeConcentrationDTO dto=new ChangeConcentrationDTO();
  			  dto=actionFacade.getByID(dtoID);
  			 JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form of student : "+dto.getStudent().getName()+"is Finalized successfully !" );
  			
  			setSelectedPendingForms(actionFacade.getByID(dtoID));
  			
      		}
      		catch(Exception ex)
      		{
      			ex.toString();
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
		pendingForms=new ArrayList<ChangeConcentrationDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.ADMISSION_DEPT)||mail.equals(Constants.CHANGE_MAJOR_HEND)||mail.equals(Constants.CHANGE_MAJOR_YASMINE)){
				pendingForms=facade.getPendingPetitionsOfstuent();
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
	}
	public void fillArchievedPetitionsLst()
	{archievedForms=new ArrayList<ChangeConcentrationDTO>();
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		String mail = authentication.getName();
		if(mail.equals(Constants.ADMISSION_DEPT)){
			archievedForms=facade.getArchievedPetitionsOfstuent();
		}
		else {
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
		}
	}
	}
	public void onRowSelect(SelectEvent event) {  
		  	try {
		  		ChangeConcentrationDTO selectedDTO=(ChangeConcentrationDTO) event.getObject();
				showDetails(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	public void showDetails(ChangeConcentrationDTO form) {
		  		try {
		      		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		      		StringBuffer url=origRequest.getRequestURL();
		      		if(url.toString().contains("changeOfConcentrationAdmission1"))
		      		{
		      			FacesContext.getCurrentInstance().getExternalContext().redirect
		  				("formDetails.xhtml?id="+form.getId()+"&cases=AdmissionD&oldMood=1");
		      		}
		      		else 
		  			FacesContext.getCurrentInstance().getExternalContext().redirect
		  			("formDetails.xhtml?id="+form.getId()+"&cases=AdmissionD");
		  		} catch (IOException e) {
		  			
		  			e.printStackTrace();
		  		}
		  		}
	public void ininttialzieYear(){
		selectedYear=null;
		archievedForms=new ArrayList<ChangeConcentrationDTO>();
	}
	public void getPetitionsBySemester(){
		if(getSelectedYear()!=null){
		if(getSelectedSemester()==0)
		archievedForms=facade.getOldFall(getSelectedYear());
		else if(getSelectedSemester()==1)
			archievedForms=facade.getOldSpring(getSelectedYear());
		else if(getSelectedSemester()==2)
			archievedForms=facade.getOldSummer(getSelectedYear());
		}
	}
	public void exportExcelReport(){
		if(getArchievedForms()!=null)
			if(getArchievedForms().size()>0)
		reprotsFacade.generateExcelByList(getArchievedForms());
	}
	public IChangeConcentrationRegistrarFacade getFacade() {
		return facade;
	}
	public void setFacade(IChangeConcentrationRegistrarFacade facade) {
		this.facade = facade;
	}
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}
	public void setSharedAcademicPetFacade(ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public List<ChangeConcentrationDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<ChangeConcentrationDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<ChangeConcentrationDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<ChangeConcentrationDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	public ChangeConcentrationDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(ChangeConcentrationDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<ChangeConcentrationDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<ChangeConcentrationDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public ChangeConcentrationDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(ChangeConcentrationDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<ChangeConcentrationDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(List<ChangeConcentrationDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public ChangeConcentrationDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(ChangeConcentrationDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public String getNewComment() {
		return newComment;
	}
	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public IChangeConcentrationActionsFacade getActionFacade() {
		return actionFacade;
	}


	public void setActionFacade(IChangeConcentrationActionsFacade actionFacade) {
		this.actionFacade = actionFacade;
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

	public Integer getSelectedSemester() {
		return selectedSemester;
	}

	public void setSelectedSemester(Integer selectedSemester) {
		this.selectedSemester = selectedSemester;
	}

	public List<BaseDTO> getSemesterLst() {
		return semesterLst;
	}

	public void setSemesterLst(List<BaseDTO> semesterLst) {
		this.semesterLst = semesterLst;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public List<Integer> getYearLst() {
		return yearLst;
	}

	public void setYearLst(List<Integer> yearLst) {
		this.yearLst = yearLst;
	}

	public IReportsFacade getReprotsFacade() {
		return reprotsFacade;
	}

	public void setReprotsFacade(IReportsFacade reprotsFacade) {
		this.reprotsFacade = reprotsFacade;
	}
		
	
	
	
}
