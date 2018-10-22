package main.com.zc.services.presentation.coursesManagment.bean;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class ReportFileGeneration {

	HSSFWorkbook workbook;
	HSSFSheet sheet;
	CourseSyllabusCollection courseSyllabusCollection;
	HSSFRow row;
	HSSFCell cell;
	
	public ReportFileGeneration(CourseSyllabusCollection courseSyllabusCollection,HSSFWorkbook wb,HSSFSheet sheet) {
		// TODO Auto-generated constructor stub
		this.workbook=wb;
		this.sheet=sheet;
		this.courseSyllabusCollection=courseSyllabusCollection;
		
	}
	
	public void generateReport(){
		sheet.setColumnWidth(0, 1356);
		sheet.setColumnWidth(1, 4908);
		sheet.setColumnWidth(2, 2601);
		sheet.setColumnWidth(3, 2601);
		sheet.setColumnWidth(4, 1356);
		sheet.setColumnWidth(5, 4955);
		sheet.setColumnWidth(6, 2601);
		sheet.setColumnWidth(7, 2601);
		sheet.setColumnWidth(8, 2601);
		sheet.setColumnWidth(9, 3957);
		
		generateZewailCityofScienceandTechnology();
		Description();
		Prerequisites();
		Relatedtopics();
		Instructor();
		TA(23);
		int returnRow = TextBooks(27);
		int returnRow2 = References(returnRow);
		int returnRow3 = Topics(returnRow2+1);
		int returnRow4 = Grades(returnRow3+2);
		int returnRow5 = Notes(returnRow4+2);
		int returnRow6 = CLO(returnRow5+5);
		SO(returnRow6+10);
		setLogo();
	}
	public void generateZewailCityofScienceandTechnology(){
		//Zewail City of Science and Technology	row5 cell								

	    HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_CENTER);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 14);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
	    sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 9));
	    row = sheet.createRow(4);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Zewail City of Science and Technology");

	    sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 9));
	    row = sheet.createRow(5);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("University of Science and Technology");
	    
	    sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 9));
	    row = sheet.createRow(6);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue(courseSyllabusCollection.getCourses().getProgram());
	    
	    sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 9));
	    row = sheet.createRow(7);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("");
	    
	    sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 9));
	    row = sheet.createRow(8);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    String value=courseSyllabusCollection.getCourses().getName()+", ";
	    value+=courseSyllabusCollection.getCourses().getCourseTitle()+", ";
	    value+=courseSyllabusCollection.getCourses().getCredit()+"Cr., ";
	    value+=courseSyllabusCollection.getCourses().getSemester().getName()+" ";
	    value+=courseSyllabusCollection.getCourses().getYear().toString()+", ";
	    if(courseSyllabusCollection.getCourses().getRequiredElective()==1){
	    	value+="Required";
	    }else{
	    	value+="Elective";
	    }
	    
	    cell.setCellValue(value);
	    
	    
	}
	
	public void setLogo(){
		try{
		//FileInputStream obtains input bytes from the image file
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/logo.png");
			//Get the contents of an InputStream as a byte[].
		   byte[] bytes = IOUtils.toByteArray(inputStream);
		   //Adds a picture to the workbook
		   int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		   //close the input stream
		   inputStream.close();
		   //Returns an object that handles instantiating concrete classes
		   CreationHelper helper = workbook.getCreationHelper();
		   //Creates the top-level drawing patriarch.
		   Drawing drawing = sheet.createDrawingPatriarch();

		   //Create an anchor that is attached to the worksheet
		   ClientAnchor anchor = helper.createClientAnchor();

		   //create an anchor with upper left cell _and_ bottom right cell
		   anchor.setCol1(0); //Column A
		   anchor.setRow1(0); //Row 1
		   anchor.setCol2(3); //Column C
		   anchor.setRow2(3); //Row 4

		   //Creates a picture
		    drawing.createPicture(anchor, pictureIdx);

		   //Reset the image to the original size
		   //pict.resize(); //don't do that. Let the anchor resize the image!

		   //Create the Cell B3
		    sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 2));
		    sheet.createRow(0).createCell(0);


		  } catch (IOException ioex) {
			  System.out.println("Error: "+ioex.getMessage());
		  }
		 
	}
	

	public void Description(){
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(10, 10, 0, 1));
	    row = sheet.createRow(10);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Course Description");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_JUSTIFY);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(11, 17, 0, 9));
	    row = sheet.createRow(11);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue(courseSyllabusCollection.getCourses().getDescription());
	}

	public void Prerequisites(){
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(19, 19, 0, 1));
	    row = sheet.createRow(19);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Prerequisites");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(19, 19, 2, 9));
	    cell = row.createCell(2);
	    row.getCell(2).setCellStyle(style);
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getPre_Requisites().size();i++){
	    	value+=courseSyllabusCollection.getPre_Requisites().get(i).getPreRequisite();
	    	if(i==courseSyllabusCollection.getPre_Requisites().size()-2){
	    		value+=" And ";	
	    	}else if(i!=courseSyllabusCollection.getPre_Requisites().size()-1){
		    	value+=" ,";
	    	}
	    	
	    }
	    cell.setCellValue(value);
	}

	public void Relatedtopics(){
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(20, 20, 0, 1));
	    row = sheet.createRow(20);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Related topics");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(20, 20, 2, 9));
	    cell = row.createCell(2);
	    row.getCell(2).setCellStyle(style);
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getRelatedTopics().size();i++){
	    	value+=courseSyllabusCollection.getRelatedTopics().get(i).getRelatedTopics();
	    	if(i==courseSyllabusCollection.getRelatedTopics().size()-2){
	    		value+=" And ";	
	    	}else if(i!=courseSyllabusCollection.getRelatedTopics().size()-1){
		    	value+=" ,";
	    	}
	    	
	    }
	    cell.setCellValue(value);
	}

	public void Instructor(){
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(22, 22, 0, 1));
	    row = sheet.createRow(22);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Instructor");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(22, 22, 2, 9));
	    cell = row.createCell(2);
	    row.getCell(2).setCellStyle(style);
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getCourseInstructor().size();i++){
	    	value+=courseSyllabusCollection.getCourseInstructor().get(i).getName();
	    	value+=", Room #: ";
	    	value+=courseSyllabusCollection.getCourseInstructor().get(i).getRoom();
	    	value+="-";
	    	value+=courseSyllabusCollection.getCourseInstructor().get(i).getBuilding();
	    	if(i==courseSyllabusCollection.getCourseInstructor().size()-2){
	    		value+=" And ";	
	    	}else if(i!=courseSyllabusCollection.getCourseInstructor().size()-1){
		    	value+=" ,";
	    	}
	    	
	    }
	    cell.setCellValue(value);
	}

	public void TA(int num){
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
	    row = sheet.createRow(num);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Teaching Assistant");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num, 2, 9));
	    cell = row.createCell(2);
	    row.getCell(2).setCellStyle(style);
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getCourseTas().size();i++){
	    	value+=courseSyllabusCollection.getCourseTas().get(i).getName();
	    	value+=", Room #: ";
	    	value+=courseSyllabusCollection.getCourseTas().get(i).getRoom();
	    	value+="-";
	    	value+=courseSyllabusCollection.getCourseTas().get(i).getBuilding();
	    	if(i==courseSyllabusCollection.getCourseTas().size()-2){
	    		value+=" And ";	
	    	}else if(i!=courseSyllabusCollection.getCourseTas().size()-1){
		    	value+=" ,";
	    	}
	    	
	    }
	    cell.setCellValue(value);
	}
	
	public int TextBooks(int num){
		int numbReturn=num;
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
	    row = sheet.createRow(num);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Textbooks");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num+courseSyllabusCollection.getBooks().size()-1, 2, 9));
	    cell = row.createCell(2);
	    row.getCell(2).setCellStyle(style);
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getCourseTas().size();i++){
	    	numbReturn++;
	    	value+=String.valueOf(i+1)+". ";
	    	value+=courseSyllabusCollection.getBooks().get(i).getBook();
	    	
	    	if(i!=courseSyllabusCollection.getCourseTas().size()-1){
		    	value+="\r\n";
		    	
	    	}
	    	
	    }
	    cell.setCellValue(value);
	    return numbReturn;
	}

	public int References(int num){
		int numbReturn=num;
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
	    row = sheet.createRow(num);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("References");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num+courseSyllabusCollection.getReferences().size()-1, 2, 9));
	    cell = row.createCell(2);
	    row.getCell(2).setCellStyle(style);
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getReferences().size();i++){
	    	numbReturn++;
	    	value+=String.valueOf(i+1)+". ";
	    	value+=courseSyllabusCollection.getReferences().get(i).getName();
	    	
	    	if(i!=courseSyllabusCollection.getReferences().size()-1){
		    	value+="\r\n";
		    	
	    	}
	    	
	    }
	    cell.setCellValue(value);
	    return numbReturn;
	}

	public int Topics(int num){
		int numbReturn=num;
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
	    row = sheet.createRow(num);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Topics");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num+courseSyllabusCollection.getTopics().size(), 2, 9));
	    cell = row.createCell(2);
	    row.getCell(2).setCellStyle(style);
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getTopics().size();i++){
	    	numbReturn++;
	    	value+=String.valueOf(i+1)+". ";
	    	value+=courseSyllabusCollection.getTopics().get(i).getName();
	    	
	    	if(i!=courseSyllabusCollection.getTopics().size()-1){
		    	value+="\r\n";
		    	
	    	}
	    	
	    }
	    cell.setCellValue(value);
	    return numbReturn;
	}

	public int Grades(int num){
		int numbReturn=num;
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
	    row = sheet.createRow(num);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Grades");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getGrades().size();i++){
	    	numbReturn++;
	    	sheet.addMergedRegion(new CellRangeAddress(numbReturn, numbReturn, 2, 3));
	    	row = sheet.createRow(numbReturn);
	    	cell = row.createCell(2);
		    row.getCell(2).setCellStyle(style);
	    	value=courseSyllabusCollection.getGrades().get(i).getTypeOfGrade();
		    cell.setCellValue(value);
		    
		    cell = row.createCell(4);
		    row.getCell(4).setCellStyle(style);
	    	value=String.valueOf(courseSyllabusCollection.getGrades().get(i).getPresentage());
		    cell.setCellValue(value);
		    
		    cell = row.createCell(5);
		    row.getCell(5).setCellStyle(style);
	    	cell.setCellValue("%");
	    	
	    	
	    }
	    return numbReturn;
	}

	public int Notes(int num){
		int numbReturn=num;
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
	    row = sheet.createRow(num);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Notes");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num+1, num+courseSyllabusCollection.getNotes().size()+3, 1, 9));
		row = sheet.createRow(num+1);
		cell = row.createCell(1);
	    row.getCell(1).setCellStyle(style);
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getNotes().size();i++){
	    	numbReturn++;
	    	value+=String.valueOf(i+1)+". ";
	    	value+=courseSyllabusCollection.getNotes().get(i).getNote();
	    	
	    	if(i!=courseSyllabusCollection.getNotes().size()-1){
		    	value+="\r\n";
		    	
	    	}

		    cell.setCellValue(value);
	    }
	    return numbReturn;
	}

	public int CLO(int num){
		int numbReturn=num;
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
	    row = sheet.createRow(num);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Course Learning Objectives");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num+1, num+1, 0, 9));
		row = sheet.createRow(num+1);
		cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Upon the completion of this course, students should be able to:");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num+2, num+courseSyllabusCollection.getClos().size()+8, 1, 9));
		row = sheet.createRow(num+2);
		cell = row.createCell(1);
	    row.getCell(1).setCellStyle(style);
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getClos().size();i++){
	    	numbReturn++;
	    	value+=String.valueOf(i+1)+". ";
	    	value+=courseSyllabusCollection.getClos().get(i).getClo();
	    	
	    	if(i!=courseSyllabusCollection.getClos().size()-1){
		    	value+="\r\n";
		    	
	    	}

		    cell.setCellValue(value);
	    }
	    return numbReturn;
	}

	public void SO(int num){
		HSSFCellStyle style = workbook.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 9));
	    row = sheet.createRow(num);
	    cell = row.createCell(0);
	    row.getCell(0).setCellStyle(style);
	    cell.setCellValue("Course addresses the following program outcomes:");
	    
	    style = workbook.createCellStyle();
	    style.setWrapText(true);
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    style.setFont(font); 
	    
		sheet.addMergedRegion(new CellRangeAddress(num+1, num+1, 1, 9));
	    row = sheet.createRow(num+1);
	    cell = row.createCell(1);
	    row.getCell(1).setCellStyle(style);
	    String value="";
	    for(int i=0;i<courseSyllabusCollection.getSos().size();i++){
	    	value+=courseSyllabusCollection.getSos().get(i).getSo();
	    	
	    	if(i==courseSyllabusCollection.getCourseInstructor().size()-2){
	    		value+=" And ";	
	    	}else if(i!=courseSyllabusCollection.getCourseInstructor().size()-1){
		    	value+=" ,";
	    	}
	    	
	    }

	    cell.setCellValue(value);
	}

	
}
