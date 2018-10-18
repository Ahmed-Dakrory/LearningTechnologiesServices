/**
 * 
 */
package main.com.zc.services.presentation.coursesManagment.bean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

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
	
	@PostConstruct
	public void init()
	{
		
			 
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
		 /*JTextPane pad = new JTextPane();
	        pad.setContentType("text/html");
	        HTMLEditorKit kit = (HTMLEditorKit)pad.getEditorKit();
	        HTMLDocument htmldoc = (HTMLDocument)kit.createDefaultDocument();
	        kit.insertHTML(htmldoc, htmldoc.getLength(), "<p>paragraph <b>1</b></p>", 0, 0, null);
	        kit.insertHTML(htmldoc, htmldoc.getLength(), "<p>paragraph <span style=\"color:red\">2</span></p>", 0, 0, null);
	        pad.setDocument(htmldoc);
	       */ 
	        
	      //load document here
		    HttpServletResponse response =
	                (HttpServletResponse) FacesContext.getCurrentInstance()
	                    .getExternalContext().getResponse();
		    try {
				PrintWriter out = response.getWriter();
				// out.println(courseSyllabus.getCourseName());
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
		note=new Note();
		relatedTopics=new RelatedTopics();
		
		courseInstructorList=courseInsFacade.getAll();
		for(int i=0;i<courseInstructorList.size();i++){
			System.out.println("Ahmed Println: "+courseInstructorList.get(i).getInstructor().getName());
		}
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
	
	
	
	
	
}
