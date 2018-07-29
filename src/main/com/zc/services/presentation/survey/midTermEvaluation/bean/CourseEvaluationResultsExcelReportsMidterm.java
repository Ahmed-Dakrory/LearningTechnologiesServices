/**
 * 
 */
package main.com.zc.services.presentation.survey.midTermEvaluation.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import main.com.zc.services.applicationService.configuration.services.impl.CoursesServiceImpl;
import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.domain.configurations.model.ICourseStudentRep;
import main.com.zc.services.domain.courseEval.model.ICourseEvalAnswersRep;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.domain.shared.enumurations.ScaleSelectionTypeEnum;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.ICourseEvaluationFacade;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.ICoursesFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.survey.midTermEvaluation.bean.facade.ICourseStudentFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellAlignment;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellFill;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCol;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.Paragraph;

/**
 * @author Omnya Alaa
 *
 */
@ViewScoped
@ManagedBean
public class CourseEvaluationResultsExcelReportsMidterm {

	@ManagedProperty("#{ICourseEvalQuestionsFacade}")
	private ICourseEvalQuestionsFacade facade;
	@ManagedProperty("#{ICourseEvalAnswersFacade}")
	private ICourseEvalAnswersFacade ansFacade;
	
	@ManagedProperty("#{ICoursesEvalFacade}")
	private ICoursesFacade coursesInstFacade;
	
	@ManagedProperty("#{ICourseEvaluationFacade}")
	private ICourseEvaluationFacade courseEvalQuesFacade;
	
	@ManagedProperty("#{ICourseStudentFacade}")
	ICourseStudentFacade courseStudentfacdae;
	
	
	
