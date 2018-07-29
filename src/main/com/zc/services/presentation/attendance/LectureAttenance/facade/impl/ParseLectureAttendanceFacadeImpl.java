/**
 * 
 */
package main.com.zc.services.presentation.attendance.LectureAttenance.facade.impl;

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
import main.com.zc.services.presentation.attendance.LectureAttenance.dto.LectureAttendanceDTO;
import main.com.zc.services.presentation.attendance.LectureAttenance.facade.IParseLectureAttendanceFacade;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("IParseLectureAttendanceFacade")
public class ParseLectureAttendanceFacadeImpl implements IParseLectureAttendanceFacade {

	private Integer fileNo;
	private Date date;
	private Date firstIn;
	private Date lastOut;
	private Date status;
	private String name;
	@Override
	public List<LectureAttendanceDTO>  parse(UploadedFile file) throws ConventionProblem {
		List<LectureAttendanceDTO> dataList = new ArrayList<LectureAttendanceDTO>();

		//InputStream inputStream = null;
		//Resource resource = null;
		// resource = new ClassPathResource("resources/studentsMails.xlsx");
		//resource = new ClassPathResource(filePath);
		try {
			//inputStream = resource.getInputStream();
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputstream());

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

	if (count == 3) { // Name
						
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
							name = cell.getStringCellValue();
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
			/*	if(fileNo==201400387)
				{
					System.out.println("Proble");
				}*/
					/*DailyAttDataDTO dao = new DailyAttDataDTO(dateCal, expectedInCal,
							expectedOutCal, inCal, outCal, fileNo);*/
				    LectureAttendanceDTO dto=new LectureAttendanceDTO();
				    dto.setFileNo(fileNo);
				 
				    Calendar fCal=Calendar.getInstance();
				    if(firstIn!=null)
				    fCal.setTime(firstIn);
				 
				    Calendar lCal=Calendar.getInstance();
				    if(lastOut!=null)
				    lCal.setTime(lastOut);
				    if(firstIn==null)
				    {
				    	  dto.setFirst_in(null);
				    }
				    else 
				    dto.setFirst_in(fCal);
				    if(lastOut==null)
				    {
				    	  dto.setLast_out(null);
				    }
				    else
				    dto.setLast_out(lCal);
				    dto.setStudentName(name);
				    if(firstIn==null||lastOut==null)
				    {
				    	dto.setStudentStatus("Absent");
				    }
				    else if(firstIn!=null&lastOut!=null)
				    {
				    	dto.setStudentStatus("Attended");
				    }
					dataList.add(dto);
				
				System.out.println("");
			}

			file.getInputstream().close();
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

	}
	}
		
	


