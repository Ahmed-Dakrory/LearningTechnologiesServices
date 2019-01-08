package main.com.zc.shared.presentation.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.security.impl.LoginBean;
import main.com.zc.services.domain.model.heads.Heads;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.service.repository.heads.HeadsAppServiceImpl;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.FormsStatusEnum;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.shared.facade.impl.MajorsFacadeImpl;
import main.com.zc.services.presentation.survey.CourseEvalNew.bean.CourseEvaluationSubmission;
import main.com.zc.services.presentation.survey.CourseEvalNew.bean.InstructorTAEvalSubmission;
import main.com.zc.services.presentation.survey.CourseEvalNew.bean.TaToTaEvalSubmission;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.bean.FillLectureObjectiveFeedbackBean;
import main.com.zc.services.presentation.survey.midTermEvaluation.bean.MidtermEvaluationSubmission;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.services.presentation.users.facade.impl.StudentFacadeImpl;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.appService.ILoginSecurityAppService;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;
import main.com.zc.shared.presentation.facade.ILoginFacade;

/**
 * @author Omnya Alaa
 * 
 */

@SessionScoped
@ManagedBean(name = "leftNavigationMenuBean")
public class LeftNavigationMenuBean {
	

	private boolean validateLoggedIn;
	private boolean admin;
	private boolean firstYearStudent;
	private String currentMenuId;
	private boolean seeResultsOfIntendedMajor;
	private boolean studentModeDeclarationOfConcentration;
	private boolean studentModeChangeOfConcentration;
	private boolean studentModeDeclarationOfMajor;
	private boolean studentModeIntendedMajor;
	private boolean courseEvalStudent;
	private boolean midtermEvalStudent;
	private boolean lectureObjStudent;
	private boolean insTAEvalSecurity;
	private boolean booksSysMode;
	private boolean seeGraduationFormReponses;
	private boolean headOrStudent;
	//@ManagedProperty("#{CourseEvalStudentBean}")
	//private CourseEvalStudentBean courseEvalBean;
/*	@ManagedProperty("#{CourseEvaluationSubmission}")
	private CourseEvaluationSubmission courseEvalBean;
*/	/*@ManagedProperty("#{InstructorTAEvalSubmission}")
	private InstructorTAEvalSubmission taInsEvalBean;
	*//*@ManagedProperty("#{TaToTaEvalSubmission}")
	private TaToTaEvalSubmission tatoTaEvalBean;*/
	private String moodleEmail;
	private String moodlePassword;
	/*@Autowired
	ILoginSecurityAppService loginSecAppService;*/
	@ManagedProperty("#{loginSecurityAppServiceImpl}")
	private ILoginSecurityAppService loginSecAppService;
	
	
	
	@ManagedProperty("#{loginSecurityAppServiceImpl}")
	private ILoginSecurityAppService securityLoginAppService;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;
	
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade; 
	
	
	@ManagedProperty("#{loginFacadeImpl}")
	private ILoginFacade loginFacade;
	
	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade settingsFacade; 
	
	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade formStatus; 
	
	
	@ManagedProperty("#{headsFacadeImpl}")
   	private HeadsAppServiceImpl headFacades; 
	
	@ManagedProperty("#{IMajorsFacade}")
	private MajorsFacadeImpl majorfacade;
	
