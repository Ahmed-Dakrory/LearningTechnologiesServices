/**
 * 
 */
package main.com.zc.services.applicationService.persons.service;

import java.util.List;

import main.com.zc.services.presentation.users.dto.StudentProfileDTO;

/**
 * @author omnya
 *
 */
public interface IStudentProfileService {

	public StudentProfileDTO add(StudentProfileDTO form);

	public boolean remove(Integer id);

	public StudentProfileDTO update(StudentProfileDTO form);

	public List<StudentProfileDTO> getAll();

	public StudentProfileDTO getById(Integer id);
	
	public List<StudentProfileDTO> getByStudentID(Integer id);
	
	public List<StudentProfileDTO>getBySemesterAndYear(Integer sem, Integer year);
	
	public StudentProfileDTO getBySemesterAndYearAndStudentId(Integer sem, Integer year , Integer id);
	
	public StudentProfileDTO updatePhoto(byte[] studentImage, StudentProfileDTO student);

	StudentProfileDTO saveStudentProfile(StudentProfileDTO form);
	
	public boolean checkStudentProfile(String mail);

	StudentProfileDTO getCurrentPRofileByStudentID(Integer id);
	
}
