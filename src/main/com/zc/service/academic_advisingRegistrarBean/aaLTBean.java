package main.com.zc.service.academic_advisingRegistrarBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_students;
import main.com.zc.service.academic_advisingInstructorStudents.aa_instructor_studentsAppServiceImpl;
import main.com.zc.service.academic_advisingInstructorsDates.aa_instructor_date;
import main.com.zc.service.academic_advisingInstructorsDates.aa_instructor_dateAppServiceImpl;
import main.com.zc.service.academic_advising_instructor.aa_instructor;
import main.com.zc.service.academic_advising_instructor.aa_instructorAppServiceImpl;
import main.com.zc.service.academic_advising_student_profile.aa_student_profile;
import main.com.zc.service.academic_advising_student_profile.aa_student_profileAppServiceImpl;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

@ManagedBean(name = "aaLTBean")
@SessionScoped
public class aaLTBean implements Serializable{
	
	

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

	private aa_student_profile selectedStudent;
	private aa_instructor selectedInstructor;
	
	private List<aa_student_profile> allStudentSelected;
	

	@ManagedProperty(value = "#{aa_instructor_dateFacadeImpl}")
	private aa_instructor_dateAppServiceImpl aa_instructor_dateFacade;


	@ManagedProperty(value = "#{aa_instructorFacadeImpl}")
	private aa_instructorAppServiceImpl aa_instructorFacade;


	@ManagedProperty(value = "#{aa_instructor_studentsFacadeImpl}")
	private aa_instructor_studentsAppServiceImpl instructor_studentsFacade;
	
	private String selectedYear;
	private String selectedSemester;
	private String selectedAction;
	

	private List<aa_instructor_date> allinstructorDates;
	private List<BaseDTO> semesterLst;
	private List<String> yearLst;
	

	private aa_instructor_date selectedDateData;
	
