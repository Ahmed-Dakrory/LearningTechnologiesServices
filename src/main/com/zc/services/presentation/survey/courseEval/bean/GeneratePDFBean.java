/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.bean;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.mapping.Array;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.Legend;
import org.jfree.chart.StandardLegend;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.IGeneratePDFFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean(name="GeneratePDFBean")
@ViewScoped
public class GeneratePDFBean {

	@ManagedProperty("#{IGeneratePDFFacade}")
	private IGeneratePDFFacade facade;
	
	@ManagedProperty("#{ICourseEvalAnswersFacade}")
	private ICourseEvalAnswersFacade ansFacade;
	
	@ManagedProperty("#{StudentAcademicPetFacadeImpl}")
	private IStudentAcademicPetFacade coursefacade;
	
	private String name;
	List<Integer> notLabCourses=new ArrayList<Integer>();
	
	//Excel styles
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
	public void init()
	{
		 Font arabic=FontFactory.getFont("/home/omnya/Desktop/imgs/arial.ttf",BaseFont.IDENTITY_H,10,Font.NORMAL);
		 
		notLabCourses=new ArrayList<Integer>();
		notLabCourses.add(60);
		notLabCourses.add(120);
		notLabCourses.add(145);
		notLabCourses.add(94);
		notLabCourses.add(119);
		notLabCourses.add(41);
		notLabCourses.add(33);
		notLabCourses.add(126);
		notLabCourses.add(108);
		notLabCourses.add(109);
		notLabCourses.add(110);
		notLabCourses.add(111);
		notLabCourses.add(125);
		notLabCourses.add(27);
		notLabCourses.add(3);
		notLabCourses.add(118);
		notLabCourses.add(92);
		notLabCourses.add(148);
		notLabCourses.add(117);
		notLabCourses.add(116);
		notLabCourses.add(149);
		notLabCourses.add(23);
		notLabCourses.add(129);
		notLabCourses.add(128);
		notLabCourses.add(19);
		notLabCourses.add(149);
		
		generateExcelSheet();
		
		
		
		
		//List<BaseDTO>courses=facade.getAllCourses();
		List<BaseDTO>courses=facade.getCoursesByYearAndSem(2015,0);
		for(int c=0;c<courses.size();c++)
		{
			// get instructors
			List<BaseDTO> instructors=facade.getInstructorsOfCourse(courses.get(c).getId());
			Integer totalNumberOfStudentsInCourse=ansFacade.getStudentsNumberOfCourse(courses.get(c).getId());
			for(int i=0;i<instructors.size();i++)
			{
				// get answers by course and instructor
				List<CourseEvalQuestionsDTO> facultyEvalAnswers=new ArrayList<CourseEvalQuestionsDTO>();
				facultyEvalAnswers=ansFacade.getFacEvalByCourseIDAndInstructorID(courses.get(c).getId(), instructors.get(i).getId());
				List<String> facultyComments=new ArrayList<String>();
				facultyComments=ansFacade.getCommentsByCategoryIDAndCourseIDAndInstructorID(courses.get(c).getId(),instructors.get(i).getId(),1);
				///////Create PDF/////////
				try{
				  Document document = new Document();
					 Font blue = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, new CMYKColor(255, 0, 0, 0));
					 File dir = new File("/home/omnya/Desktop/Courses/"+courses.get(c).getName().replaceAll("/","_"));
					 dir.mkdirs();
					 File file = new File(dir, courses.get(c).getName().replaceAll("/","_")+instructors.get(i).getName()+".pdf");
					 FileOutputStream out = new FileOutputStream(file);
					 
			         PdfWriter writer = PdfWriter.getInstance(document, out);
			         document.open();
			         Font fontques=FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
			         List<StudentDTO> students=ansFacade.getTotalStudentsOfCourse(courses.get(c).getId(),1);
			         document.add(new Paragraph(courses.get(c).getName()+"/ total number of students : "+students.size(),blue));
			         for(int a=0;a<facultyEvalAnswers.size();a++){
			    
			         //document.add(new Paragraph(a+1+" - "+facultyEvalAnswers.get(a).getText()+" of "+totalNumberOfStudentsInCourse));
			         // pie chart 
                      Integer totalSubmitted=0;
			        DefaultPieDataset myPiedataset = new DefaultPieDataset();
                   for (Map.Entry<String, Number> entry : facultyEvalAnswers.get(a).getModel().getData().entrySet())
                    {
                	   
                      //System.out.println(entry.getKey().substring(0,entry.getKey().indexOf(" :")) + "/" + entry.getValue());
                     
 		             myPiedataset.setValue(entry.getKey().substring(0,entry.getKey().indexOf(" :")) ,entry.getValue());
 		            totalSubmitted+=(Integer)entry.getValue();
 		            
                   }
                double first =totalSubmitted;
           		double second=totalNumberOfStudentsInCourse;
           		double percentage=(first/second)*100;
           		//double number=24.2378;
           		 if (2 < 0) throw new IllegalArgumentException();

           		    long factor = (long) Math.pow(10, 2);
           		    percentage = percentage * factor;
           		    long tmp = Math.round(percentage);
                   document.add(new Paragraph(a+1+" - "+facultyEvalAnswers.get(a).getText()+"( "+totalSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));

		             
		             JFreeChart PDFPieChart=ChartFactory.createPieChart("",myPiedataset,false,true,false);
		             document.addTitle("How to Add a Pie Chart to a PDF file using iText");
		             document.addAuthor("Learning Technologies");                
		             document.addKeywords("iText,PieChart,JFreeChart,PDF,Example Tutorial");
		             final PiePlot plot = (PiePlot) PDFPieChart.getPlot();
		             plot.setBackgroundPaint(Color.white);
		             plot.setCircular(true);
		           
		             plot.setLabelGenerator(new StandardPieItemLabelGenerator(
		                 "{0},{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
		             ));
		             plot.setNoDataMessage("No data available");
		             plot.setSectionPaint(1, new Color(217,179, 39));
		             plot.setSectionPaint(2,new Color(69, 140, 65));
		             plot.setSectionPaint(3, new Color(207, 38, 39));
		             plot.setSectionPaint(4,new Color(92, 51, 97));
		             plot.setSectionPaint(5, new Color(94, 48, 50));
		             plot.setShadowYOffset(0);
		             plot.setShadowXOffset(0);
		             plot.setBaseSectionOutlinePaint(Color.white);
		             int style = Font.UNDEFINED ;
		             java.awt.Font font = new java.awt.Font("Garamond", style , 7);
		             StandardLegend legend = new StandardLegend();
		             legend.setPreferredWidth(120);
		             legend.setPadding(new RectangleInsets(10, 60, 5, 10));
		             legend.setAnchor(Legend.EAST);
		             legend.setItemFont(font);
		             PDFPieChart.setLegend(legend);
		             PDFPieChart.setBackgroundPaint(Color.white);
		             PDFPieChart.setBorderPaint(Color.white);
		             plot.setLabelFont(font);
		             PdfContentByte Add_Chart_Content= writer.getDirectContent();
		             PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,200);
		             Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,200,new DefaultFontMapper());                
		             Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,200);
		             PDFPieChart.draw(Graphics_Chart,Chart_Region);            
		             Graphics_Chart.dispose();
		             Image chartImage = Image.getInstance(template_Chart_Holder);
		             document.add(chartImage);
			         
			         LineSeparator separator = new LineSeparator();
			         separator.setPercentage(59500f / 523f);
			         Chunk linebreak = new Chunk(separator);
			         document.add(linebreak);
			        	 
			         
			         }
			        
			     	    
				         document.add(new Paragraph( "Comments ", blue));
				         for(int k=0;k<facultyComments.size();k++)
				         {
				        	
				        		
				        		   
				                 PdfPTable table=new PdfPTable(1);
				                 table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
				                 
				                 PdfPCell cell;
				                 String temp=facultyComments.get(k).toString();
				                 System.out.println(temp);
				                 cell=new PdfPCell(new Phrase(k+1+" - "+temp,arabic));
				                 cell.disableBorderSide(1);
				                //cell.disableBorderSide(2);
				                 cell.disableBorderSide(5);
				                 cell.disableBorderSide(8);
				                 cell.setPaddingBottom(20);
				                 cell.setPaddingTop(20);
				                 cell.setBorderColor(new BaseColor(224,222, 222));
				                 table.addCell(cell);
				                 
				                 document.add(table);
				        	 document.add(new Paragraph(""));
				        	 
				         }
		              /*   for(int i=0;i<2;i++)
		                 {
		                	   document.add(new Paragraph(i+1+".Comment"));
		                 }*/

