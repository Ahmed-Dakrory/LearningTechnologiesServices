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
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeConcentrationInsFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class ChangeOfConcentrationInstructorBean {

	private List<ChangeConcentrationDTO> pendingForms;
	private List<ChangeConcentrationDTO> archievedForms;
	private ChangeConcentrationDTO selectedPendingForms;
	private List<ChangeConcentrationDTO> filteredPendingForms;
	private ChangeConcentrationDTO selectedArchievedForms;
	private List<ChangeConcentrationDTO> filteredArchievedForms;
    private ChangeConcentrationDTO detailedForm;
  

    private String newComment;
    private List<InstructorDTO> instructors;
    private Integer selectedInstructor;
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    
	@ManagedProperty("#{IChangeConcentrationInsFacade}")
	private IChangeConcentrationInsFacade facade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
   
	@ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
	private boolean detailView;
	private FormDTO formDetail;
	
	
	@PostConstruct
	public void init()
	{
		detailView = false;
		fillInstructorsLst();
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
		pendingForms=new ArrayList<ChangeConcentrationDTO>();
		fillPendingForms();
		archievedForms=new ArrayList<ChangeConcentrationDTO>();
		fillArchievedForms();
		

			
			
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
		 		
		     }}
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
	  
	  public void showDetails(ChangeConcentrationDTO form)
		{
			
			try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("changeOfConcentrationIns1"))
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
	    public void onRowSelect(SelectEvent event) {  
		  	try {
		  		ChangeConcentrationDTO selectedDTO=(ChangeConcentrationDTO) event.getObject();
				showDetails(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	public IChangeConcentrationInsFacade getFacade() {
		return facade;
	}
	public void setFacade(IChangeConcentrationInsFacade facade) {
		this.facade = facade;
	}
	
	
}
