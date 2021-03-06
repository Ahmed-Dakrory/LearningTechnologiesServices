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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
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
	
	
 
	 
	@ManagedProperty("#{IStudentCourseFacade}")
   	private IStudentCourseFacade facadeStudendCourses; 

	@ManagedProperty(value = "#{course_cloFacadeImpl}")
	private course_cloAppServiceImpl course_cloFacade;
	

	@ManagedProperty(value = "#{clo_survey_ansFacadeImpl}")
	private clo_survey_ansAppServiceImpl clo_survey_ansFacade;




	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade facadeSettings;


	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	

	@ManagedProperty("#{IStudentGetDataAppService}")
	private IStudentGetDataAppService studentFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;

	@ManagedProperty("#{ICourseEvalAnswersFacade}")
	private ICourseEvalAnswersFacade coursesFacade;
	
	
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
	private boolean changeCLOOpened;
	private List<course_clo> courses=new ArrayList<course_clo>();

	private Integer semesterSelected=0;
	private Integer yearSelected=2015;

	private List<cloResult> resultsPersonPercntageClo;
	
	
	
	private boolean showNowForInstructors=false;
	@PostConstruct
	public void init() {

		selectedCourse=new course_clo();
		refresh();
		
		
	}
	
	
	public void saveCourseClo() {
		if(selectedCourse.getClo1().equals("")) selectedCourse.setClo1(null);
		if(selectedCourse.getClo2().equals("")) selectedCourse.setClo2(null);
		if(selectedCourse.getClo3().equals("")) selectedCourse.setClo3(null);
		if(selectedCourse.getClo4().equals("")) selectedCourse.setClo4(null);
		if(selectedCourse.getClo5().equals("")) selectedCourse.setClo5(null);
		if(selectedCourse.getClo6().equals("")) selectedCourse.setClo6(null);
		if(selectedCourse.getClo7().equals("")) selectedCourse.setClo7(null);
		if(selectedCourse.getClo8().equals("")) selectedCourse.setClo8(null);
		if(selectedCourse.getClo9().equals("")) selectedCourse.setClo9(null);
		if(selectedCourse.getClo10().equals("")) selectedCourse.setClo10(null);
		if(selectedCourse.getClo11().equals("")) selectedCourse.setClo11(null);
		if(selectedCourse.getClo12().equals("")) selectedCourse.setClo12(null);
		if(selectedCourse.getClo13().equals("")) selectedCourse.setClo13(null);
		if(selectedCourse.getClo14().equals("")) selectedCourse.setClo14(null);
		if(selectedCourse.getClo15().equals("")) selectedCourse.setClo15(null);
		if(selectedCourse.getClo16().equals("")) selectedCourse.setClo16(null);
		if(selectedCourse.getClo17().equals("")) selectedCourse.setClo17(null);
		if(selectedCourse.getClo18().equals("")) selectedCourse.setClo18(null);
		if(selectedCourse.getClo19().equals("")) selectedCourse.setClo19(null);
		if(selectedCourse.getClo20().equals("")) selectedCourse.setClo20(null);
		course_cloFacade.addcourse_clo(selectedCourse);
		JavaScriptMessagesHandler.RegisterErrorMessage(null,
				"Saved");
	}
	public void selectTheAnswerForthisCourseAndStudent() {
		selectedCourse = course_cloFacade.getById(selectedCourse.getId());
		System.out.println("Dakrory: "+String.valueOf(selectedCourse.getCourse_code()));
		selectedAnswerForStudentAndCourse= clo_survey_ansFacade.getByStudentIdAndCourseId(studentThisAccount.getId(), selectedCourse.getId());
		if(selectedAnswerForStudentAndCourse==null) {
			selectedAnswerForStudentAndCourse=new clo_survey_ans();
		}
	}
	
	public void getListOfAllCoursesThreshold() {
		List<cloThreshold> allCoursesThresoldResults=new ArrayList<cloThreshold>();
		for(int i=0;i<listOfAllCourses.size();i++) {
			 course_clo courseCLO = course_cloFacade.getById(listOfAllCourses.get(i).getId());
				listOfCourseAnswers=clo_survey_ansFacade.getAllByCourseId(courseCLO.getId());
				List<clo_survey_ans> listOfCourseAnswersGroupbyStudent = clo_survey_ansFacade.getAllByCourseIdGroupByStudent(courseCLO.getId());
				cloThreshold course_threshold=null;
				if(listOfCourseAnswersGroupbyStudent == null) {

					 course_threshold=new cloThreshold(listOfCourseAnswers, courseCLO,0);
				}else {

					 course_threshold=new cloThreshold(listOfCourseAnswers, courseCLO,listOfCourseAnswersGroupbyStudent.size());
				}
				
			System.out.println("Ahmed Result: "+courseCLO.getCourse_code());
			
			allCoursesThresoldResults.add(course_threshold);
		}
		
		if(allCoursesThresoldResults.size()>0) {
			generateFile(allCoursesThresoldResults);
		}
	}
	
	public void generateFile(List<cloThreshold> allCoursesThresoldResults){
		 HSSFWorkbook workbook = new HSSFWorkbook();
		    HSSFSheet sheet = workbook.createSheet();
		    
		    ReportFileGeneration reportFileGeneration=new ReportFileGeneration(allCoursesThresoldResults,workbook, sheet);
		    
		    reportFileGeneration.generateReport();

		    FacesContext facesContext = FacesContext.getCurrentInstance();
		    ExternalContext externalContext = facesContext.getExternalContext();
		    externalContext.setResponseContentType("application/vnd.ms-excel");
		    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Survey_results_statistics.xls\"");

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
	
	
	public void selectTheCourseResults() {
		
		

		resultsPersonPercntageClo=new ArrayList<cloResult>();
		
		
		
		selectedCourse = course_cloFacade.getById(selectedCourse.getId());
		listOfCourseAnswers=clo_survey_ansFacade.getAllByCourseId(selectedCourse.getId());
		if(listOfCourseAnswers!=null) {
			
			
		
		for(int i=0;i<20;i++) {

			resultsPersonPercntageClo.add(new cloResult(i+1));
		}
		
		for(int i=0;i<listOfCourseAnswers.size();i++) {
			if(listOfCourseAnswers.get(i).getClo1()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo1();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(0);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(0,clo);
				
			}
			
			if(listOfCourseAnswers.get(i).getClo2()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo2();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(1);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(1,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo3()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo3();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(2);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(2,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo4()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo4();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(3);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(3,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo5()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo5();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(4);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(4,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo6()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo6();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(5);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(5,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo7()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo7();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(6);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(6,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo8()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo8();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(7);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(7,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo9()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo9();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(8);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(8,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo10()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo10();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(9);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(9,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo11()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo11();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(10);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(10,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo12()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo12();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(11);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(11,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo13()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo13();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(12);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(12,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo14()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo14();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(13);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(13,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo15()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo15();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(14);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(14,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo16()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo16();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(15);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(15,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo17()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo17();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(16);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(16,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo18()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo18();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(17);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(17,clo);
			}
			
			if(listOfCourseAnswers.get(i).getClo19()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo19();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(18);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(18,clo);
			}
			
			
			if(listOfCourseAnswers.get(i).getClo20()!=null) {
				Integer res =listOfCourseAnswers.get(i).getClo20();
				res=res-1;
				cloResult clo=resultsPersonPercntageClo.get(19);
				clo.numberOfPersons=clo.numberOfPersons+1;
				int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
				
				numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
				clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
				resultsPersonPercntageClo.set(19,clo);
			}
		}
		
		
		
		for(int i=0;i<resultsPersonPercntageClo.size();i++) {
			cloResult cR= resultsPersonPercntageClo.get(i);
			for(int j=0;j<resultsPersonPercntageClo.get(i).percentage.length;j++) {
				if(resultsPersonPercntageClo.get(i).getNumberOfPersons()!=0) {
					cR.percentage[j]= ((float)cR.getEachGradeCloPersons()[j])/((float) cR.getNumberOfPersons())*100;
					
				}
			}
			
			resultsPersonPercntageClo.set(i, cR);
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
		
		 JavaScriptMessagesHandler.RegisterNotificationMessage(null, " Your Survey have been saved Successfully");
	}

	private void doStudentProcendure(String mail) {
		// TODO Auto-generated method stub

		studentThisAccount=studentFacade.getStudentByPersonMail(mail);
		
		System.out.println("Dakrory: "+String.valueOf(studentThisAccount.getData().getMail()));
		
	}


	public void getListOfCoursesByYearAndSemester() {
		
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4))) // student case
			{
				doStudentProcendure(mail) ;
		Integer studentId = studentThisAccount.getId();
		System.out.println(studentId);
		//listOfAllCourses = course_cloFacade.getAllByYearAndSemestar(yearSelected,semesterSelected);
		List<CoursesDTO> courseOfStudent = facadeStudendCourses.getCoursesOfStudent(studentId);
		List<course_clo> allcoursesCloOfStudent = course_cloFacade.getAllByYearAndSemestar(yearSelected,semesterSelected);
		listOfAllCourses =new ArrayList<course_clo>();
		for(int i=0;i<courseOfStudent.size();i++) {
			for(int j=0;j<allcoursesCloOfStudent.size();j++) {
				if(courseOfStudent.get(i).getId() == allcoursesCloOfStudent.get(j).getId()) {
					listOfAllCourses.add(allcoursesCloOfStudent.get(j));
					//System.out.println("Dakrory: courseId"+courseOfStudent.get(i).getId()+" course: "+allcoursesCloOfStudent.get(j).getId());
					
				}
			}
		}
		}else if(mail.toLowerCase().equals(Constants.ACCREDITION_ENG_DEP.toLowerCase()))
		{
			listOfAllCourses = course_cloFacade.getAllByYearAndSemestar(yearSelected,semesterSelected);
		}else if(mail.toLowerCase().equals(Constants.ACCREDITION_SCI_DEP.toLowerCase()))
		{
			listOfAllCourses = course_cloFacade.getAllByYearAndSemestar(yearSelected,semesterSelected);
		}else if(mail.toLowerCase().equals(Constants.TeachingEffectiveness_DEP.toLowerCase()))
		{
			listOfAllCourses = course_cloFacade.getAllByYearAndSemestar(yearSelected,semesterSelected);
		}else {
			InstructorDTO inst = getInsDataFacade.getInsByPersonMail(mail);
			System.out.println("Instructor name: "+inst.getName());
			List<CoursesDTO> courseOfInstructor =coursesFacade.getCoursesByInsID(inst.getId());
			List<course_clo> allcoursesClo = course_cloFacade.getAllByYearAndSemestar(yearSelected,semesterSelected);
			listOfAllCourses =new ArrayList<course_clo>();
			for(int i=0;i<courseOfInstructor.size();i++) {
				for(int j=0;j<allcoursesClo.size();j++) {
					if(courseOfInstructor.get(i).getId() == allcoursesClo.get(j).getId()) {
						listOfAllCourses.add(allcoursesClo.get(j));
						//System.out.println("Dakrory: courseId"+courseOfStudent.get(i).getId()+" course: "+allcoursesCloOfStudent.get(j).getId());
						
					}
				}
			}
		}
		
		if(listOfAllCourses!=null) {
			if(listOfAllCourses.size()==1) {
				selectedCourse.setId(listOfAllCourses.get(0).getId());
				selectTheCourseResults();
			}
		}
	}
	}
	
	public void refresh(){
		FormsStatusDTO settingCLOOpen = facadeSettings.getById(29);
		
		if(settingCLOOpen.getStatus().getValue()!=1) {
			changeCLOOpened = true;
		}else {
			changeCLOOpened = false;
		}
		FormsStatusDTO settingCLO = facadeSettings.getById(18);
		
		/**
		 * if the setting equal phase 3 then show all results for instructors
		 */
		showNowForInstructors = (settingCLO.getStatus().getValue()==3);
		semesterSelected = Integer.valueOf(settingCLO.getSemester().getId());
		
		yearSelected = settingCLO.getYear();
		
		
		
		getListOfCoursesByYearAndSemester();
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4))) // student case
			{
				//Student
				aStudentAccount = true;
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
			course_clo c1=course_cloFacade.getAllByYearAndSemestarAndCourseCode(c0.getYear(),c0.getSemester().getValue(), c0.getCourse_code());
			if(c1==null) {
				dataList.add(c0);
			}else {
				c1.setClo1(c0.getClo1());
				c1.setClo2(c0.getClo2());
				c1.setClo3(c0.getClo3());
				c1.setClo4(c0.getClo4());
				c1.setClo5(c0.getClo5());
				c1.setClo6(c0.getClo6());
				c1.setClo7(c0.getClo7());
				c1.setClo8(c0.getClo8());
				c1.setClo9(c0.getClo9());
				c1.setClo10(c0.getClo10());
				c1.setClo11(c0.getClo11());
				c1.setClo12(c0.getClo12());
				c1.setClo13(c0.getClo13());
				c1.setClo14(c0.getClo14());
				c1.setClo15(c0.getClo15());
				c1.setClo16(c0.getClo16());
				c1.setClo17(c0.getClo17());
				c1.setClo18(c0.getClo18());
				c1.setClo19(c0.getClo19());
				c1.setClo20(c0.getClo20());
				c1.setProgram(c0.getProgram());
				
				dataList.add(c1);
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
							float f=Float.valueOf(data);
							course.setYear((int)f);
						}
					}
					
					if (withNewCount == 4) { // Semestar 
						String data = theIntgerDataAdaptedTo(cell);
						if(data!=null) {
							if(data.equalsIgnoreCase("Fall")) {
								course.setSemester(SemesterEnum.Fall);
							}else if(data.equalsIgnoreCase("Spring")) {
								course.setSemester(SemesterEnum.Spring);
							}else if(data.equalsIgnoreCase("Summer")) {
								course.setSemester(SemesterEnum.Summer);
							}
							
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
				
				System.out.println("Number: "+String.valueOf(course.getCourse_code()));
				
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
		try {
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
		}catch(Error e) {
			
		}catch(Exception excp) {
			
		}
		return null;
	}


	public void saveCourse()
	{
		for(int i=0;i<courses.size();i++) {
			course_cloFacade.addcourse_clo(courses.get(i));
			//System.out.println("Dakrory: "+courses.get(i).getCourse_code());
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

	public List<cloResult> getResultsPersonPercntageClo() {
		return resultsPersonPercntageClo;
	}

	public void setResultsPersonPercntageClo(List<cloResult> resultsPersonPercntageClo) {
		this.resultsPersonPercntageClo = resultsPersonPercntageClo;
	}

	public Integer getSemesterSelected() {
		return semesterSelected;
	}

	public void setSemesterSelected(Integer semesterSelected) {
		this.semesterSelected = semesterSelected;
	}

	public Integer getYearSelected() {
		return yearSelected;
	}

	public void setYearSelected(Integer yearSelected) {
		this.yearSelected = yearSelected;
	}

	public IFormsStatusFacade getFacadeSettings() {
		return facadeSettings;
	}

	public void setFacadeSettings(IFormsStatusFacade facadeSettings) {
		this.facadeSettings = facadeSettings;
	}

	public IStudentCourseFacade getFacadeStudendCourses() {
		return facadeStudendCourses;
	}

	public void setFacadeStudendCourses(IStudentCourseFacade facadeStudendCourses) {
		this.facadeStudendCourses = facadeStudendCourses;
	}

	

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public ICourseEvalAnswersFacade getCoursesFacade() {
		return coursesFacade;
	}

	public void setCoursesFacade(ICourseEvalAnswersFacade coursesFacade) {
		this.coursesFacade = coursesFacade;
	}


	public boolean isShowNowForInstructors() {
		return showNowForInstructors;
	}

	public void setShowNowForInstructors(boolean showNowForInstructors) {
		this.showNowForInstructors = showNowForInstructors;
	}


	public boolean isChangeCLOOpened() {
		return changeCLOOpened;
	}


	public void setChangeCLOOpened(boolean changeCLOOpened) {
		this.changeCLOOpened = changeCLOOpened;
	}



	
}
