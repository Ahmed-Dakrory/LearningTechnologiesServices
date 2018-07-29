/**
 * 
 */
package main.com.zc.services.presentation.attendance.LectureAttenance.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import main.com.zc.services.applicationService.attendance.dailyAttendance.exceptions.ConventionProblem;
import main.com.zc.services.presentation.attendance.LectureAttenance.dto.LectureAttendanceDTO;
import main.com.zc.services.presentation.attendance.LectureAttenance.facade.IParseLectureAttendanceFacade;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IParseTimeFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * @author omnya
 *
 */
@ViewScoped
@ManagedBean(name="LectureAttendanceParser")
public class LectureAttendanceParser {

	@ManagedProperty("#{IParseLectureAttendanceFacade}")
	private IParseLectureAttendanceFacade facade;
	
	
	
	private UploadedFile uploadedFile;
	List<LectureAttendanceDTO> attendanceList=new ArrayList<LectureAttendanceDTO>();
	private boolean renderTable;
	@PostConstruct
	public void init()
	{
		
	}
	public void upload(FileUploadEvent event) {  
	      
	    setUploadedFile(event.getFile());

	    try {
		} catch (Exception e) {
		}
	}  

	public void removeAttachment()
	{
		setUploadedFile(null);
	}

	public String getAttachmentFileName()
	{
		if(uploadedFile == null)
			return "None";
		else
			return uploadedFile.getFileName();
	}

	public void parse()
	{
		try {
			List<LectureAttendanceDTO>  results=facade.parse(getUploadedFile());
			setAttendanceList(results);
			if(results.size()==0)
			{
			   JavaScriptMessagesHandler.RegisterErrorMessage(null, "Invalid file");
    		
			}
			else if(results.size()>0)
			{
				renderTable=true;
				
				   RequestContext.getCurrentInstance().update("form:tbl");
			}
			System.out.println("Size : "+results.size());
		} 
		catch (ConventionProblem e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Invalid file");
			e.toString();
		}
		System.out.println("Parse btn is pressed");
	}
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public IParseLectureAttendanceFacade getFacade() {
		return facade;
	}
	public void setFacade(IParseLectureAttendanceFacade facade) {
		this.facade = facade;
	}
	public List<LectureAttendanceDTO> getAttendanceList() {
		return attendanceList;
	}
	public void setAttendanceList(List<LectureAttendanceDTO> attendanceList) {
		this.attendanceList = attendanceList;
	}
	public boolean isRenderTable() {
		return renderTable;
	}
	public void setRenderTable(boolean renderTable) {
		this.renderTable = renderTable;
	}
	
	
}
