/**
 * 
 */
package main.com.zc.services.presentation.survey.midTermEvaluation.bean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Omnya Alaa
 *
 */
public class Test {
	public static  String addLinebreaks(String input, int maxLineLength) {
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
    public static void main(String[]args) throws IOException {
    	String s=addLinebreaks("Tutorials are mostly useless as they are in most cases not related the topics we cover in lectures.I guess it will be better if we solved some questions from the test bank together and discuss the materials. ",100);
    	System.out.println(s);
    	Workbook wb = new XSSFWorkbook();   //or new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        
        Row row0 = sheet.createRow(0);
        Cell cell0 = row0.createCell(2);
        cell0.setCellValue("Laboratory Faculty Effectiveness-Strengths");
        sheet.autoSizeColumn(2);
        Row row = sheet.createRow(2);
        Cell cell = row.createCell(2);
        cell.setCellValue(s);
         //to enable newlines you need set a cell styles with wrap=true
        CellStyle cs = wb.createCellStyle();
         String[] lines = s.split("\r\n|\r|\n");
        System.out.println(lines.length);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 8));
        cs.setWrapText(true);
        cell.setCellStyle(cs);
      //increase row height to accomodate two lines of text
        row.setHeightInPoints((lines.length*sheet.getDefaultRowHeightInPoints()));

        //adjust column width to fit the content
        //s.setAlignment(CellStyle.ALIGN_LEFT);
		
        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Omnya Alaa\\Desktop\\ooxml-newlines.xlsx");
        wb.write(fileOut);
        fileOut.close();
       // ((FileOutputStream) wb).close();
    }
}
