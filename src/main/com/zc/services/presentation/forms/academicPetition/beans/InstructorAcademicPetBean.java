/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.beans;

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
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAdmissionDeptAcademicPetFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IInstructorAcademicPetFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IReportsFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean(name="InstructorAcademicPetBean")
@ViewScoped
public class InstructorAcademicPetBean {

	@ManagedProperty("#{InstructorAcademicPetFacadeImpl}")
	private IInstructorAcademicPetFacade facade;
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
		
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
	private List<CoursePetitionDTO> pendingForms;
	private List<CoursePetitionDTO> archievedForms;
	private CoursePetitionDTO selectedPendingForms;
	private List<CoursePetitionDTO> filteredPendingForms;
	private CoursePetitionDTO selectedArchievedForms;
	private List<CoursePetitionDTO> filteredArchievedForms;
	private List<CoursePetitionDTO> pendingFormsDean;
	private List<CoursePetitionDTO> archievedFormsDean;
	private CoursePetitionDTO selectedPendingFormsDean;
	private List<CoursePetitionDTO> filteredPendingFormsDean;
	private CoursePetitionDTO selectedArchievedFormsDean;
	private List<CoursePetitionDTO> filteredArchievedFormsDean;
	private CoursePetitionDTO detailedForm;
    private CoursePetitionDTO detailedFormDean;
    private Integer typeOfUserDean;
    private List<BaseDTO> userTypesDeanLst;
    private Boolean showIns;
    private Boolean showDean;    
    private String newComment;
    private List<InstructorDTO> instructors;
    private Integer selectedInstructor;
    
    @ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
    private boolean detailView;
	private FormDTO formDetail;
	@ManagedProperty("#{IReportsFacadeAcademicPet}")
	private IReportsFacade reportsFacade;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;	
	
