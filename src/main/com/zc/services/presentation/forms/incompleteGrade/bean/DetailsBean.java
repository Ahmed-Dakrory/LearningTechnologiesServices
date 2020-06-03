/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.bean;

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

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.impl.SendMailThread;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeAdminFacade;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeInsFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean(name="DetailsBean")
@ViewScoped

public class DetailsBean {

   	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
   	
	@ManagedProperty("#{IIncompleteGradeInsFacade}")
	private IIncompleteGradeInsFacade facade;
	
	
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
   	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    
	@ManagedProperty("#{IIncompleteGradeAdminFacade}")
	private IIncompleteGradeAdminFacade adminFacade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
    private IGetLoggedInStudentDataFacade studentDataFacade;
    
    
	private IncompleteGradeDTO detailedDTO;
	private List<InstructorDTO> instructors;
	private Integer selectedInstructor;
	private String newComment;
	String casesID;
	private boolean oldMood;
	private boolean renderForward;
	private boolean admissionDeptMood;
	private PetitionsActionsDTO instructorActionDetails;
	private PetitionsActionsDTO deanActionDetails;
	private PetitionsActionsDTO paActionDetails;
	private PetitionsActionsDTO admissionHActionDetails;
	private PetitionsActionsDTO admissionDActionDetails;
	private boolean renderRemindMe;
	private Date notifyAt;
	private boolean adminView;
	
