package main.com.zc.services.presentation.survey.courseEval.bean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * A simple program that writes data to an Excel file with some formats
 * for cells.
 * @author www.codejava.net
 *
 */
public class FormattedExcelWriterExample {
	
	public void writeExcel(List<Book> listBook, String excelFilePath) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		
		createHeaderRow(sheet);
		
		int rowCount = 1;
		
		for (Book aBook : listBook) {
			Row row = sheet.createRow(++rowCount);
			writeBook(aBook, row);
		}
		
		try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
			workbook.write(outputStream);
		}		
	}
	
	private void createHeaderRow(Sheet sheet) {
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		//font.setBold(true);
		font.setFontHeightInPoints((short) 16);
		cellStyle.setFont(font);
				
		Row row = sheet.createRow(0);
		Cell cellTitle = row.createCell(1);

		cellTitle.setCellStyle(cellStyle);
		cellTitle.setCellValue("Title");
		
		Cell cellAuthor = row.createCell(2);
		cellAuthor.setCellStyle(cellStyle);
		cellAuthor.setCellValue("Author");
		
		Cell cellPrice = row.createCell(3);
		cellPrice.setCellStyle(cellStyle);
		cellPrice.setCellValue("Price");
	}
	
	private void writeBook(Book aBook, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(aBook.getTitle());

		cell = row.createCell(2);
		cell.setCellValue(aBook.getAuthor());
		
		cell = row.createCell(3);
		cell.setCellValue(aBook.getPrice());
	}
	
	private List<Book> getListBook() {
		Book book1 = new Book("Head First Java", "Kathy Serria", 79);
		Book book2 = new Book("Effective Java", "Joshua Bloch", 36);
		Book book3 = new Book("Clean Code", "Robert Martin", 42);
		Book book4 = new Book("Thinking in Java", "Bruce Eckel", 35);
		
		List<Book> listBook = Arrays.asList(book1, book2, book3, book4);
		
		return listBook;
	}
	
	public static void main(String[] args) throws IOException {
		FormattedExcelWriterExample excelWriter = new FormattedExcelWriterExample();
		List<Book> listBook = excelWriter.getListBook();
		String excelFilePath = "/home/omnya/Projects/FormattedJavaBooks.xls";
		excelWriter.writeExcel(listBook, excelFilePath);
	}

}
