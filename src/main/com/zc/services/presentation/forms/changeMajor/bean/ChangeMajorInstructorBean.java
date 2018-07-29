/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.bean;

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
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeMajor.facade.IReportsFacade;
import main.com.zc.services.presentation.forms.changeMajor.facade.IchangeMajorInsFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
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
@ManagedBean(name="ChangeMajorInstructorBean")
@ViewScoped
public class ChangeMajorInstructorBean {

	private List<ChangeMajorDTO> pendingForms;
	private List<ChangeMajorDTO> archievedForms;
	private ChangeMajorDTO selectedPendingForms;
	private List<ChangeMajorDTO> filteredPendingForms;
	private ChangeMajorDTO selectedArchievedForms;
	private List<ChangeMajorDTO> filteredArchievedForms;
	private List<ChangeMajorDTO> pendingFormsDean;
	private List<ChangeMajorDTO> archievedFormsDean;
	private ChangeMajorDTO selectedPendingFormsDean;
	private List<ChangeMajorDTO> filteredPendingFormsDean;
	private List<ChangeMajorDTO> selectedArchievedFormsDean;
	private List<ChangeMajorDTO> filteredArchievedFormsDean;
    private List<BaseDTO> userTypesLst;
    private ChangeMajorDTO detailedForm;
    private ChangeMajorDTO detailedFormDean;
    private Integer typeOfUser;    
    private Boolean deanMood;
    private Boolean showIns;

    private String newComment;
    private List<InstructorDTO> instructors;
    private Integer selectedInstructor;
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    
	@ManagedProperty("#{ChangeMajorInsFacadeImpl}")
	private IchangeMajorInsFacade facade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
   
	@ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
	private boolean detailView;
	private FormDTO formDetail;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;	
	
