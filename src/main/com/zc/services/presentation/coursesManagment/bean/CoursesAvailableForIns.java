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

import main.com.zc.services.domain.booksSys.model.Book;
import main.com.zc.services.domain.courses.model.SO.ISOAppService;
import main.com.zc.services.domain.courses.model.clo.ICLOAppService;
import main.com.zc.services.domain.courses.model.corequisit.ICoRequisitesAppService;
import main.com.zc.services.domain.courses.model.courseTa.CourseTa;
import main.com.zc.services.domain.courses.model.courseTa.ICourseTaAppService;
import main.com.zc.services.domain.courses.model.grades.Grade;
import main.com.zc.services.domain.courses.model.grades.IGradeAppService;
import main.com.zc.services.domain.courses.model.note.INoteAppService;
import main.com.zc.services.domain.courses.model.prerequisites.IPreRequisitesAppService;
import main.com.zc.services.domain.courses.model.prerequisites.PreRequisites;
import main.com.zc.services.domain.courses.model.references.IReferencesAppService;
import main.com.zc.services.domain.courses.model.relatedTopics.IRelatedTopicsAppService;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.facade.IBooksFacade;
import main.com.zc.services.presentation.configuration.facade.ICourseInstructorFacade;
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
	private String numberOfCourses;
	private int courseId; 
	private CourseSyllabusCollection courseSyllabusCollection;
	private CourseTa courseTaNew;
	private Grade gradeNew;
	private Book book;
	private PreRequisites preRequisites;
	private String mail;
	
	
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
	@ManagedProperty("#{ICourseInstructorFacade}")
	private ICourseInstructorFacade courseInsFacade;
	
	//Book Table facade
	@ManagedProperty("#{IBooksFacade}")
   	private IBooksFacade facade; 
	
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
		book=new Book();
		courseSyllabusCollection=new CourseSyllabusCollection();
		
		courseSyllabusCollection.setCourses(courseFacade.getcourseById(courseId));
		courseSyllabusCollection.setCourseInstructor(courseInsFacade.getAllInstructorsByCourseId(courseId));
		courseSyllabusCollection.setCourseTas(courseTaFacade.getByCourseId(courseId));
		courseSyllabusCollection.setPre_Requisites(preRequisitFacade.getByCourseId(courseId));
		courseSyllabusCollection.setCoRequisites(coRequisitFacade.getByCourseId(courseId));
		courseSyllabusCollection.setClos(cloFacade.getByCourseId(courseId));
		courseSyllabusCollection.setSos(soFacade.getByCourseId(courseId));
		courseSyllabusCollection.setGrades(gradeFacade.getByCourseId(courseId));
		courseSyllabusCollection.setNotes(noteFacade.getByCourseId(courseId));
		courseSyllabusCollection.setReferences(referencesFacade.getByCourseId(courseId));
		courseSyllabusCollection.setRelatedTopics(relatedTopicsFacade.getByCourseId(courseId));
		courseSyllabusCollection.setBooks(facade.getCoursesBookWithCourseId(courseId));

		return "/pages/secured/courseManagment/EditingcourseSyllabus.xhtml?faces-redirect=true";
		
	}
	
	public String endofEditing(){
		courseFacade.updateCourse(courseSyllabusCollection.getCourses());
		AddCourseInstructorToDataBase();
		AddCourseTaToDataBase();
		AddNotesToDataBase();
		AddReferencesToDataBase();
		AddRelatedTopicsToDataBase();
		AddGradesToDataBase();
		AddSosToDataBase();
		AddClosToDataBase();
		AddPre_RequisitesToDataBase();
		AddCoRequisitesToDataBase();
		AddBooksToDataBase();
		return "/pages/secured/courseManagment/courseSyllabus.xhtml?faces-redirect=true";
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
		System.out.println("Ok Ya Man");
		preRequisites.setCourseId(courseId);
		courseSyllabusCollection.getPre_Requisites().add(preRequisites);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "PreRequisite was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addPrerequisteDlg.hide();");
		preRequisites=new PreRequisites();
	}
	
	public void addBook(){
		System.out.println("Ok Ya Man");
Courses courses=new Courses();
courses.setId(courseSyllabusCollection.getCourses().getId());
courses.setName(courseSyllabusCollection.getCourses().getName());

		book.setCourse(courses);
		courseSyllabusCollection.getBooks().add(book);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book was added successfully");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addBookDlg.hide();");
		book=new Book();
	}
	private void AddCoRequisitesToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getCoRequisites().size();i++){
			coRequisitFacade.addCoRequisite(courseSyllabusCollection.getCoRequisites().get(i));
		}
	}

	private void AddPre_RequisitesToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getPre_Requisites().size();i++){
			preRequisitFacade.addPreRequisite(courseSyllabusCollection.getPre_Requisites().get(i));
		}
	}

	private void AddClosToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getClos().size();i++){
			cloFacade.addCLO(courseSyllabusCollection.getClos().get(i));
		}
	}

	private void AddSosToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getSos().size();i++){
			soFacade.addSO(courseSyllabusCollection.getSos().get(i));
		}
	}
	
	private void AddBooksToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getBooks().size();i++){
			BookDTO bookDTO=new BookDTO();
			bookDTO.setId(courseSyllabusCollection.getBooks().get(i).getId());
			bookDTO.setName(courseSyllabusCollection.getBooks().get(i).getName());
			bookDTO.setOriginalCopies(courseSyllabusCollection.getBooks().get(i).getOriginalCopies());
			bookDTO.setRemaingCopies(courseSyllabusCollection.getBooks().get(i).getRemaingCopies());
			bookDTO.setStatus(courseSyllabusCollection.getBooks().get(i).getStatus());
			bookDTO.setCourse(courseSyllabusCollection.getCourses());


			facade.add(bookDTO);
		}
	}

	private void AddGradesToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getGrades().size();i++){
			gradeFacade.addGrade(courseSyllabusCollection.getGrades().get(i));
		}
	}

	private void AddRelatedTopicsToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getRelatedTopics().size();i++){
			relatedTopicsFacade.addRelatedTopics(courseSyllabusCollection.getRelatedTopics().get(i));
		}
	}

	private void AddReferencesToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getReferences().size();i++){
			referencesFacade.addReference(courseSyllabusCollection.getReferences().get(i));
		}
	}

	private void AddNotesToDataBase() {
		for(int i=0;i<courseSyllabusCollection.getNotes().size();i++){
			noteFacade.addNote(courseSyllabusCollection.getNotes().get(i));
		}
	}

	private void AddCourseInstructorToDataBase() {
		
		for(int i=0;i<courseSyllabusCollection.getCourseInstructor().size();i++){
			int instId=courseSyllabusCollection.getCourseInstructor().get(i).getId();
			int courseId=courseSyllabusCollection.getCourses().getId();
			courseInsFacade.addInstructorToCourse(instId, courseId);
		}
		
	}

	private void AddCourseTaToDataBase() {
	
		for(int i=0;i<courseSyllabusCollection.getCourseTas().size();i++){
			courseTaFacade.addCourseTa(courseSyllabusCollection.getCourseTas().get(i));
		}
	
	}

	public List<CoursesDTO> getCoursesLst() {
		return coursesLst;
	}
	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}
	
	public List<Courses_Instructors> getCourseInstructorList() {
		return courseInstructorList;
	}
	public void setCourseInstructorList(
			List<Courses_Instructors> courseInstructorList) {
		this.courseInstructorList = courseInstructorList;
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

	public ICourseInstructorFacade getCourseInsFacade() {
		return courseInsFacade;
	}

	public void setCourseInsFacade(ICourseInstructorFacade courseInsFacade) {
		this.courseInsFacade = courseInsFacade;
	}

	public IBooksFacade getFacade() {
		return facade;
	}

	public void setFacade(IBooksFacade facade) {
		this.facade = facade;
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

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	
	
	
	
}
