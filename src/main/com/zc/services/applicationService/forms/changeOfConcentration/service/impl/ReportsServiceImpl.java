/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeOfConcentration.service.impl;

import java.io.IOException;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.changeOfConcentration.service.IReportsService;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;

/**
 * @author omnya
 *
 */
@Service
public class ReportsServiceImpl implements IReportsService{

	@Override
	public void generateExcelByList(List<ChangeConcentrationDTO> lst) {
			 HSSFWorkbook wb = new HSSFWorkbook();
    	        HSSFSheet sheet = wb.createSheet();
    	         
    	        HSSFRow row;
    	         
    	        row = sheet.createRow((short)0);
    	        row.createCell((short)0).setCellValue("Student ID");
    	        row.createCell((short)1).setCellValue("Student Name");
    	        row.createCell((short)2).setCellValue("Major");
    	        row.createCell((short)3).setCellValue("Current Concentration");
    	        row.createCell((short)4).setCellValue("New Concentration");
    	        
    	        for (int i = 0; i < lst.size(); i++){
    	        	row = sheet.createRow((short)i+1);
    	           	if(lst.get(i)!=null)
    	        	{
    	        		if(lst.get(i).getStudent()!=null)
    	        		{
    	        				    row.createCell((short)0).setCellValue(lst.get(i).getStudent().getFacultyId());
    	            			  
    	            			    row.createCell((short)1).setCellValue(lst.get(i).getStudent().getName());
    	        		
    	        		
    	        		}
    	        		if(lst.get(i).getMajor()!=null)
    	        		{
    	        				    row.createCell((short)2).setCellValue(lst.get(i).getMajor().getMajorName());
    	            		
    	        		}
    	        		if(lst.get(i).getCurrentConcen()!=null)
    	        		{
    	        				    row.createCell((short)3).setCellValue(lst.get(i).getCurrentConcen().getName());
    	            		
    	        		}
    	        		if(lst.get(i).getNewConcen()!=null)
    	        		{
    	        				    row.createCell((short)4).setCellValue(lst.get(i).getNewConcen().getName());
    	            		
    	        		}
    	        	}
    	          
    	      
    	        }
    	    	     HttpServletResponse response =
    	                (HttpServletResponse) FacesContext.getCurrentInstance()
    	                    .getExternalContext().getResponse();
    	        response.setContentType("application/vnd.ms-excel");
    	        response.setHeader("Content-disposition",  "attachment; filename=ChangeConcentrationReport.xls");
    	         
    	         
    	        try {
    	            ServletOutputStream out = response.getOutputStream();
    	   
    	             wb.write(out);
    	             out.flush();
    	             out.close();
    	         
    	       } catch (IOException ex) { 
    	               ex.printStackTrace();
    	       }
    	  
    	         
    	      FacesContext faces = FacesContext.getCurrentInstance();
    	      faces.responseComplete();  
    	      
    	    
	}

}
