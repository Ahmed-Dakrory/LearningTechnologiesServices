package main.com.zc.service.instructorSurveyAllBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.service.instructorSurveyAllBean.ReportFileGeneration;
import main.com.zc.service.instructorSurveyAllBean.cloThreshold;
import main.com.zc.service.instructorSurveyAllBean.cloResult;
import main.com.zc.service.instructor_all_survey_ans.Iinstructor_all_survey_ansAppService;
import main.com.zc.service.instructor_all_survey_ans.instructor_all_survey_ans;
import main.com.zc.service.instructor_all_survey_ques.Iinstructor_all_survey_quesAppService;
import main.com.zc.service.instructor_all_survey_ques.instructor_all_survey_ques;
import main.com.zc.service.instructor_all_survey_ques_choose.Iinstructor_all_survey_ques_chooseAppService;
import main.com.zc.service.student.IStudentGetDataAppService;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.ICourseInstructorFacade;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

@ManagedBean(name = "instructorSurveyAllBean")
@ViewScoped
public class instructorSurveyAllBean implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5842440236769354416L;


	/**
	 * 
	 */
	
	
 
	 
	@ManagedProperty("#{IStudentCourseFacade}")
   	private IStudentCourseFacade facadeStudendCourses; 

	


	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade facadeSettings;


	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	

	@ManagedProperty("#{IStudentGetDataAppService}")
	private IStudentGetDataAppService studentFacade;
	

	@ManagedProperty("#{ICourseInstructorFacade}")
	private ICourseInstructorFacade courseInsFacade;
	
	@ManagedProperty("#{instructor_all_survey_quesFacadeImpl}")
	private Iinstructor_all_survey_quesAppService instructor_all_survey_quesFacade;
	

	@ManagedProperty("#{instructor_all_survey_ansFacadeImpl}")
	private Iinstructor_all_survey_ansAppService instructor_all_survey_ansFacade;
	

	@ManagedProperty("#{instructor_all_survey_ques_chooseFacadeImpl}")
	private Iinstructor_all_survey_ques_chooseAppService instructor_all_survey_ques_chooseFacade;
	
	

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
	
	
	Student studentThisAccount;
	boolean aStudentAccount = false;
	
	@ManagedProperty("#{ICourseEvalAnswersFacade}")
	private ICourseEvalAnswersFacade coursesFacade;

	private UploadedFile file;
	private String statusMessage;
	private boolean resultText;
	private boolean saveMood;
	
	private Integer semesterSelected=1;
	private Integer yearSelected=2020;

	List<CoursesDTO> allStudentCoursesthisPeriod;
	List<CoursesDTO> allCoursesthisPeriod;
	
	Courses selectedCourse;
	List<InstructorDTO> allInstructorForThisCourse;
	List<InstructorDTO> allTAsForThisCourse;
	Employee selectedInstructor;

	List<instructor_all_survey_ques> all_Categories;
	List<InstructorSurveyCategory> questionsInCateg;
	
	
	
	List<instructor_all_survey_ans> allInstructorListOfAnswers;
	
	

	private List<cloResult> resultsPersonPercntageClo;

	List<instructor_all_survey_ans> listOfCourseAnswers;
	List<instructor_all_survey_ques> allquestionThisYearAndSemester;
	
	
	//Used For Only this instructor
	List<instructor_all_survey_ans> allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor;
	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade formStatus; 
	

	private boolean showNowForInstructorsFinal=false;
	private boolean showNowForInstructorsMidterm=false;
	@PostConstruct
	public void init() {

		selectedInstructor =new Employee();
		refresh();
		
		
	}


	public void refresh() {
		
		/**
		 * if the setting equal phase 3 then show all results for instructors
		 */
		showNowForInstructorsFinal = (formStatus.getById(25).getStatus().getValue()==3);
		showNowForInstructorsMidterm = (formStatus.getById(24).getStatus().getValue()==3);
		// TODO Auto-generated method stub
		FormsStatusDTO settingCLO = facadeSettings.getById(18);
		semesterSelected = Integer.valueOf(settingCLO.getSemester().getId());
		
		yearSelected = settingCLO.getYear();
		selectedCourse = new Courses();
		 allCoursesthisPeriod = coursesFacade.getCoursesByYearAndSemester(semesterSelected, yearSelected);
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4))) // student case
			{
				//Student
				aStudentAccount = true;
				allStudentCoursesthisPeriod = new ArrayList<CoursesDTO>();
				studentThisAccount=studentFacade.getStudentByPersonMail(mail);
				List<CoursesDTO> allStudentCourses = facadeStudendCourses.getCoursesOfStudent(studentThisAccount.getId());
				
				 if(allStudentCourses!=null) {
					 for(int i=0;i<allStudentCourses.size();i++) {
						 if(allCoursesthisPeriod!=null) {
							 for(int j=0;j<allCoursesthisPeriod.size();j++) {
								 if(allCoursesthisPeriod.get(j).getId()==allStudentCourses.get(i).getId()) {
									 allStudentCoursesthisPeriod.add(allCoursesthisPeriod.get(j));
								 }
							 }
						 }
					 }

					 if(allStudentCoursesthisPeriod.size()>0) {
						 selectedCourse =new Courses();
							selectedCourse.setId(allStudentCoursesthisPeriod.get(0).getId());
							selectedCourse.setCourseCoordinator(allStudentCoursesthisPeriod.get(0).getCourseCoordinator());
							selectedCourse.setName(allStudentCoursesthisPeriod.get(0).getName());
							selectTheIstructorForThisCourse();
					 }
				 }
				 
				 
				
			}
			else
			{
				//Another
				aStudentAccount = false;
				dootherProcendure(mail);
				
			}
			
		}
		
	}


	public void selectTheIstructorForThisCourse() {

		selectedInstructor =new Employee();
	allInstructorForThisCourse = 	courseInsFacade.getAllInstructorsByCourseId(selectedCourse.getId());
	allTAsForThisCourse = courseInsFacade.getAllTAsByCourseId(selectedCourse.getId());
	
	}
	
	public void fillQuestionMidterm() {
		// TODO Auto-generated method stub
		questionsInCateg = new ArrayList<InstructorSurveyCategory>();
		all_Categories = instructor_all_survey_quesFacade.getNumberOfGategoriesByYearAndSemestarAndMidtermOrFinal(yearSelected, semesterSelected, instructor_all_survey_ques.MODE_MIDTERM);
		for(int i=0;i<all_Categories.size();i++) {
			List<instructor_all_survey_ques> questionsOnly_Categ = instructor_all_survey_quesFacade.getAllByYearAndSemestarAndCategoryAndMidtermOrFinal(yearSelected, semesterSelected, i, instructor_all_survey_ques.MODE_MIDTERM);
			questionsInCateg.add(new InstructorSurveyCategory(studentThisAccount.getId(), selectedCourse.getId(), selectedInstructor.getId(), instructor_all_survey_ansFacade,questionsOnly_Categ, instructor_all_survey_ques_chooseFacade));
		}
	}

	
	public void fillQuestionFinal() {
		// TODO Auto-generated method stub
		questionsInCateg = new ArrayList<InstructorSurveyCategory>();
		all_Categories = instructor_all_survey_quesFacade.getNumberOfGategoriesByYearAndSemestarAndMidtermOrFinal(yearSelected, semesterSelected, instructor_all_survey_ques.MODE_FINAL);
		for(int i=0;i<all_Categories.size();i++) {
			List<instructor_all_survey_ques> questionsOnly_Categ = instructor_all_survey_quesFacade.getAllByYearAndSemestarAndCategoryAndMidtermOrFinal(yearSelected, semesterSelected, i, instructor_all_survey_ques.MODE_FINAL);
			questionsInCateg.add(new InstructorSurveyCategory(studentThisAccount.getId(), selectedCourse.getId(), selectedInstructor.getId(), instructor_all_survey_ansFacade,questionsOnly_Categ, instructor_all_survey_ques_chooseFacade));
		}
	}


	
	public void submitResult() {
		for(int i=0;i<questionsInCateg.size();i++) {
			for(int j=0;j<questionsInCateg.get(i).getQuestionWithChooses_Categ().size();j++) {
				questionsInCateg.get(i).getQuestionWithChooses_Categ().get(j).answer.setDate(Calendar.getInstance());
				if(questionsInCateg.get(i).getQuestionWithChooses_Categ().get(j).question.getType().intValue()==instructor_all_survey_ques.TYPE_SELECT.intValue()) {
					StringBuffer sb = new StringBuffer();
				      for(int ii = 0; ii < questionsInCateg.get(i).getQuestionWithChooses_Categ().get(j).getManyAns().length-1; ii++) {
				         sb.append(questionsInCateg.get(i).getQuestionWithChooses_Categ().get(j).getManyAns()[ii]+",");
				      }
				      if(questionsInCateg.get(i).getQuestionWithChooses_Categ().get(j).getManyAns().length>0) {
				      sb.append(questionsInCateg.get(i).getQuestionWithChooses_Categ().get(j).getManyAns()[questionsInCateg.get(i).getQuestionWithChooses_Categ().get(j).getManyAns().length-1]);
				      }
				      String str = sb.toString();
					questionsInCateg.get(i).getQuestionWithChooses_Categ().get(j).answer.setData(str);
					instructor_all_survey_ansFacade.addinstructor_all_survey_ans(questionsInCateg.get(i).getQuestionWithChooses_Categ().get(j).answer);
				}else {
					instructor_all_survey_ansFacade.addinstructor_all_survey_ans(questionsInCateg.get(i).getQuestionWithChooses_Categ().get(j).answer);
				}
			}
		}


		 JavaScriptMessagesHandler.RegisterNotificationMessage(null, " Your Survey have been saved Successfully");
	}
	
	
	private void dootherProcendure(String mail) {
		// TODO Auto-generated method stub
		//All Accredition
		if(mail.toLowerCase().equals(Constants.ACCREDITION_ENG_DEP.toLowerCase()))
		{
			allInstructorListOfAnswers = instructor_all_survey_ansFacade.getAllForAllInstructorForYearAndSemester(semesterSelected, yearSelected);
			
		}else if(mail.toLowerCase().equals(Constants.ACCREDITION_SCI_DEP.toLowerCase()))
		{
			allInstructorListOfAnswers = instructor_all_survey_ansFacade.getAllForAllInstructorForYearAndSemester(semesterSelected, yearSelected);
			
		}else if(mail.toLowerCase().equals(Constants.TeachingEffectiveness_DEP.toLowerCase()))
		{
			allInstructorListOfAnswers = instructor_all_survey_ansFacade.getAllForAllInstructorForYearAndSemester(semesterSelected, yearSelected);
			
		}else {
			InstructorDTO inst = getInsDataFacade.getInsByPersonMail(mail);
			selectedInstructor = new Employee();
			selectedInstructor.setMail(mail);
			selectedInstructor.setId(inst.getId());
			selectedInstructor.setName(inst.getName());
			List<instructor_all_survey_ans> buffer = instructor_all_survey_ansFacade.getAllByInstructorForYearAndSemester(semesterSelected, yearSelected,inst.getId());
			allInstructorListOfAnswers = new ArrayList<instructor_all_survey_ans>();
			
			if(buffer!=null && buffer.size()>0) {
			allInstructorListOfAnswers.add(buffer.get(0));
			//Loop over Courses for this instructor
			allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor = instructor_all_survey_ansFacade.getAllByInstructorForYearAndSemesterGroupbyCourseId(inst.getId(),yearSelected,semesterSelected);
			selectedCourse =new Courses();
			selectedCourse.setId(allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor.get(0).getCourseId().getId());
			selectedCourse.setCourseCoordinator(selectedInstructor);
			selectedCourse.setName(allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor.get(0).getCourseId().getName());
			selectTheCourseResults();
			
			}
		}
	}

	
