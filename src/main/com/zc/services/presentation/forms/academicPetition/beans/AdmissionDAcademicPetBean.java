/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAcademicPetitionActionsFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAdmissionDeptAcademicPetFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IReportsFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean(name="AdmissionDAcademicPetBean")
@ViewScoped
public class AdmissionDAcademicPetBean {

	@ManagedProperty("#{AdmissionDeptAcademicPetFacadeImpl}")
	private IAdmissionDeptAcademicPetFacade facade;
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	@ManagedProperty("#{IAcademicPetitionActionsFacade}")
	private IAcademicPetitionActionsFacade actionFacade;	
	
	private List<CoursePetitionDTO> pendingForms;
	private List<CoursePetitionDTO> archievedForms;
	private CoursePetitionDTO selectedPendingForms;
	private List<CoursePetitionDTO> filteredPendingForms;
	private CoursePetitionDTO selectedArchievedForms;
	private List<CoursePetitionDTO> filteredArchievedForms;
	private CoursePetitionDTO detailedForm;
	private String newComment;
	@ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
	
	@ManagedProperty("#{IReportsFacadeAcademicPet}")
	private IReportsFacade reportsFacade;
	
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
		fillPendingFormLst();
		//fillArchievedPetitionsLst();
		detailedForm=new CoursePetitionDTO();

		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try
		{
			
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			CoursePetitionDTO dto=new CoursePetitionDTO();
			dto=actionFacade.getByID(dtoID);
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form of student : "+dto.getStudentName()+"is Finalized successfully !" );
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
		pendingForms=new ArrayList<CoursePetitionDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.ADMISSION_DEPT)){
				pendingForms=facade.getPendingPet();
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
		
	}
/*

	public void fillArchievedPetitionsLst()
	{
		archievedForms=new ArrayList<CoursePetitionDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.ADMISSION_DEPT)){
				archievedForms=facade.getOldPet();
				
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,"Not Allowed To see This Petitions" );
			}
		}
	}*/

	  public void onRowSelect(SelectEvent event) {  
	      	
        CoursePetitionDTO selectedDTO=(CoursePetitionDTO) event.getObject();
        try {
    		showDetails(selectedDTO);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	  }
		public void ininttialzieYear(){
			selectedYear=null;
			archievedForms=new ArrayList<CoursePetitionDTO>();
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
			reportsFacade.generateExcelByList(getArchievedForms());
		}
	public Date getCurrentDate(){
		return new Date();
	}
	public IAdmissionDeptAcademicPetFacade getFacade() {
		return facade;
	}
	public void setFacade(IAdmissionDeptAcademicPetFacade facade) {
		this.facade = facade;
	}
	public List<CoursePetitionDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<CoursePetitionDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<CoursePetitionDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<CoursePetitionDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
		public List<CoursePetitionDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<CoursePetitionDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	
	public CoursePetitionDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(CoursePetitionDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public CoursePetitionDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(CoursePetitionDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<CoursePetitionDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<CoursePetitionDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public CoursePetitionDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(CoursePetitionDTO detailedForm) {
		this.detailedForm = detailedForm;
	}

	public String getNewComment() {
		return newComment;
		}
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}

	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public void showDetails(CoursePetitionDTO form) {
	/*	setDetailedForm(form);
		formDetail = formsHistoryFacade.getFormDetail(form);
		detailView = true;
		formDetail.setShowMarkAsDone(true);
		formDetail.setShowRemind(formDetail.getStep().equals(PetitionStepsEnum.ADMISSION_HEAD));*/
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("admissionDAcademicPet1"))
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
	public IAcademicPetitionActionsFacade getActionFacade() {
		return actionFacade;
	}
	public void setActionFacade(IAcademicPetitionActionsFacade actionFacade) {
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