	int rowIndex = 4;
	CellStyle sectionStyle;
	CellStyle answersStyle;
	CellStyle questionsStyle;
	CellStyle otherCommentsStyle;
	CellStyle strengthConcernsStyle;
	
	
	@PostConstruct
	public void init(){
try {
			//TODO 1- get questions by categories
			// 1. Get Course Evaluation Results.			
			List<CourseEvalQuestionsDTO> courseEval = new ArrayList<CourseEvalQuestionsDTO>();
			courseEval = facade.getBySectionID(QuestionsCategory.Course_Eval.getID());
			
			// 2. Get Student Evaluation Results.			
			List<CourseEvalQuestionsDTO> studentEval = new ArrayList<CourseEvalQuestionsDTO>();
			studentEval =  facade.getBySectionID(QuestionsCategory.Student_Eval.getID());
			
			// 3. Get Lab Evaluation Results.			
			List<CourseEvalInsQuestionsDTO> labEval = new ArrayList<CourseEvalInsQuestionsDTO>();
			labEval = coursesInstFacade.getAllInstructorQuestions(QuestionsCategory.MIDTERM_EVALUATION_LAB.getID());
			
			
			// 4. Get Language of Instructions Evaluation Results.			
			List<CourseEvalQuestionsDTO> langOfInstEval = new ArrayList<CourseEvalQuestionsDTO>();
			langOfInstEval = facade.getBySectionID(QuestionsCategory.Languag_Of_Instruction.getID());
			
			// 5. Get Other comments Results.			
						List<CourseEvalQuestionsDTO> otherComments = new ArrayList<CourseEvalQuestionsDTO>();
						otherComments = facade.getBySectionID(QuestionsCategory.MIDTERM_EVALUATION_OTHER.getID());
			
		   //6. get Instructor eval
						List<CourseEvalInsQuestionsDTO> insEval = new ArrayList<CourseEvalInsQuestionsDTO>();
						insEval = coursesInstFacade.getAllInstructorQuestions(QuestionsCategory.MIDTERM_EVALUATION_INSTRUCTOR.getID());
						
						 //6. get Instructor eval
						List<CourseEvalInsQuestionsDTO> taEval = new ArrayList<CourseEvalInsQuestionsDTO>();
						taEval = coursesInstFacade.getAllInstructorQuestions(QuestionsCategory.MIDTERM_EVALUATION_TA.getID());
						
				       
			//TODO get courses by semester and year
			
						List<CoursesDTO> courses=ansFacade.getCoursesByYearAndSemester(0,2017);
			for(int i=0;i<courses.size();i++)
			{
				
				
			//6.Get instructors evaluation
						 List<InstructorDTO> instructors = new ArrayList<InstructorDTO>();
					     instructors = coursesInstFacade.getAllInstructorsOfCourse(courses.get(i).getId());
		
					     List<InstructorDTO> tas = new ArrayList<InstructorDTO>();
					     tas=courseEvalQuesFacade.getTasByCourseID(courses.get(i).getId());
		
					     
				 rowIndex = (short)0;
				/*XSSFWorkbook wb = new XSSFWorkbook();
				XSSFSheet sheet = wb.createSheet("Sample sheet");
			*/	Workbook wb = new XSSFWorkbook();   //or new HSSFWorkbook();
		        Sheet sheet = wb.createSheet();

				
			//	Section name style
				Font sectionFont = wb.createFont();
				sectionFont.setFontHeightInPoints((short) 11);
				sectionFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				//sectionFont.setColor(HSSFColor.DARK_RED.index);
				//sectionFont.setItalic(true);
				sectionFont.setUnderline(HSSFFont.U_SINGLE);
				sectionFont.setFontName("Arial");
				sectionStyle = wb.createCellStyle();
				sectionStyle.setFont(sectionFont);
				sectionStyle.setAlignment(CellStyle.ALIGN_LEFT);
				sectionStyle.setWrapText(true); 
				   
				//	 answers name style
				Font answersFont = wb.createFont();
				answersFont.setFontHeightInPoints((short)11);
				answersFont.setFontName("Arial");
				
				answersStyle = wb.createCellStyle();
				answersStyle.setFont(answersFont);
				answersStyle.setAlignment(CellStyle.ALIGN_LEFT);
				//answersStyle.setWrapText(true);
				answersStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
				answersStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
				answersStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
				answersStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	        	
				//other strengths and conserns 
				Font strengthConcernsFont = wb.createFont();
				//strengthConcernsFont.setFontHeightInPoints((short) 12);
				strengthConcernsFont.setFontName("Arial");
				
				strengthConcernsStyle = wb.createCellStyle();
				strengthConcernsStyle.setFont(strengthConcernsFont);
				strengthConcernsStyle.setAlignment(CellStyle.ALIGN_LEFT);
				strengthConcernsStyle.setWrapText(true);
				
				
//				Other comments  style
				otherCommentsStyle = wb.createCellStyle();
				otherCommentsStyle.setFont(answersFont);
				otherCommentsStyle.setAlignment(CellStyle.ALIGN_LEFT);
				//otherCommentsStyle.setWrapText(true);
				
				
				//questions  style
				Font questionsFont = wb.createFont();
				questionsFont.setFontHeightInPoints((short) 11);
				questionsFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				questionsFont.setFontName("Arial");
				
				questionsStyle = wb.createCellStyle();
				questionsStyle.setFont(questionsFont);
				questionsStyle.setAlignment(CellStyle.ALIGN_LEFT);
				questionsStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
				questionsStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				questionsStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
				questionsStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
				questionsStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
				questionsStyle .setBorderLeft(XSSFCellStyle.BORDER_THIN);
	        	
				//questionsStyle.setWrapText(true);
				
				
				Row row;
				row = sheet.createRow((short)rowIndex);  
				/*sheet.autoSizeColumn(0);
				sheet.autoSizeColumn(3);
				sheet.autoSizeColumn(6);
				*///sheet.autoSizeColumn(8);
				/*sheet.setColumnWidth(0,5000);
				sheet.setColumnWidth(1,8000);
				sheet.setColumnWidth(3,8000);
				sheet.setColumnWidth(2,800);
				sheet.setColumnWidth(4,800);
				sheet.setColumnWidth(6,10000);*/
				 PrintSetup ps = sheet.getPrintSetup();
			        ps.setFitHeight((short)18);
			        ps.setFitWidth((short)1);
			        ps.setLandscape(true);
			        sheet.setFitToPage(true);

				// PrintSetup ps = sheet.getPrintSetup();
			    //   sheet.setFitToPage(true);

//			/	sheet.set
				//my_style.setWrapText(true);
				//TODO write course Eval section
				//writefirstSections(courseEval,courses.get(i),"Course Evaluation",wb,sheet,row,0);
				//rowIndex++;
				//TODO write Student Eval section
				//writefirstSections(studentEval,courses.get(i),"Student Evaluation",wb,sheet,row,0);
				//rowIndex++;
				//TODO write lab Language of instruction section
				//writefirstSections(langOfInstEval,courses.get(i),"Language of instruction",wb,sheet,row,0);
				//rowIndex++;
				//TODO write other comments
				//rowIndex++;
				//TODO write lab eval
				
				rowIndex++;
				
				CellStyle style = wb.createCellStyle();
		        Font font = wb.createFont();
		        //font.setColor(HSSFColor.DARK_RED.index);
		        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		        font.setUnderline(HSSFFont.U_SINGLE);
		        font.setFontHeightInPoints((short)11);
		        font.setFontName("Arial");
		        style.setFont(font);
		        row = sheet.createRow((short)rowIndex);
		        Cell courseCell = 	row.createCell((short)0);
		        courseCell.setCellValue("Course : "+courses.get(i).getName());
		        courseCell.setCellStyle(style);
		        
		        List<StudentDTO> students = getTotalStudentsOfCourse(courses.get(i).getId());
		        
		        int totalNumberOfStudentsInCourse = getStudentsNumberOfCourse(courses.get(i).getId());
		        
		        double totalPercentage=((double)students.size()/(double)totalNumberOfStudentsInCourse)*100;
		        
		        long totalFactor = (long) Math.pow(10, 2);
		        totalPercentage = totalPercentage * totalFactor;
	  		    long totalTmp = Math.round(totalPercentage);	                 
		        rowIndex++;
		        row = sheet.createRow((short)rowIndex);
	  		  Cell courseCell1 = 	row.createCell((short)0);
	  		courseCell1.setCellValue("Total number of students : " + students.size() + " / " 
			        + totalNumberOfStudentsInCourse);
	  		courseCell1.setCellStyle(style);
	  	   rowIndex++;
	        row = sheet.createRow((short)rowIndex);
	        Cell courseCell2 = 	row.createCell((short)0);
	  		courseCell2.setCellValue("Participation rate : " +(double) totalTmp / totalFactor + "%");
	  		courseCell2.setCellStyle(style);
		          rowIndex++;
		         rowIndex++;
		        row = sheet.createRow((short)rowIndex);
				   Cell xxx = 	row.createCell((short)0);
						   xxx.setCellValue("Instructors Evaluation");
						   xxx.setCellStyle(style);
						   rowIndex++;
						   rowIndex++;
						   
				for(int j=0;j<instructors.size();j++)
				{
					//TODO for each instructor
					writefirstSectionsSubSelectionsInstructors
					(insEval,courses.get(i),instructors.get(j).getName(),wb,sheet,row,instructors.get(j));
					rowIndex++;
					
				}
				
				
				 row = sheet.createRow((short)rowIndex);
				   Cell xxxx = 	row.createCell((short)0);
				   xxxx.setCellValue("TAs Evaluation");
				   xxxx.setCellStyle(style);
						   rowIndex++;
						   rowIndex++;
					for(int j=0;j<tas.size();j++)
							{
								//TODO for each instructor
								writefirstSectionsSubSelectionsInstructors
								(taEval,courses.get(i),tas.get(j).getName(),wb,sheet,row,tas.get(j));
								rowIndex++;
								
							}	   
					sheet.autoSizeColumn(0);
					sheet.autoSizeColumn(3);
					//sheet.autoSizeColumn(6);
					sheet.autoSizeColumn(5);	   
			
					writefirstSectionsSubSelections(labEval,courses.get(i),"Lab Evaluation",wb,sheet,row);
					
					writefirstSections(otherComments,courses.get(i),"Other Comments",wb,sheet,row,1);
						/*	CTCol col = sheet.getCTWorksheet().getColsArray(0).addNewCol();
					col.setMin(6+2);
					col.setMax(16384); // the last column (1-indexed)
					col.setHidden(true);	
			*/		sheet.protectSheet("Lt1_@)!&");
				File dir ;
				/*dir = new File("C:\\Users\\heba\\sts-bundle\\courses-excel-fall-2017-midterm\\"
						+courses.get(i).getName().replace("/","_")+"\\");*/
				dir = new File("/home/heba/Desktop/courses-excel-fall-2017-midtermm/"
						+courses.get(i).getName().replace("/","_")+"/");
				dir.mkdirs();
				File file  = new File(dir, courses.get(i).getName().replaceAll("/","_")+".xlsx");
				
				FileOutputStream out = new FileOutputStream(file);
							wb.write(out);
					      out.close();
					      System.out.println(courses.get(i).getName()+".xls written successfully");
			}
}
catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}
	}

	//TODO this method for course Eval / student eval/ language of instruction  sections 
	//TODO int type 0 >> for types of course eval , student eval, language on instruction eval
	//1 for other comments

	public void writefirstSections(List<CourseEvalQuestionsDTO> lst,CoursesDTO course,String sectionName,Workbook wb,Sheet sheet,Row row,int type){
		try{
		
       	row = sheet.createRow((short)rowIndex);
			   Cell xxx = 	row.createCell((short)0);
					   xxx.setCellValue(sectionName);
					   xxx.setCellStyle(sectionStyle);
					
						rowIndex++;				
			switch (type) {
			case 0:
				for(int j=0;j<lst.size();j++)
				{
					 
			      
			        row = sheet.getRow((short)rowIndex);
			        if(row ==null)
			            row = sheet.createRow((short)rowIndex);
			       Cell cell = row.createCell(1);
		         cell.setCellValue(lst.get(j).getText());
		         cell.setCellStyle(questionsStyle);
		        
		            rowIndex++;
			        
					List<CourseEvalAnswersDTO> answers=ansFacade.getByQuestionIDAndCourseID
							(lst.get(j).getId(), course.getId());
					
					 List<Integer> asnwersSelections = new ArrayList<Integer>();
						
					for(int a=0;a<answers.size();a++)
					{
						asnwersSelections.add(answers.get(a).getSelections());
					}
					
					//TODO get selections of question
					List<BaseDTO> counterLst=new ArrayList<BaseDTO>();
					for(int s=0;s<lst.get(j).getScaleType().getSelections().size();s++)
					{
						//TODO Count the number of occurencies of each selection
						
						 int freq = Collections.frequency(asnwersSelections, lst.get(j).getScaleType().getSelections().get(s).getId());
						 counterLst.add(new BaseDTO(freq, lst.get(j).getScaleType().getSelections().get(s).getName()));
					}
					
				
			         

			        for (int f = 0; f < counterLst.size(); f++){
			        	row = sheet.createRow((short)rowIndex);
			        	 Cell celll = row.createCell(0);
			        	 celll.setCellValue(counterLst.get(f).getName());
			        	 Cell cellll = row.createCell(1);
			        	 cellll.setCellValue(counterLst.get(f).getId());
			        
			        	cellll.setCellStyle(answersStyle);
			        	celll.setCellStyle(answersStyle);
			         	
			        	rowIndex++;
			          }
			   
			        rowIndex++;
				}
				break;

			case 1:
				for(int j=0;j<lst.size();j++)
				{
					
					 row = sheet.getRow((short)rowIndex);
				        if(row ==null)
				            row = sheet.createRow((short)rowIndex);
				 Cell cell = row.createCell(0);
				cell.setCellValue(lst.get(j).getText());
		         Font questionsFont = wb.createFont();
					questionsFont.setFontHeightInPoints((short) 11);
					questionsFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
					questionsFont.setFontName("Arial");
					questionsStyle = wb.createCellStyle();
					questionsStyle.setFont(questionsFont);
					questionsStyle.setAlignment(CellStyle.ALIGN_LEFT);
					//questionsStyle.setWrapText(true);
					//questionsStyle.set
		         cell.setCellStyle(questionsStyle);
		         Cell cell1 = row.createCell(1);
		         //cell1.setCellValue("");
		         cell1.setCellStyle(questionsStyle);
		         Cell cell2= row.createCell(2);
		         //cell2.setCellValue("");
		         cell2.setCellStyle(questionsStyle);
		         Cell cell3= row.createCell(3);
		        // cell3.setCellValue("");
		         cell3.setCellStyle(questionsStyle);
		         rowIndex++;
		         
					List<CourseEvalAnswersDTO> answers=ansFacade.getByQuestionIDAndCourseID
							(lst.get(j).getId(), course.getId());
					
						
					
			        for (int a = 0; a < answers.size(); a++){
			        	row = sheet.createRow((short)rowIndex);
			        	 row.setHeight((short)500);
				         
			        	Cell comment=row.createCell((short)0);
			        	String s=addLinebreaks
			        			(answers.get(a).getComment(),150);
			        	
			        	comment.setCellValue(s);
			        	 String[] lines = s.split("\r\n|\r|\n");
			             //System.out.println(lines.length);
			             
			        	row.setHeightInPoints((lines.length*sheet.getDefaultRowHeightInPoints()));
			        	//answersStyle.setWrapText(true);
			        	 Font questionsFontx = wb.createFont();
			        	 questionsFontx.setFontHeightInPoints((short) 11);
							//questionsFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			        	  sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex,0, 6));
			             
			           
			        	 questionsFontx.setFontName("Arial");
							questionsStyle = wb.createCellStyle();
							questionsStyle.setFont(questionsFontx);
							questionsStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
							//questionsStyle.setAlignment(CellStyle.ALIGN_LEFT);
							questionsStyle.setWrapText(true);
							comment.setCellStyle(questionsStyle);
						  	rowIndex++;
			        	
			        }
			       // sheet.autoSizeColumn(0);
			       // rowIndex++;	  
				}
				break;
				
				
			}
			

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void writefirstSectionsSubSelections(List<CourseEvalInsQuestionsDTO> lst,CoursesDTO course,String sectionName,Workbook wb,Sheet sheet,Row row){
		try{
			for(int i=0;i<8;i++){
			sheet.autoSizeColumn(i);
			}
		        row = sheet.createRow((short)rowIndex);
				   Cell xxx = 	row.createCell((short)0);
						   xxx.setCellValue(sectionName);
						   xxx.setCellStyle(sectionStyle);
						   rowIndex++;
			for(int j=0;j<lst.size();j++)
			{
				
				  row = sheet.getRow((short)rowIndex);
				  if(row==null)
		        row = sheet.createRow((short)rowIndex);
		       Cell cell = row.createCell(0);
	         cell.setCellValue(lst.get(j).getText());
	         cell.setCellStyle(questionsStyle);
	         Cell cell22 = row.createCell(1);
	        // cell2.setCellValue(lst.get(j).getText());
	         cell22.setCellStyle(questionsStyle);
	         Cell cellstrength = row.createCell(3);
	         cellstrength.setCellValue(lst.get(j).getText()+"-Strengths");
	         cellstrength.setCellStyle(questionsStyle);
	         Cell cellstrength1 = row.createCell(4);
	         cellstrength1.setCellValue("");
	         cellstrength1.setCellStyle(questionsStyle);
	         Cell cellconcern = row.createCell(5);
	         cellconcern.setCellValue(lst.get(j).getText()+"-Concerns");
	         cellconcern.setCellStyle(questionsStyle);
	         Cell cellconcern1 = row.createCell(6);
	         cellconcern1.setCellValue("");
	         cellconcern1.setCellStyle(questionsStyle);
	         /* Attach the style to the cell */
	       
		        
				List<CourseEvalAnswersDTO> answers=ansFacade.getByQuestionIDAndCourseID
						(lst.get(j).getId(), course.getId());
				
				List<String> strengthsSelections = new ArrayList<String>();
				List<String> concernsSelections = new ArrayList<String>();
			
				List<BaseDTO> mainselections=new ArrayList<BaseDTO>();
				 List<Integer> asnwersSelections = new ArrayList<Integer>();
					
				for(int a =0;a<answers.size();a++)
				{
					asnwersSelections.add(answers.get(a).getSelections());
				}
											
						//TODO get selections of question
						List<BaseDTO> counterLstMain=new ArrayList<BaseDTO>();
						List<BaseDTO> counterLstStrength=new ArrayList<BaseDTO>();
						List<BaseDTO> counterLstConcern=new ArrayList<BaseDTO>();
						
						for(int s=0;s<lst.get(j).getScaleType().getSelections().size();s++)
						{
							//TODO Count the number of occurencies of each selection
							
							if(lst.get(j).getScaleType().getSelections().get(s).getType().equals(ScaleSelectionTypeEnum.MAIN))
							 {
							 int freq = Collections.frequency(asnwersSelections, lst.get(j).getScaleType().getSelections().get(s).getId());
							 counterLstMain.add(new BaseDTO(freq, lst.get(j).getScaleType().getSelections().get(s).getName()));
							 
							 }
							else if(lst.get(j).getScaleType().getSelections().get(s).getType().equals(ScaleSelectionTypeEnum.STRENGTH))
							 {
							 int freq = Collections.frequency(asnwersSelections, lst.get(j).getScaleType().getSelections().get(s).getId());
							 counterLstStrength.add(new BaseDTO(freq, lst.get(j).getScaleType().getSelections().get(s).getName()));
							 
							 }
							else if(lst.get(j).getScaleType().getSelections().get(s).getType().equals(ScaleSelectionTypeEnum.CONCERN))
							 {
							 int freq = Collections.frequency(asnwersSelections, lst.get(j).getScaleType().getSelections().get(s).getId());
							 counterLstConcern.add(new BaseDTO(freq, lst.get(j).getScaleType().getSelections().get(s).getName()));
							 
							 }
						}
					
						List<String> strengthsComments=new ArrayList<String>();
						List<String> concernsComments=new ArrayList<String>();
						for(int a=0;a<answers.size();a++)
						{
							if(answers.get(a).getComment()!=null && !answers.get(a).getComment().trim().equals("")
									&& answers.get(a).getSelections()!=null){
								for(int s=0;s<lst.get(j).getScaleType().getSelections().size();s++)
								{
									if(answers.get(a).getSelections().equals(lst.get(j).getScaleType().getSelections().get(s).getId()))
									{
										if(lst.get(j).getScaleType().getSelections().get(s).getType().equals(ScaleSelectionTypeEnum.STRENGTH))
										{
											strengthsComments.add(answers.get(a).getComment());
										}
										else 
										if(lst.get(j).getScaleType().getSelections().get(s).getType().equals(ScaleSelectionTypeEnum.CONCERN))
											{
											concernsComments.add(answers.get(a).getComment());
											}
									}
								}
							}
						}
				
				
						rowIndex++;
						int originIndex=rowIndex;
						int tempmax=Math.max(counterLstMain.size(),counterLstConcern.size());
						int max=Math.max(tempmax,counterLstStrength.size());
						
					        	 for (int f = 0; f < max; f++){
					        			row = sheet.createRow((short)rowIndex);
					        			rowIndex++;
					    	       
					        	 }
					     
					        	 rowIndex=originIndex;
					        int tempindex=originIndex;
		        for (int f = 0; f < counterLstMain.size(); f++){
		        	row = sheet.getRow((short)tempindex);
		        	
					Cell cell1=row.createCell((short)0);
					cell1.setCellValue(counterLstMain.get(f).getName());
					cell1.setCellStyle(answersStyle);
					Cell cell2=row.createCell((short)1);
					cell2.setCellStyle(answersStyle);
					cell2.setCellValue(counterLstMain.get(f).getId());
					tempindex++;
		          
		        	
		        }  
		        //rowIndex++;  
		      //  rowIndex=originIndex;
		    	         //rowIndex++;
	           int tempIndex2=rowIndex;
	           int tmp1=rowIndex;
	           int tmp2=rowIndex;
	          if(counterLstStrength.size()>=counterLstConcern.size())
	        {
	        	 for (int f = 0; f < counterLstStrength.size(); f++){
	        			row = sheet.getRow((short)tempIndex2);
	    	        	tempIndex2++;
	    	       
	        	 }
	     
	        	 rowIndex=tempIndex2;
	        }
	        else
	        	
	        {   	//Create new rows according to the number of strengths
	  	        	 for (int f = 0; f < counterLstConcern.size(); f++){
	  	        			row = sheet.getRow((short)tempIndex2);
	  	    	        	tempIndex2++;
	  	    	     //   	rowIndex++;
	  	        	 }
	  	        	
	  		              
		        rowIndex=tempIndex2;
	        }
	  	       
		        for (int f = 0; f < counterLstConcern.size(); f++){
		        	row = sheet.getRow(((short)(tmp1)));
		        	
		        	
					Cell cell10=row.createCell((short)5);
					cell10.setCellValue(counterLstConcern.get(f).getName());
					cell10.setCellStyle(answersStyle);
					Cell cell33=row.createCell((short)6);
					cell33.setCellValue(counterLstConcern.get(f).getId());
					cell33.setCellStyle(answersStyle);
					tmp1++;
		            //Get the added copy Data to fill the excel sheet
		        	
		        	
		        }  
		        
		        
		        for (int f = 0; f < counterLstStrength.size(); f++){
		        	row = sheet.getRow((short)tmp2);
		        	
					Cell cell10=row.createCell((short)3);
					cell10.setCellValue(counterLstStrength.get(f).getName());
					cell10.setCellStyle(answersStyle);
					Cell cell33=row.createCell((short)4);
					cell33.setCellValue(counterLstStrength.get(f).getId());
					cell33.setCellStyle(answersStyle);
					
					
		        	/*row.createCell((short)1).setCellValue(counterLstStrength.get(f).getName());
		        	row.createCell((short)2).setCellValue(counterLstStrength.get(f).getId());
		        */	tmp2++;
		            //Get the added copy Data to fill the excel sheet
		        	
		        	
		        }  
		       
		        rowIndex++;
		        //Write Comments 
		        
		        row = sheet.createRow((short)rowIndex);
		    	  Cell cell3 = row.createCell(3);
		    	  cell3.setCellValue("Comments - Strengths");
		    	  cell3.setCellStyle(questionsStyle);
			         Cell cell4 = row.createCell(5);
			         cell4.setCellValue("Comments - Concerns");
			         cell4.setCellStyle(questionsStyle);
			         rowIndex++;
			      //   rowIndex++;
			         
			         
		        int tempIndex3=rowIndex;
		           int tmp3=rowIndex;
		           int tmp4=rowIndex;
		          if(strengthsComments.size()>=concernsComments.size())
		        {
		        	//Create new rows according to the number of strengths
		        	 for (int f = 0; f < strengthsComments.size(); f++){
		        			row = sheet.createRow((short)tempIndex3);
		        			tempIndex3++;
		    	        //	rowIndex++;
		        	 }
		      
		        rowIndex=tempIndex3;
		        }
		        else
		        	
		        {   	//Create new rows according to the number of concerns
		  	        	 for (int f = 0; f < concernsComments.size(); f++){
		  	        			row = sheet.createRow((short)tempIndex3);
		  	        			tempIndex3++;
		  	    	     //   	rowIndex++;
		  	        	 }
		  	        
		  		              
			        rowIndex=tempIndex3;
		        }
		  	       
			        for (int f = 0; f < concernsComments.size(); f++){
			        	row = sheet.getRow(((short)(tmp3)));
			        	Cell comment=row.createCell((short)5);
			        	comment.setCellValue(concernsComments.get(f));
			        	comment.setCellStyle(strengthConcernsStyle);
			        	//row.createCell((short)7).setCellValue(counterLstConcern.get(f).getId());
			        	tmp3++;
			            //Get the added copy Data to fill the excel sheet
			        	
			        	
			        }  
			        
			        
			        for (int f = 0; f < strengthsComments.size(); f++){
			        	row = sheet.getRow((short)tmp4);
			          	Cell comment=row.createCell((short)3);
			        	comment.setCellValue(strengthsComments.get(f));
			        	comment.setCellStyle(strengthConcernsStyle);
			        	
			        	
			        	tmp4++;
			            //Get the added copy Data to fill the excel sheet
			        	
			        	
			        }  
			       
	
		        
		        
		        rowIndex++;
		        
				  
			}
		}
		catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	public void writefirstSectionsSubSelectionsInstructors
	(List<CourseEvalInsQuestionsDTO> lst,CoursesDTO course,String sectionName,Workbook wb,Sheet sheet,Row row,InstructorDTO ins)
	{
		try{

		        row = sheet.createRow((short)rowIndex);
				   Cell xxx = 	row.createCell((short)0);
						   xxx.setCellValue(sectionName);
						   xxx.setCellStyle(sectionStyle);
						   
						   //row = sheet.createRow((short)rowIndex);		
						  /* Cell insName = 	row.createCell((short)0);
						   insName.setCellValue(ins.getName());
						   insName.setCellStyle(sectionStyle);
						  */ rowIndex++;
			for(int j=0;j<lst.size();j++)
			{
				Font   questionsFontx =wb.createFont();
	        	 questionsFontx.setFontHeightInPoints((short) 11);
					//questionsFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	        	 questionsFontx.setFontName("Arial");
	        	 questionsFontx.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
					questionsStyle = wb.createCellStyle();
					questionsStyle.setFont(questionsFontx);
					questionsStyle.setAlignment(CellStyle.ALIGN_LEFT);
				questionsStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
				questionsStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				questionsStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
				questionsStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
				questionsStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
				questionsStyle .setBorderLeft(XSSFCellStyle.BORDER_THIN);
				//questionsStyle.setf
			 row = sheet.createRow((short)rowIndex);
		     Cell cell = row.createCell(0);
	         cell.setCellValue(lst.get(j).getText());
	         cell.setCellStyle(questionsStyle);
	         Cell cell11 = row.createCell(1);
	         //cell1.setCellValue(lst.get(j).getText());
	         cell11.setCellStyle(questionsStyle);
	         Cell cellstrength = row.createCell(3);
	         cellstrength.setCellValue(lst.get(j).getText()+"-Strengths");
	         cellstrength.setCellStyle(questionsStyle);
	         Cell cellstrength2 = row.createCell(4);
	         cellstrength2.setCellValue("");
	         cellstrength2.setCellStyle(questionsStyle);
	         Cell cellconcern = row.createCell(5);
	         cellconcern.setCellValue(lst.get(j).getText()+"-Concerns");
	         cellconcern.setCellStyle(questionsStyle);
	         Cell cellconcern2 = row.createCell(6);
	         cellconcern2.setCellValue("");
	         cellconcern2.setCellStyle(questionsStyle);
	        
	  		List<CourseEvalAnswersDTO> answers=ansFacade.getByQuestionIDAndCourseIDAndInsID
						(lst.get(j).getId(), course.getId(),ins.getId());
				
				List<String> strengthsSelections = new ArrayList<String>();
				List<String> concernsSelections = new ArrayList<String>();
			
				List<BaseDTO> mainselections=new ArrayList<BaseDTO>();
				 List<Integer> asnwersSelections = new ArrayList<Integer>();
					
				for(int a =0;a<answers.size();a++)
				{
					asnwersSelections.add(answers.get(a).getSelections());
				}
											
						//TODO get selections of question
						List<BaseDTO> counterLstMain=new ArrayList<BaseDTO>();
						List<BaseDTO> counterLstStrength=new ArrayList<BaseDTO>();
						List<BaseDTO> counterLstConcern=new ArrayList<BaseDTO>();
						
						for(int s=0;s<lst.get(j).getScaleType().getSelections().size();s++)
						{
							//TODO Count the number of occurencies of each selection
							
							if(lst.get(j).getScaleType().getSelections().get(s).getType().equals(ScaleSelectionTypeEnum.MAIN))
							 {
							 int freq = Collections.frequency(asnwersSelections, lst.get(j).getScaleType().getSelections().get(s).getId());
							 counterLstMain.add(new BaseDTO(freq, lst.get(j).getScaleType().getSelections().get(s).getName()));
							 
							 }
							else if(lst.get(j).getScaleType().getSelections().get(s).getType().equals(ScaleSelectionTypeEnum.STRENGTH))
							 {
							 int freq = Collections.frequency(asnwersSelections, lst.get(j).getScaleType().getSelections().get(s).getId());
							 counterLstStrength.add(new BaseDTO(freq, lst.get(j).getScaleType().getSelections().get(s).getName()));
							 
							 }
							else if(lst.get(j).getScaleType().getSelections().get(s).getType().equals(ScaleSelectionTypeEnum.CONCERN))
							 {
							 int freq = Collections.frequency(asnwersSelections, lst.get(j).getScaleType().getSelections().get(s).getId());
							 counterLstConcern.add(new BaseDTO(freq, lst.get(j).getScaleType().getSelections().get(s).getName()));
							 
							 }
						}
					
						List<String> strengthsComments=new ArrayList<String>();
						List<String> concernsComments=new ArrayList<String>();
						for(int a=0;a<answers.size();a++)
						{
							if(answers.get(a).getComment()!=null && !answers.get(a).getComment().trim().equals("")
									&& answers.get(a).getSelections()!=null){
								for(int s=0;s<lst.get(j).getScaleType().getSelections().size();s++)
								{
									if(answers.get(a).getSelections().equals(lst.get(j).getScaleType().getSelections().get(s).getId()))
									{
										if(lst.get(j).getScaleType().getSelections().get(s).getType().equals(ScaleSelectionTypeEnum.STRENGTH))
										{
											strengthsComments.add(answers.get(a).getComment());
										}
										else 
										if(lst.get(j).getScaleType().getSelections().get(s).getType().equals(ScaleSelectionTypeEnum.CONCERN))
											{
											concernsComments.add(answers.get(a).getComment());
											}
									}
								}
							}
						}
				
				
			
						rowIndex++;
						int originIndex=rowIndex;
						int tempmax=Math.max(counterLstMain.size(),counterLstConcern.size());
						int max=Math.max(tempmax,counterLstStrength.size());
						
					        	 for (int f = 0; f < max; f++){
					        			row = sheet.createRow((short)rowIndex);
					        			rowIndex++;
					    	       
					        	 }
					     
					        	 rowIndex=originIndex;
					        int tempindex=originIndex;
		        for (int f = 0; f < counterLstMain.size(); f++){
		        	row = sheet.getRow((short)tempindex);
		        	
					Cell cell1=row.createCell((short)0);
					cell1.setCellValue(counterLstMain.get(f).getName());
					cell1.setCellStyle(answersStyle);
					Cell cell2=row.createCell((short)1);
					cell2.setCellStyle(answersStyle);
					cell2.setCellValue(counterLstMain.get(f).getId());
					tempindex++;
		          
		        	
		        }  
		        //rowIndex++;  
		      //  rowIndex=originIndex;
		    /*	row = sheet.getRow((short)rowIndex);
		    	  Cell cell1 = row.createCell(3);
		    	  cell1.setCellValue("Strengths");
		    	
		    	  cell1.setCellStyle(questionsStyle);
			         Cell cell2 = row.createCell(6);
			         cell2.setCellValue("Concerns");
			         cell2.setCellStyle(questionsStyle);
			         Cell cell11 = row.createCell(4);
			         Cell cell12 = row.createCell(7);
			         cell11.setCellStyle(questionsStyle);
			         cell12.setCellStyle(questionsStyle);
			        
			 */        //rowIndex++;
	           int tempIndex2=rowIndex;
	           int tmp1=rowIndex;
	           int tmp2=rowIndex;
	          if(counterLstStrength.size()>=counterLstConcern.size())
	        {
	        	 for (int f = 0; f < counterLstStrength.size(); f++){
	        			row = sheet.getRow((short)tempIndex2);
	    	        	tempIndex2++;
	    	       
	        	 }
	     
	        	 rowIndex=tempIndex2;
	        }
	        else
	        	
	        {   	//Create new rows according to the number of strengths
	  	        	 for (int f = 0; f < counterLstConcern.size(); f++){
	  	        			row = sheet.getRow((short)tempIndex2);
	  	    	        	tempIndex2++;
	  	    	     //   	rowIndex++;
	  	        	 }
	  	        	
	  		              
		        rowIndex=tempIndex2;
	        }
	  	       
		        for (int f = 0; f < counterLstConcern.size(); f++){
		        	row = sheet.getRow(((short)(tmp1)));
		        	
		        	
					Cell cell10=row.createCell((short)5);
					cell10.setCellValue(counterLstConcern.get(f).getName());
					cell10.setCellStyle(answersStyle);
					Cell cell33=row.createCell((short)6);
					cell33.setCellValue(counterLstConcern.get(f).getId());
					cell33.setCellStyle(answersStyle);
					tmp1++;
		            //Get the added copy Data to fill the excel sheet
		        	
		        	
		        }  
		        
		        
		        for (int f = 0; f < counterLstStrength.size(); f++){
		        	row = sheet.getRow((short)tmp2);
		        	
					Cell cell10=row.createCell((short)3);
					cell10.setCellValue(counterLstStrength.get(f).getName());
					cell10.setCellStyle(answersStyle);
					Cell cell33=row.createCell((short)4);
					cell33.setCellValue(counterLstStrength.get(f).getId());
					cell33.setCellStyle(answersStyle);
					
					
		        	/*row.createCell((short)1).setCellValue(counterLstStrength.get(f).getName());
		        	row.createCell((short)2).setCellValue(counterLstStrength.get(f).getId());
		        */	tmp2++;
		            //Get the added copy Data to fill the excel sheet
		        	
		        	
		        }  
		       
		        rowIndex++;
		        //Write Comments 
		        rowIndex++;
		        row = sheet.createRow((short)rowIndex);
		    	  Cell cell3 = row.createCell(3);
		    	  cell3.setCellValue("Comments - Strengths");
		    	  cell3.setCellStyle(questionsStyle);
			         Cell cell4 = row.createCell(5);
			         cell4.setCellValue("Comments - Concerns");
			         cell4.setCellStyle(questionsStyle);
			         rowIndex++;
			      //   rowIndex++;
			         
			         
		        int tempIndex3=rowIndex;
		           int tmp3=rowIndex;
		           int tmp4=rowIndex;
		          if(strengthsComments.size()>=concernsComments.size())
		        {
		        	//Create new rows according to the number of strengths
		        	 for (int f = 0; f < strengthsComments.size(); f++){
		        			row = sheet.createRow((short)tempIndex3);
		        			tempIndex3++;
		    	        //	rowIndex++;
		        	 }
		      
		        rowIndex=tempIndex3;
		        }
		        else
		        	
		        {   	//Create new rows according to the number of concerns
		  	        	 for (int f = 0; f < concernsComments.size(); f++){
		  	        			row = sheet.createRow((short)tempIndex3);
		  	        			tempIndex3++;
		  	    	     //   	rowIndex++;
		  	        	 }
		  	        
		  		              
			        rowIndex=tempIndex3;
		        }
		  	       
			        for (int f = 0; f < concernsComments.size(); f++){
			        	row = sheet.getRow(((short)(tmp3)));
			        	Cell comment=row.createCell((short)5);
			        	comment.setCellValue(concernsComments.get(f));
			        	comment.setCellStyle(strengthConcernsStyle);
			        	//row.createCell((short)7).setCellValue(counterLstConcern.get(f).getId());
			        	tmp3++;
			            //Get the added copy Data to fill the excel sheet
			        	
			        	
			        }  
			        
			        
			        for (int f = 0; f < strengthsComments.size(); f++){
			        	row = sheet.getRow((short)tmp4);
			          	Cell comment=row.createCell((short)3);
			        	comment.setCellValue(strengthsComments.get(f));
			        	comment.setCellStyle(strengthConcernsStyle);
			        	
			        	
			        	tmp4++;
			            //Get the added copy Data to fill the excel sheet
			        	
			        	
			        }  
			       
	
		        
		        
		        rowIndex++;
		        
				  
			}
		}
		catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	public String addLinebreaks(String input, int maxLineLength) {
	    StringTokenizer tok = new StringTokenizer(input, " ");
	    StringBuilder output = new StringBuilder(input.length());
	    int lineLen = 0;
	    while (tok.hasMoreTokens()) {
	        String word = tok.nextToken()+" ";

	        if (lineLen + word.length() > maxLineLength) {
	            output.append("\n");
	            lineLen = 0;
	        }
	        output.append(word);
	        lineLen += word.length();
	    }
	    return output.toString();
	}
	public List<StudentDTO> getTotalStudentsOfCourse(Integer courseID) {
		try
		{
			List<Student> students=courseStudentfacdae.getTotalStudentsOfCourse(courseID,2);
			List<StudentDTO> dtos= new  ArrayList<StudentDTO>();
			for(int i=0;i<students.size();i++)
			{
				StudentDTO dto=new StudentDTO();
				dto.setId(students.get(i).getId());
				dto.setFacultyId(students.get(i).getFileNo());
				dto.setName(students.get(i).getData().getNameInEnglish());
				dto.setMail(students.get(i).getData().getMail());
				dtos.add(dto);
			}
			return dtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	public Integer getStudentsNumberOfCourse(Integer courseID) {
		
		try{
			List<CourseStudent> students = courseStudentfacdae.getByCourseID(courseID);
			return students.size();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
			
		
	}
	public ICourseEvalQuestionsFacade getFacade() {
		return facade;
	}

	public void setFacade(ICourseEvalQuestionsFacade facade) {
		this.facade = facade;
	}

	public ICourseEvalAnswersFacade getAnsFacade() {
		return ansFacade;
	}

	public void setAnsFacade(ICourseEvalAnswersFacade ansFacade) {
		this.ansFacade = ansFacade;
	}

	public ICoursesFacade getCoursesInstFacade() {
		return coursesInstFacade;
	}

	public void setCoursesInstFacade(ICoursesFacade coursesInstFacade) {
		this.coursesInstFacade = coursesInstFacade;
	}

	public ICourseEvaluationFacade getCourseEvalQuesFacade() {
		return courseEvalQuesFacade;
	}

	public void setCourseEvalQuesFacade(ICourseEvaluationFacade courseEvalQuesFacade) {
		this.courseEvalQuesFacade = courseEvalQuesFacade;
	}

	public ICourseStudentFacade getCourseStudentfacdae() {
		return courseStudentfacdae;
	}

	public void setCourseStudentfacdae(ICourseStudentFacade courseStudentfacdae) {
		this.courseStudentfacdae = courseStudentfacdae;
	}
	
	
}