	private Boolean revertedBefore;
	private String content;
	@PostConstruct
	public void init()
	{
		try{
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(!authentication.getName().toLowerCase().equals(Constants.ADMISSION_HEAD)&&
					!authentication.getName().toLowerCase().equals(Constants.ADMISSION_DEPT)&&
					!(authentication.getName().startsWith("S")||authentication.getName().startsWith("s")))
					{
				fillInstructorsLst();		
					}
		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		StringBuffer url=origRequest.getRequestURL();
	
		try{
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			if(dtoID!=null){
				detailedDTO=facade.getByID(dtoID);
				if(getDetailedDTO().getStatus()!=null){
					if(getDetailedDTO().getStatus().equals(Constants.PETITION_STATUS_UNDER_REVIEW)){
						detailedDTO.setStatus(null);
					}}
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
                  
                  
         case "Dean":  
        	 approveDean();
                  break;
                  
                  
                  
         case "AdmissionH":  
        	 approveAdmissionHead();
        	 break;
        	 
        	 
        	 
       /*  case "AdmissionD":  
        	 approveAdmissionDept();
        	 break;*/
		 }
        	
	}
	

	public void refuse()
	{
		 switch (casesID) {
         case "Ins":  //Approve Of Instructor
        	 
         
        	 refuseIns();
           
                  break;
             
                  
      /*   case "PA":  
             break;
             
             */
             
         case "Dean": 
        	 refuseDean();
                  break;
                  
                  
                  
         case "AdmissionH":  
        	 refuseAdmissionHead();
        	 break;
        	 
        	 
        	 
        /* case "AdmissionD":  
        	 refuseAdmissionDept();
        	 break;*/
		 }
        	
	}
	
	public void approveIns()
	{
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	IncompleteGradeDTO dto=getDetailedDTO();
	    	//TODO updating scenario and removing program advisor step
	    	//if(!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR)&&!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
	    	if(!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
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
	    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Approved)&&dto.getReverted()!=true)
	    			{
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Approved Before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Refused)||
	    					dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.REVERT)||dto.getReverted()==true)
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
	    		   		//9- we've two cases 
	    	    		//10- a) the logged-in instructor is also PA >> set step= instructor
	    				//TODO updating scenario and removing program advisor step
	    	    		/*if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor()))
	    	    				{
	    	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    	    				}
	    		    	*///11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	    	    		//else
	    				if(loggedInInstructor.getId().equals(dto.getInstructor().getId()))
	    	    		{
	    	    			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
	    	    				}
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		    		try {
	    		    			if(casesID.equals("AdmissionH"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
	    		    			
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("incompleteGradeIns.xhtml?id="+dto.getId());
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
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
	    		   		//9- we've two cases 
	    	    		//10- a) the logged-in instructor is also PA >> set step= instructor
	    				//TODO updating scenario and removing program advisor step
	    	    		/*if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor()))
	    	    				{
	    	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    	    				}
	    		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	    	    		else
	    	    		*/
	    				if(loggedInInstructor.getId().equals(dto.getInstructor().getId()))
	    				{
	    	    			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
	    	    				}
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("incompleteGradeIns.xhtml?id="+dto.getId());
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
	    			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
	    			//9- we've two cases 
	        		//10- a) the logged-in instructor is also PA >> set step= instructor
	    			//TODO updating scenario and removing program advisor step
	        	/*	if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor().getId()))
	        				{
	        			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	        				}
	    	    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	        		else*/
	    			if(loggedInInstructor.getId().equals(dto.getInstructor().getId()))
	        		{
	        			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
	        				
	        		}
	        		dto=facade.updateStatusOfForm(dto);
	    	    	if(dto!=null)
	    	    	{
	    	    		//init();
	    	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    	    		try {
	    	 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    	 					("incompleteGradeIns.xhtml?id="+dto.getId());
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
    			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
    			//9- we've two cases 
        		//10- a) the logged-in instructor is also PA >> set step= instructor
    			//TODO updating scenario and removing program advisor step
        		/*if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor().getId()))
        				{
        			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
        				}
    	    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
        		else*/
    			if(loggedInInstructor.getId().equals(dto.getInstructor().getId()))
        		{
        			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
        				
        		}
        		dto=facade.updateStatusOfForm(dto);
    	    	if(dto!=null)
    	    	{
    	    		//init();
    	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
    	    		try {
    	 					FacesContext.getCurrentInstance().getExternalContext().redirect
    	 					("incompleteGradeIns.xhtml?id="+dto.getId());
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
	public void refuseIns()
	{ Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
	try{
	IncompleteGradeDTO dto=getDetailedDTO();
	//TODO Updating scenario and remove program advisor step
	
	//if(!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR)&&!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
	if(!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
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
			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Refused)&&dto.getReverted()!=true)
			{
				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Refused Before !");
			}
		
			//6- b) the petition already refused
			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Approved)||
					dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.REVERT)||dto.getReverted()==true)
			{
				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Refused);
		   		//9- we've two cases 
	    		//10- a) the logged-in instructor is also PA >> set step= instructor
				
				//TODO Updating scenario and removeing program advisor step
				
			/*	if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor()))
	    				{
	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    				}*/
				
				
		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	    		//else
				
	    			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
	    		
	    		dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("incompleteGradeIns.xhtml?id="+dto.getId());
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
			else // comment without action
			{
				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
		   		//9- we've two cases 
	    		//10- a) the logged-in instructor is also PA >> set step= instructor
				//TODO updating form scenario and removing program advisor step
	    	/*	if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor()))
	    				{
	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    				}*/
		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
				if(loggedInInstructor.getId().equals(dto.getInstructor().getId()))
				{
					
				
	    		
	    			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
	    				}
	    		dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("incompleteGradeIns.xhtml?id="+dto.getId());
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
			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
			//9- we've two cases 
			//10- a) the logged-in instructor is also PA >> set step= instructor
			//TODO updating form scenario ab=nd removing program advisor step
		/*	if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor()))
					{
				dto.setStep(PetitionStepsEnum.INSTRUCTOR);
					}*/
			//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
		//	else
			if(loggedInInstructor.getId().equals(dto.getInstructor().getId()))
			{
				dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
					
			}
			dto=facade.updateStatusOfForm(dto);
			if(dto!=null)
			{
				//init();
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
				try {
							FacesContext.getCurrentInstance().getExternalContext().redirect
							("incompleteGradeIns.xhtml?id="+dto.getId());
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
		newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
		//9- we've two cases 
		//10- a) the logged-in instructor is also PA >> set step= instructor
		//TODO updating form scenario and remove program advisor step
	/*	if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor()))
				{
			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
				}*/
		//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
		//else
		if(loggedInInstructor.getId().equals(dto.getInstructor().getId()))
		{
			dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
				
		}
		dto=facade.updateStatusOfForm(dto);
		if(dto!=null)
		{
			//init();
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
			try {
						FacesContext.getCurrentInstance().getExternalContext().redirect
						("incompleteGradeIns.xhtml?id="+dto.getId());
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

	public void cancel()
	{
		 switch (casesID) {
         case "Ins":  //Approve Of Instructor
        		try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
        			("incompleteGradeIns.xhtml?faces-redirect=true");
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                  break;
             
      
             
         case "Dean": 
        		try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
        			("incompleteGradeDean.xhtml?faces-redirect=true");
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                  
                  
                  
         case "AdmissionH":  
        		try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
        			("IncompleteAdmissionH.xhtml?faces-redirect=true");
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	 
        	 
         case "AdmissionD":  
        		try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
        			("incompleteAdmissionD.xhtml?faces-redirect=true");
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
	     	case "Stduent":  
        		try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
        			("incompleteGradeStudent.xhtml?faces-redirect=true");
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		
	     	case "Admin":  
        		try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
        			("incompleteGradeAdmin.xhtml?faces-redirect=true");
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
		 }
        	
		 
	
	}
	public void forwardPetition()
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
		    		InstructorDTO loggedInIns=getInsDataFacade.getInsByPersonMail(authentication.getName());
		    	
		    		IncompleteGradeDTO dto=getDetailedDTO();
		    		InstructorDTO forwardInsTo=new InstructorDTO();
		    		forwardInsTo.setId(getSelectedInstructor());
		    		dto.setForwardTOIns(forwardInsTo);
		    		InstructorDTO forwardInsFrom=new InstructorDTO();
		    		forwardInsFrom.setId(loggedInIns.getId());
		    		dto.setForwardFromIns(forwardInsFrom);
		    	
		    		if(newComment!=null && !newComment.equals(""))
				     { dto=constructComment(dto);
				     	getDetailedDTO().setComment(dto.getComment());
				     }
		    		dto=facade.forwardPetition(dto);
		    		if(dto!=null)
		    		{
		    			
		    			
		    			
		    			for(int i=0;i<instructors.size();i++)
		    			{
		    				if(instructors.get(i).getId().equals(getSelectedInstructor()))
		    				{
		    				 
		    					try {
				 					FacesContext.getCurrentInstance().getExternalContext().redirect
				 					("incompleteGradeIns.xhtml?forwaredIns="+dto.getForwardTOIns().getId());
				 					break;
				 					
				 				} catch (IOException e) {
				 					// TODO Auto-generated catch block
				 					e.printStackTrace();
				 				}
		    				
		    				}
		    			}
		    			
		    			
		    		}
		    	}
		    	catch(Exception ex)
		    	{
		    		
		    	}
		    	}}
	}

	public IncompleteGradeDTO constructComment(IncompleteGradeDTO dto) {
				Authentication authentication = SecurityContextHolder.getContext()
						.getAuthentication();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String date = sdf.format(Calendar.getInstance().getTime());

				String addedComment = "";
				if (dto.getComment() != null)
					addedComment = dto.getComment() + "\n";

				addedComment += newComment
						+ " by :"
						+ getInsDataFacade.getInsByPersonMail(authentication.getName())
								.getName() + " (Date: " + date + " )";
				dto.setComment(addedComment);
				newComment = "";
				return dto;
			}
	public void approveDean()
	{
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	IncompleteGradeDTO dto=getDetailedDTO();
	    	if(!dto.getStep().equals(PetitionStepsEnum.DEAN))
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
	    			if(dto.getActionDTO().get(i).getActionType()==null)
	    			{
	    				actionExistBefore=true;
		    			index=i;
		    		    break;
	    			}
	    			else if(dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED)||
		    				dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED))
		    		{
		    			actionExistBefore=true;
		    			index=i;
		    			break;
		    			
		    		}
	    			
	    			
	    			
	    		}
	    		
	    	}
	    		if(actionExistBefore)
		    	{
		    		//4- then we've two cases 
	    			//5- a) the petition already approved 
	    			if(dto.getActionDTO().get(index).getActionType()!=null)
	    			{
	    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED)&&dto.getReverted()!=true)
	    			{
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Approved Before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED)||
	    					dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.REVERT)||dto.getReverted()==true)
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.DEAN_APPROVED);
	    		   		
