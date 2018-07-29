/**
 * 
 */
package main.com.zc.services.applicationService.persons.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.persons.service.IInstructorAppService;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author Omnya Alaa
 *
 */
@Service
public class InstructorAppServiceImpl implements IInstructorAppService {
	
	
	@Autowired
	IEmployeeRepository insRep; 
	
	@Autowired
	ICourse_InstructorRepository courseInsRep;
	@Override
	public List<InstructorDTO> getAllTAs() {
		List<Employee> instructors=new ArrayList<Employee>();
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		
		instructors=insRep.getAllTas();
		for(int i=0;i<instructors.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			dto.setId(instructors.get(i).getId());
			dto.setMail(instructors.get(i).getMail());
			dto.setName(instructors.get(i).getName());
			dto.setEmpID(instructors.get(i).getEmpID());
			dto.setTitle(instructors.get(i).getTitle());
			dto.setPhone(instructors.get(i).getPhone());
			dto.setEmpType(instructors.get(i).getType());
			dto.setPhoto(instructors.get(i).getImage());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public InstructorDTO update(InstructorDTO ins) {
		try{
			Employee addedIns=new Employee();
			addedIns=insRep.getById(ins.getId());
			addedIns.setName(ins.getName());
			addedIns.setMail(ins.getMail());
			addedIns.setType(ins.getEmpType());
			addedIns.setTitle(ins.getTitle());
			addedIns.setPhone(ins.getPhone());
			addedIns.setImage(ins.getPhoto());
			addedIns=insRep.update(addedIns);
		    ins=new InstructorDTO();
		
			return ins;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			
			return null;
			}
	}

	@Override
	public List<InstructorDTO> getAllIns() {
		List<Employee> instructors=new ArrayList<Employee>();
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		
		instructors=insRep.getByType(1);
		for(int i=0;i<instructors.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			dto.setId(instructors.get(i).getId());
			dto.setMail(instructors.get(i).getMail());
			dto.setName(instructors.get(i).getName());
			dto.setEmpID(instructors.get(i).getEmpID());
			dto.setTitle(instructors.get(i).getTitle());
			dto.setPhone(instructors.get(i).getPhone());
			dto.setEmpType(instructors.get(i).getType());
			dto.setPhoto(instructors.get(i).getImage());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<InstructorDTO> getByCourseID(Integer id) {
		List<Courses_Instructors> coursesInsLst=new ArrayList<Courses_Instructors>();
		try{
			coursesInsLst=courseInsRep.getByCourseId(id);
			List<InstructorDTO> insLst=new ArrayList<InstructorDTO>();
			for(int i=0;i<coursesInsLst.size();i++)
			{
				InstructorDTO dto=new InstructorDTO();
				dto.setId(coursesInsLst.get(i).getInstructor().getId());
				dto.setMail(coursesInsLst.get(i).getInstructor().getMail());
				dto.setName(coursesInsLst.get(i).getInstructor().getName());
				dto.setEmpID(coursesInsLst.get(i).getInstructor().getEmpID());
				dto.setTitle(coursesInsLst.get(i).getInstructor().getTitle());
				dto.setPhone(coursesInsLst.get(i).getInstructor().getPhone());
				dto.setEmpType(coursesInsLst.get(i).getInstructor().getType());
				dto.setPhoto(coursesInsLst.get(i).getInstructor().getImage());
				insLst.add(dto);
			}
			return insLst;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}

}
