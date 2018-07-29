/**
 * 
 */
package main.com.zc.services.applicationService.forms.CourseRepeat.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.CourseRepeat.assembler.CourseRepeatAssembler;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatAdminService;
import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.petition.model.IRepeatCourseFormRep;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service
public class CourseRepeatAdminServiceImpl implements ICourseRepeatAdminService{

	@Autowired
	IRepeatCourseFormRep rep;
	@Autowired
	ISharedNotifyService  sharedNotifyService;

	CourseRepeatAssembler assem=new CourseRepeatAssembler();
	@Override
	public List<CourseRepeatDTO> getPendingFormsOfAdmissionHead() {
		List<CourseRepeatDTO> filterdDTO=new ArrayList<CourseRepeatDTO>();
		try{
		List<RepeatCourseForm> allForms=rep.getAll();
		
		for(int i=0;i<allForms.size();i++)
		{
			RepeatCourseForm form = allForms.get(i);
			if(form.getPerformed() == null || form.getPerformed().equals(false))
				filterdDTO.add(assem.toDTO(allForms.get(i)));
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
	public List<CourseRepeatDTO> getArchievedFormsOfAdmissionHead() {
		List<CourseRepeatDTO> filterdDTO=new ArrayList<CourseRepeatDTO>();
		try{
			
			List<RepeatCourseForm> allForms=rep.getAll();
			
			for(int i=0;i<allForms.size();i++)
			{
				RepeatCourseForm form = allForms.get(i);
				if(form.getPerformed() != null && form.getPerformed().equals(true))
					filterdDTO.add(assem.toDTO(allForms.get(i)));
			}
		
			}
			catch(Exception ex)
			{
				System.out.println("-------Error in getting old petition");
				ex.printStackTrace();
				
			}
		return filterdDTO;
	}

	@Override
	public CourseRepeatDTO updateStatusOfForm(CourseRepeatDTO dto) {
		try{
			RepeatCourseForm form=assem.toEntity(dto);
			form=rep.update(form);
			if(form.getInsNotifyDate() ==null)
			{
				sharedNotifyService.removeJobFromScheduler("JRepeatCourseForm",form.getId());
			}	
			return assem.toDTO(form);
			}
			catch(Exception ex)
			{
				System.out.println("<<<<<<<<<< Form can't be updated >>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public CourseRepeatDTO getById(Integer id) {
		CourseRepeatDTO dto=new CourseRepeatDTO();
		try
		{
			RepeatCourseForm form=rep.getById(id);
			dto= assem.toDTO(form);
		}
		catch(Exception ex)
		{
			
		}
		return dto;
	}

	@Override
	public void addComment(Integer id, String comment) {
		try{
			RepeatCourseForm form = rep.getById(id);		
			form.setComment(comment); rep.update(form);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		
	}

}
