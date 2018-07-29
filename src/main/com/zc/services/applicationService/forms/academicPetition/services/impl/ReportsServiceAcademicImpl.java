/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services.impl;

import java.io.IOException;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.academicPetition.services.IReportsServiceAcademic;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;

/**
 * @author omnya
 *
 */
@Service
public class ReportsServiceAcademicImpl implements IReportsServiceAcademic{

	
	@Override
	public void generateExcelByList(List<CoursePetitionDTO> lst) {
		 HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet();
	         
	        HSSFRow row;
	         
	        row = sheet.createRow((short)0);
	        row.createCell((short)0).setCellValue("Student ID");
	        row.createCell((short)1).setCellValue("Student Name");
	        row.createCell((short)2).setCellValue("Course");
	        row.createCell((short)3).setCellValue("Comment");
	       //row.createCell((short)4).setCellValue("New Concentration");
	        
	        for (int i = 0; i < lst.size(); i++){
	        	row = sheet.createRow((short)i+1);
	           	if(lst.get(i)!=null)
	        	{
	        	
	        				    row.createCell((short)0).setCellValue(lst.get(i).getStudentFileNo());
	        					if(lst.get(i).getStudentName()!=null)
	        	        		{		  
	            			    row.createCell((short)1).setCellValue(lst.get(i).getStudentName());
	        		
	        		
	        		}
	        		if(lst.get(i).getCourseName()!=null)
	        		{
	        				    row.createCell((short)2).setCellValue(lst.get(i).getCourseName());
	            		
	        		}
	        		if(lst.get(i).getRequestText()!=null)
	        		{
	        				    row.createCell((short)3).setCellValue(lst.get(i).getRequestText());
	            		
	        		}
	        		
	        	}
	          
	      
	        }
	    	     HttpServletResponse response =
	                (HttpServletResponse) FacesContext.getCurrentInstance()
	                    .getExternalContext().getResponse();
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-disposition",  "attachment; filename=AcademicReport.xls");
	         
	         
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
