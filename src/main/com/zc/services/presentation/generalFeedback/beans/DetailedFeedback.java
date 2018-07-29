/**
 * 
 */
package main.com.zc.services.presentation.generalFeedback.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAcademicPetitionActionsFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;
import main.com.zc.services.presentation.generalFeedback.facade.IFeedbacksFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class DetailedFeedback {

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
  
	@ManagedProperty("#{IAcademicPetitionActionsFacade}")
	private IAcademicPetitionActionsFacade insFacade;
	
	@ManagedProperty("#{feedbacksFacadeImpl}")
	private IFeedbacksFacade facade;
	
	private FeedbackDTO detailedDTO;
	private List<InstructorDTO> instructors;
	private Integer selectedInstructor;
	private String newComment;
	String casesID;
	private boolean oldMood;
	private boolean renderForward;
	private PetitionsActionsDTO instructorActionDetails;
	private PetitionsActionsDTO handelerActionDetails;
	private boolean renderRemindMe;
	private Date notifyAt;
  	
	
	
	
	
	@PostConstruct
	public void init()
	{
		try{
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		StringBuffer url=origRequest.getRequestURL();
	
		try{
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			if(dtoID!=null){
				detailedDTO=facade.getFeedbackByID(dtoID);
			
				//setNotifyAt(getDetailedDTO().getNotifyAt());
				casesID=origRequest.getParameterValues("cases")[0];
				}
		}
		catch(Exception ex){
			
		}
		try
		{
		Integer mood=Integer.parseInt(origRequest.getParameterValues("oldMood")[0]);
		
		if(mood!=null)
		{
			oldMood=true;
		}
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public void fillInstructorsLst()
    {
	 instructors=new ArrayList<InstructorDTO>();
     instructors=insFacade.fillInsLst();
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
	public void cancel()
	{
		 switch (casesID) {
		 
         case "Ins":  //Approve Of Instructor
        		try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
        			("juniorTAProgIns.xhtml?faces-redirect=true");
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                  break;
             
      
             
         case "Dean": 
        		try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
        			("juniorTAProgDean.xhtml?faces-redirect=true");
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                  
         case "Stduent": 
     		try {
     			FacesContext.getCurrentInstance().getExternalContext().redirect
     			("pendingFeedback.xhtml?faces-redirect=true");
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}  
       
	     	case "handler":  
        		try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
        			("feedbackHandlerPending.xhtml?faces-redirect=true");
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
		 }
        	
		 
	
	}
	public void cancelComment()
	{
		newComment=null;
	} 
	public void addComment() {
		try {
			if (newComment == null || newComment.equals("")) {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,"Please, Write Your Comment");
			} else {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
				{
				
				if(getDetailedDTO().getActionDTO().size()>0)
				{
					boolean existed=false;
					int index=0;
				for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
				{
					
						if(getDetailedDTO().getActionDTO().get(i).getInstructorID().equals
								(getInsDataFacade.getInsByPersonMail(authentication.getName()).getId()))
						{
							existed=true;
							index=i;
							break;
							
						}
						
					}
					if(existed)
					{
						PetitionsActionsDTO actionDTO=getDetailedDTO().getActionDTO().get(index);
						actionDTO.setComment(getNewComment());
				
							
							 	
								facade.addComment(actionDTO,getInsDataFacade.getInsByPersonMail(authentication.getName()).getId());setNewComment(null);
						
						JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Comment was sent successfully");
						FacesContext.getCurrentInstance().getExternalContext().redirect
						("detailedFeedback.xhtml?id="+getDetailedDTO().getId()+"&cases="+casesID);
					}
					else 
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setComment(getNewComment());
						actionDTO.setDate(Calendar.getInstance());
						actionDTO.setFormType(FormTypesEnum.FEEDBACK);
						actionDTO.setPetitionID(getDetailedDTO().getId());
						facade.addComment(actionDTO,getInsDataFacade.getInsByPersonMail(authentication.getName()).getId());
						setNewComment(null);
						//getDetailedDTO().setComment(getNewComment());
						JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Comment was sent successfully");
						FacesContext.getCurrentInstance().getExternalContext().redirect
						("detailedFeedback.xhtml?id="+getDetailedDTO().getId()+"&cases="+casesID);
					}
				}
				
				else 
				{
					PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
					actionDTO.setComment(getNewComment());
					actionDTO.setDate(Calendar.getInstance());
					actionDTO.setFormType(FormTypesEnum.FEEDBACK);
					actionDTO.setPetitionID(getDetailedDTO().getId());
						
						facade.addComment(actionDTO,getInsDataFacade.getInsByPersonMail(authentication.getName()).getId());
					setNewComment(null);
					//getDetailedDTO().setComment(getNewComment());
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Comment was sent successfully");
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("detailedFeedback.xhtml?id="+getDetailedDTO().getId()+"&cases="+casesID);
				}
				//
				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 public void downloadAttachments(FeedbackDTO form)
		{
			AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
		}
	 	public void approve()
		{
			 switch (casesID) {
	         case "Ins":  //Approve Of Instructor
	        	 
	         {
	        	 
	        	 approveIns();
	        		break;
	         }
	                
	                  
	                  
	         case "handler":  
	        	 approveHandler();
	                  break;

			 }
	        	
		}
	   	public void refuse()
		{
			 switch (casesID) {
	         case "Ins":  //Approve Of Instructor
	        	
	        	 {
	        		
	        		 refuseIns();
	        		break;
	        	 }
	             
	         case "handler": 
	         refuseHandler();
	                  break;
	                  

			 }
	        	
		}
	   	public void approveHandler()
		{
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
				{
					
		    	try{
		    	FeedbackDTO dto=getDetailedDTO();
		    	
		    	if(!dto.getStep().equals(PetitionStepsEnum.CHECKED))
				{
					dto.setNotifyAt(null);
				}
		    	InstructorDTO loggedInInstructor=getInsDataFacade.getInsByPersonMail(authentication.getName());
		    	// 1- get action of petition
		    	
		    	//2- loop on actions 
		    	boolean actionExistBefore=false;
 		    	int index=0;
		    	if(dto.getActionDTO().size()>0){
		    		
		    	for(int i=0;i<dto.getActionDTO().size();i++)
		    	{
		    		actionExistBefore=false;
		    		//3- if(actions.get(i).getInstructorID == Logged-in instructor)
		    		if(dto.getActionDTO().get(i).getInstructorID().equals(loggedInInstructor.getId()))
		    		{
		    			actionExistBefore=true;
		    			index=i;
		    		    break;
		    			
		    			
		    		}
		    	}
		    		if(actionExistBefore)
			    	{
			    		//4- then we've two cases 
		    			//5- a) the petition already approved 
		    			if(dto.getActionDTO().get(index).getActionType()!=null){
		    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Approved))
		    			{
		    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already checked and Approved Before !");
		    			}
		    		
		    			//6- b) the petition already refused
		    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Refused))
		    			{
		    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
		    		   
		    				//7- Change the step to be checked by handler
		    	    			dto.setStep(PetitionStepsEnum.CHECKED);
		    	    				
		    	    		dto=facade.updateStatusOfForm(dto);
		    		    	if(dto!=null)
		    		    	{
		    		    		//init();
		    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
		    		    		try {
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("feedbackHandlerPending.xhtml?id="+dto.getId());
		    		 				} catch (IOException e) {
		    		 					// TODO Auto-generated catch block
		    		 					e.printStackTrace();
		    		 				}
		    		    		//TODO
		    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
		    		    		}
		    		    	else {
		    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
		    		    	}
		    		    	
		    		    	
		    		    	
		    		    	
		    			}
		    			}
		    			else 
		    			{
		    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
		    		  
		    		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
		    	    		
		    	    			dto.setStep(PetitionStepsEnum.CHECKED);
		    	    			
		    	    		dto=facade.updateStatusOfForm(dto);
		    		    	if(dto!=null)
		    		    	{
		    		    		//init();
		    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
		    		    		try {
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("feedbackHandlerPending.xhtml?id="+dto.getId());
		    		 				} catch (IOException e) {
		    		 					// TODO Auto-generated catch block
		    		 					e.printStackTrace();
		    		 				}
		    		    		//TODO 
		    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
		    		    		}
		    		    	else {
		    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
		    		    	}
		    			}
			    	}
		    		// else there is no previous actions
		    		else 
		    		{
		    			//8- add new action object 
		    			PetitionsActionsDTO newAction=new PetitionsActionsDTO();
		    			newAction.setActionType(PetitionActionTypeEnum.Approved);
		    			newAction.setDate(Calendar.getInstance());
		    			newAction.setFormType(FormTypesEnum.FEEDBACK);
		    			newAction.setInstructorID(loggedInInstructor.getId());
		    			newAction.setPetitionID(dto.getId());
		    			if(getNewComment()!=null)
		    			{
		    				if(!getNewComment().trim().equals(""))
		    				{
		    					newAction.setComment(getNewComment());
		    				}	
		    			}
		    			
		    			
		    			dto.getActionDTO().add(newAction);
		    			
	    						
		        			dto.setStep(PetitionStepsEnum.CHECKED);
		        			dto=facade.updateStatusOfForm(dto);
			    	    	if(dto!=null)
			    	    	{
			    	    		//init();
			    	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
			    	    		try {
			    	 					FacesContext.getCurrentInstance().getExternalContext().redirect
			    	 					("feedbackHandlerPending.xhtml?id="+dto.getId());
			    	 				} catch (IOException e) {
			    	 					// TODO Auto-generated catch block
			    	 					e.printStackTrace();
			    	 				}
			    	    		//TODO
			    	    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
			    	    		}
			    	    	else {
			    	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
			    	    	}
		        				
		        		}
		        		
		    		}
		 
		    	
			    
		    
		    	
		    	else 
		    	{
		    		
		    		PetitionsActionsDTO newAction=new PetitionsActionsDTO();
	    			newAction.setActionType(PetitionActionTypeEnum.Approved);
	    			newAction.setDate(Calendar.getInstance());
	    			newAction.setFormType(FormTypesEnum.FEEDBACK);
	    			newAction.setInstructorID(loggedInInstructor.getId());
	    			newAction.setPetitionID(dto.getId());
	    			if(getNewComment()!=null)
	    			{
	    				if(!getNewComment().trim().equals(""))
	    				{
	    					newAction.setComment(getNewComment());
	    				}	
	    			}
	    			
	    			
	    			dto.getActionDTO().add(newAction);
	    		
	        		dto.setStep(PetitionStepsEnum.CHECKED);
	        		dto=facade.updateStatusOfForm(dto);
	    	    	if(dto!=null)
	    	    	{
	    	    		//init();
	    	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    	    		try {
	    	 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    	 					("feedbackHandlerPending.xhtml?id="+dto.getId());
	    	 				} catch (IOException e) {
	    	 					// TODO Auto-generated catch block
	    	 					e.printStackTrace();
	    	 				}
	    	    		//TODO
	    	    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    	    		}
	    	    	else {
	    	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
	    	    	}
	        				
	        		}
	        	
		    	}
	    
		    	
		    
		    
		    	
		    
		    	
		    	
		    	
		    	
		    	catch(Exception ex)
		    	{
		    		ex.printStackTrace();
		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
		    	}}
		}
	   	public void refuseHandler()
			{
				 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
					{
						
			    	try{
			    	FeedbackDTO dto=getDetailedDTO();
			    	
			    	if(!dto.getStep().equals(PetitionStepsEnum.CHECKED))
					{
						dto.setNotifyAt(null);
					}
			    	InstructorDTO loggedInInstructor=getInsDataFacade.getInsByPersonMail(authentication.getName());
			    	// 1- get action of petition
			    	
			    	//2- loop on actions 
			    	boolean actionExistBefore=false;
	 		    	int index=0;
			    	if(dto.getActionDTO().size()>0){
			    		
			    	for(int i=0;i<dto.getActionDTO().size();i++)
			    	{
			    		actionExistBefore=false;
			    		//3- if(actions.get(i).getInstructorID == Logged-in instructor)
			    		if(dto.getActionDTO().get(i).getInstructorID().equals(loggedInInstructor.getId()))
			    		{
			    			actionExistBefore=true;
			    			index=i;
			    		    break;
			    			
			    			
			    		}
			    	}
			    		if(actionExistBefore)
				    	{
				    		//4- then we've two cases 
			    			//5- a) the petition already approved 
			    			if(dto.getActionDTO().get(index).getActionType()!=null){
			    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Refused))
			    			{
			    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already checked and Refused Before !");
			    			}
			    		
			    			//6- b) the petition already refused
			    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Approved))
			    			{
			    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Refused);
			    		   
			    				//7- Change the step to be checked by handler
			    	    			dto.setStep(PetitionStepsEnum.CHECKED);
			    	    			
			    	    			dto.setPerformed(true);
			    	    		dto=facade.updateStatusOfForm(dto);
			    		    	if(dto!=null)
			    		    	{
			    		    		//init();
			    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
			    		    		try {
			    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
			    		 					("feedbackHandlerPending.xhtml?id="+dto.getId());
			    		 				} catch (IOException e) {
			    		 					// TODO Auto-generated catch block
			    		 					e.printStackTrace();
			    		 				}
			    		    		//TODO
			    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
			    		    		}
			    		    	else {
			    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
			    		    	}
			    		    	
			    		    	
			    		    	
			    		    	
			    			}
			    			}
			    			else 
			    			{
			    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Refused);
			    		  
			    		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
			    	    		
			    	    			dto.setStep(PetitionStepsEnum.CHECKED);
			    	    			dto.setPerformed(true);
			    	    		dto=facade.updateStatusOfForm(dto);
			    		    	if(dto!=null)
			    		    	{
			    		    		//init();
			    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
			    		    		try {
			    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
			    		 					("feedbackHandlerPending.xhtml?id="+dto.getId());
			    		 				} catch (IOException e) {
			    		 					// TODO Auto-generated catch block
			    		 					e.printStackTrace();
			    		 				}
			    		    		//TODO 
			    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
			    		    		}
			    		    	else {
			    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
			    		    	}
			    			}
				    	}
			    		// else there is no previous actions
			    		else 
			    		{
			    			//8- add new action object 
			    			PetitionsActionsDTO newAction=new PetitionsActionsDTO();
			    			newAction.setActionType(PetitionActionTypeEnum.Refused);
			    			newAction.setDate(Calendar.getInstance());
			    			newAction.setFormType(FormTypesEnum.FEEDBACK);
			    			newAction.setInstructorID(loggedInInstructor.getId());
			    			newAction.setPetitionID(dto.getId());
			    			if(getNewComment()!=null)
			    			{
			    				if(!getNewComment().trim().equals(""))
			    				{
			    					newAction.setComment(getNewComment());
			    				}	
			    			}
			    			
			    			
			    			dto.getActionDTO().add(newAction);
			    			
		    						
			        			dto.setStep(PetitionStepsEnum.CHECKED);
			        			dto.setPerformed(true);
			        			dto=facade.updateStatusOfForm(dto);
				    	    	if(dto!=null)
				    	    	{
				    	    		//init();
				    	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
				    	    		try {
				    	 					FacesContext.getCurrentInstance().getExternalContext().redirect
				    	 					("feedbackHandlerPending.xhtml?id="+dto.getId());
				    	 				} catch (IOException e) {
				    	 					// TODO Auto-generated catch block
				    	 					e.printStackTrace();
				    	 				}
				    	    		//TODO
				    	    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
				    	    		}
				    	    	else {
				    	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
				    	    	}
			        				
			        		}
			        		
			    		}
			 
			    	
				    
			    
			    	
			    	else 
			    	{
			    		
			    		PetitionsActionsDTO newAction=new PetitionsActionsDTO();
		    			newAction.setActionType(PetitionActionTypeEnum.Refused);
		    			newAction.setDate(Calendar.getInstance());
		    			newAction.setFormType(FormTypesEnum.FEEDBACK);
		    			newAction.setInstructorID(loggedInInstructor.getId());
		    			newAction.setPetitionID(dto.getId());
		    			if(getNewComment()!=null)
		    			{
		    				if(!getNewComment().trim().equals(""))
		    				{
		    					newAction.setComment(getNewComment());
		    				}	
		    			}
		    			
		    			
		    			dto.getActionDTO().add(newAction);
		    		
		        		dto.setStep(PetitionStepsEnum.CHECKED);
		        		dto.setPerformed(true);
		        		dto=facade.updateStatusOfForm(dto);
		    	    	if(dto!=null)
		    	    	{
		    	    		//init();
		    	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		    	    		try {
		    	 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    	 					("feedbackHandlerPending.xhtml?id="+dto.getId());
		    	 				} catch (IOException e) {
		    	 					// TODO Auto-generated catch block
		    	 					e.printStackTrace();
		    	 				}
		    	    		//TODO
		    	    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
		    	    		}
		    	    	else {
		    	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
		    	    	}
		        				
		        		}
		        	
			    	}
		    
			    	
			    
			    
			    	
			    
			    	
			    	
			    	
			    	
			    	catch(Exception ex)
			    	{
			    		ex.printStackTrace();
			    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
			    	}}
			}
	   	public void approveIns(){
	   	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	FeedbackDTO dto=getDetailedDTO();
	    	
	    	if(!dto.getStep().equals(PetitionStepsEnum.CLOSED))
			{
				dto.setNotifyAt(null);
			}
	    	InstructorDTO loggedInInstructor=getInsDataFacade.getInsByPersonMail(authentication.getName());
	    	// 1- get action of petition
	    	
	    	//2- loop on actions 
	    	boolean actionExistBefore=false;
	    	int index=0;
	    	if(dto.getActionDTO().size()>0){
	    		
	    	for(int i=0;i<dto.getActionDTO().size();i++)
	    	{
	    		actionExistBefore=false;
	    		//3- if(actions.get(i).getInstructorID == Logged-in instructor)
	    		if(dto.getActionDTO().get(i).getInstructorID().equals(loggedInInstructor.getId()))
	    		{
	    			actionExistBefore=true;
	    			index=i;
	    		    break;
	    			
	    			
	    		}
	    	}
	    		if(actionExistBefore)
		    	{
		    		//4- then we've two cases 
	    			//5- a) the petition already approved 
	    			if(dto.getActionDTO().get(index).getActionType()!=null){
	    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Approved))
	    			{
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already checked and Approved Before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Refused))
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
	    		   
	    				//7- Change the step to be checked by handler
	    	    			dto.setStep(PetitionStepsEnum.CLOSED);
	    	    			dto.setPerformed(true);
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("feedbackPendingIns.xhtml?id="+dto.getId());
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		//TODO
	    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
	    		    	}
	    		    	
	    		    	
	    		    	
	    		    	
	    			}
	    			}
	    			else 
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
	    		  
	    		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	    	    		
	    	    			dto.setStep(PetitionStepsEnum.CLOSED);
	    	    			dto.setPerformed(true);
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("feedbackPendingIns.xhtml?id="+dto.getId());
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		//TODO 
	    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
	    		    	}
	    			}
		    	}
	    		// else there is no previous actions
	    		else 
	    		{
	    			//8- add new action object 
	    			PetitionsActionsDTO newAction=new PetitionsActionsDTO();
	    			newAction.setActionType(PetitionActionTypeEnum.Approved);
	    			newAction.setDate(Calendar.getInstance());
	    			newAction.setFormType(FormTypesEnum.FEEDBACK);
	    			newAction.setInstructorID(loggedInInstructor.getId());
	    			newAction.setPetitionID(dto.getId());
	    			if(getNewComment()!=null)
	    			{
	    				if(!getNewComment().trim().equals(""))
	    				{
	    					newAction.setComment(getNewComment());
	    				}	
	    			}
	    			
	    			
	    			dto.getActionDTO().add(newAction);
	    			
 						
	        			dto.setStep(PetitionStepsEnum.CLOSED);
    	    			dto.setPerformed(true);
	        			dto=facade.updateStatusOfForm(dto);
		    	    	if(dto!=null)
		    	    	{
		    	    		//init();
		    	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
		    	    		try {
		    	 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    	 					("feedbackPendingIns.xhtml?id="+dto.getId());
		    	 				} catch (IOException e) {
		    	 					// TODO Auto-generated catch block
		    	 					e.printStackTrace();
		    	 				}
		    	    		//TODO
		    	    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
		    	    		}
		    	    	else {
		    	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
		    	    	}
	        				
	        		}
	        		
	    		}
	 
	    	
		    
	    
	    	
	    	else 
	    	{
	    		
	    		PetitionsActionsDTO newAction=new PetitionsActionsDTO();
 			newAction.setActionType(PetitionActionTypeEnum.Approved);
 			newAction.setDate(Calendar.getInstance());
 			newAction.setFormType(FormTypesEnum.FEEDBACK);
 			newAction.setInstructorID(loggedInInstructor.getId());
 			newAction.setPetitionID(dto.getId());
 			if(getNewComment()!=null)
 			{
 				if(!getNewComment().trim().equals(""))
 				{
 					newAction.setComment(getNewComment());
 				}	
 			}
 			
 			
 			dto.getActionDTO().add(newAction);
 		
     		dto.setStep(PetitionStepsEnum.CLOSED);
			dto.setPerformed(true);
     		dto=facade.updateStatusOfForm(dto);
 	    	if(dto!=null)
 	    	{
 	    		//init();
 	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
 	    		try {
 	 					FacesContext.getCurrentInstance().getExternalContext().redirect
 	 					("feedbackPendingIns.xhtml?id="+dto.getId());
 	 				} catch (IOException e) {
 	 					// TODO Auto-generated catch block
 	 					e.printStackTrace();
 	 				}
 	    		//TODO
 	    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
 	    		}
 	    	else {
 	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
 	    	}
     				
     		}
     	
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
	    	}}
	   	}
	   	public void refuseIns(){
	   	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	FeedbackDTO dto=getDetailedDTO();
	    	
	    	if(!dto.getStep().equals(PetitionStepsEnum.CLOSED))
			{
				dto.setNotifyAt(null);
			}
	    	InstructorDTO loggedInInstructor=getInsDataFacade.getInsByPersonMail(authentication.getName());
	    	// 1- get action of petition
	    	
	    	//2- loop on actions 
	    	boolean actionExistBefore=false;
	    	int index=0;
	    	if(dto.getActionDTO().size()>0){
	    		
	    	for(int i=0;i<dto.getActionDTO().size();i++)
	    	{
	    		actionExistBefore=false;
	    		//3- if(actions.get(i).getInstructorID == Logged-in instructor)
	    		if(dto.getActionDTO().get(i).getInstructorID().equals(loggedInInstructor.getId()))
	    		{
	    			actionExistBefore=true;
	    			index=i;
	    		    break;
	    			
	    			
	    		}
	    	}
	    		if(actionExistBefore)
		    	{
		    		//4- then we've two cases 
	    			//5- a) the petition already approved 
	    			if(dto.getActionDTO().get(index).getActionType()!=null){
	    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Refused))
	    			{
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already checked and Refused Before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Approved))
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Refused);
	    		   
	    				//7- Change the step to be checked by handler
	    	    			dto.setStep(PetitionStepsEnum.CLOSED);
	    	    			
	    	    			dto.setPerformed(true);
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("feedbackPendingIns.xhtml?id="+dto.getId());
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		//TODO
	    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
	    		    	}
	    		    	
	    		    	
	    		    	
	    		    	
	    			}
	    			}
	    			else 
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Refused);
	    		  
	    		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	    	    		
	    	    			dto.setStep(PetitionStepsEnum.CLOSED);
	    	    			dto.setPerformed(true);
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("feedbackPendingIns.xhtml?id="+dto.getId());
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		//TODO 
	    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
	    		    	}
	    			}
		    	}
	    		// else there is no previous actions
	    		else 
	    		{
	    			//8- add new action object 
	    			PetitionsActionsDTO newAction=new PetitionsActionsDTO();
	    			newAction.setActionType(PetitionActionTypeEnum.Refused);
	    			newAction.setDate(Calendar.getInstance());
	    			newAction.setFormType(FormTypesEnum.FEEDBACK);
	    			newAction.setInstructorID(loggedInInstructor.getId());
	    			newAction.setPetitionID(dto.getId());
	    			if(getNewComment()!=null)
	    			{
	    				if(!getNewComment().trim().equals(""))
	    				{
	    					newAction.setComment(getNewComment());
	    				}	
	    			}
	    			
	    			
	    			dto.getActionDTO().add(newAction);
	    			
 						
	        			dto.setStep(PetitionStepsEnum.CLOSED);
	        			dto.setPerformed(true);
	        			dto=facade.updateStatusOfForm(dto);
		    	    	if(dto!=null)
		    	    	{
		    	    		//init();
		    	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		    	    		try {
		    	 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    	 					("feedbackPendingIns.xhtml?id="+dto.getId());
		    	 				} catch (IOException e) {
		    	 					// TODO Auto-generated catch block
		    	 					e.printStackTrace();
		    	 				}
		    	    		//TODO
		    	    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
		    	    		}
		    	    	else {
		    	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
		    	    	}
	        				
	        		}
	        		
	    		}
	 
	    	
		    
	    
	    	
	    	else 
	    	{
	    		
	    		PetitionsActionsDTO newAction=new PetitionsActionsDTO();
 			newAction.setActionType(PetitionActionTypeEnum.Refused);
 			newAction.setDate(Calendar.getInstance());
 			newAction.setFormType(FormTypesEnum.FEEDBACK);
 			newAction.setInstructorID(loggedInInstructor.getId());
 			newAction.setPetitionID(dto.getId());
 			if(getNewComment()!=null)
 			{
 				if(!getNewComment().trim().equals(""))
 				{
 					newAction.setComment(getNewComment());
 				}	
 			}
 			
 			
 			dto.getActionDTO().add(newAction);
 		
     		dto.setStep(PetitionStepsEnum.CLOSED);
     		dto.setPerformed(true);
     		dto=facade.updateStatusOfForm(dto);
 	    	if(dto!=null)
 	    	{
 	    		//init();
 	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
 	    		try {
 	 					FacesContext.getCurrentInstance().getExternalContext().redirect
 	 					("feedbackPendingIns.xhtml?id="+dto.getId());
 	 				} catch (IOException e) {
 	 					// TODO Auto-generated catch block
 	 					e.printStackTrace();
 	 				}
 	    		//TODO
 	    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
 	    		}
 	    	else {
 	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
 	    	}
     				
     		}
     	
	    	}
 	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
	    	}}
	   	}
	   	public void startExecuion(){
	    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
				{
					
		    	try{
		    	FeedbackDTO dto=getDetailedDTO();
		    	
		    	if(!dto.getStep().equals(PetitionStepsEnum.UNDER_PROCESSING))
				{
					dto.setNotifyAt(null);
				}
		    	InstructorDTO loggedInInstructor=getInsDataFacade.getInsByPersonMail(authentication.getName());
		    	dto.setStep(PetitionStepsEnum.UNDER_PROCESSING);
    			
    		
    		dto=facade.updateStatusOfForm(dto);
	    	if(dto!=null)
	    	{
	    		//init();
	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "The form now is under processing");
	    		
	    		//TODO
	    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		}
		    	}
		    	catch(Exception ex){
		    		ex.printStackTrace();
		    	}
	   	}}
	   	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public FeedbackDTO getDetailedDTO() {
		return detailedDTO;
	}

	public void setDetailedDTO(FeedbackDTO detailedDTO) {
		this.detailedDTO = detailedDTO;
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

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public String getCasesID() {
		return casesID;
	}

	public void setCasesID(String casesID) {
		this.casesID = casesID;
	}

	public boolean isOldMood() {
		return oldMood;
	}

	public void setOldMood(boolean oldMood) {
		this.oldMood = oldMood;
	}

	public boolean isRenderForward() {
		return renderForward;
	}

	public void setRenderForward(boolean renderForward) {
		this.renderForward = renderForward;
	}

	public PetitionsActionsDTO getInstructorActionDetails() {
		PetitionsActionsDTO dto=new PetitionsActionsDTO();
		if(getDetailedDTO()!=null)
		{
			try
			{
				
				for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
				{
					 
					// IT will be the major head if academic and category head if not
					if(getDetailedDTO().getCategoryID()==1)
					{	if(getDetailedDTO().getActionDTO().get(i).getInstructorID().equals(getDetailedDTO().getMajor().getHeadOfMajor().getId()))
					
					{
						dto= getDetailedDTO().getActionDTO().get(i);
						getDetailedDTO().setCategoryHeadName((getInsDataFacade.getInsByPersonID(getDetailedDTO().getActionDTO().get(i).getInstructorID())).getName());
					}
					}
					else 	if(getDetailedDTO().getCategoryID()!=1)
					
					
						if(getDetailedDTO().getActionDTO().get(i).
								getInstructorID().equals(getDetailedDTO().getCategoryHeadID()))
					
					{
						dto= getDetailedDTO().getActionDTO().get(i);
						getDetailedDTO().setCategoryHeadName((getInsDataFacade.getInsByPersonID(getDetailedDTO().getActionDTO().get(i).getInstructorID())).getName());
					}
				}
			
			}
			catch(Exception ex)
			{
				ex.toString();
				
			}
		}
		  return dto;
	}

	public void setInstructorActionDetails(PetitionsActionsDTO instructorActionDetails) {
		this.instructorActionDetails = instructorActionDetails;
	}

	public boolean isRenderRemindMe() {
		return renderRemindMe;
	}

	public void setRenderRemindMe(boolean renderRemindMe) {
		this.renderRemindMe = renderRemindMe;
	}

	public Date getNotifyAt() {
		return notifyAt;
	}

	public void setNotifyAt(Date notifyAt) {
		this.notifyAt = notifyAt;
	}
	public IAcademicPetitionActionsFacade getInsFacade() {
		return insFacade;
	}
	public void setInsFacade(IAcademicPetitionActionsFacade insFacade) {
		this.insFacade = insFacade;
	}
	public IFeedbacksFacade getFacade() {
		return facade;
	}
	public void setFacade(IFeedbacksFacade facade) {
		this.facade = facade;
	}
	public PetitionsActionsDTO getHandelerActionDetails() {
		PetitionsActionsDTO dto=new PetitionsActionsDTO();
		if(getDetailedDTO()!=null)
		{
			try
			{
				
				for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
				{
					 
					// get actions by handler
					//feedback@zewailcity.edu.eg
					InstructorDTO handler=getInsDataFacade.getInsByPersonMail(Constants.LTS_FEEDBACK_HANDLER);
					if(getDetailedDTO().getActionDTO().get(i).getInstructorID().equals(handler.getId()))
					{
						dto= getDetailedDTO().getActionDTO().get(i);
					}
				
				}
			
			}
			catch(Exception ex)
			{
				ex.toString();
				
			}
		}
		  return dto;
	}
	public void setHandelerActionDetails(PetitionsActionsDTO handelerActionDetails) {
		this.handelerActionDetails = handelerActionDetails;
	}
	
}
