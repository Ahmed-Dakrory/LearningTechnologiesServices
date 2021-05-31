package main.com.zc.service.academic_advisingRegistrarBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_students;
import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_studentsAppServiceImpl;
import main.com.zc.service.academic_advisingInstructorsDates.aa_instructor_date;
import main.com.zc.service.academic_advisingInstructorsDates.aa_instructor_dateAppServiceImpl;
import main.com.zc.service.academic_advising_student_profile.aa_student_profile;
import main.com.zc.service.academic_advising_student_profile.aa_student_profileAppServiceImpl;
import main.com.zc.service.filesOfLibraries.filesOfLibraries;
import main.com.zc.service.filesOfLibraries.filesOfLibrariesAppServiceImpl;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.petition.model.Attachments;
import main.com.zc.services.presentation.accountSetting.facade.impl.StudentProfileFacadeImpl;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.services.presentation.users.facade.impl.StudentFacadeImpl;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

@ManagedBean(name = "aaRegistrarBean")
@SessionScoped
public class aaRegistrarBean implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5842440236769354419L;


	/**
	 * 
	 */
	
	
 


	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade facadeSettings;


	@ManagedProperty(value = "#{aa_student_profileFacadeImpl}")
	private aa_student_profileAppServiceImpl aa_student_profileFacade;


	@ManagedProperty(value = "#{aa_instructor_studentsFacadeImpl}")
	private aa_instructor_studentsAppServiceImpl instructor_studentsFacade;
	

    @ManagedProperty("#{IStudentProfileFacade}")
	private StudentProfileFacadeImpl facadeStudentProfile;
	
    
    @ManagedProperty("#{IStudentFacade}")
    private StudentFacadeImpl studentFacadeImpl;
    
    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
    private IGetLoggedInStudentDataFacade studentDataFacade;
    
	private aa_student_profile selectedStudent;
	
	private List<aa_instructor_students> allStudentSelected;
	

	@ManagedProperty(value = "#{aa_instructor_dateFacadeImpl}")
	private aa_instructor_dateAppServiceImpl aa_instructor_dateFacade;
	

	@ManagedProperty(value = "#{filesOfLibrariesFacadeImpl}")
	private filesOfLibrariesAppServiceImpl attachmentsFacade;
	
	private String selectedYear;
	private String selectedSemester;
	private String selectedAction;
	

	private List<aa_instructor_date> allinstructorDates;
	private List<BaseDTO> semesterLst;
	private List<String> yearLst;
	

	private aa_instructor_date selectedDateData;
	private aa_instructor_students selectedInstructorForThisStudent;
	

	private boolean meetingSelected=false;
	private UploadedFile attachmentFile;

	private List<StudentProfileDTO> studentProfiles;

	private List<filesOfLibraries> allFiles;

	HSSFRow row;
	HSSFCell cell;
	
	@PostConstruct
	public void init() {
		allinstructorDates=new ArrayList<aa_instructor_date>();
		allStudentSelected =new ArrayList<aa_instructor_students>();
		allFiles = new ArrayList<filesOfLibraries>();
		refresh();
		
		
	}
	
	
	
	public UploadedFile getAttachmentFile() {
	    return attachmentFile;
	}

	public void setAttachmentFile(UploadedFile file) {
	    this.attachmentFile = file;
	}
	 
	
	public void handleFileUpload(FileUploadEvent event) {  
	    // Do what you want with the file   
		
        
	    setAttachmentFile(event.getFile());
//	    if(!selectedSemester.equalsIgnoreCase("") && !selectedYear.equalsIgnoreCase("") ) {
	    if(this.attachmentFile != null)
		{
			AttachmentDTO attachmentDto = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
			AttachmentsAssembler assemb = new AttachmentsAssembler();
			Attachments attachment = assemb.toEntity(attachmentDto);
			filesOfLibraries file =new filesOfLibraries();
			file.setAttachment(attachment);

			file.setSemester(selectedSemester);
			file.setYear(selectedYear);
			attachmentsFacade.addfilesOfLibraries(file);
			allFiles = attachmentsFacade.getAll();

			FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}else {
			FacesMessage msg = new FacesMessage("Error", event.getFile().getFileName() + " not uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
//	    }else {
//	    	FacesMessage msg = new FacesMessage("Error", "Please select Year and semester");
//	        FacesContext.getCurrentInstance().addMessage(null, msg);
//	    }
	    
	}  

	
	public void deleteFile(int id) {
		try {
			attachmentsFacade.delete(attachmentsFacade.getById(id));

			allFiles = attachmentsFacade.getAll();
			
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("pageForm:dataTable");
			FacesMessage msg = new FacesMessage("Successful", "File Has Been Deleted");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			FacesMessage msg = new FacesMessage("Error", e.toString());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public void downloadAttachments2(Attachments form)
	{
		AttachmentsAssembler assemb = new AttachmentsAssembler();
		AttachmentDTO attachmentDto = assemb.toDTO(form);
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(attachmentDto);
	}
	
	

	public void generateReport(){
		HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet();
	    
	    
	    
	    
		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 8000);
		sheet.setColumnWidth(2, 8000);
		sheet.setColumnWidth(3, 8000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 8000);
		sheet.setColumnWidth(6, 8000);
		sheet.setColumnWidth(7, 8000);
		sheet.setColumnWidth(8, 8000);
		
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
	    cell.setCellValue("Zewail city Id");

	    cell = row.createCell(1);
	    row.getCell(1).setCellStyle(style);
	    cell.setCellValue("Student Name");
	    

	    cell = row.createCell(2);
	    row.getCell(2).setCellStyle(style);
	    cell.setCellValue("Student Mail");
	    

	    cell = row.createCell(3);
	    row.getCell(3).setCellStyle(style);
	    cell.setCellValue("Major");
	    

	    cell = row.createCell(4);
	    row.getCell(4).setCellStyle(style);
	    cell.setCellValue("minor");
	    

	    cell = row.createCell(5);
	    row.getCell(5).setCellStyle(style);
	    cell.setCellValue("gpa");
	    

	    cell = row.createCell(6);
	    row.getCell(6).setCellStyle(style);
	    cell.setCellValue("Concentration");
	    

	    cell = row.createCell(7);
	    row.getCell(7).setCellStyle(style);
	    cell.setCellValue("Instructor Name");
	    
	    

	    cell = row.createCell(8);
	    row.getCell(8).setCellStyle(style);
	    cell.setCellValue("Instructor Mail");
	    
	    for(int i=0;i<allStudentSelected.size();i++) {
	    	row = sheet.createRow(i+1);
	    	
	    	try {
	    	cell = row.createCell(0);
		    row.getCell(0).setCellStyle(style);
		    cell.setCellValue(allStudentSelected.get(i).getStudent().getZewailcity_id());
		    
		    
		    cell = row.createCell(1);
		    row.getCell(1).setCellStyle(style);
		    cell.setCellValue(allStudentSelected.get(i).getStudent().getName());
		    
		    cell = row.createCell(2);
		    row.getCell(2).setCellStyle(style);
		    cell.setCellValue(allStudentSelected.get(i).getStudent().getMail());

		    cell = row.createCell(3);
		    row.getCell(3).setCellStyle(style);
		    cell.setCellValue(allStudentSelected.get(i).getStudent().getMajor());
		    

		    cell = row.createCell(4);
		    row.getCell(4).setCellStyle(style);
		    cell.setCellValue(allStudentSelected.get(i).getStudent().getMinor());
		    

		    cell = row.createCell(5);
		    row.getCell(5).setCellStyle(style);
		    cell.setCellValue(allStudentSelected.get(i).getStudent().getGpa());
		    

		    cell = row.createCell(6);
		    row.getCell(6).setCellStyle(style);
		    cell.setCellValue(allStudentSelected.get(i).getStudent().getConcentration());
		    
		    cell = row.createCell(7);
		    row.getCell(7).setCellStyle(style);
		    cell.setCellValue(allStudentSelected.get(i).getInstructor().getName());
		    
		    cell = row.createCell(8);
		    row.getCell(8).setCellStyle(style);
		    cell.setCellValue(allStudentSelected.get(i).getInstructor().getMail());
		    
	    	}catch(Error e) {
	    		
	    	}catch(NullPointerException exc) {
	    		
	    	}
	    }


	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseContentType("application/vnd.ms-excel");
	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"All student assigned to me.xls\"");

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
	
	public void getStudentList() {
		 FacesContext context = FacesContext.getCurrentInstance();
		 Map<String, String> map = context.getExternalContext().getRequestParameterMap();
		 String mail = (String) map.get("mail");
		 try {
		PersonDataDTO studentData = studentDataFacade.getPersonByPersonMail(mail);
	    //studentFacadeImpl.get
	    studentProfiles = facadeStudentProfile.getByStudentID(studentData.getId());
	    studentProfiles.remove(studentProfiles.size()-1);
	    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("pageForm:PanelScript");
		FacesMessage msg = new FacesMessage("Successful", "File Has Been Deleted");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        
        RequestContext.getCurrentInstance().execute("myFunction();");
	}catch(NullPointerException e) {
		 JavaScriptMessagesHandler.RegisterErrorMessage(null,
					"There is no GPA Data Available");
	 }
	}
	
	public void generateReportForstudent(String mail){
		try {
		HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet();
	    
	    
	    
	    
		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 8000);
		sheet.setColumnWidth(2, 8000);
		sheet.setColumnWidth(3, 8000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 8000);
		sheet.setColumnWidth(6, 8000);
		sheet.setColumnWidth(7, 8000);
		sheet.setColumnWidth(8, 8000);
		
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
	    cell.setCellValue("Student Id");

	    cell = row.createCell(1);
	    row.getCell(1).setCellStyle(style);
	    cell.setCellValue("Student Name");
	    

	    cell = row.createCell(2);
	    row.getCell(2).setCellStyle(style);
	    cell.setCellValue("Student EMail");
	    

	    cell = row.createCell(3);
	    row.getCell(3).setCellStyle(style);
	    cell.setCellValue("Major");
	    

	    cell = row.createCell(6);
	    row.getCell(6).setCellStyle(style);
	    cell.setCellValue("Concentration");

	    cell = row.createCell(4);
	    row.getCell(4).setCellStyle(style);
	    cell.setCellValue("minor");
	    

	   

	    

	    
	    
	    

	    cell = row.createCell(8);
	    row.getCell(8).setCellStyle(style);
	    cell.setCellValue("Semester");
	    
	    
	    
	    cell = row.createCell(7);
	    row.getCell(7).setCellStyle(style);
	    cell.setCellValue("Year");
	    
	    
	    cell = row.createCell(5);
	    row.getCell(5).setCellStyle(style);
	    cell.setCellValue("gpa");
	    
	    
	    PersonDataDTO studentData = studentDataFacade.getPersonByPersonMail(mail);
	    //studentFacadeImpl.get
	    studentProfiles = facadeStudentProfile.getByStudentID(studentData.getId());
	    studentProfiles.remove(studentProfiles.size()-1);
	    System.out.println(studentData.getId());

	    for(int i=0;i<studentProfiles.size();i++) {
	    	row = sheet.createRow(i+1);
	    	
	    	try {
	    	cell = row.createCell(0);
		    row.getCell(0).setCellStyle(style);
		    cell.setCellValue(studentProfiles.get(i).getStudent().getFacultyId());
		    

		    
	    	}catch(Error e) {
	    		
	    	}catch(NullPointerException exc) {
	    		
	    	}
	    	
	    	try {
		    cell = row.createCell(1);
		    row.getCell(1).setCellStyle(style);
		    cell.setCellValue(studentProfiles.get(i).getStudent().getName());

		    
		    
	    	}catch(Error e) {
	    		
	    	}catch(NullPointerException exc) {
	    		
	    	}
	    	
	    	
	    	try {
		    cell = row.createCell(2);
		    row.getCell(2).setCellStyle(style);
		    cell.setCellValue(studentProfiles.get(i).getStudent().getMail());

		    
		    
	    	}catch(Error e) {
	    		
	    	}catch(NullPointerException exc) {
	    		
	    	}
	    	
	    	

	    	try {
		    cell = row.createCell(3);
		    row.getCell(3).setCellStyle(style);
		    cell.setCellValue(studentProfiles.get(i).getMajor().getMajorName());

		    
		    
	    	}catch(Error e) {
	    		
	    	}catch(NullPointerException exc) {
	    		
	    	}

	    	
	    	
	    	try {
			    cell = row.createCell(4);
			    row.getCell(4).setCellStyle(style);
			    cell.setCellValue(studentProfiles.get(i).getConcentration().getName());
			    
			    
			    
		    	}catch(Error e) {
		    		
		    	}catch(NullPointerException exc) {
		    		
		    	}

	    	
	    	
	    	try {
		    cell = row.createCell(5);
		    row.getCell(5).setCellStyle(style);
		    cell.setCellValue(studentProfiles.get(i).getMinor());

		    
		    
	    	}catch(Error e) {
	    		
	    	}catch(NullPointerException exc) {
	    		
	    	}

	    	

	    	
	    	

	    	
	    	

	    	
	    	
	    	
	    	try {
		    cell = row.createCell(6);
		    row.getCell(6).setCellStyle(style);
		    cell.setCellValue(studentProfiles.get(i).getSemester().getName());

		    
	    	}catch(Error e) {
	    		
	    	}catch(NullPointerException exc) {
	    		
	    	}
	    	
	    	
	    	
	    	try {
			    cell = row.createCell(7);
			    row.getCell(7).setCellStyle(style);
			    cell.setCellValue(String.valueOf(studentProfiles.get(i).getYear()));
			    
		    	}catch(Error e) {
		    		
		    	}catch(NullPointerException exc) {
		    		
		    	}

		    	
		    	
		    	try {
		    		
				    cell = row.createCell(8);
				    row.getCell(8).setCellStyle(style);
				    cell.setCellValue(studentProfiles.get(i).getGpa());

				    
				    
			    	}catch(Error e) {
			    		
			    	}catch(NullPointerException exc) {
			    		
			    	}

	    }


	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseContentType("application/vnd.ms-excel");
	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"GPA history.xls\"");

	    try {
			workbook.write(externalContext.getResponseOutputStream());
			System.out.println("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}
	    facesContext.responseComplete();
		}catch(NullPointerException e) {
			 JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"There is no GPA Data Available");
		 }
	}
	
	
	
	public void goToStudentDates(int idOfDate) {
		meetingSelected = false;
		FormsStatusDTO settingform = facadeSettings.getById(23);
		
		selectedStudent = aa_student_profileFacade.getById(idOfDate);

		
		selectedInstructorForThisStudent = instructor_studentsFacade.getByStudentId(selectedStudent.getId());
		
			/**
			 * THIS CASE WHEN STUDENT NOT RESERVE A SLOT
			 * 
			 * Get all Available Dates for the student
			 */
			
			// This is the last dates reserved
			allinstructorDates = aa_instructor_dateFacade.getByStudentId(selectedInstructorForThisStudent.getStudent().getId());
			
			
		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect(ec.getRequestContextPath()
		            + "/pages/secured/academic_advising/studentDates.xhtml");
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}
	
	public void goToStudentProfileMeeting(int idOfDate) {
		selectedDateData = aa_instructor_dateFacade.getById(idOfDate);
		selectedStudent = aa_student_profileFacade.getById(selectedDateData.getStudent().getId());

		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect(ec.getRequestContextPath()
		            + "/pages/secured/academic_advising/studentProfile_Reg.xhtml");
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}
	
	public void getAllListOfDates() {
		
		if(selectedAction.equalsIgnoreCase("All")) {
			allinstructorDates = aa_instructor_dateFacade.getAll();
		}else {
			allinstructorDates = aa_instructor_dateFacade.getByAction(selectedAction);
		}
		
	}
	
	public void getAllListOfStudents() {
		allStudentSelected = instructor_studentsFacade.getAll();
	}
	
	public void getAllListOfFiles() {
		allFiles = attachmentsFacade.getAll();
	}
	
	public void onRowSelect(SelectEvent event) {
		selectedStudent= (aa_student_profile) event.getObject();

		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect(ec.getRequestContextPath()
		            + "/pages/secured/academic_advising/studentRegistrar.xhtml");
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
    }
	
	
	public void onRowEdit(RowEditEvent event) {
		
		aa_instructor_students profileStudent = (aa_instructor_students) event.getObject();
		aa_student_profileFacade.addaa_student_profile(profileStudent.getStudent());
        FacesMessage msg = new FacesMessage("Student with Id Edited", String.valueOf(profileStudent.getStudent().getZewailcity_id()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
	
	
	public void refresh(){

		fillSemesterLst();
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

	public List<aa_instructor_students> getAllStudentSelected() {
		return allStudentSelected;
	}

	public void setAllStudentSelected(List<aa_instructor_students> allStudentSelected) {
		this.allStudentSelected = allStudentSelected;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public String getSelectedSemester() {
		return selectedSemester;
	}

	public void setSelectedSemester(String selectedSemester) {
		this.selectedSemester = selectedSemester;
	}

	public IFormsStatusFacade getFacadeSettings() {
		return facadeSettings;
	}



	public void setFacadeSettings(IFormsStatusFacade facadeSettings) {
		this.facadeSettings = facadeSettings;
	}



	public aa_student_profileAppServiceImpl getAa_student_profileFacade() {
		return aa_student_profileFacade;
	}



	public void setAa_student_profileFacade(aa_student_profileAppServiceImpl aa_student_profileFacade) {
		this.aa_student_profileFacade = aa_student_profileFacade;
	}



	public aa_student_profile getSelectedStudent() {
		return selectedStudent;
	}



	public void setSelectedStudent(aa_student_profile selectedStudent) {
		this.selectedStudent = selectedStudent;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public aa_instructor_dateAppServiceImpl getAa_instructor_dateFacade() {
		return aa_instructor_dateFacade;
	}


	public void setAa_instructor_dateFacade(aa_instructor_dateAppServiceImpl aa_instructor_dateFacade) {
		this.aa_instructor_dateFacade = aa_instructor_dateFacade;
	}


	public String getSelectedAction() {
		return selectedAction;
	}


	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}


	public List<aa_instructor_date> getAllinstructorDates() {
		return allinstructorDates;
	}


	public void setAllinstructorDates(List<aa_instructor_date> allinstructorDates) {
		this.allinstructorDates = allinstructorDates;
	}

	public aa_instructor_date getSelectedDateData() {
		return selectedDateData;
	}

	public void setSelectedDateData(aa_instructor_date selectedDateData) {
		this.selectedDateData = selectedDateData;
	}

	public aa_instructor_studentsAppServiceImpl getInstructor_studentsFacade() {
		return instructor_studentsFacade;
	}

	public void setInstructor_studentsFacade(aa_instructor_studentsAppServiceImpl instructor_studentsFacade) {
		this.instructor_studentsFacade = instructor_studentsFacade;
	}

	public aa_instructor_students getSelectedInstructorForThisStudent() {
		return selectedInstructorForThisStudent;
	}

	public void setSelectedInstructorForThisStudent(aa_instructor_students selectedInstructorForThisStudent) {
		this.selectedInstructorForThisStudent = selectedInstructorForThisStudent;
	}

	public boolean isMeetingSelected() {
		return meetingSelected;
	}

	public void setMeetingSelected(boolean meetingSelected) {
		this.meetingSelected = meetingSelected;
	}



	public filesOfLibrariesAppServiceImpl getAttachmentsFacade() {
		return attachmentsFacade;
	}



	public void setAttachmentsFacade(filesOfLibrariesAppServiceImpl attachmentsFacade) {
		this.attachmentsFacade = attachmentsFacade;
	}



	public List<filesOfLibraries> getAllFiles() {
		return allFiles;
	}



	public void setAllFiles(List<filesOfLibraries> allFiles) {
		this.allFiles = allFiles;
	}



	public StudentProfileFacadeImpl getFacadeStudentProfile() {
		return facadeStudentProfile;
	}



	public void setFacadeStudentProfile(StudentProfileFacadeImpl facadeStudentProfile) {
		this.facadeStudentProfile = facadeStudentProfile;
	}



	public StudentFacadeImpl getStudentFacadeImpl() {
		return studentFacadeImpl;
	}



	public void setStudentFacadeImpl(StudentFacadeImpl studentFacadeImpl) {
		this.studentFacadeImpl = studentFacadeImpl;
	}



	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}



	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}



	public List<StudentProfileDTO> getStudentProfiles() {
		return studentProfiles;
	}



	public void setStudentProfiles(List<StudentProfileDTO> studentProfiles) {
		this.studentProfiles = studentProfiles;
	}



	
	
	
	
}



