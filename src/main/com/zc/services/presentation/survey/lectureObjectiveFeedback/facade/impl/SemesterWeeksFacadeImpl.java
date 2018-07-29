/**
 * 
 */
package main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.service.ISemesterWeeksAppService;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.ISemesterWeeksFacade;

/**
 * @author omnya
 *
 */
@Service("ISemesterWeeksFacade")
public class SemesterWeeksFacadeImpl implements ISemesterWeeksFacade{

	@Autowired
	ISemesterWeeksAppService service;
	@Override
	public SemesterWeeksDTO add(SemesterWeeksDTO week) {
		
		return service.add(week);
	}

	@Override
	public boolean remove(Integer id) {
		return service.remove(id);
	}

	@Override
	public SemesterWeeksDTO update(SemesterWeeksDTO week) {
		return service.update(week);
	}

	@Override
	public List<SemesterWeeksDTO> getAll() {
		return service.getAll();
	}

	@Override
	public SemesterWeeksDTO getById(Integer id) {
		return service.getById(id);
	}

	@Override
	public List<SemesterWeeksDTO> getBySemesterAndyear(Integer sem, Integer year) {
		return service.getBySemesterAndyear(sem,year);
	}

	@Override
	public List<SemesterWeeksDTO> getActiveBySemesterAndyear(Integer sem, Integer year) {
		return service.getActiveBySemesterAndyear(sem,year);
	}

}
