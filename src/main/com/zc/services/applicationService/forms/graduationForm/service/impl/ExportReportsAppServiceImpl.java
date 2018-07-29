/**
 * 
 */
package main.com.zc.services.applicationService.forms.graduationForm.service.impl;

import java.io.IOException;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.graduationForm.service.IExportReportsAppService;
import main.com.zc.services.presentation.forms.graduationForm.dto.GraduationInformationDTO;

/**
 * @author omnya
 *
 */
@Service
public class ExportReportsAppServiceImpl implements IExportReportsAppService{

	@Override
	public void export(List<GraduationInformationDTO> lst) {
		
		HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet();
	         
	        HSSFRow row;
	         
	        row = sheet.createRow((short)0);
	        row.createCell((short)0).setCellValue("Student ID");
	        row.createCell((short)1).setCellValue("First Name");
	        row.createCell((short)2).setCellValue("Middle Name/s");
	        row.createCell((short)3).setCellValue("Last Name");
	        row.createCell((short)4).setCellValue("الاسم بالكامل");
	        row.createCell((short)5).setCellValue("الرقم القومي");
	        row.createCell((short)6).setCellValue("محل الميلاد");
	        row.createCell((short)7).setCellValue("الجنسية");
	        row.createCell((short)8).setCellValue("Address");
	        row.createCell((short)9).setCellValue("Email");
	        row.createCell((short)10).setCellValue("Phone");
	        row.createCell((short)11).setCellValue("Mobile");
	        row.createCell((short)12).setCellValue("Emergency Contact Information Name");
	        row.createCell((short)13).setCellValue("Emergency Contact Information Relationship");
	        row.createCell((short)14).setCellValue("Emergency Contact Information Mobile Number");
	        row.createCell((short)15).setCellValue("Program");
	        row.createCell((short)16).setCellValue("Concentration");
	        row.createCell((short)17).setCellValue("Minor");
	        row.createCell((short)18).setCellValue("Attend");
	        row.createCell((short)19).setCellValue("Height:");
	        row.createCell((short)20).setCellValue("Size");
	       
	          for (int i = 0; i < lst.size(); i++){
	        	row = sheet.createRow((short)i+1);
	           	if(lst.get(i)!=null)
	        	{
	        	
	        				   
	        					if(lst.get(i).getStudent()!=null)
	        	        		{		  
	        					row.createCell((short)0).setCellValue(lst.get(i).getStudent().getFacultyId());
	            			    }
	        					 if(lst.get(i).getFirstName()!=null)
	        				    row.createCell((short)1).setCellValue(lst.get(i).getFirstName());
	        				    if(lst.get(i).getMiddleName()!=null)
	        				    row.createCell((short)2).setCellValue(lst.get(i).getMiddleName());
	        				    if(lst.get(i).getLastName()!=null)
	        				    row.createCell((short)3).setCellValue(lst.get(i).getLastName());
	        				    if(lst.get(i).getArabicName()!=null)
	        				    row.createCell((short)4).setCellValue(lst.get(i).getArabicName());
	        				    if(lst.get(i).getnID()!=null)
	        				    row.createCell((short)5).setCellValue(lst.get(i).getnID());
	        				    if(lst.get(i).getBirthPlace()!=null)
	        				    row.createCell((short)6).setCellValue(lst.get(i).getBirthPlace());
	        				    if(lst.get(i).getNationality()!=null)
	        				    row.createCell((short)7).setCellValue(lst.get(i).getNationality());
	        				    if(lst.get(i).getAddress()!=null)
	        				    row.createCell((short)8).setCellValue(lst.get(i).getAddress());
	        				    if(lst.get(i).getStudent()!=null)
	        				    row.createCell((short)9).setCellValue(lst.get(i).getStudent().getMail());
	        				    if(lst.get(i).getPhone()!=null)
	        				    row.createCell((short)10).setCellValue(lst.get(i).getPhone());
	        				    if(lst.get(i).getMobile()!=null)
	        				    row.createCell((short)11).setCellValue(lst.get(i).getMobile());
	        				    if(lst.get(i).getEmegencyConatactName()!=null)
	        				    row.createCell((short)12).setCellValue(lst.get(i).getEmegencyConatactName());
	        				    if(lst.get(i).getEmegencyRelationship()!=null)
	        				    row.createCell((short)13).setCellValue(lst.get(i).getEmegencyRelationship());
	        				    if(lst.get(i).getEmegencyMobile()!=null)
	        				    row.createCell((short)14).setCellValue(lst.get(i).getEmegencyMobile());
	        				    if(lst.get(i).getMajor()!=null)
	        				    row.createCell((short)15).setCellValue(lst.get(i).getMajor().getMajorName());
	        				    if(lst.get(i).getConcentration()!=null)
		        				    row.createCell((short)16).setCellValue(lst.get(i).getConcentration().getName());
	        				    if(lst.get(i).getMinor()!=null)
		        				    row.createCell((short)17).setCellValue(lst.get(i).getMinor().getMajorName());
	        				    row.createCell((short)18).setCellValue(lst.get(i).getAttend());
	        				    if(lst.get(i).getHight()!=null)
	        				    row.createCell((short)19).setCellValue(lst.get(i).getHight());
	        				    if(lst.get(i).getSize()!=null)
	        				    row.createCell((short)20).setCellValue(lst.get(i).getSize());
	            
	        		
	        		
	        	}
	          
	      
	        }
	    	     HttpServletResponse response =
	                (HttpServletResponse) FacesContext.getCurrentInstance()
	                    .getExternalContext().getResponse();
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-disposition",  "attachment; filename=GraduationForms.xls");
	         
	         
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
