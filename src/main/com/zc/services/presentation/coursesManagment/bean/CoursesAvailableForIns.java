/**
 * 
 */
package main.com.zc.services.presentation.coursesManagment.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.courses.model.SO.ISOAppService;
import main.com.zc.services.domain.courses.model.SO.SO;
import main.com.zc.services.domain.courses.model.books.CourseBooks;
import main.com.zc.services.domain.courses.model.books.IBookCourseAppService;
import main.com.zc.services.domain.courses.model.clo.CLO;
import main.com.zc.services.domain.courses.model.clo.ICLOAppService;
import main.com.zc.services.domain.courses.model.corequisit.CoRequisites;
import main.com.zc.services.domain.courses.model.corequisit.ICoRequisitesAppService;
import main.com.zc.services.domain.courses.model.courseIns.CourseInSyllabus;
import main.com.zc.services.domain.courses.model.courseIns.ICourseInsSyllabusAppService;
import main.com.zc.services.domain.courses.model.courseTa.CourseTa;
import main.com.zc.services.domain.courses.model.courseTa.ICourseTaAppService;
import main.com.zc.services.domain.courses.model.grades.Grade;
import main.com.zc.services.domain.courses.model.grades.IGradeAppService;
import main.com.zc.services.domain.courses.model.note.INoteAppService;
import main.com.zc.services.domain.courses.model.note.Note;
import main.com.zc.services.domain.courses.model.prerequisites.IPreRequisitesAppService;
import main.com.zc.services.domain.courses.model.prerequisites.PreRequisites;
import main.com.zc.services.domain.courses.model.references.IReferencesAppService;
import main.com.zc.services.domain.courses.model.references.References;
import main.com.zc.services.domain.courses.model.relatedTopics.IRelatedTopicsAppService;
import main.com.zc.services.domain.courses.model.relatedTopics.RelatedTopics;
import main.com.zc.services.domain.courses.model.schedule.IScheduleAppService;
import main.com.zc.services.domain.courses.model.schedule.Schedule;
import main.com.zc.services.domain.courses.model.topics.ITopicsAppService;
import main.com.zc.services.domain.courses.model.topics.Topics;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.presentation.shared.facade.ICouresFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.facade.ILoginFacade;

/**
 * @author A7med Al-Dakrory
 *
 */
@SessionScoped
@ManagedBean(name="CoursesAvailableForIns")
public class CoursesAvailableForIns {

	
	private List<CoursesDTO> coursesLst;
	private List<Courses_Instructors> courseInstructorList;
	private CourseInSyllabus courses_Instructor;
	private Employee selectedInstructor;
	private int selectedCourseInstructorId;
	private String numberOfCourses;
	private int courseId; 
	private CourseSyllabusCollection courseSyllabusCollection;
	private CourseTa courseTaNew;
	private Grade gradeNew;
	private CourseBooks book;
	private String selectedBookName;
	private PreRequisites preRequisites;
	private CoRequisites coRequisites;
	private CLO clo;
	private References references;
	private SO so;
	private Note note;
	private RelatedTopics relatedTopics;
	private Topics topics;
	private Schedule schedule;
	private String mail;
	
	private int tabSelection=0;
	
	
	@ManagedProperty(value = "#{loginFacadeImpl}")
	private ILoginFacade loginfacade;

	//Note Table facade
	@ManagedProperty(value = "#{NoteFacadeImpl}")
	private INoteAppService noteFacade;
	
	//SO Table facade
	@ManagedProperty(value = "#{SOFacadeImpl}")
	private ISOAppService soFacade;
	
	//CLO Table facade
	@ManagedProperty(value = "#{CLOFacadeImpl}")
	private ICLOAppService cloFacade;
		
	//grade Table facade
	@ManagedProperty(value = "#{GradeFacadeImpl}")
	private IGradeAppService gradeFacade;
		
	//related_topics Table facade
	@ManagedProperty(value = "#{relatedTopicsFacadeImpl}")
	private IRelatedTopicsAppService relatedTopicsFacade;
		
