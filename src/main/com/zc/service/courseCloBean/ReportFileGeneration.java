package main.com.zc.service.courseCloBean;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;


public class ReportFileGeneration {

	HSSFWorkbook workbook;
	HSSFSheet sheet;
	HSSFRow row;
	HSSFCell cell;
	List<cloThreshold> allCoursesThresoldResults;
	
	public ReportFileGeneration(List<cloThreshold> allCoursesThresoldResults,HSSFWorkbook wb,HSSFSheet sheet) {
		// TODO Auto-generated constructor stub
		this.workbook=wb;
		this.sheet=sheet;
		this.allCoursesThresoldResults=allCoursesThresoldResults;
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
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 5000);
		sheet.setColumnWidth(9, 3000);
		sheet.setColumnWidth(10, 5000);
		sheet.setColumnWidth(11, 3000);
		sheet.setColumnWidth(12, 5000);
		sheet.setColumnWidth(13, 3000);
		sheet.setColumnWidth(14, 5000);
		sheet.setColumnWidth(15, 3000);
		sheet.setColumnWidth(16, 5000);
		sheet.setColumnWidth(17, 3000);
		sheet.setColumnWidth(18, 5000);
		sheet.setColumnWidth(19, 3000);
		sheet.setColumnWidth(20, 5000);
		sheet.setColumnWidth(21, 7999);
		
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
	    for(int i=0;i<20;i++) {
	    	cell = row.createCell(2*i+1);
		    row.getCell(2*i+1).setCellStyle(style);
		    cell.setCellValue("CLO "+String.valueOf(i+1));
		    
		    
		    cell = row.createCell(2*i+2);
		    row.getCell(2*i+2).setCellStyle(style);
		    cell.setCellValue("CLO "+String.valueOf(i+1)+" Persons");
	    }


	    cell = row.createCell(2*20+2);
	    row.getCell(2*20+2).setCellStyle(style);
	    cell.setCellValue("Total Number Of Students");
	 
	    
	    
	    
	    
	    
	    
	    
	    
	    
	   for(int i=0;i<allCoursesThresoldResults.size();i++) {
		   row = sheet.createRow(i+1);

		
		    
		    
		   cell = row.createCell(0);
		   row.getCell(0).setCellStyle(style);
		   cell.setCellValue(allCoursesThresoldResults.get(i).getSelectedCourse().getCourse_code());
		   for(int j=0;j<20;j++) {
			  int cell_column_index_Percentage = 2*j+1;
			  int cell_column_index_Person = 2*j+2;

			  cell = row.createCell(cell_column_index_Percentage);
			  row.getCell(cell_column_index_Percentage).setCellStyle(style2);
			  cell.setCellValue(toFraction(allCoursesThresoldResults.get(i).getCloThresholdPercentage()[j],1000));
			  

			  cell = row.createCell(cell_column_index_Person);
			  row.getCell(cell_column_index_Person).setCellStyle(style2);
			  cell.setCellValue(allCoursesThresoldResults.get(i).getCloThresholdPersons()[j]);
		   }
		   
		   cell = row.createCell(2*20+2);
		  row.getCell(2*20+2).setCellStyle(style2);
		  cell.setCellValue(allCoursesThresoldResults.get(i).getNumberOfPersonTotalForThisCourse());
		   
	   }
	 
								   
	}
	
	
	

	

	
	
	
}
