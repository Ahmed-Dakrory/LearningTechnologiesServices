package main.com.zc.service.instructorSurveyBean;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import main.com.zc.service.instructor_survey_ans.Iinstructor_survey_ansAppService;
import main.com.zc.service.instructor_survey_ans.instructor_survey_ans;
import main.com.zc.service.instructor_survey_ques.instructor_survey_ques;


public class ReportFileGeneration {

	HSSFWorkbook workbook;
	HSSFSheet sheet;
	HSSFRow row;
	HSSFCell cell;
	List<cloThreshold> allCoursesThresoldResults;
	List<instructor_survey_ans> allanswersThisYearAndSemesterofins;
	Iinstructor_survey_ansAppService instructor_survey_ansFacade;
	private Integer yearSelected;
	private Integer semesterSelected;
	public ReportFileGeneration(List<cloThreshold> allCoursesThresoldResults,HSSFWorkbook wb,HSSFSheet sheet, Iinstructor_survey_ansAppService instructor_survey_ansFacade, Integer yearSelected, Integer semesterSelected) {
		// TODO Auto-generated constructor stub
		this.workbook=wb;
		this.sheet=sheet;
		this.allCoursesThresoldResults=allCoursesThresoldResults;
		this.instructor_survey_ansFacade=instructor_survey_ansFacade;
		this.yearSelected=yearSelected;
		this.semesterSelected=semesterSelected;
	}


	




	public static String toFraction(float d, int factor) {
	   /* StringBuilder sb = new StringBuilder();
	    if (d < 0) {
	        sb.append('-');
	        d = -d;
	    }
	    long l = (long) d;
	    if (l != 0) sb.append(l);
	    d -= l;
	    double error = Math.abs(d);
	    int bestDenominator = 1;
	    for(int i=2;i<=factor;i++) {
	        double error2 = Math.abs(d - (double) Math.round(d * i) / i);
	        if (error2 < error) {
	            error = error2;
	            bestDenominator = i;
	        }
	    }
	    if (bestDenominator > 1)
	        sb.append(' ').append(Math.round(d * bestDenominator)).append('/') .append(bestDenominator);
	    return sb.toString();
	    
	    */
		return String.format("%.2f", d);
	}
	
	
	public void generateReport(List<instructor_survey_ques>  instructor_survey_ques){
		sheet.setColumnWidth(0, 11000);
		sheet.setColumnWidth(1, 7000);
		for (int i=2;i<=(2*instructor_survey_ques.size())-5;i++) {
			sheet.setColumnWidth(i, 17000);
		}
		sheet.setColumnWidth((2*instructor_survey_ques.size())-4, 7000);
		
		titleOfSheet(instructor_survey_ques);
	}
	
	
	public void titleOfSheet(List<instructor_survey_ques>  instructor_survey_ques){
		
		//Title	
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_CENTER);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 12);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
	    
	    HSSFCellStyle style2 = workbook.createCellStyle();
	    style2.setAlignment(CellStyle.ALIGN_CENTER);
	    HSSFFont font2 = workbook.createFont();
	    font2.setFontHeightInPoints((short) 10);
	    style2.setFont(font2); 
	    
	    
	    row = sheet.createRow(0);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Instructor");
	    
	    cell = row.createCell(1);
	    row.getCell(1).setCellStyle(style);
	    cell.setCellValue("Course Code");
	    
	    
	    for(int i=0;i<instructor_survey_ques.size()-3;i++) {
	    	cell = row.createCell(2*i+2);
		    row.getCell(2*i+2).setCellStyle(style);
		    cell.setCellValue(instructor_survey_ques.get(i).getQues());
		    
		    
		    cell = row.createCell(2*i+3);
		    row.getCell(2*i+3).setCellStyle(style);
		    cell.setCellValue("Number of Persons");
	    }
	 
	    int iOfNumberOfStudents = 2*(instructor_survey_ques.size()-3)+3;
	    cell = row.createCell(iOfNumberOfStudents);
	    row.getCell(iOfNumberOfStudents).setCellStyle(style);
	    cell.setCellValue("Total Number Of Students");
	    
	    
	    
	    
	    
	    
	    
	    
	   for(int i=0;i<allCoursesThresoldResults.size();i++) {
		   row = sheet.createRow(i+1);

		
		    
		    
		   cell = row.createCell(0);
		   row.getCell(0).setCellStyle(style);
		   cell.setCellValue(allCoursesThresoldResults.get(i).getListOfCourseAnswers().get(0).getInstructorId().getName());
		   
		   
		   cell = row.createCell(1);
		   row.getCell(1).setCellStyle(style);
		   cell.setCellValue(allCoursesThresoldResults.get(i).getListOfCourseAnswers().get(0).getCourseId().getName());
		   
		   for(int j=0;j<instructor_survey_ques.size()-3;j++) {
			  int cell_column_index_Percentage = 2*j+2;
			  int cell_column_index_Person = 2*j+3;

			  cell = row.createCell(cell_column_index_Percentage);
			  row.getCell(cell_column_index_Percentage).setCellStyle(style2);
			  cell.setCellValue(toFraction(allCoursesThresoldResults.get(i).getCloThresholdPercentage()[j],1000));
			  

			  cell = row.createCell(cell_column_index_Person);
			  row.getCell(cell_column_index_Person).setCellStyle(style2);
			  cell.setCellValue(allCoursesThresoldResults.get(i).getCloThresholdPersons()[j]); 
		   }
		   
		   
		   iOfNumberOfStudents = 2*(instructor_survey_ques.size()-3)+3;
		   cell = row.createCell(iOfNumberOfStudents);
			  row.getCell(iOfNumberOfStudents).setCellStyle(style2);
			  List<instructor_survey_ans> x = instructor_survey_ansFacade.getAllByCourseAndInstructorAndYearAndSemesterGroupbyStudentId(allCoursesThresoldResults.get(i).getListOfCourseAnswers().get(0).getCourseId().getId(), allCoursesThresoldResults.get(i).getListOfCourseAnswers().get(0).getInstructorId().getId(), this.yearSelected, this.semesterSelected);
			  cell.setCellValue(x.size()); 
		   
		  
		   
	   }
	 
								   
	}
	
	
	

	

	
	
	
}
