/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IInsOverloadRequestFacade;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IReportsFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean(name="InstructorOverloadRequestBean")
@ViewScoped
public class InstructorOverloadRequestBean {

	@ManagedProperty("#{InsOverloadRequestFacadeImpl}")
	private IInsOverloadRequestFacade facade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	 @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
		
	
	private List<OverloadRequestDTO> pendingForms;
	private List<OverloadRequestDTO> archievedForms;
	private OverloadRequestDTO selectedPendingForms;
	private List<OverloadRequestDTO> filteredPendingForms;
	private OverloadRequestDTO selectedArchievedForms;
	private List<OverloadRequestDTO> filteredArchievedForms;
	private List<OverloadRequestDTO> pendingFormsDean;
	private List<OverloadRequestDTO> archievedFormsDean;
	private OverloadRequestDTO selectedPendingFormsDean;
	private List<OverloadRequestDTO> filteredPendingFormsDean;
	private OverloadRequestDTO selectedArchievedFormsDean;
	private List<OverloadRequestDTO> filteredArchievedFormsDean;
	private List<OverloadRequestDTO> pendingFormsProvost;
	private List<OverloadRequestDTO> archievedFormsProvost;
	private OverloadRequestDTO selectedPendingFormsProvost;
	private List<OverloadRequestDTO> filteredPendingFormsProvost;
	private OverloadRequestDTO selectedArchievedFormsProvost;
	private List<OverloadRequestDTO> filteredArchievedFormsProvost;
	private OverloadRequestDTO detailedForm;
    private OverloadRequestDTO detailedFormDean;
    private OverloadRequestDTO detailedFormProvost;
    private Integer typeOfUserDean;
    private Integer typeOfUserProvost; 
    private Boolean deanMood;
    private Boolean showIns;
    private Boolean showDean;
    private Boolean showProvost;
    private Boolean provostMood;
    private List<BaseDTO> userTypesDeanLst;
    private List<BaseDTO> userTypesProvostLst;
    private List<InstructorDTO> instructors;
    private Integer selectedInstructor;
    private String newComment;

