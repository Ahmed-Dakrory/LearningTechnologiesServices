/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfMinor.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import main.com.zc.services.domain.survey.model.OfficialMajor;
import main.com.zc.services.presentation.accountSetting.facade.impl.StudentProfileFacadeImpl;
import main.com.zc.services.presentation.shared.facade.impl.MajorsFacadeImpl;
import main.com.zc.services.presentation.survey.declarationOfMinor.facade.IDeclarationOfMinorFacade;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean(name="DeclarationOfMinorResultsBean")
@SessionScoped
public class DeclarationOfMinorResultsBean {

	private List<IntendedMajorSurveyDTO> results;
	private List<BaseDTO> minorsDTOLst;
	PieChartModel resultsChart = new PieChartModel();
	private List<IntendedMajorSurveyDTO> students;
	private IntendedMajorSurveyDTO selectedStudents;
	private List<IntendedMajorSurveyDTO> filteredStudents;
	@ManagedProperty("#{IDeclarationOfMinorFacade}")
	private IDeclarationOfMinorFacade facade;
	private List<BaseDTO> semesterLst;
	private Integer selectedSemester;
	private Integer selectedYear;
	private Integer selectedSemesterStudents;
	private Integer selectedYearStudents;
	private List<Integer> yearLst;
	private boolean renderChart;
	private boolean renderTable;
	
	

	private IntendedMajorSurveyDTO studentOfMajor;

    @ManagedProperty("#{IStudentProfileFacade}")
    private StudentProfileFacadeImpl profileFacade;

	@ManagedProperty("#{IMajorsFacade}")
	private MajorsFacadeImpl majorfacade2;
	