	private UploadedFile file;
	private String statusMessage;
	private boolean saveMood;
	private List<aa_instructor_students> studentInst=new ArrayList<aa_instructor_students>();
	private List<aa_instructor_students> list;
	private boolean resultText;
	
	
	@PostConstruct
	public void init() {

		refresh();
		
		
	}
	
//	public void goToStudentProfileMeeting(int idOfDate) {
////		System.out.print(idOfDate);
//		selectedDateData = aa_instructor_dateFacade.getById(idOfDate);
//		selectedStudent = aa_student_profileFacade.getById(selectedDateData.getStudent().getId());
//
//		ExternalContext ec = FacesContext.getCurrentInstance()
//		        .getExternalContext();
//		try {
//		    ec.redirect(ec.getRequestContextPath()
//		            + "/pages/secured/academic_advising/studentProfile_Reg.xhtml");
//		} catch (IOException e) {
//		    // TODO Auto-generated catch block
//		    e.printStackTrace();
//		}
//	}
//	
//	public void getAllListOfDates() {
//		allinstructorDates = aa_instructor_dateFacade.getByActionAndYearAndSemester(selectedAction, selectedYear, selectedSemester);
//	}
//	public void getAllListOfStudents() {
//		allStudentSelected = aa_student_profileFacade.getAllByYearAndSemester(selectedYear, selectedSemester);
//	}
//	
//	public void onRowSelect(SelectEvent event) {
//		selectedStudent= (aa_student_profile) event.getObject();
//
//		ExternalContext ec = FacesContext.getCurrentInstance()
//		        .getExternalContext();
//		try {
//		    ec.redirect(ec.getRequestContextPath()
//		            + "/pages/secured/academic_advising/studentRegistrar.xhtml");
//		} catch (IOException e) {
//		    // TODO Auto-generated catch block
//		    e.printStackTrace();
//		}
//    }
//	
//	
//	public void onRowEdit(RowEditEvent event) {
//		
//		aa_student_profile profileStudent = (aa_student_profile) event.getObject();
//		aa_student_profileFacade.addaa_student_profile(profileStudent);
//        FacesMessage msg = new FacesMessage("Student with Id Edited", String.valueOf(profileStudent.getZewailcity_id()));
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//     
	public String parseFile()
	{
		 InputStream inputStream = null;
		 try {
			inputStream=file.getInputstream();
			list=parseStudentInsFile(inputStream); 

		    
		   
		    studentInst.clear();
		    studentInst.addAll(list);
		   
			resultText=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "";
	}
	private List<aa_instructor_students> parseStudentInsFile(InputStream input) {
		FormsStatusDTO settingform = facadeSettings.getById(23);
		
		selectedYear = String.valueOf(settingform.getYear());
		selectedSemester = settingform.getSemester().getName();
		
		List<aa_instructor_students> dataList = new ArrayList<aa_instructor_students>();
		try {
			//inputStream = resource.getInputStream();
			// Create Workbook instance holding reference to .xlsx file
			
			OPCPackage pkg = OPCPackage.open(input);
			XSSFWorkbook workbook = new XSSFWorkbook(pkg);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			int rowsNumbers = sheet.getLastRowNum();
			 for(int i=0;i<=rowsNumbers;i++) {
					Row row = sheet.getRow(i);
					aa_instructor_students data=new aa_instructor_students();
				String studentEmail = "";
				String instructorEmail = "";
				for (int withNewCount=0;withNewCount<row.getLastCellNum();withNewCount++) {
					Cell cell = row.getCell(withNewCount);

					int count = withNewCount+1;
                  
					if (count == 2) { // Student Mail
						try{
							studentEmail = getTheValueFromCell2(cell);
						}
						catch(Exception ex)
						{
							System.out.println(ex.toString());
						}
					}
					
					
					if (count == 4) { // Instructor Mail
						try{
							instructorEmail = getTheValueFromCell2(cell);
						}
						catch(Exception ex)
						{
							System.out.println(ex.toString());
						}
					}
					data = instructor_studentsFacade.getByStudentEmailAndYearAndSemester(studentEmail, String.valueOf(settingform.getYear()), settingform.getSemester().getName());
					
					if(data == null) {
						try {
							data=new aa_instructor_students();
						selectedStudent = aa_student_profileFacade.getByMailAndYearAndSemester(studentEmail, String.valueOf(settingform.getYear()), settingform.getSemester().getName());
						selectedInstructor = aa_instructorFacade.getByMail(instructorEmail);
						data.setStudent(selectedStudent);
						data.setInstructor(selectedInstructor);
						data.setYear(selectedYear);
						data.setSemester(selectedSemester);
						
						}catch (Exception e) {
							// TODO: handle exception
						}
					}else {
						selectedStudent = aa_student_profileFacade.getByMailAndYearAndSemester(studentEmail, String.valueOf(settingform.getYear()), settingform.getSemester().getName());
						selectedInstructor = aa_instructorFacade.getByMail(instructorEmail);
						data.setStudent(selectedStudent);
						data.setInstructor(selectedInstructor);
						data.setYear(selectedYear);
						data.setSemester(selectedSemester);
						
					}



				}
					
					dataList.add(data);
				
//				System.out.println("");
			}

			input.close();
		 dataList.remove(0);
		
		return dataList;
		 
	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveCourse()
	{
		//courses=facade.saveCourses(list);
		if(list.size()!=0)
		{
			for(int i=0;i<list.size();i++) {
				instructor_studentsFacade.addaa_instructor_students(list.get(i));
			}
			 JavaScriptMessagesHandler.RegisterNotificationMessage(null, list.size()+" Student(s) has(have) been saved Successfully"); 
		}
		System.out.println("Added List size is "+list.size());
	}

	
	public void refresh(){
		studentInst=new ArrayList<aa_instructor_students>();
		list=new ArrayList<aa_instructor_students>();
//		fillSemesterLst();
	}

//
//	public void fillSemesterLst()
//	{
//		semesterLst=new ArrayList<BaseDTO>();
//		semesterLst.add(new BaseDTO(0,"Fall"));
//		semesterLst.add(new BaseDTO(1,"Spring"));
//		semesterLst.add(new BaseDTO(2,"Summer"));
//		//semesterLst.add(new BaseDTO(3,"Winter"));
//		
//		yearLst=new ArrayList<String>();
//		for(int i=2013;i<2031;i++)
//		{
//			yearLst.add(String.valueOf(i));
//		}
//	}
//	
//	public void fillYearLst(AjaxBehaviorEvent event)
//	{
//		 yearLst=new ArrayList<String>();
//		for(int i=2013;i<2031;i++)
//		{
//			yearLst.add(String.valueOf(i));
//		}
//	}

	 public String getTheValueFromCell2(Cell cell) {
		 String returnedValue="";
		 switch (cell.getCellType()) {
		 
		 case Cell.CELL_TYPE_BLANK:
        	 returnedValue = "";
             break;
         case Cell.CELL_TYPE_STRING:
        	 returnedValue = String.valueOf(cell.getStringCellValue());
             break;
         case Cell.CELL_TYPE_NUMERIC:
        	 Long number = (long) cell.getNumericCellValue();
        	 returnedValue = String.valueOf(number);
             break;
         case Cell.CELL_TYPE_BOOLEAN:
        	 returnedValue = String.valueOf(cell.getBooleanCellValue());
             break;
         
         default :

         }
		 return returnedValue;
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

	public List<aa_student_profile> getAllStudentSelected() {
		return allStudentSelected;
	}

	public void setAllStudentSelected(List<aa_student_profile> allStudentSelected) {
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

	public aa_instructorAppServiceImpl getAa_instructorFacade() {
		return aa_instructorFacade;
	}

	public void setAa_instructorFacade(aa_instructorAppServiceImpl aa_instructorFacade) {
		this.aa_instructorFacade = aa_instructorFacade;
	}

	public aa_instructor_studentsAppServiceImpl getInstructor_studentsFacade() {
		return instructor_studentsFacade;
	}

	public void setInstructor_studentsFacade(aa_instructor_studentsAppServiceImpl instructor_studentsFacade) {
		this.instructor_studentsFacade = instructor_studentsFacade;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public boolean isSaveMood() {
		return saveMood;
	}

	public void setSaveMood(boolean saveMood) {
		this.saveMood = saveMood;
	}

	public List<aa_instructor_students> getStudentInst() {
		return studentInst;
	}

	public void setStudentInst(List<aa_instructor_students> studentInst) {
		this.studentInst = studentInst;
	}

	public List<aa_instructor_students> getList() {
		return list;
	}

	public void setList(List<aa_instructor_students> list) {
		this.list = list;
	}

	public boolean isResultText() {
		return resultText;
	}

	public void setResultText(boolean resultText) {
		this.resultText = resultText;
	}

	public aa_instructor getSelectedInstructor() {
		return selectedInstructor;
	}

	public void setSelectedInstructor(aa_instructor selectedInstructor) {
		this.selectedInstructor = selectedInstructor;
	}
	
	
	
}



