/**
 * 
 */
package main.com.zc.services.applicationService.forms.tAJuniorProgram.service.impl;

import java.util.ArrayList;
import java.util.List;
import main.com.zc.services.applicationService.forms.tAJuniorProgram.assembler.SkipMajorHeadCoursesAssembler;
import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.ISkipMajorHeadCoursesService;
import main.com.zc.services.domain.petition.model.IskipMajorHeadCoursesRep;
import main.com.zc.services.domain.petition.model.SkipMajorHeadCourses;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.SkipMajorHeadCoursesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class SkipMajorHeadCoursesServiceImpl implements ISkipMajorHeadCoursesService{

	@Autowired
	IskipMajorHeadCoursesRep rep;
	
	SkipMajorHeadCoursesAssembler assem=new SkipMajorHeadCoursesAssembler();
	@Override
	public SkipMajorHeadCoursesDTO add(SkipMajorHeadCoursesDTO form) {
		
		
	
			SkipMajorHeadCourses course=new SkipMajorHeadCourses();
			try{
			
				course=rep.add(assem.toEntity(form));
				SkipMajorHeadCoursesDTO dto=new SkipMajorHeadCoursesDTO();
				dto=assem.toDTO(course);
				return dto;
			}
		
			
		
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean remove(Integer id) {
			
			try{
			
				return rep.remove(id);
			}
			catch(Exception ex){
				return false;
			}
	}

	@Override
	public SkipMajorHeadCoursesDTO update(SkipMajorHeadCoursesDTO form) {
	
			SkipMajorHeadCourses course=new SkipMajorHeadCourses();
			try{
			
				course=rep.add(assem.toEntity(form));
				SkipMajorHeadCoursesDTO dto=new SkipMajorHeadCoursesDTO();
				dto=assem.toDTO(course);
				return dto;
		
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SkipMajorHeadCoursesDTO> getAll() {
		List<SkipMajorHeadCourses> courses=new ArrayList<SkipMajorHeadCourses>();
		List<SkipMajorHeadCoursesDTO> dtos=new ArrayList<SkipMajorHeadCoursesDTO>();
		try{
		
			for(int i=0;i<courses.size();i++)
			{
				SkipMajorHeadCoursesDTO  dto=new SkipMajorHeadCoursesDTO();
				dto=assem.toDTO(courses.get(i));
				dtos.add(dto);
			}

		
	}
	catch(Exception ex){
		ex.printStackTrace();
		
	}
		return dtos;
	}

	@Override
	public SkipMajorHeadCoursesDTO getById(Integer id) {
		SkipMajorHeadCourses course = new SkipMajorHeadCourses();
		SkipMajorHeadCoursesDTO dto = new SkipMajorHeadCoursesDTO();
		try {
			course = rep.getByCourseID(id);

			dto = assem.toDTO(course);
		} catch (Exception ex) {
			ex.printStackTrace();
            return null;
		}
		return dto;
	}

	@Override
	public SkipMajorHeadCoursesDTO getByCourseID(Integer id) {
		SkipMajorHeadCourses course = new SkipMajorHeadCourses();
		SkipMajorHeadCoursesDTO dto = new SkipMajorHeadCoursesDTO();
		try {
			course = rep.getByCourseID(id);

			dto = assem.toDTO(course);
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return dto;
	}

	
}
