/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.impl.SendMailThread;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeConcentrationActionsFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
@ManagedBean(name="DetailsBeanChangeConcentration")
@ViewScoped
public class DetailsBean {

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
  
	@ManagedProperty("#{IChangeConcentrationActionsFacade}")
	private IChangeConcentrationActionsFacade facade;
	
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
   	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    

    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
    private IGetLoggedInStudentDataFacade studentDataFacade;
   
    private ChangeConcentrationDTO detailedDTO;
  	private List<InstructorDTO> instructors;
  	private Integer selectedInstructor;
  	private String newComment;
  	String casesID;
  	private boolean oldMood;
  	private boolean renderForward;
  	private boolean admissionDeptMood;
  	private PetitionsActionsDTO instructorActionDetails;
  	
  	private PetitionsActionsDTO admissionHActionDetails;
  	private PetitionsActionsDTO admissionDActionDetails;
  	private boolean renderRemindMe;
  	private Date notifyAt;
  	private boolean adminView;
  	

	private String content;
	private Boolean revertedBefore;
  	@PostConstruct
  	 public void init()
 	{
 		try{ Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
 			if(!authentication.getName().toLowerCase().equals(Constants.REGISTRAR_HEAD_EMAIL)&&
 					!authentication.getName().toLowerCase().equals(Constants.ADMISSION_DEPT)&&
 					!(authentication.getName().startsWith("S")||authentication.getName().startsWith("s")||StringUtils.isNumeric(authentication.getName().substring(0, 4))))
 					{
 				fillInstructorsLst();		
 					}
 		
 		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
 		StringBuffer url=origRequest.getRequestURL();
 	
 		try{
 			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
 			if(dtoID!=null){
 				detailedDTO=facade.getByID(dtoID);
 	
 				setNotifyAt(getDetailedDTO().getNotifyAt());
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
 		try
		{
		if(authentication.getName().toLowerCase().equals(Constants.ADMISSION_DEPT))
			{
				admissionDeptMood=true;
			}
			else admissionDeptMood=false;
		}
		catch(Exception ex)
		{
			ex.toString();
			admissionDeptMood=false;
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
	 public void approve()
	 {
		 switch (casesID) {
         case "Ins":  //Approve Of Instructor
        	 
         {
        	 approveIns();
         }
                  break;
                  
    
                  
                  
                  
         case "AdmissionH":  
        	 appoveAdmissionHead();
        	 break;
        	 
        
		 }
	 }
	 public void approveIns()
	 {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	ChangeConcentrationDTO dto=getDetailedDTO();
	    	if(!dto.getStep().equals(PetitionStepsEnum.PA))
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
	    	
	    		if(dto.getActionDTO().get(i).getInstructorID().equals(getInsDataFacade.getInsByPersonMail(authentication.getName()).getId()))
	    		{
	    			actionExistBefore=true;
	    			index=i;
	    			break;
	    			
	    		}
	    	}
	    		if(actionExistBefore)
		    	{
		    		//4- then we've two cases 
	    			//5- a) the petition already approved before
	    			if(dto.getActionDTO().get(index).getActionType()!=null){
	    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Approved)&&dto.getReverted()!=true)
	    			{
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already approved before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Refused)||
	    					dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.REVERT)||dto.getReverted()==true)
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
	    		   		
	    	    			dto.setStep(PetitionStepsEnum.PA);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		    		try {
	    		    			if(casesID.equals("AdmissionH"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId());
	    		    			else if(casesID.equals("ins"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
	    		    	}
	    		    	
	    		    	
	    		    	
	    		    	
	    			}
	    			}
	    			else // comment without action
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
	    		   		
    	    			dto.setStep(PetitionStepsEnum.PA);
    	    				
    	    		dto=facade.updateStatusOfForm(dto);
    		    	if(dto!=null)
    		    	{
    		    		//init();
    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
    		    		try {
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
    		 				} catch (IOException e) {
    		 					// TODO Auto-generated catch block
    		 					e.printStackTrace();
    		 				}
    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
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
	    			newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
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
	    			dto.setStep(PetitionStepsEnum.PA);
	 				
	    	 		
	    	 		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    	    		    		
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
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
 			newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
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
 			dto.setStep(PetitionStepsEnum.PA);
				
 	 		
 	 		dto=facade.updateStatusOfForm(dto);
 		    	if(dto!=null)
 		    	{
 		    		//init();
 		    		
 		    		try {
 		 					FacesContext.getCurrentInstance().getExternalContext().redirect
 		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
 		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
 		 				} catch (IOException e) {
 		 					
 		 					e.printStackTrace();
 		 				}
 		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
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
	    	}
	    	}
	 }
	 public void refuse()
	 {
		 switch (casesID) {
         case "Ins":  //Approve Of Instructor
        	 
         {
        	 refuseIns();
         }
                  break;
                  
                  
       
         case "AdmissionH":  
        	 refuseAdmissionHead();
        	 break;
        	 
        
		 }
	 }	 
public void refuseIns()
	 {

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	ChangeConcentrationDTO dto=getDetailedDTO();
	    	if(!dto.getStep().equals(PetitionStepsEnum.PA))
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
	    	/*	if(dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Approved)||
	    				dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED))*/
	    		if(dto.getActionDTO().get(i).getInstructorID().equals(getInsDataFacade.getInsByPersonMail(authentication.getName()).getId()))
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
	    			if(dto.getActionDTO().get(index).getActionType()!=null)
	    			{
	    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Refused)&&dto.getReverted()!=true)
	    			{
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already refused before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Approved)||
	    					dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.REVERT)||dto.getReverted()==true)
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Refused);
	    		   		
