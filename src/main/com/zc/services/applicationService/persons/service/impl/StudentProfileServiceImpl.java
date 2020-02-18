/**
 * 
 */
package main.com.zc.services.applicationService.persons.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.configuration.services.IFormsStatusAppService;
import main.com.zc.services.applicationService.persons.assembler.StudentProfileAssembler;
import main.com.zc.services.applicationService.persons.service.IStudentProfileService;
import main.com.zc.services.domain.data.model.Data;
import main.com.zc.services.domain.data.model.IDataRepository;
import main.com.zc.services.domain.data.model.IStudentProfileRep;
import main.com.zc.services.domain.data.model.StudentProfile;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author omnya
 *
 */
@Service
public class StudentProfileServiceImpl implements IStudentProfileService {

	@Autowired
	IStudentProfileRep rep;

	StudentProfileAssembler assem = new StudentProfileAssembler();
	@Autowired
	IDataRepository dataRep;
	@Autowired
	IStudentRepository studentRep;
	@Autowired
	IFormsStatusAppService formsStatusAppService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public StudentProfileDTO saveStudentProfile(StudentProfileDTO form) {
		Student student = studentRep.getPersonById(form.getStudent().getId());
		Data data = student.getData();
		data.setGender(form.getGender());
		data.setPhone(form.getMobile());
		dataRep.update(data);
		student.setData(data);
		StudentProfile profile = rep.getBySemesterAndYearAndStudentId(form
				.getSemester().getID(), form.getYear(), form.getStudent()
				.getId());
		if (profile != null) {// update
			profile.setCompletedCreditHrs(form.getCompletedCreditHrs());
			profile.setCurrentCreditHrs(form.getRegisteredCreditHrs());
			profile.setGpa(form.getGpa());
			Majors majors = new Majors();
			majors.setId(form.getMajor().getId());
			profile.setMajor(majors);
			profile.setRepeatedCourses(form.getRepeatedCourses());
			profile = rep.update(profile);
		} else {
			// add new
			profile = new StudentProfile();
			profile.setCompletedCreditHrs(form.getCompletedCreditHrs());
			profile.setCurrentCreditHrs(form.getRegisteredCreditHrs());
			profile.setGpa(form.getGpa());
			Majors majors = new Majors();
			majors.setId(form.getMajor().getId());
			profile.setMajor(majors);
			profile.setRepeatedCourses(form.getRepeatedCourses());
			profile.setYear(form.getYear());
			profile.setSemester(form.getSemester());
			profile.setStudent(student);
			profile = rep.add(profile);
		}
		return assem.toDTO(profile);
	}

