/**
 * 
 */
package main.com.zc.services.applicationService.shared.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.shared.service.IGetStudentByFileNoAppService;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author Omnya ALaa
 *
 */
@Service
public class GetStudentByFileNoAppServiceImpl implements IGetStudentByFileNoAppService {

	@Autowired
	IStudentRepository personRep;
	@Override
	public PersonDataDTO getStudentDataByFileNo(int fileNo) {
		try{
		Student person= personRep.getPersonByFileNo(fileNo);
		PersonDataDTO dao=new PersonDataDTO(person.getFileNo(), person.getData().getNameInEnglish(), 
				person.getData().getNameInArabic(),person.getData().getMail(),person.getId());
		
		return dao;
		}
		catch(IndexOutOfBoundsException ex)
		{
			return null;
		}
	}

}
