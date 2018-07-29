/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.IJuniorTAFacadeInstructor;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.ISkipMajorHeadCoursesFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean(name="TAJuniorProgramInstructorBean")
@ViewScoped
public class TAJuniorProgramInstructorBean {

   	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
   	
	@ManagedProperty("#{IJuniorTAFacadeInstructor}")
	private IJuniorTAFacadeInstructor facade;

    @ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
    
    @ManagedProperty("#{ISkipMajorHeadCoursesFacade}")
   	private ISkipMajorHeadCoursesFacade skipMajorHead;
    
    private List<TAJuniorProgramDTO> pendingForms;
	private List<TAJuniorProgramDTO> archievedForms;
	private TAJuniorProgramDTO selectedPendingForms;
	private List<TAJuniorProgramDTO> filteredPendingForms;
	private TAJuniorProgramDTO selectedArchievedForms;
	private List<TAJuniorProgramDTO> filteredArchievedForms;
	private List<TAJuniorProgramDTO> pendingFormsDean;
	private List<TAJuniorProgramDTO> archievedFormsDean;
	private TAJuniorProgramDTO selectedPendingFormsDean;
	private List<TAJuniorProgramDTO> filteredPendingFormsDean;
	private TAJuniorProgramDTO selectedArchievedFormsDean;
	private List<TAJuniorProgramDTO> filteredArchievedFormsDean;
    private List<BaseDTO> userTypesLst;
    private TAJuniorProgramDTO detailedForm;
    private TAJuniorProgramDTO detailedFormDean;
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
					fillArchFormsDean();
					
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
				List<TAJuniorProgramDTO> instructorList=  facade.getPendingFormsOfInstructor(getInsDataFacade.getInsByPersonMail(mail).getId());
				
				//TODO Updating the scenario of incomplete grade form "Remove Major Head Step"
				List<TAJuniorProgramDTO> paList=  facade.getPendingFormsOfPA(getInsDataFacade.getInsByPersonMail(mail).getId());
				
			    
			    	for(int j=0;j<paList.size();j++)
			    	{
			    		if(!skipMajorHead.checkCourse(paList.get(j).getCourse().getId())){
			    			
			    		
			    		boolean exists=false;
			    		if(instructorList.size()>0)
			    		{
			    		  for(int i=0;i<instructorList.size();i++)
			    		  {
			    			  if(instructorList.get(i).getId().equals(paList.get(j).getId()))
			    			  {
			    				  exists=true;
			    				  break;
			    			  }
			    		  }	
			    		  if(!exists){
			    				instructorList.add(paList.get(j));
			    		  }
			    		}
			    		else
			    		instructorList.add(paList.get(j));
			    		}
			    	}
			   
			    pendingForms=instructorList;
			    for(int i=0;i<pendingForms.size();i++)
			    {
			    	 if(pendingForms.get(i).getStep().equals(PetitionStepsEnum.PA))
					    {
					    	if(skipMajorHead.checkCourse(pendingForms.get(i).getCourse().getId()))
					    	{
					    		pendingForms.get(i).setCurStatus("Reviewd by Course coordinator("+pendingForms.get(i).getCourse().getCoordinator().getName()+")");
					    		//getDetailedDTO().setNextStatus("Reviewing by Dean of ACADEMIC("+getDetailedDTO().getCourse().getCoordinator().getName()+")");
					    	}
					    }
			    }
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
				List<TAJuniorProgramDTO> instructorList=  facade.getArchievedFormsOfInstructor(getInsDataFacade.getInsByPersonMail(mail).getId());
				
				List<TAJuniorProgramDTO> paList=  facade.getArchievedFormsOfPA(getInsDataFacade.getInsByPersonMail(mail).getId());

