/**
 * 
 */
package main.com.zc.services.applicationService.configuration.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.configuration.assembler.StudentsCoursesNumberAssembler;
import main.com.zc.services.applicationService.configuration.services.IStudentsCoursesNumberAppService;
import main.com.zc.services.domain.configurations.model.IStudentsCoursesNumberRep;
import main.com.zc.services.domain.configurations.model.StudentsCoursesNumber;
import main.com.zc.services.domain.lectureObjectiveFeedback.model.SemesterWeeks;
import main.com.zc.services.presentation.configuration.dto.StudentsCoursesNumberDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;

/**
 * @author omnya
 *
 */
@Service
public class StudentsCoursesNumberAppServiceImpl implements IStudentsCoursesNumberAppService {

	@Autowired
	IStudentsCoursesNumberRep rep;
	StudentsCoursesNumberAssembler assem=new StudentsCoursesNumberAssembler();
	@Override
	public List<StudentsCoursesNumberDTO> getAll() {
		List<StudentsCoursesNumber> weeks=new ArrayList<StudentsCoursesNumber>();
		List<StudentsCoursesNumberDTO> dtos=new ArrayList<StudentsCoursesNumberDTO>();
		try{
			weeks=rep.getAll();
		for(int i=0;i<weeks.size();i++)
		{
			StudentsCoursesNumberDTO dto=assem.toDTO(weeks.get(i));
			dtos.add(dto);
			
		}
		return dtos;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public StudentsCoursesNumberDTO getByCourseID(Integer id) {
		StudentsCoursesNumber num=new StudentsCoursesNumber();
		
		StudentsCoursesNumberDTO dto=new StudentsCoursesNumberDTO();
		try{
			num=rep.getByCourseID(id);
			dto=assem.toDTO(num);
	
		return dto;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public StudentsCoursesNumberDTO getById(Integer id) {
StudentsCoursesNumber num=new StudentsCoursesNumber();
		
		StudentsCoursesNumberDTO dto=new StudentsCoursesNumberDTO();
		try{
			num=rep.getById(id);
			dto=assem.toDTO(num);
	
		return dto;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public StudentsCoursesNumberDTO add(StudentsCoursesNumberDTO courseStudent) {
		try{
			StudentsCoursesNumber week=new StudentsCoursesNumber();
			week=assem.toEntity(courseStudent);
			StudentsCoursesNumber addedCopy=rep.add(week);
			StudentsCoursesNumberDTO newDto=new StudentsCoursesNumberDTO();
			newDto=assem.toDTO(addedCopy);
			return newDto;
			}
			catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public StudentsCoursesNumberDTO update(StudentsCoursesNumberDTO courseStudent) {
		try{
			StudentsCoursesNumber week=new StudentsCoursesNumber();
			week=assem.toEntity(courseStudent);
			StudentsCoursesNumber old=new StudentsCoursesNumber();
			old=rep.getByCourseID(courseStudent.getCourse().getId());
			if(old!=null)
			{
				if(old.getId()!=null)
				{
					old.setNum(week.getNum());
					StudentsCoursesNumber updated=rep.update(old);
					StudentsCoursesNumberDTO newDto=new StudentsCoursesNumberDTO();
					newDto=assem.toDTO(updated);
					return newDto;
				}
				else {
					StudentsCoursesNumberDTO newDto=add(courseStudent);
					return newDto;
				}
			}
			else {
				StudentsCoursesNumberDTO newDto=add(courseStudent);
				return newDto;
			}
			
			}
			catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean remove(StudentsCoursesNumberDTO obj) {
		try{ 
			StudentsCoursesNumber updated=rep.getById(obj.getId());
			return rep.remove(updated);
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

}
