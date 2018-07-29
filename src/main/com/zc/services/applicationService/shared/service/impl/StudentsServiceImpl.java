/**
 * 
 */
package main.com.zc.services.applicationService.shared.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.persons.assembler.StudentProfileAssembler;
import main.com.zc.services.applicationService.shared.service.IStudentsService;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
@Service
public class StudentsServiceImpl implements IStudentsService{

	@Autowired
	IStudentRepository rep;
	@Override
	public List<StudentDTO> getAll() {
		
		List<Student> students=new ArrayList<Student>();
		List<StudentDTO> studentsDTO=new ArrayList<StudentDTO>();
		try{
		students=rep.getAll();
		
		for(int i=0;i<students.size();i++)
		{
			StudentDTO dto=new StudentDTO();
			dto.setFacultyId(students.get(i).getFileNo());
			dto.setId(students.get(i).getId());
			dto.setMail(students.get(i).getData().getMail());
			dto.setName(students.get(i).getData().getNameInEnglish());
			studentsDTO.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return studentsDTO;
	}
	
	@Override
	public StudentDTO getById(Integer id) {
		
		Student student = rep.getPersonById(id);
		StudentDTO dto = new StudentDTO();
		dto.setId(student.getId());
		dto.setName(student.getData().getNameInEnglish());
		dto.setMail(student.getData().getMail());
		dto.setFacultyId(student.getFileNo());
		
		return dto;
	}

}
