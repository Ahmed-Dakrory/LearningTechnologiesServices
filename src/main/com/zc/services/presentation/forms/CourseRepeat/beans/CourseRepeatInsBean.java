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

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatInsFacade;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.IReportsFacade;
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
@ManagedBean(name="CourseRepeatInsBean")
@ViewScoped
public class CourseRepeatInsBean {

	private List<CourseRepeatDTO> pendingForms;
	private List<CourseRepeatDTO> archievedForms;
	private CourseRepeatDTO selectedPendingForms;
	private List<CourseRepeatDTO> filteredPendingForms;
	private List<CourseRepeatDTO> selectedArchievedForms;
	private List<CourseRepeatDTO> filteredArchievedForms;
	private List<CourseRepeatDTO> pendingFormsDean;
	private List<CourseRepeatDTO> archievedFormsDean;
	private CourseRepeatDTO selectedPendingFormsDean;
	private List<CourseRepeatDTO> filteredPendingFormsDean;
	private List<CourseRepeatDTO> selectedArchievedFormsDean;
	private List<CourseRepeatDTO> filteredArchievedFormsDean;
    private List<BaseDTO> userTypesLst;
    private CourseRepeatDTO detailedForm;
    private CourseRepeatDTO detailedFormDean;
    private Integer typeOfUser;    
    private Boolean deanMood;
    private Boolean showIns;

    private String newComment;
    private List<InstructorDTO> instructors;
    private Integer selectedInstructor;
    
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
   	private ISharedAcademicPetFacade sharedAcademicPetFacade;
       