	    	    			dto.setStep(PetitionStepsEnum.DEAN);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("incompleteGradeDean.xhtml?id="+dto.getId());
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
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.DEAN_APPROVED);
	    		   		
    	    			dto.setStep(PetitionStepsEnum.DEAN);
    	    				
    	    		dto=facade.updateStatusOfForm(dto);
    		    	if(dto!=null)
    		    	{
    		    		//init();
    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
    		    		try {
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("incompleteGradeDean.xhtml?id="+dto.getId());
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
	    			newAction.setActionType(PetitionActionTypeEnum.DEAN_APPROVED);
	    			newAction.setDate(Calendar.getInstance());
	    			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
	    			dto.setStep(PetitionStepsEnum.DEAN);
	 				
	    	 		
	    	 		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("incompleteGradeDean.xhtml?id="+dto.getId());
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
 			newAction.setActionType(PetitionActionTypeEnum.DEAN_APPROVED);
 			newAction.setDate(Calendar.getInstance());
 			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
 			dto.setStep(PetitionStepsEnum.DEAN);
				
 	 		
 	 		dto=facade.updateStatusOfForm(dto);
 		    	if(dto!=null)
 		    	{
 		    		//init();
 		    		
 		    		try {
 		 					FacesContext.getCurrentInstance().getExternalContext().redirect
 		 					("incompleteGradeDean.xhtml?id="+dto.getId());
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
	public void refuseDean()
	{
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	IncompleteGradeDTO dto=getDetailedDTO();
	    	if(!dto.getStep().equals(PetitionStepsEnum.DEAN))
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
	    		if(dto.getActionDTO().get(i).getInstructorID().equals(loggedInInstructor.getId()))
	    		{
	    			if(dto.getActionDTO().get(i).getActionType()==null)
	    			{
	    				actionExistBefore=true;
		    			index=i;
		    		    break;
	    			}
	    			else if(dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED)||
		    				dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED))
		    		{
		    			actionExistBefore=true;
		    			index=i;
		    			break;
		    			
		    		}
	    			
	    			
	    			
	    		}
	    	}
	    		if(actionExistBefore)
		    	{
		    		//4- then we've two cases 
	    			//5- a) the petition already approved 
	    			if(dto.getActionDTO().get(index).getActionType()!=null)
	    			{
	    			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED)&&dto.getReverted()!=true)
	    			{
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Refused Before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED)||
	    					dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.REVERT)||dto.getReverted()==true)
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.DEAN_REFUSED);
	    		   		
	    	    			dto.setStep(PetitionStepsEnum.DEAN);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("incompleteGradeDean.xhtml?id="+dto.getId());
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
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.DEAN_REFUSED);
	    		   		
    	    			dto.setStep(PetitionStepsEnum.DEAN);
    	    				
    	    		dto=facade.updateStatusOfForm(dto);
    		    	if(dto!=null)
    		    	{
    		    		//init();
    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
    		    		try {
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("incompleteGradeDean.xhtml?id="+dto.getId());
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
	    			newAction.setActionType(PetitionActionTypeEnum.DEAN_REFUSED);
	    			newAction.setDate(Calendar.getInstance());
	    			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
	    			dto.setStep(PetitionStepsEnum.DEAN);
	 				
	    	 		
	    	 		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("incompleteGradeDean.xhtml?id="+dto.getId());
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
			newAction.setActionType(PetitionActionTypeEnum.DEAN_REFUSED);
			newAction.setDate(Calendar.getInstance());
			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
			dto.setStep(PetitionStepsEnum.DEAN);
				
	 		
	 		dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("incompleteGradeDean.xhtml?id="+dto.getId());
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
	public void approveAdmissionHead()
	{

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	IncompleteGradeDTO dto=getDetailedDTO();
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
	    		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
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
    		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
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
	    			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
	    			newAction.setInstructorID(Constants.ADMISSION_HEAD_ID);
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
	    		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
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
			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
			newAction.setInstructorID(Constants.ADMISSION_HEAD_ID);
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
		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
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
	    	IncompleteGradeDTO dto=getDetailedDTO();
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
	    		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
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
    		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
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
	    			}}
	    		// else there is no previous actions
	    		else 
	    		{
	    			//8- add new action object 
	    			PetitionsActionsDTO newAction=new PetitionsActionsDTO();
	    			newAction.setActionType(PetitionActionTypeEnum.Admission_Refused);
	    			newAction.setDate(Calendar.getInstance());
	    			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
	    			newAction.setInstructorID(Constants.ADMISSION_HEAD_ID);
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
	    		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
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
			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
			newAction.setInstructorID(Constants.ADMISSION_HEAD_ID);
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
		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
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
	public void markAsDone()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
		if(authentication.getName().equals(Constants.ADMISSION_DEPT))
		{
    	try{
    		IncompleteGradeDTO dto=getDetailedDTO();
    		if(!dto.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
			{
				dto.setNotifyAt(null);
			}
    	dto.setStep(PetitionStepsEnum.ADMISSION_DEPT);
    	dto.setPerformed(true);
   	   // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    //String date = sdf.format(Calendar.getInstance().getTime());
	   
	    boolean existedbefore=false;
	    int index=0;
	   // boolean approvedByAdmissionDept=false;
	    for(int i=0;i<getDetailedDTO().getActionDTO().size();i++){
	    	
	    	if(getDetailedDTO().getActionDTO().get(i).getInstructorID()==999) // commented before by registrar
	    	{
	    		if(getDetailedDTO().getActionDTO().get(i).getActionType()==null)
	    			{existedbefore=true;
	    			index=i;
	    		break;
	    			}
	    	}
	    }
	  /*  for(int i=0;i<getDetailedDTO().getActionDTO().size();i++){
	    	if(getDetailedDTO().getActionDTO().get(i).getActionType()!=null)
	    	if(getDetailedDTO().getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved))
	    	{
	    		approvedByAdmissionDept=true;
	    		break;
	    	}
	    }*/
		if(existedbefore)
		{
/*			PetitionsActionsDTO newAction=new PetitionsActionsDTO();
			newAction.setActionType(PetitionActionTypeEnum.Mark_As_Done_Approving);
			newAction.setDate(Calendar.getInstance());
			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
			newAction.setInstructorID(Constants.ADMISSION_DEPT_ID);
			newAction.setPetitionID(dto.getId());*/
			if(getNewComment()!=null)
			{
				if(!getNewComment().trim().equals(""))
				{
					getDetailedDTO().getActionDTO().get(index).setComment(getNewComment());
				}	
			}
			//replace
			getDetailedDTO().getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Mark_As_Done_Approving);
		}
		else {
			
			//Defing new action row
			PetitionsActionsDTO newAction=new PetitionsActionsDTO();
			newAction.setActionType(PetitionActionTypeEnum.Mark_As_Done_Approving);
			newAction.setDate(Calendar.getInstance());
			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
			newAction.setInstructorID(Constants.ADMISSION_DEPT_ID);
			newAction.setPetitionID(dto.getId());
			if(getNewComment()!=null)
			{
				if(!getNewComment().trim().equals(""))
				{
					newAction.setComment(getNewComment());
				}	
			}
			
			
			dto.getActionDTO().add(newAction);
		
	
			
			
		}
		/*else if(approvedByAdmissionDept)
		{
			String newStatus=dto.getStatus()+"\n"+Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT +" ( Date : "+date+" )";
			dto.setStatus(newStatus);
		
			
			//Defing new action row
			PetitionsActionsDTO newAction=new PetitionsActionsDTO();
			newAction.setActionType(PetitionActionTypeEnum.Mark_As_Done_Approving);
			newAction.setDate(Calendar.getInstance());
			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
			newAction.setInstructorID(Constants.ADMISSION_DEPT_ID);
			newAction.setPetitionID(dto.getId());
			if(getNewComment()!=null)
			{
				if(!getNewComment().trim().equals(""))
				{
					newAction.setComment(getNewComment());
				}	
			}
			
			
			dto.getActionDTO().add(newAction);
		
	
		
			
			
		}*/

	    dto.setStep(PetitionStepsEnum.ADMISSION_DEPT);
				
		
		dto=facade.updateStatusOfForm(dto);
		
    	if(dto!=null)
    	{
    	 	init();
    	 	FacesContext.getCurrentInstance().getExternalContext().redirect
			("incompleteAdmissionD.xhtml?id="+dto.getId()+"&action=approve");
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
	 public void downloadAttachments(IncompleteGradeDTO form)
		{
			AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
		}
	 
	public void addComment(IncompleteGradeDTO dto) {
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
						actionDTO.setFormType(FormTypesEnum.INCOMPLETEGRADE);
						actionDTO.setPetitionID(getDetailedDTO().getId());
					/*	if(authentication.getName().equals(Constants.ADMISSION_DEPT))
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
				}
				
				else 
				{
					PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
					actionDTO.setComment(getNewComment());
					actionDTO.setDate(Calendar.getInstance());
					actionDTO.setFormType(FormTypesEnum.INCOMPLETEGRADE);
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
						email, "Incomplete grade form - new comment");
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
	public void notifyUser()
	{
		try{
		adminFacade.notifyNextUser(getDetailedDTO());
		JavaScriptMessagesHandler.RegisterNotificationMessage(null,
				"Notification was sent");
		}
		catch(Exception ex)
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null,
					"System Error");
		}
	}
	
	public void revert(){
		//TODO 
		//1- Reset the step to under review
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				try{
					InstructorDTO loggedInInstructor=getInsDataFacade.getInsByPersonMail(authentication.getName());
					
	    	IncompleteGradeDTO dto=getDetailedDTO();
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
	    				  revertedBefore = true;
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
	    		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
	    		    			else if(casesID.equals("Ins"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("incompleteGradeIns.xhtml?id="+dto.getId());
		    		 			else if(casesID.equals("Dean"))
			    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
			    		 					("incompleteGradeDean.xhtml?id="+dto.getId());
		    		 			else if(casesID.equals("AdmissionD"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("incompleteAdmissionD.xhtml?id="+dto.getId());		
	    		    	
	    		    			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Reverted successfully");
	    		    			sendRevertemail();
	    		 				} catch (Exception e) {
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
    		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
    		    			else if(casesID.equals("Ins"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("incompleteGradeIns.xhtml?id="+dto.getId());
	    		 			else if(casesID.equals("Dean"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("incompleteGradeDean.xhtml?id="+dto.getId());
	    		 			else if(casesID.equals("AdmissionD"))
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("incompleteAdmissionD.xhtml?id="+dto.getId());		
    		    	
    		    			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Reverted successfully");
    		    			sendRevertemail();
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
	    			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
	    			newAction.setInstructorID(loggedInInstructor.getId());
	    			newAction.setPetitionID(dto.getId());
	    			newAction.setComment(getContent());
	    			dto.getActionDTO().add(newAction);
	    			dto.setStep(PetitionStepsEnum.UNDER_REVIEW);
	 				
	    	 		
	    	 		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		try {
	    		    			if(casesID.equals("AdmissionH"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
	    		    			else if(casesID.equals("Ins"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("incompleteGradeIns.xhtml?id="+dto.getId());
		    		 			else if(casesID.equals("Dean"))
			    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
			    		 					("incompleteGradeDean.xhtml?id="+dto.getId());
		    		 			else if(casesID.equals("AdmissionD"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("incompleteAdmissionD.xhtml?id="+dto.getId());		
	    		    	
	    		    			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Reverted successfully");
	    		    				sendRevertemail();
	    	    		    		
	    		 				} catch (Exception e) {
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
 			newAction.setFormType(FormTypesEnum.INCOMPLETEGRADE);
 			newAction.setInstructorID(loggedInInstructor.getId());
 			newAction.setPetitionID(dto.getId());
 			newAction.setComment(getContent());
 			dto.getActionDTO().add(newAction);
 			dto.setStep(PetitionStepsEnum.UNDER_REVIEW);
				
 	 		
 	 		dto=facade.updateStatusOfForm(dto);
 		    	if(dto!=null)
 		    	{
 		    		//init();
 		    		
 		    		try {
 		    			if(casesID.equals("AdmissionH"))
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("IncompleteAdmissionH.xhtml?id="+dto.getId());
		    			else if(casesID.equals("Ins"))
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("incompleteGradeIns.xhtml?id="+dto.getId());
    		 			else if(casesID.equals("Dean"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("incompleteGradeDean.xhtml?id="+dto.getId());
    		 			else if(casesID.equals("AdmissionD"))
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("incompleteAdmissionD.xhtml?id="+dto.getId());		
		    	
		    			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Reverted successfully");
		    				sendRevertemail();
 		 				} catch (Exception e) {
 		 					
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
				
	

		
	}
	//2- Send email to the students with a notification to check the system
	public void sendRevertemail(){
		try{
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				
			String email="Kindly be informed that, your Incomplete Grade Petition With id:"+getDetailedDTO().getId()+" has been reverted by "+
		getInsDataFacade.getInsByPersonMail(authentication.getName()).getName()+".kindly check the petition on LTS system.<br/>The comment for the reverting is below: <br/><br/><b>( ";
			List<String> sender=new ArrayList<String>();
			PersonDataDTO stundet=new PersonDataDTO();
			stundet=studentDataFacade.getPersonByPersonMail(getDetailedDTO().getStudent().getMail());
			//sender.add("oalaaeddin@zewailcity.edu.eg");
			sender.add(getDetailedDTO().getStudent().getMail());
			SendMailThread sendMailThread = new SendMailThread(
					sender, stundet.getNameInEng(),
					email+content+" )</b>", "Incomplete Grade Petition - Revert");
			sendMailThread.start();
			RequestContext.getCurrentInstance().execute("revertDlg.hide();");
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
		IncompleteGradeDTO dto=getDetailedDTO();
		if(this.attachmentFile != null)
		{
			AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
			dto.setAttachments(attachment);
		}
		dto=facade.update(dto);
		
		if(dto!=null)
		{
			String email="Kindly be informed that, the reverted Incomplete Grade Form With id:"+getDetailedDTO().getId()+" has been updated by the student"+
					",So kindly check the system to take the appropriate action on the petition";
						List<String> sender=new ArrayList<String>();
						InstructorDTO ins=getInsDataFacade.getInsByPersonID(getDetailedDTO().getInstructor().getId());
						
						//TODO
						//comment
						//sender.add("oalaaeddin@zewailcity.edu.eg");
						//Uncomment 
						sender.add(ins.getMail());
						SendMailThread sendMailThread = new SendMailThread(
								sender, getDetailedDTO().getInstructor().getName(),
								email+"</b>", "Incomplete Grade Form-Revert-ID:"+getDetailedDTO().getId());
						sendMailThread.start();
						FacesContext.getCurrentInstance().getExternalContext().redirect
						("incompleteGradeStudent.xhtml?id="+dto.getId());
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
	public IIncompleteGradeInsFacade getFacade() {
		return facade;
	}
	public void setFacade(IIncompleteGradeInsFacade facade) {
		this.facade = facade;
	}
	public IncompleteGradeDTO getDetailedDTO() {
		return detailedDTO;
	}
	public void setDetailedDTO(IncompleteGradeDTO detailedDTO) {
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
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}
	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
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
		try
		{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication.getName().toLowerCase().equals(Constants.ADMISSION_DEPT)||
					authentication.getName().toLowerCase().equals(Constants.ADMISSION_HEAD))
			{
				return false;
			}
			else return true;
		}
		catch(Exception ex)
		{
			ex.toString();
			return false;
		}
		
	}
	public void setRenderForward(boolean renderForward) {
		this.renderForward = renderForward;
	}
	public boolean isAdmissionDeptMood() {
		try
		{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication.getName().toLowerCase().equals(Constants.ADMISSION_DEPT))
			{
				return true;
			}
			else return false;
		}
		catch(Exception ex)
		{
			ex.toString();
			return false;
		}
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
					if(getDetailedDTO().getActionDTO().get(i).getInstructorID().equals(getDetailedDTO().getInstructor().getId()))
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
	public void setInstructorActionDetails(
			PetitionsActionsDTO instructorActionDetails) {
		this.instructorActionDetails = instructorActionDetails;
	}
	public PetitionsActionsDTO getDeanActionDetails() {
		PetitionsActionsDTO dto=new PetitionsActionsDTO();
		if(getDetailedDTO()!=null)
		{
			
				try
				{
					
					for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
					{
						if(getDetailedDTO().getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED)||
								getDetailedDTO().getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED))
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
	public void setDeanActionDetails(PetitionsActionsDTO deanActionDetails) {
		this.deanActionDetails = deanActionDetails;
	}
	public PetitionsActionsDTO getPaActionDetails() {
		PetitionsActionsDTO dto=new PetitionsActionsDTO();
		if(getDetailedDTO()!=null)
		{
			if(getDetailedDTO().getStep().equals(PetitionStepsEnum.INSTRUCTOR)||getDetailedDTO().getStep().equals(PetitionStepsEnum.DEAN)
					||getDetailedDTO().getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING)||getDetailedDTO().getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
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
	public void setPaActionDetails(PetitionsActionsDTO paActionDetails) {
		this.paActionDetails = paActionDetails;
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
	public void setAdmissionHActionDetails(
			PetitionsActionsDTO admissionHActionDetails) {
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
	public void setAdmissionDActionDetails(
			PetitionsActionsDTO admissionDActionDetails) {
		this.admissionDActionDetails = admissionDActionDetails;
	}
	public boolean isRenderRemindMe() {
		boolean render=false; 
		switch (casesID) {
         case "Ins":  //Approve Of Instructor
        	 
         {
        	 if(getDetailedDTO()!=null)
        	 {
        		 if(getDetailedDTO().getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
        		 {
        			 render= true;
        		 }
        		 else if(getDetailedDTO().getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
        		 {
        			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        			 if(getInsDataFacade.getInsByPersonMail(authentication.getName()).getId().equals(getDetailedDTO().getMajor().getHeadOfMajor().getId())){
        				 render=true;
        				 
        			 }
        			 
        		 }
        		 else if(getDetailedDTO().getStep().equals(PetitionStepsEnum.INSTRUCTOR))
        		 {
        			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        			 if(getInsDataFacade.getInsByPersonMail(authentication.getName()).getId().equals(getDetailedDTO().getMajor().getHeadOfMajor().getId())){
        				 render=false;
        				 
        			 }
        			 
        		 }
        	 }
        	
            
         }
             
                  
                  
         case "Dean":  
        	
        	 {
        		 if(getDetailedDTO()!=null)
            	 {
            		 if(getDetailedDTO().getStep().equals(PetitionStepsEnum.INSTRUCTOR))
            		 {
            			 render= true;
            		 }
            
            	 }
            	
        	 }
                  
                  
                  
         case "AdmissionH":  
         {
        	 if(getDetailedDTO()!=null)
        	 {
        		 if(getDetailedDTO().getStep().equals(PetitionStepsEnum.DEAN))
        		 {
        			 render= true;
        		 }
        
        	 }
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
         } 
         
     
		 
	}
		return render; 
		}
	public void setRenderRemindMe(boolean renderRemindMe) {
		this.renderRemindMe = renderRemindMe;
	}
	public Date getCurrentDate() {
		return new Date();
	}
	public Date getNotifyAt() {
		return notifyAt;
	}
	public void setNotifyAt(Date notifyAt) {
		this.notifyAt = notifyAt;
	}
	public boolean isAdminView() {
		if(casesID.equals("Admin"))
		{
			return true;
		}
		
		else 
			return false;
	}
	public void setAdminView(boolean adminView) {
		this.adminView = adminView;
	}
	public IIncompleteGradeAdminFacade getAdminFacade() {
		return adminFacade;
	}
	public void setAdminFacade(IIncompleteGradeAdminFacade adminFacade) {
		this.adminFacade = adminFacade;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	
	public Boolean getRevertedBefore() {
		return revertedBefore;
	}
	public void setRevertedBefore(Boolean revertedBefore) {
		this.revertedBefore = revertedBefore;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
