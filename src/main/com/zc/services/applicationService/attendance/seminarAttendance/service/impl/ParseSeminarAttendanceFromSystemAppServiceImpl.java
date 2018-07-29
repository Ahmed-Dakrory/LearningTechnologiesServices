/**
 * 
 */
package main.com.zc.services.applicationService.attendance.seminarAttendance.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import main.com.zc.services.applicationService.attendance.seminarAttendance.service.IParseSeminarAttendanceFromSystemAppService;
import main.com.zc.services.presentation.attendance.petitions.dto.DailyAttPetDto;
import main.com.zc.services.presentation.attendance.seminarAttendance.dto.SeminarAttendanceDTO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
/**
 * @author Omnya Alaa
 *
 */
@Service
public class ParseSeminarAttendanceFromSystemAppServiceImpl implements IParseSeminarAttendanceFromSystemAppService
{
	/*static int fileNo;
	static Date time;
	static Date date;
	static String dateS;*/
	 int fileNo;
	 Date time;
	Date date;
	String dateS;
/*	public static void main(String[] args) {
		List<SeminarAttendanceDAO> dataList = new ArrayList<SeminarAttendanceDAO>();

		InputStream inputStream = null;
		Resource resource = null;
		resource = new ClassPathResource("resources/Seminar ZC2_127&114 11-03-2014.xlsx");
		try {
			inputStream = resource.getInputStream();
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				int count = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					count++;
					
					if (count == 2) { // file No

						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC: {

							String dec = new BigDecimal(cell.getNumericCellValue()).toString();
							fileNo = Integer.parseInt(dec);
							System.out.println(Integer.parseInt(dec) + 1+ "\t\t");
						}

						}
					}

					if (count == 5) {
					
						
						Calendar cal = Calendar.getInstance();
						switch (cell.getCellType()) {
					
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								System.out.print(cell.getDateCellValue() + "D"+ "\t\t");
								date = cell.getDateCellValue();
							
							} else {
								date = cell.getDateCellValue();
								
								System.out.print(cell.getNumericCellValue()+ "N" + "\t\t");

							}
							break;
						case Cell.CELL_TYPE_STRING:
						{
							DateFormat dfs = new SimpleDateFormat("yy hh:mm:ss"); 
						    Date startDate;
						    try {
						        startDate = dfs.parse(cell.getRichStringCellValue().getString());
						       // String newDateString = dfs.format(startDate);
						        System.out.println(startDate);
						    	time = startDate;
						    } catch (ParseException e) {
						        e.printStackTrace();
						        if(cell.getRichStringCellValue().getString().equals("NULL"))
						        {
						        	time=null;
						        }
						    }
						
							
							
						}
						}
					}


					
				

				}

				Calendar inCal = Calendar.getInstance();
				if(time != null)
				{
				inCal.setTime(time);
				}
				
				SeminarAttendanceDAO dao=new SeminarAttendanceDAO();
				dao.setFileNo(fileNo);
				dao.setTime(inCal);
				dataList.add(dao);
			}

			inputStream.close();
			dataList.remove(0);
			dataList.remove(0);
			dataList.remove(0);
			dataList.remove(0);
			
			System.out.println("Size : "+dataList.size());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}*/




	@Override
	public List<SeminarAttendanceDTO> parseSeminarAtt(String filePath) {
		List<SeminarAttendanceDTO> dataList = new ArrayList<SeminarAttendanceDTO>();

		InputStream inputStream = null;
		Resource resource = null;
		resource = new ClassPathResource(filePath);
		try {
			inputStream = resource.getInputStream();
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				int count = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					count++;
					
					if (count == 2) { // file No

						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC: {

							String dec = new BigDecimal(cell.getNumericCellValue()).toString();
							fileNo = Integer.parseInt(dec);
							System.out.println(Integer.parseInt(dec) + 1+ "\t\t");
						}

						}
					}

					if (count == 5) {
					
						
						Calendar cal = Calendar.getInstance();
						switch (cell.getCellType()) {
					
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								System.out.print(cell.getDateCellValue() + "D"+ "\t\t");
								date = cell.getDateCellValue();
							
							} else {
								date = cell.getDateCellValue();
								
								System.out.print(cell.getNumericCellValue()+ "N" + "\t\t");

							}
							break;
						case Cell.CELL_TYPE_STRING:
						{
							DateFormat dfs = new SimpleDateFormat("yy hh:mm:ss"); 
						    Date startDate;
						    try {
						        startDate = dfs.parse(cell.getRichStringCellValue().getString());
						       // String newDateString = dfs.format(startDate);
						        System.out.println(startDate);
						    	time = startDate;
						    } catch (ParseException e) {
						        e.printStackTrace();
						        if(cell.getRichStringCellValue().getString().equals("NULL"))
						        {
						        	time=null;
						        }
						    }
						
							
							
						}
						}
					}


					
				

				}

				Calendar inCal = Calendar.getInstance();
				if(time != null)
				{
				inCal.setTime(time);
				}
				
				SeminarAttendanceDTO dao=new SeminarAttendanceDTO();
				dao.setFileNo(fileNo);
				dao.setTime(inCal);
				dataList.add(dao);
			}

			inputStream.close();
			dataList.remove(0);
			dataList.remove(0);
			dataList.remove(0);
			dataList.remove(0);
			
			System.out.println("Size : "+dataList.size());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return dataList;
	}


	
}
