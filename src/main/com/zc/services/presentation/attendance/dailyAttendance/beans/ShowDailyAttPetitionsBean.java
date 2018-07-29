/**
 * 
 */
package main.com.zc.services.presentation.attendance.dailyAttendance.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IDailyAttFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class ShowDailyAttPetitionsBean {

    @ManagedProperty("#{dailyAttFacadeImpl}")
   	private IDailyAttFacade dailyAttFacade; 
    
    
    private List<DailyAttDataDTO> dailyAttPetitionLst;
    private List<DailyAttDataDTO> selectedDailyAttPetitionLst;
    private List<DailyAttDataDTO> filteredDailyAttPetitionLst;
    private DailyAttDataDTO detailedStudent;
    private boolean showDetailsMood;
    private String emailArea;
	@PostConstruct
	public void init()
	{
		dailyAttPetitionLst=new ArrayList<DailyAttDataDTO>();
		 fillPetitionsTable();
	}

	public void fillPetitionsTable()
	{
     List<DailyAttDataDTO> tempLst=dailyAttFacade.getStudentsByExcuseStatus(Constants.ATTENDANCE_STATUS_WAITING_RESPONSE);
     if(tempLst!=null)
     {
    	 if(tempLst.size()>=0)
    	 {
    		 dailyAttPetitionLst=tempLst;
    	 }
    	 
     }
	}
	public void clearGrid()
	{
		
		
	}
	public String refuseExuse()
	{
		boolean b=dailyAttFacade.refuseEditAttendance(getDetailedStudent());
		if(b)
			{
			boolean isSent=dailyAttFacade.sendRefuseEditMail(getDetailedStudent());
			if(isSent)
			{
				fillPetitionsTable();
				setShowDetailsMood(false);
				//setDetailedStudent(null);
				RequestContext.getCurrentInstance().update(":mainForm:form:fields:");
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "The Excuse has been refuse successfully and the e-mail has been sent ");
				 RequestContext.getCurrentInstance().execute("refuserConfirmDlg.hide()");
				 return "/pages/secured/att/showAttendancePetition.xhtml";
			}
			else
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "The Excuse has been refused successfully But couldn't sent email now , please try again ");
			return "/pages/secured/att/showAttendancePetition.xhtml";
			
			
			}
		else 
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error , Please contact with system admin");
			return "";
		}
		
	}
	
	public void approveExuse()
	{
		boolean b=dailyAttFacade.approveEditAttendance(getDetailedStudent());
		if(b)
			{
			boolean isSent=dailyAttFacade.sendApproveEditMail(getDetailedStudent());
			if(isSent)
			{
				fillPetitionsTable();
				setShowDetailsMood(false);
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "The Excuse has been approved successfully and the e-mail has been sent ");
			}
			else
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "The Excuse has been approved successfully But couldn't sent email now , please try again ");
			}
		else 
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error , Please contact with system admin");
		}
	}
	
	public void sendEmail()
	{
		boolean isSent=dailyAttFacade.sendCustomEditMail(getDetailedStudent(),getEmailArea());
		if(isSent)
		{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "The Message has been sent successfully");
		}
	}
	public void showStudentPetDetails(DailyAttDataDTO dto)
	{
		showDetailsMood=true;
		detailedStudent=dto;
	}
	public IDailyAttFacade getDailyAttFacade() {
		return dailyAttFacade;
	}

	public void setDailyAttFacade(IDailyAttFacade dailyAttFacade) {
		this.dailyAttFacade = dailyAttFacade;
	}

	public List<DailyAttDataDTO> getDailyAttPetitionLst() {
		return dailyAttPetitionLst;
	}

	public void setDailyAttPetitionLst(List<DailyAttDataDTO> dailyAttPetitionLst) {
		this.dailyAttPetitionLst = dailyAttPetitionLst;
	}

	public List<DailyAttDataDTO> getSelectedDailyAttPetitionLst() {
		return selectedDailyAttPetitionLst;
	}

	public void setSelectedDailyAttPetitionLst(
			List<DailyAttDataDTO> selectedDailyAttPetitionLst) {
		this.selectedDailyAttPetitionLst = selectedDailyAttPetitionLst;
	}

	public List<DailyAttDataDTO> getFilteredDailyAttPetitionLst() {
		return filteredDailyAttPetitionLst;
	}

	public void setFilteredDailyAttPetitionLst(
			List<DailyAttDataDTO> filteredDailyAttPetitionLst) {
		this.filteredDailyAttPetitionLst = filteredDailyAttPetitionLst;
	}

	public boolean isShowDetailsMood() {
		return showDetailsMood;
	}

	public void setShowDetailsMood(boolean showDetailsMood) {
		this.showDetailsMood = showDetailsMood;
	}

	public DailyAttDataDTO getDetailedStudent() {
		return detailedStudent;
	}

	public void setDetailedStudent(DailyAttDataDTO detailedStudent) {
		this.detailedStudent = detailedStudent;
	}

	public String getEmailArea() {
		return emailArea;
	}

	public void setEmailArea(String emailArea) {
		this.emailArea = emailArea;
	}
	
}
