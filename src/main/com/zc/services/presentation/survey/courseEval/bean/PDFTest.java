/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.bean;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import main.com.zc.shared.presentation.dto.BaseDTO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.Legend;
import org.jfree.chart.StandardLegend;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
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
import com.itextpdf.text.pdf.collection.PdfTargetDictionary;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.languages.ArabicLigaturizer;
import com.itextpdf.text.pdf.languages.LanguageProcessor;
/* We will use DefaultPieDataset to define the data for the Pie Chart */
/**
 * @author omnya
 *
 */
public class PDFTest {

	

	public static void main(String[]args)
	{
		 
		String s="Strongly Agree : 3 Stduent(s)";
		System.out.println(s.substring(0, s.indexOf(" :")));
		  List<BaseDTO> instructors=new ArrayList<BaseDTO>();
		  instructors.add(new BaseDTO(1,"First instructor"));
		  instructors.add(new BaseDTO(2,"Second instructor"));
		  String courseName="Phys";
		  for(int j=0;j<instructors.size();j++)
		  {
		  try
	      {
			  Document document = new Document(PageSize.A4);
			 Font blue = FontFactory.getFont(BaseFont.IDENTITY_H, 14, Font.BOLD, new CMYKColor(255, 0, 0, 0));
			 
			 Font normal=FontFactory.getFont("/arial.ttf",BaseFont.IDENTITY_H,18,Font.BOLD);
			  
			 File dir = new File("/home/omnya/Projects/");
			 dir.mkdirs();
			 File file = new File(dir, courseName+instructors.get(j).getName()+".pdf");
			 FileOutputStream out = new FileOutputStream(file);
	         PdfWriter writer = PdfWriter.getInstance(document,out);
	         
	         document.open();
	         Font fontques=FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
	         Paragraph paragraph = new Paragraph();
	         document.add(new Paragraph(courseName+"- total number : 60 students"));
	         for(int i=0;i<5;i++){
	    
	         document.add(new Paragraph(i+1+"يارب سترك"));
	         // pie chart 
	         
	         DefaultPieDataset myPiedataset = new DefaultPieDataset();
             myPiedataset.setValue("Java", 12.9);
             myPiedataset.setValue("C++", 37.9);
             myPiedataset.setValue("C", 86.5);
             myPiedataset.setValue("VB", 80.5);
             myPiedataset.setValue("Shell ", 19.5);
             
             JFreeChart PDFPieChart=ChartFactory.createPieChart("",myPiedataset,false,true,false);
             document.addTitle("How to Add a Pie Chart to a PDF file using iText");
             document.addAuthor("Learning Technologies");                
             document.addKeywords("iText,PieChart,JFreeChart,PDF,Example Tutorial");
             final PiePlot plot = (PiePlot) PDFPieChart.getPlot();
             plot.setBackgroundPaint(Color.white);
             plot.setCircular(true);
             
             int style = Font.UNDEFINED ;
             java.awt.Font font = new java.awt.Font("Garamond", style , 6);
             StandardLegend legend = new StandardLegend();
             legend.setPreferredWidth(120);
             legend.setPadding(new RectangleInsets(10, 60, 5, 10));
             legend.setAnchor(Legend.EAST);
             legend.setItemFont(font);
             PDFPieChart.setLegend(legend);
             PDFPieChart.setBackgroundPaint(Color.white);
             PDFPieChart.setBorderPaint(Color.white);
             plot.setLabelFont(font);
             plot.setLabelGenerator(new StandardPieItemLabelGenerator(
                 "{0}:{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
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
             //plot.setCircular(false);
             PdfContentByte Add_Chart_Content= writer.getDirectContent();
             PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(500,220);
             Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(500,220,new DefaultFontMapper());                
             Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,500,220);
             PDFPieChart.draw(Graphics_Chart,Chart_Region);            
             Graphics_Chart.dispose();
             Image chartImage = Image.getInstance(template_Chart_Holder);
             chartImage.setTop(50);
             document.add(chartImage);
	         
	         LineSeparator separator = new LineSeparator();
	         separator.setPercentage(59500f / 523f);
	         Chunk linebreak = new Chunk(separator);
	         document.add(linebreak);
	        	 
	         
	         }
	        
	         document.newPage();
		         document.add(new Paragraph( "Comments ", blue));
                 for(int i=0;i<2;i++)
                 {
                	   document.add(new Paragraph(i+1+".Comment"));
                 }

                 
                 PdfPTable table=new PdfPTable(1);
                 table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
                 
                 PdfPCell cell;
                 cell=new PdfPCell(new Phrase("المحاضرات سيئة للغاية و غير مفيدة على الإطلاق",normal));
                
                 cell.disableBorderSide(1);
                 cell.disableBorderSide(2);
                 cell.disableBorderSide(5);
                 cell.disableBorderSide(8);
                 cell.setBorderColorRight(BaseColor.WHITE);
                 table.addCell(cell);
                 document.add(table);
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