	@ManagedProperty("#{IStudentFacade}")
    private StudentFacadeImpl studentFacadeImpl;
	
	
	
	
	@PostConstruct
	public void init() {
		

		currentMenuId = "Dashboard";
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			headOrStudent=false;
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				headOrStudent=true;
				return;
			
			}
			else
			{
				
				List<MajorDTO> majors=majorfacade.getAll();
				for(int i=0;i<majors.size();i++){
					MajorDTO major=majors.get(i);
					if(mail.toLowerCase().equals(major.getHeadOfMajor().getMail().toLowerCase()))
					{
						
						headOrStudent=true;
						return;
					}
				}
				/*
				 * here the heads login
				 * 2 Director of Accredition for Engineering
				 * 3 Director of Accredition for Science
				 * 4 Dean of Admission
				 * 5 Director of Admission and Registration
				 * 6 Registrar staff
				 */
				
				Heads  typeHead2 = headFacades.getByType(2);
				Heads  typeHead3 = headFacades.getByType(3);
				Heads  typeHead4 = headFacades.getByType(4);
				Heads  typeHead5 = headFacades.getByType(5);
				Heads  typeHead6 = headFacades.getByType(6);
				if(mail.toLowerCase().equals(typeHead2.getHeadPersonId().getMail().toLowerCase()))
				{
					//Engineering
					headOrStudent=true;
					return;
				}
				else if(mail.toLowerCase().equals(typeHead3.getHeadPersonId().getMail().toLowerCase()))
				{
					//Science
					headOrStudent=true;
					return;
				}
				else if(mail.toLowerCase().equals(typeHead4.getHeadPersonId().getMail().toLowerCase()))
				{
					headOrStudent=true;
					return;
				}
				else if(mail.toLowerCase().equals(typeHead5.getHeadPersonId().getMail().toLowerCase()))
				{

					headOrStudent=true;
					return;
				}
				else if(mail.toLowerCase().equals(typeHead6.getHeadPersonId().getMail().toLowerCase()))
				{

					headOrStudent=true;
					return;
				}
				else 
				{

					headOrStudent=false;
					return;
				}
				
			
			}
			
		}
		else
		{
			headOrStudent=false;
			return;
		}
		
	}



	public String renderDashboard()
	{
		currentMenuId = "Dashboard";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			return"/pages/secured/dashboard.xhtml?faces-redirect=true";
		}
		else
		{
			return"/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	
	public String renderCourseSyllabus()
	{
		currentMenuId = "Course Syllabus";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			return"/pages/secured/courseManagment/courseSyllabus.xhtml?faces-redirect=true";
		}
		else
		{
			return"/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	
	public String getHelpFile()
	{
		try 
		{
			
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			String mail = authentication.getName();
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			
			String HelpURL = "";
			
			if (!authentication.getPrincipal().equals("anonymousUser"))
			{
				if(mail.startsWith("S-") || mail.startsWith("s-"))
					HelpURL = context.getRequestContextPath()+"/resources/help/LTS Student User Guide.pdf";
				else
					HelpURL = context.getRequestContextPath()+"/resources/help/LTS Staff User Guide.pdf";
	
			}
			else
				HelpURL = context.getRequestContextPath()+"/pages/public/login.xhtml?faces-redirect=true";
			
			return HelpURL;//context.redirect(HelpURL);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "#";
	}
	
	public String renderGenFeedback() {
		currentMenuId = "Feedback Form";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
					System.out.println("You are signed in ");
					String mail = authentication.getName();
					if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
					{
				return"/pages/secured/generalFeedback/pendingFeedback.xhtml?faces-redirect=true";
					}
					else if(mail.equals("lts-admin@zewailcity.edu.eg")) // handler case
					{
				return"/pages/secured/generalFeedback/feedbackHandlerPending.xhtml?faces-redirect=true";
					}
					else //instructor case 
					{
				return"/pages/secured/generalFeedback/feedbackPendingIns.xhtml?faces-redirect=true";
					}

		} else {
			loginBean.init();
			return "/pages/public/login.xhtml?faces-redirect=true";
			
		}
		

	}

	public String renderShowFeedback()
	{
		currentMenuId = "Submitted Feedback";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			
			if(mail.toLowerCase().equals(Constants.LTS_SYSTEM_ADMIN.toLowerCase()))
			{
				return"/pages/secured/generalFeedback/showFeedbacks-Admin.xhtml?faces-redirect=true";
			}
			else
			{
				return"/pages/secured/generalFeedback/showFeedbacks.xhtml?faces-redirect=true";
			}
		}
		else 
		{
			loginBean.init();
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	
	public String renderAcademicPet() // for Academic Petition
	{
		currentMenuId = "Academic Petition";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				
				return "/pages/secured/forms/academicPetition/studentCoursePetitionPage.xhtml?faces-redirect=true";
			
			}
			else
			{
				
					
					
					if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()))
					{
						if(getInsDataFacade.isCouurseCoordinator(mail))
						{
							return "/pages/secured/forms/academicPetition/InstructorCoursePetitionPage.xhtml?faces-redirect=true";
						}
						else 
							return "/pages/secured/forms/academicPetition/deanCoursePetitionPage.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase()))
					{
						return "/pages/secured/forms/academicPetition/admissionHAcademicPet.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
					{
						return "/pages/secured/forms/academicPetition/admissionDAcademicPet.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.LTS_SYSTEM_ADMIN.toLowerCase()))
					{
						return "/pages/secured/forms/academicPetition/admissionAcademicPetAdmin.xhtml?faces-redirect=true";
					}
					else 
					{
						return "/pages/secured/forms/academicPetition/InstructorCoursePetitionPage.xhtml?faces-redirect=true";
					}
				
			
			}
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
		
	
	}

		
	public String renderSendAttendance()
	{
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(!mail.startsWith("s-")&&!mail.startsWith("S-")&&mail.toLowerCase().equals("attendance@zewailcity.edu.eg".toLowerCase())) // student case
			{
				//studentDailyAttBool=false;
				return "/pages/secured/att/dailyAttPage.xhtml?faces-redirect=true";
			
			}
			else return "";
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	public String renderStudentAttendance()
	{
		currentMenuId = "Daily Attendance";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				
				return "/pages/secured/att/studentDailyAttPage.xhtml?faces-redirect=true";
			}
			else if(mail.toLowerCase().equals("attendance@zewailcity.edu.eg".toLowerCase()))
				return "/pages/secured/att/dailyAttPage.xhtml?faces-redirect=true";
			else 
				return "";
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	public String renderAttendancePetition()
	{
		currentMenuId = "Daily Attendance Petitions";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(!mail.startsWith("s-")&&!mail.startsWith("S-")&&mail.toLowerCase().equals("attendance@zewailcity.edu.eg".toLowerCase())) // student case
			{
				//studentDailyAttBool=false;
				return "/pages/secured/att/showAttendancePetition.xhtml?faces-redirect=true";
			
			}
			else return "";
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}

	public String renderAddDropForm()
	{
		currentMenuId = "Drop / Add Form";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				
				return "/pages/secured/forms/dropAndAdd/addDropStudent.xhtml?faces-redirect=true";
			
			}
			else
			{
				
					
					
					if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()))
					{
						if(getInsDataFacade.isPA(mail))
						{
							return "/pages/secured/forms/dropAndAdd/addDropIns.xhtml?faces-redirect=true";
						}
						else 
							return "/pages/secured/forms/dropAndAdd/addDropDean.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase()))
					{
						return "/pages/secured/forms/dropAndAdd/addDropAdmissionHead.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
					{
						return "/pages/secured/forms/dropAndAdd/addDropAdmissionDept.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.LTS_SYSTEM_ADMIN.toLowerCase()))
					{
						return "/pages/secured/forms/dropAndAdd/addDropAdmin.xhtml?faces-redirect=true";
					}
					else 
					{
						return "/pages/secured/forms/dropAndAdd/addDropIns.xhtml?faces-redirect=true";
					}
			}
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	
	public String renderChangeMajor()
	{
		currentMenuId = "Change Major";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				
				return "/pages/secured/forms/changeMajor/changeMajorStudent.xhtml?faces-redirect=true";
			
			}
			else
			{
				
					
					
					if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()))
					{
						if(getInsDataFacade.isPA(mail))
						{
							return "/pages/secured/forms/changeMajor/changeMajorIns.xhtml?faces-redirect=true";
						}
						else 
							return "/pages/secured/forms/changeMajor/changeMajorDean.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase()))
					{
						return "/pages/secured/forms/changeMajor/changeMajorAdmissionHead.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
					{
						return "/pages/secured/forms/changeMajor/changeMajorAdmission.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.LTS_SYSTEM_ADMIN.toLowerCase()))
					{
						return "/pages/secured/forms/changeMajor/changeMajorAdmin.xhtml?faces-redirect=true";
					}
					else 
					{
						return "/pages/secured/forms/changeMajor/changeMajorIns.xhtml?faces-redirect=true";
					}
			}
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}

	public String renderOverLoadReq()
	{
		currentMenuId = "Overload Request";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				
				return "/pages/secured/forms/overloadRequest/overloadRequestStudent.xhtml?faces-redirect=true";
			
			}
			else
			{
				
				if(mail.toLowerCase().equals(Constants.PROVOST.toLowerCase()))
				{
					if(getInsDataFacade.isPA(mail))
					{
						return "/pages/secured/forms/overloadRequest/overloadRequestInstructor.xhtml?faces-redirect=true";
					}
					else 
						return "/pages/secured/forms/overloadRequest/overloadRequestProvost.xhtml?faces-redirect=true";
				}
					
				else if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()))
					{
						if(getInsDataFacade.isPA(mail))
						{
							return "/pages/secured/forms/overloadRequest/overloadRequestInstructor.xhtml?faces-redirect=true";
						}
						else 
							return "/pages/secured/forms/overloadRequest/overloadRequestDean.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase()))
					{
						return "/pages/secured/forms/overloadRequest/overloadRequestAdmissionHead.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
					{
						return "/pages/secured/forms/overloadRequest/overloeadRequestAdmissionD.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.LTS_SYSTEM_ADMIN.toLowerCase()))
					{
						return "/pages/secured/forms/overloadRequest/overloadRequestAdmin.xhtml?faces-redirect=true";
					}
					else 
					{
						return "/pages/secured/forms/overloadRequest/overloadRequestInstructor.xhtml?faces-redirect=true";
					}
				
			
			}
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	
	/*
	 * This for the new comfirmation course
	 */
	public String renderChangeCourseConfirmation()
	{
		currentMenuId = "Change course";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				PersonDataDTO dataOfStudent= studentDataFacade.getPersonByPersonMail(mail);
				int idStudent=dataOfStudent.getId();
				return "/pages/secured/forms/courseChangeComfirmation/formDetails.xhtml?id="+idStudent+"&faces-redirect=true";
			
			}
			else
			{
				
				List<MajorDTO> majors=majorfacade.getAll();
				for(int i=0;i<majors.size();i++){
					MajorDTO major=majors.get(i);
					if(mail.toLowerCase().equals(major.getHeadOfMajor().getMail().toLowerCase()))
					{
						
							return "/pages/secured/forms/courseChangeComfirmation/programHeadformDetails.xhtml?stateNow=0&majorId="+String.valueOf(major.getId())+"&type=-1&emailForState="+mail+"&faces-redirect=true";
					}
				}
				/*
				 * here the heads login
				 * 2 Director of Accredition for Engineering
				 * 3 Director of Accredition for Science
				 * 4 Dean of Admission
				 * 5 Director of Admission and Registration
				 * 6 Registrar staff
				 */
				
				Heads  typeHead2 = headFacades.getByType(2);
				Heads  typeHead3 = headFacades.getByType(3);
				Heads  typeHead4 = headFacades.getByType(4);
				Heads  typeHead5 = headFacades.getByType(5);
				Heads  typeHead6 = headFacades.getByType(6);
				if(mail.toLowerCase().equals(typeHead2.getHeadPersonId().getMail().toLowerCase()))
				{
					//Engineering
					return "/pages/secured/forms/courseChangeComfirmation/programHeadformDetails.xhtml?stateNow=1&majorId=-1&type=2&emailForState="+mail+"&faces-redirect=true";
				}
				else if(mail.toLowerCase().equals(typeHead3.getHeadPersonId().getMail().toLowerCase()))
				{
					//Science
					return "/pages/secured/forms/courseChangeComfirmation/programHeadformDetails.xhtml?stateNow=1&majorId=-1&type=1&emailForState="+mail+"&faces-redirect=true";
				}
				else if(mail.toLowerCase().equals(typeHead4.getHeadPersonId().getMail().toLowerCase()))
				{
					return "/pages/secured/forms/courseChangeComfirmation/programHeadformDetails.xhtml?stateNow=2&majorId=-1&type=4&emailForState="+mail+"&faces-redirect=true";
				}
				else if(mail.toLowerCase().equals(typeHead5.getHeadPersonId().getMail().toLowerCase()))
				{
						return "/pages/secured/forms/courseChangeComfirmation/programHeadformDetails.xhtml?stateNow=3&majorId=-1&type=5&emailForState="+mail+"&faces-redirect=true";
				}
				else if(mail.toLowerCase().equals(typeHead6.getHeadPersonId().getMail().toLowerCase()))
				{
						return "/pages/secured/forms/courseChangeComfirmation/programHeadformDetails.xhtml?stateNow=4&majorId=-1&type=6&emailForState="+mail+"&faces-redirect=true";
				}
				else 
				{
						return "/pages/secured/forms/courseChangeComfirmation/programHeadformDetails.xhtml?faces-redirect=true";
				}
				
			
			}
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	

	public String renderCourseRepeat()
	{
		currentMenuId = "Course Repeat Form";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				
				return "/pages/secured/forms/CourseRepeatForm/courseRepeatFormStudent.xhtml?faces-redirect=true";
			
			}
			else
			{
				
					
					
					if(mail.toLowerCase().equals(Constants.DEAN_OF_ACADEMIC.toLowerCase()))
					{
						if(getInsDataFacade.isPA(mail))
						{
							return "/pages/secured/forms/CourseRepeatForm/courseRepeatFormIns.xhtml?faces-redirect=true";
						}
						else 
							return "/pages/secured/forms/CourseRepeatForm/courseRepeatFormDean.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase()))
					{
						return "/pages/secured/forms/CourseRepeatForm/courseRepeatFormAdmissionHead.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
					{
							return "/pages/secured/forms/CourseRepeatForm/courseRepeatFormAdmissionDept.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.LTS_SYSTEM_ADMIN.toLowerCase()))
					{
						return "/pages/secured/forms/CourseRepeatForm/courseRepeatFormAdmin.xhtml?faces-redirect=true";
					}
					else 
					{
						return "/pages/secured/forms/CourseRepeatForm/courseRepeatFormIns.xhtml?faces-redirect=true";
					}
			}
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	public String renderIncompleteGrade()
	{
		currentMenuId = "Incomplete Grade Form";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				
				return "/pages/secured/forms/incompleteGrade/incompleteGradeStudent.xhtml?faces-redirect=true";
			
			}

			else
			{
				
					
					
					if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()))
					{
						if(getInsDataFacade.isPA(mail))
						{
							return "/pages/secured/forms/incompleteGrade/incompleteGradeIns.xhtml?faces-redirect=true";
						}
						else 
							return "/pages/secured/forms/incompleteGrade/incompleteGradeDean.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase()))
					{
						return "/pages/secured/forms/incompleteGrade/IncompleteAdmissionH.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
					{
							return "/pages/secured/forms/incompleteGrade/incompleteAdmissionD.xhtml?faces-redirect=true";
					}
					else if(mail.toLowerCase().equals(Constants.LTS_SYSTEM_ADMIN.toLowerCase()))
					{
						return "/pages/secured/forms/incompleteGrade/incompleteGradeAdmin.xhtml?faces-redirect=true";
					}
					else 
					{
						return "/pages/secured/forms/incompleteGrade/incompleteGradeIns.xhtml?faces-redirect=true";
					}
			}
			
			}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	public String renderIntendedMajorSurvey()
	{
	
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				currentMenuId = "Intended Major";
				if(Integer.toString(studentDataFacade.getPersonByPersonMail(mail).getFileNo()).startsWith("2014"))
				{
					return "/pages/secured/survey/intendedMajor/intendedMajor.xhtml?faces-redirect=true";
				}
			
				else 
				{
					JavaScriptMessagesHandler.RegisterErrorMessage(null,"Allowed only for first year students" );
					return "";
				}
			}
			else
			{
				
					
					
					if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()) ||
							mail.toLowerCase().equals(Constants.PROVOST.toLowerCase())  ||
							mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase())||
							mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
					{
						currentMenuId = "Planned Major Results";
							return "/pages/secured/survey/intendedMajor/intendedMajorsResults.xhtml?faces-redirect=true";
					}
					else return "/pages/public/login.xhtml?faces-redirect=true";
					
			}
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}

	public String renderDeclarationOfMajor()
	{
	
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				currentMenuId = "Declaration of Major";
				
				
					return "/pages/secured/survey/declarationOfMajor/declarationOfMajor.xhtml?faces-redirect=true";
				
			}
			else
			{
				
					
					
					if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()) ||
							mail.toLowerCase().equals(Constants.PROVOST.toLowerCase())  ||
							mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase())||
							mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
					{
						
						currentMenuId = "Declaration of Major Results";
							return "/pages/secured/survey/declarationOfMajor/declarationOfMajorResults?faces-redirect=true";
					}
					else return "/pages/public/login.xhtml?faces-redirect=true";
					
			}
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	
	public String renderMajorDecResultStudent()
	{
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
		if(authentication.getName().startsWith("S-")||authentication.getName().startsWith("s-"))
		{
			currentMenuId = "Declaration of Major";
			if(Integer.toString(studentDataFacade.getPersonByPersonMail(authentication.getName()).getFileNo()).startsWith("2014"))
			{
			
				return "/pages/secured/survey/declarationOfMajor/declarationOfMajorStudents.xhtml?faces-redirect=true";
			}
		
			else 
			{
				JavaScriptMessagesHandler.RegisterErrorMessage(null,"Allowed only for first year students" );
				return "/pages/public/login.xhtml?faces-redirect=true";
			}
		}
		else 
		{

			if(authentication.getName().toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()) ||
					authentication.getName().toLowerCase().equals(Constants.PROVOST.toLowerCase())  ||
					authentication.getName().toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase())||
					authentication.getName().toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
			{
				
				currentMenuId = "Declaration of Major Results Students";
					return "/pages/secured/survey/declarationOfMajor/declarationOfMajorStudents.xhtml?faces-redirect=true";
			}
			else return "/pages/public/login.xhtml?faces-redirect=true";
		}
		}
		else return "/pages/public/login.xhtml?faces-redirect=true";
	}
	public String renderIntendedMajorResultStudent()
	{
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{    currentMenuId = "Intended Major";
				if(Integer.toString(studentDataFacade.getPersonByPersonMail(mail).getFileNo()).startsWith("2014"))
				{
					return "/pages/secured/survey/intendedMajor/intendedMajor.xhtml?faces-redirect=true";
				}
			
				else 
				{
					JavaScriptMessagesHandler.RegisterErrorMessage(null,"Allowed only for first year students" );
					return "";
				}
			}
			else
			{
				
					
					
					if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()) ||
							mail.toLowerCase().equals(Constants.PROVOST.toLowerCase())  ||
							mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase())||
							mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
					{
						currentMenuId = "Planned Major Results";
							return "/pages/secured/survey/intendedMajor/intendedMajorStudents.xhtml?faces-redirect=true";
					}
					else return "/pages/public/login.xhtml?faces-redirect=true";
					
			}
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	public String renderCourseEval()
	{
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
		
						
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{    currentMenuId = "Course Evaluation";
			
			  ExternalContext exctxt = FacesContext.getCurrentInstance().getExternalContext();
			  CourseEvaluationSubmission courseEvalBean = (CourseEvaluationSubmission) exctxt.getSessionMap().get("CourseEvaluationSubmission");
			  
			if(courseEvalBean!=null){
			        courseEvalBean.init();
			}
					return "/pages/secured/survey/courseEval/settings.xhtml?faces-redirect=true";
				
			}
				else 
				{
					
					return "/pages/secured/survey/courseEval/settings.xhtml?faces-redirect=true";
				}
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
	public String renderMidtermEval()
	{
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
		
						
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{    currentMenuId = "Midterm Evaluation";
			
			  ExternalContext exctxt = FacesContext.getCurrentInstance().getExternalContext();
			  MidtermEvaluationSubmission midtermEvalBean = (MidtermEvaluationSubmission) exctxt.getSessionMap().get("midtermEvaluationSubmission");
			  
			if(midtermEvalBean!=null){
				midtermEvalBean.init();
			}
					return "/pages/secured/survey/midtermEvaluation/settings.xhtml?faces-redirect=true";
				
			}
			else return "";
				
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
	public String renderInsTaEval()
	{
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(!mail.startsWith("s-")&&!mail.startsWith("S-")) // student case
			{    currentMenuId = "Course Evaluation INS";
		
		           InstructorDTO ins=getInsDataFacade.getInsByPersonMail(mail);
		           if(ins.getEmpType().equals(1)){
		        		  ExternalContext exctxt = FacesContext.getCurrentInstance().getExternalContext();
		        		  InstructorTAEvalSubmission taInsEvalBean = (InstructorTAEvalSubmission) exctxt.getSessionMap().get("InstructorTAEvalSubmission");
		    		
		        		if(taInsEvalBean!=null){
		    				taInsEvalBean.init();
		    			}
		        	   return "/pages/secured/survey/instructorEval/insEvalSettings.xhtml?faces-redirect=true";
		           }
		           else if(ins.getEmpType().equals(2)){
		        	   ExternalContext exctxt = FacesContext.getCurrentInstance().getExternalContext();
		        	   TaToTaEvalSubmission tatoTaEvalBean = (TaToTaEvalSubmission) exctxt.getSessionMap().get("TaToTaEvalSubmission");
		    		
		        	   
		        		if(tatoTaEvalBean!=null){
		        			tatoTaEvalBean.init();
		    			}
						return "/pages/secured/survey/instructorEval/taEvalSettings.xhtml?faces-redirect=true";
		           }
		           else return "";
				
			}
			else 	return "";
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
	
	public String navigateToAddCourse()
	{
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			  currentMenuId = "Add Course";
		
		
					return "/pages/secured/config/coursesAndInstructors.xhtml?faces-redirect=true";
				
			
				
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
	public String navigateToImportCourse()
	{
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
		
			  currentMenuId = "Import Course";
		
		
					return "/pages/secured/config/importCourses.xhtml?faces-redirect=true";
				
			
				
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
	public String navigateToImportCourseStudents()
	{
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			  currentMenuId = "Import Students";
		
		
					return "/pages/secured/config/studentsWithCourses.xhtml?faces-redirect=true";
				
			
				
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
	
	public String navigateToImportStudents()
	{
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			  currentMenuId = "Import Students2";
		
		
					return "/pages/secured/config/importStudents.xhtml?faces-redirect=true";
				
			
				
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
	
	public String navigateToStudents()
	{
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			  currentMenuId = "All Students";
		
		
					return "/pages/secured/config/students.xhtml?faces-redirect=true";
				
			
				
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
	

	public String navigateToIns()
	{
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			  currentMenuId = "All Instructors";
		
		
					return "/pages/secured/config/instructors.xhtml?faces-redirect=true";
				
			
				
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	
	}
	public String navigateToMajor()
	{
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser")&&isAdmin())// logged in
		{
			
			
			  currentMenuId = "Manage Majors";
		
		
					return "/pages/secured/config/majors.xhtml?faces-redirect=true";
				
			
				
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	
	}
	
	public String navigateToHeads()
	{
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser")&&isAdmin())// logged in
		{
			
			
			  currentMenuId = "Manage Heads";
		
		
					return "/pages/secured/config/accreditionHead.xhtml?faces-redirect=true";
				
			
				
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	
	}
	public String navigateToSurveyConfig(String cases)
	{
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			  currentMenuId = "Surveys";
		
		switch (cases) {
		case "1":
			 {currentMenuId = "Surveys";
			return "/pages/secured/survey/surveyConfig/surveys.xhtml?faces-redirect=true";
			 }
		case "2":
		{ currentMenuId = "Sections";
			return "/pages/secured/survey/surveyConfig/sectionsConfig.xhtml?faces-redirect=true";	
		}

	
		}
					return "";
				
			
				
			
	}
		else 	return "pages/public/login.xhtml?faces-redirect=true";
	
	}
	
	
	
public String navigateToForms()
{
	Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
		
		  currentMenuId = "Forms Settings";
	
	
				return "/pages/secured/config/FormsStatus.xhtml?faces-redirect=true";
			
		
			
		
}
	else 	return "pages/public/login.xhtml?faces-redirect=true";
	}

public String navigateToSemesterConfig()
{
	Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
		
		  currentMenuId = "Semester Config";
	
	
				return "/pages/secured/survey/lectureObjectiveFeedback/configureSemester.xhtml?faces-redirect=true";
			
		
			
		
}
	else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
public String navigateToStudentsConfig()
{
	Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
		
		  currentMenuId = "Semester Config";
	
	
				return "/pages/secured/survey/lectureObjectiveFeedback/configureCoursesAndStudents.xhtml?faces-redirect=true";
			
		
			
		
}
	else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
public String navigateToLectureObjectivesFeedback()
{
	Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
		
		  currentMenuId = "Lecture Objectives Result";
	
	
				return "/pages/secured/survey/lectureObjectiveFeedback/results.xhtml?faces-redirect=true";
			
		
			
		
}
	else 	return "pages/public/login.xhtml?faces-redirect=true";
	}

public String navigateToDeclarationOfConcentrationStudent()
{
	Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
		
		  currentMenuId = "Dec of Con";
	
	
				return "/pages/secured/survey/declarationOfConcentration/declarationOfConcentration.xhtml?faces-redirect=true";
			
		
			
		
}
	else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
public String navigateToDeclarationOfConcentrationResults()
{
	Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
		
		  currentMenuId = "Dec of Con Results";
	
	
				return "/pages/secured/survey/declarationOfConcentration/declarationOfConcentrationResults.xhtml?faces-redirect=true";
			
		
			
		
}
	else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
public String navigateToDeclarationOfConcentrationStudents()
{
	Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
		
		  currentMenuId = "Dec of Con Students";
	
	
				return "/pages/secured/survey/declarationOfConcentration/declarationOfConcentrationStudents.xhtml?faces-redirect=true";
			
		
			
		
}
	else 	return "pages/public/login.xhtml?faces-redirect=true";
	}
public String renderJuniorTAProgram(){
	currentMenuId = "Junior TA Program";

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				
				return "/pages/secured/forms/tAJuniorProgram/taJuniorProgramStudent.xhtml?faces-redirect=true";
			
			}
			else
			{
				if(mail.toLowerCase().equals(Constants.DEAN_OF_ACADEMIC.toLowerCase()))
				{
					if(getInsDataFacade.isPA(mail))
					{
						return "/pages/secured/forms/tAJuniorProgram/juniorTAProgIns.xhtml?faces-redirect=true";
					}
					else 
						return "/pages/secured/forms/tAJuniorProgram/juniorTAProgDean.xhtml?faces-redirect=true";
				}
					
				 else 
					return "/pages/secured/forms/tAJuniorProgram/juniorTAProgIns.xhtml?faces-redirect=true";	
						
					
			}
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
}
public String navigateBookSys(Integer page)
{
	if(page==1)
	{
		return "/pages/secured/booksSys/Books.xhtml?faces-redirect=true";
	}
	else if(page==2)
	{
		return "/pages/secured/booksSys/copiesReservation.xhtml?faces-redirect=true";
	}else if(page==3)
	{
		return "/pages/secured/booksSys/booksReturns.xhtml?faces-redirect=true";
	}
	else{
		return "/pages/public/login.xhtml?faces-redirect=true";
	}
}

public String renderChangeConcentration(){
	currentMenuId = "Change of Concentration";

	Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
		String mail = authentication.getName();
		if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
		{
			
			return "/pages/secured/forms/changeOfConcentration/changeOfConcentrationStudent.xhtml?faces-redirect=true";
		
		}
		else if(mail.equals(Constants.ADMISSION_HEAD))
		{
			
			return "/pages/secured/forms/changeOfConcentration/changeOfConcentrationAdmissionHead.xhtml?faces-redirect=true";
				
		}
		else if(mail.equals(Constants.ADMISSION_DEPT))
		{
			
				
				
			return "/pages/secured/forms/changeOfConcentration/changeOfConcentrationAdmission.xhtml?faces-redirect=true";
				
		}
		
		else 
		{
			
			return "/pages/secured/forms/changeOfConcentration/changeOfConcentrationIns.xhtml?faces-redirect=true";
		}
		
	}
	else
	{
		
		return "/pages/public/login.xhtml?faces-redirect=true";
	}
}
	
public String renderLectureObjStudent(){
	
	Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
	
					
		String mail = authentication.getName();
		if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
		{    currentMenuId = "Lectures Objectives Feedback";
		
		  ExternalContext exctxt = FacesContext.getCurrentInstance().getExternalContext();
		  FillLectureObjectiveFeedbackBean courseEvalBean = (FillLectureObjectiveFeedbackBean) exctxt.getSessionMap().get("fillLectureObjectiveFeedbackBean");
		  
		if(courseEvalBean!=null){
		        courseEvalBean.init();
		}
				return "/pages/secured/survey/lectureObjectiveFeedback/settingsPage.xhtml?faces-redirect=true";
			
		}
			else 
			{
				
				return "/pages/secured/survey/lectureObjectiveFeedback/settingsPage.xhtml?faces-redirect=true";
			}
		
}
	else 	return "pages/public/login.xhtml?faces-redirect=true";
}
	public boolean isValidateLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			return true;
		} 
		else 
			return false;
}



public void setValidateLoggedIn(boolean validateLoggedIn) {
	this.validateLoggedIn = validateLoggedIn;
}




	public ILoginSecurityAppService getLoginSecAppService() {
		return loginSecAppService;
	}

	public void setLoginSecAppService(ILoginSecurityAppService loginSecAppService) {
		this.loginSecAppService = loginSecAppService;
	}

	
	

	public ILoginSecurityAppService getSecurityLoginAppService() {
		return securityLoginAppService;
	}

	public void setSecurityLoginAppService(
			ILoginSecurityAppService securityLoginAppService) {
		this.securityLoginAppService = securityLoginAppService;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	

	public boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   String mail = authentication.getName();
		/*	if( mail.equals(Constants.DEAN_OF_STUDENTS) ||
					mail.equals(Constants.LTS_SYSTEM_ADMIN))
			{
				return true;
			}*/
			if(mail.equals(Constants.LTS_SYSTEM_ADMIN))
			{
				return true;
			}
			else return false;
		}
		else return false;
		
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	
	public boolean isDisableDailyAtt() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
		
			if(!mail.startsWith("s-")&&!mail.startsWith("S-")) //Other than student case
			{
				if(mail.equals("attendance@zewailcity.edu.eg"))
					return false;
				else 
					return true;
			}
			else 
				return false;
		}
		else 
			return false;
		
	
	}


	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}





	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	
	public String getActiveCssName(String menuId)
	{
		Boolean resetMenu = (Boolean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("resetMenu");
		if(resetMenu == null || resetMenu)
		{
			currentMenuId = "Dashboard";
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("resetMenu", false);
		}
		
		if(menuId.equals(currentMenuId))
			return "activeMenuLink";
		else
			return "inActiveMenuLink";
	}
	

	public String renderAccountSetting()
	{
		currentMenuId = "Account Settings";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
				//FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/secured/accountSettings/changePassword.xhtml?faces-redirect=true");
				return"/pages/secured/accountSettings/changePassword.xhtml?faces-redirect=true";
			
			
    	
		}
		else
		{
			return"/pages/public/login.xhtml?faces-redirect=true";
		}
	}



	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}



	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}



	public boolean isFirstYearStudent() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{
				if(Integer.toString(studentDataFacade.getPersonByPersonMail(mail).getFileNo()).startsWith("2014"))
				{
					return true;
				}
				else return false;}
			else return false;
		}
		else return false;
	}



	public void setFirstYearStudent(boolean firstYearStudent) {
		this.firstYearStudent = firstYearStudent;
	}
	public String renderPetitionHitory() // for history
	{
		currentMenuId = "Petitions Hitory";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase())||mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase())||mail.toLowerCase().equals(Constants.LTS_SYSTEM_ADMIN.toLowerCase()))
					{
						
							return "/pages/secured/forms/formsHistory/formsHistory.xhtml?faces-redirect=true";
					}
					
			else
			{
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only for Admission Head and Dean of Students");
				
				return null;
			}
			
			
		}
		else
		{
			
			return "/pages/public/login.xhtml?faces-redirect=true";
		}
		
	
	}

	public boolean getDisablePetitionHitory() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
			
				if(mail.equals("attendance@zewailcity.edu.eg"))
					return true;
				else 
					return false;
			
		}
		else 
			return false;
	}

	public boolean getPetitionHitoryBool(){
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
			
			String mail = authentication.getName();
		if(mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase())||mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase())||mail.toLowerCase().equals(Constants.LTS_SYSTEM_ADMIN.toLowerCase()))
		{
			
				return true;
		}
		
