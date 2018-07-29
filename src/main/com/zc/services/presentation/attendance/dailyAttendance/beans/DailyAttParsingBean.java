/**
 * 
 */
package main.com.zc.services.presentation.attendance.dailyAttendance.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import main.com.zc.services.applicationService.attendance.dailyAttendance.exceptions.ConventionProblem;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IDailyAttFacade;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IParseTimeFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane.ScalableIconUIResource;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean
public class DailyAttParsingBean {

	 
	private String statusMessage;
    
    private Part files;
    private String uploadedFileName="";
	private Date attDate;
	private List<DailyAttDataDTO> dailyAttList;
	private List<DailyAttDataDTO> parsedAttList;
	private List<DailyAttDataDTO> selectedDailyAttList;
	private List<DailyAttDataDTO> filteredDailyAttList;
	private List<DailyAttDataDTO> absenceList;
	private List<DailyAttDataDTO> studentEmailList;
	private List<DailyAttDataDTO> selectedStudentEmailList;
    private List<DailyAttDataDTO> dailyAttPetitionLst;
	private File uploadedFileForParsing;
	private String date;
	private UploadedFile file;
	private Integer totalNoOfStudents;
	private Integer totalNoOfAttend;
	private Integer totalNoOfAbsent;
	private boolean loading;
	private boolean saveMood;
	private Date searchDate;
	private Calendar overwrittenDate;
    private String updatedAttendanceStatus;
    private List<String> statusList;
    private String excuseArea;
    private DailyAttDataDTO updatedStudentAttendance;
    private boolean viewBtns;
    private boolean sendEditingEmail;
    private boolean newPetitionAlert;
    private Date attendanceDate;
	@ManagedProperty("#{parseTimeFacadeImpl}")
	private IParseTimeFacade parseDailyAtt;
    
    @ManagedProperty("#{dailyAttFacadeImpl}")
   	private IDailyAttFacade dailyAttFacade; 
    
     
	@PostConstruct
	public void init()
	{
		//dailyAttList=new ArrayList<DailyAttDataDTO>();
		selectedDailyAttList=new ArrayList<DailyAttDataDTO>();
		filteredDailyAttList=new ArrayList<DailyAttDataDTO>();
	    setViewBtns(false);
	    RequestContext.getCurrentInstance().update(":menuForm");
		
	}
	public String parseAttFile()throws IOException
	{
		
		 InputStream inputStream = null;
	        OutputStream outputStream = null;
	        FacesContext context = FacesContext.getCurrentInstance();
	        ServletContext servletContext = (ServletContext) context
	                .getExternalContext().getContext();
	        String path = servletContext.getRealPath("");
	    /*    if (files.getSize()!=0) {
	           */
	        	if(getFile()!=null)
	        	{
	          	           
	            try {
	    			inputStream = file.getInputstream();
	    			
	    		
	    			
	    				
	    			setLoading(true);
	    		
	    			setSaveMood(true);
	    			setViewBtns(true);
	    			
	    			List<DailyAttDataDTO> attList= parseUploadedFile(outputStream,inputStream);
	    			for(int i=0;i<attList.size();i++)
	    			{
	    				
	    				attList.set(i,dailyAttFacade.getStudentData(attList.get(i)));
	    			}
	    	    	List<DailyAttDataDTO> attendedStudents=new ArrayList<DailyAttDataDTO>();
		    		for(int i=0;i<attList.size();i++)
		    			{
		    			if(attList.get(i)!=null)
		    			{
		    				if(attList.get(i).getStudentStatus().equals(Constants.ATTENDANCE_STATUS_ATTENDED)||
		    						attList.get(i).getStudentStatus().equals(Constants.ATTENDANCE_STATUS_SCANNED_ONCE))
		    				{
		    					attendedStudents.add(attList.get(i));
		    				}
		    				/*else 
		    				{
		    					System.out.println(attList.get(i).getPersonId());
		    				}*/
		    			}
		    			}
		    		dailyAttList=attendedStudents;
		    		parsedAttList=attendedStudents;
	    			if(attList.size()>0)
	    			{
	    				String firendlyDate = null;

	    				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	    				if (attList.get(0).getDate() != null) {
	    					firendlyDate = sdf.format(attList.get(0).getDate().getTime());
	    				} 
	    				
	    				
	    				
	    				setDate(firendlyDate);
	    				setSearchDate(attList.get(0).getDate().getTime());
	    			}
	    			
	    			// get the absents 
	    			List<DailyAttDataDTO> temp=new ArrayList<DailyAttDataDTO>();
	    			for(int i=0;i<attList.size();i++)
	    			{
	    			if(attList.get(i)!=null)
	    			{
	    				if(attList.get(i).getStudentStatus().equals(Constants.ATTENDANCE_STATUS_ABSENCE))
	    				{
	    					temp.add(attList.get(i));
	    				}
	    			}
	    			else 
	    			{
	    				System.out.println("Trick");
	    			}
	    			}
	    			absenceList= temp;
	    			
	    			try
	    			{
	    				setTotalNoOfStudents(attList.size());
	    				setTotalNoOfAttend(attendedStudents.size());
	    				setTotalNoOfAbsent(temp.size());
	    				
	    			/*	for(int i=0;i<attList.size();i++)
	    				{
	    					boolean exist=false;
	    					for(int j=0;j<parsedAttList.size();j++)
	    					{
	    						if(parsedAttList.get(j).getPersonId()==attList.get(i).getPersonId())
	    						{
	    							exist=true;
	    							break;
	    						}
	    					}
	    					if(!exist)
	    					{
	    						System.out.println(attList.get(i).getPersonId());
	    					}
	    				}*/
	    			}
	    			catch(Exception ex)
	    			{
	    				
	    			}
	    			
	    		
				
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    			statusMessage = "File upload failed !!";
	    		} finally {
	    			if (outputStream != null) {
	    				outputStream.close();
	    			}
	    			if (inputStream != null) {
	    				inputStream.close();
	    			}
	        }
		

		
		}
		return "";    // return to same page
	}
	
