/**
 * 
 */
package main.com.zc.service.student;

import main.com.zc.services.domain.person.model.ILoginDataRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya alaa
 *
 */
@Service("IStudentGetDataAppService")
public class StudentGetDataAppServiceImpl implements IStudentGetDataAppService{

	@Autowired
	IStudentRepository personRep;

	@Autowired
	ILoginDataRepository loginStaffRep;
	@Override
	public Student getStudentByPersonMail(String mail) {
		try{

		Student person=personRep.getPersonByMail(mail);
		return person;
		}
		catch(IndexOutOfBoundsException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public Student getStudentByFileNo(Integer id) {
		try{

			Student person=personRep.getPersonByFileNo(id);
			
			return person;
			}
			catch(IndexOutOfBoundsException ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

}
