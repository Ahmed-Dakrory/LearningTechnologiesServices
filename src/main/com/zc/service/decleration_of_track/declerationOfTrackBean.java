package main.com.zc.service.decleration_of_track;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_students;
import main.com.zc.service.instructorSurveyBean.ReportFileGeneration;
import main.com.zc.service.instructor_survey_ques.instructor_survey_ques;
import main.com.zc.service.student.IStudentGetDataAppService;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.presentation.dto.BaseDTO;

@ManagedBean(name = "declerationOfTrackBean")
@SessionScoped
public class declerationOfTrackBean implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5842440231516516419L;


	/**
	 * 
	 */
	
	HSSFRow row;
	HSSFCell cell;
	
 


	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade facadeSettings;


	@ManagedProperty(value = "#{decleration_of_trackFacadeImpl}")
	private decleration_of_trackAppServiceImpl decleration_of_trackFacade;

	private Student selectedStudent;
	private aa_instructor_students selectedInstructorForThisStudent;

	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	

	@ManagedProperty("#{IStudentGetDataAppService}")
	private IStudentGetDataAppService studentFacade;
	


	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;


	private decleration_of_track decleration_of_trackObject;
	private List<decleration_of_track> decleration_of_trackList;
	private Integer year=0;
	private Integer semester = 0;

	private boolean trackSelected = false;
	private decleration_of_track current_decleration_of_track;
	private Integer selectedTrack = -1;

	private List<BaseDTO> semesterLst;
	private List<String> yearLst;
	
	@PostConstruct
	public void init() {

		
		refresh();
		fillSemesterLst();
		getAllListOfdeclerationOfTrack();
	}
	
	
	public void generateReport(){
		HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet();
	    
	    
	    
	    
		sheet.setColumnWidth(0, 13000);
		sheet.setColumnWidth(1, 13000);
		sheet.setColumnWidth(2, 13000);
		sheet.setColumnWidth(3, 13000);
		
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_CENTER);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 12);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
	    
	    HSSFCellStyle style2 = workbook.createCellStyle();
	    style2.setAlignment(CellStyle.ALIGN_CENTER);
	    HSSFFont font2 = workbook.createFont();
	    font2.setFontHeightInPoints((short) 10);
	    style2.setFont(font2); 
	    
	    
	    row = sheet.createRow(0);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Id");

	    cell = row.createCell(1);
	    row.getCell(1).setCellStyle(style);
	    cell.setCellValue("Name");
	    

	    cell = row.createCell(2);
	    row.getCell(2).setCellStyle(style);
	    cell.setCellValue("Mail");
	    

	    cell = row.createCell(3);
	    row.getCell(3).setCellStyle(style);
	    cell.setCellValue("Track");
	    
	    for(int i=0;i<decleration_of_trackList.size()-3;i++) {
	    	row = sheet.createRow(i+1);
	    	cell = row.createCell(0);
		    row.getCell(0).setCellStyle(style);
		    cell.setCellValue(decleration_of_trackList.get(i).getId());
		    
		    
		    cell = row.createCell(1);
		    row.getCell(1).setCellStyle(style);
		    cell.setCellValue(decleration_of_trackList.get(i).getStudentId().getData().getNameInEnglish());
		    
		    cell = row.createCell(2);
		    row.getCell(2).setCellStyle(style);
		    cell.setCellValue(decleration_of_trackList.get(i).getStudentId().getData().getMail());
		    
		    cell = row.createCell(3);
		    row.getCell(3).setCellStyle(style);
		    if(decleration_of_trackList.get(i).getTrack().intValue() == decleration_of_track.ENGINEERING_TRACK.intValue()) {
		    	cell.setCellValue("Engineering");
		    }else if(decleration_of_trackList.get(i).getTrack().intValue()==decleration_of_track.SCIENCE_TRACK.intValue()) {
		    	cell.setCellValue("Science");
		    }
		    
	    }


	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseContentType("application/vnd.ms-excel");
	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Decleration of track.xls\"");

	    try {
			workbook.write(externalContext.getResponseOutputStream());
			System.out.println("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}
	    facesContext.responseComplete();
	}
	
public void getAllListOfdeclerationOfTrack() {
	decleration_of_trackList = decleration_of_trackFacade.getAllByYearAndSemestar(year, semester);
}
	
	public void fillSemesterLst()
	{
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		//semesterLst.add(new BaseDTO(3,"Winter"));
		
		yearLst=new ArrayList<String>();
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(String.valueOf(i));
		}
	}
	
	public void fillYearLst(AjaxBehaviorEvent event)
	{
		 yearLst=new ArrayList<String>();
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(String.valueOf(i));
		}
	}

	
	public void refresh(){
		trackSelected = false;
		FormsStatusDTO settingform = facadeSettings.getById(26);
		year = settingform.getYear();
		semester = settingform.getSemester().getId();
		
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4))) // student case
			{
//				studentDataFacade.getPersonByPersonMail(mail).
				
				selectedStudent = studentFacade.getStudentByPersonMail(mail);
				List<decleration_of_track> current_decleration_of_trackAll = decleration_of_trackFacade.getAllByYearAndSemestarAndStudent(year, semester,selectedStudent.getId());
				if(current_decleration_of_trackAll!=null) {
					if(current_decleration_of_trackAll.size()>0) {
						current_decleration_of_track = current_decleration_of_trackAll.get(current_decleration_of_trackAll.size()-1);
						trackSelected = true;
					}
				}
				
				
				}
		}
		
	}
	

	
	public void addTrack() {
		
		
		if(isTrackSelected()) {

			current_decleration_of_track.setDate(Calendar.getInstance());
			current_decleration_of_track.setStudentId(selectedStudent);
			current_decleration_of_track.setTrack(selectedTrack);
			current_decleration_of_track.setSemester(semester);
			current_decleration_of_track.setYear(year);
		}else {
			current_decleration_of_track = new decleration_of_track();
			current_decleration_of_track.setDate(Calendar.getInstance());
			current_decleration_of_track.setStudentId(selectedStudent);
			current_decleration_of_track.setTrack(selectedTrack);
			current_decleration_of_track.setSemester(semester);
			current_decleration_of_track.setYear(year);
		}
		decleration_of_trackFacade.adddecleration_of_track(current_decleration_of_track);
		FacesMessage msg = new FacesMessage("Declared", "Your Track has been declared");
		trackSelected = true;
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public aa_instructor_students getSelectedInstructorForThisStudent() {
		return selectedInstructorForThisStudent;
	}



	public void setSelectedInstructorForThisStudent(aa_instructor_students selectedInstructorForThisStudent) {
		this.selectedInstructorForThisStudent = selectedInstructorForThisStudent;
	}





	public IFormsStatusFacade getFacadeSettings() {
		return facadeSettings;
	}



	public void setFacadeSettings(IFormsStatusFacade facadeSettings) {
		this.facadeSettings = facadeSettings;
	}







	public Integer getSelectedTrack() {
		return selectedTrack;
	}

	public void setSelectedTrack(Integer selectedTrack) {
		this.selectedTrack = selectedTrack;
	}

	public decleration_of_trackAppServiceImpl getDecleration_of_trackFacade() {
		return decleration_of_trackFacade;
	}

	public void setDecleration_of_trackFacade(decleration_of_trackAppServiceImpl decleration_of_trackFacade) {
		this.decleration_of_trackFacade = decleration_of_trackFacade;
	}

	public Student getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(Student selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}

	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}

	public IStudentGetDataAppService getStudentFacade() {
		return studentFacade;
	}

	public void setStudentFacade(IStudentGetDataAppService studentFacade) {
		this.studentFacade = studentFacade;
	}

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public boolean isTrackSelected() {
		return trackSelected;
	}

	public void setTrackSelected(boolean trackSelected) {
		this.trackSelected = trackSelected;
	}

	
	public decleration_of_track getCurrent_decleration_of_track() {
		return current_decleration_of_track;
	}

	public void setCurrent_decleration_of_track(decleration_of_track current_decleration_of_track) {
		this.current_decleration_of_track = current_decleration_of_track;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public decleration_of_track getDecleration_of_trackObject() {
		return decleration_of_trackObject;
	}


	public void setDecleration_of_trackObject(decleration_of_track decleration_of_trackObject) {
		this.decleration_of_trackObject = decleration_of_trackObject;
	}


	public List<BaseDTO> getSemesterLst() {
		return semesterLst;
	}


	public void setSemesterLst(List<BaseDTO> semesterLst) {
		this.semesterLst = semesterLst;
	}


	public List<String> getYearLst() {
		return yearLst;
	}


	public void setYearLst(List<String> yearLst) {
		this.yearLst = yearLst;
	}

	public List<decleration_of_track> getDecleration_of_trackList() {
		return decleration_of_trackList;
	}

	public void setDecleration_of_trackList(List<decleration_of_track> decleration_of_trackList) {
		this.decleration_of_trackList = decleration_of_trackList;
	}


}



