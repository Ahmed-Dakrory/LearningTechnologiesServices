/**
 * 
 */
package main.com.zc.shared.appService.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.ILoginDataRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.LoginData;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.presentation.survey.courseFeedback.dto.InstructorDTO;
import main.com.zc.shared.appService.IGetInstructorDataAppService;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;

/**
 * @author Omnya Alaa
 * 
 */
@Service("IGetInstructorDataAppService")
public class GetInstructorDataAppServiceImpl implements
		IGetInstructorDataAppService {

	@Autowired
	IEmployeeRepository insRep;

	@Autowired
	ICoursesRepository courseRep;
	@Autowired
	IMajorRepository majorRep;
	@Override
	public InstructorDTO getInsByMail(String mail) {
		try{
			Employee ins = insRep.getByMail(mail);
			InstructorDTO dto=new InstructorDTO();
			dto.setInsID(ins.getId());

			return dto;
			}
			
			catch(Exception ex)
			{
				System.out.println("Error In getting Is data");
				ex.printStackTrace();
				return new InstructorDTO();
			}
	}

	@Override
	public main.com.zc.services.presentation.users.dto.InstructorDTO getInsByMailNew(String mail) {
		try{
			Employee ins = insRep.getByMail(mail);
			main.com.zc.services.presentation.users.dto.InstructorDTO dto=new main.com.zc.services.presentation.users.dto.InstructorDTO();
			dto.setId(ins.getId());
			dto.setName(ins.getName());
			dto.setMail(ins.getMail());
			dto.setEmpType(ins.getType());
			return dto;
			}
			
			catch(Exception ex)
			{
				System.out.println("Error In getting Is data");
				ex.printStackTrace();
				return new main.com.zc.services.presentation.users.dto.InstructorDTO();
			}
	}

	@Override
	public boolean isCouurseCoordinator(String mail) {
		try{
		List<Courses> courses=courseRep.getByCourseCoordinatorID(mail);
		if(courses==null)
		{
		 return false;
		}
		else if(courses.size()==0)
		{
			return false;
			
		}
		else return true;
		
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in check the istructor");
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isPA(String mail) {
		try{
			Employee ins = insRep.getByMail(mail);
			List<Majors> outputs=majorRep.getByInsID(ins.getId());
			if(outputs==null||outputs.size()==0)
			{
				return false;
			}
			else 
				return true;
			}
			
			catch(Exception ex)
			{
				System.out.println("Error In getting Is data");
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public  main.com.zc.services.presentation.users.dto.InstructorDTO getInsByPersonID(Integer id) {
		main.com.zc.services.presentation.users.dto.InstructorDTO dto=new main.com.zc.services.presentation.users.dto.InstructorDTO();
		
		try{
			Employee ins = insRep.getById(id);
			dto.setId(ins.getId());
			dto.setName(ins.getName());
			dto.setMail(ins.getMail());
			
		}
			catch(Exception ex)
			{
				System.out.println("Error In getting Is data");
				ex.printStackTrace();
				
			}
			return dto;
	}
}