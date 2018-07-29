/**
 * 
 */
package main.com.zc.services.presentation.survey.lectureObjectiveFeedback.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.CoursesPercentageDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.WeekFeedbackResultsDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.ISemesterWeeksFacade;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.IWeekFeedbackFacade;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ViewScoped
@ManagedBean
public class FeedbackResultsBean {

	@ManagedProperty("#{IWeekFeedbackFacade}")
	private IWeekFeedbackFacade ansFacade;
	
	@ManagedProperty("#{ISemesterWeeksFacade}")
   	private ISemesterWeeksFacade weeksFacade; 
	
		List<CoursesPercentageDTO> courses;
		List<SemesterWeeksDTO> weeks;
		private SemesterWeeksDTO selectedWeek;
		private List<CoursesPercentageDTO> filteredCourses;
		CoursesPercentageDTO selectedCourses;
		private Integer selectedYear;
		private Integer selectedSemester;
		private List<BaseDTO> semesterLst;
		private List<Integer> yearLst;
		private List<WeekFeedbackResultsDTO> details;
		private Integer totalNum;
		private String sendingStatus;
		@PostConstruct
		public void init(){
			selectedCourses=new CoursesPercentageDTO();
			fillSemesterLst();
			selectedWeek=new SemesterWeeksDTO();
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
		public void fillweeks(AjaxBehaviorEvent event)
		{
			//TODO 
			//get weeks given semester and year
			weeks=weeksFacade.getBySemesterAndyear(getSelectedSemester(), getSelectedYear());
		}
		public void fillcoursesFeedback(AjaxBehaviorEvent event){
			courses=new ArrayList<CoursesPercentageDTO>(); 
			courses=ansFacade.getPercentageofCourse(getSelectedWeek());
			totalNum=ansFacade.getTotalNumOfParticipatedStudents(getSelectedWeek());
		}
		
		public void displayDetails(CoursesPercentageDTO course){
			details=new ArrayList<WeekFeedbackResultsDTO>();
			details=ansFacade.getResultsDetails(course.getId(),course.getCoordinator().getId(),
					getSelectedWeek().getId());
			for(int i=0;i<details.size();i++)
			{
				System.out.println("Question : "+details.get(i).getQuestionName());
				System.out.println("Question ID"+details.get(i).getQuestionName());
				for(int j=0;j<details.get(i).getSelectionsStatistics().size();j++)
				{
					System.out.println("Selection ID : "+details.get(i).getSelectionsStatistics().get(j).getId());
					System.out.println("Selection Name : "+details.get(i).getSelectionsStatistics().get(j).getName());
					System.out.println("Selections num : "+details.get(i).getSelectionsStatistics().get(j).getFileNo());
				}
			}	
		}
		public void sendToAll(){
			sendingStatus="Done for : ";
			for(int i=0;i<courses.size();i++)
			{try{
				sendingStatus="";
				if(courses.get(i).getPersentage()>=(double)25){
					//TODO
					//Send emails of results to instructors
					boolean b=ansFacade.sendEmail(courses.get(i), getSelectedWeek(), ansFacade.getResultsDetails(courses.get(i).getId(),courses.get(i).getCoordinator().getId(),
					getSelectedWeek().getId()));
					if(b)
					{
						System.out.println("Sending is done for course :"+courses.get(i).getName()+" and for instructor : "+courses.get(i).getCoordinator().getName()+" with email "
										+ courses.get(i).getCoordinator().getMail());
						sendingStatus+=courses.get(i).getName()+", \r\n";
					}
					else {
						System.out.println("Sending is failed for course :"+courses.get(i).getName()+" and for instructor : "+courses.get(i).getCoordinator().getName()+" with email "
								+ courses.get(i).getCoordinator().getMail());
					}
				}
			}
			
			catch(Exception ex){
				ex.printStackTrace();
				System.out.println("Sending is failed for course :"+courses.get(i).getName()+" and for instructor : "+courses.get(i).getCoordinator().getName()+" with email "
						+ courses.get(i).getCoordinator().getMail());
}
			}
			
		}
		public void sendToSpecificCourse(CoursesPercentageDTO course){
			try{
				sendingStatus="";
			
					//TODO
					//Send emails of results to instructors
					boolean b=ansFacade.sendEmail(course, getSelectedWeek(), ansFacade.getResultsDetails(course.getId(),course.getCoordinator().getId(),
					getSelectedWeek().getId()));
					if(b)
					{
						System.out.println("Sending is done for course :"+course.getName()+" and for instructor : "+course.getCoordinator().getName()+" with email "
										+ course.getCoordinator().getMail());
						sendingStatus+=course.getName()+", \r\n";
					}
					else {
						System.out.println("Sending is failed for course :"+course.getName()+" and for instructor : "+course.getCoordinator().getName()+" with email "
								+ course.getCoordinator().getMail());
					}
			}
			
			catch(Exception ex){
				ex.printStackTrace();
				System.out.println("Sending is failed for course :"+course.getName()+" and for instructor : "+course.getCoordinator().getName()+" with email "
						+ course.getCoordinator().getMail());

			}
		}
		public IWeekFeedbackFacade getAnsFacade() {
			return ansFacade;
		}

		public void setAnsFacade(IWeekFeedbackFacade ansFacade) {
			this.ansFacade = ansFacade;
		}

		public List<CoursesPercentageDTO> getCourses() {
			return courses;
		}

		public void setCourses(List<CoursesPercentageDTO> courses) {
			this.courses = courses;
		}

		public SemesterWeeksDTO getSelectedWeek() {
			return selectedWeek;
		}

		public void setSelectedWeek(SemesterWeeksDTO selectedWeek) {
			this.selectedWeek = selectedWeek;
		}

		public List<CoursesPercentageDTO> getFilteredCourses() {
			return filteredCourses;
		}

		public void setFilteredCourses(List<CoursesPercentageDTO> filteredCourses) {
			this.filteredCourses = filteredCourses;
		}

		public CoursesPercentageDTO getSelectedCourses() {
			return selectedCourses;
		}

		public void setSelectedCourses(CoursesPercentageDTO selectedCourses) {
			this.selectedCourses = selectedCourses;
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
		public ISemesterWeeksFacade getWeeksFacade() {
			return weeksFacade;
		}
		public void setWeeksFacade(ISemesterWeeksFacade weeksFacade) {
			this.weeksFacade = weeksFacade;
		}
		public List<SemesterWeeksDTO> getWeeks() {
			return weeks;
		}
		public void setWeeks(List<SemesterWeeksDTO> weeks) {
			this.weeks = weeks;
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
		public List<WeekFeedbackResultsDTO> getDetails() {
			return details;
		}
		public void setDetails(List<WeekFeedbackResultsDTO> details) {
			this.details = details;
		}
		public Integer getTotalNum() {
			return totalNum;
		}
		public void setTotalNum(Integer totalNum) {
			this.totalNum = totalNum;
		}
		public String getSendingStatus() {
			return sendingStatus;
		}
		public void setSendingStatus(String sendingStatus) {
			this.sendingStatus = sendingStatus;
		}
		
		
}
