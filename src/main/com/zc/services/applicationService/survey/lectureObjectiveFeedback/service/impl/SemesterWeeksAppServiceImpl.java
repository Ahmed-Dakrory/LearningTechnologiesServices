/**
 * 
 */
package main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.assembler.SemesterWeeksAssembler;
import main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.service.ISemesterWeeksAppService;
import main.com.zc.services.domain.lectureObjectiveFeedback.model.ISemesterWeeksRepository;
import main.com.zc.services.domain.lectureObjectiveFeedback.model.SemesterWeeks;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;

/**
 * @author omnya
 *
 */
@Service
public class SemesterWeeksAppServiceImpl implements  ISemesterWeeksAppService{

	@Autowired
	ISemesterWeeksRepository rep;
	SemesterWeeksAssembler assem=new SemesterWeeksAssembler();
	@Override
	public SemesterWeeksDTO add(SemesterWeeksDTO dto) {
		try{
			SemesterWeeks week=new SemesterWeeks();
			week=assem.toEntity(dto);
			SemesterWeeks addedCopy=rep.add(week);
			SemesterWeeksDTO newDto=new SemesterWeeksDTO();
			newDto=assem.toDTO(addedCopy);
			return newDto;
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
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public SemesterWeeksDTO update(SemesterWeeksDTO dto) {
		try{
			SemesterWeeks week=new SemesterWeeks();
			week=assem.toEntity(dto);
			SemesterWeeks updated=rep.update(week);
			SemesterWeeksDTO newDto=new SemesterWeeksDTO();
			newDto=assem.toDTO(updated);
			return newDto;
			}
			catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<SemesterWeeksDTO> getAll() {
		List<SemesterWeeks> weeks=new ArrayList<SemesterWeeks>();
		List<SemesterWeeksDTO> dtos=new ArrayList<SemesterWeeksDTO>();
		try{
			weeks=rep.getAll();
		for(int i=0;i<weeks.size();i++)
		{
			SemesterWeeksDTO dto=assem.toDTO(weeks.get(i));
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
	public SemesterWeeksDTO getById(Integer id) {
		SemesterWeeks copy=new SemesterWeeks();
		
		SemesterWeeksDTO dto=new SemesterWeeksDTO();
		try{
			copy=rep.getById(id);
			dto=assem.toDTO(copy);
	
		return dto;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SemesterWeeksDTO> getBySemesterAndyear(Integer sem, Integer year) {
		List<SemesterWeeks> weeks=new ArrayList<SemesterWeeks>();
		List<SemesterWeeksDTO> dtos=new ArrayList<SemesterWeeksDTO>();
		try{
			weeks=rep.getBySemesterAndyear(sem,year);
		for(int i=0;i<weeks.size();i++)
		{
			SemesterWeeksDTO dto=assem.toDTO(weeks.get(i));
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
	public List<SemesterWeeksDTO> getActiveBySemesterAndyear(Integer sem, Integer year) {
		List<SemesterWeeks> weeks=new ArrayList<SemesterWeeks>();
		List<SemesterWeeksDTO> dtos=new ArrayList<SemesterWeeksDTO>();
		try{
			weeks=rep.getActiveBySemesterAndyear(sem,year);
		for(int i=0;i<weeks.size();i++)
		{
			SemesterWeeksDTO dto=assem.toDTO(weeks.get(i));
			dtos.add(dto);
			
		}
		return dtos;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

}