   	@ManagedProperty("#{ICourseRepeatInsFacade}")
   	private ICourseRepeatInsFacade facade;
   	
   	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;

  
   	
   	
    @ManagedProperty("#{FormsHistoryFacadeImpl}")
   	private IFormsHistoryFacade formsHistoryFacade;
       private boolean detailView;
   	private FormDTO formDetail;
   	
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
  	fillInstructorsLst();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4)))
			{
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("courseRepeatFormStudent.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			else if(mail.toLowerCase().equals(Constants.REGISTRAR_HEAD_EMAIL.toLowerCase()))
			{
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("courseRepeatFormStudent.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			else if(mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
			{
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("courseRepeatFormStudent.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			else {
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		StringBuffer url=origRequest.getRequestURL();
		if(url.toString().contains("Dean"))
		{
			setShowIns(true);
			typeOfUser=2;
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
		pendingForms=new ArrayList<CourseRepeatDTO>();
		//TODO skip program advisor step due to Salma Mohsen request
		//fillPendingForms();
		archievedForms=new ArrayList<CourseRepeatDTO>();
		fillArchievedForms();
		pendingFormsDean=new ArrayList<CourseRepeatDTO>();
		fillPendingFormsDean();
		archievedFormsDean=new ArrayList<CourseRepeatDTO>();
		//fillArchievedFormsDean();
		userTypesLst=new ArrayList<BaseDTO>();
		userTypesLst.add(new BaseDTO(1,"Instructor Mode"));
		userTypesLst.add(new BaseDTO(2,"Dean Mode"));
		
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			if(mail.toLowerCase().equals(Constants.DEAN_OF_ACADEMIC.toLowerCase()))
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
			
		}	}
		semesterLst = new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0, "Fall"));
		semesterLst.add(new BaseDTO(1, "Spring"));
		semesterLst.add(new BaseDTO(2, "Summer"));
		yearLst = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			yearLst.add(2013 + i);
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
    public void fillPendingForms()
	{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			pendingForms=  facade.getPendingFormsOfInstructor(getInsDataFacade.getInsByPersonMail(mail).getId());
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
			archievedForms=  facade.getArchievedFormsOfInstructor(getInsDataFacade.getInsByPersonMail(mail).getId());
		    if(archievedForms==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
	
		
	}
	
    public void showDetails(CourseRepeatDTO form)
	{
		

    	try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("courseRepeatFormIns1"))
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
	  public void navigate()
	    {
	    	if(getTypeOfUser()==2)
	    	{
	    	
	    		setShowIns(true);
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("courseRepeatFormDean.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	    	
	    	}
	    	else
	    	{
	    		setShowIns(false);
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("courseRepeatFormIns.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
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
			    	JavaScriptMessagesHandler.RegisterErrorMessage(null, 	"Error In getting Petitions");
			    }
			}
	    }
	  /*  public void fillArchievedFormsDean()
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
	  public void ininttialzieYear(){
			selectedYear=null;
			archievedFormsDean=new ArrayList<CourseRepeatDTO>();
			
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
	    public void showDetailsDean(CourseRepeatDTO dto)
	    {
	    	 try {
		    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    		StringBuffer url=origRequest.getRequestURL();
		    		if(url.toString().contains("addDropDean1"))
		    		{
		    			FacesContext.getCurrentInstance().getExternalContext().redirect
						("formDetails.xhtml?id="+dto.getId()+"&cases=Dean&oldMood=1");
		    		}
		    		else 
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+dto.getId()+"&cases=Dean");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
/*
	     	try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("courseRepeatFormDean1"))
	    		{
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+dto.getId()+"&cases=Dean&oldMood=1");
	    		}
	    		else 
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+dto.getId()+"&cases=Dean");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	 */   }
	    
		public void onRowSelect(SelectEvent event) {  
		  	try {
		  		CourseRepeatDTO selectedDTO=(CourseRepeatDTO) event.getObject();
				showDetails(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
		public void onRowSelectDean(SelectEvent event) {  
		  	try {
		  		CourseRepeatDTO selectedDTO=(CourseRepeatDTO) event.getObject();
				showDetailsDean(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public List<CourseRepeatDTO> getSelectedArchievedForms() {
		return selectedArchievedForms;
	}

	public void setSelectedArchievedForms(
			List<CourseRepeatDTO> selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}

	public List<CourseRepeatDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}

	public void setFilteredArchievedForms(
			List<CourseRepeatDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}

	public List<CourseRepeatDTO> getPendingFormsDean() {
		return pendingFormsDean;
	}

	public void setPendingFormsDean(List<CourseRepeatDTO> pendingFormsDean) {
		this.pendingFormsDean = pendingFormsDean;
	}

	public List<CourseRepeatDTO> getArchievedFormsDean() {
		return archievedFormsDean;
	}

	public void setArchievedFormsDean(List<CourseRepeatDTO> archievedFormsDean) {
		this.archievedFormsDean = archievedFormsDean;
	}

	public CourseRepeatDTO getSelectedPendingFormsDean() {
		return selectedPendingFormsDean;
	}

	public void setSelectedPendingFormsDean(CourseRepeatDTO selectedPendingFormsDean) {
		this.selectedPendingFormsDean = selectedPendingFormsDean;
	}

	public List<CourseRepeatDTO> getFilteredPendingFormsDean() {
		return filteredPendingFormsDean;
	}

	public void setFilteredPendingFormsDean(
			List<CourseRepeatDTO> filteredPendingFormsDean) {
		this.filteredPendingFormsDean = filteredPendingFormsDean;
	}

	public List<CourseRepeatDTO> getSelectedArchievedFormsDean() {
		return selectedArchievedFormsDean;
	}

	public void setSelectedArchievedFormsDean(
			List<CourseRepeatDTO> selectedArchievedFormsDean) {
		this.selectedArchievedFormsDean = selectedArchievedFormsDean;
	}

	public List<CourseRepeatDTO> getFilteredArchievedFormsDean() {
		return filteredArchievedFormsDean;
	}

	public void setFilteredArchievedFormsDean(
			List<CourseRepeatDTO> filteredArchievedFormsDean) {
		this.filteredArchievedFormsDean = filteredArchievedFormsDean;
	}

	public List<BaseDTO> getUserTypesLst() {
		return userTypesLst;
	}

	public void setUserTypesLst(List<BaseDTO> userTypesLst) {
		this.userTypesLst = userTypesLst;
	}

	public CourseRepeatDTO getDetailedForm() {
		return detailedForm;
	}

	public void setDetailedForm(CourseRepeatDTO detailedForm) {
		this.detailedForm = detailedForm;
	}

	public CourseRepeatDTO getDetailedFormDean() {
		return detailedFormDean;
	}

	public void setDetailedFormDean(CourseRepeatDTO detailedFormDean) {
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
			if(mail.toLowerCase().equals(Constants.DEAN_OF_ACADEMIC.toLowerCase()))
			{
				if(getInsDataFacade.isPA(mail))
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

	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}

	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}

	public ICourseRepeatInsFacade getFacade() {
		return facade;
	}

	public void setFacade(ICourseRepeatInsFacade facade) {
		this.facade = facade;
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
		
	}

