/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.ISkipMajorHeadCoursesFacade;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.ITAJuniorProgramFacadeStudent;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean(name="TAJuniorProgramStudentBean")
@ViewScoped
public class TAJuniorProgramStudentBean {

	@ManagedProperty("#{ITAJuniorProgramFacadeStudent}")
	private ITAJuniorProgramFacadeStudent facade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
   	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    
    @ManagedProperty("#{StudentAcademicPetFacadeImpl}")
    private IStudentAcademicPetFacade coursefacade;
    @ManagedProperty("#{ISkipMajorHeadCoursesFacade}")
   	private ISkipMajorHeadCoursesFacade skipMajorHead;
    
    @ManagedProperty("#{IMajorsFacade}")
   	private IMajorsFacade majorFacade;
    
    
    
	private List<TAJuniorProgramDTO> pendingForms;
	private List<TAJuniorProgramDTO> archievedForms;
	private TAJuniorProgramDTO selectedPendingForms;
	private List<TAJuniorProgramDTO> filteredPendingForms;
	private TAJuniorProgramDTO selectedArchievedForms;
	private List<TAJuniorProgramDTO> filteredArchievedForms;
	private List<CoursesDTO> coursesLst;
	private List<MajorDTO> majorLst;
	private TAJuniorProgramDTO detailedForm;
	private Integer selectedCourseID;
	private Integer selectedMajor;
	private List<BaseDTO> semesterLst;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<Integer> yearLst;
    private TAJuniorProgramDTO addedForm;
 @PostConstruct
    public void init()
    {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			if(authentication.getName().startsWith("S-")||authentication.getName().startsWith("s-")||StringUtils.isNumeric(authentication.getName().substring(0, 4)))
			{
				addedForm=new TAJuniorProgramDTO();
		fillPendingLst();
		fillArchievedLst();
		fillSemesterLst();
		fillMajorLst();
		setSelectedCourseID(null);
		try
		{	HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			setSelectedPendingForms(facade.getByID(dtoID));
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form has been submitted successfully" );
		}
		catch(Exception ex)
		{
			System.out.println("Not redirect");
		}
			
		}
			else 
			{
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only for students");
				try {

					FacesContext.getCurrentInstance().getExternalContext().redirect("/LearningTechnologiesServices/pages/secured/dashboard.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		

		
		}
		else 
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only for students");
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/LearningTechnologiesServices/pages/public/login.xhtml");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    public void fillPendingLst()
	{
		pendingForms=new ArrayList<TAJuniorProgramDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
				pendingForms=facade.getPendingRequestsOfStudent(studentDataFacade.getPersonByPersonMail(mail).getId());
				for(int i=0;i<pendingForms.size();i++)
			    {
			    	 if(pendingForms.get(i).getStep().equals(PetitionStepsEnum.PA))
					    {
					    	if(skipMajorHead.checkCourse(pendingForms.get(i).getCourse().getId()))
					    	{
					    		pendingForms.get(i).setCurStatus("Reviewd by Course coordinator("+pendingForms.get(i).getCourse().getCoordinator().getName()+")");
					    		//getDetailedDTO().setNextStatus("Reviewing by Dean of students("+getDetailedDTO().getCourse().getCoordinator().getName()+")");
					    	}
					    }
			    }
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,"Not Allowed To see This Petitions" );
			}
		}

		}
	
