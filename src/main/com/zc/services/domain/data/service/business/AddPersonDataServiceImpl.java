/**
 * 
 */
package main.com.zc.services.domain.data.service.business;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author Omnya
 *
 */@Service
 @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AddPersonDataServiceImpl  implements IAddPersonDataService{
	     String FileNumber;
		 String ActualDate;
		 String ExpectedIn;
		 String ExpectedOut;
		 String FirstIn;
		 String FirstOut;
	 @Autowired 
	 IStudentRepository personRep;
	
	
	public   void parse()
	{
		   try
	        {
	            FileInputStream file = new FileInputStream(new File("30-10-2013.xlsx"));
	 
	            //Create Workbook instance holding reference to .xlsx file
	          XSSFWorkbook workbook = new XSSFWorkbook(file);
	 
	            //Get first/desired sheet from the workbook
	           XSSFSheet sheet = workbook.getSheetAt(0);
	 
	            //Iterate through each rows one by one
	            
	            Iterator<Row> rowIterator = sheet.iterator();
	            
	         
	            while (rowIterator.hasNext()) 
	            {
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                Iterator<Cell> cellIterator = row.cellIterator();
	                 
	                int count=0;
	                while (cellIterator.hasNext()) 
	                {
	                   Cell cell = cellIterator.next();
	                   count++;
	                    //Check the cell type and format accordingly
	                   
	                   if(count==1)
	                   {
	                	   FileNumber=cell.toString();
	                	   System.out.print(FileNumber + "\t");
	                   }
	                	
	                   if(count==2 || count==3)
	                   
	                   {
	                	   //Not Needed E_Name, A_Name
	                	   //System.out.print("Not Needed\t");
	                   }
	                   
	                   if(count==4)
	                   {
	                	 ActualDate=cell.toString();
	                	//System.out.print(cell.getStringCellValue() + "\t");
	                	System.out.print(ActualDate + "\t");
	                   }
	                   
	                   if(count==5)
	                   {
	                	   ExpectedIn=cell.toString();
	                	   System.out.print(ExpectedIn + "\t");
	                   }
	                   
	                   if(count==6)
	                   {
	                	   ExpectedOut=cell.toString();
	                	   System.out.print(ExpectedOut + "\t");
	                   }
	                   
	                   if(count==7)
	                   {
	                	   FirstIn=cell.toString();
	                	   System.out.print(FirstIn + "\t");
	                   }
	                   
	                   if(count==8)
	                   {
	                	   FirstOut=cell.toString();
	                	   System.out.print(FirstOut + "\t");
	                   }
	                   
	                  /*
	                    switch (cell.getCellType()) 
	                    {
	                        case Cell.CELL_TYPE_NUMERIC:
	                            System.out.print(cell.getNumericCellValue() + "\t");
	                            System.out.print(count);
	                            break;
	                        case Cell.CELL_TYPE_STRING:
	                            System.out.print(cell.getStringCellValue() + "\t");
	                            System.out.print(count);
	                            break;
	                    }
	                   */
	                   //Parse to object here
	                    
	                }
	                System.out.println("");
	            }
	            file.close();
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }	
	}

	@Override
	public Student addPersonData(Student per) {
		int id=personRep.add(per);
		return personRep.getPersonById(id);
	}

}
