/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.facade;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IStudentAddDropFormFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.Instruction;

/**
 * @author omnya
 *
 */
@Service("IGeneratePDFFacade")
public class GeneratePDFFacadeImpl implements IGeneratePDFFacade{

	@Autowired
	ICoursesRepository coursesRep;
    @Autowired
	private IStudentAddDropFormFacade coursesFacade;

	@Override
	public List<BaseDTO> getAllCourses() {
		List<Courses> courses=coursesRep.getAll();
		List<BaseDTO> dtos=new ArrayList<BaseDTO>();
		for(int i=0;i<courses.size();i++)
		{
			dtos.add(new BaseDTO(courses.get(i).getId(), courses.get(i).getName()));
		}
		return dtos;
	}

	@Override
	public List<BaseDTO> getInstructorsOfCourse(Integer courseID) {
		List<InstructorDTO> instructors=coursesFacade.getAllInstructorsOfCourse(courseID);
		List<BaseDTO> dtos=new ArrayList<BaseDTO>();
		for(int i=0;i<instructors.size();i++)
		{
			dtos.add(new BaseDTO(instructors.get(i).getId(), instructors.get(i).getName()));
		}
		return dtos;
	}

	@Override
	public List<BaseDTO> getCoursesByYearAndSem(Integer year, Integer sem) {
		List<Courses> courses=new ArrayList<Courses>();
		List<BaseDTO> coursesDTO=new ArrayList<BaseDTO>();
		try{
		courses=coursesRep.getByYearAndSemester(sem,year);
		for(int i=0;i<courses.size();i++)
		{
			BaseDTO dto=new BaseDTO();
			dto.setId(courses.get(i).getId());
			dto.setName(courses.get(i).getName());
			
			
			coursesDTO.add(dto);
		}
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting courses");
			ex.printStackTrace();
		}
		return coursesDTO;
	}
	
	
}
