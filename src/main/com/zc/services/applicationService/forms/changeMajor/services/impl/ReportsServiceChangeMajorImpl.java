/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeMajor.services.impl;

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
import main.com.zc.services.applicationService.forms.changeMajor.assembler.ChangeMajorAssembler;
import main.com.zc.services.applicationService.forms.changeMajor.services.IReportsServiceChangeMajor;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.IChangeMajorFormRep;
import main.com.zc.services.domain.petition.model.IPetitionsActionsRep;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;

/**
 * @author omnya
 *
 */
@Service
public class ReportsServiceChangeMajorImpl implements IReportsServiceChangeMajor{

	@Autowired
	IChangeMajorFormRep rep;
	@Autowired
	IPetitionsActionsRep actionRep;
	ChangeMajorAssembler assem=new ChangeMajorAssembler();
	@Override
	public List<ChangeMajorDTO> getOldSummer(Integer year) {
		List<ChangeMajorDTO> filterdDTO=new ArrayList<ChangeMajorDTO>();
		try{
		List<ChangeMajorForm> allForms=rep.getOldSummer(year);
		
		for(int i=0;i<allForms.size();i++)
		{
		
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
				ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
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
	public List<ChangeMajorDTO> getOldSpring(Integer year) {
		List<ChangeMajorDTO> filterdDTO=new ArrayList<ChangeMajorDTO>();
		try{
		List<ChangeMajorForm> allForms=rep.getOldSpring(year);
		
		for(int i=0;i<allForms.size();i++)
		{
		
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
				ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
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
	public List<ChangeMajorDTO> getOldFall(Integer year) {
		List<ChangeMajorDTO> filterdDTO=new ArrayList<ChangeMajorDTO>();
		try{
		List<ChangeMajorForm> allForms=rep.getOldFall(year);
		
		for(int i=0;i<allForms.size();i++)
		{
		
				// first add list of actions to this petition 
				List<PetitionsActions> actions=actionRep.getByPetitionIDAndForm(allForms.get(i).getId(),FormTypesEnum.CHANGEMAJOR.getValue());
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
				ChangeMajorDTO dto=assem.toDTO(allForms.get(i));
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
	public void generateExcelByList(List<ChangeMajorDTO> lst) {
		 HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet();
	         
	        HSSFRow row;
	         
	        row = sheet.createRow((short)0);
	        row.createCell((short)0).setCellValue("Student ID");
	        row.createCell((short)1).setCellValue("Student Name");
	        row.createCell((short)2).setCellValue("Current Major");
	        row.createCell((short)3).setCellValue("New MAjor");
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
	        		if(lst.get(i).getCurMajor()!=null)
	        		{
	        				    row.createCell((short)2).setCellValue(lst.get(i).getCurMajor().getMajorName());
	            		
	        		}
	        		if(lst.get(i).getNewMajor()!=null)
	        		{
	        				    row.createCell((short)3).setCellValue(lst.get(i).getNewMajor().getMajorName());
	            		
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
