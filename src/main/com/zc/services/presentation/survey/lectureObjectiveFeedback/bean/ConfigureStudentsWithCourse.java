/**
 * 
 */
package main.com.zc.services.presentation.survey.lectureObjectiveFeedback.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import main.com.zc.services.presentation.configuration.dto.StudentsCoursesNumberDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.IStudentsCoursesNumberFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ViewScoped
@ManagedBean
public class ConfigureStudentsWithCourse {

	@ManagedProperty("#{IStudentsCoursesNumberFacade}")
   	private IStudentsCoursesNumberFacade facade; 
	
	@ManagedProperty("#{StudentAcademicPetFacadeImpl}")
	private IStudentAcademicPetFacade courseFacade;
	
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;
	
	CoursesDTO selectedCourse;
	private List<CoursesDTO> filteredCourses;
	CoursesDTO updatedCourse;
	private Integer selectedYear;
	private Integer selectedSemester;
	private Integer studentsNum;
	private List<StudentsCoursesNumberDTO> coursesStudents;
	private List<StudentsCoursesNumberDTO> filteredCoursesStudents;
	StudentsCoursesNumberDTO selectedCoursesStudents;
	@PostConstruct
	public void init()
	{
		fillSemesterLst();
		updatedCourse=new CoursesDTO();
		coursesStudents=new ArrayList<StudentsCoursesNumberDTO>();
		selectedCoursesStudents=new StudentsCoursesNumberDTO();
	}
	public void fillSemesterLst()
	{
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		//semesterLst.add(new BaseDTO(3,"Winter"));
	}
	public void fillYearLst(AjaxBehaviorEvent event)
	{
		 yearLst=new ArrayList<Integer>();
		 
		 for(int i=2013;i<2031;i++)
		{
			yearLst.add(i);
		}
	}
	public void fillCourses(AjaxBehaviorEvent event)
	{
		//TODO 
		//get courses given semester and year
		 List<CoursesDTO> courses=new ArrayList<CoursesDTO>();
		courses=courseFacade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
		fillStudentsAndCourses(courses);
	}
	public void fillStudentsAndCourses(List<CoursesDTO> lst){
		coursesStudents=new ArrayList<StudentsCoursesNumberDTO>();
		for(int i=0;i<lst.size();i++)
		{
			StudentsCoursesNumberDTO dto=new StudentsCoursesNumberDTO();
			dto.setCourse(lst.get(i));
			StudentsCoursesNumberDTO old=new StudentsCoursesNumberDTO();
			old=facade.getByCourseID(lst.get(i).getId());
			if(old!=null)
			{
				dto.setNum(old.getNum());
			}
			else 
				dto.setNum(0);
			
			coursesStudents.add(dto);
		}
	}
	public void preUpdate(CoursesDTO course){
		setUpdatedCourse(course);
		setStudentsNum(null);
	}
	public void updateCourse(){
		StudentsCoursesNumberDTO dto=new StudentsCoursesNumberDTO();
		
		dto.setNum(getStudentsNum());
		dto.setCourse(getUpdatedCourse());
		StudentsCoursesNumberDTO updatedDTO=new StudentsCoursesNumberDTO();
		updatedDTO=facade.update(dto);
		if(getUpdatedCourse()!=null)
		{
		setSelectedCoursesStudents(updatedDTO);
		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Number is updated successfully!");
		 List<CoursesDTO> courses=new ArrayList<CoursesDTO>();
			courses=courseFacade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
			fillStudentsAndCourses(courses);
		}
	}
	public IStudentsCoursesNumberFacade getFacade() {
		return facade;
	}
	public void setFacade(IStudentsCoursesNumberFacade facade) {
		this.facade = facade;
	}
	public IStudentAcademicPetFacade getCourseFacade() {
		return courseFacade;
	}
	public void setCourseFacade(IStudentAcademicPetFacade courseFacade) {
		this.courseFacade = courseFacade;
	}
	public List<BaseDTO> getSemesterLst() {
		return semesterLst;
	}
	public void setSemesterLst(List<BaseDTO> semesterLst) {
		this.semesterLst = semesterLst;
	}
	public List<Integer> getYearLst() {
		return yearLst;
	}
	public void setYearLst(List<Integer> yearLst) {
		this.yearLst = yearLst;
	}

	public CoursesDTO getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(CoursesDTO selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public List<CoursesDTO> getFilteredCourses() {
		return filteredCourses;
	}
	public void setFilteredCourses(List<CoursesDTO> filteredCourses) {
		this.filteredCourses = filteredCourses;
	}
	public CoursesDTO getUpdatedCourse() {
		return updatedCourse;
	}
	public void setUpdatedCourse(CoursesDTO updatedCourse) {
		this.updatedCourse = updatedCourse;
	}
	public Integer getSelectedYear() {
		return selectedYear;
	}
	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}
	public Integer getSelectedSemester() {
		return selectedSemester;
	}
	public void setSelectedSemester(Integer selectedSemester) {
		this.selectedSemester = selectedSemester;
	}
	public Integer getStudentsNum() {
		return studentsNum;
	}
	public void setStudentsNum(Integer studentsNum) {
		this.studentsNum = studentsNum;
	}
	public List<StudentsCoursesNumberDTO> getCoursesStudents() {
		return coursesStudents;
	}
	public void setCoursesStudents(List<StudentsCoursesNumberDTO> coursesStudents) {
		this.coursesStudents = coursesStudents;
	}
	public List<StudentsCoursesNumberDTO> getFilteredCoursesStudents() {
		return filteredCoursesStudents;
	}
	public void setFilteredCoursesStudents(
			List<StudentsCoursesNumberDTO> filteredCoursesStudents) {
		this.filteredCoursesStudents = filteredCoursesStudents;
	}
	public StudentsCoursesNumberDTO getSelectedCoursesStudents() {
		return selectedCoursesStudents;
	}
	public void setSelectedCoursesStudents(
			StudentsCoursesNumberDTO selectedCoursesStudents) {
		this.selectedCoursesStudents = selectedCoursesStudents;
	}

	
}
