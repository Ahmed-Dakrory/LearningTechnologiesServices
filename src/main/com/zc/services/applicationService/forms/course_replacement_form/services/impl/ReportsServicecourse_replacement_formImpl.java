/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.services.impl;

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
import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.domain.petition.model.Icourse_replacement_formFormRep;
import main.com.zc.services.applicationService.forms.course_replacement_form.assembler.course_replacement_formAssembler;
import main.com.zc.services.applicationService.forms.course_replacement_form.services.IReportsServicecourse_replacement_form;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;

/**
 * @author omnya
 *
 */
@Service
public class ReportsServicecourse_replacement_formImpl implements IReportsServicecourse_replacement_form{

	@Autowired
	Icourse_replacement_formFormRep rep;
	@Autowired
	IPetitionsActionsRep actionRep;
	course_replacement_formAssembler assem=new course_replacement_formAssembler();
	@Override
	public List<course_replacement_formDTO> getOldSummer(Integer year) {
		List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
		try{
		List<course_replacement_formForm> allForms=rep.getOldSummer(year);
		
		for(int i=0;i<allForms.size();i++)
		{
		
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
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
				course_replacement_formDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
				
		}
		return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting pending petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	}

	@Override
	public List<course_replacement_formDTO> getOldSpring(Integer year) {
		List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
		try{
		List<course_replacement_formForm> allForms=rep.getOldSpring(year);
		
		for(int i=0;i<allForms.size();i++)
		{
		
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
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
				course_replacement_formDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
				
		}
		return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting pending petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	}

	@Override
	public List<course_replacement_formDTO> getOldFall(Integer year) {
		List<course_replacement_formDTO> filterdDTO=new ArrayList<course_replacement_formDTO>();
		try{
		List<course_replacement_formForm> allForms=rep.getOldFall(year);
		
		for(int i=0;i<allForms.size();i++)
		{
		
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.COURSE_REPLACEMENT_FORM.getValue());
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
				course_replacement_formDTO dto=assem.toDTO(allForms.get(i));
				dto.setActionDTO(actionsDTO);
				
			     filterdDTO.add(dto);
				
		}
		return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting pending petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	}

	@Override
	public void generateExcelByList(List<course_replacement_formDTO> lst) {
		 HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet();
	         
	        HSSFRow row;
	         
	        row = sheet.createRow((short)0);
	        row.createCell((short)0).setCellValue("Student ID");
	        row.createCell((short)1).setCellValue("Student Name");
	        row.createCell((short)4).setCellValue("Date");
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
	        		
	        		
	        		if(lst.get(i).getFriendlyDate()!=null)
	        		{
	        				    row.createCell((short)4).setCellValue(lst.get(i).getFriendlyDate());
	            		
	        		}
	        		
	        	}
	          
	      
	        }
	    	     HttpServletResponse response =
	                (HttpServletResponse) FacesContext.getCurrentInstance()
	                    .getExternalContext().getResponse();
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-disposition",  "attachment; filename=ChangemajorReports.xls");
	         
	         
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