		    	for(int j=0;j<paList.size();j++)
		    	{
		    		if(!skipMajorHead.checkCourse(paList.get(j).getCourse().getId())){
		    		instructorList.add(paList.get(j));
		    		}
		    	}
		    	archievedForms=instructorList;
		    	 for(int i=0;i<archievedForms.size();i++)
				    {
				    	 if(archievedForms.get(i).getStep().equals(PetitionStepsEnum.PA))
						    {
						    	if(skipMajorHead.checkCourse(archievedForms.get(i).getCourse().getId()))
						    	{
						    		archievedForms.get(i).setCurStatus("Reviewd by Course coordinator("+archievedForms.get(i).getCourse().getCoordinator().getName()+")");
						    		//getDetailedDTO().setNextStatus("Reviewing by Dean of ACADEMIC("+getDetailedDTO().getCourse().getCoordinator().getName()+")");
						    	}
						    }
				    }
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
		
			 for(int i=0;i<pendingFormsDean.size();i++)
			    {
			    	 if(pendingFormsDean.get(i).getStep().equals(PetitionStepsEnum.PA))
					    {
					    	if(skipMajorHead.checkCourse(pendingFormsDean.get(i).getCourse().getId()))
					    	{
					    		pendingFormsDean.get(i).setCurStatus("Reviewd by Course coordinator("+pendingFormsDean.get(i).getCourse().getCoordinator().getName()+")");
					    		//getDetailedDTO().setNextStatus("Reviewing by Dean of ACADEMIC("+getDetailedDTO().getCourse().getCoordinator().getName()+")");
					    	}
					    }
			    }
		  
		    if(pendingFormsDean==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
	}
	public void fillArchFormsDean()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
		
			archievedFormsDean=  facade.getArchievedFormsOfDean();
			 for(int i=0;i<archievedFormsDean.size();i++)
			    {
			    	 if(archievedFormsDean.get(i).getStep().equals(PetitionStepsEnum.PA))
					    {
					    	if(skipMajorHead.checkCourse(archievedFormsDean.get(i).getCourse().getId()))
					    	{
					    		archievedFormsDean.get(i).setCurStatus("Reviewd by Course coordinator("+archievedFormsDean.get(i).getCourse().getCoordinator().getName()+")");
					    		//getDetailedDTO().setNextStatus("Reviewing by Dean of ACADEMIC("+getDetailedDTO().getCourse().getCoordinator().getName()+")");
					    	}
					    }
			    }
		   
		  
		    if(archievedFormsDean==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
	}

	 public void navigateDean()
	    {
	    	if(getTypeOfUserDean()==2)
	    	{
	    	
	    		setShowIns(true);
	    	
	    		try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("juniorTAProgDean.xhtml");
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
					("juniorTAProgIns.xhtml");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	    		
	    	
	    	}
	    }   
	 public void showDetails(TAJuniorProgramDTO form) 
	    {
	    	try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("juniorTAProgIns1"))
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
	 public void showDetailsDean(TAJuniorProgramDTO form) 
	    {
	    	try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("juniorTAProgDean1"))
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
	 public void onRowSelect(SelectEvent event) {  
		  	try {
		  		TAJuniorProgramDTO selectedDTO=(TAJuniorProgramDTO) event.getObject();
				showDetails(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
	 public void onRowSelectDean(SelectEvent event) {  
		  	try {
		  		TAJuniorProgramDTO selectedDTO=(TAJuniorProgramDTO) event.getObject();
				showDetailsDean(selectedDTO);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}



	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}



	public IJuniorTAFacadeInstructor getFacade() {
		return facade;
	}



	public void setFacade(IJuniorTAFacadeInstructor facade) {
		this.facade = facade;
	}



	public IFormsHistoryFacade getFormsHistoryFacade() {
		return formsHistoryFacade;
	}



	public void setFormsHistoryFacade(IFormsHistoryFacade formsHistoryFacade) {
		this.formsHistoryFacade = formsHistoryFacade;
	}



	public List<TAJuniorProgramDTO> getPendingForms() {
		return pendingForms;
	}



	public void setPendingForms(List<TAJuniorProgramDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}



	public List<TAJuniorProgramDTO> getArchievedForms() {
		return archievedForms;
	}



	public void setArchievedForms(List<TAJuniorProgramDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}



	public TAJuniorProgramDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}



	public void setSelectedPendingForms(TAJuniorProgramDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}



	public List<TAJuniorProgramDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}



	public void setFilteredPendingForms(
			List<TAJuniorProgramDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}



	public TAJuniorProgramDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}



