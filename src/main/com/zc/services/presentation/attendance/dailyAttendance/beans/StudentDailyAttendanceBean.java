/**
 * 
 */
package main.com.zc.services.presentation.attendance.dailyAttendance.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IParseTimeFacade;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IStudentDailyAttFacade;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.appService.ILoginSecurityAppService;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;

/**
 * @author omnya
 *
 */
@ViewScoped
@ManagedBean
public class StudentDailyAttendanceBean {

	private List<DailyAttDataDTO> dailyAttList;
	private List<DailyAttDataDTO> selectedDailyAttList;
	private List<DailyAttDataDTO> filteredDailyAttList;
	
	private DailyAttDataDTO changedTime;
	
	private Date searchDate;
	private boolean searchMood;
	private String excuseArea;
	private boolean petitionsNotify;
	@ManagedProperty("#{StudentDailyAttFacadeImpl}")
	private IStudentDailyAttFacade dailyAttFacade;
/*	
	@ManagedProperty("#{loginSecurityAppServiceImpl}")
	private ILoginSecurityAppService loginSecAppService;*/
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	@PostConstruct
	public void init()
	{
		selectedDailyAttList=new ArrayList<DailyAttDataDTO>();
		filteredDailyAttList=new ArrayList<DailyAttDataDTO>();
		fillAttTable();
	}

	public void fillAttTable()
	{
		dailyAttList=new ArrayList<DailyAttDataDTO>();
		//TODO 
		
		//1- get Logged-in student file no
		
		// 2- call facade to get the attendance by file no
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
				try {
	    			List<DailyAttDataDTO> times=dailyAttFacade.getStudentAttByFileNo(studentDataFacade.getPersonByPersonMail(mail).getFileNo());
	 			
	    			if(times!=null)// There is a data
	    			{
	    				dailyAttList=times;
	    			}
	    			else 
	    			{
	    				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting , please try again or contact with system admin");
	    			}
	 			} catch (Exception ex) {
	 				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error");
	 			}
				
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,"Not Allowed To see This Petitions" );
			}
    		//int personId=courseFeedbackFacade.getPersonIDByMail(dao.getMail());
    	
    		 
	
		
    	}
		
		
	}
	
	public void searchByDate()
	{
		searchMood=true;
		if(getSearchDate()!=null)
		{
			Calendar cal=Calendar.getInstance();
			cal.setTime(getSearchDate());
			//TODO 
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(!authentication.getPrincipal().equals("anonymousUser"))
	    	{
	    		String mail=authentication.getName();
	    		//LoginStaffDTO dao = loginSecAppService.getUserByMail(mail);
	    		//int personId=courseFeedbackFacade.getPersonIDByMail(dao.getMail());
	    	
	    		 try {
	    			 List<DailyAttDataDTO> times=new ArrayList<DailyAttDataDTO>();
	    				DailyAttDataDTO dto=dailyAttFacade.getStudentAttByDate(studentDataFacade.getPersonByPersonMail(mail).getFileNo(),cal);
	    				if(dto!=null)
	    				times.add(dto);
	 			
	    			if(times.size()>0)// There is a data
	    			{
	    				dailyAttList=times;
	    			}
	    			else 
	    			{
	    				dailyAttList=null;
	    				JavaScriptMessagesHandler.RegisterErrorMessage(null, "No Attendance For This Date");
	    			}
	 			} catch (Exception ex) {
	 				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error");
	 			}
			
		}
	}}
	
	public void cancelSearchMood()
	{
		searchMood=false;
		setSearchDate(null);
		fillAttTable();
	}
	
	public void prePetition(DailyAttDataDTO dto)
	{
		RequestContext.getCurrentInstance().reset(
				"changeAttFormID:changeAttDlgGrid");
		
		setChangedTime(dto);
		
		FacesContext.getCurrentInstance().getPartialViewContext()
		.getRenderIds().add("changeAttFormID:statusText");
	}
	
	public void sendPetition()
	{
	//TODO 
		//1- get The updated DTO
		DailyAttDataDTO updatedDTO =getChangedTime();
		
		//2- Set the excuse
		
		updatedDTO.setExcuse(getExcuseArea());
		
		//3- Pass the DTO object to facade to update it in DB
		boolean b=dailyAttFacade.petitionRequest(updatedDTO);
		if(b)
		{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Petition Request Has Been Sent Successfully");
		}
		else 
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error , Please Try Again !");
		}
		fillAttTable();	
		
		
	}
	
    public void updatePetitionNotification()
    {
    	List<DailyAttDataDTO> petitionsList=dailyAttFacade.getPetitions(Constants.ATTENDANCE_STATUS_WAITING_RESPONSE);
    	if(petitionsList.size()==0||petitionsList==null)
    	{
    		//TODO 
    		//there is no petitions
    		
    		
    	}
    	
    	else {
    		//TOTO 
    		// There is a petitions
    	}
    }
	public List<DailyAttDataDTO> getDailyAttList() {
		return dailyAttList;
	}

	public void setDailyAttList(List<DailyAttDataDTO> dailyAttList) {
		this.dailyAttList = dailyAttList;
	}

	public List<DailyAttDataDTO> getSelectedDailyAttList() {
		return selectedDailyAttList;
	}

	public void setSelectedDailyAttList(List<DailyAttDataDTO> selectedDailyAttList) {
		this.selectedDailyAttList = selectedDailyAttList;
	}

	public List<DailyAttDataDTO> getFilteredDailyAttList() {
		return filteredDailyAttList;
	}

	public void setFilteredDailyAttList(List<DailyAttDataDTO> filteredDailyAttList) {
		this.filteredDailyAttList = filteredDailyAttList;
	}

	public IStudentDailyAttFacade getDailyAttFacade() {
		return dailyAttFacade;
	}

	public void setDailyAttFacade(IStudentDailyAttFacade dailyAttFacade) {
		this.dailyAttFacade = dailyAttFacade;
	}

	/*public ILoginSecurityAppService getLoginSecAppService() {
		return loginSecAppService;
	}

	public void setLoginSecAppService(ILoginSecurityAppService loginSecAppService) {
		this.loginSecAppService = loginSecAppService;
	}
*/
	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	public boolean isSearchMood() {
		return searchMood;
	}

	public void setSearchMood(boolean searchMood) {
		this.searchMood = searchMood;
	}

	public DailyAttDataDTO getChangedTime() {
		return changedTime;
	}

	public void setChangedTime(DailyAttDataDTO changedTime) {
		this.changedTime = changedTime;
	}

	public String getExcuseArea() {
		return excuseArea;
	}

	public void setExcuseArea(String excuseArea) {
		this.excuseArea = excuseArea;
	}

	public boolean isPetitionsNotify() {
		List<DailyAttDataDTO> petitionsList=dailyAttFacade.getPetitions(Constants.ATTENDANCE_STATUS_WAITING_RESPONSE);
    	if(petitionsList.size()==0||petitionsList==null)
    	{
    		//TODO 
    		//there is no petitions
    		
    		return false;
    	}
    	
    	else {
    		//TOTO 
    		// There is a petitions
    		return true;
    	}
	}

	public void setPetitionsNotify(boolean petitionsNotify) {
		this.petitionsNotify = petitionsNotify;
	}

	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}

	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	
	
}
