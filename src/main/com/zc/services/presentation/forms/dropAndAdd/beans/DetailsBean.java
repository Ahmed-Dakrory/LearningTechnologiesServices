/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.beans;

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

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.shared.service.impl.SendMailThread;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.DropTypesEnum;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IAddDropActionsFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IAdminAddDropFormFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IStudentAddDropFormFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
@ManagedBean(name="DetailsBeanDropAdd")
@ViewScoped
public class DetailsBean {

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
   	
	@ManagedProperty("#{IAddDropActionsFacade}")
	private IAddDropActionsFacade facade;
	
	
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
   	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    
    @ManagedProperty("#{AdminAddDropFormFacadeImpl}")
    private IAdminAddDropFormFacade adminFacade;
    
    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
    private IGetLoggedInStudentDataFacade studentDataFacade;
    
	@ManagedProperty("#{StudentAddDropFormFacadeImpl}")
	private IStudentAddDropFormFacade insFacade;
	
    private DropAddFormDTO detailedDTO;
	private List<InstructorDTO> instructors;
	private Integer selectedInstructor;
	private String newComment;
	String casesID;
	private boolean oldMood;
	private boolean renderForward;
	private boolean admissionDeptMood;
	private PetitionsActionsDTO instructorActionDetails;
	private PetitionsActionsDTO deanActionDetails;
	private PetitionsActionsDTO admissionHActionDetails;
	private PetitionsActionsDTO admissionDActionDetails;
	private boolean renderRemindMe;
	private Date notifyAt;
    private Integer selectedType;
    private List<BaseDTO> dropTypeList;
  	private boolean adminView;
  