			         document.close();
			         writer.close();
			      } catch (DocumentException e)
			      {
			         e.printStackTrace();
			      } catch (FileNotFoundException e)
			      {
			         e.printStackTrace();
			      }
				
			///////Create PDF for course/////////
				try{
					List<CourseEvalQuestionsDTO> courseEvalCourseAnswers=new ArrayList<CourseEvalQuestionsDTO>();
					List<CourseEvalQuestionsDTO> courseEvalAssignAnswers=new ArrayList<CourseEvalQuestionsDTO>();
					courseEvalAssignAnswers=ansFacade.getCourseAssignByCourseIDAndInstructorID(courses.get(c).getId(), instructors.get(i).getId());
					courseEvalCourseAnswers=ansFacade.getCourseByCourseIDAndInstructorID(courses.get(c).getId(), instructors.get(i).getId());
				  
                     Document document = new Document();
					 Font blue = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new CMYKColor(255, 0, 0, 0));
					 File dir = new File("/home/omnya/Desktop/Courses/"+courses.get(c).getName().replaceAll("/","_"));
					 dir.mkdirs();
					 File file = new File(dir, courses.get(c).getName().replaceAll("/","_")+".pdf");
					 FileOutputStream out = new FileOutputStream(file);
					 
			         PdfWriter writer = PdfWriter.getInstance(document, out);
			         document.open();
			         Font fontques=FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
			         //document.add(new Paragraph(courses.get(c).getName()+"- total number : 60 students",blue));
			         List<StudentDTO> students=ansFacade.getTotalStudentsOfCourse(courses.get(c).getId(),1);
			        document.add(new Paragraph(courses.get(c).getName()+"/ total number of students : "+students.size(),blue));
			         document.add(new Paragraph("Course Evaluation:"));
			         for(int a=0;a<courseEvalCourseAnswers.size();a++){
			    
			         
			          // pie chart 

			         
			         
			         Integer courseEvalSubmitted=0;
			        DefaultPieDataset myPiedataset = new DefaultPieDataset();
	               for (Map.Entry<String, Number> entry : courseEvalCourseAnswers.get(a).getModel().getData().entrySet())
	                {
	            	   
	                  //System.out.println(entry.getKey().substring(0,entry.getKey().indexOf(" :")) + "/" + entry.getValue());
	                 
			             myPiedataset.setValue(entry.getKey().substring(0,entry.getKey().indexOf(" :")) ,entry.getValue());
			             courseEvalSubmitted+=(Integer)entry.getValue();
	               }

	               double first =courseEvalSubmitted;
	           		double second=totalNumberOfStudentsInCourse;
	           		double percentage=(first/second)*100;
	           		//double number=24.2378;
	           		 if (2 < 0) throw new IllegalArgumentException();

	           		    long factor = (long) Math.pow(10, 2);
	           		    percentage = percentage * factor;
	           		    long tmp = Math.round(percentage);
	                 //  document.add(new Paragraph(a+1+" - "+facultyEvalAnswers.get(a).getText()+"( "+courseEvalSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));

			          
	                   
	               document.add(new Paragraph(a+1+" - "+courseEvalCourseAnswers.get(a).getText()+"( "+courseEvalSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));
				      
		             
		             JFreeChart PDFPieChart=ChartFactory.createPieChart("",myPiedataset,false,true,false);
		             document.addTitle("How to Add a Pie Chart to a PDF file using iText");
		             document.addAuthor("Learning Technologies");                
		             document.addKeywords("iText,PieChart,JFreeChart,PDF,Example Tutorial");
		             final PiePlot plot = (PiePlot) PDFPieChart.getPlot();
		             plot.setBackgroundPaint(Color.white);
		             plot.setCircular(true);
		           
		             plot.setLabelGenerator(new StandardPieItemLabelGenerator(
		                 "{0},{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
		             ));
		             plot.setNoDataMessage("No data available");
		             plot.setSectionPaint(1, new Color(217,179, 39));
		             plot.setSectionPaint(2,new Color(69, 140, 65));
		             plot.setSectionPaint(3, new Color(207, 38, 39));
		             plot.setSectionPaint(4,new Color(92, 51, 97));
		             plot.setSectionPaint(5, new Color(94, 48, 50));
		             plot.setShadowYOffset(0);
		             plot.setShadowXOffset(0);
		             plot.setBaseSectionOutlinePaint(Color.white);
		             int style = Font.UNDEFINED ;
		             java.awt.Font font = new java.awt.Font("Garamond", style , 7);
		             StandardLegend legend = new StandardLegend();
		             legend.setPreferredWidth(120);
		             legend.setPadding(new RectangleInsets(10, 60, 5, 10));
		             legend.setAnchor(Legend.EAST);
		             legend.setItemFont(font);
		             PDFPieChart.setLegend(legend);
		             PDFPieChart.setBackgroundPaint(Color.white);
		             PDFPieChart.setBorderPaint(Color.white);
		             plot.setLabelFont(font);
		             PdfContentByte Add_Chart_Content= writer.getDirectContent();
		             PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,210);
		             Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,210,new DefaultFontMapper());                
		             Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,210);
		             PDFPieChart.draw(Graphics_Chart,Chart_Region);            
		             Graphics_Chart.dispose();
		             Image chartImage = Image.getInstance(template_Chart_Holder);
		             document.add(chartImage);
			         
			         LineSeparator separator = new LineSeparator();
			         separator.setPercentage(59500f / 523f);
			         Chunk linebreak = new Chunk(separator);
			         //document.add(linebreak);
			        
			         
			         
			         }
			         document.newPage();
			         document.add(new Paragraph("Online Assignments"));
			         for(int a=0;a<courseEvalAssignAnswers.size();a++){
			    
			         
			         // pie chart 

			        DefaultPieDataset myPiedataset = new DefaultPieDataset();
			        Integer totalNumberSubmitted=0;
	               for (Map.Entry<String, Number> entry : courseEvalAssignAnswers.get(a).getModel().getData().entrySet())
	                {
	            	   
	                  //System.out.println(entry.getKey().substring(0,entry.getKey().indexOf(" :")) + "/" + entry.getValue());
	                 
			             myPiedataset.setValue(entry.getKey().substring(0,entry.getKey().indexOf(" :")) ,entry.getValue());
			             totalNumberSubmitted+=(Integer)entry.getValue();
			            
	               }

	               double first =totalNumberSubmitted;
	           		double second=totalNumberOfStudentsInCourse;
	           		double percentage=(first/second)*100;
	           		//double number=24.2378;
	           		 if (2 < 0) throw new IllegalArgumentException();

	           		    long factor = (long) Math.pow(10, 2);
	           		    percentage = percentage * factor;
	           		    long tmp = Math.round(percentage);
	                 //  document.add(new Paragraph(a+1+" - "+facultyEvalAnswers.get(a).getText()+"( "+courseEvalSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));
	           		 document.add(new Paragraph(a+1+" - "+courseEvalAssignAnswers.get(a).getText()+"( "+totalNumberSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));
				        
		             
		             JFreeChart PDFPieChart=ChartFactory.createPieChart("",myPiedataset,false,true,false);
		             document.addTitle("How to Add a Pie Chart to a PDF file using iText");
		             document.addAuthor("Learning Technologies");                
		             document.addKeywords("iText,PieChart,JFreeChart,PDF,Example Tutorial");
		             final PiePlot plot = (PiePlot) PDFPieChart.getPlot();
		             plot.setBackgroundPaint(Color.white);
		             plot.setCircular(true);
		           
		             plot.setLabelGenerator(new StandardPieItemLabelGenerator(
		                 "{0},{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
		             ));
		             plot.setNoDataMessage("No data available");
		             plot.setSectionPaint(1, new Color(217,179, 39));
		             plot.setSectionPaint(2,new Color(69, 140, 65));
		             plot.setSectionPaint(3, new Color(207, 38, 39));
		             plot.setSectionPaint(4,new Color(92, 51, 97));
		             plot.setSectionPaint(5, new Color(94, 48, 50));
		             plot.setShadowYOffset(0);
		             plot.setShadowXOffset(0);
		             plot.setBaseSectionOutlinePaint(Color.white);
		             int style = Font.UNDEFINED ;
		             java.awt.Font font = new java.awt.Font("Garamond", style , 7);
		             StandardLegend legend = new StandardLegend();
		             legend.setPreferredWidth(120);
		             legend.setPadding(new RectangleInsets(10, 60, 5, 10));
		             legend.setAnchor(Legend.EAST);
		             legend.setItemFont(font);
		             PDFPieChart.setLegend(legend);
		             PDFPieChart.setBackgroundPaint(Color.white);
		             PDFPieChart.setBorderPaint(Color.white);
		             plot.setLabelFont(font);
		             PdfContentByte Add_Chart_Content= writer.getDirectContent();
		             PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,210);
		             Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,210,new DefaultFontMapper());                
		             Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,210);
		             PDFPieChart.draw(Graphics_Chart,Chart_Region);            
		             Graphics_Chart.dispose();
		             Image chartImage = Image.getInstance(template_Chart_Holder);
		             document.add(chartImage);
			         
			         LineSeparator separator = new LineSeparator();
			         separator.setPercentage(59500f / 523f);
			         Chunk linebreak = new Chunk(separator);
			         //document.add(linebreak);
			        
			         
			         //Add TA eval 
			      
			        
			         }
			         document.newPage();
			         List<CourseEvalQuestionsDTO> teachingAsisEvalAnswers=new ArrayList<CourseEvalQuestionsDTO>();
			     	 teachingAsisEvalAnswers=ansFacade.getTAByCourseIDAndInstructorID(courses.get(c).getId(),instructors.get(i).getId());
			     	 List<String> taComments=new ArrayList<String>();
					 taComments=ansFacade.getCommentsByCategoryIDAndCourseID(courses.get(c).getId(),4);
					 
			         document.add(new Paragraph("Teaching Assistant Evaluation"));
			         for(int a=0;a<teachingAsisEvalAnswers.size();a++){
			    
			         
			         //document.add(new Paragraph(a+1+" - "+teachingAsisEvalAnswers.get(a).getText()));
			         // pie chart 

			        DefaultPieDataset myPiedataset = new DefaultPieDataset();
			        Integer totalNumberSubmitted=0;
	               for (Map.Entry<String, Number> entry : teachingAsisEvalAnswers.get(a).getModel().getData().entrySet())
	                {
	            	   
	                 // System.out.println(entry.getKey().substring(0,entry.getKey().indexOf(" :")) + "/" + entry.getValue());
	                 
			             myPiedataset.setValue(entry.getKey().substring(0,entry.getKey().indexOf(" :")) ,entry.getValue());
			             totalNumberSubmitted+=(Integer)entry.getValue();
			            
	               }
	               double first =totalNumberSubmitted;
	           		double second=totalNumberOfStudentsInCourse;
	           		double percentage=(first/second)*100;
	           		//double number=24.2378;
	           		 if (2 < 0) throw new IllegalArgumentException();

	           		    long factor = (long) Math.pow(10, 2);
	           		    percentage = percentage * factor;
	           		    long tmp = Math.round(percentage);
	                 //  document.add(new Paragraph(a+1+" - "+facultyEvalAnswers.get(a).getText()+"( "+courseEvalSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));
	           
	           		 document.add(new Paragraph(a+1+" - "+teachingAsisEvalAnswers.get(a).getText()+"( "+totalNumberSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));
				        
		             
		             JFreeChart PDFPieChart=ChartFactory.createPieChart("",myPiedataset,false,true,false);
		             document.addTitle("How to Add a Pie Chart to a PDF file using iText");
		             document.addAuthor("Learning Technologies");                
		             document.addKeywords("iText,PieChart,JFreeChart,PDF,Example Tutorial");
		             final PiePlot plot = (PiePlot) PDFPieChart.getPlot();
		             plot.setBackgroundPaint(Color.white);
		             plot.setCircular(true);
		           
		             plot.setLabelGenerator(new StandardPieItemLabelGenerator(
		                 "{0},{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
		             ));
		             plot.setNoDataMessage("No data available");
		             plot.setSectionPaint(1, new Color(217,179, 39));
		             plot.setSectionPaint(2,new Color(69, 140, 65));
		             plot.setSectionPaint(3, new Color(207, 38, 39));
		             plot.setSectionPaint(4,new Color(92, 51, 97));
		             plot.setSectionPaint(5, new Color(94, 48, 50));
		             plot.setShadowYOffset(0);
		             plot.setShadowXOffset(0);
		             plot.setBaseSectionOutlinePaint(Color.white);
		             int style = Font.UNDEFINED ;
		             java.awt.Font font = new java.awt.Font("Garamond", style , 7);
		             StandardLegend legend = new StandardLegend();
		             legend.setPreferredWidth(120);
		             legend.setPadding(new RectangleInsets(10, 60, 5, 10));
		             legend.setAnchor(Legend.EAST);
		             legend.setItemFont(font);
		             PDFPieChart.setLegend(legend);
		             PDFPieChart.setBackgroundPaint(Color.white);
		             PDFPieChart.setBorderPaint(Color.white);
		             plot.setLabelFont(font);
		             PdfContentByte Add_Chart_Content= writer.getDirectContent();
		             PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,220);
		             Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,220,new DefaultFontMapper());                
		             Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,220);
		             PDFPieChart.draw(Graphics_Chart,Chart_Region);            
		             Graphics_Chart.dispose();
		             Image chartImage = Image.getInstance(template_Chart_Holder);
		             document.add(chartImage);
			         
			         LineSeparator separator = new LineSeparator();
			         separator.setPercentage(59500f / 523f);
			         Chunk linebreak = new Chunk(separator);
			         //document.add(linebreak);
			        
			         
			         }
			         
			         
			         
			         
			         document.add(new Paragraph( "Comments ", blue));
			         for(int k=0;k<taComments.size();k++)
			         {
			        	 if(!taComments.get(k).equals(""))
			        	 {
			        		 PdfPTable table=new PdfPTable(1);
			                 table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			                 
			                 PdfPCell cell;
			                 String temp=taComments.get(k).toString();
			                 System.out.println(temp);
			                 cell=new PdfPCell(new Phrase(k+1+" - "+temp,arabic));
			                 cell.disableBorderSide(1);
			                //cell.disableBorderSide(2);
			                 cell.disableBorderSide(5);
			                 cell.disableBorderSide(8);
			                 cell.setPaddingBottom(20);
			                 cell.setPaddingTop(20);
			                 cell.setBorderColor(new BaseColor(224,222, 222));
			                 table.addCell(cell);
			                 
			                 document.add(table);
			        	 }
			         }
			         
			         
			         
			         boolean existed = false;
			     	for(int l=0;l<notLabCourses.size();l++)
			     	{
			     		if(notLabCourses.get(l)==courses.get(c).getId())
			     		{
			     			existed=true;
			     		
			     			break;
			     		}
			     	}
			     	if(!existed)
			     	{
			     		 document.newPage();
			         //Add Lab Eval
			     	List<CourseEvalQuestionsDTO> labExpAnswers=new ArrayList<CourseEvalQuestionsDTO>();
			    	List<CourseEvalQuestionsDTO> labFacAnswers=new ArrayList<CourseEvalQuestionsDTO>();
			    	labExpAnswers=ansFacade.getLabExpByCourseIDAndInstructorID(courses.get(c).getId(), instructors.get(i).getId());
		    		labFacAnswers=ansFacade.getLabFacByCourseIDAndInstructorID(courses.get(c).getId(), instructors.get(i).getId());
			         
					 
					 
			         document.add(new Paragraph("Laboratory Faculty"));
			         for(int a=0;a<labFacAnswers.size();a++){
			    
			         
			        // pie chart 
			         Integer totalNumberSubmitted=0;
			        DefaultPieDataset myPiedataset = new DefaultPieDataset();
	               for (Map.Entry<String, Number> entry : labFacAnswers.get(a).getModel().getData().entrySet())
	                {
	            	   
	                  //System.out.println(entry.getKey().substring(0,entry.getKey().indexOf(" :")) + "/" + entry.getValue());
	                 
			             myPiedataset.setValue(entry.getKey().substring(0,entry.getKey().indexOf(" :")) ,entry.getValue());
			             totalNumberSubmitted+=(Integer)entry.getValue();
	               }

	               double first =totalNumberSubmitted;
	           		double second=totalNumberOfStudentsInCourse;
	           		double percentage=(first/second)*100;
	           		//double number=24.2378;
	           		 if (2 < 0) throw new IllegalArgumentException();

	           		    long factor = (long) Math.pow(10, 2);
	           		    percentage = percentage * factor;
	           		    long tmp = Math.round(percentage);
	                 //  document.add(new Paragraph(a+1+" - "+facultyEvalAnswers.get(a).getText()+"( "+courseEvalSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));
	           		 document.add(new Paragraph(a+1+" - "+labFacAnswers.get(a).getText()+"( "+totalNumberSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));
			         
		             
		             JFreeChart PDFPieChart=ChartFactory.createPieChart("",myPiedataset,false,true,false);
		             document.addTitle("How to Add a Pie Chart to a PDF file using iText");
		             document.addAuthor("Learning Technologies");                
		             document.addKeywords("iText,PieChart,JFreeChart,PDF,Example Tutorial");
		             final PiePlot plot = (PiePlot) PDFPieChart.getPlot();
		             plot.setBackgroundPaint(Color.white);
		             plot.setCircular(true);
		           
		             plot.setLabelGenerator(new StandardPieItemLabelGenerator(
		                 "{0},{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
		             ));
		             plot.setNoDataMessage("No data available");
		             plot.setSectionPaint(1, new Color(217,179, 39));
		             plot.setSectionPaint(2,new Color(69, 140, 65));
		             plot.setSectionPaint(3, new Color(207, 38, 39));
		             plot.setSectionPaint(4,new Color(92, 51, 97));
		             plot.setSectionPaint(5, new Color(94, 48, 50));
		             plot.setShadowYOffset(0);
		             plot.setShadowXOffset(0);
		             plot.setBaseSectionOutlinePaint(Color.white);
		             int style = Font.UNDEFINED ;
		             java.awt.Font font = new java.awt.Font("Garamond", style , 7);
		             StandardLegend legend = new StandardLegend();
		             legend.setPreferredWidth(120);
		             legend.setPadding(new RectangleInsets(10, 60, 5, 10));
		             legend.setAnchor(Legend.EAST);
		             legend.setItemFont(font);
		             PDFPieChart.setLegend(legend);
		             PDFPieChart.setBackgroundPaint(Color.white);
		             PDFPieChart.setBorderPaint(Color.white);
		             plot.setLabelFont(font);
		             PdfContentByte Add_Chart_Content= writer.getDirectContent();
		             PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,210);
		             Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,210,new DefaultFontMapper());                
		             Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,210);
		             PDFPieChart.draw(Graphics_Chart,Chart_Region);            
		             Graphics_Chart.dispose();
		             Image chartImage = Image.getInstance(template_Chart_Holder);
		             document.add(chartImage);
			         
			         LineSeparator separator = new LineSeparator();
			         separator.setPercentage(59500f / 523f);
			         Chunk linebreak = new Chunk(separator);
			        // document.add(linebreak);
			        
			         
			         
			         }
			         document.newPage();
			         document.add(new Paragraph("Laboratory Experiments"));
			         
			         for(int a=0;a<labExpAnswers.size();a++){
						    
				         
				        
				         // pie chart 
				         Integer totalNumberSubmitted=0;
				        DefaultPieDataset myPiedataset = new DefaultPieDataset();
		               for (Map.Entry<String, Number> entry : labExpAnswers.get(a).getModel().getData().entrySet())
		                {
		            	   
		                 // System.out.println(entry.getKey().substring(0,entry.getKey().indexOf(" :")) + "/" + entry.getValue());
		                 
				             myPiedataset.setValue(entry.getKey().substring(0,entry.getKey().indexOf(" :")) ,entry.getValue());
				             totalNumberSubmitted+=(Integer)entry.getValue();
		               }
		               double first =totalNumberSubmitted;
		           		double second=totalNumberOfStudentsInCourse;
		           		double percentage=(first/second)*100;
		           		//double number=24.2378;
		           		 if (2 < 0) throw new IllegalArgumentException();

		           		    long factor = (long) Math.pow(10, 2);
		           		    percentage = percentage * factor;
		           		    long tmp = Math.round(percentage);
		                 //  document.add(new Paragraph(a+1+" - "+facultyEvalAnswers.get(a).getText()+"( "+courseEvalSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));
		           	
		           		 document.add(new Paragraph(a+1+" - "+labExpAnswers.get(a).getText()+"( "+totalNumberSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));
			             
			             JFreeChart PDFPieChart=ChartFactory.createPieChart("",myPiedataset,false,true,false);
			             document.addTitle("How to Add a Pie Chart to a PDF file using iText");
			             document.addAuthor("Learning Technologies");                
			             document.addKeywords("iText,PieChart,JFreeChart,PDF,Example Tutorial");
			             final PiePlot plot = (PiePlot) PDFPieChart.getPlot();
			             plot.setBackgroundPaint(Color.white);
			             plot.setCircular(true);
			           
			             plot.setLabelGenerator(new StandardPieItemLabelGenerator(
			                 "{0},{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
			             ));
			             plot.setNoDataMessage("No data available");
			             plot.setSectionPaint(1, new Color(217,179, 39));
			             plot.setSectionPaint(2,new Color(69, 140, 65));
			             plot.setSectionPaint(3, new Color(207, 38, 39));
			             plot.setSectionPaint(4,new Color(92, 51, 97));
			             plot.setSectionPaint(5, new Color(94, 48, 50));
			             plot.setShadowYOffset(0);
			             plot.setShadowXOffset(0);
			             plot.setBaseSectionOutlinePaint(Color.white);
			             int style = Font.UNDEFINED ;
			             java.awt.Font font = new java.awt.Font("Garamond", style , 7);
			             StandardLegend legend = new StandardLegend();
			             legend.setPreferredWidth(120);
			             legend.setPadding(new RectangleInsets(10, 60, 5, 10));
			             legend.setAnchor(Legend.EAST);
			             legend.setItemFont(font);
			             PDFPieChart.setLegend(legend);
			             PDFPieChart.setBackgroundPaint(Color.white);
			             PDFPieChart.setBorderPaint(Color.white);
			             plot.setLabelFont(font);
			             PdfContentByte Add_Chart_Content= writer.getDirectContent();
			             PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,220);
			             Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,220,new DefaultFontMapper());                
			             Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,220);
			             PDFPieChart.draw(Graphics_Chart,Chart_Region);            
			             Graphics_Chart.dispose();
			             Image chartImage = Image.getInstance(template_Chart_Holder);
			             document.add(chartImage);
				         
				         LineSeparator separator = new LineSeparator();
				         separator.setPercentage(59500f / 523f);
				         Chunk linebreak = new Chunk(separator);
				       //  document.add(linebreak);
			         }
			     	}
			     	
			     	//other questions Eval PDF 
			     	 document.newPage();
			    	List<CourseEvalQuestionsDTO> othersTextEvalAnswers=new ArrayList<CourseEvalQuestionsDTO>();
			    	List<CourseEvalQuestionsDTO> othersRateEvalAnswers=new ArrayList<CourseEvalQuestionsDTO>();;
			    	othersTextEvalAnswers=ansFacade.getOthersTextByCourseIDAndInstructorID(courses.get(c).getId(),instructors.get(i).getId());
		    		othersRateEvalAnswers=ansFacade.getOthersRateByCourseIDAndInstructorID(courses.get(c).getId(),instructors.get(i).getId());
			         document.add(new Paragraph("Other Questions"));
			         for(int a=0;a<othersRateEvalAnswers.size();a++){
			    
			         
			        
			         // pie chart 
			         Integer totalNumberSubmitted=0;
			        DefaultPieDataset myPiedataset = new DefaultPieDataset();
	               for (Map.Entry<String, Number> entry : othersRateEvalAnswers.get(a).getModel().getData().entrySet())
	                {
	            	   
	                  //System.out.println(entry.getKey().substring(0,entry.getKey().indexOf(" :")) + "/" + entry.getValue());
	                 
			             myPiedataset.setValue(entry.getKey().substring(0,entry.getKey().indexOf(" :")) ,entry.getValue());
			             totalNumberSubmitted+=(Integer)entry.getValue();
	               }
	               double first =totalNumberSubmitted;
	           		double second=totalNumberOfStudentsInCourse;
	           		double percentage=(first/second)*100;
	           		//double number=24.2378;
	           		 if (2 < 0) throw new IllegalArgumentException();

	           		    long factor = (long) Math.pow(10, 2);
	           		    percentage = percentage * factor;
	           		    long tmp = Math.round(percentage);
	                 //  document.add(new Paragraph(a+1+" - "+facultyEvalAnswers.get(a).getText()+"( "+courseEvalSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));
	           		 document.add(new Paragraph(a+1+" - "+othersRateEvalAnswers.get(a).getText()+"( "+totalNumberSubmitted+" of "+totalNumberOfStudentsInCourse+" - "+ (double) tmp / factor +" % )"));

		             
		             JFreeChart PDFPieChart=ChartFactory.createPieChart("",myPiedataset,false,true,false);
		             document.addTitle("How to Add a Pie Chart to a PDF file using iText");
		             document.addAuthor("Learning Technologies");                
		             document.addKeywords("iText,PieChart,JFreeChart,PDF,Example Tutorial");
		             final PiePlot plot = (PiePlot) PDFPieChart.getPlot();
		             plot.setBackgroundPaint(Color.white);
		             plot.setCircular(true);
		           
		             plot.setLabelGenerator(new StandardPieItemLabelGenerator(
		                 "{0},{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
		             ));
		             plot.setNoDataMessage("No data available");
		             plot.setSectionPaint(1, new Color(217,179, 39));
		             plot.setSectionPaint(2,new Color(69, 140, 65));
		             plot.setSectionPaint(3, new Color(207, 38, 39));
		             plot.setSectionPaint(4,new Color(92, 51, 97));
		             plot.setSectionPaint(5, new Color(94, 48, 50));
		             plot.setShadowYOffset(0);
		             plot.setShadowXOffset(0);
		             plot.setBaseSectionOutlinePaint(Color.white);
		             int style = Font.UNDEFINED ;
		             java.awt.Font font = new java.awt.Font("Garamond", style , 7);
		             StandardLegend legend = new StandardLegend();
		             legend.setPreferredWidth(120);
		             legend.setPadding(new RectangleInsets(10, 60, 5, 10));
		             legend.setAnchor(Legend.EAST);
		             legend.setItemFont(font);
		             PDFPieChart.setLegend(legend);
		             PDFPieChart.setBackgroundPaint(Color.white);
		             PDFPieChart.setBorderPaint(Color.white);
		             plot.setLabelFont(font);
		             PdfContentByte Add_Chart_Content= writer.getDirectContent();
		             PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,210);
		             Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,210,new DefaultFontMapper());                
		             Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,210);
		             PDFPieChart.draw(Graphics_Chart,Chart_Region);            
		             Graphics_Chart.dispose();
		             Image chartImage = Image.getInstance(template_Chart_Holder);
		             document.add(chartImage);
			         
			         LineSeparator separator = new LineSeparator();
			         separator.setPercentage(59500f / 523f);
			         Chunk linebreak = new Chunk(separator);
			         //document.add(linebreak);
			        
			         
			         }
			         
			         
			         
			         
			         
			         for(int k=0;k<othersTextEvalAnswers.size();k++)
			         {
			        	
			        	 document.add(new Paragraph(othersTextEvalAnswers.get(k).getText()));
			        	 for(int q=0;q<othersTextEvalAnswers.get(k).getComments().size();q++)
			        	 {
			        	// document.add(new Paragraph(q+1+" - "+othersTextEvalAnswers.get(k).getComments().get(q)));
			        	 PdfPTable table=new PdfPTable(1);
		                 table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
		                 
		                 PdfPCell cell;
		                 String temp=othersTextEvalAnswers.get(k).getComments().get(q);
		                 System.out.println(temp);
		                 cell=new PdfPCell(new Phrase(q+1+" - "+temp,arabic));
		                 cell.disableBorderSide(1);
		                //cell.disableBorderSide(2);
		                 cell.disableBorderSide(5);
		                 cell.disableBorderSide(8);
		                 cell.setPaddingBottom(20);
		                 cell.setPaddingTop(20);
		                 cell.setBorderColor(new BaseColor(224,222, 222));
		                 table.addCell(cell);
		                 
		                 document.add(table);
			        	 }
			         }
			         
			     	
			     	
			         document.close();
			         writer.close();
			      } catch (DocumentException e)
			      {
			         e.printStackTrace();
			      } catch (FileNotFoundException e)
			      {
			         e.printStackTrace();
			      }
		}
		
		
		}
			
	}

	public void generateExcelSheet()
	{
		// get all courses
		List<CoursesDTO>courses=coursefacade.getAllCoursesBySemesterAndYear(2,2015);
	   
	  
		for(int c=0;c<courses.size();c++)
		{
			  List<Object[]>arrayLst=new ArrayList<Object[]>();
				arrayLst.add(new Object[]{"Student ID", "Student Name", "Question", "Selection","Comment"});
				
			int rowCount = 0;
			
			// create File for each course
			 //Blank workbook
	        XSSFWorkbook workbook = new XSSFWorkbook();
	         
	        //Create a blank sheet
	        XSSFSheet sheet = workbook.createSheet(courses.get(c).getName().replaceAll("/","-"));
	        //List<CourseEvalAnswersDTO> courseAnswers=ansFacade.getByCourseID(courses.get(c).getId());
	        for (Object[] aBook : arrayLst) {
 				Row row = sheet.createRow(rowCount);
 				
 				int columnCount = 0;
 				
 				for (Object field : aBook) {
 					Cell cell = row.createCell(columnCount);
 					if (field instanceof String) {
 						cell.setCellValue((String) field);
 					} else if (field instanceof Integer) {
 						cell.setCellValue((Integer) field);
 					}
 					columnCount++;
 				}
 				rowCount++;
 			}
	        //int rownum = 0;
	    
	     // get instructors
	     			List<BaseDTO> instructors=facade.getInstructorsOfCourse(courses.get(c).getId());
	     			
	     			
	     				List<StudentDTO> studentsOfCourse=ansFacade.getTotalStudentsOfCourse(courses.get(c).getId(),1);
	     				for(int s=0;s<studentsOfCourse.size();s++)
	     				{
	     					for(int i=0;i<instructors.size();i++)
	    	     			{
	     						
	     		     	       
	     						arrayLst=new ArrayList<Object[]>();
	     					List<CourseEvalAnswersDTO> facAnswers=	ansFacade.getByStudentIDAndCourseIDAndInstructor(studentsOfCourse.get(s).getId(), 
	     							courses.get(c).getId(), instructors.get(i).getId());
	     					
	     					if(facAnswers.size()>0)
	     					{
	     						CellStyle style = workbook.createCellStyle();
	     					    style.setFillBackgroundColor(IndexedColors.TEAL.getIndex());
	     					    style.setFillPattern(CellStyle.ALIGN_FILL);
	     					   org.apache.poi.ss.usermodel.Font font = workbook.createFont();
	     				        font.setColor(IndexedColors.WHITE.getIndex());
	     				        style.setFont(font);
	     				        
	     						 Row rows = sheet.createRow(rowCount);
	     		     			 rows.setHeight((short) 700);
	     		     			 Cell cells = rows.createCell((short) 0);
	     		     	         
	     		     	         cells.setCellValue(instructors.get(i).getName());
	     		     	       cells.setCellStyle(style);
	     		     	         //We want the Cell Data to be distributed across B2 to D5 range
	     		     	         // We use static method valueOf in CellRangeAddress, to specify range
	     		     	         sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowCount+1)+":G"+(rowCount+1)));
	     		     	       rowCount++;
	     					}
	     					
	     					
	     					  for(int a=0;a<facAnswers.size();a++)
	     					  {
	     						  
	     						 String selection="";
	     						if(facAnswers.get(a).getSelections()==1)
	     						{
	     							selection="Strongly Disagree";
	     						}
	     						else if(facAnswers.get(a).getSelections()==2)
	     						{
	     							selection="Disagree";
	     						}
	     						else if(facAnswers.get(a).getSelections()==3)
		     						{
	     							selection="Neutral";
		     						}
	     						else if(facAnswers.get(a).getSelections()==4)
	     						{
     							selection="Agree";
	     						}
	     						else if(facAnswers.get(a).getSelections()==5)
	     						{
     							selection="Strongly Agree";
	     						}
	     						else if(facAnswers.get(a).getSelections()==6)
	     						{
     							selection="N/A";
	     						}
	     						else if(facAnswers.get(a).getSelections()==7)
	     						{
     							selection="Yes";
	     						}
	     						else if(facAnswers.get(a).getSelections()==8)
	     						{
     							selection="No";
	     						}
	     						else if(facAnswers.get(a).getSelections()==9)
	     						{
     							selection="Much Less";
	     						}
	     						else if(facAnswers.get(a).getSelections()==10)
	     						{
     							selection="Less";
	     						}
	     						else if(facAnswers.get(a).getSelections()==11)
	     						{
     							selection="Same";
	     						}
	     						else if(facAnswers.get(a).getSelections()==12)
	     						{
     							selection="Greater";
	     						}
	     						else if(facAnswers.get(a).getSelections()==13)
	     						{
     							selection="Much Greater";
	     						}
	     						
	     						
	     						
	     						
	     						
	     						
	     						
	     						
	     						 
	     						 arrayLst.add(new Object[]{studentsOfCourse.get(s).getFacultyId(),studentsOfCourse.get(s).getName(),
	     								facAnswers.get(a).getQuestion().getText(), 
	     								 selection});
	     					  }
	     		     			
	     		     			for (Object[] aBook : arrayLst) {
	     		     				Row row = sheet.createRow(rowCount);
	     		     				
	     		     				int columnCount = 0;
	     		     				
	     		     				for (Object field : aBook) {
	     		     					Cell cell = row.createCell(columnCount);
	     		     					if (field instanceof String) {
	     		     						cell.setCellValue((String) field);
	     		     					} else if (field instanceof Integer) {
	     		     						cell.setCellValue((Integer) field);
	     		     					}
	     		     					columnCount++;
	     		     				}
	     		     				rowCount++;
	     		     			}
	     		     			if(facAnswers.size()>0){
	     		     			 Row row = sheet.createRow(rowCount);
	     		     			 row.setHeight((short) 700);
	     		     			 Cell cell = row.createCell((short) 0);
	     		     	         
	     		     	         cell.setCellValue("Comment : "+facAnswers.get(0).getComment());
	     		     	        
	     		     	         //We want the Cell Data to be distributed across B2 to D5 range
	     		     	         // We use static method valueOf in CellRangeAddress, to specify range
	     		     	         sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowCount+1)+":G"+(rowCount+1)));
	     		     	       rowCount++;
	     		     			}
	     						/* data.put(data.size()+1+"", new Object[] {studentsOfCourse.get(s).getFacultyId(),studentsOfCourse.get(s).getName(),
	     								facAnswers.get(a).getQuestion().getId() ,facAnswers.get(a).getQuestion().getText(),facAnswers.get(a).getInstructor().getId(), 
	     								facAnswers.get(a).getSelections(),facAnswers.get(a).getComment()});*/
	     						 
	     					  
	     					 
	     				}
	     					CellStyle style = workbook.createCellStyle();
     					    style.setFillBackgroundColor(IndexedColors.TEAL.getIndex());
     					    style.setFillPattern(CellStyle.ALIGN_FILL);
     					   org.apache.poi.ss.usermodel.Font font = workbook.createFont();
     				        font.setColor(IndexedColors.WHITE.getIndex());
     				        style.setFont(font);
     				        
     						 Row rows = sheet.createRow(rowCount);
     		     			 rows.setHeight((short) 700);
     		     			 Cell cells = rows.createCell((short) 0);
     		     	         
     		     	         cells.setCellValue("Course Evaluation");
     		     	       cells.setCellStyle(style);
     		     	         //We want the Cell Data to be distributed across B2 to D5 range
     		     	         // We use static method valueOf in CellRangeAddress, to specify range
     		     	         sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowCount+1)+":G"+(rowCount+1)));
     		     	       rowCount++;
     		     	       
	     				 List<CourseEvalAnswersDTO> answers=	ansFacade.getByStudentIDAndCourseID(studentsOfCourse.get(s).getId(), 
	    							courses.get(c).getId(),1);
	     				arrayLst=new ArrayList<Object[]>();
	 					 for(int a=0;a<answers.size();a++)
	 					  {
	 						 String selection="";
	     						if(answers.get(a).getSelections()==1)
	     						{
	     							selection="Strongly Disagree";
	     						}
	     						else if(answers.get(a).getSelections()==2)
	     						{
	     							selection="Disagree";
	     						}
	     						else if(answers.get(a).getSelections()==3)
		     						{
	     							selection="Neutral";
		     						}
	     						else if(answers.get(a).getSelections()==4)
	     						{
  							selection="Agree";
	     						}
	     						else if(answers.get(a).getSelections()==5)
	     						{
  							selection="Strongly Agree";
	     						}
	     						else if(answers.get(a).getSelections()==6)
	     						{
  							selection="N/A";
	     						}
	     						else if(answers.get(a).getSelections()==7)
	     						{
  							selection="Yes";
	     						}
	     						else if(answers.get(a).getSelections()==8)
	     						{
  							selection="No";
	     						}
	     						else if(answers.get(a).getSelections()==9)
	     						{
  							selection="Much Less";
	     						}
	     						else if(answers.get(a).getSelections()==10)
	     						{
  							selection="Less";
	     						}
	     						else if(answers.get(a).getSelections()==11)
	     						{
  							selection="Same";
	     						}
	     						else if(answers.get(a).getSelections()==12)
	     						{
  							selection="Greater";
	     						}
	     						else if(answers.get(a).getSelections()==13)
	     						{
  							selection="Much Greater";
	     						}
	     						
	     						
	     						
	     						
	     						
	     						
	 						  if(answers.get(a).getInstructor()==null)
	 							  arrayLst.add( new Object[] {studentsOfCourse.get(s).getFacultyId(),studentsOfCourse.get(s).getName(),
	 								answers.get(a).getQuestion().getText(),null, 
	 								selection,answers.get(a).getComment()});
	 						 
	 					  }	
	 					for (Object[] aBook : arrayLst) {
 		     				Row row = sheet.createRow(rowCount);
 		     				
 		     				int columnCount = 0;
 		     				
 		     				for (Object field : aBook) {
 		     					Cell cell = row.createCell(columnCount);
 		     					if (field instanceof String) {
 		     						cell.setCellValue((String) field);
 		     					} else if (field instanceof Integer) {
 		     						cell.setCellValue((Integer) field);
 		     					}
 		     					columnCount++;
 		     				}
 		     				rowCount++;	
 		     			}
	 					 
	     			}
	     
	     			
	     					/* List<CourseEvalAnswersDTO> answers=	ansFacade.getByStudentIDAndCourseID(studentsOfCourse.get(s).getId(), 
		     							courses.get(c).getId());
	     					 for(int a=0;a<answers.size();a++)
	     					  {
	     						  if(answers.get(a).getInstructor()==null)
	     						 data.put(data.size()+1+"", new Object[] {studentsOfCourse.get(s).getFacultyId(),studentsOfCourse.get(s).getName(),
	     								answers.get(a).getQuestion().getId() ,answers.get(a).getQuestion().getText(),null, 
	     								answers.get(a).getSelections(),answers.get(a).getComment()});
	     						 
	     					  }*/
	     					 
	     		
	     			
	     				/*XSSFCell cell = (XSSFCell) row.createCell((short) 1);
	     			       cell.setCellValue(instructors.get(i).getName());
	     			      //MEARGING CELLS 
	     			      //this statement for merging cells
	     			      sheet.addMergedRegion(new CellRangeAddress(
	     			    		 rownum, //first row (0-based)
	     			    		rownum, //last row (0-based)
	     			      1, //first column (0-based)
	     			      5 //last column (0-based)
	     			      ));*/
	 
	        
	     			try
	    	        {
	     				File dir = new File("/home/omnya/Desktop/CoursesExcel/"+courses.get(c).getName().replaceAll("/","-"));
						 dir.mkdirs();
						 File file = new File(dir, courses.get(c).getName().replaceAll("/","-")+".xlsx");
						 
	    	            FileOutputStream out = new FileOutputStream(file);
	    	            workbook.write(out);
	    	            out.close();
	    	            System.out.println(courses.get(c).getName().replaceAll("/","-")+".xlsx"+"written successfully on disk.");
	    	        }
	    	        catch (Exception e)
	    	        {
	    	            e.printStackTrace();
	    	        } 
			
		}
	}

	public void generateNewVersionExcelSheet(){
		List<CoursesDTO>courses=coursefacade.getAllCoursesBySemesterAndYear(2,2015);
		for(int c=0;c<courses.size();c++)
		{
			  List<Object[]>arrayLst=new ArrayList<Object[]>();
				arrayLst.add(new Object[]{"Student ID", "Student Name", "Question", "Selection","Comment"});
			
				
			// get instrutors 
			// get questions 
			// get results by question id , course id , instructor id 
		}
	}
	 
	private int insertHeaderInfo(Sheet sheet, int index){
		 
		  int rowIndex = index;
		  Row row = null;
		  Cell c = null;
		 
		 // get All questions 
		  row = sheet.createRow(rowIndex);
		  c = row.createCell(0);
		  c.setCellValue("Line No");
		  c.setCellStyle(csBold);
		  c = row.createCell(1);
		  c.setCellValue("Quantity");
		  c.setCellStyle(csBold);
		  c = row.createCell(2);
		  c.setCellValue("Item No");
		  c.setCellStyle(csBold);
		  c = row.createCell(3);
		  c.setCellValue("Description");
		  c.setCellStyle(csBold);
		  c = row.createCell(4);
		  c.setCellValue("Price");
		  c.setCellStyle(csBold);
		  c = row.createCell(5);
		  c.setCellValue("sayed");
		  c.setCellStyle(csBold);
		  return rowIndex;
		 
		 }
	
	public IGeneratePDFFacade getFacade() {
		return facade;
	}

	public void setFacade(IGeneratePDFFacade facade) {
		this.facade = facade;
	}

	public ICourseEvalAnswersFacade getAnsFacade() {
		return ansFacade;
	}

	public void setAnsFacade(ICourseEvalAnswersFacade ansFacade) {
		this.ansFacade = ansFacade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IStudentAcademicPetFacade getCoursefacade() {
		return coursefacade;
	}

	public void setCoursefacade(IStudentAcademicPetFacade coursefacade) {
		this.coursefacade = coursefacade;
	}

	public List<Integer> getNotLabCourses() {
		return notLabCourses;
	}

	public void setNotLabCourses(List<Integer> notLabCourses) {
		this.notLabCourses = notLabCourses;
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

	

	
	
}
