/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.IGeneratePDFFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IStudentFacade;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean(name="GenerateExcelBean")
@ViewScoped
public class GenerateExcelBean {

	@ManagedProperty("#{ICourseEvalAnswersFacade}")
	private ICourseEvalAnswersFacade answersFacade;
	
	
	@ManagedProperty("#{ICourseEvalQuestionsFacade}")
	private ICourseEvalQuestionsFacade questionsFacade;;
	
	@ManagedProperty("#{IStudentFacade}")
	private IStudentFacade studentFacade;;
	
	@ManagedProperty("#{StudentAcademicPetFacadeImpl}")
	private IStudentAcademicPetFacade coursefacade;
	
	@ManagedProperty("#{IGeneratePDFFacade}")
	private IGeneratePDFFacade facade;
	
	private List<CourseEvalQuestionsDTO> questionsLst;
	 private CellStyle cs = null;
	 private CellStyle csBold = null;
	 private CellStyle csTop = null;
	 private CellStyle csRight = null;
	 private CellStyle csBottom = null;
	 private CellStyle csLeft = null;
	 private CellStyle csTopLeft = null;
	 private CellStyle csTopRight = null;
	 private CellStyle csBottomLeft = null;
	 private CellStyle csBottomRight = null;
	 
	 
	 
	
	@PostConstruct
	public void init(){
		try{
		questionsLst=questionsFacade.getAll();
		
		createExcel() ;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	//create excel styles
	private void setCellStyles(Workbook wb) {
		 
		  //font size 10
		  Font f = wb.createFont();
		  f.setFontHeightInPoints((short) 10);
		 
		  //Simple style 
		  cs = wb.createCellStyle();
		  cs.setFont(f);
		 
		  //Bold Fond
		  Font bold = wb.createFont();
		  bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		  bold.setFontHeightInPoints((short) 10);
		 
		  //Bold style 
		  csBold = wb.createCellStyle();
		  csBold.setBorderBottom(CellStyle.BORDER_THIN);
		  csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBold.setFont(bold);
		 
		  //Setup style for Top Border Line
		  csTop = wb.createCellStyle();
		  csTop.setBorderTop(CellStyle.BORDER_THIN);
		  csTop.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTop.setFont(f);
		 
		  //Setup style for Right Border Line
		  csRight = wb.createCellStyle();
		  csRight.setBorderRight(CellStyle.BORDER_THIN);
		  csRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csRight.setFont(f);
		 
		  //Setup style for Bottom Border Line
		  csBottom = wb.createCellStyle();
		  csBottom.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottom.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottom.setFont(f);
		 
		  //Setup style for Left Border Line
		  csLeft = wb.createCellStyle();
		  csLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csLeft.setFont(f);
		 
		  //Setup style for Top/Left corner cell Border Lines
		  csTopLeft = wb.createCellStyle();
		  csTopLeft.setBorderTop(CellStyle.BORDER_THIN);
		  csTopLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTopLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csTopLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csTopLeft.setFont(f);
		 
		  //Setup style for Top/Right corner cell Border Lines
		  csTopRight = wb.createCellStyle();
		  csTopRight.setBorderTop(CellStyle.BORDER_THIN);
		  csTopRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTopRight.setBorderRight(CellStyle.BORDER_THIN);
		  csTopRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csTopRight.setFont(f);
		 
		  //Setup style for Bottom/Left corner cell Border Lines
		  csBottomLeft = wb.createCellStyle();
		  csBottomLeft.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottomLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csBottomLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomLeft.setFont(f);
		 
		  //Setup style for Bottom/Right corner cell Border Lines
		  csBottomRight = wb.createCellStyle();
		  csBottomRight.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottomRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomRight.setBorderRight(CellStyle.BORDER_THIN);
		  csBottomRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomRight.setFont(f);
		 
		 }
		 
	//Create Columns
	private int insertHeaderInfoFac(Sheet sheet, int index){
		 
		  int rowIndex = index;
		  Row row = null;
		  Cell c = null;
		 
		 // get All questions 
		  
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  
		  //Student Name  (First Column)
		  c.setCellValue("Student Name");
		  c.setCellStyle(csBold);
		  c = row.createCell(1);
		  
		  //The remained columns of questions
		  List<CourseEvalQuestionsDTO> facQuestionsLst=new ArrayList<CourseEvalQuestionsDTO>();
			facQuestionsLst=questionsFacade.getByCategoryID(1);
	  
		  for(int i=0;i<facQuestionsLst.size();i++){
			  c.setCellValue(facQuestionsLst.get(i).getText());
			  c.setCellStyle(csBold);
			  c = row.createCell(i+2);
		  }
		  c = row.createCell(facQuestionsLst.size()+1);
		  c.setCellValue("Comment");
		  c.setCellStyle(csBold);
		
		 	  return rowIndex;
		 
		 }
	private int insertHeaderInfoCourses(Sheet sheet, int index){
		 
		  int rowIndex = index;
		  Row row = null;
		  Cell c = null;
		 
		 // get All questions 
		  
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  
		  //Student Name  (First Column)
		  c.setCellValue("Student Name");
		  c.setCellStyle(csBold);
		  c = row.createCell(1);
		  
		  //The remained columns of questions
		  List<CourseEvalQuestionsDTO> lst1=new ArrayList<CourseEvalQuestionsDTO>();
		  List<CourseEvalQuestionsDTO> questions=new ArrayList<CourseEvalQuestionsDTO>();
		  lst1=questionsFacade.getAll();
		  for(int i=0;i<lst1.size();i++)
		  {
			  if(!lst1.get(i).getCategory().equals(QuestionsCategory.Faculty_Eval))
				  questions.add(lst1.get(i));
		  }
	  
		
		  
		  for(int i=0;i<questions.size();i++){
			  c.setCellValue(questions.get(i).getText());
			  c.setCellStyle(csBold);
			  c = row.createCell(i+2);
		  }
		  c = row.createCell(questions.size()+1);
		  c.setCellValue("Comment");
		  c.setCellStyle(csBold);
		
		 	  return rowIndex;
		 
		 }
	
	//Add Data 
	private int insertDetailInfoInstructors(Sheet sheet, int index, int courseID, int insID){

		  int rowIndex = 0;
		  Row row = null;
		  Cell c = null;
		 
		  List<StudentDTO> students=new ArrayList<StudentDTO>();
		  students=studentFacade.getAll();
		  int indexx=1;
		    for(int i=1;i<students.size()-1;i++)
		    {
		    	// TODO 
		    	  
		    	// get answers by student id & question ID & course ID & Ins ID
		    	  List<CourseEvalAnswersDTO> studentAnswersForCourse=answersFacade.getByStudentIDAndCourseID(students.get(i).getId(), 111,1);
	  			  if(studentAnswersForCourse.size()>0)
	  			  {
  				 rowIndex =index+indexx;
	  			 row = sheet.createRow(rowIndex);
	  			 c = row.createCell(0);
				 c.setCellValue(students.get(i).getName());
				 c.setCellStyle(cs);
				 
				 indexx++;
	  			  }
	  			 List<CourseEvalQuestionsDTO> facQuestionsLst=new ArrayList<CourseEvalQuestionsDTO>();
	  			facQuestionsLst=questionsFacade.getByCategoryID(1);
		    	for(int j=0;j<facQuestionsLst.size();j++)
		    	{
		    		
		    		  List<CourseEvalAnswersDTO> studentAnswers=answersFacade.getByQuestionIDAndCourseIDAndInsIDAndStudentId
		    				  (facQuestionsLst.get(j).getId(), 111, 27, students.get(i).getId());
		 	    	 if(studentAnswers.size()>0)
		 	    	 {

		    			   c = row.createCell(j+1);
		    			   c.setCellValue(studentAnswers.get(0).getSelections());
		    			   c.setCellStyle(cs);
		    			   c = row.createCell(facQuestionsLst.size()+1);
		  				 c.setCellValue(studentAnswers.get(0).getComment());
		  				 c.setCellStyle(cs);
		  			     
		    			  
		 	    	 }
		 	    	
		    	}
		    	
		    }

		  return rowIndex;}
	private int insertDetailCourses(Sheet sheet, int index, int courseID){

		  int rowIndex = 0;
		  Row row = null;
		  Cell c = null;
		 
		  List<StudentDTO> students=new ArrayList<StudentDTO>();
		  students=studentFacade.getAll();
		  int indexx=1;
		    for(int i=1;i<students.size()-1;i++)
		    {
		    	// TODO 
		    	  
		    	// get answers by student id & question ID & course ID & Ins ID
		    	  List<CourseEvalAnswersDTO> studentAnswersForCourse=answersFacade.getByStudentIDAndCourseID(students.get(i).getId(), courseID,1);
	  			  if(studentAnswersForCourse.size()>0)
	  			  {
				 rowIndex =index+indexx;
	  			 row = sheet.createRow(rowIndex);
	  			 c = row.createCell(0);
				 c.setCellValue(students.get(i).getName());
				 c.setCellStyle(cs);
				 
				 indexx++;
	  			  }
	  		  			  //The remained columns of questions
	  			  List<CourseEvalQuestionsDTO> lst1=new ArrayList<CourseEvalQuestionsDTO>();
	  			  List<CourseEvalQuestionsDTO> questions=new ArrayList<CourseEvalQuestionsDTO>();
	  			  List<CourseEvalQuestionsDTO> otherQuestions=new ArrayList<CourseEvalQuestionsDTO>();
	  			  lst1=questionsFacade.getAll();
	  			  for(int q=0;q<lst1.size();q++)
	  			  {
	  				  if(!lst1.get(q).getCategory().equals(QuestionsCategory.Faculty_Eval)&&
	  						!lst1.get(q).getCategory().equals(QuestionsCategory.Other_Ques_Text))
	  					  questions.add(lst1.get(q));
	  			  }
	  			 for(int q=0;q<lst1.size();q++)
	  			  {
	  				  if(
	  						lst1.get(q).getCategory().equals(QuestionsCategory.Other_Ques_Text))
	  					otherQuestions.add(lst1.get(q));
	  			  }
	  			 
	  			  
		    	for(int j=0;j<questions.size();j++)
		    	{
		    		
		    		  List<CourseEvalAnswersDTO> studentAnswers=answersFacade.getByQuestionIDAndCourseIDAndStudentId(questions.get(j).getId(), courseID, students.get(i).getId());
		 	    	 if(studentAnswers.size()>0)
		 	    	 {

		    			   c = row.createCell(j+1);
		    			   c.setCellValue(studentAnswers.get(0).getSelections());
		    			   c.setCellStyle(cs);
		    			   if(studentAnswers.get(0).getComment()!=null&&!studentAnswers.get(0).getComment().equals(""))
		    			   {
		    			   c = row.createCell((otherQuestions.size()+questions.size())+1);
		    			  c.setCellValue(studentAnswers.get(0).getComment());
			  			   c.setCellStyle(cs);
		  			     
		    			   }
		 	    	 }
		 	    	
		    	}
		    	for(int j=0;j<otherQuestions.size();j++)
		    	{
		    		
		    		  List<CourseEvalAnswersDTO> studentAnswers=answersFacade.getByQuestionIDAndCourseIDAndStudentId(otherQuestions.get(j).getId(), courseID, students.get(i).getId());
		 	    	 if(studentAnswers.size()>0)
		 	    	 {

		    			   c = row.createCell(questions.size()+(j+1));
		    			   c.setCellValue(studentAnswers.get(0).getComment());
		    			   c.setCellStyle(cs);
		    			 
		  			     
		    			  
		 	    	 }
		 	    	
		    	}
		    	
		    	
		    }

		  return rowIndex;}
	
	 public void createExcel() {
		 //Faculty questions
		 List<CoursesDTO> courses= coursefacade.getAllCoursesBySemesterAndYear(1,2015);
		 for(int i=0;i<courses.size();i++)
		 {
			 List<BaseDTO> instructors=facade.getInstructorsOfCourse(courses.get(i).getId());
			 for(int j=0;j<instructors.size();j++)
			 {
		  try{
		 
		   Workbook wb = new XSSFWorkbook();
		   Sheet sheet = wb.createSheet("My Excel Report");
		 
		   //Setup some styles that we need for the Cells
		   setCellStyles(wb);
		 
		   //Get current Date and Time
		   Date date = new Date(System.currentTimeMillis());
		   DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		 
		   //Set Column Widths
		   sheet.setColumnWidth(0, 10000); 
		   sheet.setColumnWidth(1, 10000);
		   sheet.setColumnWidth(2, 10000);
		   sheet.setColumnWidth(3, 10000);
		   sheet.setColumnWidth(4, 10000);
		   sheet.setColumnWidth(5, 10000);
		   sheet.setColumnWidth(6, 10000);
		   sheet.setColumnWidth(7, 10000);
		   sheet.setColumnWidth(8, 10000);
		   sheet.setColumnWidth(9, 10000);
		   sheet.setColumnWidth(10, 10000);
		   sheet.setColumnWidth(11, 10000);
		   sheet.setColumnWidth(12, 10000);
		   sheet.setColumnWidth(13, 10000);
		   sheet.setColumnWidth(14, 10000);
		   sheet.setColumnWidth(15, 10000);
		   sheet.setColumnWidth(16, 10000);
		 
		   //Setup the Page margins - Left, Right, Top and Bottom
		   sheet.setMargin(Sheet.LeftMargin, 0.25);
		   sheet.setMargin(Sheet.RightMargin, 0.25);
		   sheet.setMargin(Sheet.TopMargin, 0.75);
		   sheet.setMargin(Sheet.BottomMargin, 0.75);
		 
		   //Setup the Header and Footer Margins
		   sheet.setMargin(Sheet.HeaderMargin, 0.25);
		   sheet.setMargin(Sheet.FooterMargin, 0.25);
		    
		   //If you are using HSSFWorkbook() instead of XSSFWorkbook()
		   //HSSFPrintSetup ps = (HSSFPrintSetup) sheet.getPrintSetup();
		   //ps.setHeaderMargin((double) .25);
		   //ps.setFooterMargin((double) .25);
		 

		 
		 
		   int rowIndex = 0;

		   //rowIndex = insertHeaderInfoFac(sheet, rowIndex);
		   rowIndex = insertDetailInfoInstructors(sheet, rowIndex, 111,27);
		 

		   File dir = new File("/home/heba/Desktop/CoursesExcelNew/"+courses.get(i).getName().replaceAll("/","-"));
			 dir.mkdirs();
			 File file = new File(dir, courses.get(i).getName().replaceAll("/","-")+"-"+instructors.get(j).getName()+".xlsx");
			 
          FileOutputStream fileOut = new FileOutputStream(file);
 
		    wb.write(fileOut);
		   fileOut.close();
		 
		  }
		  catch (Exception e) {
		   System.out.println(e);
		  }
		 }
			 
			  try{
					 
				   Workbook wb = new XSSFWorkbook();
				   Sheet sheet = wb.createSheet("My Excel Report");
				 
				   //Setup some styles that we need for the Cells
				   setCellStyles(wb);
				 
				   //Get current Date and Time
				   Date date = new Date(System.currentTimeMillis());
				   DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
				 
				   //Set Column Widths
				   sheet.setColumnWidth(0, 10000); 
				   sheet.setColumnWidth(1, 10000);
				   sheet.setColumnWidth(2, 10000);
				   sheet.setColumnWidth(3, 10000);
				   sheet.setColumnWidth(4, 10000);
				   sheet.setColumnWidth(5, 10000);
				   sheet.setColumnWidth(6, 10000);
				   sheet.setColumnWidth(7, 10000);
				   sheet.setColumnWidth(8, 10000);
				   sheet.setColumnWidth(9, 10000);
				   sheet.setColumnWidth(10, 10000);
				   sheet.setColumnWidth(11, 10000);
				   sheet.setColumnWidth(12, 10000);
				   sheet.setColumnWidth(13, 10000);
				   sheet.setColumnWidth(14, 10000);
				   sheet.setColumnWidth(15, 10000);
				   sheet.setColumnWidth(16, 10000);
				 
				   //Setup the Page margins - Left, Right, Top and Bottom
				   sheet.setMargin(Sheet.LeftMargin, 0.25);
				   sheet.setMargin(Sheet.RightMargin, 0.25);
				   sheet.setMargin(Sheet.TopMargin, 0.75);
				   sheet.setMargin(Sheet.BottomMargin, 0.75);
				 
				   //Setup the Header and Footer Margins
				   sheet.setMargin(Sheet.HeaderMargin, 0.25);
				   sheet.setMargin(Sheet.FooterMargin, 0.25);
				    
				   //If you are using HSSFWorkbook() instead of XSSFWorkbook()
				   //HSSFPrintSetup ps = (HSSFPrintSetup) sheet.getPrintSetup();
				   //ps.setHeaderMargin((double) .25);
				   //ps.setFooterMargin((double) .25);
				 

				 
				 
				   int rowIndex = 0;

				   rowIndex = insertHeaderInfoCourses(sheet, rowIndex);
				   rowIndex = insertDetailCourses(sheet, rowIndex, courses.get(i).getId());
				 

				   File dir = new File("/home/heba/Desktop/CoursesExcelNew/"+courses.get(i).getName().replaceAll("/","-"));
					 dir.mkdirs();
					 File file = new File(dir, courses.get(i).getName().replaceAll("/","-")+".xlsx");
					 
		          FileOutputStream fileOut = new FileOutputStream(file);
		 
				    wb.write(fileOut);
				   fileOut.close();
				 
				  }
				  catch (Exception e) {
				   System.out.println(e);
				  }
		 }
		 }
	 
	public ICourseEvalAnswersFacade getAnswersFacade() {
		return answersFacade;
	}


	public void setAnswersFacade(ICourseEvalAnswersFacade answersFacade) {
		this.answersFacade = answersFacade;
	}



	public ICourseEvalQuestionsFacade getQuestionsFacade() {
		return questionsFacade;
	}

	public void setQuestionsFacade(ICourseEvalQuestionsFacade questionsFacade) {
		this.questionsFacade = questionsFacade;
	}

	public CellStyle getCs() {
		return cs;
	}


	public void setCs(CellStyle cs) {
		this.cs = cs;
	}


	public CellStyle getCsBold() {
		return csBold;
	}


	public void setCsBold(CellStyle csBold) {
		this.csBold = csBold;
	}


	public CellStyle getCsTop() {
		return csTop;
	}


	public void setCsTop(CellStyle csTop) {
		this.csTop = csTop;
	}


	public CellStyle getCsRight() {
		return csRight;
	}


	public void setCsRight(CellStyle csRight) {
		this.csRight = csRight;
	}


	public CellStyle getCsBottom() {
		return csBottom;
	}


	public void setCsBottom(CellStyle csBottom) {
		this.csBottom = csBottom;
	}


	public CellStyle getCsLeft() {
		return csLeft;
	}


	public void setCsLeft(CellStyle csLeft) {
		this.csLeft = csLeft;
	}


	public CellStyle getCsTopLeft() {
		return csTopLeft;
	}


	public void setCsTopLeft(CellStyle csTopLeft) {
		this.csTopLeft = csTopLeft;
	}


	public CellStyle getCsTopRight() {
		return csTopRight;
	}


	public void setCsTopRight(CellStyle csTopRight) {
		this.csTopRight = csTopRight;
	}


	public CellStyle getCsBottomLeft() {
		return csBottomLeft;
	}


	public void setCsBottomLeft(CellStyle csBottomLeft) {
		this.csBottomLeft = csBottomLeft;
	}


	public CellStyle getCsBottomRight() {
		return csBottomRight;
	}


	public void setCsBottomRight(CellStyle csBottomRight) {
		this.csBottomRight = csBottomRight;
	}

	public List<CourseEvalQuestionsDTO> getQuestionsLst() {
		return questionsLst;
	}

	public void setQuestionsLst(List<CourseEvalQuestionsDTO> questionsLst) {
		this.questionsLst = questionsLst;
	}

	public IStudentFacade getStudentFacade() {
		return studentFacade;
	}

	public void setStudentFacade(IStudentFacade studentFacade) {
		this.studentFacade = studentFacade;
	}

	public IStudentAcademicPetFacade getCoursefacade() {
		return coursefacade;
	}

	public void setCoursefacade(IStudentAcademicPetFacade coursefacade) {
		this.coursefacade = coursefacade;
	}

	public IGeneratePDFFacade getFacade() {
		return facade;
	}

	public void setFacade(IGeneratePDFFacade facade) {
		this.facade = facade;
	}
	
	
	
}
