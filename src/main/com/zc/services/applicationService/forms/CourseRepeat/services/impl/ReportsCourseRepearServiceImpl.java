/**
 * 
 */
package main.com.zc.services.applicationService.forms.CourseRepeat.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.CourseRepeat.assembler.CourseRepeatAssembler;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.IReportsCourseRepearService;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.IRepeatCourseFormRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;

/**
 * @author omnya
 *
 */
@Service
public class ReportsCourseRepearServiceImpl implements IReportsCourseRepearService{
	
	@Autowired
	IRepeatCourseFormRep rep;
	@Autowired
	IPetitionsActionsRep actionRep;
	CourseRepeatAssembler assem=new CourseRepeatAssembler();

	@Override
	public List<CourseRepeatDTO> getOldSummer(Integer year) {
		List<CourseRepeatDTO> filterdDTO=new ArrayList<CourseRepeatDTO>();
		try{
		List<RepeatCourseForm> allForms=rep.getOldSummer(year);
		
		for(int i=0;i<allForms.size();i++)
		{
		
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.REPEATECOURSE.getValue());
				List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
				if(actions!=null)
				{
					for(int a=0;a<actions.size();a++)
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setId(actions.get(a).getId());
						actionDTO.setComment(actions.get(a).getComment());
						actionDTO.setDate(actions.get(a).getDate());
						actionDTO.setFormType(actions.get(a).getFormType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
						actionDTO.setPetitionID(actions.get(a).getPetitionID());
						actionDTO.setActionType(actions.get(a).getActionType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
						actionsDTO.add(actionDTO);
					}
				}
				CourseRepeatDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
				
		}
		return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting old petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	}

	@Override
	public List<CourseRepeatDTO> getOldSpring(Integer year) {
		List<CourseRepeatDTO> filterdDTO=new ArrayList<CourseRepeatDTO>();
		try{
		List<RepeatCourseForm> allForms=rep.getOldSpring(year);
		
		for(int i=0;i<allForms.size();i++)
		{
		
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.REPEATECOURSE.getValue());
				List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
				if(actions!=null)
				{
					for(int a=0;a<actions.size();a++)
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setId(actions.get(a).getId());
						actionDTO.setComment(actions.get(a).getComment());
						actionDTO.setDate(actions.get(a).getDate());
						actionDTO.setFormType(actions.get(a).getFormType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
						actionDTO.setPetitionID(actions.get(a).getPetitionID());
						actionDTO.setActionType(actions.get(a).getActionType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
						actionsDTO.add(actionDTO);
					}
				}
				CourseRepeatDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
				
		}
		return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting old petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	}

	@Override
	public List<CourseRepeatDTO> getOldFall(Integer year) {
		List<CourseRepeatDTO> filterdDTO=new ArrayList<CourseRepeatDTO>();
		try{
		List<RepeatCourseForm> allForms=rep.getOldFall(year);
		
		for(int i=0;i<allForms.size();i++)
		{
		
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.REPEATECOURSE.getValue());
				List<PetitionsActionsDTO> actionsDTO=new ArrayList<PetitionsActionsDTO>();
				if(actions!=null)
				{
					for(int a=0;a<actions.size();a++)
					{
						PetitionsActionsDTO actionDTO=new PetitionsActionsDTO();
						actionDTO.setId(actions.get(a).getId());
						actionDTO.setComment(actions.get(a).getComment());
						actionDTO.setDate(actions.get(a).getDate());
						actionDTO.setFormType(actions.get(a).getFormType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorID(actions.get(a).getInstructor().getId());
						actionDTO.setPetitionID(actions.get(a).getPetitionID());
						actionDTO.setActionType(actions.get(a).getActionType());
						if(actions.get(a).getInstructor()!=null)
						actionDTO.setInstructorName(actions.get(a).getInstructor().getName());
						actionsDTO.add(actionDTO);
					}
				}
				CourseRepeatDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
				
		}
		return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting old petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	}

	@Override
	public void generateExcelByList(List<CourseRepeatDTO> lst) {
		 HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet();
	         
	        HSSFRow row;
	         
	        row = sheet.createRow((short)0);
	        row.createCell((short)0).setCellValue("Student ID");
	        row.createCell((short)1).setCellValue("Student Name");
	        row.createCell((short)2).setCellValue("Major");
	        row.createCell((short)3).setCellValue("Repeated Course");
	        row.createCell((short)4).setCellValue("Reason");
	        row.createCell((short)5).setCellValue("Grade");
	        row.createCell((short)6).setCellValue("Was taken in");
	        row.createCell((short)7).setCellValue("New semester");
	        row.createCell((short)8).setCellValue("Date");
	       //row.createCell((short)4).setCellValue("New Concentration");
	        
	        for (int i = 0; i < lst.size(); i++){
	        	row = sheet.createRow((short)i+1);
	           	if(lst.get(i)!=null)
	        	{
	        	
	        				    row.createCell((short)0).setCellValue(lst.get(i).getStudent().getFacultyId());
	        					if(lst.get(i).getStudent()!=null)
	        	        		{		  
	            			    row.createCell((short)1).setCellValue(lst.get(i).getStudent().getName());
	        		
	        		
	        		}
	        		if(lst.get(i).getMajor()!=null)
	        		{
	        				    row.createCell((short)2).setCellValue(lst.get(i).getMajor().getMajorName());
	            		
	        		}
	        		if(lst.get(i).getCourse()!=null)
	        		{
	        				    row.createCell((short)3).setCellValue(lst.get(i).getCourse().getName());
	            		
	        		}
	        		if(lst.get(i).getReason()!=null)
	        		{
	        				    row.createCell((short)4).setCellValue(lst.get(i).getReason());
	            		
	        		}
	        		if(lst.get(i).getGrade()!=null)
	        		{
	        				    row.createCell((short)5).setCellValue(lst.get(i).getGrade());
	            		
	        		}
	        		if(lst.get(i).getOldSem()!=null)
	        		{
	        				    row.createCell((short)6).setCellValue(lst.get(i).getOldSem().getName());
	            		
	        		}
	        		if(lst.get(i).getNewSem()!=null)
	        		{
	        				    row.createCell((short)7).setCellValue(lst.get(i).getNewSem().getName());
	            		
	        		}
	        		if(lst.get(i).getFriendlyDate()!=null)
	        		{
	        				    row.createCell((short)8).setCellValue(lst.get(i).getFriendlyDate());
	            		
	        		}
	        		
	        	}
	          
	      
	        }
	    	     HttpServletResponse response =
	                (HttpServletResponse) FacesContext.getCurrentInstance()
	                    .getExternalContext().getResponse();
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-disposition",  "attachment; filename=Course-Repeat-Reports.xls");
	         
	         
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
