/**
 * 
 */
package main.com.zc.services.presentation.accountSetting.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.persons.service.IStudentProfileService;
import main.com.zc.services.applicationService.shared.service.IMajorAppService;
import main.com.zc.services.presentation.accountSetting.facade.IStudentProfileFacade;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;

/**
 * @author omnya
 *
 */
@Service("IStudentProfileFacade")
public class StudentProfileFacadeImpl implements IStudentProfileFacade{

	@Autowired
	IStudentProfileService service;

	@Autowired
	IMajorAppService majorSerice;

	@Override
	public StudentProfileDTO add(StudentProfileDTO form) {
		
		return service.add(form);
	}

	@Override
	public boolean remove(Integer id) {
		
		return service.remove(id);
	}

	@Override
	public StudentProfileDTO update(StudentProfileDTO form) {
		
		return service.update(form);
	}

	@Override
	public List<StudentProfileDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public StudentProfileDTO getById(Integer id) {
		
		return service.getById(id);
	}

	@Override
	public List<StudentProfileDTO> getByStudentID(Integer id) {
		
		return service.getByStudentID(id);
	}

	@Override
	public List<StudentProfileDTO> getBySemesterAndYear(Integer sem,
			Integer year) {
		
		return service.getBySemesterAndYear(sem, year);
	}

	@Override
	public StudentProfileDTO getBySemesterAndYearAndStudentId(
			Integer sem, Integer year, Integer id) {
		
		return service.getBySemesterAndYearAndStudentId(sem, year, id);
	}

	@Override
	public StudentProfileDTO updatePhoto(byte[] studentImage, StudentProfileDTO student) {
		return service.updatePhoto(studentImage,student);
	}
	@Override
	public List<MajorDTO> getAllMajors() {
		
		return majorSerice.getAll();
	}

	@Override
	public StudentProfileDTO saveStudentProfile(StudentProfileDTO form) {
		try{
			return service.saveStudentProfile(form);
			}catch(Exception ex){
				return null;
			}
	}

	@Override
	public StudentProfileDTO getCurrentPRofileByStudentID(Integer id) {
		return service.getCurrentPRofileByStudentID(id);
	}
	
	
}
