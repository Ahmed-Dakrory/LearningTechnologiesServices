/**
 * 
 */
package main.com.zc.services.presentation.attendance.petitions.beans;

import java.io.IOException;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;

import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.attendance.petitions.dto.DailyAttPetDto;
import main.com.zc.services.presentation.attendance.petitions.facade.IEditDailyAttendanceFacade;
import main.com.zc.services.presentation.attendance.petitions.facade.IParseDailyAttPetFileFacade;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Component;

/**
 * @author Omnya Alaa
 *
 */
@Component
@SessionScoped
@ManagedBean
public class DailyAttendancePetitionBean {

	private String firstName;
	@Autowired
	IParseDailyAttPetFileFacade parseDailyPetFacade;
	@Autowired
	IEditDailyAttendanceFacade editDailyAttFacade;
	/*@Autowired
	IPersonRepository perRep;*/
	@PostConstruct
	public void init()
	{
		/*List<Person> persons=perRep.getAll();
		String mails="";
		for(int i=0;i<persons.size();i++)
		{
			mails+=persons.get(i).getData().getMail()+" , ";
			
		}
		System.out.println("Mails >> "+persons.size());
		System.out.println("---------");
		System.out.println(mails);*/
		
	}
	public void editAttendance()
	{
		// parse file of petitions 
		String fileName="resources/April 1st week.xlsx";
		try {
			List<DailyAttPetDto> petitions=parseDailyPetFacade.parsePetFile(fileName);
			System.out.println(petitions.size());
			// send emails to students 
			editDailyAttFacade.sendEmailByEditingDailyAtt(petitions);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
}