	public void fillArchievedLst()
	{
		archievedForms=new ArrayList<TAJuniorProgramDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
				archievedForms=facade.getArchievedRequestsOfStudent(studentDataFacade.getPersonByPersonMail(mail).getId());
				
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}	
	}
	
	public void fillCourseLst(AjaxBehaviorEvent event)
	{
		//coursesLst=facade.getAllCourses();
		coursesLst=coursefacade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
		
	}
	public void fillMajorLst()
	{
		//majorLst=facade.getAllMajors();
		majorLst=majorFacade.getAll();
		
	}
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		TAJuniorProgramDTO selectedDTO=(TAJuniorProgramDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	public void fillSemesterLst()
	{
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		semesterLst.add(new BaseDTO(3,"Winter"));
		
	}
	public void fillYearLst(AjaxBehaviorEvent event)
	{
		 yearLst=new ArrayList<Integer>();
		 setSelectedYear(null);
		 setSelectedCourseID(null);
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(i);
		}
	}
	public void showDetails(TAJuniorProgramDTO form)
	{
				try {
    		
    		
    			FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=Stduent&oldMood=1");
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		public void submitForm()
		{
			
			
			try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				String mail = authentication.getName();
				if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
					
				    StudentDTO student=new StudentDTO();
					student.setId(studentDataFacade.getPersonByPersonMail(mail).getId());
					
			        getAddedForm().setStudent(student);
			        CoursesDTO course =new CoursesDTO();
			        course.setId(getSelectedCourseID());
			        getAddedForm().setCourse(course);
					
					MajorDTO major =new MajorDTO();
					major.setId(getSelectedMajor());
					getAddedForm().setMajor(major);
					
				
					//getAddedForm().setStatus(PetitionStepsEnum.UNDER_REVIEW.getName());
					getAddedForm().setSubmissionDate(Calendar.getInstance());
					getAddedForm().setStep(PetitionStepsEnum.UNDER_REVIEW);
					
					//getAddedForm().setComment(getReason());
					

					TAJuniorProgramDTO newAddedForm=facade.submitRequest(getAddedForm());
					
			        if(newAddedForm!=null)
			        {
			        	
			        	init();
			        	FacesContext.getCurrentInstance().getExternalContext().redirect
						("taJuniorProgramStudent.xhtml?id="+newAddedForm.getId());
			        	setSelectedPendingForms(newAddedForm);
			        	JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form has been submitted successfully" );
			        	sharedAcademicPetFacade.notifayNextStepOwner(newAddedForm);
			        	
			        }
			        else 
			        {
			        	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can't Be Submitted");
			        }
			        
				}
			}
			 else 
		        {
				 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only For students");
		        }
			}
			catch(Exception ex){
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can not Be Submitted");
			}
		}
		
	
	public ITAJuniorProgramFacadeStudent getFacade() {
		return facade;
	}
	public void setFacade(ITAJuniorProgramFacadeStudent facade) {
		this.facade = facade;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}
	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}
	public IStudentAcademicPetFacade getCoursefacade() {
		return coursefacade;
	}
	public void setCoursefacade(IStudentAcademicPetFacade coursefacade) {
		this.coursefacade = coursefacade;
	}
	public List<TAJuniorProgramDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<TAJuniorProgramDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<TAJuniorProgramDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<TAJuniorProgramDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	public TAJuniorProgramDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(TAJuniorProgramDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<TAJuniorProgramDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(
			List<TAJuniorProgramDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public TAJuniorProgramDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(TAJuniorProgramDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<TAJuniorProgramDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<TAJuniorProgramDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public List<CoursesDTO> getCoursesLst() {
		return coursesLst;
	}
	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}
	public List<MajorDTO> getMajorLst() {
		return majorLst;
	}
	public void setMajorLst(List<MajorDTO> majorLst) {
		this.majorLst = majorLst;
	}
	public TAJuniorProgramDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(TAJuniorProgramDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public Integer getSelectedCourseID() {
		return selectedCourseID;
	}
	public void setSelectedCourseID(Integer selectedCourseID) {
		this.selectedCourseID = selectedCourseID;
	}

	public List<BaseDTO> getSemesterLst() {
		return semesterLst;
	}
	public void setSemesterLst(List<BaseDTO> semesterLst) {
		this.semesterLst = semesterLst;
	}
	public Integer getSelectedSemester() {
		return selectedSemester;
	}
	public void setSelectedSemester(Integer selectedSemester) {
		this.selectedSemester = selectedSemester;
	}
	public Integer getSelectedYear() {
		return selectedYear;
	}
	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}
	public List<Integer> getYearLst() {
		return yearLst;
	}
	public void setYearLst(List<Integer> yearLst) {
		this.yearLst = yearLst;
	}
	public Integer getSelectedMajor() {
		return selectedMajor;
	}
	public void setSelectedMajor(Integer selectedMajor) {
		this.selectedMajor = selectedMajor;
	}
	public TAJuniorProgramDTO getAddedForm() {
		return addedForm;
	}
	public void setAddedForm(TAJuniorProgramDTO addedForm) {
		this.addedForm = addedForm;
	}
	public ISkipMajorHeadCoursesFacade getSkipMajorHead() {
		return skipMajorHead;
	}
	public void setSkipMajorHead(ISkipMajorHeadCoursesFacade skipMajorHead) {
		this.skipMajorHead = skipMajorHead;
	}
	public IMajorsFacade getMajorFacade() {
		return majorFacade;
	}
	public void setMajorFacade(IMajorsFacade majorFacade) {
		this.majorFacade = majorFacade;
	}

}