	@ManagedProperty("#{AdmissionDeptAcademicPetFacadeImpl}")
	private IAdmissionDeptAcademicPetFacade oldPetFacade;
	
	
	@PostConstruct
	public void init()
	{
		detailView = false;
		//fillInstructorsLst();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			if(!authentication.getName().startsWith("S-")&&!authentication.getName().startsWith("s-")&&!StringUtils.isNumeric(authentication.getName().substring(0, 4)))
			{
				String mail = authentication.getName();
				if(mail.equals(Constants.DEAN_OF_STRATEGIC))
				{
					if(mail.equals(Constants.DEAN_OF_STRATEGIC))
					{
						if(!getInsDataFacade.isCouurseCoordinator(mail))
						{
						
						    
							//return true;
							setShowIns(true);
						}
					}
					else 
					{
						setShowIns(false);
					}
				}
				else 
				{
					setShowIns(false);
				}
				
				fillPendingForms();
				fillArchievedForms();
				fillPendingFormsDean();
				//fillArchievedFormsDean();
				userTypesDeanLst=new ArrayList<BaseDTO>();
				userTypesDeanLst.add(new BaseDTO(1,"Instructor Mode"));
				userTypesDeanLst.add(new BaseDTO(2,"Dean Mode"));
				HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
				StringBuffer url=origRequest.getRequestURL();
				
				if(url.toString().contains("dean"))
				{
					
					
					typeOfUserDean=2;
					setShowIns(true);
				}
				//HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
				try
				{
					Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
					
					if(origRequest.getParameterValues("action")[0].equals("approveIns"))
					{
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is approved successfully !" );
					setSelectedPendingForms(facade.getByID(dtoID));
					}
					else if(origRequest.getParameterValues("action")[0].equals("refuseIns"))
					{
						setSelectedPendingForms(facade.getByID(dtoID));
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is Refused successfully !" );
					}
					else if(origRequest.getParameterValues("action")[0].equals("approveDean"))
					{
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is approved successfully !" );
					setSelectedPendingFormsDean(facade.getByID(dtoID));
					}
					else if(origRequest.getParameterValues("action")[0].equals("refuseDean"))
					{
						setSelectedPendingFormsDean(facade.getByID(dtoID));
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is Refused successfully !" );
					}
					
					
				}
				catch(Exception ex)
				{
					System.out.println("Not redirect");
					ex.toString();
				}
				
				
				try
				{
					Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
					if(dtoID!=null){
						JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Done !" );
						setSelectedPendingForms(facade.getByID(dtoID));
					}
				}
				catch(Exception ex)
				{
					ex.toString();
				}
				try
				{
					Integer forwaredInsID=Integer.parseInt(origRequest.getParameterValues("forwaredIns")[0]);
					InstructorDTO forwardedIns=getInsDataFacade.getInsByPersonID(forwaredInsID);
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is forwarded successfully to : "+forwardedIns.getName() );
					
				}
				catch(Exception ex)
				{
					ex.toString();	
				}
			}
			else 
			{
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only for Instructors");
			}
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
		pendingForms=new ArrayList<CoursePetitionDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			pendingForms=  facade.getPendingPetOfInstructor(getInsDataFacade.getInsByPersonMail(mail).getId());
		    if(pendingForms==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
	}
	
	  public void fillArchievedForms()
	{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			archievedForms=  facade.getOldPetOfInstructor(getInsDataFacade.getInsByPersonMail(mail).getId());
		    if(archievedForms==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
	
		
	}
	  public void fillPendingFormsDean()
	    {
	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				
				pendingFormsDean=  facade.getPendingPetOfDean();
			    if(pendingFormsDean==null)
			    {
			    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
			    }
			}
	    }
	  public void ininttialzieYear(){
			selectedYear=null;
			archievedFormsDean=new ArrayList<CoursePetitionDTO>();
		}
	  public void getPetitionsBySemester(){
			if(getSelectedYear()!=null){
			if(getSelectedSemester()==0)
				archievedFormsDean=oldPetFacade.getOldFall(getSelectedYear());
			else if(getSelectedSemester()==1)
				archievedFormsDean=oldPetFacade.getOldSpring(getSelectedYear());
			else if(getSelectedSemester()==2)
				archievedFormsDean=oldPetFacade.getOldSummer(getSelectedYear());
			}
		}
	  public void exportExcelReport(){
			if(getArchievedForms()!=null)
				if(getArchievedForms().size()>0)
			reportsFacade.generateExcelByList(getArchievedFormsDean());
		}
	  public void showDetailsDean(CoursePetitionDTO form)
		{
			
		  try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("InstructorCoursePetitionPage1"))
	    		{
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+form.getId()+"&cases=Ins&oldMood=1");
	    		}
	    		else 
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=Dean");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		}  
	  public void onRowSelect(SelectEvent event) {  
	      	
          CoursePetitionDTO selectedDTO=(CoursePetitionDTO) event.getObject();
          try {
      		showDetails(selectedDTO);
      	} catch (Exception e) {
      		// TODO Auto-generated catch block
      		e.printStackTrace();
      	}
      	
    }  
 