	//courseTa Table facade
	@ManagedProperty(value = "#{courseTaFacadeImpl}")
	private ICourseTaAppService courseTaFacade;
	
	//references Table facade
	@ManagedProperty(value = "#{referencesFacadeImpl}")
	private IReferencesAppService referencesFacade;
	
	//preRequisit Table facade
	@ManagedProperty(value = "#{preRequisiteFacadeImpl}")
	private IPreRequisitesAppService preRequisitFacade;
	
	//coRequisit Table facade
	@ManagedProperty(value = "#{coRequisitFacadeImpl}")
	private ICoRequisitesAppService coRequisitFacade;
	
	//Courses Table facade
	@ManagedProperty("#{ICouresFacade}")
	ICouresFacade courseFacade;
	
	//CoursesInstructor Table facade
	@ManagedProperty("#{ICourse_InstructorRepository}")
	private ICourse_InstructorRepository courseInsFacade;
	
	//CoursesInstructorSyllabus Table facade
	@ManagedProperty("#{courseInsSyllabusFacadeImpl}")
	private ICourseInsSyllabusAppService courseInsSyllabusFacade;
		
	//Book Table facade
	@ManagedProperty("#{BookCourseFacadeImpl}")
   	private IBookCourseAppService bookFacade; 
	
	//Topic Table facade
	@ManagedProperty(value = "#{TopicFacadeImpl}")
	private ITopicsAppService topicFacade;
		
	//Topic Table facade
	@ManagedProperty(value = "#{ScheduleFacadeImpl}")
	private IScheduleAppService scheduleFacade;
	
	@PostConstruct
	public void init()
	{
		
			 
	}
	
