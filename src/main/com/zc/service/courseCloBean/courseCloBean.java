package main.com.zc.service.courseCloBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.service.clo_survey_ans.clo_survey_ans;
import main.com.zc.service.clo_survey_ans.clo_survey_ansAppServiceImpl;
import main.com.zc.service.courseClo.course_clo;
import main.com.zc.service.courseClo.course_cloAppServiceImpl;
import main.com.zc.service.student.IStudentGetDataAppService;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

@ManagedBean(name = "courseCloBean")
@ViewScoped
public class courseCloBean implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5842440236769354416L;


	/**
	 * 
	 */
	
	
 
	 


	@ManagedProperty(value = "#{course_cloFacadeImpl}")
	private course_cloAppServiceImpl course_cloFacade;
	

	@ManagedProperty(value = "#{clo_survey_ansFacadeImpl}")
	private clo_survey_ansAppServiceImpl clo_survey_ansFacade;






	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	

	@ManagedProperty("#{IStudentGetDataAppService}")
	private IStudentGetDataAppService studentFacade;
	

    
	List<clo_survey_ans> listOfStudentAnswers;
	List<clo_survey_ans> listOfCourseAnswers;

	List<course_clo> listOfAllCourses;
	

	List<course_clo> listOfUploadedCourses;
	
	
	course_clo selectedCourse;
	clo_survey_ans selectedAnswerForStudentAndCourse;
	
	Student studentThisAccount;
	boolean aStudentAccount = false;
	
	

	private UploadedFile file;
	private String statusMessage;
	private boolean resultText;
	private boolean saveMood;
	private List<course_clo> courses=new ArrayList<course_clo>();

	private List<Integer> resultsPersonClo;
	private double[] resultsPercentage;
	
	@PostConstruct
	public void init() {

		selectedCourse=new course_clo();
		refresh();
		
		
	}
	
	public void selectTheAnswerForthisCourseAndStudent() {
		selectedCourse = course_cloFacade.getById(selectedCourse.getId());
		System.out.println("Dakrory: "+String.valueOf(selectedCourse.getCourse_code()));
		selectedAnswerForStudentAndCourse= clo_survey_ansFacade.getByStudentIdAndCourseId(studentThisAccount.getId(), selectedCourse.getId());
		if(selectedAnswerForStudentAndCourse==null) {
			selectedAnswerForStudentAndCourse=new clo_survey_ans();
		}
	}
	
	public void selectTheCourseResults() {
		resultsPersonClo=new ArrayList<Integer>();
		
		selectedCourse = course_cloFacade.getById(selectedCourse.getId());
		listOfCourseAnswers=clo_survey_ansFacade.getAllByCourseId(selectedCourse.getId());
		if(listOfCourseAnswers!=null) {
		
		
		for(int i=0;i<20;i++) {

			resultsPersonClo.add(0);
			resultsPercentage[i]=0;
			
		}
		
		for(int i=0;i<listOfCourseAnswers.size();i++) {
			if(listOfCourseAnswers.get(i).getClo1()!=null) {
				resultsPersonClo.set(0, resultsPersonClo.get(0)+1);
				double res =listOfCourseAnswers.get(i).getClo1();
				System.out.println("Dakrory: "+listOfCourseAnswers.get(i).getClo1());
				resultsPercentage[0]= res+resultsPercentage[0];
			}
			
			if(listOfCourseAnswers.get(i).getClo2()!=null) {
				resultsPersonClo.set(1, resultsPersonClo.get(1)+1);
				double res =listOfCourseAnswers.get(i).getClo2();
				resultsPercentage[1] = res +resultsPercentage[1];
			}
			
			if(listOfCourseAnswers.get(i).getClo3()!=null) {
				resultsPersonClo[2, resultsPersonClo.get(2)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo3();
				if(res !=null)
				resultsPercentage[2] = res +resultsPercentage[2];
			}
			
			if(listOfCourseAnswers.get(i).getClo4()!=null) {
				resultsPersonClo[3, resultsPersonClo.get(3)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo4();
				if(res !=null)
				resultsPercentage[3] = res +resultsPercentage[3];
			}
			
			if(listOfCourseAnswers.get(i).getClo5()!=null) {
				resultsPersonClo[4, resultsPersonClo.get(4)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo5();
				if(res !=null)
				resultsPercentage[4] = res +resultsPercentage[4];
			}
			
			if(listOfCourseAnswers.get(i).getClo6()!=null) {
				resultsPersonClo[5, resultsPersonClo.get(5)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo6();
				if(res !=null)
				resultsPercentage[5] = res +resultsPercentage[5];
			}
			
			if(listOfCourseAnswers.get(i).getClo7()!=null) {
				resultsPersonClo[6, resultsPersonClo.get(6)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo7();
				if(res !=null)
				resultsPercentage[6] = res +resultsPercentage[6];
			}
			
			if(listOfCourseAnswers.get(i).getClo8()!=null) {
				resultsPersonClo[7, resultsPersonClo.get(7)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo8();
				if(res !=null)
				resultsPercentage[7] = res +resultsPercentage[7];
			}
			
			if(listOfCourseAnswers.get(i).getClo9()!=null) {
				resultsPersonClo[8, resultsPersonClo.get(8)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo9();
				if(res !=null)
				resultsPercentage[8] = res +resultsPercentage[8];
			}
			
			if(listOfCourseAnswers.get(i).getClo10()!=null) {
				resultsPersonClo[9, resultsPersonClo.get(9)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo10();
				if(res !=null)
				resultsPercentage[9] = res +resultsPercentage[9];
			}
			
			if(listOfCourseAnswers.get(i).getClo11()!=null) {
				resultsPersonClo[10, resultsPersonClo.get(10)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo11();
				if(res !=null)
				resultsPercentage[10] = res +resultsPercentage[10];
			}
			
			if(listOfCourseAnswers.get(i).getClo12()!=null) {
				resultsPersonClo[11, resultsPersonClo.get(11)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo12();
				if(res !=null)
				resultsPercentage[11] = res +resultsPercentage[11];
			}
			
			if(listOfCourseAnswers.get(i).getClo13()!=null) {
				resultsPersonClo[12, resultsPersonClo.get(12)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo13();
				if(res !=null)
				resultsPercentage[12] = res +resultsPercentage[12];
			}
			
			if(listOfCourseAnswers.get(i).getClo14()!=null) {
				resultsPersonClo[13, resultsPersonClo.get(13)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo14();
				if(res !=null)
				resultsPercentage[13] = res +resultsPercentage[13];
			}
			
			if(listOfCourseAnswers.get(i).getClo15()!=null) {
				resultsPersonClo[14, resultsPersonClo.get(14)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo15();
				if(res !=null)
				resultsPercentage[14] = res +resultsPercentage[14];
			}
			
			if(listOfCourseAnswers.get(i).getClo16()!=null) {
				resultsPersonClo[15, resultsPersonClo.get(15)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo16();
				if(res !=null)
				resultsPercentage[15] = res +resultsPercentage[15];
			}
			
			if(listOfCourseAnswers.get(i).getClo17()!=null) {
				resultsPersonClo[16, resultsPersonClo.get(16)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo17();
				if(res !=null)
				resultsPercentage[16] = res +resultsPercentage[16];
			}
			
			if(listOfCourseAnswers.get(i).getClo18()!=null) {
				resultsPersonClo[17, resultsPersonClo.get(17)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo18();
				if(res !=null)
				resultsPercentage[17] = res +resultsPercentage[17];
			}
			
			if(listOfCourseAnswers.get(i).getClo19()!=null) {
				resultsPersonClo[18, resultsPersonClo.get(18)+1);
				double res =Long.getLong(String.valueOf(listOfCourseAnswers.get(i).getClo19();
				if(res !=null)
				resultsPercentage[18] = res +resultsPercentage[18];
			}
			
			
			if(listOfCourseAnswers.get(i).getClo20()!=null) {
				resultsPersonClo[19, resultsPersonClo.get(19)+1);
				double res =listOfCourseAnswers.get(i).getClo20();
				if(res !=null)
				resultsPercentage[19] = res +resultsPercentage[19];
			}
		}
		
		for(int i=0;i<resultsPercentage.size();i++) {
			
			resultsPercentage.set(i, resultsPercentage.get(i)/5);
			System.out.println(resultsPercentage.get(i));
			
		}
		}
		
	}
	private void dootherProcendure(String mail) {
		// TODO Auto-generated method stub
		
		
	}

	public void submitTheSurvey() {
		selectedAnswerForStudentAndCourse.setCourseCloId(selectedCourse);
		selectedAnswerForStudentAndCourse.setDate(new Date());
		selectedAnswerForStudentAndCourse.setStudentId(studentThisAccount);
		clo_survey_ansFacade.addclo_survey_ans(selectedAnswerForStudentAndCourse);
		
		 JavaScriptMessagesHandler.RegisterNotificationMessage(null, courses.size()+" course(s) has(have) been saved Successfully");
	}

	private void doStudentProcendure(String mail) {
		// TODO Auto-generated method stub

		studentThisAccount=studentFacade.getStudentByPersonMail(mail);
		
		System.out.println("Dakrory: "+String.valueOf(studentThisAccount.getData().getMail()));
		
	}


	public void refresh(){
		
		listOfAllCourses = course_cloFacade.getAll();
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4))) // student case
			{
				//Student
				aStudentAccount = true;
				System.out.println("1Dakrory: "+String.valueOf(mail));
				doStudentProcendure(mail);
			
			}
			else
			{
				//Another
				aStudentAccount = false;
				dootherProcendure(mail);
				
			}
			
		}
		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRequest();
		
		try{
			Integer id=Integer.parseInt(origRequest.getParameterValues("id")[0]);
				if(id!=null){
					
				}
			}
		catch(Exception ex){
			 
		}
		
		
		
		
	}

	public String parseFile()
	{
		 InputStream inputStream = null;
		 try {
			inputStream=file.getInputstream();
			listOfUploadedCourses=parseCoursesFile(inputStream);
			 
			courses = newCoursesOnly(listOfUploadedCourses);

		    
			System.out.println("Size : "+courses.size());
			resultText=true;
			//courses=facade.saveCourses(list);
			//System.out.println("Added Courses size is "+courses.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "";
	}
	
	private List<course_clo> newCoursesOnly(List<course_clo> listOfUploadedCourses2) {
		// TODO Auto-generated method stub

		List<course_clo> dataList = new ArrayList<course_clo>();
		for(int i=0;i<listOfUploadedCourses2.size();i++) {
			course_clo c0=listOfUploadedCourses2.get(i);
			course_clo c1=course_cloFacade.getAllByYearAndSemestarAndCourseCode(c0.getYear(),c0.getSemestar(), c0.getCourse_code());
			if(c1==null) {
				dataList.add(c0);
			}
		}
		return dataList;
	}


	private List<course_clo> parseCoursesFile(InputStream inputStream) {
		// TODO Auto-generated method stub
		
		List<course_clo> dataList = new ArrayList<course_clo>();
		try {
			//inputStream = resource.getInputStream();
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			int rowsNumbers = sheet.getLastRowNum();
	 	       System.out.println("Sheet");
			
	 	       for(int i=1;i<rowsNumbers+1;i++) {
				Row row = sheet.getRow(i);
				// For each row, iterate through all the columns

				course_clo course=new course_clo();
				for (int count=0;count<row.getLastCellNum();count++) {
					Cell cell = row.getCell(count);
					
					int withNewCount = count+1;
					
					if (withNewCount == 1) { // Program 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setProgram(data);
						}
					}
					
					
					if (withNewCount == 2) { // Course_code 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setCourse_code(data);
						}
					}
					
					if (withNewCount == 3) { // Course_code 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setYear(data);
						}
					}
					
					if (withNewCount == 4) { // Semestar 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setSemestar(data);
						}
					}
					
					if (withNewCount == 5) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo1(data);
						}
					}
					
					if (withNewCount == 6) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo2(data);
						}
					}
					
					if (withNewCount == 7) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo3(data);
						}
					}
					
					if (withNewCount == 8) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo4(data);
						}
					}
					
					if (withNewCount == 9) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo5(data);
						}
					}
					
					if (withNewCount == 10) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo6(data);
						}
					}
					
					if (withNewCount == 11) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo7(data);
						}
					}
					
					if (withNewCount == 12) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo8(data);
						}
					}
					
					if (withNewCount == 13) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo9(data);
						}
					}
					
					if (withNewCount == 14) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo10(data);
						}
					}
					
					if (withNewCount == 15) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo11(data);
						}
					}
					
					if (withNewCount == 16) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo12(data);
						}
					}
					
					if (withNewCount == 17) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo13(data);
						}
					}
					
					if (withNewCount == 18) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo14(data);
						}
					}
					
					if (withNewCount == 19) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo15(data);
						}
					}
					
					if (withNewCount == 20) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo16(data);
						}
					}
					
					if (withNewCount == 21) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo17(data);
						}
					}
					
					if (withNewCount == 22) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo18(data);
						}
					}
					
					if (withNewCount == 23) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo19(data);
						}
					}
					
					if (withNewCount == 24) { // CLO 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							course.setClo20(data);
						}
					}
					
					
					
					
					
				}

				course.setDate(new Date());
				dataList.add(course);
	 	       }
			
				
				
				System.out.println("");
			

			inputStream.close();
				return dataList;
		 
	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		

	}


	private String theIntgerDataAdaptedTo(Cell cell) {
		// TODO Auto-generated method stub
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
		{ 
		
			try{
				
		
			 return String.valueOf(cell.getNumericCellValue());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}

			 break;
			 
		}
		case Cell.CELL_TYPE_STRING:
		{
		
			try{
				return String.valueOf(cell.getStringCellValue());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
		}
		
		}
		return null;
	}


	public void saveCourse()
	{
		for(int i=0;i<courses.size();i++) {
			course_cloFacade.addcourse_clo(courses.get(i));
		}
		if(courses.size()!=0)
		{
			 JavaScriptMessagesHandler.RegisterNotificationMessage(null, courses.size()+" course(s) has(have) been saved Successfully"); 
		}
		System.out.println("Added Courses size is "+courses.size());
	}

	public course_cloAppServiceImpl getCourse_cloFacade() {
		return course_cloFacade;
	}


	public void setCourse_cloFacade(course_cloAppServiceImpl course_cloFacade) {
		this.course_cloFacade = course_cloFacade;
	}


	public clo_survey_ansAppServiceImpl getClo_survey_ansFacade() {
		return clo_survey_ansFacade;
	}


	public void setClo_survey_ansFacade(clo_survey_ansAppServiceImpl clo_survey_ansFacade) {
		this.clo_survey_ansFacade = clo_survey_ansFacade;
	}





	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}


	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}


	public List<clo_survey_ans> getListOfStudentAnswers() {
		return listOfStudentAnswers;
	}


	public void setListOfStudentAnswers(List<clo_survey_ans> listOfStudentAnswers) {
		this.listOfStudentAnswers = listOfStudentAnswers;
	}


	public List<clo_survey_ans> getListOfCourseAnswers() {
		return listOfCourseAnswers;
	}


	public void setListOfCourseAnswers(List<clo_survey_ans> listOfCourseAnswers) {
		this.listOfCourseAnswers = listOfCourseAnswers;
	}


	public List<course_clo> getListOfAllCourses() {
		return listOfAllCourses;
	}


	public void setListOfAllCourses(List<course_clo> listOfAllCourses) {
		this.listOfAllCourses = listOfAllCourses;
	}


	public course_clo getSelectedCourse() {
		return selectedCourse;
	}


	public void setSelectedCourse(course_clo selectedCourse) {
		this.selectedCourse = selectedCourse;
	}



	public boolean isaStudentAccount() {
		return aStudentAccount;
	}


	public void setaStudentAccount(boolean aStudentAccount) {
		this.aStudentAccount = aStudentAccount;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public List<course_clo> getListOfUploadedCourses() {
		return listOfUploadedCourses;
	}


	public void setListOfUploadedCourses(List<course_clo> listOfUploadedCourses) {
		this.listOfUploadedCourses = listOfUploadedCourses;
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


	public boolean isResultText() {
		return resultText;
	}


	public void setResultText(boolean resultText) {
		this.resultText = resultText;
	}


	public boolean isSaveMood() {
		return saveMood;
	}


	public void setSaveMood(boolean saveMood) {
		this.saveMood = saveMood;
	}


	public List<course_clo> getCourses() {
		return courses;
	}


	public void setCourses(List<course_clo> courses) {
		this.courses = courses;
	}


	public IStudentGetDataAppService getStudentFacade() {
		return studentFacade;
	}


	public void setStudentFacade(IStudentGetDataAppService studentFacade) {
		this.studentFacade = studentFacade;
	}


	public Student getStudentThisAccount() {
		return studentThisAccount;
	}


	public void setStudentThisAccount(Student studentThisAccount) {
		this.studentThisAccount = studentThisAccount;
	}

	public clo_survey_ans getSelectedAnswerForStudentAndCourse() {
		return selectedAnswerForStudentAndCourse;
	}

	public void setSelectedAnswerForStudentAndCourse(clo_survey_ans selectedAnswerForStudentAndCourse) {
		this.selectedAnswerForStudentAndCourse = selectedAnswerForStudentAndCourse;
	}

	public List<Integer> getResultsPersonClo() {
		return resultsPersonClo;
	}

	public void setResultsPersonClo(List<Integer> resultsPersonClo) {
		this.resultsPersonClo = resultsPersonClo;
	}

	public List<Long> getResultsPercentage() {
		return resultsPercentage;
	}

	public void setResultsPercentage(List<Long> resultsPercentage) {
		this.resultsPercentage = resultsPercentage;
	}


	
}
