/**
 * 
 */
package main.com.zc.shared.appService.impl;

import main.com.zc.services.domain.person.model.ILoginDataRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.shared.appService.IPersonGetDataAppService;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya alaa
 *
 */
@Service("IPersonGetDataAppService")
public class PersonGetDataAppServiceImpl implements IPersonGetDataAppService{

	@Autowired
	IStudentRepository personRep;

	@Autowired
	ILoginDataRepository loginStaffRep;
	@Override
	public PersonDataDTO getPersonByPersonMail(String mail) {
		try{

		Student person=personRep.getPersonByMail(mail);
		PersonDataDTO dto=new PersonDataDTO
				(person.getFileNo(), person.getData().getNameInEnglish(), person.getData().getNameInArabic(),person.getId());
		dto.setLevelID(person.getLevel());
		return dto;
		}
		catch(IndexOutOfBoundsException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public PersonDataDTO getPersonByFileNo(Integer id) {
		try{

			Student person=personRep.getPersonByFileNo(id);
			PersonDataDTO dto=new PersonDataDTO
					(person.getFileNo(), person.getData().getNameInEnglish(), person.getData().getNameInArabic(),person.getId());
			dto.setLevelID(person.getLevel());
			return dto;
			}
			catch(IndexOutOfBoundsException ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

}