public void selectTheCourseResults() {
		

		resultsPersonPercntageClo=new ArrayList<cloResult>();
		
		

		System.out.println(selectedCourse.getId()+" , "+selectedInstructor.getId()+" , "+yearSelected+" , "+semesterSelected);
		listOfCourseAnswers=instructor_all_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemester(selectedCourse.getId(),selectedInstructor.getId(),yearSelected, semesterSelected);
		
		System.out.println(listOfCourseAnswers.size());
		
		if(listOfCourseAnswers!=null) {

			allquestionThisYearAndSemester = instructor_all_survey_quesFacade.getAllByYearAndSemestarAndMidtermOrFinalAndType(yearSelected, semesterSelected, instructor_all_survey_ques.MODE_MIDTERM, instructor_all_survey_ques.TYPE_CHOOSE);
//			allquestionThisYearAndSemester = instructor_all_survey_quesFacade.getAllByYearAndSemestarAndMidtermOrFinalAndType(yearSelected, semesterSelected, instructor_all_survey_ques.MODE_MIDTERM, instructor_all_survey_ques.TYPE_CHOOSE);
		

			System.out.println(allquestionThisYearAndSemester.size());
			//Construct ALL question cloResults
		for(int i=0;i<allquestionThisYearAndSemester.size();i++) {

			resultsPersonPercntageClo.add(new cloResult(i+1));
		}
		
		for(int i=0;i<listOfCourseAnswers.size();i++) {
			if(listOfCourseAnswers.get(i).getAns()!=null) {
				Integer res =listOfCourseAnswers.get(i).getAns();
				cloResult clo = null;
				for(int j=0;j<allquestionThisYearAndSemester.size();j++) {

					if(allquestionThisYearAndSemester.get(j).getId().equals(listOfCourseAnswers.get(i).getQuesId().getId())) {

						
						
						clo=resultsPersonPercntageClo.get(j);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						clo=resultsPersonPercntageClo.get(j);
						resultsPersonPercntageClo.set(j,clo);
						
						
					}
					
				}
				
			
				
				
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
	


public void getListOfAllCoursesThreshold() {
	allquestionThisYearAndSemester = instructor_all_survey_quesFacade.getAllByYearAndSemestar(yearSelected, semesterSelected);
	
	List<cloThreshold> allCoursesThresoldResults=new ArrayList<cloThreshold>();
	//Loop over instructor
	for(int i=0;i<allInstructorListOfAnswers.size();i++) {
		//Loop over Courses for this instructor
		List<instructor_all_survey_ans> allcoursesForThisInstructor_thisYear_thisSemester = instructor_all_survey_ansFacade.getAllByInstructorForYearAndSemesterGroupbyCourseId(allInstructorListOfAnswers.get(i).getInstructorId().getId(),yearSelected,semesterSelected);
		for(int j=0;j<allcoursesForThisInstructor_thisYear_thisSemester.size();j++) {
		instructor_all_survey_ques courseCLO = instructor_all_survey_quesFacade.getById(allInstructorListOfAnswers.get(i).getQuesId().getId());
		listOfCourseAnswers=instructor_all_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemester(allcoursesForThisInstructor_thisYear_thisSemester.get(j).getCourseId().getId(), allInstructorListOfAnswers.get(i).getInstructorId().getId(), yearSelected, semesterSelected);


		cloThreshold course_threshold=new cloThreshold(listOfCourseAnswers, courseCLO,allquestionThisYearAndSemester.size(),allquestionThisYearAndSemester);
		allCoursesThresoldResults.add(course_threshold);
		}
	}
	
	if(allCoursesThresoldResults.size()>0) {
		generateFile(allCoursesThresoldResults,allquestionThisYearAndSemester);
	}else {
		 JavaScriptMessagesHandler.RegisterNotificationMessage(null, " You don't have any survey");
	}
}

public void generateFile(List<cloThreshold> allCoursesThresoldResults,List<instructor_all_survey_ques> allquestionThisYearAndSemester){
	 HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet();
	    
	    ReportFileGeneration reportFileGeneration=new ReportFileGeneration(allCoursesThresoldResults,workbook, sheet);
	    
	    reportFileGeneration.generateReport(allquestionThisYearAndSemester);

	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseContentType("application/vnd.ms-excel");
	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Survey_results_statistics-All.xls\"");

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



public void generateFileOfComments(){
	if(selectedCourse.getId()!=null && selectedInstructor.getId()!=null) {
	List<instructor_all_survey_ans> allanswersThisYearAndSemesterofinsPositive = instructor_all_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemesterAndCategory(selectedCourse.getId(), selectedInstructor.getId(),yearSelected,semesterSelected,6);
	List<instructor_all_survey_ans> allanswersThisYearAndSemesterofinsNegative = instructor_all_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemesterAndCategory(selectedCourse.getId(), selectedInstructor.getId(),yearSelected,semesterSelected,7);
	List<instructor_all_survey_ans> allanswersThisYearAndSemesterofTAs = instructor_all_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemesterAndCategory(selectedCourse.getId(), selectedInstructor.getId(),yearSelected,semesterSelected,8);
	if(allanswersThisYearAndSemesterofinsPositive!=null || allanswersThisYearAndSemesterofinsNegative!=null || allanswersThisYearAndSemesterofTAs!=null) { 
	HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet();
	    
	    ReportFileGenerationComments reportFileGeneration=new ReportFileGenerationComments(allanswersThisYearAndSemesterofinsPositive,allanswersThisYearAndSemesterofinsNegative,allanswersThisYearAndSemesterofTAs ,workbook, sheet);
	    
	    reportFileGeneration.generateReport();

	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseContentType("application/vnd.ms-excel");
	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Comments-All.xls\"");

	    try {
			workbook.write(externalContext.getResponseOutputStream());
			System.out.println("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}
	    facesContext.responseComplete();
	    
	}else {
		 JavaScriptMessagesHandler.RegisterNotificationMessage(null, " You don't have any survey");
	}
	}else {
		JavaScriptMessagesHandler.RegisterNotificationMessage(null, " You don't have any survey");
	}
}




	public IStudentCourseFacade getFacadeStudendCourses() {
		return facadeStudendCourses;
	}


	public void setFacadeStudendCourses(IStudentCourseFacade facadeStudendCourses) {
		this.facadeStudendCourses = facadeStudendCourses;
	}


	public IFormsStatusFacade getFacadeSettings() {
		return facadeSettings;
	}


	public void setFacadeSettings(IFormsStatusFacade facadeSettings) {
		this.facadeSettings = facadeSettings;
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


	public ICourseInstructorFacade getCourseInsFacade() {
		return courseInsFacade;
	}


	public void setCourseInsFacade(ICourseInstructorFacade courseInsFacade) {
		this.courseInsFacade = courseInsFacade;
	}


	public Iinstructor_all_survey_quesAppService getinstructor_all_survey_quesFacade() {
		return instructor_all_survey_quesFacade;
	}


	public void setinstructor_all_survey_quesFacade(Iinstructor_all_survey_quesAppService instructor_all_survey_quesFacade) {
		this.instructor_all_survey_quesFacade = instructor_all_survey_quesFacade;
	}


	public Iinstructor_all_survey_ansAppService getinstructor_all_survey_ansFacade() {
		return instructor_all_survey_ansFacade;
	}


	public void setinstructor_all_survey_ansFacade(Iinstructor_all_survey_ansAppService instructor_all_survey_ansFacade) {
		this.instructor_all_survey_ansFacade = instructor_all_survey_ansFacade;
	}


	public Student getStudentThisAccount() {
		return studentThisAccount;
	}


	public void setStudentThisAccount(Student studentThisAccount) {
		this.studentThisAccount = studentThisAccount;
	}


	public boolean isaStudentAccount() {
		return aStudentAccount;
	}


	public void setaStudentAccount(boolean aStudentAccount) {
		this.aStudentAccount = aStudentAccount;
	}


	public ICourseEvalAnswersFacade getCoursesFacade() {
		return coursesFacade;
	}


	public void setCoursesFacade(ICourseEvalAnswersFacade coursesFacade) {
		this.coursesFacade = coursesFacade;
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


	public List<CoursesDTO> getAllStudentCoursesthisPeriod() {
		return allStudentCoursesthisPeriod;
	}


	public void setAllStudentCoursesthisPeriod(List<CoursesDTO> allStudentCoursesthisPeriod) {
		this.allStudentCoursesthisPeriod = allStudentCoursesthisPeriod;
	}


	public List<CoursesDTO> getAllCoursesthisPeriod() {
		return allCoursesthisPeriod;
	}


	public void setAllCoursesthisPeriod(List<CoursesDTO> allCoursesthisPeriod) {
		this.allCoursesthisPeriod = allCoursesthisPeriod;
	}


	


	

	public Iinstructor_all_survey_ques_chooseAppService getInstructor_all_survey_ques_chooseFacade() {
		return instructor_all_survey_ques_chooseFacade;
	}


	public void setInstructor_all_survey_ques_chooseFacade(
			Iinstructor_all_survey_ques_chooseAppService instructor_all_survey_ques_chooseFacade) {
		this.instructor_all_survey_ques_chooseFacade = instructor_all_survey_ques_chooseFacade;
	}


	public List<InstructorSurveyCategory> getQuestionsInCateg() {
		return questionsInCateg;
	}


	public void setQuestionsInCateg(List<InstructorSurveyCategory> questionsInCateg) {
		this.questionsInCateg = questionsInCateg;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	


	public Courses getSelectedCourse() {
		return selectedCourse;
	}


	public void setSelectedCourse(Courses selectedCourse) {
		this.selectedCourse = selectedCourse;
	}


	public Employee getSelectedInstructor() {
		return selectedInstructor;
	}


	public void setSelectedInstructor(Employee selectedInstructor) {
		this.selectedInstructor = selectedInstructor;
	}


	public List<InstructorDTO> getAllInstructorForThisCourse() {
		return allInstructorForThisCourse;
	}


	public void setAllInstructorForThisCourse(List<InstructorDTO> allInstructorForThisCourse) {
		this.allInstructorForThisCourse = allInstructorForThisCourse;
	}


	

	public List<InstructorDTO> getAllTAsForThisCourse() {
		return allTAsForThisCourse;
	}


	public void setAllTAsForThisCourse(List<InstructorDTO> allTAsForThisCourse) {
		this.allTAsForThisCourse = allTAsForThisCourse;
	}


	

	public List<instructor_all_survey_ans> getAllInstructorListOfAnswers() {
		return allInstructorListOfAnswers;
	}


	public void setAllInstructorListOfAnswers(List<instructor_all_survey_ans> allInstructorListOfAnswers) {
		this.allInstructorListOfAnswers = allInstructorListOfAnswers;
	}


	public List<cloResult> getResultsPersonPercntageClo() {
		return resultsPersonPercntageClo;
	}


	public void setResultsPersonPercntageClo(List<cloResult> resultsPersonPercntageClo) {
		this.resultsPersonPercntageClo = resultsPersonPercntageClo;
	}


	public List<instructor_all_survey_ans> getListOfCourseAnswers() {
		return listOfCourseAnswers;
	}


	public void setListOfCourseAnswers(List<instructor_all_survey_ans> listOfCourseAnswers) {
		this.listOfCourseAnswers = listOfCourseAnswers;
	}


	public List<instructor_all_survey_ques> getAllquestionThisYearAndSemester() {
		return allquestionThisYearAndSemester;
	}


	public void setAllquestionThisYearAndSemester(List<instructor_all_survey_ques> allquestionThisYearAndSemester) {
		this.allquestionThisYearAndSemester = allquestionThisYearAndSemester;
	}


	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}


	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}


	


	public List<instructor_all_survey_ques> getAll_Categories() {
		return all_Categories;
	}


	public void setAll_Categories(List<instructor_all_survey_ques> all_Categories) {
		this.all_Categories = all_Categories;
	}


	public List<instructor_all_survey_ans> getAllcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor() {
		return allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor;
	}


	public void setAllcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor(
			List<instructor_all_survey_ans> allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor) {
		this.allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor = allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor;
	}


	public IFormsStatusFacade getFormStatus() {
		return formStatus;
	}


	public void setFormStatus(IFormsStatusFacade formStatus) {
		this.formStatus = formStatus;
	}


	public Iinstructor_all_survey_quesAppService getInstructor_all_survey_quesFacade() {
		return instructor_all_survey_quesFacade;
	}


	public void setInstructor_all_survey_quesFacade(
			Iinstructor_all_survey_quesAppService instructor_all_survey_quesFacade) {
		this.instructor_all_survey_quesFacade = instructor_all_survey_quesFacade;
	}


	public Iinstructor_all_survey_ansAppService getInstructor_all_survey_ansFacade() {
		return instructor_all_survey_ansFacade;
	}


	public void setInstructor_all_survey_ansFacade(Iinstructor_all_survey_ansAppService instructor_all_survey_ansFacade) {
		this.instructor_all_survey_ansFacade = instructor_all_survey_ansFacade;
	}


	public boolean isShowNowForInstructorsFinal() {
		return showNowForInstructorsFinal;
	}


	public void setShowNowForInstructorsFinal(boolean showNowForInstructorsFinal) {
		this.showNowForInstructorsFinal = showNowForInstructorsFinal;
	}


	public boolean isShowNowForInstructorsMidterm() {
		return showNowForInstructorsMidterm;
	}


	public void setShowNowForInstructorsMidterm(boolean showNowForInstructorsMidterm) {
		this.showNowForInstructorsMidterm = showNowForInstructorsMidterm;
	}

	
	
	
	
	
}
