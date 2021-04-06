/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.bean;

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
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatAdminFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.IJuniorTAActionsFacade;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.IJuniorTAFacadeInstructor;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.ISkipMajorHeadCoursesFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;

import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean(name="DetailsBeanJuniorTA")
@ViewScoped
public class DetailsBean {

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
	
	@ManagedProperty("#{IJuniorTAActionsFacade}")
	private IJuniorTAActionsFacade facade;
	
	@ManagedProperty("#{IJuniorTAFacadeInstructor}")
	private IJuniorTAFacadeInstructor juniorTAfacade;
	
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
   	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    
    @ManagedProperty("#{ICourseRepeatAdminFacade}")
   	private ICourseRepeatAdminFacade adminFacade;
    
    @ManagedProperty("#{ISkipMajorHeadCoursesFacade}")
   	private ISkipMajorHeadCoursesFacade skipMajorHead;
    
    
    private TAJuniorProgramDTO detailedDTO;
    
	private boolean oldMood;
   
   	
   	private PetitionsActionsDTO instructorActionDetails;
   	private PetitionsActionsDTO paActionDetails;
   	private PetitionsActionsDTO deanActionDetails;

   	private boolean renderRemindMe;
   	private Date notifyAt;
    private Integer selectedType;
 	private boolean adminView;
   	private String newComment;
   	String casesID;
   	private boolean paMode;
   	private boolean skipMajorHeadCourse;
   	private Integer numberOfApprovedCourses;
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
				detailedDTO=facade.getByID(dtoID);
			    if(getDetailedDTO().getStep().equals(PetitionStepsEnum.PA))
			    {
			    	if(skipMajorHead.checkCourse(getDetailedDTO().getCourse().getId()))
			    	{
			    		getDetailedDTO().setCurStatus("Reviewd by Course coordinator("+getDetailedDTO().getCourse().getCoordinator().getName()+")");
			    		//getDetailedDTO().setNextStatus("Reviewing by Dean of students("+getDetailedDTO().getCourse().getCoordinator().getName()+")");
			    		
			    	}
			    }
				setNotifyAt(getDetailedDTO().getNotifyAt());
				casesID=origRequest.getParameterValues("cases")[0];
			
