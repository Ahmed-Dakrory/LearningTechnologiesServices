/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.beans;

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
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatActionsSharedFacade;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatAdmissionHeadFacade;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.IReportsFacade;
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
@ManagedBean(name="CourseRepeatAdmissionHeadBean")
@ViewScoped
public class CourseRepeatAdmissionHeadBean {

	@ManagedProperty("#{ICourseRepeatAdmissionHeadFacade}")
	private ICourseRepeatAdmissionHeadFacade facade;
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
	private List<CourseRepeatDTO> pendingForms;
	private List<CourseRepeatDTO> archievedForms;
	private CourseRepeatDTO selectedPendingForms;
	private List<CourseRepeatDTO> filteredPendingForms;
	private CourseRepeatDTO selectedArchievedForms;
	private List<CourseRepeatDTO> filteredArchievedForms;
	private CourseRepeatDTO detailedForm;
	private String newComment;
	
	
	@ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
	private boolean detailView;
	private FormDTO formDetail;
	
	@ManagedProperty("#{ICourseRepeatActionsSharedFacade}")
	private ICourseRepeatActionsSharedFacade actionFacade;	
	
	@ManagedProperty("#{ReportsFacadeCourseRepeatImpl}")
	IReportsFacade reportsFacade;
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
		detailedForm=new CourseRepeatDTO();
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try
		{
			
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			if(origRequest.getParameterValues("action")[0].equals("approve"))
			{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is approved successfully !" );
			setSelectedPendingForms(actionFacade.getByID(dtoID));
			}
			else if(origRequest.getParameterValues("action")[0].equals("refuse"))
			{
				setSelectedPendingForms(actionFacade.getByID(dtoID));
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is Refused successfully !" );
			}
		}
		catch(Exception ex){
			System.out.println("No redirect");
		}

		semesterLst = new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0, "Fall"));
		semesterLst.add(new BaseDTO(1, "Spring"));
		semesterLst.add(new BaseDTO(2, "Summer"));
		yearLst = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			yearLst.add(2013 + i);
		}
	}
	public void fillPendingFormLst()
	{
		pendingForms=new ArrayList<CourseRepeatDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.ADMISSION_HEAD)){
				pendingForms=facade.getPendingFormsOfAdmissionHead();
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
		
	}

	  //TODO mode=1 > DEAN , mode=2 >Provost
	  public void ininttialzieYear(){
			selectedYear=null;
			archievedForms=new ArrayList<CourseRepeatDTO>();
			
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
	  
	/*public void fillArchievedPetitionsLst()
	{
		archievedForms=new ArrayList<CourseRepeatDTO>();
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
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		CourseRepeatDTO selectedDTO=(CourseRepeatDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  

	public ICourseRepeatAdmissionHeadFacade getFacade() {
		return facade;
	}
	public void setFacade(ICourseRepeatAdmissionHeadFacade facade) {
		this.facade = facade;
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
	public List<CourseRepeatDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<CourseRepeatDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<CourseRepeatDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<CourseRepeatDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}

	public CourseRepeatDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(CourseRepeatDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<CourseRepeatDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<CourseRepeatDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}

	public List<CourseRepeatDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<CourseRepeatDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public CourseRepeatDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(CourseRepeatDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public String getNewComment() {
		return newComment;
	}
	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}
	
	public void showDetails(CourseRepeatDTO  form) {
		/*setDetailedForm(form);
		formDetail = formsHistoryFacade.getFormDetail(form);
		detailView = true;
		formDetail.setShowMarkAsDone(false);
		formDetail.setShowRemind(formDetail.getStep().equals(PetitionStepsEnum.DEAN));*/
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("courseRepeatFormAdmissionHead1"))
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
	public CourseRepeatDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(CourseRepeatDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public ICourseRepeatActionsSharedFacade getActionFacade() {
		return actionFacade;
	}
	public void setActionFacade(ICourseRepeatActionsSharedFacade actionFacade) {
		this.actionFacade = actionFacade;
	}
	public IReportsFacade getReportsFacade() {
		return reportsFacade;
	}
	public void setReportsFacade(IReportsFacade reportsFacade) {
		this.reportsFacade = reportsFacade;
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
	
	


}