	@ManagedProperty("#{ReportsFacadeChangeMajorImpl}")
	private IReportsFacade reportsFacade;
	@PostConstruct
	public void init()
	{
		detailView = false;
		fillInstructorsLst();
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		StringBuffer url=origRequest.getRequestURL();
		if(url.toString().contains("Dean"))
		{
			setShowIns(true);
			typeOfUser=2;
		}
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
		pendingForms=new ArrayList<ChangeMajorDTO>();
		fillPendingForms();
		archievedForms=new ArrayList<ChangeMajorDTO>();
		fillArchievedForms();
		pendingFormsDean=new ArrayList<ChangeMajorDTO>();
		fillPendingFormsDean();
		archievedFormsDean=new ArrayList<ChangeMajorDTO>();
		//fillArchievedFormsDean();
		userTypesLst=new ArrayList<BaseDTO>();
		userTypesLst.add(new BaseDTO(1,"Instructor Mode"));
		userTypesLst.add(new BaseDTO(2,"Dean Mode"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
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
	public void showDetails(ChangeMajorDTO form)
	{
		
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("changeMajorIns1"))
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
    		/*fillPendingFormsDean();
    		fillArchievedFormsDean();*/
    		setShowIns(true);
    		try {
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("changeMajorDean.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	/*	 FacesContext.getCurrentInstance().getPartialViewContext()
 			.getRenderIds().add("mainForm:form:pendingDatatable3");
    		 FacesContext.getCurrentInstance().getPartialViewContext()
 			.getRenderIds().add("mainForm:form:pendingDatatable2");*/
    		//return "/pages/secured/forms/dropAndAdd/addDropDean.xhtml?faces-redirect=true";
    	}
    	else/* if(getTypeOfUser()==1)*/
    	{
    		setShowIns(false);
    		try {
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("changeMajorIns.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	/*	fillPendingForms();
    		fillArchievedForms();
    		 FacesContext.getCurrentInstance().getPartialViewContext()
  			.getRenderIds().add("mainForm:form:pendingDatatable");
     		 FacesContext.getCurrentInstance().getPartialViewContext()
  			.getRenderIds().add("mainForm:form:pendingDatatable1");*/
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
		archievedFormsDean=new ArrayList<ChangeMajorDTO>();
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
    public void showDetailsDean(ChangeMajorDTO dto)
    {
    	/*RequestContext.getCurrentInstance().reset("detFormDean:detGridDean");
    	setDetailedFormDean(dto);
    	
    	RequestContext.getCurrentInstance().execute("detDeanDlg.show();");
		 FacesContext.getCurrentInstance().getPartialViewContext()
			.getRenderIds().add("detFormDean:detGridDean");
		 StringBuffer url = (StringBuffer) ((HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest())
					.getRequestURL();
			if (!url.toString().contains("1")) {
				FacesContext.getCurrentInstance().getPartialViewContext()
						.getRenderIds().add("detFormDean:dialogBtn");
				RequestContext.getCurrentInstance().reset("detFormDean:dialogBtn");
			}*/
    	try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("changeMajorDean1"))
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
		 
    }
    
    public void onRowSelect(SelectEvent event) {  
	  	try {
	  		ChangeMajorDTO selectedDTO=(ChangeMajorDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
    public void onRowSelectDean(SelectEvent event) {  
	  	try {
	  		ChangeMajorDTO selectedDTO=(ChangeMajorDTO) event.getObject();
			showDetailsDean(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
    
	/*public void addComment(ChangeMajorDTO dto)
	{		
		  if (newComment == null || newComment.equals("")) {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Please, Write Your Comment");
			}else{	try {
			
				dto=constructComment(dto);
				facade.addComment(dto.getId(), dto.getComment());
				getFormDetail().setComment(dto.getComment());
			} catch (Exception e) {
				e.printStackTrace();
	    		JavaScriptMessagesHandler.RegisterErrorMessage(null,"System Error");
		
			}	}		
		}

		 public ChangeMajorDTO constructComment( ChangeMajorDTO dto){
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String date = sdf.format(Calendar.getInstance().getTime());
				
				String addedComment="";
				if(dto.getComment()!=null)			
					addedComment = dto.getComment()+"\n";
				
				addedComment +=  newComment+" by :"+getInsDataFacade.getInsByPersonMail(authentication.getName()).getName()+" (Date: "+date+" )";		
				dto.setComment(addedComment);
				newComment = "";
		return dto;
		 }*/
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
	
	 /* public void forwardPetition()
	  {
		   if (getSelectedInstructor() == null || getSelectedInstructor() == 0) {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Please, Select Instructor To forward");
			} else {
		
		   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try
	    	{
	    		ChangeMajorDTO dto=getDetailedForm();
	    		InstructorDTO forwardIns=new InstructorDTO();
	    		forwardIns.setId(getSelectedInstructor());
	    		dto.setForwardIns(forwardIns);
	    		if(newComment!=null && !newComment.equals(""))
			     { dto=constructComment(dto);
			     	getFormDetail().setComment(dto.getComment());
			     }
	    		dto=facade.forwardPetition(dto);
	    		if(dto!=null)
	    		{
	    			for(int i=0;i<instructors.size();i++)
	    			{
	    				if(instructors.get(i).getId().equals(getSelectedInstructor()))
	    				{
	    				  fillPendingForms();
	    				  detailView = false;
		    				JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is forwarded successfully to : "+instructors.get(i).getName() );
	    					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"You can find it in old petitions section");
	    					break;
	    				}
	    			}
	    			
	    			
	    		}
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    		JavaScriptMessagesHandler.RegisterErrorMessage(null,"System Error");
				
	    	}}
	    	}
	   }*/
	  public IchangeMajorInsFacade getFacade() {
		return facade;
	}

	public void setFacade(IchangeMajorInsFacade facade) {
		this.facade = facade;
	}

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public List<ChangeMajorDTO> getPendingForms() {
		return pendingForms;
	}

	public void setPendingForms(List<ChangeMajorDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}

	public List<ChangeMajorDTO> getArchievedForms() {
		return archievedForms;
	}

	public void setArchievedForms(List<ChangeMajorDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}

	

	public List<ChangeMajorDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}

	public void setFilteredPendingForms(List<ChangeMajorDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}



	public ChangeMajorDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(ChangeMajorDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<ChangeMajorDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}

	public void setFilteredArchievedForms(
			List<ChangeMajorDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}

	public List<ChangeMajorDTO> getPendingFormsDean() {
		return pendingFormsDean;
	}

	public void setPendingFormsDean(List<ChangeMajorDTO> pendingFormsDean) {
		this.pendingFormsDean = pendingFormsDean;
	}

	public List<ChangeMajorDTO> getArchievedFormsDean() {
		return archievedFormsDean;
	}

	public void setArchievedFormsDean(List<ChangeMajorDTO> archievedFormsDean) {
		this.archievedFormsDean = archievedFormsDean;
	}

	

	public ChangeMajorDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}

	public void setSelectedPendingForms(ChangeMajorDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}

	public ChangeMajorDTO getSelectedPendingFormsDean() {
		return selectedPendingFormsDean;
	}

	public void setSelectedPendingFormsDean(ChangeMajorDTO selectedPendingFormsDean) {
		this.selectedPendingFormsDean = selectedPendingFormsDean;
	}

	public List<ChangeMajorDTO> getFilteredPendingFormsDean() {
		return filteredPendingFormsDean;
	}

	public void setFilteredPendingFormsDean(
			List<ChangeMajorDTO> filteredPendingFormsDean) {
		this.filteredPendingFormsDean = filteredPendingFormsDean;
	}

	public List<ChangeMajorDTO> getSelectedArchievedFormsDean() {
		return selectedArchievedFormsDean;
	}

	public void setSelectedArchievedFormsDean(
			List<ChangeMajorDTO> selectedArchievedFormsDean) {
		this.selectedArchievedFormsDean = selectedArchievedFormsDean;
	}

	public List<ChangeMajorDTO> getFilteredArchievedFormsDean() {
		return filteredArchievedFormsDean;
	}

	public void setFilteredArchievedFormsDean(
			List<ChangeMajorDTO> filteredArchievedFormsDean) {
		this.filteredArchievedFormsDean = filteredArchievedFormsDean;
	}

	public List<BaseDTO> getUserTypesLst() {
		return userTypesLst;
	}

	public void setUserTypesLst(List<BaseDTO> userTypesLst) {
		this.userTypesLst = userTypesLst;
	}

	public ChangeMajorDTO getDetailedForm() {
		return detailedForm;
	}

	public void setDetailedForm(ChangeMajorDTO detailedForm) {
		this.detailedForm = detailedForm;
	}

	public ChangeMajorDTO getDetailedFormDean() {
		return detailedFormDean;
	}

	public void setDetailedFormDean(ChangeMajorDTO detailedFormDean) {
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
			if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()))
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
	
	 public void downloadAttachments(ChangeMajorDTO form)
		{
			AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
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
	/*public void showDetails(ChangeMajorDTO form) {
		setDetailedForm(form);
		formDetail = formsHistoryFacade.getFormDetail(form);
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
			}
		
	}*/
/*	public void approve(){
		StringBuffer	 url = (StringBuffer) ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getRequestURL();
		boolean isDean=url.toString().toLowerCase().contains("dean");
		if( isDean)
		{
			approveActionDean();
		}else{
			approveAction();
		}
	}
	public void refuse(){
		StringBuffer	 url = (StringBuffer) ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getRequestURL();
		boolean isDean=url.toString().toLowerCase().contains("dean");
		if( isDean)
		{
			refuseActionDean();
		}else{
			refuseAction();
		}
	}*/
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