			    try{
			    if(casesID.equals("Ins"))
			    		{
			    List<TAJuniorProgramDTO> approved=juniorTAfacade.getByCourseID(getDetailedDTO().getCourse().getId());
	    		List<TAJuniorProgramDTO> filteredApproved=new ArrayList<TAJuniorProgramDTO>();
	    		InstructorDTO ins=getInsDataFacade.getInsByPersonMail(authentication.getName());
	    		
	    		for(int i=0;i<approved.size();i++)
	    		{
	    			boolean b=facade.approvedBefore(approved.get(i).getId(), FormTypesEnum.JUNIORTAPROGRAM.getID(), ins.getId());
	    			if(b)
	    			{
	    				filteredApproved.add(approved.get(i));
	    			}
	    		}
	    		
	    		numberOfApprovedCourses=filteredApproved.size();
			    }
			    }
			    catch(Exception ex){
			    	ex.toString();
			    }
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
   	public void approve()
	{
		 switch (casesID) {
         case "Ins":  //Approve Of Instructor
        	 
         {
        	 //
        	 //TODO open dialog to insert no. of hrs and task
        	if(!getDetailedDTO().getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
        	{
        		approveIns();
        	}
        	 
         }
                  break;
                  
                  
         case "Dean":  
        	 approveDean();
                  break;

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
                  

		 }
        	
	}
   	public void approveIns()
	{
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	TAJuniorProgramDTO dto=getDetailedDTO();
	    	
	    	if(!dto.getStep().equals(PetitionStepsEnum.PA)&&!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
	    	//if(!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
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
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Approved Before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Refused))
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
	    		   		//9- we've two cases 
	    	    		//10- a) the logged-in instructor is also PA >> set step= instructor
	    				//TODO updating scenario and removing program advisor step
	    	    		if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor().getId()))
	    	    				{
	    	    			dto.setStep(PetitionStepsEnum.PA);
	    	    				}
	    		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	    	    		else
	    				if(loggedInInstructor.getId().equals(dto.getCourse().getCoordinator().getId()))
	    	    		{
	    					if(skipMajorHead.checkCourse(dto.getCourse().getId()))
	    					{
	    						dto.setStep(PetitionStepsEnum.PA);
	    					}
	    					else
	    	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    	    				}
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("juniorTAProgIns.xhtml?id="+dto.getId());
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
	    	    		if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor().getId()))
	    	    				{
	    	    			dto.setStep(PetitionStepsEnum.PA);
	    	    				}
	    		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	    	    		else
	       				if(loggedInInstructor.getId().equals(dto.getCourse().getCoordinator().getId()))
	    				{
	       					if(skipMajorHead.checkCourse(dto.getCourse().getId()))
	    					{
	    						dto.setStep(PetitionStepsEnum.PA);
	    					}
	    					else
	    	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    	    				}
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("juniorTAProgIns.xhtml?id="+dto.getId());
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
	    			newAction.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
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
	        		if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor().getId()))
	        				{
	        			dto.setStep(PetitionStepsEnum.PA);
	        				}
	    	    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	        		else
	    			if(loggedInInstructor.getId().equals(dto.getCourse().getCoordinator().getId()))
	    				
	        		{
	    				if(skipMajorHead.checkCourse(dto.getCourse().getId()))
    					{
    						dto.setStep(PetitionStepsEnum.PA);
    					}
    					else
	        			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	        				
	        		}
	        		dto=facade.updateStatusOfForm(dto);
	    	    	if(dto!=null)
	    	    	{
	    	    		//init();
	    	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    	    		try {
	    	 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    	 					("juniorTAProgIns.xhtml?id="+dto.getId());
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
    			newAction.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
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
        		if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor().getId()))
        				{
        			dto.setStep(PetitionStepsEnum.PA);
        				}
    	    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
        		else
    			if(loggedInInstructor.getId().equals(dto.getCourse().getCoordinator().getId()))
        		{
    				if(skipMajorHead.checkCourse(dto.getCourse().getId()))
					{
						dto.setStep(PetitionStepsEnum.PA);
					}
					else
        			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
        				
        		}
        		dto=facade.updateStatusOfForm(dto);
    	    	if(dto!=null)
    	    	{
    	    		//init();
    	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
    	    		try {
    	 					FacesContext.getCurrentInstance().getExternalContext().redirect
    	 					("juniorTAProgIns.xhtml?id="+dto.getId());
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
	TAJuniorProgramDTO dto=getDetailedDTO();
	//TODO Updating scenario and remove program advisor step
	
	if(!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR)&&!dto.getStep().equals(PetitionStepsEnum.PA))
	//if(!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
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
				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Refused Before !");
			}
		
			//6- b) the petition already refused
			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Approved))
			{
				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Refused);
		   		//9- we've two cases 
	    		//10- a) the logged-in instructor is also PA >> set step= instructor
				
				//TODO Updating scenario and removeing program advisor step
				
				if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor().getId()))
	    				{
	    			dto.setStep(PetitionStepsEnum.PA);
	    				}
				
				
		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	    		else
	    			{if(skipMajorHead.checkCourse(dto.getCourse().getId()))
					{
						dto.setStep(PetitionStepsEnum.PA);
					}
					else
	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    			}
	    		
	    		dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("juniorTAProgIns.xhtml?id="+dto.getId());
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
	    		if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor().getId()))
	    				{
	    			dto.setStep(PetitionStepsEnum.PA);
	    				}
		    	//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
	    		else 
				if(loggedInInstructor.getId().equals(dto.getCourse().getCoordinator().getId()))
				{
					
				
					if(skipMajorHead.checkCourse(dto.getCourse().getId()))
					{
						dto.setStep(PetitionStepsEnum.PA);
					}
					else
	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    				}
	    		dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("juniorTAProgIns.xhtml?id="+dto.getId());
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
			newAction.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
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
			if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor().getId()))
					{
				dto.setStep(PetitionStepsEnum.PA);
					}
			//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
			else
			if(loggedInInstructor.getId().equals(dto.getCourse().getCoordinator().getId()))
			{
				if(skipMajorHead.checkCourse(dto.getCourse().getId()))
				{
					dto.setStep(PetitionStepsEnum.PA);
				}
				else
				dto.setStep(PetitionStepsEnum.INSTRUCTOR);
					
			}
			dto=facade.updateStatusOfForm(dto);
			if(dto!=null)
			{
				//init();
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
				try {
							FacesContext.getCurrentInstance().getExternalContext().redirect
							("juniorTAProgIns.xhtml?id="+dto.getId());
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
		newAction.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
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
		if(loggedInInstructor.getId().equals(dto.getMajor().getHeadOfMajor().getId()))
				{
			dto.setStep(PetitionStepsEnum.PA);
				}
		//11- b) the logged-in instructor is n't PA >> set step = instructor of the course
		else
		if(loggedInInstructor.getId().equals(dto.getCourse().getCoordinator().getId()))
		{
			if(skipMajorHead.checkCourse(dto.getCourse().getId()))
			{
				dto.setStep(PetitionStepsEnum.PA);
			}
			else
			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
				
		}
		dto=facade.updateStatusOfForm(dto);
		if(dto!=null)
		{
			//init();
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
			try {
						FacesContext.getCurrentInstance().getExternalContext().redirect
						("juniorTAProgIns.xhtml?id="+dto.getId());
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
     			("taJuniorProgramStudent.xhtml?faces-redirect=true");
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
   	public TAJuniorProgramDTO constructComment(TAJuniorProgramDTO dto) {
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
		TAJuniorProgramDTO dto=getDetailedDTO();
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
			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED))
			{
				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Approved Before !");
			}
		
			//6- b) the petition already refused
			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED))
			{
				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.DEAN_APPROVED);
		   		
	    			dto.setStep(PetitionStepsEnum.DEAN);
	    				dto.setPerformed(true);
	    		dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("juniorTAProgDean.xhtml?id="+dto.getId());
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
    				dto.setPerformed(true);
    		dto=facade.updateStatusOfForm(dto);
	    	if(dto!=null)
	    	{
	    		//init();
	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		try {
	 					FacesContext.getCurrentInstance().getExternalContext().redirect
	 					("juniorTAProgDean.xhtml?id="+dto.getId());
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
			newAction.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
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
				
	 		dto.setPerformed(true);
	 		dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("juniorTAProgDean.xhtml?id="+dto.getId());
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
		newAction.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
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
		
		dto.setPerformed(true);
		dto=facade.updateStatusOfForm(dto);
	    	if(dto!=null)
	    	{
	    		//init();
	    		
	    		try {
	 					FacesContext.getCurrentInstance().getExternalContext().redirect
	 					("juniorTAProgDean.xhtml?id="+dto.getId());
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
	TAJuniorProgramDTO dto=getDetailedDTO();
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
			if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED))
			{
				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Refused Before !");
			}
		
			//6- b) the petition already refused
			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED))
			{
				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.DEAN_REFUSED);
		   		
	    			dto.setStep(PetitionStepsEnum.DEAN);
	    				dto.setPerformed(true);
	    		dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("juniorTAProgDean.xhtml?id="+dto.getId());
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
    				dto.setPerformed(true);
    		dto=facade.updateStatusOfForm(dto);
	    	if(dto!=null)
	    	{
	    		//init();
	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		try {
	 					FacesContext.getCurrentInstance().getExternalContext().redirect
	 					("juniorTAProgDean.xhtml?id="+dto.getId());
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
			newAction.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
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
				
	 		dto.setPerformed(true);
	 		dto=facade.updateStatusOfForm(dto);
		    	if(dto!=null)
		    	{
		    		//init();
		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		    		try {
		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		 					("juniorTAProgDean.xhtml?id="+dto.getId());
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
	newAction.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
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
		
		dto.setPerformed(true);
		dto=facade.updateStatusOfForm(dto);
    	if(dto!=null)
    	{
    		//init();
    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
    		try {
 					FacesContext.getCurrentInstance().getExternalContext().redirect
 					("juniorTAProgDean.xhtml?id="+dto.getId());
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
public void addComment(TAJuniorProgramDTO dto) {
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
					if(authentication.getName().equals(Constants.ADMISSION_DEPT))
						facade.addComment(actionDTO,Constants.ADMISSION_DEPT_ID);
						else if(authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL))
								facade.addComment(actionDTO,Constants.REGISTRAR_HEAD_ID);
						else 	
							facade.addComment(actionDTO,getInsDataFacade.getInsByPersonMail(authentication.getName()).getId());setNewComment(null);
					
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Comment was sent successfully");
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+getDetailedDTO().getId()+"&cases="+casesID);
				}
				else 
				{
					PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
					actionDTO.setComment(getNewComment());
					actionDTO.setDate(Calendar.getInstance());
					actionDTO.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
					actionDTO.setPetitionID(getDetailedDTO().getId());
					if(authentication.getName().equals(Constants.ADMISSION_DEPT))
					facade.addComment(actionDTO,Constants.ADMISSION_DEPT_ID);
					else if(authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL))
							facade.addComment(actionDTO,Constants.REGISTRAR_HEAD_ID);
					else 	
						facade.addComment(actionDTO,getInsDataFacade.getInsByPersonMail(authentication.getName()).getId());
					setNewComment(null);
					//getDetailedDTO().setComment(getNewComment());
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Comment was sent successfully");
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+getDetailedDTO().getId()+"&cases="+casesID);
				}
			}
			
			else 
			{
				PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
				actionDTO.setComment(getNewComment());
				actionDTO.setDate(Calendar.getInstance());
				actionDTO.setFormType(FormTypesEnum.JUNIORTAPROGRAM);
				actionDTO.setPetitionID(getDetailedDTO().getId());
				if(authentication.getName().equals(Constants.ADMISSION_DEPT))
				facade.addComment(actionDTO,Constants.ADMISSION_DEPT_ID);
				else if(authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL))
						facade.addComment(actionDTO,Constants.REGISTRAR_HEAD_ID);
				else 	
					facade.addComment(actionDTO,getInsDataFacade.getInsByPersonMail(authentication.getName()).getId());
				setNewComment(null);
				//getDetailedDTO().setComment(getNewComment());
				JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Comment was sent successfully");
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+getDetailedDTO().getId()+"&cases="+casesID);
			}
			//
			
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public void cancelComment()
{
	newComment=null;
}   
/*public void notifyUser()
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
}*/
public void editHoursAndTask()
{
	try{
	TAJuniorProgramDTO newDTO=facade.updateRequest(getDetailedDTO());
	if(newDTO!=null){
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Updated Successfully");
		  RequestContext.getCurrentInstance().execute("myDialogVar.hide();");
	}
	}
	catch(Exception ex)
	{	
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error");
	}
}
public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public IJuniorTAActionsFacade getFacade() {
		return facade;
	}
	public void setFacade(IJuniorTAActionsFacade facade) {
		this.facade = facade;
	}
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}
	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}
	public ICourseRepeatAdminFacade getAdminFacade() {
		return adminFacade;
	}
	public void setAdminFacade(ICourseRepeatAdminFacade adminFacade) {
		this.adminFacade = adminFacade;
	}
	public TAJuniorProgramDTO getDetailedDTO() {
		return detailedDTO;
	}
	public void setDetailedDTO(TAJuniorProgramDTO detailedDTO) {
		this.detailedDTO = detailedDTO;
	}
	public boolean isOldMood() {
		return oldMood;
	}
	public void setOldMood(boolean oldMood) {
		this.oldMood = oldMood;
	}

	public PetitionsActionsDTO getInstructorActionDetails() {
		PetitionsActionsDTO dto=new PetitionsActionsDTO();
		if(getDetailedDTO()!=null)
		{
			try
			{
				
				for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
				{
					//TODO 
					// IT will be the major head instead of dropped course instructor
					if(getDetailedDTO().getActionDTO().get(i).getInstructorID().equals(getDetailedDTO().getCourse().getCoordinator().getId()))
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
       		
       		
       	 }
       	 break;
           
        }
            
        case "PA":  //Approve Of PA
          	 
        {
       	 if(getDetailedDTO()!=null)
       	 {
       		 if(getDetailedDTO().getStep().equals(PetitionStepsEnum.INSTRUCTOR))
       		 {
       			 render= true;
       			
       		 }
       		
       		
       	 }
       	 break;
           
        }
                 
        case "Dean":  
       	
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
	public Integer getSelectedType() {
		return selectedType;
	}
	public void setSelectedType(Integer selectedType) {
		this.selectedType = selectedType;
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
	public PetitionsActionsDTO getPaActionDetails() {
		PetitionsActionsDTO dto=new PetitionsActionsDTO();
		if(getDetailedDTO()!=null)
		{
			try
			{
				
				for(int i=0;i<getDetailedDTO().getActionDTO().size();i++)
				{
					//TODO 
					// IT will be the major head instead of dropped course instructor
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
	public boolean isPaMode() {
		
		try 
		{Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if(authentication.getName().equals(getDetailedDTO().getMajor().getHeadOfMajor().getMail())){
			return true;
		}
		else return false;}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}
	public void setPaMode(boolean paMode) {
		this.paMode = paMode;
	}
	public ISkipMajorHeadCoursesFacade getSkipMajorHead() {
		return skipMajorHead;
	}
	public void setSkipMajorHead(ISkipMajorHeadCoursesFacade skipMajorHead) {
		this.skipMajorHead = skipMajorHead;
	}
	public boolean isSkipMajorHeadCourse() {
		try{
		return skipMajorHead.checkCourse(getDetailedDTO().getCourse().getId());
		}
		catch(Exception ex){
			return true;
		}
	}
	public void setSkipMajorHeadCourse(boolean skipMajorHeadCourse) {
		this.skipMajorHeadCourse = skipMajorHeadCourse;
	}
	public Integer getNumberOfApprovedCourses() {
		return numberOfApprovedCourses;
	}
	public void setNumberOfApprovedCourses(Integer numberOfApprovedCourses) {
		this.numberOfApprovedCourses = numberOfApprovedCourses;
	}
	public IJuniorTAFacadeInstructor getJuniorTAfacade() {
		return juniorTAfacade;
	}
	public void setJuniorTAfacade(IJuniorTAFacadeInstructor juniorTAfacade) {
		this.juniorTAfacade = juniorTAfacade;
	}
 	
 	
 	
 	
    
}