    private Boolean  provostRequired;
   
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
   		
try{
	detailView = false;
		detailedForm=new OverloadRequestDTO();
		detailedFormDean=new OverloadRequestDTO();
		detailedFormProvost=new OverloadRequestDTO();
		fillPendingForms();
		fillArchievedForms();
		fillPendingFormsDean();
		//fillArchievedFormsDean();
		fillPendingFormsProvost();
		//fillArchievedFormsProvost();
		fillInstructorsLst();
		userTypesDeanLst=new ArrayList<BaseDTO>();
		userTypesDeanLst.add(new BaseDTO(1,"Instructor Mode"));
		userTypesDeanLst.add(new BaseDTO(2,"Dean Mode"));
		userTypesProvostLst=new ArrayList<BaseDTO>();
		userTypesProvostLst.add(new BaseDTO(1,"Instructor Mode"));
		userTypesProvostLst.add(new BaseDTO(2,"Provost Mode"));
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		StringBuffer url=origRequest.getRequestURL();
		
		if(url.toString().contains("Dean"))
		{
			setShowDean(true);
			
			typeOfUserDean=2;
			setShowIns(false);
		}
		else if(url.toString().contains("Provost"))
		{
			setShowProvost(true);
			
			typeOfUserProvost=2;
			setShowIns(false);
		}
		else {
			setShowIns(true);
		}
		try
		{
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			
			if(origRequest.getParameterValues("action")[0].equals("approveIns"))
			{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is approved successfully !" );
			setSelectedPendingForms(facade.getById(dtoID));
			}
			else if(origRequest.getParameterValues("action")[0].equals("refuseIns"))
			{
				setSelectedPendingForms(facade.getById(dtoID));
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is Refused successfully !" );
			}
			else if(origRequest.getParameterValues("action")[0].equals("approveDean"))
			{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is approved successfully !" );
			setSelectedPendingFormsDean(facade.getById(dtoID));
			}
			else if(origRequest.getParameterValues("action")[0].equals("refuseDean"))
			{
				setSelectedPendingFormsDean(facade.getById(dtoID));
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
			Integer forwaredInsID=Integer.parseInt(origRequest.getParameterValues("forwaredIns")[0]);
			InstructorDTO forwardedIns=getInsDataFacade.getInsByPersonID(forwaredInsID);
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is forwarded successfully to : "+forwardedIns.getName() );
			
		}
		catch(Exception ex)
		{
			ex.toString();	
		}
	}
	catch(Exception ex)
	{
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed please you have to login first");
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
		pendingForms=new ArrayList<OverloadRequestDTO>();
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
	  //TODO mode=1 > DEAN , mode=2 >Provost
	  public void ininttialzieYear(int mode){
			selectedYear=null;
			if(mode==1)
			archievedFormsDean=new ArrayList<OverloadRequestDTO>();
			else 
				if(mode==2)
					archievedFormsProvost=new ArrayList<OverloadRequestDTO>();
		}
	  public void getPetitionsBySemester(int mode){
			if(getSelectedYear()!=null){
				if(mode==1)
			{
					if(getSelectedSemester()==0)
			
			archievedFormsDean=reportsFacade.getOldFall(getSelectedYear());
			else if(getSelectedSemester()==1)
				archievedFormsDean=reportsFacade.getOldSpring(getSelectedYear());
			else if(getSelectedSemester()==2)
				archievedFormsDean=reportsFacade.getOldSummer(getSelectedYear());
			}
				else if(mode==2)
			{
					if(getSelectedSemester()==0)
			
			archievedFormsProvost=reportsFacade.getOldFall(getSelectedYear());
			else if(getSelectedSemester()==1)
				archievedFormsProvost=reportsFacade.getOldSpring(getSelectedYear());
			else if(getSelectedSemester()==2)
				archievedFormsProvost=reportsFacade.getOldSummer(getSelectedYear());
			}
			}
		}
	  public void exportExcelReport(int mode){
			if(mode==1)
			{  if(getArchievedFormsDean()!=null)
				if(getArchievedFormsDean().size()>0)
					reportsFacade.generateExcelByList(getArchievedFormsDean());
			}
			if(mode==2)
			{  if(getArchievedFormsProvost()!=null)
				if(getArchievedFormsProvost().size()>0)
					reportsFacade.generateExcelByList(getArchievedFormsProvost());
			}
		}
	    public void fillPendingFormsProvost()
	    {
	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				
				pendingFormsProvost=  facade.getPendingFormsOfProvost();
			    if(pendingFormsProvost==null)
			    {
			    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
			    }
			}
	    }
/*	    public void fillArchievedFormsProvost()
	    {
	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				
				archievedFormsProvost=  facade.getArchievedFormsOfProvost();
			    if(archievedFormsProvost==null)
			    {
			    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
			    }
			}
	    }*/
	    

	    public void showDetailsDean(OverloadRequestDTO form)
		{
	    	try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("overloadRequestDean1"))
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
	    public void showDetailsProvost(OverloadRequestDTO form)
		{
	    	try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("overloadRequestProvost1"))
	    		{
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
	    			("formDetails.xhtml?id="+form.getId()+"&cases=Provost&oldMood=1");
	    		}
	    		else 
	    		FacesContext.getCurrentInstance().getExternalContext().redirect
	    		("formDetails.xhtml?id="+form.getId()+"&cases=Provost");
	    	} catch (IOException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	
		
		}  
	
