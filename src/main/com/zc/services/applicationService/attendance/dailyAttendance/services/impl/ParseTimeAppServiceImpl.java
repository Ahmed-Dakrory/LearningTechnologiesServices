/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services.impl;

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

import main.com.zc.services.applicationService.attendance.dailyAttendance.exceptions.ConventionProblem;
import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IParseTimeAppService;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.time.model.Time;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class ParseTimeAppServiceImpl implements IParseTimeAppService {

	private int fileNo;
	private Date date;
	private Date expectedIn;
	private Date expectedOut;
	private Date firstIn;
	private Date lastOut;

	@Autowired
     IStudentRepository personRep;	
	@Override
	public List<DailyAttDataDTO> parse(InputStream input) throws IOException,
			ConventionProblem {
		List<DailyAttDataDTO> dataList = new ArrayList<DailyAttDataDTO>();
		try {
			
			//InputStream input = new FileInputStream(filse);
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(input);
		        // Get first sheet from the workbook
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

		       // org.apache.poi.ss.usermodel.Row row;
		        //Cell cell;

		        // Iterate through each rows from first sheet
		        Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.iterator();
		
		    while(rowIterator.hasNext()) {
		    	  org.apache.poi.ss.usermodel.Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		    	int count = 0;
		        while(cellIterator.hasNext()) {
		             
		            Cell cell = cellIterator.next();
		             
					
					count++;
					// Check the cell type and format accordingly

					if (count == 1) {
						/*
						 * // fileNo=cell.toString(); int value = new
						 * Double(cell.getNumericCellValue()).intValue();
						 * //fileNo=Double.toString(cell.getNumericCellValue());
						 * System.out.print(value+ "\t");
						 */
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC: {// System.out.print(cell.getNumericCellValue()
														// + "\t\t");
							String dec = new BigDecimal(
									cell.getNumericCellValue()).toString();
							fileNo = Integer.parseInt(dec);
							// System.out.print(Integer.parseInt(dec)+1 +
							// "\t\t");
						}
						}
					}

					if (count == 2 || count == 3)

					{
						// Not Needed E_Name, A_Name
						// System.out.print("Not Needed\t");
					}

					if (count == 4) {
						switch (cell.getCellType()) {
						/*
						 * case Cell.CELL_TYPE_NUMERIC: if
						 * (DateUtil.isCellDateFormatted(cell)) { {
						 * //System.out.println(cell.getDateCellValue());
						 * date=cell.getDateCellValue(); } } break;
						 */
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								/*
								 * System.out.print(cell.getDateCellValue() +
								 * "D" + "\t\t");
								 */
								date = cell.getDateCellValue();
							} else {
								date = cell.getDateCellValue();
								/*
								 * System.out.print(cell.getNumericCellValue() +
								 * "N" + "\t\t");
								 */

							}
							break;
						case Cell.CELL_TYPE_STRING:
							/*
							 * System.out.print(cell.getRichStringCellValue()
							 * .getString() + "T" + "\t\t\t\t\t");
							 */
							date = null;
							break;
						}
					}

					if (count == 5) {
						switch (cell.getCellType()) {
						/*
						 * case Cell.CELL_TYPE_NUMERIC: if
						 * (DateUtil.isCellDateFormatted(cell)) {
						 * {//System.out.println(cell.getDateCellValue());
						 * expectedIn=cell.getDateCellValue(); } } break;
						 */
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								/*
								 * System.out.print(cell.getDateCellValue() +
								 * "D" + "\t\t");
								 */
								expectedIn = cell.getDateCellValue();
							} else {
								/*
								 * System.out.print(cell.getNumericCellValue() +
								 * "N" + "\t\t");
								 */
								expectedIn = cell.getDateCellValue();
							}
							break;
						case Cell.CELL_TYPE_STRING:
							/*
							 * System.out.print(cell.getRichStringCellValue()
							 * .getString() + "T" + "\t\t\t\t\t");
							 */
							expectedIn = null;
							break;
						}
					}

					if (count == 6) {
						switch (cell.getCellType()) {
						/*
						 * case Cell.CELL_TYPE_NUMERIC: if
						 * (DateUtil.isCellDateFormatted(cell)) {
						 * {//System.out.println(cell.getDateCellValue());
						 * expectedOut=cell.getDateCellValue(); } }break;
						 */
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								/*
								 * System.out.print(cell.getDateCellValue() +
								 * "D" + "\t\t");
								 */
								expectedOut = cell.getDateCellValue();
							} else {
								/*
								 * System.out.print(cell.getNumericCellValue() +
								 * "N" + "\t\t");
								 */
								expectedOut = cell.getDateCellValue();
							}
							break;
						case Cell.CELL_TYPE_STRING:
							/*
							 * System.out.print(cell.getRichStringCellValue()
							 * .getString() + "T" + "\t\t\t\t\t\t");
							 */
							expectedOut = null;
							break;
						}
					}

					if (count == 7) {
						switch (cell.getCellType()) {
						/*
						 * case Cell.CELL_TYPE_NUMERIC: if
						 * (DateUtil.isCellDateFormatted(cell)) { {
						 * System.out.print(cell.getDateCellValue()+"\t");
						 * firstIn=cell.getDateCellValue(); } } break;
						 */
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								/*
								 * System.out.print(cell.getDateCellValue() +
								 * "D" + "\t\t");
								 */
								firstIn = cell.getDateCellValue();
							} else {
								/*
								 * System.out.print(cell.getNumericCellValue() +
								 * "N" + "\t\t");
								 */
								firstIn = cell.getDateCellValue();
							}
							break;
						case Cell.CELL_TYPE_STRING:
							/*
							 * System.out.print(cell.getRichStringCellValue()
							 * .getString() + "T" + "\t\t\t\t\t");
							 */
							firstIn = null;
							break;
						}
					}
					if (count == 8) {
						switch (cell.getCellType()) {
						/*
						 * case Cell.CELL_TYPE_NUMERIC: if
						 * (DateUtil.isCellDateFormatted(cell)) {
						 * {System.out.print(cell.getDateCellValue()+"\t");
						 * lastOut=cell.getDateCellValue(); } } break;
						 */
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								/*
								 * System.out.print(cell.getDateCellValue() +
								 * "D" + "\t\t");
								 */
								lastOut = cell.getDateCellValue();
							} else {
								/*
								 * System.out.print(cell.getNumericCellValue() +
								 * "N" + "\t\t");
								 */
								lastOut = cell.getDateCellValue();
							}
							break;
						case Cell.CELL_TYPE_STRING:
							/*
							 * System.out.print(cell.getRichStringCellValue()
							 * .getString() + "T" + "\t\t\t\t\t");
							 */
							lastOut = null;
							break;
						}
					}
					/*
					 * switch (cell.getCellType()) { case
					 * Cell.CELL_TYPE_NUMERIC: if
					 * (DateUtil.isCellDateFormatted(cell)) {
					 * System.out.println(cell.getDateCellValue()); } else {
					 * System.out.println(cell.getNumericCellValue()); } break;
					 * case Cell.CELL_TYPE_STRING:
					 * System.out.print(cell.getStringCellValue() + "\t");
					 * System.out.print(count);
					 * 
					 * 
					 * break; }
					 */
		        }
		    	Calendar dateCal = Calendar.getInstance();
				if (date != null) {
					System.out.println("");

					dateCal.setTime(date);
				}

				Calendar expectedInCal = Calendar.getInstance();

				if (expectedIn != null) {
					expectedInCal.setTime(expectedIn);
				} else {
					expectedInCal = null;
				}
				Calendar expectedOutCal = Calendar.getInstance();
				if (expectedOut != null) {
					expectedOutCal.setTime(expectedOut);
				} else {
					expectedOutCal = null;
				}
				Calendar inCal = Calendar.getInstance();
				if (firstIn != null) {
					inCal.setTime(firstIn);
				} else {
					inCal = null;
				}
				Calendar outCal = Calendar.getInstance();
				if (lastOut != null) {
					outCal.setTime(lastOut);
				} else {
					outCal = null;
				}
				DailyAttDataDTO dao = new DailyAttDataDTO(dateCal,
						expectedInCal, expectedOutCal, inCal, outCal, fileNo);
				dataList.add(dao);

				System.out.println("");
			}

		  //  filse.close();
			dataList.remove(0);
			System.out.println("list size >>>>>> " + dataList.size());
			if (dataList.size() == 0)
				throw new ConventionProblem("Convention Problem ");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConventionProblem("Convention Problem ");
		}
		    
		
		return dataList;
	}

	public List<DailyAttDataDTO> newParse(InputStream input) throws IOException,
	ConventionProblem {
	{
		List<DailyAttDataDTO> dataList = new ArrayList<DailyAttDataDTO>();

		//InputStream inputStream = null;
		//Resource resource = null;
		// resource = new ClassPathResource("resources/studentsMails.xlsx");
		//resource = new ClassPathResource(filePath);
		try {
			//inputStream = resource.getInputStream();
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(input);

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
					// Check the cell type and format accordingly

					if (count == 2) { // file No OR Date of attendance
						
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
						{ System.out.print(cell.getNumericCellValue()
														 + "\t\t");
							String dec = new BigDecimal(
									cell.getNumericCellValue()).toString();
							try{
							fileNo = Integer.parseInt(dec);
							 System.out.print(Integer.parseInt(dec)+1 +
							 "\t\t");
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								
							}
			
							 break;
							 
						}
						case Cell.CELL_TYPE_STRING:
						{
							System.out.print(cell.getStringCellValue());
							try{
							fileNo = Integer.parseInt(cell.getStringCellValue());
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								
							}
						}
						
						}
					}

					

					if (count == 5) {
						switch (cell.getCellType()) {
						
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								System.out.print(cell.getDateCellValue() + "D"
										+ "\t\t");
								firstIn = cell.getDateCellValue();
							} else {
								firstIn = cell.getDateCellValue();
								System.out.print(cell.getNumericCellValue()
										+ "N" + "\t\t");

							}
							break;
						case Cell.CELL_TYPE_STRING:
							/*System.out.print(cell.getRichStringCellValue()
									.getString() + "T" + "\t\t\t\t\t");*/
							DateFormat dfs = new SimpleDateFormat(" hh:mma"); 
						    Date startDate;
						    try {
						        startDate = dfs.parse(cell.getRichStringCellValue().getString());
						       // String newDateString = dfs.format(startDate);
						        System.out.println(startDate);
						    	firstIn = startDate;
						    } catch (ParseException e) {
						    
						        e.printStackTrace();
						        if(cell.getRichStringCellValue().getString().equals("NULL"))
						        {
						        	firstIn=null;
						        }
						        else  if(cell.getRichStringCellValue().getString().equals("-"))
						        {
						        	firstIn=null;
						        }
						        else if(!cell.getRichStringCellValue().getString().equals("First In"))
						        {
						        	DateFormat secParser = new SimpleDateFormat("hh:mma"); 
						        	   startDate = secParser.parse(cell.getRichStringCellValue().getString());
								       // String newDateString = dfs.format(startDate);
								        System.out.println(startDate);
								    	firstIn = startDate;
						        }
						      
						    }
						
							break;
						}
					}

					if (count == 6) {
						switch (cell.getCellType()) {
						/*
						 * case Cell.CELL_TYPE_NUMERIC: if
						 * (DateUtil.isCellDateFormatted(cell)) {
						 * {//System.out.println(cell.getDateCellValue());
						 * expectedIn=cell.getDateCellValue(); } } break;
						 */
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
							System.out.print(cell.getDateCellValue() + "D"
										+ "\t\t");
								lastOut = cell.getDateCellValue();
							} else {
								System.out.print(cell.getNumericCellValue()
										+ "N" + "\t\t");
								lastOut = cell.getDateCellValue();
							}
							break;
						case Cell.CELL_TYPE_STRING:
							DateFormat dfs = new SimpleDateFormat("hh:mma"); 
						    Date startDate;
						    try {
						        startDate = dfs.parse(cell.getRichStringCellValue().getString());
						       // String newDateString = dfs.format(startDate);
						        System.out.println(startDate);
						    	lastOut = startDate;
						    } catch (ParseException e) {
						    	
							        
							       
						        e.printStackTrace();
						        if(cell.getRichStringCellValue().getString().equals("NULL"))
						        {
						        	lastOut=null;
						        }
						        else  if(cell.getRichStringCellValue().getString().equals("-"))
						        {
						        	lastOut=null;
						        }
						        else if(!cell.getRichStringCellValue().getString().equals("Last Out"))
						        {
						        	DateFormat secParse = new SimpleDateFormat(" hh:mma");
						        	 startDate = secParse.parse(cell.getRichStringCellValue().getString());
								       // String newDateString = dfs.format(startDate);
								        System.out.println(startDate);
								    	lastOut = startDate;
						        }
						       
						    }
							break;
						}
					}

				
				}
			
				Calendar dateCal = Calendar.getInstance();
				if (date != null) {
					System.out.println("");

					dateCal.setTime(date);
				}
				
				Calendar expectedInCal = Calendar.getInstance();

				if (expectedIn != null) {
					expectedInCal.setTime(expectedIn);
				} else {
					expectedInCal = null;
				}
				Calendar expectedOutCal = Calendar.getInstance();
				if(expectedOut != null)
				{
				expectedOutCal.setTime(expectedOut);
				}
				else
				{
					expectedOutCal=null;
				}
				Calendar inCal = Calendar.getInstance();
				if(firstIn != null)
				{
				inCal.setTime(firstIn);
				}
				else
				{
					inCal=null;
				}
				Calendar outCal = Calendar.getInstance();
				if(lastOut != null)
				{
				outCal.setTime(lastOut);
				}
				else
				{
					outCal=null;
				}
				if(fileNo==201400387)
				{
					System.out.println("Proble");
				}
					DailyAttDataDTO dao = new DailyAttDataDTO(dateCal, expectedInCal,
							expectedOutCal, inCal, outCal, fileNo);
					dataList.add(dao);
				
				System.out.println("");
			}

			input.close();
		 dataList.remove(0);
		// dataList.remove(0);
		// dataList.remove(0);
	//	 dataList.remove(0);
		/* dataList.remove(0);
		 dataList.remove(2);
		 dataList.remove(3);*/
		
		 
			System.out.println("list size >>>>>> " + dataList.size());
			if (dataList.size() == 0)
				throw new ConventionProblem("Convention Problem ");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConventionProblem("Convention Problem ");
		}
		return dataList;

	}}


}
