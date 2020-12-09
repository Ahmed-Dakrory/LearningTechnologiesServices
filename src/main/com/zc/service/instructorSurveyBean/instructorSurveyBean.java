package main.com.zc.service.instructorSurveyBean;

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

import main.com.zc.service.instructorSurveyBean.ReportFileGeneration;
import main.com.zc.service.instructorSurveyBean.cloThreshold;
import main.com.zc.service.instructorSurveyBean.cloResult;
import main.com.zc.service.instructor_survey_ans.Iinstructor_survey_ansAppService;
import main.com.zc.service.instructor_survey_ans.instructor_survey_ans;
import main.com.zc.service.instructor_survey_ques.Iinstructor_survey_quesAppService;
import main.com.zc.service.instructor_survey_ques.instructor_survey_ques;
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

@ManagedBean(name = "instructorSurveyBean")
@ViewScoped
public class instructorSurveyBean implements Serializable{
	
	

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
	
	@ManagedProperty("#{instructor_survey_quesFacadeImpl}")
	private Iinstructor_survey_quesAppService instructor_survey_quesFacade;
	
	
	@ManagedProperty("#{instructor_survey_ansFacadeImpl}")
	private Iinstructor_survey_ansAppService instructor_survey_ansFacade;
	
	

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

	List<instructor_survey_ques> ques_Categ0;
	instructor_survey_ans ans_Categ0[];
	List<instructor_survey_ques> ques_Categ1;
	instructor_survey_ans ans_Categ1[];
	List<instructor_survey_ques> ques_Categ2;
	instructor_survey_ans ans_Categ2[];
	List<instructor_survey_ques> ques_Categ3;
	instructor_survey_ans ans_Categ3[];
	List<instructor_survey_ques> ques_Categ4;
	instructor_survey_ans ans_Categ4[];
	List<instructor_survey_ques> ques_Categ5;
	instructor_survey_ans ans_Categ5[];
	List<instructor_survey_ques> comment1Ques;
	instructor_survey_ans comment1ans;
	List<instructor_survey_ques> comment2Ques;
	instructor_survey_ans comment2ans;
	List<instructor_survey_ques> comment3Ques;
	instructor_survey_ans comment3ans;
	
	
	
	List<instructor_survey_ans> allInstructorListOfAnswers;
	
	

	private List<cloResult> resultsPersonPercntageClo;

	List<instructor_survey_ans> listOfCourseAnswers;
	List<instructor_survey_ques> allquestionThisYearAndSemester;
	
	
	//Used For Only this instructor
	List<instructor_survey_ans> allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor;
	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade formStatus; 
	
	
	private boolean showNowForInstructors=false;
	@PostConstruct
	public void init() {

		selectedInstructor =new Employee();
		refresh();
		
		
	}