		public void onRowSelect(SelectEvent event) {  
		  	try {
		  		OverloadRequestDTO selectedDTO=(OverloadRequestDTO) event.getObject();
				showDetails(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
		
		public void onRowSelectDean(SelectEvent event) {  
		  	try {
		  		OverloadRequestDTO selectedDTO=(OverloadRequestDTO) event.getObject();
				showDetailsDean(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
		
		public void onRowSelectProvost(SelectEvent event) {  
		  	try {
		  		OverloadRequestDTO selectedDTO=(OverloadRequestDTO) event.getObject();
				showDetailsProvost(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
		
	    public void navigateDean()
	    {
	    	if(getTypeOfUserDean()==2)
	    	{
	    		setShowDean(true);
	    		setShowIns(false);
	    		setShowProvost(false);
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("overloadRequestDean.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	    	
	    	}
	    	else
	    	{
	    		setShowDean(false);
	    		setShowIns(true);
	    		setShowProvost(false);
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("overloadRequestInstructor.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	    		
	    	
	    	}
	    }
	    public void navigateProvost()
	    {
	    	if(getTypeOfUserProvost()==2)
	    	{
	    		setShowProvost(true);
	    		setShowIns(false);
	    		setShowDean(false);
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("overloadRequestProvost.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	    	
	    	}
	    	else
	    	{
	    		setShowProvost(false);
	    		setShowIns(true);
	    		setShowDean(false);
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("overloadRequestInstructor.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	    		
	    	
	    	}
	    }

    public IInsOverloadRequestFacade getFacade() {
		return facade;
	}
	public void setFacade(IInsOverloadRequestFacade facade) {
		this.facade = facade;
	}

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
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
	
	public List<OverloadRequestDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(
			List<OverloadRequestDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}

	public List<OverloadRequestDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<OverloadRequestDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public List<OverloadRequestDTO> getPendingFormsDean() {
		return pendingFormsDean;
	}
	public void setPendingFormsDean(List<OverloadRequestDTO> pendingFormsDean) {
		this.pendingFormsDean = pendingFormsDean;
	}
	public List<OverloadRequestDTO> getArchievedFormsDean() {
		return archievedFormsDean;
	}
	public void setArchievedFormsDean(List<OverloadRequestDTO> archievedFormsDean) {
		this.archievedFormsDean = archievedFormsDean;
	}
	
	public List<OverloadRequestDTO> getFilteredPendingFormsDean() {
		return filteredPendingFormsDean;
	}
	public void setFilteredPendingFormsDean(
			List<OverloadRequestDTO> filteredPendingFormsDean) {
		this.filteredPendingFormsDean = filteredPendingFormsDean;
	}

	public List<OverloadRequestDTO> getFilteredArchievedFormsDean() {
		return filteredArchievedFormsDean;
	}
	public void setFilteredArchievedFormsDean(
			List<OverloadRequestDTO> filteredArchievedFormsDean) {
		this.filteredArchievedFormsDean = filteredArchievedFormsDean;
	}
	public List<OverloadRequestDTO> getPendingFormsProvost() {
		return pendingFormsProvost;
	}
	public void setPendingFormsProvost(List<OverloadRequestDTO> pendingFormsProvost) {
		this.pendingFormsProvost = pendingFormsProvost;
	}
	public List<OverloadRequestDTO> getArchievedFormsProvost() {
		return archievedFormsProvost;
	}
	public void setArchievedFormsProvost(
			List<OverloadRequestDTO> archievedFormsProvost) {
		this.archievedFormsProvost = archievedFormsProvost;
	}
	
	public OverloadRequestDTO getSelectedPendingFormsProvost() {
		return selectedPendingFormsProvost;
	}
	public void setSelectedPendingFormsProvost(
			OverloadRequestDTO selectedPendingFormsProvost) {
		this.selectedPendingFormsProvost = selectedPendingFormsProvost;
	}
	public List<OverloadRequestDTO> getFilteredPendingFormsProvost() {
		return filteredPendingFormsProvost;
	}
	public void setFilteredPendingFormsProvost(
			List<OverloadRequestDTO> filteredPendingFormsProvost) {
		this.filteredPendingFormsProvost = filteredPendingFormsProvost;
	}

	public OverloadRequestDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}

	public void setSelectedArchievedForms(OverloadRequestDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}

	public OverloadRequestDTO getSelectedArchievedFormsDean() {
		return selectedArchievedFormsDean;
	}

	public void setSelectedArchievedFormsDean(
			OverloadRequestDTO selectedArchievedFormsDean) {
		this.selectedArchievedFormsDean = selectedArchievedFormsDean;
	}

	public OverloadRequestDTO getSelectedArchievedFormsProvost() {
		return selectedArchievedFormsProvost;
	}

	public void setSelectedArchievedFormsProvost(
			OverloadRequestDTO selectedArchievedFormsProvost) {
		this.selectedArchievedFormsProvost = selectedArchievedFormsProvost;
	}

	public List<OverloadRequestDTO> getFilteredArchievedFormsProvost() {
		return filteredArchievedFormsProvost;
	}
	public void setFilteredArchievedFormsProvost(
			List<OverloadRequestDTO> filteredArchievedFormsProvost) {
		this.filteredArchievedFormsProvost = filteredArchievedFormsProvost;
	}
	public OverloadRequestDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(OverloadRequestDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public OverloadRequestDTO getDetailedFormDean() {
		return detailedFormDean;
	}
	public void setDetailedFormDean(OverloadRequestDTO detailedFormDean) {
		this.detailedFormDean = detailedFormDean;
	}
	public OverloadRequestDTO getDetailedFormProvost() {
		return detailedFormProvost;
	}
	public void setDetailedFormProvost(OverloadRequestDTO detailedFormProvost) {
		this.detailedFormProvost = detailedFormProvost;
	}

	public Boolean getDeanMood() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.equals(Constants.DEAN_OF_STRATEGIC))
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
	public Boolean getProvostMood() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.equals(Constants.PROVOST))
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
	public void setProvostMood(Boolean provostMood) {
		this.provostMood = provostMood;
	}
	public List<BaseDTO> getUserTypesDeanLst() {
		return userTypesDeanLst;
	}
	public void setUserTypesDeanLst(List<BaseDTO> userTypesDeanLst) {
		this.userTypesDeanLst = userTypesDeanLst;
	}
	public List<BaseDTO> getUserTypesProvostLst() {
		return userTypesProvostLst;
	}
	public void setUserTypesProvostLst(List<BaseDTO> userTypesProvostLst) {
		this.userTypesProvostLst = userTypesProvostLst;
	}
	public Integer getTypeOfUserDean() {
		return typeOfUserDean;
	}
	public void setTypeOfUserDean(Integer typeOfUserDean) {
		this.typeOfUserDean = typeOfUserDean;
	}
	public Integer getTypeOfUserProvost() {
		return typeOfUserProvost;
	}
	public void setTypeOfUserProvost(Integer typeOfUserProvost) {
		this.typeOfUserProvost = typeOfUserProvost;
	}
	public Boolean getShowDean() {
		return showDean;
	}
	public void setShowDean(Boolean showDean) {
		this.showDean = showDean;
	}
	public Boolean getShowProvost() {
		return showProvost;
	}
	public void setShowProvost(Boolean showProvost) {
		this.showProvost = showProvost;
	}


	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}
	public Boolean getProvostRequired() {
		return provostRequired;
	}

	public void setProvostRequired(Boolean provostRequired) {
		this.provostRequired = provostRequired;
	}
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}
	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}
	public OverloadRequestDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(OverloadRequestDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public OverloadRequestDTO getSelectedPendingFormsDean() {
		return selectedPendingFormsDean;
	}
	public void setSelectedPendingFormsDean(
			OverloadRequestDTO selectedPendingFormsDean) {
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
	
	public void showDetails(OverloadRequestDTO form) {
		
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("overloadRequestInstructor1"))
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