	    	    			dto.setStep(PetitionStepsEnum.PA);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
	    		    	}
	    		    	
	    		    	
	    		    	
	    		    	
	    			}
	    			}
	    			else 
	    			{

	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Refused);
	    		   		
	    	    			dto.setStep(PetitionStepsEnum.PA);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
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
	    			newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
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
	    			dto.setStep(PetitionStepsEnum.PA);
	 				
	    	 		
	    	 		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    	    		    		
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
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
 			newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
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
 			dto.setStep(PetitionStepsEnum.PA);
				
 	 		
 	 		dto=facade.updateStatusOfForm(dto);
 		    	if(dto!=null)
 		    	{
 		    		//init();
 		    		
 		    		try {
 		 					FacesContext.getCurrentInstance().getExternalContext().redirect
 		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
 		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
 		 				} catch (IOException e) {
 		 					
 		 					e.printStackTrace();
 		 				}
 		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
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
	    	}
	    	}
	 }
	 public void cancel()
		{
			 switch (casesID) {
	         case "Ins":  //Approve Of Instructor
	        		try {
	        			if(oldMood)
	        			FacesContext.getCurrentInstance().getExternalContext().redirect
	        			("changeOfConcentrationIns1.xhtml?faces-redirect=true");
	        			else 
	        				FacesContext.getCurrentInstance().getExternalContext().redirect
		        			("changeOfConcentrationIns.xhtml?faces-redirect=true");
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	                  break;
	          
	         case "AdmissionH":  
	        		try {
	        			if(oldMood)
	        			FacesContext.getCurrentInstance().getExternalContext().redirect
	        			("changeOfConcentrationAdmissionHead1.xhtml?faces-redirect=true");
	        			else 
	        				FacesContext.getCurrentInstance().getExternalContext().redirect
		        			("changeOfConcentrationAdmissionHead1.xhtml?faces-redirect=true");
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	        	 
	        	 
	         case "AdmissionD":  
	        		try {
	        			if(oldMood)
	        			FacesContext.getCurrentInstance().getExternalContext().redirect
	        			("changeOfConcentrationAdmission1.xhtml?faces-redirect=true");
	        			else 
	        				FacesContext.getCurrentInstance().getExternalContext().redirect
		        			("changeOfConcentrationAdmission.xhtml?faces-redirect=true");
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	        		
	         case "Stduent":  
	        		try {
	        		
	        				FacesContext.getCurrentInstance().getExternalContext().redirect
		        			("changeOfConcentrationStudent.xhtml?faces-redirect=true");
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
/*	         case "Admin":  
	        		try {
	        			FacesContext.getCurrentInstance().getExternalContext().redirect
	        			("changeMajorAdmin.xhtml?faces-redirect=true");
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}		
	        		*/
			 }
	        	
			 
		
		}
	 public void appoveAdmissionHead()
	 {

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	ChangeConcentrationDTO dto=getDetailedDTO();
	    	if(!dto.getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
			{
				dto.setNotifyAt(null);
			}
	    //	InstructorDTO loggedInInstructor=getInsDataFacade.getInsByPersonMail(authentication.getName());
	    	// 1- get action of petition
	    	
	    	//2- loop on actions 
	    	boolean actionExistBefore=false;
	    	int index=0;
	    	if(dto.getActionDTO().size()>0){
	    		
	    	for(int i=0;i<dto.getActionDTO().size();i++)
	    	{
	    		actionExistBefore=false;
	    		//3- if(actions.get(i).getInstructorID == Logged-in instructor)
	    		
	    			
	    		if(dto.getActionDTO().get(i).getInstructorID().equals(Constants.REGISTRAR_HEAD_ID))
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
	    			if(dto.getActionDTO().get(index).getActionType()!=null)
	    			{
	    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)&&dto.getReverted()!=true)
	    			{
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Approved Before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Admission_Refused)||
	    					dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.REVERT)||dto.getReverted()==true)
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Admission_Approved);
	    		   		
	    	    			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId()+"&action=approve");
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
	    		    	}
	    		    	
	    		    	
	    		    	
	    		    	
	    			}
	    			}
	    			else 
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Admission_Approved);
	    		   		
    	    			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
    	    				
    	    		dto=facade.updateStatusOfForm(dto);
    		    	if(dto!=null)
    		    	{
    		    		//init();
    		    		
    		    		try {
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId()+"&action=approve");
    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
    		 				} catch (IOException e) {
    		 					// TODO Auto-generated catch block
    		 					e.printStackTrace();
    		 				}
    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
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
	    			newAction.setActionType(PetitionActionTypeEnum.Admission_Approved);
	    			newAction.setDate(Calendar.getInstance());
	    			newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
	    			newAction.setInstructorID(Constants.REGISTRAR_HEAD_ID);
	    			newAction.setPetitionID(dto.getId());
	    			if(getNewComment()!=null)
	    			{
	    				if(!getNewComment().trim().equals(""))
	    				{
	    					newAction.setComment(getNewComment());
	    				}	
	    			}
	    			
	    			
	    			dto.getActionDTO().add(newAction);
	    			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
					
	    			
	    			dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId()+"&action=approve");
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
	    		    	}
	    		    	
	    		    
	    		    
	    		    	
	    		}
	 
	    	}
	    	
	    	
	    	else 
	    	{
	    		
	    	PetitionsActionsDTO newAction=new PetitionsActionsDTO();
			newAction.setActionType(PetitionActionTypeEnum.Admission_Approved);
			newAction.setDate(Calendar.getInstance());
			newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
			newAction.setInstructorID(Constants.REGISTRAR_HEAD_ID);
			newAction.setPetitionID(dto.getId());
			if(getNewComment()!=null)
			{
				if(!getNewComment().trim().equals(""))
				{
					newAction.setComment(getNewComment());
					
				    
				    
				    	
				}	
			}
	    	
			
			dto.getActionDTO().add(newAction);
			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
			
			
			dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId()+"&action=approve");
		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
		 				} catch (IOException e) {
		 					// TODO Auto-generated catch block
		 					e.printStackTrace();
		 				}
		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
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
	    	}
	    	}
	    	
	 }
	 public void refuseAdmissionHead()
	 {


		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	ChangeConcentrationDTO dto=getDetailedDTO();
	    	if(!dto.getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
			{
				dto.setNotifyAt(null);
			}
	    	//InstructorDTO loggedInInstructor=getInsDataFacade.getInsByPersonMail(authentication.getName());
	    	// 1- get action of petition
	    	
	    	//2- loop on actions 
	    	boolean actionExistBefore=false;
	    	int index=0;
	    	if(dto.getActionDTO().size()>0){
	    		
	    	for(int i=0;i<dto.getActionDTO().size();i++)
	    	{
	    		actionExistBefore=false;
	    		//3- if(actions.get(i).getInstructorID == Logged-in instructor)
	    		if(dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
	    				dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
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
	    			if(dto.getActionDTO().get(index).getActionType()!=null)
	    			{
	    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Admission_Refused)&&dto.getReverted()!=true)
	    			{
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Refused Before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
	    					dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.REVERT)||dto.getReverted()==true)
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Admission_Refused);
	    		   		
	    	    			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId()+"&action=refuse");
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
	    		    	}
	    		    	
	    		    	
	    		    	
	    		    	
	    			}
	    			}
	    			else 
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Admission_Refused);
	    		   		
    	    			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
    	    				
    	    		dto=facade.updateStatusOfForm(dto);
    		    	if(dto!=null)
    		    	{
    		    		//init();
    		    		
    		    		try {
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId()+"&action=refuse");
    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
    		 				} catch (IOException e) {
    		 					// TODO Auto-generated catch block
    		 					e.printStackTrace();
    		 				}
    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
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
	    			newAction.setActionType(PetitionActionTypeEnum.Admission_Refused);
	    			newAction.setDate(Calendar.getInstance());
	    			newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
	    			newAction.setInstructorID(Constants.REGISTRAR_HEAD_ID);
	    			newAction.setPetitionID(dto.getId());
	    			if(getNewComment()!=null)
	    			{
	    				if(!getNewComment().trim().equals(""))
	    				{
	    					newAction.setComment(getNewComment());
	    				}	
	    			}
	    			
	    			
	    			dto.getActionDTO().add(newAction);
	    			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
					
	    			
	    			dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId()+"&action=refuse");
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
	    		    	}
	    		}
	 
	    	
	    	
	    	}
	    	else 
	    	{
	    		
	    	PetitionsActionsDTO newAction=new PetitionsActionsDTO();
			newAction.setActionType(PetitionActionTypeEnum.Admission_Refused);
			newAction.setDate(Calendar.getInstance());
			newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
			newAction.setInstructorID(Constants.REGISTRAR_HEAD_ID);
			newAction.setPetitionID(dto.getId());
			if(getNewComment()!=null)
			{
				if(!getNewComment().trim().equals(""))
				{
					newAction.setComment(getNewComment());
				}	
			}
	    
			
			dto.getActionDTO().add(newAction);
			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
			
			
			dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId()+"&action=refuse");
		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		 				} catch (IOException e) {
		 					// TODO Auto-generated catch block
		 					e.printStackTrace();
		 				}
		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
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
	    	}
	    	}
	    	
	 }
	 public void addComment(ChangeConcentrationDTO dto) {
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
						/*	if(authentication.getName().equals(Constants.ADMISSION_DEPT))
								facade.addComment(actionDTO,Constants.ADMISSION_DEPT_ID);
								else if(authentication.getName().equals(Constants.ADMISSION_HEAD))
										facade.addComment(actionDTO,Constants.ADMISSION_HEAD_ID);
								else */	
									facade.addComment(actionDTO,getInsDataFacade.getInsByPersonMail(authentication.getName()).getId());setNewComment(null);
							//getDetailedDTO().setComment(getNewComment());
							JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Comment was sent successfully");
							FacesContext.getCurrentInstance().getExternalContext().redirect
							("formDetails.xhtml?id="+getDetailedDTO().getId()+"&cases="+casesID);
							sendCommentEmail();
						}
						else 
						{
							PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
							actionDTO.setComment(getNewComment());
							actionDTO.setDate(Calendar.getInstance());
							actionDTO.setFormType(FormTypesEnum.ACADEMICPETITION);
							actionDTO.setPetitionID(getDetailedDTO().getId());
						/*	if(authentication.getName().equals(Constants.ADMISSION_DEPT))
							facade.addComment(actionDTO,Constants.ADMISSION_DEPT_ID);
							else if(authentication.getName().equals(Constants.ADMISSION_HEAD))
									facade.addComment(actionDTO,Constants.ADMISSION_HEAD_ID);
							else */	
								facade.addComment(actionDTO,getInsDataFacade.getInsByPersonMail(authentication.getName()).getId());
							setNewComment(null);
							//getDetailedDTO().setComment(getNewComment());
							JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Comment was sent successfully");
							FacesContext.getCurrentInstance().getExternalContext().redirect
							("formDetails.xhtml?id="+getDetailedDTO().getId()+"&cases="+casesID);
							sendCommentEmail();
						}
					}
					
					else 
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setComment(getNewComment());
						actionDTO.setDate(Calendar.getInstance());
						actionDTO.setFormType(FormTypesEnum.ACADEMICPETITION);
						actionDTO.setPetitionID(getDetailedDTO().getId());
						/*if(authentication.getName().equals(Constants.ADMISSION_DEPT))
						facade.addComment(actionDTO,Constants.ADMISSION_DEPT_ID);
						else if(authentication.getName().equals(Constants.ADMISSION_HEAD))
								facade.addComment(actionDTO,Constants.ADMISSION_HEAD_ID);
						else 	*/
							facade.addComment(actionDTO,getInsDataFacade.getInsByPersonMail(authentication.getName()).getId());
						setNewComment(null);
						//getDetailedDTO().setComment(getNewComment());
						JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Comment was sent successfully");
						FacesContext.getCurrentInstance().getExternalContext().redirect
						("formDetails.xhtml?id="+getDetailedDTO().getId()+"&cases="+casesID);
						sendCommentEmail();
					}
					//
					
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	 public void sendCommentEmail(){
		 try{
				String email="Kindly check your submitted petition with id "+getDetailedDTO().getId()+" on LTS system to check the new comments";
				List<String> sender=new ArrayList<String>();
				PersonDataDTO stundet=new PersonDataDTO();
				stundet=studentDataFacade.getPersonByPersonMail(getDetailedDTO().getStudent().getMail());
				//sender.add("oalaaeddin@zewailcity.edu.eg");
				sender.add(getDetailedDTO().getStudent().getMail());
				SendMailThread sendMailThread = new SendMailThread(
						sender, stundet.getNameInEng(),
						email, "Change of concentration form - new comment");
				sendMailThread.start();
				}catch(Exception ex){
					ex.printStackTrace();
				}
	 }
	 
	 
		public void cancelComment()
		{
			newComment=null;
		}
		public void saveReminder() {
			try {
				Authentication authentication = SecurityContextHolder.getContext()
						.getAuthentication();
				if (getDetailedDTO() != null) {
					getDetailedDTO().setNotifyAt(this.notifyAt);
					sharedAcademicPetFacade.notifyAt(getDetailedDTO(),
							authentication.getName());
				} 
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"A reminder will be sent to your email on the selected date");
			} catch (Exception ex) {
				ex.printStackTrace();
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"System Error");
			}
		}

		 public void markAsDone()
		 {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
				{
				if(authentication.getName().equals(Constants.ADMISSION_DEPT))
				{
		    	try{
		    		ChangeConcentrationDTO dto=getDetailedDTO();
		    		if(!dto.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
					{
						dto.setNotifyAt(null);
					}
		    	dto.setStep(PetitionStepsEnum.ADMISSION_DEPT);
		    	dto.setPerformed(true);
		    	
		   	    boolean refusedByAdmissionDept=false;
			    boolean approvedByAdmissionDept=false;
			    for(int i=0;i<getDetailedDTO().getActionDTO().size();i++){
			    	if(getDetailedDTO().getActionDTO().get(i).getActionType()!=null)
			    	{
			    	if(getDetailedDTO().getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
			    	{
			    		refusedByAdmissionDept=true;
			    		break;
			    	}
			    	}
			    }
			    for(int i=0;i<getDetailedDTO().getActionDTO().size();i++){
			    	if(getDetailedDTO().getActionDTO().get(i).getActionType()!=null)
			    	{
			    	if(getDetailedDTO().getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved))
			    	{
			    		approvedByAdmissionDept=true;
			    		break;
			    	}
			    	}
			    }
				if(refusedByAdmissionDept)
				{

					boolean existed=false;
					int index=0;
	                for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
	                {
	                 if(getDetailedDTO().getActionDTO().get(i).getInstructorID().equals(Constants.ADMISSION_DEPT_ID))
	                 {
	                	
		    		    existed=true;
		    		    index=i;
	                	break;
	                 }	
	                }
					if(existed){
						dto.setStep(PetitionStepsEnum.ADMISSION_DEPT);
	            		dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Mark_As_Done_Refusing);
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "refused successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationAdmission.xhtml?id="+dto.getId());
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "refused successfully");
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Refusing is failed!");
	    		    	}
					}
					//Defing new action row
					else {
					PetitionsActionsDTO newAction=new PetitionsActionsDTO();
					newAction.setActionType(PetitionActionTypeEnum.Mark_As_Done_Refusing);
					newAction.setDate(Calendar.getInstance());
					newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
					newAction.setPetitionID(dto.getId());
					newAction.setInstructorID(Constants.ADMISSION_DEPT_ID);
					if(getNewComment()!=null)
					{
						if(!getNewComment().trim().equals(""))
						{
							newAction.setComment(getNewComment());
						}	
					}
					
					
					dto.getActionDTO().add(newAction);
					dto=facade.updateStatusOfForm(dto);
    		    	
			
					
					}
				}
				if(approvedByAdmissionDept)
				{
					boolean existed=false;
					int index=0;
	                for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
	                {
	                 if(getDetailedDTO().getActionDTO().get(i).getInstructorID().equals(Constants.ADMISSION_DEPT_ID))
	                 {
	                	
		    		    existed=true;
		    		    index=i;
	                	break;
	                 }	
	                }
					if(existed){
						dto.setStep(PetitionStepsEnum.ADMISSION_DEPT);
	            		dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Mark_As_Done_Approving);
	               		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationAdmission.xhtml?id="+dto.getId());
	    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Approving is failed!");
	    		    	}
					}
					//Defing new action row
					else {
					PetitionsActionsDTO newAction=new PetitionsActionsDTO();
					newAction.setActionType(PetitionActionTypeEnum.Mark_As_Done_Refusing);
					newAction.setDate(Calendar.getInstance());
					newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
					newAction.setPetitionID(dto.getId());
					newAction.setInstructorID(Constants.ADMISSION_DEPT_ID);
					if(getNewComment()!=null)
					{
						if(!getNewComment().trim().equals(""))
						{
							newAction.setComment(getNewComment());
						}	
					}
					
					
					dto.getActionDTO().add(newAction);
					dto=facade.updateStatusOfForm(dto);
	    		    
			
					
					}
					
				}
				
			 	
		    	if(dto!=null)
		    	{
		    	 	init();
		    	 	FacesContext.getCurrentInstance().getExternalContext().redirect
					("changeOfConcentrationAdmission.xhtml?id="+dto.getId()+"&action=approve");
		        		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Done!");
		        		sharedAcademicPetFacade.notifayNextStepOwner(dto);
		    	}
		    	else {
		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, " failed!");
		    	}
		    	}
		    	catch(Exception ex)
		    	{
		    		ex.printStackTrace();
		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, " failed!");
		    		
		    	}
		    	
		    }
				else{
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed only for registrar");
				}
				
				}
		 }
		
	 public void downloadAttachments(ChangeConcentrationDTO form)
		{
			AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
		}
		
	 
	 public void revert(){
			//TODO 
			//1- Reset the step to under review
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
				{
					try{
						InstructorDTO loggedInInstructor=getInsDataFacade.getInsByPersonMail(authentication.getName());
						
		    	ChangeConcentrationDTO dto=getDetailedDTO();
		    	dto.setReverted(true);

		    	// 1- get action of petition
		    	
		    	//2- loop on actions 
		    	boolean actionExistBefore=false;
		    	int index=0;
		    	if(dto.getActionDTO().size()>0){
		    		
		    	for(int i=0;i<dto.getActionDTO().size();i++)
		    	{
		    		actionExistBefore=false;
		      		if(dto.getActionDTO().get(i).getInstructorID().equals(getInsDataFacade.getInsByPersonMail(authentication.getName()).getId()))
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
		    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.REVERT)&&dto.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		    			{
		    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Reverted Before !");
		    				  revertedBefore=true;
		    			}
		    		
		    			//6- b) the petition already refused
		    			else 
		    			{
		    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.REVERT);
		    				revertedBefore = false;

		    				dto.getActionDTO().get(index).setComment(getContent());
		    	    			dto.setStep(PetitionStepsEnum.UNDER_REVIEW);
		    	    				
		    	    		dto=facade.updateStatusOfForm(dto);
		    		    	if(dto!=null)
		    		    	{
		    		    		try {
		    		    			if(casesID.equals("AdmissionH"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId());
		    		    			else if(casesID.equals("ins"))
		    		 			FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
		    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Reverted successfully");
		    		 				} catch (IOException e) {
		    		 					// TODO Auto-generated catch block
		    		 					e.printStackTrace();
		    		 				}
		    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
		    		    		}
		    		    	else {
		    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Reverting is failed!");
		    		    	}
		    		    	
		    			}
		    			}
		    			else // comment without action
		    			{
		    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.REVERT);
		    				revertedBefore = false;

		    				dto.getActionDTO().get(index).setComment(getContent());
	    	    			dto.setStep(PetitionStepsEnum.UNDER_REVIEW);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		try {
	    		    			if(casesID.equals("AdmissionH"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId());
	    		    			else if(casesID.equals("ins"))
	    		 			FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
	    		 		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Reverted successfully");
	    		 				} catch (IOException e) {
	    		 					// TODO Auto-generated catch block
	    		 					e.printStackTrace();
	    		 				}
	    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    		    		}
	    		    	else {
	    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Reverting is failed!");
	    		    	}
		    			}
		    		
			    	}
		    		// else there is no previous actions
		    		else 
		    		{
		    			//8- add new action object 
		    			PetitionsActionsDTO newAction=new PetitionsActionsDTO();
		    			newAction.setActionType(PetitionActionTypeEnum.REVERT);
	    				revertedBefore = false;

		    			newAction.setDate(Calendar.getInstance());
		    			newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
		    			newAction.setInstructorID(loggedInInstructor.getId());
		    			newAction.setPetitionID(dto.getId());
		    			if(getNewComment()!=null)
		    			{
		    				if(!getNewComment().trim().equals(""))
		    				{
		    					newAction.setComment(getContent());
		    				}	
		    			}
		    			
		    			
		    			dto.getActionDTO().add(newAction);
		    			dto.setStep(PetitionStepsEnum.UNDER_REVIEW);
		 				
		    	 		
		    	 		dto=facade.updateStatusOfForm(dto);
		    		    	if(dto!=null)
		    		    	{
		    		    		//init();
		    		    		try {
		    		    			if(casesID.equals("AdmissionH"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId());
		    		    			else if(casesID.equals("ins"))
		    		 			FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
		    		 		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Reverted successfully");
		    	    		    		
		    		 				} catch (IOException e) {
		    		 					// TODO Auto-generated catch block
		    		 					e.printStackTrace();
		    		 				}
		    		    		//sharedAcademicPetFacade.notifayNextStepOwner(dto);
		    		    		}
		    		    	else {
		    		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Reverting is failed!");
		    		    	}  	
		    		}
		 }
		    	else 
		    	{
		    		
		    	PetitionsActionsDTO newAction=new PetitionsActionsDTO();
	 			newAction.setActionType(PetitionActionTypeEnum.REVERT);
				revertedBefore = false;

	 			newAction.setDate(Calendar.getInstance());
	 			newAction.setFormType(FormTypesEnum.CHANGECONCENTRATION);
	 			newAction.setInstructorID(loggedInInstructor.getId());
	 			newAction.setPetitionID(dto.getId());
	 			if(getNewComment()!=null)
	 			{
	 				if(!getNewComment().trim().equals(""))
	 				{
	 					newAction.setComment(getContent());
	 				}	
	 			}
	 			
	 			
	 			dto.getActionDTO().add(newAction);
	 			dto.setStep(PetitionStepsEnum.UNDER_REVIEW);
					
	 	 		
	 	 		dto=facade.updateStatusOfForm(dto);
	 		    	if(dto!=null)
	 		    	{
	 		    		//init();
	 		    		
	 		    		try {
	 		    			if(casesID.equals("AdmissionH"))
 		 					FacesContext.getCurrentInstance().getExternalContext().redirect
 		 					("changeOfConcentrationAdmissionHead.xhtml?id="+dto.getId());
 		    			else if(casesID.equals("ins"))
 		 			FacesContext.getCurrentInstance().getExternalContext().redirect
 		 					("changeOfConcentrationIns.xhtml?id="+dto.getId());
 		 		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Reverted successfully");
	 		 				} catch (IOException e) {
	 		 					
	 		 					e.printStackTrace();
	 		 				}
	 		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
	 		    		}
	 		    	else {
	 		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Reverting is failed!");
	 		    	}
	 		    	}
	 	}
		    	catch(Exception ex)
		    	{
		    		ex.printStackTrace();
		    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Reverting is failed!");
		    	}
		    	}
					
			//2- Send email to the students with a notification to check the system
		try{
					String email="Kindly be informed that, your Change Of Concentration With id:"+getDetailedDTO().getId()+" has been reverted by "+
				getInsDataFacade.getInsByPersonMail(authentication.getName()).getName()+" And kindly check the system and his below message : <br/><br/><b>( ";
					List<String> sender=new ArrayList<String>();
					PersonDataDTO stundet=new PersonDataDTO();
					stundet=studentDataFacade.getPersonByPersonMail(getDetailedDTO().getStudent().getMail());
					//sender.add("oalaaeddin@zewailcity.edu.eg");
					sender.add(getDetailedDTO().getStudent().getMail());
					SendMailThread sendMailThread = new SendMailThread(
							sender, stundet.getNameInEng(),
							email+content+" )</b>", "Change Of Concentration - Revert");
					sendMailThread.start();
					}catch(Exception ex){
						ex.printStackTrace();
					}
			
		}
		private UploadedFile attachmentFile;

		public UploadedFile getAttachmentFile() {
		    return attachmentFile;
		}

		public void setAttachmentFile(UploadedFile file) {
		    this.attachmentFile = file;
		}
		 
		public void upload(FileUploadEvent event) {  
		    // Do what you want with the file      
		    setAttachmentFile(event.getFile());

		    try {
			} catch (Exception e) {
			}
		}  

		public void removeAttachment()
		{
			setAttachmentFile(null);
		}

		public String getAttachmentFileName()
		{
			if(attachmentFile == null)
				return "None";
			else
				return attachmentFile.getFileName();
		}

		public void downloadAttachments2(CoursePetitionDTO form)
		{
			AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
		}
		public void resendPetition(){
			//TODO 
			//Check that logged in student is the same student for the petition
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
				{
					if(getDetailedDTO().getStudent().getMail().equals(authentication.getName()))
					{
			try{
				
			//1- Update the petition
			ChangeConcentrationDTO dto=getDetailedDTO();
			if(this.attachmentFile != null)
			{
				AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
				dto.setAttachments(attachment);
			}
			dto=facade.update(dto);
			
			if(dto!=null)
			{
				String email="Kindly be informed that, the reverted Change Of Concentration With id:"+getDetailedDTO().getId()+" has been updated by the student"+
						",So kindly check the system to take the appropriate action on the petition";
							List<String> sender=new ArrayList<String>();
							InstructorDTO ins=getInsDataFacade.getInsByPersonID(getDetailedDTO().getMajor().getHeadOfMajor().getId());
							
							//TODO
							//comment
							//sender.add("oalaaeddin@zewailcity.edu.eg");
							//Uncomment 
							sender.add(ins.getMail());
							SendMailThread sendMailThread = new SendMailThread(
									sender, getDetailedDTO().getMajor().getHeadOfMajor().getName(),
									email+"</b>", "Change Of Concentration-Revert-ID:"+getDetailedDTO().getId());
							sendMailThread.start();
							FacesContext.getCurrentInstance().getExternalContext().redirect
							("changeOfConcentrationStudent.xhtml?id="+dto.getId());
				        	JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form has been submitted successfully" );
			}
			//2- Send an email 
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			}
				}
				else {
					//Alert not allowed msg 
				}
		}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public IChangeConcentrationActionsFacade getFacade() {
		return facade;
	}

	public void setFacade(IChangeConcentrationActionsFacade facade) {
		this.facade = facade;
	}

	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}

	public void setSharedAcademicPetFacade(ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}

	public ChangeConcentrationDTO getDetailedDTO() {
		return detailedDTO;
	}

	public void setDetailedDTO(ChangeConcentrationDTO detailedDTO) {
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

	public boolean isAdmissionDeptMood() {
		return admissionDeptMood;
	}

	public void setAdmissionDeptMood(boolean admissionDeptMood) {
		this.admissionDeptMood = admissionDeptMood;
	}

	public PetitionsActionsDTO getInstructorActionDetails() {
		PetitionsActionsDTO dto=new PetitionsActionsDTO();
		if(getDetailedDTO()!=null)
		{
			try
			{
				
				for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
				{
					if(getDetailedDTO().getActionDTO().get(i).getInstructorID().equals(getDetailedDTO().getMajor().getHeadOfMajor().getId()))
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

	public void setInstructorActionDetails(PetitionsActionsDTO instructorActionDetails) {
		this.instructorActionDetails = instructorActionDetails;
	}

	public PetitionsActionsDTO getAdmissionHActionDetails() {
		PetitionsActionsDTO dto=new PetitionsActionsDTO();
		if(getDetailedDTO()!=null)
		{
			
				try
				{
					
					for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
					{
						if(getDetailedDTO().getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved)||
								getDetailedDTO().getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
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

	public void setAdmissionHActionDetails(PetitionsActionsDTO admissionHActionDetails) {
		this.admissionHActionDetails = admissionHActionDetails;
	}

	public PetitionsActionsDTO getAdmissionDActionDetails() {
		PetitionsActionsDTO dto=new PetitionsActionsDTO();
		if(getDetailedDTO()!=null)
		{
			
				try
				{
					
					for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
					{
						if(getDetailedDTO().getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Approving)||
								getDetailedDTO().getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Mark_As_Done_Refusing))
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

	public void setAdmissionDActionDetails(PetitionsActionsDTO admissionDActionDetails) {
		this.admissionDActionDetails = admissionDActionDetails;
	}

	public boolean isRenderRemindMe() {
		boolean render=false; 
		switch (casesID) {
         case "Ins":
         {
        	 if(getDetailedDTO()!=null)
        	 {
        		 if(getDetailedDTO().getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
        		 {
        			 render= true;
        			
        		 }
        		
        		
        	 }
        	 break;
            
         }

                  
                  
                  
         case "AdmissionH":  
         {
        	 if(getDetailedDTO()!=null)
        	 {
        		 if(getDetailedDTO().getStep().equals(PetitionStepsEnum.PA))
        		 {
        			 render= true;
        			
        		 }
        
        	 }
        	 break;
         } 
         case "AdmissionD":  
         {
        	 if(getDetailedDTO()!=null)
        	 {
        		 if(getDetailedDTO().getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
        		 {
        			 render= true;
        			
        		 }
        
        	 }
        	   break;
         } 
         
     
      
	}
		return render; 
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

	public boolean isAdminView() {
		return adminView;
	}

	public void setAdminView(boolean adminView) {
		this.adminView = adminView;
	}
	public Date getCurrentDate() {
		return new Date();
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getRevertedBefore() {
		return revertedBefore;
	}
	public void setRevertedBefore(Boolean revertedBefore) {
		this.revertedBefore = revertedBefore;
	}
	
	
	
}