	private boolean canAccept=false;
	
	
	@PostConstruct
	public void init()
	{
		 fillSemesterLst();
		 fillYearLst();
		    minorsDTOLst=new ArrayList<BaseDTO>();
		    minorsDTOLst=facade.getAllMinors();
	       /* fillPieChart();
	        fillResultsTable();*/
	}
	public void fillSemesterLst()
	{
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		//semesterLst.add(new BaseDTO(3,"Winter"));
	}
	public void fillYearLst()
	{
		 yearLst=new ArrayList<Integer>();
		setSelectedYear(null);
		
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(i);
		}
	}
	public void fillPieChart()
	{
		  for(int i=0;i<minorsDTOLst.size();i++)
		  {
			  List<IntendedMajorSurveyDTO> majorResult=new ArrayList<IntendedMajorSurveyDTO>();
				majorResult=facade.getAllByMinorIDAndYearAndSemester(minorsDTOLst.get(i).getId(),getSelectedYear(),getSelectedSemester());
				resultsChart.set(minorsDTOLst.get(i).getName() +" ------------- "+majorResult.size()+" Students", majorResult.size());
		  }
	}
	public void fillResultsTable()
	{
		
		students=new ArrayList<IntendedMajorSurveyDTO>();
		//students=facade.getAllReults();
		students=facade.getAllByYearAndSemester(getSelectedYearStudents(),getSelectedSemesterStudents());
	}
	public void updateChart()
	{
		resultsChart = new PieChartModel();
		results=new ArrayList<IntendedMajorSurveyDTO>();
		renderChart=true;
		 for(int i=0;i<minorsDTOLst.size();i++)
		  {
			  List<IntendedMajorSurveyDTO> majorResult=new ArrayList<IntendedMajorSurveyDTO>();
				majorResult=facade.getAllByMinorIDAndYearAndSemester(minorsDTOLst.get(i).getId(),getSelectedYear(),getSelectedSemester());
				resultsChart.set(minorsDTOLst.get(i).getName() +" ------------- "+majorResult.size()+" Students", majorResult.size());
		  }
	}
	
	
	public void approve() {
		studentOfMajor.setState(OfficialMajor.STATE_ACCEPTED);
		facade.update(studentOfMajor);
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Major Selection Accepted!");
	}
	
	public void refuse() {
		studentOfMajor.setState(OfficialMajor.STATE_Refused);
		facade.update(studentOfMajor);
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Major Selection Refused!");
	}
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		studentOfMajor=(IntendedMajorSurveyDTO) event.getObject();
	  		
	  		studentOfMajor = facade.getByStudentID(studentOfMajor.getStudent().getId());
			showDetails(studentOfMajor);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	private void showDetails(IntendedMajorSurveyDTO selectedDTO) {
		// TODO Auto-generated method stub
		StudentProfileDTO profileStudentSelected = profileFacade.getCurrentPRofileByStudentID(selectedDTO.getStudent().getId());
		selectedDTO.getStudent().setStudentProfileDTO(profileStudentSelected);
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			
		List<MajorDTO> majors=majorfacade2.getMajorsWithConcentrations();
		for (int i=0;i<majors.size();i++) {
			if(mail.toLowerCase().equals(majors.get(i).getHeadOfMajor().getMail().toLowerCase()))
			{
				canAccept=true;
				
				
			}
		}
		
		
try {
    		
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("formDetails.xhtml");
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		}
	}
	
	
	public void updateTable()
	{
		renderTable=true;
		fillResultsTable();
	}
	public List<IntendedMajorSurveyDTO> getResults() {
		return results;
	}
	public void setResults(List<IntendedMajorSurveyDTO> results) {
		this.results = results;
	}
	public PieChartModel getResultsChart() {
		return resultsChart;
	}
	public void setResultsChart(PieChartModel resultsChart) {
		this.resultsChart = resultsChart;
	}
	public List<BaseDTO> getConcentrationsDTOLst() {
		return minorsDTOLst;
	}
	public void setConcentrationsDTOLst(List<BaseDTO> minorsDTOLst) {
		this.minorsDTOLst = minorsDTOLst;
	}

	public IDeclarationOfMinorFacade getFacade() {
		return facade;
	}
	public void setFacade(IDeclarationOfMinorFacade facade) {
		this.facade = facade;
	}
	public List<IntendedMajorSurveyDTO> getStudents() {
		return students;
	}
	public void setStudents(List<IntendedMajorSurveyDTO> students) {
		this.students = students;
	}
	public IntendedMajorSurveyDTO getSelectedStudents() {
		return selectedStudents;
	}
	public void setSelectedStudents(IntendedMajorSurveyDTO selectedStudents) {
		this.selectedStudents = selectedStudents;
	}
	public List<IntendedMajorSurveyDTO> getFilteredStudents() {
		return filteredStudents;
	}
	public void setFilteredStudents(List<IntendedMajorSurveyDTO> filteredStudents) {
		this.filteredStudents = filteredStudents;
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
	public Integer getSelectedSemesterStudents() {
		return selectedSemesterStudents;
	}
	public void setSelectedSemesterStudents(Integer selectedSemesterStudents) {
		this.selectedSemesterStudents = selectedSemesterStudents;
	}
	public Integer getSelectedYearStudents() {
		return selectedYearStudents;
	}
	public void setSelectedYearStudents(Integer selectedYearStudents) {
		this.selectedYearStudents = selectedYearStudents;
	}
	public List<Integer> getYearLst() {
		return yearLst;
	}
	public void setYearLst(List<Integer> yearLst) {
		this.yearLst = yearLst;
	}
	public boolean isRenderChart() {
		return renderChart;
	}
	public void setRenderChart(boolean renderChart) {
		this.renderChart = renderChart;
	}
	public boolean isRenderTable() {
		return renderTable;
	}
	public void setRenderTable(boolean renderTable) {
		this.renderTable = renderTable;
	}
	public IntendedMajorSurveyDTO getStudentOfMajor() {
		return studentOfMajor;
	}
	public void setStudentOfMajor(IntendedMajorSurveyDTO studentOfMajor) {
		this.studentOfMajor = studentOfMajor;
	}
	public StudentProfileFacadeImpl getProfileFacade() {
		return profileFacade;
	}
	public void setProfileFacade(StudentProfileFacadeImpl profileFacade) {
		this.profileFacade = profileFacade;
	}
	public MajorsFacadeImpl getMajorfacade2() {
		return majorfacade2;
	}
	public void setMajorfacade2(MajorsFacadeImpl majorfacade2) {
		this.majorfacade2 = majorfacade2;
	}
	public boolean isCanAccept() {
		return canAccept;
	}
	public void setCanAccept(boolean canAccept) {
		this.canAccept = canAccept;
	}
	
	
}
