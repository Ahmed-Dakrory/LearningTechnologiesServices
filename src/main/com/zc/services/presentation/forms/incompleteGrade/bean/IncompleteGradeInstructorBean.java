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

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeInsFacade;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IReportsFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean(name="IncompleteGradeInstructorBean")
@ViewScoped
public class IncompleteGradeInstructorBean {

   	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
   	
	@ManagedProperty("#{IIncompleteGradeInsFacade}")
	private IIncompleteGradeInsFacade facade;

    @ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
    
	private List<IncompleteGradeDTO> pendingForms;
	private List<IncompleteGradeDTO> archievedForms;
	private IncompleteGradeDTO selectedPendingForms;
	private List<IncompleteGradeDTO> filteredPendingForms;
	private IncompleteGradeDTO selectedArchievedForms;
	private List<IncompleteGradeDTO> filteredArchievedForms;
	private List<IncompleteGradeDTO> pendingFormsDean;
	private List<IncompleteGradeDTO> archievedFormsDean;
	private IncompleteGradeDTO selectedPendingFormsDean;
	private List<IncompleteGradeDTO> filteredPendingFormsDean;
	private IncompleteGradeDTO selectedArchievedFormsDean;
	private List<IncompleteGradeDTO> filteredArchievedFormsDean;
    private List<BaseDTO> userTypesLst;
    private IncompleteGradeDTO detailedForm;
    private IncompleteGradeDTO detailedFormDean;
    private Integer typeOfUser;    
    private Boolean deanMood;
    private Boolean showIns;
    private List<BaseDTO> userTypesDeanLst;
    private String newComment;
    private List<InstructorDTO> instructors;
    private Integer selectedInstructor;
    private Integer typeOfUserDean;
    private boolean oldMood;
    private FormDTO formDetail;
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
			try{
		fillInstructorsLst();
		fillPendingFormsIns();
		fillArchievedFormsIns();
			}
			catch(Exception ex)
			{
				ex.toString();
			}
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
		try{
			StringBuffer url=origRequest.getRequestURL();
			if(url.toString().contains("Dean"))
			{
				setShowIns(true);
				typeOfUser=2;
				fillPendingFormsDean();
				//fillArchFormsDean();
				
			}
			
		}
		catch(Exception ex)
		{
			ex.toString();
		}
		userTypesDeanLst=new ArrayList<BaseDTO>();
		userTypesDeanLst.add(new BaseDTO(1,"Instructor Mode"));
		userTypesDeanLst.add(new BaseDTO(2,"Dean Mode"));
	StringBuffer url = origRequest.getRequestURL();
		if (url.toString().contains("Dean")) {
			setShowIns(true);
			typeOfUserDean = 2;
		}
		}
		catch(Exception ex){
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
	public void fillInstructorsLst()
	    {
		 instructors=new ArrayList<InstructorDTO>();
	     instructors=facade.fillInsLst();
	     for(int i=0;i<instructors.size();i++)
	     {
	    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	 		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	 		{
	 			
	 			String mail = authentication.getName();
	    	 if(getInsDataFacade.getInsByPersonMail(mail).getId().equals(instructors.get(i).getId()))
	    	 {
	    		 instructors.remove(i);
	    	 }
	 		}
	 		
	     }
	    }
	public void fillPendingFormsIns()
		{
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				String mail = authentication.getName();
				List<IncompleteGradeDTO> instructorList=  facade.getPendingFormsOfInstructor(getInsDataFacade.getInsByPersonMail(mail).getId());
				
				//TODO Updating the scenario of incomplete grade form "Remove Major Head Step"
				/*List<IncompleteGradeDTO> paList=  facade.getPendingFormsOfPA(getInsDataFacade.getInsByPersonMail(mail).getId());
				
			    
			    	for(int j=0;j<paList.size();j++)
			    	{
			    		boolean exists=false;
			    		if(instructorList.size()>0)
			    		{
			    		  for(int i=0;i<instructorList.size();i++)
			    		  {
			    			  if(instructorList.get(i).getId().equals(paList.get(j).getId()))
			    			  {
			    				  exists=true;break;
			    			  }
			    		  }	
			    		  if(!exists){
			    				instructorList.add(paList.get(j));
			    		  }
			    		}
			    		else
			    		instructorList.add(paList.get(j));
			    	}
			   */
			    pendingForms=instructorList;
			    if(pendingForms==null)
			    {
			    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
			    }
			}
		}
	    
	public void fillArchievedFormsIns()
		{
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				String mail = authentication.getName();
				List<IncompleteGradeDTO> instructorList=  facade.getArchievedFormsOfInstructor(getInsDataFacade.getInsByPersonMail(mail).getId());
				
				//TODO Updating the scenario of incomplete grade form "Remove Major Head Step"
				
				/*List<IncompleteGradeDTO> paList=  facade.getArchievedFormsOfPA(getInsDataFacade.getInsByPersonMail(mail).getId());

		    	for(int j=0;j<paList.size();j++)
		    	{
		    		instructorList.add(paList.get(j));
		    	}*/
		    	archievedForms=instructorList;
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
			
			
			
			pendingFormsDean=  facade.getPendingFormsOfDean();
		
		   
		  
		    if(pendingFormsDean==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
	}
/*	public void fillArchFormsDean()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
		
			archievedFormsDean=  facade.getArchievedFormsOfDean();
		
		   
		  
		    if(archievedFormsDean==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
	}*/
	  
	    public void navigateDean()
	    {
	    	if(getTypeOfUserDean()==2)
	    	{
	    	
	    		setShowIns(true);
	    	
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("incompleteGradeDean.xhtml");
				} 
	    		catch (IOException e) {
					
					e.printStackTrace();
				}
	    	
	    	}
	    	else
	    	{
	    		
	    		setShowIns(false);
	    		
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("incompleteGradeIns.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	    		
	    	
	    	}
	    }   
	    public void showDetails(IncompleteGradeDTO form) 
	    {
	    	try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("incompleteGradeIns1"))
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
	    public void showDetailsDean(IncompleteGradeDTO form) 
	    {
	    	try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("incompleteGradeDean1"))
	    		{
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+form.getId()+"&cases=Dean&oldMood=1");
	    		}
	    		else 
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=Dean");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
	    public void ininttialzieYear(){
			selectedYear=null;
			archievedFormsDean=new ArrayList<IncompleteGradeDTO>();
		}
	  public void getPetitionsBySemester(){
			if(getSelectedYear()!=null){
			if(getSelectedSemester()==0)
			archievedFormsDean=reportsFacade.getOldFall(getSelectedYear());
			else if(getSelectedSemester()==1)
				archievedFormsDean=reportsFacade.getOldSpring(getSelectedYear());
			else if(getSelectedSemester()==2)
				archievedFormsDean=reportsFacade.getOldSummer(getSelectedYear());
			}
		}
	  public void exportExcelReport(){
			if(getArchievedForms()!=null)
				if(getArchievedForms().size()>0)
					reportsFacade.generateExcelByList(getArchievedFormsDean());
		}
	    public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
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
	    public void onRowSelectDean(SelectEvent event) {  
		  	try {
		  		IncompleteGradeDTO selectedDTO=(IncompleteGradeDTO) event.getObject();
				showDetailsDean(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public IIncompleteGradeInsFacade getFacade() {
		return facade;
	}

	public void setFacade(IIncompleteGradeInsFacade facade) {
		this.facade = facade;
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
	
	public List<IncompleteGradeDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<IncompleteGradeDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public List<IncompleteGradeDTO> getPendingFormsDean() {
		return pendingFormsDean;
	}
	public void setPendingFormsDean(List<IncompleteGradeDTO> pendingFormsDean) {
		this.pendingFormsDean = pendingFormsDean;
	}
	public List<IncompleteGradeDTO> getArchievedFormsDean() {
		return archievedFormsDean;
	}
	public void setArchievedFormsDean(List<IncompleteGradeDTO> archievedFormsDean) {
		this.archievedFormsDean = archievedFormsDean;
	}
	public IncompleteGradeDTO getSelectedPendingFormsDean() {
		return selectedPendingFormsDean;
	}
	public void setSelectedPendingFormsDean(
			IncompleteGradeDTO selectedPendingFormsDean) {
		this.selectedPendingFormsDean = selectedPendingFormsDean;
	}
	public List<IncompleteGradeDTO> getFilteredPendingFormsDean() {
		return filteredPendingFormsDean;
	}
	public void setFilteredPendingFormsDean(
			List<IncompleteGradeDTO> filteredPendingFormsDean) {
		this.filteredPendingFormsDean = filteredPendingFormsDean;
	}

	public IncompleteGradeDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(IncompleteGradeDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public IncompleteGradeDTO getSelectedArchievedFormsDean() {
		return selectedArchievedFormsDean;
	}
	public void setSelectedArchievedFormsDean(
			IncompleteGradeDTO selectedArchievedFormsDean) {
		this.selectedArchievedFormsDean = selectedArchievedFormsDean;
	}
	public List<IncompleteGradeDTO> getFilteredArchievedFormsDean() {
		return filteredArchievedFormsDean;
	}
	public void setFilteredArchievedFormsDean(
			List<IncompleteGradeDTO> filteredArchievedFormsDean) {
		this.filteredArchievedFormsDean = filteredArchievedFormsDean;
	}
	public List<BaseDTO> getUserTypesLst() {
		return userTypesLst;
	}
	public void setUserTypesLst(List<BaseDTO> userTypesLst) {
		this.userTypesLst = userTypesLst;
	}
	public IncompleteGradeDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(IncompleteGradeDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public IncompleteGradeDTO getDetailedFormDean() {
		return detailedFormDean;
	}
	public void setDetailedFormDean(IncompleteGradeDTO detailedFormDean) {
		this.detailedFormDean = detailedFormDean;
	}
	public Integer getTypeOfUser() {
		return typeOfUser;
	}
	public void setTypeOfUser(Integer typeOfUser) {
		this.typeOfUser = typeOfUser;
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
	public void setDeanMood(Boolean deanMood) {
		this.deanMood = deanMood;
	}
	public Boolean getShowIns() {
		return showIns;
	}
	public void setShowIns(Boolean showIns) {
		this.showIns = showIns;
	}
	public String getNewComment() {
		return newComment;
	}
	public void setNewComment(String newComment) {
		this.newComment = newComment;
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
	public List<BaseDTO> getUserTypesDeanLst() {
		return userTypesDeanLst;
	}
	public void setUserTypesDeanLst(List<BaseDTO> userTypesDeanLst) {
		this.userTypesDeanLst = userTypesDeanLst;
	}
	public Integer getTypeOfUserDean() {
		return typeOfUserDean;
	}
	public void setTypeOfUserDean(Integer typeOfUserDean) {
		this.typeOfUserDean = typeOfUserDean;
	}
	
	public FormDTO getFormDetail() {
		return formDetail;
	}
	public void setFormDetail(FormDTO formDetail) {
		this.formDetail = formDetail;
	}
	public IFormsHistoryFacade getFormsHistoryFacade() {
		return formsHistoryFacade;
	}
	public void setFormsHistoryFacade(IFormsHistoryFacade formsHistoryFacade) {
		this.formsHistoryFacade = formsHistoryFacade;
	}
	public boolean isOldMood() {
		return oldMood;
	}
	public void setOldMood(boolean oldMood) {
		this.oldMood = oldMood;
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