	public List<DailyAttDataDTO> getStudentDetails(
			List<DailyAttDataDTO> studentAtt) {
		for (int i = 0; i < studentAtt.size(); i++) {
			studentAtt.set(i, dailyAttFacade.getStudentData(studentAtt.get(i)));
		}
		return studentAtt;
	}

	public List<DailyAttDataDTO>  parseUploadedFile( OutputStream outPutStream, InputStream input)
	{
		

			
				List<DailyAttDataDTO> times;
				try {
				
					times = parseDailyAtt.newParse(input);
					for (int i = 0; i < times.size(); i++) {
						Calendar cal=Calendar.getInstance();
						cal.setTime(getAttendanceDate());

						times.get(i).setDate(cal);
						
					}
					
					
			
					return times;
					
				}
			
			catch (IOException ex)
				{
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In File");
					return null;
				}
				catch( ConventionProblem ex) {
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "The File in an unconventional way , Please back to template");
					ex.printStackTrace();
					return null;
				}
		
			
			
			
		
	}
	
	private String getFileName(Part part) {
		final String partHeader = part.getHeader("content-disposition");
		System.out.println("***** partHeader: " + partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
	
	private List<DailyAttDataDTO> absentsList(List<DailyAttDataDTO> attendedList)
	{
		List<DailyAttDataDTO> absentsList=new ArrayList<DailyAttDataDTO>();
		for(int i=0;i<attendedList.size();i++)
		{
		if(attendedList.get(i).getStudentStatus().equals(Constants.ATTENDANCE_STATUS_ABSENCE))
		{
			absentsList.add(attendedList.get(i));
		}
		}
		/*// 1- get the file numbers of all students
		
		List<DailyAttDataDTO> allData=new ArrayList<DailyAttDataDTO>();
		allData=dailyAttFacade.getAllStudentData();
		
		//2- Loop on all file numbers and if it's not existed . then add it to absents list
		boolean existed=false;
		for(int i=0;i<allData.size();i++)
		{
			existed=false;
			for(int j=0;j<attendedList.size();j++)
			{
				allData.get(i).setDate(attendedList.get(j).getDate());
				if(allData.get(i).getPersonId()==attendedList.get(j).getPersonId())
				{
					
					existed=true;
					break ;
				}
				
			}
			if(!existed)
			{
				
				absentsList.add(allData.get(i));
			}
		}*/
		
		
		
		return absentsList;
	}
	
	public void saveData()
	{
		 List<DailyAttDataDTO> allStudents=new ArrayList<DailyAttDataDTO>();
		 try
		 {
			 Calendar cal=getParsedAttList().get(0).getDate();
			 allStudents=dailyAttFacade.getTimesListByDate(cal);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
	
	     if(allStudents.size()>0) // existed before
	     {
	    	 
	    	 RequestContext.getCurrentInstance().execute("confirmation.show()");
	    	 setOverwrittenDate(getParsedAttList().get(0).getDate());
	    	 RequestContext.getCurrentInstance().update(":pageForm:saveData");
	 		RequestContext.getCurrentInstance().update(":pageForm:sendEmails");
	    	 
	     }
	     else {
		// Code of saving students to DB
		setSaveMood(false);
		
		// save daily att for students 
		 dailyAttFacade.addStudentData(getParsedAttList());
		 dailyAttFacade.addStudentData(getAbsenceList());
		 JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Saved Successfully");
		RequestContext.getCurrentInstance().update(":pageForm:saveData");
		RequestContext.getCurrentInstance().update(":pageForm:sendEmails");
	     }
	}

	public String navigateToSendEmails()
	{
		selectedStudentEmailList=new ArrayList<DailyAttDataDTO>();
		// Fill the Table of absent and scanned Once Table 
		
		// 1- get the students from database by status and date
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    studentEmailList=new ArrayList<DailyAttDataDTO>();
	    try {
	    	
	    	if(getDate()!=null)
	    	{
			cal.setTime(sdf.parse(getDate()));
			List<DailyAttDataDTO> scannedOnceLst=dailyAttFacade.getTimesListByAttStatusAndDate(Constants.ATTENDANCE_STATUS_SCANNED_ONCE, cal);
			List<DailyAttDataDTO> absentsLst=dailyAttFacade.getTimesListByAttStatusAndDate(Constants.ATTENDANCE_STATUS_ABSENCE, cal);
			for(int i=0;i<scannedOnceLst.size();i++)
			{
				studentEmailList.add(scannedOnceLst.get(i));
			}
			for(int i=0;i<absentsLst.size();i++)
			{
				studentEmailList.add(absentsLst.get(i));
			}
	    	}
	    	 if(getSearchDate()!=null&&studentEmailList.size()==0 )// search case
	    	{
	    		 
	    		cal.setTime(getSearchDate());
	    		List<DailyAttDataDTO> scannedOnceLst=dailyAttFacade.getTimesListByAttStatusAndDate(Constants.ATTENDANCE_STATUS_SCANNED_ONCE, cal);
				List<DailyAttDataDTO> absentsLst=dailyAttFacade.getTimesListByAttStatusAndDate(Constants.ATTENDANCE_STATUS_ABSENCE, cal);
				for(int i=0;i<scannedOnceLst.size();i++)
				{
					studentEmailList.add(scannedOnceLst.get(i));
				}
				for(int i=0;i<absentsLst.size();i++)
				{
					studentEmailList.add(absentsLst.get(i));
				}
	    	}
	    	
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "/pages/secured/att/sendDailyAttEmails.xhtml?faces-redirect=true;";
	}
	public void sendEmailsToAll()
	{
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    try {
	    	if(getDate()!=null)
	    	{
			cal.setTime(sdf.parse(getDate()));
			boolean b=dailyAttFacade.sendMailsOfAttendanceToStudents(cal);
			if(b)
			{
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Emails have been sent successfully");
			}
			else 
			{
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error , Please try again !");
			}
	    	}
	    	else if(getSearchDate()!=null)// search case
			{
	    		cal.setTime(getSearchDate());
				boolean b=dailyAttFacade.sendMailsOfAttendanceToStudents(cal);
				if(b)
				{
					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Emails have been sent successfully");
				}
				
				else 
				{
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error , Please try again !");
				}
			}
	    	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// all done
	}
	
	public void sendEmailsTOScannedOnce()
	{
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    try {
	    	if(getDate()!=null)
	    	{
			cal.setTime(sdf.parse(getDate()));
			boolean b=dailyAttFacade.sendMailsOfAttendanceToSpecialTypeOfStudents(cal,Constants.ATTENDANCE_STATUS_SCANNED_ONCE);
			if(b)
			{
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Emails have been sent to scanned-once students successfully");
			}
			else 
			{
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error , Please try again !");
			}
	    	}
	    	else if(getSearchDate()!=null)// search case
			{
	    		cal.setTime(getSearchDate());
	    		boolean b=dailyAttFacade.sendMailsOfAttendanceToSpecialTypeOfStudents(cal,Constants.ATTENDANCE_STATUS_SCANNED_ONCE);
				if(b)
				{
					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Emails have been sent successfully");
				}
				
				else 
				{
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error , Please try again !");
				}
			}
	    	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendEmailsToAbsents()
	{
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    try {
	    	if(getDate()!=null)
	    	{
			cal.setTime(sdf.parse(getDate()));
			boolean b=dailyAttFacade.sendMailsOfAttendanceToSpecialTypeOfStudents(cal,Constants.ATTENDANCE_STATUS_ABSENCE);
			if(b)
			{
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Emails have been sent to Absents successfully");
			}
			else 
			{
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error , Please try again !");
			}
	    	}
	    	else if(getSearchDate()!=null)// search case
			{
	    		cal.setTime(getSearchDate());
	    		boolean b=dailyAttFacade.sendMailsOfAttendanceToSpecialTypeOfStudents(cal,Constants.ATTENDANCE_STATUS_ABSENCE);
				if(b)
				{
					JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Emails have been sent successfully");
				}
				
				else 
				{
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error , Please try again !");
				}
			}
	    	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void serachByDate()
	{
Calendar cal=Calendar.getInstance();
		
		cal.setTime(getSearchDate());
		
		
		
          List<DailyAttDataDTO> allStudents=new ArrayList<DailyAttDataDTO>();
          allStudents=dailyAttFacade.getTimesListByDate(cal);
          List<DailyAttDataDTO> absentsStudents=new ArrayList<DailyAttDataDTO>();
          List<DailyAttDataDTO> attendedStudents=new ArrayList<DailyAttDataDTO>();
          if(allStudents.size()==0)
          {
        	  JavaScriptMessagesHandler.RegisterNotificationMessage(null, "No Attendance For This Date");
        	  setLoading(false);
        	  setViewBtns(false);
          }
          else 
          {  
         
          for(int i=0;i<allStudents.size();i++)
          {
        	  setLoading(true);
        	  setSaveMood(false);
        	  setViewBtns(true);
        	
        	  if(allStudents.get(i).getStudentStatus().equals(Constants.ATTENDANCE_STATUS_ABSENCE))
        	  {  if(allStudents.get(i).getExcuseStatus()!=null)
        		  {
        			if(allStudents.get(i).getExcuseStatus().equals(Constants.ATTENDANCE_STATUS_APPROVE_EXCUSE))
        			{
        				DailyAttDataDTO tempDTo=allStudents.get(i);
        				tempDTo.setStudentStatus("Was "+allStudents.get(i).getStudentStatus()+" and has been edited");
        				attendedStudents.add(allStudents.get(i));
        			}
        			else 
        			{
        				 absentsStudents.add(allStudents.get(i));
        			}
        			}
        	  else 
        	  {
        		  absentsStudents.add(allStudents.get(i));
        	  }
        	  }
        	  else 
        	  {
        		 
        		  attendedStudents.add(allStudents.get(i));
        	  }
          }
          }
      	try
		{
			setTotalNoOfStudents(allStudents.size());
			setTotalNoOfAttend(attendedStudents.size());
			setTotalNoOfAbsent(absentsStudents.size());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

			if (cal!= null) {
				setDate(sdf.format(cal.getTime()));
			} 
			
		}
		catch(Exception ex)
		{
			
		}
          
          parsedAttList=attendedStudents;
                    absenceList=absentsStudents;
		//3- display it in the datatables 
		System.out.println("Date >>>> "+getSearchDate());
	}
	
	public void preUpdateAttendance(DailyAttDataDTO dto)
	{
		setUpdatedStudentAttendance(null);
		RequestContext.getCurrentInstance().reset(
				"changeAttFormID:changeAttDlgGrid");
		setExcuseArea("");
	
		FacesContext.getCurrentInstance().getPartialViewContext()
		.getRenderIds().add("changeAttFormID:changeAttDlgGrid");
		statusList=new ArrayList<String>();
		statusList.add(Constants.ATTENDANCE_STATUS_ATTENDED);
		statusList.add(Constants.ATTENDANCE_STATUS_ABSENCE);
		setUpdatedAttendanceStatus(dto.getStudentStatus());
		setUpdatedStudentAttendance(dto);
		FacesContext.getCurrentInstance().getPartialViewContext()
		.getRenderIds().add("changeAttFormID:statusID");
		
		
	}

	public void updateStatus()
	{

		if (!getExcuseArea().equals("") && getExcuseArea() != null&&!getExcuseArea().trim().equals(""))
		{
		if(getUpdatedAttendanceStatus().equals(Constants.ATTENDANCE_STATUS_ATTENDED)) // make sure the user isn't considering him as absent
		{
		DailyAttDataDTO dto=new DailyAttDataDTO();
		dto=updatedStudentAttendance;
		//dto.setStudentStatus(getExcuseArea());
		dto.setExcuse(getExcuseArea());
		dto.setExcuseStatus(Constants.ATTENDANCE_STATUS_APPROVE_EXCUSE);
	
		
		boolean isEdited =dailyAttFacade.editDailyAttendance(dto);
		if(isEdited)
		{
			
		
			
			Calendar cal=Calendar.getInstance();
			cal.setTime(getSearchDate());
			boolean b=fillTables(cal);
		
             if(b)
             {
            	 
            	//TODO
             	//3- send email of editing
            	 if(isSendEditingEmail())
            	 {
            		 boolean sent=dailyAttFacade.sendEditAttendanceTimesMailConformation(dto);
            		 // if sending is done 
            		 if(sent)
            		 {
            			 JavaScriptMessagesHandler.RegisterNotificationMessage(null, "The status has been updated and The Email has been sent successfully");
            		 }
            		 // if sending is failed
            		 else 
            		 {
            			 JavaScriptMessagesHandler.RegisterWarningMessage(null, "The status has been updated successfully But the email hasn't been sent , please try again! or contactwith the system admin");
            		 }
            	 }
            	 else 
            	 {
            		 JavaScriptMessagesHandler.RegisterNotificationMessage(null, "The status has been updated successfully");
            	 }
             }
             else
             {
            	 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Can't Load the page correctly , please try again!");
             }
			
			
		}
		else 
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error , Please contact with system admin !");
		}
		
		
		
		
		
		 RequestContext.getCurrentInstance().execute("changeAttDlg.hide()");
		
		}
		else // Ignore it  
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "No changing in his status to be updated");
		}
		}
		else 
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "The Excuse is empty");
		}
	}
	
	public boolean fillTables(Calendar cal)
	{
		try
		{
		List<DailyAttDataDTO> absentsList=new ArrayList<DailyAttDataDTO>();
		List<DailyAttDataDTO> attendedList=new ArrayList<DailyAttDataDTO>();
	    List<DailyAttDataDTO> all=new ArrayList<DailyAttDataDTO>();
		
	    all=dailyAttFacade.getTimesListByDate(cal);
		
		
		
		for(int i=0;i<all.size();i++)
		{
			
			  if(all.get(i).getStudentStatus().equals(Constants.ATTENDANCE_STATUS_ABSENCE))
        	  {  if(all.get(i).getExcuseStatus()!=null)
        		  {
        			if(all.get(i).getExcuseStatus().equals(Constants.ATTENDANCE_STATUS_APPROVE_EXCUSE))
        			{
        				DailyAttDataDTO tempDTo=all.get(i);
        				tempDTo.setStudentStatus("Was "+all.get(i).getStudentStatus()+" and has been edited");
        				attendedList.add(all.get(i));
        			}
        			else 
        			{
        				absentsList.add(all.get(i));
        			}
        			}
        	  else 
        	  {
        		  absentsList.add(all.get(i));
        	  }
        	  }
        	  else 
        	  {
        		 
        		  attendedList.add(all.get(i));
        	  }
			parsedAttList=attendedList;
			absenceList=absentsList;
			setTotalNoOfStudents(all.size());
			setTotalNoOfAttend(parsedAttList.size());
			setTotalNoOfAbsent(absenceList.size());
		
		}
		return true;
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public String overwrite()
	{
		 RequestContext.getCurrentInstance().execute("confirmation.hide()");
		//TODO 
		
		//1-delete from DB by Date 
		boolean deleted=dailyAttFacade.deleteOldAttendance(getOverwrittenDate());
		
		
		//2- add the new times to DB 
		if(deleted)
		{
			//save data 
			setSaveMood(false);
			
			// save daily att for students 
			 dailyAttFacade.addStudentData(getParsedAttList());
			 dailyAttFacade.addStudentData(getAbsenceList());
			 
			RequestContext.getCurrentInstance().update(":pageForm:saveData");
			RequestContext.getCurrentInstance().update(":pageForm:sendEmails");
			
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Saved Successfully");
		}
		else 
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error in overwritten , Please contact the system admin");
		}
		return "/pages/secured/att/dailyAttPage.xhtml";
	}
	public String navigateTODailyPetPage()
	{

		return "/pages/secured/att/showAttendancePetition.xhtml";
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public boolean  fillTablesByCal(Calendar cal)
	{

        List<DailyAttDataDTO> allStudents=new ArrayList<DailyAttDataDTO>();
        allStudents=dailyAttFacade.getTimesListByDate(cal);
        List<DailyAttDataDTO> absentsStudents=new ArrayList<DailyAttDataDTO>();
        List<DailyAttDataDTO> attendedStudents=new ArrayList<DailyAttDataDTO>();
        if(allStudents.size()==0)
        {
        	JavaScriptMessagesHandler.RegisterNotificationMessage(null, "No Attendance For This Date");
      	  setLoading(false);
      	  setViewBtns(false);
        }
        else 
        {  
       
        for(int i=0;i<allStudents.size();i++)
        {
      	  setLoading(true);
      	  setSaveMood(false);
      	  setViewBtns(true);
      	
      	  if(allStudents.get(i).getStudentStatus().equals(Constants.ATTENDANCE_STATUS_ABSENCE))
      	  {  if(allStudents.get(i).getExcuseStatus()!=null)
      		  {
      			if(!allStudents.get(i).getExcuseStatus().equals(Constants.ATTENDANCE_STATUS_APPROVE_EXCUSE))
      			{
      				DailyAttDataDTO tempDTo=allStudents.get(i);
      				tempDTo.setStudentStatus("Was "+allStudents.get(i).getStudentStatus()+" and has been edited");
      				attendedStudents.add(allStudents.get(i));
      			}
      			else 
      			{
      				 absentsStudents.add(allStudents.get(i));
      			}
      			}
      	  else 
      	  {
      		  absentsStudents.add(allStudents.get(i));
      	  }
      	  }
      	  else 
      	  {
      		 
      		  attendedStudents.add(allStudents.get(i));
      	  }
        }
        }
    	try
		{
    		parsedAttList=attendedStudents;
			absenceList=absentsStudents;
			setTotalNoOfStudents(allStudents.size());
			setTotalNoOfAttend(attendedStudents.size());
			setTotalNoOfAbsent(absentsStudents.size());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

			if (cal!= null) {
				setDate(sdf.format(cal.getTime()));
			} 
			return true;
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
        
       
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public Part getFiles() {
		return files;
	}
	public void setFiles(Part files) {
		this.files = files;
	}

	public Date getAttDate() {
		return attDate;
	}
	public void setAttDate(Date attDate) {
		this.attDate = attDate;
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
	public List<DailyAttDataDTO> getParsedAttList() {
		return parsedAttList;
	}
	public void setParsedAttList(List<DailyAttDataDTO> parsedAttList) {
		this.parsedAttList = parsedAttList;
	}
	public List<DailyAttDataDTO> getAbsenceList() {
		return absenceList;
	}
	public void setAbsenceList(List<DailyAttDataDTO> absenceList) {
		this.absenceList = absenceList;
	}
	public IParseTimeFacade getParseDailyAtt() {
		return parseDailyAtt;
	}
	public void setParseDailyAtt(IParseTimeFacade parseDailyAtt) {
		this.parseDailyAtt = parseDailyAtt;
	}
	public IDailyAttFacade getDailyAttFacade() {
		return dailyAttFacade;
	}
	public void setDailyAttFacade(IDailyAttFacade dailyAttFacade) {
		this.dailyAttFacade = dailyAttFacade;
	}
	public String getDate() {
		
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getTotalNoOfStudents() {
		return totalNoOfStudents;
	}
	public void setTotalNoOfStudents(Integer totalNoOfStudents) {
		this.totalNoOfStudents = totalNoOfStudents;
	}
	public Integer getTotalNoOfAttend() {
		return totalNoOfAttend;
	}
	public void setTotalNoOfAttend(Integer totalNoOfAttend) {
		this.totalNoOfAttend = totalNoOfAttend;
	}
	public Integer getTotalNoOfAbsent() {
		return totalNoOfAbsent;
	}
	public void setTotalNoOfAbsent(Integer totalNoOfAbsent) {
		this.totalNoOfAbsent = totalNoOfAbsent;
	}
	public boolean isLoading() {
		return loading;
	}
	public void setLoading(boolean loading) {
		this.loading = loading;
	}
	public boolean isSaveMood() {
		return saveMood;
	}
	public void setSaveMood(boolean saveMood) {
		this.saveMood = saveMood;
	}
	public List<DailyAttDataDTO> getStudentEmailList() {
		return studentEmailList;
	}
	public void setStudentEmailList(List<DailyAttDataDTO> studentEmailList) {
		this.studentEmailList = studentEmailList;
	}
	public List<DailyAttDataDTO> getSelectedStudentEmailList() {
		return selectedStudentEmailList;
	}
	public void setSelectedStudentEmailList(
			List<DailyAttDataDTO> selectedStudentEmailList) {
		this.selectedStudentEmailList = selectedStudentEmailList;
	}
	public Date getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
	public String getUpdatedAttendanceStatus() {
		return updatedAttendanceStatus;
	}
	public void setUpdatedAttendanceStatus(String updatedAttendanceStatus) {
		this.updatedAttendanceStatus = updatedAttendanceStatus;
	}
	public List<String> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	public String getExcuseArea() {
		return excuseArea;
	}
	public void setExcuseArea(String excuseArea) {
		this.excuseArea = excuseArea;
	}
	public DailyAttDataDTO getUpdatedStudentAttendance() {
		return updatedStudentAttendance;
	}
	public void setUpdatedStudentAttendance(DailyAttDataDTO updatedStudentAttendance) {
		this.updatedStudentAttendance = updatedStudentAttendance;
	}
	public boolean isViewBtns() {
		return viewBtns;
	}
	public void setViewBtns(boolean viewBtns) {
		this.viewBtns = viewBtns;
	}
	public Calendar getOverwrittenDate() {
		return overwrittenDate;
	}
	public void setOverwrittenDate(Calendar overwrittenDate) {
		this.overwrittenDate = overwrittenDate;
	}
	public boolean isSendEditingEmail() {
		return sendEditingEmail;
	}
	public void setSendEditingEmail(boolean sendEditingEmail) {
		this.sendEditingEmail = sendEditingEmail;
	}
	public boolean isNewPetitionAlert() {
		//TODO 
		// get the all petitions with status waiting the response 
		List<DailyAttDataDTO> list=dailyAttFacade.getStudentsByExcuseStatus(Constants.ATTENDANCE_STATUS_WAITING_RESPONSE);
		if(list!=null)
		{
			//if the size greater than 0 then set true
			if(list.size()>0)
				return true;
				 
			//else set false
			else 
				 
				return false;
		}
		else return false;

		
		
		
	}
	public void upload(FileUploadEvent event) {  
	    // Do what you want with the file      
	    setFile(event.getFile());

	    try {
		} catch (Exception e) {
		}
	}  
	public void setNewPetitionAlert(boolean newPetitionAlert) {
		this.newPetitionAlert = newPetitionAlert;
	}
	public List<DailyAttDataDTO> getDailyAttPetitionLst() {
		return dailyAttPetitionLst;
	}
	public void setDailyAttPetitionLst(List<DailyAttDataDTO> dailyAttPetitionLst) {
		this.dailyAttPetitionLst = dailyAttPetitionLst;
	}
	public Date getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	
}