	@Override
	public StudentProfileDTO add(StudentProfileDTO form) {
		try {
			StudentProfile profile = assem.toEntity(form);

			// Update Data of student
			try {
				Data data = profile.getStudent().getData();
				data.setGender(form.getGender());
				data.setPhone(form.getMobile());
				data.setStudentImage(form.getStudentImage());
				dataRep.update(data);
			} catch (Exception ex) {
				System.out.println("Can't update the data of student");
				ex.printStackTrace();
			}

			StudentProfile addedProfile = rep.add(profile);
			StudentProfileDTO dto = assem.toDTO(addedProfile);
			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean remove(Integer id) {
		try {
			return rep.remove(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	@Override
	public StudentProfileDTO update(StudentProfileDTO form) {
		try {

			StudentProfile profile = assem.toEntity(form);

			// Update Data of student
			try {
				if (profile.getId() != null) {
					Data data = profile.getStudent().getData();
					data.setGender(form.getGender());
					data.setPhone(form.getMobile());
					data.setStudentImage(form.getStudentImage());
					dataRep.update(data);
				} else {
					Data data = dataRep.getByMail(form.getStudent().getMail());
					data.setGender(form.getGender());
					data.setPhone(form.getMobile());
					data.setStudentImage(form.getStudentImage());
					dataRep.update(data);
				}
			} catch (Exception ex) {
				System.out.println("Can't update the data of student");
				ex.printStackTrace();
			}

			StudentProfile addedProfile = rep.update(profile);
			StudentProfileDTO dto = assem.toDTO(addedProfile);
			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<StudentProfileDTO> getAll() {
		List<StudentProfileDTO> dtos = new ArrayList<StudentProfileDTO>();

		try {
			List<StudentProfile> objects = new ArrayList<StudentProfile>();
			objects = rep.getAll();
			for (int i = 0; i < objects.size(); i++) {
				StudentProfileDTO dto = new StudentProfileDTO();
				dto = assem.toDTO(objects.get(i));
				dtos.add(dto);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public StudentProfileDTO getById(Integer id) {

		try {
			StudentProfile object = new StudentProfile();
			object = rep.getById(id);
			StudentProfileDTO dto = new StudentProfileDTO();
			dto = assem.toDTO(object);
			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<StudentProfileDTO> getByStudentID(Integer id) {
		List<StudentProfileDTO> dtos = new ArrayList<StudentProfileDTO>();

		try {
			List<StudentProfile> objects = new ArrayList<StudentProfile>();
			objects = rep.getByStudentID(id);
			for (int i = 0; i < objects.size(); i++) {
				StudentProfileDTO dto = new StudentProfileDTO();
				dto = assem.toDTO(objects.get(i));
				dtos.add(dto);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dtos;
	}
	

	@Override
	public List<StudentProfileDTO> getBySemesterAndYear(Integer sem,
			Integer year) {
		List<StudentProfileDTO> dtos = new ArrayList<StudentProfileDTO>();

		try {
			List<StudentProfile> objects = new ArrayList<StudentProfile>();
			objects = rep.getBySemesterAndYear(sem, year);
			for (int i = 0; i < objects.size(); i++) {
				StudentProfileDTO dto = new StudentProfileDTO();
				dto = assem.toDTO(objects.get(i));
				dtos.add(dto);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public StudentProfileDTO getBySemesterAndYearAndStudentId(Integer sem,
			Integer year, Integer id) {
		try {

			StudentProfile studentProfile = new StudentProfile();
			studentProfile = rep
					.getBySemesterAndYearAndStudentId(sem, year, id);
			if (studentProfile != null) {
				StudentProfileDTO dto = new StudentProfileDTO();
				dto = assem.toDTO(studentProfile);
				return dto;
			} else {
				StudentProfileDTO dto = new StudentProfileDTO();
				dto.setId(null);
				Student student = studentRep.getPersonById(id);
				dto.setGender(student.getData().getGender());
				dto.setMobile(student.getData().getPhone());
				dto.setStudentImage(student.getData().getStudentImage());
				StudentDTO studentDTO = new StudentDTO();
				studentDTO.setId(student.getId());
				dto.setStudent(studentDTO);
				dto.setYear(year);
				switch (sem) {
				case 0:
					dto.setSemester(SemesterEnum.Fall);
					break;

				case 1:
					dto.setSemester(SemesterEnum.Spring);
					break;
				case 2:
					dto.setSemester(SemesterEnum.Summer);
					break;
				case 3:
					dto.setSemester(SemesterEnum.Winter);
					break;
				}

				return dto;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public StudentProfileDTO updatePhoto(byte[] studentImage,
			StudentProfileDTO student) {
		// get student by id
		// get data of student
		// update the data of student
		// update the data of student in profile object
		Student studentObject = studentRep.getPersonById(student.getStudent()
				.getId());
		Data data = studentObject.getData();
		data.setStudentImage(studentImage);
		int i = dataRep.update(data);
		Student updatedStudentObject = studentRep.getPersonById(student
				.getStudent().getId());
		student.setStudentImage(updatedStudentObject.getData()
				.getStudentImage());

		return student;
	}

	@Override
	public boolean checkStudentProfile(String mail) {
		Student studentObject = studentRep.getPersonByMail(mail);
		if (null == studentObject.getData().getGender()
				|| null == studentObject.getData().getPhone()) {
			return false;
		}

		FormsStatusDTO formSetting = formsStatusAppService.getById(11);
		StudentProfile profile = rep.getBySemesterAndYearAndStudentId(
				formSetting.getSemester().getId(), formSetting.getYear(),
				studentObject.getId());
		if (null == profile) {
			return false;
		}
		if (null == profile.getCompletedCreditHrs()
				|| null == profile.getCurrentCreditHrs()
				|| null == profile.getGpa() || null == profile.getMajor()
				|| null == profile.getRepeatedCourses()) {
			return false;
		}
		return true;
	}

	@Override
	public StudentProfileDTO getCurrentPRofileByStudentID(Integer id) {
		FormsStatusDTO formSetting = formsStatusAppService.getById(11);
		StudentProfile studentProfile = rep.getBySemesterAndYearAndStudentId(
				formSetting.getSemester().getId(), formSetting.getYear(),
				id);
		if (studentProfile != null) {
			StudentProfileDTO dto = new StudentProfileDTO();
			dto = assem.toDTO(studentProfile);
			return dto;
		} else {
			StudentProfileDTO dto = new StudentProfileDTO();
			dto.setId(null);
			Student student = studentRep.getPersonById(id);
			dto.setGender(student.getData().getGender());
			dto.setMobile(student.getData().getPhone());
			dto.setStudentImage(student.getData().getStudentImage());
			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setId(student.getId());
			dto.setStudent(studentDTO);
			dto.setYear(formSetting.getYear());
			switch (formSetting.getSemester().getId()) {
			case 0:
				dto.setSemester(SemesterEnum.Fall);
				break;

			case 1:
				dto.setSemester(SemesterEnum.Spring);
				break;
			case 2:
				dto.setSemester(SemesterEnum.Summer);
				break;
			case 3:
				dto.setSemester(SemesterEnum.Winter);
				break;
			}

			return dto;
	}}
}
