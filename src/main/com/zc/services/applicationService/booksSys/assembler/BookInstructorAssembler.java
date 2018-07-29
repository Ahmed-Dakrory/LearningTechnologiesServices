/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.assembler;

import main.com.zc.services.domain.booksSys.model.BookInstructor;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.presentation.booksSys.dto.BookInstructorDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public class BookInstructorAssembler {

	public BookInstructorDTO toDTO(BookInstructor obj)
	{
		BookInstructorDTO dto=new BookInstructorDTO();
		dto.setId(obj.getId());
		dto.setDate(obj.getDate());
		dto.setBarCode(obj.getBarCode());
		dto.setAction(obj.getAction());
		try
		{
			InstructorDTO ins=new  InstructorDTO();
			ins.setId(obj.getInstructor().getId());
			ins.setName(obj.getInstructor().getName());
			ins.setMail(obj.getInstructor().getMail());
			dto.setInstructor(ins);
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return dto;
		
	}
	public BookInstructor toEntity(BookInstructorDTO dto)
	{
		BookInstructor obj=new BookInstructor();
		obj.setId(dto.getId());
		obj.setDate(dto.getDate());
		obj.setBarCode(dto.getBarCode());
		obj.setAction(dto.getAction());
		try{
			Employee ins=new  Employee();
			ins.setId(dto.getInstructor().getId());
			ins.setName(dto.getInstructor().getName());
			obj.setInstructor(ins);
		}
		catch(Exception ex){
			
		}
		
		return obj;
		
	}
}
