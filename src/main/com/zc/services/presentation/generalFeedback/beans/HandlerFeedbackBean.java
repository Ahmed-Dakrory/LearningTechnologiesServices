/**
 * 
 */
package main.com.zc.services.presentation.generalFeedback.beans;

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

import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;
import main.com.zc.services.presentation.generalFeedback.facade.IFeedbacksFacade;
import main.com.zc.services.presentation.shared.facade.IMajorFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */


@ManagedBean
@ViewScoped
public class HandlerFeedbackBean {

	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	

	@ManagedProperty("#{feedbacksFacadeImpl}")
	private IFeedbacksFacade facade;
	
	@ManagedProperty("#{IMajorFacade}")
	private IMajorFacade majorFacade;
	
	private List<FeedbackDTO> pendingForms;
	private List<FeedbackDTO> archievedForms;
	private FeedbackDTO selectedPendingForms;
	private List<FeedbackDTO> filteredPendingForms;
	private FeedbackDTO selectedArchievedForms;
	private List<FeedbackDTO> filteredArchievedForms;
	private FeedbackDTO detailedForm;
	@PostConstruct
	public void init()
	{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			if(!authentication.getName().startsWith("S-")&&!authentication.getName().startsWith("s-")&&!StringUtils.isNumeric(authentication.getName().substring(0, 4)))
			{
			if(authentication.getName().equals("lts-admin@zewailcity.edu.eg")){
				fillPendingForms();
				fillArchievedForms();
				HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
				StringBuffer url=origRequest.getRequestURL();
			
				//HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
						setSelectedPendingForms(facade.getById(dtoID));
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
		}}
	}
	public void fillPendingForms()
	{
		pendingForms=new ArrayList<FeedbackDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(!mail.startsWith("S-")&&!mail.startsWith("s-")&&!StringUtils.isNumeric(mail.substring(0, 4)))
			{try{
				
				pendingForms=  facade.getPendingHandler();
			
			}
			catch(Exception ex)
			{
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting feedbacks");
				ex.toString();
			}
			if(pendingForms==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting feedbacks");
		    }}
		}
	}
	
	  public void fillArchievedForms()
	{
		  archievedForms=new ArrayList<FeedbackDTO>();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				String mail = authentication.getName();
				if(!mail.startsWith("S-")&&!mail.startsWith("s-")&&!StringUtils.isNumeric(mail.substring(0, 4)))
				{try{
					
				archievedForms=  facade.getOldHandler();
				}
				catch(Exception ex)
				{
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting feedbacks");
					ex.toString();
				}
				if(archievedForms==null)
			    {
			    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting feedbacks");
			    }}
			}
		
	}
	  public void showDetails(FeedbackDTO form) 
	    {
	    	try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("feedbackHandlerOld"))
	    		{
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("detailedFeedback.xhtml?id="+form.getId()+"&cases=handler&oldMood=1");
	    		}
	    		else 
				FacesContext.getCurrentInstance().getExternalContext().redirect("detailedFeedback.xhtml?id="+form.getId()+"&cases=handler");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 public void onRowSelect(SelectEvent event) {  
			  	try {
			  		FeedbackDTO selectedDTO=(FeedbackDTO) event.getObject();
					showDetails(selectedDTO);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	public IFeedbacksFacade getFacade() {
		return facade;
	}
	public void setFacade(IFeedbacksFacade facade) {
		this.facade = facade;
	}
	public IMajorFacade getMajorFacade() {
		return majorFacade;
	}
	public void setMajorFacade(IMajorFacade majorFacade) {
		this.majorFacade = majorFacade;
	}
	public List<FeedbackDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<FeedbackDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<FeedbackDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<FeedbackDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	public FeedbackDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(FeedbackDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<FeedbackDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<FeedbackDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public FeedbackDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(FeedbackDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<FeedbackDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(List<FeedbackDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public FeedbackDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(FeedbackDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	
}
