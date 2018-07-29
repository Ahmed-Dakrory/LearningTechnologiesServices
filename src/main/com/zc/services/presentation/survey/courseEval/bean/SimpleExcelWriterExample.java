package main.com.zc.services.presentation.survey.courseEval.bean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * A very simple program that writes some data to an Excel file
 * using the Apache POI library.
 * @author www.codejava.net
 *
 */
public class SimpleExcelWriterExample {

	public static void main(String[] args) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Java Books");
		
		Object[][] bookData =new Object[4][];
		bookData[0]=new Object[]{"Head First Java", "Kathy Serria", 79};
		bookData[1]=new Object[]{"Head First Java", "Kathy Serria", 79};
		bookData[2]=new Object[]{"Head First Java", "Kathy Serria", 79};
		bookData[3]=new Object[]{"Head First Java", "Kathy Serria", 79};
		
		List<Object[]>arrayLst=new ArrayList<Object[]>();
		arrayLst.add(new Object[]{"Head First Java", "Kathy Serria", 80});
		/*Object[][] bookData = {
				{"Head First Java", "Kathy Serria", 79},
				{"Effective Java", "Joshua Bloch", 36},
				{"Clean Code", "Robert martin", 42},
				{"Thinking in Java", "Bruce Eckel", 35},
		};*/
		
		int rowCount = 0;
		
		for (Object[] aBook : arrayLst) {
			Row row = sheet.createRow(rowCount);
			
			int columnCount = 0;
			
			for (Object field : aBook) {
				Cell cell = row.createCell(columnCount++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}
			rowCount++;
			
		}
		CellStyle style = workbook.createCellStyle();
	    style.setFillBackgroundColor(IndexedColors.TEAL.getIndex());
	    style.setFillPattern(CellStyle.ALIGN_FILL);
	    Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
		 Row row = sheet.createRow(rowCount);
         Cell cell = row.createCell((short) 0);
         cell.setCellValue("Merge Across Rows and Columnsss - Example");
         cell.setCellStyle(style);
         //We want the Cell Data to be distributed across B2 to D5 range
         // We use static method valueOf in CellRangeAddress, to specify range
         sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowCount+1)+":D"+(rowCount+1)));
        
		
		try (FileOutputStream outputStream = new FileOutputStream("/home/omnya/Projects/JavaBooks.xlsx")) {
			workbook.write(outputStream);
		}
	}

}