	private String content;
	private Boolean revertedBefore;
	private Integer selectedAction=0;
    private boolean showAddPnl=false;
    private boolean showDropPnl=false;
    private boolean showDropAddPnl=false;
	private List<BaseDTO> actionsPhase1;
	private List<InstructorDTO>insLst;
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
		if(getDetailedDTO().getReverted()&&(authentication.getName().startsWith("S")||authentication.getName().startsWith("s"))){
			fillInstructorsLstDialog();
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
		dropTypeList=new ArrayList<BaseDTO>();
		dropTypeList.add(new BaseDTO(1,"WP"));
		dropTypeList.add(new BaseDTO(2,"WF"));
		if(getDetailedDTO().getDropType()!=null)
		{
			if(getDetailedDTO().getDropType().equals(DropTypesEnum.WF))
			setSelectedType(2);
			else if(getDetailedDTO().getDropType().equals(DropTypesEnum.WP))
				setSelectedType(1);
		}
		

	}
	public void fillInstructorsLstDialog()
	{
		insLst=new ArrayList<InstructorDTO>();
	
		try{
			insLst=insFacade.getAllInstructorsOfCourse(getDetailedDTO().getDropCourse().getId());
		}
		catch(Exception ex){
			System.out.println(ex.toString());
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
        	 appoveAdmissionHead();
        	 break;
        	 
        
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
                  
                  
         case "Dean":  
        	 refuseDean();
                  break;
                  
                  
                  
         case "AdmissionH":  
        	 refuseAdmissionHead();
        	 break;
        	 
         case "AdmissionD":  
        	 refuseAdmissionDep();
        	 break;
        	 
        
		 }
	 }
	 
	 
	 public void refuseAdmissionDep()
	 {
   		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
		if(authentication.getName().equals(Constants.ADMISSION_DEPT))
		{
    	try{
    		DropAddFormDTO dto=getDetailedDTO();
    		if(!dto.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
			{
				dto.setNotifyAt(null);
			}

    		dto.setStatus(PetitionStepsEnum.ADMISSION_DEPT.getName());
    	dto.setStep(PetitionStepsEnum.ADMISSION_DEPT);
    	dto.setPerformed(true);
   	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    String date = sdf.format(Calendar.getInstance().getTime());
	   
	  
		
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
	dto.setStatus(PetitionStepsEnum.ADMISSION_DEPT.getName());
	dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Refused);
	dto=facade.updateStatusOfForm(dto);
	if(dto!=null)
	{
		//init();
		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
		try {
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("addDropAdmissionDept.xhtml?id="+dto.getId());
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
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
newAction.setActionType(PetitionActionTypeEnum.Refused);
newAction.setDate(Calendar.getInstance());
dto.setStatus(PetitionStepsEnum.ADMISSION_DEPT.getName());
newAction.setFormType(FormTypesEnum.DROPADD);
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

dto.setStep(PetitionStepsEnum.ADMISSION_DEPT);
sharedAcademicPetFacade.notifayNextStepOwner(dto);

}

				
		
		dto=facade.updateStatusOfForm(dto);

    	System.out.println("DakroryNew: 7");
    	if(dto!=null)
    	{
    	 	init();
    	 	FacesContext.getCurrentInstance().getExternalContext().redirect
			("addDropAdmissionDept.xhtml?id="+dto.getId()+"&action=refuse");
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
   	
	 
	 public void approveIns()
	 {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
		
				if(getDetailedDTO().getDroppedCourseIns()==null||(getDetailedDTO().getDroppedCourseIns()!=null&&(getSelectedType()!=null&&!getSelectedType().equals(0))))
				{
			
					
	    	try{
	    	DropAddFormDTO dto=getDetailedDTO();
	    	 // TODO add drop phase three 
		    if(getSelectedType()!=null)
		    {
		    	if(getSelectedType().equals(1))
		    	{
		    		dto.setDropType(DropTypesEnum.WP);
		    	}
		    	else if(getSelectedType().equals(2))
		    	{
		    		dto.setDropType(DropTypesEnum.WF);
		    	}
		    }
	    	if(!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
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
	    		   		
	    	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
	    		    		try {
	    		    			if(casesID.equals("AdmissionH"))
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("addDropAdmissionHead.xhtml?id="+dto.getId());
    		    			else if(casesID.equals("ins"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("addDropIns.xhtml?id="+dto.getId());
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
	    		   		
    	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
    	    				
    	    		dto=facade.updateStatusOfForm(dto);
    		    	if(dto!=null)
    		    	{
    		    		//init();
    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
    		    		try {
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("addDropIns.xhtml?id="+dto.getId());
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
	    			newAction.setFormType(FormTypesEnum.DROPADD);
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
	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	 				
	    	 		
	    	 		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("addDropIns.xhtml?id="+dto.getId());
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
 			newAction.setFormType(FormTypesEnum.DROPADD);
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
 			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
				
 	 		
 	 		dto=facade.updateStatusOfForm(dto);
 		    	if(dto!=null)
 		    	{
 		    		//init();
 		    		
 		    		try {
 		 					FacesContext.getCurrentInstance().getExternalContext().redirect
 		 					("addDropIns.xhtml?id="+dto.getId());
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
	    	
	    	//TODO Uncomment it in phase 3
				}
				else 
				{
					
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Please select WF or WP");
					
				}
				
	    	}
				
	 }
	 public void refuseIns()
	 {

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
			/*	//TODO Uncomment if condition and else in phase one /two
				if(getSelectedType()!=null&&!getSelectedType().equals(0))
				{
	    */	try{
	    	DropAddFormDTO dto=getDetailedDTO();
	   	 // TODO add drop phase three 
		    if(getSelectedType()!=null)
		    {
		    	if(getSelectedType().equals(1))
		    	{
		    		dto.setDropType(DropTypesEnum.WP);
		    	}
		    	else if(getSelectedType().equals(2))
		    	{
		    		dto.setDropType(DropTypesEnum.WF);
		    	}
		    }
	    	if(!dto.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
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
	    				  JavaScriptMessagesHandler.RegisterWarningMessage(null, "Already Refused Before !");
	    			}
	    		
	    			//6- b) the petition already refused
	    			else if(dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.Approved)||
	    					dto.getActionDTO().get(index).getActionType().equals(PetitionActionTypeEnum.REVERT)||dto.getReverted()==true)
	    			{
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Refused);
	    		   		
	    	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("addDropIns.xhtml?id="+dto.getId());
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
	    		   		
	    	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	    	    				
	    	    		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("addDropIns.xhtml?id="+dto.getId());
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
	    			newAction.setFormType(FormTypesEnum.DROPADD);
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
	    			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
	 				
	    	 		
	    	 		dto=facade.updateStatusOfForm(dto);
	    		    	if(dto!=null)
	    		    	{
	    		    		//init();
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("addDropIns.xhtml?id="+dto.getId());
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
 			newAction.setFormType(FormTypesEnum.DROPADD);
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
 			dto.setStep(PetitionStepsEnum.INSTRUCTOR);
				
 	 		
 	 		dto=facade.updateStatusOfForm(dto);
 		    	if(dto!=null)
 		    	{
 		    		//init();
 		    		
 		    		try {
 		 					FacesContext.getCurrentInstance().getExternalContext().redirect
 		 					("addDropIns.xhtml?id="+dto.getId());
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
	    	}}
	 }
	
	 public void approveDean()
	 {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	DropAddFormDTO dto=getDetailedDTO();
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
	    		if(dto.getActionDTO().get(i).getActionType()!=null)
	    		{
	    		if(dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED)||
	    				dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED))
	    		//if(dto.getActionDTO().get(i).getInstructorID().equals(getInsDataFacade.getInsByPersonMail(authentication.getName()).getId()))
	    		{
	    			actionExistBefore=true;
	    			index=i;
	    			break;
	    			
	    		}
	    		}
	    		else 
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
	    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
	    		    		try {
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("addDropDean.xhtml?id="+dto.getId());
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
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.DEAN_APPROVED);
	    		   		
    	    			dto.setStep(PetitionStepsEnum.DEAN);
    	    				
    	    		dto=facade.updateStatusOfForm(dto);
    		    	if(dto!=null)
    		    	{
    		    		//init();
    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
    		    		try {
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("addDropDean.xhtml?id="+dto.getId());
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
	    			newAction.setActionType(PetitionActionTypeEnum.DEAN_APPROVED);
	    			newAction.setDate(Calendar.getInstance());
	    			newAction.setFormType(FormTypesEnum.DROPADD);
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
	    		 					("addDropDean.xhtml?id="+dto.getId());
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
			newAction.setFormType(FormTypesEnum.DROPADD);
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
		 					("addDropDean.xhtml?id="+dto.getId());
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
	 public void refuseDean()
	 {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	DropAddFormDTO dto=getDetailedDTO();
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
                if(dto.getActionDTO().get(i).getActionType()!=null)
                {
	    		if(dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED)||
	    				dto.getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED))
	    		//if(dto.getActionDTO().get(i).getInstructorID().equals(getInsDataFacade.getInsByPersonMail(authentication.getName()).getId()))
	    		{
	    			actionExistBefore=true;
	    			index=i;
	    			break;
	    			
	    		}
                }
                else 
                {	actionExistBefore=true;
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
	    		 					("addDropDean.xhtml?id="+dto.getId());
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
	    				dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.DEAN_REFUSED);
	    		   		
    	    			dto.setStep(PetitionStepsEnum.DEAN);
    	    				
    	    		dto=facade.updateStatusOfForm(dto);
    		    	if(dto!=null)
    		    	{
    		    		//init();
    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Refused successfully");
    		    		try {
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("addDropDean.xhtml?id="+dto.getId());
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
	    			newAction.setActionType(PetitionActionTypeEnum.DEAN_REFUSED);
	    			newAction.setDate(Calendar.getInstance());
	    			newAction.setFormType(FormTypesEnum.DROPADD);
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
	    		 					("addDropDean.xhtml?id="+dto.getId());
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
			newAction.setActionType(PetitionActionTypeEnum.DEAN_REFUSED);
			newAction.setDate(Calendar.getInstance());
			newAction.setFormType(FormTypesEnum.DROPADD);
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
		 					("addDropDean.xhtml?id="+dto.getId());
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
	 
	 public void appoveAdmissionHead()
	 {

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
	    	try{
	    	DropAddFormDTO dto=getDetailedDTO();
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
	    		
	    			
	    		if(dto.getActionDTO().get(i).getInstructorID().equals(Constants.ADMISSION_HEAD_ID))
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
	    		 					("addDropAdmissionHead.xhtml?id="+dto.getId()+"&action=approve");
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
    		 					("addDropAdmissionHead.xhtml?id="+dto.getId()+"&action=approve");
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
	    			newAction.setFormType(FormTypesEnum.DROPADD);
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
	    		 					("addDropAdmissionHead.xhtml?id="+dto.getId()+"&action=approve");
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
			newAction.setFormType(FormTypesEnum.DROPADD);
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
		 					("addDropAdmissionHead.xhtml?id="+dto.getId()+"&action=approve");
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
	    	DropAddFormDTO dto=getDetailedDTO();
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
	    		 					("addDropAdmissionHead.xhtml?id="+dto.getId()+"&action=refuse");
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
    		 					("addDropAdmissionHead.xhtml?id="+dto.getId()+"&action=refuse");
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
	    			newAction.setFormType(FormTypesEnum.DROPADD);
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
	    		 					("addDropAdmissionHead.xhtml?id="+dto.getId()+"&action=refuse");
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
			newAction.setFormType(FormTypesEnum.DROPADD);
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
		 					("addDropAdmissionHead.xhtml?id="+dto.getId()+"&action=refuse");
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
	    		
	    		DropAddFormDTO dto=getDetailedDTO();
	    		if(dto.getStep().equals(PetitionStepsEnum.UNDER_PROCESSING))//for petitions in first phase
	    		{
	    			dto.setNotifyAt(null);
	    			dto.setStep(PetitionStepsEnum.ADMISSION_DEPT);
	    	    	dto.setPerformed(true);
	    	    	dto.setStatus(PetitionStepsEnum.ADMISSION_DEPT.getName());
			    	PetitionsActionsDTO newAction=new PetitionsActionsDTO();
					newAction.setActionType(PetitionActionTypeEnum.Mark_As_Done_Approving);
					newAction.setDate(Calendar.getInstance());
					newAction.setFormType(FormTypesEnum.DROPADD);
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
	    	   	 	dto=facade.updateStatusOfForm(dto);
    		    	if(dto!=null)
    		    	{
    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "The request is approved by registrar successfully");
    		    		try {
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("addDropAdmissionDept.xhtml?id="+dto.getId());
    		 					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "The request is approved by registrar successfully");
    		 				} catch (IOException e) {
    		 					// TODO Auto-generated catch block
    		 					e.printStackTrace();
    		 				}
    		    		sharedAcademicPetFacade.notifayNextStepOwner(dto);
    		    		}
	    		}else{
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
    		 					("addDropAdmissionDept.xhtml?id="+dto.getId());
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
				newAction.setFormType(FormTypesEnum.DROPADD);
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
    		 					("addDropAdmissionDept.xhtml?id="+dto.getId());
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
				newAction.setFormType(FormTypesEnum.DROPADD);
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
			
		
				
				}
				
			}
			
			
			if(!approvedByAdmissionDept && !refusedByAdmissionDept) {
				int index =0;
				boolean existed = false;
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
            		dto.getActionDTO().get(index).setActionType(PetitionActionTypeEnum.Approved);
    	    		dto=facade.updateStatusOfForm(dto);
    		    	if(dto!=null)
    		    	{
    		    		//init();
    		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Approved successfully");
    		    		try {
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("addDropAdmissionDept.xhtml?id="+dto.getId());
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
				newAction.setActionType(PetitionActionTypeEnum.Approved);
				newAction.setDate(Calendar.getInstance());
				newAction.setFormType(FormTypesEnum.DROPADD);
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
			
		
				
				}
			}
		    dto.setStep(PetitionStepsEnum.ADMISSION_DEPT);
					
			
			dto=facade.updateStatusOfForm(dto);
			
	    	if(dto!=null)
	    	{
	    	 	init();
	    	 	FacesContext.getCurrentInstance().getExternalContext().redirect
				("addDropAdmissionDept.xhtml?id="+dto.getId()+"&action=approve");
	        		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Done!");
	        		sharedAcademicPetFacade.notifayNextStepOwner(dto);
	    	}
	    	else {
	    		JavaScriptMessagesHandler.RegisterErrorMessage(null, " failed!");
	    	}
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
	
	 public void cancel()
		{
			 switch (casesID) {
	         case "Ins":  //Approve Of Instructor
	        		try {
	        			FacesContext.getCurrentInstance().getExternalContext().redirect
	        			("addDropIns.xhtml?faces-redirect=true");
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	                  break;
	             
	      
	             
	         case "Dean": 
	        		try {
	        			FacesContext.getCurrentInstance().getExternalContext().redirect
	        			("addDropDean.xhtml?faces-redirect=true");
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	                  
	                  
	                  
	         case "AdmissionH":  
	        		try {
	        			FacesContext.getCurrentInstance().getExternalContext().redirect
	        			("addDropAdmissionHead.xhtml?faces-redirect=true");
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	        	 
	        	 
	         case "AdmissionD":  
	        		try {
	        			FacesContext.getCurrentInstance().getExternalContext().redirect
	        			("addDropAdmissionDept.xhtml?faces-redirect=true");
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	     	case "Stduent":  
        		try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
        			("addDropStudent.xhtml?faces-redirect=true");
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
	     	 case "Admin":  
	        		try {
	        			FacesContext.getCurrentInstance().getExternalContext().redirect
	        			("addDropAdmin.xhtml?faces-redirect=true");
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
			    	
			    		DropAddFormDTO dto=getDetailedDTO();
			    		InstructorDTO forwardInsTo=new InstructorDTO();
			    		forwardInsTo.setId(getSelectedInstructor());
			    		dto.setForwardTOIns(forwardInsTo);
			    		InstructorDTO forwardInsFrom=new InstructorDTO();
			    		forwardInsFrom.setId(loggedInIns.getId());
			    		dto.setForwardFromIns(forwardInsFrom);
			    	
			    		if(newComment!=null && !newComment.equals(""))
					     { 
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
					 					("addDropIns.xhtml?forwaredIns="+dto.getForwardTOIns().getId());
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

	 public void downloadAttachments(DropAddFormDTO form)
		{
			AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
		}
	 public void addComment(DropAddFormDTO dto) {
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
								else 	*/
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
							actionDTO.setFormType(FormTypesEnum.DROPADD);
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
					}
					
					else 
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setComment(getNewComment());
						actionDTO.setDate(Calendar.getInstance());
						actionDTO.setFormType(FormTypesEnum.DROPADD);
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
						email, "Drop and Add form - new comment");
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
						
		    	DropAddFormDTO dto=getDetailedDTO();
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
		    		    			HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    		    			StringBuffer url=origRequest.getRequestURL();
		    		        	
			    		    			if(casesID.equals("AdmissionH"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("addDropAdmissionHead.xhtml?id="+dto.getId());
		    		    			else if(casesID.equals("Ins"))
			    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
			    		 					("addDropIns.xhtml?id="+dto.getId());
			    		 			else if(casesID.equals("Dean"))
				    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
				    		 					("addDropDean.xhtml?id="+dto.getId());
			    		 			else if(casesID.equals("AdmissionD"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("addDropAdmissionDept.xhtml?id="+dto.getId());		
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
	    		    			HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		    			StringBuffer url=origRequest.getRequestURL();
	    		    			if(casesID.equals("AdmissionH"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("addDropAdmissionHead.xhtml?id="+dto.getId());
	    		    			else if(casesID.equals("Ins"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("addDropIns.xhtml?id="+dto.getId());
		    		 			else if(casesID.equals("Dean"))
			    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
			    		 					("addDropDean.xhtml?id="+dto.getId());
		    		 			else if(casesID.equals("AdmissionD"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("addDropAdmissionDept.xhtml?id="+dto.getId());		
	    		    		
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
		    			newAction.setFormType(FormTypesEnum.DROPADD);
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
		    		    			HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    		    			StringBuffer url=origRequest.getRequestURL();
		    		    			if(casesID.equals("AdmissionH"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("addDropAdmissionHead.xhtml?id="+dto.getId());
		    		    			else if(casesID.equals("Ins"))
			    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
			    		 					("addDropIns.xhtml?id="+dto.getId());
			    		 			else if(casesID.equals("Dean"))
				    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
				    		 					("addDropDean.xhtml?id="+dto.getId());
			    		 			else if(casesID.equals("AdmissionD"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("addDropAdmissionDept.xhtml?id="+dto.getId());		
		    		    		
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
	 			newAction.setFormType(FormTypesEnum.DROPADD);
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
    		 					("addDropAdmissionHead.xhtml?id="+dto.getId());
    		    			else if(casesID.equals("Ins"))
	    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
	    		 					("addDropIns.xhtml?id="+dto.getId());
	    		 			else if(casesID.equals("Dean"))
		    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
		    		 					("addDropDean.xhtml?id="+dto.getId());
	    		 			else if(casesID.equals("AdmissionD"))
    		 					FacesContext.getCurrentInstance().getExternalContext().redirect
    		 					("addDropAdmissionDept.xhtml?id="+dto.getId());		
    		    		
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
							
						String email="Kindly be informed that, your drop&add petition With id:"+getDetailedDTO().getId()+" has been reverted by "+
					getInsDataFacade.getInsByPersonMail(authentication.getName()).getName()+".kindly check the petition on LTS system.<br/>The comment for the reverting is below: <br/><br/><b>( ";
						List<String> sender=new ArrayList<String>();
						PersonDataDTO stundet=new PersonDataDTO();
						stundet=studentDataFacade.getPersonByPersonMail(getDetailedDTO().getStudent().getMail());
						//sender.add("oalaaeddin@zewailcity.edu.eg");
						sender.add(getDetailedDTO().getStudent().getMail());
						SendMailThread sendMailThread = new SendMailThread(
								sender, stundet.getNameInEng(),
								email+content+" )</b>", "Drop&Add Petition - Revert");
						sendMailThread.start();
						System.out.println("Done sending for:"+getDetailedDTO().getStudent().getMail());
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
			DropAddFormDTO dto=getDetailedDTO();
			if(this.attachmentFile != null)
			{
				AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
				dto.setAttachments(attachment);
			}
			dto=facade.update(dto);
			
			if(dto!=null)
			{
				String email="Kindly be informed that, the reverted Drop Petition With id:"+getDetailedDTO().getId()+" has been updated by the student"+
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
									email+"</b>", "Drop Petition-Revert-ID:"+getDetailedDTO().getId());
							sendMailThread.start();
							FacesContext.getCurrentInstance().getExternalContext().redirect
							("addDropStudent.xhtml?id="+dto.getId());
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
	 
	
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public IAddDropActionsFacade getFacade() {
		return facade;
	}
	public void setFacade(IAddDropActionsFacade facade) {
		this.facade = facade;
	}
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}
	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}
	public DropAddFormDTO getDetailedDTO() {
		return detailedDTO;
	}
	public void setDetailedDTO(DropAddFormDTO detailedDTO) {
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
					//TODO \
					// IT will be the major head instead of dropped course instructor
					if(getDetailedDTO().getDroppedCourseIns()==null)
					{
					if(getDetailedDTO().getActionDTO().get(i).getInstructorID().equals(getDetailedDTO().getMajor().getHeadOfMajor().getId()))
					{
						dto= getDetailedDTO().getActionDTO().get(i);
					}
					}
					else {
						if(getDetailedDTO().getActionDTO().get(i).getInstructorID().equals(getDetailedDTO().getDroppedCourseIns().getId()))
						{
							dto= getDetailedDTO().getActionDTO().get(i);
						}
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
        		
        		
        	 }
        	 break;
            
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
        		 break;
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
        	 
         } 
         
     
         break;
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

	public List<BaseDTO> getDropTypeList() {
		return dropTypeList;
	}

	public void setDropTypeList(List<BaseDTO> dropTypeList) {
		this.dropTypeList = dropTypeList;
	}
	
	public Date getCurrentDate() {
		return new Date();
	}

	public IAdminAddDropFormFacade getAdminFacade() {
		return adminFacade;
	}

	public void setAdminFacade(IAdminAddDropFormFacade adminFacade) {
		this.adminFacade = adminFacade;
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

	public Integer getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(Integer selectedAction) {
		this.selectedAction = selectedAction;
	}

	public boolean isShowAddPnl() {
		return showAddPnl;
	}

	public void setShowAddPnl(boolean showAddPnl) {
		this.showAddPnl = showAddPnl;
	}

	public boolean isShowDropPnl() {
		return showDropPnl;
	}

	public void setShowDropPnl(boolean showDropPnl) {
		this.showDropPnl = showDropPnl;
	}

	public boolean isShowDropAddPnl() {
		return showDropAddPnl;
	}

	public void setShowDropAddPnl(boolean showDropAddPnl) {
		this.showDropAddPnl = showDropAddPnl;
	}

	public List<BaseDTO> getActionsPhase1() {
		return actionsPhase1;
	}

	public void setActionsPhase1(List<BaseDTO> actionsPhase1) {
		this.actionsPhase1 = actionsPhase1;
	}

	public List<InstructorDTO> getInsLst() {
		return insLst;
	}

	public void setInsLst(List<InstructorDTO> insLst) {
		this.insLst = insLst;
	}
	public IStudentAddDropFormFacade getInsFacade() {
		return insFacade;
	}
	public void setInsFacade(IStudentAddDropFormFacade insFacade) {
		this.insFacade = insFacade;
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}


}