	public void refresh() {
		
		/**
		 * if the setting equal phase 3 then show all results for instructors
		 */
		showNowForInstructors = (formStatus.getById(22).getStatus().getValue()==3);
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

					 if(allStudentCourses.size()>0) {
						 selectedCourse =new Courses();
							selectedCourse.setId(allStudentCourses.get(0).getId());
							selectedCourse.setCourseCoordinator(allStudentCourses.get(0).getCourseCoordinator());
							selectedCourse.setName(allStudentCourses.get(0).getName());
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
	
	public void fillQuestion() {
		// TODO Auto-generated method stub
		ques_Categ0 = instructor_survey_quesFacade.getAllByYearAndSemestarAndCategory(yearSelected, semesterSelected, 0);
		ques_Categ1 = instructor_survey_quesFacade.getAllByYearAndSemestarAndCategory(yearSelected, semesterSelected, 1);
		ques_Categ2 = instructor_survey_quesFacade.getAllByYearAndSemestarAndCategory(yearSelected, semesterSelected, 2);
		ques_Categ3 = instructor_survey_quesFacade.getAllByYearAndSemestarAndCategory(yearSelected, semesterSelected, 3);
		ques_Categ4 = instructor_survey_quesFacade.getAllByYearAndSemestarAndCategory(yearSelected, semesterSelected, 4);
		ques_Categ5 = instructor_survey_quesFacade.getAllByYearAndSemestarAndCategory(yearSelected, semesterSelected, 5);
		comment1Ques = instructor_survey_quesFacade.getAllByYearAndSemestarAndCategory(yearSelected, semesterSelected,6);
		comment2Ques = instructor_survey_quesFacade.getAllByYearAndSemestarAndCategory(yearSelected, semesterSelected,7);
		comment3Ques = instructor_survey_quesFacade.getAllByYearAndSemestarAndCategory(yearSelected, semesterSelected,8);
		
		
		ans_Categ0 =new instructor_survey_ans[ques_Categ0.size()];
		for(int i=0;i<ans_Categ0.length;i++) {
			ans_Categ0[i]=new instructor_survey_ans();
			ans_Categ0[i].setCourseId(selectedCourse);
			ans_Categ0[i].setInstructorId(selectedInstructor);
			ans_Categ0[i].setStudentId(studentThisAccount);
			instructor_survey_ans answer = instructor_survey_ansFacade.getAllByCourseAndInstructorAndStudentAndQuestion(selectedCourse.getId(), selectedInstructor.getId(), studentThisAccount.getId(), ques_Categ0.get(i).getId());
			if(answer!=null) {
				ans_Categ0[i].setId(answer.getId());
				ans_Categ0[i].setAns(answer.getAns());
			}
			
		}
		ans_Categ1 =new instructor_survey_ans[ques_Categ1.size()];
		for(int i=0;i<ans_Categ1.length;i++) {
			ans_Categ1[i]=new instructor_survey_ans();
			ans_Categ1[i].setCourseId(selectedCourse);
			ans_Categ1[i].setInstructorId(selectedInstructor);
			ans_Categ1[i].setStudentId(studentThisAccount);
			instructor_survey_ans answer = instructor_survey_ansFacade.getAllByCourseAndInstructorAndStudentAndQuestion(selectedCourse.getId(), selectedInstructor.getId(), studentThisAccount.getId(), ques_Categ1.get(i).getId());
			if(answer!=null) {
				ans_Categ1[i].setId(answer.getId());
				ans_Categ1[i].setAns(answer.getAns());
			}
		}
		ans_Categ2 =new instructor_survey_ans[ques_Categ2.size()];
		for(int i=0;i<ans_Categ2.length;i++) {
			ans_Categ2[i]=new instructor_survey_ans();
			ans_Categ2[i].setCourseId(selectedCourse);
			ans_Categ2[i].setInstructorId(selectedInstructor);
			ans_Categ2[i].setStudentId(studentThisAccount);
			instructor_survey_ans answer = instructor_survey_ansFacade.getAllByCourseAndInstructorAndStudentAndQuestion(selectedCourse.getId(), selectedInstructor.getId(), studentThisAccount.getId(), ques_Categ2.get(i).getId());
			if(answer!=null) {
				ans_Categ2[i].setId(answer.getId());
				ans_Categ2[i].setAns(answer.getAns());
			}
		}
		ans_Categ3 =new instructor_survey_ans[ques_Categ3.size()];
		for(int i=0;i<ans_Categ3.length;i++) {
			ans_Categ3[i]=new instructor_survey_ans();
			ans_Categ3[i].setCourseId(selectedCourse);
			ans_Categ3[i].setInstructorId(selectedInstructor);
			ans_Categ3[i].setStudentId(studentThisAccount);
			instructor_survey_ans answer = instructor_survey_ansFacade.getAllByCourseAndInstructorAndStudentAndQuestion(selectedCourse.getId(), selectedInstructor.getId(), studentThisAccount.getId(), ques_Categ3.get(i).getId());
			if(answer!=null) {
				ans_Categ3[i].setId(answer.getId());
				ans_Categ3[i].setAns(answer.getAns());
			}
		}
		ans_Categ4 =new instructor_survey_ans[ques_Categ4.size()];
		for(int i=0;i<ans_Categ4.length;i++) {
			ans_Categ4[i]=new instructor_survey_ans();
			ans_Categ4[i].setCourseId(selectedCourse);
			ans_Categ4[i].setInstructorId(selectedInstructor);
			ans_Categ4[i].setStudentId(studentThisAccount);
			instructor_survey_ans answer = instructor_survey_ansFacade.getAllByCourseAndInstructorAndStudentAndQuestion(selectedCourse.getId(), selectedInstructor.getId(), studentThisAccount.getId(), ques_Categ4.get(i).getId());
			if(answer!=null) {
				ans_Categ4[i].setId(answer.getId());
				ans_Categ4[i].setAns(answer.getAns());
			}
		}
		ans_Categ5 =new instructor_survey_ans[ques_Categ5.size()];
		for(int i=0;i<ans_Categ5.length;i++) {
			ans_Categ5[i]=new instructor_survey_ans();
			ans_Categ5[i].setCourseId(selectedCourse);
			ans_Categ5[i].setInstructorId(selectedInstructor);
			ans_Categ5[i].setStudentId(studentThisAccount);
			instructor_survey_ans answer = instructor_survey_ansFacade.getAllByCourseAndInstructorAndStudentAndQuestion(selectedCourse.getId(), selectedInstructor.getId(), studentThisAccount.getId(), ques_Categ5.get(i).getId());
			if(answer!=null) {
				ans_Categ5[i].setId(answer.getId());
				ans_Categ5[i].setAns(answer.getAns());
			}
		}
		comment1ans =new instructor_survey_ans();
		comment1ans.setCourseId(selectedCourse);
		comment1ans.setInstructorId(selectedInstructor);
		comment1ans.setStudentId(studentThisAccount);
		instructor_survey_ans answer = instructor_survey_ansFacade.getAllByCourseAndInstructorAndStudentAndQuestion(selectedCourse.getId(), selectedInstructor.getId(), studentThisAccount.getId(), comment1Ques.get(0).getId());
		if(answer!=null) {
			if(answer.getComment()!=null) {
				comment1ans.setId(answer.getId());
				comment1ans.setComment(answer.getComment());
			}
		}
		
		
		comment2ans =new instructor_survey_ans();
		comment2ans.setCourseId(selectedCourse);
		comment2ans.setInstructorId(selectedInstructor);
		comment2ans.setStudentId(studentThisAccount);
		instructor_survey_ans answer2 = instructor_survey_ansFacade.getAllByCourseAndInstructorAndStudentAndQuestion(selectedCourse.getId(), selectedInstructor.getId(), studentThisAccount.getId(), comment2Ques.get(0).getId());
		if(answer2!=null) {
			if(answer2.getComment()!=null) {
				comment2ans.setId(answer2.getId());
			comment2ans.setComment(answer2.getComment());
			}
		}
		
		
		comment3ans =new instructor_survey_ans();
		comment3ans.setCourseId(selectedCourse);
		comment3ans.setInstructorId(selectedInstructor);
		comment3ans.setStudentId(studentThisAccount);
		instructor_survey_ans answer3 = instructor_survey_ansFacade.getAllByCourseAndInstructorAndStudentAndQuestion(selectedCourse.getId(), selectedInstructor.getId(), studentThisAccount.getId(), comment3Ques.get(0).getId());
		if(answer3!=null) {
			if(answer3.getComment()!=null) {
				comment3ans.setId(answer3.getId());
			comment3ans.setComment(answer3.getComment());
			}
		}
		
	}


	public void show() {
		System.out.println("ShowOk");

		System.out.println(studentThisAccount.getId());
		System.out.println(selectedCourse.getId());
		System.out.println(selectedInstructor.getId());
	}
	public void submitResult() {
		for(int i=0;i<ans_Categ0.length;i++) {
			ans_Categ0[i].setDate(Calendar.getInstance());
			ans_Categ0[i].setQuesId(ques_Categ0.get(i));
			if(ans_Categ0[i].getAns()!=null) {
				instructor_survey_ansFacade.addinstructor_survey_ans(ans_Categ0[i]);
				}
		}
		
		
		for(int i=0;i<ans_Categ1.length;i++) {
			ans_Categ1[i].setDate(Calendar.getInstance());
			ans_Categ1[i].setQuesId(ques_Categ1.get(i));
			if(ans_Categ1[i].getAns()!=null) {
				instructor_survey_ansFacade.addinstructor_survey_ans(ans_Categ1[i]);
				}
		}
		
		for(int i=0;i<ans_Categ2.length;i++) {
			ans_Categ2[i].setDate(Calendar.getInstance());
			ans_Categ2[i].setQuesId(ques_Categ2.get(i));
			if(ans_Categ2[i].getAns()!=null) {
				instructor_survey_ansFacade.addinstructor_survey_ans(ans_Categ2[i]);
				}
		}
		
		
		for(int i=0;i<ans_Categ3.length;i++) {
			ans_Categ3[i].setDate(Calendar.getInstance());
			ans_Categ3[i].setQuesId(ques_Categ3.get(i));
			if(ans_Categ3[i].getAns()!=null) {
				instructor_survey_ansFacade.addinstructor_survey_ans(ans_Categ3[i]);
				}
		}
		
		
		for(int i=0;i<ans_Categ4.length;i++) {
			ans_Categ4[i].setDate(Calendar.getInstance());
			ans_Categ4[i].setQuesId(ques_Categ4.get(i));
			if(ans_Categ4[i].getAns()!=null) {
				instructor_survey_ansFacade.addinstructor_survey_ans(ans_Categ4[i]);
				}
		}
		
		
		for(int i=0;i<ans_Categ5.length;i++) {
			ans_Categ5[i].setDate(Calendar.getInstance());
			ans_Categ5[i].setQuesId(ques_Categ5.get(i));
			if(ans_Categ5[i].getAns()!=null) {
			instructor_survey_ansFacade.addinstructor_survey_ans(ans_Categ5[i]);
			}
		}
		
		
		comment1ans.setDate(Calendar.getInstance());
		comment1ans.setQuesId(comment1Ques.get(0));
		if(!comment1ans.getComment().equalsIgnoreCase("")) {
			instructor_survey_ansFacade.addinstructor_survey_ans(comment1ans);
			}
		
		
		comment2ans.setDate(Calendar.getInstance());
		comment2ans.setQuesId(comment2Ques.get(0));
		if(!comment2ans.getComment().equalsIgnoreCase("")) {
			instructor_survey_ansFacade.addinstructor_survey_ans(comment2ans);
			}
		
		comment3ans.setDate(Calendar.getInstance());
		comment3ans.setQuesId(comment3Ques.get(0));
		if(!comment3ans.getComment().equalsIgnoreCase("")) {
			instructor_survey_ansFacade.addinstructor_survey_ans(comment3ans);
			}

		 JavaScriptMessagesHandler.RegisterNotificationMessage(null, " Your Survey have been saved Successfully");
	}
	
	
	private void dootherProcendure(String mail) {
		// TODO Auto-generated method stub
		//All Accredition
		if(mail.toLowerCase().equals(Constants.ACCREDITION_ENG_DEP.toLowerCase()))
		{
			allInstructorListOfAnswers = instructor_survey_ansFacade.getAllForAllInstructorForYearAndSemester(semesterSelected, yearSelected);
			
		}else if(mail.toLowerCase().equals(Constants.ACCREDITION_SCI_DEP.toLowerCase()))
		{
			allInstructorListOfAnswers = instructor_survey_ansFacade.getAllForAllInstructorForYearAndSemester(semesterSelected, yearSelected);
			
		}else if(mail.toLowerCase().equals(Constants.TeachingEffectiveness_DEP.toLowerCase()))
		{
			allInstructorListOfAnswers = instructor_survey_ansFacade.getAllForAllInstructorForYearAndSemester(semesterSelected, yearSelected);
			
		}else {
			InstructorDTO inst = getInsDataFacade.getInsByPersonMail(mail);
			selectedInstructor = new Employee();
			selectedInstructor.setMail(mail);
			selectedInstructor.setId(inst.getId());
			selectedInstructor.setName(inst.getName());
			List<instructor_survey_ans> buffer = instructor_survey_ansFacade.getAllByInstructorForYearAndSemester(semesterSelected, yearSelected,inst.getId());
			allInstructorListOfAnswers = new ArrayList<instructor_survey_ans>();
			
			if(buffer!=null && buffer.size()>0) {
			allInstructorListOfAnswers.add(buffer.get(0));
			//Loop over Courses for this instructor
			allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor = instructor_survey_ansFacade.getAllByInstructorForYearAndSemesterGroupbyCourseId(inst.getId(),yearSelected,semesterSelected);
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
		
		
		
		listOfCourseAnswers=instructor_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemester(selectedCourse.getId(),selectedInstructor.getId(),yearSelected, semesterSelected);
		
		
		
		if(listOfCourseAnswers!=null) {
			
			allquestionThisYearAndSemester = instructor_survey_quesFacade.getAllByYearAndSemestar(yearSelected, semesterSelected);
		
			
			//Construct ALL question cloResults
		for(int i=0;i<allquestionThisYearAndSemester.size()-3;i++) {

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
	allquestionThisYearAndSemester = instructor_survey_quesFacade.getAllByYearAndSemestar(yearSelected, semesterSelected);
	
	List<cloThreshold> allCoursesThresoldResults=new ArrayList<cloThreshold>();
	//Loop over instructor
	for(int i=0;i<allInstructorListOfAnswers.size();i++) {
		//Loop over Courses for this instructor
		List<instructor_survey_ans> allcoursesForThisInstructor_thisYear_thisSemester = instructor_survey_ansFacade.getAllByInstructorForYearAndSemesterGroupbyCourseId(allInstructorListOfAnswers.get(i).getInstructorId().getId(),yearSelected,semesterSelected);
		for(int j=0;j<allcoursesForThisInstructor_thisYear_thisSemester.size();j++) {
		instructor_survey_ques courseCLO = instructor_survey_quesFacade.getById(allInstructorListOfAnswers.get(i).getQuesId().getId());
		listOfCourseAnswers=instructor_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemester(allcoursesForThisInstructor_thisYear_thisSemester.get(j).getCourseId().getId(), allInstructorListOfAnswers.get(i).getInstructorId().getId(), yearSelected, semesterSelected);


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

public void generateFile(List<cloThreshold> allCoursesThresoldResults,List<instructor_survey_ques> allquestionThisYearAndSemester){
	 HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet();
	    
	    ReportFileGeneration reportFileGeneration=new ReportFileGeneration(allCoursesThresoldResults,workbook, sheet,instructor_survey_ansFacade, yearSelected, semesterSelected);
	    
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
	List<instructor_survey_ans> allanswersThisYearAndSemesterofinsPositive = instructor_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemesterAndCategory(selectedCourse.getId(), selectedInstructor.getId(),yearSelected,semesterSelected,6);
	List<instructor_survey_ans> allanswersThisYearAndSemesterofinsNegative = instructor_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemesterAndCategory(selectedCourse.getId(), selectedInstructor.getId(),yearSelected,semesterSelected,7);
	List<instructor_survey_ans> allanswersThisYearAndSemesterofTAs = instructor_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemesterAndCategory(selectedCourse.getId(), selectedInstructor.getId(),yearSelected,semesterSelected,8);
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


	public Iinstructor_survey_quesAppService getInstructor_survey_quesFacade() {
		return instructor_survey_quesFacade;
	}


	public void setInstructor_survey_quesFacade(Iinstructor_survey_quesAppService instructor_survey_quesFacade) {
		this.instructor_survey_quesFacade = instructor_survey_quesFacade;
	}


	public Iinstructor_survey_ansAppService getInstructor_survey_ansFacade() {
		return instructor_survey_ansFacade;
	}


	public void setInstructor_survey_ansFacade(Iinstructor_survey_ansAppService instructor_survey_ansFacade) {
		this.instructor_survey_ansFacade = instructor_survey_ansFacade;
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


	public List<instructor_survey_ques> getQues_Categ0() {
		return ques_Categ0;
	}


	public void setQues_Categ0(List<instructor_survey_ques> ques_Categ0) {
		this.ques_Categ0 = ques_Categ0;
	}


	public List<instructor_survey_ques> getQues_Categ1() {
		return ques_Categ1;
	}


	public void setQues_Categ1(List<instructor_survey_ques> ques_Categ1) {
		this.ques_Categ1 = ques_Categ1;
	}


	public List<instructor_survey_ques> getQues_Categ2() {
		return ques_Categ2;
	}


	public void setQues_Categ2(List<instructor_survey_ques> ques_Categ2) {
		this.ques_Categ2 = ques_Categ2;
	}


	public List<instructor_survey_ques> getQues_Categ3() {
		return ques_Categ3;
	}


	public void setQues_Categ3(List<instructor_survey_ques> ques_Categ3) {
		this.ques_Categ3 = ques_Categ3;
	}


	public List<instructor_survey_ques> getQues_Categ4() {
		return ques_Categ4;
	}


	public void setQues_Categ4(List<instructor_survey_ques> ques_Categ4) {
		this.ques_Categ4 = ques_Categ4;
	}


	public List<instructor_survey_ques> getQues_Categ5() {
		return ques_Categ5;
	}


	public void setQues_Categ5(List<instructor_survey_ques> ques_Categ5) {
		this.ques_Categ5 = ques_Categ5;
	}


	public List<instructor_survey_ques> getComment1Ques() {
		return comment1Ques;
	}


	public void setComment1Ques(List<instructor_survey_ques> comment1Ques) {
		this.comment1Ques = comment1Ques;
	}


	public List<instructor_survey_ques> getComment2Ques() {
		return comment2Ques;
	}


	public void setComment2Ques(List<instructor_survey_ques> comment2Ques) {
		this.comment2Ques = comment2Ques;
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


	public instructor_survey_ans[] getAns_Categ0() {
		return ans_Categ0;
	}


	public void setAns_Categ0(instructor_survey_ans[] ans_Categ0) {
		this.ans_Categ0 = ans_Categ0;
	}


	public instructor_survey_ans[] getAns_Categ1() {
		return ans_Categ1;
	}


	public void setAns_Categ1(instructor_survey_ans[] ans_Categ1) {
		this.ans_Categ1 = ans_Categ1;
	}


	public instructor_survey_ans[] getAns_Categ2() {
		return ans_Categ2;
	}


	public void setAns_Categ2(instructor_survey_ans[] ans_Categ2) {
		this.ans_Categ2 = ans_Categ2;
	}


	public instructor_survey_ans[] getAns_Categ3() {
		return ans_Categ3;
	}


	public void setAns_Categ3(instructor_survey_ans[] ans_Categ3) {
		this.ans_Categ3 = ans_Categ3;
	}


	public instructor_survey_ans[] getAns_Categ4() {
		return ans_Categ4;
	}


	public void setAns_Categ4(instructor_survey_ans[] ans_Categ4) {
		this.ans_Categ4 = ans_Categ4;
	}


	public instructor_survey_ans[] getAns_Categ5() {
		return ans_Categ5;
	}


	public void setAns_Categ5(instructor_survey_ans[] ans_Categ5) {
		this.ans_Categ5 = ans_Categ5;
	}


	public instructor_survey_ans getComment1ans() {
		return comment1ans;
	}


	public void setComment1ans(instructor_survey_ans comment1ans) {
		this.comment1ans = comment1ans;
	}


	public instructor_survey_ans getComment2ans() {
		return comment2ans;
	}


	public void setComment2ans(instructor_survey_ans comment2ans) {
		this.comment2ans = comment2ans;
	}


	public List<instructor_survey_ans> getAllInstructorListOfAnswers() {
		return allInstructorListOfAnswers;
	}


	public void setAllInstructorListOfAnswers(List<instructor_survey_ans> allInstructorListOfAnswers) {
		this.allInstructorListOfAnswers = allInstructorListOfAnswers;
	}


	public List<cloResult> getResultsPersonPercntageClo() {
		return resultsPersonPercntageClo;
	}


	public void setResultsPersonPercntageClo(List<cloResult> resultsPersonPercntageClo) {
		this.resultsPersonPercntageClo = resultsPersonPercntageClo;
	}


	public List<instructor_survey_ans> getListOfCourseAnswers() {
		return listOfCourseAnswers;
	}


	public void setListOfCourseAnswers(List<instructor_survey_ans> listOfCourseAnswers) {
		this.listOfCourseAnswers = listOfCourseAnswers;
	}


	public List<instructor_survey_ques> getAllquestionThisYearAndSemester() {
		return allquestionThisYearAndSemester;
	}


	public void setAllquestionThisYearAndSemester(List<instructor_survey_ques> allquestionThisYearAndSemester) {
		this.allquestionThisYearAndSemester = allquestionThisYearAndSemester;
	}


	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}


	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}


	public List<instructor_survey_ques> getComment3Ques() {
		return comment3Ques;
	}


	public void setComment3Ques(List<instructor_survey_ques> comment3Ques) {
		this.comment3Ques = comment3Ques;
	}


	public instructor_survey_ans getComment3ans() {
		return comment3ans;
	}


	public void setComment3ans(instructor_survey_ans comment3ans) {
		this.comment3ans = comment3ans;
	}


	public List<instructor_survey_ans> getAllcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor() {
		return allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor;
	}


	public void setAllcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor(
			List<instructor_survey_ans> allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor) {
		this.allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor = allcoursesForThisInstructor_thisYear_thisSemesterOnlyOneInstructor;
	}


	public IFormsStatusFacade getFormStatus() {
		return formStatus;
	}


	public void setFormStatus(IFormsStatusFacade formStatus) {
		this.formStatus = formStatus;
	}


	public boolean isShowNowForInstructors() {
		return showNowForInstructors;
	}


	public void setShowNowForInstructors(boolean showNowForInstructors) {
		this.showNowForInstructors = showNowForInstructors;
	}


	
	
	
	
}
