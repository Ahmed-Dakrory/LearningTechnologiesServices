package main.com.zc.service.instructorSurveyAllBean;

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

import main.com.zc.service.instructor_all_survey_ans.instructor_all_survey_ans;


public class ReportFileGenerationComments {

	HSSFWorkbook workbook;
	HSSFSheet sheet;
	HSSFRow row;
	HSSFCell cell;
	List<instructor_all_survey_ans> allanswersThisYearAndSemesterofinsPositive;
	public ReportFileGenerationComments(List<instructor_all_survey_ans> allanswersThisYearAndSemesterofinsPositive,
			HSSFWorkbook workbook2,
			HSSFSheet sheet2) {
		// TODO Auto-generated constructor stub
		this.workbook=workbook2;
		this.sheet=sheet2;
		this.allanswersThisYearAndSemesterofinsPositive=allanswersThisYearAndSemesterofinsPositive;
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
		    cell.setCellValue("Questions ");
		    
		    
		    cell = row.createCell(2*i+3);
		    row.getCell(2*i+3).setCellStyle(style);
		    cell.setCellValue("Comments");
		    
	    }
	 
	    int negSize = 0;
	    int posSize = 0;
	    int TaSize = 0;
	    int sizeoflist=0;
	    
	    
	
	    
		if(allanswersThisYearAndSemesterofinsPositive!=null) {
			    	
			  posSize = allanswersThisYearAndSemesterofinsPositive.size();
			    	
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
			   
		    }
	    
	    
	    
	    
	    
	   for(int i=0;i<sizeoflist;i++) {
		   row = sheet.createRow(i+1);

		
		    
		    
		   int cell_column_index_Percentage = 2;
			  int cell_column_index_Person = 3;

			  if(allanswersThisYearAndSemesterofinsPositive!=null) {
					if(i < allanswersThisYearAndSemesterofinsPositive.size()) {
					  cell = row.createCell(cell_column_index_Percentage);
					  row.getCell(cell_column_index_Percentage).setCellStyle(style2);
					  cell.setCellValue(allanswersThisYearAndSemesterofinsPositive.get(i).getQuesId().getQues());
					}
				}	  
				
			  if(allanswersThisYearAndSemesterofinsPositive!=null) {
					if(i < allanswersThisYearAndSemesterofinsPositive.size()) {
					  cell = row.createCell(cell_column_index_Person);
					  row.getCell(cell_column_index_Person).setCellStyle(style2);
					  cell.setCellValue(allanswersThisYearAndSemesterofinsPositive.get(i).getData());
					}
				}	
			  
			
				
		  
		   
	   }
	 
								   
	}
	
	
	

	

	
	
	
}