	public void orderThisBook(int refId){
		References references=referencesFacade.getById(refId);
				try{
					if (references != null) { // Implementation of sending mails
						Properties props = new Properties();
						props.put("mail.smtp.host", "smtp.gmail.com");
						props.put("mail.smtp.socketFactory.port", "465");
						props.put("mail.smtp.socketFactory.class",
								"javax.net.ssl.SSLSocketFactory");
						props.put("mail.smtp.auth", "true");
						props.put("mail.smtp.port", "465");

						Session session = Session.getDefaultInstance(props,
								new javax.mail.Authenticator() {
									protected PasswordAuthentication getPasswordAuthentication() {
										return new PasswordAuthentication(
												"LearningTechnologies@zewailcity.edu.eg",
												"zcltinfo");
									}
								});

						javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
						
		     				addressTo[0] = new javax.mail.internet.InternetAddress(
		     						"rramzy@zewailcity.edu.eg");
			

						/* Message message = new MimeMessage(session); */
						Message message = new MimeMessage(session);

						message.setFrom(new InternetAddress(
								"LearningTechnologies@zewailcity.edu.eg"));
						message.setRecipients(Message.RecipientType.TO, addressTo);

						message.setSubject("Book details for purchase order");

						String htmlText = "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
								+ "<ul style=margin:0;padding:0;>"
								+ "<li style=list-style:none;float:left;width:700px;margin:0;>"
								+ "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
								+ "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
								+ "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
								+ "</ul>"
								+ "</li>"
								+ "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
								+ "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
								+ "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
								+ "</li>"
								+ "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
								+ "<div style=padding:24px 36px;color:#676767 !important;>"
								+ "<span style=color:#676767>Dear "
								+ "Rasha"
								+ ",</span><br/><br/><br/>"
								+ "<span style=color:#404040>You're receiving this email because it is needed to order this book with this details"+"</span><br/><br/><br/>"
								+ "<span style=color:#404040>"+"Course Name: "+"</span>"+ "<span style=color:#676767>"+courseSyllabusCollection.getCourses().getCourseTitle()+"</span><br/>"
								+ "<span style=color:#404040>"+"Course Code: "+"</span>"+ "<span style=color:#676767>"+courseSyllabusCollection.getCourses().getName()+"</span><br/>"
								+ "<span style=color:#404040>"+"Course Coordinator: "+"</span>"+ "<span style=color:#676767>"+courseSyllabusCollection.getCourses().getCourseCoordinator().getName()+"</span><br/>"
								+ "<span style=color:#404040>"+"Coordinator Email: "+"</span>"+ "<span style=color:#676767>"+courseSyllabusCollection.getCourses().getCourseCoordinator().getMail()+"</span><br/>"
								+ "<span style=color:#404040>"+"Book Title: "+"</span>"+ "<span style=color:#676767>"+references.getName()+"</span><br/>"
								+ "<span style=color:#404040>"+"Book Authors: "+"</span>"+ "<span style=color:#676767>"+references.getAuthors()+"</span><br/>"
								+ "<span style=color:#404040>"+"Book Publisher: "+"</span>"+ "<span style=color:#676767>"+references.getPublisher()+"</span><br/>"
								+ "<span style=color:#404040>"+"Book Year: "+"</span>"+ "<span style=color:#676767>"+references.getYear()+"</span><br/>"
								+ "<span style=color:#404040>"+"Book Edition: "+"</span>"+ "<span style=color:#676767>"+references.getEdition()+"</span><br/>"
								+ "<span style=color:#404040>"+"Book ISBN Number: "+"</span>"+ "<span style=color:#676767>"+references.getIsbn_number()+"</span><br/><br/>"
								+ "</span><br/><br/>"
								+ "<span style=color:#676767>Thank you, </span><br/><br/>"
								+ "<span style=color:#676767>Center for Learning Technologies</span>"
								+ "</div>"
								+ "</li>"
								+ "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
								+ "<ul style=margin:0;padding:0;>"
								+ "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
								+ "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
								+ "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
								+ "</li>"
								+ "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
								+ "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
								+ "</li>"
								+ "</ul>"
								+ "</li>"
								+ "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
								+ "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
								+ " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
								+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
								+ "</div>" + "</li>" + "</ul>" + "</div>";
						/*
						 * message.setText("Dear " + dao.getName() + " ," +
						 * "\n Your Password is : " + dao.getPassword() + "\n\n Regards"
						 * + "\n Learning Technologies Department" +
						 * "\n\n Please do not reply to this email ");
						 * 
						 * Transport.send(message);
						 */

						message.setContent(htmlText, "text/html; charset=ISO-8859-1");

						Transport.send(message);

						JavaScriptMessagesHandler.RegisterNotificationMessage("",
								"Email Sent");
						// System.out.println("Done sending ");
						

					} else {
						JavaScriptMessagesHandler.RegisterErrorMessage("",
								"This email address is not registered in the system!");
					}

				} catch (Exception excep) {
					excep.printStackTrace();
					JavaScriptMessagesHandler.RegisterErrorMessage("",
							"This email address is not registered in the system!");

				}
				
				
	}
	
