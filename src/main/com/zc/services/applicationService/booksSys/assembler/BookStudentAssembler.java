/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.assembler;

import main.com.zc.services.domain.booksSys.model.BookStudent;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.booksSys.dto.BookStudentDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class BookStudentAssembler {

	public BookStudentDTO toDTO(BookStudent obj)
	{
		BookStudentDTO dto=new BookStudentDTO();
		dto.setId(obj.getId());
		dto.setDate(obj.getDate());
		dto.setBarCode(obj.getBarCode());
		dto.setAction(obj.getAction());
		try
		{
			StudentDTO student=new  StudentDTO();
			student.setId(obj.getStudent().getId());
			student.setName(obj.getStudent().getData().getNameInEnglish());
			student.setMail(obj.getStudent().getData().getMail());
			student.setFacultyId(obj.getStudent().getFileNo());
			dto.setStudent(student);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return dto;
		
	}
	public BookStudent toEntity(BookStudentDTO dto)
	{
		BookStudent obj=new BookStudent();
		obj.setId(dto.getId());
		obj.setDate(dto.getDate());
		obj.setBarCode(dto.getBarCode());
		obj.setAction(dto.getAction());
		try{
			Student student=new  Student();
			student.setId(dto.getStudent().getId());
			obj.setStudent(student);
		}
		catch(Exception ex){
			
		}
	
		return obj;
		
	}
}