else
{
	
	return false;
} 
	}



	public boolean isSeeResultsOfIntendedMajor() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   String mail = authentication.getName();
			if(( mail.toLowerCase().equals(Constants.DEAN_OF_STRATEGIC.toLowerCase()) ||
				mail.toLowerCase().equals(Constants.PROVOST.toLowerCase())  ||
				mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase())||
				mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase())))
			{
			return true;
			}
			else return false;
		}
		else return false;
		
	}
	public boolean isSeeGraduationFormReponses() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   String mail = authentication.getName();
			if(	mail.toLowerCase().equals(Constants.LTS_SYSTEM_ADMIN) ||
				mail.toLowerCase().equals(Constants.ADMISSION_HEAD.toLowerCase())||
				mail.toLowerCase().equals(Constants.ADMISSION_DEPT.toLowerCase()))
			{
			return true;
			}
			else return false;
		}
		else return false;
		
	}


	public void setSeeResultsOfIntendedMajor(boolean seeResultsOfIntendedMajor) {
		this.seeResultsOfIntendedMajor = seeResultsOfIntendedMajor;
	}


	public String getMoodleEmail() {
		//	return "nermeen_rashed@yahoo.com";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
		    return mail;
			
			
			
		}
		else 
			return"";
			
	
	}



	public void setMoodleEmail(String moodleEmail) {
		this.moodleEmail = moodleEmail;
	}



	public String getMoodlePassword() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
            LoginStaffDTO dto=loginFacade.checkRegisteryOFMail(mail);
		    return dto.getPassword();
			
			
		}
		else 
			return"";
		
	}



	public void setMoodlePassword(String moodlePassword) {
		this.moodlePassword = moodlePassword;
	}



	public ILoginFacade getLoginFacade() {
		return loginFacade;
	}



	public void setLoginFacade(ILoginFacade loginFacade) {
		this.loginFacade = loginFacade;
	}



	public IFormsStatusFacade getSettingsFacade() {
		return settingsFacade;
	}



	public void setSettingsFacade(IFormsStatusFacade settingsFacade) {
		this.settingsFacade = settingsFacade;
	}






	public boolean isStudentModeDeclarationOfConcentration() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-"))
			{
				PersonDataDTO student= studentDataFacade.getPersonByPersonMail(mail);
				//if(Integer.toString(student.getFileNo()).startsWith("")
				 List<Integer> settings=settingsFacade.getById(7).getLevels();
				 boolean exist=false;
				 for(int i=0;i<settings.size();i++)
				 {
					if(settings.get(i).equals(student.getLevelID()))
					{
						exist= true;
						break;
						
					}
					
				 }
				 if(exist)
				 {
					 return true;
				 }
				 else return false;
				
			}
				
			else return false;
		}
		else return false;
	}



	public void setStudentModeDeclarationOfConcentration(
			boolean studentModeDeclarationOfConcentration) {
		this.studentModeDeclarationOfConcentration = studentModeDeclarationOfConcentration;
	}



	public boolean isStudentModeDeclarationOfMajor() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-"))
			{
				PersonDataDTO student= studentDataFacade.getPersonByPersonMail(mail);
				//if(Integer.toString(student.getFileNo()).startsWith("")
				 List<Integer> settings=settingsFacade.getById(9).getLevels();
				 boolean exist=false;
				 for(int i=0;i<settings.size();i++)
				 {
					if(settings.get(i).equals(student.getLevelID()))
					{
						exist= true;
						break;
						
					}
					
				 }
				 if(exist)
				 {
					 return true;
				 }
				 else return false;
				
			}
				
			else return false;
		}
		else return false;
	}



	public void setStudentModeDeclarationOfMajor(
			boolean studentModeDeclarationOfMajor) {
		this.studentModeDeclarationOfMajor = studentModeDeclarationOfMajor;
	}



	public boolean isStudentModeIntendedMajor() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-"))
			{
				PersonDataDTO student= studentDataFacade.getPersonByPersonMail(mail);
				//if(Integer.toString(student.getFileNo()).startsWith("")
				 List<Integer> settings=settingsFacade.getById(8).getLevels();
				 boolean exist=false;
				 for(int i=0;i<settings.size();i++)
				 {
					if(settings.get(i).equals(student.getLevelID()))
					{
						exist= true;
						break;
						
					}
					
				 }
				 if(exist)
				 {
					 return true;
				 }
				 else return false;
				
			}
				
			else return false;
		}
		else return false;
	}

	public boolean isStudentGraduationForm() {
		
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{   
				String mail = authentication.getName();
				if(mail.startsWith("s-")||mail.startsWith("S-"))
				{
					PersonDataDTO student= studentDataFacade.getPersonByPersonMail(mail);
					//if(Integer.toString(student.getFileNo()).startsWith("")
					FormsStatusDTO form=formStatus.getById(16);
					if(form.getStatus().equals(FormsStatusEnum.Active))
					{
						return true;
					}
					else return false;
				}
				else return false;	
			
			}
			else return false;
			}

	public void setStudentModeIntendedMajor(boolean studentModeIntendedMajor) {
		this.studentModeIntendedMajor = studentModeIntendedMajor;
	}


	public String renderStudentProfile()
	{
		currentMenuId = "Student Profile";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			return"/pages/secured/accountSettings/studentProfile.xhtml?faces-redirect=true";
		}
		else
		{
			return"/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	public String renderGraduationForm()
	{
		currentMenuId = "Graduation Form";
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			if(authentication.getName().startsWith("s-")||authentication.getName().startsWith("S-"))
			return"/pages/secured/forms/graduationForm/fillForm.xhtml?faces-redirect=true";
			else 
			return"/pages/secured/forms/graduationForm/result.xhtml?faces-redirect=true";
		}
		else
		{
			return"/pages/public/login.xhtml?faces-redirect=true";
		}
	}
	public boolean isStudent() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-"))
			{
				return true;
			}
				
			else return false;
		}
		else return false;
	}

	public boolean isCourseEvalStudent() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{   
	
				FormsStatusDTO form=formStatus.getById(12);
				if(form.getStatus().equals(FormsStatusEnum.Active))
				{
					return true;
				}
				
				else return false;
				
				
			}
				else 
				{
					return false;
				}
			
	}
		else return false;
	}



	public void setCourseEvalStudent(boolean courseEvalStudent) {
		this.courseEvalStudent = courseEvalStudent;
	}



	public IFormsStatusFacade getFormStatus() {
		return formStatus;
	}



	public void setFormStatus(IFormsStatusFacade formStatus) {
		this.formStatus = formStatus;
	}
	public boolean isInsTAEvalSecurity() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
			if(!mail.startsWith("s-")&&!mail.startsWith("S-"))
			{
				FormsStatusDTO form=formStatus.getById(13);
				if(form.getStatus().equals(FormsStatusEnum.Active))
				{
					return true;
				}
				
				else return false;
				
				
				}
			else return false;
			}
		else return false;
	}



	public void setInsTAEvalSecurity(boolean insTAEvalSecurity) {
		this.insTAEvalSecurity = insTAEvalSecurity;
	}


	public boolean isBooksSysMode() {
		try{
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
			if(!mail.startsWith("s-")&&!mail.startsWith("S-"))
			{
				InstructorDTO emp=new InstructorDTO();
				emp=getInsDataFacade.getInsByPersonMail(mail);
				if(emp.getEmpType()==3) //Books Sys
				{
					return true;
				}
				else return false;
				
				
			}
			else return false;
			}
		else return false;
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
			return false;
		}
	}



	public void setBooksSysMode(boolean booksSysMode) {
		this.booksSysMode = booksSysMode;
	}



	public boolean isStudentModeChangeOfConcentration() {
		boolean result=false;
	
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{   
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-"))
			{
				PersonDataDTO student= studentDataFacade.getPersonByPersonMail(mail);
				List<Integer> settings=settingsFacade.getById(14).getLevels();
				 boolean exist=false;
				 for(int i=0;i<settings.size();i++)
				 {
					if(settings.get(i).equals(student.getLevelID()))
					{
						exist= true;
						break;
						
					}
					
				 }
				 if(exist)
				 {
					 result= true;
				 }
				 else result= false;
				
			}
			else {
				result= true;
			}
		}
		return result;
	}



	public void setStudentModeChangeOfConcentration(
			boolean studentModeChangeOfConcentration) {
		this.studentModeChangeOfConcentration = studentModeChangeOfConcentration;
	}



	public String getCurrentMenuId() {
		return currentMenuId;
	}



	public void setCurrentMenuId(String currentMenuId) {
		this.currentMenuId = currentMenuId;
	}



	public boolean isLectureObjStudent() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{   
	
				FormsStatusDTO form=formStatus.getById(15);
				if(form.getStatus().equals(FormsStatusEnum.Active))
				{
					return true;
				}
				
				else return false;
				
				
			}
				else 
				{
					return false;
				}
			
	}
		else return false;
	}



	public void setLectureObjStudent(boolean lectureObjStudent) {
		this.lectureObjStudent = lectureObjStudent;
	}



	public void setSeeGraduationFormReponses(boolean seeGraduationFormReponses) {
		this.seeGraduationFormReponses = seeGraduationFormReponses;
	}



	public boolean isMidtermEvalStudent() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("s-")||mail.startsWith("S-")) // student case
			{   
	
				FormsStatusDTO form=formStatus.getById(17);
				if(form.getStatus().equals(FormsStatusEnum.Active))
				{
					return true;
				}
				
				else return false;
				
				
			}
				else 
				{
					return false;
				}
			
	}
		else return false;
	}



	public void setMidtermEvalStudent(boolean midtermEvalStudent) {
		this.midtermEvalStudent = midtermEvalStudent;
	}



	



	public HeadsAppServiceImpl getHeadFacades() {
		return headFacades;
	}



	public void setHeadFacades(HeadsAppServiceImpl headFacades) {
		this.headFacades = headFacades;
	}



	public MajorsFacadeImpl getMajorfacade() {
		return majorfacade;
	}



	public void setMajorfacade(MajorsFacadeImpl majorfacade) {
		this.majorfacade = majorfacade;
	}



	public StudentFacadeImpl getStudentFacadeImpl() {
		return studentFacadeImpl;
	}



	public void setStudentFacadeImpl(StudentFacadeImpl studentFacadeImpl) {
		this.studentFacadeImpl = studentFacadeImpl;
	}



	public boolean isHeadOrStudent() {
		return headOrStudent;
	}



	public void setHeadOrStudent(boolean headOrStudent) {
		this.headOrStudent = headOrStudent;
	}

	
	
}
 