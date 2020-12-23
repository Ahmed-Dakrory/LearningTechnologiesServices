package main.com.zc.service.instructorSurveyBean;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import main.com.zc.service.instructor_survey_ans.instructor_survey_ans;


public class ReportFileGenerationComments {

	HSSFWorkbook workbook;
	HSSFSheet sheet;
	HSSFRow row;
	HSSFCell cell;
	List<instructor_survey_ans> allanswersThisYearAndSemesterofinsPositive;
	List<instructor_survey_ans> allanswersThisYearAndSemesterofinsnegaitive;
	List<instructor_survey_ans> allanswersThisYearAndSemesterofinsTA;
	public ReportFileGenerationComments(List<instructor_survey_ans> allanswersThisYearAndSemesterofinsPositive,List<instructor_survey_ans> allanswersThisYearAndSemesterofinsnegaitive,List<instructor_survey_ans> allanswersThisYearAndSemesterofinsTA,
			HSSFWorkbook workbook2,
			HSSFSheet sheet2) {
		// TODO Auto-generated constructor stub
		this.workbook=workbook2;
		this.sheet=sheet2;
		this.allanswersThisYearAndSemesterofinsPositive=allanswersThisYearAndSemesterofinsPositive;
		this.allanswersThisYearAndSemesterofinsnegaitive=allanswersThisYearAndSemesterofinsnegaitive;
		this.allanswersThisYearAndSemesterofinsTA=allanswersThisYearAndSemesterofinsTA;
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
	
	
	public void generateReport(){
		sheet.setColumnWidth(0, 11000);
		sheet.setColumnWidth(1, 7000);
		for (int i=2;i<=4;i++) {

			sheet.setColumnWidth(i, 19000);
		}
	
		titleOfSheet();
	}
	
	
	public void titleOfSheet(){
		
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
	    for(int i=0;i<1;i++) {
	    	cell = row.createCell(2*i+2);
		    row.getCell(2*i+2).setCellStyle(style);
		    cell.setCellValue("Please respond to the following question only if your course is online or hybrid How do you rate the accessibility to the online material (e.g. communicated early enough (1 day or more) before the online session (if applicable), availability, clarity…etc.)?");
		    
		    
		    cell = row.createCell(2*i+3);
		    row.getCell(2*i+3).setCellStyle(style);
		    cell.setCellValue("General Comments (Please indicate both positive experiences and any suggestions you may have to improve the course)");
		    
		    
		    cell = row.createCell(2*i+4);
		    row.getCell(2*i+4).setCellStyle(style);
		    cell.setCellValue("TA Evaluation");
	    }
	 
	    int negSize = 0;
	    int posSize = 0;
	    int TaSize = 0;
	    int sizeoflist=0;
	    
	    
	    if(allanswersThisYearAndSemesterofinsnegaitive!=null) {
	    	
	    	 negSize = allanswersThisYearAndSemesterofinsnegaitive.size();
	    	
	    }
	    
		if(allanswersThisYearAndSemesterofinsPositive!=null) {
			    	
			  posSize = allanswersThisYearAndSemesterofinsPositive.size();
			    	
		}

		if(allanswersThisYearAndSemesterofinsTA!=null) {
			
			TaSize = allanswersThisYearAndSemesterofinsTA.size();
			
		}
	    
		
		int[] nums={posSize,negSize,TaSize};
		Arrays.sort(nums);
		
	    sizeoflist=nums[nums.length-1];
	    
	    

		
	    
	    if(allanswersThisYearAndSemesterofinsPositive!=null && posSize>0) {
	    	cell = row.createCell(0);
			   row.getCell(0).setCellStyle(style);
			   cell.setCellValue(allanswersThisYearAndSemesterofinsPositive.get(0).getInstructorId().getName());
			   
			   
			   cell = row.createCell(1);
			   row.getCell(1).setCellStyle(style);
			   cell.setCellValue(allanswersThisYearAndSemesterofinsPositive.get(0).getCourseId().getName());
			   
		    }else if(allanswersThisYearAndSemesterofinsnegaitive!=null && negSize>0) {
		    	cell = row.createCell(0);
				   row.getCell(0).setCellStyle(style);
				   cell.setCellValue(allanswersThisYearAndSemesterofinsnegaitive.get(0).getInstructorId().getName());
				   

				   cell = row.createCell(1);
				   row.getCell(1).setCellStyle(style);
				   cell.setCellValue(allanswersThisYearAndSemesterofinsnegaitive.get(1).getCourseId().getName());
			    
		    }else if(allanswersThisYearAndSemesterofinsTA!=null && TaSize>0) {
		    	cell = row.createCell(0);
				   row.getCell(0).setCellStyle(style);
				   cell.setCellValue(allanswersThisYearAndSemesterofinsTA.get(0).getInstructorId().getName());
				   

				   cell = row.createCell(1);
				   row.getCell(1).setCellStyle(style);
				   cell.setCellValue(allanswersThisYearAndSemesterofinsTA.get(1).getCourseId().getName());
			    
		    }
	    
	    
	    
	    
	    
	   for(int i=0;i<sizeoflist;i++) {
		   row = sheet.createRow(i+1);

		
		    
		    
		   int cell_column_index_Percentage = 2;
			  int cell_column_index_Person = 3;
			  int cell_column_index_PersonTA = 4;

			  if(allanswersThisYearAndSemesterofinsPositive!=null) {
					if(i < allanswersThisYearAndSemesterofinsPositive.size()) {
					  cell = row.createCell(cell_column_index_Percentage);
					  row.getCell(cell_column_index_Percentage).setCellStyle(style2);
					  cell.setCellValue(allanswersThisYearAndSemesterofinsPositive.get(i).getComment());
					}
				}	  
				
			  if(allanswersThisYearAndSemesterofinsnegaitive!=null) {
					if(i < allanswersThisYearAndSemesterofinsnegaitive.size()) {
					  cell = row.createCell(cell_column_index_Person);
					  row.getCell(cell_column_index_Person).setCellStyle(style2);
					  cell.setCellValue(allanswersThisYearAndSemesterofinsnegaitive.get(i).getComment());
					}
				}	
			  
			  
			  if(allanswersThisYearAndSemesterofinsTA!=null) {
					if(i < allanswersThisYearAndSemesterofinsTA.size()) {
					  cell = row.createCell(cell_column_index_PersonTA);
					  row.getCell(cell_column_index_PersonTA).setCellStyle(style2);
					  cell.setCellValue(allanswersThisYearAndSemesterofinsTA.get(i).getComment());
					}
				}	
			  
			  
				
		  
		   
	   }
	 
								   
	}
	
	
	

	

	
	
	
}
