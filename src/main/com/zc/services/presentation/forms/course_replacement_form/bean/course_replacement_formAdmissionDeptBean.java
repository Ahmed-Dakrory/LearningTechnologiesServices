/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.bean;

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
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formActionsFacade;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formAdmissionDeptFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.IReportsFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya.alaa
 *
 */
@ManagedBean(name="course_replacement_formAdmissionDeptBean")
@ViewScoped
public class course_replacement_formAdmissionDeptBean {

	@ManagedProperty("#{course_replacement_formAdmissionDeptFacadeImpl}")
	private Icourse_replacement_formAdmissionDeptFacade facade;
	
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
	private List<course_replacement_formDTO> pendingForms;
	private List<course_replacement_formDTO> archievedForms;
	private course_replacement_formDTO selectedPendingForms;
	private List<course_replacement_formDTO> filteredPendingForms;
	private course_replacement_formDTO selectedArchievedForms;
	private List<course_replacement_formDTO> filteredArchievedForms;
	private course_replacement_formDTO detailedForm;
	private String newComment;
	@ManagedProperty("#{Icourse_replacement_formActionsFacade}")
	private Icourse_replacement_formActionsFacade actionFacade;
	
	
	@ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
	private boolean detailView;
	private FormDTO formDetail;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;	
	
	@ManagedProperty("#{ReportsFacadecourse_replacement_formImpl}")
	private IReportsFacade reportsFacade;
	@PostConstruct
	public void init()
	{
		detailView = false;
		
		fillPendingFormLst();
		//fillArchievedPetitionsLst();
		detailedForm=new course_replacement_formDTO();
		try{
      		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
              Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
              course_replacement_formDTO dto=new course_replacement_formDTO();
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
		pendingForms=new ArrayList<course_replacement_formDTO>();
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
	{archievedForms=new ArrayList<course_replacement_formDTO>();
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
		  		course_replacement_formDTO selectedDTO=(course_replacement_formDTO) event.getObject();
				showDetails(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		public void ininttialzieYear(){
			selectedYear=null;
			archievedForms=new ArrayList<course_replacement_formDTO>();
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
	public Icourse_replacement_formAdmissionDeptFacade getFacade() {
		return facade;
	}
	public void setFacade(Icourse_replacement_formAdmissionDeptFacade facade) {
		this.facade = facade;
	}
	public List<course_replacement_formDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<course_replacement_formDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<course_replacement_formDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<course_replacement_formDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	
	public List<course_replacement_formDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<course_replacement_formDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	
	public course_replacement_formDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}

	public void setSelectedPendingForms(course_replacement_formDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}

	public course_replacement_formDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}

	public void setSelectedArchievedForms(course_replacement_formDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}

	public List<course_replacement_formDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<course_replacement_formDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public course_replacement_formDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(course_replacement_formDTO detailedForm) {
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
	
	public void showDetails(course_replacement_formDTO form) {
	try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("changeMajorAdmission1"))
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

	public Icourse_replacement_formActionsFacade getActionFacade() {
		return actionFacade;
	}

	public void setActionFacade(Icourse_replacement_formActionsFacade actionFacade) {
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

