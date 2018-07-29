/**
 * 
 */
package main.com.zc.services.presentation.attendance.petitions.facade.impl;

import java.io.IOException;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import main.com.zc.services.presentation.attendance.petitions.dto.DailyAttPetDto;
import main.com.zc.services.presentation.attendance.petitions.facade.IParseDailyAttPetFileFacade;

/**
 * @author Omnya Alaa
 * 
 */
@Service
public class ParseDailyAttPetFileFacadeImpl implements IParseDailyAttPetFileFacade {

	static int fileNo;
	static Date date;
	static String dateS;
	static String excuse;
	static List<Calendar> dates;
	static Date[] datesArray;
/*
	public static void main(String[] args) {
		List<DailyAttPetDao> dataList = new ArrayList<DailyAttPetDao>();

		InputStream inputStream = null;
		Resource resource = null;
		resource = new ClassPathResource("resources/Attendence Petition.xlsx");
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

					if (count == 3) {
						dates=new ArrayList<Calendar>();
						
						Calendar cal = Calendar.getInstance();
						switch (cell.getCellType()) {
					
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								System.out.print(cell.getDateCellValue() + "D"+ "\t\t");
								date = cell.getDateCellValue();
								cal.setTime(date);
								dates.add(cal);
							} else {
								date = cell.getDateCellValue();
								cal.setTime(date);
								dates.add(cal);
								System.out.print(cell.getNumericCellValue()+ "N" + "\t\t");

							}
							break;
						case Cell.CELL_TYPE_STRING:
						{
							dateS=cell.getStringCellValue();
							System.out.print(cell.getStringCellValue()+ "S" + "\t\t");
							String str = dateS;
							String delimiter = ",";
							String[] temp;
							temp = str.split(delimiter);
							datesArray=new Date[temp.length];
							for(int i =0; i < temp.length ; i++)
							{
							System.out.println(temp[i]);
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							try {
								 
								Date date = formatter.parse(temp[i]);
								cal.setTime(date);
								//dates.add(i,cal);
								datesArray[i]=date;
							} catch (ParseException e) {
								e.printStackTrace();
							}
							}
							try{
								for(int i=0;i<datesArray.length;i++)
								{
									Calendar calen=Calendar.getInstance();
									calen.setTime(datesArray[i]);
									dates.add(calen);
								}
								//dataList.add(new DailyAttPetDao(fileNo, excuse, dates));
								}
								catch(Exception ex){}
							
							
						}
						}
					}


					if (count == 4) {
						switch (cell.getCellType()) {
						
				
						case Cell.CELL_TYPE_STRING:
						{
							excuse=cell.getStringCellValue();
							System.out.print(cell.getStringCellValue()+ "S" + "\t\t");
						}
						}
					}

		
				

				}

				System.out.println(datesArray.length);
				dataList.add(new DailyAttPetDao(fileNo, excuse, dates));
			}

			inputStream.close();
			dataList.remove(0);
			dataList.remove(dataList.size()-1);
			System.out.println("list size >>>>>> " + dataList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}*/

	@Override
	public List<DailyAttPetDto> parsePetFile(String fileName)
			throws IOException {
		List<DailyAttPetDto> dataList = new ArrayList<DailyAttPetDto>();

		InputStream inputStream = null;
		Resource resource = null;
		resource = new ClassPathResource(fileName);
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

					if (count == 3) {
						dates=new ArrayList<Calendar>();
						
						Calendar cal = Calendar.getInstance();
						switch (cell.getCellType()) {
					
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								System.out.print(cell.getDateCellValue() + "D"+ "\t\t");
								date = cell.getDateCellValue();
								cal.setTime(date);
								dates.add(cal);
							} else {
								date = cell.getDateCellValue();
								cal.setTime(date);
								dates.add(cal);
								System.out.print(cell.getNumericCellValue()+ "N" + "\t\t");

							}
							break;
						case Cell.CELL_TYPE_STRING:
						{
							dateS=cell.getStringCellValue();
							System.out.print(cell.getStringCellValue()+ "S" + "\t\t");
							String str = dateS;
							String delimiter = ",";
							String[] temp;
							temp = str.split(delimiter);
							datesArray=new Date[temp.length];
							for(int i =0; i < temp.length ; i++)
							{
							System.out.println(temp[i]);
							SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
							try {
								 
								Date date = formatter.parse(temp[i]);
								cal.setTime(date);
								//dates.add(i,cal);
								datesArray[i]=date;
							} catch (ParseException e) {
								e.printStackTrace();
							}
							}
							try{
								for(int i=0;i<datesArray.length;i++)
								{
									Calendar calen=Calendar.getInstance();
									calen.setTime(datesArray[i]);
									dates.add(calen);
								}
								//dataList.add(new DailyAttPetDao(fileNo, excuse, dates));
								}
								catch(Exception ex){}
							
							
						}
						}
					}


					if (count == 4) {
						switch (cell.getCellType()) {
						
				
						case Cell.CELL_TYPE_STRING:
						{
							excuse=cell.getStringCellValue();
							System.out.print(cell.getStringCellValue()+ "S" + "\t\t");
						}
						}
					}

		
				

				}

				System.out.println(datesArray.length);
				dataList.add(new DailyAttPetDto(fileNo, excuse, dates));
			}

			inputStream.close();
			/*dataList.remove(0);
			dataList.remove(dataList.size()-1);*/
			System.out.println("list size >>>>>> " + dataList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	return dataList;
	}

}