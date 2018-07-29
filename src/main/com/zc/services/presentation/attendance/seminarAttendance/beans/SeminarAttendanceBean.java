/**
 * 
 */
package main.com.zc.services.presentation.attendance.seminarAttendance.beans;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import main.com.zc.services.applicationService.attendance.seminarAttendance.service.ISendSeminarAttendanceMailsAPPService;
import main.com.zc.services.presentation.attendance.seminarAttendance.facade.ISeminarAttendanceFacade;

/**
 * @author Omnya Alaa
 *
 */
@ManagedBean
@SessionScoped
public class SeminarAttendanceBean {

	private String firstName;
	
	
	@ManagedProperty("#{seminarAttendanceFacadeImpl}")
	private ISeminarAttendanceFacade seminarAttFacade;
	
	@ManagedProperty("#{sendSeminarAttendanceMailsAPPServiceImpl}")
	private ISendSeminarAttendanceMailsAPPService seminarAttAppSerSendMails;
	
	@PostConstruct
	public void init()
	{
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2014,2,18, 0, 0, 0);
		//seminarAttFacade.addDataToDB("resources/Seminar ZC2_127&114 18-03-2014.xlsx", cal);
	//	seminarAttAppSerSendMails.sendEmails(cal);
	}

	public ISeminarAttendanceFacade getSeminarAttFacade() {
		return seminarAttFacade;
	}

	public void setSeminarAttFacade(ISeminarAttendanceFacade seminarAttFacade) {
		this.seminarAttFacade = seminarAttFacade;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public ISendSeminarAttendanceMailsAPPService getSeminarAttAppSerSendMails() {
		return seminarAttAppSerSendMails;
	}

	public void setSeminarAttAppSerSendMails(
			ISendSeminarAttendanceMailsAPPService seminarAttAppSerSendMails) {
		this.seminarAttAppSerSendMails = seminarAttAppSerSendMails;
	}
	
	
	
	
	
}
