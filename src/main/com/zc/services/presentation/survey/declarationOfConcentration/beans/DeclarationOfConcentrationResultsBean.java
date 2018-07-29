/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfConcentration.beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import main.com.zc.services.presentation.survey.declarationOfConcentration.facade.IDeclarationOfConcentrationFacade;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;
import org.primefaces.model.chart.PieChartModel;

/**
 * @author omnya
 *
 */
@ManagedBean(name="DeclarationOfConcentrationResultsBean")
@ViewScoped
public class DeclarationOfConcentrationResultsBean {

	private List<IntendedMajorSurveyDTO> results;
	private List<BaseDTO> concentrationsDTOLst;
	PieChartModel resultsChart = new PieChartModel();
	private List<IntendedMajorSurveyDTO> students;
	private IntendedMajorSurveyDTO selectedStudents;
	private List<IntendedMajorSurveyDTO> filteredStudents;
	@ManagedProperty("#{IDeclarationOfConcentrationFacade}")
	private IDeclarationOfConcentrationFacade facade;
	private List<BaseDTO> semesterLst;
	private Integer selectedSemester;
	private Integer selectedYear;
	private Integer selectedSemesterStudents;
	private Integer selectedYearStudents;
	private List<Integer> yearLst;
	private boolean renderChart;
	private boolean renderTable;
	@PostConstruct
	public void init()
	{
		 fillSemesterLst();
		 fillYearLst();
		    concentrationsDTOLst=new ArrayList<BaseDTO>();
		    concentrationsDTOLst=facade.getAllConcentrations();
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
		  for(int i=0;i<concentrationsDTOLst.size();i++)
		  {
			  List<IntendedMajorSurveyDTO> majorResult=new ArrayList<IntendedMajorSurveyDTO>();
				majorResult=facade.getAllByConcentrationIDAndYearAndSemester(concentrationsDTOLst.get(i).getId(),getSelectedYear(),getSelectedSemester());
				resultsChart.set(concentrationsDTOLst.get(i).getName() +" ------------- "+majorResult.size()+" Students", majorResult.size());
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
		 for(int i=0;i<concentrationsDTOLst.size();i++)
		  {
			  List<IntendedMajorSurveyDTO> majorResult=new ArrayList<IntendedMajorSurveyDTO>();
				majorResult=facade.getAllByConcentrationIDAndYearAndSemester(concentrationsDTOLst.get(i).getId(),getSelectedYear(),getSelectedSemester());
				resultsChart.set(concentrationsDTOLst.get(i).getName() +" ------------- "+majorResult.size()+" Students", majorResult.size());
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
		return concentrationsDTOLst;
	}
	public void setConcentrationsDTOLst(List<BaseDTO> concentrationsDTOLst) {
		this.concentrationsDTOLst = concentrationsDTOLst;
	}

	public IDeclarationOfConcentrationFacade getFacade() {
		return facade;
	}
	public void setFacade(IDeclarationOfConcentrationFacade facade) {
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
	
	
}