	public void setSelectedArchievedForms(TAJuniorProgramDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}



	public List<TAJuniorProgramDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}



	public void setFilteredArchievedForms(
			List<TAJuniorProgramDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}



	public List<TAJuniorProgramDTO> getPendingFormsDean() {
		return pendingFormsDean;
	}



	public void setPendingFormsDean(List<TAJuniorProgramDTO> pendingFormsDean) {
		this.pendingFormsDean = pendingFormsDean;
	}



	public List<TAJuniorProgramDTO> getArchievedFormsDean() {
		return archievedFormsDean;
	}



	public void setArchievedFormsDean(List<TAJuniorProgramDTO> archievedFormsDean) {
		this.archievedFormsDean = archievedFormsDean;
	}



	public TAJuniorProgramDTO getSelectedPendingFormsDean() {
		return selectedPendingFormsDean;
	}



	public void setSelectedPendingFormsDean(
			TAJuniorProgramDTO selectedPendingFormsDean) {
		this.selectedPendingFormsDean = selectedPendingFormsDean;
	}



	public List<TAJuniorProgramDTO> getFilteredPendingFormsDean() {
		return filteredPendingFormsDean;
	}



	public void setFilteredPendingFormsDean(
			List<TAJuniorProgramDTO> filteredPendingFormsDean) {
		this.filteredPendingFormsDean = filteredPendingFormsDean;
	}



	public TAJuniorProgramDTO getSelectedArchievedFormsDean() {
		return selectedArchievedFormsDean;
	}



	public void setSelectedArchievedFormsDean(
			TAJuniorProgramDTO selectedArchievedFormsDean) {
		this.selectedArchievedFormsDean = selectedArchievedFormsDean;
	}



	public List<TAJuniorProgramDTO> getFilteredArchievedFormsDean() {
		return filteredArchievedFormsDean;
	}



	public void setFilteredArchievedFormsDean(
			List<TAJuniorProgramDTO> filteredArchievedFormsDean) {
		this.filteredArchievedFormsDean = filteredArchievedFormsDean;
	}



	public List<BaseDTO> getUserTypesLst() {
		return userTypesLst;
	}



	public void setUserTypesLst(List<BaseDTO> userTypesLst) {
		this.userTypesLst = userTypesLst;
	}



	public TAJuniorProgramDTO getDetailedForm() {
		return detailedForm;
	}



	public void setDetailedForm(TAJuniorProgramDTO detailedForm) {
		this.detailedForm = detailedForm;
	}



	public TAJuniorProgramDTO getDetailedFormDean() {
		return detailedFormDean;
	}



	public void setDetailedFormDean(TAJuniorProgramDTO detailedFormDean) {
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
			if(mail.equals(Constants.DEAN_OF_ACADEMIC))
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



	public List<BaseDTO> getUserTypesDeanLst() {
		return userTypesDeanLst;
	}



	public void setUserTypesDeanLst(List<BaseDTO> userTypesDeanLst) {
		this.userTypesDeanLst = userTypesDeanLst;
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



	public Integer getTypeOfUserDean() {
		return typeOfUserDean;
	}



	public void setTypeOfUserDean(Integer typeOfUserDean) {
		this.typeOfUserDean = typeOfUserDean;
	}



	public boolean isOldMood() {
		return oldMood;
	}



	public void setOldMood(boolean oldMood) {
		this.oldMood = oldMood;
	}



	public FormDTO getFormDetail() {
		return formDetail;
	}



	public void setFormDetail(FormDTO formDetail) {
		this.formDetail = formDetail;
	}

	public ISkipMajorHeadCoursesFacade getSkipMajorHead() {
		return skipMajorHead;
	}

	public void setSkipMajorHead(ISkipMajorHeadCoursesFacade skipMajorHead) {
		this.skipMajorHead = skipMajorHead;
	}
	 
	 
	 
}
