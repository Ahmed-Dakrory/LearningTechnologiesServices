/**
 * 
 */
package main.com.zc.services.applicationService.forms.tAJuniorProgram.assembler;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.petition.model.TAJuniorProgram;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class TAJuniorProgramAssembler {
	public TAJuniorProgramDTO toDTO(TAJuniorProgram form)
	{
		TAJuniorProgramDTO dto=new  TAJuniorProgramDTO();
		
	    dto.setId(form.getId());
	    dto.setComment(form.getComment());
	    dto.setPerformed(form.getPerformed());
	    dto.setGpa(form.getGpa());
	    dto.setHours(form.getHours());
	    dto.setStep(form.getStep());
	    dto.setSubmissionDate(form.getSubmissionDate());
	    dto.setGrade(form.getGrade());
	    dto.setHoursINS(form.getHoursINS());
	    dto.setTask(form.getTask());
	    try{
		dto.setImage(form.getStudent().getData().getStudentImage());
	    }
	    catch(Exception  ex){
	    	
	    }
		try{
			CoursesDTO course=new CoursesDTO();
			course.setId(form.getCourse().getId());
			course.setName(form.getCourse().getName());
			InstructorDTO ins=new InstructorDTO();
			ins.setId(form.getCourse().getCourseCoordinator().getId());
			ins.setMail(form.getCourse().getCourseCoordinator().getMail());
			ins.setName(form.getCourse().getCourseCoordinator().getName());
			course.setCoordinator(ins);
			dto.setCourse(course);
			
			}
			catch(Exception ex)
			{
				System.out.println("Can't add  course to petitions");
			}

		 try{
				MajorDTO major=new MajorDTO();
				major.setId(form.getMajor().getId());
				major.setMajorName(form.getMajor().getMajorName());
				InstructorDTO majorIns=new InstructorDTO(); 
				majorIns.setId(form.getMajor().getHeadOfMajorId().getId());
				majorIns.setName(form.getMajor().getHeadOfMajorId().getName());
				majorIns.setMail(form.getMajor().getHeadOfMajorId().getMail());
				major.setHeadOfMajor(majorIns);
				dto.setMajor(major);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		  try{
				StudentDTO studentDTO=new StudentDTO();
				studentDTO.setId(form.getStudent().getId());
				studentDTO.setMail(form.getStudent().getData().getMail());
				studentDTO.setFacultyId(form.getStudent().getFileNo());
				studentDTO.setName(form.getStudent().getData().getNameInEnglish());
		       
				dto.setStudent(studentDTO);
			   }
		        catch(Exception ex)
				{
					System.out.println("Can't add student to petitions");
				}
  	 
		  if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
			{
			  
				dto.setCurStatus("Under Review");
				
				if(form.getCourse().getCourseCoordinator()!=null)
				{
				dto.setNextStatus("Reviewing By " + form.getCourse().getCourseCoordinator().getName()+ " (Course Instructor)");
				}
				else
					dto.setNextStatus("Course Instructor");
			}
		  
		  
			else if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
			{
				if(form.getCourse().getCourseCoordinator()!=null)
			
					dto.setCurStatus("Reviewed By " + form.getCourse().getCourseCoordinator().getName() + " (Course Coordinator)");
				else
					dto.setCurStatus("Reviewed By Course Coordinator");
				dto.setNextStatus("Reviewing By Program Advisor");
			}
				
			
			
			else if(form.getStep().equals(PetitionStepsEnum.PA))
			{
				
				if(form.getMajor().getHeadOfMajorId()!=null)
					
					dto.setCurStatus("Reviewed By " + form.getMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
				else
				dto.setCurStatus("Reviewed By Program Advisor");
				
				dto.setNextStatus("Reviewing By Dean of Academic Affairs");
				}
			
			
			else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
			{
				dto.setCurStatus("Reviewed By Dean of Academic Affairs");
				dto.setNextStatus("");
			}
		
			
		return dto;
	}
	public TAJuniorProgram toEntity(TAJuniorProgramDTO dto)
	{
	TAJuniorProgram form=new  TAJuniorProgram();
		
	form.setId(dto.getId());
	form.setComment(dto.getComment());
	form.setPerformed(dto.getPerformed());
	form.setGpa(dto.getGpa());
	form.setHours(dto.getHours());
	form.setStep(dto.getStep());
	form.setSubmissionDate(dto.getSubmissionDate());
	form.setGrade(dto.getGrade());
	form.setHoursINS(dto.getHoursINS());
    form.setTask(dto.getTask());
		try{
			Courses course=new Courses();
			course.setId(dto.getCourse().getId());
			
			form.setCourse(course);
			
			}
			catch(Exception ex)
			{
				System.out.println("Can't add  course to petitions");
			}

		 try{
				Majors major=new Majors();
				major.setId(dto.getMajor().getId());
				form.setMajor(major);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		  try{
			  
			  Student student=new Student();
			  student.setId(dto.getStudent().getId());
				
				form.setStudent(student);
			   }
		        catch(Exception ex)
				{
					System.out.println("Can't add student to petitions");
				}
  	 
		
		return form;
	}
	
}
