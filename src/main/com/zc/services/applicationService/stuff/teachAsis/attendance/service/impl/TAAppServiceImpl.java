/**
 * 
 */
package main.com.zc.services.applicationService.stuff.teachAsis.attendance.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.attendance.dailyAttendance.exceptions.ConventionProblem;
import main.com.zc.services.applicationService.stuff.teachAsis.attendance.service.ITAAppService;
import main.com.zc.services.domain.stuff.teachAsis.attendance.model.ITARepository;
import main.com.zc.services.domain.stuff.teachAsis.attendance.model.TA;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;
import main.com.zc.services.presentation.stuff.teachAsis.attendance.dto.TADTO;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
/**
 * @author omnya
 *
 */
@Service("TAAppServiceImpl")
public class TAAppServiceImpl implements ITAAppService {
@Autowired
ITARepository taRep;

	@Override
	public TADTO add(TADTO dto) {
		try
		{
			TADTO added=new TADTO();
			TA ta=new TA();
			ta.setName(dto.getName());
			ta.setDept(dto.getDepartment());
			ta.setFacultyID(dto.getFacultyID());
			ta=taRep.add(ta);
			added.setId(ta.getId());
			added.setName(ta.getName());
			added.setDepartment(ta.getName());
			added.setDepartment(ta.getDept());
			return added;
			
		}
		catch(Exception ex)
		{
            System.out.println("Error in addint TA:"+dto.getName());
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<TADTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseFile(String path) throws FileNotFoundException {
        File file=new File(path);
        InputStream inputStream = null;
		List<DailyAttDataDTO> dataList = new ArrayList<DailyAttDataDTO>();

		Resource resource = null;
		// resource = new ClassPathResource("resources/studentsMails.xlsx");
		resource = new ClassPathResource(path);
		try {

			inputStream = resource.getInputStream();
			//inputStream = resource.getInputStream();
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
                					
					// Check the cell type and format accordingly

					if (count == 2) { // file No OR Date of attendance
						
						/*switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC: { System.out.print(cell.getNumericCellValue()
														 + "\t\t");
							String dec = new BigDecimal(
									cell.getNumericCellValue()).toString();
							fileNo = Integer.parseInt(dec);
							 System.out.print(Integer.parseInt(dec)+1 +
							 "\t\t");
							 if(HSSFDateUtil.isCellDateFormatted(cell))
										{
									DateFormat dfs = new SimpleDateFormat("dd-MMMM-yy"); 
								    Date startDate;
								    try {
								        startDate = dfs.parse(cell.toString());
								       // String newDateString = dfs.format(startDate);
								        System.out.println(startDate);
								    	date = startDate;
								    } catch (ParseException e) {
								        e.printStackTrace();
								        if(cell.toString().equals("NULL"))
								        {
								        	date=null;
								        }
								        else 
								        {
								        	 dfs = new SimpleDateFormat("dd-MMM-yy"); 
								        	 startDate = dfs.parse(cell.toString());
								        	 System.out.println(startDate);
										    	date = startDate;
								        }
								    }
								    
										}
							 break;
							 
						}
						
						}*/
					}

					

					if (count == 5) {
						/*switch (cell.getCellType()) {
						
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
							System.out.print(cell.getRichStringCellValue()
									.getString() + "T" + "\t\t\t\t\t");
							DateFormat dfs = new SimpleDateFormat(" hh:mm:ss a"); 
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
						    }
						
							break;*/
						
					}

					if (count == 6) {
					/*	switch (cell.getCellType()) {
						
						 * case Cell.CELL_TYPE_NUMERIC: if
						 * (DateUtil.isCellDateFormatted(cell)) {
						 * {//System.out.println(cell.getDateCellValue());
						 * expectedIn=cell.getDateCellValue(); } } break;
						 
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
							DateFormat dfs = new SimpleDateFormat(" hh:mm a"); 
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
						    }
							break;
						}*/
					}

				
				}
			
	

	
		
	}

}
		catch (Exception e) {
			e.printStackTrace();
		}
}}