	public void refresh(){
		System.out.println("Ahmed Dakrory11");
		int id=0; 
		// 1- call facade to get the attendance by file no
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				mail = authentication.getName();
	    		main.com.zc.services.presentation.survey.courseFeedback.dto.InstructorDTO Instperson=loginfacade.getCourseInsByInsMail(mail);
	    		id=Instperson.getId();
				
			}else {
					JavaScriptMessagesHandler.RegisterErrorMessage(null,"Not Allowed To see This Petitions" );
			}
	    	if(id==0){
	    		System.out.println("Ahmed Dakrory nothing");
				
	    	}else{

				fillCoursesLst();
	    	}
	}
	public void fillCoursesLst(){
		coursesLst=new ArrayList<CoursesDTO>();
		coursesLst=courseFacade.getByCourseCoordinatorID(mail);
		}
	
	public void generateFile(){
		 HSSFWorkbook workbook = new HSSFWorkbook();
		    HSSFSheet sheet = workbook.createSheet();
		    
		    ReportFileGeneration reportFileGeneration=new ReportFileGeneration(courseSyllabusCollection,workbook, sheet);
		    
		    reportFileGeneration.generateReport();

		    FacesContext facesContext = FacesContext.getCurrentInstance();
		    ExternalContext externalContext = facesContext.getExternalContext();
		    externalContext.setResponseContentType("application/vnd.ms-excel");
		    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"my.xls\"");

		    try {
				workbook.write(externalContext.getResponseOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    facesContext.responseComplete();
	}
	/*public void generateFile(){
		
	        
	      //load document here
		    HttpServletResponse response =
	                (HttpServletResponse) FacesContext.getCurrentInstance()
	                    .getExternalContext().getResponse();
		    try {
				PrintWriter out = response.getWriter();
				 out.println(courseSyllabusCollection.getCourses().getName());
			        try {
			        	response.setContentType("application/msword");
			            response.setHeader("Content-disposition", "attachment; fileName=\"" + "Syllabus.doc" + "\"" );
			            
			            }catch(Exception e){
			        out.println("Exeception while creating word doc"+e);
			        }
	 	        
	 	             
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
	           
			
	          
	         
	  
		    System.out.println("Ok Ahmed 5");
	      FacesContext faces = FacesContext.getCurrentInstance();
	      faces.responseComplete();  
	      RequestContext context = RequestContext.getCurrentInstance();
	      context.execute("exportCopiesDlg.hide();");
 	      //
	      System.out.println("Ok Ahmed 6");
 	      RequestContext.getCurrentInstance().update("dlgFormExport:addPanelExport");
 	      FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("dlgFormExport:addPanelExport");
 	      
	}
*/
	public String openTheSessionOfAddingSyllabus(){

		courseTaNew=new CourseTa();
		gradeNew=new Grade();
		preRequisites=new PreRequisites();
		book=new CourseBooks();
		courses_Instructor=new CourseInSyllabus();
		coRequisites=new CoRequisites();
		clo=new CLO();
		so=new SO();
		references=new References();
		topics=new Topics();
		schedule=new Schedule();
		note=new Note();
		relatedTopics=new RelatedTopics();
		
		courseInstructorList=courseInsFacade.getAll();
		
		courseSyllabusCollection=new CourseSyllabusCollection();
		
		courseSyllabusCollection.setCourses(courseFacade.getcourseById(courseId));
		courseSyllabusCollection.setCourseInstructor(courseInsSyllabusFacade.getByCourseId(courseId));
		courseSyllabusCollection.setCourseTas(courseTaFacade.getByCourseId(courseId));
		courseSyllabusCollection.setPre_Requisites(preRequisitFacade.getByCourseId(courseId));
		courseSyllabusCollection.setCoRequisites(coRequisitFacade.getByCourseId(courseId));
		courseSyllabusCollection.setClos(cloFacade.getByCourseId(courseId));
		courseSyllabusCollection.setSos(soFacade.getByCourseId(courseId));
		courseSyllabusCollection.setGrades(gradeFacade.getByCourseId(courseId));
		courseSyllabusCollection.setNotes(noteFacade.getByCourseId(courseId));
		courseSyllabusCollection.setReferences(referencesFacade.getByCourseId(courseId));
		courseSyllabusCollection.setRelatedTopics(relatedTopicsFacade.getByCourseId(courseId));
		courseSyllabusCollection.setBooks(bookFacade.getByCourseId(courseId));
		courseSyllabusCollection.setTopics(topicFacade.getByCourseId(courseId));
		courseSyllabusCollection.setSchedules(scheduleFacade.getByCourseId(courseId));
		return "/pages/secured/courseManagment/EditingcourseSyllabus.xhtml?faces-redirect=true";
		
	}
	
	public String endofEditing(){
		
		return "/pages/secured/courseManagment/courseSyllabus.xhtml?faces-redirect=true";
	}
	
	public void deleteNote(int Id){
		noteFacade.delete(noteFacade.getById(Id));
		courseSyllabusCollection.setNotes(noteFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Note was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public void deleteCourseIns(int Id){
		courseInsSyllabusFacade.delete(courseInsSyllabusFacade.getById(Id));
		courseSyllabusCollection.setCourseInstructor(courseInsSyllabusFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Instructor was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public void deleteCourseTa(int Id){
		courseTaFacade.delete(courseTaFacade.getById(Id));
		courseSyllabusCollection.setCourseTas(courseTaFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "TA was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public void deleteCLO(int Id){
		cloFacade.delete(cloFacade.getById(Id));
		courseSyllabusCollection.setClos(cloFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Course Learning objective was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public void deleteTopic(int Id){
		topicFacade.delete(topicFacade.getById(Id));
		courseSyllabusCollection.setTopics(topicFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Topics was Deleted successfully");

	}
	
	public void deleteSchedule(int Id){
		scheduleFacade.delete(scheduleFacade.getById(Id));
		courseSyllabusCollection.setSchedules(scheduleFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Schedule was Deleted successfully");

	}
	
	public void deletePreRequisite(int Id){
		preRequisitFacade.delete(preRequisitFacade.getById(Id));
		courseSyllabusCollection.setPre_Requisites(preRequisitFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Pre Requisite was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public void deleteCoRequisite(int Id){
		coRequisitFacade.delete(coRequisitFacade.getById(Id));
		courseSyllabusCollection.setCoRequisites(coRequisitFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Co Requisite was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public void deleteBook(int Id){
		bookFacade.delete(bookFacade.getById(Id));
		courseSyllabusCollection.setBooks(bookFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public void deleteSO(int Id){
		soFacade.delete(soFacade.getById(Id));
		courseSyllabusCollection.setSos(soFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Student OutCome was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public void deleteGrade(int Id){
		gradeFacade.delete(gradeFacade.getById(Id));
		courseSyllabusCollection.setGrades(gradeFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Grade was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public void deleteRelatedTopic(int Id){
		relatedTopicsFacade.delete(relatedTopicsFacade.getById(Id));
		courseSyllabusCollection.setRelatedTopics(relatedTopicsFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Related Topic was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public void deleteReference(int Id){
		referencesFacade.delete(referencesFacade.getById(Id));
		courseSyllabusCollection.setReferences(referencesFacade.getByCourseId(courseId));
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Reference was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}

	public void addTa(){
		System.out.println("Ok Ya Man");
		courseTaNew.setCourseId(courseId);
		courseSyllabusCollection.getCourseTas().add(courseTaNew);
		int si=courseSyllabusCollection.getCourseTas().size();
		System.out.println("AhmedDakrory ya man: "+String.valueOf(si));
		System.out.println("AhmedDakrory ya man: "+String.valueOf(courseSyllabusCollection.getCourseTas().get(si-1).getName()));
		System.out.println("AhmedDakrory ya man: "+String.valueOf(courseTaNew.getName()));

		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Ta was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addCourseDlg.hide();");

		
		courseTaNew=new CourseTa();
	}
	
	public void addGrade(){
		System.out.println("Ok Ya Man");
		gradeNew.setCourseId(courseId);
		courseSyllabusCollection.getGrades().add(gradeNew);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Grade was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addGradeDlg.hide();");
		gradeNew=new Grade();
	}
	public void addPreRequisite(){
		preRequisites.setCourseId(courseId);
		courseSyllabusCollection.getPre_Requisites().add(preRequisites);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "PreRequisite was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addPrerequisteDlg.hide();");
		preRequisites=new PreRequisites();
	}
	public void addCoRequisite(){
		coRequisites.setCourseId(courseId);
		courseSyllabusCollection.getCoRequisites().add(coRequisites);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "CoRequisite was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addCorequisteDlg.hide();");
		coRequisites=new CoRequisites();
	}
	public void addClo(){
		clo.setCourseId(courseId);
		courseSyllabusCollection.getClos().add(clo);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Clo was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addCloDlg.hide();");
		clo=new CLO();
	}
	public void addTopic(){
		topics.setCourseId(courseId);
		courseSyllabusCollection.getTopics().add(topics);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Topic was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addTopicDlg.hide();");
		topics=new Topics();
	}
	public void addSo(){
		so.setCourseId(courseId);
		courseSyllabusCollection.getSos().add(so);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "SO was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addSoDlg.hide();");
		so=new SO();
	}
	public void addNote(){
		note.setCourseId(courseId);
		courseSyllabusCollection.getNotes().add(note);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Note was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addNoteDlg.hide();");
		note=new Note();
	}
	public void addRelatedTopic(){
		relatedTopics.setCourseId(courseId);
		courseSyllabusCollection.getRelatedTopics().add(relatedTopics);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "RelatedTopic was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addRelatedTopicDlg.hide();");
		relatedTopics=new RelatedTopics();
	}
	public void addReference(){
		references.setCourseId(courseId);
		courseSyllabusCollection.getReferences().add(references);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Reference was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addReferenceDlg.hide();");
		references=new References();
	}
	
	public void addSchedule(){
		schedule.setCourseId(courseId);
		courseSyllabusCollection.getSchedules().add(schedule);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Schedule was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addScheduleDlg.hide();");
		schedule=new Schedule();
	}
	
	public void addBook(){
		book.setCourseId(courseId);
		book.setBook(selectedBookName);
		courseSyllabusCollection.getBooks().add(book);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addBookDlg.hide();");
		book=new CourseBooks();
		
	}
	public void addIntructor(){
		selectedInstructor=courseInsFacade.getById(selectedCourseInstructorId).getInstructor();
		courses_Instructor.setCourseId(courseId);
		courses_Instructor.setName(selectedInstructor.getName());
		courses_Instructor.setEmail(selectedInstructor.getMail());
		courseSyllabusCollection.getCourseInstructor().add(courses_Instructor);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Instructor was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addInstDlg.hide();");
		courses_Instructor=new CourseInSyllabus();
		
	}
	
	

	
	public void addMainDataToDatabase(){
		System.out.println("Ahmed Play ok");
		courseFacade.updateCourse(courseSyllabusCollection.getCourses());
			System.out.println("Ahmed Play ok 3");
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");

	}
	
	public void addScheduleToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getSchedules().size();i++){
			scheduleFacade.addSchedule(courseSyllabusCollection.getSchedules().get(i));
		}		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");

	}
	
	public void addCoRequisitesToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getCoRequisites().size();i++){
			coRequisitFacade.addCoRequisite(courseSyllabusCollection.getCoRequisites().get(i));
		}		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");

	}

	public void addPre_RequisitesToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getPre_Requisites().size();i++){
			preRequisitFacade.addPreRequisite(courseSyllabusCollection.getPre_Requisites().get(i));
		}	
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");

	}

	public void addClosToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getClos().size();i++){
			cloFacade.addCLO(courseSyllabusCollection.getClos().get(i));
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");
	}
	
	public void addTopicToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getTopics().size();i++){
			topicFacade.addTopic(courseSyllabusCollection.getTopics().get(i));
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");
	}

	public void addSosToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getSos().size();i++){
			soFacade.addSO(courseSyllabusCollection.getSos().get(i));
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");
	}
	
	public void addBooksToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getBooks().size();i++){
			bookFacade.addCourseBook(courseSyllabusCollection.getBooks().get(i));
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");

	}

	public void addGradesToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getGrades().size();i++){
			gradeFacade.addGrade(courseSyllabusCollection.getGrades().get(i));
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");

	}

	public void addRelatedTopicsToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getRelatedTopics().size();i++){
			relatedTopicsFacade.addRelatedTopics(courseSyllabusCollection.getRelatedTopics().get(i));
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");

	}

	public void addReferencesToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getReferences().size();i++){
			referencesFacade.addReference(courseSyllabusCollection.getReferences().get(i));
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");

	}

	public void addNotesToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getNotes().size();i++){
			noteFacade.addNote(courseSyllabusCollection.getNotes().get(i));
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");

	}

	public void addCourseInstructorToDataBase() {
		
		for(int i=0;i<courseSyllabusCollection.getCourseInstructor().size();i++){
			courseInsSyllabusFacade.addCourseIns(courseSyllabusCollection.getCourseInstructor().get(i));
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");

	}

	public void addCourseTaToDataBase() {
	
		for(int i=0;i<courseSyllabusCollection.getCourseTas().size();i++){
			courseTaFacade.addCourseTa(courseSyllabusCollection.getCourseTas().get(i));
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Data has been saved");
	
	}

	

	
	
	////////////////////////Setters and Getters////////////////
	
	
	
	
	public List<CoursesDTO> getCoursesLst() {
		return coursesLst;
	}
	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}
	
	
	public String getNumberOfCourses() {
		return numberOfCourses;
	}
	public void setNumberOfCourses(String numberOfCourses) {
		this.numberOfCourses = numberOfCourses;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public ILoginFacade getLoginfacade() {
		return loginfacade;
	}
	public void setLoginfacade(ILoginFacade loginfacade) {
		this.loginfacade = loginfacade;
	}
	
	
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public ICouresFacade getCourseFacade() {
		return courseFacade;
	}
	public void setCourseFacade(ICouresFacade courseFacade) {
		this.courseFacade = courseFacade;
	}

	public INoteAppService getNoteFacade() {
		return noteFacade;
	}

	public void setNoteFacade(INoteAppService noteFacade) {
		this.noteFacade = noteFacade;
	}

	public ISOAppService getSoFacade() {
		return soFacade;
	}

	public void setSoFacade(ISOAppService soFacade) {
		this.soFacade = soFacade;
	}

	public ICLOAppService getCloFacade() {
		return cloFacade;
	}

	public void setCloFacade(ICLOAppService cloFacade) {
		this.cloFacade = cloFacade;
	}

	public IRelatedTopicsAppService getRelatedTopicsFacade() {
		return relatedTopicsFacade;
	}

	public void setRelatedTopicsFacade(IRelatedTopicsAppService relatedTopicsFacade) {
		this.relatedTopicsFacade = relatedTopicsFacade;
	}

	public ICourseTaAppService getCourseTaFacade() {
		return courseTaFacade;
	}

	public void setCourseTaFacade(ICourseTaAppService courseTaFacade) {
		this.courseTaFacade = courseTaFacade;
	}

	public IReferencesAppService getReferencesFacade() {
		return referencesFacade;
	}

	public void setReferencesFacade(IReferencesAppService referencesFacade) {
		this.referencesFacade = referencesFacade;
	}

	public IPreRequisitesAppService getPreRequisitFacade() {
		return preRequisitFacade;
	}

	public void setPreRequisitFacade(IPreRequisitesAppService preRequisitFacade) {
		this.preRequisitFacade = preRequisitFacade;
	}

	public ICoRequisitesAppService getCoRequisitFacade() {
		return coRequisitFacade;
	}

	public void setCoRequisitFacade(ICoRequisitesAppService coRequisitFacade) {
		this.coRequisitFacade = coRequisitFacade;
	}

	public CourseSyllabusCollection getCourseSyllabusCollection() {
		return courseSyllabusCollection;
	}

	public void setCourseSyllabusCollection(
			CourseSyllabusCollection courseSyllabusCollection) {
		this.courseSyllabusCollection = courseSyllabusCollection;
	}

	public IGradeAppService getGradeFacade() {
		return gradeFacade;
	}

	public void setGradeFacade(IGradeAppService gradeFacade) {
		this.gradeFacade = gradeFacade;
	}

	public CourseTa getCourseTaNew() {
		return courseTaNew;
	}

	public void setCourseTaNew(CourseTa courseTaNew) {
		this.courseTaNew = courseTaNew;
	}

	public Grade getGradeNew() {
		return gradeNew;
	}

	public void setGradeNew(Grade gradeNew) {
		this.gradeNew = gradeNew;
	}

	public PreRequisites getPreRequisites() {
		return preRequisites;
	}

	public void setPreRequisites(PreRequisites preRequisites) {
		this.preRequisites = preRequisites;
	}

	
	public int getTabSelection() {
		return tabSelection;
	}

	public void setTabSelection(int tabSelection) {
		this.tabSelection = tabSelection;
	}

	public CourseBooks getBook() {
		return book;
	}

	public void setBook(CourseBooks book) {
		this.book = book;
	}

	

	public IBookCourseAppService getBookFacade() {
		return bookFacade;
	}

	public void setBookFacade(IBookCourseAppService bookFacade) {
		this.bookFacade = bookFacade;
	}

	public String getSelectedBookName() {
		return selectedBookName;
	}

	public void setSelectedBookName(String selectedBookName) {
		this.selectedBookName = selectedBookName;
	}

	
	public List<Courses_Instructors> getCourseInstructorList() {
		return courseInstructorList;
	}

	public void setCourseInstructorList(
			List<Courses_Instructors> courseInstructorList) {
		this.courseInstructorList = courseInstructorList;
	}


	public ICourse_InstructorRepository getCourseInsFacade() {
		return courseInsFacade;
	}

	public void setCourseInsFacade(ICourse_InstructorRepository courseInsFacade) {
		this.courseInsFacade = courseInsFacade;
	}

	public CourseInSyllabus getCourses_Instructor() {
		return courses_Instructor;
	}

	public void setCourses_Instructor(CourseInSyllabus courses_Instructor) {
		this.courses_Instructor = courses_Instructor;
	}

	
	public Employee getSelectedInstructor() {
		return selectedInstructor;
	}

	public void setSelectedInstructor(Employee selectedInstructor) {
		this.selectedInstructor = selectedInstructor;
	}

	public ICourseInsSyllabusAppService getCourseInsSyllabusFacade() {
		return courseInsSyllabusFacade;
	}

	public void setCourseInsSyllabusFacade(
			ICourseInsSyllabusAppService courseInsSyllabusFacade) {
		this.courseInsSyllabusFacade = courseInsSyllabusFacade;
	}

	public int getSelectedInstructorName() {
		return selectedCourseInstructorId;
	}

	public void setSelectedInstructorName(int selectedInstructorName) {
		this.selectedCourseInstructorId = selectedInstructorName;
	}

	public int getSelectedCourseInstructorId() {
		return selectedCourseInstructorId;
	}

	public void setSelectedCourseInstructorId(int selectedCourseInstructorId) {
		this.selectedCourseInstructorId = selectedCourseInstructorId;
	}

	public CoRequisites getCoRequisites() {
		return coRequisites;
	}

	public void setCoRequisites(CoRequisites coRequisites) {
		this.coRequisites = coRequisites;
	}

	public CLO getClo() {
		return clo;
	}

	public void setClo(CLO clo) {
		this.clo = clo;
	}

	public References getReferences() {
		return references;
	}

	public void setReferences(References references) {
		this.references = references;
	}

	public SO getSo() {
		return so;
	}

	public void setSo(SO so) {
		this.so = so;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public RelatedTopics getRelatedTopics() {
		return relatedTopics;
	}

	public void setRelatedTopics(RelatedTopics relatedTopics) {
		this.relatedTopics = relatedTopics;
	}

	public Topics getTopics() {
		return topics;
	}

	public void setTopics(Topics topics) {
		this.topics = topics;
	}

	public ITopicsAppService getTopicFacade() {
		return topicFacade;
	}

	public void setTopicFacade(ITopicsAppService topicFacade) {
		this.topicFacade = topicFacade;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public IScheduleAppService getScheduleFacade() {
		return scheduleFacade;
	}

	public void setScheduleFacade(IScheduleAppService scheduleFacade) {
		this.scheduleFacade = scheduleFacade;
	}


	
	
	
	
	
}