 public void onRowSelectDean(SelectEvent event) {  
      	
     CoursePetitionDTO selectedDTO=(CoursePetitionDTO) event.getObject();
     try {
    		showDetailsDean(selectedDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
}  
 
	
	  public void navigateDean()
	    {
	    	if(getTypeOfUserDean()==2)
	    	{
	    	
	    		setShowIns(true);
	    	
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("deanCoursePetitionPage.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	    	
	    	}
	    	else
	    	{
	    		
	    		setShowIns(false);
	    		
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("InstructorCoursePetitionPage.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	    		
	    	
	    	}
	    }   
	
	  public IInstructorAcademicPetFacade getFacade() {
		return facade;
	}
	public void setFacade(IInstructorAcademicPetFacade facade) {
		this.facade = facade;
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
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
	
	public CoursePetitionDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}

	public void setSelectedPendingForms(CoursePetitionDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}

	public List<CoursePetitionDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<CoursePetitionDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}

	public List<CoursePetitionDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<CoursePetitionDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public List<CoursePetitionDTO> getPendingFormsDean() {
		return pendingFormsDean;
	}
	public void setPendingFormsDean(List<CoursePetitionDTO> pendingFormsDean) {
		this.pendingFormsDean = pendingFormsDean;
	}
	public List<CoursePetitionDTO> getArchievedFormsDean() {
		return archievedFormsDean;
	}
	public void setArchievedFormsDean(List<CoursePetitionDTO> archievedFormsDean) {
		this.archievedFormsDean = archievedFormsDean;
	}
	
	public List<CoursePetitionDTO> getFilteredPendingFormsDean() {
		return filteredPendingFormsDean;
	}
	public void setFilteredPendingFormsDean(
			List<CoursePetitionDTO> filteredPendingFormsDean) {
		this.filteredPendingFormsDean = filteredPendingFormsDean;
	}

	public CoursePetitionDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(CoursePetitionDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public CoursePetitionDTO getSelectedArchievedFormsDean() {
		return selectedArchievedFormsDean;
	}
	public void setSelectedArchievedFormsDean(
			CoursePetitionDTO selectedArchievedFormsDean) {
		this.selectedArchievedFormsDean = selectedArchievedFormsDean;
	}
	public List<CoursePetitionDTO> getFilteredArchievedFormsDean() {
		return filteredArchievedFormsDean;
	}
	public void setFilteredArchievedFormsDean(
			List<CoursePetitionDTO> filteredArchievedFormsDean) {
		this.filteredArchievedFormsDean = filteredArchievedFormsDean;
	}
	public CoursePetitionDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(CoursePetitionDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public CoursePetitionDTO getDetailedFormDean() {
		return detailedFormDean;
	}
	public void setDetailedFormDean(CoursePetitionDTO detailedFormDean) {
		this.detailedFormDean = detailedFormDean;
	}
	public Integer getTypeOfUserDean() {
		return typeOfUserDean;
	}
	public void setTypeOfUserDean(Integer typeOfUserDean) {
		this.typeOfUserDean = typeOfUserDean;
	}
	public List<BaseDTO> getUserTypesDeanLst() {
		return userTypesDeanLst;
	}
	public void setUserTypesDeanLst(List<BaseDTO> userTypesDeanLst) {
		this.userTypesDeanLst = userTypesDeanLst;
	}
	public Boolean getDeanMood() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.equals(Constants.DEAN_OF_STRATEGIC))
			{
				if(getInsDataFacade.isCouurseCoordinator(mail))
				{
				
				    
					return true;
					
				}
				else 
				{
					
			return false;
				}
			}
			else 
			{
				
				return false;
			}
		}
		else return false;
	}
	
	
	public Boolean getShowIns() {
		return showIns;
	}
	public void setShowIns(Boolean showIns) {
		this.showIns = showIns;
	}
	public Boolean getShowDean() {
		return showDean;
	}
	public void setShowDean(Boolean showDean) {
		this.showDean = showDean;
	}

	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}

	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public CoursePetitionDTO getSelectedPendingFormsDean() {
		return selectedPendingFormsDean;
	}

	public void setSelectedPendingFormsDean(
			CoursePetitionDTO selectedPendingFormsDean) {
		this.selectedPendingFormsDean = selectedPendingFormsDean;
	}
	public List<InstructorDTO> getInstructors() {
		return instructors;
	}
	public void setInstructors(List<InstructorDTO> instructors) {
		this.instructors = instructors;
	}
	public Integer getSelectedInstructor() {
		return selectedInstructor;
	}
	public void setSelectedInstructor(Integer selectedInstructor) {
		this.selectedInstructor = selectedInstructor;
	}
	
	public void showDetails(CoursePetitionDTO form) {
		//setDetailedForm(form);
		/*formDetail = formsHistoryFacade.getFormDetail(form);
		detailView = true;
		formDetail.setShowMarkAsDone(false);
		StringBuffer	 url = (StringBuffer) ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getRequestURL();
		boolean isDean=url.toString().toLowerCase().contains("dean");
		if( isDean)
		{
			formDetail.setShowRemind(formDetail.getStep().equals(PetitionStepsEnum.INSTRUCTOR));
		}else{
			formDetail.setShowRemind(formDetail.getStep().equals(PetitionStepsEnum.UNDER_REVIEW));
			}*/
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("InstructorCoursePetitionPage1"))
    		{
    			FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=Ins&oldMood=1");
    		}
    		else 
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("formDetails.xhtml?id="+form.getId()+"&cases=Ins");
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
	public IAdmissionDeptAcademicPetFacade getOldPetFacade() {
		return oldPetFacade;
	}
	public void setOldPetFacade(IAdmissionDeptAcademicPetFacade oldPetFacade) {
		this.oldPetFacade = oldPetFacade;
	}
	
}
