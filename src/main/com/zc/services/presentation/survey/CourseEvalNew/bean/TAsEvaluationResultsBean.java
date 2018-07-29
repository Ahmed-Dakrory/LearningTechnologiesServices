/**
 * 
 */
package main.com.zc.services.presentation.survey.CourseEvalNew.bean;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.presentation.configuration.facade.ICourseInstructorFacade;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.InstructorsEvalAnswersDTO;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.IInstructorsEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.IGeneratePDFFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.Legend;
import org.jfree.chart.StandardLegend;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;
import org.primefaces.model.chart.PieChartModel;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * @author Omnya Alaa
 *
 */
@ManagedBean(name="TAsEvaluationResultsBean",eager=false)
@ViewScoped
public class TAsEvaluationResultsBean {

	@ManagedProperty("#{ICourseInstructorFacade}")
	private ICourseInstructorFacade insFacde;
	
	@ManagedProperty("#{ICourseEvalQuestionsFacade}")
	private ICourseEvalQuestionsFacade questionFacade;
	
	@ManagedProperty("#{IInstructorsEvalAnswersFacade}")
	private IInstructorsEvalAnswersFacade answersFacade;
	@ManagedProperty("#{IGeneratePDFFacade}")
	private IGeneratePDFFacade facade;
	@PostConstruct
	public void init()
	{
		generatePDFInss();
		generatePDFTAs();
	}
	public void generatePDFInss(){
			//get courses 
		List<BaseDTO>courses=facade.getCoursesByYearAndSem(2015,0);
		for(int c=0;c<courses.size();c++)
		{
			
		
		// get TAs
			List<InstructorDTO> tas=insFacde.getAllTAsByCourseId(courses.get(c).getId());
			//Integer totalNumberOfStudentsInCourse=ansFacade.getStudentsNumberOfCourse(courses.get(c).getId());
			/////Create PDF/////////
				try{
					for(int i=0;i<tas.size();i++)
					{//
				  Document document = new Document();
					 Font blue = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, new CMYKColor(255, 0, 0, 0));
					 //TODO for windows
					 //File dir = new File("C:/Users/Omnya Alaa/Desktop/instructors/"+courses.get(c).getName().replaceAll("/","_"));
					 //TODO for linux 
					 File dir = new File("/home/omnya/Desktop/instructors/"+courses.get(c).getName().replaceAll("/","_"));
					 dir.mkdirs();
					 File file = new File(dir, courses.get(c).getName().replaceAll("/","_")+tas.get(i).getName()+".pdf");
					 //File file = new File(dir, tas.get(i).getName().replaceAll("/","_")+".pdf");
					 FileOutputStream out = new FileOutputStream(file);
			         PdfWriter writer = PdfWriter.getInstance(document, out);
			         document.open();
			       
			
			         
				// get answers by course and instructor
				List<CourseEvalQuestionsDTO> questions=new ArrayList<CourseEvalQuestionsDTO>();
				questions = questionFacade.getBySectionID(QuestionsCategory.Ins_Eval_TA.getID());
			
				for(int j=0;j<questions.size();j++)
				{
					  document.add(new Paragraph(j+1+" - "+questions.get(j).getText()));
					  
				
							
			         List<InstructorsEvalAnswersDTO> answers=new ArrayList<InstructorsEvalAnswersDTO>();
			         answers=answersFacade.getAnswersByTOAndQues( tas.get(i).getId(),questions.get(j).getId()
			        		);
						
			         PieChartModel model=new PieChartModel();
			            int stronglyDisAgreeCount=0;
						int disAgreeCount=0;
						int netrualCount=0;
						int agreeCount=0;
						int stronglyAgreeCount=0;
						int nACount=0;
			         for(int a=0;a<answers.size();a++)
			         {
			        	 if(answers.get(a).getSelection()==224)
			 				{
			        		 nACount++;
			 				}
			 				
			         else if(answers.get(a).getSelection()==225)
			 				{
			 					stronglyDisAgreeCount++;
			 				}
			 				else if(answers.get(a).getSelection()==226)
			 				{
			 					disAgreeCount++;
			 				}
			 				else if(answers.get(a).getSelection()==227)
			 				{
			 					netrualCount++;
			 				}
			 				else if(answers.get(a).getSelection()==228)
			 				{
			 					agreeCount++;
			 				}
			 				else if(answers.get(a).getSelection()==229)
			 				{
			 					stronglyAgreeCount++;
			 				}
			 				
			 				
			         
			 			model.set("Strongly Disagree : "+stronglyDisAgreeCount, stronglyDisAgreeCount);
			 			model.set("Disagree : "+disAgreeCount, disAgreeCount);
			 			model.set("Neutral : "+netrualCount, netrualCount);
			 			model.set("Agree : "+agreeCount, agreeCount);
			 			model.set("Strongly Agree : "+stronglyAgreeCount, stronglyAgreeCount);
			 			model.set("N/A : "+nACount, nACount);
			 		//	questions.get(i).setModel(model);
			         }
			         
			         
			    
			         //document.add(new Paragraph(a+1+" - "+facultyEvalAnswers.get(a).getText()+" of "+totalNumberOfStudentsInCourse));
			         // pie chart 
                      Integer totalSubmitted=0;
			        DefaultPieDataset myPiedataset = new DefaultPieDataset();
                   for (Map.Entry<String, Number> entry :model.getData().entrySet())
                    {
                	   
                      //System.out.println(entry.getKey().substring(0,entry.getKey().indexOf(" :")) + "/" + entry.getValue());
                     
 		             myPiedataset.setValue(entry.getKey().substring(0,entry.getKey().indexOf(" :")) ,entry.getValue());
 		            totalSubmitted+=(Integer)entry.getValue();
 		            
                   }
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
		             java.awt.Font font = new java.awt.Font("Garamond", style , 5);
		             StandardLegend legend = new StandardLegend();
		             legend.setPreferredWidth(120);
		             legend.setPadding(new RectangleInsets(4, 60,4, 10));
		             legend.setAnchor(Legend.EAST);
		             legend.setItemFont(font);
		             PDFPieChart.setLegend(legend);
		             PDFPieChart.setBackgroundPaint(Color.white);
		             PDFPieChart.setBorderPaint(Color.white);
		             plot.setLabelFont(font);
		             PdfContentByte Add_Chart_Content= writer.getDirectContent();
		             PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,200);
		             Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,200,new DefaultFontMapper());                
		             Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,160);
		             PDFPieChart.draw(Graphics_Chart,Chart_Region);            
		             Graphics_Chart.dispose();
		             Image chartImage = Image.getInstance(template_Chart_Holder);
		             document.add(chartImage);
			         
			         LineSeparator separator = new LineSeparator();
			         separator.setPercentage(59500f / 523f);
			         Chunk linebreak = new Chunk(separator);
			         document.add(linebreak);
			        	 
			         
			         }
			        
			     	    
				  
			       
			      
				
				  document.close();
			         writer.close();	
		}} catch (DocumentException e)
	    {
		       e.printStackTrace();
		    } catch (FileNotFoundException e)
		    {
		       e.printStackTrace();
		    }}}
	public void generatePDFTAs(){
		//get courses 
	List<BaseDTO>courses=facade.getCoursesByYearAndSem(2015,0);
	for(int c=0;c<courses.size();c++)
	{
		
	
	// get TAs
		List<InstructorDTO> tas=insFacde.getAllTAsByCourseId(courses.get(c).getId());
		//Integer totalNumberOfStudentsInCourse=ansFacade.getStudentsNumberOfCourse(courses.get(c).getId());
		/////Create PDF/////////
			try{
				for(int i=0;i<tas.size();i++)
				{//
			  Document document = new Document();
				 Font blue = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, new CMYKColor(255, 0, 0, 0));
				 //TODO for windows 
				 //File dir = new File("C:/Users/Omnya Alaa/Desktop/TAs/"+courses.get(c).getName().replaceAll("/","_"));
				 //TODO for linux 
				 File dir = new File("/home/omnya/Desktop/TAs/"+courses.get(c).getName().replaceAll("/","_"));
				 dir.mkdirs();
				 File file = new File(dir, courses.get(c).getName().replaceAll("/","_")+tas.get(i).getName()+".pdf");
				 //File file = new File(dir, tas.get(i).getName().replaceAll("/","_")+".pdf");
				 FileOutputStream out = new FileOutputStream(file);
		         PdfWriter writer = PdfWriter.getInstance(document, out);
		         document.open();
		       
		
		         
			// get answers by course and instructor
			List<CourseEvalQuestionsDTO> questions=new ArrayList<CourseEvalQuestionsDTO>();
			questions = questionFacade.getBySectionID(QuestionsCategory.TA_Eval_TA.getID());
		
			for(int j=0;j<questions.size();j++)
			{
				  document.add(new Paragraph(j+1+" - "+questions.get(j).getText()));
				  
			
						
		         List<InstructorsEvalAnswersDTO> answers=new ArrayList<InstructorsEvalAnswersDTO>();
		         answers=answersFacade.getAnswersByTOAndQues(tas.get(i).getId(),questions.get(j).getId()
		        		 );
					
		         PieChartModel model=new PieChartModel();
		            int stronglyDisAgreeCount=0;
					int disAgreeCount=0;
					int netrualCount=0;
					int agreeCount=0;
					int stronglyAgreeCount=0;
					int nACount=0;
		         for(int a=0;a<answers.size();a++)
		         {
		        	 if(answers.get(a).getSelection()==224)
		 				{
		        		 nACount++;
		 				}
		 				
		         else if(answers.get(a).getSelection()==225)
		 				{
		 					stronglyDisAgreeCount++;
		 				}
		 				else if(answers.get(a).getSelection()==226)
		 				{
		 					disAgreeCount++;
		 				}
		 				else if(answers.get(a).getSelection()==227)
		 				{
		 					netrualCount++;
		 				}
		 				else if(answers.get(a).getSelection()==228)
		 				{
		 					agreeCount++;
		 				}
		 				else if(answers.get(a).getSelection()==229)
		 				{
		 					stronglyAgreeCount++;
		 				}
		 				
		 				
		         
		 			model.set("Strongly Disagree : "+stronglyDisAgreeCount, stronglyDisAgreeCount);
		 			model.set("Disagree : "+disAgreeCount, disAgreeCount);
		 			model.set("Neutral : "+netrualCount, netrualCount);
		 			model.set("Agree : "+agreeCount, agreeCount);
		 			model.set("Strongly Agree : "+stronglyAgreeCount, stronglyAgreeCount);
		 			model.set("N/A : "+nACount, nACount);
		 		//	questions.get(i).setModel(model);
		         }
		         
		         
		    
		         //document.add(new Paragraph(a+1+" - "+facultyEvalAnswers.get(a).getText()+" of "+totalNumberOfStudentsInCourse));
		         // pie chart 
                  Integer totalSubmitted=0;
		        DefaultPieDataset myPiedataset = new DefaultPieDataset();
               for (Map.Entry<String, Number> entry :model.getData().entrySet())
                {
            	   
                  //System.out.println(entry.getKey().substring(0,entry.getKey().indexOf(" :")) + "/" + entry.getValue());
                 
		             myPiedataset.setValue(entry.getKey().substring(0,entry.getKey().indexOf(" :")) ,entry.getValue());
		            totalSubmitted+=(Integer)entry.getValue();
		            
               }
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
	             java.awt.Font font = new java.awt.Font("Garamond", style , 5);
	             StandardLegend legend = new StandardLegend();
	             legend.setPreferredWidth(120);
	             legend.setPadding(new RectangleInsets(4, 60,4, 10));
	             legend.setAnchor(Legend.EAST);
	             legend.setItemFont(font);
	             PDFPieChart.setLegend(legend);
	             PDFPieChart.setBackgroundPaint(Color.white);
	             PDFPieChart.setBorderPaint(Color.white);
	             plot.setLabelFont(font);
	             PdfContentByte Add_Chart_Content= writer.getDirectContent();
	             PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,200);
	             Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,200,new DefaultFontMapper());                
	             Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,160);
	             PDFPieChart.draw(Graphics_Chart,Chart_Region);            
	             Graphics_Chart.dispose();
	             Image chartImage = Image.getInstance(template_Chart_Holder);
	             document.add(chartImage);
		         
		         LineSeparator separator = new LineSeparator();
		         separator.setPercentage(59500f / 523f);
		         Chunk linebreak = new Chunk(separator);
		         document.add(linebreak);
		        	 
		         
		         }
		        
		     	    
			  
		       
		      
			
			  document.close();
		         writer.close();	
	}} catch (DocumentException e)
    {
	       e.printStackTrace();
	    } catch (FileNotFoundException e)
	    {
	       e.printStackTrace();
	    }}}
	public ICourseInstructorFacade getInsFacde() {
		return insFacde;
	}
	public void setInsFacde(ICourseInstructorFacade insFacde) {
		this.insFacde = insFacde;
	}
	public ICourseEvalQuestionsFacade getQuestionFacade() {
		return questionFacade;
	}
	public void setQuestionFacade(ICourseEvalQuestionsFacade questionFacade) {
		this.questionFacade = questionFacade;
	}
	public IInstructorsEvalAnswersFacade getAnswersFacade() {
		return answersFacade;
	}
	public void setAnswersFacade(IInstructorsEvalAnswersFacade answersFacade) {
		this.answersFacade = answersFacade;
	}
	public IGeneratePDFFacade getFacade() {
		return facade;
	}
	public void setFacade(IGeneratePDFFacade facade) {
		this.facade = facade;
	}
	
}
